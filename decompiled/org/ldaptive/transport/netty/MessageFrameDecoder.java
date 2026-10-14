/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.UniversalDERTag;
import org.ldaptive.transport.netty.NettyConnection;
import org.ldaptive.transport.netty.NettyDERBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageFrameDecoder
extends ByteToMessageDecoder {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        this.logger.trace("decoding {} bytes from {} on {}", in.readableBytes(), in, ctx);
        if (in.readableBytes() <= 2) {
            return;
        }
        int readerIdx = in.readerIndex();
        int writerIdx = in.writerIndex();
        int len = 0;
        try {
            NettyDERBuffer buffer = new NettyDERBuffer(in.readSlice(in.readableBytes()));
            len = this.readMessageLength(buffer);
        }
        catch (Throwable throwable) {
            this.logger.trace("decoded message length of {} for {} on {}", len, in, ctx);
            in.setIndex(readerIdx, writerIdx);
            throw throwable;
        }
        this.logger.trace("decoded message length of {} for {} on {}", len, in, ctx);
        in.setIndex(readerIdx, writerIdx);
        if (len > 0) {
            ByteBuf retained = in.readRetainedSlice(len);
            this.logger.trace("read enough bytes from {} to decode message {} on {}", in, retained, ctx);
            out.add(retained);
            if (ctx != null) {
                ctx.fireUserEventTriggered((Object)NettyConnection.MessageStatus.READ);
            }
        } else {
            this.logger.trace("could not read enough bytes from {} to decode message on {}", (Object)in, (Object)ctx);
        }
    }

    private int readMessageLength(DERBuffer buffer) {
        DERParser messageParser = new DERParser();
        int tag = messageParser.readTag(buffer).getTagNo();
        if (UniversalDERTag.SEQ.getTagNo() != tag) {
            throw new IllegalArgumentException("Invalid message tag: " + tag);
        }
        try {
            int len = messageParser.readLength(buffer);
            if (buffer.position() + len <= buffer.capacity()) {
                this.logger.trace("read entire message of length {} with buffer {}", (Object)len, (Object)buffer);
                return buffer.position() + len;
            }
            this.logger.trace("could not read entire message of length {} with buffer {}", (Object)len, (Object)buffer);
        }
        catch (IndexOutOfBoundsException e) {
            this.logger.trace("error reading message length with buffer {}", (Object)buffer, (Object)e);
        }
        catch (Exception e) {
            this.logger.warn("Error reading message length with buffer {}", (Object)buffer, (Object)e);
        }
        return -1;
    }
}

