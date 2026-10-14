/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.vm;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.activemq.command.ShutdownInfo;
import org.apache.activemq.thread.Task;
import org.apache.activemq.thread.TaskRunner;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.transport.FutureResponse;
import org.apache.activemq.transport.ResponseCallback;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportDisposedIOException;
import org.apache.activemq.transport.TransportListener;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.wireformat.WireFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VMTransport
implements Transport,
Task {
    protected static final Logger LOG = LoggerFactory.getLogger(VMTransport.class);
    private static final AtomicLong NEXT_ID = new AtomicLong(0L);
    protected VMTransport peer;
    protected TransportListener transportListener;
    protected boolean marshal;
    protected boolean async = true;
    protected int asyncQueueDepth = 2000;
    protected final URI location;
    protected final long id;
    private volatile LinkedBlockingQueue<Object> messageQueue;
    private volatile TaskRunnerFactory taskRunnerFactory;
    private volatile TaskRunner taskRunner;
    protected final AtomicBoolean started = new AtomicBoolean();
    protected final AtomicBoolean disposed = new AtomicBoolean();
    private volatile int receiveCounter;

    public VMTransport(URI location) {
        this.location = location;
        this.id = NEXT_ID.getAndIncrement();
    }

    public void setPeer(VMTransport peer) {
        this.peer = peer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void oneway(Object command) throws IOException {
        block12: {
            if (this.disposed.get()) {
                throw new TransportDisposedIOException("Transport disposed.");
            }
            if (this.peer == null) {
                throw new IOException("Peer not connected.");
            }
            try {
                if (this.peer.disposed.get()) {
                    throw new TransportDisposedIOException("Peer (" + this.peer.toString() + ") disposed.");
                }
                if (this.peer.async) {
                    this.peer.getMessageQueue().put(command);
                    this.peer.wakeup();
                    return;
                }
                if (this.peer.started.get()) break block12;
                LinkedBlockingQueue<Object> pending = this.peer.getMessageQueue();
                boolean accepted = false;
                do {
                    int sleepTimeMillis = 0;
                    AtomicBoolean atomicBoolean = this.peer.started;
                    synchronized (atomicBoolean) {
                        if (!this.peer.started.get() && !(accepted = pending.offer(command))) {
                            sleepTimeMillis = 500;
                        }
                    }
                    TimeUnit.MILLISECONDS.sleep(sleepTimeMillis);
                } while (!accepted && !this.peer.started.get());
                if (accepted) {
                    return;
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                InterruptedIOException iioe = new InterruptedIOException(e.getMessage());
                iioe.initCause(e);
                throw iioe;
            }
        }
        this.dispatch(this.peer, this.peer.messageQueue, command);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void dispatch(VMTransport transport, BlockingQueue<Object> pending, Object command) {
        TransportListener transportListener = transport.getTransportListener();
        if (transportListener != null) {
            AtomicBoolean atomicBoolean = transport.started;
            synchronized (atomicBoolean) {
                while (pending != null && !pending.isEmpty() && !transport.isDisposed()) {
                    this.doDispatch(transport, transportListener, pending.poll());
                }
                transport.messageQueue = null;
                if (command != null && !this.disposed.get() && !transport.isDisposed()) {
                    this.doDispatch(transport, transportListener, command);
                }
            }
        }
    }

    public void doDispatch(VMTransport transport, TransportListener transportListener, Object command) {
        ++transport.receiveCounter;
        transportListener.onCommand(command);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void start() throws Exception {
        if (this.transportListener == null) {
            throw new IOException("TransportListener not set.");
        }
        if (!this.async) {
            AtomicBoolean atomicBoolean = this.started;
            synchronized (atomicBoolean) {
                if (this.started.compareAndSet(false, true)) {
                    Object command;
                    LinkedBlockingQueue<Object> mq = this.getMessageQueue();
                    while ((command = mq.poll()) != null && !this.disposed.get()) {
                        ++this.receiveCounter;
                        this.doDispatch(this, this.transportListener, command);
                    }
                }
            }
        } else if (this.started.compareAndSet(false, true)) {
            this.wakeup();
        }
    }

    @Override
    public void stop() throws Exception {
        if (this.disposed.compareAndSet(false, true)) {
            TaskRunner tr = this.taskRunner;
            LinkedBlockingQueue<Object> mq = this.messageQueue;
            this.taskRunner = null;
            this.messageQueue = null;
            if (mq != null) {
                mq.clear();
            }
            if (tr != null) {
                try {
                    tr.shutdown(1L);
                }
                catch (Exception exception) {
                    // empty catch block
                }
                tr = null;
            }
            if (this.peer.transportListener != null) {
                try {
                    this.peer.transportListener.onCommand(new ShutdownInfo());
                }
                catch (Exception exception) {
                    // empty catch block
                }
                try {
                    this.peer.transportListener.onException(new TransportDisposedIOException("peer (" + this + ") stopped."));
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if (this.taskRunnerFactory != null) {
                this.taskRunnerFactory.shutdownNow();
                this.taskRunnerFactory = null;
            }
        }
    }

    protected void wakeup() {
        if (this.async && this.started.get()) {
            try {
                this.getTaskRunner().wakeup();
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            catch (TransportDisposedIOException transportDisposedIOException) {
                // empty catch block
            }
        }
    }

    @Override
    public boolean iterate() {
        LinkedBlockingQueue<Object> mq;
        TransportListener tl = this.transportListener;
        try {
            mq = this.getMessageQueue();
        }
        catch (TransportDisposedIOException e) {
            return false;
        }
        Object command = mq.poll();
        if (command != null && !this.disposed.get()) {
            try {
                tl.onCommand(command);
            }
            catch (Exception e) {
                try {
                    this.peer.transportListener.onException(IOExceptionSupport.create(e));
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            return !mq.isEmpty() && !this.disposed.get();
        }
        if (this.disposed.get()) {
            mq.clear();
        }
        return false;
    }

    @Override
    public void setTransportListener(TransportListener commandListener) {
        this.transportListener = commandListener;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public LinkedBlockingQueue<Object> getMessageQueue() throws TransportDisposedIOException {
        LinkedBlockingQueue<Object> result = this.messageQueue;
        if (result == null) {
            VMTransport vMTransport = this;
            synchronized (vMTransport) {
                result = this.messageQueue;
                if (result == null) {
                    if (this.disposed.get()) {
                        throw new TransportDisposedIOException("The Transport has been disposed");
                    }
                    this.messageQueue = result = new LinkedBlockingQueue(this.asyncQueueDepth);
                }
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected TaskRunner getTaskRunner() throws TransportDisposedIOException {
        TaskRunner result = this.taskRunner;
        if (result == null) {
            VMTransport vMTransport = this;
            synchronized (vMTransport) {
                result = this.taskRunner;
                if (result == null) {
                    if (this.disposed.get()) {
                        throw new TransportDisposedIOException("The Transport has been disposed");
                    }
                    String name = "ActiveMQ VMTransport: " + this.toString();
                    if (this.taskRunnerFactory == null) {
                        this.taskRunnerFactory = new TaskRunnerFactory(name);
                        this.taskRunnerFactory.init();
                    }
                    this.taskRunner = result = this.taskRunnerFactory.createTaskRunner(this, name);
                }
            }
        }
        return result;
    }

    @Override
    public FutureResponse asyncRequest(Object command, ResponseCallback responseCallback) throws IOException {
        throw new AssertionError((Object)"Unsupported Method");
    }

    @Override
    public Object request(Object command) throws IOException {
        throw new AssertionError((Object)"Unsupported Method");
    }

    @Override
    public Object request(Object command, int timeout2) throws IOException {
        throw new AssertionError((Object)"Unsupported Method");
    }

    @Override
    public TransportListener getTransportListener() {
        return this.transportListener;
    }

    @Override
    public <T> T narrow(Class<T> target) {
        if (target.isAssignableFrom(this.getClass())) {
            return target.cast(this);
        }
        return null;
    }

    public boolean isMarshal() {
        return this.marshal;
    }

    public void setMarshal(boolean marshal) {
        this.marshal = marshal;
    }

    public String toString() {
        return this.location + "#" + this.id;
    }

    @Override
    public String getRemoteAddress() {
        if (this.peer != null) {
            return this.peer.toString();
        }
        return null;
    }

    public boolean isAsync() {
        return this.async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public int getAsyncQueueDepth() {
        return this.asyncQueueDepth;
    }

    public void setAsyncQueueDepth(int asyncQueueDepth) {
        this.asyncQueueDepth = asyncQueueDepth;
    }

    @Override
    public boolean isFaultTolerant() {
        return false;
    }

    @Override
    public boolean isDisposed() {
        return this.disposed.get();
    }

    @Override
    public boolean isConnected() {
        return !this.disposed.get();
    }

    @Override
    public void reconnect(URI uri) throws IOException {
        throw new IOException("Transport reconnect is not supported");
    }

    @Override
    public boolean isReconnectSupported() {
        return false;
    }

    @Override
    public boolean isUpdateURIsSupported() {
        return false;
    }

    @Override
    public void updateURIs(boolean reblance, URI[] uris) throws IOException {
        throw new IOException("URI update feature not supported");
    }

    @Override
    public int getReceiveCounter() {
        return this.receiveCounter;
    }

    @Override
    public X509Certificate[] getPeerCertificates() {
        return null;
    }

    @Override
    public void setPeerCertificates(X509Certificate[] certificates) {
    }

    @Override
    public WireFormat getWireFormat() {
        return null;
    }
}

