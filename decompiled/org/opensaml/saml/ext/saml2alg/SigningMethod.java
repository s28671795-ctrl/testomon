/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2alg;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;

public interface SigningMethod
extends SAMLObject,
ElementExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SigningMethod";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:algsupport", "SigningMethod", "alg");
    public static final String TYPE_LOCAL_NAME = "SigningMethodType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:algsupport", "SigningMethodType", "alg");
    public static final String ALGORITHM_ATTRIB_NAME = "Algorithm";
    public static final String MIN_KEY_SIZE_ATTRIB_NAME = "MinKeySize";
    public static final String MAX_KEY_SIZE_ATTRIB_NAME = "MaxKeySize";

    @Nullable
    public String getAlgorithm();

    public void setAlgorithm(@Nullable String var1);

    @Nullable
    public Integer getMinKeySize();

    public void setMinKeySize(@Nullable Integer var1);

    @Nullable
    public Integer getMaxKeySize();

    public void setMaxKeySize(@Nullable Integer var1);
}

