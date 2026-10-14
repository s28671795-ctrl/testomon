/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.function.Consumer;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.IteratingCallback;
import org.eclipse.jetty.util.IteratingNestedCallback;
import org.eclipse.jetty.util.Utf8Appendable;
import org.eclipse.jetty.util.Utf8StringBuilder;
import org.eclipse.jetty.websocket.core.CloseStatus;
import org.eclipse.jetty.websocket.core.CoreSession;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.FrameHandler;
import org.eclipse.jetty.websocket.core.exception.BadPayloadException;
import org.eclipse.jetty.websocket.core.exception.MessageTooLargeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageHandler
implements FrameHandler {
    private static final Logger LOG = LoggerFactory.getLogger(MessageHandler.class);
    private CoreSession coreSession;
    private Utf8StringBuilder textMessageBuffer;
    private ByteArrayOutputStream binaryMessageBuffer;
    private byte dataType = (byte)-1;

    public static MessageHandler from(final Consumer<String> onText, final Consumer<ByteBuffer> onBinary) {
        return new MessageHandler(){

            @Override
            protected void onText(String message, Callback callback) {
                if (onText == null) {
                    super.onText(message, callback);
                    return;
                }
                try {
                    onText.accept(message);
                    callback.succeeded();
                }
                catch (Throwable th) {
                    callback.failed(th);
                }
            }

            @Override
            protected void onBinary(ByteBuffer message, Callback callback) {
                if (onBinary == null) {
                    super.onBinary(message, callback);
                    return;
                }
                try {
                    onBinary.accept(message);
                    callback.succeeded();
                }
                catch (Throwable th) {
                    callback.failed(th);
                }
            }
        };
    }

    public CoreSession getCoreSession() {
        return this.coreSession;
    }

    private Utf8StringBuilder getTextMessageBuffer() {
        if (this.textMessageBuffer == null) {
            this.textMessageBuffer = new Utf8StringBuilder();
        }
        return this.textMessageBuffer;
    }

    private ByteArrayOutputStream getBinaryMessageBuffer() {
        if (this.binaryMessageBuffer == null) {
            this.binaryMessageBuffer = new ByteArrayOutputStream();
        }
        return this.binaryMessageBuffer;
    }

    @Override
    public void onOpen(CoreSession coreSession, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onOpen {}", (Object)coreSession);
        }
        this.coreSession = coreSession;
        callback.succeeded();
    }

    @Override
    public void onFrame(Frame frame, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onFrame {}", (Object)frame);
        }
        switch (frame.getOpCode()) {
            case 8: {
                this.onCloseFrame(frame, callback);
                break;
            }
            case 9: {
                this.onPingFrame(frame, callback);
                break;
            }
            case 10: {
                this.onPongFrame(frame, callback);
                break;
            }
            case 1: {
                this.dataType = 1;
                this.onTextFrame(frame, callback);
                break;
            }
            case 2: {
                this.dataType = (byte)2;
                this.onBinaryFrame(frame, callback);
                break;
            }
            case 0: {
                this.onContinuationFrame(frame, callback);
                if (!frame.isFin()) break;
                this.dataType = (byte)-1;
                break;
            }
            default: {
                callback.failed(new IllegalStateException());
            }
        }
    }

    @Override
    public void onError(Throwable cause, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onError ", cause);
        }
        callback.succeeded();
    }

    @Override
    public void onClosed(CloseStatus closeStatus, Callback callback) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("onClosed {}", (Object)closeStatus);
        }
        if (this.textMessageBuffer != null) {
            this.textMessageBuffer.reset();
            this.textMessageBuffer = null;
        }
        if (this.binaryMessageBuffer != null) {
            this.binaryMessageBuffer.reset();
            this.binaryMessageBuffer = null;
        }
        callback.succeeded();
    }

    protected void onTextFrame(Frame frame, Callback callback) {
        try {
            Utf8StringBuilder textBuffer = this.getTextMessageBuffer();
            if (frame.hasPayload()) {
                long maxSize = this.coreSession.getMaxTextMessageSize();
                long currentSize = frame.getPayload().remaining() + textBuffer.length();
                if (currentSize > maxSize) {
                    throw new MessageTooLargeException("Message larger than " + maxSize + " bytes");
                }
                textBuffer.append(frame.getPayload());
            }
            if (frame.isFin()) {
                this.onText(textBuffer.toString(), callback);
                textBuffer.reset();
            } else {
                callback.succeeded();
            }
        }
        catch (Utf8Appendable.NotUtf8Exception e) {
            callback.failed(new BadPayloadException(e));
        }
        catch (Throwable t2) {
            callback.failed(t2);
        }
    }

    protected void onBinaryFrame(Frame frame, Callback callback) {
        try {
            ByteArrayOutputStream binaryBuffer = this.getBinaryMessageBuffer();
            if (frame.hasPayload()) {
                long maxSize = this.coreSession.getMaxBinaryMessageSize();
                long currentSize = frame.getPayload().remaining() + binaryBuffer.size();
                if (currentSize > maxSize) {
                    throw new MessageTooLargeException("Message larger than " + maxSize + " bytes");
                }
                BufferUtil.writeTo(frame.getPayload(), binaryBuffer);
            }
            if (frame.isFin()) {
                this.onBinary(BufferUtil.toBuffer(binaryBuffer.toByteArray()), callback);
                binaryBuffer.reset();
            } else {
                callback.succeeded();
            }
        }
        catch (Throwable t2) {
            callback.failed(t2);
        }
    }

    protected void onContinuationFrame(Frame frame, Callback callback) {
        switch (this.dataType) {
            case 2: {
                this.onBinaryFrame(frame, callback);
                break;
            }
            case 1: {
                this.onTextFrame(frame, callback);
                break;
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }

    protected void onPingFrame(Frame frame, Callback callback) {
        this.coreSession.sendFrame(new Frame(10, true, frame.getPayload()), callback, false);
    }

    protected void onPongFrame(Frame frame, Callback callback) {
        callback.succeeded();
    }

    protected void onCloseFrame(Frame frame, Callback callback) {
        callback.succeeded();
    }

    protected void onText(String message, Callback callback) {
        callback.failed(new BadPayloadException("Text Not Accepted"));
    }

    protected void onBinary(ByteBuffer message, Callback callback) {
        callback.failed(new BadPayloadException("Binary Not Accepted"));
    }

    public void sendText(String message, Callback callback, boolean batch) {
        this.getCoreSession().sendFrame(new Frame(1, true, message), callback, batch);
    }

    public void sendText(Callback callback, final boolean batch, final String ... parts) {
        if (parts == null || parts.length == 0) {
            callback.succeeded();
            return;
        }
        if (parts.length == 1) {
            this.sendText(parts[0], callback, batch);
            return;
        }
        new IteratingNestedCallback(callback){
            int i;
            {
                super(arg0);
                this.i = 0;
            }

            @Override
            protected IteratingCallback.Action process() throws Throwable {
                if (this.i + 1 > parts.length) {
                    return IteratingCallback.Action.SUCCEEDED;
                }
                String part = parts[this.i++];
                MessageHandler.this.getCoreSession().sendFrame(new Frame(this.i == 1 ? (byte)1 : 0, this.i == parts.length, part), this, batch);
                return IteratingCallback.Action.SCHEDULED;
            }
        }.iterate();
    }

    public void sendBinary(ByteBuffer message, Callback callback, boolean batch) {
        this.getCoreSession().sendFrame(new Frame(2, true, message), callback, batch);
    }

    public void sendBinary(Callback callback, final boolean batch, final ByteBuffer ... parts) {
        if (parts == null || parts.length == 0) {
            callback.succeeded();
            return;
        }
        if (parts.length == 1) {
            this.sendBinary(parts[0], callback, batch);
            return;
        }
        new IteratingNestedCallback(callback){
            int i;
            {
                super(arg0);
                this.i = 0;
            }

            @Override
            protected IteratingCallback.Action process() throws Throwable {
                if (this.i + 1 > parts.length) {
                    return IteratingCallback.Action.SUCCEEDED;
                }
                ByteBuffer part = parts[this.i++];
                MessageHandler.this.getCoreSession().sendFrame(new Frame(this.i == 1 ? (byte)2 : 0, this.i == parts.length, part), this, batch);
                return IteratingCallback.Action.SCHEDULED;
            }
        }.iterate();
    }
}

