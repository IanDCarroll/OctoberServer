package ServerShell;

import Loggers.Logger;
import io.reactivex.FlowableEmitter;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AsynchronousHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {
    private AsynchronousListener listener;
    private Logger logger;
    private FlowableEmitter<AsynchronousSocketChannel> emitter;

    public AsynchronousHandler(AsynchronousListener listener, Logger logger) {
        this.listener = listener;
        this.logger = logger;
    }

    public void setEmitter(FlowableEmitter<AsynchronousSocketChannel> emitter) {
        this.emitter = emitter;
    }

    @Override
    public void completed(AsynchronousSocketChannel clientConnection, Void attachment) {
        emitter.onNext(clientConnection);
        listener.listen(this);
    }

    @Override
    public void failed(Throwable error, Void attachment) {
        logger.log("Handler reports a failure");
    }
}
