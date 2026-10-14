/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.http;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpMessage;
import io.grpc.netty.shaded.io.netty.handler.codec.http.LastHttpContent;

public interface FullHttpMessage
extends HttpMessage,
LastHttpContent {
    @Override
    public FullHttpMessage copy();

    @Override
    public FullHttpMessage duplicate();

    @Override
    public FullHttpMessage retainedDuplicate();

    @Override
    public FullHttpMessage replace(ByteBuf var1);

    @Override
    public FullHttpMessage retain(int var1);

    @Override
    public FullHttpMessage retain();

    @Override
    public FullHttpMessage touch();

    @Override
    public FullHttpMessage touch(Object var1);
}

