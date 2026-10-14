/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.generator;

import java.nio.ByteBuffer;
import org.eclipse.jetty.http2.frames.Frame;
import org.eclipse.jetty.http2.frames.FrameType;
import org.eclipse.jetty.http2.frames.PriorityFrame;
import org.eclipse.jetty.http2.generator.FrameGenerator;
import org.eclipse.jetty.http2.generator.HeaderGenerator;
import org.eclipse.jetty.io.ByteBufferPool;
import org.eclipse.jetty.util.BufferUtil;

public class PriorityGenerator
extends FrameGenerator {
    public PriorityGenerator(HeaderGenerator headerGenerator) {
        super(headerGenerator);
    }

    @Override
    public int generate(ByteBufferPool.Lease lease, Frame frame) {
        PriorityFrame priorityFrame = (PriorityFrame)frame;
        return this.generatePriority(lease, priorityFrame.getStreamId(), priorityFrame.getParentStreamId(), priorityFrame.getWeight(), priorityFrame.isExclusive());
    }

    public int generatePriority(ByteBufferPool.Lease lease, int streamId, int parentStreamId, int weight, boolean exclusive) {
        ByteBuffer header = this.generateHeader(lease, FrameType.PRIORITY, 5, 0, streamId);
        this.generatePriorityBody(header, streamId, parentStreamId, weight, exclusive);
        BufferUtil.flipToFlush(header, 0);
        lease.append(header, true);
        return 14;
    }

    public void generatePriorityBody(ByteBuffer header, int streamId, int parentStreamId, int weight, boolean exclusive) {
        if (streamId < 0) {
            throw new IllegalArgumentException("Invalid stream id: " + streamId);
        }
        if (parentStreamId < 0) {
            throw new IllegalArgumentException("Invalid parent stream id: " + parentStreamId);
        }
        if (parentStreamId == streamId) {
            throw new IllegalArgumentException("Stream " + streamId + " cannot depend on stream " + parentStreamId);
        }
        if (weight < 1 || weight > 256) {
            throw new IllegalArgumentException("Invalid weight: " + weight);
        }
        if (exclusive) {
            parentStreamId |= Integer.MIN_VALUE;
        }
        header.putInt(parentStreamId);
        header.put((byte)(weight - 1));
    }
}

