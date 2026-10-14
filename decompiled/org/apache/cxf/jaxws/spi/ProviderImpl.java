/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.spi;

import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.wsdl.Definition;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.ws.Endpoint;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.spi.Invoker;
import javax.xml.ws.spi.Provider;
import javax.xml.ws.spi.ServiceDelegate;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.jaxws.EndpointUtils;
import org.apache.cxf.jaxws.JAXWSMethodInvoker;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.jaxws.ServiceImpl;
import org.apache.cxf.jaxws.context.WebServiceContextImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamReader;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.ObjectFactory;
import org.apache.cxf.wsdl.WSDLConstants;
import org.apache.cxf.wsdl.WSDLManager;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;

public class ProviderImpl
extends Provider {
    public static final String JAXWS_PROVIDER = ProviderImpl.class.getName();
    protected static final Logger LOG = LogUtils.getL7dLogger(ProviderImpl.class);
    private static JAXBContext jaxbContext;

    public ServiceDelegate createServiceDelegate(URL url2, QName qname, Class cls) {
        return new ServiceImpl(null, url2, qname, cls, new WebServiceFeature[0]);
    }

    public ServiceDelegate createServiceDelegate(URL wsdlDocumentLocation, QName serviceName, Class serviceClass, WebServiceFeature ... features) {
        for (WebServiceFeature f : features) {
            if (f.getClass().getName().startsWith("javax.xml.ws") || f instanceof Feature) continue;
            throw new WebServiceException("Unknown feature error: " + f.getClass().getName());
        }
        return new ServiceImpl(null, wsdlDocumentLocation, serviceName, serviceClass, features);
    }

    protected EndpointImpl createEndpointImpl(Bus bus, String bindingId, Object implementor, WebServiceFeature ... features) {
        return new EndpointImpl(bus, implementor, bindingId, features);
    }

    @Override
    public Endpoint createEndpoint(String bindingId, Object implementor) {
        EndpointImpl ep = null;
        if (EndpointUtils.isValidImplementor(implementor)) {
            Bus bus = BusFactory.getThreadDefaultBus();
            ep = this.createEndpointImpl(bus, bindingId, implementor, new WebServiceFeature[0]);
            return ep;
        }
        throw new WebServiceException(new org.apache.cxf.common.i18n.Message("INVALID_IMPLEMENTOR_EXC", LOG, new Object[0]).toString());
    }

    @Override
    public Endpoint createEndpoint(String bindingId, Object implementor, WebServiceFeature ... features) {
        EndpointImpl ep = null;
        if (EndpointUtils.isValidImplementor(implementor)) {
            Bus bus = BusFactory.getThreadDefaultBus();
            ep = this.createEndpointImpl(bus, bindingId, implementor, features);
            return ep;
        }
        throw new WebServiceException(new org.apache.cxf.common.i18n.Message("INVALID_IMPLEMENTOR_EXC", LOG, new Object[0]).toString());
    }

    @Override
    public Endpoint createAndPublishEndpoint(String url2, Object implementor) {
        Endpoint ep = this.createEndpoint(null, implementor);
        ep.publish(url2);
        return ep;
    }

    @Override
    public Endpoint createAndPublishEndpoint(String address, Object implementor, WebServiceFeature ... features) {
        Endpoint ep = this.createEndpoint(null, implementor, features);
        ep.publish(address);
        return ep;
    }

    @Override
    public Endpoint createEndpoint(String bindingId, Class<?> implementorClass, Invoker invoker, WebServiceFeature ... features) {
        if (EndpointUtils.isValidImplementor(implementorClass)) {
            Bus bus = BusFactory.getThreadDefaultBus();
            JaxWsServerFactoryBean factory2 = new JaxWsServerFactoryBean();
            if (features != null) {
                factory2.getJaxWsServiceFactory().setWsFeatures(Arrays.asList(features));
            }
            if (invoker != null) {
                factory2.setInvoker(new JAXWSMethodInvoker(invoker));
                try {
                    invoker.inject(new WebServiceContextImpl());
                }
                catch (Exception e) {
                    throw new WebServiceException(new org.apache.cxf.common.i18n.Message("ENDPOINT_CREATION_FAILED_MSG", LOG, new Object[0]).toString(), e);
                }
            }
            EndpointImpl ep = new EndpointImpl(bus, null, factory2);
            ep.setImplementorClass(implementorClass);
            return ep;
        }
        throw new WebServiceException(new org.apache.cxf.common.i18n.Message("INVALID_IMPLEMENTOR_EXC", LOG, new Object[0]).toString());
    }

    @Override
    public W3CEndpointReference createW3CEndpointReference(String address, QName serviceName, QName portName, List<Element> metadata, String wsdlDocumentLocation, List<Element> referenceParameters) {
        return this.createW3CEndpointReference(address, null, serviceName, portName, metadata, wsdlDocumentLocation, referenceParameters, null, null);
    }

    public static EndpointReferenceType convertToInternal(EndpointReference external) {
        if (external instanceof W3CEndpointReference) {
            EndpointReferenceType endpointReferenceType;
            Unmarshaller um = null;
            try {
                DocumentFragment frag = DOMUtils.getEmptyDocument().createDocumentFragment();
                DOMResult result = new DOMResult(frag);
                external.writeTo(result);
                W3CDOMStreamReader reader = new W3CDOMStreamReader(frag);
                JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
                um = context.createUnmarshaller();
                endpointReferenceType = um.unmarshal(reader, EndpointReferenceType.class).getValue();
            }
            catch (JAXBException e) {
                try {
                    throw new IllegalArgumentException("Could not unmarshal EndpointReference", e);
                }
                catch (Throwable throwable) {
                    JAXBUtils.closeUnmarshaller(um);
                    throw throwable;
                }
            }
            JAXBUtils.closeUnmarshaller(um);
            return endpointReferenceType;
        }
        return null;
    }

    @Override
    public W3CEndpointReference createW3CEndpointReference(String address, QName interfaceName, QName serviceName, QName portName, List<Element> metadata, String wsdlDocumentLocation, List<Element> referenceParameters, List<Element> elements, Map<QName, String> attributes) {
        if (serviceName != null && portName != null && wsdlDocumentLocation != null && interfaceName == null) {
            Bus bus = BusFactory.getThreadDefaultBus();
            WSDLManager wsdlManager = bus.getExtension(WSDLManager.class);
            try {
                Definition definition = wsdlManager.getDefinition(wsdlDocumentLocation);
                interfaceName = definition.getService(serviceName).getPort(portName.getLocalPart()).getBinding().getPortType().getQName();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (serviceName == null && portName == null && address == null) {
            throw new IllegalStateException("Address in an EPR cannot be null,  when serviceName or portName is null");
        }
        try {
            final W3CDOMStreamWriter writer = new W3CDOMStreamWriter();
            writer.setPrefix("wsa", "http://www.w3.org/2005/08/addressing");
            writer.writeStartElement("wsa", "EndpointReference", "http://www.w3.org/2005/08/addressing");
            writer.writeNamespace("wsa", "http://www.w3.org/2005/08/addressing");
            writer.writeStartElement("wsa", "Address", "http://www.w3.org/2005/08/addressing");
            address = address == null ? "" : address;
            writer.writeCharacters(address);
            writer.writeEndElement();
            if (referenceParameters != null) {
                writer.writeStartElement("wsa", "ReferenceParameters", "http://www.w3.org/2005/08/addressing");
                for (Element element : referenceParameters) {
                    StaxUtils.writeElement(element, writer, true);
                }
                writer.writeEndElement();
            }
            if (wsdlDocumentLocation != null || interfaceName != null || serviceName != null || metadata != null && !metadata.isEmpty()) {
                writer.writeStartElement("wsa", "Metadata", "http://www.w3.org/2005/08/addressing");
                writer.writeNamespace("wsaw", "http://www.w3.org/2006/05/addressing/wsdl");
                writer.writeNamespace("wsam", "http://www.w3.org/2007/05/addressing/metadata");
                if (wsdlDocumentLocation != null) {
                    boolean includeLocationOnly = false;
                    Message message = PhaseInterceptorChain.getCurrentMessage();
                    if (message != null) {
                        includeLocationOnly = MessageUtils.getContextualBoolean(message, "org.apache.cxf.wsa.metadata.wsdlLocationOnly", false);
                    }
                    String attrubuteValue = serviceName != null && !includeLocationOnly ? serviceName.getNamespaceURI() + " " + wsdlDocumentLocation : wsdlDocumentLocation;
                    writer.writeNamespace("wsdli", "http://www.w3.org/ns/wsdl-instance");
                    writer.writeAttribute("wsdli", "http://www.w3.org/ns/wsdl-instance", "wsdlLocation", attrubuteValue);
                }
                if (interfaceName != null) {
                    writer.writeStartElement("wsam", "InterfaceName", "http://www.w3.org/2007/05/addressing/metadata");
                    String portTypePrefix = interfaceName.getPrefix();
                    if (portTypePrefix == null || portTypePrefix.isEmpty()) {
                        portTypePrefix = "ns1";
                    }
                    writer.writeNamespace(portTypePrefix, interfaceName.getNamespaceURI());
                    writer.writeCharacters(portTypePrefix + ":" + interfaceName.getLocalPart());
                    writer.writeEndElement();
                }
                Object serviceNamePrefix = null;
                if (serviceName != null) {
                    serviceNamePrefix = serviceName.getPrefix() == null || serviceName.getPrefix().length() == 0 ? "ns2" : serviceName.getPrefix();
                    writer.writeStartElement("wsam", "ServiceName", "http://www.w3.org/2007/05/addressing/metadata");
                    if (portName != null) {
                        writer.writeAttribute("EndpointName", portName.getLocalPart());
                    }
                    writer.writeNamespace((String)serviceNamePrefix, serviceName.getNamespaceURI());
                    writer.writeCharacters((String)serviceNamePrefix + ":" + serviceName.getLocalPart());
                    writer.writeEndElement();
                }
                if (wsdlDocumentLocation != null) {
                    writer.writeStartElement("wsdl", WSDLConstants.QNAME_DEFINITIONS.getLocalPart(), "http://schemas.xmlsoap.org/wsdl/");
                    writer.writeNamespace("wsdl", "http://schemas.xmlsoap.org/wsdl/");
                    writer.writeStartElement("wsdl", WSDLConstants.QNAME_IMPORT.getLocalPart(), WSDLConstants.QNAME_IMPORT.getNamespaceURI());
                    if (serviceName != null) {
                        writer.writeAttribute("namespace", serviceName.getNamespaceURI());
                    }
                    writer.writeAttribute("location", wsdlDocumentLocation);
                    writer.writeEndElement();
                    writer.writeEndElement();
                }
                if (metadata != null) {
                    for (Element e : metadata) {
                        StaxUtils.writeElement(e, writer, true);
                    }
                }
                writer.writeEndElement();
            }
            if (elements != null) {
                for (Element element : elements) {
                    StaxUtils.writeElement(element, writer, true);
                }
            }
            writer.writeEndElement();
            writer.flush();
            try {
                return AccessController.doPrivileged(new PrivilegedExceptionAction<W3CEndpointReference>(){

                    @Override
                    public W3CEndpointReference run() throws Exception {
                        Unmarshaller unmarshaller = ProviderImpl.getJAXBContext().createUnmarshaller();
                        try {
                            W3CEndpointReference w3CEndpointReference = (W3CEndpointReference)unmarshaller.unmarshal(writer.getDocument());
                            return w3CEndpointReference;
                        }
                        finally {
                            JAXBUtils.closeUnmarshaller(unmarshaller);
                        }
                    }
                });
            }
            catch (PrivilegedActionException pae) {
                Exception exception = pae.getException();
                if (exception instanceof JAXBException) {
                    throw (JAXBException)exception;
                }
                throw new SecurityException(exception);
            }
        }
        catch (Exception e) {
            throw new WebServiceException(new org.apache.cxf.common.i18n.Message("ERROR_UNMARSHAL_ENDPOINTREFERENCE", LOG, new Object[0]).toString(), e);
        }
    }

    @Override
    public <T> T getPort(EndpointReference endpointReference, Class<T> serviceEndpointInterface, WebServiceFeature ... features) {
        ServiceDelegate sd = this.createServiceDelegate((URL)null, (QName)null, serviceEndpointInterface);
        return sd.getPort(endpointReference, serviceEndpointInterface, features);
    }

    @Override
    public EndpointReference readEndpointReference(Source eprInfoset) {
        try {
            final XMLStreamReader reader = StaxUtils.createXMLStreamReader(eprInfoset);
            return AccessController.doPrivileged(new PrivilegedExceptionAction<EndpointReference>(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public EndpointReference run() throws Exception {
                    Unmarshaller unmarshaller = null;
                    try {
                        unmarshaller = ProviderImpl.getJAXBContext().createUnmarshaller();
                        EndpointReference endpointReference = (EndpointReference)unmarshaller.unmarshal(reader);
                        return endpointReference;
                    }
                    finally {
                        try {
                            StaxUtils.close(reader);
                        }
                        catch (XMLStreamException xMLStreamException) {}
                        JAXBUtils.closeUnmarshaller(unmarshaller);
                    }
                }
            });
        }
        catch (PrivilegedActionException pae) {
            Exception e = pae.getException();
            if (e instanceof JAXBException) {
                throw new WebServiceException(new org.apache.cxf.common.i18n.Message("ERROR_UNMARSHAL_ENDPOINTREFERENCE", LOG, new Object[0]).toString(), e);
            }
            throw new SecurityException(e);
        }
    }

    private static JAXBContext getJAXBContext() {
        if (jaxbContext == null) {
            try {
                jaxbContext = JAXBContext.newInstance(W3CEndpointReference.class);
            }
            catch (JAXBException e) {
                throw new WebServiceException(new org.apache.cxf.common.i18n.Message("JAXBCONTEXT_CREATION_FAILED", LOG, new Object[0]).toString(), e);
            }
        }
        return jaxbContext;
    }
}

