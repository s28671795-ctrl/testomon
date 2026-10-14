/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.AssertionIDReference;
import org.opensaml.saml.saml1.core.Evidentiary;

public interface Evidence
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Evidence";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Evidence", "saml1");
    public static final String TYPE_LOCAL_NAME = "EvidenceType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "EvidenceType", "saml1");

    public List<Evidentiary> getEvidence();

    public List<AssertionIDReference> getAssertionIDReferences();

    public List<Assertion> getAssertions();
}

