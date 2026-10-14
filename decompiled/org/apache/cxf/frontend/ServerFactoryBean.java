/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.frontend;

import java.io.IOException;
import java.util.List;
import org.apache.cxf.BusException;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.util.ClassHelper;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.endpoint.ServerImpl;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.frontend.AbstractWSDLBasedEndpointFactory;
import org.apache.cxf.service.factory.FactoryBeanListener;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.apache.cxf.service.invoker.BeanInvoker;
import org.apache.cxf.service.invoker.FactoryInvoker;
import org.apache.cxf.service.invoker.Invoker;
import org.apache.cxf.service.invoker.SingletonFactory;
import org.apache.cxf.transport.ConduitInitiator;
import org.apache.cxf.transport.ConduitInitiatorManager;
import org.apache.cxf.transport.DestinationFactory;
import org.apache.cxf.transport.DestinationFactoryManager;
import org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean;
import org.apache.cxf.wsdl11.WSDLEndpointFactory;

public class ServerFactoryBean
extends AbstractWSDLBasedEndpointFactory {
    private boolean start = true;
    private Object serviceBean;
    private List<String> schemaLocations;
    private Invoker invoker;

    public ServerFactoryBean() {
        this(new ReflectionServiceFactoryBean());
    }

    public ServerFactoryBean(ReflectionServiceFactoryBean sbean) {
        super(sbean);
    }

    public String getBeanName() {
        return this.getClass().getName();
    }

    @Override
    protected String detectTransportIdFromAddress(String ad) {
        DestinationFactoryManager dfm;
        DestinationFactory df = this.getDestinationFactory();
        if (df == null && (df = (dfm = this.getBus().getExtension(DestinationFactoryManager.class)).getDestinationFactoryForUri(this.getAddress())) != null) {
            return df.getTransportIds().get(0);
        }
        return null;
    }

    @Override
    protected WSDLEndpointFactory getWSDLEndpointFactory() {
        if (this.destinationFactory == null) {
            try {
                this.destinationFactory = this.getBus().getExtension(DestinationFactoryManager.class).getDestinationFactory(this.transportId);
            }
            catch (Throwable t2) {
                try {
                    ConduitInitiator o = this.getBus().getExtension(ConduitInitiatorManager.class).getConduitInitiator(this.transportId);
                    if (o instanceof WSDLEndpointFactory) {
                        return (WSDLEndpointFactory)((Object)o);
                    }
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
        }
        if (this.destinationFactory instanceof WSDLEndpointFactory) {
            return (WSDLEndpointFactory)((Object)this.destinationFactory);
        }
        return null;
    }

    public Server getServer() {
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Server create() {
        ClassLoaderUtils.ClassLoaderHolder orig = null;
        try {
            ServerImpl server = null;
            try {
                ClassLoader loader;
                if (this.bus != null && (loader = this.bus.getExtension(ClassLoader.class)) != null) {
                    orig = ClassLoaderUtils.setThreadContextClassloader(loader);
                }
                if (this.getServiceFactory().getProperties() == null) {
                    this.getServiceFactory().setProperties(this.getProperties());
                } else if (this.getProperties() != null) {
                    this.getServiceFactory().getProperties().putAll(this.getProperties());
                }
                if (this.serviceBean != null && this.getServiceClass() == null) {
                    this.setServiceClass(ClassHelper.getRealClass(this.bus, this.serviceBean));
                }
                if (this.invoker != null) {
                    this.getServiceFactory().setInvoker(this.invoker);
                } else if (this.serviceBean != null) {
                    this.invoker = this.createInvoker();
                    this.getServiceFactory().setInvoker(this.invoker);
                }
                Endpoint ep = this.createEndpoint();
                Object[] objectArray = new Object[3];
                objectArray[0] = server;
                objectArray[1] = this.serviceBean;
                objectArray[2] = this.serviceBean == null ? (this.getServiceClass() == null ? this.getServiceFactory().getServiceClass() : this.getServiceClass()) : (this.getServiceClass() == null ? ClassHelper.getRealClass(this.getBus(), this.getServiceBean()) : this.getServiceClass());
                this.getServiceFactory().sendEvent(FactoryBeanListener.Event.PRE_SERVER_CREATE, objectArray);
                server = new ServerImpl(this.getBus(), ep, this.getDestinationFactory(), this.getBindingFactory());
                if (ep.getService().getInvoker() == null) {
                    if (this.invoker == null) {
                        ep.getService().setInvoker(this.createInvoker());
                    } else {
                        ep.getService().setInvoker(this.invoker);
                    }
                }
            }
            catch (IOException | BusException | EndpointException e) {
                throw new ServiceConstructionException(e);
            }
            if (this.serviceBean != null) {
                Class<?> cls = ClassHelper.getRealClass(this.getServiceBean());
                if (this.getServiceClass() == null || cls.equals(this.getServiceClass())) {
                    this.initializeAnnotationInterceptors(server.getEndpoint(), cls);
                } else {
                    this.initializeAnnotationInterceptors(server.getEndpoint(), cls, this.getServiceClass());
                }
            } else if (this.getServiceClass() != null) {
                this.initializeAnnotationInterceptors(server.getEndpoint(), this.getServiceClass());
            }
            this.applyFeatures(server);
            Object[] objectArray = new Object[3];
            objectArray[0] = server;
            objectArray[1] = this.serviceBean;
            objectArray[2] = this.serviceBean == null ? (this.getServiceClass() == null ? this.getServiceFactory().getServiceClass() : this.getServiceClass()) : (this.getServiceClass() == null ? ClassHelper.getRealClass(this.getServiceBean()) : this.getServiceClass());
            this.getServiceFactory().sendEvent(FactoryBeanListener.Event.SERVER_CREATED, objectArray);
            if (this.start) {
                try {
                    server.start();
                }
                catch (RuntimeException re) {
                    server.destroy();
                    throw re;
                }
            }
            this.getServiceFactory().reset();
            ServerImpl serverImpl = server;
            return serverImpl;
        }
        finally {
            if (orig != null) {
                orig.reset();
            }
        }
    }

    @Override
    protected void initializeServiceFactory() {
        super.initializeServiceFactory();
        this.getServiceFactory().setSchemaLocations(this.schemaLocations);
    }

    protected void applyFeatures(Server server) {
        if (this.getFeatures() != null) {
            for (Feature feature : this.getFeatures()) {
                feature.initialize(server, this.getBus());
            }
        }
    }

    protected Invoker createInvoker() {
        if (this.getServiceBean() == null) {
            return new FactoryInvoker(new SingletonFactory(this.getServiceClass()));
        }
        return new BeanInvoker(this.getServiceBean());
    }

    public boolean isStart() {
        return this.start;
    }

    public void setStart(boolean start2) {
        this.start = start2;
    }

    public Object getServiceBean() {
        return this.serviceBean;
    }

    public Class<?> getServiceBeanClass() {
        if (this.serviceBean != null) {
            return ClassHelper.getRealClass(this.getBus(), this.serviceBean);
        }
        return this.getServiceFactory().getServiceClass();
    }

    public void setServiceBean(Object serviceBean) {
        this.serviceBean = serviceBean;
    }

    public List<String> getSchemaLocations() {
        return this.schemaLocations;
    }

    public void setSchemaLocations(List<String> schemaLocations) {
        this.schemaLocations = schemaLocations;
    }

    public Invoker getInvoker() {
        return this.invoker;
    }

    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    public void setWsdlLocation(String location) {
        this.setWsdlURL(location);
    }

    public String getWsdlLocation() {
        return this.getWsdlURL();
    }
}

