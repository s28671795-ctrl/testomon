/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.policy.PolicyCombinerParametersType;
import org.opensaml.xacml.policy.impl.CombinerParametersTypeMarshaller;
import org.w3c.dom.Element;

public class PolicyCombinerParametersTypeMarshaller
extends CombinerParametersTypeMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        PolicyCombinerParametersType policyCombinerParametersType = (PolicyCombinerParametersType)xmlObject;
        if (!Strings.isNullOrEmpty(policyCombinerParametersType.getPolicyIdRef())) {
            domElement.setAttributeNS(null, "PolicyIdRef", policyCombinerParametersType.getPolicyIdRef());
        }
        super.marshallAttributes(xmlObject, domElement);
    }
}

