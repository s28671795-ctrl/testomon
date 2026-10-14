/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.AssertionIDReference;

public interface Advice
extends SAMLObject,
ElementExtensibleXMLObject {
    @Nonnull
    @NotEmpty
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Advice";
    @Nonnull
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Advice", "saml1");
    @Nonnull
    @NotEmpty
    public static final String TYPE_LOCAL_NAME = "AdviceType";
    @Nonnull
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AdviceType", "saml1");

    @Nonnull
    @NonnullElements
    public List<AssertionIDReference> getAssertionIDReferences();

    @Nonnull
    @NonnullElements
    public List<Assertion> getAssertions();
}

