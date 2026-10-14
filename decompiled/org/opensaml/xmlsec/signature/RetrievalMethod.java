/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.Transforms;

public interface RetrievalMethod
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "RetrievalMethod";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RetrievalMethod", "ds");
    public static final String TYPE_LOCAL_NAME = "RetrievalMethodType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RetrievalMethodType", "ds");
    public static final String URI_ATTRIB_NAME = "URI";
    public static final String TYPE_ATTRIB_NAME = "Type";

    @Nullable
    public String getURI();

    public void setURI(@Nullable String var1);

    @Nullable
    public String getType();

    public void setType(@Nullable String var1);

    @Nullable
    public Transforms getTransforms();

    public void setTransforms(@Nullable Transforms var1);
}

