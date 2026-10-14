/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration;

public interface Configurer {
    public static final String DEFAULT_USER_CFG_FILE = "cxf.xml";
    public static final String USER_CFG_FILE_PROPERTY_NAME = "cxf.config.file";
    public static final String USER_CFG_FILE_PROPERTY_URL = "cxf.config.file.url";

    public void configureBean(Object var1);

    public void configureBean(String var1, Object var2);
}

