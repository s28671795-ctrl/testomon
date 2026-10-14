/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.fanout;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.ConnectionStateTracker;
import org.apache.activemq.thread.Task;
import org.apache.activemq.thread.TaskRunner;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.transport.CompositeTransport;
import org.apache.activemq.transport.DefaultTransportListener;
import org.apache.activemq.transport.FutureResponse;
import org.apache.activemq.transport.ResponseCallback;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFactory;
import org.apache.activemq.transport.TransportListener;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.ServiceStopper;
import org.apache.activemq.util.ServiceSupport;
import org.apache.activemq.wireformat.WireFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FanoutTransport
implements CompositeTransport {
    private static final Logger LOG = LoggerFactory.getLogger(FanoutTransport.class);
    private TransportListener transportListener;
    private boolean disposed;
    private boolean connected;
    private final Object reconnectMutex = new Object();
    private final ConnectionStateTracker stateTracker = new ConnectionStateTracker();
    private final ConcurrentMap<Integer, RequestCounter> requestMap = new ConcurrentHashMap<Integer, RequestCounter>();
    private final TaskRunnerFactory reconnectTaskFactory;
    private final TaskRunner reconnectTask;
    private boolean started;
    private final ArrayList<FanoutTransportHandler> transports = new ArrayList();
    private int connectedCount;
    private int minAckCount = 2;
    private long initialReconnectDelay = 10L;
    private long maxReconnectDelay = 30000L;
    private long backOffMultiplier = 2L;
    private final boolean useExponentialBackOff = true;
    private int maxReconnectAttempts;
    private Exception connectionFailure;
    private FanoutTransportHandler primary;
    private boolean fanOutQueues = false;

    public FanoutTransport() {
        this.reconnectTaskFactory = new TaskRunnerFactory();
        this.reconnectTaskFactory.init();
        this.reconnectTask = this.reconnectTaskFactory.createTaskRunner(new Task(){

            @Override
            public boolean iterate() {
                return FanoutTransport.this.doConnect();
            }
        }, "ActiveMQ Fanout Worker: " + System.identityHashCode(this));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean doConnect() {
        long closestReconnectDate = 0L;
        Object object = this.reconnectMutex;
        synchronized (object) {
            if (this.disposed || this.connectionFailure != null) {
                this.reconnectMutex.notifyAll();
            }
            if (this.transports.size() == this.connectedCount || this.disposed || this.connectionFailure != null) {
                return false;
            }
            if (!this.transports.isEmpty()) {
                Iterator<FanoutTransportHandler> iter = this.transports.iterator();
                while (iter.hasNext() && !this.disposed) {
                    long now = System.currentTimeMillis();
                    FanoutTransportHandler fanoutHandler = iter.next();
                    if (fanoutHandler.transport != null) continue;
                    if (fanoutHandler.reconnectDate != 0L && fanoutHandler.reconnectDate > now) {
                        if (closestReconnectDate != 0L && fanoutHandler.reconnectDate >= closestReconnectDate) continue;
                        closestReconnectDate = fanoutHandler.reconnectDate;
                        continue;
                    }
                    URI uri = fanoutHandler.uri;
                    try {
                        LOG.debug("Stopped: " + this);
                        LOG.debug("Attempting connect to: " + uri);
                        Transport t2 = TransportFactory.compositeConnect(uri);
                        fanoutHandler.transport = t2;
                        t2.setTransportListener(fanoutHandler);
                        if (this.started) {
                            this.restoreTransport(fanoutHandler);
                        }
                        LOG.debug("Connection established");
                        fanoutHandler.reconnectDelay = this.initialReconnectDelay;
                        fanoutHandler.connectFailures = 0;
                        if (this.primary == null) {
                            this.primary = fanoutHandler;
                        }
                        ++this.connectedCount;
                    }
                    catch (Exception e) {
                        LOG.debug("Connect fail to: " + uri + ", reason: " + e);
                        if (fanoutHandler.transport != null) {
                            ServiceSupport.dispose(fanoutHandler.transport);
                            fanoutHandler.transport = null;
                        }
                        if (this.maxReconnectAttempts > 0 && ++fanoutHandler.connectFailures >= this.maxReconnectAttempts) {
                            LOG.error("Failed to connect to transport after: " + fanoutHandler.connectFailures + " attempt(s)");
                            this.connectionFailure = e;
                            this.reconnectMutex.notifyAll();
                            return false;
                        }
                        FanoutTransportHandler fanoutTransportHandler = fanoutHandler;
                        fanoutTransportHandler.reconnectDelay = fanoutTransportHandler.reconnectDelay * this.backOffMultiplier;
                        if (fanoutHandler.reconnectDelay > this.maxReconnectDelay) {
                            fanoutHandler.reconnectDelay = this.maxReconnectDelay;
                        }
                        fanoutHandler.reconnectDate = now + fanoutHandler.reconnectDelay;
                        if (closestReconnectDate != 0L && fanoutHandler.reconnectDate >= closestReconnectDate) continue;
                        closestReconnectDate = fanoutHandler.reconnectDate;
                    }
                }
                if (this.transports.size() == this.connectedCount || this.disposed) {
                    this.reconnectMutex.notifyAll();
                    return false;
                }
            }
        }
        try {
            long reconnectDelay = closestReconnectDate - System.currentTimeMillis();
            if (reconnectDelay > 0L) {
                LOG.debug("Waiting " + reconnectDelay + " ms before attempting connection. ");
                Thread.sleep(reconnectDelay);
            }
        }
        catch (InterruptedException e1) {
            Thread.currentThread().interrupt();
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void start() throws Exception {
        Object object = this.reconnectMutex;
        synchronized (object) {
            LOG.debug("Started.");
            if (this.started) {
                return;
            }
            this.started = true;
            for (FanoutTransportHandler th : this.transports) {
                if (th.transport == null) continue;
                this.restoreTransport(th);
            }
            this.connected = true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void stop() throws Exception {
        try {
            Object object = this.reconnectMutex;
            synchronized (object) {
                ServiceStopper ss;
                block9: {
                    ss = new ServiceStopper();
                    if (this.started) break block9;
                    return;
                }
                this.started = false;
                this.disposed = true;
                this.connected = false;
                for (FanoutTransportHandler th : this.transports) {
                    if (th.transport == null) continue;
                    ss.stop(th.transport);
                }
                LOG.debug("Stopped: " + this);
                ss.throwFirstException();
            }
        }
        finally {
            this.reconnectTask.shutdown();
            this.reconnectTaskFactory.shutdownNow();
        }
    }

    public int getMinAckCount() {
        return this.minAckCount;
    }

    public void setMinAckCount(int minAckCount) {
        this.minAckCount = minAckCount;
    }

    public long getInitialReconnectDelay() {
        return this.initialReconnectDelay;
    }

    public void setInitialReconnectDelay(long initialReconnectDelay) {
        this.initialReconnectDelay = initialReconnectDelay;
    }

    public long getMaxReconnectDelay() {
        return this.maxReconnectDelay;
    }

    public void setMaxReconnectDelay(long maxReconnectDelay) {
        this.maxReconnectDelay = maxReconnectDelay;
    }

    public long getReconnectDelayExponent() {
        return this.backOffMultiplier;
    }

    public void setReconnectDelayExponent(long reconnectDelayExponent) {
        this.backOffMultiplier = reconnectDelayExponent;
    }

    public int getMaxReconnectAttempts() {
        return this.maxReconnectAttempts;
    }

    public void setMaxReconnectAttempts(int maxReconnectAttempts) {
        this.maxReconnectAttempts = maxReconnectAttempts;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void oneway(Object o) throws IOException {
        Command command = (Command)o;
        try {
            Object object = this.reconnectMutex;
            synchronized (object) {
                while (this.connectedCount < this.minAckCount && !this.disposed && this.connectionFailure == null) {
                    LOG.debug("Waiting for at least " + this.minAckCount + " transports to be connected.");
                    this.reconnectMutex.wait(1000L);
                }
                if (this.connectedCount < this.minAckCount) {
                    Exception error = this.disposed ? new IOException("Transport disposed.") : (this.connectionFailure != null ? this.connectionFailure : new IOException("Unexpected failure."));
                    if (error instanceof IOException) {
                        throw error;
                    }
                    throw IOExceptionSupport.create(error);
                }
                boolean fanout = this.isFanoutCommand(command);
                if (this.stateTracker.track(command) == null && command.isResponseRequired()) {
                    int size = fanout ? this.minAckCount : 1;
                    this.requestMap.put(new Integer(command.getCommandId()), new RequestCounter(command, size));
                }
                if (fanout) {
                    for (FanoutTransportHandler th : this.transports) {
                        if (th.transport == null) continue;
                        try {
                            th.transport.oneway(command);
                        }
                        catch (IOException e) {
                            LOG.debug("Send attempt: failed.");
                            th.onException(e);
                        }
                    }
                } else {
                    try {
                        this.primary.transport.oneway(command);
                    }
                    catch (IOException e) {
                        LOG.debug("Send attempt: failed.");
                        this.primary.onException(e);
                    }
                }
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException();
        }
    }

    private boolean isFanoutCommand(Command command) {
        if (command.isMessage()) {
            if (this.fanOutQueues) {
                return true;
            }
            return ((Message)command).getDestination().isTopic();
        }
        return command.getDataStructureType() != 5 && command.getDataStructureType() != 12;
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

    public void reconnect() {
        LOG.debug("Waking up reconnect task");
        try {
            this.reconnectTask.wakeup();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public TransportListener getTransportListener() {
        return this.transportListener;
    }

    @Override
    public void setTransportListener(TransportListener commandListener) {
        this.transportListener = commandListener;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> T narrow(Class<T> target) {
        if (target.isAssignableFrom(this.getClass())) {
            return target.cast(this);
        }
        Object object = this.reconnectMutex;
        synchronized (object) {
            for (FanoutTransportHandler th : this.transports) {
                T rc;
                if (th.transport == null || (rc = th.transport.narrow(target)) == null) continue;
                return rc;
            }
        }
        return null;
    }

    protected void restoreTransport(FanoutTransportHandler th) throws Exception, IOException {
        th.transport.start();
        this.stateTracker.setRestoreConsumers(th.transport == this.primary);
        this.stateTracker.restore(th.transport);
        for (RequestCounter rc : this.requestMap.values()) {
            th.transport.oneway(rc.command);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void add(boolean reblance, URI[] uris) {
        Object object = this.reconnectMutex;
        synchronized (object) {
            for (int i = 0; i < uris.length; ++i) {
                URI uri = uris[i];
                boolean match = false;
                for (FanoutTransportHandler th : this.transports) {
                    if (!th.uri.equals(uri)) continue;
                    match = true;
                    break;
                }
                if (match) continue;
                FanoutTransportHandler th = new FanoutTransportHandler(uri);
                this.transports.add(th);
                this.reconnect();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void remove(boolean rebalance, URI[] uris) {
        Object object = this.reconnectMutex;
        synchronized (object) {
            block3: for (int i = 0; i < uris.length; ++i) {
                URI uri = uris[i];
                Iterator<FanoutTransportHandler> iter = this.transports.iterator();
                while (iter.hasNext()) {
                    FanoutTransportHandler th = iter.next();
                    if (!th.uri.equals(uri)) continue;
                    if (th.transport != null) {
                        ServiceSupport.dispose(th.transport);
                        --this.connectedCount;
                    }
                    iter.remove();
                    continue block3;
                }
            }
        }
    }

    @Override
    public void reconnect(URI uri) throws IOException {
        this.add(true, new URI[]{uri});
    }

    @Override
    public boolean isReconnectSupported() {
        return true;
    }

    @Override
    public boolean isUpdateURIsSupported() {
        return true;
    }

    @Override
    public void updateURIs(boolean reblance, URI[] uris) throws IOException {
        this.add(reblance, uris);
    }

    @Override
    public String getRemoteAddress() {
        if (this.primary != null && this.primary.transport != null) {
            return this.primary.transport.getRemoteAddress();
        }
        return null;
    }

    protected void transportListenerOnCommand(Command command) {
        if (this.transportListener != null) {
            this.transportListener.onCommand(command);
        }
    }

    @Override
    public boolean isFaultTolerant() {
        return true;
    }

    public boolean isFanOutQueues() {
        return this.fanOutQueues;
    }

    public void setFanOutQueues(boolean fanOutQueues) {
        this.fanOutQueues = fanOutQueues;
    }

    @Override
    public boolean isDisposed() {
        return this.disposed;
    }

    @Override
    public boolean isConnected() {
        return this.connected;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getReceiveCounter() {
        int rc = 0;
        Object object = this.reconnectMutex;
        synchronized (object) {
            for (FanoutTransportHandler th : this.transports) {
                if (th.transport == null) continue;
                rc += th.transport.getReceiveCounter();
            }
        }
        return rc;
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

    class FanoutTransportHandler
    extends DefaultTransportListener {
        private final URI uri;
        private Transport transport;
        private int connectFailures;
        private long reconnectDelay;
        private long reconnectDate;

        public FanoutTransportHandler(URI uri) {
            this.reconnectDelay = FanoutTransport.this.initialReconnectDelay;
            this.uri = uri;
        }

        @Override
        public void onCommand(Object o) {
            Command command = (Command)o;
            if (command.isResponse()) {
                Integer id = new Integer(((Response)command).getCorrelationId());
                RequestCounter rc = (RequestCounter)FanoutTransport.this.requestMap.get(id);
                if (rc != null) {
                    if (rc.ackCount.decrementAndGet() <= 0) {
                        FanoutTransport.this.requestMap.remove(id);
                        FanoutTransport.this.transportListenerOnCommand(command);
                    }
                } else {
                    FanoutTransport.this.transportListenerOnCommand(command);
                }
            } else {
                FanoutTransport.this.transportListenerOnCommand(command);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onException(IOException error) {
            block7: {
                try {
                    Object object = FanoutTransport.this.reconnectMutex;
                    synchronized (object) {
                        if (this.transport == null || !this.transport.isConnected()) {
                            return;
                        }
                        LOG.debug("Transport failed, starting up reconnect task", error);
                        ServiceSupport.dispose(this.transport);
                        this.transport = null;
                        FanoutTransport.this.connectedCount--;
                        if (FanoutTransport.this.primary == this) {
                            FanoutTransport.this.primary = null;
                        }
                        FanoutTransport.this.reconnectTask.wakeup();
                    }
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    if (FanoutTransport.this.transportListener == null) break block7;
                    FanoutTransport.this.transportListener.onException(new InterruptedIOException());
                }
            }
        }
    }

    static class RequestCounter {
        final Command command;
        final AtomicInteger ackCount;

        RequestCounter(Command command, int count) {
            this.command = command;
            this.ackCount = new AtomicInteger(count);
        }

        public String toString() {
            return this.command.getCommandId() + "=" + this.ackCount.get();
        }
    }
}

