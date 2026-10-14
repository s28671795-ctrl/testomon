/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.core5.http.nio.support;

import java.io.IOException;
import java.net.URI;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.Method;
import org.apache.hc.core5.http.message.BasicHttpRequest;
import org.apache.hc.core5.http.nio.AsyncEntityProducer;
import org.apache.hc.core5.http.nio.AsyncRequestProducer;
import org.apache.hc.core5.http.nio.DataStreamChannel;
import org.apache.hc.core5.http.nio.RequestChannel;
import org.apache.hc.core5.http.protocol.HttpContext;

public class BasicRequestProducer
implements AsyncRequestProducer {
    private final HttpRequest request;
    private final AsyncEntityProducer dataProducer;

    public BasicRequestProducer(HttpRequest request, AsyncEntityProducer dataProducer) {
        this.request = request;
        this.dataProducer = dataProducer;
    }

    public BasicRequestProducer(String method2, HttpHost host2, String path, AsyncEntityProducer dataProducer) {
        this(new BasicHttpRequest(method2, host2, path), dataProducer);
    }

    public BasicRequestProducer(String method2, HttpHost host2, String path) {
        this(method2, host2, path, null);
    }

    public BasicRequestProducer(String method2, URI requestUri, AsyncEntityProducer dataProducer) {
        this(new BasicHttpRequest(method2, requestUri), dataProducer);
    }

    public BasicRequestProducer(String method2, URI requestUri) {
        this(method2, requestUri, null);
    }

    public BasicRequestProducer(Method method2, HttpHost host2, String path, AsyncEntityProducer dataProducer) {
        this(new BasicHttpRequest(method2, host2, path), dataProducer);
    }

    public BasicRequestProducer(Method method2, HttpHost host2, String path) {
        this(method2, host2, path, null);
    }

    public BasicRequestProducer(Method method2, URI requestUri, AsyncEntityProducer dataProducer) {
        this(new BasicHttpRequest(method2, requestUri), dataProducer);
    }

    public BasicRequestProducer(Method method2, URI requestUri) {
        this(method2, requestUri, null);
    }

    @Override
    public void sendRequest(RequestChannel requestChannel, HttpContext httpContext) throws HttpException, IOException {
        requestChannel.sendRequest(this.request, this.dataProducer, httpContext);
    }

    @Override
    public int available() {
        return this.dataProducer != null ? this.dataProducer.available() : 0;
    }

    @Override
    public void produce(DataStreamChannel channel) throws IOException {
        if (this.dataProducer != null) {
            this.dataProducer.produce(channel);
        }
    }

    @Override
    public boolean isRepeatable() {
        return this.dataProducer == null || this.dataProducer.isRepeatable();
    }

    @Override
    public void failed(Exception cause) {
        try {
            if (this.dataProducer != null) {
                this.dataProducer.failed(cause);
            }
        }
        finally {
            this.releaseResources();
        }
    }

    @Override
    public void releaseResources() {
        if (this.dataProducer != null) {
            this.dataProducer.releaseResources();
        }
    }
}

