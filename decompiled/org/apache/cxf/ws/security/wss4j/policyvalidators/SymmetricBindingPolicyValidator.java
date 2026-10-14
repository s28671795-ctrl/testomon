/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.util.Collection;
import java.util.List;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractBindingPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.SymmetricBinding;

public class SymmetricBindingPolicyValidator
extends AbstractBindingPolicyValidator {
    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        return assertionInfo.getAssertion() != null && (SP12Constants.SYMMETRIC_BINDING.equals(assertionInfo.getAssertion().getName()) || SP11Constants.SYMMETRIC_BINDING.equals(assertionInfo.getAssertion().getName()));
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        boolean hasDerivedKeys = parameters.getResults().getActionResults().containsKey(2048);
        for (AssertionInfo ai : ais) {
            SymmetricBinding binding = (SymmetricBinding)ai.getAssertion();
            ai.setAsserted(true);
            if (this.checkProtectionOrder(binding, parameters.getAssertionInfoMap(), ai, parameters.getResults().getResults()) && this.checkProperties(binding, ai, parameters.getAssertionInfoMap(), parameters.getResults(), parameters.getSignedResults(), parameters.getMessage()) && this.checkTokens(binding, ai, parameters.getAssertionInfoMap(), hasDerivedKeys, parameters.getSignedResults(), parameters.getEncryptedResults())) continue;
        }
    }

    private boolean checkTokens(SymmetricBinding binding, AssertionInfo ai, AssertionInfoMap aim, boolean hasDerivedKeys, List<WSSecurityEngineResult> signedResults, List<WSSecurityEngineResult> encryptedResults) {
        if (binding.getEncryptionToken() != null) {
            PolicyUtils.assertPolicy(aim, binding.getEncryptionToken().getName());
            if (!this.checkDerivedKeys(binding.getEncryptionToken(), hasDerivedKeys, signedResults, encryptedResults)) {
                ai.setNotAsserted("Message fails the DerivedKeys requirement");
                return false;
            }
            this.assertDerivedKeys(binding.getEncryptionToken().getToken(), aim);
        }
        if (binding.getSignatureToken() != null) {
            PolicyUtils.assertPolicy(aim, binding.getSignatureToken().getName());
            if (!this.checkDerivedKeys(binding.getSignatureToken(), hasDerivedKeys, signedResults, encryptedResults)) {
                ai.setNotAsserted("Message fails the DerivedKeys requirement");
                return false;
            }
            this.assertDerivedKeys(binding.getSignatureToken().getToken(), aim);
        }
        if (binding.getProtectionToken() != null) {
            PolicyUtils.assertPolicy(aim, binding.getProtectionToken().getName());
            if (!this.checkDerivedKeys(binding.getProtectionToken(), hasDerivedKeys, signedResults, encryptedResults)) {
                ai.setNotAsserted("Message fails the DerivedKeys requirement");
                return false;
            }
            this.assertDerivedKeys(binding.getProtectionToken().getToken(), aim);
        }
        return true;
    }
}

