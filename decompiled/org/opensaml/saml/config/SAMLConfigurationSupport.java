/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.config;

import java.util.List;
import org.joda.time.format.DateTimeFormatter;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.saml.config.SAMLConfiguration;
import org.opensaml.saml.saml1.binding.artifact.SAML1ArtifactBuilderFactory;
import org.opensaml.saml.saml2.binding.artifact.SAML2ArtifactBuilderFactory;

public final class SAMLConfigurationSupport {
    private SAMLConfigurationSupport() {
    }

    public static DateTimeFormatter getSAMLDateFormatter() {
        return ConfigurationService.get(SAMLConfiguration.class).getSAMLDateFormatter();
    }

    public static void setSAMLDateFormat(String format) {
        ConfigurationService.get(SAMLConfiguration.class).setSAMLDateFormat(format);
    }

    public static SAML1ArtifactBuilderFactory getSAML1ArtifactBuilderFactory() {
        return ConfigurationService.get(SAMLConfiguration.class).getSAML1ArtifactBuilderFactory();
    }

    public static void setSAML1ArtifactBuilderFactory(SAML1ArtifactBuilderFactory factory2) {
        ConfigurationService.get(SAMLConfiguration.class).setSAML1ArtifactBuilderFactory(factory2);
    }

    public static SAML2ArtifactBuilderFactory getSAML2ArtifactBuilderFactory() {
        return ConfigurationService.get(SAMLConfiguration.class).getSAML2ArtifactBuilderFactory();
    }

    public static void setSAML2ArtifactBuilderFactory(SAML2ArtifactBuilderFactory factory2) {
        ConfigurationService.get(SAMLConfiguration.class).setSAML2ArtifactBuilderFactory(factory2);
    }

    public static List<String> getAllowedBindingURLSchemes() {
        return ConfigurationService.get(SAMLConfiguration.class).getAllowedBindingURLSchemes();
    }

    public static void setAllowedBindingURLSchemes(List<String> schemes) {
        ConfigurationService.get(SAMLConfiguration.class).setAllowedBindingURLSchemes(schemes);
    }
}

