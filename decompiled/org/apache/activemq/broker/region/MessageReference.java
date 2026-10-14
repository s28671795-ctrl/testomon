/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageId;

public interface MessageReference {
    public MessageId getMessageId();

    public Message getMessageHardRef();

    public Message getMessage();

    public boolean isPersistent();

    public Message.MessageDestination getRegionDestination();

    public int getRedeliveryCounter();

    public void incrementRedeliveryCounter();

    public int getReferenceCount();

    public int incrementReferenceCount();

    public int decrementReferenceCount();

    public ConsumerId getTargetConsumerId();

    public int getSize();

    public long getExpiration();

    public String getGroupID();

    public int getGroupSequence();

    public boolean isExpired();

    public boolean isDropped();

    public boolean isAdvisory();

    public boolean canProcessAsExpired();
}

