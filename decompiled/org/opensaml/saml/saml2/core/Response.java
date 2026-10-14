/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.EncryptedAssertion;
import org.opensaml.saml.saml2.core.StatusResponseType;

public interface Response
extends StatusResponseType {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Response";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "Response", "saml2p");
    public static final String TYPE_LOCAL_NAME = "ResponseType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ResponseType", "saml2p");

    public List<Assertion> getAssertions();

    public List<EncryptedAssertion> getEncryptedAssertions();
}

