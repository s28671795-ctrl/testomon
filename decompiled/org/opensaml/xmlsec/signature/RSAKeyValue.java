/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.Exponent;
import org.opensaml.xmlsec.signature.Modulus;

public interface RSAKeyValue
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "RSAKeyValue";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RSAKeyValue", "ds");
    public static final String TYPE_LOCAL_NAME = "RSAKeyValueType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RSAKeyValueType", "ds");

    @Nullable
    public Modulus getModulus();

    public void setModulus(@Nullable Modulus var1);

    @Nullable
    public Exponent getExponent();

    public void setExponent(@Nullable Exponent var1);
}

