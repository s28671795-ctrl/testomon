/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.AudienceRestrictionCondition;
import org.opensaml.saml.saml1.core.Condition;
import org.opensaml.saml.saml1.core.DoNotCacheCondition;

public interface Conditions
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Conditions";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Conditions", "saml1");
    public static final String TYPE_LOCAL_NAME = "ConditionsType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "ConditionsType", "saml1");
    public static final String NOTBEFORE_ATTRIB_NAME = "NotBefore";
    public static final String NOTONORAFTER_ATTRIB_NAME = "NotOnOrAfter";

    public DateTime getNotBefore();

    public void setNotBefore(DateTime var1);

    public DateTime getNotOnOrAfter();

    public void setNotOnOrAfter(DateTime var1);

    public List<Condition> getConditions();

    public List<Condition> getConditions(QName var1);

    public List<AudienceRestrictionCondition> getAudienceRestrictionConditions();

    public List<DoNotCacheCondition> getDoNotCacheConditions();
}

