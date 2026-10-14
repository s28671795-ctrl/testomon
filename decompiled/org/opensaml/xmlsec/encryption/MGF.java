/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import javax.xml.namespace.QName;
import org.opensaml.xmlsec.encryption.AlgorithmIdentifierType;

public interface MGF
extends AlgorithmIdentifierType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "MGF";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2009/xmlenc11#", "MGF", "xenc11");
    public static final String TYPE_LOCAL_NAME = "MGFType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2009/xmlenc11#", "MGFType", "xenc11");
}

