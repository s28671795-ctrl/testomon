/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import java.io.IOException;
import org.apache.activemq.Service;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.store.TransactionRecoveryListener;

public interface TransactionStore
extends Service {
    public void prepare(TransactionId var1) throws IOException;

    public void commit(TransactionId var1, boolean var2, Runnable var3, Runnable var4) throws IOException;

    public void rollback(TransactionId var1) throws IOException;

    public void recover(TransactionRecoveryListener var1) throws IOException;
}

