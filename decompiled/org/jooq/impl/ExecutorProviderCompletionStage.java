/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jooq.ExecutorProvider;

final class ExecutorProviderCompletionStage<T>
implements CompletionStage<T> {
    private final CompletionStage<T> delegate;
    private final ExecutorProvider provider;

    static final <T> ExecutorProviderCompletionStage<T> of(CompletionStage<T> delegate, ExecutorProvider provider) {
        return new ExecutorProviderCompletionStage<T>(delegate, provider);
    }

    ExecutorProviderCompletionStage(CompletionStage<T> delegate, ExecutorProvider provider) {
        this.delegate = delegate;
        this.provider = provider;
    }

    @Override
    public final <U> CompletionStage<U> thenApply(Function<? super T, ? extends U> fn) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenApply(fn), this.provider);
    }

    @Override
    public final <U> CompletionStage<U> thenApplyAsync(Function<? super T, ? extends U> fn) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenApplyAsync(fn, this.provider.provide()), this.provider);
    }

    @Override
    public final <U> CompletionStage<U> thenApplyAsync(Function<? super T, ? extends U> fn, Executor executor2) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenApplyAsync(fn, executor2), this.provider);
    }

    @Override
    public final CompletionStage<Void> thenAccept(Consumer<? super T> action) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenAccept(action), this.provider);
    }

    @Override
    public final CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenAcceptAsync(action, this.provider.provide()), this.provider);
    }

    @Override
    public final CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action, Executor executor2) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenAcceptAsync(action, executor2), this.provider);
    }

    @Override
    public final CompletionStage<Void> thenRun(Runnable action) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenRun(action), this.provider);
    }

    @Override
    public final CompletionStage<Void> thenRunAsync(Runnable action) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenRunAsync(action, this.provider.provide()), this.provider);
    }

    @Override
    public final CompletionStage<Void> thenRunAsync(Runnable action, Executor executor2) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenRunAsync(action, executor2), this.provider);
    }

    @Override
    public final <U, V> CompletionStage<V> thenCombine(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenCombine(other, fn), this.provider);
    }

    @Override
    public final <U, V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenCombineAsync(other, fn, this.provider.provide()), this.provider);
    }

    @Override
    public final <U, V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn, Executor executor2) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenCombineAsync(other, fn, executor2), this.provider);
    }

    @Override
    public final <U> CompletionStage<Void> thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenAcceptBoth(other, action), this.provider);
    }

    @Override
    public final <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenAcceptBothAsync(other, action, this.provider.provide()), this.provider);
    }

    @Override
    public final <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action, Executor executor2) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenAcceptBothAsync(other, action, executor2), this.provider);
    }

    @Override
    public final CompletionStage<Void> runAfterBoth(CompletionStage<?> other, Runnable action) {
        return ExecutorProviderCompletionStage.of(this.delegate.runAfterBoth(other, action), this.provider);
    }

    @Override
    public final CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action) {
        return ExecutorProviderCompletionStage.of(this.delegate.runAfterBothAsync(other, action, this.provider.provide()), this.provider);
    }

    @Override
    public final CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action, Executor executor2) {
        return ExecutorProviderCompletionStage.of(this.delegate.runAfterBothAsync(other, action, executor2), this.provider);
    }

    @Override
    public final <U> CompletionStage<U> applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return ExecutorProviderCompletionStage.of(this.delegate.applyToEither(other, fn), this.provider);
    }

    @Override
    public final <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return ExecutorProviderCompletionStage.of(this.delegate.applyToEitherAsync(other, fn, this.provider.provide()), this.provider);
    }

    @Override
    public final <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn, Executor executor2) {
        return ExecutorProviderCompletionStage.of(this.delegate.applyToEitherAsync(other, fn, executor2), this.provider);
    }

    @Override
    public final CompletionStage<Void> acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return ExecutorProviderCompletionStage.of(this.delegate.acceptEither(other, action), this.provider);
    }

    @Override
    public final CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return ExecutorProviderCompletionStage.of(this.delegate.acceptEitherAsync(other, action, this.provider.provide()), this.provider);
    }

    @Override
    public final CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action, Executor executor2) {
        return ExecutorProviderCompletionStage.of(this.delegate.acceptEitherAsync(other, action, executor2), this.provider);
    }

    @Override
    public final CompletionStage<Void> runAfterEither(CompletionStage<?> other, Runnable action) {
        return ExecutorProviderCompletionStage.of(this.delegate.runAfterEither(other, action), this.provider);
    }

    @Override
    public final CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action) {
        return ExecutorProviderCompletionStage.of(this.delegate.runAfterEitherAsync(other, action, this.provider.provide()), this.provider);
    }

    @Override
    public final CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action, Executor executor2) {
        return ExecutorProviderCompletionStage.of(this.delegate.runAfterEitherAsync(other, action, executor2), this.provider);
    }

    @Override
    public final <U> CompletionStage<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenCompose(fn), this.provider);
    }

    @Override
    public final <U> CompletionStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenComposeAsync(fn, this.provider.provide()), this.provider);
    }

    @Override
    public final <U> CompletionStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn, Executor executor2) {
        return ExecutorProviderCompletionStage.of(this.delegate.thenComposeAsync(fn, executor2), this.provider);
    }

    @Override
    public final CompletionStage<T> exceptionally(Function<Throwable, ? extends T> fn) {
        return ExecutorProviderCompletionStage.of(this.delegate.exceptionally(fn), this.provider);
    }

    @Override
    public final CompletionStage<T> whenComplete(BiConsumer<? super T, ? super Throwable> action) {
        return ExecutorProviderCompletionStage.of(this.delegate.whenComplete(action), this.provider);
    }

    @Override
    public final CompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action) {
        return ExecutorProviderCompletionStage.of(this.delegate.whenCompleteAsync(action, this.provider.provide()), this.provider);
    }

    @Override
    public final CompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action, Executor executor2) {
        return ExecutorProviderCompletionStage.of(this.delegate.whenCompleteAsync(action, executor2), this.provider);
    }

    @Override
    public final <U> CompletionStage<U> handle(BiFunction<? super T, Throwable, ? extends U> fn) {
        return ExecutorProviderCompletionStage.of(this.delegate.handle(fn), this.provider);
    }

    @Override
    public final <U> CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn) {
        return ExecutorProviderCompletionStage.of(this.delegate.handleAsync(fn, this.provider.provide()), this.provider);
    }

    @Override
    public final <U> CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn, Executor executor2) {
        return ExecutorProviderCompletionStage.of(this.delegate.handleAsync(fn, executor2), this.provider);
    }

    @Override
    public final CompletableFuture<T> toCompletableFuture() {
        return this.delegate.toCompletableFuture();
    }
}

