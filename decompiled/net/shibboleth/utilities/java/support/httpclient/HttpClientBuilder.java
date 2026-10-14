/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.httpclient;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.IterableSupport;
import net.shibboleth.utilities.java.support.httpclient.HttpClientSupport;
import net.shibboleth.utilities.java.support.httpclient.RequestConnectionClose;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.util.CharsetUtils;

public class HttpClientBuilder {
    private InetAddress socketLocalAddress;
    private int socketTimeout;
    private int socketBufferSize;
    private int connectionTimeout;
    private int connectionRequestTimeout;
    private int maxConnectionsTotal;
    private int maxConnectionsPerRoute;
    private boolean connectionDisregardTLSCertificate;
    @Nullable
    private LayeredConnectionSocketFactory tlsSocketFactory;
    private boolean connectionCloseAfterResponse;
    private boolean connectionStaleCheck;
    @Nullable
    private String connectionProxyHost;
    @Nullable
    private String userAgent;
    private int connectionProxyPort;
    @Nullable
    private String connectionProxyUsername;
    @Nullable
    private String connectionProxyPassword;
    private boolean httpFollowRedirects;
    @Nullable
    private String httpContentCharSet;
    @Nullable
    private HttpRequestRetryHandler retryHandler;
    @Nullable
    private ServiceUnavailableRetryStrategy serviceUnavailStrategy;
    private boolean disableAuthCaching;
    private boolean disableAutomaticRetries;
    private boolean disableConnectionState;
    private boolean disableContentCompression;
    private boolean disableCookieManagement;
    private boolean disableRedirectHandling;
    private boolean useSystemProperties;
    private List<HttpRequestInterceptor> requestInterceptorsFirst;
    private List<HttpRequestInterceptor> requestInterceptorsLast;
    private List<HttpResponseInterceptor> responseInterceptorsFirst;
    private List<HttpResponseInterceptor> responseInterceptorsLast;
    private org.apache.http.impl.client.HttpClientBuilder apacheBuilder;

    public HttpClientBuilder() {
        this(org.apache.http.impl.client.HttpClientBuilder.create());
    }

    public HttpClientBuilder(@Nonnull org.apache.http.impl.client.HttpClientBuilder builder) {
        Constraint.isNotNull(builder, "Apache HttpClientBuilder may not be null");
        this.apacheBuilder = builder;
        this.resetDefaults();
    }

    public void resetDefaults() {
        this.maxConnectionsTotal = -1;
        this.maxConnectionsPerRoute = -1;
        this.socketLocalAddress = null;
        this.socketBufferSize = 8192;
        this.socketTimeout = 60000;
        this.connectionTimeout = 60000;
        this.connectionRequestTimeout = 60000;
        this.connectionDisregardTLSCertificate = false;
        this.connectionCloseAfterResponse = true;
        this.connectionStaleCheck = false;
        this.connectionProxyHost = null;
        this.connectionProxyPort = 8080;
        this.connectionProxyUsername = null;
        this.connectionProxyPassword = null;
        this.httpFollowRedirects = true;
        this.httpContentCharSet = "UTF-8";
        this.userAgent = null;
    }

    public int getMaxConnectionsTotal() {
        return this.maxConnectionsTotal;
    }

    public void setMaxConnectionsTotal(int max) {
        this.maxConnectionsTotal = max;
    }

    public int getMaxConnectionsPerRoute() {
        return this.maxConnectionsPerRoute;
    }

    public void setMaxConnectionsPerRoute(int max) {
        this.maxConnectionsPerRoute = max;
    }

    public InetAddress getSocketLocalAddress() {
        return this.socketLocalAddress;
    }

    public void setSocketLocalAddress(InetAddress address) {
        this.socketLocalAddress = address;
    }

    public void setSocketLocalAddress(String ipOrHost) throws UnknownHostException {
        this.socketLocalAddress = InetAddress.getByName(Constraint.isNotNull(ipOrHost, "IP or hostname may not be null"));
    }

    public int getSocketTimeout() {
        return this.socketTimeout;
    }

    public void setSocketTimeout(int timeout2) {
        this.socketTimeout = timeout2;
    }

    public int getSocketBufferSize() {
        return this.socketBufferSize;
    }

    public void setSocketBufferSize(int size) {
        this.socketBufferSize = (int)Constraint.isGreaterThan(0L, size, "Socket buffer size must be greater than 0");
    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setConnectionTimeout(int timeout2) {
        this.connectionTimeout = timeout2;
    }

    public int getConnectionRequestTimeout() {
        return this.connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int timeout2) {
        this.connectionRequestTimeout = timeout2;
    }

    public boolean isConnectionDisregardTLSCertificate() {
        return this.connectionDisregardTLSCertificate;
    }

    public void setConnectionDisregardTLSCertificate(boolean disregard) {
        this.connectionDisregardTLSCertificate = disregard;
    }

    @Nullable
    public LayeredConnectionSocketFactory getTLSSocketFactory() {
        return this.tlsSocketFactory;
    }

    public void setTLSSocketFactory(@Nullable LayeredConnectionSocketFactory factory2) {
        this.tlsSocketFactory = factory2;
    }

    public boolean isConnectionCloseAfterResponse() {
        return this.connectionCloseAfterResponse;
    }

    public void setConnectionCloseAfterResponse(boolean close) {
        this.connectionCloseAfterResponse = close;
    }

    public boolean isConnectionStalecheck() {
        return this.isConnectionStaleCheck();
    }

    public void setConnectionStalecheck(boolean check2) {
        this.setConnectionStaleCheck(check2);
    }

    public boolean isConnectionStaleCheck() {
        return this.connectionStaleCheck;
    }

    public void setConnectionStaleCheck(boolean check2) {
        this.connectionStaleCheck = check2;
    }

    @Nullable
    public String getConnectionProxyHost() {
        return this.connectionProxyHost;
    }

    public void setConnectionProxyHost(@Nullable String host2) {
        this.connectionProxyHost = StringSupport.trimOrNull(host2);
    }

    public int getConnectionProxyPort() {
        return this.connectionProxyPort;
    }

    public void setConnectionProxyPort(int port) {
        this.connectionProxyPort = (int)Constraint.numberInRangeExclusive(0L, 65536L, port, "Proxy port must be between 0 and 65536, exclusive");
    }

    @Nullable
    public String getConnectionProxyUsername() {
        return this.connectionProxyUsername;
    }

    public void setConnectionProxyUsername(@Nullable String usename) {
        this.connectionProxyUsername = usename;
    }

    @Nullable
    public String getConnectionProxyPassword() {
        return this.connectionProxyPassword;
    }

    public void setConnectionProxyPassword(@Nullable String password) {
        this.connectionProxyPassword = password;
    }

    public boolean isHttpFollowRedirects() {
        return this.httpFollowRedirects;
    }

    public void setHttpFollowRedirects(boolean followRedirects) {
        this.httpFollowRedirects = followRedirects;
    }

    @Nullable
    public String getHttpContentCharSet() {
        return this.httpContentCharSet;
    }

    public void setHttpContentCharSet(@Nullable String charSet) {
        this.httpContentCharSet = charSet;
    }

    @Nullable
    public String getUserAgent() {
        return this.userAgent;
    }

    public void setUserAgent(@Nullable String what) {
        this.userAgent = what;
    }

    @Nullable
    public HttpRequestRetryHandler getHttpRequestRetryHandler() {
        return this.retryHandler;
    }

    public void setHttpRequestRetryHandler(@Nullable HttpRequestRetryHandler handler) {
        this.retryHandler = handler;
    }

    @Nullable
    public ServiceUnavailableRetryStrategy getServiceUnavailableRetryHandler() {
        return this.serviceUnavailStrategy;
    }

    public void setServiceUnavailableRetryHandler(@Nullable ServiceUnavailableRetryStrategy strategy) {
        this.serviceUnavailStrategy = strategy;
    }

    public boolean isDisableAuthCaching() {
        return this.disableAuthCaching;
    }

    public void setDisableAuthCaching(boolean flag) {
        this.disableAuthCaching = flag;
    }

    public boolean isDisableAutomaticRetries() {
        return this.disableAutomaticRetries;
    }

    public void setDisableAutomaticRetries(boolean flag) {
        this.disableAutomaticRetries = flag;
    }

    public boolean isDisableConnectionState() {
        return this.disableConnectionState;
    }

    public void setDisableConnectionState(boolean flag) {
        this.disableConnectionState = flag;
    }

    public boolean isDisableContentCompression() {
        return this.disableContentCompression;
    }

    public void setDisableContentCompression(boolean flag) {
        this.disableContentCompression = flag;
    }

    public boolean isDisableCookieManagement() {
        return this.disableCookieManagement;
    }

    public void setDisableCookieManagement(boolean flag) {
        this.disableCookieManagement = flag;
    }

    public boolean isDisableRedirectHandling() {
        return this.disableRedirectHandling;
    }

    public void setDisableRedirectHandling(boolean flag) {
        this.disableRedirectHandling = flag;
    }

    public boolean isUseSystemProperties() {
        return this.useSystemProperties;
    }

    public void setUseSystemProperties(boolean flag) {
        this.useSystemProperties = flag;
    }

    @Nullable
    public List<HttpRequestInterceptor> getFirstRequestInterceptors() {
        return this.requestInterceptorsFirst;
    }

    public void setFirstRequestInterceptors(@Nullable List<HttpRequestInterceptor> interceptors) {
        this.requestInterceptorsFirst = this.normalizeInterceptors(interceptors);
    }

    @Nullable
    public List<HttpRequestInterceptor> getLastRequestInterceptors() {
        return this.requestInterceptorsLast;
    }

    public void setLastRequestInterceptors(List<HttpRequestInterceptor> interceptors) {
        this.requestInterceptorsLast = this.normalizeInterceptors(interceptors);
    }

    @Nullable
    public List<HttpResponseInterceptor> getFirstResponseInterceptors() {
        return this.responseInterceptorsFirst;
    }

    public void setFirstResponseInterceptors(List<HttpResponseInterceptor> interceptors) {
        this.responseInterceptorsFirst = this.normalizeInterceptors(interceptors);
    }

    @Nullable
    public List<HttpResponseInterceptor> getLastResponseInterceptors() {
        return this.responseInterceptorsLast;
    }

    public void setLastResponseInterceptors(List<HttpResponseInterceptor> interceptors) {
        this.responseInterceptorsLast = this.normalizeInterceptors(interceptors);
    }

    @Nullable
    private List<? extends Object> normalizeInterceptors(@Nullable List<? extends Object> interceptors) {
        if (interceptors == null) {
            return null;
        }
        return new ArrayList<Object>(Collections2.filter(interceptors, Predicates.notNull()));
    }

    public HttpClient buildClient() throws Exception {
        this.decorateApacheBuilder();
        return this.getApacheBuilder().build();
    }

    protected void decorateApacheBuilder() throws Exception {
        org.apache.http.impl.client.HttpClientBuilder builder = this.getApacheBuilder();
        if (this.getTLSSocketFactory() != null) {
            builder.setSSLSocketFactory(this.getTLSSocketFactory());
        } else if (this.connectionDisregardTLSCertificate) {
            builder.setSSLSocketFactory(HttpClientSupport.buildNoTrustTLSSocketFactory());
        } else {
            builder.setSSLSocketFactory(HttpClientSupport.buildStrictTLSSocketFactory());
        }
        if (!(!this.connectionCloseAfterResponse || this.getFirstRequestInterceptors() != null && IterableSupport.containsInstance(this.getFirstRequestInterceptors(), RequestConnectionClose.class) || this.getLastRequestInterceptors() != null && IterableSupport.containsInstance(this.getLastRequestInterceptors(), RequestConnectionClose.class))) {
            builder.addInterceptorLast(new RequestConnectionClose());
        }
        if (this.maxConnectionsTotal > 0) {
            builder.setMaxConnTotal(this.maxConnectionsTotal);
        }
        if (this.maxConnectionsPerRoute > 0) {
            builder.setMaxConnPerRoute(this.maxConnectionsPerRoute);
        }
        if (this.retryHandler != null) {
            builder.setRetryHandler(this.retryHandler);
        }
        if (this.serviceUnavailStrategy != null) {
            builder.setServiceUnavailableRetryStrategy(this.serviceUnavailStrategy);
        }
        if (this.isDisableAuthCaching()) {
            builder.disableAuthCaching();
        }
        if (this.isDisableAutomaticRetries()) {
            builder.disableAutomaticRetries();
        }
        if (this.isDisableConnectionState()) {
            builder.disableConnectionState();
        }
        if (this.isDisableContentCompression()) {
            builder.disableContentCompression();
        }
        if (this.isDisableCookieManagement()) {
            builder.disableCookieManagement();
        }
        if (this.isDisableRedirectHandling()) {
            builder.disableRedirectHandling();
        }
        if (this.isUseSystemProperties()) {
            builder.useSystemProperties();
        }
        if (this.getFirstRequestInterceptors() != null) {
            for (HttpRequestInterceptor httpRequestInterceptor : this.getFirstRequestInterceptors()) {
                builder.addInterceptorFirst(httpRequestInterceptor);
            }
        }
        if (this.getLastRequestInterceptors() != null) {
            for (HttpRequestInterceptor httpRequestInterceptor : this.getLastRequestInterceptors()) {
                builder.addInterceptorLast(httpRequestInterceptor);
            }
        }
        if (this.getFirstResponseInterceptors() != null) {
            for (HttpResponseInterceptor httpResponseInterceptor : this.getFirstResponseInterceptors()) {
                builder.addInterceptorFirst(httpResponseInterceptor);
            }
        }
        if (this.getLastResponseInterceptors() != null) {
            for (HttpResponseInterceptor httpResponseInterceptor : this.getLastResponseInterceptors()) {
                builder.addInterceptorLast(httpResponseInterceptor);
            }
        }
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        if (this.socketLocalAddress != null) {
            requestConfigBuilder.setLocalAddress(this.socketLocalAddress);
        }
        if (this.socketTimeout >= 0) {
            requestConfigBuilder.setSocketTimeout(this.socketTimeout);
        }
        if (this.connectionTimeout >= 0) {
            requestConfigBuilder.setConnectTimeout(this.connectionTimeout);
        }
        if (this.connectionRequestTimeout >= 0) {
            requestConfigBuilder.setConnectionRequestTimeout(this.connectionRequestTimeout);
        }
        requestConfigBuilder.setStaleConnectionCheckEnabled(this.connectionStaleCheck);
        requestConfigBuilder.setRedirectsEnabled(this.httpFollowRedirects);
        if (this.connectionProxyHost != null) {
            HttpHost httpHost = new HttpHost(this.connectionProxyHost, this.connectionProxyPort);
            requestConfigBuilder.setProxy(httpHost);
            if (this.connectionProxyUsername != null && this.connectionProxyPassword != null) {
                BasicCredentialsProvider credProvider = new BasicCredentialsProvider();
                credProvider.setCredentials(new AuthScope(this.connectionProxyHost, this.connectionProxyPort), new UsernamePasswordCredentials(this.connectionProxyUsername, this.connectionProxyPassword));
                builder.setDefaultCredentialsProvider(credProvider);
            }
        }
        ConnectionConfig.Builder builder2 = ConnectionConfig.custom();
        builder2.setBufferSize(this.socketBufferSize);
        if (this.httpContentCharSet != null) {
            builder2.setCharset(CharsetUtils.get(this.httpContentCharSet));
        }
        builder.setDefaultRequestConfig(requestConfigBuilder.build());
        builder.setDefaultConnectionConfig(builder2.build());
        if (null != this.userAgent) {
            builder.setUserAgent(this.userAgent);
        }
    }

    protected org.apache.http.impl.client.HttpClientBuilder getApacheBuilder() {
        return this.apacheBuilder;
    }
}

