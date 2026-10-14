/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.SoapActionInInterceptor;
import org.apache.cxf.binding.soap.model.SoapBindingInfo;
import org.apache.cxf.binding.soap.model.SoapOperationInfo;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.policy.EffectivePolicy;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.WSS4JPolicyAsserter;
import org.apache.cxf.ws.security.wss4j.WSS4JStaxInInterceptor;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.stax.OperationPolicy;
import org.apache.wss4j.policy.stax.enforcer.PolicyEnforcer;
import org.apache.wss4j.policy.stax.enforcer.PolicyInputProcessor;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.securityToken.HttpsSecurityTokenImpl;
import org.apache.wss4j.stax.securityEvent.HttpsTokenSecurityEvent;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventListener;

public class PolicyBasedWSS4JStaxInInterceptor
extends WSS4JStaxInInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(PolicyBasedWSS4JStaxInInterceptor.class);

    @Override
    public void handleMessage(SoapMessage msg) throws Fault {
        AssertionInfoMap aim = msg.get(AssertionInfoMap.class);
        boolean enableStax = MessageUtils.getContextualBoolean(msg, "ws-security.enable.streaming");
        if (aim != null && enableStax) {
            super.handleMessage(msg);
        }
    }

    @Override
    protected WSSSecurityProperties createSecurityProperties() {
        WSSSecurityProperties securityProperties = new WSSSecurityProperties();
        securityProperties.setSkipDocumentEvents(true);
        return securityProperties;
    }

    private void checkAsymmetricBinding(AssertionInfoMap aim, SoapMessage message, WSSSecurityProperties securityProperties) throws WSSecurityException {
        Object e;
        AssertionInfo ais = PolicyUtils.getFirstAssertionByLocalname(aim, "AsymmetricBinding");
        if (ais == null) {
            return;
        }
        Object s2 = SecurityUtils.getSecurityPropertyValue("security.signature.crypto", message);
        if (s2 == null) {
            s2 = SecurityUtils.getSecurityPropertyValue("security.signature.properties", message);
        }
        if ((e = SecurityUtils.getSecurityPropertyValue("security.encryption.crypto", message)) == null) {
            e = SecurityUtils.getSecurityPropertyValue("security.encryption.properties", message);
        }
        Crypto encrCrypto = this.getEncryptionCrypto(e, message, securityProperties);
        Crypto signCrypto = null;
        signCrypto = e != null && e.equals(s2) ? encrCrypto : this.getSignatureCrypto(s2, message, securityProperties);
        if (signCrypto != null) {
            securityProperties.setDecryptionCrypto(signCrypto);
        }
        if (encrCrypto != null) {
            securityProperties.setSignatureVerificationCrypto(encrCrypto);
        } else if (signCrypto != null) {
            securityProperties.setSignatureVerificationCrypto(signCrypto);
        }
    }

    private void checkTransportBinding(AssertionInfoMap aim, SoapMessage message, WSSSecurityProperties securityProperties) throws XMLSecurityException {
        Object e;
        Object s2;
        boolean transportPolicyInEffect;
        boolean bl = transportPolicyInEffect = PolicyUtils.getFirstAssertionByLocalname(aim, "TransportBinding") != null;
        if (!(transportPolicyInEffect || PolicyUtils.getFirstAssertionByLocalname(aim, "SymmetricBinding") == null && PolicyUtils.getFirstAssertionByLocalname(aim, "AsymmetricBinding") == null)) {
            return;
        }
        if (this.isRequestor(message)) {
            HttpsTokenSecurityEvent httpsTokenSecurityEvent = new HttpsTokenSecurityEvent();
            httpsTokenSecurityEvent.setAuthenticationType(HttpsTokenSecurityEvent.AuthenticationType.HttpsNoAuthentication);
            HttpsSecurityTokenImpl httpsSecurityToken = new HttpsSecurityTokenImpl();
            try {
                httpsSecurityToken.addTokenUsage(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE);
            }
            catch (XMLSecurityException e2) {
                LOG.fine(e2.getMessage());
            }
            httpsTokenSecurityEvent.setSecurityToken(httpsSecurityToken);
            List<SecurityEvent> securityEvents = this.getSecurityEventList(message);
            securityEvents.add(httpsTokenSecurityEvent);
        }
        if ((s2 = SecurityUtils.getSecurityPropertyValue("security.signature.crypto", message)) == null) {
            s2 = SecurityUtils.getSecurityPropertyValue("security.signature.properties", message);
        }
        if ((e = SecurityUtils.getSecurityPropertyValue("security.encryption.crypto", message)) == null) {
            e = SecurityUtils.getSecurityPropertyValue("security.encryption.properties", message);
        }
        Crypto encrCrypto = this.getEncryptionCrypto(e, message, securityProperties);
        Crypto signCrypto = null;
        signCrypto = e != null && e.equals(s2) ? encrCrypto : this.getSignatureCrypto(s2, message, securityProperties);
        if (signCrypto != null) {
            securityProperties.setDecryptionCrypto(signCrypto);
        }
        if (encrCrypto != null) {
            securityProperties.setSignatureVerificationCrypto(encrCrypto);
        } else if (signCrypto != null) {
            securityProperties.setSignatureVerificationCrypto(signCrypto);
        }
    }

    private List<SecurityEvent> getSecurityEventList(Message message) {
        ArrayList securityEvents = (ArrayList)message.getExchange().get(SecurityEvent.class.getName() + ".out");
        if (securityEvents == null) {
            securityEvents = new ArrayList();
            message.getExchange().put(SecurityEvent.class.getName() + ".out", securityEvents);
        }
        return securityEvents;
    }

    private void checkSymmetricBinding(AssertionInfoMap aim, SoapMessage message, WSSSecurityProperties securityProperties) throws WSSecurityException {
        Object e;
        AssertionInfo ais = PolicyUtils.getFirstAssertionByLocalname(aim, "SymmetricBinding");
        if (ais == null) {
            return;
        }
        Object s2 = SecurityUtils.getSecurityPropertyValue("security.signature.crypto", message);
        if (s2 == null) {
            s2 = SecurityUtils.getSecurityPropertyValue("security.signature.properties", message);
        }
        if ((e = SecurityUtils.getSecurityPropertyValue("security.encryption.crypto", message)) == null) {
            e = SecurityUtils.getSecurityPropertyValue("security.encryption.properties", message);
        }
        Crypto encrCrypto = this.getEncryptionCrypto(e, message, securityProperties);
        Crypto signCrypto = null;
        signCrypto = e != null && e.equals(s2) ? encrCrypto : this.getSignatureCrypto(s2, message, securityProperties);
        if (this.isRequestor(message)) {
            Crypto crypto = encrCrypto;
            if (crypto == null) {
                crypto = signCrypto;
            }
            if (crypto != null) {
                securityProperties.setSignatureCrypto(crypto);
            }
            if ((crypto = signCrypto) == null) {
                crypto = encrCrypto;
            }
            if (crypto != null) {
                securityProperties.setDecryptionCrypto(crypto);
            }
        } else {
            Crypto crypto = signCrypto;
            if (crypto == null) {
                crypto = encrCrypto;
            }
            if (crypto != null) {
                securityProperties.setSignatureVerificationCrypto(crypto);
            }
            if ((crypto = encrCrypto) == null) {
                crypto = signCrypto;
            }
            if (crypto != null) {
                securityProperties.setDecryptionCrypto(crypto);
            }
        }
    }

    @Override
    protected void configureProperties(SoapMessage msg, WSSSecurityProperties securityProperties) throws XMLSecurityException {
        Collection algorithmSuites;
        AssertionInfoMap aim = msg.get(AssertionInfoMap.class);
        this.checkAsymmetricBinding(aim, msg, securityProperties);
        this.checkSymmetricBinding(aim, msg, securityProperties);
        this.checkTransportBinding(aim, msg, securityProperties);
        String asymSignatureAlgorithm = (String)msg.getContextualProperty("ws-security.asymmetric.signature.algorithm");
        String symSignatureAlgorithm = (String)msg.getContextualProperty("ws-security.symmetric.signature.algorithm");
        if (!(asymSignatureAlgorithm == null && symSignatureAlgorithm == null || (algorithmSuites = (Collection)aim.get(SP12Constants.ALGORITHM_SUITE)) == null || algorithmSuites.isEmpty())) {
            for (AssertionInfo algorithmSuite : algorithmSuites) {
                AlgorithmSuite algSuite = (AlgorithmSuite)algorithmSuite.getAssertion();
                if (asymSignatureAlgorithm != null) {
                    algSuite.setAsymmetricSignature(asymSignatureAlgorithm);
                }
                if (symSignatureAlgorithm == null) continue;
                algSuite.setSymmetricSignature(symSignatureAlgorithm);
            }
        }
        super.configureProperties(msg, securityProperties);
    }

    @Override
    protected boolean isNonceCacheRequired(SoapMessage msg, WSSSecurityProperties securityProperties) {
        AssertionInfo ais;
        AssertionInfoMap aim = msg.get(AssertionInfoMap.class);
        return aim != null && (ais = PolicyUtils.getFirstAssertionByLocalname(aim, "UsernameToken")) != null;
    }

    @Override
    protected boolean isTimestampCacheRequired(SoapMessage msg, WSSSecurityProperties securityProperties) {
        AssertionInfo ais;
        AssertionInfoMap aim = msg.get(AssertionInfoMap.class);
        return aim != null && (ais = PolicyUtils.getFirstAssertionByLocalname(aim, "IncludeTimestamp")) != null;
    }

    @Override
    protected boolean isSamlCacheRequired(SoapMessage msg, WSSSecurityProperties securityProperties) {
        AssertionInfo ais;
        AssertionInfoMap aim = msg.get(AssertionInfoMap.class);
        return aim != null && (ais = PolicyUtils.getFirstAssertionByLocalname(aim, "SamlToken")) != null;
    }

    @Override
    protected List<SecurityEventListener> configureSecurityEventListeners(SoapMessage msg, WSSSecurityProperties securityProperties) throws WSSPolicyException {
        ArrayList<SecurityEventListener> securityEventListeners = new ArrayList<SecurityEventListener>(2);
        securityEventListeners.addAll(super.configureSecurityEventListeners(msg, securityProperties));
        Endpoint endoint = msg.getExchange().getEndpoint();
        PolicyEnforcer policyEnforcer = this.createPolicyEnforcer(endoint.getEndpointInfo(), msg);
        securityProperties.addInputProcessor(new PolicyInputProcessor(policyEnforcer, securityProperties));
        securityEventListeners.add(policyEnforcer);
        return securityEventListeners;
    }

    private PolicyEnforcer createPolicyEnforcer(EndpointInfo endpointInfo, SoapMessage msg) throws WSSPolicyException {
        EffectivePolicy dispatchPolicy = null;
        ArrayList<OperationPolicy> operationPolicies = new ArrayList<OperationPolicy>();
        Collection<BindingOperationInfo> bindingOperationInfos = endpointInfo.getBinding().getOperations();
        for (BindingOperationInfo bindingOperationInfo : bindingOperationInfos) {
            SoapOperationInfo soapOperationInfo;
            QName cn;
            MessageInfo messageInfo;
            QName operationName = bindingOperationInfo.getName();
            EffectivePolicy policy = (EffectivePolicy)bindingOperationInfo.getProperty("policy-engine-info-serve-request");
            if (MessageUtils.isRequestor(msg)) {
                policy = (EffectivePolicy)bindingOperationInfo.getProperty("policy-engine-info-client-response");
                if (policy != null && "http://cxf.apache.org/jaxws/dispatch".equals(operationName.getNamespaceURI())) {
                    dispatchPolicy = policy;
                }
                if (bindingOperationInfo.getOutput() != null) {
                    messageInfo = bindingOperationInfo.getOutput().getMessageInfo();
                    operationName = messageInfo.getName();
                    if (messageInfo.getMessagePartsNumber() > 0 && (cn = messageInfo.getFirstMessagePart().getConcreteName()) != null) {
                        operationName = cn;
                    }
                }
            } else if (bindingOperationInfo.getInput() != null) {
                messageInfo = bindingOperationInfo.getInput().getMessageInfo();
                operationName = messageInfo.getName();
                if (messageInfo.getMessagePartsNumber() > 0 && (cn = messageInfo.getFirstMessagePart().getConcreteName()) != null) {
                    operationName = cn;
                }
            }
            if ((soapOperationInfo = bindingOperationInfo.getExtensor(SoapOperationInfo.class)) != null && policy == null && dispatchPolicy != null) {
                policy = dispatchPolicy;
            }
            if (policy == null || soapOperationInfo == null) continue;
            BindingInfo bindingInfo = bindingOperationInfo.getBinding();
            if (!(bindingInfo instanceof SoapBindingInfo)) {
                throw new IllegalArgumentException("BindingInfo is not an instance of SoapBindingInfo");
            }
            String soapNS = ((SoapBindingInfo)bindingInfo).getSoapVersion().getNamespace();
            OperationPolicy operationPolicy = new OperationPolicy(operationName);
            operationPolicy.setPolicy(policy.getPolicy());
            operationPolicy.setOperationAction(soapOperationInfo.getAction());
            operationPolicy.setSoapMessageVersionNamespace(soapNS);
            operationPolicies.add(operationPolicy);
        }
        String soapAction = SoapActionInInterceptor.getSoapAction(msg);
        if (soapAction == null) {
            soapAction = "";
        }
        String actor = (String)msg.getContextualProperty("ws-security.actor");
        Collection<Attachment> attachments = msg.getAttachments();
        int attachmentCount = 0;
        if (attachments != null && !attachments.isEmpty()) {
            attachmentCount = attachments.size();
        }
        return new PolicyEnforcer(operationPolicies, soapAction, this.isRequestor(msg), actor, attachmentCount, new WSS4JPolicyAsserter(msg.get(AssertionInfoMap.class)));
    }
}

