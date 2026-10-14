/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.saml;

import java.io.IOException;
import java.io.InputStream;
import org.opensaml.core.config.Configuration;
import org.opensaml.core.xml.config.XMLConfigurationException;
import org.opensaml.core.xml.config.XMLConfigurator;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;

public final class OpenSAMLBootstrap {
    private static final String[] XML_CONFIGS = new String[]{"/default-config.xml", "/schema-config.xml", "/saml1-assertion-config.xml", "/saml1-metadata-config.xml", "/saml1-protocol-config.xml", "/saml2-assertion-config.xml", "/saml2-assertion-delegation-restriction-config.xml", "/saml2-ecp-config.xml", "/saml2-metadata-algorithm-config.xml", "/saml2-metadata-attr-config.xml", "/saml2-metadata-config.xml", "/saml2-metadata-idp-discovery-config.xml", "/saml2-metadata-query-config.xml", "/saml2-metadata-reqinit-config.xml", "/saml2-metadata-ui-config.xml", "/saml2-metadata-rpi-config.xml", "/saml2-protocol-config.xml", "/saml2-protocol-thirdparty-config.xml", "/saml2-protocol-aslo-config.xml", "/saml2-channel-binding-config.xml", "/saml-ec-gss-config.xml", "/signature-config.xml", "/encryption-config.xml", "/xacml20-context-config.xml", "/xacml20-policy-config.xml", "/xacml10-saml2-profile-config.xml", "/xacml11-saml2-profile-config.xml", "/xacml2-saml2-profile-config.xml", "/xacml3-saml2-profile-config.xml", "/saml2-xacml2-profile.xml"};

    private OpenSAMLBootstrap() {
    }

    public static synchronized void bootstrap() throws XMLConfigurationException {
        OpenSAMLBootstrap.bootstrap(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static synchronized void bootstrap(boolean includeXacml) throws XMLConfigurationException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            XMLConfigurator configurator2 = new XMLConfigurator();
            Thread.currentThread().setContextClassLoader(XMLObjectProviderRegistrySupport.class.getClassLoader());
            for (String config : XML_CONFIGS) {
                if (!includeXacml && config.contains("xacml")) continue;
                InputStream ins = Configuration.class.getResourceAsStream(config);
                if (ins == null) {
                    ins = OpenSAMLBootstrap.class.getResourceAsStream(config);
                }
                if (ins == null) continue;
                configurator2.load(ins);
                try {
                    ins.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
        finally {
            Thread.currentThread().setContextClassLoader(loader);
        }
    }
}

