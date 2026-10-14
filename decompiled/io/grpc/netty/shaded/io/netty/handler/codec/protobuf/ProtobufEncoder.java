/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.protobuf;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import io.grpc.netty.shaded.io.netty.buffer.Unpooled;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandler;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

@ChannelHandler.Sharable
public class ProtobufEncoder
extends MessageToMessageEncoder<MessageLiteOrBuilder> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List<Object> out) throws Exception {
        if (msg instanceof MessageLite) {
            out.add(Unpooled.wrappedBuffer(((MessageLite)msg).toByteArray()));
            return;
        }
        if (msg instanceof MessageLite.Builder) {
            out.add(Unpooled.wrappedBuffer(((MessageLite.Builder)msg).build().toByteArray()));
        }
    }
}

