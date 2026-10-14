/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;

public interface DEREncodedKeyValue
extends XSBase64Binary {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "DEREncodedKeyValue";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "DEREncodedKeyValue", "ds11");
    public static final String TYPE_LOCAL_NAME = "DEREncodedKeyValueType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "DEREncodedKeyValueType", "ds11");
    public static final String ID_ATTRIB_NAME = "Id";

    @Nullable
    public String getID();

    public void setID(@Nullable String var1);
}

