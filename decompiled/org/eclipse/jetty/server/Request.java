/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.AsyncListener;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestAttributeEvent;
import jakarta.servlet.ServletRequestAttributeListener;
import jakarta.servlet.ServletRequestWrapper;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpUpgradeHandler;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.PushBuilder;
import jakarta.servlet.http.WebConnection;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.ComplianceViolation;
import org.eclipse.jetty.http.HostPortHttpField;
import org.eclipse.jetty.http.HttpCompliance;
import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpHeaderValue;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpScheme;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.http.UriCompliance;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.RuntimeIOException;
import org.eclipse.jetty.server.AsyncContextEvent;
import org.eclipse.jetty.server.AsyncContextState;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.Cookies;
import org.eclipse.jetty.server.Dispatcher;
import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.HttpChannelOverHttp;
import org.eclipse.jetty.server.HttpChannelState;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnection;
import org.eclipse.jetty.server.HttpInput;
import org.eclipse.jetty.server.MultiPartFormDataCompliance;
import org.eclipse.jetty.server.MultiParts;
import org.eclipse.jetty.server.PushBuilderImpl;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServletAttributes;
import org.eclipse.jetty.server.ServletPathMapping;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.session.Session;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.util.Attributes;
import org.eclipse.jetty.util.AttributesMap;
import org.eclipse.jetty.util.HostPort;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.UrlEncoded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Request
implements HttpServletRequest {
    public static final String __MULTIPART_CONFIG_ELEMENT = "org.eclipse.jetty.multipartConfig";
    private static final Logger LOG = LoggerFactory.getLogger(Request.class);
    private static final Collection<Locale> __defaultLocale = Collections.singleton(Locale.getDefault());
    private static final int INPUT_NONE = 0;
    private static final int INPUT_STREAM = 1;
    private static final int INPUT_READER = 2;
    private static final MultiMap<String> NO_PARAMS = new MultiMap();
    private static final MultiMap<String> BAD_PARAMS = new MultiMap();
    private final HttpChannel _channel;
    private final List<ServletRequestAttributeListener> _requestAttributeListeners = new ArrayList<ServletRequestAttributeListener>();
    private final HttpInput _input;
    private MetaData.Request _metaData;
    private HttpFields _httpFields;
    private HttpFields _trailers;
    private HttpURI _uri;
    private String _method;
    private String _pathInContext;
    private ServletPathMapping _servletPathMapping;
    private Object _asyncNotSupportedSource = null;
    private boolean _secure;
    private boolean _newContext;
    private boolean _cookiesExtracted = false;
    private boolean _handled = false;
    private boolean _contentParamsExtracted;
    private boolean _requestedSessionIdFromCookie = false;
    private Attributes _attributes;
    private Authentication _authentication;
    private String _contentType;
    private String _characterEncoding;
    private ContextHandler.Context _context;
    private ContextHandler.Context _errorContext;
    private Cookies _cookies;
    private DispatcherType _dispatcherType;
    private int _inputState = 0;
    private BufferedReader _reader;
    private String _readerEncoding;
    private MultiMap<String> _queryParameters;
    private MultiMap<String> _contentParameters;
    private MultiMap<String> _parameters;
    private Charset _queryEncoding;
    private InetSocketAddress _remote;
    private String _requestedSessionId;
    private UserIdentity.Scope _scope;
    private HttpSession _session;
    private SessionHandler _sessionHandler;
    private long _timeStamp;
    private MultiParts _multiParts;
    private AsyncContextState _async;
    private List<Session> _sessions;
    private static final EnumSet<HttpHeader> NOT_PUSHED_HEADERS = EnumSet.of(HttpHeader.IF_MATCH, new HttpHeader[]{HttpHeader.IF_RANGE, HttpHeader.IF_UNMODIFIED_SINCE, HttpHeader.RANGE, HttpHeader.EXPECT, HttpHeader.REFERER, HttpHeader.COOKIE, HttpHeader.AUTHORIZATION, HttpHeader.IF_NONE_MATCH, HttpHeader.IF_MODIFIED_SINCE});

    private static boolean isNoParams(MultiMap<String> inputParameters) {
        boolean isNoParams = inputParameters == NO_PARAMS;
        return isNoParams;
    }

    public static Request getBaseRequest(ServletRequest request) {
        if (request instanceof Request) {
            return (Request)request;
        }
        Object channel = request.getAttribute(HttpChannel.class.getName());
        if (channel instanceof HttpChannel) {
            return ((HttpChannel)channel).getRequest();
        }
        while (request instanceof ServletRequestWrapper) {
            request = ((ServletRequestWrapper)request).getRequest();
        }
        if (request instanceof Request) {
            return (Request)request;
        }
        return null;
    }

    public Request(HttpChannel channel, HttpInput input) {
        this._channel = channel;
        this._input = input;
    }

    public HttpFields getHttpFields() {
        return this._httpFields;
    }

    public void setHttpFields(HttpFields fields) {
        this._httpFields = fields.asImmutable();
    }

    @Override
    public Map<String, String> getTrailerFields() {
        HttpFields trailersFields = this.getTrailerHttpFields();
        if (trailersFields == null) {
            return Collections.emptyMap();
        }
        HashMap<String, String> trailers = new HashMap<String, String>();
        for (HttpField field2 : trailersFields) {
            String key;
            String value = (String)trailers.get(key = field2.getName().toLowerCase());
            trailers.put(key, (String)(value == null ? field2.getValue() : value + "," + field2.getValue()));
        }
        return trailers;
    }

    public void setTrailerHttpFields(HttpFields trailers) {
        this._trailers = trailers == null ? null : trailers.asImmutable();
    }

    public HttpFields getTrailerHttpFields() {
        return this._trailers;
    }

    public HttpInput getHttpInput() {
        return this._input;
    }

    public boolean isPush() {
        return Boolean.TRUE.equals(this.getAttribute("org.eclipse.jetty.pushed"));
    }

    public boolean isPushSupported() {
        return !this.isPush() && this.getHttpChannel().getHttpTransport().isPushSupported();
    }

    @Override
    public PushBuilder newPushBuilder() {
        String id;
        if (!this.isPushSupported()) {
            return null;
        }
        HttpFields.Mutable fields = HttpFields.build(this.getHttpFields(), NOT_PUSHED_HEADERS);
        HttpField authField = this.getHttpFields().getField(HttpHeader.AUTHORIZATION);
        if (authField != null && this.getUserPrincipal() != null && authField.getValue().startsWith("Basic")) {
            fields.add(authField);
        }
        try {
            HttpSession session = this.getSession();
            if (session != null) {
                session.getLastAccessedTime();
                id = session.getId();
            } else {
                id = this.getRequestedSessionId();
            }
        }
        catch (IllegalStateException e) {
            id = this.getRequestedSessionId();
        }
        HashMap<String, String> cookies = new HashMap<String, String>();
        Cookie[] existingCookies = this.getCookies();
        if (existingCookies != null) {
            for (Cookie c : this.getCookies()) {
                cookies.put(c.getName(), c.getValue());
            }
        }
        HttpFields.Mutable responseFields = this.getResponse().getHttpFields();
        for (HttpField field2 : responseFields) {
            HttpCookie cookie;
            HttpHeader header = field2.getHeader();
            if (header != HttpHeader.SET_COOKIE) continue;
            HttpCookie httpCookie = cookie = field2 instanceof HttpCookie.SetCookieHttpField ? ((HttpCookie.SetCookieHttpField)field2).getHttpCookie() : new HttpCookie(field2.getValue());
            if (cookie.getMaxAge() > 0L) {
                cookies.put(cookie.getName(), cookie.getValue());
                continue;
            }
            cookies.remove(cookie.getName());
        }
        if (!cookies.isEmpty()) {
            StringBuilder buff = new StringBuilder();
            for (Map.Entry entry : cookies.entrySet()) {
                if (buff.length() > 0) {
                    buff.append("; ");
                }
                buff.append((String)entry.getKey()).append('=').append((String)entry.getValue());
            }
            fields.add(new HttpField(HttpHeader.COOKIE, buff.toString()));
        }
        PushBuilderImpl builder = new PushBuilderImpl(this, fields, this.getMethod(), this.getQueryString(), id);
        builder.addHeader("referer", this.getRequestURL().toString());
        return builder;
    }

    public void addEventListener(EventListener listener) {
        if (listener instanceof ServletRequestAttributeListener) {
            this._requestAttributeListeners.add((ServletRequestAttributeListener)listener);
        }
        if (listener instanceof AsyncListener) {
            throw new IllegalArgumentException(listener.getClass().toString());
        }
    }

    public void enterSession(HttpSession s2) {
        if (!(s2 instanceof Session)) {
            return;
        }
        if (this._sessions == null) {
            this._sessions = new ArrayList<Session>();
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Request {} entering session={}", (Object)this, (Object)s2);
        }
        this._sessions.add((Session)s2);
    }

    private void leaveSession(Session session) {
        ServletContext ctx;
        ContextHandler handler;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Request {} leaving session {}", (Object)this, (Object)session);
        }
        if ((handler = ContextHandler.getContextHandler(ctx = session.getServletContext())) == null) {
            session.getSessionHandler().complete(session);
        } else {
            handler.handle(this, () -> session.getSessionHandler().complete(session));
        }
    }

    private void commitSession(Session session) {
        ServletContext ctx;
        ContextHandler handler;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Response {} committing for session {}", (Object)this, (Object)session);
        }
        if ((handler = ContextHandler.getContextHandler(ctx = session.getServletContext())) == null) {
            session.getSessionHandler().commit(session);
        } else {
            handler.handle(this, () -> session.getSessionHandler().commit(session));
        }
    }

    private MultiMap<String> getParameters() {
        if (!this._contentParamsExtracted) {
            this._contentParamsExtracted = true;
            if (this._contentParameters == null) {
                try {
                    this.extractContentParameters();
                }
                catch (IllegalArgumentException | IllegalStateException e) {
                    LOG.warn(e.toString());
                    throw new BadMessageException("Unable to parse form content", e);
                }
            }
        }
        if (this._queryParameters == null) {
            this.extractQueryParameters();
        }
        if (Request.isNoParams(this._queryParameters) || this._queryParameters.size() == 0) {
            this._parameters = this._contentParameters;
        } else if (Request.isNoParams(this._contentParameters) || this._contentParameters.size() == 0) {
            this._parameters = this._queryParameters;
        } else if (this._parameters == null) {
            this._parameters = new MultiMap();
            this._parameters.addAllValues(this._queryParameters);
            this._parameters.addAllValues(this._contentParameters);
        }
        MultiMap<String> parameters = this._parameters;
        return parameters == null ? NO_PARAMS : parameters;
    }

    private void extractQueryParameters() {
        if (this._uri == null || StringUtil.isEmpty(this._uri.getQuery())) {
            this._queryParameters = NO_PARAMS;
        } else {
            try {
                this._queryParameters = new MultiMap();
                UrlEncoded.decodeTo(this._uri.getQuery(), this._queryParameters, this._queryEncoding);
            }
            catch (IllegalArgumentException | IllegalStateException e) {
                this._queryParameters = BAD_PARAMS;
                throw new BadMessageException("Unable to parse URI query", e);
            }
        }
    }

    private boolean isContentEncodingSupported() {
        String contentEncoding = this.getHttpFields().get(HttpHeader.CONTENT_ENCODING);
        if (contentEncoding == null) {
            return true;
        }
        return HttpHeaderValue.IDENTITY.is(contentEncoding);
    }

    private void extractContentParameters() {
        String contentType = this.getContentType();
        if (contentType == null || contentType.isEmpty()) {
            this._contentParameters = NO_PARAMS;
        } else {
            this._contentParameters = new MultiMap();
            int contentLength = this.getContentLength();
            if (contentLength != 0 && this._inputState == 0) {
                String baseType = HttpField.valueParameters(contentType, null);
                if (MimeTypes.Type.FORM_ENCODED.is(baseType) && this._channel.getHttpConfiguration().isFormEncodedMethod(this.getMethod())) {
                    if (this._metaData != null && !this.isContentEncodingSupported()) {
                        throw new BadMessageException(415, "Unsupported Content-Encoding");
                    }
                    this.extractFormParameters(this._contentParameters);
                } else if (MimeTypes.Type.MULTIPART_FORM_DATA.is(baseType) && this.getAttribute(__MULTIPART_CONFIG_ELEMENT) != null && this._multiParts == null) {
                    try {
                        if (this._metaData != null && !this.isContentEncodingSupported()) {
                            throw new BadMessageException(415, "Unsupported Content-Encoding");
                        }
                        this.getParts(this._contentParameters);
                    }
                    catch (IOException e) {
                        String msg = "Unable to extract content parameters";
                        if (LOG.isDebugEnabled()) {
                            LOG.debug(msg, e);
                        }
                        throw new RuntimeIOException(msg, e);
                    }
                }
            }
        }
    }

    public void extractFormParameters(MultiMap<String> params) {
        try {
            int maxFormContentSize = 200000;
            int maxFormKeys = 1000;
            if (this._context != null) {
                ContextHandler contextHandler = this._context.getContextHandler();
                maxFormContentSize = contextHandler.getMaxFormContentSize();
                maxFormKeys = contextHandler.getMaxFormKeys();
            } else {
                maxFormContentSize = this.lookupServerAttribute("org.eclipse.jetty.server.Request.maxFormContentSize", maxFormContentSize);
                maxFormKeys = this.lookupServerAttribute("org.eclipse.jetty.server.Request.maxFormKeys", maxFormKeys);
            }
            int contentLength = this.getContentLength();
            if (maxFormContentSize >= 0 && contentLength > maxFormContentSize) {
                throw new IllegalStateException("Form is larger than max length " + maxFormContentSize);
            }
            ServletInputStream in = this.getInputStream();
            if (this._input.isAsync()) {
                throw new IllegalStateException("Cannot extract parameters with async IO");
            }
            UrlEncoded.decodeTo((InputStream)in, params, this.getCharacterEncoding(), maxFormContentSize, maxFormKeys);
        }
        catch (IOException e) {
            String msg = "Unable to extract form parameters";
            if (LOG.isDebugEnabled()) {
                LOG.debug(msg, e);
            }
            throw new RuntimeIOException(msg, e);
        }
    }

    private int lookupServerAttribute(String key, int dftValue) {
        Object attribute = this._channel.getServer().getAttribute(key);
        if (attribute instanceof Number) {
            return ((Number)attribute).intValue();
        }
        if (attribute instanceof String) {
            return Integer.parseInt((String)attribute);
        }
        return dftValue;
    }

    @Override
    public AsyncContext getAsyncContext() {
        HttpChannelState state = this.getHttpChannelState();
        if (this._async == null || !state.isAsyncStarted()) {
            throw new IllegalStateException(state.getStatusString());
        }
        return this._async;
    }

    public HttpChannelState getHttpChannelState() {
        return this._channel.getState();
    }

    public ComplianceViolation.Listener getComplianceViolationListener() {
        if (this._channel instanceof ComplianceViolation.Listener) {
            return (ComplianceViolation.Listener)((Object)this._channel);
        }
        ComplianceViolation.Listener listener = this._channel.getConnector().getBean(ComplianceViolation.Listener.class);
        if (listener == null) {
            listener = this._channel.getServer().getBean(ComplianceViolation.Listener.class);
        }
        return listener;
    }

    @Override
    public Object getAttribute(String name) {
        if (name.startsWith("org.eclipse.jetty")) {
            if (Server.class.getName().equals(name)) {
                return this._channel.getServer();
            }
            if (HttpChannel.class.getName().equals(name)) {
                return this._channel;
            }
            if (HttpConnection.class.getName().equals(name) && this._channel.getHttpTransport() instanceof HttpConnection) {
                return this._channel.getHttpTransport();
            }
        }
        return this._attributes == null ? null : this._attributes.getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        if (this._attributes == null) {
            return Collections.enumeration(Collections.emptyList());
        }
        return AttributesMap.getAttributeNamesCopy(this._attributes);
    }

    public Attributes getAttributes() {
        if (this._attributes == null) {
            this._attributes = new ServletAttributes();
        }
        return this._attributes;
    }

    public Authentication getAuthentication() {
        return this._authentication;
    }

    @Override
    public String getAuthType() {
        if (this._authentication instanceof Authentication.Deferred) {
            this.setAuthentication(((Authentication.Deferred)this._authentication).authenticate(this));
        }
        if (this._authentication instanceof Authentication.User) {
            return ((Authentication.User)this._authentication).getAuthMethod();
        }
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        if (this._characterEncoding == null) {
            String contentType;
            if (this._context != null) {
                this._characterEncoding = this._context.getRequestCharacterEncoding();
            }
            if (this._characterEncoding == null && (contentType = this.getContentType()) != null) {
                String charset;
                MimeTypes.Type mime = MimeTypes.CACHE.get(contentType);
                String string = charset = mime == null || mime.getCharset() == null ? MimeTypes.getCharsetFromContentType(contentType) : mime.getCharset().toString();
                if (charset != null) {
                    this._characterEncoding = charset;
                }
            }
        }
        return this._characterEncoding;
    }

    public HttpChannel getHttpChannel() {
        return this._channel;
    }

    @Override
    public int getContentLength() {
        long contentLength = this.getContentLengthLong();
        if (contentLength > Integer.MAX_VALUE) {
            return -1;
        }
        return (int)contentLength;
    }

    @Override
    public long getContentLengthLong() {
        if (this._httpFields == null) {
            return -1L;
        }
        return this._httpFields.getLongField(HttpHeader.CONTENT_LENGTH);
    }

    public long getContentRead() {
        return this._input.getContentReceived();
    }

    @Override
    public String getContentType() {
        if (this._contentType == null) {
            MetaData.Request metadata = this._metaData;
            this._contentType = metadata == null ? null : metadata.getFields().get(HttpHeader.CONTENT_TYPE);
        }
        return this._contentType;
    }

    public ContextHandler.Context getContext() {
        return this._context;
    }

    public ContextHandler.Context getErrorContext() {
        ContextHandler handler;
        if (this.isAsyncStarted() && (handler = this._channel.getState().getContextHandler()) != null) {
            return handler.getServletContext();
        }
        return this._errorContext;
    }

    @Override
    public String getContextPath() {
        Dispatcher.IncludeAttributes include;
        ContextHandler.Context context = this._dispatcherType == DispatcherType.INCLUDE ? ((include = Attributes.unwrap(this._attributes, Dispatcher.IncludeAttributes.class)) == null ? this._context : include.getSourceContext()) : this._context;
        if (context == null) {
            return null;
        }
        return context.getContextHandler().getRequestContextPath();
    }

    public String getPathInContext() {
        return this._pathInContext;
    }

    @Override
    public Cookie[] getCookies() {
        MetaData.Request metadata = this._metaData;
        if (metadata == null || this._cookiesExtracted) {
            if (this._cookies == null || this._cookies.getCookies().length == 0) {
                return null;
            }
            return this._cookies.getCookies();
        }
        this._cookiesExtracted = true;
        for (HttpField field2 : metadata.getFields()) {
            if (field2.getHeader() != HttpHeader.COOKIE) continue;
            if (this._cookies == null) {
                this._cookies = new Cookies(this.getHttpChannel().getHttpConfiguration().getRequestCookieCompliance(), this.getComplianceViolationListener());
            }
            this._cookies.addCookieField(field2.getValue());
        }
        if (this._cookies == null || this._cookies.getCookies().length == 0) {
            return null;
        }
        return this._cookies.getCookies();
    }

    @Override
    public long getDateHeader(String name) {
        HttpFields fields = this._httpFields;
        return fields == null ? -1L : fields.getDateField(name);
    }

    @Override
    public DispatcherType getDispatcherType() {
        return this._dispatcherType;
    }

    @Override
    public String getHeader(String name) {
        HttpFields fields = this._httpFields;
        return fields == null ? null : fields.get(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        HttpFields fields = this._httpFields;
        return fields == null ? Collections.emptyEnumeration() : fields.getFieldNames();
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        HttpFields fields = this._httpFields;
        if (fields == null) {
            return Collections.emptyEnumeration();
        }
        Enumeration<String> e = fields.getValues(name);
        if (e == null) {
            return Collections.enumeration(Collections.emptyList());
        }
        return e;
    }

    public int getInputState() {
        return this._inputState;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (this._inputState != 0 && this._inputState != 1) {
            throw new IllegalStateException("READER");
        }
        this._inputState = 1;
        if (this._channel.isExpecting100Continue()) {
            this._channel.continue100(this._input.available());
        }
        return this._input;
    }

    @Override
    public int getIntHeader(String name) {
        HttpFields fields = this._httpFields;
        return fields == null ? -1 : (int)fields.getLongField(name);
    }

    @Override
    public Locale getLocale() {
        HttpFields fields = this._httpFields;
        if (fields == null) {
            return Locale.getDefault();
        }
        List<String> acceptable = fields.getQualityCSV(HttpHeader.ACCEPT_LANGUAGE);
        if (acceptable.isEmpty()) {
            return Locale.getDefault();
        }
        String language2 = acceptable.get(0);
        language2 = HttpField.stripParameters(language2);
        String country = "";
        int dash = language2.indexOf(45);
        if (dash > -1) {
            country = language2.substring(dash + 1).trim();
            language2 = language2.substring(0, dash).trim();
        }
        return new Locale(language2, country);
    }

    @Override
    public Enumeration<Locale> getLocales() {
        HttpFields fields = this._httpFields;
        if (fields == null) {
            return Collections.enumeration(__defaultLocale);
        }
        List<String> acceptable = fields.getQualityCSV(HttpHeader.ACCEPT_LANGUAGE);
        if (acceptable.isEmpty()) {
            return Collections.enumeration(__defaultLocale);
        }
        List locales = acceptable.stream().map(language2 -> {
            language2 = HttpField.stripParameters(language2);
            String country = "";
            int dash = language2.indexOf(45);
            if (dash > -1) {
                country = language2.substring(dash + 1).trim();
                language2 = language2.substring(0, dash).trim();
            }
            return new Locale((String)language2, country);
        }).collect(Collectors.toList());
        return Collections.enumeration(locales);
    }

    @Override
    public String getLocalAddr() {
        if (this._channel != null) {
            InetSocketAddress local = this._channel.getLocalAddress();
            if (local == null) {
                return "";
            }
            InetAddress address = local.getAddress();
            String result = address == null ? local.getHostString() : address.getHostAddress();
            return this.formatAddrOrHost(result);
        }
        return "";
    }

    @Override
    public String getLocalName() {
        if (this._channel != null) {
            String localName = this._channel.getLocalName();
            return this.formatAddrOrHost(localName);
        }
        return "";
    }

    @Override
    public int getLocalPort() {
        int localPort;
        if (this._channel != null && (localPort = this._channel.getLocalPort()) > 0) {
            return localPort;
        }
        return 0;
    }

    @Override
    public String getMethod() {
        return this._method;
    }

    @Override
    public String getParameter(String name) {
        return this.getParameters().getValue(name, 0);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(this.getParameters().toStringArrayMap());
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(this.getParameters().keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        List<String> vals = this.getParameters().getValues(name);
        if (vals == null) {
            return null;
        }
        return vals.toArray(new String[0]);
    }

    public MultiMap<String> getQueryParameters() {
        return this._queryParameters;
    }

    public void setQueryParameters(MultiMap<String> queryParameters) {
        this._queryParameters = queryParameters;
    }

    public void setContentParameters(MultiMap<String> contentParameters) {
        this._contentParameters = contentParameters;
    }

    public void resetParameters() {
        this._parameters = null;
    }

    @Override
    public String getPathInfo() {
        ServletPathMapping mapping = this.findServletPathMapping();
        return mapping == null ? this._pathInContext : mapping.getPathInfo();
    }

    @Override
    public String getPathTranslated() {
        String pathInfo = this.getPathInfo();
        if (pathInfo == null || this._context == null) {
            return null;
        }
        return this._context.getRealPath(pathInfo);
    }

    @Override
    public String getProtocol() {
        MetaData.Request metadata = this._metaData;
        if (metadata == null) {
            return null;
        }
        HttpVersion version = metadata.getHttpVersion();
        if (version == null) {
            return null;
        }
        return version.toString();
    }

    public HttpVersion getHttpVersion() {
        MetaData.Request metadata = this._metaData;
        return metadata == null ? null : metadata.getHttpVersion();
    }

    public String getQueryEncoding() {
        return this._queryEncoding == null ? null : this._queryEncoding.name();
    }

    Charset getQueryCharset() {
        return this._queryEncoding;
    }

    @Override
    public String getQueryString() {
        return this._uri == null ? null : this._uri.getQuery();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (this._inputState != 0 && this._inputState != 2) {
            throw new IllegalStateException("STREAMED");
        }
        if (this._inputState == 2) {
            return this._reader;
        }
        String encoding = this.getCharacterEncoding();
        if (encoding == null) {
            encoding = "iso-8859-1";
        }
        if (this._reader == null || !encoding.equalsIgnoreCase(this._readerEncoding)) {
            final ServletInputStream in = this.getInputStream();
            this._readerEncoding = encoding;
            this._reader = new BufferedReader(new InputStreamReader((InputStream)in, encoding)){

                @Override
                public void close() throws IOException {
                    in.close();
                }
            };
        } else if (this._channel.isExpecting100Continue()) {
            this._channel.continue100(this._input.available());
        }
        this._inputState = 2;
        return this._reader;
    }

    @Override
    @Deprecated(since="Servlet API 2.1")
    public String getRealPath(String path) {
        if (this._context == null) {
            return null;
        }
        return this._context.getRealPath(path);
    }

    public InetSocketAddress getRemoteInetSocketAddress() {
        InetSocketAddress remote = this._remote;
        if (remote == null) {
            remote = this._channel.getRemoteAddress();
        }
        return remote;
    }

    @Override
    public String getRemoteAddr() {
        InetSocketAddress remote = this._remote;
        if (remote == null) {
            remote = this._channel.getRemoteAddress();
        }
        if (remote == null) {
            return "";
        }
        InetAddress address = remote.getAddress();
        String result = address == null ? remote.getHostString() : address.getHostAddress();
        return this.formatAddrOrHost(result);
    }

    @Override
    public String getRemoteHost() {
        InetSocketAddress remote = this._remote;
        if (remote == null) {
            remote = this._channel.getRemoteAddress();
        }
        if (remote == null) {
            return "";
        }
        return this.formatAddrOrHost(remote.getHostString());
    }

    @Override
    public int getRemotePort() {
        InetSocketAddress remote = this._remote;
        if (remote == null) {
            remote = this._channel.getRemoteAddress();
        }
        return remote == null ? 0 : remote.getPort();
    }

    @Override
    public String getRemoteUser() {
        Principal p = this.getUserPrincipal();
        if (p == null) {
            return null;
        }
        return p.getName();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        if (path == null || this._context == null) {
            return null;
        }
        if (!path.startsWith("/")) {
            String relTo = this._pathInContext;
            int slash = relTo.lastIndexOf("/");
            relTo = slash > 1 ? relTo.substring(0, slash + 1) : "/";
            path = URIUtil.addPaths(relTo, path);
        }
        return this._context.getRequestDispatcher(path);
    }

    @Override
    public String getRequestedSessionId() {
        return this._requestedSessionId;
    }

    @Override
    public String getRequestURI() {
        return this._uri == null ? null : this._uri.getPath();
    }

    @Override
    public StringBuffer getRequestURL() {
        StringBuffer url2 = new StringBuffer(128);
        URIUtil.appendSchemeHostPort(url2, this.getScheme(), this.getServerName(), this.getServerPort());
        String path = this.getRequestURI();
        if (path != null) {
            url2.append(path);
        }
        return url2;
    }

    public Response getResponse() {
        return this._channel.getResponse();
    }

    public StringBuilder getRootURL() {
        StringBuilder url2 = new StringBuilder(128);
        URIUtil.appendSchemeHostPort(url2, this.getScheme(), this.getServerName(), this.getServerPort());
        return url2;
    }

    @Override
    public String getScheme() {
        return this._uri == null ? "http" : this._uri.getScheme();
    }

    @Override
    public String getServerName() {
        if (this._uri != null && StringUtil.isNotBlank(this._uri.getAuthority())) {
            return this.formatAddrOrHost(this._uri.getHost());
        }
        return this.findServerName();
    }

    private String findServerName() {
        HostPort serverAuthority;
        if (this._channel != null && (serverAuthority = this._channel.getServerAuthority()) != null) {
            return this.formatAddrOrHost(serverAuthority.getHost());
        }
        String name = this.getLocalName();
        if (name != null) {
            return this.formatAddrOrHost(name);
        }
        return "";
    }

    @Override
    public int getServerPort() {
        int port = -1;
        port = this._uri != null && StringUtil.isNotBlank(this._uri.getAuthority()) ? this._uri.getPort() : this.findServerPort();
        if (port <= 0) {
            return HttpScheme.getDefaultPort(this.getScheme());
        }
        return port;
    }

    private int findServerPort() {
        HostPort serverAuthority;
        if (this._channel != null && (serverAuthority = this._channel.getServerAuthority()) != null) {
            return serverAuthority.getPort();
        }
        return this.getLocalPort();
    }

    @Override
    public ServletContext getServletContext() {
        return this._context;
    }

    public String getServletName() {
        if (this._scope != null) {
            return this._scope.getName();
        }
        return null;
    }

    @Override
    public String getServletPath() {
        ServletPathMapping mapping = this.findServletPathMapping();
        return mapping == null ? "" : mapping.getServletPath();
    }

    public ServletResponse getServletResponse() {
        return this._channel.getResponse();
    }

    @Override
    public String changeSessionId() {
        HttpSession session = this.getSession(false);
        if (session == null) {
            throw new IllegalStateException("No session");
        }
        if (session instanceof Session) {
            Session s2 = (Session)session;
            s2.renewId(this);
            if (this.getRemoteUser() != null) {
                s2.setAttribute("org.eclipse.jetty.security.sessionCreatedSecure", Boolean.TRUE);
            }
            if (s2.isIdChanged() && this._sessionHandler.isUsingCookies()) {
                this._channel.getResponse().replaceCookie(this._sessionHandler.getSessionCookie(s2, this.getContextPath(), this.isSecure()));
            }
        }
        return session.getId();
    }

    public void onCompleted() {
        Object requestLog;
        HttpChannel httpChannel = this.getHttpChannel();
        if (httpChannel != null && (requestLog = httpChannel.getRequestLog()) != null) {
            MetaData.Response committedResponse;
            this._contentParamsExtracted = true;
            if (this._contentParameters == null) {
                this._contentParameters = NO_PARAMS;
            }
            if ((committedResponse = this.getResponse().getCommittedMetaData()) != null) {
                this.getResponse().setStatus(committedResponse.getStatus());
            }
            requestLog.log(this, this.getResponse());
        }
        if (this._sessions != null) {
            for (Session s2 : this._sessions) {
                this.leaveSession(s2);
            }
        }
        if (this._multiParts != null) {
            try {
                this._multiParts.close();
            }
            catch (Throwable e) {
                LOG.warn("Errors deleting multipart tmp files", e);
            }
        }
    }

    public void onResponseCommit() {
        if (this._sessions != null) {
            for (Session s2 : this._sessions) {
                this.commitSession(s2);
            }
        }
    }

    public HttpSession getSession(SessionHandler sessionHandler) {
        if (this._sessions == null || this._sessions.size() == 0 || sessionHandler == null) {
            return null;
        }
        HttpSession session = null;
        for (HttpSession httpSession : this._sessions) {
            Session ss = (Session)httpSession;
            if (sessionHandler != ss.getSessionHandler()) continue;
            session = httpSession;
            if (!ss.isValid()) continue;
            return session;
        }
        return session;
    }

    @Override
    public HttpSession getSession() {
        return this.getSession(true);
    }

    @Override
    public HttpSession getSession(boolean create) {
        if (this._session != null) {
            if (this._sessionHandler != null && !this._sessionHandler.isValid(this._session)) {
                this._session = null;
            } else {
                return this._session;
            }
        }
        if (!create) {
            return null;
        }
        if (this.getResponse().isCommitted()) {
            throw new IllegalStateException("Response is committed");
        }
        if (this._sessionHandler == null) {
            throw new IllegalStateException("No SessionManager");
        }
        this._session = this._sessionHandler.newHttpSession(this);
        if (this._session == null) {
            throw new IllegalStateException("Create session failed");
        }
        HttpCookie cookie = this._sessionHandler.getSessionCookie(this._session, this.getContextPath(), this.isSecure());
        if (cookie != null) {
            this._channel.getResponse().replaceCookie(cookie);
        }
        return this._session;
    }

    public SessionHandler getSessionHandler() {
        return this._sessionHandler;
    }

    public long getTimeStamp() {
        return this._timeStamp;
    }

    public HttpURI getHttpURI() {
        return this._uri;
    }

    public void setHttpURI(HttpURI uri) {
        if (this._uri != null && !Objects.equals(this._uri.getQuery(), uri.getQuery()) && this._queryParameters != BAD_PARAMS) {
            this._queryParameters = null;
            this._parameters = null;
        }
        this._uri = uri.asImmutable();
    }

    public String getOriginalURI() {
        MetaData.Request metadata = this._metaData;
        if (metadata == null) {
            return null;
        }
        HttpURI uri = metadata.getURI();
        if (uri == null) {
            return null;
        }
        return uri.isAbsolute() && metadata.getHttpVersion() == HttpVersion.HTTP_2 ? uri.getPathQuery() : uri.toString();
    }

    public UserIdentity getUserIdentity() {
        if (this._authentication instanceof Authentication.Deferred) {
            this.setAuthentication(((Authentication.Deferred)this._authentication).authenticate(this));
        }
        if (this._authentication instanceof Authentication.User) {
            return ((Authentication.User)this._authentication).getUserIdentity();
        }
        return null;
    }

    public UserIdentity getResolvedUserIdentity() {
        if (this._authentication instanceof Authentication.User) {
            return ((Authentication.User)this._authentication).getUserIdentity();
        }
        return null;
    }

    public UserIdentity.Scope getUserIdentityScope() {
        return this._scope;
    }

    @Override
    public Principal getUserPrincipal() {
        if (this._authentication instanceof Authentication.Deferred) {
            this.setAuthentication(((Authentication.Deferred)this._authentication).authenticate(this));
        }
        if (this._authentication instanceof Authentication.User) {
            UserIdentity user = ((Authentication.User)this._authentication).getUserIdentity();
            return user.getUserPrincipal();
        }
        return null;
    }

    public boolean isHandled() {
        return this._handled;
    }

    @Override
    public boolean isAsyncStarted() {
        return this.getHttpChannelState().isAsyncStarted();
    }

    @Override
    public boolean isAsyncSupported() {
        return this._asyncNotSupportedSource == null;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return this._requestedSessionId != null && this._requestedSessionIdFromCookie;
    }

    @Override
    @Deprecated(since="Servlet API 2.1")
    public boolean isRequestedSessionIdFromUrl() {
        return this._requestedSessionId != null && !this._requestedSessionIdFromCookie;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return this._requestedSessionId != null && !this._requestedSessionIdFromCookie;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        if (this._requestedSessionId == null) {
            return false;
        }
        HttpSession session = this.getSession(false);
        return session != null && this._sessionHandler.getSessionIdManager().getId(this._requestedSessionId).equals(this._sessionHandler.getId(session));
    }

    @Override
    public boolean isSecure() {
        return this._secure;
    }

    public void setSecure(boolean secure) {
        this._secure = secure;
    }

    public long getBeginNanoTime() {
        return this._metaData.getBeginNanoTime();
    }

    @Override
    public boolean isUserInRole(String role) {
        if (this._authentication instanceof Authentication.Deferred) {
            this.setAuthentication(((Authentication.Deferred)this._authentication).authenticate(this));
        }
        if (this._authentication instanceof Authentication.User) {
            return ((Authentication.User)this._authentication).isUserInRole(this._scope, role);
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void setMetaData(MetaData.Request request) {
        String badMessage;
        if (this._metaData == null && this._input != null && this._channel != null) {
            this._input.reopen();
            this._channel.getResponse().getHttpOutput().reopen();
        }
        this._metaData = request;
        this._method = request.getMethod();
        this._httpFields = request.getFields();
        HttpURI uri = request.getURI();
        UriCompliance compliance = null;
        if (uri.hasViolations() && (badMessage = UriCompliance.checkUriCompliance(compliance = this._channel == null || this._channel.getHttpConfiguration() == null ? null : this._channel.getHttpConfiguration().getUriCompliance(), uri)) != null) {
            throw new BadMessageException(badMessage);
        }
        HttpField host2 = this.getHttpFields().getField(HttpHeader.HOST);
        if (uri.isAbsolute() && uri.hasAuthority() && uri.getPath() != null) {
            HttpChannel httpChannel;
            HttpConfiguration httpConfiguration;
            this._uri = uri;
            if (host2 instanceof HostPortHttpField && !((HostPortHttpField)host2).getHostPort().toString().equals(uri.getAuthority()) && (httpConfiguration = (httpChannel = this.getHttpChannel()).getHttpConfiguration()) != null) {
                HttpCompliance httpCompliance = httpConfiguration.getHttpCompliance();
                if (!httpCompliance.allows(HttpCompliance.Violation.MISMATCHED_AUTHORITY)) throw new BadMessageException(400, "Mismatched Authority");
                if (httpChannel instanceof ComplianceViolation.Listener) {
                    ((ComplianceViolation.Listener)((Object)httpChannel)).onComplianceViolation(httpCompliance, HttpCompliance.Violation.MISMATCHED_AUTHORITY, this._uri.toString());
                }
            }
        } else {
            HttpURI.Mutable builder = HttpURI.build(uri);
            if (!uri.isAbsolute()) {
                builder.scheme(HttpScheme.HTTP.asString());
            }
            if (uri.getPath() == null) {
                builder.path("/");
            }
            if (!uri.hasAuthority()) {
                if (host2 instanceof HostPortHttpField) {
                    HostPortHttpField authority = (HostPortHttpField)host2;
                    builder.host(authority.getHost()).port(authority.getPort());
                } else {
                    builder.host(this.findServerName()).port(this.findServerPort());
                }
            }
            this._uri = builder.asImmutable();
        }
        this.setSecure(HttpScheme.HTTPS.is(this._uri.getScheme()));
        String encoded = this._uri.getPath();
        String path = encoded == null ? (this._uri.isAbsolute() ? "/" : null) : (encoded.startsWith("/") ? (encoded.length() == 1 ? "/" : this._uri.getDecodedPath()) : ("*".equals(encoded) || HttpMethod.CONNECT.is(this.getMethod()) ? encoded : null));
        if (path == null || path.isEmpty()) {
            this._pathInContext = encoded == null ? "" : encoded;
            throw new BadMessageException(400, "Bad URI");
        }
        this._pathInContext = path;
    }

    public MetaData.Request getMetaData() {
        return this._metaData;
    }

    public boolean hasMetaData() {
        return this._metaData != null;
    }

    protected void recycle() {
        if (this._context != null) {
            throw new IllegalStateException("Request in context!");
        }
        if (this._reader != null && this._inputState == 2) {
            try {
                int r = this._reader.read();
                while (r != -1) {
                    r = this._reader.read();
                }
            }
            catch (Exception e) {
                LOG.trace("IGNORED", e);
                this._reader = null;
                this._readerEncoding = null;
            }
        }
        this.getHttpChannelState().recycle();
        this._requestAttributeListeners.clear();
        this._input.recycle();
        this._metaData = null;
        this._httpFields = null;
        this._trailers = null;
        this._uri = null;
        this._method = null;
        this._pathInContext = null;
        this._servletPathMapping = null;
        this._asyncNotSupportedSource = null;
        this._secure = false;
        this._newContext = false;
        this._cookiesExtracted = false;
        this._handled = false;
        this._contentParamsExtracted = false;
        this._requestedSessionIdFromCookie = false;
        this._attributes = Attributes.unwrap(this._attributes);
        if (this._attributes != null) {
            if (ServletAttributes.class.equals(this._attributes.getClass())) {
                this._attributes.clearAttributes();
            } else {
                this._attributes = null;
            }
        }
        this.setAuthentication(Authentication.NOT_CHECKED);
        this._contentType = null;
        this._characterEncoding = null;
        this._context = null;
        this._errorContext = null;
        if (this._cookies != null) {
            this._cookies.reset();
        }
        this._dispatcherType = null;
        this._inputState = 0;
        this._queryParameters = null;
        this._contentParameters = null;
        this._parameters = null;
        this._queryEncoding = null;
        this._remote = null;
        this._requestedSessionId = null;
        this._scope = null;
        this._session = null;
        this._sessionHandler = null;
        this._timeStamp = 0L;
        this._multiParts = null;
        if (this._async != null) {
            this._async.reset();
        }
        this._async = null;
        this._sessions = null;
    }

    @Override
    public void removeAttribute(String name) {
        Object oldValue;
        Object object = oldValue = this._attributes == null ? null : this._attributes.getAttribute(name);
        if (this._attributes != null) {
            this._attributes.removeAttribute(name);
        }
        if (oldValue != null && !this._requestAttributeListeners.isEmpty()) {
            ServletRequestAttributeEvent event = new ServletRequestAttributeEvent(this._context, this, name, oldValue);
            for (ServletRequestAttributeListener listener : this._requestAttributeListeners) {
                listener.attributeRemoved(event);
            }
        }
    }

    public void removeEventListener(EventListener listener) {
        this._requestAttributeListeners.remove(listener);
    }

    public void setAsyncSupported(boolean supported, Object source2) {
        this._asyncNotSupportedSource = supported ? null : (source2 == null ? "unknown" : source2);
    }

    @Override
    public void setAttribute(String name, Object value) {
        Object oldValue;
        Object object = oldValue = this._attributes == null ? null : this._attributes.getAttribute(name);
        if ("org.eclipse.jetty.server.Request.queryEncoding".equals(name)) {
            this.setQueryEncoding(value == null ? null : value.toString());
        } else if ("org.eclipse.jetty.server.sendContent".equals(name)) {
            LOG.warn("Deprecated: org.eclipse.jetty.server.sendContent");
        }
        if (this._attributes == null) {
            this._attributes = new ServletAttributes();
        }
        this._attributes.setAttribute(name, value);
        if (!this._requestAttributeListeners.isEmpty()) {
            ServletRequestAttributeEvent event = new ServletRequestAttributeEvent(this._context, this, name, oldValue == null ? value : oldValue);
            for (ServletRequestAttributeListener l : this._requestAttributeListeners) {
                if (oldValue == null) {
                    l.attributeAdded(event);
                    continue;
                }
                if (value == null) {
                    l.attributeRemoved(event);
                    continue;
                }
                l.attributeReplaced(event);
            }
        }
    }

    public void setAttributes(Attributes attributes) {
        this._attributes = attributes;
    }

    public void setAsyncAttributes() {
        if (this.getAttribute("jakarta.servlet.async.request_uri") != null) {
            return;
        }
        Attributes baseAttributes = this._attributes == null ? (this._attributes = new ServletAttributes()) : Attributes.unwrap(this._attributes);
        String fwdRequestURI = (String)this.getAttribute("jakarta.servlet.forward.request_uri");
        if (fwdRequestURI == null) {
            if (baseAttributes instanceof ServletAttributes) {
                ((ServletAttributes)baseAttributes).setAsyncAttributes(this.getRequestURI(), this.getContextPath(), this.getPathInContext(), this.getServletPathMapping(), this.getQueryString());
            } else {
                this._attributes.setAttribute("jakarta.servlet.async.request_uri", this.getRequestURI());
                this._attributes.setAttribute("jakarta.servlet.async.context_path", this.getContextPath());
                this._attributes.setAttribute("jakarta.servlet.async.servlet_path", this.getServletPath());
                this._attributes.setAttribute("jakarta.servlet.async.path_info", this.getPathInfo());
                this._attributes.setAttribute("jakarta.servlet.async.query_string", this.getQueryString());
                this._attributes.setAttribute("jakarta.servlet.async.mapping", this.getHttpServletMapping());
            }
        } else if (baseAttributes instanceof ServletAttributes) {
            ((ServletAttributes)baseAttributes).setAsyncAttributes(fwdRequestURI, (String)this.getAttribute("jakarta.servlet.forward.context_path"), (String)this.getAttribute("jakarta.servlet.forward.path_info"), (ServletPathMapping)this.getAttribute("jakarta.servlet.forward.mapping"), (String)this.getAttribute("jakarta.servlet.forward.query_string"));
        } else {
            this._attributes.setAttribute("jakarta.servlet.async.request_uri", fwdRequestURI);
            this._attributes.setAttribute("jakarta.servlet.async.context_path", this.getAttribute("jakarta.servlet.forward.context_path"));
            this._attributes.setAttribute("jakarta.servlet.async.servlet_path", this.getAttribute("jakarta.servlet.forward.servlet_path"));
            this._attributes.setAttribute("jakarta.servlet.async.path_info", this.getAttribute("jakarta.servlet.forward.path_info"));
            this._attributes.setAttribute("jakarta.servlet.async.query_string", this.getAttribute("jakarta.servlet.forward.query_string"));
            this._attributes.setAttribute("jakarta.servlet.async.mapping", this.getAttribute("jakarta.servlet.forward.mapping"));
        }
    }

    public void setAuthentication(Authentication authentication) {
        this._authentication = authentication;
    }

    @Override
    public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException {
        if (this._inputState != 0) {
            return;
        }
        this._characterEncoding = encoding;
        if (!StringUtil.isUTF8(encoding)) {
            try {
                Charset.forName(encoding);
            }
            catch (UnsupportedCharsetException e) {
                throw new UnsupportedEncodingException(e.getMessage());
            }
        }
    }

    public void setCharacterEncodingUnchecked(String encoding) {
        this._characterEncoding = encoding;
    }

    public void setContentType(String contentType) {
        this._contentType = contentType;
    }

    public void setContext(ContextHandler.Context context, String pathInContext) {
        this._newContext = this._context != context;
        this._context = context;
        this._pathInContext = pathInContext;
        if (context != null) {
            this._errorContext = context;
        }
    }

    public boolean takeNewContext() {
        boolean nc = this._newContext;
        this._newContext = false;
        return nc;
    }

    public void setCookies(Cookie[] cookies) {
        if (this._cookies == null) {
            this._cookies = new Cookies(this.getHttpChannel().getHttpConfiguration().getRequestCookieCompliance(), this.getComplianceViolationListener());
        }
        this._cookies.setCookies(cookies);
    }

    public void setDispatcherType(DispatcherType type) {
        this._dispatcherType = type;
    }

    public void setHandled(boolean h2) {
        this._handled = h2;
    }

    public void setMethod(String method2) {
        this._method = method2;
    }

    public boolean isHead() {
        return HttpMethod.HEAD.is(this.getMethod());
    }

    public void setQueryEncoding(String queryEncoding) {
        this._queryEncoding = Charset.forName(queryEncoding);
    }

    public void setRemoteAddr(InetSocketAddress addr) {
        this._remote = addr;
    }

    public void setRequestedSessionId(String requestedSessionId) {
        this._requestedSessionId = requestedSessionId;
    }

    public void setRequestedSessionIdFromCookie(boolean requestedSessionIdCookie) {
        this._requestedSessionIdFromCookie = requestedSessionIdCookie;
    }

    public void setSession(HttpSession session) {
        this._session = session;
    }

    public void setSessionHandler(SessionHandler sessionHandler) {
        this._sessionHandler = sessionHandler;
    }

    public void setTimeStamp(long ts) {
        this._timeStamp = ts;
    }

    public void setUserIdentityScope(UserIdentity.Scope scope) {
        this._scope = scope;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        if (this._asyncNotSupportedSource != null) {
            throw new IllegalStateException("!asyncSupported: " + this._asyncNotSupportedSource);
        }
        return this.forceStartAsync();
    }

    private AsyncContextState forceStartAsync() {
        HttpChannelState state = this.getHttpChannelState();
        if (this._async == null) {
            this._async = new AsyncContextState(state);
        }
        AsyncContextEvent event = new AsyncContextEvent(this._context, this._async, state, this, this, this.getResponse());
        state.startAsync(event);
        return this._async;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        if (this._asyncNotSupportedSource != null) {
            throw new IllegalStateException("!asyncSupported: " + this._asyncNotSupportedSource);
        }
        HttpChannelState state = this.getHttpChannelState();
        if (this._async == null) {
            this._async = new AsyncContextState(state);
        }
        AsyncContextEvent event = new AsyncContextEvent(this._context, this._async, state, this, servletRequest, servletResponse, this.getHttpURI());
        event.setDispatchContext(this.getServletContext());
        state.startAsync(event);
        return this._async;
    }

    public static HttpServletRequest unwrap(ServletRequest servletRequest) {
        if (servletRequest instanceof HttpServletRequestWrapper) {
            return (HttpServletRequestWrapper)servletRequest;
        }
        if (servletRequest instanceof ServletRequestWrapper) {
            return Request.unwrap(((ServletRequestWrapper)servletRequest).getRequest());
        }
        return (HttpServletRequest)servletRequest;
    }

    public String toString() {
        return String.format("%s%s%s %s%s@%x", this.getClass().getSimpleName(), this._handled ? "[" : "(", this.getMethod(), this.getHttpURI(), this._handled ? "]" : ")", this.hashCode());
    }

    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        if (this.getUserPrincipal() != null && this.getRemoteUser() != null && this.getAuthType() != null) {
            return true;
        }
        if (this._authentication instanceof Authentication.Deferred) {
            this.setAuthentication(((Authentication.Deferred)this._authentication).authenticate(this, response));
        }
        if (this._authentication instanceof Authentication.Deferred) {
            response.sendError(401);
        }
        if (!(this._authentication instanceof Authentication.ResponseSent)) {
            return false;
        }
        throw new ServletException("Authentication failed");
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
        this.getParts();
        return this._multiParts.getPart(name);
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        String contentType = this.getContentType();
        if (contentType == null || !MimeTypes.Type.MULTIPART_FORM_DATA.is(HttpField.valueParameters(contentType, null))) {
            throw new ServletException("Unsupported Content-Type [" + contentType + "], expected [multipart/form-data]");
        }
        return this.getParts(null);
    }

    private Collection<Part> getParts(MultiMap<String> params) throws IOException {
        if (this._multiParts == null) {
            MultipartConfigElement config = (MultipartConfigElement)this.getAttribute(__MULTIPART_CONFIG_ELEMENT);
            if (config == null) {
                throw new IllegalStateException("No multipart config for servlet");
            }
            int maxFormContentSize = 200000;
            int maxFormKeys = 1000;
            if (this._context != null) {
                ContextHandler contextHandler = this._context.getContextHandler();
                maxFormContentSize = contextHandler.getMaxFormContentSize();
                maxFormKeys = contextHandler.getMaxFormKeys();
            } else {
                maxFormContentSize = this.lookupServerAttribute("org.eclipse.jetty.server.Request.maxFormContentSize", maxFormContentSize);
                maxFormKeys = this.lookupServerAttribute("org.eclipse.jetty.server.Request.maxFormKeys", maxFormKeys);
            }
            this._multiParts = this.newMultiParts(config, maxFormKeys);
            Collection<Part> parts = this._multiParts.getParts();
            String formCharset = null;
            Part charsetPart = this._multiParts.getPart("_charset_");
            if (charsetPart != null) {
                try (InputStream is = charsetPart.getInputStream();){
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    IO.copy(is, os);
                    formCharset = os.toString(StandardCharsets.UTF_8);
                }
            }
            Charset defaultCharset = formCharset != null ? Charset.forName(formCharset) : (this.getCharacterEncoding() != null ? Charset.forName(this.getCharacterEncoding()) : StandardCharsets.UTF_8);
            long formContentSize = 0L;
            ByteArrayOutputStream os = null;
            for (Part p : parts) {
                if (p.getSubmittedFileName() != null) continue;
                formContentSize = Math.addExact(formContentSize, p.getSize());
                if (maxFormContentSize >= 0 && formContentSize > (long)maxFormContentSize) {
                    throw new IllegalStateException("Form is larger than max length " + maxFormContentSize);
                }
                String charset = null;
                if (p.getContentType() != null) {
                    charset = MimeTypes.getCharsetFromContentType(p.getContentType());
                }
                try (InputStream is = p.getInputStream();){
                    if (os == null) {
                        os = new ByteArrayOutputStream();
                    }
                    IO.copy(is, os);
                    String content = os.toString(charset == null ? defaultCharset : Charset.forName(charset));
                    if (this._contentParameters == null) {
                        this._contentParameters = params == null ? new MultiMap() : params;
                    }
                    this._contentParameters.add(p.getName(), content);
                }
                os.reset();
            }
        }
        return this._multiParts.getParts();
    }

    private MultiParts newMultiParts(MultipartConfigElement config, int maxParts) throws IOException {
        MultiPartFormDataCompliance compliance = this.getHttpChannel().getHttpConfiguration().getMultipartFormDataCompliance();
        if (LOG.isDebugEnabled()) {
            LOG.debug("newMultiParts {} {}", (Object)compliance, (Object)this);
        }
        switch (compliance) {
            case RFC7578: {
                return new MultiParts.MultiPartsHttpParser(this.getInputStream(), this.getContentType(), config, this._context != null ? (File)this._context.getAttribute("javax.servlet.context.tempdir") : null, this, maxParts);
            }
        }
        return new MultiParts.MultiPartsUtilParser(this.getInputStream(), this.getContentType(), config, this._context != null ? (File)this._context.getAttribute("javax.servlet.context.tempdir") : null, this, maxParts);
    }

    @Override
    public void login(String username, String password) throws ServletException {
        Authentication auth;
        if (this._authentication instanceof Authentication.LoginAuthentication) {
            auth = ((Authentication.LoginAuthentication)this._authentication).login(username, password, this);
            if (auth == null) {
                throw new Authentication.Failed("Authentication failed for username '" + username + "'");
            }
        } else {
            throw new Authentication.Failed("Authenticated failed for username '" + username + "'. Already authenticated as " + this._authentication);
        }
        this._authentication = auth;
    }

    @Override
    public void logout() throws ServletException {
        if (this._authentication instanceof Authentication.LogoutAuthentication) {
            this._authentication = ((Authentication.LogoutAuthentication)this._authentication).logout(this);
        }
    }

    public void mergeQueryParameters(String oldQuery, String newQuery) {
        MultiMap<String> mergedQueryParams;
        MultiMap<String> oldQueryParams;
        MultiMap<String> newQueryParams = null;
        if (newQuery != null) {
            newQueryParams = new MultiMap<String>();
            UrlEncoded.decodeTo(newQuery, newQueryParams, UrlEncoded.ENCODING);
        }
        if ((oldQueryParams = this._queryParameters) == null && oldQuery != null) {
            oldQueryParams = new MultiMap();
            try {
                UrlEncoded.decodeTo(oldQuery, oldQueryParams, this.getQueryCharset());
            }
            catch (Throwable th) {
                this._queryParameters = BAD_PARAMS;
                throw new BadMessageException(400, "Bad query encoding", th);
            }
        }
        if (newQueryParams == null || newQueryParams.size() == 0) {
            mergedQueryParams = oldQueryParams == null ? NO_PARAMS : oldQueryParams;
        } else if (oldQueryParams == null || oldQueryParams.size() == 0) {
            mergedQueryParams = newQueryParams;
        } else {
            mergedQueryParams = new MultiMap<String>(newQueryParams);
            mergedQueryParams.addAllValues(oldQueryParams);
        }
        this.setQueryParameters(mergedQueryParams);
        this.resetParameters();
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
        HttpUpgradeHandler upgradeHandler;
        Response response = this._channel.getResponse();
        if (response.getStatus() != 101) {
            throw new IllegalStateException("Response status should be 101");
        }
        if (response.getHeader("Upgrade") == null) {
            throw new IllegalStateException("Missing Upgrade header");
        }
        if (!"Upgrade".equalsIgnoreCase(response.getHeader("Connection"))) {
            throw new IllegalStateException("Invalid Connection header");
        }
        if (response.isCommitted()) {
            throw new IllegalStateException("Cannot upgrade committed response");
        }
        if (this._metaData == null || this._metaData.getHttpVersion() != HttpVersion.HTTP_1_1) {
            throw new IllegalStateException("Only requests over HTTP/1.1 can be upgraded");
        }
        final ServletOutputStream outputStream2 = response.getOutputStream();
        final ServletInputStream inputStream2 = this.getInputStream();
        HttpChannelOverHttp httpChannel11 = (HttpChannelOverHttp)this._channel;
        HttpConnection httpConnection = (HttpConnection)this._channel.getConnection();
        try {
            upgradeHandler = (HttpUpgradeHandler)handlerClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            throw new ServletException("Unable to instantiate handler class", e);
        }
        httpChannel11.servletUpgrade();
        final AsyncContextState asyncContext = this.forceStartAsync();
        outputStream2.flush();
        httpConnection.getGenerator().servletUpgrade();
        httpConnection.addEventListener(new Connection.Listener(){

            @Override
            public void onClosed(Connection connection) {
                try {
                    asyncContext.complete();
                }
                catch (Exception e) {
                    LOG.warn("error during upgrade AsyncContext complete", e);
                }
                try {
                    upgradeHandler.destroy();
                }
                catch (Exception e) {
                    LOG.warn("error during upgrade HttpUpgradeHandler destroy", e);
                }
            }

            @Override
            public void onOpened(Connection connection) {
            }
        });
        upgradeHandler.init(new WebConnection(){

            @Override
            public void close() throws Exception {
                try {
                    inputStream2.close();
                }
                finally {
                    outputStream2.close();
                }
            }

            @Override
            public ServletInputStream getInputStream() {
                return inputStream2;
            }

            @Override
            public ServletOutputStream getOutputStream() {
                return outputStream2;
            }
        });
        return (T)upgradeHandler;
    }

    public void setServletPathMapping(ServletPathMapping servletPathMapping) {
        this._servletPathMapping = servletPathMapping;
    }

    public ServletPathMapping getServletPathMapping() {
        return this._servletPathMapping;
    }

    ServletPathMapping findServletPathMapping() {
        Dispatcher.IncludeAttributes include;
        ServletPathMapping mapping = this._dispatcherType == DispatcherType.INCLUDE ? ((include = Attributes.unwrap(this._attributes, Dispatcher.IncludeAttributes.class)) == null ? this._servletPathMapping : include.getSourceMapping()) : this._servletPathMapping;
        return mapping;
    }

    @Override
    public HttpServletMapping getHttpServletMapping() {
        ServletPathMapping async;
        if (this._dispatcherType == DispatcherType.ASYNC && (async = (ServletPathMapping)this.getAttribute("jakarta.servlet.async.mapping")) != null && "/DispatchServlet".equals(async.getServletPath())) {
            return async;
        }
        return this.findServletPathMapping();
    }

    private String formatAddrOrHost(String name) {
        return this._channel == null ? HostPort.normalizeHost(name) : this._channel.formatAddrOrHost(name);
    }
}

