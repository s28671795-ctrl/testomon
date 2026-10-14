/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.Bundle
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.Version
 *  org.osgi.service.blueprint.container.BlueprintContainer
 */
package org.apache.cxf.wsn.services;

import java.net.URL;
import java.util.Dictionary;
import java.util.HashMap;
import javax.management.MBeanServer;
import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.blueprint.BlueprintBus;
import org.apache.cxf.common.util.CollectionUtils;
import org.apache.cxf.wsn.EndpointRegistrationException;
import org.apache.cxf.wsn.services.JaxwsEndpointManager;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Version;
import org.osgi.service.blueprint.container.BlueprintContainer;

public class OSGiJaxwsEndpointManager
extends JaxwsEndpointManager {
    private BundleContext bundleContext;
    private BlueprintContainer container;
    private Object cxfBus;
    private boolean hasCXF = true;

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
        this.mbeanServer = (MBeanServer)bundleContext.getService(bundleContext.getServiceReference(MBeanServer.class.getName()));
    }

    public void setBlueprintContainer(BlueprintContainer c) {
        this.container = c;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Endpoint register(String address, Object service2, URL wsdlLocation) throws EndpointRegistrationException {
        Object o = this.setCXFBus();
        try {
            Endpoint endpoint = super.register(address, service2, wsdlLocation);
            return endpoint;
        }
        finally {
            this.restoreCXFBus(o);
        }
    }

    private void restoreCXFBus(Object o) {
        if (this.hasCXF) {
            this.restoreCXFBusInternal(o);
        }
    }

    private Object setCXFBus() {
        if (this.cxfBus == null && this.hasCXF) {
            try {
                this.createCXFBus();
            }
            catch (Throwable t2) {
                this.hasCXF = false;
            }
        }
        if (this.hasCXF) {
            return this.setCXFBusInternal();
        }
        return null;
    }

    public void destroy() {
        if (this.cxfBus != null) {
            this.destroyBus();
        }
    }

    private void destroyBus() {
        ((Bus)this.cxfBus).shutdown(true);
        this.cxfBus = null;
    }

    private void restoreCXFBusInternal(Object o) {
        BusFactory.setThreadDefaultBus((Bus)o);
    }

    private Object setCXFBusInternal() {
        return BusFactory.getAndSetThreadDefaultBus((Bus)this.cxfBus);
    }

    private void createCXFBus() {
        BlueprintBus bp = new BlueprintBus();
        bp.setBundleContext(this.bundleContext);
        bp.setBlueprintContainer(this.container);
        bp.setId("WS-Notification");
        bp.initialize();
        if (null != this.bundleContext) {
            HashMap<String, String> props = new HashMap<String, String>();
            props.put("cxf.context.symbolicname", this.bundleContext.getBundle().getSymbolicName());
            props.put("cxf.context.version", (String)this.getBundleVersion(this.bundleContext.getBundle()));
            props.put("cxf.bus.id", bp.getId());
            this.bundleContext.registerService(Bus.class.getName(), (Object)bp, CollectionUtils.toDictionary(props));
        }
        this.cxfBus = bp;
    }

    private Version getBundleVersion(Bundle bundle2) {
        Dictionary headers = bundle2.getHeaders();
        String version = (String)headers.get("Bundle-Version");
        return version != null ? Version.parseVersion((String)version) : Version.emptyVersion;
    }
}

