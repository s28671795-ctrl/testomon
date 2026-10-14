/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import javax.xml.namespace.QName;
import org.opensaml.xmlsec.signature.CryptoBinary;

public interface Generator
extends CryptoBinary {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Generator";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "Generator", "xenc");
}

