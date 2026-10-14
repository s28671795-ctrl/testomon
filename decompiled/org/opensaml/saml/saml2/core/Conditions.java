/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.AudienceRestriction;
import org.opensaml.saml.saml2.core.Condition;
import org.opensaml.saml.saml2.core.OneTimeUse;
import org.opensaml.saml.saml2.core.ProxyRestriction;

public interface Conditions
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Conditions";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Conditions", "saml2");
    public static final String TYPE_LOCAL_NAME = "ConditionsType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "ConditionsType", "saml2");
    public static final String NOT_BEFORE_ATTRIB_NAME = "NotBefore";
    public static final String NOT_ON_OR_AFTER_ATTRIB_NAME = "NotOnOrAfter";

    public DateTime getNotBefore();

    public void setNotBefore(DateTime var1);

    public DateTime getNotOnOrAfter();

    public void setNotOnOrAfter(DateTime var1);

    public List<Condition> getConditions();

    public List<AudienceRestriction> getAudienceRestrictions();

    public OneTimeUse getOneTimeUse();

    public ProxyRestriction getProxyRestriction();
}

