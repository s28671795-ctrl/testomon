/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensionRegistry;
import org.apache.cxf.Bus;
import org.apache.cxf.BusException;
import org.apache.cxf.binding.soap.SOAPBindingUtil;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.model.SoapBindingInfo;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapAddress;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.AbstractPropertiesHolder;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.transport.AbstractTransportFactory;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.ConduitInitiator;
import org.apache.cxf.transport.ConduitInitiatorManager;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.DestinationFactory;
import org.apache.cxf.transport.DestinationFactoryManager;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.wsdl.WSDLManager;
import org.apache.cxf.wsdl11.WSDLEndpointFactory;

@NoJSR250Annotations
public class SoapTransportFactory
extends AbstractTransportFactory
implements DestinationFactory,
WSDLEndpointFactory,
ConduitInitiator {
    public static final String CANNOT_GET_CONDUIT_ERROR = "Could not find conduit initiator for address: %s and transport: %s";
    public static final String SOAP_11_HTTP_BINDING = "http://schemas.xmlsoap.org/soap/http";
    public static final String SOAP_12_HTTP_BINDING = "http://www.w3.org/2003/05/soap/bindings/HTTP/";
    public static final String TRANSPORT_ID = "http://schemas.xmlsoap.org/soap/";
    public static final List<String> DEFAULT_NAMESPACES = Collections.unmodifiableList(Arrays.asList("http://schemas.xmlsoap.org/soap/", "http://schemas.xmlsoap.org/wsdl/soap/", "http://schemas.xmlsoap.org/wsdl/soap12/", "http://schemas.xmlsoap.org/soap/http", "http://schemas.xmlsoap.org/wsdl/soap/http", "http://www.w3.org/2010/soapjms/", "http://www.w3.org/2003/05/soap/bindings/HTTP/"));
    public static final Set<String> DEFAULT_PREFIXES = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("soap.udp")));

    public SoapTransportFactory() {
        super(DEFAULT_NAMESPACES);
    }

    @Override
    public Set<String> getUriPrefixes() {
        return DEFAULT_PREFIXES;
    }

    public String mapTransportURI(String s2, String address) {
        if ("http://www.w3.org/2010/soapjms/".equals(s2) || address != null && address.startsWith("jms")) {
            s2 = "http://cxf.apache.org/transports/jms";
        } else if (address != null && address.startsWith("soap.udp")) {
            s2 = "http://cxf.apache.org/transports/udp";
        } else if (SOAP_11_HTTP_BINDING.equals(s2) || SOAP_12_HTTP_BINDING.equals(s2) || "http://schemas.xmlsoap.org/wsdl/soap/".equals(s2) || "http://schemas.xmlsoap.org/wsdl/http".equals(s2) || "http://schemas.xmlsoap.org/wsdl/soap/http".equals(s2) || "http://schemas.xmlsoap.org/wsdl/soap/http/".equals(s2) || "http://schemas.xmlsoap.org/wsdl/http/".equals(s2)) {
            s2 = "http://cxf.apache.org/transports/http";
        }
        return s2;
    }

    private boolean isJMSSpecAddress(String address) {
        return address != null && address.startsWith("jms:") && !"jms://".equals(address);
    }

    @Override
    public Destination getDestination(EndpointInfo ei, Bus bus) throws IOException {
        String address = ei.getAddress();
        BindingInfo bi = ei.getBinding();
        String transId = ei.getTransportId();
        if (bi instanceof SoapBindingInfo && (transId = ((SoapBindingInfo)bi).getTransportURI()) == null) {
            transId = ei.getTransportId();
        }
        try {
            DestinationFactoryManager mgr = bus.getExtension(DestinationFactoryManager.class);
            DestinationFactory destinationFactory = StringUtils.isEmpty(address) || address.startsWith("http") || address.startsWith("jms") || address.startsWith("soap.udp") || address.startsWith("/") ? mgr.getDestinationFactory(this.mapTransportURI(transId, address)) : mgr.getDestinationFactoryForUri(address);
            if (destinationFactory == null) {
                throw new IOException("Could not find destination factory for transport " + transId);
            }
            return destinationFactory.getDestination(ei, bus);
        }
        catch (BusException e) {
            IOException ex = new IOException("Could not find destination factory for transport " + transId);
            ex.initCause(e);
            throw ex;
        }
    }

    @Override
    public void createPortExtensors(Bus b, EndpointInfo ei, Service service2) {
        if (ei.getBinding() instanceof SoapBindingInfo) {
            SoapBindingInfo bi = (SoapBindingInfo)ei.getBinding();
            this.createSoapExtensors(b, ei, bi, bi.getSoapVersion() instanceof Soap12);
        }
    }

    private void createSoapExtensors(Bus bus, EndpointInfo ei, SoapBindingInfo bi, boolean isSoap12) {
        try {
            String address = ei.getAddress();
            if (address == null) {
                address = "http://localhost:9090";
            }
            ExtensionRegistry registry = bus.getExtension(WSDLManager.class).getExtensionRegistry();
            SoapAddress soapAddress = SOAPBindingUtil.createSoapAddress(registry, isSoap12);
            soapAddress.setLocationURI(address);
            ei.addExtensor(soapAddress);
        }
        catch (WSDLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public EndpointInfo createEndpointInfo(Bus bus, ServiceInfo serviceInfo, BindingInfo b, List<?> ees) {
        String transportURI = "http://schemas.xmlsoap.org/wsdl/soap/";
        if (b instanceof SoapBindingInfo) {
            SoapBindingInfo sbi = (SoapBindingInfo)b;
            transportURI = sbi.getTransportURI();
        }
        SoapEndpointInfo info = new SoapEndpointInfo(serviceInfo, transportURI);
        if (ees != null) {
            for (Object extensor : ees) {
                if (SOAPBindingUtil.isSOAPAddress(extensor)) {
                    SoapAddress sa = SOAPBindingUtil.getSoapAddress(extensor);
                    ((AbstractPropertiesHolder)info).addExtensor(sa);
                    ((EndpointInfo)info).setAddress(sa.getLocationURI());
                    if (!this.isJMSSpecAddress(sa.getLocationURI())) continue;
                    info.setTransportId("http://www.w3.org/2010/soapjms/");
                    continue;
                }
                ((AbstractPropertiesHolder)info).addExtensor(extensor);
            }
        }
        return info;
    }

    @Override
    public Conduit getConduit(EndpointInfo ei, EndpointReferenceType target, Bus bus) throws IOException {
        String address = target == null ? ei.getAddress() : target.getAddress().getValue();
        BindingInfo bi = ei.getBinding();
        String transId = ei.getTransportId();
        if (bi instanceof SoapBindingInfo && (transId = ((SoapBindingInfo)bi).getTransportURI()) == null) {
            transId = ei.getTransportId();
        }
        try {
            ConduitInitiatorManager mgr = bus.getExtension(ConduitInitiatorManager.class);
            ConduitInitiator conduitInit = StringUtils.isEmpty(address) || address.startsWith("http") || address.startsWith("jms") || address.startsWith("soap.udp") ? mgr.getConduitInitiator(this.mapTransportURI(transId, address)) : mgr.getConduitInitiatorForUri(address);
            if (conduitInit == null) {
                throw new RuntimeException(String.format(CANNOT_GET_CONDUIT_ERROR, address, transId));
            }
            return conduitInit.getConduit(ei, target, bus);
        }
        catch (BusException e) {
            throw new RuntimeException(String.format(CANNOT_GET_CONDUIT_ERROR, address, transId));
        }
    }

    @Override
    public Conduit getConduit(EndpointInfo ei, Bus b) throws IOException {
        return this.getConduit(ei, ei.getTarget(), b);
    }

    public void setActivationNamespaces(Collection<String> ans) {
        super.setTransportIds(new ArrayList<String>(ans));
    }

    private static class SoapEndpointInfo
    extends EndpointInfo {
        SoapAddress saddress;

        SoapEndpointInfo(ServiceInfo serv, String trans) {
            super(serv, trans);
        }

        @Override
        public void setAddress(String s2) {
            super.setAddress(s2);
            if (this.saddress != null) {
                this.saddress.setLocationURI(s2);
            }
        }

        @Override
        public void addExtensor(Object el) {
            super.addExtensor(el);
            if (el instanceof SoapAddress) {
                this.saddress = (SoapAddress)el;
            }
        }
    }
}

