/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.core.AttributeQuery;
import org.opensaml.saml.saml2.core.impl.SubjectQueryUnmarshaller;

public class AttributeQueryUnmarshaller
extends SubjectQueryUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
        AttributeQuery query = (AttributeQuery)parentSAMLObject;
        if (childSAMLObject instanceof Attribute) {
            query.getAttributes().add((Attribute)childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }
}

