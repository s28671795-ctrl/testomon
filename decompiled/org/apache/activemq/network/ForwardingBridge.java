/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.activemq.Service;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.ExceptionResponse;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageDispatch;
import org.apache.activemq.command.ProducerInfo;
import org.apache.activemq.command.Response;
import org.apache.activemq.command.SessionInfo;
import org.apache.activemq.command.ShutdownInfo;
import org.apache.activemq.network.NetworkBridgeListener;
import org.apache.activemq.transport.DefaultTransportListener;
import org.apache.activemq.transport.FutureResponse;
import org.apache.activemq.transport.ResponseCallback;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.util.IdGenerator;
import org.apache.activemq.util.ServiceStopper;
import org.apache.activemq.util.ServiceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForwardingBridge
implements Service {
    private static final IdGenerator ID_GENERATOR = new IdGenerator();
    private static final Logger LOG = LoggerFactory.getLogger(ForwardingBridge.class);
    final AtomicLong enqueueCounter = new AtomicLong();
    final AtomicLong dequeueCounter = new AtomicLong();
    ConnectionInfo connectionInfo;
    SessionInfo sessionInfo;
    ProducerInfo producerInfo;
    ConsumerInfo queueConsumerInfo;
    ConsumerInfo topicConsumerInfo;
    BrokerId localBrokerId;
    BrokerId remoteBrokerId;
    BrokerInfo localBrokerInfo;
    BrokerInfo remoteBrokerInfo;
    private final Transport localBroker;
    private final Transport remoteBroker;
    private String clientId;
    private int prefetchSize = 1000;
    private boolean dispatchAsync;
    private String destinationFilter = ">";
    private NetworkBridgeListener bridgeFailedListener;
    private boolean useCompression = false;

    public ForwardingBridge(Transport localBroker, Transport remoteBroker) {
        this.localBroker = localBroker;
        this.remoteBroker = remoteBroker;
    }

    @Override
    public void start() throws Exception {
        LOG.info("Starting a network connection between {} and {} has been established.", (Object)this.localBroker, (Object)this.remoteBroker);
        this.localBroker.setTransportListener(new DefaultTransportListener(){

            @Override
            public void onCommand(Object o) {
                Command command = (Command)o;
                ForwardingBridge.this.serviceLocalCommand(command);
            }

            @Override
            public void onException(IOException error) {
                ForwardingBridge.this.serviceLocalException(error);
            }
        });
        this.remoteBroker.setTransportListener(new DefaultTransportListener(){

            @Override
            public void onCommand(Object o) {
                Command command = (Command)o;
                ForwardingBridge.this.serviceRemoteCommand(command);
            }

            @Override
            public void onException(IOException error) {
                ForwardingBridge.this.serviceRemoteException(error);
            }
        });
        this.localBroker.start();
        this.remoteBroker.start();
    }

    protected void triggerStartBridge() throws IOException {
        Thread thead = new Thread(){

            @Override
            public void run() {
                try {
                    ForwardingBridge.this.startBridge();
                }
                catch (IOException e) {
                    LOG.error("Failed to start network bridge: ", e);
                }
            }
        };
        thead.start();
    }

    final void startBridge() throws IOException {
        this.connectionInfo = new ConnectionInfo();
        this.connectionInfo.setConnectionId(new ConnectionId(ID_GENERATOR.generateId()));
        this.connectionInfo.setClientId(this.clientId);
        this.localBroker.oneway(this.connectionInfo);
        this.remoteBroker.oneway(this.connectionInfo);
        this.sessionInfo = new SessionInfo(this.connectionInfo, 1L);
        this.localBroker.oneway(this.sessionInfo);
        this.remoteBroker.oneway(this.sessionInfo);
        this.queueConsumerInfo = new ConsumerInfo(this.sessionInfo, 1L);
        this.queueConsumerInfo.setDispatchAsync(this.dispatchAsync);
        this.queueConsumerInfo.setDestination(new ActiveMQQueue(this.destinationFilter));
        this.queueConsumerInfo.setPrefetchSize(this.prefetchSize);
        this.queueConsumerInfo.setPriority((byte)-5);
        this.localBroker.oneway(this.queueConsumerInfo);
        this.producerInfo = new ProducerInfo(this.sessionInfo, 1L);
        this.producerInfo.setResponseRequired(false);
        this.remoteBroker.oneway(this.producerInfo);
        if (this.connectionInfo.getClientId() != null) {
            this.topicConsumerInfo = new ConsumerInfo(this.sessionInfo, 2L);
            this.topicConsumerInfo.setDispatchAsync(this.dispatchAsync);
            this.topicConsumerInfo.setSubscriptionName("topic-bridge");
            this.topicConsumerInfo.setRetroactive(true);
            this.topicConsumerInfo.setDestination(new ActiveMQTopic(this.destinationFilter));
            this.topicConsumerInfo.setPrefetchSize(this.prefetchSize);
            this.topicConsumerInfo.setPriority((byte)-5);
            this.localBroker.oneway(this.topicConsumerInfo);
        }
        LOG.info("Network connection between {} and {} has been established.", (Object)this.localBroker, (Object)this.remoteBroker);
    }

    @Override
    public void stop() throws Exception {
        try {
            if (this.connectionInfo != null) {
                this.localBroker.request(this.connectionInfo.createRemoveCommand());
                this.remoteBroker.request(this.connectionInfo.createRemoveCommand());
            }
            this.localBroker.setTransportListener(null);
            this.remoteBroker.setTransportListener(null);
            this.localBroker.oneway(new ShutdownInfo());
            this.remoteBroker.oneway(new ShutdownInfo());
        }
        finally {
            ServiceStopper ss = new ServiceStopper();
            ss.stop(this.localBroker);
            ss.stop(this.remoteBroker);
            ss.throwFirstException();
        }
    }

    public void serviceRemoteException(Throwable error) {
        LOG.info("Unexpected remote exception: {}", (Object)error.getMessage());
        LOG.debug("Exception trace: ", error);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void serviceRemoteCommand(Command command) {
        block9: {
            try {
                if (command.isBrokerInfo()) {
                    ForwardingBridge forwardingBridge = this;
                    synchronized (forwardingBridge) {
                        this.remoteBrokerInfo = (BrokerInfo)command;
                        this.remoteBrokerId = this.remoteBrokerInfo.getBrokerId();
                        if (this.localBrokerId != null) {
                            if (this.localBrokerId.equals(this.remoteBrokerId)) {
                                LOG.info("Disconnecting loop back connection.");
                                ServiceSupport.dispose(this);
                            } else {
                                this.triggerStartBridge();
                            }
                        }
                        break block9;
                    }
                }
                LOG.warn("Unexpected remote command: {}", (Object)command);
            }
            catch (IOException e) {
                this.serviceLocalException(e);
            }
        }
    }

    public void serviceLocalException(Throwable error) {
        LOG.info("Unexpected local exception: {}", (Object)error.getMessage());
        LOG.debug("Exception trace: ", error);
        this.fireBridgeFailed();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void serviceLocalCommand(Command command) {
        block14: {
            try {
                if (command.isMessageDispatch()) {
                    this.enqueueCounter.incrementAndGet();
                    final MessageDispatch md = (MessageDispatch)command;
                    Message message = md.getMessage();
                    message.setProducerId(this.producerInfo.getProducerId());
                    if (message.getOriginalTransactionId() == null) {
                        message.setOriginalTransactionId(message.getTransactionId());
                    }
                    message.setTransactionId(null);
                    if (this.isUseCompression()) {
                        message.compress();
                    }
                    if (!message.isResponseRequired()) {
                        this.remoteBroker.oneway(message);
                        this.dequeueCounter.incrementAndGet();
                        this.localBroker.oneway(new MessageAck(md, 2, 1));
                    } else {
                        ResponseCallback callback = new ResponseCallback(){

                            @Override
                            public void onCompletion(FutureResponse future) {
                                try {
                                    Response response = future.getResult();
                                    if (response.isException()) {
                                        ExceptionResponse er = (ExceptionResponse)response;
                                        ForwardingBridge.this.serviceLocalException(er.getException());
                                    } else {
                                        ForwardingBridge.this.dequeueCounter.incrementAndGet();
                                        ForwardingBridge.this.localBroker.oneway(new MessageAck(md, 2, 1));
                                    }
                                }
                                catch (IOException e) {
                                    ForwardingBridge.this.serviceLocalException(e);
                                }
                            }
                        };
                        this.remoteBroker.asyncRequest(message, callback);
                    }
                    break block14;
                }
                if (command.isBrokerInfo()) {
                    ForwardingBridge md = this;
                    synchronized (md) {
                        this.localBrokerInfo = (BrokerInfo)command;
                        this.localBrokerId = this.localBrokerInfo.getBrokerId();
                        if (this.remoteBrokerId != null) {
                            if (this.remoteBrokerId.equals(this.localBrokerId)) {
                                LOG.info("Disconnecting loop back connection.");
                                ServiceSupport.dispose(this);
                            } else {
                                this.triggerStartBridge();
                            }
                        }
                        break block14;
                    }
                }
                LOG.debug("Unexpected local command: {}", (Object)command);
            }
            catch (IOException e) {
                this.serviceLocalException(e);
            }
        }
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getPrefetchSize() {
        return this.prefetchSize;
    }

    public void setPrefetchSize(int prefetchSize) {
        this.prefetchSize = prefetchSize;
    }

    public boolean isDispatchAsync() {
        return this.dispatchAsync;
    }

    public void setDispatchAsync(boolean dispatchAsync) {
        this.dispatchAsync = dispatchAsync;
    }

    public String getDestinationFilter() {
        return this.destinationFilter;
    }

    public void setDestinationFilter(String destinationFilter) {
        this.destinationFilter = destinationFilter;
    }

    public void setNetworkBridgeFailedListener(NetworkBridgeListener listener) {
        this.bridgeFailedListener = listener;
    }

    private void fireBridgeFailed() {
        NetworkBridgeListener l = this.bridgeFailedListener;
        if (l != null) {
            l.bridgeFailed();
        }
    }

    public String getRemoteAddress() {
        return this.remoteBroker.getRemoteAddress();
    }

    public String getLocalAddress() {
        return this.localBroker.getRemoteAddress();
    }

    public String getLocalBrokerName() {
        return this.localBrokerInfo == null ? null : this.localBrokerInfo.getBrokerName();
    }

    public String getRemoteBrokerName() {
        return this.remoteBrokerInfo == null ? null : this.remoteBrokerInfo.getBrokerName();
    }

    public long getDequeueCounter() {
        return this.dequeueCounter.get();
    }

    public long getEnqueueCounter() {
        return this.enqueueCounter.get();
    }

    public void setUseCompression(boolean useCompression) {
        this.useCompression = useCompression;
    }

    public boolean isUseCompression() {
        return this.useCompression;
    }
}

