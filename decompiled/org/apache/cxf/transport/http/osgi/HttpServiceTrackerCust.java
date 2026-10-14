/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Servlet
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.ServiceReference
 *  org.osgi.framework.ServiceRegistration
 *  org.osgi.service.cm.ConfigurationException
 *  org.osgi.service.cm.ManagedService
 *  org.osgi.service.http.HttpService
 *  org.osgi.util.tracker.ServiceTrackerCustomizer
 */
package org.apache.cxf.transport.http.osgi;

import javax.servlet.Servlet;
import org.apache.cxf.common.util.CollectionUtils;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.http.osgi.ServletExporter;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

final class HttpServiceTrackerCust
implements ServiceTrackerCustomizer<HttpService, HttpService> {
    private static final String CXF_CONFIG_PID = "org.apache.cxf.osgi";
    private final DestinationRegistry destinationRegistry;
    private final BundleContext context;
    private ServiceRegistration<ManagedService> servletPublisherReg;
    private ServletExporter servletExporter;

    HttpServiceTrackerCust(DestinationRegistry destinationRegistry, BundleContext context) {
        this.destinationRegistry = destinationRegistry;
        this.context = context;
    }

    public void removedService(ServiceReference<HttpService> reference, HttpService service2) {
        this.servletPublisherReg.unregister();
        try {
            this.servletExporter.updated(null);
        }
        catch (ConfigurationException configurationException) {
            // empty catch block
        }
    }

    public void modifiedService(ServiceReference<HttpService> reference, HttpService service2) {
    }

    public HttpService addingService(ServiceReference<HttpService> reference) {
        HttpService httpService = (HttpService)this.context.getService(reference);
        CXFNonSpringServlet servlet = new CXFNonSpringServlet(this.destinationRegistry, false);
        this.servletExporter = new ServletExporter((Servlet)servlet, httpService);
        this.servletPublisherReg = this.context.registerService(ManagedService.class, (Object)this.servletExporter, CollectionUtils.singletonDictionary("service.pid", CXF_CONFIG_PID));
        return httpService;
    }
}

