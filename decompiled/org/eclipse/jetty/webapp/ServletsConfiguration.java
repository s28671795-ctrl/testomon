/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppConfiguration;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletsConfiguration
extends AbstractConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(ServletsConfiguration.class);

    public ServletsConfiguration() {
        this.addDependencies(WebXmlConfiguration.class, MetaInfConfiguration.class, WebInfConfiguration.class, WebAppConfiguration.class);
        this.addDependents(JettyWebXmlConfiguration.class);
        this.protectAndExpose(new String[0]);
        this.protect("org.eclipse.jetty.servlets.PushCacheFilter", "org.eclipse.jetty.servlets.PushSessionCacheFilter");
        this.expose("org.eclipse.jetty.servlets.");
    }

    @Override
    public boolean isAvailable() {
        try {
            return Loader.loadClass("org.eclipse.jetty.servlets.PushCacheFilter") != null;
        }
        catch (Throwable e) {
            LOG.trace("IGNORED", e);
            return false;
        }
    }
}

