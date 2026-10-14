/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.security.wss4j.CryptoCoverageUtil;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractSecurityPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AbstractSupportingTokenPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AlgorithmSuitePolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.AsymmetricBindingPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.ConcreteSupportingTokenPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.EncryptedTokenPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.EndorsingEncryptedTokenPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.EndorsingTokenPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.IssuedTokenPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.KerberosTokenPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.LayoutPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.RequiredElementsPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.RequiredPartsPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SamlTokenPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SecuredElementsPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SecuredPartsPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SecurityContextTokenPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SecurityPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SignedEncryptedTokenPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SignedEndorsingEncryptedTokenPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SignedEndorsingTokenPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SignedTokenPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SymmetricBindingPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.TransportBindingPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.UsernameTokenPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.WSS11PolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.X509TokenPolicyValidator;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;

public final class ValidatorUtils {
    private static final Map<QName, SecurityPolicyValidator> DEFAULT_SECURITY_POLICY_VALIDATORS = new HashMap<QName, SecurityPolicyValidator>();

    private ValidatorUtils() {
    }

    private static void configureTokenValidators() {
        AbstractSecurityPolicyValidator validator = new X509TokenPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.X509_TOKEN, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.X509_TOKEN, validator);
        validator = new UsernameTokenPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.USERNAME_TOKEN, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.USERNAME_TOKEN, validator);
        validator = new SamlTokenPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.SAML_TOKEN, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.SAML_TOKEN, validator);
        validator = new SecurityContextTokenPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.SECURITY_CONTEXT_TOKEN, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.SECURITY_CONTEXT_TOKEN, validator);
        validator = new WSS11PolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.WSS11, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.WSS11, validator);
        validator = new IssuedTokenPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.ISSUED_TOKEN, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.ISSUED_TOKEN, validator);
        validator = new KerberosTokenPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.KERBEROS_TOKEN, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.KERBEROS_TOKEN, validator);
    }

    private static void configureBindingValidators() {
        SecurityPolicyValidator validator = new TransportBindingPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.TRANSPORT_BINDING, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.TRANSPORT_BINDING, validator);
        validator = new SymmetricBindingPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.SYMMETRIC_BINDING, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.SYMMETRIC_BINDING, validator);
        validator = new AsymmetricBindingPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.ASYMMETRIC_BINDING, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.ASYMMETRIC_BINDING, validator);
        validator = new AlgorithmSuitePolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.ALGORITHM_SUITE, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.ALGORITHM_SUITE, validator);
        validator = new LayoutPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.LAYOUT, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.LAYOUT, validator);
    }

    private static void configureSupportingTokenValidators() {
        AbstractSupportingTokenPolicyValidator validator = new ConcreteSupportingTokenPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.SUPPORTING_TOKENS, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.SUPPORTING_TOKENS, validator);
        validator = new SignedTokenPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.SIGNED_SUPPORTING_TOKENS, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.SIGNED_SUPPORTING_TOKENS, validator);
        validator = new EndorsingTokenPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.ENDORSING_SUPPORTING_TOKENS, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.ENDORSING_SUPPORTING_TOKENS, validator);
        validator = new SignedEndorsingTokenPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.SIGNED_ENDORSING_SUPPORTING_TOKENS, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.SIGNED_ENDORSING_SUPPORTING_TOKENS, validator);
        validator = new EncryptedTokenPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.ENCRYPTED_SUPPORTING_TOKENS, validator);
        validator = new SignedEncryptedTokenPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.SIGNED_ENCRYPTED_SUPPORTING_TOKENS, validator);
        validator = new EndorsingEncryptedTokenPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.ENDORSING_ENCRYPTED_SUPPORTING_TOKENS, validator);
        validator = new SignedEndorsingEncryptedTokenPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.SIGNED_ENDORSING_ENCRYPTED_SUPPORTING_TOKENS, validator);
    }

    private static void configurePartsValidators() {
        SecurityPolicyValidator validator = new SecuredPartsPolicyValidator();
        validator.setCoverageType(CryptoCoverageUtil.CoverageType.SIGNED);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.SIGNED_PARTS, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.SIGNED_PARTS, validator);
        validator = new SecuredPartsPolicyValidator();
        validator.setCoverageType(CryptoCoverageUtil.CoverageType.ENCRYPTED);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.ENCRYPTED_PARTS, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.ENCRYPTED_PARTS, validator);
        validator = new SecuredElementsPolicyValidator();
        ((SecuredElementsPolicyValidator)validator).setCoverageType(CryptoCoverageUtil.CoverageType.SIGNED);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.SIGNED_ELEMENTS, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.SIGNED_ELEMENTS, validator);
        validator = new SecuredElementsPolicyValidator();
        ((SecuredElementsPolicyValidator)validator).setCoverageType(CryptoCoverageUtil.CoverageType.ENCRYPTED);
        ((SecuredElementsPolicyValidator)validator).setCoverageScope(CryptoCoverageUtil.CoverageScope.ELEMENT);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.ENCRYPTED_ELEMENTS, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.ENCRYPTED_ELEMENTS, validator);
        validator = new SecuredElementsPolicyValidator();
        ((SecuredElementsPolicyValidator)validator).setCoverageType(CryptoCoverageUtil.CoverageType.ENCRYPTED);
        ((SecuredElementsPolicyValidator)validator).setCoverageScope(CryptoCoverageUtil.CoverageScope.CONTENT);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.CONTENT_ENCRYPTED_ELEMENTS, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.CONTENT_ENCRYPTED_ELEMENTS, validator);
        validator = new RequiredPartsPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.REQUIRED_PARTS, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.REQUIRED_PARTS, validator);
        validator = new RequiredElementsPolicyValidator();
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP12Constants.REQUIRED_ELEMENTS, validator);
        DEFAULT_SECURITY_POLICY_VALIDATORS.put(SP11Constants.REQUIRED_ELEMENTS, validator);
    }

    public static Map<QName, SecurityPolicyValidator> getSecurityPolicyValidators(Message message) {
        HashMap<QName, SecurityPolicyValidator> mapToReturn = new HashMap<QName, SecurityPolicyValidator>(DEFAULT_SECURITY_POLICY_VALIDATORS);
        Map policyMap = CastUtils.cast((Map)message.getContextualProperty("ws-security.policy.validator.map"));
        if (policyMap != null && !policyMap.isEmpty()) {
            mapToReturn.putAll(policyMap);
        }
        return mapToReturn;
    }

    static {
        ValidatorUtils.configureTokenValidators();
        ValidatorUtils.configureBindingValidators();
        ValidatorUtils.configureSupportingTokenValidators();
        ValidatorUtils.configurePartsValidators();
    }
}

