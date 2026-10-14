/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.parser;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.ErrorCode;
import org.eclipse.jetty.http2.hpack.HpackDecoder;
import org.eclipse.jetty.http2.hpack.HpackException;
import org.eclipse.jetty.http2.parser.BodyParser;
import org.eclipse.jetty.http2.parser.HeaderParser;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.util.BufferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeaderBlockParser {
    public static final MetaData STREAM_FAILURE = new MetaData(HttpVersion.HTTP_2, null);
    public static final MetaData SESSION_FAILURE = new MetaData(HttpVersion.HTTP_2, null);
    private static final Logger LOG = LoggerFactory.getLogger(HeaderBlockParser.class);
    private final HeaderParser headerParser;
    private final ByteBufferPool byteBufferPool;
    private final HpackDecoder hpackDecoder;
    private final BodyParser notifier;
    private ByteBuffer blockBuffer;

    public HeaderBlockParser(HeaderParser headerParser, ByteBufferPool byteBufferPool, HpackDecoder hpackDecoder, BodyParser notifier) {
        this.headerParser = headerParser;
        this.byteBufferPool = byteBufferPool;
        this.hpackDecoder = hpackDecoder;
        this.notifier = notifier;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public MetaData parse(ByteBuffer buffer, int blockLength) {
        ByteBuffer toDecode;
        int accumulated = this.blockBuffer == null ? 0 : this.blockBuffer.position();
        int remaining = blockLength - accumulated;
        if (buffer.remaining() < remaining) {
            if (this.blockBuffer == null) {
                this.blockBuffer = this.byteBufferPool.acquire(blockLength, buffer.isDirect());
                BufferUtil.clearToFill(this.blockBuffer);
            }
            this.blockBuffer.put(buffer);
            return null;
        }
        int limit = buffer.limit();
        buffer.limit(buffer.position() + remaining);
        if (this.blockBuffer != null) {
            this.blockBuffer.put(buffer);
            BufferUtil.flipToFlush(this.blockBuffer, 0);
            toDecode = this.blockBuffer;
        } else {
            toDecode = buffer;
        }
        try {
            MetaData metaData = this.hpackDecoder.decode(toDecode);
            return metaData;
        }
        catch (HpackException.StreamException x) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Stream error, stream={}", (Object)this.headerParser.getStreamId(), (Object)x);
            }
            this.notifier.streamFailure(this.headerParser.getStreamId(), ErrorCode.PROTOCOL_ERROR.code, "invalid_hpack_block");
            MetaData metaData = STREAM_FAILURE;
            return metaData;
        }
        catch (HpackException.CompressionException x) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Compression error, buffer={}", (Object)BufferUtil.toDetailString(buffer), (Object)x);
            }
            this.notifier.connectionFailure(buffer, ErrorCode.COMPRESSION_ERROR.code, "invalid_hpack_block");
            MetaData metaData = SESSION_FAILURE;
            return metaData;
        }
        catch (HpackException.SessionException x) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Session error, buffer={}", (Object)BufferUtil.toDetailString(buffer), (Object)x);
            }
            this.notifier.connectionFailure(buffer, ErrorCode.PROTOCOL_ERROR.code, "invalid_hpack_block");
            MetaData metaData = SESSION_FAILURE;
            return metaData;
        }
        finally {
            buffer.limit(limit);
            if (this.blockBuffer != null) {
                this.byteBufferPool.release(this.blockBuffer);
                this.blockBuffer = null;
            }
        }
    }
}

