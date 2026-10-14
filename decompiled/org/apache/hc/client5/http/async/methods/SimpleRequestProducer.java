/*
 * Decompiled with CFR 0.152.
 */
package org.apache.hc.client5.http.async.methods;

import org.apache.hc.client5.http.async.methods.SimpleBody;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.core5.http.nio.AsyncEntityProducer;
import org.apache.hc.core5.http.nio.entity.BasicAsyncEntityProducer;
import org.apache.hc.core5.http.nio.entity.StringAsyncEntityProducer;
import org.apache.hc.core5.http.nio.support.BasicRequestProducer;
import org.apache.hc.core5.util.Args;

public final class SimpleRequestProducer
extends BasicRequestProducer {
    SimpleRequestProducer(SimpleHttpRequest request, AsyncEntityProducer entityProducer) {
        super(request, entityProducer);
    }

    public static SimpleRequestProducer create(SimpleHttpRequest request) {
        Args.notNull(request, "Request");
        SimpleBody body2 = request.getBody();
        AsyncEntityProducer entityProducer = body2 != null ? (body2.isText() ? new StringAsyncEntityProducer(body2.getBodyText(), body2.getContentType()) : new BasicAsyncEntityProducer(body2.getBodyBytes(), body2.getContentType())) : null;
        return new SimpleRequestProducer(request, entityProducer);
    }
}

