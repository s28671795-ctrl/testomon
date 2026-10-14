/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl11;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.wsdl.Binding;
import javax.wsdl.Definition;
import javax.wsdl.PortType;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.factory.WSDLFactory;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.ServiceImpl;
import org.apache.cxf.service.factory.AbstractServiceFactoryBean;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.wsdl.WSDLManager;
import org.apache.cxf.wsdl11.PartialWSDLProcessor;
import org.apache.cxf.wsdl11.WSDLServiceBuilder;
import org.apache.ws.commons.schema.XmlSchemaException;

public class WSDLServiceFactory
extends AbstractServiceFactoryBean {
    private static final Logger LOG = LogUtils.getL7dLogger(WSDLServiceFactory.class);
    protected final String wsdlUrl;
    private QName serviceName;
    private QName endpointName;
    private Definition definition;
    private boolean allowRefs;

    public WSDLServiceFactory(Bus b, Definition d) {
        this.setBus(b);
        this.definition = d;
        this.wsdlUrl = d.getDocumentBaseURI();
    }

    public WSDLServiceFactory(Bus b, Definition d, QName sn) {
        this(b, d);
        this.serviceName = sn;
    }

    public WSDLServiceFactory(Bus b, String url2) {
        this.setBus(b);
        this.wsdlUrl = url2;
        try {
            this.definition = this.getBus().getExtension(WSDLManager.class).getDefinition(url2);
        }
        catch (WSDLException ex) {
            throw new ServiceConstructionException(new Message("SERVICE_CREATION_MSG", LOG, new Object[0]), (Throwable)ex);
        }
    }

    public WSDLServiceFactory(Bus b, String url2, QName sn) {
        this.setBus(b);
        this.wsdlUrl = url2;
        try {
            this.definition = this.getBus().getExtension(WSDLManager.class).getDefinition(url2);
        }
        catch (WSDLException ex) {
            throw new ServiceConstructionException(new Message("SERVICE_CREATION_MSG", LOG, new Object[0]), (Throwable)ex);
        }
        this.serviceName = sn;
    }

    public void setAllowElementRefs(boolean b) {
        this.allowRefs = b;
    }

    public void setEndpointName(QName qn) {
        this.endpointName = qn;
    }

    public Definition getDefinition() {
        return this.definition;
    }

    @Override
    public Service create() {
        List<ServiceInfo> services;
        if (this.serviceName == null) {
            try {
                WSDLServiceBuilder builder = new WSDLServiceBuilder(this.getBus());
                builder.setAllowElementRefs(this.allowRefs);
                services = builder.buildServices(this.definition);
            }
            catch (XmlSchemaException ex) {
                throw new ServiceConstructionException(new Message("SERVICE_CREATION_MSG", LOG, new Object[0]), (Throwable)ex);
            }
            if (services.isEmpty()) {
                throw new ServiceConstructionException(new Message("NO_SERVICE_EXC", LOG, new Object[0]));
            }
            this.serviceName = services.get(0).getName();
            Iterator<ServiceInfo> it = services.iterator();
            while (it.hasNext()) {
                if (it.next().getName().equals(this.serviceName)) continue;
                it.remove();
            }
        } else {
            javax.wsdl.Service wsdlService = this.definition.getService(this.serviceName);
            if (wsdlService == null) {
                if (!PartialWSDLProcessor.isServiceExisted(this.definition, this.serviceName) && !PartialWSDLProcessor.isBindingExisted(this.definition, this.serviceName) && PartialWSDLProcessor.isPortTypeExisted(this.definition, this.serviceName)) {
                    try {
                        Map portTypes = CastUtils.cast(this.definition.getAllPortTypes());
                        String existPortName = null;
                        PortType portType = null;
                        for (Map.Entry entry : portTypes.entrySet()) {
                            existPortName = ((QName)entry.getKey()).getLocalPart();
                            if (!this.serviceName.getLocalPart().contains(existPortName)) continue;
                            portType = (PortType)entry.getValue();
                            break;
                        }
                        WSDLFactory factory2 = WSDLFactory.newInstance();
                        ExtensionRegistry extReg = factory2.newPopulatedExtensionRegistry();
                        Binding binding = PartialWSDLProcessor.doAppendBinding(this.definition, existPortName, portType, extReg);
                        this.definition.addBinding(binding);
                        wsdlService = PartialWSDLProcessor.doAppendService(this.definition, existPortName, extReg, binding);
                        this.definition.addService(wsdlService);
                    }
                    catch (Exception e) {
                        throw new ServiceConstructionException(new Message("NO_SUCH_SERVICE_EXC", LOG, this.serviceName));
                    }
                } else {
                    throw new ServiceConstructionException(new Message("NO_SUCH_SERVICE_EXC", LOG, this.serviceName));
                }
            }
            try {
                services = new WSDLServiceBuilder(this.getBus()).buildServices(this.definition, wsdlService, this.endpointName);
                if (services.isEmpty()) {
                    throw new ServiceConstructionException(new Message("NO_SUCH_ENDPOINT_EXC", LOG, this.endpointName));
                }
            }
            catch (XmlSchemaException ex) {
                throw new ServiceConstructionException(new Message("SERVICE_CREATION_MSG", LOG, new Object[0]), (Throwable)ex);
            }
        }
        ServiceImpl service2 = new ServiceImpl(services);
        this.setService(service2);
        return service2;
    }
}

