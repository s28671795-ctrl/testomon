/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing;

import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.ContextJAXBUtils;
import org.apache.cxf.ws.addressing.ContextUtils;
import org.apache.cxf.ws.addressing.EndpointReferenceUtils;
import org.apache.cxf.ws.addressing.RelatesToType;
import org.apache.cxf.ws.addressing.v200403.AttributedQName;
import org.apache.cxf.ws.addressing.v200408.AttributedURI;
import org.apache.cxf.ws.addressing.v200408.EndpointReferenceType;
import org.apache.cxf.ws.addressing.v200408.ObjectFactory;
import org.apache.cxf.ws.addressing.v200408.ReferenceParametersType;
import org.apache.cxf.ws.addressing.v200408.Relationship;
import org.apache.cxf.ws.addressing.v200408.ServiceNameType;
import org.w3c.dom.Element;

public class VersionTransformer {
    protected static final String NATIVE_VERSION = "http://www.w3.org/2005/08/addressing";

    protected VersionTransformer() {
    }

    public static boolean isSupported(String namespace) {
        return NATIVE_VERSION.equals(namespace) || "http://schemas.xmlsoap.org/ws/2004/08/addressing".equals(namespace) || "http://schemas.xmlsoap.org/ws/2004/03/addressing".equals(namespace);
    }

    public static AttributedURI convert(AttributedURIType internal) {
        AttributedURI exposed = Names200408.WSA_OBJECT_FACTORY.createAttributedURI();
        String exposedValue = "http://www.w3.org/2005/08/addressing/anonymous".equals(internal.getValue()) ? "http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous" : ("http://www.w3.org/2005/08/addressing/none".equals(internal.getValue()) ? "http://schemas.xmlsoap.org/ws/2004/08/addressing/role/none" : internal.getValue());
        exposed.setValue(exposedValue);
        VersionTransformer.putAll(exposed.getOtherAttributes(), internal.getOtherAttributes());
        return exposed;
    }

    public static org.apache.cxf.ws.addressing.v200403.AttributedURI convertTo200403(AttributedURIType internal) {
        org.apache.cxf.ws.addressing.v200403.AttributedURI exposed = Names200403.WSA_OBJECT_FACTORY.createAttributedURI();
        String exposedValue = "http://www.w3.org/2005/08/addressing/anonymous".equals(internal.getValue()) ? "http://schemas.xmlsoap.org/ws/2004/03/addressing/role/anonymous" : ("http://www.w3.org/2005/08/addressing/none".equals(internal.getValue()) ? "http://schemas.xmlsoap.org/ws/2004/03/addressing/role/none" : internal.getValue());
        exposed.setValue(exposedValue);
        VersionTransformer.putAll(exposed.getOtherAttributes(), internal.getOtherAttributes());
        return exposed;
    }

    public static AttributedURIType convert(AttributedURI exposed) {
        AttributedURIType internal = ContextUtils.WSA_OBJECT_FACTORY.createAttributedURIType();
        String internalValue = "http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous".equals(exposed.getValue()) ? "http://www.w3.org/2005/08/addressing/anonymous" : ("http://schemas.xmlsoap.org/ws/2004/08/addressing/role/none".equals(exposed.getValue()) ? "http://www.w3.org/2005/08/addressing/none" : exposed.getValue());
        internal.setValue(internalValue);
        VersionTransformer.putAll(internal.getOtherAttributes(), exposed.getOtherAttributes());
        return internal;
    }

    public static AttributedURIType convert(org.apache.cxf.ws.addressing.v200403.AttributedURI exposed) {
        AttributedURIType internal = ContextUtils.WSA_OBJECT_FACTORY.createAttributedURIType();
        String internalValue = "http://schemas.xmlsoap.org/ws/2004/03/addressing/role/anonymous".equals(exposed.getValue()) ? "http://www.w3.org/2005/08/addressing/anonymous" : ("http://schemas.xmlsoap.org/ws/2004/03/addressing/role/none".equals(exposed.getValue()) ? "http://www.w3.org/2005/08/addressing/none" : exposed.getValue());
        internal.setValue(internalValue);
        VersionTransformer.putAll(internal.getOtherAttributes(), exposed.getOtherAttributes());
        return internal;
    }

    public static EndpointReferenceType convert(org.apache.cxf.ws.addressing.EndpointReferenceType internal) {
        String portLocalName;
        EndpointReferenceType exposed = Names200408.WSA_OBJECT_FACTORY.createEndpointReferenceType();
        exposed.setAddress(VersionTransformer.convert(internal.getAddress()));
        exposed.setReferenceParameters(VersionTransformer.convert(internal.getReferenceParameters()));
        QName serviceQName = EndpointReferenceUtils.getServiceName(internal, null);
        if (serviceQName != null) {
            ServiceNameType serviceName = Names200408.WSA_OBJECT_FACTORY.createServiceNameType();
            serviceName.setValue(serviceQName);
            exposed.setServiceName(serviceName);
        }
        if ((portLocalName = EndpointReferenceUtils.getPortName(internal)) != null && serviceQName != null) {
            String namespace = serviceQName.getNamespaceURI() != null ? serviceQName.getNamespaceURI() : "http://www.w3.org/2004/08/wsdl-instance";
            QName portQName = new QName(namespace, portLocalName);
            org.apache.cxf.ws.addressing.v200408.AttributedQName portType = Names200408.WSA_OBJECT_FACTORY.createAttributedQName();
            portType.setValue(portQName);
            exposed.setPortType(portType);
        }
        VersionTransformer.addAll(exposed.getAny(), internal.getAny());
        VersionTransformer.putAll(exposed.getOtherAttributes(), internal.getOtherAttributes());
        return exposed;
    }

    public static org.apache.cxf.ws.addressing.v200403.EndpointReferenceType convertTo200403(org.apache.cxf.ws.addressing.EndpointReferenceType internal) {
        String portLocalName;
        org.apache.cxf.ws.addressing.v200403.EndpointReferenceType exposed = Names200403.WSA_OBJECT_FACTORY.createEndpointReferenceType();
        exposed.setAddress(VersionTransformer.convertTo200403(internal.getAddress()));
        QName serviceQName = EndpointReferenceUtils.getServiceName(internal, null);
        if (serviceQName != null) {
            org.apache.cxf.ws.addressing.v200403.ServiceNameType serviceName = Names200403.WSA_OBJECT_FACTORY.createServiceNameType();
            serviceName.setValue(serviceQName);
            exposed.setServiceName(serviceName);
        }
        if ((portLocalName = EndpointReferenceUtils.getPortName(internal)) != null && serviceQName != null) {
            String namespace = serviceQName.getNamespaceURI() != null ? serviceQName.getNamespaceURI() : "http://www.w3.org/2004/08/wsdl-instance";
            QName portQName = new QName(namespace, portLocalName);
            AttributedQName portType = Names200403.WSA_OBJECT_FACTORY.createAttributedQName();
            portType.setValue(portQName);
            exposed.setPortType(portType);
        }
        VersionTransformer.addAll(exposed.getAny(), internal.getAny());
        VersionTransformer.putAll(exposed.getOtherAttributes(), internal.getOtherAttributes());
        return exposed;
    }

    public static org.apache.cxf.ws.addressing.EndpointReferenceType convert(EndpointReferenceType exposed) {
        org.apache.cxf.ws.addressing.EndpointReferenceType internal = ContextUtils.WSA_OBJECT_FACTORY.createEndpointReferenceType();
        internal.setAddress(VersionTransformer.convert(exposed.getAddress()));
        internal.setReferenceParameters(VersionTransformer.convert(exposed.getReferenceParameters()));
        ServiceNameType serviceName = exposed.getServiceName();
        org.apache.cxf.ws.addressing.v200408.AttributedQName portName = exposed.getPortType();
        if (serviceName != null && portName != null) {
            EndpointReferenceUtils.setServiceAndPortName(internal, serviceName.getValue(), portName.getValue().getLocalPart());
        }
        VersionTransformer.addAll(internal.getAny(), exposed.getAny());
        VersionTransformer.putAll(internal.getOtherAttributes(), exposed.getOtherAttributes());
        return internal;
    }

    public static org.apache.cxf.ws.addressing.EndpointReferenceType convert(org.apache.cxf.ws.addressing.v200403.EndpointReferenceType exposed) {
        org.apache.cxf.ws.addressing.EndpointReferenceType internal = ContextUtils.WSA_OBJECT_FACTORY.createEndpointReferenceType();
        internal.setAddress(VersionTransformer.convert(exposed.getAddress()));
        org.apache.cxf.ws.addressing.v200403.ServiceNameType serviceName = exposed.getServiceName();
        AttributedQName portName = exposed.getPortType();
        if (serviceName != null && portName != null) {
            EndpointReferenceUtils.setServiceAndPortName(internal, serviceName.getValue(), portName.getValue().getLocalPart());
        }
        VersionTransformer.addAll(internal.getAny(), exposed.getAny());
        VersionTransformer.putAll(internal.getOtherAttributes(), exposed.getOtherAttributes());
        return internal;
    }

    public static ReferenceParametersType convert(org.apache.cxf.ws.addressing.ReferenceParametersType internal) {
        ReferenceParametersType exposed = null;
        if (internal != null) {
            exposed = Names200408.WSA_OBJECT_FACTORY.createReferenceParametersType();
            VersionTransformer.addAll(exposed.getAny(), internal.getAny());
        }
        return exposed;
    }

    public static org.apache.cxf.ws.addressing.ReferenceParametersType convert(ReferenceParametersType exposed) {
        org.apache.cxf.ws.addressing.ReferenceParametersType internal = null;
        if (exposed != null) {
            internal = ContextUtils.WSA_OBJECT_FACTORY.createReferenceParametersType();
            VersionTransformer.addAll(internal.getAny(), exposed.getAny());
        }
        return internal;
    }

    public static Relationship convert(RelatesToType internal) {
        Relationship exposed = null;
        if (internal != null) {
            exposed = Names200408.WSA_OBJECT_FACTORY.createRelationship();
            exposed.setValue(internal.getValue());
            String internalRelationshipType = internal.getRelationshipType();
            if (internalRelationshipType != null) {
                QName exposedRelationshipType = null;
                if (!"http://www.w3.org/2005/08/addressing/reply".equals(internalRelationshipType)) {
                    exposedRelationshipType = new QName(internalRelationshipType);
                }
                exposed.setRelationshipType(exposedRelationshipType);
            }
            VersionTransformer.putAll(exposed.getOtherAttributes(), internal.getOtherAttributes());
        }
        return exposed;
    }

    public static org.apache.cxf.ws.addressing.v200403.Relationship convertTo200403(RelatesToType internal) {
        org.apache.cxf.ws.addressing.v200403.Relationship exposed = null;
        if (internal != null) {
            exposed = Names200403.WSA_OBJECT_FACTORY.createRelationship();
            exposed.setValue(internal.getValue());
            String internalRelationshipType = internal.getRelationshipType();
            if (internalRelationshipType != null) {
                QName exposedRelationshipType = null;
                if (!"http://www.w3.org/2005/08/addressing/reply".equals(internalRelationshipType)) {
                    exposedRelationshipType = new QName(internalRelationshipType);
                }
                exposed.setRelationshipType(exposedRelationshipType);
            }
            VersionTransformer.putAll(exposed.getOtherAttributes(), internal.getOtherAttributes());
        }
        return exposed;
    }

    public static RelatesToType convert(Relationship exposed) {
        RelatesToType internal = null;
        if (exposed != null) {
            internal = ContextUtils.WSA_OBJECT_FACTORY.createRelatesToType();
            internal.setValue(exposed.getValue());
            QName exposedRelationshipType = exposed.getRelationshipType();
            if (exposedRelationshipType != null) {
                String internalRelationshipType = "reply".equalsIgnoreCase(exposedRelationshipType.getLocalPart()) ? "http://www.w3.org/2005/08/addressing/reply" : exposedRelationshipType.toString();
                internal.setRelationshipType(internalRelationshipType);
            }
            internal.getOtherAttributes().putAll(exposed.getOtherAttributes());
        }
        return internal;
    }

    public static RelatesToType convert(org.apache.cxf.ws.addressing.v200403.Relationship exposed) {
        RelatesToType internal = null;
        if (exposed != null) {
            internal = ContextUtils.WSA_OBJECT_FACTORY.createRelatesToType();
            internal.setValue(exposed.getValue());
            QName exposedRelationshipType = exposed.getRelationshipType();
            if (exposedRelationshipType != null) {
                String internalRelationshipType = "reply".equals(exposedRelationshipType.getLocalPart()) ? "http://www.w3.org/2005/08/addressing/reply" : exposedRelationshipType.toString();
                internal.setRelationshipType(internalRelationshipType);
            }
            internal.getOtherAttributes().putAll(exposed.getOtherAttributes());
        }
        return internal;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static org.apache.cxf.ws.addressing.EndpointReferenceType parseEndpointReference(Element ref) throws JAXBException {
        Element child = DOMUtils.getFirstElement(ref);
        String tns = null;
        while (child != null && tns == null) {
            if (VersionTransformer.isSupported(child.getNamespaceURI())) {
                tns = child.getNamespaceURI();
            }
            child = DOMUtils.getNextElement(child);
        }
        if (tns == null) {
            return null;
        }
        JAXBContext ctx = VersionTransformer.getExposedJAXBContext(tns);
        Unmarshaller um = ctx.createUnmarshaller();
        um.setEventHandler(null);
        try {
            JAXBElement<?> o = um.unmarshal(ref, VersionTransformer.getExposedReferenceType(tns));
            if (o != null) {
                org.apache.cxf.ws.addressing.EndpointReferenceType endpointReferenceType = VersionTransformer.convertToNative(o.getValue());
                return endpointReferenceType;
            }
            org.apache.cxf.ws.addressing.EndpointReferenceType endpointReferenceType = VersionTransformer.convertToNative(null);
            return endpointReferenceType;
        }
        finally {
            JAXBUtils.closeUnmarshaller(um);
        }
    }

    public static org.apache.cxf.ws.addressing.EndpointReferenceType convertToNative(Object exposed) {
        if (org.apache.cxf.ws.addressing.EndpointReferenceType.class.isInstance(exposed)) {
            return (org.apache.cxf.ws.addressing.EndpointReferenceType)exposed;
        }
        if (Names200408.EPR_TYPE.isInstance(exposed)) {
            return VersionTransformer.convert((EndpointReferenceType)exposed);
        }
        if (Names200403.EPR_TYPE.isInstance(exposed)) {
            return VersionTransformer.convert((org.apache.cxf.ws.addressing.v200403.EndpointReferenceType)exposed);
        }
        return null;
    }

    public static Class<?> getExposedReferenceType(String exposedURI) {
        return NATIVE_VERSION.equals(exposedURI) ? org.apache.cxf.ws.addressing.EndpointReferenceType.class : ("http://schemas.xmlsoap.org/ws/2004/08/addressing".equals(exposedURI) ? Names200408.EPR_TYPE : ("http://schemas.xmlsoap.org/ws/2004/03/addressing".equals(exposedURI) ? Names200403.EPR_TYPE : null));
    }

    public static JAXBContext getExposedJAXBContext(String exposedURI) throws JAXBException {
        return NATIVE_VERSION.equals(exposedURI) ? ContextJAXBUtils.getJAXBContext() : ("http://schemas.xmlsoap.org/ws/2004/08/addressing".equals(exposedURI) ? Names200408.getJAXBContext() : ("http://schemas.xmlsoap.org/ws/2004/03/addressing".equals(exposedURI) ? Names200403.getJAXBContext() : null));
    }

    private static void putAll(Map<QName, String> to, Map<QName, String> from) {
        if (from != null) {
            to.putAll(from);
        }
    }

    private static void addAll(List<Object> to, List<Object> from) {
        if (from != null) {
            to.addAll(from);
        }
    }

    public static class Names200403 {
        public static final String WSA_NAMESPACE_NAME = "http://schemas.xmlsoap.org/ws/2004/03/addressing";
        public static final String WSA_ANONYMOUS_ADDRESS = "http://schemas.xmlsoap.org/ws/2004/03/addressing/role/anonymous";
        public static final String WSA_NONE_ADDRESS = "http://schemas.xmlsoap.org/ws/2004/03/addressing/role/none";
        public static final org.apache.cxf.ws.addressing.v200403.ObjectFactory WSA_OBJECT_FACTORY = new org.apache.cxf.ws.addressing.v200403.ObjectFactory();
        public static final Class<org.apache.cxf.ws.addressing.v200403.EndpointReferenceType> EPR_TYPE = org.apache.cxf.ws.addressing.v200403.EndpointReferenceType.class;
        private static JAXBContext jaxbContext;

        protected Names200403() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public static JAXBContext getJAXBContext() throws JAXBException {
            Class<Names200403> clazz = Names200403.class;
            synchronized (Names200403.class) {
                if (jaxbContext == null) {
                    jaxbContext = JAXBContext.newInstance(org.apache.cxf.ws.addressing.v200403.ObjectFactory.class);
                }
                // ** MonitorExit[var0] (shouldn't be in output)
                return jaxbContext;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public static void setJAXBContext(JAXBContext ctx) throws JAXBException {
            Class<Names200403> clazz = Names200403.class;
            synchronized (Names200403.class) {
                jaxbContext = ctx;
                // ** MonitorExit[var1_1] (shouldn't be in output)
                return;
            }
        }
    }

    public static class Names200408 {
        public static final String WSA_NAMESPACE_NAME = "http://schemas.xmlsoap.org/ws/2004/08/addressing";
        public static final String WSA_ANONYMOUS_ADDRESS = "http://schemas.xmlsoap.org/ws/2004/08/addressing/role/anonymous";
        public static final String WSA_NONE_ADDRESS = "http://schemas.xmlsoap.org/ws/2004/08/addressing/role/none";
        public static final ObjectFactory WSA_OBJECT_FACTORY = new ObjectFactory();
        public static final Class<EndpointReferenceType> EPR_TYPE = EndpointReferenceType.class;
        private static JAXBContext jaxbContext;

        protected Names200408() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public static JAXBContext getJAXBContext() throws JAXBException {
            Class<Names200408> clazz = Names200408.class;
            synchronized (Names200408.class) {
                if (jaxbContext == null) {
                    jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
                }
                // ** MonitorExit[var0] (shouldn't be in output)
                return jaxbContext;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public static void setJAXBContext(JAXBContext ctx) throws JAXBException {
            Class<Names200408> clazz = Names200408.class;
            synchronized (Names200408.class) {
                jaxbContext = ctx;
                // ** MonitorExit[var1_1] (shouldn't be in output)
                return;
            }
        }
    }
}

