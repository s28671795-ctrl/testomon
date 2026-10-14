/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.ctx.AttributeType;
import org.opensaml.xacml.ctx.SubjectType;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.w3c.dom.Attr;

public class SubjectTypeUnmarshaller
extends AbstractXACMLObjectUnmarshaller {
    @Override
    protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
        SubjectType attrib = (SubjectType)xmlObject;
        if (attribute.getLocalName().equals("SubjectCategory")) {
            attrib.setSubjectCategory(attribute.getValue());
        } else {
            super.processAttribute(xmlObject, attribute);
        }
    }

    @Override
    protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
        SubjectType subject = (SubjectType)parentObject;
        if (childObject instanceof AttributeType) {
            subject.getAttributes().add((AttributeType)childObject);
        } else {
            super.processChildElement(parentObject, childObject);
        }
    }
}

