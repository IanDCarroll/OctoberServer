package ServerShell;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.nio.channels.AsynchronousSocketChannel;

public class ReactiveSubscriber {
    private AsynchronousResponder responder;

    public ReactiveSubscriber(AsynchronousResponder responder) {
        this.responder = responder;
    }

    public Subscriber<AsynchronousSocketChannel> getAsynchronousResponder() {
        return new Subscriber<AsynchronousSocketChannel>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("subscriber has successfully subscribed to the listener");
            }

            @Override
            public void onNext(AsynchronousSocketChannel clientConnection) {
                //do all the read and write calls for the Asynchronous Socket
                System.out.println("Subscriber received a message");
                responder.respondTo(clientConnection);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.format("subscriber has been sent an error:\n\n%s\n", throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("subscriber was told the listener is done listening now, so it's going to stop too");
            }
        };
    }
}
