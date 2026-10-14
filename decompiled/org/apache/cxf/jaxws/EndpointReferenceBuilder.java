/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.UnknownExtensibilityElement;
import javax.xml.namespace.QName;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.jaxws.binding.soap.SOAPBindingImpl;
import org.apache.cxf.jaxws.support.JaxWsEndpointImpl;
import org.w3c.dom.Element;

public class EndpointReferenceBuilder {
    private static final Logger LOG = LogUtils.getL7dLogger(EndpointReferenceBuilder.class);
    private final JaxWsEndpointImpl endpoint;

    public EndpointReferenceBuilder(JaxWsEndpointImpl e) {
        this.endpoint = e;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public EndpointReference getEndpointReference() {
        String bindingId;
        List<ExtensibilityElement> portExtensors = this.endpoint.getEndpointInfo().getExtensors(ExtensibilityElement.class);
        if (portExtensors != null) {
            Iterator<ExtensibilityElement> extensionElements = portExtensors.iterator();
            QName wsaEpr = new QName("http://www.w3.org/2005/08/addressing", "EndpointReference");
            while (extensionElements.hasNext()) {
                ExtensibilityElement ext = extensionElements.next();
                if (!(ext instanceof UnknownExtensibilityElement) || !wsaEpr.equals(ext.getElementType())) continue;
                Element eprEle = ((UnknownExtensibilityElement)ext).getElement();
                List<Element> addressElements = DOMUtils.getChildrenWithName(eprEle, "http://www.w3.org/2005/08/addressing", "Address");
                if (!addressElements.isEmpty()) {
                    addressElements.get(0).setTextContent(this.endpoint.getEndpointInfo().getAddress());
                }
                return EndpointReference.readFrom(new DOMSource(eprEle));
            }
        }
        if (!SOAPBindingImpl.isSoapBinding(bindingId = this.endpoint.getJaxwsBinding().getBindingID())) {
            throw new UnsupportedOperationException(new Message("GET_ENDPOINTREFERENCE_UNSUPPORTED_BINDING", LOG, bindingId).toString());
        }
        W3CEndpointReferenceBuilder builder = new W3CEndpointReferenceBuilder();
        builder.address(this.endpoint.getEndpointInfo().getAddress());
        builder.serviceName(this.endpoint.getService().getName());
        builder.endpointName(this.endpoint.getEndpointInfo().getName());
        if (this.endpoint.getEndpointInfo().getService().getDescription() != null) {
            builder.wsdlDocumentLocation(this.endpoint.getEndpointInfo().getService().getDescription().getBaseURI());
        }
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(EndpointReferenceBuilder.class.getClassLoader());
            W3CEndpointReference w3CEndpointReference = builder.build();
            return w3CEndpointReference;
        }
        finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
    }

    public <T extends EndpointReference> T getEndpointReference(Class<T> clazz) {
        if (clazz != W3CEndpointReference.class) {
            throw new WebServiceException("Unsupported EPR type: " + clazz);
        }
        return (T)((EndpointReference)clazz.cast(this.getEndpointReference()));
    }
}

