/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.Audience;
import org.opensaml.saml.saml2.core.Condition;

public interface AudienceRestriction
extends Condition {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AudienceRestriction";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AudienceRestriction", "saml2");
    public static final String TYPE_LOCAL_NAME = "AudienceRestrictionType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AudienceRestrictionType", "saml2");

    public List<Audience> getAudiences();
}

