/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.handler.gzip;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.jetty.http.CompressedContentFormat;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.http.PreEncodedHttpField;
import org.eclipse.jetty.http.pathmap.PathSpecSet;
import org.eclipse.jetty.server.HttpOutput;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.server.handler.gzip.GzipFactory;
import org.eclipse.jetty.server.handler.gzip.GzipHttpInputInterceptor;
import org.eclipse.jetty.server.handler.gzip.GzipHttpOutputInterceptor;
import org.eclipse.jetty.util.AsciiLowerCaseSet;
import org.eclipse.jetty.util.IncludeExclude;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.compression.CompressionPool;
import org.eclipse.jetty.util.compression.DeflaterPool;
import org.eclipse.jetty.util.compression.InflaterPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GzipHandler
extends HandlerWrapper
implements GzipFactory {
    public static final EnumSet<HttpHeader> ETAG_HEADERS = EnumSet.of(HttpHeader.IF_MATCH, HttpHeader.IF_NONE_MATCH);
    public static final String GZIP_HANDLER_ETAGS = "o.e.j.s.h.gzip.GzipHandler.etag";
    public static final String GZIP = "gzip";
    public static final String DEFLATE = "deflate";
    public static final int DEFAULT_MIN_GZIP_SIZE = 32;
    public static final int BREAK_EVEN_GZIP_SIZE = 23;
    private static final Logger LOG = LoggerFactory.getLogger(GzipHandler.class);
    private static final HttpField X_CE_GZIP = new PreEncodedHttpField("X-Content-Encoding", "gzip");
    private static final Pattern COMMA_GZIP = Pattern.compile(".*, *gzip");
    private InflaterPool _inflaterPool;
    private DeflaterPool _deflaterPool;
    private int _minGzipSize = 32;
    private boolean _syncFlush = false;
    private int _inflateBufferSize = -1;
    private EnumSet<DispatcherType> _dispatchers = EnumSet.of(DispatcherType.REQUEST);
    private final IncludeExclude<String> _methods = new IncludeExclude();
    private final IncludeExclude<String> _paths = new IncludeExclude(PathSpecSet.class);
    private final IncludeExclude<String> _inflatePaths = new IncludeExclude(PathSpecSet.class);
    private final IncludeExclude<String> _mimeTypes = new IncludeExclude(AsciiLowerCaseSet.class);
    private HttpField _vary = GzipHttpOutputInterceptor.VARY_ACCEPT_ENCODING;

    public GzipHandler() {
        this._methods.include(HttpMethod.GET.asString());
        this._methods.include(HttpMethod.POST.asString());
        for (String type : MimeTypes.getKnownMimeTypes()) {
            if ("image/svg+xml".equals(type)) {
                this._paths.exclude("*.svgz");
                continue;
            }
            if (!type.startsWith("image/") && !type.startsWith("audio/") && !type.startsWith("video/")) continue;
            this._mimeTypes.exclude(type);
        }
        this._mimeTypes.exclude("application/compress");
        this._mimeTypes.exclude("application/zip");
        this._mimeTypes.exclude("application/gzip");
        this._mimeTypes.exclude("application/bzip2");
        this._mimeTypes.exclude("application/brotli");
        this._mimeTypes.exclude("application/x-xz");
        this._mimeTypes.exclude("application/x-rar-compressed");
        this._mimeTypes.exclude("text/event-stream");
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} mime types {}", (Object)this, (Object)this._mimeTypes);
        }
    }

    @Override
    protected void doStart() throws Exception {
        Server server = this.getServer();
        if (this._inflaterPool == null) {
            this._inflaterPool = InflaterPool.ensurePool(server);
            this.addBean(this._inflaterPool);
        }
        if (this._deflaterPool == null) {
            this._deflaterPool = DeflaterPool.ensurePool(server);
            this.addBean(this._deflaterPool);
        }
        super.doStart();
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
        this.removeBean(this._inflaterPool);
        this._inflaterPool = null;
        this.removeBean(this._deflaterPool);
        this._deflaterPool = null;
    }

    public HttpField getVary() {
        return this._vary;
    }

    public void setVary(HttpField vary) {
        if (this.isRunning()) {
            throw new IllegalStateException(this.getState());
        }
        this._vary = vary == null || vary instanceof PreEncodedHttpField ? vary : new PreEncodedHttpField(vary.getHeader(), vary.getName(), vary.getValue());
    }

    public void addExcludedMethods(String ... methods) {
        for (String m4 : methods) {
            this._methods.exclude(m4);
        }
    }

    public EnumSet<DispatcherType> getDispatcherTypes() {
        return this._dispatchers;
    }

    public void setDispatcherTypes(EnumSet<DispatcherType> dispatchers) {
        this._dispatchers = dispatchers;
    }

    public void setDispatcherTypes(DispatcherType ... dispatchers) {
        this._dispatchers = EnumSet.copyOf(Arrays.asList(dispatchers));
    }

    public void addExcludedMimeTypes(String ... types) {
        for (String t2 : types) {
            this._mimeTypes.exclude((T[])StringUtil.csvSplit(t2));
        }
    }

    public void addExcludedPaths(String ... pathspecs) {
        for (String p : pathspecs) {
            this._paths.exclude((T[])StringUtil.csvSplit(p));
        }
    }

    public void addExcludedInflationPaths(String ... pathspecs) {
        for (String p : pathspecs) {
            this._inflatePaths.exclude((T[])StringUtil.csvSplit(p));
        }
    }

    public void addIncludedMethods(String ... methods) {
        for (String m4 : methods) {
            this._methods.include(m4);
        }
    }

    public boolean isSyncFlush() {
        return this._syncFlush;
    }

    public void setSyncFlush(boolean syncFlush) {
        this._syncFlush = syncFlush;
    }

    public void addIncludedMimeTypes(String ... types) {
        for (String t2 : types) {
            this._mimeTypes.include((T[])StringUtil.csvSplit(t2));
        }
    }

    public void addIncludedPaths(String ... pathspecs) {
        for (String p : pathspecs) {
            this._paths.include((T[])StringUtil.csvSplit(p));
        }
    }

    public void addIncludedInflationPaths(String ... pathspecs) {
        for (String p : pathspecs) {
            this._inflatePaths.include((T[])StringUtil.csvSplit(p));
        }
    }

    @Override
    public CompressionPool.Entry getDeflaterEntry(Request request, long contentLength) {
        if (contentLength >= 0L && contentLength < (long)this._minGzipSize) {
            LOG.debug("{} excluded minGzipSize {}", (Object)this, (Object)request);
            return null;
        }
        if (!request.getHttpFields().contains(HttpHeader.ACCEPT_ENCODING, GZIP)) {
            LOG.debug("{} excluded not gzip accept {}", (Object)this, (Object)request);
            return null;
        }
        return this._deflaterPool.acquire();
    }

    public String[] getExcludedMethods() {
        Set<String> excluded = this._methods.getExcluded();
        return excluded.toArray(new String[0]);
    }

    public String[] getExcludedMimeTypes() {
        Set<String> excluded = this._mimeTypes.getExcluded();
        return excluded.toArray(new String[0]);
    }

    public String[] getExcludedPaths() {
        Set<String> excluded = this._paths.getExcluded();
        return excluded.toArray(new String[0]);
    }

    public String[] getExcludedInflationPaths() {
        Set<String> excluded = this._inflatePaths.getExcluded();
        return excluded.toArray(new String[0]);
    }

    public String[] getIncludedMethods() {
        Set<String> includes = this._methods.getIncluded();
        return includes.toArray(new String[0]);
    }

    public String[] getIncludedMimeTypes() {
        Set<String> includes = this._mimeTypes.getIncluded();
        return includes.toArray(new String[0]);
    }

    public String[] getIncludedPaths() {
        Set<String> includes = this._paths.getIncluded();
        return includes.toArray(new String[0]);
    }

    public String[] getIncludedInflationPaths() {
        Set<String> includes = this._inflatePaths.getIncluded();
        return includes.toArray(new String[0]);
    }

    public int getMinGzipSize() {
        return this._minGzipSize;
    }

    protected HttpField getVaryField() {
        return this._vary;
    }

    public int getInflateBufferSize() {
        return this._inflateBufferSize;
    }

    public void setInflateBufferSize(int size) {
        this._inflateBufferSize = size;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String mimeType;
        boolean inflated;
        if (baseRequest.isHandled()) {
            super.handle(target, baseRequest, request, response);
            return;
        }
        ServletContext context = baseRequest.getServletContext();
        String path = baseRequest.getPathInContext();
        LOG.debug("{} handle {} in {}", this, baseRequest, context);
        if (!this._dispatchers.contains((Object)baseRequest.getDispatcherType())) {
            LOG.debug("{} excluded by dispatcherType {}", (Object)this, (Object)baseRequest.getDispatcherType());
            super.handle(target, baseRequest, request, response);
            return;
        }
        HttpFields httpFields = baseRequest.getHttpFields();
        boolean bl = inflated = this._inflateBufferSize > 0 && httpFields.contains(HttpHeader.CONTENT_ENCODING, GZIP) && this.isPathInflatable(path);
        if (inflated) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} inflate {}", (Object)this, (Object)request);
            }
            GzipHttpInputInterceptor gzipHttpInputInterceptor = new GzipHttpInputInterceptor(this._inflaterPool, baseRequest.getHttpChannel().getByteBufferPool(), this._inflateBufferSize, baseRequest.getHttpChannel().isUseInputDirectByteBuffers());
            baseRequest.getHttpInput().addInterceptor(gzipHttpInputInterceptor);
        }
        if (response.isCommitted()) {
            super.handle(target, baseRequest, request, response);
            return;
        }
        HttpOutput out = baseRequest.getResponse().getHttpOutput();
        boolean alreadyGzipped = false;
        for (HttpOutput.Interceptor interceptor = out.getInterceptor(); interceptor != null; interceptor = interceptor.getNextInterceptor()) {
            if (!(interceptor instanceof GzipHttpOutputInterceptor)) continue;
            alreadyGzipped = true;
            break;
        }
        if (inflated || httpFields.contains(ETAG_HEADERS)) {
            HttpFields.Mutable newFields = HttpFields.build(httpFields.size() + 1);
            block9: for (HttpField field2 : httpFields) {
                if (field2.getHeader() == null) {
                    newFields.add(field2);
                    continue;
                }
                switch (field2.getHeader()) {
                    case IF_MATCH: 
                    case IF_NONE_MATCH: {
                        String etags = field2.getValue();
                        String etagsNoSuffix = CompressedContentFormat.GZIP.stripSuffixes(etags);
                        if (etagsNoSuffix.equals(etags)) {
                            newFields.add(field2);
                            continue block9;
                        }
                        newFields.add(new HttpField(field2.getHeader(), etagsNoSuffix));
                        baseRequest.setAttribute(GZIP_HANDLER_ETAGS, etags);
                        continue block9;
                    }
                    case CONTENT_LENGTH: {
                        newFields.add(inflated ? new HttpField("X-Content-Length", field2.getValue()) : field2);
                        continue block9;
                    }
                    case CONTENT_ENCODING: {
                        if (inflated) {
                            if (field2.getValue().equalsIgnoreCase(GZIP)) {
                                newFields.add(X_CE_GZIP);
                                continue block9;
                            }
                            if (!COMMA_GZIP.matcher(field2.getValue()).matches()) continue block9;
                            String v = field2.getValue();
                            v = v.substring(0, v.lastIndexOf(44));
                            newFields.add(X_CE_GZIP);
                            newFields.add(new HttpField(HttpHeader.CONTENT_ENCODING, v));
                            continue block9;
                        }
                        newFields.add(field2);
                        continue block9;
                    }
                }
                newFields.add(field2);
            }
            baseRequest.setHttpFields(newFields);
        }
        if (alreadyGzipped) {
            LOG.debug("{} already intercepting {}", (Object)this, (Object)request);
            super.handle(target, baseRequest, request, response);
            return;
        }
        if (!this._methods.test(baseRequest.getMethod())) {
            LOG.debug("{} excluded by method {}", (Object)this, (Object)request);
            super.handle(target, baseRequest, request, response);
            return;
        }
        if (!this.isPathGzipable(path)) {
            LOG.debug("{} excluded by path {}", (Object)this, (Object)request);
            super.handle(target, baseRequest, request, response);
            return;
        }
        String string = mimeType = context == null ? MimeTypes.getDefaultMimeByExtension(path) : context.getMimeType(path);
        if (mimeType != null && !this.isMimeTypeGzipable(mimeType = HttpField.valueParameters(mimeType, null))) {
            LOG.debug("{} excluded by path suffix mime type {}", (Object)this, (Object)request);
            super.handle(target, baseRequest, request, response);
            return;
        }
        HttpOutput.Interceptor origInterceptor = out.getInterceptor();
        try {
            out.setInterceptor(new GzipHttpOutputInterceptor(this, this.getVaryField(), baseRequest.getHttpChannel(), origInterceptor, this.isSyncFlush()));
            super.handle(target, baseRequest, request, response);
        }
        finally {
            if (!baseRequest.isHandled() && !baseRequest.isAsyncStarted()) {
                out.setInterceptor(origInterceptor);
            }
        }
    }

    @Override
    public boolean isMimeTypeGzipable(String mimetype) {
        return this._mimeTypes.test(mimetype);
    }

    protected boolean isPathGzipable(String requestURI) {
        if (requestURI == null) {
            return true;
        }
        return this._paths.test(requestURI);
    }

    protected boolean isPathInflatable(String requestURI) {
        if (requestURI == null) {
            return true;
        }
        return this._inflatePaths.test(requestURI);
    }

    public void setExcludedMethods(String ... methods) {
        this._methods.getExcluded().clear();
        this._methods.exclude((T[])methods);
    }

    public void setExcludedMimeTypes(String ... types) {
        this._mimeTypes.getExcluded().clear();
        this._mimeTypes.exclude((T[])types);
    }

    public void setExcludedMimeTypesList(String csvTypes) {
        this.setExcludedMimeTypes(StringUtil.csvSplit(csvTypes));
    }

    public void setExcludedPaths(String ... pathspecs) {
        this._paths.getExcluded().clear();
        this._paths.exclude((T[])pathspecs);
    }

    public void setExcludedInflatePaths(String ... pathspecs) {
        this._inflatePaths.getExcluded().clear();
        this._inflatePaths.exclude((T[])pathspecs);
    }

    public void setDispatcherTypes(String ... dispatchers) {
        this._dispatchers = EnumSet.copyOf(Stream.of(dispatchers).flatMap(s2 -> Stream.of(StringUtil.csvSplit(s2))).map(DispatcherType::valueOf).collect(Collectors.toSet()));
    }

    public void setIncludedMethods(String ... methods) {
        this._methods.getIncluded().clear();
        this._methods.include((T[])methods);
    }

    public void setIncludedMimeTypes(String ... types) {
        this._mimeTypes.getIncluded().clear();
        this._mimeTypes.include((T[])types);
    }

    public void setIncludedMimeTypesList(String csvTypes) {
        this.setIncludedMimeTypes(StringUtil.csvSplit(csvTypes));
    }

    public void setIncludedPaths(String ... pathspecs) {
        this._paths.getIncluded().clear();
        this._paths.include((T[])pathspecs);
    }

    public void setIncludedInflatePaths(String ... pathspecs) {
        this._inflatePaths.getIncluded().clear();
        this._inflatePaths.include((T[])pathspecs);
    }

    public void setMinGzipSize(int minGzipSize) {
        if (minGzipSize < 23) {
            LOG.warn("minGzipSize of {} is inefficient for short content, break even is size {}", (Object)minGzipSize, (Object)23);
        }
        this._minGzipSize = Math.max(0, minGzipSize);
    }

    public void setIncludedMethodList(String csvMethods) {
        this.setIncludedMethods(StringUtil.csvSplit(csvMethods));
    }

    public String getIncludedMethodList() {
        return String.join((CharSequence)",", this.getIncludedMethods());
    }

    public void setExcludedMethodList(String csvMethods) {
        this.setExcludedMethods(StringUtil.csvSplit(csvMethods));
    }

    public String getExcludedMethodList() {
        return String.join((CharSequence)",", this.getExcludedMethods());
    }

    public DeflaterPool getDeflaterPool() {
        return this._deflaterPool;
    }

    public InflaterPool getInflaterPool() {
        return this._inflaterPool;
    }

    public void setDeflaterPool(DeflaterPool deflaterPool) {
        if (this.isStarted()) {
            throw new IllegalStateException(this.getState());
        }
        this.updateBean(this._deflaterPool, deflaterPool);
        this._deflaterPool = deflaterPool;
    }

    public void setInflaterPool(InflaterPool inflaterPool) {
        if (this.isStarted()) {
            throw new IllegalStateException(this.getState());
        }
        this.updateBean(this._inflaterPool, inflaterPool);
        this._inflaterPool = inflaterPool;
    }

    @Deprecated
    public int getDeflaterPoolCapacity() {
        return this._deflaterPool == null ? 1024 : this._deflaterPool.getCapacity();
    }

    @Deprecated
    public void setDeflaterPoolCapacity(int capacity) {
        if (this._deflaterPool != null) {
            this._deflaterPool.setCapacity(capacity);
        }
    }

    @Deprecated
    public int getInflaterPoolCapacity() {
        return this._inflaterPool == null ? 1024 : this._inflaterPool.getCapacity();
    }

    @Deprecated
    public void setInflaterPoolCapacity(int capacity) {
        if (this._inflaterPool != null) {
            this._inflaterPool.setCapacity(capacity);
        }
    }

    @Override
    public String toString() {
        return String.format("%s@%x{%s,min=%s,inflate=%s}", this.getClass().getSimpleName(), this.hashCode(), this.getState(), this._minGzipSize, this._inflateBufferSize);
    }
}

