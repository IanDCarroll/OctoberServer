package ServerShell;

import Loggers.Logger;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.nio.channels.AsynchronousSocketChannel;

public class ReactiveSubscriber {
    private AsynchronousResponder responder;
    private Logger logger;

    public ReactiveSubscriber(AsynchronousResponder responder, Logger logger) {
        this.responder = responder;
        this.logger = logger;
    }

    public Subscriber<AsynchronousSocketChannel> getAsynchronousResponder() {
        return new Subscriber<AsynchronousSocketChannel>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                logger.log("subscriber has successfully subscribed to the listener");
            }

            @Override
            public void onNext(AsynchronousSocketChannel clientConnection) { responder.respondTo(clientConnection); }

            @Override
            public void onError(Throwable throwable) {
                logger.log("subscriber has been sent an error:\n%s" + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                logger.log("subscriber was told the listener is done listening now, so it's going to stop too");
            }
        };
    }
}
