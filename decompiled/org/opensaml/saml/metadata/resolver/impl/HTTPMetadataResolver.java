/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.util.EntityUtils;
import org.opensaml.saml.metadata.resolver.impl.AbstractReloadingMetadataResolver;
import org.opensaml.security.httpclient.HttpClientSecurityParameters;
import org.opensaml.security.httpclient.HttpClientSecuritySupport;
import org.opensaml.security.trust.TrustEngine;
import org.opensaml.security.x509.X509Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPMetadataResolver
extends AbstractReloadingMetadataResolver {
    private final Logger log = LoggerFactory.getLogger(HTTPMetadataResolver.class);
    private HttpClient httpClient;
    private URI metadataURI;
    private String cachedMetadataETag;
    private String cachedMetadataLastModified;
    @Nullable
    private BasicCredentialsProvider credentialsProvider;
    @Nullable
    private TrustEngine<? super X509Credential> tlsTrustEngine;
    @Nullable
    private HttpClientSecurityParameters httpClientSecurityParameters;

    public HTTPMetadataResolver(HttpClient client, String metadataURL) throws ResolverException {
        this(null, client, metadataURL);
    }

    public HTTPMetadataResolver(Timer backgroundTaskTimer, HttpClient client, String metadataURL) throws ResolverException {
        super(backgroundTaskTimer);
        if (client == null) {
            throw new ResolverException("HTTP client may not be null");
        }
        this.httpClient = client;
        try {
            this.metadataURI = new URI(metadataURL);
        }
        catch (URISyntaxException e) {
            throw new ResolverException("Illegal URL syntax", e);
        }
    }

    public String getMetadataURI() {
        return this.metadataURI.toASCIIString();
    }

    public void setTLSTrustEngine(@Nullable TrustEngine<? super X509Credential> engine) {
        this.tlsTrustEngine = engine;
    }

    public void setBasicCredentials(@Nullable UsernamePasswordCredentials credentials) {
        this.setBasicCredentialsWithScope(credentials, null);
    }

    public void setBasicCredentialsWithScope(@Nullable UsernamePasswordCredentials credentials, @Nullable AuthScope scope) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        if (credentials != null) {
            AuthScope authScope = scope;
            if (authScope == null) {
                authScope = new AuthScope(this.metadataURI.getHost(), this.metadataURI.getPort());
            }
            BasicCredentialsProvider provider = new BasicCredentialsProvider();
            provider.setCredentials(authScope, credentials);
            this.credentialsProvider = provider;
        } else {
            this.log.debug("Either username or password were null, disabling basic auth");
            this.credentialsProvider = null;
        }
    }

    @Nullable
    protected HttpClientSecurityParameters getHttpClientSecurityParameters() {
        return this.httpClientSecurityParameters;
    }

    public void setHttpClientSecurityParameters(@Nullable HttpClientSecurityParameters params) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.httpClientSecurityParameters = params;
    }

    @Override
    protected void doDestroy() {
        this.httpClient = null;
        this.tlsTrustEngine = null;
        this.credentialsProvider = null;
        this.httpClientSecurityParameters = null;
        this.metadataURI = null;
        this.cachedMetadataETag = null;
        this.cachedMetadataLastModified = null;
        super.doDestroy();
    }

    @Override
    protected String getMetadataIdentifier() {
        return this.metadataURI.toString();
    }

    /*
     * Loose catch block
     */
    @Override
    protected byte[] fetchMetadata() throws ResolverException {
        int httpStatusCode;
        HttpResponse response;
        block15: {
            HttpGet httpGet = this.buildHttpGet();
            HttpClientContext context = this.buildHttpClientContext(httpGet);
            response = null;
            this.log.debug("{} Attempting to fetch metadata document from '{}'", (Object)this.getLogPrefix(), (Object)this.metadataURI);
            response = this.httpClient.execute((HttpUriRequest)httpGet, context);
            HttpClientSecuritySupport.checkTLSCredentialEvaluated(context, this.metadataURI.getScheme());
            httpStatusCode = response.getStatusLine().getStatusCode();
            if (httpStatusCode != 304) break block15;
            this.log.debug("{} Metadata document from '{}' has not changed since last retrieval", (Object)this.getLogPrefix(), (Object)this.getMetadataURI());
            byte[] byArray = null;
            try {
                if (response != null && response instanceof CloseableHttpResponse) {
                    ((CloseableHttpResponse)response).close();
                }
            }
            catch (IOException e) {
                this.log.error("{} Error closing HTTP response from {}", this.metadataURI, this.getLogPrefix(), e);
            }
            return byArray;
        }
        if (httpStatusCode != 200) {
            String errMsg = "Non-ok status code " + httpStatusCode + " returned from remote metadata source " + this.metadataURI;
            this.log.error("{} " + errMsg, (Object)this.getLogPrefix());
            throw new ResolverException(errMsg);
        }
        this.processConditionalRetrievalHeaders(response);
        byte[] rawMetadata = this.getMetadataBytesFromResponse(response);
        this.log.debug("{} Successfully fetched {} bytes of metadata from {}", this.getLogPrefix(), rawMetadata.length, this.getMetadataURI());
        byte[] byArray = rawMetadata;
        try {
            if (response != null && response instanceof CloseableHttpResponse) {
                ((CloseableHttpResponse)response).close();
            }
        }
        catch (IOException e) {
            this.log.error("{} Error closing HTTP response from {}", this.metadataURI, this.getLogPrefix(), e);
        }
        return byArray;
        catch (IOException e) {
            try {
                String errMsg = "Error retrieving metadata from " + this.metadataURI;
                this.log.error("{} " + errMsg, (Object)this.getLogPrefix(), (Object)e);
                throw new ResolverException(errMsg, e);
            }
            catch (Throwable throwable) {
                try {
                    if (response != null && response instanceof CloseableHttpResponse) {
                        ((CloseableHttpResponse)response).close();
                    }
                }
                catch (IOException e2) {
                    this.log.error("{} Error closing HTTP response from {}", this.metadataURI, this.getLogPrefix(), e2);
                }
                throw throwable;
            }
        }
    }

    @Deprecated
    protected void checkTLSCredentialTrusted(HttpClientContext context) throws SSLPeerUnverifiedException {
        HttpClientSecuritySupport.checkTLSCredentialEvaluated(context, this.metadataURI.getScheme());
    }

    protected HttpGet buildHttpGet() {
        HttpGet getMethod = new HttpGet(this.getMetadataURI());
        if (this.cachedMetadataETag != null) {
            getMethod.setHeader("If-None-Match", this.cachedMetadataETag);
        }
        if (this.cachedMetadataLastModified != null) {
            getMethod.setHeader("If-Modified-Since", this.cachedMetadataLastModified);
        }
        return getMethod;
    }

    protected HttpClientContext buildHttpClientContext() {
        return this.buildHttpClientContext(null);
    }

    protected HttpClientContext buildHttpClientContext(@Nullable HttpUriRequest request) {
        HttpClientContext context = HttpClientContext.create();
        HttpClientSecuritySupport.marshalSecurityParameters(context, this.httpClientSecurityParameters, true);
        if (this.credentialsProvider != null) {
            context.setCredentialsProvider(this.credentialsProvider);
        }
        if (this.tlsTrustEngine != null) {
            context.setAttribute("opensaml.TrustEngine", this.tlsTrustEngine);
        }
        if (request != null) {
            HttpClientSecuritySupport.addDefaultTLSTrustEngineCriteria(context, request);
        }
        return context;
    }

    protected void processConditionalRetrievalHeaders(HttpResponse response) {
        Header httpHeader = response.getFirstHeader("ETag");
        if (httpHeader != null) {
            this.cachedMetadataETag = httpHeader.getValue();
        }
        if ((httpHeader = response.getFirstHeader("Last-Modified")) != null) {
            this.cachedMetadataLastModified = httpHeader.getValue();
        }
    }

    protected byte[] getMetadataBytesFromResponse(HttpResponse response) throws ResolverException {
        this.log.debug("{} Attempting to extract metadata from response to request for metadata from '{}'", (Object)this.getLogPrefix(), (Object)this.getMetadataURI());
        try {
            InputStream ins = response.getEntity().getContent();
            byte[] byArray = this.inputstreamToByteArray(ins);
            return byArray;
        }
        catch (IOException e) {
            this.log.error("{} Unable to read response", (Object)this.getLogPrefix(), (Object)e);
            throw new ResolverException("Unable to read response", e);
        }
        finally {
            EntityUtils.consumeQuietly(response.getEntity());
        }
    }
}

