/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml1.core.Attribute;
import org.opensaml.saml.saml1.core.AttributeStatement;
import org.opensaml.saml.saml1.core.impl.SubjectStatementUnmarshaller;

public class AttributeStatementUnmarshaller
extends SubjectStatementUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
        AttributeStatement attributeStatement = (AttributeStatement)parentSAMLObject;
        if (childSAMLObject instanceof Attribute) {
            attributeStatement.getAttributes().add((Attribute)childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }
}

