/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpRetryException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.configuration.Configurable;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.configuration.security.CertificateConstraintsType;
import org.apache.cxf.configuration.security.ProxyAuthorizationPolicy;
import org.apache.cxf.endpoint.ClientCallback;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.HttpHeaderHelper;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.helpers.LoadingByteArrayOutputStream;
import org.apache.cxf.io.AbstractThresholdOutputStream;
import org.apache.cxf.io.CacheAndWriteOutputStream;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.ExchangeImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.policy.PolicyDataEngine;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.AbstractConduit;
import org.apache.cxf.transport.Assertor;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.MessageObserver;
import org.apache.cxf.transport.http.Address;
import org.apache.cxf.transport.http.Cookie;
import org.apache.cxf.transport.http.Cookies;
import org.apache.cxf.transport.http.HTTPException;
import org.apache.cxf.transport.http.Headers;
import org.apache.cxf.transport.http.MessageTrustDecider;
import org.apache.cxf.transport.http.ProxyFactory;
import org.apache.cxf.transport.http.ProxyOutputStream;
import org.apache.cxf.transport.http.UntrustedURLConnectionIOException;
import org.apache.cxf.transport.http.auth.CustomAuthSupplier;
import org.apache.cxf.transport.http.auth.DefaultBasicAuthSupplier;
import org.apache.cxf.transport.http.auth.DigestAuthSupplier;
import org.apache.cxf.transport.http.auth.HttpAuthHeader;
import org.apache.cxf.transport.http.auth.HttpAuthSupplier;
import org.apache.cxf.transport.http.auth.SpnegoAuthSupplier;
import org.apache.cxf.transport.http.policy.impl.ClientPolicyCalculator;
import org.apache.cxf.transport.https.CertConstraints;
import org.apache.cxf.transport.https.CertConstraintsInterceptor;
import org.apache.cxf.transport.https.CertConstraintsJaxBUtils;
import org.apache.cxf.transport.https.HttpsURLConnectionInfo;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.workqueue.AutomaticWorkQueue;
import org.apache.cxf.workqueue.WorkQueueManager;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

@NoJSR250Annotations
public abstract class HTTPConduit
extends AbstractConduit
implements Configurable,
Assertor,
PropertyChangeListener {
    public static final String KEY_HTTP_CONNECTION = "http.connection";
    public static final String KEY_HTTP_CONNECTION_ADDRESS = "http.connection.address";
    public static final String SET_HTTP_RESPONSE_MESSAGE = "org.apache.cxf.transport.http.set.response.message";
    public static final String HTTP_RESPONSE_MESSAGE = "http.responseMessage";
    public static final String PROCESS_FAULT_ON_HTTP_400 = "org.apache.cxf.transport.process_fault_on_http_400";
    public static final String NO_IO_EXCEPTIONS = "org.apache.cxf.transport.no_io_exceptions";
    protected static final Logger LOG = LogUtils.getL7dLogger(HTTPConduit.class);
    private static boolean hasLoggedAsyncWarning;
    private static final String SC_HTTP_CONDUIT_SUFFIX = ".http-conduit";
    private static final String AUTO_REDIRECT_SAME_HOST_ONLY = "http.redirect.same.host.only";
    private static final String AUTO_REDIRECT_ALLOW_REL_URI = "http.redirect.relative.uri";
    private static final String AUTO_REDIRECT_ALLOWED_URI = "http.redirect.allowed.uri";
    private static final String AUTO_REDIRECT_MAX_SAME_URI_COUNT = "http.redirect.max.same.uri.count";
    private static final String HTTP_POST_METHOD = "POST";
    private static final String HTTP_GET_METHOD = "GET";
    private static final Set<String> KNOWN_HTTP_VERBS_WITH_NO_CONTENT;
    private static final String KEY_VISITED_URLS = "VisitedURLs";
    private static final String KEY_AUTH_URLS = "AuthURLs";
    protected final Bus bus;
    protected final EndpointInfo endpointInfo;
    protected volatile Address defaultAddress;
    protected boolean fromEndpointReferenceType;
    protected ProxyFactory proxyFactory;
    protected HTTPClientPolicy clientSidePolicy;
    protected AuthorizationPolicy authorizationPolicy;
    protected ProxyAuthorizationPolicy proxyAuthorizationPolicy;
    protected TLSClientParameters tlsClientParameters;
    protected MessageTrustDecider trustDecider;
    protected volatile HttpAuthSupplier authSupplier;
    protected volatile HttpAuthSupplier proxyAuthSupplier;
    protected Cookies cookies;
    protected CertConstraints certConstraints;
    private volatile boolean clientSidePolicyCalced;

    public HTTPConduit(Bus b, EndpointInfo ei) throws IOException {
        this(b, ei, null);
    }

    public HTTPConduit(Bus b, EndpointInfo ei, EndpointReferenceType t2) throws IOException {
        super(HTTPConduit.getTargetReference(ei, t2, b));
        this.bus = b;
        this.endpointInfo = ei;
        if (t2 != null) {
            this.fromEndpointReferenceType = true;
        }
        this.proxyFactory = new ProxyFactory();
        this.cookies = new Cookies();
    }

    private void updateClientPolicy(Message m4) {
        PolicyDataEngine policyEngine;
        if (!this.clientSidePolicyCalced && (policyEngine = this.bus.getExtension(PolicyDataEngine.class)) != null && this.endpointInfo.getService() != null) {
            this.clientSidePolicy = policyEngine.getClientEndpointPolicy(m4, this.endpointInfo, this, new ClientPolicyCalculator());
            if (this.clientSidePolicy != null) {
                this.clientSidePolicy.removePropertyChangeListener(this);
                this.clientSidePolicy.addPropertyChangeListener(this);
            }
        }
        this.clientSidePolicyCalced = true;
    }

    private void updateClientPolicy() {
        if (!this.clientSidePolicyCalced) {
            MessageImpl m4 = new MessageImpl();
            m4.setExchange(new ExchangeImpl());
            m4.getExchange().put(EndpointInfo.class, this.endpointInfo);
            this.updateClientPolicy(m4);
        }
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    public final String getConduitName() {
        return this.endpointInfo.getName() + SC_HTTP_CONDUIT_SUFFIX;
    }

    private static void configureConduitFromEndpointInfo(HTTPConduit conduit, EndpointInfo endpointInfo) {
        if (conduit.getClient() == null) {
            conduit.setClient(endpointInfo.getTraversedExtensor(new HTTPClientPolicy(), HTTPClientPolicy.class));
        }
        if (conduit.getAuthorization() == null) {
            conduit.setAuthorization(endpointInfo.getTraversedExtensor(new AuthorizationPolicy(), AuthorizationPolicy.class));
        }
        if (conduit.getProxyAuthorization() == null) {
            conduit.setProxyAuthorization(endpointInfo.getTraversedExtensor(new ProxyAuthorizationPolicy(), ProxyAuthorizationPolicy.class));
        }
        if (conduit.getTlsClientParameters() == null) {
            conduit.setTlsClientParameters(endpointInfo.getTraversedExtensor(null, TLSClientParameters.class));
        }
        if (conduit.getTrustDecider() == null) {
            conduit.setTrustDecider(endpointInfo.getTraversedExtensor(null, MessageTrustDecider.class));
        }
        if (conduit.getAuthSupplier() == null) {
            conduit.setAuthSupplier(endpointInfo.getTraversedExtensor(null, HttpAuthSupplier.class));
        }
    }

    private void logConfig() {
        if (!LOG.isLoggable(Level.FINE)) {
            return;
        }
        if (this.trustDecider == null) {
            LOG.log(Level.FINE, "No Trust Decider configured for Conduit '" + this.getConduitName() + "'");
        } else {
            LOG.log(Level.FINE, "Message Trust Decider of class '" + this.trustDecider.getClass().getName() + "' with logical name of '" + this.trustDecider.getLogicalName() + "' has been configured for Conduit '" + this.getConduitName() + "'");
        }
        if (this.authSupplier == null) {
            LOG.log(Level.FINE, "No Auth Supplier configured for Conduit '" + this.getConduitName() + "'");
        } else {
            LOG.log(Level.FINE, "HttpAuthSupplier of class '" + this.authSupplier.getClass().getName() + "' has been configured for Conduit '" + this.getConduitName() + "'");
        }
        if (this.tlsClientParameters != null) {
            LOG.log(Level.FINE, "Conduit '" + this.getConduitName() + "' has been configured for TLS keyManagers " + Arrays.toString(this.tlsClientParameters.getKeyManagers()) + "trustManagers " + Arrays.toString(this.tlsClientParameters.getTrustManagers()) + "secureRandom " + this.tlsClientParameters.getSecureRandom() + "Disable Common Name (CN) Check: " + this.tlsClientParameters.isDisableCNCheck());
        } else {
            LOG.log(Level.FINE, "Conduit '" + this.getConduitName() + "' has been configured for plain http.");
        }
    }

    public void finalizeConfig() {
        HTTPConduit.configureConduitFromEndpointInfo(this, this.endpointInfo);
        this.logConfig();
        if (this.getClient().getDecoupledEndpoint() != null) {
            this.endpointInfo.setProperty("org.apache.cxf.ws.addressing.replyto", this.getClient().getDecoupledEndpoint());
        }
    }

    public Map<String, Cookie> getCookies() {
        return this.cookies.getSessionCookies();
    }

    protected abstract void setupConnection(Message var1, Address var2, HTTPClientPolicy var3) throws IOException;

    @Override
    public void prepare(Message message) throws IOException {
        Address currentAddress;
        try {
            currentAddress = this.setupAddress(message);
        }
        catch (URISyntaxException e) {
            throw new IOException(e);
        }
        boolean needToCacheRequest = false;
        HTTPClientPolicy csPolicy = this.getClient(message);
        this.setupConnection(message, currentAddress, csPolicy);
        String httpRequestMethod = (String)message.get("org.apache.cxf.request.method");
        if (httpRequestMethod == null) {
            httpRequestMethod = HTTP_POST_METHOD;
            message.put("org.apache.cxf.request.method", HTTP_POST_METHOD);
        }
        boolean isChunking = false;
        int chunkThreshold = 0;
        AuthorizationPolicy effectiveAuthPolicy = this.getEffectiveAuthPolicy(message);
        if (this.authSupplier == null) {
            this.authSupplier = this.createAuthSupplier(effectiveAuthPolicy);
        }
        if (this.proxyAuthSupplier == null) {
            this.proxyAuthSupplier = this.createAuthSupplier(this.proxyAuthorizationPolicy);
        }
        if (this.authSupplier.requiresRequestCaching()) {
            needToCacheRequest = true;
            isChunking = false;
            LOG.log(Level.FINE, "Auth Supplier, but no Preemptive User Pass or Digest auth (nonce may be stale) We must cache request.");
        }
        if (csPolicy.isAutoRedirect()) {
            needToCacheRequest = true;
            LOG.log(Level.FINE, "AutoRedirect is turned on.");
        }
        if (csPolicy.getMaxRetransmits() > 0) {
            needToCacheRequest = true;
            LOG.log(Level.FINE, "MaxRetransmits is set > 0.");
        }
        if (csPolicy.isAllowChunking() && this.isChunkingSupported(message, httpRequestMethod)) {
            isChunking = true;
            chunkThreshold = csPolicy.getChunkingThreshold();
        }
        this.cookies.writeToMessageHeaders(message);
        if (this.certConstraints != null) {
            message.put(CertConstraints.class.getName(), this.certConstraints);
            message.getInterceptorChain().add(CertConstraintsInterceptor.INSTANCE);
        }
        this.setHeadersByAuthorizationPolicy(message, currentAddress.getURI());
        new Headers(message).setFromClientPolicy(this.getClient(message));
        ProxyOutputStream pos = message.getContent(ProxyOutputStream.class);
        if (pos != null && message.getContent(OutputStream.class) != null) {
            pos.setWrappedOutputStream(this.createOutputStream(message, needToCacheRequest, isChunking, chunkThreshold));
        } else {
            message.setContent(OutputStream.class, this.createOutputStream(message, needToCacheRequest, isChunking, chunkThreshold));
        }
    }

    protected boolean isChunkingSupported(Message message, String httpMethod) {
        MessageContentsList objs;
        if (HTTP_POST_METHOD.equals(httpMethod)) {
            return true;
        }
        if (!HTTP_GET_METHOD.equals(httpMethod) && (objs = MessageContentsList.getContentsList(message)) != null && !objs.isEmpty()) {
            Object obj = objs.get(0);
            return obj.getClass() != String.class || obj.getClass() == String.class && ((String)obj).length() > 0;
        }
        return false;
    }

    protected abstract OutputStream createOutputStream(Message var1, boolean var2, boolean var3, int var4) throws IOException;

    private HttpAuthSupplier createAuthSupplier(AuthorizationPolicy authzPolicy) {
        String authType = authzPolicy.getAuthorizationType();
        if ("Negotiate".equals(authType)) {
            return new SpnegoAuthSupplier();
        }
        if ("Digest".equals(authType)) {
            return new DigestAuthSupplier();
        }
        if (authType != null && !"Basic".equals(authType) && authzPolicy.getAuthorization() != null) {
            return new CustomAuthSupplier();
        }
        return new DefaultBasicAuthSupplier();
    }

    protected static int determineReceiveTimeout(Message message, HTTPClientPolicy csPolicy) {
        long rtimeout = csPolicy.getReceiveTimeout();
        if (message.get("javax.xml.ws.client.receiveTimeout") != null) {
            Object obj = message.get("javax.xml.ws.client.receiveTimeout");
            try {
                rtimeout = Long.parseLong(obj.toString());
            }
            catch (NumberFormatException e) {
                LOG.log(Level.WARNING, "INVALID_TIMEOUT_FORMAT", new Object[]{"javax.xml.ws.client.receiveTimeout", obj.toString()});
            }
        }
        if (rtimeout > Integer.MAX_VALUE) {
            rtimeout = Integer.MAX_VALUE;
        }
        return (int)rtimeout;
    }

    protected static int determineConnectionTimeout(Message message, HTTPClientPolicy csPolicy) {
        long ctimeout = csPolicy.getConnectionTimeout();
        if (message.get("javax.xml.ws.client.connectionTimeout") != null) {
            Object obj = message.get("javax.xml.ws.client.connectionTimeout");
            try {
                ctimeout = Long.parseLong(obj.toString());
            }
            catch (NumberFormatException e) {
                LOG.log(Level.WARNING, "INVALID_TIMEOUT_FORMAT", new Object[]{"javax.xml.ws.client.connectionTimeout", obj.toString()});
            }
        }
        if (ctimeout > Integer.MAX_VALUE) {
            ctimeout = Integer.MAX_VALUE;
        }
        return (int)ctimeout;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close(Message msg) throws IOException {
        InputStream in = msg.getContent(InputStream.class);
        try {
            if (in != null) {
                byte[] buffer = new byte[1024];
                for (int count = 0; in.read(buffer) != -1 && count < 25; ++count) {
                }
            }
        }
        finally {
            super.close(msg);
        }
    }

    private Address setupAddress(Message message) throws URISyntaxException {
        String result = (String)message.get(Message.ENDPOINT_ADDRESS);
        String pathInfo = (String)message.get(Message.PATH_INFO);
        String queryString = (String)message.get(Message.QUERY_STRING);
        this.setAndGetDefaultAddress();
        if (result == null) {
            if (pathInfo == null && queryString == null) {
                if (this.defaultAddress != null) {
                    message.put(Message.ENDPOINT_ADDRESS, this.defaultAddress.getString());
                }
                return this.defaultAddress;
            }
            if (this.defaultAddress != null) {
                result = this.defaultAddress.getString();
                message.put(Message.ENDPOINT_ADDRESS, result);
            }
        }
        if (null != pathInfo && !result.endsWith(pathInfo)) {
            result = result + pathInfo;
        }
        if (queryString != null) {
            result = result + "?" + queryString;
        }
        if (this.defaultAddress == null) {
            return this.setAndGetDefaultAddress(result);
        }
        return result.equals(this.defaultAddress.getString()) ? this.defaultAddress : new Address(result);
    }

    @Override
    public void close() {
        if (this.clientSidePolicy != null) {
            this.clientSidePolicy.removePropertyChangeListener(this);
        }
    }

    public String getAddress() {
        if (this.defaultAddress != null) {
            return this.defaultAddress.getString();
        }
        if (this.fromEndpointReferenceType) {
            return this.getTarget().getAddress().getValue();
        }
        return this.endpointInfo.getAddress();
    }

    protected URI getURI() throws URISyntaxException {
        return this.setAndGetDefaultAddress().getURI();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Address setAndGetDefaultAddress() throws URISyntaxException {
        if (this.defaultAddress == null) {
            HTTPConduit hTTPConduit = this;
            synchronized (hTTPConduit) {
                if (this.defaultAddress == null) {
                    if (this.fromEndpointReferenceType && this.getTarget().getAddress().getValue() != null) {
                        this.defaultAddress = new Address(this.getTarget().getAddress().getValue());
                    } else if (this.endpointInfo.getAddress() != null) {
                        this.defaultAddress = new Address(this.endpointInfo.getAddress());
                    }
                }
            }
        }
        return this.defaultAddress;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Address setAndGetDefaultAddress(String curAddr) throws URISyntaxException {
        if (this.defaultAddress == null) {
            HTTPConduit hTTPConduit = this;
            synchronized (hTTPConduit) {
                if (this.defaultAddress == null) {
                    if (curAddr != null) {
                        this.defaultAddress = new Address(curAddr);
                    } else {
                        throw new URISyntaxException("<null>", "Invalid address. Endpoint address cannot be null.", 0);
                    }
                }
            }
        }
        return this.defaultAddress;
    }

    protected void setHeadersByAuthorizationPolicy(Message message, URI currentURI) {
        String proxyAuthString;
        Headers headers = new Headers(message);
        AuthorizationPolicy effectiveAuthPolicy = this.getEffectiveAuthPolicy(message);
        String authString = this.authSupplier.getAuthorization(effectiveAuthPolicy, currentURI, message, null);
        if (authString != null) {
            headers.setAuthorization(authString);
        }
        if ((proxyAuthString = this.proxyAuthSupplier.getAuthorization(this.proxyAuthorizationPolicy, currentURI, message, null)) != null) {
            headers.setProxyAuthorization(proxyAuthString);
        }
    }

    @Override
    public String getBeanName() {
        if (this.endpointInfo.getName() != null) {
            return this.endpointInfo.getName().toString() + SC_HTTP_CONDUIT_SUFFIX;
        }
        return null;
    }

    public AuthorizationPolicy getEffectiveAuthPolicy(Message message) {
        AuthorizationPolicy authPolicy = this.getAuthorization();
        AuthorizationPolicy newPolicy = message.get(AuthorizationPolicy.class);
        AuthorizationPolicy effectivePolicy = newPolicy;
        if (effectivePolicy == null) {
            effectivePolicy = authPolicy;
        }
        if (effectivePolicy == null) {
            effectivePolicy = new AuthorizationPolicy();
        }
        return effectivePolicy;
    }

    public AuthorizationPolicy getAuthorization() {
        return this.authorizationPolicy;
    }

    public void setAuthorization(AuthorizationPolicy authorization) {
        this.authorizationPolicy = authorization;
    }

    public HTTPClientPolicy getClient(Message message) {
        ClientPolicyCalculator cpc = new ClientPolicyCalculator();
        HTTPClientPolicy pol = message.get(HTTPClientPolicy.class);
        this.updateClientPolicy(message);
        pol = pol != null ? cpc.intersect(pol, this.clientSidePolicy) : this.clientSidePolicy;
        PolicyDataEngine policyDataEngine = this.bus.getExtension(PolicyDataEngine.class);
        if (policyDataEngine == null) {
            return pol;
        }
        return policyDataEngine.getPolicy(message, pol, cpc);
    }

    public HTTPClientPolicy getClient() {
        this.updateClientPolicy();
        return this.clientSidePolicy;
    }

    public void setClient(HTTPClientPolicy client) {
        if (this.clientSidePolicy != null) {
            this.clientSidePolicy.removePropertyChangeListener(this);
        }
        this.clientSidePolicyCalced = true;
        this.clientSidePolicy = client;
        this.clientSidePolicy.removePropertyChangeListener(this);
        this.clientSidePolicy.addPropertyChangeListener(this);
        this.endpointInfo.setProperty("org.apache.cxf.ws.addressing.replyto", client.getDecoupledEndpoint());
    }

    public ProxyAuthorizationPolicy getProxyAuthorization() {
        return this.proxyAuthorizationPolicy;
    }

    public void setProxyAuthorization(ProxyAuthorizationPolicy proxyAuthorization) {
        this.proxyAuthorizationPolicy = proxyAuthorization;
    }

    public TLSClientParameters getTlsClientParameters() {
        return this.tlsClientParameters;
    }

    public void setTlsClientParameters(TLSClientParameters params) {
        this.tlsClientParameters = params;
        if (this.tlsClientParameters != null) {
            CertificateConstraintsType constraints;
            if (LOG.isLoggable(Level.FINE)) {
                LOG.log(Level.FINE, "Conduit '" + this.getConduitName() + "' has been (re) configured for TLS keyManagers " + Arrays.toString(this.tlsClientParameters.getKeyManagers()) + "trustManagers " + Arrays.toString(this.tlsClientParameters.getTrustManagers()) + "secureRandom " + this.tlsClientParameters.getSecureRandom());
            }
            if ((constraints = params.getCertConstraints()) != null) {
                this.certConstraints = CertConstraintsJaxBUtils.createCertConstraints(constraints);
            }
        } else if (LOG.isLoggable(Level.FINE)) {
            LOG.log(Level.FINE, "Conduit '" + this.getConduitName() + "' has been (re)configured for plain http.");
        }
    }

    public MessageTrustDecider getTrustDecider() {
        return this.trustDecider;
    }

    public void setTrustDecider(MessageTrustDecider decider) {
        this.trustDecider = decider;
    }

    public HttpAuthSupplier getAuthSupplier() {
        return this.authSupplier;
    }

    public void setAuthSupplier(HttpAuthSupplier supplier) {
        this.authSupplier = supplier;
    }

    public HttpAuthSupplier getProxyAuthSupplier() {
        return this.proxyAuthSupplier;
    }

    public void setProxyAuthSupplier(HttpAuthSupplier proxyAuthSupplier) {
        this.proxyAuthSupplier = proxyAuthSupplier;
    }

    protected String extractLocation(Map<String, List<String>> headers) throws MalformedURLException {
        for (Map.Entry<String, List<String>> head : headers.entrySet()) {
            List<String> locs;
            if (!"Location".equalsIgnoreCase(head.getKey()) || (locs = head.getValue()) == null || locs.isEmpty()) continue;
            String location = locs.get(0);
            if (location != null) {
                return location;
            }
            return null;
        }
        return null;
    }

    protected void logStackTrace(Throwable ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        LOG.warning(sw.toString());
    }

    @Override
    public void assertMessage(Message message) {
        PolicyDataEngine policyDataEngine = this.bus.getExtension(PolicyDataEngine.class);
        policyDataEngine.assertMessage(message, this.getClient(), new ClientPolicyCalculator());
    }

    @Override
    public boolean canAssert(QName type) {
        return type.equals(new QName("http://cxf.apache.org/transports/http/configuration", "client"));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == this.clientSidePolicy && "decoupledEndpoint".equals(evt.getPropertyName())) {
            this.endpointInfo.setProperty("org.apache.cxf.ws.addressing.replyto", evt.getNewValue());
        }
    }

    private static void checkAllowedRedirectUri(String conduitName, String lastURL, String newURL, Message message) throws IOException {
        if (newURL != null) {
            String allowedRedirectURI;
            URI newUri = URI.create(newURL);
            if (MessageUtils.getContextualBoolean(message, AUTO_REDIRECT_SAME_HOST_ONLY)) {
                URI lastUri = URI.create(lastURL);
                if (!newUri.getScheme().equals(lastUri.getScheme()) || !newUri.getHost().equals(lastUri.getHost())) {
                    String msg = "Different HTTP Scheme or Host Redirect detected on Conduit '" + conduitName + "' on '" + newURL + "'";
                    LOG.log(Level.INFO, msg);
                    throw new IOException(msg);
                }
            }
            if ((allowedRedirectURI = (String)message.getContextualProperty(AUTO_REDIRECT_ALLOWED_URI)) != null && !newURL.startsWith(allowedRedirectURI)) {
                String msg = "Forbidden Redirect URI " + newURL + "detected on Conduit '" + conduitName;
                LOG.log(Level.INFO, msg);
                throw new IOException(msg);
            }
        }
    }

    private static String convertToAbsoluteUrlIfNeeded(String conduitName, String lastURL, String newURL, Message message) throws IOException {
        if (newURL != null && !newURL.startsWith("http")) {
            if (MessageUtils.getContextualBoolean(message, AUTO_REDIRECT_ALLOW_REL_URI)) {
                return URI.create(lastURL).resolve(newURL).toString();
            }
            String msg = "Relative Redirect detected on Conduit '" + conduitName + "' on '" + newURL + "'";
            LOG.log(Level.INFO, msg);
            throw new IOException(msg);
        }
        return newURL;
    }

    private static void detectRedirectLoop(String conduitName, String lastURL, String newURL, Message message) throws IOException {
        Integer visitCount;
        Map visitedURLs = CastUtils.cast((Map)message.get(KEY_VISITED_URLS));
        if (visitedURLs == null) {
            visitedURLs = new HashMap();
            message.put(KEY_VISITED_URLS, visitedURLs);
        }
        if ((visitCount = (Integer)visitedURLs.get(lastURL)) == null) {
            visitCount = 1;
        } else {
            Integer n = visitCount;
            Integer n2 = visitCount = Integer.valueOf(visitCount + 1);
        }
        visitedURLs.put(lastURL, visitCount);
        Integer newURLCount = (Integer)visitedURLs.get(newURL);
        if (newURL != null && newURLCount != null) {
            boolean invalidLoopDetected = newURL.equals(lastURL);
            Integer maxSameURICount = PropertyUtils.getInteger(message, AUTO_REDIRECT_MAX_SAME_URI_COUNT);
            if (!invalidLoopDetected) {
                if (maxSameURICount == null || newURLCount > maxSameURICount) {
                    invalidLoopDetected = true;
                }
            } else if (maxSameURICount != null && newURLCount <= maxSameURICount) {
                invalidLoopDetected = false;
            }
            if (invalidLoopDetected) {
                String msg = "Redirect loop detected on Conduit '" + conduitName + "' on '" + newURL + "'";
                LOG.log(Level.INFO, msg);
                throw new IOException(msg);
            }
        }
    }

    private static void detectAuthorizationLoop(String conduitName, Message message, URI currentURL, String realm) throws IOException {
        HashSet<String> authURLs = (HashSet<String>)message.get(KEY_AUTH_URLS);
        if (authURLs == null) {
            authURLs = new HashSet<String>();
            message.put(KEY_AUTH_URLS, authURLs);
        }
        if (authURLs.contains(currentURL.toString() + realm)) {
            String logMessage = "Authorization loop detected on Conduit \"" + conduitName + "\" on URL \"" + currentURL + "\" with realm \"" + realm + "\"";
            if (LOG.isLoggable(Level.INFO)) {
                LOG.log(Level.INFO, logMessage);
            }
            throw new IOException(logMessage);
        }
        authURLs.add(currentURL.toString() + realm);
    }

    static {
        KNOWN_HTTP_VERBS_WITH_NO_CONTENT = new HashSet<String>(Arrays.asList(HTTP_GET_METHOD, "HEAD", "OPTIONS", "TRACE"));
    }

    protected abstract class WrappedOutputStream
    extends AbstractThresholdOutputStream {
        protected boolean cachingForRetransmission;
        protected final boolean chunking;
        protected CacheAndWriteOutputStream cachedStream;
        protected Message outMessage;
        protected String conduitName;
        protected URI url;

        protected WrappedOutputStream(Message outMessage, boolean possibleRetransmit, boolean isChunking, int chunkThreshold, String conduitName, URI url2) {
            super(chunkThreshold);
            this.outMessage = outMessage;
            this.cachingForRetransmission = possibleRetransmit;
            this.chunking = isChunking;
            this.conduitName = conduitName;
            this.url = url2;
        }

        protected WrappedOutputStream(WrappedOutputStream wos) {
            super(wos.threshold);
            this.outMessage = wos.outMessage;
            this.cachingForRetransmission = wos.cachingForRetransmission;
            this.chunking = wos.chunking;
            this.conduitName = wos.conduitName;
            this.url = wos.url;
        }

        @Override
        public void thresholdNotReached() {
            if (this.chunking) {
                this.setFixedLengthStreamingMode(this.buffer.size());
            }
        }

        protected abstract void setupWrappedStream() throws IOException;

        protected abstract HttpsURLConnectionInfo getHttpsURLConnectionInfo() throws IOException;

        protected abstract void setProtocolHeaders() throws IOException;

        protected abstract void setFixedLengthStreamingMode(int var1);

        protected abstract int getResponseCode() throws IOException;

        protected abstract String getResponseMessage() throws IOException;

        protected abstract void updateResponseHeaders(Message var1) throws IOException;

        protected abstract void handleResponseAsync() throws IOException;

        protected abstract void closeInputStream() throws IOException;

        protected abstract boolean usingProxy();

        protected abstract InputStream getInputStream() throws IOException;

        protected abstract InputStream getPartialResponse() throws IOException;

        protected abstract void setupNewConnection(String var1) throws IOException;

        protected abstract void retransmitStream() throws IOException;

        protected abstract void updateCookiesBeforeRetransmit() throws IOException;

        protected void handleNoOutput() throws IOException {
        }

        protected void handleResponseOnWorkqueue(boolean allowCurrentThread, boolean forceWQ) throws IOException {
            boolean exceptionSet;
            Runnable runnable2 = new Runnable(){

                @Override
                public void run() {
                    try {
                        WrappedOutputStream.this.handleResponseInternal();
                    }
                    catch (Throwable e) {
                        ((PhaseInterceptorChain)WrappedOutputStream.this.outMessage.getInterceptorChain()).abort();
                        WrappedOutputStream.this.outMessage.setContent(Exception.class, e);
                        ((PhaseInterceptorChain)WrappedOutputStream.this.outMessage.getInterceptorChain()).unwind(WrappedOutputStream.this.outMessage);
                        MessageObserver mo = WrappedOutputStream.this.outMessage.getInterceptorChain().getFaultObserver();
                        if (mo == null) {
                            mo = WrappedOutputStream.this.outMessage.getExchange().get(MessageObserver.class);
                        }
                        mo.onMessage(WrappedOutputStream.this.outMessage);
                    }
                }
            };
            HTTPClientPolicy policy = HTTPConduit.this.getClient(this.outMessage);
            boolean bl = exceptionSet = this.outMessage.getContent(Exception.class) != null;
            if (!exceptionSet) {
                try {
                    Executor ex = this.outMessage.getExchange().get(Executor.class);
                    if (forceWQ && ex != null) {
                        final Executor ex2 = ex;
                        final Runnable origRunnable = runnable2;
                        runnable2 = new Runnable(){

                            @Override
                            public void run() {
                                WrappedOutputStream.this.outMessage.getExchange().put(Executor.class.getName() + ".USING_SPECIFIED", Boolean.TRUE);
                                ex2.execute(origRunnable);
                            }
                        };
                    }
                    if (ex == null || forceWQ) {
                        WorkQueueManager mgr = this.outMessage.getExchange().getBus().getExtension(WorkQueueManager.class);
                        AutomaticWorkQueue qu = mgr.getNamedWorkQueue("http-conduit");
                        if (qu == null) {
                            qu = mgr.getAutomaticWorkQueue();
                        }
                        long timeout2 = 1000L;
                        if (policy != null && policy.isSetAsyncExecuteTimeout()) {
                            timeout2 = policy.getAsyncExecuteTimeout();
                        }
                        if (timeout2 > 0L) {
                            qu.execute(runnable2, timeout2);
                        } else {
                            qu.execute(runnable2);
                        }
                    } else {
                        this.outMessage.getExchange().put(Executor.class.getName() + ".USING_SPECIFIED", Boolean.TRUE);
                        ex.execute(runnable2);
                    }
                }
                catch (RejectedExecutionException rex) {
                    if (!allowCurrentThread || policy != null && policy.isSetAsyncExecuteTimeoutRejection() && policy.isAsyncExecuteTimeoutRejection()) {
                        throw rex;
                    }
                    if (!hasLoggedAsyncWarning) {
                        LOG.warning("EXECUTOR_FULL_WARNING");
                        hasLoggedAsyncWarning = true;
                    }
                    LOG.fine("EXECUTOR_FULL");
                    this.handleResponseInternal();
                }
            }
        }

        protected void retransmit(String newURL) throws IOException {
            this.setupNewConnection(newURL);
            if (this.cachedStream != null && this.cachedStream.size() < Integer.MAX_VALUE) {
                this.setFixedLengthStreamingMode((int)this.cachedStream.size());
            }
            this.setProtocolHeaders();
            this.makeTrustDecision();
            if (HTTPConduit.HTTP_GET_METHOD.equals(this.getMethod()) || this.cachedStream == null) {
                this.handleNoOutput();
                return;
            }
            this.retransmitStream();
            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine("Conduit \"" + HTTPConduit.this.getConduitName() + "\" Retransmit message to: " + newURL + ": " + new String(this.cachedStream.getBytes()));
            }
        }

        @Override
        protected void onFirstWrite() throws IOException {
            try {
                this.handleHeadersTrustCaching();
            }
            catch (IOException e) {
                if (e.getMessage() != null && e.getMessage().contains("HTTPS hostname wrong:")) {
                    throw new IOException("The https URL hostname does not match the Common Name (CN) on the server certificate in the client's truststore.  Make sure server certificate is correct, or to disable this check (NOT recommended for production) set the CXF client TLS configuration property \"disableCNCheck\" to true.");
                }
                throw e;
            }
        }

        protected String getMethod() {
            return (String)this.outMessage.get("org.apache.cxf.request.method");
        }

        protected void handleHeadersTrustCaching() throws IOException {
            this.setProtocolHeaders();
            this.makeTrustDecision();
            String method2 = this.getMethod();
            if (KNOWN_HTTP_VERBS_WITH_NO_CONTENT.contains(method2) || PropertyUtils.isTrue(this.outMessage.get("org.apache.cxf.empty.request"))) {
                this.handleNoOutput();
                return;
            }
            this.setupWrappedStream();
        }

        @Override
        public void close() throws IOException {
            try {
                boolean exceptionSet;
                if (this.buffer != null && this.buffer.size() > 0) {
                    this.thresholdNotReached();
                    LoadingByteArrayOutputStream tmp = this.buffer;
                    this.buffer = null;
                    super.write(tmp.getRawBytes(), 0, tmp.size());
                }
                boolean bl = exceptionSet = this.outMessage.getContent(Exception.class) != null;
                if (!this.written && !exceptionSet) {
                    this.handleHeadersTrustCaching();
                }
                if (!this.cachingForRetransmission) {
                    super.close();
                } else if (this.cachedStream != null) {
                    super.flush();
                    this.cachedStream.getOut().close();
                    this.cachedStream.closeFlowthroughStream();
                }
                try {
                    this.handleResponse();
                }
                finally {
                    if (this.cachingForRetransmission && this.cachedStream != null) {
                        this.cachedStream.close();
                    }
                }
            }
            catch (HttpRetryException e) {
                this.handleHttpRetryException(e);
            }
            catch (IOException e) {
                String origMessage = e.getMessage();
                if (origMessage != null && origMessage.contains(this.url.toString())) {
                    throw e;
                }
                throw this.mapException(e.getClass().getSimpleName() + " invoking " + this.url + ": " + e.getMessage(), e, IOException.class);
            }
            catch (RuntimeException e) {
                throw this.mapException(e.getClass().getSimpleName() + " invoking " + this.url + ": " + e.getMessage(), e, RuntimeException.class);
            }
        }

        private <T extends Exception> T mapException(String msg, T ex, Class<T> cls) {
            Object ex2 = ex;
            try {
                ex2 = (Exception)cls.cast(ex.getClass().getConstructor(String.class).newInstance(msg));
                ((Throwable)ex2).initCause(ex);
            }
            catch (Throwable e) {
                ex2 = ex;
            }
            return (T)ex2;
        }

        protected void handleRetransmits() throws IOException {
            if (this.cachedStream != null || HTTPConduit.this.getClient().isAutoRedirect() && KNOWN_HTTP_VERBS_WITH_NO_CONTENT.contains(this.getMethod()) || HTTPConduit.this.authSupplier != null && HTTPConduit.this.authSupplier.requiresRequestCaching()) {
                if (LOG.isLoggable(Level.FINE) && this.cachedStream != null) {
                    StringBuilder b = new StringBuilder(4096);
                    b.append("Conduit \"").append(HTTPConduit.this.getConduitName()).append("\" Transmit cached message to: ").append(this.url).append(": ");
                    this.cachedStream.writeCacheTo(b, 16384L);
                    LOG.fine(b.toString());
                }
                int maxRetransmits = this.getMaxRetransmits();
                this.updateCookiesBeforeRetransmit();
                for (int nretransmits = 0; (maxRetransmits < 0 || nretransmits < maxRetransmits) && this.processRetransmit(); ++nretransmits) {
                }
            }
        }

        protected boolean processRetransmit() throws IOException {
            int responseCode = this.getResponseCode();
            if (this.outMessage != null && this.outMessage.getExchange() != null) {
                this.outMessage.getExchange().put(Message.RESPONSE_CODE, responseCode);
            }
            switch (responseCode) {
                case 301: 
                case 302: 
                case 303: 
                case 307: {
                    return this.redirectRetransmit();
                }
                case 401: 
                case 407: {
                    return this.authorizationRetransmit();
                }
            }
            return false;
        }

        protected boolean redirectRetransmit() throws IOException {
            if (!HTTPConduit.this.getClient(this.outMessage).isAutoRedirect()) {
                return false;
            }
            MessageImpl m4 = new MessageImpl();
            this.updateResponseHeaders(m4);
            String newURL = HTTPConduit.this.extractLocation(Headers.getSetProtocolHeaders(m4));
            String urlString = this.url.toString();
            try {
                newURL = HTTPConduit.convertToAbsoluteUrlIfNeeded(this.conduitName, urlString, newURL, this.outMessage);
                HTTPConduit.detectRedirectLoop(this.conduitName, urlString, newURL, this.outMessage);
                HTTPConduit.checkAllowedRedirectUri(this.conduitName, urlString, newURL, this.outMessage);
            }
            catch (IOException ex) {
                this.outMessage.getExchange().put("client.redirect.exception", "true");
                throw ex;
            }
            if (newURL != null) {
                new Headers(this.outMessage).removeAuthorizationHeaders();
                try {
                    HTTPConduit.this.setHeadersByAuthorizationPolicy(this.outMessage, new URI(newURL));
                }
                catch (URISyntaxException e) {
                    throw new IOException(e);
                }
                HTTPConduit.this.cookies.writeToMessageHeaders(this.outMessage);
                this.outMessage.put("transport.retransmit.url", newURL);
                this.retransmit(newURL);
                return true;
            }
            return false;
        }

        protected boolean authorizationRetransmit() throws IOException {
            MessageImpl m4 = new MessageImpl();
            this.updateResponseHeaders(m4);
            List<String> authHeaderValues = Headers.getSetProtocolHeaders(m4).get("WWW-Authenticate");
            if (authHeaderValues == null) {
                LOG.warning("WWW-Authenticate response header is not set");
                return false;
            }
            HttpAuthHeader authHeader = new HttpAuthHeader(authHeaderValues);
            URI currentURI = this.url;
            String realm = authHeader.getRealm();
            HTTPConduit.detectAuthorizationLoop(HTTPConduit.this.getConduitName(), this.outMessage, currentURI, realm);
            AuthorizationPolicy effectiveAthPolicy = HTTPConduit.this.getEffectiveAuthPolicy(this.outMessage);
            String authorizationToken = HTTPConduit.this.authSupplier.getAuthorization(effectiveAthPolicy, currentURI, this.outMessage, authHeader.getFullHeader());
            if (authorizationToken == null) {
                return false;
            }
            try {
                this.closeInputStream();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            new Headers(this.outMessage).setAuthorization(authorizationToken);
            HTTPConduit.this.cookies.writeToMessageHeaders(this.outMessage);
            this.retransmit(this.url.toString());
            return true;
        }

        private int getMaxRetransmits() {
            HTTPClientPolicy policy = HTTPConduit.this.getClient(this.outMessage);
            return policy == null ? -1 : policy.getMaxRetransmits();
        }

        protected void handleResponse() throws IOException {
            this.handleRetransmits();
            if (this.outMessage == null || this.outMessage.getExchange() == null || this.outMessage.getExchange().isSynchronous()) {
                this.handleResponseInternal();
            } else {
                this.handleResponseAsync();
            }
        }

        private boolean isOneway(Exchange exchange) {
            return exchange != null && exchange.isOneWay();
        }

        private boolean doProcessResponse(Message message, int responseCode) {
            if (!this.isOneway(message.getExchange())) {
                return true;
            }
            return responseCode == 500 && MessageUtils.getContextualBoolean(message, "org.apache.cxf.oneway.robust", false);
        }

        protected int doProcessResponseCode() throws IOException {
            Exchange exchange = this.outMessage.getExchange();
            int rc = this.getResponseCode();
            if (rc == -1) {
                LOG.warning("HTTP Response code appears to be corrupted");
            }
            if (exchange != null) {
                exchange.put(Message.RESPONSE_CODE, rc);
                if (rc == 404 || rc == 503 || rc == 429) {
                    exchange.put("org.apache.cxf.transport.service_not_available", true);
                }
            }
            if (!(rc < 400 || rc == 500 || MessageUtils.getContextualBoolean(this.outMessage, HTTPConduit.NO_IO_EXCEPTIONS) || rc <= 400 && MessageUtils.getContextualBoolean(this.outMessage, HTTPConduit.PROCESS_FAULT_ON_HTTP_400))) {
                throw new HTTPException(rc, this.getResponseMessage(), this.url.toURL());
            }
            return rc;
        }

        protected void handleResponseInternal() throws IOException {
            String charset;
            String normalizedEncoding;
            Exchange exchange = this.outMessage.getExchange();
            int responseCode = this.doProcessResponseCode();
            InputStream in = null;
            MessageImpl inMessage = new MessageImpl();
            inMessage.setExchange(exchange);
            this.updateResponseHeaders(inMessage);
            inMessage.put(Message.RESPONSE_CODE, responseCode);
            if (MessageUtils.getContextualBoolean(this.outMessage, HTTPConduit.SET_HTTP_RESPONSE_MESSAGE, false)) {
                inMessage.put(HTTPConduit.HTTP_RESPONSE_MESSAGE, this.getResponseMessage());
            }
            this.propagateConduit(exchange, inMessage);
            if ((!this.doProcessResponse(this.outMessage, responseCode) || 202 == responseCode) && MessageUtils.getContextualBoolean(this.outMessage, "org.apache.cxf.transport.process202Response", true)) {
                in = this.getPartialResponse();
                if (in == null || !MessageUtils.getContextualBoolean(this.outMessage, "org.apache.cxf.transport.processOneWayResponse", false)) {
                    ClientCallback cc;
                    this.closeInputStream();
                    if (this.isOneway(exchange) && responseCode > 300) {
                        throw new HTTPException(responseCode, this.getResponseMessage(), this.url.toURL());
                    }
                    Endpoint ep = exchange.getEndpoint();
                    if (null != ep && null != ep.getEndpointInfo() && null == ep.getEndpointInfo().getProperty("org.apache.cxf.ws.addressing.MAPAggregator.decoupledDestination") && null != (cc = exchange.remove(ClientCallback.class))) {
                        cc.handleResponse(null, null);
                    }
                    exchange.put("IN_CHAIN_COMPLETE", Boolean.TRUE);
                    exchange.setInMessage(inMessage);
                    return;
                }
            } else {
                this.outMessage.removeContent(OutputStream.class);
                if (this.cachingForRetransmission && this.cachedStream != null) {
                    this.cachedStream.close();
                }
                this.cachedStream = null;
            }
            if ((normalizedEncoding = HttpHeaderHelper.mapCharset(charset = HttpHeaderHelper.findCharset((String)inMessage.get("Content-Type")))) == null) {
                String m4 = new org.apache.cxf.common.i18n.Message("INVALID_ENCODING_MSG", LOG, charset).toString();
                LOG.log(Level.WARNING, m4);
                throw new IOException(m4);
            }
            inMessage.put(Message.ENCODING, normalizedEncoding);
            if (in == null) {
                in = this.getInputStream();
            }
            if (in == null) {
                in = new ByteArrayInputStream(new byte[0]);
            }
            inMessage.setContent(InputStream.class, in);
            HTTPConduit.this.incomingObserver.onMessage(inMessage);
        }

        protected void propagateConduit(Exchange exchange, Message in) {
            Message out;
            if (exchange != null && (out = exchange.getOutMessage()) != null) {
                in.put(Conduit.class, out.get(Conduit.class));
            }
        }

        protected void handleHttpRetryException(HttpRetryException e) throws IOException {
            String msg = "HTTP response '" + e.responseCode() + ": " + this.getResponseMessage() + "' invoking " + this.url;
            switch (e.responseCode()) {
                case 301: 
                case 302: 
                case 303: 
                case 307: {
                    msg = msg + " that returned location header '" + e.getLocation() + "'";
                    break;
                }
                case 401: {
                    if (HTTPConduit.this.authorizationPolicy == null || HTTPConduit.this.authorizationPolicy.getUserName() == null) {
                        msg = msg + " with NO authorization username configured in conduit " + HTTPConduit.this.getConduitName();
                        break;
                    }
                    msg = msg + " with authorization username '" + HTTPConduit.this.authorizationPolicy.getUserName() + "'";
                    break;
                }
                case 407: {
                    msg = HTTPConduit.this.proxyAuthorizationPolicy == null || HTTPConduit.this.proxyAuthorizationPolicy.getUserName() == null ? msg + " with NO proxy authorization configured in conduit " + HTTPConduit.this.getConduitName() : msg + " with proxy authorization username '" + HTTPConduit.this.proxyAuthorizationPolicy.getUserName() + "'";
                    if (HTTPConduit.this.clientSidePolicy == null || HTTPConduit.this.clientSidePolicy.getProxyServer() == null) {
                        if (this.usingProxy()) {
                            msg = msg + " using a proxy even if NONE is configured in CXF conduit " + HTTPConduit.this.getConduitName() + " (maybe one is configured by java.net.ProxySelector)";
                            break;
                        }
                        msg = msg + " but NO proxy was used by the connection (none configured in cxf conduit and none selected by java.net.ProxySelector)";
                        break;
                    }
                    msg = msg + " using " + (Object)((Object)HTTPConduit.this.clientSidePolicy.getProxyServerType()) + " proxy " + HTTPConduit.this.clientSidePolicy.getProxyServer() + ":" + HTTPConduit.this.clientSidePolicy.getProxyServerPort();
                    break;
                }
            }
            throw new IOException(msg, e);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        protected void makeTrustDecision() throws IOException {
            MessageTrustDecider decider2 = this.outMessage.get(MessageTrustDecider.class);
            if (HTTPConduit.this.trustDecider != null || decider2 != null) {
                try {
                    HttpsURLConnectionInfo info = this.getHttpsURLConnectionInfo();
                    if (HTTPConduit.this.trustDecider != null) {
                        HTTPConduit.this.trustDecider.establishTrust(this.conduitName, info, this.outMessage);
                        if (LOG.isLoggable(Level.FINE)) {
                            LOG.log(Level.FINE, "Trust Decider " + HTTPConduit.this.trustDecider.getLogicalName() + " considers Conduit " + this.conduitName + " trusted.");
                        }
                    }
                    if (decider2 == null) return;
                    decider2.establishTrust(this.conduitName, info, this.outMessage);
                    if (!LOG.isLoggable(Level.FINE)) return;
                    LOG.log(Level.FINE, "Trust Decider " + decider2.getLogicalName() + " considers Conduit " + this.conduitName + " trusted.");
                    return;
                }
                catch (UntrustedURLConnectionIOException untrustedEx) {
                    if (!LOG.isLoggable(Level.FINE)) throw untrustedEx;
                    LOG.log(Level.FINE, "Trust Decider " + (HTTPConduit.this.trustDecider != null ? HTTPConduit.this.trustDecider.getLogicalName() : decider2.getLogicalName()) + " considers Conduit " + this.conduitName + " untrusted.", untrustedEx);
                    throw untrustedEx;
                }
            } else {
                if (!LOG.isLoggable(Level.FINE)) return;
                LOG.log(Level.FINE, "No Trust Decider for Conduit '" + this.conduitName + "'. An affirmative Trust Decision is assumed.");
            }
        }
    }

    protected class InterposedMessageObserver
    implements MessageObserver {
        protected InterposedMessageObserver() {
        }

        @Override
        public void onMessage(Message inMessage) {
            inMessage.setExchange(new ExchangeImpl());
            inMessage.getExchange().put(Bus.class, HTTPConduit.this.bus);
            inMessage.put("decoupled.channel.message", Boolean.TRUE);
            Headers.getSetProtocolHeaders(inMessage);
            inMessage.put(Message.RESPONSE_CODE, 200);
            inMessage.remove("HTTP.REQUEST");
            inMessage.remove("HTTP.RESPONSE");
            inMessage.remove("org.apache.cxf.async.post.response.dispatch");
            try {
                InputStream in = inMessage.getContent(InputStream.class);
                if (in != null) {
                    CachedOutputStream cos = new CachedOutputStream();
                    IOUtils.copy(in, cos);
                    inMessage.setContent(InputStream.class, cos.getInputStream());
                }
                HTTPConduit.this.incomingObserver.onMessage(inMessage);
            }
            catch (IOException e) {
                HTTPConduit.this.logStackTrace(e);
            }
        }
    }
}

