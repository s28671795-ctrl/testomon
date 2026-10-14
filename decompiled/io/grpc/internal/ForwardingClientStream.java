/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.MoreObjects;
import io.grpc.Attributes;
import io.grpc.Compressor;
import io.grpc.Deadline;
import io.grpc.DecompressorRegistry;
import io.grpc.Status;
import io.grpc.internal.ClientStream;
import io.grpc.internal.ClientStreamListener;
import io.grpc.internal.InsightBuilder;
import java.io.InputStream;

abstract class ForwardingClientStream
implements ClientStream {
    ForwardingClientStream() {
    }

    protected abstract ClientStream delegate();

    @Override
    public void request(int numMessages) {
        this.delegate().request(numMessages);
    }

    @Override
    public void writeMessage(InputStream message) {
        this.delegate().writeMessage(message);
    }

    @Override
    public void flush() {
        this.delegate().flush();
    }

    @Override
    public boolean isReady() {
        return this.delegate().isReady();
    }

    @Override
    public void optimizeForDirectExecutor() {
        this.delegate().optimizeForDirectExecutor();
    }

    @Override
    public void setCompressor(Compressor compressor) {
        this.delegate().setCompressor(compressor);
    }

    @Override
    public void setMessageCompression(boolean enable) {
        this.delegate().setMessageCompression(enable);
    }

    @Override
    public void cancel(Status reason) {
        this.delegate().cancel(reason);
    }

    @Override
    public void halfClose() {
        this.delegate().halfClose();
    }

    @Override
    public void setAuthority(String authority) {
        this.delegate().setAuthority(authority);
    }

    @Override
    public void setFullStreamDecompression(boolean fullStreamDecompression) {
        this.delegate().setFullStreamDecompression(fullStreamDecompression);
    }

    @Override
    public void setDecompressorRegistry(DecompressorRegistry decompressorRegistry) {
        this.delegate().setDecompressorRegistry(decompressorRegistry);
    }

    @Override
    public void start(ClientStreamListener listener) {
        this.delegate().start(listener);
    }

    @Override
    public void setMaxInboundMessageSize(int maxSize) {
        this.delegate().setMaxInboundMessageSize(maxSize);
    }

    @Override
    public void setMaxOutboundMessageSize(int maxSize) {
        this.delegate().setMaxOutboundMessageSize(maxSize);
    }

    @Override
    public void setDeadline(Deadline deadline) {
        this.delegate().setDeadline(deadline);
    }

    @Override
    public Attributes getAttributes() {
        return this.delegate().getAttributes();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("delegate", this.delegate()).toString();
    }

    @Override
    public void appendTimeoutInsight(InsightBuilder insight) {
        this.delegate().appendTimeoutInsight(insight);
    }
}

