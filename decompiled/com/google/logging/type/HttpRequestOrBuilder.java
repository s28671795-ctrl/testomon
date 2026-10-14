/*
 * Decompiled with CFR 0.152.
 */
package com.google.logging.type;

import com.google.protobuf.ByteString;
import com.google.protobuf.Duration;
import com.google.protobuf.DurationOrBuilder;
import com.google.protobuf.MessageOrBuilder;

public interface HttpRequestOrBuilder
extends MessageOrBuilder {
    public String getRequestMethod();

    public ByteString getRequestMethodBytes();

    public String getRequestUrl();

    public ByteString getRequestUrlBytes();

    public long getRequestSize();

    public int getStatus();

    public long getResponseSize();

    public String getUserAgent();

    public ByteString getUserAgentBytes();

    public String getRemoteIp();

    public ByteString getRemoteIpBytes();

    public String getServerIp();

    public ByteString getServerIpBytes();

    public String getReferer();

    public ByteString getRefererBytes();

    public boolean hasLatency();

    public Duration getLatency();

    public DurationOrBuilder getLatencyOrBuilder();

    public boolean getCacheLookup();

    public boolean getCacheHit();

    public boolean getCacheValidatedWithOriginServer();

    public long getCacheFillBytes();

    public String getProtocol();

    public ByteString getProtocolBytes();
}

