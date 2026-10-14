/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface NamedCurve
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "NamedCurve";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "NamedCurve", "ds11");
    public static final String TYPE_LOCAL_NAME = "NamedCurveType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "NamedCurveType", "ds11");
    public static final String URI_ATTRIB_NAME = "URI";

    @Nullable
    public String getURI();

    public void setURI(@Nullable String var1);
}

