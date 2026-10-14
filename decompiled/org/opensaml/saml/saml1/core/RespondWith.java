/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSQName;
import org.opensaml.saml.common.SAMLObject;

public interface RespondWith
extends SAMLObject,
XSQName {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "RespondWith";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "RespondWith", "saml1p");
}

