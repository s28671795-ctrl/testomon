/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.saml2.core.Extensions;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.Status;

public interface StatusResponseType
extends SignableSAMLObject {
    public static final String TYPE_LOCAL_NAME = "StatusResponseType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "StatusResponseType", "saml2p");
    public static final String ID_ATTRIB_NAME = "ID";
    public static final String IN_RESPONSE_TO_ATTRIB_NAME = "InResponseTo";
    public static final String VERSION_ATTRIB_NAME = "Version";
    public static final String ISSUE_INSTANT_ATTRIB_NAME = "IssueInstant";
    public static final String DESTINATION_ATTRIB_NAME = "Destination";
    public static final String CONSENT_ATTRIB_NAME = "Consent";
    public static final String UNSPECIFIED_CONSENT = "urn:oasis:names:tc:SAML:2.0:consent:unspecified";
    public static final String OBTAINED_CONSENT = "urn:oasis:names:tc:SAML:2.0:consent:obtained";
    public static final String PRIOR_CONSENT = "urn:oasis:names:tc:SAML:2.0:consent:prior";
    public static final String IMPLICIT_CONSENT = "urn:oasis:names:tc:SAML:2.0:consent:current-implicit";
    public static final String EXPLICIT_CONSENT = "urn:oasis:names:tc:SAML:2.0:consent:current-explicit";
    public static final String UNAVAILABLE_CONSENT = "urn:oasis:names:tc:SAML:2.0:consent:unavailable";
    public static final String INAPPLICABLE_CONSENT = "urn:oasis:names:tc:SAML:2.0:consent:inapplicable";

    public SAMLVersion getVersion();

    public void setVersion(SAMLVersion var1);

    public String getID();

    public void setID(String var1);

    public String getInResponseTo();

    public void setInResponseTo(String var1);

    public DateTime getIssueInstant();

    public void setIssueInstant(DateTime var1);

    public String getDestination();

    public void setDestination(String var1);

    public String getConsent();

    public void setConsent(String var1);

    public Issuer getIssuer();

    public void setIssuer(Issuer var1);

    public Status getStatus();

    public void setStatus(Status var1);

    public Extensions getExtensions();

    public void setExtensions(Extensions var1);
}

