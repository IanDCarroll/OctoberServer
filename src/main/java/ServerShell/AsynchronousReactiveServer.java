package ServerShell;

public class AsynchronousReactiveServer implements ServerShell.Server {
    ReactiveFlowable flowable;
    ReactiveSubscriber subscriber;

    public AsynchronousReactiveServer(ReactiveFlowable flowable, ReactiveSubscriber subscriber) {
        this.flowable = flowable;
        this.subscriber = subscriber;
    }

    public void start() {  flowable.getAsynchronousListener().subscribe(subscriber.getAsynchronousResponder()); }
}
