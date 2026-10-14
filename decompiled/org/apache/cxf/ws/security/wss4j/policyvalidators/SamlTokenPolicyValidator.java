/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.security.cert.Certificate;
import java.util.Collection;
import javax.xml.namespace.QName;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.security.transport.TLSSessionInfo;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractSamlPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.saml.DOMSAMLUtil;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.SamlToken;
import org.opensaml.saml.common.SAMLVersion;

public class SamlTokenPolicyValidator
extends AbstractSamlPolicyValidator {
    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        return assertionInfo.getAssertion() != null && (SP12Constants.SAML_TOKEN.equals(assertionInfo.getAssertion().getName()) || SP11Constants.SAML_TOKEN.equals(assertionInfo.getAssertion().getName()));
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        for (AssertionInfo ai : ais) {
            SamlToken samlToken = (SamlToken)ai.getAssertion();
            ai.setAsserted(true);
            this.assertToken(samlToken, parameters.getAssertionInfoMap());
            if (!this.isTokenRequired(samlToken, parameters.getMessage())) {
                PolicyUtils.assertPolicy(parameters.getAssertionInfoMap(), new QName(samlToken.getVersion().getNamespace(), samlToken.getSamlTokenType().name()));
                continue;
            }
            if (parameters.getSamlResults().isEmpty()) {
                ai.setNotAsserted("The received token does not match the token inclusion requirement");
                continue;
            }
            String valSAMLSubjectConf = (String)SecurityUtils.getSecurityPropertyValue("security.validate.saml.subject.conf", parameters.getMessage());
            boolean validateSAMLSubjectConf = true;
            if (valSAMLSubjectConf != null) {
                validateSAMLSubjectConf = Boolean.parseBoolean(valSAMLSubjectConf);
            }
            for (WSSecurityEngineResult result : parameters.getSamlResults()) {
                SamlAssertionWrapper assertionWrapper = (SamlAssertionWrapper)result.get("saml-assertion");
                if (!this.checkVersion(parameters.getAssertionInfoMap(), samlToken, assertionWrapper)) {
                    ai.setNotAsserted("Wrong SAML Version");
                    continue;
                }
                if (!validateSAMLSubjectConf) continue;
                TLSSessionInfo tlsInfo = parameters.getMessage().get(TLSSessionInfo.class);
                Certificate[] tlsCerts = null;
                if (tlsInfo != null) {
                    tlsCerts = tlsInfo.getPeerCertificates();
                }
                if (!this.checkHolderOfKey(assertionWrapper, parameters.getSignedResults(), tlsCerts)) {
                    ai.setNotAsserted("Assertion fails holder-of-key requirements");
                    continue;
                }
                if (parameters.getSoapBody() != null && DOMSAMLUtil.checkSenderVouches(assertionWrapper, tlsCerts, parameters.getSoapBody(), parameters.getSignedResults())) continue;
                ai.setNotAsserted("Assertion fails sender-vouches requirements");
            }
        }
    }

    private boolean checkVersion(AssertionInfoMap aim, SamlToken samlToken, SamlAssertionWrapper assertionWrapper) {
        SamlToken.SamlTokenType samlTokenType = samlToken.getSamlTokenType();
        if ((samlTokenType == SamlToken.SamlTokenType.WssSamlV11Token10 || samlTokenType == SamlToken.SamlTokenType.WssSamlV11Token11) && assertionWrapper.getSamlVersion() != SAMLVersion.VERSION_11) {
            return false;
        }
        if (samlTokenType == SamlToken.SamlTokenType.WssSamlV20Token11 && assertionWrapper.getSamlVersion() != SAMLVersion.VERSION_20) {
            return false;
        }
        if (samlTokenType != null) {
            PolicyUtils.assertPolicy(aim, new QName(samlToken.getVersion().getNamespace(), samlTokenType.name()));
        }
        return true;
    }

    private void assertToken(SamlToken token, AssertionInfoMap aim) {
        String namespace = token.getName().getNamespaceURI();
        if (token.isRequireKeyIdentifierReference()) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, "RequireKeyIdentifierReference"));
        }
    }
}

