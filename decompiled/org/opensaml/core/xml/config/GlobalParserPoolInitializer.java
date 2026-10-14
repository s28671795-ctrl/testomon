/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.config;

import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.xml.BasicParserPool;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.Initializer;
import org.opensaml.core.xml.config.XMLObjectProviderRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalParserPoolInitializer
implements Initializer {
    private Logger log = LoggerFactory.getLogger(GlobalParserPoolInitializer.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void init() throws InitializationException {
        BasicParserPool pp = new BasicParserPool();
        pp.setMaxPoolSize(50);
        try {
            pp.initialize();
        }
        catch (ComponentInitializationException e) {
            throw new InitializationException("Error initializing parser pool", e);
        }
        XMLObjectProviderRegistry registry = null;
        Class<ConfigurationService> clazz = ConfigurationService.class;
        synchronized (ConfigurationService.class) {
            registry = ConfigurationService.get(XMLObjectProviderRegistry.class);
            if (registry == null) {
                this.log.debug("XMLObjectProviderRegistry did not exist in ConfigurationService, will be created");
                registry = new XMLObjectProviderRegistry();
                ConfigurationService.register(XMLObjectProviderRegistry.class, registry);
            }
            // ** MonitorExit[var3_4] (shouldn't be in output)
            registry.setParserPool(pp);
            return;
        }
    }
}

