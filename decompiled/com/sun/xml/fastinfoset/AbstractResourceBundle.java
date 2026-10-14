/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.fastinfoset;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class AbstractResourceBundle
extends ResourceBundle {
    public static final String LOCALE = "com.sun.xml.fastinfoset.locale";

    public String getString(String key, Object[] args2) {
        String pattern = this.getBundle().getString(key);
        return MessageFormat.format(pattern, args2);
    }

    public static Locale parseLocale(String localeString) {
        Locale locale = null;
        if (localeString == null) {
            locale = Locale.getDefault();
        } else {
            try {
                String[] args2 = localeString.split("_");
                if (args2.length == 1) {
                    locale = new Locale(args2[0]);
                } else if (args2.length == 2) {
                    locale = new Locale(args2[0], args2[1]);
                } else if (args2.length == 3) {
                    locale = new Locale(args2[0], args2[1], args2[2]);
                }
            }
            catch (Throwable t2) {
                locale = Locale.getDefault();
            }
        }
        return locale;
    }

    public abstract ResourceBundle getBundle();

    @Override
    protected Object handleGetObject(String key) {
        return this.getBundle().getObject(key);
    }

    public final Enumeration getKeys() {
        return this.getBundle().getKeys();
    }
}

