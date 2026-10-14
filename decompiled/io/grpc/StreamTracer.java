/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.ExperimentalApi;
import io.grpc.Status;
import javax.annotation.concurrent.ThreadSafe;

@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/2861")
@ThreadSafe
public abstract class StreamTracer {
    public void streamClosed(Status status) {
    }

    public void outboundMessage(int seqNo) {
    }

    public void inboundMessage(int seqNo) {
    }

    public void outboundMessageSent(int seqNo, long optionalWireSize, long optionalUncompressedSize) {
    }

    public void inboundMessageRead(int seqNo, long optionalWireSize, long optionalUncompressedSize) {
    }

    public void outboundWireSize(long bytes) {
    }

    public void outboundUncompressedSize(long bytes) {
    }

    public void inboundWireSize(long bytes) {
    }

    public void inboundUncompressedSize(long bytes) {
    }
}

