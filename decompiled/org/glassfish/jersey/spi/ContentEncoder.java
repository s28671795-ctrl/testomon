/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.spi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Priority;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import org.glassfish.jersey.spi.Contract;

@Priority(value=4000)
@Contract
public abstract class ContentEncoder
implements ReaderInterceptor,
WriterInterceptor {
    private final Set<String> supportedEncodings;

    protected ContentEncoder(String ... supportedEncodings) {
        if (supportedEncodings.length == 0) {
            throw new IllegalArgumentException();
        }
        this.supportedEncodings = Collections.unmodifiableSet(Arrays.stream(supportedEncodings).collect(Collectors.toSet()));
    }

    public final Set<String> getSupportedEncodings() {
        return this.supportedEncodings;
    }

    public abstract InputStream decode(String var1, InputStream var2) throws IOException;

    public abstract OutputStream encode(String var1, OutputStream var2) throws IOException;

    @Override
    public final Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
        String contentEncoding = context.getHeaders().getFirst("Content-Encoding");
        if (contentEncoding != null && this.getSupportedEncodings().contains(contentEncoding)) {
            context.setInputStream(this.decode(contentEncoding, context.getInputStream()));
        }
        return context.proceed();
    }

    @Override
    public final void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        String contentEncoding = (String)context.getHeaders().getFirst("Content-Encoding");
        if (contentEncoding != null && this.getSupportedEncodings().contains(contentEncoding)) {
            context.setOutputStream(this.encode(contentEncoding, context.getOutputStream()));
        }
        context.proceed();
    }
}

