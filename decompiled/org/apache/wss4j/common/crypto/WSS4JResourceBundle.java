/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.crypto;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.xml.security.utils.I18n;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WSS4JResourceBundle
extends ResourceBundle {
    private static final Logger LOG = LoggerFactory.getLogger(WSS4JResourceBundle.class);
    private final ResourceBundle wss4jSecResourceBundle = ResourceBundle.getBundle("messages.wss4j_errors");
    private final ResourceBundle xmlSecResourceBundle;

    public WSS4JResourceBundle() {
        ResourceBundle tmpResourceBundle;
        try {
            tmpResourceBundle = ResourceBundle.getBundle("org/apache/xml/security/resource/xmlsecurity", Locale.getDefault(), I18n.class.getClassLoader());
        }
        catch (MissingResourceException ex) {
            LOG.debug(ex.getMessage());
            tmpResourceBundle = ResourceBundle.getBundle("org/apache/xml/security/resource/xmlsecurity", new Locale("en", "US"), I18n.class.getClassLoader());
        }
        this.xmlSecResourceBundle = tmpResourceBundle;
    }

    @Override
    protected Object handleGetObject(String key) {
        Object value = null;
        try {
            value = this.wss4jSecResourceBundle.getObject(key);
        }
        catch (MissingResourceException e) {
            try {
                value = this.xmlSecResourceBundle.getObject(key);
            }
            catch (MissingResourceException missingResourceException) {
                // empty catch block
            }
        }
        return value;
    }

    @Override
    public Enumeration<String> getKeys() {
        throw new UnsupportedOperationException("getKeys not supported");
    }
}

