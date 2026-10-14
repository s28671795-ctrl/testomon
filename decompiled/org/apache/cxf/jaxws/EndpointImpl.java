/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.logging.Logger;
import javax.wsdl.Definition;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.Binding;
import javax.xml.ws.EndpointContext;
import javax.xml.ws.EndpointReference;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.WebServicePermission;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.spi.http.HttpContext;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.binding.BindingConfiguration;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ClassHelper;
import org.apache.cxf.common.util.ModCountCopyOnWriteArrayList;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.configuration.Configurable;
import org.apache.cxf.configuration.Configurer;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.endpoint.ServerImpl;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.frontend.WSDLGetUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.jaxws.EndpointReferenceBuilder;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.jaxws.support.JaxWsEndpointImpl;
import org.apache.cxf.jaxws.support.JaxWsImplementorInfo;
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.invoker.Invoker;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.http_jaxws_spi.JAXWSHttpSpiTransportFactory;
import org.apache.cxf.wsdl.WSDLManager;
import org.apache.cxf.wsdl11.WSDLServiceBuilder;
import org.w3c.dom.Element;

public class EndpointImpl
extends javax.xml.ws.Endpoint
implements InterceptorProvider,
Configurable,
AutoCloseable {
    public static final String CHECK_PUBLISH_ENDPOINT_PERMISSON_PROPERTY = "org.apache.cxf.jaxws.checkPublishEndpointPermission";
    public static final String CHECK_PUBLISH_ENDPOINT_PERMISSON_PROPERTY_WITH_SECURITY_MANAGER = "org.apache.cxf.jaxws.checkPublishEndpointPermissionWithSecurityManager";
    private static final WebServicePermission PUBLISH_PERMISSION = new WebServicePermission("publishEndpoint");
    private static final Logger LOG = LogUtils.getL7dLogger(EndpointImpl.class);
    private Bus bus;
    private Object implementor;
    private Server server;
    private JaxWsServerFactoryBean serverFactory;
    private JaxWsServiceFactoryBean serviceFactory;
    private Service service;
    private Map<String, Object> properties;
    private List<Source> metadata;
    private Invoker invoker;
    private Executor executor;
    private String bindingUri;
    private String wsdlLocation;
    private String address;
    private String publishedEndpointUrl;
    private QName endpointName;
    private QName serviceName;
    private Class<?> implementorClass;
    private List<String> schemaLocations;
    private List<Feature> features;
    private List<Interceptor<? extends Message>> in = new ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>();
    private List<Interceptor<? extends Message>> out = new ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>();
    private List<Interceptor<? extends Message>> outFault = new ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>();
    private List<Interceptor<? extends Message>> inFault = new ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>();
    private List<Handler> handlers = new ModCountCopyOnWriteArrayList<Handler>();
    private EndpointContext endpointContext;
    private boolean publishable = true;

    public EndpointImpl(Object implementor) {
        this(BusFactory.getThreadDefaultBus(), implementor);
    }

    public EndpointImpl(Bus b, Object implementor, JaxWsServerFactoryBean sf) {
        this.bus = b;
        this.serverFactory = sf;
        this.implementor = implementor;
    }

    public EndpointImpl(Bus b, Object i, String bindingUri, String wsdl) {
        this(b, i, bindingUri, wsdl, null);
    }

    public EndpointImpl(Bus b, Object i, String bindingUri, String wsdl, WebServiceFeature[] f) {
        this.bus = b;
        this.implementor = i;
        this.bindingUri = bindingUri;
        this.wsdlLocation = wsdl == null ? null : new String(wsdl);
        this.serverFactory = new JaxWsServerFactoryBean();
        if (f != null) {
            ((JaxWsServiceFactoryBean)this.serverFactory.getServiceFactory()).setWsFeatures(Arrays.asList(f));
        }
    }

    public EndpointImpl(Bus b, Object i, String bindingUri) {
        this(b, i, bindingUri, (String)null);
    }

    public EndpointImpl(Bus b, Object i, String bindingUri, WebServiceFeature[] features) {
        this(b, i, bindingUri, null, features);
    }

    public EndpointImpl(Bus bus, Object implementor) {
        this(bus, implementor, (String)null);
    }

    public void setBus(Bus b) {
        this.bus = b;
    }

    public Bus getBus() {
        return this.bus;
    }

    @Override
    public Binding getBinding() {
        return ((JaxWsEndpointImpl)this.getEndpoint()).getJaxwsBinding();
    }

    @Override
    public void setExecutor(Executor executor2) {
        this.executor = executor2;
    }

    @Override
    public Executor getExecutor() {
        return this.executor;
    }

    public Service getService() {
        return this.service;
    }

    public JaxWsServiceFactoryBean getServiceFactory() {
        return this.serviceFactory;
    }

    @Override
    public Object getImplementor() {
        return this.implementor;
    }

    public Class<?> getImplementorClass() {
        return this.implementorClass != null ? this.implementorClass : ClassHelper.getRealClass(this.bus, this.implementor);
    }

    @Override
    public List<Source> getMetadata() {
        return this.metadata;
    }

    @Override
    public Map<String, Object> getProperties() {
        if (this.server != null) {
            return this.server.getEndpoint();
        }
        if (this.properties == null) {
            this.properties = new HashMap<String, Object>();
        }
        return this.properties;
    }

    @Override
    public boolean isPublished() {
        return this.server != null;
    }

    @Override
    public void publish(Object arg0) {
        this.checkPublishPermission();
    }

    @Override
    public void publish(String addr) {
        this.doPublish(addr);
    }

    public void setServiceFactory(JaxWsServiceFactoryBean sf) {
        this.serviceFactory = sf;
    }

    @Override
    public void setMetadata(List<Source> metadata) {
        this.checkPublishable();
        this.metadata = metadata;
    }

    @Override
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
        if (this.server != null) {
            this.server.getEndpoint().putAll(properties);
        }
    }

    @Override
    public void stop() {
        if (null != this.server) {
            this.server.destroy();
            this.server = null;
        }
    }

    @Override
    public String getBeanName() {
        return this.endpointName.toString() + ".jaxws-endpoint";
    }

    public JaxWsServerFactoryBean getServerFactory() {
        return this.serverFactory;
    }

    protected void setServerFactory(JaxWsServerFactoryBean bean) {
        this.serverFactory = bean;
    }

    protected void checkProperties() {
        if (this.properties != null) {
            if (this.properties.containsKey("javax.xml.ws.wsdl.description")) {
                this.wsdlLocation = this.properties.get("javax.xml.ws.wsdl.description").toString();
            }
            if (this.properties.containsKey("javax.xml.ws.wsdl.port")) {
                this.endpointName = (QName)this.properties.get("javax.xml.ws.wsdl.port");
            }
            if (this.properties.containsKey("javax.xml.ws.wsdl.service")) {
                this.serviceName = (QName)this.properties.get("javax.xml.ws.wsdl.service");
            }
        }
    }

    protected void doPublish(String addr) {
        this.checkPublishPermission();
        this.checkPublishable();
        ServerImpl serv = null;
        ClassLoaderUtils.ClassLoaderHolder loader = null;
        try {
            ClassLoader newLoader;
            if (this.bus != null && (newLoader = this.bus.getExtension(ClassLoader.class)) != null) {
                loader = ClassLoaderUtils.setThreadContextClassloader(newLoader);
            }
            serv = this.getServer(addr);
            if (addr != null) {
                EndpointInfo endpointInfo = serv.getEndpoint().getEndpointInfo();
                if (endpointInfo.getAddress() == null || !endpointInfo.getAddress().contains(addr)) {
                    endpointInfo.setAddress(addr);
                }
                if (this.publishedEndpointUrl != null) {
                    endpointInfo.setProperty("publishedEndpointUrl", this.publishedEndpointUrl);
                }
                if (this.publishedEndpointUrl != null && this.wsdlLocation != null) {
                    Definition def = endpointInfo.getService().getProperty(WSDLServiceBuilder.WSDL_DEFINITION, Definition.class);
                    if (def == null) {
                        def = this.bus.getExtension(WSDLManager.class).getDefinition(this.wsdlLocation);
                    }
                    new WSDLGetUtils().updateWSDLPublishedEndpointAddress(def, endpointInfo);
                }
                if (null != this.properties) {
                    for (Map.Entry<String, Object> entry : this.properties.entrySet()) {
                        endpointInfo.setProperty(entry.getKey(), entry.getValue());
                    }
                }
                this.address = endpointInfo.getAddress();
            }
            serv.start();
            this.publishable = false;
        }
        catch (Exception ex) {
            try {
                this.stop();
            }
            catch (Exception exception) {
                // empty catch block
            }
            throw new WebServiceException(ex);
        }
        finally {
            if (loader != null) {
                loader.reset();
            }
        }
    }

    public ServerImpl getServer() {
        return this.getServer(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized ServerImpl getServer(String addr) {
        if (this.server == null) {
            this.checkProperties();
            ClassLoaderUtils.ClassLoaderHolder loader = null;
            try {
                ClassLoader newLoader;
                if (this.bus != null && (newLoader = this.bus.getExtension(ClassLoader.class)) != null) {
                    loader = ClassLoaderUtils.setThreadContextClassloader(newLoader);
                }
                QName origEpn = this.endpointName;
                if (this.endpointName == null) {
                    JaxWsImplementorInfo implInfo = new JaxWsImplementorInfo(this.getImplementorClass());
                    this.endpointName = implInfo.getEndpointName();
                }
                if (this.serviceFactory != null) {
                    this.serverFactory.setServiceFactory(this.serviceFactory);
                }
                this.configureObject(this);
                this.endpointName = origEpn;
                this.serverFactory.setAddress(addr);
                this.serverFactory.setStart(false);
                this.serverFactory.setEndpointName(this.endpointName);
                this.serverFactory.setServiceBean(this.implementor);
                this.serverFactory.setBus(this.bus);
                this.serverFactory.setFeatures(this.getFeatures());
                this.serverFactory.setInvoker(this.invoker);
                this.serverFactory.setSchemaLocations(this.schemaLocations);
                if (this.serverFactory.getProperties() != null) {
                    this.serverFactory.getProperties().putAll(this.properties);
                } else {
                    this.serverFactory.setProperties(this.properties);
                }
                if (this.getWsdlLocation() != null) {
                    this.serverFactory.setWsdlURL(this.getWsdlLocation());
                }
                if (this.bindingUri != null) {
                    this.serverFactory.setBindingId(this.bindingUri);
                }
                if (this.serviceName != null) {
                    this.serverFactory.getServiceFactory().setServiceName(this.serviceName);
                }
                if (this.implementorClass != null) {
                    this.serverFactory.setServiceClass(this.implementorClass);
                }
                if (this.executor != null) {
                    this.serverFactory.getServiceFactory().setExecutor(this.executor);
                }
                if (!this.handlers.isEmpty()) {
                    this.serverFactory.addHandlers(this.handlers);
                }
                this.configureObject(this.serverFactory);
                this.server = this.serverFactory.create();
                Endpoint endpoint = this.getEndpoint();
                if (this.in != null) {
                    endpoint.getInInterceptors().addAll(this.in);
                }
                if (this.out != null) {
                    endpoint.getOutInterceptors().addAll(this.out);
                }
                if (this.inFault != null) {
                    endpoint.getInFaultInterceptors().addAll(this.inFault);
                }
                if (this.outFault != null) {
                    endpoint.getOutFaultInterceptors().addAll(this.outFault);
                }
                if (this.properties != null) {
                    endpoint.putAll(this.properties);
                }
                this.configureObject(endpoint.getService());
                this.configureObject(endpoint);
                this.service = endpoint.getService();
                if (this.getWsdlLocation() == null) {
                    this.setWsdlLocation(this.serverFactory.getWsdlURL());
                }
                if (this.serviceName == null) {
                    this.setServiceName(this.serverFactory.getServiceFactory().getServiceQName());
                }
                if (this.endpointName == null) {
                    this.endpointName = endpoint.getEndpointInfo().getName();
                }
            }
            finally {
                if (loader != null) {
                    loader.reset();
                }
            }
        }
        return (ServerImpl)this.server;
    }

    Endpoint getEndpoint() {
        return this.getServer(null).getEndpoint();
    }

    private void configureObject(Object instance) {
        Configurer configurer = this.bus.getExtension(Configurer.class);
        if (null != configurer) {
            configurer.configureBean(instance);
        }
    }

    protected void checkPublishPermission() {
        SecurityManager sm = System.getSecurityManager();
        boolean checkPublishEndpointPermissionWithSecurityManager = Boolean.parseBoolean(SystemPropertyAction.getProperty(CHECK_PUBLISH_ENDPOINT_PERMISSON_PROPERTY_WITH_SECURITY_MANAGER, "true"));
        if (checkPublishEndpointPermissionWithSecurityManager && sm != null) {
            sm.checkPermission(PUBLISH_PERMISSION);
        } else if (Boolean.getBoolean(CHECK_PUBLISH_ENDPOINT_PERMISSON_PROPERTY)) {
            AccessController.checkPermission(PUBLISH_PERMISSION);
        }
    }

    protected void checkPublishable() {
        if (!this.publishable) {
            throw new IllegalStateException("Cannot invoke method after endpoint has been published.");
        }
    }

    public void publish() {
        this.publish(this.getAddress());
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPublishedEndpointUrl() {
        return this.publishedEndpointUrl;
    }

    public void setPublishedEndpointUrl(String publishedEndpointUrl) {
        this.publishedEndpointUrl = publishedEndpointUrl;
    }

    public QName getEndpointName() {
        return this.endpointName;
    }

    public void setEndpointName(QName endpointName) {
        this.endpointName = endpointName;
    }

    public QName getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(QName serviceName) {
        this.serviceName = serviceName;
    }

    public String getWsdlLocation() {
        return this.wsdlLocation;
    }

    public void setWsdlLocation(String wsdlLocation) {
        this.wsdlLocation = wsdlLocation != null ? new String(wsdlLocation) : null;
    }

    public void setBindingUri(String binding) {
        this.bindingUri = binding;
    }

    public String getBindingUri() {
        return this.bindingUri;
    }

    public void setDataBinding(DataBinding dataBinding) {
        this.serverFactory.setDataBinding(dataBinding);
    }

    public DataBinding getDataBinding() {
        return this.serverFactory.getDataBinding();
    }

    @Override
    public List<Interceptor<? extends Message>> getOutFaultInterceptors() {
        if (this.server == null) {
            return this.outFault;
        }
        return new DoubleAddInterceptorList(this.outFault, this.server.getEndpoint().getOutFaultInterceptors());
    }

    @Override
    public List<Interceptor<? extends Message>> getInFaultInterceptors() {
        if (this.server == null) {
            return this.inFault;
        }
        return new DoubleAddInterceptorList(this.inFault, this.server.getEndpoint().getInFaultInterceptors());
    }

    @Override
    public List<Interceptor<? extends Message>> getInInterceptors() {
        if (this.server == null) {
            return this.in;
        }
        return new DoubleAddInterceptorList(this.in, this.server.getEndpoint().getInInterceptors());
    }

    @Override
    public List<Interceptor<? extends Message>> getOutInterceptors() {
        if (this.server == null) {
            return this.out;
        }
        return new DoubleAddInterceptorList(this.out, this.server.getEndpoint().getOutInterceptors());
    }

    public void setInInterceptors(List<Interceptor<? extends Message>> interceptors) {
        this.in = interceptors;
    }

    public void setInFaultInterceptors(List<Interceptor<? extends Message>> interceptors) {
        this.inFault = interceptors;
    }

    public void setOutInterceptors(List<Interceptor<? extends Message>> interceptors) {
        this.out = interceptors;
    }

    public void setOutFaultInterceptors(List<Interceptor<? extends Message>> interceptors) {
        this.outFault = interceptors;
    }

    public void setHandlers(List<Handler> h2) {
        this.handlers.clear();
        this.handlers.addAll(h2);
    }

    public List<Handler> getHandlers() {
        return this.handlers;
    }

    public List<Feature> getFeatures() {
        if (this.features == null) {
            this.features = new ArrayList<Feature>();
        }
        return this.features;
    }

    public void setFeatures(List<? extends Feature> features) {
        this.features = CastUtils.cast(features);
    }

    public Invoker getInvoker() {
        return this.invoker;
    }

    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    public void setImplementorClass(Class<?> implementorClass) {
        this.implementorClass = implementorClass;
    }

    public void setTransportId(String transportId) {
        this.serverFactory.setTransportId(transportId);
    }

    public String getTransportId() {
        return this.serverFactory.getTransportId();
    }

    public void setBindingConfig(BindingConfiguration config) {
        this.serverFactory.setBindingConfig(config);
    }

    public BindingConfiguration getBindingConfig() {
        return this.serverFactory.getBindingConfig();
    }

    public List<String> getSchemaLocations() {
        return this.schemaLocations;
    }

    public void setSchemaLocations(List<String> schemaLocations) {
        this.schemaLocations = schemaLocations;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public EndpointReference getEndpointReference(Element ... referenceParameters) {
        if (!this.isPublished()) {
            throw new WebServiceException(new org.apache.cxf.common.i18n.Message("ENDPOINT_NOT_PUBLISHED", LOG, new Object[0]).toString());
        }
        if (this.getBinding() instanceof HTTPBinding) {
            throw new UnsupportedOperationException(new org.apache.cxf.common.i18n.Message("GET_ENDPOINTREFERENCE_UNSUPPORTED_BINDING", LOG, new Object[0]).toString());
        }
        W3CEndpointReferenceBuilder builder = new W3CEndpointReferenceBuilder();
        builder.address(this.address);
        builder.serviceName(this.serviceName);
        builder.endpointName(this.endpointName);
        if (referenceParameters != null) {
            for (Element referenceParameter : referenceParameters) {
                builder.referenceParameter(referenceParameter);
            }
        }
        builder.wsdlDocumentLocation(this.wsdlLocation);
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

    @Override
    public <T extends EndpointReference> T getEndpointReference(Class<T> clazz, Element ... referenceParameters) {
        if (W3CEndpointReference.class.isAssignableFrom(clazz)) {
            return (T)((EndpointReference)clazz.cast(this.getEndpointReference(referenceParameters)));
        }
        throw new WebServiceException(new org.apache.cxf.common.i18n.Message("ENDPOINTREFERENCE_TYPE_NOT_SUPPORTED", LOG, clazz.getName()).toString());
    }

    @Override
    public void setEndpointContext(EndpointContext ctxt) {
        this.endpointContext = ctxt;
    }

    public EndpointContext getEndpointContext() {
        return this.endpointContext;
    }

    @Override
    public void publish(HttpContext context) {
        JaxWsServerFactoryBean sf = this.getServerFactory();
        if (sf.getDestinationFactory() == null) {
            sf.setDestinationFactory(new JAXWSHttpSpiTransportFactory(context));
        }
        this.publish(context.getPath());
    }

    @Override
    public void close() throws Exception {
        this.stop();
    }

    class DoubleAddInterceptorList
    implements List<Interceptor<? extends Message>> {
        List<Interceptor<? extends Message>> orig;
        List<Interceptor<? extends Message>> other;

        DoubleAddInterceptorList(List<Interceptor<? extends Message>> a1, List<Interceptor<? extends Message>> a2) {
            this.orig = a1;
            this.other = a2;
        }

        @Override
        public boolean add(Interceptor<? extends Message> e) {
            this.other.add(e);
            return this.orig.add(e);
        }

        @Override
        public void add(int index, Interceptor<? extends Message> element) {
            this.other.add(element);
            this.orig.add(index, element);
        }

        @Override
        public boolean addAll(Collection<? extends Interceptor<? extends Message>> c) {
            this.other.addAll(c);
            return this.orig.addAll(c);
        }

        @Override
        public boolean addAll(int index, Collection<? extends Interceptor<? extends Message>> c) {
            this.other.addAll(c);
            return this.orig.addAll(index, c);
        }

        @Override
        public void clear() {
            this.orig.clear();
        }

        @Override
        public boolean contains(Object o) {
            return this.orig.contains(o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return this.orig.containsAll(c);
        }

        @Override
        public Interceptor<? extends Message> get(int index) {
            return this.orig.get(index);
        }

        @Override
        public int indexOf(Object o) {
            return this.orig.indexOf(o);
        }

        @Override
        public boolean isEmpty() {
            return this.orig.isEmpty();
        }

        @Override
        public Iterator<Interceptor<? extends Message>> iterator() {
            return this.orig.iterator();
        }

        @Override
        public int lastIndexOf(Object o) {
            return this.orig.lastIndexOf(o);
        }

        @Override
        public ListIterator<Interceptor<? extends Message>> listIterator() {
            return this.orig.listIterator();
        }

        @Override
        public ListIterator<Interceptor<? extends Message>> listIterator(int index) {
            return this.orig.listIterator(index);
        }

        @Override
        public boolean remove(Object o) {
            this.other.remove(o);
            return this.orig.remove(o);
        }

        @Override
        public Interceptor<? extends Message> remove(int index) {
            Interceptor<? extends Message> o = this.orig.remove(index);
            this.other.remove(o);
            return o;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            this.other.removeAll(c);
            return this.orig.removeAll(c);
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Interceptor<? extends Message> set(int index, Interceptor<? extends Message> element) {
            Interceptor<? extends Message> o = this.orig.set(index, element);
            if (o != null) {
                int idx = this.other.indexOf(o);
                this.other.set(idx, element);
            }
            return o;
        }

        @Override
        public int size() {
            return this.orig.size();
        }

        @Override
        public List<Interceptor<? extends Message>> subList(int fromIndex, int toIndex) {
            return this.orig.subList(fromIndex, toIndex);
        }

        @Override
        public Object[] toArray() {
            return this.orig.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return this.orig.toArray(a);
        }
    }
}

