/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.utils.resolver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.resolver.ResourceResolverContext;
import org.apache.xml.security.utils.resolver.ResourceResolverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ResourceResolverSpi {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceResolverSpi.class);
    protected Map<String, String> properties;

    public abstract XMLSignatureInput engineResolveURI(ResourceResolverContext var1) throws ResourceResolverException;

    public void engineSetProperty(String key, String value) {
        if (this.properties == null) {
            this.properties = new HashMap<String, String>();
        }
        this.properties.put(key, value);
    }

    public String engineGetProperty(String key) {
        if (this.properties == null) {
            return null;
        }
        return this.properties.get(key);
    }

    public void engineAddProperies(Map<String, String> newProperties) {
        if (newProperties != null && !newProperties.isEmpty()) {
            if (this.properties == null) {
                this.properties = new HashMap<String, String>();
            }
            this.properties.putAll(newProperties);
        }
    }

    public boolean engineIsThreadSafe() {
        return false;
    }

    public abstract boolean engineCanResolveURI(ResourceResolverContext var1);

    public String[] engineGetPropertyKeys() {
        return new String[0];
    }

    public boolean understandsProperty(String propertyToTest) {
        String[] understood = this.engineGetPropertyKeys();
        if (understood != null) {
            for (String str : understood) {
                if (!str.equals(propertyToTest)) continue;
                return true;
            }
        }
        return false;
    }

    public static String fixURI(String str) {
        char ch0;
        char ch1;
        if ((str = str.replace(File.separatorChar, '/')).length() >= 4) {
            boolean isDosFilename;
            char ch02 = Character.toUpperCase(str.charAt(0));
            char ch12 = str.charAt(1);
            char ch2 = str.charAt(2);
            char ch3 = str.charAt(3);
            boolean bl = isDosFilename = 'A' <= ch02 && ch02 <= 'Z' && ch12 == ':' && ch2 == '/' && ch3 != '/';
            if (isDosFilename) {
                LOG.debug("Found DOS filename: {}", (Object)str);
            }
        }
        if (str.length() >= 2 && (ch1 = str.charAt(1)) == ':' && 'A' <= (ch0 = Character.toUpperCase(str.charAt(0))) && ch0 <= 'Z') {
            str = "/" + str;
        }
        return str;
    }
}

