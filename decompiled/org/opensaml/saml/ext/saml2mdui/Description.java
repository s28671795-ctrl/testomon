/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdui;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.metadata.LocalizedName;

public interface Description
extends LocalizedName,
SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Description";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "Description", "mdui");
}

