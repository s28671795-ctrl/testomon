/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.util.Collection;
import javax.xml.namespace.QName;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SecurityPolicyValidator;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.Header;
import org.apache.wss4j.policy.model.RequiredParts;
import org.w3c.dom.Element;

public class RequiredPartsPolicyValidator
implements SecurityPolicyValidator {
    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        return assertionInfo.getAssertion() != null && (SP12Constants.REQUIRED_PARTS.equals(assertionInfo.getAssertion().getName()) || SP11Constants.REQUIRED_PARTS.equals(assertionInfo.getAssertion().getName()));
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        Element header = parameters.getSoapHeader();
        for (AssertionInfo ai : ais) {
            RequiredParts rp = (RequiredParts)ai.getAssertion();
            ai.setAsserted(true);
            for (Header h2 : rp.getHeaders()) {
                QName qName = new QName(h2.getNamespace(), h2.getName());
                if (header != null && DOMUtils.getFirstChildWithName(header, qName) != null) continue;
                ai.setNotAsserted("No header element of name " + qName + " found.");
            }
        }
    }
}

