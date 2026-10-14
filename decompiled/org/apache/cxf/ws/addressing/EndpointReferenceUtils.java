/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.jaxb.JAXBContextCache;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.xmlschema.LSInputImpl;
import org.apache.cxf.endpoint.EndpointResolverRegistry;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.endpoint.ServerRegistry;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.helpers.LoadingByteArrayOutputStream;
import org.apache.cxf.resource.ExtendedURIResolver;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.service.model.SchemaInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.MultiplexDestination;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.EndpointUtilsException;
import org.apache.cxf.ws.addressing.MetadataType;
import org.apache.cxf.ws.addressing.ObjectFactory;
import org.apache.cxf.ws.addressing.wsdl.AttributedQNameType;
import org.apache.cxf.ws.addressing.wsdl.ServiceNameType;
import org.apache.ws.commons.schema.XmlSchema;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.InputSource;

public final class EndpointReferenceUtils {
    public static final String ANONYMOUS_ADDRESS = "http://www.w3.org/2005/08/addressing/anonymous";
    private static final Logger LOG = LogUtils.getL7dLogger(EndpointReferenceUtils.class);
    private static final String NS_WSAW_2005 = "http://www.w3.org/2005/02/addressing/wsdl";
    private static final String WSDL_INSTANCE_NAMESPACE2 = "http://www.w3.org/2006/01/wsdl-instance";
    private static final String WSDL_INSTANCE_NAMESPACE = "http://www.w3.org/ns/wsdl-instance";
    private static final QName WSA_WSDL_NAMESPACE_NS = new QName("xmlns:wsaw");
    private static final String XML_SCHEMA_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
    private static final String XML_SCHEMA_NAMESPACE_PREFIX = "xs";
    private static final QName XML_SCHEMA_NAMESPACE_NS = new QName("xmlns:xs");
    private static final String XML_SCHEMA_INSTANCE_NAMESPACE = "http://www.w3.org/2001/XMLSchema-instance";
    private static final QName WSDL_LOCATION2 = new QName("http://www.w3.org/2006/01/wsdl-instance", "wsdlLocation");
    private static final QName WSDL_LOCATION = new QName("http://www.w3.org/ns/wsdl-instance", "wsdlLocation");
    private static final QName XSI_TYPE = new QName("http://www.w3.org/2001/XMLSchema-instance", "type", "xsi");
    private static final org.apache.cxf.ws.addressing.wsdl.ObjectFactory WSA_WSDL_OBJECT_FACTORY = new org.apache.cxf.ws.addressing.wsdl.ObjectFactory();
    private static final Set<Class<?>> ADDRESSING_CLASSES = new HashSet();
    private static final AtomicReference<Reference<JAXBContext>> ADDRESSING_CONTEXT = new AtomicReference<SoftReference<Object>>(new SoftReference<Object>(null));

    private EndpointReferenceUtils() {
    }

    public static void setServiceAndPortName(EndpointReferenceType ref, QName serviceName, String portName) {
        if (null != serviceName) {
            JAXBElement<ServiceNameType> jaxbElement = EndpointReferenceUtils.getServiceNameType(serviceName, portName);
            MetadataType mt = EndpointReferenceUtils.getSetMetadata(ref);
            mt.getAny().add(jaxbElement);
        }
    }

    public static MetadataType getSetMetadata(EndpointReferenceType ref) {
        MetadataType mt = ref.getMetadata();
        if (null == mt) {
            mt = new MetadataType();
            ref.setMetadata(mt);
        }
        return mt;
    }

    public static JAXBElement<ServiceNameType> getServiceNameType(QName serviceName, String portName) {
        ServiceNameType serviceNameType = WSA_WSDL_OBJECT_FACTORY.createServiceNameType();
        serviceNameType.setValue(serviceName);
        serviceNameType.setEndpointName(portName);
        serviceNameType.getOtherAttributes().put(WSA_WSDL_NAMESPACE_NS, "http://www.w3.org/2006/05/addressing/wsdl");
        serviceNameType.getOtherAttributes().put(XSI_TYPE, "wsaw:" + serviceNameType.getClass().getSimpleName());
        return WSA_WSDL_OBJECT_FACTORY.createServiceName(serviceNameType);
    }

    public static QName getServiceName(EndpointReferenceType ref, Bus bus) {
        MetadataType metadata = ref.getMetadata();
        if (metadata == null) {
            return null;
        }
        for (Object obj : metadata.getAny()) {
            if (obj instanceof Element) {
                Element node = (Element)obj;
                if (!node.getNamespaceURI().equals("http://www.w3.org/2006/05/addressing/wsdl") && !node.getNamespaceURI().equals(NS_WSAW_2005) && !node.getNamespaceURI().equals("http://www.w3.org/2007/05/addressing/metadata") || !"ServiceName".equals(node.getLocalName())) continue;
                String content = node.getTextContent();
                String namespaceURI = node.getFirstChild().getNamespaceURI();
                String service2 = content;
                if (content.contains(":")) {
                    namespaceURI = EndpointReferenceUtils.getNameSpaceUri(node, content, namespaceURI);
                    service2 = EndpointReferenceUtils.getService(content);
                } else {
                    Node nodeAttr = node.getAttributes().getNamedItem("xmlns");
                    namespaceURI = nodeAttr.getNodeValue();
                }
                return new QName(namespaceURI, service2);
            }
            if (obj instanceof JAXBElement) {
                Object val = ((JAXBElement)obj).getValue();
                if (!(val instanceof ServiceNameType)) continue;
                return ((ServiceNameType)val).getValue();
            }
            if (!(obj instanceof ServiceNameType)) continue;
            return ((ServiceNameType)obj).getValue();
        }
        return null;
    }

    public static String getPortName(EndpointReferenceType ref) {
        MetadataType metadata = ref.getMetadata();
        if (metadata != null) {
            for (Object obj : metadata.getAny()) {
                if (obj instanceof Element) {
                    Element node = (Element)obj;
                    if (!node.getNamespaceURI().equals("http://www.w3.org/2006/05/addressing/wsdl") && !node.getNamespaceURI().equals(NS_WSAW_2005) && !node.getNamespaceURI().equals("http://www.w3.org/2007/05/addressing/metadata") || !node.getNodeName().contains("ServiceName")) continue;
                    Node item = node.getAttributes().getNamedItem("EndpointName");
                    return item != null ? item.getTextContent() : null;
                }
                if (obj instanceof JAXBElement) {
                    Object val = ((JAXBElement)obj).getValue();
                    if (!(val instanceof ServiceNameType)) continue;
                    return ((ServiceNameType)val).getEndpointName();
                }
                if (!(obj instanceof ServiceNameType)) continue;
                return ((ServiceNameType)obj).getEndpointName();
            }
        }
        return null;
    }

    public static QName getPortQName(EndpointReferenceType ref, Bus bus) {
        QName serviceName = EndpointReferenceUtils.getServiceName(ref, bus);
        return new QName(serviceName.getNamespaceURI(), EndpointReferenceUtils.getPortName(ref));
    }

    public static void setPortName(EndpointReferenceType ref, String portName) {
        MetadataType metadata = ref.getMetadata();
        if (metadata != null) {
            for (Object obj : metadata.getAny()) {
                if (obj instanceof Element) {
                    Element node = (Element)obj;
                    if (!node.getNodeName().contains("ServiceName") || !node.getNamespaceURI().equals("http://www.w3.org/2006/05/addressing/wsdl") && !node.getNamespaceURI().equals(NS_WSAW_2005) && !node.getNamespaceURI().equals("http://www.w3.org/2007/05/addressing/metadata")) continue;
                    node.setAttribute("EndpointName", portName);
                    continue;
                }
                if (obj instanceof JAXBElement) {
                    Object val = ((JAXBElement)obj).getValue();
                    if (!(val instanceof ServiceNameType)) continue;
                    ((ServiceNameType)val).setEndpointName(portName);
                    continue;
                }
                if (!(obj instanceof ServiceNameType)) continue;
                ((ServiceNameType)obj).setEndpointName(portName);
            }
        }
    }

    public static void setInterfaceName(EndpointReferenceType ref, QName portTypeName) {
        if (null != portTypeName) {
            AttributedQNameType interfaceNameType = WSA_WSDL_OBJECT_FACTORY.createAttributedQNameType();
            interfaceNameType.setValue(portTypeName);
            interfaceNameType.getOtherAttributes().put(XML_SCHEMA_NAMESPACE_NS, XML_SCHEMA_NAMESPACE);
            interfaceNameType.getOtherAttributes().put(XSI_TYPE, "xs:" + portTypeName.getClass().getSimpleName());
            JAXBElement<AttributedQNameType> jaxbElement = WSA_WSDL_OBJECT_FACTORY.createInterfaceName(interfaceNameType);
            MetadataType mt = EndpointReferenceUtils.getSetMetadata(ref);
            mt.getAny().add(jaxbElement);
        }
    }

    public static QName getInterfaceName(EndpointReferenceType ref, Bus bus) {
        MetadataType metadata = ref.getMetadata();
        if (metadata == null) {
            return null;
        }
        for (Object obj : metadata.getAny()) {
            if (obj instanceof Element) {
                Element node = (Element)obj;
                if (!node.getNamespaceURI().equals("http://www.w3.org/2006/05/addressing/wsdl") && !node.getNamespaceURI().equals("http://www.w3.org/2007/05/addressing/metadata") || !node.getNodeName().contains("InterfaceName")) continue;
                String content = node.getTextContent();
                String namespaceURI = node.getFirstChild().getNamespaceURI();
                if (content.contains(":")) {
                    namespaceURI = EndpointReferenceUtils.getNameSpaceUri(node, content, namespaceURI);
                    content = EndpointReferenceUtils.getService(content);
                } else {
                    Node nodeAttr = node.getAttributes().getNamedItem("xmlns");
                    namespaceURI = nodeAttr.getNodeValue();
                }
                return new QName(namespaceURI, content);
            }
            if (obj instanceof JAXBElement) {
                Object val = ((JAXBElement)obj).getValue();
                if (!(val instanceof AttributedQNameType)) continue;
                return ((AttributedQNameType)val).getValue();
            }
            if (!(obj instanceof AttributedQNameType)) continue;
            return ((AttributedQNameType)obj).getValue();
        }
        return null;
    }

    public static void setWSDLLocation(EndpointReferenceType ref, String ... wsdlLocation) {
        MetadataType metadata = EndpointReferenceUtils.getSetMetadata(ref);
        metadata.getOtherAttributes().put(WSDL_LOCATION, String.join((CharSequence)" ", wsdlLocation).trim());
    }

    public static String getWSDLLocation(EndpointReferenceType ref) {
        String wsdlLocation = null;
        MetadataType metadata = ref.getMetadata();
        if (metadata != null && (wsdlLocation = metadata.getOtherAttributes().get(WSDL_LOCATION)) == null) {
            wsdlLocation = metadata.getOtherAttributes().get(WSDL_LOCATION2);
        }
        if (null == wsdlLocation) {
            return null;
        }
        return wsdlLocation;
    }

    public static void setMetadata(EndpointReferenceType ref, List<Source> metadata) throws EndpointUtilsException {
        if (null != ref) {
            MetadataType mt = EndpointReferenceUtils.getSetMetadata(ref);
            List<Object> anyList = mt.getAny();
            try {
                for (Source source2 : metadata) {
                    Node node = null;
                    boolean doTransform = true;
                    if (source2 instanceof StreamSource) {
                        StreamSource ss = (StreamSource)source2;
                        if (null == ss.getInputStream() && null == ss.getReader()) {
                            EndpointReferenceUtils.setWSDLLocation(ref, ss.getSystemId());
                            doTransform = false;
                        }
                    } else if (source2 instanceof DOMSource) {
                        node = ((DOMSource)source2).getNode();
                        doTransform = false;
                    }
                    if (doTransform) {
                        node = StaxUtils.read(source2);
                    }
                    if (null == node) continue;
                    if (node instanceof Document) {
                        try {
                            ((Document)node).setDocumentURI(source2.getSystemId());
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        node = node.getFirstChild();
                    }
                    while (node.getNodeType() != 1) {
                        node = node.getNextSibling();
                    }
                    anyList.add(node);
                }
            }
            catch (XMLStreamException te) {
                throw new EndpointUtilsException(new Message("COULD_NOT_POPULATE_EPR", LOG, new Object[0]), (Throwable)te);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Schema createSchema(ServiceInfo serviceInfo, Bus b) {
        Schema schema = serviceInfo.getProperty(Schema.class.getName(), Schema.class);
        if (schema == null) {
            SchemaFactory factory2 = SchemaFactory.newInstance(XML_SCHEMA_NAMESPACE);
            LinkedHashMap<String, byte[]> schemaSourcesMap = new LinkedHashMap<String, byte[]>();
            LinkedHashMap<String, Source> schemaSourcesMap2 = new LinkedHashMap<String, Source>();
            XMLStreamWriter writer = null;
            try {
                LoadingByteArrayOutputStream out;
                for (SchemaInfo si : serviceInfo.getSchemas()) {
                    Element el = si.getElement();
                    String baseURI = null;
                    try {
                        baseURI = el.getBaseURI();
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    if (baseURI == null) {
                        baseURI = si.getSystemId();
                    }
                    DOMSource ds = new DOMSource(el, baseURI);
                    schemaSourcesMap2.put(si.getSystemId() + ':' + si.getNamespaceURI(), ds);
                    out = new LoadingByteArrayOutputStream();
                    writer = StaxUtils.createXMLStreamWriter(out);
                    StaxUtils.copy(el, writer);
                    writer.flush();
                    schemaSourcesMap.put(si.getSystemId() + ':' + si.getNamespaceURI(), out.toByteArray());
                }
                for (XmlSchema sch : serviceInfo.getXmlSchemaCollection().getXmlSchemas()) {
                    if (sch.getSourceURI() == null || schemaSourcesMap.containsKey(sch.getSourceURI() + ':' + sch.getTargetNamespace())) continue;
                    InputStream ins = null;
                    try {
                        URL url2 = new URL(sch.getSourceURI());
                        ins = url2.openStream();
                    }
                    catch (Exception url2) {
                        // empty catch block
                    }
                    out = new LoadingByteArrayOutputStream();
                    if (ins == null) {
                        sch.write(out);
                    } else {
                        IOUtils.copyAndCloseInput(ins, out);
                    }
                    schemaSourcesMap.put(sch.getSourceURI() + ':' + sch.getTargetNamespace(), out.toByteArray());
                    StreamSource source2 = new StreamSource(out.createInputStream(), sch.getSourceURI());
                    schemaSourcesMap2.put(sch.getSourceURI() + ':' + sch.getTargetNamespace(), source2);
                }
                factory2.setResourceResolver(new SchemaLSResourceResolver(schemaSourcesMap, b != null ? b : BusFactory.getThreadDefaultBus(false)));
                schema = factory2.newSchema(schemaSourcesMap2.values().toArray(new Source[schemaSourcesMap2.size()]));
            }
            catch (Exception ex) {
                LOG.log(Level.WARNING, "SAXException for newSchema()", ex);
                for (SchemaInfo schemaInfo : serviceInfo.getSchemas()) {
                    String s2 = StaxUtils.toString(schemaInfo.getElement(), 4);
                    LOG.log(Level.INFO, "Schema for: " + schemaInfo.getNamespaceURI() + "\n" + s2);
                }
            }
            finally {
                StaxUtils.close(writer);
            }
            serviceInfo.setProperty(Schema.class.getName(), schema);
        }
        return schema;
    }

    public static Schema getSchema(ServiceInfo serviceInfo) {
        return EndpointReferenceUtils.getSchema(serviceInfo, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Schema getSchema(ServiceInfo serviceInfo, Bus b) {
        if (serviceInfo == null) {
            return null;
        }
        Schema schema = serviceInfo.getProperty(Schema.class.getName(), Schema.class);
        if (schema == null && !serviceInfo.hasProperty(Schema.class.getName() + ".CHECKED")) {
            try {
                ServiceInfo serviceInfo2 = serviceInfo;
                synchronized (serviceInfo2) {
                    Schema schema2 = EndpointReferenceUtils.createSchema(serviceInfo, b);
                    return schema2;
                }
            }
            finally {
                serviceInfo.setProperty(Schema.class.getName() + ".CHECKED", Boolean.TRUE);
            }
        }
        return schema;
    }

    public static String getAddress(EndpointReferenceType ref) {
        AttributedURIType a = ref.getAddress();
        if (null != a) {
            return a.getValue();
        }
        return null;
    }

    public static void setAddress(EndpointReferenceType ref, String address) {
        AttributedURIType a = new AttributedURIType();
        a.setValue(address);
        ref.setAddress(a);
    }

    public static EndpointReferenceType getEndpointReference(URL wsdlUrl, QName serviceName, String portName) {
        EndpointReferenceType reference = new EndpointReferenceType();
        reference.setMetadata(new MetadataType());
        EndpointReferenceUtils.setServiceAndPortName(reference, serviceName, portName);
        EndpointReferenceUtils.setWSDLLocation(reference, wsdlUrl.toString());
        return reference;
    }

    public static EndpointReferenceType duplicate(EndpointReferenceType ref) {
        EndpointReferenceType reference = new EndpointReferenceType();
        reference.setMetadata(ref.getMetadata());
        reference.getAny().addAll(ref.getAny());
        reference.setAddress(ref.getAddress());
        return reference;
    }

    public static EndpointReferenceType getEndpointReference(String address) {
        EndpointReferenceType reference = new EndpointReferenceType();
        EndpointReferenceUtils.setAddress(reference, address);
        return reference;
    }

    public static EndpointReferenceType getEndpointReference(AttributedURIType address) {
        EndpointReferenceType reference = new EndpointReferenceType();
        reference.setAddress(address);
        return reference;
    }

    public static EndpointReferenceType getAnonymousEndpointReference() {
        EndpointReferenceType reference = new EndpointReferenceType();
        EndpointReferenceUtils.setAddress(reference, ANONYMOUS_ADDRESS);
        return reference;
    }

    public static EndpointReferenceType resolve(EndpointReferenceType logical, Bus bus) {
        EndpointResolverRegistry registry;
        EndpointReferenceType physical = null;
        if (bus != null && (registry = bus.getExtension(EndpointResolverRegistry.class)) != null) {
            physical = registry.resolve(logical);
        }
        return physical != null ? physical : logical;
    }

    public static EndpointReferenceType renew(EndpointReferenceType logical, EndpointReferenceType physical, Bus bus) {
        EndpointResolverRegistry registry;
        EndpointReferenceType renewed = null;
        if (bus != null && (registry = bus.getExtension(EndpointResolverRegistry.class)) != null) {
            renewed = registry.renew(logical, physical);
        }
        return renewed != null ? renewed : physical;
    }

    public static EndpointReferenceType mint(QName serviceName, Bus bus) {
        EndpointResolverRegistry registry;
        EndpointReferenceType logical = null;
        if (bus != null && (registry = bus.getExtension(EndpointResolverRegistry.class)) != null) {
            logical = registry.mint(serviceName);
        }
        return logical;
    }

    public static EndpointReferenceType mint(EndpointReferenceType physical, Bus bus) {
        EndpointResolverRegistry registry;
        EndpointReferenceType logical = null;
        if (bus != null && (registry = bus.getExtension(EndpointResolverRegistry.class)) != null) {
            logical = registry.mint(physical);
        }
        return logical != null ? logical : physical;
    }

    private static String getNameSpaceUri(Node node, String content, String namespaceURI) {
        if (namespaceURI == null) {
            namespaceURI = node.lookupNamespaceURI(content.substring(0, content.indexOf(58)));
        }
        return namespaceURI;
    }

    private static String getService(String content) {
        return content.substring(content.indexOf(58) + 1, content.length());
    }

    public static EndpointReferenceType getEndpointReferenceWithId(QName serviceQName, String portName, String id, Bus bus) {
        EndpointReferenceType epr = null;
        MultiplexDestination destination = EndpointReferenceUtils.getMatchingMultiplexDestination(serviceQName, portName, bus);
        if (null != destination) {
            epr = destination.getAddressWithId(id);
        }
        return epr;
    }

    public static String getEndpointReferenceId(Map<String, Object> messageContext) {
        String id = null;
        Destination destination = (Destination)messageContext.get(Destination.class.getName());
        if (destination instanceof MultiplexDestination) {
            id = ((MultiplexDestination)destination).getId(messageContext);
        }
        return id;
    }

    private static synchronized JAXBContext createContextForEPR() throws JAXBException {
        Reference<JAXBContext> rctx = ADDRESSING_CONTEXT.get();
        JAXBContext ctx = rctx.get();
        if (ctx == null) {
            ctx = JAXBContextCache.getCachedContextAndSchemas(ADDRESSING_CLASSES, null, null, null, true).getContext();
            ADDRESSING_CONTEXT.set(new SoftReference<JAXBContext>(ctx));
        }
        return ctx;
    }

    private static JAXBContext getJAXBContextForEPR() throws JAXBException {
        Reference<JAXBContext> rctx = ADDRESSING_CONTEXT.get();
        JAXBContext ctx = rctx.get();
        if (ctx == null) {
            ctx = EndpointReferenceUtils.createContextForEPR();
        }
        return ctx;
    }

    public static Source convertToXML(EndpointReferenceType epr) {
        try {
            Marshaller jm = EndpointReferenceUtils.getJAXBContextForEPR().createMarshaller();
            jm.setProperty("jaxb.fragment", true);
            QName qname = new QName("http://www.w3.org/2005/08/addressing", "EndpointReference");
            JAXBElement<EndpointReferenceType> jaxEle = new JAXBElement<EndpointReferenceType>(qname, EndpointReferenceType.class, epr);
            W3CDOMStreamWriter writer = new W3CDOMStreamWriter();
            jm.marshal(jaxEle, writer);
            return new DOMSource(writer.getDocument());
        }
        catch (JAXBException jAXBException) {
            return null;
        }
    }

    private static MultiplexDestination getMatchingMultiplexDestination(QName serviceQName, String portName, Bus bus) {
        MultiplexDestination destination = null;
        ServerRegistry serverRegistry = bus.getExtension(ServerRegistry.class);
        if (null != serverRegistry) {
            List<Server> servers = serverRegistry.getServers();
            for (Server s2 : servers) {
                Destination dest;
                QName targetServiceQName = s2.getEndpoint().getEndpointInfo().getService().getName();
                if (!serviceQName.equals(targetServiceQName) || !EndpointReferenceUtils.portNameMatches(s2, portName) || !((dest = s2.getDestination()) instanceof MultiplexDestination)) continue;
                destination = (MultiplexDestination)dest;
                break;
            }
        } else {
            LOG.log(Level.WARNING, "Failed to locate service matching " + serviceQName + ", because the bus ServerRegistry extension provider is null");
        }
        return destination;
    }

    private static boolean portNameMatches(Server s2, String portName) {
        return null == portName || portName.equals(s2.getEndpoint().getEndpointInfo().getName().getLocalPart());
    }

    static {
        ADDRESSING_CLASSES.add(WSA_WSDL_OBJECT_FACTORY.getClass());
        ADDRESSING_CLASSES.add(ObjectFactory.class);
    }

    private static final class SchemaLSResourceResolver
    implements LSResourceResolver {
        private final Map<String, byte[]> schemas;
        private final Set<String> done = new HashSet<String>();
        private final ExtendedURIResolver resolver = new ExtendedURIResolver();
        private final Bus bus;

        private SchemaLSResourceResolver(Map<String, byte[]> schemas, Bus b) {
            this.schemas = schemas;
            this.bus = b;
        }

        @Override
        public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
            byte[] ds;
            String newId = systemId;
            if (baseURI != null && systemId != null) {
                try {
                    URI uri = new URI(baseURI);
                    uri = uri.resolve(systemId);
                    newId = uri.toString();
                    if (newId.equals(systemId)) {
                        URL url2 = new URL(baseURI);
                        url2 = new URL(url2, systemId);
                        newId = url2.toExternalForm();
                    }
                }
                catch (IllegalArgumentException uri) {
                }
                catch (URISyntaxException uri) {
                }
                catch (MalformedURLException uri) {
                    // empty catch block
                }
            }
            LSInputImpl impl = null;
            if (this.done.contains(newId + ":" + namespaceURI)) {
                return null;
            }
            if (this.schemas.containsKey(newId + ":" + namespaceURI)) {
                ds = this.schemas.remove(newId + ":" + namespaceURI);
                impl = this.createInput(newId, ds);
                this.done.add(newId + ":" + namespaceURI);
            }
            if (impl == null && this.schemas.containsKey(newId + ":null")) {
                ds = this.schemas.get(newId + ":null");
                impl = this.createInput(newId, ds);
                this.done.add(newId + ":" + namespaceURI);
            }
            if (impl == null && this.bus != null && systemId != null) {
                URL uRL;
                ResourceManager rm = this.bus.getExtension(ResourceManager.class);
                URL uRL2 = uRL = rm == null ? null : rm.resolveResource(systemId, URL.class);
                if (uRL != null) {
                    newId = uRL.toString();
                    if (this.done.contains(newId + ":" + namespaceURI)) {
                        return null;
                    }
                    if (this.schemas.containsKey(newId + ":" + namespaceURI)) {
                        byte[] byArray = this.schemas.remove(newId + ":" + namespaceURI);
                        impl = this.createInput(newId, byArray);
                        this.done.add(newId + ":" + namespaceURI);
                    }
                }
            }
            if (impl == null) {
                InputSource source2;
                for (Map.Entry entry : this.schemas.entrySet()) {
                    if (!((String)entry.getKey()).endsWith(systemId + ":" + namespaceURI)) continue;
                    this.schemas.remove(entry.getKey());
                    impl = this.createInput(newId, (byte[])entry.getValue());
                    this.done.add(newId + ":" + namespaceURI);
                    return impl;
                }
                if (systemId != null) {
                    String systemIdFileName = systemId.substring(systemId.lastIndexOf(47) + 1);
                    for (Map.Entry<String, byte[]> entry : this.schemas.entrySet()) {
                        if (!entry.getKey().endsWith((String)systemIdFileName + ":" + namespaceURI)) continue;
                        this.schemas.remove(entry.getKey());
                        impl = this.createInput(newId, entry.getValue());
                        this.done.add(newId + ":" + namespaceURI);
                        return impl;
                    }
                }
                if (namespaceURI != null) {
                    for (Map.Entry entry : this.schemas.entrySet()) {
                        if (!((String)entry.getKey()).endsWith(":" + namespaceURI)) continue;
                        this.schemas.remove(entry.getKey());
                        impl = this.createInput(newId, (byte[])entry.getValue());
                        this.done.add(newId + ":" + namespaceURI);
                        return impl;
                    }
                }
                if (systemId == null) {
                    systemId = publicId;
                }
                if (systemId != null && (source2 = this.resolver.resolve(systemId, baseURI)) != null) {
                    impl = new LSInputImpl();
                    impl.setByteStream(source2.getByteStream());
                    impl.setSystemId(source2.getSystemId());
                    impl.setPublicId(source2.getPublicId());
                }
                LOG.warning("Could not resolve Schema for " + systemId);
            }
            return impl;
        }

        private LSInputImpl createInput(String newId, byte[] value) {
            LSInputImpl impl = new LSInputImpl();
            impl.setSystemId(newId);
            impl.setBaseURI(newId);
            impl.setByteStream(new ByteArrayInputStream(value));
            return impl;
        }
    }
}

