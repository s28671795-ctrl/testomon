/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.cursors;

import java.util.LinkedList;
import java.util.List;
import org.apache.activemq.ActiveMQMessageAudit;
import org.apache.activemq.Service;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.usage.SystemUsage;

public interface PendingMessageCursor
extends Service {
    public static final long INFINITE_WAIT = 0L;

    public void add(ConnectionContext var1, Destination var2) throws Exception;

    public List<MessageReference> remove(ConnectionContext var1, Destination var2) throws Exception;

    public boolean isEmpty();

    public boolean isEmpty(Destination var1);

    public void reset();

    public void release();

    public boolean addMessageLast(MessageReference var1) throws Exception;

    public boolean tryAddMessageLast(MessageReference var1, long var2) throws Exception;

    public void addMessageFirst(MessageReference var1) throws Exception;

    public void addRecoveredMessage(MessageReference var1) throws Exception;

    public boolean hasNext();

    public MessageReference next();

    public void remove();

    public int size();

    public long messageSize();

    public void clear();

    public boolean isRecoveryRequired();

    public int getMaxBatchSize();

    public void setMaxBatchSize(int var1);

    public void resetForGC();

    public void remove(MessageReference var1);

    public void gc();

    public void setSystemUsage(SystemUsage var1);

    public SystemUsage getSystemUsage();

    public int getMemoryUsageHighWaterMark();

    public void setMemoryUsageHighWaterMark(int var1);

    public boolean isFull();

    public boolean hasSpace();

    public boolean hasMessagesBufferedToDeliver();

    public void destroy() throws Exception;

    public LinkedList<MessageReference> pageInList(int var1);

    public void setMaxProducersToAudit(int var1);

    public int getMaxProducersToAudit();

    public void setMaxAuditDepth(int var1);

    public int getMaxAuditDepth();

    public boolean isEnableAudit();

    public void setEnableAudit(boolean var1);

    public boolean isTransient();

    public void setMessageAudit(ActiveMQMessageAudit var1);

    public ActiveMQMessageAudit getMessageAudit();

    public void setUseCache(boolean var1);

    public boolean isUseCache();

    public void rollback(MessageId var1);

    public boolean isCacheEnabled();

    public void rebase();
}

