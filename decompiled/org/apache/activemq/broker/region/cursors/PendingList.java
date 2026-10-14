/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.cursors;

import java.util.Collection;
import java.util.Iterator;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.cursors.PendingNode;
import org.apache.activemq.command.MessageId;

public interface PendingList
extends Iterable<MessageReference> {
    public boolean isEmpty();

    public void clear();

    public PendingNode addMessageFirst(MessageReference var1);

    public PendingNode addMessageLast(MessageReference var1);

    public PendingNode remove(MessageReference var1);

    public int size();

    public long messageSize();

    @Override
    public Iterator<MessageReference> iterator();

    public boolean contains(MessageReference var1);

    public Collection<MessageReference> values();

    public void addAll(PendingList var1);

    public MessageReference get(MessageId var1);
}

