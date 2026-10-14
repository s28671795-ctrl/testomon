/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.TabularData;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.jmx.DurableSubscriptionViewMBean;
import org.apache.activemq.broker.jmx.MBeanInfo;
import org.apache.activemq.broker.jmx.ManagedRegionBroker;
import org.apache.activemq.broker.jmx.SubscriptionView;
import org.apache.activemq.broker.region.DurableTopicSubscription;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.RemoveSubscriptionInfo;

public class DurableSubscriptionView
extends SubscriptionView
implements DurableSubscriptionViewMBean {
    protected ManagedRegionBroker broker;
    protected BrokerService brokerService;
    protected String subscriptionName;
    protected DurableTopicSubscription durableSub;

    public DurableSubscriptionView(ManagedRegionBroker broker, BrokerService brokerService, String clientId, String userName, Subscription sub) {
        super(clientId, userName, sub);
        this.broker = broker;
        this.brokerService = brokerService;
        this.durableSub = (DurableTopicSubscription)sub;
        if (sub != null) {
            this.subscriptionName = sub.getConsumerInfo().getSubscriptionName();
        }
    }

    @Override
    public String getSubscriptionName() {
        return this.subscriptionName;
    }

    @Override
    public CompositeData[] browse() throws OpenDataException {
        return this.broker.browse(this);
    }

    @Override
    public TabularData browseAsTable() throws OpenDataException {
        return this.broker.browseAsTable(this);
    }

    @Override
    public void destroy() throws Exception {
        RemoveSubscriptionInfo info = new RemoveSubscriptionInfo();
        info.setClientId(this.clientId);
        info.setSubscriptionName(this.subscriptionName);
        ConnectionContext context = new ConnectionContext();
        context.setBroker(this.broker);
        context.setClientId(this.clientId);
        this.brokerService.getBroker().removeSubscription(context, info);
    }

    @Override
    public String toString() {
        return "ActiveDurableSubscriptionView: " + this.getClientId() + ":" + this.getSubscriptionName();
    }

    @Override
    public int cursorSize() {
        if (this.durableSub != null && this.durableSub.getPending() != null) {
            return this.durableSub.getPending().size();
        }
        return 0;
    }

    @Override
    public void removeMessage(@MBeanInfo(value="messageId") String messageId) throws Exception {
        throw new IllegalStateException("Subscription must be inactive");
    }

    @Override
    public boolean doesCursorHaveMessagesBuffered() {
        if (this.durableSub != null && this.durableSub.getPending() != null) {
            return this.durableSub.getPending().hasMessagesBufferedToDeliver();
        }
        return false;
    }

    @Override
    public boolean doesCursorHaveSpace() {
        if (this.durableSub != null && this.durableSub.getPending() != null) {
            return this.durableSub.getPending().hasSpace();
        }
        return false;
    }

    @Override
    public long getCursorMemoryUsage() {
        if (this.durableSub != null && this.durableSub.getPending() != null && this.durableSub.getPending().getSystemUsage() != null) {
            return this.durableSub.getPending().getSystemUsage().getMemoryUsage().getUsage();
        }
        return 0L;
    }

    @Override
    public int getCursorPercentUsage() {
        if (this.durableSub != null && this.durableSub.getPending() != null && this.durableSub.getPending().getSystemUsage() != null) {
            return this.durableSub.getPending().getSystemUsage().getMemoryUsage().getPercentUsage();
        }
        return 0;
    }

    @Override
    public boolean isCursorFull() {
        if (this.durableSub != null && this.durableSub.getPending() != null) {
            return this.durableSub.getPending().isFull();
        }
        return false;
    }

    @Override
    public boolean isActive() {
        return this.durableSub.isActive();
    }
}

