/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jboss.marshalling.Unmarshaller
 */
package io.grpc.netty.shaded.io.netty.handler.codec.marshalling;

import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import org.jboss.marshalling.Unmarshaller;

public interface UnmarshallerProvider {
    public Unmarshaller getUnmarshaller(ChannelHandlerContext var1) throws Exception;
}

