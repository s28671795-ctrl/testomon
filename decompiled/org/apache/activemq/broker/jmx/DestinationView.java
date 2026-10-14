/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jms.Connection;
import javax.jms.InvalidSelectorException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.jmx.BrokerMBeanSupport;
import org.apache.activemq.broker.jmx.DestinationViewMBean;
import org.apache.activemq.broker.jmx.ManagedRegionBroker;
import org.apache.activemq.broker.jmx.OpenTypeSupport;
import org.apache.activemq.broker.jmx.Sensitive;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.AbortSlowConsumerStrategy;
import org.apache.activemq.broker.region.policy.SlowConsumerStrategy;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.Message;
import org.apache.activemq.filter.BooleanExpression;
import org.apache.activemq.filter.NonCachedMessageEvaluationContext;
import org.apache.activemq.selector.SelectorParser;
import org.apache.activemq.store.MessageStore;
import org.apache.activemq.util.URISupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DestinationView
implements DestinationViewMBean {
    private static final Logger LOG = LoggerFactory.getLogger(DestinationViewMBean.class);
    protected final Destination destination;
    protected final ManagedRegionBroker broker;

    public DestinationView(ManagedRegionBroker broker, Destination destination) {
        this.broker = broker;
        this.destination = destination;
    }

    public void gc() {
        this.destination.gc();
    }

    @Override
    public String getName() {
        return this.destination.getName();
    }

    @Override
    public void resetStatistics() {
        this.destination.getDestinationStatistics().reset();
    }

    @Override
    public long getEnqueueCount() {
        return this.destination.getDestinationStatistics().getEnqueues().getCount();
    }

    @Override
    public long getDequeueCount() {
        return this.destination.getDestinationStatistics().getDequeues().getCount();
    }

    @Override
    public long getForwardCount() {
        return this.destination.getDestinationStatistics().getForwards().getCount();
    }

    @Override
    public long getDispatchCount() {
        return this.destination.getDestinationStatistics().getDispatched().getCount();
    }

    @Override
    public long getInFlightCount() {
        return this.destination.getDestinationStatistics().getInflight().getCount();
    }

    @Override
    public long getExpiredCount() {
        return this.destination.getDestinationStatistics().getExpired().getCount();
    }

    @Override
    public long getConsumerCount() {
        return this.destination.getDestinationStatistics().getConsumers().getCount();
    }

    @Override
    public long getQueueSize() {
        return this.destination.getDestinationStatistics().getMessages().getCount();
    }

    @Override
    public long getStoreMessageSize() {
        MessageStore messageStore = this.destination.getMessageStore();
        return messageStore != null ? messageStore.getMessageStoreStatistics().getMessageSize().getTotalSize() : 0L;
    }

    public long getMessagesCached() {
        return this.destination.getDestinationStatistics().getMessagesCached().getCount();
    }

    @Override
    public int getMemoryPercentUsage() {
        return this.destination.getMemoryUsage().getPercentUsage();
    }

    @Override
    public long getMemoryUsageByteCount() {
        return this.destination.getMemoryUsage().getUsage();
    }

    @Override
    public long getMemoryLimit() {
        return this.destination.getMemoryUsage().getLimit();
    }

    @Override
    public void setMemoryLimit(long limit) {
        this.destination.getMemoryUsage().setLimit(limit);
    }

    @Override
    public double getAverageEnqueueTime() {
        return this.destination.getDestinationStatistics().getProcessTime().getAverageTime();
    }

    @Override
    public int getTempUsagePercentUsage() {
        return this.destination.getTempUsage().getPercentUsage();
    }

    @Override
    public long getTempUsageLimit() {
        return this.destination.getTempUsage().getLimit();
    }

    @Override
    public void setTempUsageLimit(long limit) {
        this.destination.getTempUsage().setLimit(limit);
    }

    @Override
    public long getMaxEnqueueTime() {
        return this.destination.getDestinationStatistics().getProcessTime().getMaxTime();
    }

    @Override
    public long getMinEnqueueTime() {
        return this.destination.getDestinationStatistics().getProcessTime().getMinTime();
    }

    @Override
    public long getAverageMessageSize() {
        return (long)this.destination.getDestinationStatistics().getMessageSize().getAverageSize();
    }

    @Override
    public long getMaxMessageSize() {
        return this.destination.getDestinationStatistics().getMessageSize().getMaxSize();
    }

    @Override
    public long getMinMessageSize() {
        return this.destination.getDestinationStatistics().getMessageSize().getMinSize();
    }

    @Override
    public boolean isPrioritizedMessages() {
        return this.destination.isPrioritizedMessages();
    }

    @Override
    public CompositeData[] browse() throws OpenDataException {
        try {
            return this.browse(null);
        }
        catch (InvalidSelectorException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompositeData[] browse(String selector) throws OpenDataException, InvalidSelectorException {
        Message[] messages = this.destination.browse();
        ArrayList<CompositeData> c = new ArrayList<CompositeData>();
        NonCachedMessageEvaluationContext ctx = new NonCachedMessageEvaluationContext();
        ctx.setDestination(this.destination.getActiveMQDestination());
        BooleanExpression selectorExpression = selector == null ? null : SelectorParser.parse(selector);
        for (int i = 0; i < messages.length; ++i) {
            try {
                if (selectorExpression == null) {
                    c.add(OpenTypeSupport.convert(messages[i]));
                    continue;
                }
                ctx.setMessageReference(messages[i]);
                if (!selectorExpression.matches(ctx)) continue;
                c.add(OpenTypeSupport.convert(messages[i]));
                continue;
            }
            catch (Throwable e) {
                LOG.warn("exception browsing destination", e);
            }
        }
        CompositeData[] rc = new CompositeData[c.size()];
        c.toArray(rc);
        return rc;
    }

    public List<Object> browseMessages() throws InvalidSelectorException {
        return this.browseMessages(null);
    }

    public List<Object> browseMessages(String selector) throws InvalidSelectorException {
        Message[] messages = this.destination.browse();
        ArrayList<Object> answer = new ArrayList<Object>();
        NonCachedMessageEvaluationContext ctx = new NonCachedMessageEvaluationContext();
        ctx.setDestination(this.destination.getActiveMQDestination());
        BooleanExpression selectorExpression = selector == null ? null : SelectorParser.parse(selector);
        for (int i = 0; i < messages.length; ++i) {
            try {
                Message message = messages[i];
                message.setReadOnlyBody(true);
                if (selectorExpression == null) {
                    answer.add(message);
                    continue;
                }
                ctx.setMessageReference(message);
                if (!selectorExpression.matches(ctx)) continue;
                answer.add(message);
                continue;
            }
            catch (Throwable e) {
                LOG.warn("exception browsing destination", e);
            }
        }
        return answer;
    }

    @Override
    public TabularData browseAsTable() throws OpenDataException {
        try {
            return this.browseAsTable(null);
        }
        catch (InvalidSelectorException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TabularData browseAsTable(String selector) throws OpenDataException, InvalidSelectorException {
        OpenTypeSupport.OpenTypeFactory factory2 = OpenTypeSupport.getFactory(ActiveMQMessage.class);
        Message[] messages = this.destination.browse();
        CompositeType ct = factory2.getCompositeType();
        TabularType tt = new TabularType("MessageList", "MessageList", ct, new String[]{"JMSMessageID"});
        TabularDataSupport rc = new TabularDataSupport(tt);
        NonCachedMessageEvaluationContext ctx = new NonCachedMessageEvaluationContext();
        ctx.setDestination(this.destination.getActiveMQDestination());
        BooleanExpression selectorExpression = selector == null ? null : SelectorParser.parse(selector);
        for (int i = 0; i < messages.length; ++i) {
            try {
                if (selectorExpression == null) {
                    rc.put(new CompositeDataSupport(ct, factory2.getFields(messages[i])));
                    continue;
                }
                ctx.setMessageReference(messages[i]);
                if (!selectorExpression.matches(ctx)) continue;
                rc.put(new CompositeDataSupport(ct, factory2.getFields(messages[i])));
                continue;
            }
            catch (Throwable e) {
                LOG.warn("exception browsing destination", e);
            }
        }
        return rc;
    }

    @Override
    public String sendTextMessageWithProperties(String properties) throws Exception {
        String[] kvs = properties.split(",");
        HashMap<String, String> props = new HashMap<String, String>();
        for (String kv : kvs) {
            String[] it = kv.split("=");
            if (it.length != 2) continue;
            props.put(it[0], it[1]);
        }
        return this.sendTextMessage(props, (String)props.remove("body"), (String)props.remove("username"), (String)props.remove("password"));
    }

    @Override
    public String sendTextMessage(String body2) throws Exception {
        return this.sendTextMessage(Collections.EMPTY_MAP, body2);
    }

    public String sendTextMessage(Map headers, String body2) throws Exception {
        return this.sendTextMessage(headers, body2, null, null);
    }

    @Override
    public String sendTextMessage(String body2, String user, @Sensitive String password) throws Exception {
        return this.sendTextMessage(Collections.EMPTY_MAP, body2, user, password);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String sendTextMessage(Map<String, String> headers, String body2, String userName, @Sensitive String password) throws Exception {
        String brokerUrl = "vm://" + this.broker.getBrokerName();
        ActiveMQDestination dest = this.destination.getActiveMQDestination();
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(brokerUrl);
        try (Connection connection = null;){
            connection = cf.createConnection(userName, password);
            Session session = connection.createSession(false, 1);
            MessageProducer producer = session.createProducer(dest);
            ActiveMQTextMessage msg = (ActiveMQTextMessage)session.createTextMessage(body2);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                msg.setObjectProperty(entry.getKey(), entry.getValue());
            }
            producer.setDeliveryMode(msg.getJMSDeliveryMode());
            producer.setPriority(msg.getPriority());
            long ttl = 0L;
            if (msg.getExpiration() != 0L) {
                ttl = msg.getExpiration() - System.currentTimeMillis();
            } else {
                String timeToLive = headers.get("timeToLive");
                if (timeToLive != null) {
                    ttl = Integer.valueOf(timeToLive).intValue();
                }
            }
            producer.setTimeToLive(ttl > 0L ? ttl : 0L);
            producer.send(msg);
            String string = msg.getJMSMessageID();
            return string;
        }
    }

    @Override
    public int getMaxAuditDepth() {
        return this.destination.getMaxAuditDepth();
    }

    @Override
    public int getMaxProducersToAudit() {
        return this.destination.getMaxProducersToAudit();
    }

    public boolean isEnableAudit() {
        return this.destination.isEnableAudit();
    }

    public void setEnableAudit(boolean enableAudit) {
        this.destination.setEnableAudit(enableAudit);
    }

    @Override
    public void setMaxAuditDepth(int maxAuditDepth) {
        this.destination.setMaxAuditDepth(maxAuditDepth);
    }

    @Override
    public void setMaxProducersToAudit(int maxProducersToAudit) {
        this.destination.setMaxProducersToAudit(maxProducersToAudit);
    }

    @Override
    public float getMemoryUsagePortion() {
        return this.destination.getMemoryUsage().getUsagePortion();
    }

    @Override
    public long getProducerCount() {
        return this.destination.getDestinationStatistics().getProducers().getCount();
    }

    @Override
    public boolean isProducerFlowControl() {
        return this.destination.isProducerFlowControl();
    }

    @Override
    public void setMemoryUsagePortion(float value) {
        this.destination.getMemoryUsage().setUsagePortion(value);
    }

    @Override
    public void setProducerFlowControl(boolean producerFlowControl) {
        this.destination.setProducerFlowControl(producerFlowControl);
    }

    @Override
    public boolean isAlwaysRetroactive() {
        return this.destination.isAlwaysRetroactive();
    }

    @Override
    public void setAlwaysRetroactive(boolean alwaysRetroactive) {
        this.destination.setAlwaysRetroactive(alwaysRetroactive);
    }

    @Override
    public void setBlockedProducerWarningInterval(long blockedProducerWarningInterval) {
        this.destination.setBlockedProducerWarningInterval(blockedProducerWarningInterval);
    }

    @Override
    public long getBlockedProducerWarningInterval() {
        return this.destination.getBlockedProducerWarningInterval();
    }

    @Override
    public int getMaxPageSize() {
        return this.destination.getMaxPageSize();
    }

    @Override
    public void setMaxPageSize(int pageSize) {
        this.destination.setMaxPageSize(pageSize);
    }

    @Override
    public boolean isUseCache() {
        return this.destination.isUseCache();
    }

    @Override
    public void setUseCache(boolean value) {
        this.destination.setUseCache(value);
    }

    @Override
    public ObjectName[] getSubscriptions() throws IOException, MalformedObjectNameException {
        List<Subscription> subscriptions = this.destination.getConsumers();
        ObjectName[] answer = new ObjectName[subscriptions.size()];
        ObjectName brokerObjectName = this.broker.getBrokerService().getBrokerObjectName();
        int index = 0;
        for (Subscription subscription : subscriptions) {
            String connectionClientId = subscription.getContext().getClientId();
            answer[index++] = BrokerMBeanSupport.createSubscriptionName(brokerObjectName, connectionClientId, subscription.getConsumerInfo());
        }
        return answer;
    }

    @Override
    public ObjectName getSlowConsumerStrategy() throws IOException, MalformedObjectNameException {
        ObjectName result = null;
        SlowConsumerStrategy strategy = this.destination.getSlowConsumerStrategy();
        if (strategy != null && strategy instanceof AbortSlowConsumerStrategy) {
            result = this.broker.registerSlowConsumerStrategy((AbortSlowConsumerStrategy)strategy);
        }
        return result;
    }

    @Override
    public String getOptions() {
        Map<String, String> options = this.destination.getActiveMQDestination().getOptions();
        String optionsString = "";
        try {
            if (options != null) {
                optionsString = URISupport.createQueryString(options);
            }
        }
        catch (URISyntaxException uRISyntaxException) {
            // empty catch block
        }
        return optionsString;
    }

    @Override
    public boolean isDLQ() {
        return this.destination.getActiveMQDestination().isDLQ();
    }

    @Override
    public void setDLQ(boolean val) {
        this.destination.getActiveMQDestination().setDLQ(val);
    }

    @Override
    public long getBlockedSends() {
        return this.destination.getDestinationStatistics().getBlockedSends().getCount();
    }

    @Override
    public double getAverageBlockedTime() {
        return this.destination.getDestinationStatistics().getBlockedTime().getAverageTime();
    }

    @Override
    public long getTotalBlockedTime() {
        return this.destination.getDestinationStatistics().getBlockedTime().getTotalTime();
    }
}

