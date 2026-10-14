/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal;

import java.io.IOException;
import javax.annotation.Priority;
import javax.inject.Singleton;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;
import org.glassfish.jersey.server.internal.process.MappableException;

@Priority(value=10)
@Singleton
public class MappableExceptionWrapperInterceptor
implements ReaderInterceptor,
WriterInterceptor {
    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
        try {
            return context.proceed();
        }
        catch (WebApplicationException | MessageBodyProviderNotFoundException | MappableException e) {
            throw e;
        }
        catch (Exception e) {
            throw new MappableException(e);
        }
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        try {
            context.proceed();
        }
        catch (WebApplicationException | MappableException e) {
            throw e;
        }
        catch (MessageBodyProviderNotFoundException nfe) {
            throw new InternalServerErrorException(nfe);
        }
        catch (Exception e) {
            throw new MappableException(e);
        }
    }

    public static class Binder
    extends AbstractBinder {
        @Override
        protected void configure() {
            ((ClassBinding)((ClassBinding)this.bind(MappableExceptionWrapperInterceptor.class).to(ReaderInterceptor.class)).to(WriterInterceptor.class)).in(Singleton.class);
        }
    }
}

