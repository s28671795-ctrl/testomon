/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractBindingPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.AbstractTokenWrapper;
import org.apache.wss4j.policy.model.AsymmetricBinding;
import org.apache.wss4j.policy.model.X509Token;

public class AsymmetricBindingPolicyValidator
extends AbstractBindingPolicyValidator {
    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        return assertionInfo.getAssertion() != null && (SP12Constants.ASYMMETRIC_BINDING.equals(assertionInfo.getAssertion().getName()) || SP11Constants.ASYMMETRIC_BINDING.equals(assertionInfo.getAssertion().getName()));
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        boolean hasDerivedKeys = parameters.getResults().getActionResults().containsKey(2048);
        for (AssertionInfo ai : ais) {
            AsymmetricBinding binding = (AsymmetricBinding)ai.getAssertion();
            ai.setAsserted(true);
            if (this.checkProtectionOrder(binding, parameters.getAssertionInfoMap(), ai, parameters.getResults().getResults()) && this.checkProperties(binding, ai, parameters.getAssertionInfoMap(), parameters.getResults(), parameters.getSignedResults(), parameters.getMessage()) && this.checkTokens(binding, ai, parameters.getAssertionInfoMap(), hasDerivedKeys, parameters.getSignedResults(), parameters.getEncryptedResults())) continue;
        }
    }

    private boolean checkTokens(AsymmetricBinding binding, AssertionInfo ai, AssertionInfoMap aim, boolean hasDerivedKeys, List<WSSecurityEngineResult> signedResults, List<WSSecurityEngineResult> encryptedResults) {
        boolean result = true;
        if (binding.getInitiatorToken() != null) {
            result &= this.checkInitiatorTokens(binding.getInitiatorToken(), ai, aim, hasDerivedKeys, signedResults, encryptedResults);
        }
        if (binding.getInitiatorSignatureToken() != null) {
            result &= this.checkInitiatorTokens(binding.getInitiatorSignatureToken(), ai, aim, hasDerivedKeys, signedResults, encryptedResults);
        }
        if (binding.getInitiatorEncryptionToken() != null) {
            result &= this.checkInitiatorTokens(binding.getInitiatorEncryptionToken(), ai, aim, hasDerivedKeys, signedResults, encryptedResults);
        }
        if (binding.getRecipientToken() != null) {
            result &= this.checkRecipientTokens(binding.getRecipientToken(), ai, aim, hasDerivedKeys, signedResults, encryptedResults);
        }
        if (binding.getRecipientSignatureToken() != null) {
            result &= this.checkRecipientTokens(binding.getRecipientSignatureToken(), ai, aim, hasDerivedKeys, signedResults, encryptedResults);
        }
        if (binding.getRecipientEncryptionToken() != null) {
            result &= this.checkRecipientTokens(binding.getRecipientEncryptionToken(), ai, aim, hasDerivedKeys, signedResults, encryptedResults);
        }
        return result;
    }

    private boolean checkInitiatorTokens(AbstractTokenWrapper wrapper, AssertionInfo ai, AssertionInfoMap aim, boolean hasDerivedKeys, List<WSSecurityEngineResult> signedResults, List<WSSecurityEngineResult> encryptedResults) {
        AbstractToken token = wrapper.getToken();
        if (token instanceof X509Token) {
            boolean foundCert = false;
            for (WSSecurityEngineResult result : signedResults) {
                X509Certificate cert = (X509Certificate)result.get("x509-certificate");
                if (cert == null) continue;
                foundCert = true;
                break;
            }
            if (!foundCert && !signedResults.isEmpty()) {
                String error = "An X.509 certificate was not used for the " + wrapper.getName();
                this.unassertPolicy(aim, wrapper.getName(), error);
                ai.setNotAsserted(error);
                return false;
            }
        }
        PolicyUtils.assertPolicy(aim, wrapper.getName());
        if (!this.checkDerivedKeys(wrapper, hasDerivedKeys, signedResults, encryptedResults)) {
            ai.setNotAsserted("Message fails the DerivedKeys requirement");
            return false;
        }
        this.assertDerivedKeys(wrapper.getToken(), aim);
        return true;
    }

    private void unassertPolicy(AssertionInfoMap aim, QName q, String msg) {
        Collection ais = (Collection)aim.get(q);
        if (ais != null && !ais.isEmpty()) {
            for (AssertionInfo ai : ais) {
                ai.setNotAsserted(msg);
            }
        }
    }

    private boolean checkRecipientTokens(AbstractTokenWrapper wrapper, AssertionInfo ai, AssertionInfoMap aim, boolean hasDerivedKeys, List<WSSecurityEngineResult> signedResults, List<WSSecurityEngineResult> encryptedResults) {
        PolicyUtils.assertPolicy(aim, wrapper.getName());
        if (!this.checkDerivedKeys(wrapper, hasDerivedKeys, signedResults, encryptedResults)) {
            ai.setNotAsserted("Message fails the DerivedKeys requirement");
            return false;
        }
        this.assertDerivedKeys(wrapper.getToken(), aim);
        return true;
    }
}

