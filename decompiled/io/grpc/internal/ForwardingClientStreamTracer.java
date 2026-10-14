/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.MoreObjects;
import io.grpc.Attributes;
import io.grpc.ClientStreamTracer;
import io.grpc.Metadata;
import io.grpc.Status;

public abstract class ForwardingClientStreamTracer
extends ClientStreamTracer {
    protected abstract ClientStreamTracer delegate();

    @Override
    public void streamCreated(Attributes transportAttrs, Metadata headers) {
        this.delegate().streamCreated(transportAttrs, headers);
    }

    @Override
    public void createPendingStream() {
        this.delegate().createPendingStream();
    }

    @Override
    public void outboundHeaders() {
        this.delegate().outboundHeaders();
    }

    @Override
    public void inboundHeaders() {
        this.delegate().inboundHeaders();
    }

    @Override
    public void inboundHeaders(Metadata headers) {
        this.delegate().inboundHeaders(headers);
    }

    @Override
    public void inboundTrailers(Metadata trailers) {
        this.delegate().inboundTrailers(trailers);
    }

    @Override
    public void addOptionalLabel(String key, String value) {
        this.delegate().addOptionalLabel(key, value);
    }

    @Override
    public void streamClosed(Status status) {
        this.delegate().streamClosed(status);
    }

    @Override
    public void outboundMessage(int seqNo) {
        this.delegate().outboundMessage(seqNo);
    }

    @Override
    public void inboundMessage(int seqNo) {
        this.delegate().inboundMessage(seqNo);
    }

    @Override
    public void outboundMessageSent(int seqNo, long optionalWireSize, long optionalUncompressedSize) {
        this.delegate().outboundMessageSent(seqNo, optionalWireSize, optionalUncompressedSize);
    }

    @Override
    public void inboundMessageRead(int seqNo, long optionalWireSize, long optionalUncompressedSize) {
        this.delegate().inboundMessageRead(seqNo, optionalWireSize, optionalUncompressedSize);
    }

    @Override
    public void outboundWireSize(long bytes) {
        this.delegate().outboundWireSize(bytes);
    }

    @Override
    public void outboundUncompressedSize(long bytes) {
        this.delegate().outboundUncompressedSize(bytes);
    }

    @Override
    public void inboundWireSize(long bytes) {
        this.delegate().inboundWireSize(bytes);
    }

    @Override
    public void inboundUncompressedSize(long bytes) {
        this.delegate().inboundUncompressedSize(bytes);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("delegate", this.delegate()).toString();
    }
}

