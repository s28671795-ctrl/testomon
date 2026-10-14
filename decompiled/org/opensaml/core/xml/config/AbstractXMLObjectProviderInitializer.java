/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.config;

import java.io.InputStream;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.Initializer;
import org.opensaml.core.xml.config.XMLConfigurationException;
import org.opensaml.core.xml.config.XMLConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractXMLObjectProviderInitializer
implements Initializer {
    private final Logger log = LoggerFactory.getLogger(AbstractXMLObjectProviderInitializer.class);

    @Override
    public void init() throws InitializationException {
        try {
            XMLConfigurator configurator2 = new XMLConfigurator();
            for (String resource : this.getConfigResources()) {
                if (resource.startsWith("/")) {
                    resource = resource.substring(1);
                }
                this.log.debug("Loading XMLObject provider configuration from resource '{}'", (Object)resource);
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
                if (is == null) {
                    throw new XMLConfigurationException("Resource not found");
                }
                configurator2.load(is);
            }
        }
        catch (XMLConfigurationException e) {
            this.log.error("Problem loading configuration resource", e);
            throw new InitializationException("Problem loading configuration resource", e);
        }
    }

    protected abstract String[] getConfigResources();
}

