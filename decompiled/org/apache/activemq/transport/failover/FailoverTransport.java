/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.failover;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.activemq.broker.SslContext;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.ConnectionControl;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.ConsumerControl;
import org.apache.activemq.command.MessageDispatch;
import org.apache.activemq.command.MessagePull;
import org.apache.activemq.command.RemoveInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.ConnectionStateTracker;
import org.apache.activemq.state.Tracked;
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
import org.apache.activemq.transport.failover.BackupTransport;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.ServiceSupport;
import org.apache.activemq.util.URISupport;
import org.apache.activemq.wireformat.WireFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FailoverTransport
implements CompositeTransport {
    private static final Logger LOG = LoggerFactory.getLogger(FailoverTransport.class);
    private static final int DEFAULT_INITIAL_RECONNECT_DELAY = 10;
    private static final int INFINITE = -1;
    private TransportListener transportListener;
    private volatile boolean disposed;
    private final CopyOnWriteArrayList<URI> uris = new CopyOnWriteArrayList();
    private final CopyOnWriteArrayList<URI> updated = new CopyOnWriteArrayList();
    private final Object reconnectMutex = new Object();
    private final Object backupMutex = new Object();
    private final Object sleepMutex = new Object();
    private final Object listenerMutex = new Object();
    private final ConnectionStateTracker stateTracker = new ConnectionStateTracker();
    private final Map<Integer, Command> requestMap = new LinkedHashMap<Integer, Command>();
    private URI connectedTransportURI;
    private URI failedConnectTransportURI;
    private final AtomicReference<Transport> connectedTransport = new AtomicReference();
    private final TaskRunnerFactory reconnectTaskFactory;
    private final TaskRunner reconnectTask;
    private volatile boolean started;
    private long initialReconnectDelay = 10L;
    private long maxReconnectDelay = 30000L;
    private double backOffMultiplier = 2.0;
    private long timeout = -1L;
    private boolean useExponentialBackOff = true;
    private boolean randomize = true;
    private int maxReconnectAttempts = -1;
    private int startupMaxReconnectAttempts = -1;
    private int connectFailures;
    private int warnAfterReconnectAttempts = 10;
    private long reconnectDelay = 10L;
    private Exception connectionFailure;
    private boolean firstConnection = true;
    private boolean backup = false;
    private final List<BackupTransport> backups = new CopyOnWriteArrayList<BackupTransport>();
    private int backupPoolSize = 1;
    private boolean trackMessages = false;
    private boolean trackTransactionProducers = true;
    private int maxCacheSize = 131072;
    private final TransportListener disposedListener = new DefaultTransportListener(){};
    private boolean updateURIsSupported = true;
    private boolean reconnectSupported = true;
    private SslContext brokerSslContext;
    private String updateURIsURL = null;
    private boolean rebalanceUpdateURIs = true;
    private boolean doRebalance = false;
    private boolean connectedToPriority = false;
    private boolean priorityBackup = false;
    private final ArrayList<URI> priorityList = new ArrayList();
    private boolean priorityBackupAvailable = false;
    private String nestedExtraQueryOptions;
    private volatile boolean shuttingDown = false;

    public FailoverTransport() {
        this.brokerSslContext = SslContext.getCurrentSslContext();
        this.stateTracker.setTrackTransactions(true);
        this.reconnectTaskFactory = new TaskRunnerFactory();
        this.reconnectTaskFactory.init();
        this.reconnectTask = this.reconnectTaskFactory.createTaskRunner(new Task(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public boolean iterate() {
                boolean result = false;
                if (!FailoverTransport.this.started) {
                    return result;
                }
                boolean buildBackup = true;
                Object object = FailoverTransport.this.backupMutex;
                synchronized (object) {
                    if ((FailoverTransport.this.connectedTransport.get() == null || FailoverTransport.this.doRebalance || FailoverTransport.this.priorityBackupAvailable) && !FailoverTransport.this.disposed) {
                        result = FailoverTransport.this.doReconnect();
                        buildBackup = false;
                    }
                }
                if (buildBackup) {
                    FailoverTransport.this.buildBackups();
                    if (FailoverTransport.this.priorityBackup && !FailoverTransport.this.connectedToPriority) {
                        try {
                            FailoverTransport.this.doDelay();
                            if (FailoverTransport.this.reconnectTask == null) {
                                return true;
                            }
                            FailoverTransport.this.reconnectTask.wakeup();
                        }
                        catch (InterruptedException e) {
                            LOG.debug("Reconnect task has been interrupted.", e);
                        }
                    }
                } else {
                    buildBackup = true;
                    try {
                        if (FailoverTransport.this.reconnectTask == null) {
                            return true;
                        }
                        FailoverTransport.this.reconnectTask.wakeup();
                    }
                    catch (InterruptedException e) {
                        LOG.debug("Reconnect task has been interrupted.", e);
                    }
                }
                return result;
            }
        }, "ActiveMQ Failover Worker: " + System.identityHashCode(this));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void processCommand(Object incoming) {
        ConsumerControl consumerControl;
        Command command = (Command)incoming;
        if (command == null) {
            return;
        }
        if (command.isResponse()) {
            Command object = null;
            Map<Integer, Command> map = this.requestMap;
            synchronized (map) {
                object = this.requestMap.remove(((Response)command).getCorrelationId());
            }
            if (object != null && object.getClass() == Tracked.class) {
                ((Tracked)object).onResponses(command);
            }
        }
        if (command.isConnectionControl()) {
            this.handleConnectionControl((ConnectionControl)command);
        } else if (command.isConsumerControl() && (consumerControl = (ConsumerControl)command).isClose()) {
            this.stateTracker.processRemoveConsumer(consumerControl.getConsumerId(), -2L);
        }
        if (this.transportListener != null) {
            this.transportListener.onCommand(command);
        }
    }

    private TransportListener createTransportListener(final Transport owner) {
        return new TransportListener(){

            @Override
            public void onCommand(Object o) {
                FailoverTransport.this.processCommand(o);
            }

            @Override
            public void onException(IOException error) {
                block2: {
                    try {
                        FailoverTransport.this.handleTransportFailure(owner, error);
                    }
                    catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        if (FailoverTransport.this.transportListener == null) break block2;
                        FailoverTransport.this.transportListener.onException(new InterruptedIOException());
                    }
                }
            }

            @Override
            public void transportInterupted() {
            }

            @Override
            public void transportResumed() {
            }
        };
    }

    public final void disposeTransport(Transport transport) {
        transport.setTransportListener(this.disposedListener);
        ServiceSupport.dispose(transport);
    }

    public final void handleTransportFailure(IOException e) throws InterruptedException {
        this.handleTransportFailure(this.getConnectedTransport(), e);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void handleTransportFailure(Transport failed, IOException e) throws InterruptedException {
        if (this.shuttingDown) {
            return;
        }
        if (LOG.isTraceEnabled()) {
            LOG.trace(this + " handleTransportFailure: " + e, e);
        }
        Transport transport = null;
        if (this.connectedTransport.compareAndSet(failed, null) && (transport = failed) != null) {
            this.disposeTransport(transport);
        }
        Object object = this.reconnectMutex;
        synchronized (object) {
            if (transport != null && this.connectedTransport.get() == null) {
                boolean reconnectOk = false;
                if (this.canReconnect()) {
                    reconnectOk = true;
                }
                LOG.warn("Transport ({}) failed {} attempting to automatically reconnect: {}", this.connectedTransportURI, reconnectOk ? "," : ", not", e);
                this.failedConnectTransportURI = this.connectedTransportURI;
                this.connectedTransportURI = null;
                this.connectedToPriority = false;
                if (reconnectOk) {
                    if (this.transportListener != null) {
                        this.transportListener.transportInterupted();
                    }
                    this.updated.remove(this.failedConnectTransportURI);
                    this.reconnectTask.wakeup();
                } else if (!this.isDisposed()) {
                    this.propagateFailureToExceptionListener(e);
                }
            }
        }
    }

    private boolean canReconnect() {
        return this.started && 0 != this.calculateReconnectAttemptLimit();
    }

    public final void handleConnectionControl(ConnectionControl control) {
        String reconnectStr = control.getReconnectTo();
        if (LOG.isTraceEnabled()) {
            LOG.trace("Received ConnectionControl: {}", (Object)control);
        }
        if (reconnectStr != null && (reconnectStr = reconnectStr.trim()).length() > 0) {
            try {
                URI uri = new URI(reconnectStr);
                if (this.isReconnectSupported()) {
                    this.reconnect(uri);
                    LOG.info("Reconnected to: " + uri);
                }
            }
            catch (Exception e) {
                LOG.error("Failed to handle ConnectionControl reconnect to " + reconnectStr, e);
            }
        }
        this.processNewTransports(control.isRebalanceConnection(), control.getConnectedBrokers());
    }

    private final void processNewTransports(boolean rebalance, String newTransports) {
        if (newTransports != null && (newTransports = newTransports.trim()).length() > 0 && this.isUpdateURIsSupported()) {
            ArrayList<URI> list = new ArrayList<URI>();
            StringTokenizer tokenizer = new StringTokenizer(newTransports, ",");
            while (tokenizer.hasMoreTokens()) {
                String str = tokenizer.nextToken();
                try {
                    URI uri = new URI(str);
                    list.add(uri);
                }
                catch (Exception e) {
                    LOG.error("Failed to parse broker address: " + str, e);
                }
            }
            if (!list.isEmpty()) {
                try {
                    this.updateURIs(rebalance, list.toArray(new URI[list.size()]));
                }
                catch (IOException e) {
                    LOG.error("Failed to update transport URI's from: " + newTransports, e);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void start() throws Exception {
        Object object = this.reconnectMutex;
        synchronized (object) {
            LOG.debug("Started {}", (Object)this);
            if (this.started) {
                return;
            }
            this.started = true;
            this.stateTracker.setMaxCacheSize(this.getMaxCacheSize());
            this.stateTracker.setTrackMessages(this.isTrackMessages());
            this.stateTracker.setTrackTransactionProducers(this.isTrackTransactionProducers());
            if (this.connectedTransport.get() != null) {
                this.stateTracker.restore(this.connectedTransport.get());
            } else {
                this.reconnect(false);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void stop() throws Exception {
        Iterator iterator2;
        Transport transportToStop = null;
        ArrayList<Transport> backupsToStop = new ArrayList<Transport>(this.backups.size());
        try {
            iterator2 = this.reconnectMutex;
            synchronized (iterator2) {
                block21: {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Stopped {}", (Object)this);
                    }
                    if (this.started) break block21;
                    return;
                }
                this.started = false;
                this.disposed = true;
                if (this.connectedTransport.get() != null) {
                    transportToStop = this.connectedTransport.getAndSet(null);
                }
                this.reconnectMutex.notifyAll();
            }
            iterator2 = this.sleepMutex;
            synchronized (iterator2) {
                this.sleepMutex.notifyAll();
            }
        }
        finally {
            this.reconnectTask.shutdown();
            this.reconnectTaskFactory.shutdownNow();
        }
        iterator2 = this.backupMutex;
        synchronized (iterator2) {
            for (BackupTransport backup : this.backups) {
                backup.setDisposed(true);
                Transport transport = backup.getTransport();
                if (transport == null) continue;
                transport.setTransportListener(this.disposedListener);
                backupsToStop.add(transport);
            }
            this.backups.clear();
        }
        for (Transport transport : backupsToStop) {
            try {
                LOG.trace("Stopped backup: {}", (Object)transport);
                this.disposeTransport(transport);
            }
            catch (Exception exception) {}
        }
        if (transportToStop != null) {
            transportToStop.stop();
        }
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

    public long getReconnectDelay() {
        return this.reconnectDelay;
    }

    public void setReconnectDelay(long reconnectDelay) {
        this.reconnectDelay = reconnectDelay;
    }

    public double getReconnectDelayExponent() {
        return this.backOffMultiplier;
    }

    public void setReconnectDelayExponent(double reconnectDelayExponent) {
        this.backOffMultiplier = reconnectDelayExponent;
    }

    public Transport getConnectedTransport() {
        return this.connectedTransport.get();
    }

    public URI getConnectedTransportURI() {
        return this.connectedTransportURI;
    }

    public int getMaxReconnectAttempts() {
        return this.maxReconnectAttempts;
    }

    public void setMaxReconnectAttempts(int maxReconnectAttempts) {
        this.maxReconnectAttempts = maxReconnectAttempts;
    }

    public int getStartupMaxReconnectAttempts() {
        return this.startupMaxReconnectAttempts;
    }

    public void setStartupMaxReconnectAttempts(int startupMaxReconnectAttempts) {
        this.startupMaxReconnectAttempts = startupMaxReconnectAttempts;
    }

    public long getTimeout() {
        return this.timeout;
    }

    public void setTimeout(long timeout2) {
        this.timeout = timeout2;
    }

    public boolean isRandomize() {
        return this.randomize;
    }

    public void setRandomize(boolean randomize) {
        this.randomize = randomize;
    }

    public boolean isBackup() {
        return this.backup;
    }

    public void setBackup(boolean backup) {
        this.backup = backup;
    }

    public int getBackupPoolSize() {
        return this.backupPoolSize;
    }

    public void setBackupPoolSize(int backupPoolSize) {
        this.backupPoolSize = backupPoolSize;
    }

    public int getCurrentBackups() {
        return this.backups.size();
    }

    public boolean isTrackMessages() {
        return this.trackMessages;
    }

    public void setTrackMessages(boolean trackMessages) {
        this.trackMessages = trackMessages;
    }

    public boolean isTrackTransactionProducers() {
        return this.trackTransactionProducers;
    }

    public void setTrackTransactionProducers(boolean trackTransactionProducers) {
        this.trackTransactionProducers = trackTransactionProducers;
    }

    public int getMaxCacheSize() {
        return this.maxCacheSize;
    }

    public void setMaxCacheSize(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }

    public boolean isPriorityBackup() {
        return this.priorityBackup;
    }

    public void setPriorityBackup(boolean priorityBackup) {
        this.priorityBackup = priorityBackup;
    }

    public void setPriorityURIs(String priorityURIs) {
        StringTokenizer tokenizer = new StringTokenizer(priorityURIs, ",");
        while (tokenizer.hasMoreTokens()) {
            String str = tokenizer.nextToken();
            try {
                URI uri = new URI(str);
                this.priorityList.add(uri);
            }
            catch (Exception e) {
                LOG.error("Failed to parse broker address: " + str, e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void oneway(Object o) throws IOException {
        Command command = (Command)o;
        Exception error = null;
        try {
            Object object = this.reconnectMutex;
            synchronized (object) {
                if (command != null && this.connectedTransport.get() == null) {
                    if (command.isShutdownInfo()) {
                        return;
                    }
                    if (command instanceof RemoveInfo || command.isMessageAck()) {
                        this.stateTracker.track(command);
                        if (command.isResponseRequired()) {
                            Response response = new Response();
                            response.setCorrelationId(command.getCommandId());
                            this.processCommand(response);
                        }
                        return;
                    }
                    if (command instanceof MessagePull) {
                        MessagePull pullRequest = (MessagePull)command;
                        if (pullRequest.getTimeout() != 0L) {
                            MessageDispatch dispatch = new MessageDispatch();
                            dispatch.setConsumerId(pullRequest.getConsumerId());
                            dispatch.setDestination(pullRequest.getDestination());
                            this.processCommand(dispatch);
                        }
                        return;
                    }
                }
                int i = 0;
                while (!this.disposed) {
                    try {
                        Transport transport = this.connectedTransport.get();
                        long start2 = System.currentTimeMillis();
                        boolean timedout = false;
                        while (transport == null && !this.disposed && this.connectionFailure == null && !Thread.currentThread().isInterrupted() && this.willReconnect()) {
                            LOG.trace("Waiting for transport to reconnect..: {}", (Object)command);
                            long end = System.currentTimeMillis();
                            if (command.isMessage() && this.timeout > 0L && end - start2 > this.timeout) {
                                timedout = true;
                                LOG.info("Failover timed out after {} ms", (Object)(end - start2));
                                break;
                            }
                            try {
                                this.reconnectMutex.wait(100L);
                            }
                            catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                LOG.debug("Interupted:", e);
                            }
                            transport = this.connectedTransport.get();
                        }
                        if (transport == null) {
                            error = this.disposed ? new IOException("Transport disposed.") : (this.connectionFailure != null ? this.connectionFailure : (timedout ? new IOException("Failover timeout of " + this.timeout + " ms reached.") : (!this.willReconnect() ? new IOException("Reconnect attempts of " + this.maxReconnectAttempts + " exceeded") : new IOException("Unexpected failure."))));
                            break;
                        }
                        Tracked tracked = null;
                        try {
                            tracked = this.stateTracker.track(command);
                        }
                        catch (IOException ioe) {
                            LOG.debug("Cannot track the command {} {}", (Object)command, (Object)ioe);
                        }
                        Map<Integer, Command> ioe = this.requestMap;
                        synchronized (ioe) {
                            if (tracked != null && tracked.isWaitingForResponse()) {
                                this.requestMap.put(command.getCommandId(), tracked);
                            } else if (tracked == null && command.isResponseRequired()) {
                                this.requestMap.put(command.getCommandId(), command);
                            }
                        }
                        try {
                            transport.oneway(command);
                            this.stateTracker.trackBack(command);
                            if (command.isShutdownInfo()) {
                                this.shuttingDown = true;
                            }
                        }
                        catch (IOException e) {
                            if (tracked == null && this.canReconnect()) {
                                if (command.isResponseRequired()) {
                                    this.requestMap.remove(command.getCommandId());
                                }
                                throw e;
                            }
                            LOG.debug("Send oneway attempt: {} failed for command: {}", (Object)i, (Object)command);
                            this.handleTransportFailure(e);
                        }
                        return;
                    }
                    catch (IOException e) {
                        LOG.debug("Send oneway attempt: {} failed for command: {}", (Object)i, (Object)command);
                        this.handleTransportFailure(e);
                        ++i;
                    }
                }
            }
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException();
        }
        if (!this.disposed && error != null) {
            if (error instanceof IOException) {
                throw (IOException)error;
            }
            throw IOExceptionSupport.create(error);
        }
    }

    private boolean willReconnect() {
        return this.firstConnection || 0 != this.calculateReconnectAttemptLimit();
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
    public void add(boolean rebalance, URI[] u) {
        boolean newURI = false;
        for (URI uri : u) {
            if (this.contains(uri)) continue;
            this.uris.add(uri);
            newURI = true;
        }
        if (newURI) {
            this.reconnect(rebalance);
        }
    }

    @Override
    public void remove(boolean rebalance, URI[] u) {
        for (URI uri : u) {
            this.uris.remove(uri);
        }
    }

    public void add(boolean rebalance, String u) {
        try {
            URI newURI = new URI(u);
            if (!this.contains(newURI)) {
                this.uris.add(newURI);
                this.reconnect(rebalance);
            }
        }
        catch (Exception e) {
            LOG.error("Failed to parse URI: {}", (Object)u);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void reconnect(boolean rebalance) {
        Object object = this.reconnectMutex;
        synchronized (object) {
            if (this.started) {
                if (rebalance) {
                    this.doRebalance = true;
                }
                LOG.debug("Waking up reconnect task");
                try {
                    this.reconnectTask.wakeup();
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                LOG.debug("Reconnect was triggered but transport is not started yet. Wait for start to connect the transport.");
            }
        }
    }

    private List<URI> getConnectList() {
        if (!this.updated.isEmpty()) {
            return this.updated;
        }
        ArrayList<URI> l = new ArrayList<URI>(this.uris);
        boolean removed = false;
        if (this.failedConnectTransportURI != null) {
            removed = l.remove(this.failedConnectTransportURI);
        }
        if (this.randomize) {
            for (int i = 0; i < l.size(); ++i) {
                int p = (int)(Math.random() * 100.0) % l.size();
                URI t2 = l.get(p);
                l.set(p, l.get(i));
                l.set(i, t2);
            }
        }
        if (removed) {
            l.add(this.failedConnectTransportURI);
        }
        LOG.debug("urlList connectionList:{}, from: {}", (Object)l, (Object)this.uris);
        return l;
    }

    @Override
    public TransportListener getTransportListener() {
        return this.transportListener;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setTransportListener(TransportListener commandListener) {
        Object object = this.listenerMutex;
        synchronized (object) {
            this.transportListener = commandListener;
            this.listenerMutex.notifyAll();
        }
    }

    @Override
    public <T> T narrow(Class<T> target) {
        if (target.isAssignableFrom(this.getClass())) {
            return target.cast(this);
        }
        Transport transport = this.connectedTransport.get();
        if (transport != null) {
            return transport.narrow(target);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void restoreTransport(Transport t2) throws Exception, IOException {
        t2.start();
        ConnectionControl cc = new ConnectionControl();
        cc.setFaultTolerant(true);
        t2.oneway(cc);
        this.stateTracker.restore(t2);
        LinkedHashMap<Integer, Command> tmpMap = null;
        Map<Integer, Command> map = this.requestMap;
        synchronized (map) {
            tmpMap = new LinkedHashMap<Integer, Command>(this.requestMap);
        }
        for (Command command : tmpMap.values()) {
            LOG.trace("restore requestMap, replay: {}", (Object)command);
            t2.oneway(command);
        }
    }

    public boolean isUseExponentialBackOff() {
        return this.useExponentialBackOff;
    }

    public void setUseExponentialBackOff(boolean useExponentialBackOff) {
        this.useExponentialBackOff = useExponentialBackOff;
    }

    public String toString() {
        return this.connectedTransportURI == null ? "unconnected" : this.connectedTransportURI.toString();
    }

    @Override
    public String getRemoteAddress() {
        Transport transport = this.connectedTransport.get();
        if (transport != null) {
            return transport.getRemoteAddress();
        }
        return null;
    }

    @Override
    public boolean isFaultTolerant() {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doUpdateURIsFromDisk() {
        String fileURL = this.getUpdateURIsURL();
        if (fileURL != null) {
            BufferedReader in = null;
            String newUris = null;
            StringBuffer buffer = new StringBuffer();
            try {
                String line;
                in = new BufferedReader(this.getURLStream(fileURL));
                while ((line = in.readLine()) != null) {
                    buffer.append(line);
                }
                newUris = buffer.toString();
            }
            catch (IOException ioe) {
                LOG.error("Failed to read updateURIsURL: {} {}", (Object)fileURL, (Object)ioe);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    }
                    catch (IOException iOException) {}
                }
            }
            this.processNewTransports(this.isRebalanceUpdateURIs(), newUris);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive exception aggregation
     */
    final boolean doReconnect() {
        Exception failure = null;
        Object object = this.reconnectMutex;
        synchronized (object) {
            Object transport;
            this.doUpdateURIsFromDisk();
            if (this.disposed || this.connectionFailure != null) {
                this.reconnectMutex.notifyAll();
            }
            if (this.connectedTransport.get() != null && !this.doRebalance && !this.priorityBackupAvailable || this.disposed || this.connectionFailure != null) {
                return false;
            }
            List<URI> connectList = this.getConnectList();
            if (connectList.isEmpty()) {
                failure = new IOException("No uris available to connect to.");
            } else {
                Object l22;
                if (this.doRebalance) {
                    if (this.connectedToPriority || this.compareURIs(connectList.get(0), this.connectedTransportURI)) {
                        this.doRebalance = false;
                        return false;
                    }
                    LOG.debug("Doing rebalance from: {} to {}", (Object)this.connectedTransportURI, (Object)connectList);
                    try {
                        transport = this.connectedTransport.getAndSet(null);
                        if (transport != null) {
                            this.disposeTransport((Transport)transport);
                        }
                    }
                    catch (Exception e) {
                        LOG.debug("Caught an exception stopping existing transport for rebalance", e);
                    }
                    this.doRebalance = false;
                }
                this.resetReconnectDelay();
                transport = null;
                URI uri = null;
                Object object2 = this.backupMutex;
                synchronized (object2) {
                    if ((this.priorityBackup || this.backup) && !this.backups.isEmpty()) {
                        l22 = new ArrayList<BackupTransport>(this.backups);
                        if (this.randomize) {
                            Collections.shuffle(l22);
                        }
                        BackupTransport bt = ((ArrayList)l22).remove(0);
                        this.backups.remove(bt);
                        transport = bt.getTransport();
                        uri = bt.getUri();
                        this.processCommand(bt.getBrokerInfo());
                        if (this.priorityBackup && this.priorityBackupAvailable) {
                            Transport old = this.connectedTransport.getAndSet(null);
                            if (old != null) {
                                this.disposeTransport(old);
                            }
                            this.priorityBackupAvailable = false;
                        }
                    }
                }
                if (transport == null && !this.firstConnection && this.connectFailures == 0 && this.initialReconnectDelay > 0L && !this.disposed) {
                    this.doDelay();
                }
                Iterator<URI> iter = connectList.iterator();
                while ((transport != null || iter.hasNext()) && this.connectedTransport.get() == null && !this.disposed) {
                    try {
                        SslContext.setCurrentSslContext(this.brokerSslContext);
                        if (transport == null) {
                            uri = this.addExtraQueryOptions(iter.next());
                            transport = TransportFactory.compositeConnect(uri);
                        }
                        LOG.debug("Attempting {}th connect to: {}", (Object)this.connectFailures, (Object)uri);
                        transport.setTransportListener(this.createTransportListener((Transport)transport));
                        transport.start();
                        if (this.started && !this.firstConnection) {
                            this.restoreTransport((Transport)transport);
                        }
                        LOG.debug("Connection established");
                        this.reconnectDelay = this.initialReconnectDelay;
                        this.connectedTransportURI = uri;
                        this.connectedTransport.set((Transport)transport);
                        this.connectedToPriority = this.isPriority(this.connectedTransportURI);
                        this.reconnectMutex.notifyAll();
                        this.connectFailures = 0;
                        l22 = this.listenerMutex;
                        synchronized (l22) {
                            if (this.transportListener == null) {
                                try {
                                    this.listenerMutex.wait(2000L);
                                }
                                catch (InterruptedException bt) {
                                    // empty catch block
                                }
                            }
                        }
                        if (this.transportListener != null) {
                            this.transportListener.transportResumed();
                        } else {
                            LOG.debug("transport resumed by transport listener not set");
                        }
                        if (this.firstConnection) {
                            this.firstConnection = false;
                            LOG.info("Successfully connected to {}", (Object)uri);
                        } else {
                            LOG.info("Successfully reconnected to {}", (Object)uri);
                        }
                        boolean l22 = false;
                        return l22;
                    }
                    catch (Exception e) {
                        failure = e;
                        LOG.debug("Connect fail to: {}, reason: {}", (Object)uri, (Object)e);
                        if (transport == null) continue;
                        try {
                            transport.stop();
                            transport = null;
                        }
                        catch (Exception ee) {
                            LOG.debug("Stop of failed transport: {} failed with reason: {}", transport, (Object)ee);
                        }
                    }
                    finally {
                        SslContext.setCurrentSslContext(null);
                    }
                }
            }
            int reconnectLimit = this.calculateReconnectAttemptLimit();
            ++this.connectFailures;
            if (reconnectLimit != -1 && this.connectFailures >= reconnectLimit) {
                LOG.error("Failed to connect to {} after: {} attempt(s)", (Object)this.uris, (Object)this.connectFailures);
                this.connectionFailure = failure;
                transport = this.listenerMutex;
                synchronized (transport) {
                    if (this.transportListener == null) {
                        try {
                            this.listenerMutex.wait(2000L);
                        }
                        catch (InterruptedException interruptedException) {
                            // empty catch block
                        }
                    }
                }
                this.propagateFailureToExceptionListener(this.connectionFailure);
                return false;
            }
            int warnInterval = this.getWarnAfterReconnectAttempts();
            if (warnInterval > 0 && this.connectFailures % warnInterval == 0) {
                LOG.warn("Failed to connect to {} after: {} attempt(s) continuing to retry.", (Object)this.uris, (Object)this.connectFailures);
            }
        }
        if (!this.disposed) {
            this.doDelay();
        }
        return !this.disposed;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doDelay() {
        if (this.reconnectDelay > 0L) {
            Object object = this.sleepMutex;
            synchronized (object) {
                LOG.debug("Waiting {} ms before attempting connection", (Object)this.reconnectDelay);
                try {
                    this.sleepMutex.wait(this.reconnectDelay);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        if (this.useExponentialBackOff) {
            this.reconnectDelay = (long)((double)this.reconnectDelay * this.backOffMultiplier);
            if (this.reconnectDelay > this.maxReconnectDelay) {
                this.reconnectDelay = this.maxReconnectDelay;
            }
        }
    }

    private void resetReconnectDelay() {
        if (!this.useExponentialBackOff || this.reconnectDelay == 10L) {
            this.reconnectDelay = this.initialReconnectDelay;
        }
    }

    private void propagateFailureToExceptionListener(Exception exception) {
        if (this.transportListener != null) {
            if (exception instanceof IOException) {
                this.transportListener.onException((IOException)exception);
            } else {
                this.transportListener.onException(IOExceptionSupport.create(exception));
            }
        }
        this.reconnectMutex.notifyAll();
    }

    private int calculateReconnectAttemptLimit() {
        int maxReconnectValue = this.maxReconnectAttempts;
        if (this.firstConnection && this.startupMaxReconnectAttempts != -1) {
            maxReconnectValue = this.startupMaxReconnectAttempts;
        }
        return maxReconnectValue;
    }

    private boolean shouldBuildBackups() {
        return this.backup && this.backups.size() < this.backupPoolSize || this.priorityBackup && !this.priorityBackupAvailable && !this.connectedToPriority;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final boolean buildBackups() {
        Object object = this.backupMutex;
        synchronized (object) {
            if (!this.disposed && this.shouldBuildBackups()) {
                ArrayList<URI> backupList = new ArrayList<URI>(this.priorityList);
                List<URI> connectList = this.getConnectList();
                for (URI uRI : connectList) {
                    if (backupList.contains(uRI)) continue;
                    backupList.add(uRI);
                }
                ArrayList<BackupTransport> disposedList = new ArrayList<BackupTransport>();
                for (BackupTransport bt : this.backups) {
                    if (!bt.isDisposed()) continue;
                    disposedList.add(bt);
                }
                this.backups.removeAll(disposedList);
                disposedList.clear();
                Iterator<URI> iterator2 = backupList.iterator();
                while (!this.disposed && iterator2.hasNext() && this.shouldBuildBackups()) {
                    URI uri = this.addExtraQueryOptions(iterator2.next());
                    if (this.connectedTransportURI == null || this.connectedTransportURI.equals(uri)) continue;
                    try {
                        SslContext.setCurrentSslContext(this.brokerSslContext);
                        BackupTransport bt = new BackupTransport(this);
                        bt.setUri(uri);
                        if (this.backups.contains(bt)) continue;
                        Transport t2 = TransportFactory.compositeConnect(uri);
                        t2.setTransportListener(bt);
                        t2.start();
                        bt.setTransport(t2);
                        if (this.priorityBackup && this.isPriority(uri)) {
                            this.priorityBackupAvailable = true;
                            this.backups.add(0, bt);
                            if (this.backups.size() <= this.backupPoolSize) continue;
                            BackupTransport disposeTransport = this.backups.remove(this.backups.size() - 1);
                            disposeTransport.setDisposed(true);
                            Transport transport = disposeTransport.getTransport();
                            if (transport == null) continue;
                            transport.setTransportListener(this.disposedListener);
                            this.disposeTransport(transport);
                            continue;
                        }
                        this.backups.add(bt);
                    }
                    catch (Exception e) {
                        LOG.debug("Failed to build backup ", e);
                    }
                    finally {
                        SslContext.setCurrentSslContext(null);
                    }
                }
            }
        }
        return false;
    }

    protected boolean isPriority(URI uri) {
        if (!this.priorityBackup) {
            return false;
        }
        if (!this.priorityList.isEmpty()) {
            for (URI priorityURI : this.priorityList) {
                if (!this.compareURIs(priorityURI, uri)) continue;
                return true;
            }
        } else if (!this.uris.isEmpty()) {
            return this.compareURIs(this.uris.get(0), uri);
        }
        return false;
    }

    @Override
    public boolean isDisposed() {
        return this.disposed;
    }

    @Override
    public boolean isConnected() {
        return this.connectedTransport.get() != null;
    }

    @Override
    public void reconnect(URI uri) throws IOException {
        this.add(true, new URI[]{uri});
    }

    @Override
    public boolean isReconnectSupported() {
        return this.reconnectSupported;
    }

    public void setReconnectSupported(boolean value) {
        this.reconnectSupported = value;
    }

    @Override
    public boolean isUpdateURIsSupported() {
        return this.updateURIsSupported;
    }

    public void setUpdateURIsSupported(boolean value) {
        this.updateURIsSupported = value;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void updateURIs(boolean rebalance, URI[] updatedURIs) throws IOException {
        if (this.isUpdateURIsSupported()) {
            HashSet<URI> copy = new HashSet<URI>();
            Object object = this.reconnectMutex;
            synchronized (object) {
                copy.addAll(this.updated);
                this.updated.clear();
                if (updatedURIs != null && updatedURIs.length > 0) {
                    for (URI uri : updatedURIs) {
                        if (uri == null || this.updated.contains(uri)) continue;
                        this.updated.add(uri);
                    }
                }
            }
            if (!(copy.isEmpty() && this.updated.isEmpty() || copy.equals(new HashSet<URI>(this.updated)))) {
                this.buildBackups();
                this.reconnect(rebalance);
            }
        }
    }

    public String getUpdateURIsURL() {
        return this.updateURIsURL;
    }

    public void setUpdateURIsURL(String updateURIsURL) {
        this.updateURIsURL = updateURIsURL;
    }

    public boolean isRebalanceUpdateURIs() {
        return this.rebalanceUpdateURIs;
    }

    public void setRebalanceUpdateURIs(boolean rebalanceUpdateURIs) {
        this.rebalanceUpdateURIs = rebalanceUpdateURIs;
    }

    @Override
    public int getReceiveCounter() {
        Transport transport = this.connectedTransport.get();
        if (transport == null) {
            return 0;
        }
        return transport.getReceiveCounter();
    }

    public int getConnectFailures() {
        return this.connectFailures;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void connectionInterruptProcessingComplete(ConnectionId connectionId) {
        Object object = this.reconnectMutex;
        synchronized (object) {
            this.stateTracker.connectionInterruptProcessingComplete(this, connectionId);
        }
    }

    public ConnectionStateTracker getStateTracker() {
        return this.stateTracker;
    }

    public boolean isConnectedToPriority() {
        return this.connectedToPriority;
    }

    private boolean contains(URI newURI) {
        boolean result = false;
        for (URI uri : this.uris) {
            if (!this.compareURIs(newURI, uri)) continue;
            result = true;
            break;
        }
        return result;
    }

    private boolean compareURIs(URI first, URI second) {
        boolean result;
        block7: {
            result = false;
            if (first == null || second == null) {
                return result;
            }
            if (first.getPort() == second.getPort()) {
                InetAddress firstAddr = null;
                InetAddress secondAddr = null;
                try {
                    firstAddr = InetAddress.getByName(first.getHost());
                    secondAddr = InetAddress.getByName(second.getHost());
                    if (firstAddr.equals(secondAddr)) {
                        result = true;
                    }
                }
                catch (IOException e) {
                    if (firstAddr == null) {
                        LOG.error("Failed to Lookup INetAddress for URI[{}] : {}", (Object)first, (Object)e);
                    } else {
                        LOG.error("Failed to Lookup INetAddress for URI[{}] : {}", (Object)second, (Object)e);
                    }
                    if (!first.getHost().equalsIgnoreCase(second.getHost())) break block7;
                    result = true;
                }
            }
        }
        return result;
    }

    private InputStreamReader getURLStream(String path) throws IOException {
        InputStreamReader result = null;
        URL url2 = null;
        try {
            url2 = new URL(path);
            result = new InputStreamReader(url2.openStream());
        }
        catch (MalformedURLException malformedURLException) {
            // empty catch block
        }
        if (result == null) {
            result = new FileReader(path);
        }
        return result;
    }

    private URI addExtraQueryOptions(URI uri) {
        try {
            if (this.nestedExtraQueryOptions != null && !this.nestedExtraQueryOptions.isEmpty()) {
                uri = uri.getQuery() == null ? URISupport.createURIWithQuery(uri, this.nestedExtraQueryOptions) : URISupport.createURIWithQuery(uri, uri.getQuery() + "&" + this.nestedExtraQueryOptions);
            }
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return uri;
    }

    public void setNestedExtraQueryOptions(String nestedExtraQueryOptions) {
        this.nestedExtraQueryOptions = nestedExtraQueryOptions;
    }

    public int getWarnAfterReconnectAttempts() {
        return this.warnAfterReconnectAttempts;
    }

    public void setWarnAfterReconnectAttempts(int warnAfterReconnectAttempts) {
        this.warnAfterReconnectAttempts = warnAfterReconnectAttempts;
    }

    @Override
    public X509Certificate[] getPeerCertificates() {
        Transport transport = this.connectedTransport.get();
        if (transport != null) {
            return transport.getPeerCertificates();
        }
        return null;
    }

    @Override
    public void setPeerCertificates(X509Certificate[] certificates) {
    }

    @Override
    public WireFormat getWireFormat() {
        Transport transport = this.connectedTransport.get();
        if (transport != null) {
            return transport.getWireFormat();
        }
        return null;
    }
}

