package Mocks;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class MockSocketDealer {
    public static String result;

    private static final AsynchronousChannelProvider provider = new AsynchronousChannelProvider() {
        public AsynchronousChannelGroup openAsynchronousChannelGroup(int nThreads, ThreadFactory threadFactory) throws IOException { return null; }
        public AsynchronousChannelGroup openAsynchronousChannelGroup(ExecutorService executor, int initialSize) throws IOException { return null; }
        public AsynchronousServerSocketChannel openAsynchronousServerSocketChannel(AsynchronousChannelGroup group) throws IOException { return null; }
        public AsynchronousSocketChannel openAsynchronousSocketChannel(AsynchronousChannelGroup group) throws IOException { return null; }
    };

    public static AsynchronousSocketChannel socket = new AsynchronousSocketChannel(provider) {

        @Override
        public Future<Integer> write(ByteBuffer src) {
            result = new String(src.array());
            return null;
        }

        public AsynchronousSocketChannel bind(SocketAddress local) throws IOException { return null; }
        public <T> AsynchronousSocketChannel setOption(SocketOption<T> name, T value) throws IOException { return null; }
        public AsynchronousSocketChannel shutdownInput() throws IOException { return null; }
        public AsynchronousSocketChannel shutdownOutput() throws IOException { return null; }
        public SocketAddress getRemoteAddress() throws IOException { return null; }
        public <A> void connect(SocketAddress remote, A attachment, CompletionHandler<Void, ? super A> handler) {}
        public Future<Void> connect(SocketAddress remote) { return null; }
        public <A> void read(ByteBuffer dst, long timeout, TimeUnit unit, A attachment, CompletionHandler<Integer, ? super A> handler) {}
        public Future<Integer> read(ByteBuffer dst) { return null; }
        public <A> void read(ByteBuffer[] dsts, int offset, int length, long timeout, TimeUnit unit, A attachment, CompletionHandler<Long, ? super A> handler) {}
        public <A> void write(ByteBuffer src, long timeout, TimeUnit unit, A attachment, CompletionHandler<Integer, ? super A> handler) {}
        public <A> void write(ByteBuffer[] srcs, int offset, int length, long timeout, TimeUnit unit, A attachment, CompletionHandler<Long, ? super A> handler) {}
        public SocketAddress getLocalAddress() throws IOException { return null; }
        public void close() throws IOException {}
        public <T> T getOption(SocketOption<T> name) throws IOException { return null; }
        public Set<SocketOption<?>> supportedOptions() { return null; }
        public boolean isOpen() { return false; }
    };
}
