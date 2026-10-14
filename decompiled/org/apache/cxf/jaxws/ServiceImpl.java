/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.wsdl.Definition;
import javax.wsdl.Port;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.http.HTTPAddress;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.extensions.soap.SOAPBinding;
import javax.wsdl.extensions.soap12.SOAP12Address;
import javax.wsdl.extensions.soap12.SOAP12Binding;
import javax.xml.bind.JAXBContext;
import javax.xml.namespace.QName;
import javax.xml.ws.Dispatch;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.spi.ServiceDelegate;
import org.apache.cxf.Bus;
import org.apache.cxf.BusException;
import org.apache.cxf.BusFactory;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapAddress;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapBinding;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.i18n.UncheckedException;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PackageUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.Configurer;
import org.apache.cxf.databinding.AbstractDataBinding;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.databinding.source.SourceDataBinding;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ClientImpl;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.endpoint.ServiceContractResolverRegistry;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.AbstractBasicInterceptorProvider;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.jaxws.CXFService;
import org.apache.cxf.jaxws.DispatchImpl;
import org.apache.cxf.jaxws.JaxWsClientFactoryBean;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.binding.soap.JaxWsSoapBindingConfiguration;
import org.apache.cxf.jaxws.handler.HandlerResolverImpl;
import org.apache.cxf.jaxws.handler.PortInfoImpl;
import org.apache.cxf.jaxws.spi.ProviderImpl;
import org.apache.cxf.jaxws.support.BindingID;
import org.apache.cxf.jaxws.support.DummyImpl;
import org.apache.cxf.jaxws.support.JaxWsClientEndpointImpl;
import org.apache.cxf.jaxws.support.JaxWsEndpointImpl;
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.factory.AbstractServiceFactoryBean;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.service.model.ServiceModelUtil;
import org.apache.cxf.transport.DestinationFactory;
import org.apache.cxf.transport.DestinationFactoryManager;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.EndpointReferenceUtils;
import org.apache.cxf.wsdl.WSDLManager;
import org.apache.cxf.wsdl.http.AddressType;
import org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean;
import org.apache.cxf.wsdl11.WSDLServiceFactory;

public class ServiceImpl
extends ServiceDelegate {
    private static final Logger LOG = LogUtils.getL7dLogger(ServiceImpl.class);
    private static final ResourceBundle BUNDLE = LOG.getResourceBundle();
    private Bus bus;
    private String wsdlURL;
    private HandlerResolver handlerResolver;
    private Executor executor;
    private QName serviceName;
    private Class<?> clazz;
    private Map<QName, PortInfoImpl> portInfos = new HashMap<QName, PortInfoImpl>();
    private WebServiceFeature[] serviceFeatures;

    public ServiceImpl(Bus b, URL url2, QName name, Class<?> cls, WebServiceFeature ... f) {
        this.clazz = cls;
        this.serviceName = name;
        if (cls == null || !CXFService.class.isAssignableFrom(cls)) {
            this.initialize(b, url2, f);
        }
    }

    final void initialize(Bus b, URL url2, WebServiceFeature ... f) {
        URI uri;
        ServiceContractResolverRegistry registry;
        if (b == null) {
            b = BusFactory.getThreadDefaultBus(true);
        }
        this.serviceFeatures = f;
        this.bus = b;
        this.handlerResolver = new HandlerResolverImpl(this.bus, this.serviceName, this.clazz);
        if (null == url2 && null != this.bus && null != (registry = this.bus.getExtension(ServiceContractResolverRegistry.class)) && null != (uri = registry.getContractLocation(this.serviceName))) {
            try {
                url2 = uri.toURL();
            }
            catch (MalformedURLException e) {
                LOG.log(Level.FINE, "resolve qname failed", this.serviceName);
                throw new WebServiceException(e);
            }
        }
        String string = this.wsdlURL = url2 == null ? null : url2.toString();
        if (url2 != null) {
            try {
                this.initializePorts();
            }
            catch (ServiceConstructionException e) {
                throw new WebServiceException(e);
            }
        }
    }

    private void initializePorts() {
        try {
            Definition def = this.bus.getExtension(WSDLManager.class).getDefinition(this.wsdlURL);
            javax.wsdl.Service serv = def.getService(this.serviceName);
            if (serv == null) {
                throw new WebServiceException("Could not find service named " + this.serviceName + " in wsdl " + this.wsdlURL);
            }
            Map wsdlports = CastUtils.cast(serv.getPorts());
            for (Port port : wsdlports.values()) {
                ExtensibilityElement e;
                QName name = new QName(this.serviceName.getNamespaceURI(), port.getName());
                String address = null;
                String bindingID = null;
                List extensions = CastUtils.cast(port.getBinding().getExtensibilityElements());
                if (!extensions.isEmpty()) {
                    e = (ExtensibilityElement)extensions.get(0);
                    if (e instanceof SoapBinding) {
                        bindingID = "http://schemas.xmlsoap.org/wsdl/soap/http";
                    } else if (e instanceof SOAP12Binding) {
                        bindingID = "http://www.w3.org/2003/05/soap/bindings/HTTP/";
                    } else if (e instanceof SOAPBinding) {
                        bindingID = "http://schemas.xmlsoap.org/wsdl/soap/http";
                    }
                }
                if (!(extensions = CastUtils.cast(port.getExtensibilityElements())).isEmpty()) {
                    e = (ExtensibilityElement)extensions.get(0);
                    if (e instanceof SoapAddress) {
                        address = ((SoapAddress)e).getLocationURI();
                    } else if (e instanceof AddressType) {
                        address = ((AddressType)((Object)e)).getLocation();
                    } else if (e instanceof SOAP12Address) {
                        address = ((SOAP12Address)e).getLocationURI();
                    } else if (e instanceof SOAPAddress) {
                        address = ((SOAPAddress)e).getLocationURI();
                    } else if (e instanceof HTTPAddress) {
                        address = ((HTTPAddress)e).getLocationURI();
                    }
                }
                this.addPort(name, bindingID, address);
            }
        }
        catch (WebServiceException e) {
            throw e;
        }
        catch (Throwable e) {
            if (e instanceof UncheckedException && LOG.isLoggable(Level.FINE)) {
                LOG.log(Level.FINE, e.getLocalizedMessage(), e);
            }
            WSDLServiceFactory sf = new WSDLServiceFactory(this.bus, this.wsdlURL, this.serviceName);
            Service service2 = sf.create();
            for (ServiceInfo si : service2.getServiceInfos()) {
                for (EndpointInfo ei : si.getEndpoints()) {
                    String bindingID = BindingID.getJaxwsBindingID(ei.getTransportId());
                    this.addPort(ei.getName(), bindingID, ei.getAddress());
                }
            }
        }
    }

    @Override
    public final void addPort(QName portName, String bindingId, String address) {
        PortInfoImpl portInfo = new PortInfoImpl(bindingId, portName, this.serviceName);
        portInfo.setAddress(address);
        this.portInfos.put(portName, portInfo);
    }

    private List<WebServiceFeature> getAllFeatures(WebServiceFeature[] features) {
        ArrayList<WebServiceFeature> f = new ArrayList<WebServiceFeature>();
        if (features != null) {
            Collections.addAll(f, features);
        }
        if (this.serviceFeatures != null) {
            Collections.addAll(f, this.serviceFeatures);
        }
        return f;
    }

    private JaxWsClientEndpointImpl getJaxwsEndpoint(QName portName, AbstractServiceFactoryBean sf, WebServiceFeature ... features) {
        PortInfoImpl portInfo;
        Service service2 = sf.getService();
        EndpointInfo ei = null;
        if (portName == null) {
            ei = service2.getServiceInfos().get(0).getEndpoints().iterator().next();
        } else {
            ei = service2.getEndpointInfo(portName);
            if (ei == null && null != (portInfo = this.getPortInfo(portName))) {
                try {
                    ei = this.createEndpointInfo(sf, portName, portInfo);
                }
                catch (BusException e) {
                    throw new WebServiceException(e);
                }
            }
        }
        if (ei == null) {
            Message msg = new Message("INVALID_PORT", BUNDLE, portName);
            throw new WebServiceException(msg.toString());
        }
        portInfo = this.getPortInfo(portName);
        if (portInfo != null && portInfo.getAddress() != null && !portInfo.getAddress().equals(ei.getAddress())) {
            ei.setAddress(portInfo.getAddress());
        }
        try {
            return new JaxWsClientEndpointImpl(this.bus, service2, ei, this, this.getAllFeatures(features));
        }
        catch (EndpointException e) {
            throw new WebServiceException(e);
        }
    }

    private AbstractServiceFactoryBean createDispatchService(DataBinding db) {
        AbstractServiceFactoryBean serviceFactory;
        AbstractServiceFactoryBean sf;
        Service dispatchService = null;
        if (null != this.wsdlURL) {
            sf = new WSDLServiceFactory(this.bus, this.wsdlURL, this.serviceName);
            dispatchService = ((WSDLServiceFactory)sf).create();
            dispatchService.setDataBinding(db);
            serviceFactory = sf;
        } else {
            sf = new JaxWsServiceFactoryBean();
            sf.setBus(this.bus);
            ((ReflectionServiceFactoryBean)sf).setServiceName(this.serviceName);
            ((ReflectionServiceFactoryBean)sf).setServiceClass(DummyImpl.class);
            sf.setDataBinding(db);
            dispatchService = ((ReflectionServiceFactoryBean)sf).create();
            serviceFactory = sf;
        }
        this.configureObject(dispatchService);
        for (ServiceInfo si : dispatchService.getServiceInfos()) {
            si.setProperty("soap.force.doclit.bare", Boolean.TRUE);
            if (null == this.wsdlURL) {
                for (EndpointInfo ei : si.getEndpoints()) {
                    ei.setProperty("soap.no.validate.parts", Boolean.TRUE);
                }
            }
            for (BindingInfo bind : si.getBindings()) {
                for (BindingOperationInfo bop : bind.getOperations()) {
                    if (!bop.isUnwrappedCapable()) continue;
                    bop.getOperationInfo().setUnwrappedOperation(null);
                    bop.setUnwrappedOperation(null);
                }
            }
        }
        return serviceFactory;
    }

    @Override
    public Executor getExecutor() {
        return this.executor;
    }

    @Override
    public HandlerResolver getHandlerResolver() {
        return this.handlerResolver;
    }

    @Override
    public <T> T getPort(Class<T> serviceEndpointInterface) {
        return this.getPort(serviceEndpointInterface, new WebServiceFeature[0]);
    }

    @Override
    public <T> T getPort(Class<T> serviceEndpointInterface, WebServiceFeature ... features) {
        try {
            return this.createPort(null, null, serviceEndpointInterface, features);
        }
        catch (ServiceConstructionException e) {
            throw new WebServiceException(e);
        }
    }

    @Override
    public <T> T getPort(QName portName, Class<T> serviceEndpointInterface) {
        return this.getPort(portName, serviceEndpointInterface, new WebServiceFeature[0]);
    }

    @Override
    public <T> T getPort(QName portName, Class<T> serviceEndpointInterface, WebServiceFeature ... features) {
        if (portName == null) {
            throw new WebServiceException(BUNDLE.getString("PORT_NAME_NULL_EXC"));
        }
        try {
            return this.createPort(portName, null, serviceEndpointInterface, features);
        }
        catch (ServiceConstructionException e) {
            throw new WebServiceException(e);
        }
    }

    public <T> T getPort(EndpointReferenceType endpointReference, Class<T> type) {
        return this.getPort(endpointReference, type, new WebServiceFeature[0]);
    }

    public <T> T getPort(EndpointReferenceType endpointReference, Class<T> type, WebServiceFeature ... features) {
        endpointReference = EndpointReferenceUtils.resolve(endpointReference, this.bus);
        QName serviceQName = EndpointReferenceUtils.getServiceName(endpointReference, this.bus);
        String portName = EndpointReferenceUtils.getPortName(endpointReference);
        QName portQName = null;
        if (portName != null && serviceQName != null) {
            String ns = serviceQName.getNamespaceURI();
            if (StringUtils.isEmpty(ns)) {
                for (QName qn : this.portInfos.keySet()) {
                    if (!portName.equals(qn.getLocalPart())) continue;
                    ns = qn.getNamespaceURI();
                }
            }
            if (StringUtils.isEmpty(ns) && this.serviceName != null) {
                ns = this.serviceName.getNamespaceURI();
            }
            portQName = new QName(ns, portName);
        }
        return this.createPort(portQName, endpointReference, type, features);
    }

    @Override
    public Iterator<QName> getPorts() {
        return this.portInfos.keySet().iterator();
    }

    @Override
    public QName getServiceName() {
        return this.serviceName;
    }

    @Override
    public URL getWSDLDocumentLocation() {
        try {
            return new URL(this.wsdlURL);
        }
        catch (MalformedURLException e) {
            throw new WebServiceException(e);
        }
    }

    @Override
    public void setExecutor(Executor e) {
        this.executor = e;
    }

    @Override
    public void setHandlerResolver(HandlerResolver hr) {
        this.handlerResolver = hr;
    }

    public Bus getBus() {
        return this.bus;
    }

    protected <T> T createPort(QName portName, EndpointReferenceType epr, Class<T> serviceEndpointInterface) {
        return this.createPort(portName, epr, serviceEndpointInterface, new WebServiceFeature[0]);
    }

    protected <T> T createPort(QName portName, EndpointReferenceType epr, Class<T> serviceEndpointInterface, WebServiceFeature ... features) {
        PortInfoImpl portInfo;
        LOG.log(Level.FINE, "creating port for portName", portName);
        LOG.log(Level.FINE, "endpoint reference:", epr);
        LOG.log(Level.FINE, "endpoint interface:", serviceEndpointInterface);
        JaxWsProxyFactoryBean proxyFac = new JaxWsProxyFactoryBean();
        JaxWsClientFactoryBean clientFac = (JaxWsClientFactoryBean)proxyFac.getClientFactoryBean();
        JaxWsServiceFactoryBean serviceFactory = (JaxWsServiceFactoryBean)proxyFac.getServiceFactory();
        List<WebServiceFeature> f = this.getAllFeatures(features);
        proxyFac.initFeatures();
        if (f != null) {
            serviceFactory.setWsFeatures(f);
        }
        proxyFac.setBus(this.bus);
        proxyFac.setServiceClass(serviceEndpointInterface);
        proxyFac.setServiceName(this.serviceName);
        if (epr != null && epr.getAddress() != null && epr.getAddress().getValue() != null) {
            clientFac.setAddress(epr.getAddress().getValue());
        }
        if (this.wsdlURL != null) {
            proxyFac.setWsdlURL(this.wsdlURL);
        }
        this.configureObject(proxyFac);
        this.configureObject(clientFac);
        if (portName == null) {
            EndpointInfo ei;
            QName portTypeName = this.getPortTypeName(serviceEndpointInterface);
            Service service2 = serviceFactory.getService();
            if (service2 == null) {
                serviceFactory.setServiceClass(serviceEndpointInterface);
                serviceFactory.setBus(this.getBus());
                service2 = serviceFactory.create();
            }
            portName = (ei = ServiceModelUtil.findBestEndpointInfo(portTypeName, service2.getServiceInfos())) != null ? ei.getName() : serviceFactory.getEndpointName();
        }
        serviceFactory.setEndpointName(portName);
        if (epr != null) {
            clientFac.setEndpointReference(epr);
        }
        if ((portInfo = this.portInfos.get(portName)) != null) {
            clientFac.setBindingId(portInfo.getBindingID());
            clientFac.setAddress(portInfo.getAddress());
        }
        if (this.clazz != ServiceImpl.class) {
            proxyFac.setLoadHandlers(false);
        }
        Object obj = proxyFac.create();
        Service service3 = serviceFactory.getService();
        this.configureObject(service3);
        Client client = ClientProxy.getClient(obj);
        client.getEndpoint().setExecutor(this.executor);
        client.setExecutor(this.executor);
        JaxWsEndpointImpl jaxwsEndpoint = (JaxWsEndpointImpl)client.getEndpoint();
        this.configureObject(jaxwsEndpoint);
        List<Handler> hc = jaxwsEndpoint.getJaxwsBinding().getHandlerChain();
        hc.addAll(this.handlerResolver.getHandlerChain(this.portInfos.get(portName)));
        jaxwsEndpoint.getJaxwsBinding().setHandlerChain(hc);
        LOG.log(Level.FINE, "created proxy", obj);
        if (portInfo == null) {
            this.addPort(portName, clientFac.getBindingId(), clientFac.getAddress());
        }
        return serviceEndpointInterface.cast(obj);
    }

    private EndpointInfo createEndpointInfo(AbstractServiceFactoryBean serviceFactory, QName portName, PortInfoImpl portInfo) throws BusException {
        EndpointInfo ei = null;
        String address = portInfo.getAddress();
        String bindingID = BindingID.getBindingID(portInfo.getBindingID());
        DestinationFactoryManager dfm = this.bus.getExtension(DestinationFactoryManager.class);
        try {
            dfm.getDestinationFactory(bindingID);
        }
        catch (BusException busException) {
            // empty catch block
        }
        DestinationFactory df = dfm.getDestinationFactoryForUri(address);
        String transportId = null;
        transportId = df != null && df.getTransportIds() != null && !df.getTransportIds().isEmpty() ? df.getTransportIds().get(0) : bindingID;
        JaxWsSoapBindingConfiguration config = null;
        if (serviceFactory instanceof JaxWsServiceFactoryBean) {
            config = new JaxWsSoapBindingConfiguration((JaxWsServiceFactoryBean)serviceFactory);
        }
        BindingInfo bindingInfo = this.bus.getExtension(BindingFactoryManager.class).getBindingFactory(bindingID).createBindingInfo(serviceFactory.getService(), bindingID, config);
        Service service2 = serviceFactory.getService();
        service2.getServiceInfos().get(0).addBinding(bindingInfo);
        ei = new EndpointInfo(service2.getServiceInfos().get(0), transportId);
        ei.setName(portName);
        ei.setAddress(address);
        ei.setBinding(bindingInfo);
        service2.getServiceInfos().get(0).addEndpoint(ei);
        return ei;
    }

    private void configureObject(Object instance) {
        this.configureObject(null, instance);
    }

    private void configureObject(String name, Object instance) {
        Configurer configurer = this.bus.getExtension(Configurer.class);
        if (null != configurer) {
            configurer.configureBean(name, instance);
        }
    }

    private PortInfoImpl getPortInfo(QName portName) {
        if (portName == null) {
            return null;
        }
        return this.portInfos.get(portName);
    }

    private QName getPortTypeName(Class<?> serviceEndpointInterface) {
        String tns;
        String name;
        WebService webService;
        String epi;
        Class<?> seiClass = serviceEndpointInterface;
        if (!serviceEndpointInterface.isAnnotationPresent(WebService.class)) {
            Message msg = new Message("SEI_NO_WEBSERVICE_ANNOTATION", BUNDLE, serviceEndpointInterface.getCanonicalName());
            throw new WebServiceException(msg.toString());
        }
        if (!serviceEndpointInterface.isInterface() && (epi = (webService = serviceEndpointInterface.getAnnotation(WebService.class)).endpointInterface()).length() > 0) {
            try {
                seiClass = Thread.currentThread().getContextClassLoader().loadClass(epi);
            }
            catch (ClassNotFoundException e) {
                Message msg = new Message("COULD_NOT_LOAD_CLASS", BUNDLE, epi);
                throw new WebServiceException(msg.toString());
            }
            if (!seiClass.isAnnotationPresent(WebService.class)) {
                Message msg = new Message("SEI_NO_WEBSERVICE_ANNOTATION", BUNDLE, seiClass.getCanonicalName());
                throw new WebServiceException(msg.toString());
            }
        }
        if ((name = (webService = seiClass.getAnnotation(WebService.class)).name()).length() == 0) {
            name = seiClass.getSimpleName();
        }
        if ((tns = webService.targetNamespace()).isEmpty()) {
            tns = PackageUtils.getNamespace(PackageUtils.getPackageName(seiClass));
        }
        return new QName(tns, name);
    }

    @Override
    public <T> Dispatch<T> createDispatch(QName portName, Class<T> type, Service.Mode mode) {
        return this.createDispatch(portName, type, mode, new WebServiceFeature[0]);
    }

    @Override
    public <T> Dispatch<T> createDispatch(QName portName, Class<T> type, Service.Mode mode, WebServiceFeature ... features) {
        return this.createDispatch(portName, type, null, mode, features);
    }

    public <T> Dispatch<T> createDispatch(QName portName, Class<T> type, JAXBContext context, Service.Mode mode, WebServiceFeature ... features) {
        JaxWsProxyFactoryBean clientFac = new JaxWsProxyFactoryBean();
        this.configureObject(portName.toString() + ".jaxws-client.proxyFactory", clientFac);
        AbstractServiceFactoryBean sf = null;
        try {
            AbstractDataBinding db = context != null ? new JAXBDataBinding(context) : new SourceDataBinding(type);
            sf = this.createDispatchService(db);
        }
        catch (ServiceConstructionException e) {
            throw new WebServiceException(e);
        }
        JaxWsClientEndpointImpl endpoint = this.getJaxwsEndpoint(portName, sf, features);
        if (clientFac.getProperties() != null) {
            endpoint.putAll(clientFac.getProperties());
        }
        endpoint.getFeatures().addAll(clientFac.getFeatures());
        Bus clientBus = this.getBus();
        if (clientFac.getBus() != BusFactory.getThreadDefaultBus(false) && clientFac.getBus() != null) {
            clientBus = clientFac.getBus();
        }
        List<Handler> hc = clientFac.getHandlers();
        hc.addAll(this.handlerResolver.getHandlerChain(this.portInfos.get(portName)));
        endpoint.getJaxwsBinding().setHandlerChain(hc);
        ClientImpl client = new ClientImpl(clientBus, (Endpoint)endpoint, clientFac.getConduitSelector());
        for (Feature af : endpoint.getFeatures()) {
            af.initialize(client, clientBus);
        }
        this.initIntercepors(client, clientFac);
        if (this.executor != null) {
            client.getEndpoint().setExecutor(this.executor);
        }
        if (!StringUtils.isEmpty(clientFac.getAddress())) {
            client.getEndpoint().getEndpointInfo().setAddress(clientFac.getAddress());
        } else {
            PortInfoImpl portInfo = this.portInfos.get(portName);
            if (portInfo != null && !StringUtils.isEmpty(portInfo.getAddress())) {
                client.getEndpoint().getEndpointInfo().setAddress(portInfo.getAddress());
            }
        }
        DispatchImpl<T> disp = new DispatchImpl<T>(client, mode, context, type);
        this.configureObject(disp);
        return disp;
    }

    @Override
    public <T> Dispatch<T> createDispatch(EndpointReference endpointReference, Class<T> type, Service.Mode mode, WebServiceFeature ... features) {
        EndpointReferenceType ref = ProviderImpl.convertToInternal(endpointReference);
        QName portName = EndpointReferenceUtils.getPortQName(ref, this.bus);
        this.updatePortInfoAddress(portName, EndpointReferenceUtils.getAddress(ref));
        return this.createDispatch(portName, type, mode, features);
    }

    @Override
    public Dispatch<Object> createDispatch(QName portName, JAXBContext context, Service.Mode mode) {
        return this.createDispatch(portName, context, mode, new WebServiceFeature[0]);
    }

    @Override
    public Dispatch<Object> createDispatch(QName portName, JAXBContext context, Service.Mode mode, WebServiceFeature ... features) {
        return this.createDispatch(portName, Object.class, context, mode, features);
    }

    @Override
    public Dispatch<Object> createDispatch(EndpointReference endpointReference, JAXBContext context, Service.Mode mode, WebServiceFeature ... features) {
        EndpointReferenceType ref = ProviderImpl.convertToInternal(endpointReference);
        QName portName = EndpointReferenceUtils.getPortQName(ref, this.bus);
        this.updatePortInfoAddress(portName, EndpointReferenceUtils.getAddress(ref));
        return this.createDispatch(portName, context, mode, features);
    }

    @Override
    public <T> T getPort(EndpointReference endpointReference, Class<T> serviceEndpointInterface, WebServiceFeature ... features) {
        return this.getPort(ProviderImpl.convertToInternal(endpointReference), serviceEndpointInterface, features);
    }

    private void initIntercepors(Client client, AbstractBasicInterceptorProvider clientFact) {
        client.getInInterceptors().addAll(clientFact.getInInterceptors());
        client.getOutInterceptors().addAll(clientFact.getOutInterceptors());
        client.getInFaultInterceptors().addAll(clientFact.getInFaultInterceptors());
        client.getOutFaultInterceptors().addAll(clientFact.getOutFaultInterceptors());
    }

    private void updatePortInfoAddress(QName portName, String eprAddress) {
        PortInfoImpl portInfo = this.portInfos.get(portName);
        if (!StringUtils.isEmpty(eprAddress) && portInfo != null) {
            portInfo.setAddress(eprAddress);
        }
    }
}

