/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.config;

import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.Initializer;
import org.opensaml.saml.config.SAMLConfiguration;
import org.opensaml.saml.saml1.binding.artifact.SAML1ArtifactBuilderFactory;
import org.opensaml.saml.saml2.binding.artifact.SAML2ArtifactBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLConfigurationInitializer
implements Initializer {
    private Logger log = LoggerFactory.getLogger(SAMLConfigurationInitializer.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void init() throws InitializationException {
        this.log.debug("Initializing SAML Artifact builder factories");
        SAMLConfiguration config = null;
        Class<ConfigurationService> clazz = ConfigurationService.class;
        synchronized (ConfigurationService.class) {
            config = ConfigurationService.get(SAMLConfiguration.class);
            if (config == null) {
                config = new SAMLConfiguration();
                ConfigurationService.register(SAMLConfiguration.class, config);
            }
            // ** MonitorExit[var2_2] (shouldn't be in output)
            config.setSAML1ArtifactBuilderFactory(new SAML1ArtifactBuilderFactory());
            config.setSAML2ArtifactBuilderFactory(new SAML2ArtifactBuilderFactory());
            return;
        }
    }
}

