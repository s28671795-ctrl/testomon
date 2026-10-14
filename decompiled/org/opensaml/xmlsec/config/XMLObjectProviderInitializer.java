/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.config;

import org.opensaml.core.xml.config.AbstractXMLObjectProviderInitializer;

public class XMLObjectProviderInitializer
extends AbstractXMLObjectProviderInitializer {
    private static String[] configs = new String[]{"/signature-config.xml", "/encryption-config.xml"};

    @Override
    protected String[] getConfigResources() {
        return configs;
    }
}

