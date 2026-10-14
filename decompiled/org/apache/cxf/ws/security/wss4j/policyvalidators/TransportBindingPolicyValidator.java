/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.util.Collection;
import javax.xml.namespace.QName;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.security.transport.TLSSessionInfo;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractBindingPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.TransportBinding;

public class TransportBindingPolicyValidator
extends AbstractBindingPolicyValidator {
    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        return assertionInfo.getAssertion() != null && (SP12Constants.TRANSPORT_BINDING.equals(assertionInfo.getAssertion().getName()) || SP11Constants.TRANSPORT_BINDING.equals(assertionInfo.getAssertion().getName()));
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        for (AssertionInfo ai : ais) {
            TransportBinding binding = (TransportBinding)ai.getAssertion();
            ai.setAsserted(true);
            boolean initiator = MessageUtils.isRequestor(parameters.getMessage());
            TLSSessionInfo tlsInfo = parameters.getMessage().get(TLSSessionInfo.class);
            if (!initiator && tlsInfo == null) {
                ai.setNotAsserted("TLS is not enabled");
                continue;
            }
            if (binding.getTransportToken() != null) {
                PolicyUtils.assertPolicy(parameters.getAssertionInfoMap(), binding.getTransportToken().getName());
            }
            if (!this.validateTimestamp(binding.isIncludeTimestamp(), true, parameters.getResults(), parameters.getSignedResults(), parameters.getMessage())) {
                String error = "Received Timestamp does not match the requirements";
                ai.setNotAsserted(error);
                continue;
            }
            PolicyUtils.assertPolicy(parameters.getAssertionInfoMap(), new QName(binding.getName().getNamespaceURI(), "IncludeTimestamp"));
        }
        if (!ais.isEmpty()) {
            PolicyUtils.assertPolicy(parameters.getAssertionInfoMap(), SP12Constants.ENCRYPTED_PARTS);
            PolicyUtils.assertPolicy(parameters.getAssertionInfoMap(), SP11Constants.ENCRYPTED_PARTS);
            PolicyUtils.assertPolicy(parameters.getAssertionInfoMap(), SP12Constants.SIGNED_PARTS);
            PolicyUtils.assertPolicy(parameters.getAssertionInfoMap(), SP11Constants.SIGNED_PARTS);
        }
    }
}

