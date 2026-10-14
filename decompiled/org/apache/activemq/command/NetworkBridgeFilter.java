/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.io.IOException;
import java.util.Arrays;
import javax.jms.JMSException;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.Message;
import org.apache.activemq.filter.BooleanExpression;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.util.JMSExceptionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkBridgeFilter
implements DataStructure,
BooleanExpression {
    public static final byte DATA_STRUCTURE_TYPE = 91;
    static final Logger LOG = LoggerFactory.getLogger(NetworkBridgeFilter.class);
    protected BrokerId networkBrokerId;
    protected int messageTTL;
    protected int consumerTTL;
    transient ConsumerInfo consumerInfo;

    public NetworkBridgeFilter() {
    }

    public NetworkBridgeFilter(ConsumerInfo consumerInfo, BrokerId networkBrokerId, int messageTTL, int consumerTTL) {
        this.networkBrokerId = networkBrokerId;
        this.messageTTL = messageTTL;
        this.consumerTTL = consumerTTL;
        this.consumerInfo = consumerInfo;
    }

    @Override
    public byte getDataStructureType() {
        return 91;
    }

    @Override
    public boolean isMarshallAware() {
        return false;
    }

    @Override
    public boolean matches(MessageEvaluationContext mec) throws JMSException {
        try {
            Message message = mec.getMessage();
            return message != null && this.matchesForwardingFilter(message, mec);
        }
        catch (IOException e) {
            throw JMSExceptionSupport.create(e);
        }
    }

    @Override
    public Object evaluate(MessageEvaluationContext message) throws JMSException {
        return this.matches(message) ? Boolean.TRUE : Boolean.FALSE;
    }

    protected boolean matchesForwardingFilter(Message message, MessageEvaluationContext mec) {
        int hops;
        if (NetworkBridgeFilter.contains(message.getBrokerPath(), this.networkBrokerId)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Message all ready routed once through target broker (" + this.networkBrokerId + "), path: " + Arrays.toString(message.getBrokerPath()) + " - ignoring: " + message);
            }
            return false;
        }
        int n = hops = message.getBrokerPath() == null ? 0 : message.getBrokerPath().length;
        if (this.messageTTL > -1 && hops >= this.messageTTL) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Message restricted to " + this.messageTTL + " network hops ignoring: " + message);
            }
            return false;
        }
        if (message.isAdvisory()) {
            if (this.consumerInfo != null && this.consumerInfo.isNetworkSubscription() && NetworkBridgeFilter.isAdvisoryInterpretedByNetworkBridge(message)) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("not propagating advisory to network sub: " + this.consumerInfo.getConsumerId() + ", message: " + message);
                }
                return false;
            }
            if (message.getDataStructure() != null && message.getDataStructure().getDataStructureType() == 5) {
                ConsumerInfo info = (ConsumerInfo)message.getDataStructure();
                int n2 = hops = info.getBrokerPath() == null ? 0 : info.getBrokerPath().length;
                if (this.consumerTTL > -1 && hops >= this.consumerTTL) {
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("ConsumerInfo advisory restricted to " + this.consumerTTL + " network hops ignoring: " + message);
                    }
                    return false;
                }
                if (NetworkBridgeFilter.contains(info.getBrokerPath(), this.networkBrokerId)) {
                    LOG.trace("ConsumerInfo advisory all ready routed once through target broker (" + this.networkBrokerId + "), path: " + Arrays.toString(info.getBrokerPath()) + " - ignoring: " + message);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isAdvisoryInterpretedByNetworkBridge(Message message) {
        return AdvisorySupport.isConsumerAdvisoryTopic(message.getDestination()) || AdvisorySupport.isVirtualDestinationConsumerAdvisoryTopic(message.getDestination()) || AdvisorySupport.isTempDestinationAdvisoryTopic(message.getDestination());
    }

    public static boolean contains(BrokerId[] brokerPath, BrokerId brokerId) {
        if (brokerPath != null && brokerId != null) {
            for (int i = 0; i < brokerPath.length; ++i) {
                if (!brokerId.equals(brokerPath[i])) continue;
                return true;
            }
        }
        return false;
    }

    public int getNetworkTTL() {
        return this.messageTTL;
    }

    public void setNetworkTTL(int networkTTL) {
        this.messageTTL = networkTTL;
        this.consumerTTL = networkTTL;
    }

    public BrokerId getNetworkBrokerId() {
        return this.networkBrokerId;
    }

    public void setNetworkBrokerId(BrokerId remoteBrokerPath) {
        this.networkBrokerId = remoteBrokerPath;
    }

    public void setMessageTTL(int messageTTL) {
        this.messageTTL = messageTTL;
    }

    public int getMessageTTL() {
        return this.messageTTL;
    }

    public void setConsumerTTL(int consumerTTL) {
        this.consumerTTL = consumerTTL;
    }

    public int getConsumerTTL() {
        return this.consumerTTL;
    }
}

