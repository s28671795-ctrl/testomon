/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.context;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.logging.Logger;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.EndpointReferenceBuilder;
import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.apache.cxf.message.Message;
import org.apache.cxf.security.SecurityContext;
import org.w3c.dom.Element;

public class WebServiceContextImpl
implements WebServiceContext {
    private static final Logger LOG = LogUtils.getL7dLogger(WebServiceContextImpl.class);
    private static ThreadLocal<MessageContext> context = new ThreadLocal();
    private final MessageContext localCtx;

    public WebServiceContextImpl() {
        this.localCtx = null;
    }

    public WebServiceContextImpl(MessageContext c) {
        this.localCtx = c;
    }

    @Override
    public final MessageContext getMessageContext() {
        return this.localCtx == null ? context.get() : this.localCtx;
    }

    @Override
    public final Principal getUserPrincipal() {
        SecurityContext ctx = (SecurityContext)this.getMessageContext().get(SecurityContext.class.getName());
        if (ctx == null) {
            return null;
        }
        return ctx.getUserPrincipal();
    }

    @Override
    public final boolean isUserInRole(String role) {
        SecurityContext ctx = (SecurityContext)this.getMessageContext().get(SecurityContext.class.getName());
        if (ctx == null) {
            return false;
        }
        return ctx.isUserInRole(role);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public EndpointReference getEndpointReference(Element ... referenceParameters) {
        WrappedMessageContext ctx = (WrappedMessageContext)this.getMessageContext();
        Message msg = ctx.getWrappedMessage();
        Endpoint ep = msg.getExchange().getEndpoint();
        W3CEndpointReferenceBuilder builder = new W3CEndpointReferenceBuilder();
        builder.address(ep.getEndpointInfo().getAddress());
        builder.serviceName(ep.getService().getName());
        builder.endpointName(ep.getEndpointInfo().getName());
        URI wsdlDescription = ep.getEndpointInfo().getProperty("URI", URI.class);
        if (wsdlDescription == null) {
            Element[] address = ep.getEndpointInfo().getAddress();
            try {
                wsdlDescription = new URI((String)address + "?wsdl");
            }
            catch (URISyntaxException uRISyntaxException) {
                // empty catch block
            }
            ep.getEndpointInfo().setProperty("URI", wsdlDescription);
        }
        if (wsdlDescription != null) {
            builder.wsdlDocumentLocation(wsdlDescription.toString());
        }
        if (referenceParameters != null) {
            for (Element referenceParameter : referenceParameters) {
                builder.referenceParameter(referenceParameter);
            }
        }
        ClassLoaderUtils.ClassLoaderHolder orig = null;
        try {
            orig = ClassLoaderUtils.setThreadContextClassloader(EndpointReferenceBuilder.class.getClassLoader());
            W3CEndpointReference w3CEndpointReference = builder.build();
            return w3CEndpointReference;
        }
        finally {
            if (orig != null) {
                orig.reset();
            }
        }
    }

    @Override
    public <T extends EndpointReference> T getEndpointReference(Class<T> clazz, Element ... referenceParameters) {
        if (W3CEndpointReference.class.isAssignableFrom(clazz)) {
            return (T)((EndpointReference)clazz.cast(this.getEndpointReference(referenceParameters)));
        }
        throw new WebServiceException(new org.apache.cxf.common.i18n.Message("ENDPOINTREFERENCE_TYPE_NOT_SUPPORTED", LOG, clazz.getName()).toString());
    }

    public static MessageContext setMessageContext(MessageContext ctx) {
        MessageContext oldCtx = context.get();
        context.set(ctx);
        return oldCtx;
    }

    public static void clear() {
        context.remove();
    }
}

