/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy.interceptors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.ws.policy.AbstractPolicyInterceptorProvider;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.policy.interceptors.NegotiationUtils;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.trust.DefaultSTSTokenCacher;
import org.apache.cxf.ws.security.trust.STSTokenCacher;
import org.apache.cxf.ws.security.trust.STSTokenRetriever;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JOutInterceptor;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JStaxInInterceptor;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JStaxOutInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SecurityPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.ValidatorUtils;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.IssuedToken;

public class IssuedTokenInterceptorProvider
extends AbstractPolicyInterceptorProvider {
    private static final long serialVersionUID = -6936475570762840527L;

    public IssuedTokenInterceptorProvider() {
        super(Arrays.asList(SP11Constants.ISSUED_TOKEN, SP12Constants.ISSUED_TOKEN));
        PolicyBasedWSS4JInInterceptor in = new PolicyBasedWSS4JInInterceptor();
        this.getOutInterceptors().add(PolicyBasedWSS4JOutInterceptor.INSTANCE);
        this.getOutFaultInterceptors().add(PolicyBasedWSS4JOutInterceptor.INSTANCE);
        this.getInInterceptors().add(in);
        this.getInFaultInterceptors().add(in);
        IssuedTokenOutInterceptor outInterceptor = new IssuedTokenOutInterceptor();
        this.getOutInterceptors().add(outInterceptor);
        this.getOutFaultInterceptors().add(outInterceptor);
        IssuedTokenInInterceptor inInterceptor = new IssuedTokenInInterceptor();
        this.getInInterceptors().add(inInterceptor);
        this.getInFaultInterceptors().add(inInterceptor);
        PolicyBasedWSS4JStaxOutInterceptor so = new PolicyBasedWSS4JStaxOutInterceptor();
        PolicyBasedWSS4JStaxInInterceptor si = new PolicyBasedWSS4JStaxInInterceptor();
        this.getOutInterceptors().add(so);
        this.getOutFaultInterceptors().add(so);
        this.getInInterceptors().add(si);
        this.getInFaultInterceptors().add(si);
    }

    protected static void assertIssuedToken(IssuedToken issuedToken, AssertionInfoMap aim) {
        if (issuedToken == null) {
            return;
        }
        if (issuedToken.isRequireExternalReference()) {
            PolicyUtils.assertPolicy(aim, new QName(issuedToken.getName().getNamespaceURI(), "RequireExternalReference"));
        }
        if (issuedToken.isRequireInternalReference()) {
            PolicyUtils.assertPolicy(aim, new QName(issuedToken.getName().getNamespaceURI(), "RequireInternalReference"));
        }
    }

    static class IssuedTokenInInterceptor
    extends AbstractPhaseInterceptor<Message> {
        IssuedTokenInInterceptor() {
            super("pre-protocol");
            this.addAfter(WSS4JInInterceptor.class.getName());
            this.addAfter(PolicyBasedWSS4JInInterceptor.class.getName());
        }

        @Override
        public void handleMessage(Message message) throws Fault {
            AssertionInfoMap aim = message.get(AssertionInfoMap.class);
            if (aim != null) {
                Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "IssuedToken");
                if (ais.isEmpty()) {
                    return;
                }
                IssuedToken itok = (IssuedToken)ais.iterator().next().getAssertion();
                IssuedTokenInterceptorProvider.assertIssuedToken(itok, aim);
                if (!this.isRequestor(message)) {
                    message.getExchange().remove("ws-security.token");
                    List results = CastUtils.cast((List)message.get("RECV_RESULTS"));
                    if (results != null && !results.isEmpty()) {
                        this.parseHandlerResults((WSHandlerResult)results.get(0), message, ais);
                    }
                } else {
                    for (AssertionInfo ai : ais) {
                        ai.setAsserted(true);
                    }
                }
            }
        }

        private void parseHandlerResults(WSHandlerResult rResult, Message message, Collection<AssertionInfo> issuedAis) {
            PolicyValidatorParameters parameters = new PolicyValidatorParameters();
            parameters.setAssertionInfoMap(message.get(AssertionInfoMap.class));
            parameters.setMessage(message);
            parameters.setResults(rResult);
            parameters.setSignedResults(rResult.getActionResults().get(2));
            ArrayList<WSSecurityEngineResult> samlResults = new ArrayList<WSSecurityEngineResult>();
            if (rResult.getActionResults().containsKey(16)) {
                samlResults.addAll((Collection)rResult.getActionResults().get(16));
            }
            if (rResult.getActionResults().containsKey(8)) {
                samlResults.addAll((Collection<WSSecurityEngineResult>)rResult.getActionResults().get(8));
            }
            parameters.setSamlResults(samlResults);
            QName qName = issuedAis.iterator().next().getAssertion().getName();
            Map<QName, SecurityPolicyValidator> validators = ValidatorUtils.getSecurityPolicyValidators(message);
            if (validators.containsKey(qName)) {
                validators.get(qName).validatePolicies(parameters, issuedAis);
            }
        }
    }

    static class IssuedTokenOutInterceptor
    extends AbstractPhaseInterceptor<Message> {
        IssuedTokenOutInterceptor() {
            super("prepare-send");
        }

        @Override
        public void handleMessage(Message message) throws Fault {
            AssertionInfoMap aim = message.get(AssertionInfoMap.class);
            if (aim != null) {
                Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "IssuedToken");
                if (ais.isEmpty()) {
                    return;
                }
                if (this.isRequestor(message)) {
                    SecurityToken tok;
                    IssuedToken itok = (IssuedToken)ais.iterator().next().getAssertion();
                    STSTokenRetriever.TokenRequestParams params = new STSTokenRetriever.TokenRequestParams();
                    params.setIssuer(itok.getIssuer());
                    params.setClaims(itok.getClaims());
                    if (itok.getPolicy() != null) {
                        params.setWspNamespace(itok.getPolicy().getNamespace());
                    }
                    params.setTrust10(NegotiationUtils.getTrust10(aim));
                    params.setTrust13(NegotiationUtils.getTrust13(aim));
                    params.setTokenTemplate(itok.getRequestSecurityTokenTemplate());
                    STSTokenCacher tokenCacher = (STSTokenCacher)SecurityUtils.getSecurityPropertyValue("security.sts.token.cacher.impl", message);
                    if (tokenCacher == null) {
                        tokenCacher = new DefaultSTSTokenCacher();
                    }
                    if ((tok = STSTokenRetriever.getToken(message, params, tokenCacher)) != null) {
                        IssuedTokenInterceptorProvider.assertIssuedToken(itok, aim);
                        for (AssertionInfo ai : ais) {
                            ai.setAsserted(true);
                        }
                    }
                } else {
                    for (AssertionInfo ai : ais) {
                        ai.setAsserted(true);
                    }
                    IssuedToken itok = (IssuedToken)ais.iterator().next().getAssertion();
                    IssuedTokenInterceptorProvider.assertIssuedToken(itok, aim);
                }
            }
        }
    }
}

