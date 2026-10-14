/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.NameIDType;

public interface NameID
extends SAMLObject,
NameIDType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "NameID";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "NameID", "saml2");
    public static final String TYPE_LOCAL_NAME = "NameIDType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "NameIDType", "saml2");
}

