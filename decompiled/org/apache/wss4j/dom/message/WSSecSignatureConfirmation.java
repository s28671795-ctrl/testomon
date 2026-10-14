/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message;

import org.apache.wss4j.dom.message.WSSecBase;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.token.SignatureConfirmation;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WSSecSignatureConfirmation
extends WSSecBase {
    private static final Logger LOG = LoggerFactory.getLogger(WSSecSignatureConfirmation.class);
    private SignatureConfirmation sc;
    private byte[] signatureValue;

    public WSSecSignatureConfirmation(WSSecHeader securityHeader) {
        super(securityHeader);
    }

    public WSSecSignatureConfirmation(Document doc) {
        super(doc);
    }

    public void setSignatureValue(byte[] signatureValue) {
        this.signatureValue = signatureValue;
    }

    public void prepare() {
        this.sc = new SignatureConfirmation(this.getDocument(), this.signatureValue);
        this.sc.setID(this.getIdAllocator().createId("SC-", this.sc));
        if (this.addWSUNamespace) {
            this.sc.addWSUNamespace();
        }
    }

    public void prependToHeader() {
        Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
        WSSecurityUtil.prependChildElement(securityHeaderElement, this.sc.getElement());
    }

    public Document build(byte[] sigVal) {
        LOG.debug("Begin add signature confirmation...");
        this.signatureValue = sigVal;
        this.prepare();
        this.prependToHeader();
        return this.getDocument();
    }

    public String getId() {
        if (this.sc == null) {
            return null;
        }
        return this.sc.getID();
    }

    public Element getSignatureConfirmationElement() {
        return this.sc != null ? this.sc.getElement() : null;
    }
}

