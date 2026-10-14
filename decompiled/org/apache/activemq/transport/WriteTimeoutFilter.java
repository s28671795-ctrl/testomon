/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFilter;
import org.apache.activemq.transport.tcp.TimeStampStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteTimeoutFilter
extends TransportFilter {
    private static final Logger LOG = LoggerFactory.getLogger(WriteTimeoutFilter.class);
    protected static ConcurrentLinkedQueue<WriteTimeoutFilter> writers = new ConcurrentLinkedQueue();
    protected static AtomicInteger messageCounter = new AtomicInteger(0);
    protected static TimeoutThread timeoutThread = new TimeoutThread();
    protected static long sleep = 5000L;
    protected long writeTimeout = -1L;

    public WriteTimeoutFilter(Transport next) {
        super(next);
    }

    @Override
    public void oneway(Object command) throws IOException {
        try {
            WriteTimeoutFilter.registerWrite(this);
            super.oneway(command);
        }
        catch (IOException x) {
            throw x;
        }
        finally {
            WriteTimeoutFilter.deRegisterWrite(this, false, null);
        }
    }

    public long getWriteTimeout() {
        return this.writeTimeout;
    }

    public void setWriteTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public static long getSleep() {
        return sleep;
    }

    public static void setSleep(long sleep) {
        WriteTimeoutFilter.sleep = sleep;
    }

    protected TimeStampStream getWriter() {
        return this.next.narrow(TimeStampStream.class);
    }

    protected Socket getSocket() {
        return this.next.narrow(Socket.class);
    }

    protected static void registerWrite(WriteTimeoutFilter filter2) {
        writers.add(filter2);
    }

    protected static boolean deRegisterWrite(WriteTimeoutFilter filter2, boolean fail, IOException iox) {
        boolean result = writers.remove(filter2);
        if (result && fail) {
            String message = "Forced write timeout for:" + filter2.getNext().getRemoteAddress();
            LOG.warn(message);
            Socket sock = filter2.getSocket();
            if (sock == null) {
                LOG.error("Destination socket is null, unable to close socket.(" + message + ")");
            } else {
                try {
                    sock.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
        return result;
    }

    @Override
    public void start() throws Exception {
        super.start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    protected static class TimeoutThread
    extends Thread {
        static AtomicInteger instance = new AtomicInteger(0);
        boolean run = true;

        public TimeoutThread() {
            this.setName("WriteTimeoutFilter-Timeout-" + instance.incrementAndGet());
            this.setDaemon(true);
            this.setPriority(1);
            this.start();
        }

        @Override
        public void run() {
            while (this.run) {
                boolean error = false;
                try {
                    if (!TimeoutThread.interrupted()) {
                        Iterator<WriteTimeoutFilter> filters = writers.iterator();
                        while (this.run && filters.hasNext()) {
                            WriteTimeoutFilter filter2 = filters.next();
                            if (filter2.getWriteTimeout() <= 0L) continue;
                            long writeStart = filter2.getWriter().getWriteTimestamp();
                            long delta = filter2.getWriter().isWriting() && writeStart > 0L ? System.currentTimeMillis() - writeStart : -1L;
                            if (delta <= filter2.getWriteTimeout()) continue;
                            WriteTimeoutFilter.deRegisterWrite(filter2, true, null);
                        }
                    }
                    try {
                        Thread.sleep(WriteTimeoutFilter.getSleep());
                        error = false;
                    }
                    catch (InterruptedException filters) {
                    }
                }
                catch (Throwable t2) {
                    if (error) continue;
                    LOG.error("WriteTimeout thread unable validate existing sockets.", t2);
                    boolean bl = true;
                }
            }
        }
    }
}

