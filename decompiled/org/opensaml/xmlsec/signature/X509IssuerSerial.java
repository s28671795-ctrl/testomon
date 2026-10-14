/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.X509IssuerName;
import org.opensaml.xmlsec.signature.X509SerialNumber;

public interface X509IssuerSerial
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "X509IssuerSerial";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509IssuerSerial", "ds");
    public static final String TYPE_LOCAL_NAME = "X509IssuerSerialType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509IssuerSerialType", "ds");

    @Nullable
    public X509IssuerName getX509IssuerName();

    public void setX509IssuerName(@Nullable X509IssuerName var1);

    @Nullable
    public X509SerialNumber getX509SerialNumber();

    public void setX509SerialNumber(@Nullable X509SerialNumber var1);
}

