/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust.delegation;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.ws.security.trust.delegation.DelegationCallback;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.token.PKIPathSecurity;
import org.apache.wss4j.common.token.X509Security;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.w3c.dom.Element;

public class ReceivedTokenCallbackHandler
implements CallbackHandler {
    private static final List<Integer> DEFAULT_SECURITY_PRIORITIES = new ArrayList<Integer>();
    private List<Integer> securityPriorities = new ArrayList<Integer>(DEFAULT_SECURITY_PRIORITIES);
    private boolean useTransformedToken = true;

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; ++i) {
            if (callbacks[i] instanceof DelegationCallback) {
                WeakReference wr;
                SoapMessage previousSoapMessage;
                Element token;
                DelegationCallback callback = (DelegationCallback)callbacks[i];
                Message message = callback.getCurrentMessage();
                if (message == null || message.get(PhaseInterceptorChain.PREVIOUS_MESSAGE) == null || (token = this.getTokenFromMessage(previousSoapMessage = (SoapMessage)(wr = (WeakReference)message.get(PhaseInterceptorChain.PREVIOUS_MESSAGE)).get())) == null) continue;
                callback.setToken(token);
                continue;
            }
            throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
        }
    }

    private Element getTokenFromMessage(SoapMessage soapMessage) {
        List<WSHandlerResult> results;
        if (soapMessage != null && (results = CastUtils.cast((List)soapMessage.get("RECV_RESULTS"))) != null) {
            for (WSHandlerResult handlerResult : results) {
                Element token = this.getTokenFromResults(handlerResult);
                if (token == null) continue;
                return token;
            }
        }
        return null;
    }

    private Element getTokenFromResults(WSHandlerResult handlerResult) {
        Map<Integer, List<WSSecurityEngineResult>> actionResults = handlerResult.getActionResults();
        for (Integer resultPriority : this.securityPriorities) {
            List<WSSecurityEngineResult> foundResults = actionResults.get(resultPriority);
            if (foundResults == null || foundResults.isEmpty()) continue;
            for (WSSecurityEngineResult result : foundResults) {
                if (this.skipResult(resultPriority, result)) continue;
                Object transformedToken = result.get("transformed-token");
                if (this.useTransformedToken && transformedToken instanceof SamlAssertionWrapper) {
                    return ((SamlAssertionWrapper)transformedToken).getElement();
                }
                if (result.get("token-element") == null) continue;
                return (Element)result.get("token-element");
            }
        }
        return null;
    }

    protected boolean skipResult(Integer resultPriority, WSSecurityEngineResult result) {
        Object binarySecurity = result.get("binary-security-token");
        return resultPriority == 4096 && (binarySecurity instanceof X509Security || binarySecurity instanceof PKIPathSecurity);
    }

    public boolean isUseTransformedToken() {
        return this.useTransformedToken;
    }

    public void setUseTransformedToken(boolean useTransformedToken) {
        this.useTransformedToken = useTransformedToken;
    }

    public List<Integer> getSecurityPriorities() {
        return this.securityPriorities;
    }

    public void setSecurityPriorities(List<Integer> securityPriorities) {
        this.securityPriorities = securityPriorities;
    }

    static {
        DEFAULT_SECURITY_PRIORITIES.add(16);
        DEFAULT_SECURITY_PRIORITIES.add(8);
        DEFAULT_SECURITY_PRIORITIES.add(1);
        DEFAULT_SECURITY_PRIORITIES.add(4096);
        DEFAULT_SECURITY_PRIORITIES.add(8192);
    }
}

