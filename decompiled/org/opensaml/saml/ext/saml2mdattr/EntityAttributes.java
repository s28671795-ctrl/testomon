/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdattr;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Attribute;

public interface EntityAttributes
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "EntityAttributes";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:attribute", "EntityAttributes", "mdattr");
    public static final String TYPE_LOCAL_NAME = "EntityAttributesType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:attribute", "EntityAttributesType", "mdattr");

    public List<Attribute> getAttributes();

    public List<Assertion> getAssertions();
}

