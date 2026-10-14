/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.saml.common.SAMLObject;

public interface AssertionArtifact
extends SAMLObject {
    @Nonnull
    @NotEmpty
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AssertionArtifact";
    @Nonnull
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AssertionArtifact", "saml1p");

    @Nullable
    public String getAssertionArtifact();

    public void setAssertionArtifact(@Nullable String var1);
}

