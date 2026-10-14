/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.util.IntrospectionSupport;

public class JournalTransaction
implements DataStructure {
    public static final byte DATA_STRUCTURE_TYPE = 54;
    public static final byte XA_PREPARE = 1;
    public static final byte XA_COMMIT = 2;
    public static final byte XA_ROLLBACK = 3;
    public static final byte LOCAL_COMMIT = 4;
    public static final byte LOCAL_ROLLBACK = 5;
    public byte type;
    public boolean wasPrepared;
    public TransactionId transactionId;

    public JournalTransaction(byte type, TransactionId transactionId, boolean wasPrepared) {
        this.type = type;
        this.transactionId = transactionId;
        this.wasPrepared = wasPrepared;
    }

    public JournalTransaction() {
    }

    @Override
    public byte getDataStructureType() {
        return 54;
    }

    public TransactionId getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(TransactionId transactionId) {
        this.transactionId = transactionId;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public boolean getWasPrepared() {
        return this.wasPrepared;
    }

    public void setWasPrepared(boolean wasPrepared) {
        this.wasPrepared = wasPrepared;
    }

    @Override
    public boolean isMarshallAware() {
        return false;
    }

    public String toString() {
        return IntrospectionSupport.toString(this, JournalTransaction.class);
    }
}

