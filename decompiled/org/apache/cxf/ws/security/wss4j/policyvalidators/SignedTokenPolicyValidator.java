/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.util.Collection;
import java.util.List;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractSupportingTokenPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.IssuedToken;
import org.apache.wss4j.policy.model.KerberosToken;
import org.apache.wss4j.policy.model.KeyValueToken;
import org.apache.wss4j.policy.model.SamlToken;
import org.apache.wss4j.policy.model.SecurityContextToken;
import org.apache.wss4j.policy.model.SupportingTokens;
import org.apache.wss4j.policy.model.UsernameToken;
import org.apache.wss4j.policy.model.X509Token;

public class SignedTokenPolicyValidator
extends AbstractSupportingTokenPolicyValidator {
    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        return assertionInfo.getAssertion() != null && (SP12Constants.SIGNED_SUPPORTING_TOKENS.equals(assertionInfo.getAssertion().getName()) || SP11Constants.SIGNED_SUPPORTING_TOKENS.equals(assertionInfo.getAssertion().getName()));
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        for (AssertionInfo ai : ais) {
            SupportingTokens binding = (SupportingTokens)ai.getAssertion();
            ai.setAsserted(true);
            this.setSignedParts(binding.getSignedParts());
            this.setEncryptedParts(binding.getEncryptedParts());
            this.setSignedElements(binding.getSignedElements());
            this.setEncryptedElements(binding.getEncryptedElements());
            List<AbstractToken> tokens = binding.getTokens();
            for (AbstractToken token : tokens) {
                if (!this.isTokenRequired(token, parameters.getMessage())) continue;
                boolean processingFailed = false;
                if (token instanceof UsernameToken) {
                    if (!this.processUsernameTokens(parameters, false)) {
                        processingFailed = true;
                    }
                } else if (token instanceof SamlToken) {
                    if (!this.processSAMLTokens(parameters, false)) {
                        processingFailed = true;
                    }
                } else if (token instanceof KerberosToken) {
                    if (!this.processKerberosTokens(parameters, false)) {
                        processingFailed = true;
                    }
                } else if (token instanceof X509Token) {
                    if (!this.processX509Tokens(parameters, false)) {
                        processingFailed = true;
                    }
                } else if (token instanceof KeyValueToken) {
                    if (!this.processKeyValueTokens(parameters)) {
                        processingFailed = true;
                    }
                } else if (token instanceof SecurityContextToken) {
                    if (!this.processSCTokens(parameters, false)) {
                        processingFailed = true;
                    }
                } else if (token instanceof IssuedToken) {
                    IssuedToken issuedToken = (IssuedToken)token;
                    if (SignedTokenPolicyValidator.isSamlTokenRequiredForIssuedToken(issuedToken) && !this.processSAMLTokens(parameters, false)) {
                        processingFailed = true;
                    }
                } else {
                    processingFailed = true;
                }
                if (!processingFailed) continue;
                ai.setNotAsserted("The received token does not match the signed supporting token requirement");
            }
        }
    }

    @Override
    protected boolean isSigned() {
        return true;
    }

    @Override
    protected boolean isEncrypted() {
        return false;
    }

    @Override
    protected boolean isEndorsing() {
        return false;
    }
}

