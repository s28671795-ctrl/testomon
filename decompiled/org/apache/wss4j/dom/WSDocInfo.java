/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.crypto.dom.DOMCryptoContext;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.callback.CallbackLookup;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WSDocInfo {
    private Document doc;
    private Crypto crypto;
    private final Map<String, TokenValue> tokens = new HashMap<String, TokenValue>();
    private final List<WSSecurityEngineResult> results = new LinkedList<WSSecurityEngineResult>();
    private final Map<Integer, List<WSSecurityEngineResult>> actionResults = new HashMap<Integer, List<WSSecurityEngineResult>>();
    private CallbackLookup callbackLookup;
    private Element securityHeader;

    public WSDocInfo(Document doc) {
        this.doc = doc != null && doc.getDocumentElement() != null ? doc.getDocumentElement().getOwnerDocument() : doc;
    }

    public void clear() {
        this.crypto = null;
        this.doc = null;
        this.callbackLookup = null;
        this.securityHeader = null;
        this.tokens.clear();
        this.results.clear();
        this.actionResults.clear();
    }

    public void addTokenElement(Element element) throws WSSecurityException {
        this.addTokenElement(element, true);
    }

    public void addTokenElement(Element element, boolean checkMultipleElements) throws WSSecurityException {
        TokenValue previousValue;
        TokenValue tokenValue;
        String id;
        if (element == null) {
            return;
        }
        if (element.hasAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id")) {
            id = element.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
            tokenValue = new TokenValue("Id", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", element);
            previousValue = this.tokens.put(id, tokenValue);
            if (checkMultipleElements && previousValue != null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "duplicateError");
            }
        }
        if (element.hasAttributeNS(null, "Id")) {
            id = element.getAttributeNS(null, "Id");
            tokenValue = new TokenValue("Id", null, element);
            previousValue = this.tokens.put(id, tokenValue);
            if (checkMultipleElements && previousValue != null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "duplicateError");
            }
        }
        if ("Assertion".equals(element.getLocalName())) {
            if ("urn:oasis:names:tc:SAML:1.0:assertion".equals(element.getNamespaceURI()) && element.hasAttributeNS(null, "AssertionID")) {
                id = element.getAttributeNS(null, "AssertionID");
                tokenValue = new TokenValue("AssertionID", null, element);
                previousValue = this.tokens.put(id, tokenValue);
                if (checkMultipleElements && previousValue != null) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "duplicateError");
                }
            } else if ("urn:oasis:names:tc:SAML:2.0:assertion".equals(element.getNamespaceURI()) && element.hasAttributeNS(null, "ID")) {
                id = element.getAttributeNS(null, "ID");
                tokenValue = new TokenValue("ID", null, element);
                previousValue = this.tokens.put(id, tokenValue);
                if (checkMultipleElements && previousValue != null) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "duplicateError");
                }
            }
        }
    }

    public Element getTokenElement(String uri) {
        String id = XMLUtils.getIDFromReference(uri);
        if (id == null) {
            return null;
        }
        TokenValue token = this.tokens.get(id);
        if (token != null) {
            return token.getToken();
        }
        return null;
    }

    public void setTokensOnContext(DOMCryptoContext context) {
        if (!this.tokens.isEmpty() && context != null) {
            for (Map.Entry<String, TokenValue> entry : this.tokens.entrySet()) {
                TokenValue tokenValue = entry.getValue();
                context.setIdAttributeNS(tokenValue.getToken(), tokenValue.getIdNamespace(), tokenValue.getIdName());
            }
        }
    }

    public void setTokenOnContext(String uri, DOMCryptoContext context) {
        String id = XMLUtils.getIDFromReference(uri);
        if (id == null || context == null) {
            return;
        }
        TokenValue tokenValue = this.tokens.get(id);
        if (tokenValue != null) {
            context.setIdAttributeNS(tokenValue.getToken(), tokenValue.getIdNamespace(), tokenValue.getIdName());
        }
    }

    public void addResult(WSSecurityEngineResult result) {
        this.results.add(result);
        Integer resultTag = (Integer)result.get("action");
        if (resultTag != null) {
            List<WSSecurityEngineResult> storedResults = this.actionResults.get(resultTag);
            if (storedResults == null) {
                storedResults = new ArrayList<WSSecurityEngineResult>();
            }
            storedResults.add(result);
            this.actionResults.put(resultTag, storedResults);
        }
    }

    public List<WSSecurityEngineResult> getResults() {
        if (this.results.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<WSSecurityEngineResult>(this.results);
    }

    public Map<Integer, List<WSSecurityEngineResult>> getActionResults() {
        if (this.actionResults.isEmpty()) {
            return Collections.emptyMap();
        }
        return new HashMap<Integer, List<WSSecurityEngineResult>>(this.actionResults);
    }

    public WSSecurityEngineResult getResult(String uri) {
        String id = XMLUtils.getIDFromReference(uri);
        if (id == null) {
            return null;
        }
        if (!this.results.isEmpty()) {
            for (WSSecurityEngineResult result : this.results) {
                String cId = (String)result.get("id");
                if (!id.equals(cId)) continue;
                return result;
            }
        }
        return null;
    }

    public List<WSSecurityEngineResult> getResultsByTag(Integer tag) {
        if (this.actionResults.isEmpty() || !this.actionResults.containsKey(tag)) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(this.actionResults.get(tag));
    }

    public boolean hasResult(Integer tag, String uri) {
        String id = XMLUtils.getIDFromReference(uri);
        if (id == null || "".equals(uri)) {
            return false;
        }
        if (!this.actionResults.isEmpty() && this.actionResults.containsKey(tag)) {
            for (WSSecurityEngineResult result : this.actionResults.get(tag)) {
                String cId = (String)result.get("id");
                if (!id.equals(cId)) continue;
                return true;
            }
        }
        return false;
    }

    public Crypto getCrypto() {
        return this.crypto;
    }

    public Document getDocument() {
        return this.doc;
    }

    public void setCrypto(Crypto crypto) {
        this.crypto = crypto;
    }

    public void setCallbackLookup(CallbackLookup callbackLookup) {
        this.callbackLookup = callbackLookup;
    }

    public CallbackLookup getCallbackLookup() {
        return this.callbackLookup;
    }

    public Element getSecurityHeader() {
        return this.securityHeader;
    }

    public void setSecurityHeader(Element securityHeader) {
        this.securityHeader = securityHeader;
    }

    private static class TokenValue {
        private final String idName;
        private final String idNamespace;
        private final Element token;

        TokenValue(String idName, String idNamespace, Element token) {
            this.idName = idName;
            this.idNamespace = idNamespace;
            this.token = token;
        }

        public String getIdName() {
            return this.idName;
        }

        public String getIdNamespace() {
            return this.idNamespace;
        }

        public Element getToken() {
            return this.token;
        }
    }
}

