/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import org.apache.activemq.command.SubscriptionInfo;

public class SubscriptionKey {
    public final String clientId;
    public final String subscriptionName;
    private final int hashValue;

    public SubscriptionKey(SubscriptionInfo info) {
        this(info.getClientId(), info.getSubscriptionName());
    }

    public SubscriptionKey(String clientId, String subscriptionName) {
        this.clientId = clientId;
        this.subscriptionName = subscriptionName != null ? subscriptionName : "NOT_SET";
        this.hashValue = clientId.hashCode() ^ this.subscriptionName.hashCode();
    }

    public int hashCode() {
        return this.hashValue;
    }

    public boolean equals(Object o) {
        try {
            SubscriptionKey key = (SubscriptionKey)o;
            return key.clientId.equals(this.clientId) && key.subscriptionName.equals(this.subscriptionName);
        }
        catch (Throwable e) {
            return false;
        }
    }

    public String toString() {
        return this.clientId + ":" + this.subscriptionName;
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getSubscriptionName() {
        return this.subscriptionName;
    }
}

