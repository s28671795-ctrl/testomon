/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal.messages;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.nio.ByteBuffer;
import java.util.Objects;
import org.eclipse.jetty.io.ByteBufferCallbackAccumulator;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.exception.InvalidSignatureException;
import org.eclipse.jetty.websocket.core.exception.MessageTooLargeException;
import org.eclipse.jetty.websocket.core.internal.messages.AbstractMessageSink;

public class ByteBufferMessageSink
extends AbstractMessageSink {
    private ByteBufferCallbackAccumulator out;

    public ByteBufferMessageSink(CoreSession session, MethodHandle methodHandle) {
        super(session, methodHandle);
        Objects.requireNonNull(methodHandle, "MethodHandle");
        MethodType onMessageType = MethodType.methodType(Void.TYPE, ByteBuffer.class);
        if (methodHandle.type() != onMessageType) {
            throw InvalidSignatureException.build(onMessageType, methodHandle.type());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void accept(Frame frame, Callback callback) {
        try {
            long size = (this.out == null ? 0 : this.out.getLength()) + frame.getPayloadLength();
            long maxBinaryMessageSize = this.session.getMaxBinaryMessageSize();
            if (maxBinaryMessageSize > 0L && size > maxBinaryMessageSize) {
                throw new MessageTooLargeException(String.format("Binary message too large: (actual) %,d > (configured max binary message size) %,d", size, maxBinaryMessageSize));
            }
            if (frame.isFin() && this.out == null) {
                if (frame.hasPayload()) {
                    this.methodHandle.invoke(frame.getPayload());
                } else {
                    this.methodHandle.invoke(BufferUtil.EMPTY_BUFFER);
                }
                callback.succeeded();
                this.session.demand(1L);
                return;
            }
            if (frame.hasPayload()) {
                ByteBuffer payload = frame.getPayload();
                if (this.out == null) {
                    this.out = new ByteBufferCallbackAccumulator();
                }
                this.out.addEntry(payload, callback);
            }
            callback = Callback.NOOP;
            if (frame.isFin()) {
                ByteBufferPool bufferPool = this.session.getByteBufferPool();
                ByteBuffer buffer = bufferPool.acquire(this.out.getLength(), false);
                this.out.writeTo(buffer);
                try {
                    this.methodHandle.invoke(buffer);
                }
                finally {
                    bufferPool.release(buffer);
                }
            }
            this.session.demand(1L);
        }
        catch (Throwable t2) {
            if (this.out != null) {
                this.out.fail(t2);
            }
            callback.failed(t2);
        }
        finally {
            if (frame.isFin()) {
                this.out = null;
            }
        }
    }

    @Override
    public void fail(Throwable failure) {
        if (this.out != null) {
            this.out.fail(failure);
        }
    }
}

