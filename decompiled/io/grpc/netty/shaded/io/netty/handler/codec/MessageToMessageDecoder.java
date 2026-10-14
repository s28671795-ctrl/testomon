/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec;

import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.channel.ChannelInboundHandlerAdapter;
import io.grpc.netty.shaded.io.netty.handler.codec.CodecOutputList;
import io.grpc.netty.shaded.io.netty.handler.codec.DecoderException;
import io.grpc.netty.shaded.io.netty.util.ReferenceCountUtil;
import io.grpc.netty.shaded.io.netty.util.internal.TypeParameterMatcher;
import java.util.List;

public abstract class MessageToMessageDecoder<I>
extends ChannelInboundHandlerAdapter {
    private final TypeParameterMatcher matcher;

    protected MessageToMessageDecoder() {
        this.matcher = TypeParameterMatcher.find(this, MessageToMessageDecoder.class, "I");
    }

    protected MessageToMessageDecoder(Class<? extends I> inboundMessageType) {
        this.matcher = TypeParameterMatcher.get(inboundMessageType);
    }

    public boolean acceptInboundMessage(Object msg) throws Exception {
        return this.matcher.match(msg);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        block16: {
            CodecOutputList out = CodecOutputList.newInstance();
            try {
                if (this.acceptInboundMessage(msg)) {
                    Object cast = msg;
                    try {
                        this.decode(ctx, cast, out);
                        break block16;
                    }
                    finally {
                        ReferenceCountUtil.release(cast);
                    }
                }
                out.add(msg);
            }
            catch (DecoderException e) {
                throw e;
            }
            catch (Exception e) {
                throw new DecoderException(e);
            }
            finally {
                try {
                    int size = out.size();
                    for (int i = 0; i < size; ++i) {
                        ctx.fireChannelRead(out.getUnsafe(i));
                    }
                }
                finally {
                    out.recycle();
                }
            }
        }
    }

    protected abstract void decode(ChannelHandlerContext var1, I var2, List<Object> var3) throws Exception;
}

