/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import javax.inject.Singleton;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.WriterInterceptor;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.server.ChunkedResponseWriter;
import org.glassfish.jersey.server.internal.JsonWithPaddingInterceptor;
import org.glassfish.jersey.server.internal.MappableExceptionWrapperInterceptor;
import org.glassfish.jersey.server.internal.monitoring.MonitoringContainerListener;

class ServerBinder
extends AbstractBinder {
    ServerBinder() {
    }

    @Override
    protected void configure() {
        this.install(new MappableExceptionWrapperInterceptor.Binder(), new MonitoringContainerListener.Binder());
        ((ClassBinding)this.bind(ChunkedResponseWriter.class).to(MessageBodyWriter.class)).in(Singleton.class);
        ((ClassBinding)this.bind(JsonWithPaddingInterceptor.class).to(WriterInterceptor.class)).in(Singleton.class);
    }
}

