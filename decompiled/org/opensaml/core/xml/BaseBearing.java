/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;

public interface BaseBearing {
    public static final String XML_BASE_ATTR_LOCAL_NAME = "base";
    public static final QName XML_BASE_ATTR_NAME = new QName("http://www.w3.org/XML/1998/namespace", "base", "xml");

    @Nullable
    public String getXMLBase();

    public void setXMLBase(@Nullable String var1);
}

