/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.saml2.core.AuthnContext;
import org.opensaml.saml.saml2.core.Statement;
import org.opensaml.saml.saml2.core.SubjectLocality;

public interface AuthnStatement
extends Statement {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AuthnStatement";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnStatement", "saml2");
    public static final String TYPE_LOCAL_NAME = "AuthnStatementType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnStatementType", "saml2");
    public static final String AUTHN_INSTANT_ATTRIB_NAME = "AuthnInstant";
    public static final String SESSION_INDEX_ATTRIB_NAME = "SessionIndex";
    public static final String SESSION_NOT_ON_OR_AFTER_ATTRIB_NAME = "SessionNotOnOrAfter";

    public DateTime getAuthnInstant();

    public void setAuthnInstant(DateTime var1);

    public String getSessionIndex();

    public void setSessionIndex(String var1);

    public DateTime getSessionNotOnOrAfter();

    public void setSessionNotOnOrAfter(DateTime var1);

    public SubjectLocality getSubjectLocality();

    public void setSubjectLocality(SubjectLocality var1);

    public AuthnContext getAuthnContext();

    public void setAuthnContext(AuthnContext var1);
}

