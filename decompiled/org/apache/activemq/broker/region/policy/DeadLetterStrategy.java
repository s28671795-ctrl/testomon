/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;

public interface DeadLetterStrategy {
    public boolean isSendToDeadLetterQueue(Message var1);

    public ActiveMQDestination getDeadLetterQueueFor(Message var1, Subscription var2);

    public boolean isProcessExpired();

    public void setProcessExpired(boolean var1);

    public boolean isProcessNonPersistent();

    public void setProcessNonPersistent(boolean var1);

    public void rollback(Message var1);

    public void setExpiration(long var1);

    public long getExpiration();
}

