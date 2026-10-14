/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import java.io.IOException;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.SubscriptionInfo;
import org.apache.activemq.store.MessageRecoveryListener;
import org.apache.activemq.store.ReferenceStore;
import org.apache.activemq.store.TopicMessageStore;

public interface TopicReferenceStore
extends ReferenceStore,
TopicMessageStore {
    public boolean acknowledgeReference(ConnectionContext var1, String var2, String var3, MessageId var4) throws IOException;

    @Override
    public void deleteSubscription(String var1, String var2) throws IOException;

    @Override
    public void recoverSubscription(String var1, String var2, MessageRecoveryListener var3) throws Exception;

    @Override
    public void recoverNextMessages(String var1, String var2, int var3, MessageRecoveryListener var4) throws Exception;

    @Override
    public void resetBatching(String var1, String var2);

    @Override
    public int getMessageCount(String var1, String var2) throws IOException;

    @Override
    public SubscriptionInfo lookupSubscription(String var1, String var2) throws IOException;

    @Override
    public SubscriptionInfo[] getAllSubscriptions() throws IOException;

    public void addSubsciption(SubscriptionInfo var1, boolean var2) throws IOException;
}

