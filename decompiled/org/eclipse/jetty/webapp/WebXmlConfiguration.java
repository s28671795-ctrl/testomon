/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.io.IOException;
import java.net.MalformedURLException;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.DefaultsDescriptor;
import org.eclipse.jetty.webapp.OverrideDescriptor;
import org.eclipse.jetty.webapp.StandardDescriptorProcessor;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebDescriptor;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebXmlConfiguration
extends AbstractConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(WebXmlConfiguration.class);

    public WebXmlConfiguration() {
        this.addDependencies(WebInfConfiguration.class);
    }

    @Override
    public void preConfigure(WebAppContext context) throws Exception {
        Resource webxml;
        String defaultsDescriptor = context.getDefaultsDescriptor();
        if (defaultsDescriptor != null && defaultsDescriptor.length() > 0) {
            Resource dftResource = Resource.newSystemResource(defaultsDescriptor);
            if (dftResource == null) {
                String pkg = WebXmlConfiguration.class.getPackageName().replace(".", "/") + "/";
                if (defaultsDescriptor.startsWith(pkg)) {
                    dftResource = Resource.newResource(WebXmlConfiguration.class.getResource(defaultsDescriptor.substring(pkg.length())));
                }
                if (dftResource == null) {
                    dftResource = context.newResource(defaultsDescriptor);
                }
            }
            context.getMetaData().setDefaultsDescriptor(new DefaultsDescriptor(dftResource));
        }
        if ((webxml = this.findWebXml(context)) != null) {
            context.getMetaData().setWebDescriptor(new WebDescriptor(webxml));
            context.getServletContext().setEffectiveMajorVersion(context.getMetaData().getWebDescriptor().getMajorVersion());
            context.getServletContext().setEffectiveMinorVersion(context.getMetaData().getWebDescriptor().getMinorVersion());
        }
        for (String overrideDescriptor : context.getOverrideDescriptors()) {
            if (overrideDescriptor == null || overrideDescriptor.length() <= 0) continue;
            Resource orideResource = Resource.newSystemResource(overrideDescriptor);
            if (orideResource == null) {
                orideResource = context.newResource(overrideDescriptor);
            }
            context.getMetaData().addOverrideDescriptor(new OverrideDescriptor(orideResource));
        }
    }

    @Override
    public void configure(WebAppContext context) throws Exception {
        context.getMetaData().addDescriptorProcessor(new StandardDescriptorProcessor());
    }

    protected Resource findWebXml(WebAppContext context) throws IOException, MalformedURLException {
        Resource web;
        String descriptor = context.getDescriptor();
        if (descriptor != null && (web = context.newResource(descriptor)).exists() && !web.isDirectory()) {
            return web;
        }
        Resource webInf = context.getWebInf();
        if (webInf != null && webInf.isDirectory()) {
            Resource web2 = webInf.addPath("web.xml");
            if (web2.exists()) {
                return web2;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("No WEB-INF/web.xml in {}. Serving files and default/dynamic servlets only", (Object)context.getWar());
            }
        }
        return null;
    }

    @Override
    public void deconfigure(WebAppContext context) throws Exception {
        context.setWelcomeFiles(null);
        if (context.getErrorHandler() instanceof ErrorPageErrorHandler) {
            ((ErrorPageErrorHandler)context.getErrorHandler()).setErrorPages(null);
        }
    }
}

