import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.Socket;

public class ReactiveServer {
    ListenerSocket listenerSocket;
    ResponderSocket responderSocket;

    public ReactiveServer(ListenerSocket listenerSocket, ResponderSocket responderSocket) {
        this.listenerSocket = listenerSocket;
        this.responderSocket = responderSocket;
    }

    public void start() {
        getListenerStream().subscribe(respondWithSubscriber());
    }

    private Flowable<Socket> getListenerStream() {
        return Flowable.create(new FlowableOnSubscribe<Socket>() {
            @Override
            public void subscribe(FlowableEmitter<Socket> emitter) throws Exception {
                System.out.println("starting event stream");
                emitter.onNext(listenerSocket.getClientConnection());
                emitter.onNext(listenerSocket.getClientConnection());
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
                System.out.println("error emitted by event stream");
            }

            @Override
            public void onComplete() {
                System.out.println("event stream reports its completion");
            }
        };
    }

}
