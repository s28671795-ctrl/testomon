/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Query
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Query";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "Query", "saml1p");
    public static final String TYPE_LOCAL_NAME = "QueryAbstractType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "QueryAbstractType", "saml1p");
}

