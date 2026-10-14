/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.saml.common.SAMLObject;

public interface AttributeDesignator
extends SAMLObject {
    @Nonnull
    @NotEmpty
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeDesignator";
    @Nonnull
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeDesignator", "saml1");
    @Nonnull
    @NotEmpty
    public static final String TYPE_LOCAL_NAME = "AttributeDesignatorType";
    @Nonnull
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeDesignatorType", "saml1");
    @Nonnull
    @NotEmpty
    public static final String ATTRIBUTENAME_ATTRIB_NAME = "AttributeName";
    @Nonnull
    @NotEmpty
    public static final String ATTRIBUTENAMESPACE_ATTRIB_NAME = "AttributeNamespace";

    @Nullable
    public String getAttributeName();

    public void setAttributeName(@Nullable String var1);

    @Nullable
    public String getAttributeNamespace();

    public void setAttributeNamespace(@Nullable String var1);
}

