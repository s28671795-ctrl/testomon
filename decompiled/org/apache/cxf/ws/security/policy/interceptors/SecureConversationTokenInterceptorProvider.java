/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy.interceptors;

import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.policy.AbstractPolicyInterceptorProvider;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.SecurityConstants;
import org.apache.cxf.ws.security.policy.interceptors.NegotiationUtils;
import org.apache.cxf.ws.security.policy.interceptors.SecureConversationInInterceptor;
import org.apache.cxf.ws.security.policy.interceptors.SecureConversationOutInterceptor;
import org.apache.cxf.ws.security.trust.STSClient;
import org.apache.neethi.All;
import org.apache.neethi.ExactlyOne;
import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.model.SecureConversationToken;
import org.apache.wss4j.policy.model.SupportingTokens;

public class SecureConversationTokenInterceptorProvider
extends AbstractPolicyInterceptorProvider {
    private static final long serialVersionUID = 8739057200687855383L;
    private static final Logger LOG = LogUtils.getL7dLogger(SecureConversationTokenInterceptorProvider.class);

    public SecureConversationTokenInterceptorProvider() {
        super(Arrays.asList(SP11Constants.SECURE_CONVERSATION_TOKEN, SP12Constants.SECURE_CONVERSATION_TOKEN, SP12Constants.BOOTSTRAP_POLICY, SP11Constants.BOOTSTRAP_POLICY));
        SecureConversationOutInterceptor outInterceptor = new SecureConversationOutInterceptor();
        this.getOutInterceptors().add(outInterceptor);
        this.getOutFaultInterceptors().add(outInterceptor);
        SecureConversationInInterceptor inInterceptor = new SecureConversationInInterceptor();
        this.getInInterceptors().add(inInterceptor);
        this.getInFaultInterceptors().add(inInterceptor);
    }

    static String setupClient(STSClient client, SoapMessage message, AssertionInfoMap aim, SecureConversationToken itok, boolean endorse) {
        if (itok.getBootstrapPolicy() == null || itok.getBootstrapPolicy().getPolicy() == null) {
            throw new Fault("The SecureConversationToken does not define a BootstrapPolicy", LOG);
        }
        client.setTrust(NegotiationUtils.getTrust10(aim));
        client.setTrust(NegotiationUtils.getTrust13(aim));
        Policy pol = itok.getBootstrapPolicy().getPolicy();
        Policy p = new Policy();
        ExactlyOne ea = new ExactlyOne();
        p.addPolicyComponent(ea);
        All all = new All();
        all.addPolicyComponent(NegotiationUtils.getAddressingPolicy(aim, false));
        ea.addPolicyComponent(all);
        if (endorse) {
            SupportingTokens st = new SupportingTokens(SPConstants.SPVersion.SP12, SP12Constants.ENDORSING_SUPPORTING_TOKENS, new Policy());
            st.addToken(itok);
            all.addPolicyComponent(st);
        }
        pol = p.merge(pol);
        client.setPolicy(pol);
        client.setSoap11(message.getVersion() == Soap11.getInstance());
        client.setSecureConv(true);
        String s2 = message.getContextualProperty(Message.ENDPOINT_ADDRESS).toString();
        client.setLocation(s2);
        AlgorithmSuite suite = NegotiationUtils.getAlgorithmSuite(aim);
        if (suite != null) {
            client.setAlgorithmSuite(suite);
            int x = suite.getAlgorithmSuiteType().getMaximumSymmetricKeyLength();
            if (x < 256) {
                client.setKeySize(x);
            }
        }
        Map<String, Object> ctx = client.getRequestContext();
        SecureConversationTokenInterceptorProvider.mapSecurityProps(message, ctx);
        return s2;
    }

    private static void mapSecurityProps(Message message, Map<String, Object> ctx) {
        for (String s2 : SecurityConstants.ALL_PROPERTIES) {
            Object v = message.getContextualProperty(s2 + ".sct");
            if (v == null) {
                v = message.getContextualProperty(s2);
            }
            if (v == null || s2.equals("ws-security.token") || s2.equals("ws-security.token.id")) continue;
            ctx.put(s2, v);
        }
    }
}

