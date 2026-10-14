/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core;

import java.net.SocketAddress;
import java.net.URI;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.websocket.core.Behavior;
import org.eclipse.jetty.websocket.core.Configuration;
import org.eclipse.jetty.websocket.core.ExtensionConfig;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.OutgoingFrames;
import org.eclipse.jetty.websocket.core.WebSocketComponents;

public interface CoreSession
extends OutgoingFrames,
Configuration {
    public String getNegotiatedSubProtocol();

    public List<ExtensionConfig> getNegotiatedExtensions();

    public Map<String, List<String>> getParameterMap();

    public String getProtocolVersion();

    public URI getRequestURI();

    public boolean isSecure();

    public Behavior getBehavior();

    public WebSocketComponents getWebSocketComponents();

    public ByteBufferPool getByteBufferPool();

    public SocketAddress getLocalAddress();

    public SocketAddress getRemoteAddress();

    public boolean isInputOpen();

    public boolean isOutputOpen();

    public void flush(Callback var1);

    public void close(Callback var1);

    public void close(int var1, String var2, Callback var3);

    public void abort();

    public void demand(long var1);

    public boolean isRsv1Used();

    public boolean isRsv2Used();

    public boolean isRsv3Used();

    public static class Empty
    extends Configuration.ConfigurationCustomizer
    implements CoreSession {
        @Override
        public String getNegotiatedSubProtocol() {
            return null;
        }

        @Override
        public List<ExtensionConfig> getNegotiatedExtensions() {
            return null;
        }

        @Override
        public Map<String, List<String>> getParameterMap() {
            return null;
        }

        @Override
        public String getProtocolVersion() {
            return null;
        }

        @Override
        public URI getRequestURI() {
            return null;
        }

        @Override
        public boolean isSecure() {
            return false;
        }

        @Override
        public void abort() {
        }

        @Override
        public Behavior getBehavior() {
            return null;
        }

        @Override
        public WebSocketComponents getWebSocketComponents() {
            return null;
        }

        @Override
        public ByteBufferPool getByteBufferPool() {
            return null;
        }

        @Override
        public SocketAddress getLocalAddress() {
            return null;
        }

        @Override
        public SocketAddress getRemoteAddress() {
            return null;
        }

        @Override
        public boolean isInputOpen() {
            return true;
        }

        @Override
        public boolean isOutputOpen() {
            return true;
        }

        @Override
        public void flush(Callback callback) {
            callback.succeeded();
        }

        @Override
        public void close(Callback callback) {
            callback.succeeded();
        }

        @Override
        public void close(int statusCode, String reason, Callback callback) {
            callback.succeeded();
        }

        @Override
        public void demand(long n) {
        }

        @Override
        public void sendFrame(Frame frame, Callback callback, boolean batch) {
            callback.succeeded();
        }

        @Override
        public boolean isRsv1Used() {
            return false;
        }

        @Override
        public boolean isRsv2Used() {
            return false;
        }

        @Override
        public boolean isRsv3Used() {
            return false;
        }
    }
}

