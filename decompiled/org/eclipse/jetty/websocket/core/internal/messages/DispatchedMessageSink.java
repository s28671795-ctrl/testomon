/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal.messages;

import java.io.Closeable;
import java.lang.invoke.MethodHandle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.internal.messages.AbstractMessageSink;
import org.eclipse.jetty.websocket.core.internal.messages.MessageSink;

public abstract class DispatchedMessageSink
extends AbstractMessageSink {
    private CompletableFuture<Void> dispatchComplete;
    private MessageSink typeSink;
    private final Executor executor;

    public DispatchedMessageSink(CoreSession session, MethodHandle methodHandle) {
        super(session, methodHandle);
        this.executor = session.getWebSocketComponents().getExecutor();
    }

    public abstract MessageSink newSink(Frame var1);

    @Override
    public void accept(Frame frame, Callback callback) {
        Callback frameCallback;
        if (this.typeSink == null) {
            this.typeSink = this.newSink(frame);
            this.dispatchComplete = new CompletableFuture();
            this.executor.execute(() -> {
                try {
                    this.methodHandle.invoke(this.typeSink);
                    if (this.typeSink instanceof Closeable) {
                        IO.close((Closeable)((Object)this.typeSink));
                    }
                    this.dispatchComplete.complete(null);
                }
                catch (Throwable throwable) {
                    if (this.typeSink instanceof Closeable) {
                        IO.close((Closeable)((Object)this.typeSink));
                    }
                    this.dispatchComplete.completeExceptionally(throwable);
                }
            });
        }
        if (frame.isFin()) {
            Callback.Completable finComplete;
            frameCallback = finComplete = Callback.Completable.from(callback);
            CompletableFuture.allOf(this.dispatchComplete, finComplete).whenComplete((aVoid, throwable) -> {
                this.typeSink = null;
                this.dispatchComplete = null;
                if (throwable == null) {
                    this.session.demand(1L);
                }
            });
        } else {
            frameCallback = new Callback.Nested(callback){

                @Override
                public void succeeded() {
                    super.succeeded();
                    DispatchedMessageSink.this.session.demand(1L);
                }
            };
        }
        this.typeSink.accept(frame, frameCallback);
    }

    @Override
    public void fail(Throwable failure) {
        if (this.typeSink != null) {
            this.typeSink.fail(failure);
        }
    }
}

