/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Logger;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.security.transport.TLSSessionInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.policy.PolicyException;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JOutInterceptor;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JStaxInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.policy.model.AbstractToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractTokenInterceptor
extends AbstractSoapInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(AbstractSoapInterceptor.class);
    private static final Set<QName> HEADERS = Collections.singleton(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security"));

    public AbstractTokenInterceptor() {
        super("pre-protocol");
        this.addAfter(PolicyBasedWSS4JOutInterceptor.class.getName());
        this.addAfter(PolicyBasedWSS4JInInterceptor.class.getName());
        this.addAfter(PolicyBasedWSS4JStaxInInterceptor.class.getName());
    }

    @Override
    public Set<QName> getUnderstoodHeaders() {
        return HEADERS;
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        boolean isOut;
        boolean enableStax = MessageUtils.getContextualBoolean(message, "ws-security.enable.streaming", false);
        if (enableStax) {
            return;
        }
        boolean isReq = MessageUtils.isRequestor(message);
        if (isReq != (isOut = MessageUtils.isOutbound(message))) {
            this.assertTokens(message);
            return;
        }
        if (isReq) {
            if (message.containsKey(PolicyBasedWSS4JOutInterceptor.SECURITY_PROCESSED)) {
                return;
            }
            this.addToken(message);
        } else {
            if (message.containsKey(WSS4JInInterceptor.SECURITY_PROCESSED)) {
                return;
            }
            this.processToken(message);
        }
    }

    protected abstract void processToken(SoapMessage var1);

    protected abstract void addToken(SoapMessage var1);

    protected abstract AbstractToken assertTokens(SoapMessage var1);

    protected AbstractToken assertTokens(SoapMessage message, String localname, boolean signed) {
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, localname);
        AbstractToken tok = null;
        for (AssertionInfo ai : ais) {
            tok = (AbstractToken)ai.getAssertion();
            ai.setAsserted(true);
        }
        PolicyUtils.assertPolicy(aim, "SupportingTokens");
        if (signed || this.isTLSInUse(message)) {
            PolicyUtils.assertPolicy(aim, "SignedSupportingTokens");
        }
        return tok;
    }

    protected boolean isTLSInUse(SoapMessage message) {
        TLSSessionInfo tlsInfo = message.get(TLSSessionInfo.class);
        return tlsInfo != null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected TokenStore getTokenStore(SoapMessage message) {
        EndpointInfo info;
        EndpointInfo endpointInfo = info = message.getExchange().getEndpoint().getEndpointInfo();
        synchronized (endpointInfo) {
            TokenStore tokenStore = (TokenStore)message.getContextualProperty("org.apache.cxf.ws.security.tokenstore.TokenStore");
            if (tokenStore == null) {
                tokenStore = (TokenStore)info.getProperty("org.apache.cxf.ws.security.tokenstore.TokenStore");
            }
            return tokenStore;
        }
    }

    protected Header findSecurityHeader(SoapMessage message, boolean create) {
        String actor = (String)message.getContextualProperty("ws-security.actor");
        for (Header h2 : message.getHeaders()) {
            QName n = h2.getName();
            if (!"Security".equals(n.getLocalPart()) || !n.getNamespaceURI().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd") && !n.getNamespaceURI().equals("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd")) continue;
            String receivedActor = ((SoapHeader)h2).getActor();
            if (actor != null && !actor.equalsIgnoreCase(receivedActor)) continue;
            return h2;
        }
        if (!create) {
            return null;
        }
        Document doc = DOMUtils.getEmptyDocument();
        Element el = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:Security");
        el.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        SoapHeader sh = new SoapHeader(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security"), el);
        sh.setMustUnderstand(true);
        if (actor != null && actor.length() > 0) {
            sh.setActor(actor);
        }
        message.getHeaders().add(sh);
        return sh;
    }

    protected String getPassword(String userName, AbstractToken info, int usage, SoapMessage message) {
        CallbackHandler handler = null;
        try {
            Object o = SecurityUtils.getSecurityPropertyValue("security.callback-handler", message);
            handler = SecurityUtils.getCallbackHandler(o);
            if (handler == null) {
                this.policyNotAsserted(info, "No callback handler and no password available", message);
                return null;
            }
        }
        catch (Exception ex) {
            this.policyNotAsserted(info, "No callback handler and no password available", message);
            return null;
        }
        Callback[] cb = new WSPasswordCallback[]{new WSPasswordCallback(userName, usage)};
        try {
            handler.handle(cb);
        }
        catch (Exception e) {
            this.policyNotAsserted(info, e, message);
        }
        return ((WSPasswordCallback)cb[0]).getPassword();
    }

    protected void policyNotAsserted(AbstractToken assertion, String reason, SoapMessage message) {
        if (assertion == null) {
            return;
        }
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        Collection ais = (Collection)aim.get(assertion.getName());
        if (ais != null) {
            for (AssertionInfo ai : ais) {
                if (ai.getAssertion() != assertion) continue;
                ai.setNotAsserted(reason);
            }
        }
        if (!assertion.isOptional()) {
            throw new PolicyException(new Message(reason, LOG, new Object[0]));
        }
    }

    protected void policyNotAsserted(AbstractToken assertion, Exception reason, SoapMessage message) {
        if (assertion == null) {
            return;
        }
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        Collection ais = (Collection)aim.get(assertion.getName());
        if (ais != null) {
            for (AssertionInfo ai : ais) {
                if (ai.getAssertion() != assertion) continue;
                ai.setNotAsserted(reason.getMessage());
            }
        }
        throw new PolicyException(reason);
    }
}

