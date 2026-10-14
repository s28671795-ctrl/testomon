/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AssertionIDRef;
import org.opensaml.saml.saml2.core.AssertionURIRef;
import org.opensaml.saml.saml2.core.EncryptedAssertion;

public interface Advice
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Advice";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Advice", "saml2");
    public static final String TYPE_LOCAL_NAME = "AdviceType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AdviceType", "saml2");

    public List<XMLObject> getChildren();

    public List<XMLObject> getChildren(QName var1);

    public List<AssertionIDRef> getAssertionIDReferences();

    public List<AssertionURIRef> getAssertionURIReferences();

    public List<Assertion> getAssertions();

    public List<EncryptedAssertion> getEncryptedAssertions();
}

