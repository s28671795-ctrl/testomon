/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppConfiguration;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JspConfiguration
extends AbstractConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(JspConfiguration.class);

    public JspConfiguration() {
        this.addDependencies(WebXmlConfiguration.class, MetaInfConfiguration.class, WebInfConfiguration.class, FragmentConfiguration.class);
        this.addDependents(WebAppConfiguration.class);
        this.protectAndExpose("org.eclipse.jetty.jsp.");
        this.expose("org.eclipse.jetty.apache.");
        this.hide("org.eclipse.jdt.");
    }

    @Override
    public boolean isAvailable() {
        try {
            return Loader.loadClass("org.eclipse.jetty.jsp.JettyJspServlet") != null;
        }
        catch (Throwable e) {
            LOG.trace("IGNORED", e);
            return false;
        }
    }
}

