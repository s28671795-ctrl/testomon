/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.engine;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.callback.CallbackLookup;
import org.apache.wss4j.dom.callback.DOMCallbackLookup;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.dom.processor.Processor;
import org.apache.wss4j.dom.saml.DOMSAMLUtil;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class WSSecurityEngine {
    private static final Logger LOG = LoggerFactory.getLogger(WSSecurityEngine.class);
    private WSSConfig wssConfig;
    private boolean doDebug;
    private CallbackLookup callbackLookup;

    public final WSSConfig getWssConfig() {
        if (this.wssConfig == null) {
            this.wssConfig = WSSConfig.getNewInstance();
        }
        return this.wssConfig;
    }

    public final WSSConfig setWssConfig(WSSConfig cfg) {
        WSSConfig ret = this.wssConfig;
        this.wssConfig = cfg;
        return ret;
    }

    public void setCallbackLookup(CallbackLookup callbackLookup) {
        this.callbackLookup = callbackLookup;
    }

    public CallbackLookup getCallbackLookup() {
        return this.callbackLookup;
    }

    public WSHandlerResult processSecurityHeader(Document doc, String actor, CallbackHandler cb, Crypto crypto) throws WSSecurityException {
        return this.processSecurityHeader(doc, actor, cb, crypto, crypto);
    }

    public WSHandlerResult processSecurityHeader(Document doc, String actor, CallbackHandler cb, Crypto sigVerCrypto, Crypto decCrypto) throws WSSecurityException {
        LOG.debug("enter processSecurityHeader()");
        if (actor == null) {
            actor = "";
        }
        WSHandlerResult wsResult = null;
        Element elem = WSSecurityUtil.getSecurityHeader(doc, actor);
        if (elem != null) {
            LOG.debug("Processing WS-Security header for '{}' actor.", (Object)actor);
            wsResult = this.processSecurityHeader(elem, actor, cb, sigVerCrypto, decCrypto);
        }
        return wsResult;
    }

    public WSHandlerResult processSecurityHeader(Element securityHeader, String actor, CallbackHandler cb, Crypto sigVerCrypto, Crypto decCrypto) throws WSSecurityException {
        RequestData data = new RequestData();
        data.setActor(actor);
        data.setWssConfig(this.getWssConfig());
        data.setDecCrypto(decCrypto);
        data.setSigVerCrypto(sigVerCrypto);
        data.setCallbackHandler(cb);
        return this.processSecurityHeader(securityHeader, data);
    }

    public WSHandlerResult processSecurityHeader(Document doc, RequestData requestData) throws WSSecurityException {
        if (requestData.getActor() == null) {
            requestData.setActor("");
        }
        String actor = requestData.getActor();
        WSHandlerResult wsResult = null;
        Element elem = WSSecurityUtil.getSecurityHeader(doc, actor);
        if (elem != null) {
            if (this.doDebug) {
                LOG.debug("Processing WS-Security header for '" + actor + "' actor.");
            }
            wsResult = this.processSecurityHeader(elem, requestData);
        }
        return wsResult;
    }

    public WSHandlerResult processSecurityHeader(Element securityHeader, RequestData requestData) throws WSSecurityException {
        if (securityHeader == null) {
            List<WSSecurityEngineResult> results = Collections.emptyList();
            Map<Integer, List<WSSecurityEngineResult>> actionResults = Collections.emptyMap();
            return new WSHandlerResult(null, results, actionResults);
        }
        if (requestData.getWssConfig() == null) {
            requestData.setWssConfig(this.getWssConfig());
        }
        WSDocInfo wsDocInfo = new WSDocInfo(securityHeader.getOwnerDocument());
        CallbackLookup callbackLookupToUse = this.callbackLookup;
        if (callbackLookupToUse == null) {
            callbackLookupToUse = new DOMCallbackLookup(securityHeader.getOwnerDocument());
        }
        wsDocInfo.setCallbackLookup(callbackLookupToUse);
        wsDocInfo.setCrypto(requestData.getSigVerCrypto());
        wsDocInfo.setSecurityHeader(securityHeader);
        requestData.setWsDocInfo(wsDocInfo);
        WSSConfig cfg = this.getWssConfig();
        Node node = securityHeader.getFirstChild();
        LinkedList<WSSecurityEngineResult> returnResults = new LinkedList<WSSecurityEngineResult>();
        boolean foundTimestamp = false;
        while (node != null) {
            Node nextSibling = node.getNextSibling();
            if (1 == node.getNodeType()) {
                QName el = new QName(node.getNamespaceURI(), node.getLocalName());
                if (foundTimestamp && el.equals(WSConstants.TIMESTAMP)) {
                    requestData.getBSPEnforcer().handleBSPRule(BSPRule.R3227);
                } else if (el.equals(WSConstants.TIMESTAMP)) {
                    foundTimestamp = true;
                }
                Processor p = cfg.getProcessor(el);
                if (p != null) {
                    List<WSSecurityEngineResult> results = p.handleToken((Element)node, requestData);
                    if (!results.isEmpty()) {
                        returnResults.addAll(0, results);
                    }
                } else if (this.doDebug) {
                    LOG.debug("Unknown Element: " + node.getLocalName() + " " + node.getNamespaceURI());
                }
            }
            if (node.getNextSibling() == null && nextSibling != null && nextSibling.getParentNode() != null) {
                node = nextSibling;
                continue;
            }
            node = node.getNextSibling();
        }
        WSHandlerResult handlerResult = new WSHandlerResult(requestData.getActor(), returnResults, wsDocInfo.getActionResults());
        if (requestData.isValidateSamlSubjectConfirmation()) {
            Element bodyElement = callbackLookupToUse.getSOAPBody();
            DOMSAMLUtil.validateSAMLResults(handlerResult, requestData.getTlsCerts(), bodyElement);
        }
        wsDocInfo.clear();
        return handlerResult;
    }
}

