/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.xml.namespace.QName;
import org.opensaml.xmlsec.signature.CryptoBinary;

public interface Seed
extends CryptoBinary {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Seed";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Seed", "ds");
}

