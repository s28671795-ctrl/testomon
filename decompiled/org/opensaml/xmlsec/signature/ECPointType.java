/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.xml.namespace.QName;
import org.opensaml.xmlsec.signature.CryptoBinary;

public interface ECPointType
extends CryptoBinary {
    public static final String TYPE_LOCAL_NAME = "ECPointType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "ECPointType", "ds11");
}

