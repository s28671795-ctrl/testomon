/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.util.Collection;
import java.util.List;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractSecurityPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.SecurityContextToken;

public class SecurityContextTokenPolicyValidator
extends AbstractSecurityPolicyValidator {
    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        return assertionInfo.getAssertion() != null && (SP12Constants.SECURITY_CONTEXT_TOKEN.equals(assertionInfo.getAssertion().getName()) || SP11Constants.SECURITY_CONTEXT_TOKEN.equals(assertionInfo.getAssertion().getName()));
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        List<WSSecurityEngineResult> sctResults = parameters.getResults().getActionResults().get(1024);
        for (AssertionInfo ai : ais) {
            SecurityContextToken sctPolicy = (SecurityContextToken)ai.getAssertion();
            ai.setAsserted(true);
            this.assertToken(sctPolicy, parameters.getAssertionInfoMap());
            if (!this.isTokenRequired(sctPolicy, parameters.getMessage()) || sctResults != null && !sctResults.isEmpty()) continue;
            ai.setNotAsserted("The received token does not match the token inclusion requirement");
        }
    }

    private void assertToken(SecurityContextToken token, AssertionInfoMap aim) {
        if (token.isRequireExternalUriReference()) {
            PolicyUtils.assertPolicy(aim, SP12Constants.REQUIRE_EXTERNAL_URI_REFERENCE);
        }
        if (token.isSc10SecurityContextToken()) {
            PolicyUtils.assertPolicy(aim, SP11Constants.SC10_SECURITY_CONTEXT_TOKEN);
        }
        if (token.isSc13SecurityContextToken()) {
            PolicyUtils.assertPolicy(aim, SP12Constants.SC13_SECURITY_CONTEXT_TOKEN);
        }
    }
}

