/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.server.config;

import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppConfiguration;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyWebSocketConfiguration
extends AbstractConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(JettyWebSocketConfiguration.class);

    public JettyWebSocketConfiguration() {
        this.addDependencies(WebXmlConfiguration.class, MetaInfConfiguration.class, WebInfConfiguration.class, FragmentConfiguration.class);
        this.addDependents("org.eclipse.jetty.annotations.AnnotationConfiguration", WebAppConfiguration.class.getName());
        this.protectAndExpose("org.eclipse.jetty.websocket.api.");
        this.protectAndExpose("org.eclipse.jetty.websocket.server.");
        this.protectAndExpose("org.eclipse.jetty.websocket.servlet.");
        this.hide("org.eclipse.jetty.server.internal.");
        this.hide("org.eclipse.jetty.server.config.");
    }
}

