/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.Transforms;

public interface CipherReference
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "CipherReference";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "CipherReference", "xenc");
    public static final String TYPE_LOCAL_NAME = "CipherReferenceType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "CipherReferenceType", "xenc");
    public static final String URI_ATTRIB_NAME = "URI";

    @Nullable
    public String getURI();

    public void setURI(@Nullable String var1);

    @Nullable
    public Transforms getTransforms();

    public void setTransforms(@Nullable Transforms var1);
}

