/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.common;

import java.lang.invoke.MethodHandle;
import org.eclipse.jetty.websocket.api.BatchMode;
import org.eclipse.jetty.websocket.api.exceptions.InvalidWebSocketException;
import org.eclipse.jetty.websocket.core.Configuration;
import org.eclipse.jetty.websocket.core.internal.messages.MessageSink;

public class JettyWebSocketFrameHandlerMetadata
extends Configuration.ConfigurationCustomizer {
    private MethodHandle openHandle;
    private MethodHandle closeHandle;
    private MethodHandle errorHandle;
    private MethodHandle frameHandle;
    private MethodHandle textHandle;
    private Class<? extends MessageSink> textSink;
    private MethodHandle binaryHandle;
    private Class<? extends MessageSink> binarySink;
    private MethodHandle pingHandle;
    private MethodHandle pongHandle;
    private BatchMode batchMode = BatchMode.OFF;

    public void setBatchMode(BatchMode batchMode) {
        this.batchMode = batchMode;
    }

    public BatchMode getBatchMode() {
        return this.batchMode;
    }

    public void setBinaryHandle(Class<? extends MessageSink> sinkClass, MethodHandle binary, Object origin) {
        this.assertNotSet(this.binaryHandle, "BINARY Handler", origin);
        this.binaryHandle = binary;
        this.binarySink = sinkClass;
    }

    public MethodHandle getBinaryHandle() {
        return this.binaryHandle;
    }

    public Class<? extends MessageSink> getBinarySink() {
        return this.binarySink;
    }

    public void setCloseHandler(MethodHandle close, Object origin) {
        this.assertNotSet(this.closeHandle, "CLOSE Handler", origin);
        this.closeHandle = close;
    }

    public MethodHandle getCloseHandle() {
        return this.closeHandle;
    }

    public void setErrorHandler(MethodHandle error, Object origin) {
        this.assertNotSet(this.errorHandle, "ERROR Handler", origin);
        this.errorHandle = error;
    }

    public MethodHandle getErrorHandle() {
        return this.errorHandle;
    }

    public void setFrameHandler(MethodHandle frame, Object origin) {
        this.assertNotSet(this.frameHandle, "FRAME Handler", origin);
        this.frameHandle = frame;
    }

    public MethodHandle getFrameHandle() {
        return this.frameHandle;
    }

    public void setOpenHandler(MethodHandle open, Object origin) {
        this.assertNotSet(this.openHandle, "OPEN Handler", origin);
        this.openHandle = open;
    }

    public MethodHandle getOpenHandle() {
        return this.openHandle;
    }

    public void setPingHandle(MethodHandle ping, Object origin) {
        this.assertNotSet(this.pingHandle, "PING Handler", origin);
        this.pingHandle = ping;
    }

    public MethodHandle getPingHandle() {
        return this.pingHandle;
    }

    public void setPongHandle(MethodHandle pong, Object origin) {
        this.assertNotSet(this.pongHandle, "PONG Handler", origin);
        this.pongHandle = pong;
    }

    public MethodHandle getPongHandle() {
        return this.pongHandle;
    }

    public void setTextHandler(Class<? extends MessageSink> sinkClass, MethodHandle text, Object origin) {
        this.assertNotSet(this.textHandle, "TEXT Handler", origin);
        this.textHandle = text;
        this.textSink = sinkClass;
    }

    public MethodHandle getTextHandle() {
        return this.textHandle;
    }

    public Class<? extends MessageSink> getTextSink() {
        return this.textSink;
    }

    private void assertNotSet(Object val, String role, Object origin) {
        if (val == null) {
            return;
        }
        StringBuilder err = new StringBuilder();
        err.append("Cannot replace previously assigned [");
        err.append(role);
        err.append("] at ").append(this.describeOrigin(val));
        err.append(" with ");
        err.append(this.describeOrigin(origin));
        throw new InvalidWebSocketException(err.toString());
    }

    private String describeOrigin(Object obj) {
        if (obj == null) {
            return "<undefined>";
        }
        return obj.toString();
    }
}

