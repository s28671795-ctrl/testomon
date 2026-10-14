/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2alg;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;

public interface DigestMethod
extends SAMLObject,
ElementExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "DigestMethod";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:algsupport", "DigestMethod", "alg");
    public static final String TYPE_LOCAL_NAME = "DigestMethodType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:algsupport", "DigestMethodType", "alg");
    public static final String ALGORITHM_ATTRIB_NAME = "Algorithm";

    @Nullable
    public String getAlgorithm();

    public void setAlgorithm(@Nullable String var1);
}

