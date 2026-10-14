/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.RejectedExecutionHandler;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.ClientInternalExceptionListener;
import org.apache.activemq.ConfigurationException;
import org.apache.activemq.MessageTransformer;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.blob.BlobTransferPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.jndi.JNDIBaseStorable;
import org.apache.activemq.management.JMSStatsImpl;
import org.apache.activemq.management.StatsCapable;
import org.apache.activemq.management.StatsImpl;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFactory;
import org.apache.activemq.transport.TransportListener;
import org.apache.activemq.util.ClassLoadingAwareObjectInputStream;
import org.apache.activemq.util.IdGenerator;
import org.apache.activemq.util.IntrospectionSupport;
import org.apache.activemq.util.JMSExceptionSupport;
import org.apache.activemq.util.URISupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMQConnectionFactory
extends JNDIBaseStorable
implements ConnectionFactory,
QueueConnectionFactory,
TopicConnectionFactory,
StatsCapable,
Cloneable {
    private static final Logger LOG = LoggerFactory.getLogger(ActiveMQConnectionFactory.class);
    private static final String DEFAULT_BROKER_HOST;
    private static final int DEFAULT_BROKER_PORT;
    public static final String DEFAULT_BROKER_BIND_URL;
    public static final String DEFAULT_BROKER_URL;
    public static final String DEFAULT_USER;
    public static final String DEFAULT_PASSWORD;
    public static final int DEFAULT_PRODUCER_WINDOW_SIZE = 0;
    protected URI brokerURL;
    protected String userName;
    protected String password;
    protected String clientID;
    protected boolean dispatchAsync = true;
    protected boolean alwaysSessionAsync = true;
    JMSStatsImpl factoryStats = new JMSStatsImpl();
    private IdGenerator clientIdGenerator;
    private String clientIDPrefix;
    private IdGenerator connectionIdGenerator;
    private String connectionIDPrefix;
    private ActiveMQPrefetchPolicy prefetchPolicy = new ActiveMQPrefetchPolicy();
    private RedeliveryPolicyMap redeliveryPolicyMap = new RedeliveryPolicyMap();
    private BlobTransferPolicy blobTransferPolicy;
    private MessageTransformer transformer;
    private boolean disableTimeStampsByDefault;
    private boolean optimizedMessageDispatch;
    private long optimizeAcknowledgeTimeOut;
    private long optimizedAckScheduledAckInterval;
    private boolean copyMessageOnSend;
    private boolean useCompression;
    private boolean objectMessageSerializationDefered;
    private boolean useAsyncSend;
    private boolean optimizeAcknowledge;
    private int closeTimeout;
    private boolean useRetroactiveConsumer;
    private boolean exclusiveConsumer;
    private boolean nestedMapAndListEnabled;
    private boolean alwaysSyncSend;
    private boolean watchTopicAdvisories;
    private int producerWindowSize;
    private long warnAboutUnstartedConnectionTimeout;
    private int sendTimeout;
    private int connectResponseTimeout;
    private boolean sendAcksAsync;
    private TransportListener transportListener;
    private ExceptionListener exceptionListener;
    private int auditDepth;
    private int auditMaximumProducerNumber;
    private boolean useDedicatedTaskRunner;
    private long consumerFailoverRedeliveryWaitPeriod;
    private boolean checkForDuplicates;
    private ClientInternalExceptionListener clientInternalExceptionListener;
    private boolean messagePrioritySupported;
    private boolean transactedIndividualAck;
    private boolean nonBlockingRedelivery;
    private int maxThreadPoolSize;
    private TaskRunnerFactory sessionTaskRunner;
    private RejectedExecutionHandler rejectedTaskHandler;
    protected int xaAckMode;
    private boolean rmIdFromConnectionId;
    private boolean consumerExpiryCheckEnabled;
    private List<String> trustedPackages;
    private boolean trustAllPackages;

    public ActiveMQConnectionFactory() {
        this(DEFAULT_BROKER_URL);
    }

    public ActiveMQConnectionFactory(String brokerURL) {
        this(ActiveMQConnectionFactory.createURI(brokerURL));
    }

    public ActiveMQConnectionFactory(URI brokerURL) {
        this.redeliveryPolicyMap.setDefaultEntry(new RedeliveryPolicy());
        this.blobTransferPolicy = new BlobTransferPolicy();
        this.optimizedMessageDispatch = true;
        this.optimizeAcknowledgeTimeOut = 300L;
        this.optimizedAckScheduledAckInterval = 0L;
        this.copyMessageOnSend = true;
        this.closeTimeout = 15000;
        this.nestedMapAndListEnabled = true;
        this.watchTopicAdvisories = true;
        this.producerWindowSize = 0;
        this.warnAboutUnstartedConnectionTimeout = 500L;
        this.sendTimeout = 0;
        this.connectResponseTimeout = 0;
        this.sendAcksAsync = true;
        this.auditDepth = 2048;
        this.auditMaximumProducerNumber = 64;
        this.consumerFailoverRedeliveryWaitPeriod = 0L;
        this.checkForDuplicates = true;
        this.messagePrioritySupported = false;
        this.transactedIndividualAck = false;
        this.nonBlockingRedelivery = false;
        this.maxThreadPoolSize = ActiveMQConnection.DEFAULT_THREAD_POOL_SIZE;
        this.rejectedTaskHandler = null;
        this.xaAckMode = -1;
        this.rmIdFromConnectionId = false;
        this.consumerExpiryCheckEnabled = true;
        this.trustedPackages = Arrays.asList(ClassLoadingAwareObjectInputStream.serializablePackages);
        this.trustAllPackages = false;
        this.setBrokerURL(brokerURL.toString());
    }

    public ActiveMQConnectionFactory(String userName, String password, URI brokerURL) {
        this.redeliveryPolicyMap.setDefaultEntry(new RedeliveryPolicy());
        this.blobTransferPolicy = new BlobTransferPolicy();
        this.optimizedMessageDispatch = true;
        this.optimizeAcknowledgeTimeOut = 300L;
        this.optimizedAckScheduledAckInterval = 0L;
        this.copyMessageOnSend = true;
        this.closeTimeout = 15000;
        this.nestedMapAndListEnabled = true;
        this.watchTopicAdvisories = true;
        this.producerWindowSize = 0;
        this.warnAboutUnstartedConnectionTimeout = 500L;
        this.sendTimeout = 0;
        this.connectResponseTimeout = 0;
        this.sendAcksAsync = true;
        this.auditDepth = 2048;
        this.auditMaximumProducerNumber = 64;
        this.consumerFailoverRedeliveryWaitPeriod = 0L;
        this.checkForDuplicates = true;
        this.messagePrioritySupported = false;
        this.transactedIndividualAck = false;
        this.nonBlockingRedelivery = false;
        this.maxThreadPoolSize = ActiveMQConnection.DEFAULT_THREAD_POOL_SIZE;
        this.rejectedTaskHandler = null;
        this.xaAckMode = -1;
        this.rmIdFromConnectionId = false;
        this.consumerExpiryCheckEnabled = true;
        this.trustedPackages = Arrays.asList(ClassLoadingAwareObjectInputStream.serializablePackages);
        this.trustAllPackages = false;
        this.setUserName(userName);
        this.setPassword(password);
        this.setBrokerURL(brokerURL.toString());
    }

    public ActiveMQConnectionFactory(String userName, String password, String brokerURL) {
        this.redeliveryPolicyMap.setDefaultEntry(new RedeliveryPolicy());
        this.blobTransferPolicy = new BlobTransferPolicy();
        this.optimizedMessageDispatch = true;
        this.optimizeAcknowledgeTimeOut = 300L;
        this.optimizedAckScheduledAckInterval = 0L;
        this.copyMessageOnSend = true;
        this.closeTimeout = 15000;
        this.nestedMapAndListEnabled = true;
        this.watchTopicAdvisories = true;
        this.producerWindowSize = 0;
        this.warnAboutUnstartedConnectionTimeout = 500L;
        this.sendTimeout = 0;
        this.connectResponseTimeout = 0;
        this.sendAcksAsync = true;
        this.auditDepth = 2048;
        this.auditMaximumProducerNumber = 64;
        this.consumerFailoverRedeliveryWaitPeriod = 0L;
        this.checkForDuplicates = true;
        this.messagePrioritySupported = false;
        this.transactedIndividualAck = false;
        this.nonBlockingRedelivery = false;
        this.maxThreadPoolSize = ActiveMQConnection.DEFAULT_THREAD_POOL_SIZE;
        this.rejectedTaskHandler = null;
        this.xaAckMode = -1;
        this.rmIdFromConnectionId = false;
        this.consumerExpiryCheckEnabled = true;
        this.trustedPackages = Arrays.asList(ClassLoadingAwareObjectInputStream.serializablePackages);
        this.trustAllPackages = false;
        this.setUserName(userName);
        this.setPassword(password);
        this.setBrokerURL(brokerURL);
    }

    public ActiveMQConnectionFactory copy() {
        try {
            return (ActiveMQConnectionFactory)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("This should never happen: " + e, e);
        }
    }

    private static URI createURI(String brokerURL) {
        try {
            return new URI(brokerURL);
        }
        catch (URISyntaxException e) {
            throw (IllegalArgumentException)new IllegalArgumentException("Invalid broker URI: " + brokerURL).initCause(e);
        }
    }

    @Override
    public Connection createConnection() throws JMSException {
        return this.createActiveMQConnection();
    }

    @Override
    public Connection createConnection(String userName, String password) throws JMSException {
        return this.createActiveMQConnection(userName, password);
    }

    @Override
    public QueueConnection createQueueConnection() throws JMSException {
        return this.createActiveMQConnection().enforceQueueOnlyConnection();
    }

    @Override
    public QueueConnection createQueueConnection(String userName, String password) throws JMSException {
        return this.createActiveMQConnection(userName, password).enforceQueueOnlyConnection();
    }

    @Override
    public TopicConnection createTopicConnection() throws JMSException {
        return this.createActiveMQConnection();
    }

    @Override
    public TopicConnection createTopicConnection(String userName, String password) throws JMSException {
        return this.createActiveMQConnection(userName, password);
    }

    @Override
    public StatsImpl getStats() {
        return this.factoryStats;
    }

    protected ActiveMQConnection createActiveMQConnection() throws JMSException {
        return this.createActiveMQConnection(this.userName, this.password);
    }

    protected Transport createTransport() throws JMSException {
        try {
            URI connectBrokerUL = this.brokerURL;
            String scheme2 = this.brokerURL.getScheme();
            if (scheme2 == null) {
                throw new IOException("Transport not scheme specified: [" + this.brokerURL + "]");
            }
            if (scheme2.equals("auto")) {
                connectBrokerUL = new URI(this.brokerURL.toString().replace("auto", "tcp"));
            } else if (scheme2.equals("auto+ssl")) {
                connectBrokerUL = new URI(this.brokerURL.toString().replace("auto+ssl", "ssl"));
            } else if (scheme2.equals("auto+nio")) {
                connectBrokerUL = new URI(this.brokerURL.toString().replace("auto+nio", "nio"));
            } else if (scheme2.equals("auto+nio+ssl")) {
                connectBrokerUL = new URI(this.brokerURL.toString().replace("auto+nio+ssl", "nio+ssl"));
            }
            return TransportFactory.connect(connectBrokerUL);
        }
        catch (Exception e) {
            throw JMSExceptionSupport.create("Could not create Transport. Reason: " + e, e);
        }
    }

    protected ActiveMQConnection createActiveMQConnection(String userName, String password) throws JMSException {
        if (this.brokerURL == null) {
            throw new ConfigurationException("brokerURL not set.");
        }
        ActiveMQConnection connection = null;
        try {
            Transport transport = this.createTransport();
            connection = this.createActiveMQConnection(transport, this.factoryStats);
            connection.setUserName(userName);
            connection.setPassword(password);
            this.configureConnection(connection);
            transport.start();
            if (this.clientID != null) {
                connection.setDefaultClientID(this.clientID);
            }
            return connection;
        }
        catch (JMSException e) {
            try {
                connection.close();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            throw e;
        }
        catch (Exception e) {
            try {
                connection.close();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            throw JMSExceptionSupport.create("Could not connect to broker URL: " + this.brokerURL + ". Reason: " + e, e);
        }
    }

    protected ActiveMQConnection createActiveMQConnection(Transport transport, JMSStatsImpl stats) throws Exception {
        ActiveMQConnection connection = new ActiveMQConnection(transport, this.getClientIdGenerator(), this.getConnectionIdGenerator(), stats);
        return connection;
    }

    protected void configureConnection(ActiveMQConnection connection) throws JMSException {
        connection.setPrefetchPolicy(this.getPrefetchPolicy());
        connection.setDisableTimeStampsByDefault(this.isDisableTimeStampsByDefault());
        connection.setOptimizedMessageDispatch(this.isOptimizedMessageDispatch());
        connection.setCopyMessageOnSend(this.isCopyMessageOnSend());
        connection.setUseCompression(this.isUseCompression());
        connection.setObjectMessageSerializationDefered(this.isObjectMessageSerializationDefered());
        connection.setDispatchAsync(this.isDispatchAsync());
        connection.setUseAsyncSend(this.isUseAsyncSend());
        connection.setAlwaysSyncSend(this.isAlwaysSyncSend());
        connection.setAlwaysSessionAsync(this.isAlwaysSessionAsync());
        connection.setOptimizeAcknowledge(this.isOptimizeAcknowledge());
        connection.setOptimizeAcknowledgeTimeOut(this.getOptimizeAcknowledgeTimeOut());
        connection.setOptimizedAckScheduledAckInterval(this.getOptimizedAckScheduledAckInterval());
        connection.setUseRetroactiveConsumer(this.isUseRetroactiveConsumer());
        connection.setExclusiveConsumer(this.isExclusiveConsumer());
        connection.setRedeliveryPolicyMap(this.getRedeliveryPolicyMap());
        connection.setTransformer(this.getTransformer());
        connection.setBlobTransferPolicy(this.getBlobTransferPolicy().copy());
        connection.setWatchTopicAdvisories(this.isWatchTopicAdvisories());
        connection.setProducerWindowSize(this.getProducerWindowSize());
        connection.setWarnAboutUnstartedConnectionTimeout(this.getWarnAboutUnstartedConnectionTimeout());
        connection.setSendTimeout(this.getSendTimeout());
        connection.setCloseTimeout(this.getCloseTimeout());
        connection.setSendAcksAsync(this.isSendAcksAsync());
        connection.setAuditDepth(this.getAuditDepth());
        connection.setAuditMaximumProducerNumber(this.getAuditMaximumProducerNumber());
        connection.setUseDedicatedTaskRunner(this.isUseDedicatedTaskRunner());
        connection.setConsumerFailoverRedeliveryWaitPeriod(this.getConsumerFailoverRedeliveryWaitPeriod());
        connection.setCheckForDuplicates(this.isCheckForDuplicates());
        connection.setMessagePrioritySupported(this.isMessagePrioritySupported());
        connection.setTransactedIndividualAck(this.isTransactedIndividualAck());
        connection.setNonBlockingRedelivery(this.isNonBlockingRedelivery());
        connection.setMaxThreadPoolSize(this.getMaxThreadPoolSize());
        connection.setSessionTaskRunner(this.getSessionTaskRunner());
        connection.setRejectedTaskHandler(this.getRejectedTaskHandler());
        connection.setNestedMapAndListEnabled(this.isNestedMapAndListEnabled());
        connection.setRmIdFromConnectionId(this.isRmIdFromConnectionId());
        connection.setConsumerExpiryCheckEnabled(this.isConsumerExpiryCheckEnabled());
        connection.setTrustedPackages(this.getTrustedPackages());
        connection.setTrustAllPackages(this.isTrustAllPackages());
        connection.setConnectResponseTimeout(this.getConnectResponseTimeout());
        if (this.transportListener != null) {
            connection.addTransportListener(this.transportListener);
        }
        if (this.exceptionListener != null) {
            connection.setExceptionListener(this.exceptionListener);
        }
        if (this.clientInternalExceptionListener != null) {
            connection.setClientInternalExceptionListener(this.clientInternalExceptionListener);
        }
    }

    public String getBrokerURL() {
        return this.brokerURL == null ? null : this.brokerURL.toString();
    }

    public void setBrokerURL(String brokerURL) {
        this.brokerURL = ActiveMQConnectionFactory.createURI(brokerURL);
        if (this.brokerURL.getQuery() != null) {
            try {
                Map<String, String> map = URISupport.parseQuery(this.brokerURL.getQuery());
                Map<String, Object> jmsOptionsMap = IntrospectionSupport.extractProperties(map, "jms.");
                if (this.buildFromMap(jmsOptionsMap)) {
                    if (!jmsOptionsMap.isEmpty()) {
                        String msg = "There are " + jmsOptionsMap.size() + " jms options that couldn't be set on the ConnectionFactory. Check the options are spelled correctly. Unknown parameters=[" + jmsOptionsMap + "]. This connection factory cannot be started.";
                        throw new IllegalArgumentException(msg);
                    }
                    this.brokerURL = URISupport.createRemainingURI(this.brokerURL, map);
                }
            }
            catch (URISyntaxException map) {}
        } else {
            try {
                URISupport.CompositeData data = URISupport.parseComposite(this.brokerURL);
                Map<String, Object> jmsOptionsMap = IntrospectionSupport.extractProperties(data.getParameters(), "jms.");
                if (this.buildFromMap(jmsOptionsMap)) {
                    if (!jmsOptionsMap.isEmpty()) {
                        String msg = "There are " + jmsOptionsMap.size() + " jms options that couldn't be set on the ConnectionFactory. Check the options are spelled correctly. Unknown parameters=[" + jmsOptionsMap + "]. This connection factory cannot be started.";
                        throw new IllegalArgumentException(msg);
                    }
                    this.brokerURL = data.toURI();
                }
            }
            catch (URISyntaxException uRISyntaxException) {
                // empty catch block
            }
        }
    }

    public String getClientID() {
        return this.clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public boolean isCopyMessageOnSend() {
        return this.copyMessageOnSend;
    }

    public void setCopyMessageOnSend(boolean copyMessageOnSend) {
        this.copyMessageOnSend = copyMessageOnSend;
    }

    public boolean isDisableTimeStampsByDefault() {
        return this.disableTimeStampsByDefault;
    }

    public void setDisableTimeStampsByDefault(boolean disableTimeStampsByDefault) {
        this.disableTimeStampsByDefault = disableTimeStampsByDefault;
    }

    public boolean isOptimizedMessageDispatch() {
        return this.optimizedMessageDispatch;
    }

    public void setOptimizedMessageDispatch(boolean optimizedMessageDispatch) {
        this.optimizedMessageDispatch = optimizedMessageDispatch;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ActiveMQPrefetchPolicy getPrefetchPolicy() {
        return this.prefetchPolicy;
    }

    public void setPrefetchPolicy(ActiveMQPrefetchPolicy prefetchPolicy) {
        this.prefetchPolicy = prefetchPolicy;
    }

    public boolean isUseAsyncSend() {
        return this.useAsyncSend;
    }

    public BlobTransferPolicy getBlobTransferPolicy() {
        return this.blobTransferPolicy;
    }

    public void setBlobTransferPolicy(BlobTransferPolicy blobTransferPolicy) {
        this.blobTransferPolicy = blobTransferPolicy;
    }

    public void setUseAsyncSend(boolean useAsyncSend) {
        this.useAsyncSend = useAsyncSend;
    }

    public synchronized boolean isWatchTopicAdvisories() {
        return this.watchTopicAdvisories;
    }

    public synchronized void setWatchTopicAdvisories(boolean watchTopicAdvisories) {
        this.watchTopicAdvisories = watchTopicAdvisories;
    }

    public boolean isAlwaysSyncSend() {
        return this.alwaysSyncSend;
    }

    public void setAlwaysSyncSend(boolean alwaysSyncSend) {
        this.alwaysSyncSend = alwaysSyncSend;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isUseRetroactiveConsumer() {
        return this.useRetroactiveConsumer;
    }

    public void setUseRetroactiveConsumer(boolean useRetroactiveConsumer) {
        this.useRetroactiveConsumer = useRetroactiveConsumer;
    }

    public boolean isExclusiveConsumer() {
        return this.exclusiveConsumer;
    }

    public void setExclusiveConsumer(boolean exclusiveConsumer) {
        this.exclusiveConsumer = exclusiveConsumer;
    }

    public RedeliveryPolicy getRedeliveryPolicy() {
        return this.redeliveryPolicyMap.getDefaultEntry();
    }

    public void setRedeliveryPolicy(RedeliveryPolicy redeliveryPolicy) {
        this.redeliveryPolicyMap.setDefaultEntry(redeliveryPolicy);
    }

    public RedeliveryPolicyMap getRedeliveryPolicyMap() {
        return this.redeliveryPolicyMap;
    }

    public void setRedeliveryPolicyMap(RedeliveryPolicyMap redeliveryPolicyMap) {
        this.redeliveryPolicyMap = redeliveryPolicyMap;
    }

    public MessageTransformer getTransformer() {
        return this.transformer;
    }

    public int getSendTimeout() {
        return this.sendTimeout;
    }

    public void setSendTimeout(int sendTimeout) {
        this.sendTimeout = sendTimeout;
    }

    public boolean isSendAcksAsync() {
        return this.sendAcksAsync;
    }

    public void setSendAcksAsync(boolean sendAcksAsync) {
        this.sendAcksAsync = sendAcksAsync;
    }

    public boolean isMessagePrioritySupported() {
        return this.messagePrioritySupported;
    }

    public void setMessagePrioritySupported(boolean messagePrioritySupported) {
        this.messagePrioritySupported = messagePrioritySupported;
    }

    public void setTransformer(MessageTransformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public void buildFromProperties(Properties properties) {
        String temp;
        if (properties == null) {
            properties = new Properties();
        }
        if ((temp = properties.getProperty("java.naming.provider.url")) == null || temp.length() == 0) {
            temp = properties.getProperty("brokerURL");
        }
        if (temp != null && temp.length() > 0) {
            this.setBrokerURL(temp);
        }
        HashMap<Object, Object> p = new HashMap<Object, Object>(properties);
        this.buildFromMap(p);
    }

    public boolean buildFromMap(Map<String, Object> properties) {
        BlobTransferPolicy blobTransferPolicy;
        RedeliveryPolicy rp;
        boolean rc = false;
        ActiveMQPrefetchPolicy p = new ActiveMQPrefetchPolicy();
        if (IntrospectionSupport.setProperties((Object)p, properties, "prefetchPolicy.")) {
            this.setPrefetchPolicy(p);
            rc = true;
        }
        if (IntrospectionSupport.setProperties((Object)(rp = new RedeliveryPolicy()), properties, "redeliveryPolicy.")) {
            this.setRedeliveryPolicy(rp);
            rc = true;
        }
        if (IntrospectionSupport.setProperties((Object)(blobTransferPolicy = new BlobTransferPolicy()), properties, "blobTransferPolicy.")) {
            this.setBlobTransferPolicy(blobTransferPolicy);
            rc = true;
        }
        return rc |= IntrospectionSupport.setProperties(this, properties);
    }

    @Override
    public void populateProperties(Properties props) {
        props.setProperty("dispatchAsync", Boolean.toString(this.isDispatchAsync()));
        if (this.getBrokerURL() != null) {
            props.setProperty("java.naming.provider.url", this.getBrokerURL());
            props.setProperty("brokerURL", this.getBrokerURL());
        }
        if (this.getClientID() != null) {
            props.setProperty("clientID", this.getClientID());
        }
        IntrospectionSupport.getProperties(this.getPrefetchPolicy(), props, "prefetchPolicy.");
        IntrospectionSupport.getProperties(this.getRedeliveryPolicy(), props, "redeliveryPolicy.");
        IntrospectionSupport.getProperties(this.getBlobTransferPolicy(), props, "blobTransferPolicy.");
        props.setProperty("copyMessageOnSend", Boolean.toString(this.isCopyMessageOnSend()));
        props.setProperty("disableTimeStampsByDefault", Boolean.toString(this.isDisableTimeStampsByDefault()));
        props.setProperty("objectMessageSerializationDefered", Boolean.toString(this.isObjectMessageSerializationDefered()));
        props.setProperty("optimizedMessageDispatch", Boolean.toString(this.isOptimizedMessageDispatch()));
        if (this.getPassword() != null) {
            props.setProperty("password", this.getPassword());
        }
        props.setProperty("useAsyncSend", Boolean.toString(this.isUseAsyncSend()));
        props.setProperty("useCompression", Boolean.toString(this.isUseCompression()));
        props.setProperty("useRetroactiveConsumer", Boolean.toString(this.isUseRetroactiveConsumer()));
        props.setProperty("watchTopicAdvisories", Boolean.toString(this.isWatchTopicAdvisories()));
        if (this.getUserName() != null) {
            props.setProperty("userName", this.getUserName());
        }
        props.setProperty("closeTimeout", Integer.toString(this.getCloseTimeout()));
        props.setProperty("alwaysSessionAsync", Boolean.toString(this.isAlwaysSessionAsync()));
        props.setProperty("optimizeAcknowledge", Boolean.toString(this.isOptimizeAcknowledge()));
        props.setProperty("statsEnabled", Boolean.toString(this.isStatsEnabled()));
        props.setProperty("alwaysSyncSend", Boolean.toString(this.isAlwaysSyncSend()));
        props.setProperty("producerWindowSize", Integer.toString(this.getProducerWindowSize()));
        props.setProperty("sendTimeout", Integer.toString(this.getSendTimeout()));
        props.setProperty("connectResponseTimeout", Integer.toString(this.getConnectResponseTimeout()));
        props.setProperty("sendAcksAsync", Boolean.toString(this.isSendAcksAsync()));
        props.setProperty("auditDepth", Integer.toString(this.getAuditDepth()));
        props.setProperty("auditMaximumProducerNumber", Integer.toString(this.getAuditMaximumProducerNumber()));
        props.setProperty("checkForDuplicates", Boolean.toString(this.isCheckForDuplicates()));
        props.setProperty("messagePrioritySupported", Boolean.toString(this.isMessagePrioritySupported()));
        props.setProperty("transactedIndividualAck", Boolean.toString(this.isTransactedIndividualAck()));
        props.setProperty("nonBlockingRedelivery", Boolean.toString(this.isNonBlockingRedelivery()));
        props.setProperty("maxThreadPoolSize", Integer.toString(this.getMaxThreadPoolSize()));
        props.setProperty("nestedMapAndListEnabled", Boolean.toString(this.isNestedMapAndListEnabled()));
        props.setProperty("consumerFailoverRedeliveryWaitPeriod", Long.toString(this.getConsumerFailoverRedeliveryWaitPeriod()));
        props.setProperty("rmIdFromConnectionId", Boolean.toString(this.isRmIdFromConnectionId()));
        props.setProperty("consumerExpiryCheckEnabled", Boolean.toString(this.isConsumerExpiryCheckEnabled()));
    }

    public boolean isUseCompression() {
        return this.useCompression;
    }

    public void setUseCompression(boolean useCompression) {
        this.useCompression = useCompression;
    }

    public boolean isObjectMessageSerializationDefered() {
        return this.objectMessageSerializationDefered;
    }

    public void setObjectMessageSerializationDefered(boolean objectMessageSerializationDefered) {
        this.objectMessageSerializationDefered = objectMessageSerializationDefered;
    }

    public boolean isDispatchAsync() {
        return this.dispatchAsync;
    }

    public void setDispatchAsync(boolean asyncDispatch) {
        this.dispatchAsync = asyncDispatch;
    }

    public int getCloseTimeout() {
        return this.closeTimeout;
    }

    public void setCloseTimeout(int closeTimeout) {
        this.closeTimeout = closeTimeout;
    }

    public boolean isAlwaysSessionAsync() {
        return this.alwaysSessionAsync;
    }

    public void setAlwaysSessionAsync(boolean alwaysSessionAsync) {
        this.alwaysSessionAsync = alwaysSessionAsync;
    }

    public boolean isOptimizeAcknowledge() {
        return this.optimizeAcknowledge;
    }

    public void setOptimizeAcknowledge(boolean optimizeAcknowledge) {
        this.optimizeAcknowledge = optimizeAcknowledge;
    }

    public void setOptimizeAcknowledgeTimeOut(long optimizeAcknowledgeTimeOut) {
        this.optimizeAcknowledgeTimeOut = optimizeAcknowledgeTimeOut;
    }

    public long getOptimizeAcknowledgeTimeOut() {
        return this.optimizeAcknowledgeTimeOut;
    }

    public boolean isNestedMapAndListEnabled() {
        return this.nestedMapAndListEnabled;
    }

    public void setNestedMapAndListEnabled(boolean structuredMapsEnabled) {
        this.nestedMapAndListEnabled = structuredMapsEnabled;
    }

    public String getClientIDPrefix() {
        return this.clientIDPrefix;
    }

    public void setClientIDPrefix(String clientIDPrefix) {
        this.clientIDPrefix = clientIDPrefix;
    }

    protected synchronized IdGenerator getClientIdGenerator() {
        if (this.clientIdGenerator == null) {
            this.clientIdGenerator = this.clientIDPrefix != null ? new IdGenerator(this.clientIDPrefix) : new IdGenerator();
        }
        return this.clientIdGenerator;
    }

    protected void setClientIdGenerator(IdGenerator clientIdGenerator) {
        this.clientIdGenerator = clientIdGenerator;
    }

    public void setConnectionIDPrefix(String connectionIDPrefix) {
        this.connectionIDPrefix = connectionIDPrefix;
    }

    protected synchronized IdGenerator getConnectionIdGenerator() {
        if (this.connectionIdGenerator == null) {
            this.connectionIdGenerator = this.connectionIDPrefix != null ? new IdGenerator(this.connectionIDPrefix) : new IdGenerator();
        }
        return this.connectionIdGenerator;
    }

    protected void setConnectionIdGenerator(IdGenerator connectionIdGenerator) {
        this.connectionIdGenerator = connectionIdGenerator;
    }

    public boolean isStatsEnabled() {
        return this.factoryStats.isEnabled();
    }

    public void setStatsEnabled(boolean statsEnabled) {
        this.factoryStats.setEnabled(statsEnabled);
    }

    public synchronized int getProducerWindowSize() {
        return this.producerWindowSize;
    }

    public synchronized void setProducerWindowSize(int producerWindowSize) {
        this.producerWindowSize = producerWindowSize;
    }

    public long getWarnAboutUnstartedConnectionTimeout() {
        return this.warnAboutUnstartedConnectionTimeout;
    }

    public void setWarnAboutUnstartedConnectionTimeout(long warnAboutUnstartedConnectionTimeout) {
        this.warnAboutUnstartedConnectionTimeout = warnAboutUnstartedConnectionTimeout;
    }

    public TransportListener getTransportListener() {
        return this.transportListener;
    }

    public void setTransportListener(TransportListener transportListener) {
        this.transportListener = transportListener;
    }

    public ExceptionListener getExceptionListener() {
        return this.exceptionListener;
    }

    public void setExceptionListener(ExceptionListener exceptionListener) {
        this.exceptionListener = exceptionListener;
    }

    public int getAuditDepth() {
        return this.auditDepth;
    }

    public void setAuditDepth(int auditDepth) {
        this.auditDepth = auditDepth;
    }

    public int getAuditMaximumProducerNumber() {
        return this.auditMaximumProducerNumber;
    }

    public void setAuditMaximumProducerNumber(int auditMaximumProducerNumber) {
        this.auditMaximumProducerNumber = auditMaximumProducerNumber;
    }

    public void setUseDedicatedTaskRunner(boolean useDedicatedTaskRunner) {
        this.useDedicatedTaskRunner = useDedicatedTaskRunner;
    }

    public boolean isUseDedicatedTaskRunner() {
        return this.useDedicatedTaskRunner;
    }

    public void setConsumerFailoverRedeliveryWaitPeriod(long consumerFailoverRedeliveryWaitPeriod) {
        this.consumerFailoverRedeliveryWaitPeriod = consumerFailoverRedeliveryWaitPeriod;
    }

    public long getConsumerFailoverRedeliveryWaitPeriod() {
        return this.consumerFailoverRedeliveryWaitPeriod;
    }

    public ClientInternalExceptionListener getClientInternalExceptionListener() {
        return this.clientInternalExceptionListener;
    }

    public void setClientInternalExceptionListener(ClientInternalExceptionListener clientInternalExceptionListener) {
        this.clientInternalExceptionListener = clientInternalExceptionListener;
    }

    public boolean isCheckForDuplicates() {
        return this.checkForDuplicates;
    }

    public void setCheckForDuplicates(boolean checkForDuplicates) {
        this.checkForDuplicates = checkForDuplicates;
    }

    public boolean isTransactedIndividualAck() {
        return this.transactedIndividualAck;
    }

    public void setTransactedIndividualAck(boolean transactedIndividualAck) {
        this.transactedIndividualAck = transactedIndividualAck;
    }

    public boolean isNonBlockingRedelivery() {
        return this.nonBlockingRedelivery;
    }

    public void setNonBlockingRedelivery(boolean nonBlockingRedelivery) {
        this.nonBlockingRedelivery = nonBlockingRedelivery;
    }

    public int getMaxThreadPoolSize() {
        return this.maxThreadPoolSize;
    }

    public void setMaxThreadPoolSize(int maxThreadPoolSize) {
        this.maxThreadPoolSize = maxThreadPoolSize;
    }

    public TaskRunnerFactory getSessionTaskRunner() {
        return this.sessionTaskRunner;
    }

    public void setSessionTaskRunner(TaskRunnerFactory sessionTaskRunner) {
        this.sessionTaskRunner = sessionTaskRunner;
    }

    public RejectedExecutionHandler getRejectedTaskHandler() {
        return this.rejectedTaskHandler;
    }

    public void setRejectedTaskHandler(RejectedExecutionHandler rejectedTaskHandler) {
        this.rejectedTaskHandler = rejectedTaskHandler;
    }

    public long getOptimizedAckScheduledAckInterval() {
        return this.optimizedAckScheduledAckInterval;
    }

    public void setOptimizedAckScheduledAckInterval(long optimizedAckScheduledAckInterval) {
        this.optimizedAckScheduledAckInterval = optimizedAckScheduledAckInterval;
    }

    public boolean isRmIdFromConnectionId() {
        return this.rmIdFromConnectionId;
    }

    public void setRmIdFromConnectionId(boolean rmIdFromConnectionId) {
        this.rmIdFromConnectionId = rmIdFromConnectionId;
    }

    public boolean isConsumerExpiryCheckEnabled() {
        return this.consumerExpiryCheckEnabled;
    }

    public void setConsumerExpiryCheckEnabled(boolean consumerExpiryCheckEnabled) {
        this.consumerExpiryCheckEnabled = consumerExpiryCheckEnabled;
    }

    public List<String> getTrustedPackages() {
        return this.trustedPackages;
    }

    public void setTrustedPackages(List<String> trustedPackages) {
        this.trustedPackages = trustedPackages;
    }

    public boolean isTrustAllPackages() {
        return this.trustAllPackages;
    }

    public void setTrustAllPackages(boolean trustAllPackages) {
        this.trustAllPackages = trustAllPackages;
    }

    public int getConnectResponseTimeout() {
        return this.connectResponseTimeout;
    }

    public void setConnectResponseTimeout(int connectResponseTimeout) {
        this.connectResponseTimeout = connectResponseTimeout;
    }

    static {
        String host2 = null;
        String port = null;
        try {
            host2 = AccessController.doPrivileged(new PrivilegedAction<String>(){

                @Override
                public String run() {
                    String result = System.getProperty("org.apache.activemq.AMQ_HOST");
                    result = result == null || result.isEmpty() ? System.getProperty("AMQ_HOST", "localhost") : result;
                    return result;
                }
            });
            port = AccessController.doPrivileged(new PrivilegedAction<String>(){

                @Override
                public String run() {
                    String result = System.getProperty("org.apache.activemq.AMQ_PORT");
                    result = result == null || result.isEmpty() ? System.getProperty("AMQ_PORT", "61616") : result;
                    return result;
                }
            });
        }
        catch (Throwable e) {
            LOG.debug("Failed to look up System properties for host and port", e);
        }
        host2 = host2 == null || host2.isEmpty() ? "localhost" : host2;
        port = port == null || port.isEmpty() ? "61616" : port;
        DEFAULT_BROKER_HOST = host2;
        DEFAULT_BROKER_PORT = Integer.parseInt(port);
        final String defaultURL = "tcp://" + DEFAULT_BROKER_HOST + ":" + DEFAULT_BROKER_PORT;
        String bindURL = null;
        try {
            bindURL = AccessController.doPrivileged(new PrivilegedAction<String>(){

                @Override
                public String run() {
                    String result = System.getProperty("org.apache.activemq.BROKER_BIND_URL");
                    result = result == null || result.isEmpty() ? System.getProperty("BROKER_BIND_URL", defaultURL) : result;
                    return result;
                }
            });
        }
        catch (Throwable e) {
            LOG.debug("Failed to look up System properties for host and port", e);
        }
        DEFAULT_BROKER_BIND_URL = bindURL = bindURL == null || bindURL.isEmpty() ? defaultURL : bindURL;
        DEFAULT_BROKER_URL = "failover://" + DEFAULT_BROKER_BIND_URL;
        DEFAULT_USER = null;
        DEFAULT_PASSWORD = null;
    }
}

