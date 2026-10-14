/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface GeolocationHint
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "GeolocationHint";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "GeolocationHint", "mdui");

    public String getHint();

    public void setHint(String var1);
}

