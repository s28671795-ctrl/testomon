/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.saml1.core.Audience;
import org.opensaml.saml.saml1.core.Condition;

public interface AudienceRestrictionCondition
extends Condition {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AudienceRestrictionCondition";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AudienceRestrictionCondition", "saml1");
    public static final String TYPE_LOCAL_NAME = "AudienceRestrictionConditionType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AudienceRestrictionConditionType", "saml1");

    public List<Audience> getAudiences();
}

