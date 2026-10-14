/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.util.IntrospectionSupport;

public class SubscriptionInfo
implements DataStructure {
    public static final byte DATA_STRUCTURE_TYPE = 55;
    protected ActiveMQDestination subscribedDestination;
    protected ActiveMQDestination destination;
    protected String clientId;
    protected String subscriptionName;
    protected String selector;
    protected boolean noLocal;

    public SubscriptionInfo() {
    }

    public SubscriptionInfo(String clientId, String subscriptionName) {
        this.clientId = clientId;
        this.subscriptionName = subscriptionName;
    }

    @Override
    public byte getDataStructureType() {
        return 55;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ActiveMQDestination getDestination() {
        return this.destination;
    }

    public void setDestination(ActiveMQDestination destination) {
        this.destination = destination;
    }

    public String getSelector() {
        return this.selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getSubcriptionName() {
        return this.subscriptionName;
    }

    public void setSubcriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public String getSubscriptionName() {
        return this.subscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    @Override
    public boolean isMarshallAware() {
        return false;
    }

    public String toString() {
        return IntrospectionSupport.toString(this);
    }

    public int hashCode() {
        int h1 = this.clientId != null ? this.clientId.hashCode() : -1;
        int h2 = this.subscriptionName != null ? this.subscriptionName.hashCode() : -1;
        return h1 ^ h2;
    }

    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof SubscriptionInfo) {
            SubscriptionInfo other = (SubscriptionInfo)obj;
            result = (this.clientId == null && other.clientId == null || this.clientId != null && other.clientId != null && this.clientId.equals(other.clientId)) && (this.subscriptionName == null && other.subscriptionName == null || this.subscriptionName != null && other.subscriptionName != null && this.subscriptionName.equals(other.subscriptionName));
        }
        return result;
    }

    public ActiveMQDestination getSubscribedDestination() {
        if (this.subscribedDestination == null) {
            return this.getDestination();
        }
        return this.subscribedDestination;
    }

    public void setSubscribedDestination(ActiveMQDestination subscribedDestination) {
        this.subscribedDestination = subscribedDestination;
    }

    public boolean isNoLocal() {
        return this.noLocal;
    }

    public void setNoLocal(boolean noLocal) {
        this.noLocal = noLocal;
    }
}

