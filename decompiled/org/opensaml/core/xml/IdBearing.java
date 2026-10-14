/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;

public interface IdBearing {
    public static final String XML_ID_ATTR_LOCAL_NAME = "id";
    public static final QName XML_ID_ATTR_NAME = new QName("http://www.w3.org/XML/1998/namespace", "id", "xml");

    @Nullable
    public String getXMLId();

    public void setXMLId(@Nullable String var1);
}

