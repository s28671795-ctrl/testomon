/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageAck;
import org.apache.activemq.command.XATransactionId;

public interface TransactionRecoveryListener {
    public void recover(XATransactionId var1, Message[] var2, MessageAck[] var3);
}

