/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.WSS4JStaxOutInterceptor;
import org.apache.cxf.ws.security.wss4j.policyhandlers.StaxAsymmetricBindingHandler;
import org.apache.cxf.ws.security.wss4j.policyhandlers.StaxSymmetricBindingHandler;
import org.apache.cxf.ws.security.wss4j.policyhandlers.StaxTransportBindingHandler;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.policy.model.AsymmetricBinding;
import org.apache.wss4j.policy.model.SymmetricBinding;
import org.apache.wss4j.policy.model.TransportBinding;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.xml.security.stax.ext.OutboundSecurityContext;

public class PolicyBasedWSS4JStaxOutInterceptor
extends WSS4JStaxOutInterceptor {
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
        return new WSSSecurityProperties();
    }

    private void checkAsymmetricBinding(SoapMessage message, WSSSecurityProperties securityProperties) throws WSSecurityException {
        Object e;
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
            securityProperties.setSignatureCrypto(signCrypto);
        }
        if (encrCrypto != null) {
            securityProperties.setEncryptionCrypto(encrCrypto);
        } else if (signCrypto != null) {
            securityProperties.setEncryptionCrypto(signCrypto);
        }
    }

    private void checkTransportBinding(SoapMessage message, WSSSecurityProperties securityProperties) throws WSSecurityException {
        Object e;
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
            securityProperties.setSignatureCrypto(signCrypto);
        }
        if (encrCrypto != null) {
            securityProperties.setEncryptionCrypto(encrCrypto);
        } else if (signCrypto != null) {
            securityProperties.setEncryptionCrypto(signCrypto);
        }
    }

    private void checkSymmetricBinding(SoapMessage message, WSSSecurityProperties securityProperties) throws WSSecurityException {
        Object e;
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
                securityProperties.setEncryptionCrypto(crypto);
            }
            if ((crypto = signCrypto) == null) {
                crypto = encrCrypto;
            }
            if (crypto != null) {
                securityProperties.setSignatureCrypto(crypto);
            }
        } else {
            Crypto crypto = signCrypto;
            if (crypto == null) {
                crypto = encrCrypto;
            }
            if (crypto != null) {
                securityProperties.setEncryptionCrypto(crypto);
            }
            if ((crypto = encrCrypto) == null) {
                crypto = signCrypto;
            }
            if (crypto != null) {
                securityProperties.setSignatureCrypto(crypto);
            }
        }
    }

    @Override
    protected void configureProperties(SoapMessage msg, OutboundSecurityContext outboundSecurityContext, WSSSecurityProperties securityProperties) throws WSSecurityException {
        AssertionInfo transAis;
        AssertionInfo symAis;
        AssertionInfoMap aim = msg.get(AssertionInfoMap.class);
        AssertionInfo asymAis = PolicyUtils.getFirstAssertionByLocalname(aim, "AsymmetricBinding");
        if (asymAis != null) {
            this.checkAsymmetricBinding(msg, securityProperties);
            asymAis.setAsserted(true);
        }
        if ((symAis = PolicyUtils.getFirstAssertionByLocalname(aim, "SymmetricBinding")) != null) {
            this.checkSymmetricBinding(msg, securityProperties);
            symAis.setAsserted(true);
        }
        if ((transAis = PolicyUtils.getFirstAssertionByLocalname(aim, "TransportBinding")) != null) {
            this.checkTransportBinding(msg, securityProperties);
            transAis.setAsserted(true);
        }
        super.configureProperties(msg, outboundSecurityContext, securityProperties);
        if (transAis != null) {
            TransportBinding binding = (TransportBinding)transAis.getAssertion();
            new StaxTransportBindingHandler(securityProperties, msg, binding, outboundSecurityContext).handleBinding();
        } else if (asymAis != null) {
            AsymmetricBinding binding = (AsymmetricBinding)asymAis.getAssertion();
            new StaxAsymmetricBindingHandler(securityProperties, msg, binding, outboundSecurityContext).handleBinding();
        } else if (symAis != null) {
            SymmetricBinding binding = (SymmetricBinding)symAis.getAssertion();
            new StaxSymmetricBindingHandler(securityProperties, msg, binding, outboundSecurityContext).handleBinding();
        } else {
            new StaxTransportBindingHandler(securityProperties, msg, null, outboundSecurityContext).handleBinding();
        }
    }
}

