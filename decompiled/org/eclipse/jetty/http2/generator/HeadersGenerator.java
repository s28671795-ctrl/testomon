/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.generator;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.PriorityFrame;
import org.eclipse.jetty.http2.generator.FrameGenerator;
import org.eclipse.jetty.http2.generator.HeaderGenerator;
import org.eclipse.jetty.http2.generator.PriorityGenerator;
import org.eclipse.jetty.http2.hpack.HpackEncoder;
import org.eclipse.jetty.http2.hpack.HpackException;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.util.BufferUtil;

public class HeadersGenerator
extends FrameGenerator {
    private final HpackEncoder encoder;
    private final int maxHeaderBlockFragment;
    private final PriorityGenerator priorityGenerator;

    public HeadersGenerator(HeaderGenerator headerGenerator, HpackEncoder encoder) {
        this(headerGenerator, encoder, 0);
    }

    public HeadersGenerator(HeaderGenerator headerGenerator, HpackEncoder encoder, int maxHeaderBlockFragment) {
        super(headerGenerator);
        this.encoder = encoder;
        this.maxHeaderBlockFragment = maxHeaderBlockFragment;
        this.priorityGenerator = new PriorityGenerator(headerGenerator);
    }

    @Override
    public int generate(ByteBufferPool.Lease lease, Frame frame) throws HpackException {
        HeadersFrame headersFrame = (HeadersFrame)frame;
        return this.generateHeaders(lease, headersFrame.getStreamId(), headersFrame.getMetaData(), headersFrame.getPriority(), headersFrame.isEndStream());
    }

    public int generateHeaders(ByteBufferPool.Lease lease, int streamId, MetaData metaData, PriorityFrame priority, boolean endStream) throws HpackException {
        if (streamId < 0) {
            throw new IllegalArgumentException("Invalid stream id: " + streamId);
        }
        int flags = 0;
        if (priority != null) {
            flags = 32;
        }
        ByteBuffer hpacked = this.encode(this.encoder, lease, metaData, this.getMaxFrameSize());
        int hpackedLength = hpacked.position();
        BufferUtil.flipToFlush(hpacked, 0);
        if (this.maxHeaderBlockFragment > 0 && hpackedLength > this.maxHeaderBlockFragment) {
            if (endStream) {
                flags |= 1;
            }
            int length = this.maxHeaderBlockFragment;
            if (priority != null) {
                length += 5;
            }
            ByteBuffer header = this.generateHeader(lease, FrameType.HEADERS, length, flags, streamId);
            this.generatePriority(header, priority);
            BufferUtil.flipToFlush(header, 0);
            lease.append(header, true);
            hpacked.limit(this.maxHeaderBlockFragment);
            lease.append(hpacked.slice(), false);
            int totalLength = 9 + length;
            int position = this.maxHeaderBlockFragment;
            int limit = position + this.maxHeaderBlockFragment;
            while (limit < hpackedLength) {
                hpacked.position(position).limit(limit);
                header = this.generateHeader(lease, FrameType.CONTINUATION, this.maxHeaderBlockFragment, 0, streamId);
                BufferUtil.flipToFlush(header, 0);
                lease.append(header, true);
                lease.append(hpacked.slice(), false);
                position += this.maxHeaderBlockFragment;
                limit += this.maxHeaderBlockFragment;
                totalLength += 9 + this.maxHeaderBlockFragment;
            }
            hpacked.position(position).limit(hpackedLength);
            header = this.generateHeader(lease, FrameType.CONTINUATION, hpacked.remaining(), 4, streamId);
            BufferUtil.flipToFlush(header, 0);
            lease.append(header, true);
            lease.append(hpacked, true);
            return totalLength += 9 + hpacked.remaining();
        }
        flags |= 4;
        if (endStream) {
            flags |= 1;
        }
        int length = hpackedLength;
        if (priority != null) {
            length += 5;
        }
        ByteBuffer header = this.generateHeader(lease, FrameType.HEADERS, length, flags, streamId);
        this.generatePriority(header, priority);
        BufferUtil.flipToFlush(header, 0);
        lease.append(header, true);
        lease.append(hpacked, true);
        return 9 + length;
    }

    private void generatePriority(ByteBuffer header, PriorityFrame priority) {
        if (priority != null) {
            this.priorityGenerator.generatePriorityBody(header, priority.getStreamId(), priority.getParentStreamId(), priority.getWeight(), priority.isExclusive());
        }
    }
}

