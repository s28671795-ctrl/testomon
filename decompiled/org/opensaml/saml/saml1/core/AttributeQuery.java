/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.saml.saml1.core.AttributeDesignator;
import org.opensaml.saml.saml1.core.SubjectQuery;

public interface AttributeQuery
extends SubjectQuery {
    @Nonnull
    @NotEmpty
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeQuery";
    @Nonnull
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AttributeQuery", "saml1p");
    @Nonnull
    @NotEmpty
    public static final String TYPE_LOCAL_NAME = "AttributeQueryType";
    @Nonnull
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "AttributeQueryType", "saml1p");
    @Nonnull
    @NotEmpty
    public static final String RESOURCE_ATTRIB_NAME = "Resource";

    @Nonnull
    @NonnullElements
    public List<AttributeDesignator> getAttributeDesignators();

    @Nullable
    public String getResource();

    public void setResource(@Nullable String var1);
}

