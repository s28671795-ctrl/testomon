/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.io.IOException;
import java.util.Map;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyWebXmlConfiguration
extends AbstractConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(JettyWebXmlConfiguration.class);
    public static final String PROPERTY_WEB_INF_URI = "web-inf.uri";
    public static final String PROPERTY_WEB_INF = "web-inf";
    public static final String XML_CONFIGURATION = "org.eclipse.jetty.webapp.JettyWebXmlConfiguration";
    public static final String JETTY_WEB_XML = "jetty-web.xml";

    public JettyWebXmlConfiguration() {
        this.addDependencies(WebXmlConfiguration.class, FragmentConfiguration.class, MetaInfConfiguration.class);
    }

    @Override
    public void configure(WebAppContext context) throws Exception {
        Resource webInf;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Configuring web-jetty.xml");
        }
        if ((webInf = context.getWebInf()) != null && webInf.isDirectory()) {
            Resource jetty = webInf.addPath("jetty8-web.xml");
            if (!jetty.exists()) {
                jetty = webInf.addPath(JETTY_WEB_XML);
            }
            if (!jetty.exists()) {
                jetty = webInf.addPath("web-jetty.xml");
            }
            if (jetty.exists()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Configure: {}", (Object)jetty);
                }
                Object xmlAttr = context.getAttribute(XML_CONFIGURATION);
                context.removeAttribute(XML_CONFIGURATION);
                XmlConfiguration jetty_config = xmlAttr instanceof XmlConfiguration ? (XmlConfiguration)xmlAttr : new XmlConfiguration(jetty);
                this.setupXmlConfiguration(context, jetty_config, webInf);
                try {
                    WebAppClassLoader.runWithServerClassAccess(() -> {
                        jetty_config.configure(context);
                        return null;
                    });
                }
                catch (Exception e) {
                    LOG.warn("Error applying {}", (Object)jetty);
                    throw e;
                }
            }
        }
    }

    private void setupXmlConfiguration(WebAppContext context, XmlConfiguration jettyConfig, Resource webInf) throws IOException {
        jettyConfig.setJettyStandardIdsAndProperties(context.getServer(), null);
        Map<String, String> props = jettyConfig.getProperties();
        props.put(PROPERTY_WEB_INF_URI, XmlConfiguration.normalizeURI(webInf.getURI().toString()));
        props.put(PROPERTY_WEB_INF, webInf.toString());
    }
}

