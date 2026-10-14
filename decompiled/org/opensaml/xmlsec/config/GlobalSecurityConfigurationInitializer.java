/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.config;

import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.Initializer;
import org.opensaml.xmlsec.DecryptionConfiguration;
import org.opensaml.xmlsec.EncryptionConfiguration;
import org.opensaml.xmlsec.SignatureSigningConfiguration;
import org.opensaml.xmlsec.SignatureValidationConfiguration;
import org.opensaml.xmlsec.config.DefaultSecurityConfigurationBootstrap;

public class GlobalSecurityConfigurationInitializer
implements Initializer {
    @Override
    public void init() throws InitializationException {
        ConfigurationService.register(EncryptionConfiguration.class, DefaultSecurityConfigurationBootstrap.buildDefaultEncryptionConfiguration());
        ConfigurationService.register(DecryptionConfiguration.class, DefaultSecurityConfigurationBootstrap.buildDefaultDecryptionConfiguration());
        ConfigurationService.register(SignatureSigningConfiguration.class, DefaultSecurityConfigurationBootstrap.buildDefaultSignatureSigningConfiguration());
        ConfigurationService.register(SignatureValidationConfiguration.class, DefaultSecurityConfigurationBootstrap.buildDefaultSignatureValidationConfiguration());
    }
}

