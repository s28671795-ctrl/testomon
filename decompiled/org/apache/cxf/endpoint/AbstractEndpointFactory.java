/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.BusException;
import org.apache.cxf.BusFactory;
import org.apache.cxf.binding.BindingConfiguration;
import org.apache.cxf.binding.BindingFactory;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.endpoint.ConduitSelector;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.AbstractBasicInterceptorProvider;
import org.apache.cxf.interceptor.AnnotationInterceptors;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.transport.DestinationFactory;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

public abstract class AbstractEndpointFactory
extends AbstractBasicInterceptorProvider {
    private static final Logger LOG = LogUtils.getL7dLogger(AbstractEndpointFactory.class);
    private static final String PRIVATE_ENDPOINT = "org.apache.cxf.endpoint.private";
    private static final String PRIVATE_ENDPOINTS = "org.apache.cxf.private.endpoints";
    protected Bus bus;
    protected String address;
    protected String transportId;
    protected String bindingId;
    protected DataBinding dataBinding;
    protected BindingFactory bindingFactory;
    protected DestinationFactory destinationFactory;
    protected String publishedEndpointUrl;
    protected QName endpointName;
    protected QName serviceName;
    protected Map<String, Object> properties;
    protected List<Feature> features;
    protected BindingConfiguration bindingConfig;
    protected EndpointReferenceType endpointReference;
    protected ConduitSelector conduitSelector;

    protected abstract Endpoint createEndpoint() throws BusException, EndpointException;

    protected abstract BindingInfo createBindingInfo();

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Bus getBus() {
        return this.getBus(true);
    }

    public Bus getBus(boolean createIfNeeded) {
        if (this.bus == null && createIfNeeded) {
            this.bus = BusFactory.getThreadDefaultBus();
        }
        return this.bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public String getTransportId() {
        return this.transportId;
    }

    public void setTransportId(String transportId) {
        this.transportId = transportId;
    }

    public void setBindingId(String bind) {
        this.bindingId = bind;
    }

    public String getBindingId() {
        return this.bindingId;
    }

    public void setBindingConfig(BindingConfiguration obj) {
        this.bindingConfig = obj;
    }

    public BindingConfiguration getBindingConfig() {
        return this.bindingConfig;
    }

    public DestinationFactory getDestinationFactory() {
        return this.destinationFactory;
    }

    public void setDestinationFactory(DestinationFactory destinationFactory) {
        this.destinationFactory = destinationFactory;
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

    public void setServiceName(QName name) {
        this.serviceName = name;
    }

    public QName getServiceName() {
        return this.serviceName;
    }

    public void setEndpointReference(EndpointReferenceType epr) {
        this.endpointReference = epr;
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public Map<String, Object> getProperties(boolean create) {
        if (create && this.properties == null) {
            this.properties = new HashMap<String, Object>();
        }
        return this.properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public List<Feature> getFeatures() {
        if (this.features == null) {
            this.features = new ArrayList<Feature>();
        }
        return this.features;
    }

    public void setFeatures(List<? extends Feature> features2) {
        this.features = CastUtils.cast(features2);
    }

    public BindingFactory getBindingFactory() {
        return this.bindingFactory;
    }

    public void setBindingFactory(BindingFactory bf) {
        this.bindingFactory = bf;
    }

    public ConduitSelector getConduitSelector() {
        return this.conduitSelector;
    }

    public void setConduitSelector(ConduitSelector selector) {
        this.conduitSelector = selector;
    }

    public DataBinding getDataBinding() {
        return this.dataBinding;
    }

    public void setDataBinding(DataBinding dataBinding) {
        this.dataBinding = dataBinding;
    }

    protected boolean checkPrivateEndpoint(Endpoint ep) {
        if (PropertyUtils.isTrue(ep.get(PRIVATE_ENDPOINT))) {
            LinkedList<String> addresses = (LinkedList<String>)this.getBus().getProperty(PRIVATE_ENDPOINTS);
            if (addresses == null) {
                addresses = new LinkedList<String>();
            }
            addresses.add(this.getAddress());
            this.bus.setProperty(PRIVATE_ENDPOINTS, addresses);
            return true;
        }
        return false;
    }

    protected void initializeAnnotationInterceptors(Endpoint ep, Class<?> cls) {
        this.initializeAnnotationInterceptors(ep, new Class[]{cls});
    }

    protected void initializeAnnotationInterceptors(Endpoint ep, Class<?> ... cls) {
        AnnotationInterceptors provider = new AnnotationInterceptors(cls);
        if (this.initializeAnnotationInterceptors(provider, ep)) {
            LOG.fine("Added annotation based interceptors and features");
        }
    }

    protected static void addToBeans(Collection<Object> beans, Object o) {
        if (o instanceof Collection) {
            for (Object o2 : (Collection)o) {
                AbstractEndpointFactory.addToBeans(beans, o2);
            }
        } else {
            beans.add(o);
        }
    }

    protected boolean initializeAnnotationInterceptors(AnnotationInterceptors provider, Endpoint ep) {
        boolean hasAnnotation = false;
        if (provider.getInFaultInterceptors() != null) {
            ep.getInFaultInterceptors().addAll(provider.getInFaultInterceptors());
            hasAnnotation = true;
        }
        if (provider.getInInterceptors() != null) {
            ep.getInInterceptors().addAll(provider.getInInterceptors());
            hasAnnotation = true;
        }
        if (provider.getOutFaultInterceptors() != null) {
            ep.getOutFaultInterceptors().addAll(provider.getOutFaultInterceptors());
            hasAnnotation = true;
        }
        if (provider.getOutInterceptors() != null) {
            ep.getOutInterceptors().addAll(provider.getOutInterceptors());
            hasAnnotation = true;
        }
        if (provider.getFeatures() != null) {
            this.getFeatures().addAll(provider.getFeatures());
            hasAnnotation = true;
        }
        return hasAnnotation;
    }
}

