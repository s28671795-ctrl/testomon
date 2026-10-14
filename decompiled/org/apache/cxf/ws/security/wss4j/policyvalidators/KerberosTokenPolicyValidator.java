/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStoreUtils;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractSecurityPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.message.token.KerberosSecurity;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.KerberosToken;
import org.apache.xml.security.utils.XMLUtils;

public class KerberosTokenPolicyValidator
extends AbstractSecurityPolicyValidator {
    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        return assertionInfo.getAssertion() != null && (SP12Constants.KERBEROS_TOKEN.equals(assertionInfo.getAssertion().getName()) || SP11Constants.KERBEROS_TOKEN.equals(assertionInfo.getAssertion().getName()));
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        List<WSSecurityEngineResult> kerberosResults = this.findKerberosResults(parameters.getResults().getActionResults().get(4096));
        for (WSSecurityEngineResult kerberosResult : kerberosResults) {
            KerberosSecurity kerberosToken = (KerberosSecurity)kerberosResult.get("binary-security-token");
            boolean asserted = true;
            for (AssertionInfo ai : ais) {
                KerberosToken kerberosTokenPolicy = (KerberosToken)ai.getAssertion();
                ai.setAsserted(true);
                this.assertToken(kerberosTokenPolicy, parameters.getAssertionInfoMap());
                if (!this.isTokenRequired(kerberosTokenPolicy, parameters.getMessage())) {
                    PolicyUtils.assertPolicy(parameters.getAssertionInfoMap(), new QName(kerberosTokenPolicy.getVersion().getNamespace(), "WssKerberosV5ApReqToken11"));
                    PolicyUtils.assertPolicy(parameters.getAssertionInfoMap(), new QName(kerberosTokenPolicy.getVersion().getNamespace(), "WssGssKerberosV5ApReqToken11"));
                    continue;
                }
                if (this.checkToken(parameters.getAssertionInfoMap(), kerberosTokenPolicy, kerberosToken)) continue;
                asserted = false;
                ai.setNotAsserted("An incorrect Kerberos Token Type is detected");
            }
            if (!asserted) continue;
            SecurityToken token = this.createSecurityToken(kerberosToken);
            token.setSecret((byte[])kerberosResult.get("secret"));
            TokenStoreUtils.getTokenStore(parameters.getMessage()).add(token);
            parameters.getMessage().getExchange().put("ws-security.token.id", token.getId());
            return;
        }
    }

    private void assertToken(KerberosToken token, AssertionInfoMap aim) {
        String namespace = token.getName().getNamespaceURI();
        if (token.isRequireKeyIdentifierReference()) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, "RequireKeyIdentifierReference"));
        }
    }

    private boolean checkToken(AssertionInfoMap aim, KerberosToken kerberosTokenPolicy, KerberosSecurity kerberosToken) {
        KerberosToken.ApReqTokenType apReqTokenType = kerberosTokenPolicy.getApReqTokenType();
        if (apReqTokenType == KerberosToken.ApReqTokenType.WssKerberosV5ApReqToken11 && kerberosToken.isV5ApReq()) {
            PolicyUtils.assertPolicy(aim, new QName(kerberosTokenPolicy.getVersion().getNamespace(), "WssKerberosV5ApReqToken11"));
            return true;
        }
        if (apReqTokenType == KerberosToken.ApReqTokenType.WssGssKerberosV5ApReqToken11 && kerberosToken.isGssV5ApReq()) {
            PolicyUtils.assertPolicy(aim, new QName(kerberosTokenPolicy.getVersion().getNamespace(), "WssGssKerberosV5ApReqToken11"));
            return true;
        }
        return false;
    }

    private List<WSSecurityEngineResult> findKerberosResults(List<WSSecurityEngineResult> bstResults) {
        ArrayList<WSSecurityEngineResult> results = new ArrayList<WSSecurityEngineResult>();
        if (bstResults != null) {
            for (WSSecurityEngineResult wser : bstResults) {
                BinarySecurity binarySecurity = (BinarySecurity)wser.get("binary-security-token");
                if (!(binarySecurity instanceof KerberosSecurity)) continue;
                results.add(wser);
            }
        }
        return results;
    }

    private SecurityToken createSecurityToken(KerberosSecurity binarySecurityToken) {
        SecurityToken token = new SecurityToken(binarySecurityToken.getID());
        token.setToken(binarySecurityToken.getElement());
        token.setTokenType(binarySecurityToken.getValueType());
        byte[] tokenBytes = binarySecurityToken.getToken();
        try {
            token.setSHA1(XMLUtils.encodeToString(KeyUtils.generateDigest(tokenBytes)));
        }
        catch (WSSecurityException wSSecurityException) {
            // empty catch block
        }
        return token;
    }
}

