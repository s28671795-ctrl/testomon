/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.xml.namespace.QName;
import org.opensaml.xmlsec.signature.ECPointType;

public interface PublicKey
extends ECPointType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "PublicKey";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "PublicKey", "ds11");
}

