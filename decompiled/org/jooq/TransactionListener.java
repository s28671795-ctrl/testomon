/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.function.Consumer;
import org.jooq.TransactionContext;
import org.jooq.impl.CallbackTransactionListener;

public interface TransactionListener {
    default public void beginStart(TransactionContext ctx) {
    }

    default public void beginEnd(TransactionContext ctx) {
    }

    default public void commitStart(TransactionContext ctx) {
    }

    default public void commitEnd(TransactionContext ctx) {
    }

    default public void rollbackStart(TransactionContext ctx) {
    }

    default public void rollbackEnd(TransactionContext ctx) {
    }

    public static CallbackTransactionListener onBeginStart(Consumer<? super TransactionContext> onBeginStart) {
        return new CallbackTransactionListener().onBeginStart(onBeginStart);
    }

    public static CallbackTransactionListener onBeginEnd(Consumer<? super TransactionContext> onBeginEnd) {
        return new CallbackTransactionListener().onBeginEnd(onBeginEnd);
    }

    public static CallbackTransactionListener onCommitStart(Consumer<? super TransactionContext> onCommitStart) {
        return new CallbackTransactionListener().onCommitStart(onCommitStart);
    }

    public static CallbackTransactionListener onCommitEnd(Consumer<? super TransactionContext> onCommitEnd) {
        return new CallbackTransactionListener().onCommitEnd(onCommitEnd);
    }

    public static CallbackTransactionListener onRollbackStart(Consumer<? super TransactionContext> onRollbackStart) {
        return new CallbackTransactionListener().onRollbackStart(onRollbackStart);
    }

    public static CallbackTransactionListener onRollbackEnd(Consumer<? super TransactionContext> onRollbackEnd) {
        return new CallbackTransactionListener().onRollbackEnd(onRollbackEnd);
    }
}

