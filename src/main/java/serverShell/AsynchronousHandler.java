package serverShell;

import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AsynchronousHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {
    private AsynchronousListener listener;
    private FlowableEmitter<AsynchronousSocketChannel> emitter;

    public AsynchronousHandler(AsynchronousListener listener) {
        this.listener = listener;
    }

    public void setEmitter(FlowableEmitter<AsynchronousSocketChannel> emitter) {
        this.emitter = emitter;
    }

    @Override
    public void completed(AsynchronousSocketChannel clientConnection, Void attachment) {
        listener.listen(this);
        emitter.onNext(clientConnection);
    }

    @Override
    public void failed(Throwable error, Void attachment) {}
}
