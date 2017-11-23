package serverShell;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsynchronousListener {
    private final AsynchronousServerSocketChannel listener;
    private final AsynchronousChannelGroup threadpool = AsynchronousChannelGroup.withThreadPool(Executors.newWorkStealingPool());

    public AsynchronousListener(int portNumber) throws IOException {
        this.listener = AsynchronousServerSocketChannel.open(threadpool);
        this.listener.bind(new InetSocketAddress(portNumber));
    }

    public void listen(AsynchronousHandler handler) {
        Void noAttachment = null;
        listener.accept(noAttachment, handler);
        keepTheThreadAlive();
    }

    private void keepTheThreadAlive() {
        long thisMuchTime = Long.MAX_VALUE;
        TimeUnit byThisMeasure = TimeUnit.DAYS; // about 252 trillion years with long's max value
        try {
            threadpool.awaitTermination(thisMuchTime, byThisMeasure);
        } catch (InterruptedException e) { System.out.println("Thread interrupted."); }
    }
}
