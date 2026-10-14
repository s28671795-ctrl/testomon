/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.InOutOperationContext
 *  org.opensaml.messaging.context.httpclient.HttpClientRequestContext
 *  org.opensaml.messaging.decoder.MessageDecodingException
 *  org.opensaml.messaging.decoder.httpclient.HttpClientResponseMessageDecoder
 *  org.opensaml.messaging.encoder.MessageEncodingException
 *  org.opensaml.messaging.encoder.httpclient.HttpClientRequestMessageEncoder
 *  org.opensaml.messaging.handler.MessageHandlerException
 *  org.opensaml.messaging.pipeline.httpclient.HttpClientMessagePipeline
 */
package org.opensaml.soap.client.http;

import com.google.common.base.Function;
import java.io.IOException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.opensaml.messaging.context.InOutOperationContext;
import org.opensaml.messaging.context.httpclient.HttpClientRequestContext;
import org.opensaml.messaging.decoder.MessageDecodingException;
import org.opensaml.messaging.decoder.httpclient.HttpClientResponseMessageDecoder;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.messaging.encoder.httpclient.HttpClientRequestMessageEncoder;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.messaging.pipeline.httpclient.HttpClientMessagePipeline;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.httpclient.HttpClientSecurityParameters;
import org.opensaml.security.httpclient.HttpClientSecuritySupport;
import org.opensaml.security.messaging.HttpClientSecurityContext;
import org.opensaml.soap.client.SOAPClient;
import org.opensaml.soap.client.SOAPFaultException;
import org.opensaml.soap.common.SOAP11FaultDecodingException;
import org.opensaml.soap.common.SOAPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
public abstract class AbstractPipelineHttpSOAPClient<OutboundMessageType, InboundMessageType>
extends AbstractInitializableComponent
implements SOAPClient {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AbstractPipelineHttpSOAPClient.class);
    @NonnullAfterInit
    private HttpClient httpClient;
    @Nullable
    private HttpClientSecurityParameters httpClientSecurityParameters;
    @Nullable
    private Function<InOutOperationContext<?, ?>, CriteriaSet> tlsCriteriaSetStrategy;

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.httpClient == null) {
            throw new ComponentInitializationException("HttpClient cannot be null");
        }
    }

    @Override
    protected void doDestroy() {
        this.httpClient = null;
        this.httpClientSecurityParameters = null;
        this.tlsCriteriaSetStrategy = null;
        super.doDestroy();
    }

    @Nonnull
    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    public void setHttpClient(@Nonnull HttpClient client) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.httpClient = Constraint.isNotNull(client, "HttpClient cannot be null");
    }

    @Nullable
    public HttpClientSecurityParameters getHttpClientSecurityParameters() {
        return this.httpClientSecurityParameters;
    }

    public void setHttpClientSecurityParameters(@Nullable HttpClientSecurityParameters params) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.httpClientSecurityParameters = params;
    }

    @Nullable
    public Function<InOutOperationContext<?, ?>, CriteriaSet> getTLSCriteriaSetStrategy() {
        return this.tlsCriteriaSetStrategy;
    }

    public void setTLSCriteriaSetStrategy(@Nullable Function<InOutOperationContext<?, ?>, CriteriaSet> function) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        this.tlsCriteriaSetStrategy = function;
    }

    @Override
    public void send(@Nonnull @NotEmpty String endpoint, @Nonnull InOutOperationContext operationContext) throws SOAPException, SecurityException {
        Constraint.isNotNull(endpoint, "Endpoint cannot be null");
        Constraint.isNotNull(operationContext, "Operation context cannot be null");
        HttpClientMessagePipeline<InboundMessageType, OutboundMessageType> pipeline = null;
        try {
            pipeline = this.resolvePipeline(operationContext);
            if (pipeline.getOutboundPayloadMessageHandler() != null) {
                pipeline.getOutboundPayloadMessageHandler().invoke(operationContext.getOutboundMessageContext());
            }
            HttpUriRequest httpRequest = this.buildHttpRequest(endpoint, operationContext);
            HttpClientContext httpContext = this.buildHttpContext(httpRequest, operationContext);
            HttpClientRequestMessageEncoder encoder = pipeline.getEncoder();
            encoder.setHttpRequest((HttpRequest)httpRequest);
            encoder.setMessageContext(operationContext.getOutboundMessageContext());
            encoder.initialize();
            encoder.prepareContext();
            if (pipeline.getOutboundTransportMessageHandler() != null) {
                pipeline.getOutboundTransportMessageHandler().invoke(operationContext.getOutboundMessageContext());
            }
            encoder.encode();
            HttpResponse httpResponse = this.getHttpClient().execute(httpRequest, httpContext);
            HttpClientSecuritySupport.checkTLSCredentialEvaluated(httpContext, httpRequest.getURI().getScheme());
            HttpClientResponseMessageDecoder decoder = pipeline.getDecoder();
            decoder.setHttpResponse(httpResponse);
            decoder.initialize();
            decoder.decode();
            operationContext.setInboundMessageContext(decoder.getMessageContext());
            if (pipeline.getInboundMessageHandler() != null) {
                pipeline.getInboundMessageHandler().invoke(operationContext.getInboundMessageContext());
            }
        }
        catch (SOAP11FaultDecodingException e) {
            SOAPFaultException faultException = new SOAPFaultException(e.getMessage(), (Exception)((Object)e));
            faultException.setFault(e.getFault());
            throw faultException;
        }
        catch (SSLException e) {
            throw new SecurityException("Problem establising TLS connection to: " + endpoint, e);
        }
        catch (ComponentInitializationException e) {
            throw new SOAPException("Problem initializing a SOAP client component", e);
        }
        catch (MessageEncodingException e) {
            throw new SOAPException("Problem encoding SOAP request message to: " + endpoint, (Exception)((Object)e));
        }
        catch (MessageDecodingException e) {
            throw new SOAPException("Problem decoding SOAP response message from: " + endpoint, (Exception)((Object)e));
        }
        catch (MessageHandlerException e) {
            throw new SOAPException("Problem handling SOAP message exchange with: " + endpoint, (Exception)((Object)e));
        }
        catch (ClientProtocolException e) {
            throw new SOAPException("Client protocol problem sending SOAP request message to: " + endpoint, e);
        }
        catch (IOException e) {
            throw new SOAPException("I/O problem with SOAP message exchange with: " + endpoint, e);
        }
        finally {
            if (pipeline != null) {
                pipeline.getEncoder().destroy();
                pipeline.getDecoder().destroy();
            }
        }
    }

    @Nonnull
    protected HttpClientMessagePipeline<InboundMessageType, OutboundMessageType> resolvePipeline(@Nonnull InOutOperationContext operationContext) throws SOAPException {
        try {
            return this.newPipeline();
        }
        catch (SOAPException e) {
            this.log.warn("Problem resolving pipeline instance", e);
            throw e;
        }
        catch (Exception e) {
            this.log.warn("Problem resolving pipeline instance", e);
            throw new SOAPException("Could not resolve pipeline", e);
        }
    }

    @Nonnull
    protected abstract HttpClientMessagePipeline<InboundMessageType, OutboundMessageType> newPipeline() throws SOAPException;

    @Deprecated
    protected void checkTLSCredentialTrusted(@Nonnull HttpClientContext context, @Nonnull HttpUriRequest request) throws SSLPeerUnverifiedException {
        HttpClientSecuritySupport.checkTLSCredentialEvaluated(context, request.getURI().getScheme());
    }

    @Nonnull
    protected HttpUriRequest buildHttpRequest(@Nonnull @NotEmpty String endpoint, @Nonnull InOutOperationContext operationContext) {
        return new HttpPost(endpoint);
    }

    @Nonnull
    protected HttpClientContext buildHttpContext(@Nonnull HttpUriRequest request, @Nonnull InOutOperationContext operationContext) {
        HttpClientContext clientContext = this.resolveClientContext(operationContext);
        HttpClientSecurityParameters contextSecurityParameters = this.resolveContextSecurityParameters(operationContext);
        HttpClientSecuritySupport.marshalSecurityParameters(clientContext, contextSecurityParameters, false);
        HttpClientSecuritySupport.marshalSecurityParameters(clientContext, this.getHttpClientSecurityParameters(), false);
        if ("https".equalsIgnoreCase(request.getURI().getScheme()) && clientContext.getAttribute("opensaml.TrustEngine") != null && clientContext.getAttribute("opensaml.CriteriaSet") == null) {
            clientContext.setAttribute("opensaml.CriteriaSet", this.buildTLSCriteriaSet(request, operationContext));
        }
        HttpClientSecuritySupport.addDefaultTLSTrustEngineCriteria(clientContext, request);
        return clientContext;
    }

    protected HttpClientSecurityParameters resolveContextSecurityParameters(@Nonnull InOutOperationContext operationContext) {
        HttpClientSecurityContext securityContext = (HttpClientSecurityContext)operationContext.getOutboundMessageContext().getSubcontext(HttpClientSecurityContext.class);
        if (securityContext != null) {
            return securityContext.getSecurityParameters();
        }
        return null;
    }

    @Nonnull
    protected HttpClientContext resolveClientContext(@Nonnull InOutOperationContext operationContext) {
        HttpClientRequestContext requestContext = (HttpClientRequestContext)operationContext.getOutboundMessageContext().getSubcontext(HttpClientRequestContext.class);
        if (requestContext != null && requestContext.getHttpClientContext() != null) {
            return requestContext.getHttpClientContext();
        }
        return HttpClientContext.create();
    }

    @Nonnull
    protected CriteriaSet buildTLSCriteriaSet(@Nonnull HttpUriRequest request, @Nonnull InOutOperationContext operationContext) {
        CriteriaSet resolved;
        CriteriaSet criteriaSet = new CriteriaSet();
        if (this.getTLSCriteriaSetStrategy() != null && (resolved = this.getTLSCriteriaSetStrategy().apply(operationContext)) != null) {
            criteriaSet.addAll(resolved);
        }
        if (!criteriaSet.contains(UsageType.class)) {
            criteriaSet.add(new UsageCriterion(UsageType.SIGNING));
        }
        return criteriaSet;
    }
}

