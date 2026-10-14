/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml.config;

import org.opensaml.core.xml.config.AbstractXMLObjectProviderInitializer;

public class XMLObjectProviderInitializer
extends AbstractXMLObjectProviderInitializer {
    private static String[] configs = new String[]{"/xacml10-saml2-profile-config.xml", "/xacml11-saml2-profile-config.xml", "/xacml2-saml2-profile-config.xml", "/xacml3-saml2-profile-config.xml"};

    @Override
    protected String[] getConfigResources() {
        return configs;
    }
}

