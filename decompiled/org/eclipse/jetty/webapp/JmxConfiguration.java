/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmxConfiguration
extends AbstractConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(JmxConfiguration.class);

    public JmxConfiguration() {
        this.addDependents(WebXmlConfiguration.class, MetaInfConfiguration.class, WebInfConfiguration.class);
        this.protectAndExpose("org.eclipse.jetty.util.annotation", "org.eclipse.jetty.jmx.");
    }

    @Override
    public boolean isAvailable() {
        try {
            return Loader.loadClass("org.eclipse.jetty.jmx.ObjectMBean") != null;
        }
        catch (Throwable e) {
            LOG.trace("IGNORED", e);
            return false;
        }
    }
}

