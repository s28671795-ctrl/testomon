/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.util.Collection;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractSupportingTokenPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.IssuedToken;
import org.apache.wss4j.policy.model.KerberosToken;
import org.apache.wss4j.policy.model.KeyValueToken;
import org.apache.wss4j.policy.model.SamlToken;
import org.apache.wss4j.policy.model.SecurityContextToken;
import org.apache.wss4j.policy.model.SpnegoContextToken;
import org.apache.wss4j.policy.model.SupportingTokens;
import org.apache.wss4j.policy.model.UsernameToken;
import org.apache.wss4j.policy.model.X509Token;

public class EndorsingEncryptedTokenPolicyValidator
extends AbstractSupportingTokenPolicyValidator {
    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        QName sp12QName = SP12Constants.ENDORSING_ENCRYPTED_SUPPORTING_TOKENS;
        return assertionInfo.getAssertion() != null && sp12QName.equals(assertionInfo.getAssertion().getName());
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        if (this.isTLSInUse(parameters.getMessage())) {
            AssertionInfo transportAi = PolicyUtils.getFirstAssertionByLocalname(parameters.getAssertionInfoMap(), "TransportBinding");
            super.setEnforceEncryptedTokens(transportAi == null);
        }
        for (AssertionInfo ai : ais) {
            SupportingTokens binding = (SupportingTokens)ai.getAssertion();
            ai.setAsserted(true);
            this.setSignedParts(binding.getSignedParts());
            this.setEncryptedParts(binding.getEncryptedParts());
            this.setSignedElements(binding.getSignedElements());
            this.setEncryptedElements(binding.getEncryptedElements());
            List<AbstractToken> tokens = binding.getTokens();
            for (AbstractToken token : tokens) {
                if (!this.isTokenRequired(token, parameters.getMessage())) {
                    this.assertDerivedKeys(token, parameters.getAssertionInfoMap());
                    this.assertSecurePartsIfTokenNotRequired(binding, parameters.getAssertionInfoMap());
                    continue;
                }
                AbstractToken.DerivedKeys derivedKeys = token.getDerivedKeys();
                boolean derived = derivedKeys == AbstractToken.DerivedKeys.RequireDerivedKeys;
                boolean processingFailed = false;
                if (token instanceof KerberosToken) {
                    if (!this.processKerberosTokens(parameters, derived)) {
                        processingFailed = true;
                    }
                } else if (token instanceof X509Token) {
                    if (!this.processX509Tokens(parameters, derived)) {
                        processingFailed = true;
                    }
                } else if (token instanceof KeyValueToken) {
                    if (!this.processKeyValueTokens(parameters)) {
                        processingFailed = true;
                    }
                } else if (token instanceof UsernameToken) {
                    if (!this.processUsernameTokens(parameters, derived)) {
                        processingFailed = true;
                    }
                } else if (token instanceof SecurityContextToken || token instanceof SpnegoContextToken) {
                    if (!this.processSCTokens(parameters, derived)) {
                        processingFailed = true;
                    }
                } else if (token instanceof SamlToken) {
                    if (!this.processSAMLTokens(parameters, derived)) {
                        processingFailed = true;
                    }
                } else if (token instanceof IssuedToken) {
                    IssuedToken issuedToken = (IssuedToken)token;
                    if (EndorsingEncryptedTokenPolicyValidator.isSamlTokenRequiredForIssuedToken(issuedToken) && !this.processSAMLTokens(parameters, derived)) {
                        processingFailed = true;
                    }
                } else {
                    processingFailed = true;
                }
                if (processingFailed) {
                    ai.setNotAsserted("The received token does not match the endorsing encrypted supporting token requirement");
                    continue;
                }
                if (!derived || !parameters.getResults().getActionResults().containsKey(2048)) continue;
                this.assertDerivedKeys(token, parameters.getAssertionInfoMap());
            }
        }
    }

    @Override
    protected boolean isSigned() {
        return false;
    }

    @Override
    protected boolean isEncrypted() {
        return true;
    }

    @Override
    protected boolean isEndorsing() {
        return true;
    }
}

