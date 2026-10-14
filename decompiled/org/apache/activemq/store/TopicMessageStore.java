/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import java.io.IOException;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.SubscriptionInfo;
import org.apache.activemq.store.MessageRecoveryListener;
import org.apache.activemq.store.MessageStore;
import org.apache.activemq.store.MessageStoreSubscriptionStatistics;

public interface TopicMessageStore
extends MessageStore {
    public void acknowledge(ConnectionContext var1, String var2, String var3, MessageId var4, MessageAck var5) throws IOException;

    public void deleteSubscription(String var1, String var2) throws IOException;

    public void recoverSubscription(String var1, String var2, MessageRecoveryListener var3) throws Exception;

    public void recoverNextMessages(String var1, String var2, int var3, MessageRecoveryListener var4) throws Exception;

    public void resetBatching(String var1, String var2);

    public int getMessageCount(String var1, String var2) throws IOException;

    public long getMessageSize(String var1, String var2) throws IOException;

    public MessageStoreSubscriptionStatistics getMessageStoreSubStatistics();

    public SubscriptionInfo lookupSubscription(String var1, String var2) throws IOException;

    public SubscriptionInfo[] getAllSubscriptions() throws IOException;

    public void addSubscription(SubscriptionInfo var1, boolean var2) throws IOException;
}

