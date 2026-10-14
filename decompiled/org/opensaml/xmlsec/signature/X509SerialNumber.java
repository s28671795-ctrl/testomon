/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import java.math.BigInteger;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface X509SerialNumber
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "X509SerialNumber";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509SerialNumber", "ds");
    public static final String TYPE_LOCAL_NAME = "integer";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/XMLSchema", "integer", "xsd");

    @Nullable
    public BigInteger getValue();

    public void setValue(@Nullable BigInteger var1);
}

