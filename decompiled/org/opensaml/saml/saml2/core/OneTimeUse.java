/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.Condition;

public interface OneTimeUse
extends Condition {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "OneTimeUse";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "OneTimeUse", "saml2");
    public static final String TYPE_LOCAL_NAME = "OneTimeUseType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "OneTimeUseType", "saml2");
}

