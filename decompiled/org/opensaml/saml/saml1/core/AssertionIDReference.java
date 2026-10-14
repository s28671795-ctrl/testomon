/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.Evidentiary;

public interface AssertionIDReference
extends SAMLObject,
Evidentiary {
    @Nonnull
    @NotEmpty
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AssertionIDReference";
    @Nonnull
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference", "saml1");

    @Nullable
    public String getReference();

    public void setReference(@Nullable String var1);
}

