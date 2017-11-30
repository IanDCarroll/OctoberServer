package ServerShell;

import Loggers.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsynchronousListener {
    private final AsynchronousServerSocketChannel listener;
    private final AsynchronousChannelGroup threadpool = AsynchronousChannelGroup.withThreadPool(Executors.newWorkStealingPool());
    private final Logger logger;

    public AsynchronousListener(int portNumber, Logger logger) throws IOException {
        this.listener = AsynchronousServerSocketChannel.open(threadpool);
        this.listener.bind(new InetSocketAddress(portNumber));
        this.logger = logger;
    }

    public void listen(CompletionHandler<AsynchronousSocketChannel, Void> handler) {
        Void noAttachment = null;
        listener.accept(noAttachment, handler);
    }

    public void keepTheThreadAlive() {
        long thisMuchTime = Long.MAX_VALUE;
        TimeUnit byThisMeasure = TimeUnit.DAYS; // about 252 trillion years with long's max value
        try {
            threadpool.awaitTermination(thisMuchTime, byThisMeasure);
        } catch (InterruptedException e) { logger.systemLog("Thread interrupted."); }
    }
}
