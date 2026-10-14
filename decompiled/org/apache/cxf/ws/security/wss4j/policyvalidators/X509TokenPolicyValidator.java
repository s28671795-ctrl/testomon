/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractSecurityPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.token.BinarySecurity;
import org.apache.wss4j.common.token.X509Security;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.str.STRParser;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.X509Token;
import org.w3c.dom.Element;

public class X509TokenPolicyValidator
extends AbstractSecurityPolicyValidator {
    private static final Logger LOG = LogUtils.getL7dLogger(X509TokenPolicyValidator.class);
    private static final String X509_V3_VALUETYPE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3";
    private static final String PKI_VALUETYPE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1";

    @Override
    public boolean canValidatePolicy(AssertionInfo assertionInfo) {
        return assertionInfo.getAssertion() != null && (SP12Constants.X509_TOKEN.equals(assertionInfo.getAssertion().getName()) || SP11Constants.X509_TOKEN.equals(assertionInfo.getAssertion().getName()));
    }

    @Override
    public void validatePolicies(PolicyValidatorParameters parameters, Collection<AssertionInfo> ais) {
        List<WSSecurityEngineResult> bstResults = parameters.getResults().getActionResults().get(4096);
        for (AssertionInfo ai : ais) {
            X509Token x509TokenPolicy = (X509Token)ai.getAssertion();
            ai.setAsserted(true);
            this.assertToken(x509TokenPolicy, parameters.getAssertionInfoMap());
            if (!this.isTokenRequired(x509TokenPolicy, parameters.getMessage())) continue;
            if ((bstResults == null || bstResults.isEmpty()) && parameters.getSignedResults().isEmpty()) {
                ai.setNotAsserted("The received token does not match the token inclusion requirement");
                continue;
            }
            if (this.checkTokenType(x509TokenPolicy.getTokenType(), bstResults, parameters.getSignedResults())) continue;
            ai.setNotAsserted("An incorrect X.509 Token Type is detected");
        }
    }

    private void assertToken(X509Token token, AssertionInfoMap aim) {
        X509Token.TokenType tokenType;
        String namespace = token.getName().getNamespaceURI();
        if (token.isRequireIssuerSerialReference()) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, "RequireIssuerSerialReference"));
        }
        if (token.isRequireThumbprintReference()) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, "RequireThumbprintReference"));
        }
        if (token.isRequireEmbeddedTokenReference()) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, "RequireEmbeddedTokenReference"));
        }
        if (token.isRequireKeyIdentifierReference()) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, "RequireKeyIdentifierReference"));
        }
        if ((tokenType = token.getTokenType()) != null) {
            PolicyUtils.assertPolicy(aim, new QName(namespace, tokenType.name()));
        }
    }

    private boolean checkTokenType(X509Token.TokenType tokenType, List<WSSecurityEngineResult> bstResults, List<WSSecurityEngineResult> signedResults) {
        if ((bstResults == null || bstResults.isEmpty()) && signedResults.isEmpty()) {
            return false;
        }
        String requiredType = X509_V3_VALUETYPE;
        boolean v3certRequired = false;
        if (tokenType == X509Token.TokenType.WssX509PkiPathV1Token10 || tokenType == X509Token.TokenType.WssX509PkiPathV1Token11) {
            requiredType = PKI_VALUETYPE;
        } else if (tokenType == X509Token.TokenType.WssX509V3Token10 || tokenType == X509Token.TokenType.WssX509V3Token11) {
            v3certRequired = true;
        }
        if (bstResults != null) {
            for (WSSecurityEngineResult result : bstResults) {
                BinarySecurity binarySecurityToken = (BinarySecurity)result.get("binary-security-token");
                if (binarySecurityToken == null || !requiredType.equals(binarySecurityToken.getValueType())) continue;
                if (v3certRequired && binarySecurityToken instanceof X509Security) {
                    try {
                        X509Certificate cert = ((X509Security)binarySecurityToken).getX509Certificate(null);
                        if (cert == null || cert.getVersion() != 3) continue;
                        return true;
                    }
                    catch (WSSecurityException e) {
                        LOG.log(Level.FINE, e.getMessage());
                        continue;
                    }
                }
                return true;
            }
        }
        if (X509_V3_VALUETYPE.equals(requiredType)) {
            for (WSSecurityEngineResult result : signedResults) {
                Element signatureElement;
                Element keyIdentifier;
                STRParser.REFERENCE_TYPE referenceType = (STRParser.REFERENCE_TYPE)((Object)result.get("x509-reference-type"));
                if (STRParser.REFERENCE_TYPE.KEY_IDENTIFIER != referenceType || (keyIdentifier = this.getKeyIdentifier(signatureElement = (Element)result.get("token-element"))) == null || !X509_V3_VALUETYPE.equals(keyIdentifier.getAttributeNS(null, "ValueType"))) continue;
                try {
                    X509Security token = new X509Security(keyIdentifier, new BSPEnforcer(true));
                    X509Certificate cert = token.getX509Certificate(null);
                    if (cert == null || cert.getVersion() != 3) continue;
                    return true;
                }
                catch (WSSecurityException e) {
                    LOG.log(Level.FINE, e.getMessage());
                }
            }
        }
        return false;
    }

    private Element getKeyIdentifier(Element signatureElement) {
        Element strElement;
        Element keyInfoElement;
        if (signatureElement != null && (keyInfoElement = XMLUtils.getDirectChildElement(signatureElement, "KeyInfo", "http://www.w3.org/2000/09/xmldsig#")) != null && (strElement = XMLUtils.getDirectChildElement(keyInfoElement, "SecurityTokenReference", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")) != null) {
            return XMLUtils.getDirectChildElement(strElement, "KeyIdentifier", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        }
        return null;
    }
}

