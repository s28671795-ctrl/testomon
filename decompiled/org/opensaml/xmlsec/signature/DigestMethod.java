/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.core.xml.XMLObject;

public interface DigestMethod
extends XMLObject,
ElementExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "DigestMethod";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DigestMethod", "ds");
    public static final String TYPE_LOCAL_NAME = "DigestMethodType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DigestMethodType", "ds");
    public static final String ALGORITHM_ATTRIB_NAME = "Algorithm";

    @Nullable
    public String getAlgorithm();

    public void setAlgorithm(@Nullable String var1);
}

