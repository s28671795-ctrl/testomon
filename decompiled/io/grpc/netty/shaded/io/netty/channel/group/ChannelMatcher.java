/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.group;

import io.grpc.netty.shaded.io.netty.channel.Channel;

public interface ChannelMatcher {
    public boolean matches(Channel var1);
}

