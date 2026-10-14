/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import org.apache.activemq.broker.region.LockOwner;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.NullMessageReference;

public interface QueueMessageReference
extends MessageReference {
    public static final QueueMessageReference NULL_MESSAGE = new NullMessageReference();

    public boolean isAcked();

    public void setAcked(boolean var1);

    public void drop();

    public boolean dropIfLive();

    @Override
    public boolean isDropped();

    public boolean lock(LockOwner var1);

    public boolean unlock();

    public LockOwner getLockOwner();
}

