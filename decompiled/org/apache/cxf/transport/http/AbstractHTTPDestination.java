/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.ServletRequest
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.attachment.AttachmentDataSource;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.Base64Exception;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.Configurable;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.continuations.ContinuationProvider;
import org.apache.cxf.continuations.SuspendedInvocationException;
import org.apache.cxf.helpers.HttpHeaderHelper;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.AbstractWrappedOutputStream;
import org.apache.cxf.io.CopyingOutputStream;
import org.apache.cxf.io.DelegatingInputStream;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.policy.PolicyDataEngine;
import org.apache.cxf.security.SecurityContext;
import org.apache.cxf.security.transport.TLSSessionInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.AbstractDestination;
import org.apache.cxf.transport.AbstractMultiplexDestination;
import org.apache.cxf.transport.Assertor;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.http.ContinuationProviderFactory;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.http.HTTPSession;
import org.apache.cxf.transport.http.Headers;
import org.apache.cxf.transport.http.HttpServletRequestSnapshot;
import org.apache.cxf.transport.http.Servlet3ContinuationProvider;
import org.apache.cxf.transport.http.policy.impl.ServerPolicyCalculator;
import org.apache.cxf.transport.https.CertConstraints;
import org.apache.cxf.transport.https.CertConstraintsInterceptor;
import org.apache.cxf.transports.http.configuration.HTTPServerPolicy;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.ContextUtils;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.EndpointReferenceUtils;

public abstract class AbstractHTTPDestination
extends AbstractMultiplexDestination
implements Configurable,
Assertor {
    public static final String HTTP_REQUEST = "HTTP.REQUEST";
    public static final String HTTP_RESPONSE = "HTTP.RESPONSE";
    public static final String HTTP_CONTEXT = "HTTP.CONTEXT";
    public static final String HTTP_CONFIG = "HTTP.CONFIG";
    public static final String HTTP_CONTEXT_MATCH_STRATEGY = "HTTP_CONTEXT_MATCH_STRATEGY";
    public static final String RESPONSE_HEADERS_COPIED = "http.headers.copied";
    public static final String RESPONSE_COMMITED = "http.response.done";
    public static final String REQUEST_REDIRECTED = "http.request.redirected";
    public static final String CXF_CONTINUATION_MESSAGE = "cxf.continuation.message";
    public static final String CXF_ASYNC_CONTEXT = "cxf.async.context";
    public static final String SERVICE_REDIRECTION = "http.service.redirection";
    private static final String HTTP_BASE_PATH = "http.base.path";
    private static final String SSL_CIPHER_SUITE_ATTRIBUTE = "javax.servlet.request.cipher_suite";
    private static final String SSL_PEER_CERT_CHAIN_ATTRIBUTE = "javax.servlet.request.X509Certificate";
    private static final String DECODE_BASIC_AUTH_WITH_ISO8859 = "decode.basicauth.with.iso8859";
    private static final Logger LOG = LogUtils.getL7dLogger(AbstractHTTPDestination.class);
    protected final Bus bus;
    protected DestinationRegistry registry;
    protected final String path;
    protected volatile HTTPServerPolicy serverPolicy;
    protected String contextMatchStrategy = "stem";
    protected boolean fixedParameterOrder;
    protected boolean multiplexWithAddress;
    protected CertConstraints certConstraints;
    protected boolean isServlet3;
    protected boolean decodeBasicAuthWithIso8859;
    protected ContinuationProviderFactory cproviderFactory;
    protected boolean enableWebSocket;
    private volatile boolean serverPolicyCalced;

    public AbstractHTTPDestination(Bus b, DestinationRegistry registry, EndpointInfo ei, String path, boolean dp) throws IOException {
        super(b, AbstractHTTPDestination.getTargetReference(AbstractHTTPDestination.getAddressValue(ei, dp), b), ei);
        this.bus = b;
        this.registry = registry;
        this.path = path;
        try {
            ServletRequest.class.getMethod("isAsyncSupported", new Class[0]);
            this.isServlet3 = true;
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        this.decodeBasicAuthWithIso8859 = PropertyUtils.isTrue(this.bus.getProperty(DECODE_BASIC_AUTH_WITH_ISO8859));
        this.initConfig();
    }

    public Bus getBus() {
        return this.bus;
    }

    private AuthorizationPolicy getAuthorizationPolicyFromMessage(String credentials, SecurityContext sc) {
        if (credentials == null || StringUtils.isEmpty(credentials.trim())) {
            return null;
        }
        String[] creds = credentials.split(" ");
        String authType = creds[0];
        if ("Basic".equals(authType) && creds.length == 2) {
            String authEncoded = creds[1];
            try {
                byte[] authBytes = Base64Utility.decode(authEncoded);
                if (authBytes == null) {
                    throw new Base64Exception(new Throwable("Invalid Base64 data."));
                }
                String authDecoded = this.decodeBasicAuthWithIso8859 ? new String(authBytes, StandardCharsets.ISO_8859_1) : new String(authBytes);
                int idx = authDecoded.indexOf(58);
                String username = null;
                String password = null;
                if (idx == -1) {
                    username = authDecoded;
                } else {
                    username = authDecoded.substring(0, idx);
                    if (idx < authDecoded.length() - 1) {
                        password = authDecoded.substring(idx + 1);
                    }
                }
                AuthorizationPolicy policy = sc.getUserPrincipal() == null ? new AuthorizationPolicy() : new PrincipalAuthorizationPolicy(sc);
                policy.setUserName(username);
                policy.setPassword(password);
                policy.setAuthorizationType(authType);
                return policy;
            }
            catch (Base64Exception base64Exception) {
                // empty catch block
            }
        }
        if (sc.getUserPrincipal() != null) {
            PrincipalAuthorizationPolicy policy = new PrincipalAuthorizationPolicy(sc);
            policy.setAuthorization(credentials);
            policy.setAuthorizationType(authType);
            return policy;
        }
        return null;
    }

    protected final boolean isOneWay(Message message) {
        Exchange ex = message.getExchange();
        return ex != null && ex.isOneWay();
    }

    public void invoke(ServletConfig config, ServletContext context, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Message inMessage = this.retrieveFromContinuation(req);
        if (inMessage == null) {
            LOG.fine("Create a new message for processing");
            inMessage = new MessageImpl();
            ExchangeImpl exchange = new ExchangeImpl();
            exchange.setInMessage(inMessage);
            this.setupMessage(inMessage, config, context, req, resp);
            exchange.setSession(new HTTPSession(req));
            ((MessageImpl)inMessage).setDestination(this);
        } else {
            LOG.fine("Get the message from the request for processing");
        }
        this.copyKnownRequestAttributes(req, inMessage);
        try {
            this.incomingObserver.onMessage(inMessage);
            this.invokeComplete(context, req, resp, inMessage);
        }
        catch (SuspendedInvocationException ex) {
            if (ex.getRuntimeException() != null) {
                throw ex.getRuntimeException();
            }
        }
        catch (Fault ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException)cause;
            }
            throw ex;
        }
        catch (RuntimeException ex) {
            throw ex;
        }
        finally {
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("Finished servicing http request on thread: " + Thread.currentThread());
            }
        }
    }

    protected void invokeComplete(ServletContext context, HttpServletRequest req, HttpServletResponse resp, Message m4) throws IOException {
        ContinuationProvider p = m4.get(ContinuationProvider.class);
        if (p != null) {
            p.complete();
        }
    }

    private void copyKnownRequestAttributes(HttpServletRequest request, Message message) {
        message.put(SERVICE_REDIRECTION, request.getAttribute(SERVICE_REDIRECTION));
    }

    protected void setupMessage(Message inMessage, ServletConfig config, ServletContext context, final HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int index;
        String servletPath;
        this.setupContinuation(inMessage, req, resp);
        final Exchange exchange = inMessage.getExchange();
        DelegatingInputStream in = new DelegatingInputStream((InputStream)req.getInputStream()){

            @Override
            public void cacheInput() {
                if (!this.cached && (exchange.isOneWay() || this.isWSAddressingReplyToSpecified(exchange))) {
                    exchange.getInMessage().put(AbstractHTTPDestination.HTTP_REQUEST, new HttpServletRequestSnapshot(req));
                }
                super.cacheInput();
            }

            private boolean isWSAddressingReplyToSpecified(Exchange ex) {
                AddressingProperties map = ContextUtils.retrieveMAPs(ex.getInMessage(), false, false, false);
                return map != null && !ContextUtils.isGenericAddress(map.getReplyTo());
            }
        };
        inMessage.setContent(DelegatingInputStream.class, in);
        inMessage.setContent(InputStream.class, in);
        inMessage.put(HTTP_REQUEST, req);
        inMessage.put(HTTP_RESPONSE, resp);
        inMessage.put(HTTP_CONTEXT, context);
        inMessage.put(HTTP_CONFIG, config);
        inMessage.put(HTTP_CONTEXT_MATCH_STRATEGY, this.contextMatchStrategy);
        inMessage.put("org.apache.cxf.request.method", req.getMethod());
        String requestURI = req.getRequestURI();
        inMessage.put("org.apache.cxf.request.uri", requestURI);
        String requestURL = req.getRequestURL().toString();
        inMessage.put("org.apache.cxf.request.url", requestURL);
        String contextPath = req.getContextPath();
        if (contextPath == null) {
            contextPath = "";
        }
        if ((servletPath = req.getServletPath()) == null) {
            servletPath = "";
        }
        String contextServletPath = contextPath + servletPath;
        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            inMessage.put(Message.PATH_INFO, contextServletPath + pathInfo);
        } else {
            inMessage.put(Message.PATH_INFO, requestURI);
        }
        if (!StringUtils.isEmpty(requestURI)) {
            index = requestURL.indexOf(requestURI);
            if (index > 0) {
                String schemaInfo = requestURL.substring(0, index);
                String basePathWithContextOnly = schemaInfo + contextPath;
                inMessage.put(HTTP_BASE_PATH, basePathWithContextOnly);
            }
        } else if (!StringUtils.isEmpty(servletPath) && requestURL.endsWith(servletPath) && (index = requestURL.lastIndexOf(servletPath)) > 0) {
            inMessage.put(HTTP_BASE_PATH, requestURL.substring(0, index));
        }
        String contentType = req.getContentType();
        inMessage.put("Content-Type", contentType);
        this.setEncoding(inMessage, req, contentType);
        inMessage.put(Message.QUERY_STRING, req.getQueryString());
        inMessage.put("Accept", req.getHeader("Accept"));
        String basePath = this.getBasePath(contextServletPath);
        if (!StringUtils.isEmpty(basePath)) {
            inMessage.put(Message.BASE_PATH, basePath);
        }
        inMessage.put(Message.FIXED_PARAMETER_ORDER, this.isFixedParameterOrder());
        inMessage.put("org.apache.cxf.async.post.response.dispatch", Boolean.TRUE);
        SecurityContext httpSecurityContext = new SecurityContext(){

            @Override
            public Principal getUserPrincipal() {
                return req.getUserPrincipal();
            }

            @Override
            public boolean isUserInRole(String role) {
                return req.isUserInRole(role);
            }
        };
        inMessage.put(SecurityContext.class, httpSecurityContext);
        Headers headers = new Headers(inMessage);
        headers.copyFromRequest(req);
        String credentials = headers.getAuthorization();
        AuthorizationPolicy authPolicy = this.getAuthorizationPolicyFromMessage(credentials, httpSecurityContext);
        inMessage.put(AuthorizationPolicy.class, authPolicy);
        AbstractHTTPDestination.propogateSecureSession(req, inMessage);
        inMessage.put(CertConstraints.class.getName(), this.certConstraints);
        inMessage.put(Message.IN_INTERCEPTORS, Arrays.asList(CertConstraintsInterceptor.INSTANCE));
    }

    private static void propogateSecureSession(HttpServletRequest request, Message message) {
        String cipherSuite = (String)request.getAttribute(SSL_CIPHER_SUITE_ATTRIBUTE);
        if (cipherSuite != null) {
            Certificate[] certs = (Certificate[])request.getAttribute(SSL_PEER_CERT_CHAIN_ATTRIBUTE);
            message.put(TLSSessionInfo.class, new TLSSessionInfo(cipherSuite, null, certs));
        }
    }

    private String setEncoding(Message inMessage, HttpServletRequest req, String contentType) throws IOException {
        String enc = HttpHeaderHelper.findCharset(contentType);
        if (enc == null) {
            enc = req.getCharacterEncoding();
        }
        if (enc != null && enc.endsWith("\"")) {
            enc = enc.substring(0, enc.length() - 1);
        }
        if (enc != null || "POST".equals(req.getMethod()) || "PUT".equals(req.getMethod())) {
            String normalizedEncoding = HttpHeaderHelper.mapCharset(enc);
            if (normalizedEncoding == null) {
                String m4 = new org.apache.cxf.common.i18n.Message("INVALID_ENCODING_MSG", LOG, enc).toString();
                LOG.log(Level.WARNING, m4);
                throw new IOException(m4);
            }
            inMessage.put(Message.ENCODING, normalizedEncoding);
        }
        return contentType;
    }

    protected Message retrieveFromContinuation(HttpServletRequest req) {
        if (!this.isServlet3) {
            if (this.cproviderFactory != null) {
                return this.cproviderFactory.retrieveFromContinuation(req);
            }
            return null;
        }
        return this.retrieveFromServlet3Async(req);
    }

    protected Message retrieveFromServlet3Async(HttpServletRequest req) {
        try {
            return (Message)req.getAttribute(CXF_CONTINUATION_MESSAGE);
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    protected void setupContinuation(Message inMessage, HttpServletRequest req, HttpServletResponse resp) {
        try {
            ContinuationProvider p;
            if (this.isServlet3 && req.isAsyncSupported()) {
                inMessage.put(ContinuationProvider.class.getName(), new Servlet3ContinuationProvider(req, resp, inMessage));
            } else if (this.cproviderFactory != null && (p = this.cproviderFactory.createContinuationProvider(inMessage, req, resp)) != null) {
                inMessage.put(ContinuationProvider.class.getName(), p);
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    protected String getBasePath(String contextPath) throws IOException {
        if (StringUtils.isEmpty(this.endpointInfo.getAddress())) {
            return "";
        }
        return new URL(this.endpointInfo.getAddress()).getPath();
    }

    protected static EndpointInfo getAddressValue(EndpointInfo ei) {
        return AbstractHTTPDestination.getAddressValue(ei, true);
    }

    protected static EndpointInfo getAddressValue(EndpointInfo ei, boolean dp) {
        if (dp) {
            String addr;
            String eiAddress = ei.getAddress();
            if (eiAddress == null) {
                try {
                    ServerSocket s2 = new ServerSocket(0);
                    ei.setAddress("http://localhost:" + s2.getLocalPort());
                    s2.close();
                    return ei;
                }
                catch (IOException ex) {
                    ei.setAddress("http://localhost");
                }
            }
            if ((addr = StringUtils.addDefaultPortIfMissing(ei.getAddress())) != null) {
                ei.setAddress(addr);
            }
        }
        return ei;
    }

    @Override
    protected Conduit getInbuiltBackChannel(Message inMessage) {
        HttpServletResponse response = (HttpServletResponse)inMessage.get(HTTP_RESPONSE);
        return new BackChannelConduit(response);
    }

    private void initConfig() {
        this.cproviderFactory = this.bus.getExtension(ContinuationProviderFactory.class);
    }

    private synchronized HTTPServerPolicy calcServerPolicyInternal(Message m4) {
        HTTPServerPolicy sp2 = this.serverPolicy;
        if (!this.serverPolicyCalced) {
            PolicyDataEngine pde = this.bus.getExtension(PolicyDataEngine.class);
            if (pde != null) {
                sp2 = pde.getServerEndpointPolicy(m4, this.endpointInfo, this, new ServerPolicyCalculator());
            }
            if (null == sp2) {
                sp2 = this.endpointInfo.getTraversedExtensor(new HTTPServerPolicy(), HTTPServerPolicy.class);
            }
            this.serverPolicy = sp2;
            this.serverPolicyCalced = true;
        }
        return sp2;
    }

    private HTTPServerPolicy calcServerPolicy(Message m4) {
        HTTPServerPolicy sp2 = this.serverPolicy;
        if (!this.serverPolicyCalced) {
            sp2 = this.calcServerPolicyInternal(m4);
        }
        return sp2;
    }

    private void cacheInput(Message outMessage) {
        if (outMessage.getExchange() == null) {
            return;
        }
        Message inMessage = outMessage.getExchange().getInMessage();
        if (inMessage == null) {
            return;
        }
        Object o = inMessage.get("cxf.io.cacheinput");
        DelegatingInputStream in = inMessage.getContent(DelegatingInputStream.class);
        if (PropertyUtils.isTrue(o)) {
            Collection<Attachment> atts = inMessage.getAttachments();
            if (atts != null) {
                for (Attachment a : atts) {
                    if (!(a.getDataHandler().getDataSource() instanceof AttachmentDataSource)) continue;
                    try {
                        ((AttachmentDataSource)a.getDataHandler().getDataSource()).cache(inMessage);
                    }
                    catch (IOException e) {
                        throw new Fault(e);
                    }
                }
            }
            if (in != null) {
                in.cacheInput();
            }
        } else if (in != null) {
            try {
                IOUtils.consume(in, 0x1000000);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    protected OutputStream flushHeaders(Message outMessage) throws IOException {
        return this.flushHeaders(outMessage, true);
    }

    protected OutputStream flushHeaders(Message outMessage, boolean getStream) throws IOException {
        String ec;
        if (this.isResponseRedirected(outMessage)) {
            return null;
        }
        this.cacheInput(outMessage);
        HTTPServerPolicy sp2 = this.calcServerPolicy(outMessage);
        if (sp2 != null) {
            new Headers(outMessage).setFromServerPolicy(sp2);
        }
        ServletOutputStream responseStream = null;
        boolean oneWay = this.isOneWay(outMessage);
        HttpServletResponse response = this.getHttpResponseFromMessage(outMessage);
        int responseCode = this.getReponseCodeFromMessage(outMessage);
        if (responseCode >= 300 && !StringUtils.isEmpty(ec = (String)outMessage.get(Message.ERROR_MESSAGE))) {
            response.sendError(responseCode, ec);
            return null;
        }
        response.setStatus(responseCode);
        new Headers(outMessage).copyToResponse(response);
        outMessage.put(RESPONSE_HEADERS_COPIED, "true");
        if (this.hasNoResponseContent(outMessage)) {
            response.setContentLength(0);
            response.flushBuffer();
            this.closeResponseOutputStream(response);
        } else if (!getStream) {
            this.closeResponseOutputStream(response);
        } else {
            responseStream = response.getOutputStream();
        }
        if (oneWay) {
            outMessage.remove(HTTP_RESPONSE);
        }
        return responseStream;
    }

    private void closeResponseOutputStream(HttpServletResponse response) throws IOException {
        try {
            response.getOutputStream().close();
        }
        catch (IllegalStateException illegalStateException) {
            // empty catch block
        }
    }

    private int getReponseCodeFromMessage(Message message) {
        Integer i = (Integer)message.get(Message.RESPONSE_CODE);
        if (i != null) {
            return i;
        }
        int code = this.hasNoResponseContent(message) ? 202 : 200;
        message.put(Message.RESPONSE_CODE, code);
        return code;
    }

    private boolean hasNoResponseContent(Message message) {
        boolean ow = this.isOneWay(message);
        boolean pr = MessageUtils.isPartialResponse(message);
        boolean epr = MessageUtils.isEmptyPartialResponse(message);
        return ow && !pr || epr;
    }

    private HttpServletResponse getHttpResponseFromMessage(Message message) throws IOException {
        Object responseObj = message.get(HTTP_RESPONSE);
        if (responseObj instanceof HttpServletResponse) {
            return (HttpServletResponse)responseObj;
        }
        if (null != responseObj) {
            String m4 = new org.apache.cxf.common.i18n.Message("UNEXPECTED_RESPONSE_TYPE_MSG", LOG, responseObj.getClass()).toString();
            LOG.log(Level.WARNING, m4);
            throw new IOException(m4);
        }
        String m5 = new org.apache.cxf.common.i18n.Message("NULL_RESPONSE_MSG", LOG, new Object[0]).toString();
        LOG.log(Level.WARNING, m5);
        throw new IOException(m5);
    }

    private boolean isResponseRedirected(Message outMessage) {
        Exchange exchange = outMessage.getExchange();
        return exchange != null && Boolean.TRUE.equals(exchange.get(REQUEST_REDIRECTED));
    }

    protected boolean contextMatchOnExact() {
        return "exact".equals(this.contextMatchStrategy);
    }

    public void finalizeConfig() {
    }

    @Override
    public String getBeanName() {
        String beanName = null;
        if (this.endpointInfo.getName() != null) {
            beanName = this.endpointInfo.getName().toString() + ".http-destination";
        }
        return beanName;
    }

    @Override
    public EndpointReferenceType getAddressWithId(String id) {
        EndpointReferenceType ref = null;
        if (this.isMultiplexWithAddress()) {
            String address = EndpointReferenceUtils.getAddress(this.reference);
            ref = EndpointReferenceUtils.duplicate(this.reference);
            if (address.endsWith("/")) {
                EndpointReferenceUtils.setAddress(ref, address + id);
            } else {
                EndpointReferenceUtils.setAddress(ref, address + "/" + id);
            }
        } else {
            ref = super.getAddressWithId(id);
        }
        return ref;
    }

    @Override
    public String getId(Map<String, Object> context) {
        String id = null;
        if (this.isMultiplexWithAddress()) {
            String address = (String)context.get(Message.PATH_INFO);
            if (null != address) {
                int afterLastSlashIndex = address.lastIndexOf(47) + 1;
                if (afterLastSlashIndex > 0 && afterLastSlashIndex < address.length()) {
                    id = address.substring(afterLastSlashIndex);
                }
            } else {
                this.getLogger().log(Level.WARNING, new org.apache.cxf.common.i18n.Message("MISSING_PATH_INFO", LOG, new Object[0]).toString());
            }
        } else {
            return super.getId(context);
        }
        return id;
    }

    public String getContextMatchStrategy() {
        return this.contextMatchStrategy;
    }

    public void setContextMatchStrategy(String contextMatchStrategy) {
        this.contextMatchStrategy = contextMatchStrategy;
    }

    public boolean isFixedParameterOrder() {
        return this.fixedParameterOrder;
    }

    public void setFixedParameterOrder(boolean fixedParameterOrder) {
        this.fixedParameterOrder = fixedParameterOrder;
    }

    public boolean isMultiplexWithAddress() {
        return this.multiplexWithAddress;
    }

    public void setMultiplexWithAddress(boolean multiplexWithAddress) {
        this.multiplexWithAddress = multiplexWithAddress;
    }

    public HTTPServerPolicy getServer() {
        return this.calcServerPolicy(null);
    }

    public void setServer(HTTPServerPolicy server) {
        this.serverPolicy = server;
        if (server != null) {
            this.serverPolicyCalced = true;
        }
    }

    @Override
    public void assertMessage(Message message) {
        PolicyDataEngine pde = this.bus.getExtension(PolicyDataEngine.class);
        pde.assertMessage(message, this.calcServerPolicy(message), new ServerPolicyCalculator());
    }

    @Override
    public boolean canAssert(QName type) {
        return new ServerPolicyCalculator().getDataClassName().equals(type);
    }

    public void releaseRegistry() {
        this.registry = null;
    }

    public String getPath() {
        return this.path;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void activate() {
        AbstractHTTPDestination abstractHTTPDestination = this;
        synchronized (abstractHTTPDestination) {
            if (this.registry != null) {
                this.registry.addDestination(this);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void deactivate() {
        AbstractHTTPDestination abstractHTTPDestination = this;
        synchronized (abstractHTTPDestination) {
            if (this.registry != null) {
                this.registry.removeDestination(this.path);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void shutdown() {
        AbstractHTTPDestination abstractHTTPDestination = this;
        synchronized (abstractHTTPDestination) {
            if (this.registry != null) {
                this.registry.removeDestination(this.path);
            }
        }
        super.shutdown();
    }

    private class WrappedOutputStream
    extends AbstractWrappedOutputStream
    implements CopyingOutputStream {
        private Message outMessage;

        WrappedOutputStream(Message m4) {
            this.outMessage = m4;
        }

        @Override
        public int copyFrom(InputStream in) throws IOException {
            if (!this.written) {
                this.onFirstWrite();
                this.written = true;
            }
            if (this.wrappedStream != null) {
                return IOUtils.copy(in, this.wrappedStream);
            }
            return IOUtils.copy(in, this, 4096);
        }

        @Override
        protected void onFirstWrite() throws IOException {
            OutputStream responseStream = AbstractHTTPDestination.this.flushHeaders(this.outMessage);
            if (null != responseStream) {
                this.wrappedStream = responseStream;
            }
        }

        @Override
        public void close() throws IOException {
            OutputStream responseStream;
            if (!this.written && this.wrappedStream == null && null != (responseStream = AbstractHTTPDestination.this.flushHeaders(this.outMessage, false))) {
                this.wrappedStream = responseStream;
            }
            if (this.wrappedStream != null) {
                this.wrappedStream.close();
            }
        }
    }

    public class BackChannelConduit
    extends AbstractDestination.AbstractBackChannelConduit {
        protected HttpServletResponse response;

        BackChannelConduit(HttpServletResponse resp) {
            this.response = resp;
        }

        @Override
        public void prepare(Message message) throws IOException {
            message.put(AbstractHTTPDestination.HTTP_RESPONSE, this.response);
            OutputStream os = message.getContent(OutputStream.class);
            if (os == null) {
                message.setContent(OutputStream.class, new WrappedOutputStream(message));
            }
        }

        @Override
        public void close(Message msg) throws IOException {
            super.close(msg);
            if (msg.getExchange() == null) {
                return;
            }
            Message m4 = msg.getExchange().getInMessage();
            if (m4 == null) {
                return;
            }
            InputStream is = m4.getContent(InputStream.class);
            if (is != null) {
                try {
                    is.close();
                    m4.removeContent(InputStream.class);
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
    }

    public static final class PrincipalAuthorizationPolicy
    extends AuthorizationPolicy {
        final SecurityContext sc;

        public PrincipalAuthorizationPolicy(SecurityContext sc) {
            this.sc = sc;
        }

        public Principal getPrincipal() {
            return this.sc.getUserPrincipal();
        }

        @Override
        public String getUserName() {
            String name = super.getUserName();
            if (name != null) {
                return name;
            }
            Principal pp = this.getPrincipal();
            return pp != null ? pp.getName() : null;
        }
    }
}

