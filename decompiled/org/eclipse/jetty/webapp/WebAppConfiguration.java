/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

public class WebAppConfiguration
extends AbstractConfiguration {
    public WebAppConfiguration() {
        this.addDependencies(WebXmlConfiguration.class, MetaInfConfiguration.class, WebInfConfiguration.class);
        this.addDependents(JettyWebXmlConfiguration.class);
        this.protectAndExpose("org.eclipse.jetty.servlet.StatisticsServlet", "org.eclipse.jetty.servlet.DefaultServlet", "org.eclipse.jetty.servlet.NoJspServlet");
        this.expose("org.eclipse.jetty.servlet.listener.");
    }
}

