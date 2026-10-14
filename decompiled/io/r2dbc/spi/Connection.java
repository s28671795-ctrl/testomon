/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Batch;
import io.r2dbc.spi.Closeable;
import io.r2dbc.spi.ConnectionMetadata;
import io.r2dbc.spi.IsolationLevel;
import io.r2dbc.spi.Statement;
import io.r2dbc.spi.TransactionDefinition;
import io.r2dbc.spi.ValidationDepth;
import java.time.Duration;
import org.reactivestreams.Publisher;

public interface Connection
extends Closeable {
    public Publisher<Void> beginTransaction();

    public Publisher<Void> beginTransaction(TransactionDefinition var1);

    @Override
    public Publisher<Void> close();

    public Publisher<Void> commitTransaction();

    public Batch createBatch();

    public Publisher<Void> createSavepoint(String var1);

    public Statement createStatement(String var1);

    public boolean isAutoCommit();

    public ConnectionMetadata getMetadata();

    public IsolationLevel getTransactionIsolationLevel();

    public Publisher<Void> releaseSavepoint(String var1);

    public Publisher<Void> rollbackTransaction();

    public Publisher<Void> rollbackTransactionToSavepoint(String var1);

    public Publisher<Void> setAutoCommit(boolean var1);

    public Publisher<Void> setLockWaitTimeout(Duration var1);

    public Publisher<Void> setStatementTimeout(Duration var1);

    public Publisher<Void> setTransactionIsolationLevel(IsolationLevel var1);

    public Publisher<Boolean> validate(ValidationDepth var1);
}

