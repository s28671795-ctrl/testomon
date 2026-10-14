/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;

public interface X509Digest
extends XSBase64Binary {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "X509Digest";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "X509Digest", "ds11");
    public static final String TYPE_LOCAL_NAME = "X509DigestType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "X509DigestType", "ds11");
    public static final String ALGORITHM_ATTRIB_NAME = "Algorithm";

    @Nullable
    public String getAlgorithm();

    public void setAlgorithm(@Nullable String var1);
}

