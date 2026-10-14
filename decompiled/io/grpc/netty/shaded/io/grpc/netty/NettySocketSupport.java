/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import io.grpc.InternalChannelz;
import io.grpc.netty.shaded.io.netty.channel.Channel;
import java.util.Map;
import javax.annotation.Nullable;

final class NettySocketSupport {
    private static volatile Helper instance = new NettySocketHelperImpl();

    NettySocketSupport() {
    }

    public static NativeSocketOptions getNativeSocketOptions(Channel ch) {
        return instance.getNativeSocketOptions(ch);
    }

    static void setHelper(Helper helper) {
        instance = Preconditions.checkNotNull(helper);
    }

    private static final class NettySocketHelperImpl
    implements Helper {
        private NettySocketHelperImpl() {
        }

        @Override
        public NativeSocketOptions getNativeSocketOptions(Channel ch) {
            return null;
        }
    }

    public static class NativeSocketOptions {
        @Nullable
        public final InternalChannelz.TcpInfo tcpInfo;
        public final ImmutableMap<String, String> otherInfo;

        public NativeSocketOptions(InternalChannelz.TcpInfo tcpInfo, Map<String, String> otherInfo) {
            Preconditions.checkNotNull(otherInfo);
            this.tcpInfo = tcpInfo;
            this.otherInfo = ImmutableMap.copyOf(otherInfo);
        }
    }

    static interface Helper {
        @Nullable
        public NativeSocketOptions getNativeSocketOptions(Channel var1);
    }
}

