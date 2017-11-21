package serverShell;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.Executors;

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
    }
}
