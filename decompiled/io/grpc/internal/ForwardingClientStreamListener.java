/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.MoreObjects;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.internal.ClientStreamListener;
import io.grpc.internal.StreamListener;

abstract class ForwardingClientStreamListener
implements ClientStreamListener {
    ForwardingClientStreamListener() {
    }

    protected abstract ClientStreamListener delegate();

    @Override
    public void headersRead(Metadata headers) {
        this.delegate().headersRead(headers);
    }

    @Override
    public void closed(Status status, ClientStreamListener.RpcProgress rpcProgress, Metadata trailers) {
        this.delegate().closed(status, rpcProgress, trailers);
    }

    @Override
    public void messagesAvailable(StreamListener.MessageProducer producer) {
        this.delegate().messagesAvailable(producer);
    }

    @Override
    public void onReady() {
        this.delegate().onReady();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("delegate", this.delegate()).toString();
    }
}

