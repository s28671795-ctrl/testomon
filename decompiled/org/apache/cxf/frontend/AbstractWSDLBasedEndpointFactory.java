/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.frontend;

import java.lang.reflect.Method;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.BusException;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.binding.soap.SoapBindingConfiguration;
import org.apache.cxf.binding.soap.SoapBindingFactory;
import org.apache.cxf.binding.soap.model.SoapBindingInfo;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.AbstractEndpointFactory;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.endpoint.EndpointImpl;
import org.apache.cxf.frontend.WSDLGetInterceptor;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.factory.FactoryBeanListener;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.ServiceModelUtil;
import org.apache.cxf.transport.ConduitInitiator;
import org.apache.cxf.transport.ConduitInitiatorManager;
import org.apache.cxf.transport.DestinationFactory;
import org.apache.cxf.transport.DestinationFactoryManager;
import org.apache.cxf.wsdl.service.factory.AbstractServiceConfiguration;
import org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean;
import org.apache.cxf.wsdl11.WSDLEndpointFactory;

public abstract class AbstractWSDLBasedEndpointFactory
extends AbstractEndpointFactory {
    private static final Logger LOG = LogUtils.getL7dLogger(AbstractWSDLBasedEndpointFactory.class);
    private Class<?> serviceClass;
    private ReflectionServiceFactoryBean serviceFactory;

    protected AbstractWSDLBasedEndpointFactory(ReflectionServiceFactoryBean sbean) {
        this.serviceFactory = sbean;
        this.serviceClass = sbean.getServiceClass();
        this.serviceName = sbean.getServiceQName(false);
        this.endpointName = sbean.getEndpointName(false);
        sbean.getServiceConfigurations().add(new SoapBindingServiceConfiguration());
    }

    protected AbstractWSDLBasedEndpointFactory() {
    }

    @Override
    protected Endpoint createEndpoint() throws BusException, EndpointException {
        Endpoint ep;
        BindingFactoryManager bfm;
        EndpointInfo ei;
        Service service2;
        this.serviceFactory.setFeatures(this.getFeatures());
        if (this.serviceName != null) {
            this.serviceFactory.setServiceName(this.serviceName);
        }
        if (this.endpointName != null) {
            this.serviceFactory.setEndpointName(this.endpointName);
        }
        if ((service2 = this.serviceFactory.getService()) == null) {
            this.initializeServiceFactory();
            service2 = this.serviceFactory.create();
        }
        if (this.endpointName == null) {
            this.endpointName = this.serviceFactory.getEndpointName();
        }
        if ((ei = service2.getEndpointInfo(this.endpointName)) != null) {
            if (this.transportId != null && !ei.getTransportId().equals(this.transportId) || this.bindingId != null && !ei.getBinding().getBindingId().equals(this.bindingId)) {
                ei = null;
            } else {
                bfm = this.getBus().getExtension(BindingFactoryManager.class);
                this.bindingFactory = bfm.getBindingFactory(ei.getBinding().getBindingId());
            }
        }
        if (ei == null) {
            if (this.getAddress() == null) {
                ei = ServiceModelUtil.findBestEndpointInfo(this.serviceFactory.getInterfaceName(), service2.getServiceInfos());
            }
            if (ei == null && !this.serviceFactory.isPopulateFromClass()) {
                ei = ServiceModelUtil.findBestEndpointInfo(this.serviceFactory.getInterfaceName(), service2.getServiceInfos());
                if (ei != null) {
                    bfm = this.getBus().getExtension(BindingFactoryManager.class);
                    this.bindingFactory = bfm.getBindingFactory(ei.getBinding().getBindingId());
                }
                if (ei == null) {
                    LOG.warning("Could not find endpoint/port for " + this.endpointName + " in wsdl. Creating default.");
                } else if (!ei.getName().equals(this.endpointName)) {
                    LOG.warning("Could not find endpoint/port for " + this.endpointName + " in wsdl. Using " + ei.getName() + ".");
                }
            }
            if (ei == null) {
                ei = this.createEndpointInfo(null);
            } else if (this.transportId != null && !ei.getTransportId().equals(this.transportId)) {
                LOG.warning("Transport for endpoint/port " + this.endpointName + " in wsdl doesn't match " + this.transportId + ".");
                BindingInfo bi = ei.getBinding();
                ei = this.createEndpointInfo(bi);
            } else if (!(this.bindingId == null || ei.getBinding().getBindingId().equals(this.bindingId) || SoapBindingFactory.DEFAULT_NAMESPACES.contains(this.bindingId) && SoapBindingFactory.DEFAULT_NAMESPACES.contains(ei.getBinding().getBindingId()))) {
                LOG.warning("Binding for endpoint/port " + this.endpointName + " in wsdl doesn't match " + this.bindingId + ".");
                ei = this.createEndpointInfo(null);
            } else if (this.getAddress() != null) {
                ei.setAddress(this.getAddress());
                if (ei.getAddress().startsWith("camel") || ei.getAddress().startsWith("local")) {
                    this.modifyTransportIdPerAddress(ei);
                }
            }
        } else if (this.getAddress() != null) {
            ei.setAddress(this.getAddress());
        }
        if (this.publishedEndpointUrl != null && !"".equals(this.publishedEndpointUrl)) {
            ei.setProperty("publishedEndpointUrl", this.publishedEndpointUrl);
        }
        if (this.endpointReference != null) {
            ei.setAddress(this.endpointReference);
        }
        if ((ep = service2.getEndpoints().get(ei.getName())) == null) {
            ep = this.serviceFactory.createEndpoint(ei);
            ((EndpointImpl)ep).initializeActiveFeatures(this.getFeatures());
        } else {
            this.serviceFactory.setEndpointName(ei.getName());
            if (ep.getActiveFeatures() == null) {
                ((EndpointImpl)ep).initializeActiveFeatures(this.getFeatures());
            }
        }
        if (this.properties != null) {
            ep.putAll(this.properties);
        }
        service2.getEndpoints().put(ep.getEndpointInfo().getName(), ep);
        if (this.getInInterceptors() != null) {
            ep.getInInterceptors().addAll(this.getInInterceptors());
            ep.getInInterceptors().add(WSDLGetInterceptor.INSTANCE);
        }
        if (this.getOutInterceptors() != null) {
            ep.getOutInterceptors().addAll(this.getOutInterceptors());
        }
        if (this.getInFaultInterceptors() != null) {
            ep.getInFaultInterceptors().addAll(this.getInFaultInterceptors());
        }
        if (this.getOutFaultInterceptors() != null) {
            ep.getOutFaultInterceptors().addAll(this.getOutFaultInterceptors());
        }
        this.serviceFactory.sendEvent(FactoryBeanListener.Event.ENDPOINT_SELECTED, ei, ep, this.serviceFactory.getServiceClass(), this.getServiceClass());
        return ep;
    }

    private void modifyTransportIdPerAddress(EndpointInfo ei) {
        if (this.transportId == null && this.getAddress() != null) {
            DestinationFactory df = this.getDestinationFactory();
            if (df == null) {
                DestinationFactoryManager dfm = this.getBus().getExtension(DestinationFactoryManager.class);
                df = dfm.getDestinationFactoryForUri(this.getAddress());
            }
            if (df != null) {
                this.transportId = df.getTransportIds().get(0);
            } else {
                ConduitInitiatorManager cim = this.getBus().getExtension(ConduitInitiatorManager.class);
                ConduitInitiator ci = cim.getConduitInitiatorForUri(this.getAddress());
                if (ci != null) {
                    this.transportId = ci.getTransportIds().get(0);
                }
            }
        }
        if (this.transportId != null) {
            ei.setTransportId(this.transportId);
        }
    }

    protected void initializeServiceFactory() {
        Class<?> cls = this.getServiceClass();
        this.serviceFactory.setServiceClass(cls);
        this.serviceFactory.setBus(this.getBus());
        if (this.dataBinding != null) {
            this.serviceFactory.setDataBinding(this.dataBinding);
        }
    }

    protected abstract String detectTransportIdFromAddress(String var1);

    protected abstract WSDLEndpointFactory getWSDLEndpointFactory();

    protected EndpointInfo createEndpointInfo(BindingInfo bindingInfo) throws BusException {
        EndpointInfo ei;
        if (this.transportId == null && this.getAddress() != null && this.getAddress().startsWith("jms:") && !"jms://".equals(this.getAddress())) {
            this.transportId = "http://www.w3.org/2010/soapjms/";
        }
        Service service2 = this.serviceFactory.getService();
        if (bindingInfo == null) {
            bindingInfo = this.createBindingInfo();
            if (bindingInfo instanceof SoapBindingInfo && (((SoapBindingInfo)bindingInfo).getTransportURI() == null || "http://cxf.apache.org/transports/local".equals(this.transportId))) {
                ((SoapBindingInfo)bindingInfo).setTransportURI(this.transportId);
                this.transportId = "http://schemas.xmlsoap.org/wsdl/soap/";
            }
            service2.getServiceInfos().get(0).addBinding(bindingInfo);
        }
        if (this.transportId == null) {
            if (bindingInfo instanceof SoapBindingInfo) {
                this.transportId = ((SoapBindingInfo)bindingInfo).getTransportURI();
            }
            if (this.transportId == null && this.getAddress() != null && this.getAddress().contains("://")) {
                this.transportId = this.detectTransportIdFromAddress(this.getAddress());
            }
            if (this.transportId == null) {
                this.transportId = "http://schemas.xmlsoap.org/wsdl/http/";
            }
        }
        this.setTransportId(this.transportId);
        WSDLEndpointFactory wsdlEndpointFactory = this.getWSDLEndpointFactory();
        if (wsdlEndpointFactory != null) {
            ei = wsdlEndpointFactory.createEndpointInfo(this.bus, service2.getServiceInfos().get(0), bindingInfo, null);
            ei.setTransportId(this.transportId);
        } else {
            ei = new EndpointInfo(service2.getServiceInfos().get(0), this.transportId);
        }
        int count = 1;
        while (service2.getEndpointInfo(this.endpointName) != null) {
            this.endpointName = new QName(this.endpointName.getNamespaceURI(), this.endpointName.getLocalPart() + count);
            ++count;
        }
        ei.setName(this.endpointName);
        ei.setAddress(this.getAddress());
        ei.setBinding(bindingInfo);
        if (wsdlEndpointFactory != null) {
            wsdlEndpointFactory.createPortExtensors(this.bus, ei, service2);
        }
        service2.getServiceInfos().get(0).addEndpoint(ei);
        this.serviceFactory.sendEvent(FactoryBeanListener.Event.ENDPOINTINFO_CREATED, ei);
        return ei;
    }

    protected SoapBindingConfiguration createSoapBindingConfig() {
        return new SoapBindingConfiguration();
    }

    @Override
    protected BindingInfo createBindingInfo() {
        BindingFactoryManager mgr = this.bus.getExtension(BindingFactoryManager.class);
        String binding = this.bindingId;
        if (binding == null && this.bindingConfig != null) {
            binding = this.bindingConfig.getBindingId();
        }
        if (binding == null) {
            binding = "http://schemas.xmlsoap.org/soap/";
        }
        try {
            if (binding.contains("/soap")) {
                if (this.bindingConfig == null) {
                    this.bindingConfig = this.createSoapBindingConfig();
                }
                if (this.bindingConfig instanceof SoapBindingConfiguration && !((SoapBindingConfiguration)this.bindingConfig).isSetStyle()) {
                    ((SoapBindingConfiguration)this.bindingConfig).setStyle(this.serviceFactory.getStyle());
                }
            }
            this.bindingFactory = mgr.getBindingFactory(binding);
            BindingInfo inf = this.bindingFactory.createBindingInfo(this.serviceFactory.getService(), binding, this.bindingConfig);
            for (BindingOperationInfo boi : inf.getOperations()) {
                this.serviceFactory.updateBindingOperation(boi);
                Method m4 = this.serviceFactory.getMethodDispatcher().getMethod(boi);
                this.serviceFactory.sendEvent(FactoryBeanListener.Event.BINDING_OPERATION_CREATED, inf, boi, m4);
            }
            this.serviceFactory.sendEvent(FactoryBeanListener.Event.BINDING_CREATED, inf);
            return inf;
        }
        catch (BusException ex) {
            throw new ServiceConstructionException(new Message("COULD.NOT.RESOLVE.BINDING", LOG, this.bindingId), (Throwable)ex);
        }
    }

    public Class<?> getServiceClass() {
        return this.serviceClass;
    }

    public void setServiceClass(Class<?> serviceClass) {
        this.serviceClass = serviceClass;
    }

    public ReflectionServiceFactoryBean getServiceFactory() {
        return this.serviceFactory;
    }

    public void setServiceFactory(ReflectionServiceFactoryBean serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public String getWsdlURL() {
        return this.getServiceFactory().getWsdlURL();
    }

    public void setWsdlURL(String wsdlURL) {
        this.getServiceFactory().setWsdlURL(wsdlURL);
    }

    private class SoapBindingServiceConfiguration
    extends AbstractServiceConfiguration {
        private SoapBindingServiceConfiguration() {
        }

        @Override
        public String getStyle() {
            if (AbstractWSDLBasedEndpointFactory.this.getBindingConfig() instanceof SoapBindingConfiguration && ((SoapBindingConfiguration)AbstractWSDLBasedEndpointFactory.this.getBindingConfig()).isSetStyle()) {
                return ((SoapBindingConfiguration)AbstractWSDLBasedEndpointFactory.this.getBindingConfig()).getStyle();
            }
            return null;
        }

        @Override
        public Boolean isWrapped() {
            if (AbstractWSDLBasedEndpointFactory.this.getBindingConfig() instanceof SoapBindingConfiguration && ((SoapBindingConfiguration)AbstractWSDLBasedEndpointFactory.this.getBindingConfig()).isSetStyle() && "rpc".equals(((SoapBindingConfiguration)AbstractWSDLBasedEndpointFactory.this.getBindingConfig()).getStyle())) {
                return Boolean.FALSE;
            }
            return null;
        }
    }
}

