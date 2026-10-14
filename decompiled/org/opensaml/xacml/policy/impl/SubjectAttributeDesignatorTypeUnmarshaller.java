/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.policy.SubjectAttributeDesignatorType;
import org.opensaml.xacml.policy.impl.AttributeDesignatorTypeUnmarshaller;
import org.w3c.dom.Attr;

public class SubjectAttributeDesignatorTypeUnmarshaller
extends AttributeDesignatorTypeUnmarshaller {
    @Override
    protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
        if (attribute.getLocalName().equals("SubjectCategory")) {
            SubjectAttributeDesignatorType subjectAttributeDesignatorType = (SubjectAttributeDesignatorType)xmlObject;
            subjectAttributeDesignatorType.setSubjectCategory(StringSupport.trimOrNull(attribute.getValue()));
        } else {
            super.processAttribute(xmlObject, attribute);
        }
    }
}

