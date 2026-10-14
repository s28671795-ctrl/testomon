/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml1.core.Condition;

public interface DoNotCacheCondition
extends Condition {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "DoNotCacheCondition";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "DoNotCacheCondition", "saml1");
    public static final String TYPE_LOCAL_NAME = "DoNotCacheConditionType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "DoNotCacheConditionType", "saml1");
}

