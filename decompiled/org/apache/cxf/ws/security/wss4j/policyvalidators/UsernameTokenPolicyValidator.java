/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.util.Collection;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractSecurityPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.SP13Constants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.SupportingTokens;
import org.apache.wss4j.policy.model.UsernameToken;

public class UsernameTokenPolicyValidator
extends AbstractSecurityPolicyValidator {
    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        return assertionInfo.getAssertion() != null && (SP12Constants.USERNAME_TOKEN.equals(assertionInfo.getAssertion().getName()) || SP11Constants.USERNAME_TOKEN.equals(assertionInfo.getAssertion().getName()));
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        for (AssertionInfo ai : ais) {
            UsernameToken usernameTokenPolicy = (UsernameToken)ai.getAssertion();
            ai.setAsserted(true);
            this.assertToken(usernameTokenPolicy, parameters.getAssertionInfoMap());
            if (!this.isTokenRequired(usernameTokenPolicy, parameters.getMessage())) continue;
            if (parameters.getUsernameTokenResults().isEmpty()) {
                ai.setNotAsserted("The received token does not match the token inclusion requirement");
                continue;
            }
            if (this.checkTokens(usernameTokenPolicy, ai, parameters.getUsernameTokenResults())) continue;
        }
    }

    private void assertToken(UsernameToken token, AssertionInfoMap aim) {
        UsernameToken.UsernameTokenType usernameTokenType;
        UsernameToken.PasswordType passwordType;
        String namespace = token.getName().getNamespaceURI();
        if (token.isCreated()) {
            PolicyUtils.assertPolicy(aim, SP13Constants.CREATED);
        }
        if (token.isNonce()) {
            PolicyUtils.assertPolicy(aim, SP13Constants.NONCE);
        }
        if ((passwordType = token.getPasswordType()) != null) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, passwordType.name()));
        }
        if ((usernameTokenType = token.getUsernameTokenType()) != null) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, usernameTokenType.name()));
        }
    }

    public boolean checkTokens(UsernameToken usernameTokenPolicy, AssertionInfo ai, List<WSSecurityEngineResult> utResults) {
        for (WSSecurityEngineResult result : utResults) {
            boolean isNoPassword;
            org.apache.wss4j.dom.message.token.UsernameToken usernameToken = (org.apache.wss4j.dom.message.token.UsernameToken)result.get("username-token");
            UsernameToken.PasswordType passwordType = usernameTokenPolicy.getPasswordType();
            boolean isHashPassword = passwordType == UsernameToken.PasswordType.HashPassword;
            boolean bl = isNoPassword = passwordType == UsernameToken.PasswordType.NoPassword;
            if (isHashPassword != usernameToken.isHashed()) {
                ai.setNotAsserted("Password hashing policy not enforced");
                return false;
            }
            if (isNoPassword && usernameToken.getPassword() != null) {
                ai.setNotAsserted("Username Token NoPassword policy not enforced");
                return false;
            }
            if (!isNoPassword && usernameToken.getPassword() == null && this.isNonEndorsingSupportingToken(usernameTokenPolicy)) {
                ai.setNotAsserted("Username Token No Password supplied");
                return false;
            }
            if (usernameTokenPolicy.isCreated() && (usernameToken.getCreated() == null || usernameToken.isHashed())) {
                ai.setNotAsserted("Username Token Created policy not enforced");
                return false;
            }
            if (!usernameTokenPolicy.isNonce() || usernameToken.getNonce() != null && !usernameToken.isHashed()) continue;
            ai.setNotAsserted("Username Token Nonce policy not enforced");
            return false;
        }
        return true;
    }

    private boolean isNonEndorsingSupportingToken(UsernameToken usernameTokenPolicy) {
        SupportingTokens supportingToken;
        String localname;
        AbstractSecurityAssertion parentAssertion = usernameTokenPolicy.getParentAssertion();
        return parentAssertion instanceof SupportingTokens && ((localname = (supportingToken = (SupportingTokens)parentAssertion).getName().getLocalPart()).equals("SupportingTokens") || localname.equals("SignedSupportingTokens") || localname.equals("EncryptedSupportingTokens") || localname.equals("SignedEncryptedSupportingTokens"));
    }
}

