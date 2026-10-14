/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy.interceptors;

import java.util.Arrays;
import java.util.Map;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.policy.AbstractPolicyInterceptorProvider;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.SecurityConstants;
import org.apache.cxf.ws.security.policy.interceptors.NegotiationUtils;
import org.apache.cxf.ws.security.policy.interceptors.SpnegoContextTokenInInterceptor;
import org.apache.cxf.ws.security.policy.interceptors.SpnegoContextTokenOutInterceptor;
import org.apache.cxf.ws.security.trust.STSClient;
import org.apache.neethi.All;
import org.apache.neethi.ExactlyOne;
import org.apache.neethi.Policy;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.AlgorithmSuite;

public class SpnegoTokenInterceptorProvider
extends AbstractPolicyInterceptorProvider {
    private static final long serialVersionUID = -3412111025613191505L;

    public SpnegoTokenInterceptorProvider() {
        super(Arrays.asList(SP11Constants.SPNEGO_CONTEXT_TOKEN, SP12Constants.SPNEGO_CONTEXT_TOKEN));
        SpnegoContextTokenOutInterceptor outInterceptor = new SpnegoContextTokenOutInterceptor();
        this.getOutInterceptors().add(outInterceptor);
        this.getOutFaultInterceptors().add(outInterceptor);
        SpnegoContextTokenInInterceptor inInterceptor = new SpnegoContextTokenInInterceptor();
        this.getInInterceptors().add(inInterceptor);
        this.getInFaultInterceptors().add(inInterceptor);
    }

    static String setupClient(STSClient client, SoapMessage message, AssertionInfoMap aim) {
        client.setTrust(NegotiationUtils.getTrust10(aim));
        client.setTrust(NegotiationUtils.getTrust13(aim));
        Policy p = new Policy();
        ExactlyOne ea = new ExactlyOne();
        p.addPolicyComponent(ea);
        All all = new All();
        all.addPolicyComponent(NegotiationUtils.getAddressingPolicy(aim, false));
        ea.addPolicyComponent(all);
        client.setPolicy(p);
        client.setSoap11(message.getVersion() == Soap11.getInstance());
        client.setSpnego(true);
        WSSConfig config = WSSConfig.getNewInstance();
        String context = config.getIdAllocator().createSecureId("_", null);
        client.setContext(context);
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
        SpnegoTokenInterceptorProvider.mapSecurityProps(message, ctx);
        return s2;
    }

    private static void mapSecurityProps(Message message, Map<String, Object> ctx) {
        for (String s2 : SecurityConstants.ALL_PROPERTIES) {
            Object v = message.getContextualProperty(s2);
            if (v == null) continue;
            ctx.put(s2, v);
        }
    }
}

