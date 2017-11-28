package ServerShell;

import io.reactivex.*;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class SingleSocketedReactiveServer implements ServerShell.Server {
    SingleSocketedListenerSocket listenerSocket;
    SingleSocketedResponderSocket responderSocket;

    public SingleSocketedReactiveServer(SingleSocketedListenerSocket listenerSocket, SingleSocketedResponderSocket responderSocket) {
        this.listenerSocket = listenerSocket;
        this.responderSocket = responderSocket;
    }

    public void start() {
        CountDownLatch latch = new CountDownLatch(1);
        getListenerStream()
                .subscribeOn(Schedulers.newThread())
                .subscribe(respondWithSubscriber());
        try {
            latch.await();
        } catch (InterruptedException e) {}
    }

    private Flowable<Socket> getListenerStream() {
        return Flowable.create(new FlowableOnSubscribe<Socket>() {
            @Override
            public void subscribe(FlowableEmitter<Socket> emitter) throws Exception {
                System.out.println("starting event stream");
                while (!Thread.currentThread().isInterrupted()) {
                    emitter.onNext(listenerSocket.getClientConnection());
                }
                System.out.println("completing event stream");
                emitter.onComplete();
            }
        }, BackpressureStrategy.MISSING);
    }

    private Subscriber<Socket> respondWithSubscriber() {
        return new Subscriber<Socket>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("Subscribing to event stream");
            }

            @Override
            public void onNext(Socket clientConnection) {
                System.out.println("responding to event");
                responderSocket.respondTo(clientConnection);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.toString() + "\nerror emitted by event stream; check if the port is occupied");
            }

            @Override
            public void onComplete() {
                System.out.println("event stream reports its completion");
            }
        };
    }
}
