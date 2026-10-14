/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import org.apache.activemq.command.TransactionId;

public interface TransactionIdTransformer {
    public TransactionId transform(TransactionId var1);
}

