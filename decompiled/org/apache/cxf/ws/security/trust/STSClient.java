/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.Bus;
import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.trust.AbstractSTSClient;
import org.apache.cxf.ws.security.trust.TrustException;
import org.apache.cxf.ws.security.wss4j.AttachmentCallbackHandler;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.w3c.dom.Element;

public class STSClient
extends AbstractSTSClient {
    private static final Logger LOG = LogUtils.getL7dLogger(STSClient.class);

    public STSClient(Bus b) {
        super(b);
    }

    public SecurityToken requestSecurityToken() throws Exception {
        return this.requestSecurityToken(null);
    }

    public SecurityToken requestSecurityToken(String appliesTo) throws Exception {
        return this.requestSecurityToken(appliesTo, null);
    }

    public SecurityToken requestSecurityToken(String appliesTo, String binaryExchange) throws Exception {
        return this.requestSecurityToken(appliesTo, null, "/Issue", binaryExchange);
    }

    public SecurityToken requestSecurityToken(String appliesTo, String action, String requestType, String binaryExchange) throws Exception {
        AbstractSTSClient.STSResponse response = this.issue(appliesTo, action, requestType, binaryExchange);
        SecurityToken token = this.createSecurityToken(this.getDocumentElement(response.getResponse()), response.getEntropy());
        this.inlineAttachments(token, response.getAttachments());
        if (response.getCert() != null) {
            token.setX509Certificate(response.getCert(), response.getCrypto());
        }
        if (token.getTokenType() == null) {
            String tokenTypeFromTemplate = this.getTokenTypeFromTemplate();
            if (tokenTypeFromTemplate != null) {
                token.setTokenType(tokenTypeFromTemplate);
            } else if (this.tokenType != null) {
                token.setTokenType(this.tokenType);
            }
        }
        return token;
    }

    public SecurityToken renewSecurityToken(SecurityToken tok) throws Exception {
        AbstractSTSClient.STSResponse response = this.renew(tok);
        SecurityToken token = this.createSecurityToken(this.getDocumentElement(response.getResponse()), null);
        this.inlineAttachments(token, response.getAttachments());
        if (token.getTokenType() == null) {
            String tokenTypeFromTemplate = this.getTokenTypeFromTemplate();
            if (tokenTypeFromTemplate != null) {
                token.setTokenType(tokenTypeFromTemplate);
            } else if (this.tokenType != null) {
                token.setTokenType(this.tokenType);
            }
        }
        return token;
    }

    public List<SecurityToken> validateSecurityToken(SecurityToken tok) throws Exception {
        String validateTokenType = this.tokenType;
        if (validateTokenType == null) {
            validateTokenType = this.namespace + "/RSTR/Status";
        }
        return this.validateSecurityToken(tok, validateTokenType);
    }

    private void inlineAttachments(SecurityToken token, Collection<Attachment> attachments) throws WSSecurityException {
        Element requestedSecurityTokenElement;
        Message msg = PhaseInterceptorChain.getCurrentMessage();
        if (AttachmentUtil.isMtomEnabled(msg) && attachments != null && (requestedSecurityTokenElement = token.getToken()) != null) {
            List<Element> includeElements = XMLUtils.findElements(requestedSecurityTokenElement.getFirstChild(), "Include", "http://www.w3.org/2004/08/xop/include");
            WSSecurityUtil.inlineAttachments(includeElements, new AttachmentCallbackHandler(attachments), true);
        }
    }

    protected List<SecurityToken> validateSecurityToken(SecurityToken tok, String tokentype) throws Exception {
        AbstractSTSClient.STSResponse response = this.validate(tok, tokentype);
        Element el = this.getDocumentElement(response.getResponse());
        if ("RequestSecurityTokenResponseCollection".equals(el.getLocalName())) {
            el = DOMUtils.getFirstElement(el);
        }
        if (!"RequestSecurityTokenResponse".equals(el.getLocalName())) {
            throw new Fault("Unexpected element " + el.getLocalName(), LOG);
        }
        el = DOMUtils.getFirstElement(el);
        String reason = null;
        boolean valid = false;
        LinkedList<SecurityToken> tokens = new LinkedList<SecurityToken>();
        while (el != null) {
            if ("Status".equals(el.getLocalName())) {
                Element e2 = DOMUtils.getFirstChildWithName(el, el.getNamespaceURI(), "Code");
                String s2 = DOMUtils.getContent(e2);
                valid = s2.endsWith("/status/valid");
                e2 = DOMUtils.getFirstChildWithName(el, el.getNamespaceURI(), "Reason");
                if (e2 != null) {
                    reason = DOMUtils.getContent(e2);
                }
            } else if ("RequestedSecurityToken".equals(el.getLocalName())) {
                SecurityToken token = this.createSecurityToken(this.getDocumentElement(response.getResponse()), response.getEntropy());
                if (response.getCert() != null) {
                    token.setX509Certificate(response.getCert(), response.getCrypto());
                }
                if (token.getTokenType() == null) {
                    String tokenTypeFromTemplate = this.getTokenTypeFromTemplate();
                    if (tokenTypeFromTemplate != null) {
                        token.setTokenType(tokenTypeFromTemplate);
                    } else if (this.tokenType != null) {
                        token.setTokenType(this.tokenType);
                    }
                }
                tokens.add(token);
            }
            el = DOMUtils.getNextElement(el);
        }
        if (!valid) {
            throw new TrustException(LOG, "VALIDATION_FAILED", reason);
        }
        if (tokens.isEmpty()) {
            tokens.add(tok);
        }
        return tokens;
    }

    public boolean cancelSecurityToken(SecurityToken token) throws Exception {
        try {
            this.cancel(token);
            return true;
        }
        catch (Exception ex) {
            LOG.log(Level.WARNING, "Problem cancelling token", ex);
            return false;
        }
    }

    private String getTokenTypeFromTemplate() {
        if (this.template != null && DOMUtils.getFirstElement(this.template) != null) {
            Element tl = DOMUtils.getFirstElement(this.template);
            while (tl != null) {
                if ("TokenType".equals(tl.getLocalName())) {
                    return DOMUtils.getContent(tl);
                }
                tl = DOMUtils.getNextElement(tl);
            }
        }
        return null;
    }
}

