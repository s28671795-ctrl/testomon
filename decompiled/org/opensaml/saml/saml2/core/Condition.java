/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Condition
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Condition";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Condition", "saml2");
    public static final String TYPE_LOCAL_NAME = "ConditionAbstractType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "ConditionAbstractType", "saml2");
}

