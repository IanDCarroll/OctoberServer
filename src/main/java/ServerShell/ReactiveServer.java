package ServerShell;

public class ReactiveServer implements ServerShell.Server {
    ReactiveFlowable flowable;
    ReactiveSubscriber subscriber;

    public ReactiveServer(ReactiveFlowable flowable, ReactiveSubscriber subscriber) {
        this.flowable = flowable;
        this.subscriber = subscriber;
    }

    public void start() {  flowable.getAsynchronousListener().subscribe(subscriber.getAsynchronousResponder()); }
}
