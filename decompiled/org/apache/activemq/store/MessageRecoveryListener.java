/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageId;

public interface MessageRecoveryListener {
    public boolean recoverMessage(Message var1) throws Exception;

    public boolean recoverMessageReference(MessageId var1) throws Exception;

    public boolean hasSpace();

    default public boolean canRecoveryNextMessage() {
        return true;
    }

    public boolean isDuplicate(MessageId var1);
}

