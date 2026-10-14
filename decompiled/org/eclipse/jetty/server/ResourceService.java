/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.InvalidPathException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import org.eclipse.jetty.http.CompressedContentFormat;
import org.eclipse.jetty.http.DateParser;
import org.eclipse.jetty.http.HttpContent;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpHeaderValue;
import org.eclipse.jetty.http.PreEncodedHttpField;
import org.eclipse.jetty.http.QuotedCSV;
import org.eclipse.jetty.http.QuotedQualityCSV;
import org.eclipse.jetty.io.WriterOutputStream;
import org.eclipse.jetty.server.HttpOutput;
import org.eclipse.jetty.server.InclusiveByteRange;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.resource.HttpContentRangeWriter;
import org.eclipse.jetty.server.resource.InputStreamRangeWriter;
import org.eclipse.jetty.server.resource.RangeWriter;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.MultiPartOutputStream;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.thread.Invocable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceService {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceService.class);
    private static final PreEncodedHttpField ACCEPT_RANGES = new PreEncodedHttpField(HttpHeader.ACCEPT_RANGES, "bytes");
    private HttpContent.ContentFactory _contentFactory;
    private WelcomeFactory _welcomeFactory;
    private boolean _acceptRanges = true;
    private boolean _dirAllowed = true;
    private boolean _redirectWelcome = false;
    private CompressedContentFormat[] _precompressedFormats = new CompressedContentFormat[0];
    private String[] _preferredEncodingOrder = new String[0];
    private final Map<String, List<String>> _preferredEncodingOrderCache = new ConcurrentHashMap<String, List<String>>();
    private int _encodingCacheSize = 100;
    private boolean _pathInfoOnly = false;
    private boolean _etags = false;
    private HttpField _cacheControl;
    private List<String> _gzipEquivalentFileExtensions;

    public HttpContent.ContentFactory getContentFactory() {
        return this._contentFactory;
    }

    public void setContentFactory(HttpContent.ContentFactory contentFactory) {
        this._contentFactory = contentFactory;
    }

    public WelcomeFactory getWelcomeFactory() {
        return this._welcomeFactory;
    }

    public void setWelcomeFactory(WelcomeFactory welcomeFactory) {
        this._welcomeFactory = welcomeFactory;
    }

    public boolean isAcceptRanges() {
        return this._acceptRanges;
    }

    public void setAcceptRanges(boolean acceptRanges) {
        this._acceptRanges = acceptRanges;
    }

    public boolean isDirAllowed() {
        return this._dirAllowed;
    }

    public void setDirAllowed(boolean dirAllowed) {
        this._dirAllowed = dirAllowed;
    }

    public boolean isRedirectWelcome() {
        return this._redirectWelcome;
    }

    public void setRedirectWelcome(boolean redirectWelcome) {
        this._redirectWelcome = redirectWelcome;
    }

    public CompressedContentFormat[] getPrecompressedFormats() {
        return this._precompressedFormats;
    }

    public void setPrecompressedFormats(CompressedContentFormat[] precompressedFormats) {
        this._precompressedFormats = precompressedFormats;
        this._preferredEncodingOrder = (String[])Arrays.stream(this._precompressedFormats).map(f -> f.getEncoding()).toArray(String[]::new);
    }

    public void setEncodingCacheSize(int encodingCacheSize) {
        this._encodingCacheSize = encodingCacheSize;
    }

    public int getEncodingCacheSize() {
        return this._encodingCacheSize;
    }

    public boolean isPathInfoOnly() {
        return this._pathInfoOnly;
    }

    public void setPathInfoOnly(boolean pathInfoOnly) {
        this._pathInfoOnly = pathInfoOnly;
    }

    public boolean isEtags() {
        return this._etags;
    }

    public void setEtags(boolean etags) {
        this._etags = etags;
    }

    public HttpField getCacheControl() {
        return this._cacheControl;
    }

    public void setCacheControl(HttpField cacheControl) {
        if (cacheControl == null) {
            this._cacheControl = null;
        }
        if (cacheControl.getHeader() != HttpHeader.CACHE_CONTROL) {
            throw new IllegalArgumentException("!Cache-Control");
        }
        this._cacheControl = cacheControl instanceof PreEncodedHttpField ? cacheControl : new PreEncodedHttpField(cacheControl.getHeader(), cacheControl.getValue());
    }

    public List<String> getGzipEquivalentFileExtensions() {
        return this._gzipEquivalentFileExtensions;
    }

    public void setGzipEquivalentFileExtensions(List<String> gzipEquivalentFileExtensions) {
        this._gzipEquivalentFileExtensions = gzipEquivalentFileExtensions;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean included;
        String servletPath = null;
        String pathInfo = null;
        Enumeration<String> reqRanges = null;
        boolean bl = included = request.getAttribute("jakarta.servlet.include.request_uri") != null;
        if (included) {
            servletPath = this._pathInfoOnly ? "/" : (String)request.getAttribute("jakarta.servlet.include.servlet_path");
            pathInfo = (String)request.getAttribute("jakarta.servlet.include.path_info");
            if (servletPath == null) {
                servletPath = request.getServletPath();
                pathInfo = request.getPathInfo();
            }
        } else {
            servletPath = this._pathInfoOnly ? "/" : request.getServletPath();
            pathInfo = request.getPathInfo();
            reqRanges = request.getHeaders(HttpHeader.RANGE.asString());
            if (!this.hasDefinedRange(reqRanges)) {
                reqRanges = null;
            }
        }
        Object pathInContext = URIUtil.addPaths(servletPath, pathInfo);
        boolean endsWithSlash = (pathInfo == null ? (this._pathInfoOnly ? "" : servletPath) : pathInfo).endsWith("/");
        boolean checkPrecompressedVariants = this._precompressedFormats.length > 0 && !endsWithSlash && !included && reqRanges == null;
        HttpContent content = null;
        boolean releaseContent = true;
        try {
            Map<CompressedContentFormat, ? extends HttpContent> precompressedContents;
            content = this._contentFactory.getContent((String)pathInContext, response.getBufferSize());
            if (LOG.isDebugEnabled()) {
                LOG.debug("content={}", (Object)content);
            }
            if (content == null || !content.getResource().exists()) {
                if (included) {
                    throw new FileNotFoundException("!" + (String)pathInContext);
                }
                this.notFound(request, response);
                boolean bl2 = response.isCommitted();
                return bl2;
            }
            if (content.getResource().isDirectory()) {
                this.sendWelcome(content, (String)pathInContext, endsWithSlash, included, request, response);
                boolean bl3 = true;
                return bl3;
            }
            if (!included && endsWithSlash && ((String)pathInContext).length() > 1) {
                String q = request.getQueryString();
                pathInContext = ((String)pathInContext).substring(0, ((String)pathInContext).length() - 1);
                if (q != null && q.length() != 0) {
                    pathInContext = (String)pathInContext + "?" + q;
                }
                response.sendRedirect(response.encodeRedirectURL(URIUtil.addPaths(request.getContextPath(), (String)pathInContext)));
                boolean bl4 = true;
                return bl4;
            }
            if (!included && !this.passConditionalHeaders(request, response, content)) {
                boolean q = true;
                return q;
            }
            Map<CompressedContentFormat, ? extends HttpContent> map = precompressedContents = checkPrecompressedVariants ? content.getPrecompressedContents() : null;
            if (precompressedContents != null && precompressedContents.size() > 0) {
                response.addHeader(HttpHeader.VARY.asString(), HttpHeader.ACCEPT_ENCODING.asString());
                List<String> preferredEncodings = this.getPreferredEncodingOrder(request);
                CompressedContentFormat precompressedContentEncoding = this.getBestPrecompressedContent(preferredEncodings, precompressedContents.keySet());
                if (precompressedContentEncoding != null) {
                    HttpContent precompressedContent = precompressedContents.get(precompressedContentEncoding);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("precompressed={}", (Object)precompressedContent);
                    }
                    content = precompressedContent;
                    response.setHeader(HttpHeader.CONTENT_ENCODING.asString(), precompressedContentEncoding.getEncoding());
                }
            }
            if (this.isGzippedContent((String)pathInContext)) {
                response.setHeader(HttpHeader.CONTENT_ENCODING.asString(), "gzip");
            }
            releaseContent = this.sendData(request, response, included, content, reqRanges);
        }
        catch (InvalidPathException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("InvalidPathException for pathInContext: {}", pathInContext, (Object)e);
            }
            if (included) {
                throw new FileNotFoundException("!" + (String)pathInContext);
            }
            this.notFound(request, response);
            boolean bl5 = response.isCommitted();
            return bl5;
        }
        catch (IllegalArgumentException e) {
            LOG.warn("Failed to serve resource: {}", pathInContext, (Object)e);
            if (!response.isCommitted()) {
                response.sendError(500, e.getMessage());
            }
        }
        finally {
            if (releaseContent && content != null) {
                content.release();
            }
        }
        return true;
    }

    private List<String> getPreferredEncodingOrder(HttpServletRequest request) {
        List<String> values2;
        Enumeration<String> headers = request.getHeaders(HttpHeader.ACCEPT_ENCODING.asString());
        if (!headers.hasMoreElements()) {
            return Collections.emptyList();
        }
        String key = headers.nextElement();
        if (headers.hasMoreElements()) {
            StringBuilder sb = new StringBuilder(key.length() * 2);
            sb.append(key);
            do {
                sb.append(',').append(headers.nextElement());
            } while (headers.hasMoreElements());
            key = sb.toString();
        }
        if ((values2 = this._preferredEncodingOrderCache.get(key)) == null) {
            QuotedQualityCSV encodingQualityCSV = new QuotedQualityCSV(this._preferredEncodingOrder);
            encodingQualityCSV.addValue(key);
            values2 = encodingQualityCSV.getValues();
            if (this._preferredEncodingOrderCache.size() > this._encodingCacheSize) {
                this._preferredEncodingOrderCache.clear();
            }
            this._preferredEncodingOrderCache.put(key, values2);
        }
        return values2;
    }

    private CompressedContentFormat getBestPrecompressedContent(List<String> preferredEncodings, Collection<CompressedContentFormat> availableFormats) {
        if (availableFormats.isEmpty()) {
            return null;
        }
        for (String encoding : preferredEncodings) {
            for (CompressedContentFormat format : availableFormats) {
                if (!format.getEncoding().equals(encoding)) continue;
                return format;
            }
            if ("*".equals(encoding)) {
                return availableFormats.iterator().next();
            }
            if (!HttpHeaderValue.IDENTITY.asString().equals(encoding)) continue;
            return null;
        }
        return null;
    }

    protected void sendWelcome(HttpContent content, String pathInContext, boolean endsWithSlash, boolean included, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String welcome;
        if (!endsWithSlash) {
            StringBuilder buf = new StringBuilder(request.getRequestURI());
            int param2 = buf.lastIndexOf(";");
            if (param2 < 0 || buf.lastIndexOf("/", param2) > 0) {
                buf.append('/');
            } else {
                buf.insert(param2, '/');
            }
            String q = request.getQueryString();
            if (q != null && q.length() != 0) {
                buf.append('?');
                buf.append(q);
            }
            response.setContentLength(0);
            response.sendRedirect(response.encodeRedirectURL(buf.toString()));
            return;
        }
        String string = welcome = this._welcomeFactory == null ? null : this._welcomeFactory.getWelcomeFile(pathInContext);
        if (welcome != null) {
            String servletPath;
            String string2 = servletPath = included ? (String)request.getAttribute("jakarta.servlet.include.servlet_path") : request.getServletPath();
            if (this._pathInfoOnly) {
                welcome = URIUtil.addPaths(servletPath, welcome);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("welcome={}", (Object)welcome);
            }
            ServletContext context = request.getServletContext();
            if (this._redirectWelcome || context == null) {
                response.setContentLength(0);
                Object uri = URIUtil.encodePath(URIUtil.addPaths(request.getContextPath(), welcome));
                String q = request.getQueryString();
                if (q != null && !q.isEmpty()) {
                    uri = (String)uri + "?" + q;
                }
                response.sendRedirect(response.encodeRedirectURL((String)uri));
                return;
            }
            RequestDispatcher dispatcher = context.getRequestDispatcher(URIUtil.encodePath(welcome));
            if (dispatcher != null) {
                if (included) {
                    dispatcher.include(request, response);
                } else {
                    request.setAttribute("org.eclipse.jetty.server.welcome", welcome);
                    dispatcher.forward(request, response);
                }
            }
            return;
        }
        if (included || this.passConditionalHeaders(request, response, content)) {
            this.sendDirectory(request, response, content.getResource(), pathInContext);
        }
    }

    protected boolean isGzippedContent(String path) {
        if (path == null || this._gzipEquivalentFileExtensions == null) {
            return false;
        }
        for (String suffix : this._gzipEquivalentFileExtensions) {
            if (!path.endsWith(suffix)) continue;
            return true;
        }
        return false;
    }

    private boolean hasDefinedRange(Enumeration<String> reqRanges) {
        return reqRanges != null && reqRanges.hasMoreElements();
    }

    protected void notFound(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(404);
    }

    protected void sendStatus(HttpServletResponse response, int status, Supplier<String> etag) throws IOException {
        response.setStatus(status);
        if (this._etags && etag != null) {
            response.setHeader(HttpHeader.ETAG.asString(), etag.get());
        }
        response.flushBuffer();
    }

    protected boolean passConditionalHeaders(HttpServletRequest request, HttpServletResponse response, HttpContent content) throws IOException {
        try {
            long lm;
            long ifumsl;
            String ifm = null;
            String ifnm = null;
            String ifms = null;
            String ifums = null;
            if (request instanceof Request) {
                for (HttpField field2 : ((Request)request).getHttpFields()) {
                    if (field2.getHeader() == null) continue;
                    switch (field2.getHeader()) {
                        case IF_MATCH: {
                            ifm = field2.getValue();
                            break;
                        }
                        case IF_NONE_MATCH: {
                            ifnm = field2.getValue();
                            break;
                        }
                        case IF_MODIFIED_SINCE: {
                            ifms = field2.getValue();
                            break;
                        }
                        case IF_UNMODIFIED_SINCE: {
                            ifums = field2.getValue();
                            break;
                        }
                    }
                }
            } else {
                ifm = request.getHeader(HttpHeader.IF_MATCH.asString());
                ifnm = request.getHeader(HttpHeader.IF_NONE_MATCH.asString());
                ifms = request.getHeader(HttpHeader.IF_MODIFIED_SINCE.asString());
                ifums = request.getHeader(HttpHeader.IF_UNMODIFIED_SINCE.asString());
            }
            if (this._etags) {
                String etag = content.getETagValue();
                if (ifm != null) {
                    boolean match = false;
                    if (etag != null) {
                        QuotedCSV quoted = new QuotedCSV(true, ifm);
                        Iterator<String> iterator2 = quoted.iterator();
                        while (iterator2.hasNext()) {
                            String etagWithSuffix = iterator2.next();
                            if (!CompressedContentFormat.tagEquals(etag, etagWithSuffix)) continue;
                            match = true;
                            break;
                        }
                    }
                    if (!match) {
                        this.sendStatus(response, 412, null);
                        return false;
                    }
                }
                if (ifnm != null && etag != null) {
                    if (CompressedContentFormat.tagEquals(etag, ifnm) && ifnm.indexOf(44) < 0) {
                        this.sendStatus(response, 304, ifnm::toString);
                        return false;
                    }
                    QuotedCSV quoted = new QuotedCSV(true, ifnm);
                    for (String tag : quoted) {
                        if (!CompressedContentFormat.tagEquals(etag, tag)) continue;
                        this.sendStatus(response, 304, tag::toString);
                        return false;
                    }
                    return true;
                }
            }
            if (ifms != null && ifnm == null) {
                long lm2;
                String mdlm = content.getLastModifiedValue();
                if (ifms.equals(mdlm)) {
                    this.sendStatus(response, 304, content::getETagValue);
                    return false;
                }
                long ifmsl = DateParser.parseDate(ifms);
                if (ifmsl != -1L && (lm2 = content.getResource().lastModified()) != -1L && lm2 / 1000L <= ifmsl / 1000L) {
                    this.sendStatus(response, 304, content::getETagValue);
                    return false;
                }
            }
            if (ifums != null && ifm == null && (ifumsl = DateParser.parseDate(ifums)) != -1L && (lm = content.getResource().lastModified()) != -1L && lm / 1000L > ifumsl / 1000L) {
                response.sendError(412);
                return false;
            }
        }
        catch (IllegalArgumentException iae) {
            if (!response.isCommitted()) {
                response.sendError(400, iae.getMessage());
            }
            throw iae;
        }
        return true;
    }

    protected void sendDirectory(HttpServletRequest request, HttpServletResponse response, Resource resource, String pathInContext) throws IOException {
        if (!this._dirAllowed) {
            response.sendError(403);
            return;
        }
        byte[] data = null;
        String base = URIUtil.addEncodedPaths(request.getRequestURI(), "/");
        String dir = resource.getListHTML(base, pathInContext.length() > 1, request.getQueryString());
        if (dir == null) {
            response.sendError(403, "No directory");
            return;
        }
        data = dir.getBytes(StandardCharsets.UTF_8);
        response.setContentType("text/html;charset=utf-8");
        response.setContentLength(data.length);
        response.getOutputStream().write(data);
    }

    protected boolean sendData(HttpServletRequest request, HttpServletResponse response, boolean include, final HttpContent content, Enumeration<String> reqRanges) throws IOException {
        boolean written;
        OutputStream out;
        long content_length = content.getContentLengthValue();
        try {
            out = response.getOutputStream();
            written = out instanceof HttpOutput ? ((HttpOutput)out).isWritten() : true;
        }
        catch (IllegalStateException e) {
            out = new WriterOutputStream(response.getWriter());
            written = true;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("sendData content=%s out=%s async=%b", content, out, request.isAsyncSupported()));
        }
        if (reqRanges == null || !reqRanges.hasMoreElements() || content_length < 0L) {
            if (include) {
                ResourceService.writeContent(content, out, 0L, content_length);
            } else if (written) {
                this.putHeaders(response, content, -1L);
                ResourceService.writeContent(content, out, 0L, content_length);
            } else {
                this.putHeaders(response, content, -2L);
                if (request.isAsyncSupported()) {
                    final AsyncContext context = request.startAsync();
                    context.setTimeout(0L);
                    ((HttpOutput)out).sendContent(content, new Callback(){

                        @Override
                        public void succeeded() {
                            context.complete();
                            content.release();
                        }

                        @Override
                        public void failed(Throwable x) {
                            String msg = "Failed to send content";
                            if (x instanceof IOException) {
                                LOG.debug(msg, x);
                            } else {
                                LOG.warn(msg, x);
                            }
                            context.complete();
                            content.release();
                        }

                        @Override
                        public Invocable.InvocationType getInvocationType() {
                            return Invocable.InvocationType.NON_BLOCKING;
                        }

                        public String toString() {
                            return String.format("ResourceService@%x$CB", ResourceService.this.hashCode());
                        }
                    });
                    return false;
                }
                ((HttpOutput)out).sendContent(content);
            }
        } else {
            List<InclusiveByteRange> ranges = InclusiveByteRange.satisfiableRanges(reqRanges, content_length);
            if (ranges == null || ranges.size() == 0) {
                this.putHeaders(response, content, -2L);
                response.setHeader(HttpHeader.CONTENT_RANGE.asString(), InclusiveByteRange.to416HeaderRangeString(content_length));
                this.sendStatus(response, 416, null);
                return true;
            }
            if (ranges.size() == 1) {
                InclusiveByteRange singleSatisfiableRange = ranges.iterator().next();
                long singleLength = singleSatisfiableRange.getSize();
                this.putHeaders(response, content, singleLength);
                response.setStatus(206);
                if (!response.containsHeader(HttpHeader.DATE.asString())) {
                    response.addDateHeader(HttpHeader.DATE.asString(), System.currentTimeMillis());
                }
                response.setHeader(HttpHeader.CONTENT_RANGE.asString(), singleSatisfiableRange.toHeaderRangeString(content_length));
                ResourceService.writeContent(content, out, singleSatisfiableRange.getFirst(), singleLength);
                return true;
            }
            this.putHeaders(response, content, -1L);
            String mimetype = content.getContentTypeValue();
            if (mimetype == null) {
                LOG.warn("Unknown mimetype for {}", (Object)request.getRequestURI());
            }
            response.setStatus(206);
            if (!response.containsHeader(HttpHeader.DATE.asString())) {
                response.addDateHeader(HttpHeader.DATE.asString(), System.currentTimeMillis());
            }
            String ctp = request.getHeader(HttpHeader.REQUEST_RANGE.asString()) != null ? "multipart/x-byteranges; boundary=" : "multipart/byteranges; boundary=";
            MultiPartOutputStream multi = new MultiPartOutputStream(out);
            response.setContentType(ctp + multi.getBoundary());
            int length = 0;
            String[] header = new String[ranges.size()];
            int i = 0;
            int CRLF = "\r\n".length();
            int DASHDASH = "--".length();
            int BOUNDARY = multi.getBoundary().length();
            int FIELD_SEP = ": ".length();
            for (InclusiveByteRange ibr : ranges) {
                header[i] = ibr.toHeaderRangeString(content_length);
                if (i > 0) {
                    length += CRLF;
                }
                length += DASHDASH + BOUNDARY + CRLF;
                if (mimetype != null) {
                    length += HttpHeader.CONTENT_TYPE.asString().length() + FIELD_SEP + mimetype.length() + CRLF;
                }
                length += HttpHeader.CONTENT_RANGE.asString().length() + FIELD_SEP + header[i].length() + CRLF;
                length += CRLF;
                length = (int)((long)length + ibr.getSize());
                ++i;
            }
            response.setContentLength(length += CRLF + DASHDASH + BOUNDARY + DASHDASH + CRLF);
            try (RangeWriter rangeWriter = HttpContentRangeWriter.newRangeWriter(content);){
                i = 0;
                for (InclusiveByteRange ibr : ranges) {
                    multi.startPart(mimetype, new String[]{HttpHeader.CONTENT_RANGE + ": " + header[i]});
                    rangeWriter.writeTo(multi, ibr.getFirst(), ibr.getSize());
                    ++i;
                }
            }
            multi.close();
        }
        return true;
    }

    private static void writeContent(HttpContent content, OutputStream out, long start2, long contentLength) throws IOException {
        if (start2 == 0L && content.getResource().length() == contentLength) {
            ByteBuffer buffer = content.getIndirectBuffer();
            if (buffer != null) {
                BufferUtil.writeTo(buffer, out);
                return;
            }
            try (InputStream input = content.getResource().getInputStream();){
                IO.copy(input, out);
                return;
            }
        }
        try (InputStreamRangeWriter rangeWriter = new InputStreamRangeWriter(() -> content.getInputStream());){
            rangeWriter.writeTo(out, start2, contentLength);
        }
    }

    protected void putHeaders(HttpServletResponse response, HttpContent content, long contentLength) {
        if (response instanceof Response) {
            Response r = (Response)response;
            r.putHeaders(content, contentLength, this._etags);
            HttpFields.Mutable fields = r.getHttpFields();
            if (this._acceptRanges && !fields.contains(HttpHeader.ACCEPT_RANGES)) {
                fields.add(ACCEPT_RANGES);
            }
            if (this._cacheControl != null && !fields.contains(HttpHeader.CACHE_CONTROL)) {
                fields.add(this._cacheControl);
            }
        } else {
            Response.putHeaders(response, content, contentLength, this._etags);
            if (this._acceptRanges && !response.containsHeader(HttpHeader.ACCEPT_RANGES.asString())) {
                response.setHeader(ACCEPT_RANGES.getName(), ACCEPT_RANGES.getValue());
            }
            if (this._cacheControl != null && !response.containsHeader(HttpHeader.CACHE_CONTROL.asString())) {
                response.setHeader(this._cacheControl.getName(), this._cacheControl.getValue());
            }
        }
    }

    public static interface WelcomeFactory {
        public String getWelcomeFile(String var1) throws IOException;
    }
}

