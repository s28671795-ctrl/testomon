/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.opensaml.xacml.policy.DescriptionType;

public class DescriptionTypeUnmarshaller
extends AbstractXACMLObjectUnmarshaller {
    @Override
    protected void processElementContent(XMLObject xmlObject, String elementContent) {
        DescriptionType description = (DescriptionType)xmlObject;
        description.setValue(elementContent);
    }
}

