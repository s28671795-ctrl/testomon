/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.handler;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Queue;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.http.pathmap.PathSpecSet;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpOutput;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.IncludeExclude;
import org.eclipse.jetty.util.IteratingCallback;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BufferedResponseHandler
extends HandlerWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(BufferedResponseHandler.class);
    private final IncludeExclude<String> _methods = new IncludeExclude();
    private final IncludeExclude<String> _paths = new IncludeExclude(PathSpecSet.class);
    private final IncludeExclude<String> _mimeTypes = new IncludeExclude();

    public BufferedResponseHandler() {
        this._methods.include(HttpMethod.GET.asString());
        for (String type : MimeTypes.getKnownMimeTypes()) {
            if (!type.startsWith("image/") && !type.startsWith("audio/") && !type.startsWith("video/")) continue;
            this._mimeTypes.exclude(type);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} mime types {}", (Object)this, (Object)this._mimeTypes);
        }
    }

    public IncludeExclude<String> getMethodIncludeExclude() {
        return this._methods;
    }

    public IncludeExclude<String> getPathIncludeExclude() {
        return this._paths;
    }

    public IncludeExclude<String> getMimeIncludeExclude() {
        return this._mimeTypes;
    }

    protected boolean isMimeTypeBufferable(String mimetype) {
        return this._mimeTypes.test(mimetype);
    }

    protected boolean isPathBufferable(String requestURI) {
        if (requestURI == null) {
            return true;
        }
        return this._paths.test(requestURI);
    }

    protected boolean shouldBuffer(HttpChannel channel, boolean last) {
        if (last) {
            return false;
        }
        Response response = channel.getResponse();
        int status = response.getStatus();
        if (HttpStatus.hasNoBody(status) || HttpStatus.isRedirection(status)) {
            return false;
        }
        String ct = response.getContentType();
        if (ct == null) {
            return true;
        }
        ct = MimeTypes.getContentTypeWithoutCharset(ct);
        return this.isMimeTypeBufferable(StringUtil.asciiToLowerCase(ct));
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String mimeType;
        ServletContext context = baseRequest.getServletContext();
        String path = baseRequest.getPathInContext();
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} handle {} in {}", this, baseRequest, context);
        }
        HttpOutput out = baseRequest.getResponse().getHttpOutput();
        for (HttpOutput.Interceptor interceptor = out.getInterceptor(); interceptor != null; interceptor = interceptor.getNextInterceptor()) {
            if (!(interceptor instanceof BufferedInterceptor)) continue;
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} already intercepting {}", (Object)this, (Object)request);
            }
            this._handler.handle(target, baseRequest, request, response);
            return;
        }
        if (!this._methods.test(baseRequest.getMethod())) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} excluded by method {}", (Object)this, (Object)request);
            }
            this._handler.handle(target, baseRequest, request, response);
            return;
        }
        if (!this.isPathBufferable(path)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} excluded by path {}", (Object)this, (Object)request);
            }
            this._handler.handle(target, baseRequest, request, response);
            return;
        }
        String string = mimeType = context == null ? MimeTypes.getDefaultMimeByExtension(path) : context.getMimeType(path);
        if (mimeType != null && !this.isMimeTypeBufferable(mimeType = MimeTypes.getContentTypeWithoutCharset(mimeType))) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} excluded by path suffix mime type {}", (Object)this, (Object)request);
            }
            this._handler.handle(target, baseRequest, request, response);
            return;
        }
        out.setInterceptor(this.newBufferedInterceptor(baseRequest.getHttpChannel(), out.getInterceptor()));
        if (this._handler != null) {
            this._handler.handle(target, baseRequest, request, response);
        }
    }

    protected BufferedInterceptor newBufferedInterceptor(HttpChannel httpChannel, HttpOutput.Interceptor interceptor) {
        return new ArrayBufferedInterceptor(httpChannel, interceptor);
    }

    protected static interface BufferedInterceptor
    extends HttpOutput.Interceptor {
    }

    class ArrayBufferedInterceptor
    implements BufferedInterceptor {
        private final HttpOutput.Interceptor _next;
        private final HttpChannel _channel;
        private final Queue<ByteBuffer> _buffers = new ArrayDeque<ByteBuffer>();
        private Boolean _aggregating;
        private ByteBuffer _aggregate;

        public ArrayBufferedInterceptor(HttpChannel httpChannel, HttpOutput.Interceptor interceptor) {
            this._next = interceptor;
            this._channel = httpChannel;
        }

        @Override
        public HttpOutput.Interceptor getNextInterceptor() {
            return this._next;
        }

        @Override
        public void resetBuffer() {
            this._buffers.clear();
            this._aggregating = null;
            this._aggregate = null;
            BufferedInterceptor.super.resetBuffer();
        }

        @Override
        public void write(ByteBuffer content, boolean last, Callback callback) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} write last={} {}", this, last, BufferUtil.toDetailString(content));
            }
            if (this._aggregating == null) {
                this._aggregating = BufferedResponseHandler.this.shouldBuffer(this._channel, last);
            }
            if (!this._aggregating.booleanValue()) {
                this.getNextInterceptor().write(content, last, callback);
                return;
            }
            if (last) {
                if (BufferUtil.length(content) > 0) {
                    this._buffers.offer(content);
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{} committing {}", (Object)this, (Object)this._buffers.size());
                }
                this.commit(callback);
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{} aggregating", (Object)this);
                }
                while (BufferUtil.hasContent(content)) {
                    if (BufferUtil.space(this._aggregate) == 0) {
                        int size = Math.max(this._channel.getHttpConfiguration().getOutputBufferSize(), BufferUtil.length(content));
                        this._aggregate = BufferUtil.allocate(size);
                        this._buffers.offer(this._aggregate);
                    }
                    BufferUtil.append(this._aggregate, content);
                }
                callback.succeeded();
            }
        }

        private void commit(final Callback callback) {
            if (this._buffers.size() == 0) {
                this.getNextInterceptor().write(BufferUtil.EMPTY_BUFFER, true, callback);
            } else if (this._buffers.size() == 1) {
                this.getNextInterceptor().write(this._buffers.poll(), true, callback);
            } else {
                IteratingCallback icb = new IteratingCallback(){

                    @Override
                    protected IteratingCallback.Action process() {
                        ByteBuffer buffer = ArrayBufferedInterceptor.this._buffers.poll();
                        if (buffer == null) {
                            return IteratingCallback.Action.SUCCEEDED;
                        }
                        ArrayBufferedInterceptor.this.getNextInterceptor().write(buffer, ArrayBufferedInterceptor.this._buffers.isEmpty(), this);
                        return IteratingCallback.Action.SCHEDULED;
                    }

                    @Override
                    protected void onCompleteSuccess() {
                        callback.succeeded();
                    }

                    @Override
                    protected void onCompleteFailure(Throwable cause) {
                        callback.failed(cause);
                    }
                };
                icb.iterate();
            }
        }
    }
}

