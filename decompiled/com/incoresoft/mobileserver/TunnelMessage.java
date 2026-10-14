/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.mobileserver;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public record TunnelMessage(@JsonProperty(value="type") Type type, @JsonProperty(value="request_id") String requestId, @JsonProperty(value="agent_id") String agentId, @JsonProperty(value="method") String method, @JsonProperty(value="path") String path, @JsonProperty(value="headers") Map<String, String> headers, @JsonProperty(value="body") byte[] body, @JsonProperty(value="status_code") Integer statusCode, @JsonProperty(value="ws_message_type") String wsMessageType, @JsonProperty(value="error") String error) {
    public static TunnelMessage register(String agentId) {
        return new TunnelMessage(Type.REGISTER, null, agentId, null, null, null, null, null, null, null);
    }

    public static TunnelMessage request(String requestId, String agentId, String method2, String path, Map<String, String> headers, byte[] body2) {
        return new TunnelMessage(Type.REQUEST, requestId, agentId, method2, path, headers, body2, null, null, null);
    }

    public static TunnelMessage response(String requestId, int statusCode, Map<String, String> headers, byte[] body2) {
        return new TunnelMessage(Type.RESPONSE, requestId, null, null, null, headers, body2, statusCode, null, null);
    }

    public static TunnelMessage streamChunk(String requestId, byte[] data) {
        return new TunnelMessage(Type.STREAM_CHUNK, requestId, null, null, null, null, data, null, null, null);
    }

    public static TunnelMessage streamEnd(String requestId) {
        return new TunnelMessage(Type.STREAM_END, requestId, null, null, null, null, null, null, null, null);
    }

    public static TunnelMessage wsConnect(String requestId, String agentId, String path, Map<String, String> headers) {
        return new TunnelMessage(Type.WS_CONNECT, requestId, agentId, null, path, headers, null, null, null, null);
    }

    public static TunnelMessage wsMessage(String requestId, String messageType, byte[] data) {
        return new TunnelMessage(Type.WS_MESSAGE, requestId, null, null, null, null, data, null, messageType, null);
    }

    public static TunnelMessage wsClose(String requestId) {
        return new TunnelMessage(Type.WS_CLOSE, requestId, null, null, null, null, null, null, null, null);
    }

    public static TunnelMessage error(String requestId, String error) {
        return new TunnelMessage(Type.ERROR, requestId, null, null, null, null, null, null, null, error);
    }

    public static enum Type {
        REGISTER,
        REQUEST,
        RESPONSE,
        STREAM_CHUNK,
        STREAM_END,
        WS_CONNECT,
        WS_MESSAGE,
        WS_CLOSE,
        ERROR;

    }
}

