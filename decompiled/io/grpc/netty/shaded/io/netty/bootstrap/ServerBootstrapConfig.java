/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.bootstrap;

import io.grpc.netty.shaded.io.netty.bootstrap.AbstractBootstrapConfig;
import io.grpc.netty.shaded.io.netty.bootstrap.ServerBootstrap;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandler;
import io.grpc.netty.shaded.io.netty.channel.ChannelOption;
import io.grpc.netty.shaded.io.netty.channel.EventLoopGroup;
import io.grpc.netty.shaded.io.netty.channel.ServerChannel;
import io.grpc.netty.shaded.io.netty.util.AttributeKey;
import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import java.util.Map;

public final class ServerBootstrapConfig
extends AbstractBootstrapConfig<ServerBootstrap, ServerChannel> {
    ServerBootstrapConfig(ServerBootstrap bootstrap) {
        super(bootstrap);
    }

    public EventLoopGroup childGroup() {
        return ((ServerBootstrap)this.bootstrap).childGroup();
    }

    public ChannelHandler childHandler() {
        return ((ServerBootstrap)this.bootstrap).childHandler();
    }

    public Map<ChannelOption<?>, Object> childOptions() {
        return ((ServerBootstrap)this.bootstrap).childOptions();
    }

    public Map<AttributeKey<?>, Object> childAttrs() {
        return ((ServerBootstrap)this.bootstrap).childAttrs();
    }

    @Override
    public String toString() {
        ChannelHandler childHandler;
        Map<AttributeKey<?>, Object> childAttrs;
        Map<ChannelOption<?>, Object> childOptions;
        StringBuilder buf = new StringBuilder(super.toString());
        buf.setLength(buf.length() - 1);
        buf.append(", ");
        EventLoopGroup childGroup = this.childGroup();
        if (childGroup != null) {
            buf.append("childGroup: ");
            buf.append(StringUtil.simpleClassName(childGroup));
            buf.append(", ");
        }
        if (!(childOptions = this.childOptions()).isEmpty()) {
            buf.append("childOptions: ");
            buf.append(childOptions);
            buf.append(", ");
        }
        if (!(childAttrs = this.childAttrs()).isEmpty()) {
            buf.append("childAttrs: ");
            buf.append(childAttrs);
            buf.append(", ");
        }
        if ((childHandler = this.childHandler()) != null) {
            buf.append("childHandler: ");
            buf.append(childHandler);
            buf.append(", ");
        }
        if (buf.charAt(buf.length() - 1) == '(') {
            buf.append(')');
        } else {
            buf.setCharAt(buf.length() - 2, ')');
            buf.setLength(buf.length() - 1);
        }
        return buf.toString();
    }
}

