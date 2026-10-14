/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.InOutOperationContext
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.context.navigate.ChildContextLookup
 */
package org.opensaml.soap.client.http;

import com.google.common.base.Function;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import net.shibboleth.utilities.java.support.xml.XMLParserException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.AbstractExecutionAwareRequest;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.messaging.context.InOutOperationContext;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.security.SecurityException;
import org.opensaml.soap.client.SOAPClient;
import org.opensaml.soap.client.SOAPClientContext;
import org.opensaml.soap.client.SOAPClientException;
import org.opensaml.soap.client.SOAPFaultException;
import org.opensaml.soap.client.http.HttpSOAPRequestParameters;
import org.opensaml.soap.common.SOAPException;
import org.opensaml.soap.messaging.context.SOAP11Context;
import org.opensaml.soap.soap11.Envelope;
import org.opensaml.soap.soap11.Fault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

@ThreadSafe
public class HttpSOAPClient
extends AbstractInitializableComponent
implements SOAPClient {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(HttpSOAPClient.class);
    @NonnullAfterInit
    private HttpClient httpClient;
    @NonnullAfterInit
    private ParserPool parserPool;
    @Nonnull
    private Function<MessageContext, SOAPClientContext> soapClientContextLookupStrategy = new ChildContextLookup(SOAPClientContext.class, false);
    @Nonnull
    private Function<MessageContext, SOAP11Context> soap11ContextLookupStrategy = new ChildContextLookup(SOAP11Context.class, false);

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.httpClient == null) {
            throw new ComponentInitializationException("HttpClient cannot be null");
        }
        if (this.parserPool == null) {
            throw new ComponentInitializationException("ParserPool cannot be null");
        }
    }

    public void setHttpClient(@Nonnull HttpClient client) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.httpClient = Constraint.isNotNull(client, "HttpClient cannot be null");
    }

    public void setParserPool(@Nonnull ParserPool parser) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.parserPool = Constraint.isNotNull(parser, "ParserPool cannot be null");
    }

    @Nonnull
    public Function<MessageContext, SOAPClientContext> getSOAPClientContextLookupStrategy() {
        return this.soapClientContextLookupStrategy;
    }

    public void setSOAPClientContextLookupStrategy(@Nonnull Function<MessageContext, SOAPClientContext> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.soapClientContextLookupStrategy = Constraint.isNotNull(strategy, "SOAP client context lookup strategy cannot be null");
    }

    @Nonnull
    public Function<MessageContext, SOAP11Context> getSOAP11ContextLookupStrategy() {
        return this.soap11ContextLookupStrategy;
    }

    public void setSOAP11ContextLookupStrategy(@Nonnull Function<MessageContext, SOAP11Context> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.soap11ContextLookupStrategy = Constraint.isNotNull(strategy, "SOAP 1.1 context lookup strategy cannot be null");
    }

    @Override
    public void send(@Nonnull @NotEmpty String endpoint, @Nonnull InOutOperationContext context) throws SOAPException, SecurityException {
        block10: {
            Constraint.isNotNull(endpoint, "Endpoint cannot be null");
            Constraint.isNotNull(context, "Operation context cannot be null");
            SOAP11Context soapCtx = this.soap11ContextLookupStrategy.apply(context.getOutboundMessageContext());
            SOAPClientContext clientCtx = this.soapClientContextLookupStrategy.apply(context.getOutboundMessageContext());
            HttpSOAPRequestParameters soapRequestParams = null;
            if (soapCtx == null || soapCtx.getEnvelope() == null) {
                throw new SOAPClientException("Operation context did not contain an outbound SOAP Envelope");
            }
            if (clientCtx != null) {
                soapRequestParams = (HttpSOAPRequestParameters)clientCtx.getSOAPRequestParameters();
            }
            AbstractExecutionAwareRequest post = null;
            try {
                post = this.createPostMethod(endpoint, soapRequestParams, soapCtx.getEnvelope());
                HttpResponse result = this.httpClient.execute((HttpUriRequest)((Object)post));
                int code = result.getStatusLine().getStatusCode();
                this.log.debug("Received HTTP status code of {} when POSTing SOAP message to {}", (Object)code, (Object)endpoint);
                if (code == 200) {
                    this.processSuccessfulResponse(result, context);
                    break block10;
                }
                if (code == 500) {
                    this.processFaultResponse(result, context);
                    break block10;
                }
                throw new SOAPClientException("Received " + code + " HTTP response status code from HTTP request to " + endpoint);
            }
            catch (IOException e) {
                throw new SOAPClientException("Unable to send request to " + endpoint, e);
            }
            finally {
                if (post != null) {
                    post.reset();
                }
            }
        }
    }

    protected HttpPost createPostMethod(@Nonnull @NotEmpty String endpoint, @Nullable HttpSOAPRequestParameters requestParams, @Nonnull Envelope message) throws SOAPClientException {
        this.log.debug("POSTing SOAP message to {}", (Object)endpoint);
        HttpPost post = new HttpPost(endpoint);
        post.setEntity(this.createRequestEntity(message, Charset.forName("UTF-8")));
        if (requestParams != null && requestParams.getSOAPAction() != null) {
            post.setHeader("SOAPAction", requestParams.getSOAPAction());
        }
        return post;
    }

    protected HttpEntity createRequestEntity(@Nonnull Envelope message, @Nullable Charset charset) throws SOAPClientException {
        try {
            Marshaller marshaller = Constraint.isNotNull(XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(message), "SOAP Envelope marshaller not available");
            ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
            if (this.log.isDebugEnabled()) {
                this.log.debug("Outbound SOAP message is:\n" + SerializeSupport.prettyPrintXML(marshaller.marshall(message)));
            }
            SerializeSupport.writeNode(marshaller.marshall(message), arrayOut);
            return new ByteArrayEntity(arrayOut.toByteArray(), ContentType.create("text/xml", charset));
        }
        catch (MarshallingException e) {
            throw new SOAPClientException("Unable to marshall SOAP envelope", e);
        }
    }

    protected void processSuccessfulResponse(@Nonnull HttpResponse httpResponse, @Nonnull InOutOperationContext context) throws SOAPClientException {
        try {
            if (httpResponse.getEntity() == null) {
                throw new SOAPClientException("No response body from server");
            }
            Envelope response = this.unmarshallResponse(httpResponse.getEntity().getContent());
            context.setInboundMessageContext(new MessageContext());
            ((SOAP11Context)context.getInboundMessageContext().getSubcontext(SOAP11Context.class, true)).setEnvelope(response);
        }
        catch (IOException e) {
            throw new SOAPClientException("Unable to read response", e);
        }
    }

    protected void processFaultResponse(@Nonnull HttpResponse httpResponse, @Nonnull InOutOperationContext context) throws SOAPClientException, SOAPFaultException {
        try {
            if (httpResponse.getEntity() == null) {
                throw new SOAPClientException("No response body from server");
            }
            Envelope response = this.unmarshallResponse(httpResponse.getEntity().getContent());
            context.setInboundMessageContext(new MessageContext());
            ((SOAP11Context)context.getInboundMessageContext().getSubcontext(SOAP11Context.class, true)).setEnvelope(response);
            if (response.getBody() != null) {
                List<XMLObject> faults = response.getBody().getUnknownXMLObjects(Fault.DEFAULT_ELEMENT_NAME);
                if (faults.size() < 1) {
                    throw new SOAPClientException("HTTP status code was 500 but SOAP response did not contain a Fault");
                }
                String code = "(not set)";
                String msg = "(not set)";
                Fault fault = (Fault)faults.get(0);
                if (fault.getCode() != null) {
                    code = fault.getCode().getValue().toString();
                }
                if (fault.getMessage() != null) {
                    msg = fault.getMessage().getValue();
                }
                this.log.debug("SOAP fault code {} with message {}", (Object)code, (Object)msg);
                SOAPFaultException faultException = new SOAPFaultException("SOAP Fault: " + code + " Fault Message: " + msg);
                faultException.setFault(fault);
                throw faultException;
            }
            throw new SOAPClientException("HTTP status code was 500 but SOAP response did not contain a Body");
        }
        catch (IOException e) {
            throw new SOAPClientException("Unable to read response", e);
        }
    }

    protected Envelope unmarshallResponse(@Nonnull InputStream responseStream) throws SOAPClientException {
        try {
            Element responseElem = this.parserPool.parse(responseStream).getDocumentElement();
            if (this.log.isDebugEnabled()) {
                this.log.debug("Inbound SOAP message was:\n" + SerializeSupport.prettyPrintXML(responseElem));
            }
            Unmarshaller unmarshaller = Constraint.isNotNull(XMLObjectProviderRegistrySupport.getUnmarshallerFactory().getUnmarshaller(responseElem), "SOAP envelope unmarshaller not available");
            return (Envelope)unmarshaller.unmarshall(responseElem);
        }
        catch (XMLParserException e) {
            throw new SOAPClientException("Unable to parse the XML within the response", e);
        }
        catch (UnmarshallingException e) {
            throw new SOAPClientException("Unable to unmarshall the response DOM", e);
        }
    }

    protected void evaluateSecurityPolicy(SOAPClientContext messageContext) throws SOAPClientException {
    }
}

