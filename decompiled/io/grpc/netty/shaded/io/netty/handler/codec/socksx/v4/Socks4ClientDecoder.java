/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.handler.codec.socksx.v4;

import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.ByteBufUtil;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.handler.codec.DecoderException;
import io.grpc.netty.shaded.io.netty.handler.codec.DecoderResult;
import io.grpc.netty.shaded.io.netty.handler.codec.ReplayingDecoder;
import io.grpc.netty.shaded.io.netty.handler.codec.socksx.v4.DefaultSocks4CommandResponse;
import io.grpc.netty.shaded.io.netty.handler.codec.socksx.v4.Socks4CommandStatus;
import io.grpc.netty.shaded.io.netty.util.NetUtil;
import java.util.List;

public class Socks4ClientDecoder
extends ReplayingDecoder<State> {
    public Socks4ClientDecoder() {
        super(State.START);
        this.setSingleDecode(true);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try {
            switch ((State)((Object)this.state())) {
                case START: {
                    short version = in.readUnsignedByte();
                    if (version != 0) {
                        throw new DecoderException("unsupported reply version: " + version + " (expected: 0)");
                    }
                    Socks4CommandStatus status = Socks4CommandStatus.valueOf(in.readByte());
                    int dstPort = ByteBufUtil.readUnsignedShortBE(in);
                    String dstAddr = NetUtil.intToIpAddress(ByteBufUtil.readIntBE(in));
                    out.add(new DefaultSocks4CommandResponse(status, dstAddr, dstPort));
                    this.checkpoint(State.SUCCESS);
                }
                case SUCCESS: {
                    int readableBytes = this.actualReadableBytes();
                    if (readableBytes <= 0) break;
                    out.add(in.readRetainedSlice(readableBytes));
                    break;
                }
                case FAILURE: {
                    in.skipBytes(this.actualReadableBytes());
                }
            }
        }
        catch (Exception e) {
            this.fail(out, e);
        }
    }

    private void fail(List<Object> out, Exception cause) {
        if (!(cause instanceof DecoderException)) {
            cause = new DecoderException(cause);
        }
        DefaultSocks4CommandResponse m4 = new DefaultSocks4CommandResponse(Socks4CommandStatus.REJECTED_OR_FAILED);
        m4.setDecoderResult(DecoderResult.failure(cause));
        out.add(m4);
        this.checkpoint(State.FAILURE);
    }

    public static enum State {
        START,
        SUCCESS,
        FAILURE;

    }
}

