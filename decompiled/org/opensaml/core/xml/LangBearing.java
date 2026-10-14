/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;

public interface LangBearing {
    public static final String XML_LANG_ATTR_LOCAL_NAME = "lang";
    public static final QName XML_LANG_ATTR_NAME = new QName("http://www.w3.org/XML/1998/namespace", "lang", "xml");

    @Nullable
    public String getXMLLang();

    public void setXMLLang(@Nullable String var1);
}

