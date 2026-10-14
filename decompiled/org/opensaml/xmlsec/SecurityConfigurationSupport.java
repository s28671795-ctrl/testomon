/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec;

import org.opensaml.core.config.ConfigurationService;
import org.opensaml.xmlsec.DecryptionConfiguration;
import org.opensaml.xmlsec.EncryptionConfiguration;
import org.opensaml.xmlsec.SignatureSigningConfiguration;
import org.opensaml.xmlsec.SignatureValidationConfiguration;

public final class SecurityConfigurationSupport {
    private SecurityConfigurationSupport() {
    }

    public static DecryptionConfiguration getGlobalDecryptionConfiguration() {
        return ConfigurationService.get(DecryptionConfiguration.class);
    }

    public static EncryptionConfiguration getGlobalEncryptionConfiguration() {
        return ConfigurationService.get(EncryptionConfiguration.class);
    }

    public static SignatureSigningConfiguration getGlobalSignatureSigningConfiguration() {
        return ConfigurationService.get(SignatureSigningConfiguration.class);
    }

    public static SignatureValidationConfiguration getGlobalSignatureValidationConfiguration() {
        return ConfigurationService.get(SignatureValidationConfiguration.class);
    }
}

