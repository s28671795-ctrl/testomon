/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.util.Collection;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractSecurityPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.Wss11;

public class WSS11PolicyValidator
extends AbstractSecurityPolicyValidator {
    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        return assertionInfo.getAssertion() != null && (SP12Constants.WSS11.equals(assertionInfo.getAssertion().getName()) || SP11Constants.WSS11.equals(assertionInfo.getAssertion().getName()));
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        List<WSSecurityEngineResult> scResults = parameters.getResults().getActionResults().get(128);
        for (AssertionInfo ai : ais) {
            Wss11 wss11 = (Wss11)ai.getAssertion();
            ai.setAsserted(true);
            this.assertToken(wss11, parameters.getAssertionInfoMap());
            if (!MessageUtils.isRequestor(parameters.getMessage()) || (!wss11.isRequireSignatureConfirmation() || scResults != null && !scResults.isEmpty()) && (wss11.isRequireSignatureConfirmation() || scResults == null || scResults.isEmpty())) continue;
            ai.setNotAsserted("Signature Confirmation policy validation failed");
        }
    }

    private void assertToken(Wss11 token, AssertionInfoMap aim) {
        String namespace = token.getName().getNamespaceURI();
        if (token.isMustSupportRefEmbeddedToken()) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, "MustSupportRefEmbeddedToken"));
        }
        if (token.isMustSupportRefEncryptedKey()) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, "MustSupportRefEncryptedKey"));
        }
        if (token.isMustSupportRefExternalURI()) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, "MustSupportRefExternalURI"));
        }
        if (token.isMustSupportRefIssuerSerial()) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, "MustSupportRefIssuerSerial"));
        }
        if (token.isMustSupportRefKeyIdentifier()) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, "MustSupportRefKeyIdentifier"));
        }
        if (token.isMustSupportRefThumbprint()) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, "MustSupportRefThumbprint"));
        }
        if (token.isRequireSignatureConfirmation()) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, "RequireSignatureConfirmation"));
        }
    }
}

