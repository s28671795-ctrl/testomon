/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.config;

import javax.xml.namespace.QName;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.xml.config.AbstractXMLObjectProviderInitializer;
import org.opensaml.core.xml.config.XMLObjectProviderRegistry;

public class XMLObjectProviderInitializer
extends AbstractXMLObjectProviderInitializer {
    private static String[] configs = new String[]{"/default-config.xml", "/schema-config.xml"};

    @Override
    protected String[] getConfigResources() {
        return configs;
    }

    @Override
    public void init() throws InitializationException {
        super.init();
        XMLObjectProviderRegistry registry = ConfigurationService.get(XMLObjectProviderRegistry.class);
        registry.registerIDAttribute(new QName("http://www.w3.org/XML/1998/namespace", "id"));
    }
}

