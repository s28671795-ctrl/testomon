/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.io.ByteStreams;
import com.google.common.net.MediaType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.net.MediaTypeSupport;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.protocol.HttpContext;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSource;
import org.opensaml.saml.metadata.resolver.impl.AbstractDynamicMetadataResolver;
import org.opensaml.security.httpclient.HttpClientSecurityParameters;
import org.opensaml.security.httpclient.HttpClientSecuritySupport;
import org.opensaml.security.trust.TrustEngine;
import org.opensaml.security.x509.X509Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public abstract class AbstractDynamicHTTPMetadataResolver
extends AbstractDynamicMetadataResolver {
    public static final String[] DEFAULT_CONTENT_TYPES = new String[]{"application/samlmetadata+xml", "application/xml", "text/xml"};
    public static final String MDC_ATTRIB_CURRENT_REQUEST_URI = AbstractDynamicHTTPMetadataResolver.class.getName() + ".currentRequestURI";
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AbstractDynamicHTTPMetadataResolver.class);
    @Nonnull
    private HttpClient httpClient;
    @NonnullAfterInit
    private List<String> supportedContentTypes;
    @NonnullAfterInit
    private String supportedContentTypesValue;
    @NonnullAfterInit
    private Set<MediaType> supportedMediaTypes;
    @Nonnull
    private ResponseHandler<XMLObject> responseHandler;
    @Nullable
    private CredentialsProvider credentialsProvider;
    @Nullable
    private TrustEngine<? super X509Credential> tlsTrustEngine;
    @Nullable
    private HttpClientSecurityParameters httpClientSecurityParameters;

    public AbstractDynamicHTTPMetadataResolver(@Nonnull HttpClient client) {
        this(null, client);
    }

    public AbstractDynamicHTTPMetadataResolver(@Nullable Timer backgroundTaskTimer, @Nonnull HttpClient client) {
        super(backgroundTaskTimer);
        this.httpClient = Constraint.isNotNull(client, "HttpClient may not be null");
        this.responseHandler = new BasicMetadataResponseHandler();
    }

    public void setTLSTrustEngine(@Nullable TrustEngine<? super X509Credential> engine) {
        this.tlsTrustEngine = engine;
    }

    public void setCredentialsProvider(@Nullable CredentialsProvider provider) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.credentialsProvider = provider;
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
                authScope = new AuthScope(AuthScope.ANY_HOST, -1);
            }
            BasicCredentialsProvider provider = new BasicCredentialsProvider();
            provider.setCredentials(authScope, credentials);
            this.credentialsProvider = provider;
        } else {
            this.log.debug("{} Either username or password were null, disabling basic auth", (Object)this.getLogPrefix());
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

    @NonnullAfterInit
    @NotLive
    @Unmodifiable
    protected Set<MediaType> getSupportedMediaTypes() {
        return this.supportedMediaTypes;
    }

    @NonnullAfterInit
    @NotLive
    @Unmodifiable
    public List<String> getSupportedContentTypes() {
        return this.supportedContentTypes;
    }

    public void setSupportedContentTypes(@Nullable List<String> types) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.supportedContentTypes = types == null ? Collections.emptyList() : new ArrayList<String>(Collections2.transform(StringSupport.normalizeStringCollection(types), new Function<String, String>(){

            @Override
            @Nullable
            public String apply(@Nullable String input) {
                return input == null ? null : input.toLowerCase();
            }
        }));
    }

    @Override
    protected void initMetadataResolver() throws ComponentInitializationException {
        super.initMetadataResolver();
        if (this.getSupportedContentTypes() == null) {
            this.setSupportedContentTypes(Arrays.asList(DEFAULT_CONTENT_TYPES));
        }
        if (!this.getSupportedContentTypes().isEmpty()) {
            this.supportedContentTypesValue = StringSupport.listToStringValue(this.getSupportedContentTypes(), ", ");
            this.supportedMediaTypes = new LazySet<MediaType>();
            for (String contentType : this.getSupportedContentTypes()) {
                this.supportedMediaTypes.add(MediaType.parse(contentType));
            }
        } else {
            this.supportedMediaTypes = Collections.emptySet();
        }
        this.log.debug("{} Supported content types are: {}", (Object)this.getLogPrefix(), (Object)this.getSupportedContentTypes());
    }

    @Override
    protected void doDestroy() {
        this.httpClient = null;
        this.credentialsProvider = null;
        this.tlsTrustEngine = null;
        this.httpClientSecurityParameters = null;
        this.supportedContentTypes = null;
        this.supportedContentTypesValue = null;
        this.supportedMediaTypes = null;
        super.doDestroy();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Nullable
    protected XMLObject fetchFromOriginSource(@Nonnull CriteriaSet criteria) throws IOException {
        HttpUriRequest request = this.buildHttpRequest(criteria);
        if (request == null) {
            this.log.debug("{} Could not build request based on input criteria, unable to query", (Object)this.getLogPrefix());
            return null;
        }
        HttpClientContext context = this.buildHttpClientContext(request);
        try {
            MDC.put(MDC_ATTRIB_CURRENT_REQUEST_URI, request.getURI().toString());
            XMLObject result = this.httpClient.execute(request, this.responseHandler, (HttpContext)context);
            HttpClientSecuritySupport.checkTLSCredentialEvaluated(context, request.getURI().getScheme());
            XMLObject xMLObject = result;
            return xMLObject;
        }
        finally {
            MDC.remove(MDC_ATTRIB_CURRENT_REQUEST_URI);
        }
    }

    @Deprecated
    protected void checkTLSCredentialTrusted(HttpClientContext context, HttpUriRequest request) throws SSLPeerUnverifiedException {
        HttpClientSecuritySupport.checkTLSCredentialEvaluated(context, request.getURI().getScheme());
    }

    @Nullable
    protected HttpUriRequest buildHttpRequest(@Nonnull CriteriaSet criteria) {
        String url2 = this.buildRequestURL(criteria);
        this.log.debug("{} Built request URL of: {}", (Object)this.getLogPrefix(), (Object)url2);
        if (url2 == null) {
            this.log.debug("{} Could not construct request URL from input criteria, unable to query", (Object)this.getLogPrefix());
            return null;
        }
        HttpGet getMethod = new HttpGet(url2);
        if (!Strings.isNullOrEmpty(this.supportedContentTypesValue)) {
            getMethod.addHeader("Accept", this.supportedContentTypesValue);
        }
        return getMethod;
    }

    @Nullable
    protected abstract String buildRequestURL(@Nonnull CriteriaSet var1);

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

    public class BasicMetadataResponseHandler
    implements ResponseHandler<XMLObject> {
        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public XMLObject handleResponse(@Nonnull HttpResponse response) throws IOException {
            int httpStatusCode = response.getStatusLine().getStatusCode();
            String currentRequestURI = MDC.get(MDC_ATTRIB_CURRENT_REQUEST_URI);
            if (httpStatusCode == 304) {
                AbstractDynamicHTTPMetadataResolver.this.log.debug("{} Metadata document from '{}' has not changed since last retrieval", (Object)AbstractDynamicHTTPMetadataResolver.this.getLogPrefix(), (Object)currentRequestURI);
                return null;
            }
            if (httpStatusCode != 200) {
                AbstractDynamicHTTPMetadataResolver.this.log.warn("{} Non-ok status code '{}' returned from remote metadata source: {}", AbstractDynamicHTTPMetadataResolver.this.getLogPrefix(), httpStatusCode, currentRequestURI);
                return null;
            }
            try {
                this.validateHttpResponse(response);
            }
            catch (ResolverException e) {
                AbstractDynamicHTTPMetadataResolver.this.log.error("{} Problem validating dynamic metadata HTTP response", (Object)AbstractDynamicHTTPMetadataResolver.this.getLogPrefix(), (Object)e);
                return null;
            }
            try {
                InputStream ins = response.getEntity().getContent();
                byte[] source2 = ByteStreams.toByteArray(ins);
                try (ByteArrayInputStream bais = new ByteArrayInputStream(source2);){
                    XMLObject xmlObject = AbstractDynamicHTTPMetadataResolver.this.unmarshallMetadata(bais);
                    xmlObject.getObjectMetadata().put(new XMLObjectSource(source2));
                    XMLObject xMLObject = xmlObject;
                    return xMLObject;
                }
            }
            catch (IOException | UnmarshallingException e) {
                AbstractDynamicHTTPMetadataResolver.this.log.error("{} Error unmarshalling HTTP response stream", (Object)AbstractDynamicHTTPMetadataResolver.this.getLogPrefix(), (Object)e);
                return null;
            }
        }

        protected void validateHttpResponse(@Nonnull HttpResponse response) throws ResolverException {
            if (!AbstractDynamicHTTPMetadataResolver.this.getSupportedMediaTypes().isEmpty()) {
                String contentTypeValue = null;
                Header contentType = response.getEntity().getContentType();
                if (contentType != null && contentType.getValue() != null) {
                    contentTypeValue = StringSupport.trimOrNull(contentType.getValue());
                }
                AbstractDynamicHTTPMetadataResolver.this.log.debug("{} Saw raw Content-Type from response header '{}'", (Object)AbstractDynamicHTTPMetadataResolver.this.getLogPrefix(), (Object)contentTypeValue);
                if (!MediaTypeSupport.validateContentType(contentTypeValue, AbstractDynamicHTTPMetadataResolver.this.getSupportedMediaTypes(), true, false)) {
                    throw new ResolverException("HTTP response specified an unsupported Content-Type MIME type: " + contentTypeValue);
                }
            }
        }
    }
}

