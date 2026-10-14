/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy.interceptors;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.xml.namespace.QName;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.ws.policy.AbstractPolicyInterceptorProvider;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.kerberos.KerberosClient;
import org.apache.cxf.ws.security.kerberos.KerberosUtils;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStoreUtils;
import org.apache.cxf.ws.security.wss4j.KerberosTokenInterceptor;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JStaxInInterceptor;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JStaxOutInterceptor;
import org.apache.cxf.ws.security.wss4j.StaxSecurityContextInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SecurityPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.ValidatorUtils;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.KeyUtils;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.stax.securityEvent.KerberosTokenSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.wss4j.stax.securityToken.KerberosServiceSecurityToken;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.utils.XMLUtils;

public class KerberosTokenInterceptorProvider
extends AbstractPolicyInterceptorProvider {
    private static final long serialVersionUID = 5922028830873137490L;

    public KerberosTokenInterceptorProvider() {
        super(Arrays.asList(SP11Constants.KERBEROS_TOKEN, SP12Constants.KERBEROS_TOKEN));
        KerberosTokenOutInterceptor outInterceptor = new KerberosTokenOutInterceptor();
        this.getOutInterceptors().add(outInterceptor);
        this.getOutFaultInterceptors().add(outInterceptor);
        KerberosTokenDOMInInterceptor domInInterceptor = new KerberosTokenDOMInInterceptor();
        this.getInInterceptors().add(domInInterceptor);
        this.getInFaultInterceptors().add(domInInterceptor);
        KerberosTokenStaxInInterceptor staxInInterceptor = new KerberosTokenStaxInInterceptor();
        this.getInInterceptors().add(staxInInterceptor);
        this.getInFaultInterceptors().add(staxInInterceptor);
        this.getOutInterceptors().add(new KerberosTokenInterceptor());
        this.getInInterceptors().add(new KerberosTokenInterceptor());
        PolicyBasedWSS4JStaxOutInterceptor so = new PolicyBasedWSS4JStaxOutInterceptor();
        PolicyBasedWSS4JStaxInInterceptor si = new PolicyBasedWSS4JStaxInInterceptor();
        this.getOutInterceptors().add(so);
        this.getOutFaultInterceptors().add(so);
        this.getInInterceptors().add(si);
        this.getInFaultInterceptors().add(si);
    }

    static class KerberosTokenStaxInInterceptor
    extends AbstractPhaseInterceptor<Message> {
        private static final Logger LOG = LogUtils.getL7dLogger(KerberosTokenStaxInInterceptor.class);

        KerberosTokenStaxInInterceptor() {
            super("pre-protocol");
            this.getBefore().add(StaxSecurityContextInInterceptor.class.getName());
        }

        @Override
        public void handleMessage(Message message) throws Fault {
            block3: {
                AssertionInfoMap aim;
                block5: {
                    Collection<AssertionInfo> ais;
                    block4: {
                        aim = message.get(AssertionInfoMap.class);
                        boolean enableStax = MessageUtils.getContextualBoolean(message, "ws-security.enable.streaming");
                        if (aim == null || !enableStax) break block3;
                        ais = PolicyUtils.getAllAssertionsByLocalname(aim, "KerberosToken");
                        if (ais.isEmpty()) {
                            return;
                        }
                        if (this.isRequestor(message)) break block4;
                        SecurityEvent event = this.findKerberosEvent(message);
                        if (event == null) break block5;
                        for (AssertionInfo ai : ais) {
                            ai.setAsserted(true);
                        }
                        KerberosServiceSecurityToken kerberosToken = (KerberosServiceSecurityToken)((KerberosTokenSecurityEvent)event).getSecurityToken();
                        if (kerberosToken == null) break block5;
                        this.storeKerberosToken(message, kerberosToken);
                        break block5;
                    }
                    for (AssertionInfo ai : ais) {
                        ai.setAsserted(true);
                    }
                }
                PolicyUtils.assertPolicy(aim, "WssKerberosV5ApReqToken11");
                PolicyUtils.assertPolicy(aim, "WssGssKerberosV5ApReqToken11");
            }
        }

        private void storeKerberosToken(Message message, KerberosServiceSecurityToken kerberosToken) {
            SecurityToken token = new SecurityToken(kerberosToken.getId());
            token.setTokenType(kerberosToken.getKerberosTokenValueType());
            SecretKey secretKey = this.getSecretKeyFromToken(kerberosToken);
            token.setKey(secretKey);
            if (secretKey != null) {
                token.setSecret(secretKey.getEncoded());
            }
            byte[] ticket = kerberosToken.getBinaryContent();
            try {
                token.setSHA1(XMLUtils.encodeToString(KeyUtils.generateDigest(ticket)));
            }
            catch (WSSecurityException wSSecurityException) {
                // empty catch block
            }
            TokenStoreUtils.getTokenStore(message).add(token);
            message.getExchange().put("ws-security.token.id", token.getId());
        }

        private SecurityEvent findKerberosEvent(Message message) {
            List incomingEventList = (List)message.get(SecurityEvent.class.getName() + ".in");
            if (incomingEventList != null) {
                for (SecurityEvent incomingEvent : incomingEventList) {
                    if (WSSecurityEventConstants.KERBEROS_TOKEN != incomingEvent.getSecurityEventType()) continue;
                    return incomingEvent;
                }
            }
            return null;
        }

        private SecretKey getSecretKeyFromToken(KerberosServiceSecurityToken kerberosToken) {
            try {
                Map<String, Key> secretKeys = kerberosToken.getSecretKey();
                if (secretKeys != null) {
                    SecretKey foundKey = null;
                    for (Map.Entry<String, Key> entry : kerberosToken.getSecretKey().entrySet()) {
                        if (!(entry.getValue() instanceof SecretKey)) continue;
                        SecretKey secretKey = (SecretKey)entry.getValue();
                        if (foundKey != null && secretKey.getEncoded().length <= foundKey.getEncoded().length) continue;
                        foundKey = secretKey;
                    }
                    return foundKey;
                }
            }
            catch (XMLSecurityException e) {
                LOG.fine(e.getMessage());
            }
            return null;
        }
    }

    static class KerberosTokenDOMInInterceptor
    extends AbstractPhaseInterceptor<Message> {
        KerberosTokenDOMInInterceptor() {
            super("pre-protocol");
            this.addAfter(WSS4JInInterceptor.class.getName());
            this.addAfter(PolicyBasedWSS4JInInterceptor.class.getName());
        }

        @Override
        public void handleMessage(Message message) throws Fault {
            AssertionInfoMap aim = message.get(AssertionInfoMap.class);
            boolean enableStax = MessageUtils.getContextualBoolean(message, "ws-security.enable.streaming");
            if (aim != null && !enableStax) {
                Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "KerberosToken");
                if (ais.isEmpty()) {
                    return;
                }
                if (!this.isRequestor(message)) {
                    List results = CastUtils.cast((List)message.get("RECV_RESULTS"));
                    if (results != null && !results.isEmpty()) {
                        this.parseHandlerResults((WSHandlerResult)results.get(0), message, ais);
                    }
                } else {
                    for (AssertionInfo ai : ais) {
                        ai.setAsserted(true);
                    }
                }
                PolicyUtils.assertPolicy(aim, "WssKerberosV5ApReqToken11");
                PolicyUtils.assertPolicy(aim, "WssGssKerberosV5ApReqToken11");
            }
        }

        private void parseHandlerResults(WSHandlerResult rResult, Message message, Collection<AssertionInfo> ais) {
            PolicyValidatorParameters parameters = new PolicyValidatorParameters();
            parameters.setAssertionInfoMap(message.get(AssertionInfoMap.class));
            parameters.setMessage(message);
            parameters.setResults(rResult);
            QName qName = ais.iterator().next().getAssertion().getName();
            Map<QName, SecurityPolicyValidator> validators = ValidatorUtils.getSecurityPolicyValidators(message);
            if (validators.containsKey(qName)) {
                validators.get(qName).validatePolicies(parameters, ais);
            }
        }
    }

    static class KerberosTokenOutInterceptor
    extends AbstractPhaseInterceptor<Message> {
        KerberosTokenOutInterceptor() {
            super("prepare-send");
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void handleMessage(Message message) throws Fault {
            block10: {
                AssertionInfoMap aim;
                block12: {
                    Collection<AssertionInfo> ais;
                    block11: {
                        aim = message.get(AssertionInfoMap.class);
                        if (aim == null) break block10;
                        ais = PolicyUtils.getAllAssertionsByLocalname(aim, "KerberosToken");
                        if (ais.isEmpty()) {
                            return;
                        }
                        if (!this.isRequestor(message)) break block11;
                        SecurityToken tok = null;
                        try {
                            KerberosClient client;
                            KerberosClient kerberosClient = client = KerberosUtils.getClient(message, "kerberos");
                            synchronized (kerberosClient) {
                                tok = client.requestSecurityToken();
                            }
                        }
                        catch (RuntimeException e) {
                            throw e;
                        }
                        catch (Exception e) {
                            throw new Fault(e);
                        }
                        if (tok == null) break block12;
                        for (AssertionInfo ai : ais) {
                            ai.setAsserted(true);
                        }
                        message.getExchange().getEndpoint().put("ws-security.token.id", tok.getId());
                        message.getExchange().put("ws-security.token.id", tok.getId());
                        TokenStoreUtils.getTokenStore(message).add(tok);
                        if (tok.getSHA1() == null) break block12;
                        TokenStoreUtils.getTokenStore(message).add(tok.getSHA1(), tok);
                        break block12;
                    }
                    for (AssertionInfo ai : ais) {
                        ai.setAsserted(true);
                    }
                }
                PolicyUtils.assertPolicy(aim, "WssKerberosV5ApReqToken11");
                PolicyUtils.assertPolicy(aim, "WssGssKerberosV5ApReqToken11");
            }
        }
    }
}

