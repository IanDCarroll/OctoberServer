package ServerShell;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

import java.nio.channels.AsynchronousSocketChannel;

public class ReactiveFlowable {
    private AsynchronousListener listener;
    private AsynchronousHandler handler;

    public ReactiveFlowable(AsynchronousListener listener, AsynchronousHandler handler) {
        this.listener = listener;
        this.handler = handler;
    }

    public Flowable<AsynchronousSocketChannel> getAsynchronousListener() {
        return Flowable.create(new FlowableOnSubscribe<AsynchronousSocketChannel>() {
            @Override
            public void subscribe(FlowableEmitter<AsynchronousSocketChannel> emitter) throws Exception {
                handler.setEmitter(emitter);
                listener.listen(handler);
                listener.keepTheThreadAlive();
            }
        }, BackpressureStrategy.MISSING);
    };
}
