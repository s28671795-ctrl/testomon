/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.policy.PolicySetCombinerParametersType;
import org.opensaml.xacml.policy.impl.CombinerParametersTypeUnmarshaller;
import org.w3c.dom.Attr;

public class PolicySetCombinerParametersTypeUnmarshaller
extends CombinerParametersTypeUnmarshaller {
    @Override
    protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
        if (attribute.getLocalName().equals("PolicySetIdRef")) {
            PolicySetCombinerParametersType policySetCombinerParametersType = (PolicySetCombinerParametersType)xmlObject;
            policySetCombinerParametersType.setPolicySetIdRef(StringSupport.trimOrNull(attribute.getValue()));
        } else {
            super.processAttribute(xmlObject, attribute);
        }
    }
}

