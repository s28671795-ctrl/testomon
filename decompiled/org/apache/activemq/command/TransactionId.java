/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.DataStructure;

public abstract class TransactionId
implements DataStructure {
    public abstract boolean isXATransaction();

    public abstract boolean isLocalTransaction();

    public abstract String getTransactionKey();

    @Override
    public boolean isMarshallAware() {
        return false;
    }
}

