/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message;

import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class WSSecHeader {
    private String actor;
    private boolean mustunderstand = true;
    private Element securityHeader;
    private final Document doc;
    private String wsuPrefix = "wsu";

    public WSSecHeader(Document doc) {
        this(null, doc);
    }

    public WSSecHeader(String actor, Document doc) {
        this(actor, true, doc);
    }

    public WSSecHeader(String act, boolean mu, Document doc) {
        this.actor = act;
        this.mustunderstand = mu;
        this.doc = doc;
    }

    public void setActor(String act) {
        this.actor = act;
    }

    public void setMustUnderstand(boolean mu) {
        this.mustunderstand = mu;
    }

    public Document getSecurityHeaderDoc() {
        return this.doc;
    }

    public Element getSecurityHeaderElement() {
        return this.securityHeader;
    }

    public void setSecurityHeaderElement(Element securityHeaderElement) {
        this.securityHeader = securityHeaderElement;
    }

    public boolean isEmpty() throws WSSecurityException {
        if (this.doc == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"The Document of WSSecHeader is null"});
        }
        if (this.securityHeader == null) {
            this.securityHeader = WSSecurityUtil.findWsseSecurityHeaderBlock(this.doc, this.doc.getDocumentElement(), this.actor, false);
        }
        return this.securityHeader == null || this.securityHeader.getFirstChild() == null;
    }

    public Element insertSecurityHeader() throws WSSecurityException {
        if (this.securityHeader != null) {
            return this.securityHeader;
        }
        if (this.doc == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"The Document of WSSecHeader is null"});
        }
        this.securityHeader = WSSecurityUtil.findWsseSecurityHeaderBlock(this.doc, this.doc.getDocumentElement(), this.actor, true);
        String soapNamespace = WSSecurityUtil.getSOAPNamespace(this.doc.getDocumentElement());
        String soapPrefix = XMLUtils.setNamespace(this.securityHeader, soapNamespace, "soapenv");
        if (this.actor != null && this.actor.length() > 0) {
            String actorLocal = "actor";
            if ("http://www.w3.org/2003/05/soap-envelope".equals(soapNamespace)) {
                actorLocal = "role";
            }
            this.securityHeader.setAttributeNS(soapNamespace, soapPrefix + ":" + actorLocal, this.actor);
        }
        if (this.mustunderstand) {
            String mustUnderstandLocal = "1";
            if ("http://www.w3.org/2003/05/soap-envelope".equals(soapNamespace)) {
                mustUnderstandLocal = "true";
            }
            this.securityHeader.setAttributeNS(soapNamespace, soapPrefix + ":" + "mustUnderstand", mustUnderstandLocal);
        }
        this.wsuPrefix = XMLUtils.setNamespace(this.securityHeader, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu");
        return this.securityHeader;
    }

    public void removeSecurityHeader() throws WSSecurityException {
        if (this.securityHeader == null) {
            if (this.doc == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"The Document of WSSecHeader is null"});
            }
            this.securityHeader = WSSecurityUtil.findWsseSecurityHeaderBlock(this.doc, this.doc.getDocumentElement(), this.actor, false);
        }
        if (this.securityHeader != null) {
            Node parent = this.securityHeader.getParentNode();
            parent.removeChild(this.securityHeader);
        }
    }

    public String getWsuPrefix() {
        return this.wsuPrefix;
    }
}

