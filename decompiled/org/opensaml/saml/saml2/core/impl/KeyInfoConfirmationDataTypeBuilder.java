/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.KeyInfoConfirmationDataType;
import org.opensaml.saml.saml2.core.impl.KeyInfoConfirmationDataTypeImpl;

public class KeyInfoConfirmationDataTypeBuilder
extends AbstractSAMLObjectBuilder<KeyInfoConfirmationDataType> {
    @Override
    public KeyInfoConfirmationDataType buildObject() {
        return (KeyInfoConfirmationDataType)this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectConfirmationData", "saml2", KeyInfoConfirmationDataType.TYPE_NAME);
    }

    @Override
    public KeyInfoConfirmationDataType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new KeyInfoConfirmationDataTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

