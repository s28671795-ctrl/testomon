/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.channel.unix;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.channel.unix.Limits;
import io.grpc.netty.shaded.io.netty.util.internal.PlatformDependent;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public final class UnixChannelUtil {
    private UnixChannelUtil() {
    }

    public static boolean isBufferCopyNeededForWrite(ByteBuf byteBuf) {
        return UnixChannelUtil.isBufferCopyNeededForWrite(byteBuf, Limits.IOV_MAX);
    }

    static boolean isBufferCopyNeededForWrite(ByteBuf byteBuf, int iovMax) {
        return !byteBuf.hasMemoryAddress() && (!byteBuf.isDirect() || byteBuf.nioBufferCount() > iovMax);
    }

    public static InetSocketAddress computeRemoteAddr(InetSocketAddress remoteAddr, InetSocketAddress osRemoteAddr) {
        if (osRemoteAddr != null) {
            if (PlatformDependent.javaVersion() >= 7) {
                try {
                    return new InetSocketAddress(InetAddress.getByAddress(remoteAddr.getHostString(), osRemoteAddr.getAddress().getAddress()), osRemoteAddr.getPort());
                }
                catch (UnknownHostException unknownHostException) {
                    // empty catch block
                }
            }
            return osRemoteAddr;
        }
        return remoteAddr;
    }
}

