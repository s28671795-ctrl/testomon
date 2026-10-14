/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml1.core.AttributeDesignator;

public interface Attribute
extends AttributeDesignator {
    @Nonnull
    @NotEmpty
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Attribute";
    @Nonnull
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Attribute", "saml1");
    @Nonnull
    @NotEmpty
    public static final String TYPE_LOCAL_NAME = "AttributeType";
    @Nonnull
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeType", "saml1");

    @Nonnull
    @NonnullElements
    public List<XMLObject> getAttributeValues();
}

