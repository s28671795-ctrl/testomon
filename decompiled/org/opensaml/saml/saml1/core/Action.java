/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.saml.common.SAMLObject;

public interface Action
extends SAMLObject {
    @Nonnull
    @NotEmpty
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Action";
    @Nullable
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Action", "saml1");
    @Nonnull
    @NotEmpty
    public static final String TYPE_LOCAL_NAME = "ActionType";
    @Nullable
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "ActionType", "saml1");
    @Nonnull
    @NotEmpty
    public static final String NAMESPACE_ATTRIB_NAME = "Namespace";

    @Nullable
    public String getNamespace();

    public void setNamespace(@Nullable String var1);

    @Nullable
    public String getContents();

    public void setContents(@Nullable String var1);
}

