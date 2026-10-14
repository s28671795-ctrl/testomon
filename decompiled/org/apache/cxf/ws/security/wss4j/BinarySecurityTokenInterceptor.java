/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.security.DefaultSecurityContext;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.security.SecurityContext;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.cxf.ws.security.wss4j.AbstractTokenInterceptor;
import org.apache.cxf.ws.security.wss4j.CXFRequestData;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.dom.processor.BinarySecurityTokenProcessor;
import org.apache.wss4j.policy.model.AbstractToken;
import org.w3c.dom.Element;

public class BinarySecurityTokenInterceptor
extends AbstractTokenInterceptor {
    @Override
    protected void processToken(SoapMessage message) {
        Header h2 = this.findSecurityHeader(message, false);
        if (h2 == null) {
            return;
        }
        Element el = (Element)h2.getObject();
        Element child = DOMUtils.getFirstElement(el);
        while (child != null) {
            if ("BinarySecurityToken".equals(child.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(child.getNamespaceURI())) {
                try {
                    List<WSSecurityEngineResult> bstResults = this.processToken(child, message);
                    if (bstResults != null) {
                        List results = CastUtils.cast((List)message.get("RECV_RESULTS"));
                        if (results == null) {
                            results = new ArrayList();
                            message.put("RECV_RESULTS", (Object)results);
                        }
                        WSHandlerResult rResult = new WSHandlerResult(null, bstResults, Collections.singletonMap(4096, bstResults));
                        results.add(0, rResult);
                        this.assertTokens(message);
                        Principal principal = (Principal)bstResults.get(0).get("principal");
                        SecurityContext sc = message.get(SecurityContext.class);
                        if (sc == null || sc.getUserPrincipal() == null) {
                            message.put(SecurityContext.class, new DefaultSecurityContext(principal, null));
                        }
                    }
                }
                catch (WSSecurityException ex) {
                    throw WSS4JUtils.createSoapFault(message, message.getVersion(), ex);
                }
            }
            child = DOMUtils.getNextElement(child);
        }
    }

    private List<WSSecurityEngineResult> processToken(Element tokenElement, SoapMessage message) throws WSSecurityException {
        CXFRequestData data = new CXFRequestData();
        Object o = SecurityUtils.getSecurityPropertyValue("security.callback-handler", message);
        try {
            data.setCallbackHandler(SecurityUtils.getCallbackHandler(o));
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex);
        }
        data.setMsgContext(message);
        data.setWssConfig(WSSConfig.getNewInstance());
        WSDocInfo wsDocInfo = new WSDocInfo(tokenElement.getOwnerDocument());
        data.setWsDocInfo(wsDocInfo);
        BinarySecurityTokenProcessor p = new BinarySecurityTokenProcessor();
        return p.handleToken(tokenElement, data);
    }

    @Override
    protected AbstractToken assertTokens(SoapMessage message) {
        return null;
    }

    @Override
    protected void addToken(SoapMessage message) {
        SecurityToken securityToken = this.getSecurityToken(message);
        if (securityToken == null || securityToken.getToken() == null) {
            return;
        }
        this.assertTokens(message);
        Header h2 = this.findSecurityHeader(message, true);
        Element el = (Element)h2.getObject();
        el.appendChild(el.getOwnerDocument().importNode(securityToken.getToken(), true));
    }

    private SecurityToken getSecurityToken(SoapMessage message) {
        if (message.getContextualProperty("ws-security.token") instanceof SecurityToken) {
            return (SecurityToken)message.getContextualProperty("ws-security.token");
        }
        TokenStore tokenStore = this.getTokenStore(message);
        if (tokenStore == null) {
            return null;
        }
        String id = (String)message.getContextualProperty("ws-security.token.id");
        if (id != null) {
            return tokenStore.getToken(id);
        }
        return null;
    }
}

