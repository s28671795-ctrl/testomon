/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.Servlet
 *  org.osgi.framework.ServiceRegistration
 *  org.osgi.service.cm.ConfigurationException
 *  org.osgi.service.cm.ManagedService
 *  org.osgi.service.http.HttpContext
 *  org.osgi.service.http.HttpService
 */
package org.apache.cxf.transport.http.osgi;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Servlet;
import org.apache.cxf.common.logging.LogUtils;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;

class ServletExporter
implements ManagedService {
    protected static final Logger LOG = LogUtils.getL7dLogger(ServletExporter.class);
    private static final String CXF_SERVLET_PREFIX = "org.apache.cxf.servlet.";
    private String alias;
    private Servlet servlet;
    private ServiceRegistration<?> serviceRegistration;
    private HttpService httpService;

    ServletExporter(Servlet servlet, HttpService httpService) {
        this.servlet = servlet;
        this.httpService = httpService;
    }

    public void updated(Dictionary properties) throws ConfigurationException {
        if (this.alias != null) {
            try {
                LOG.log(Level.INFO, "Unregistering previous instance of \"" + this.alias + "\" servlet");
                this.httpService.unregister(this.alias);
            }
            catch (IllegalArgumentException e) {
                if (e.getMessage() != null && e.getMessage().contains("was never registered")) {
                    LOG.log(Level.INFO, "CXF OSGi servlet was not unregistered: " + e.getMessage());
                }
                LOG.log(Level.SEVERE, e.getMessage(), e);
            }
            if (properties == null) {
                return;
            }
            this.alias = null;
        }
        if (properties == null) {
            properties = new Properties();
        }
        Properties sprops = new Properties();
        sprops.put("init-prefix", this.getProp(properties, "org.apache.cxf.servlet.init-prefix", ""));
        sprops.put("servlet-name", this.getProp(properties, "org.apache.cxf.servlet.name", "cxf-osgi-transport-servlet"));
        sprops.put("hide-service-list-page", this.getProp(properties, "org.apache.cxf.servlet.hide-service-list-page", "false"));
        sprops.put("disable-address-updates", this.getProp(properties, "org.apache.cxf.servlet.disable-address-updates", "true"));
        sprops.put("base-address", this.getProp(properties, "org.apache.cxf.servlet.base-address", ""));
        sprops.put("service-list-path", this.getProp(properties, "org.apache.cxf.servlet.service-list-path", ""));
        sprops.put("static-resources-list", this.getProp(properties, "org.apache.cxf.servlet.static-resources-list", ""));
        sprops.put("redirects-list", this.getProp(properties, "org.apache.cxf.servlet.redirects-list", ""));
        sprops.put("redirect-servlet-name", this.getProp(properties, "org.apache.cxf.servlet.redirect-servlet-name", ""));
        sprops.put("redirect-servlet-path", this.getProp(properties, "org.apache.cxf.servlet.redirect-servlet-path", ""));
        sprops.put("service-list-all-contexts", this.getProp(properties, "org.apache.cxf.servlet.service-list-all-contexts", ""));
        sprops.put("service-list-page-authenticate", this.getProp(properties, "org.apache.cxf.servlet.service-list-page-authenticate", "false"));
        sprops.put("service-list-page-authenticate-realm", this.getProp(properties, "org.apache.cxf.servlet.service-list-page-authenticate-realm", "karaf"));
        sprops.put("use-x-forwarded-headers", this.getProp(properties, "org.apache.cxf.servlet.use-x-forwarded-headers", "false"));
        sprops.put("async-supported", this.getProp(properties, "org.apache.cxf.servlet.async-supported", "true"));
        if (Boolean.valueOf(this.getProp(properties, "org.apache.cxf.servlet.support.extra.properties", "true").toString()).booleanValue()) {
            Enumeration keys2 = properties.keys();
            while (keys2.hasMoreElements()) {
                String nextKey = keys2.nextElement().toString();
                if (nextKey.startsWith(CXF_SERVLET_PREFIX)) continue;
                sprops.put(nextKey, properties.get(nextKey));
            }
        }
        if (this.serviceRegistration != null) {
            this.serviceRegistration.unregister();
        }
        this.alias = (String)this.getProp(properties, "org.apache.cxf.servlet.context", "/cxf");
        HttpContext context = this.httpService.createDefaultHttpContext();
        try {
            LOG.log(Level.INFO, "Registering new instance of \"" + this.alias + "\" servlet");
            this.httpService.registerServlet(this.alias, this.servlet, (Dictionary)sprops, context);
        }
        catch (Exception e) {
            LOG.log(Level.WARNING, "Error registering CXF OSGi servlet " + e.getMessage(), e);
        }
    }

    private Object getProp(Dictionary properties, String key, Object defaultValue) {
        Object value = properties.get(key);
        return value == null ? defaultValue : value;
    }
}

