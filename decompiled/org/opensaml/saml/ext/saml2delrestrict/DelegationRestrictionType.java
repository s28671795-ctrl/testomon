/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2delrestrict;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.ext.saml2delrestrict.Delegate;
import org.opensaml.saml.saml2.core.Condition;

public interface DelegationRestrictionType
extends Condition {
    public static final String TYPE_LOCAL_NAME = "DelegationRestrictionType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:conditions:delegation", "DelegationRestrictionType", "del");

    public List<Delegate> getDelegates();
}

