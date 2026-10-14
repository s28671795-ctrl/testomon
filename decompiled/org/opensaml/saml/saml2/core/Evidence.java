/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AssertionIDRef;
import org.opensaml.saml.saml2.core.AssertionURIRef;
import org.opensaml.saml.saml2.core.EncryptedAssertion;
import org.opensaml.saml.saml2.core.Evidentiary;

public interface Evidence
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Evidence";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Evidence", "saml2");
    public static final String TYPE_LOCAL_NAME = "EvidenceType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "EvidenceType", "saml2");

    public List<AssertionIDRef> getAssertionIDReferences();

    public List<AssertionURIRef> getAssertionURIReferences();

    public List<Assertion> getAssertions();

    public List<EncryptedAssertion> getEncryptedAssertions();

    public List<Evidentiary> getEvidence();
}

