/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message;

import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.dom.message.WSSecBase;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WSSecSAMLToken
extends WSSecBase {
    private static final Logger LOG = LoggerFactory.getLogger(WSSecSAMLToken.class);
    private SamlAssertionWrapper saml;
    private Element samlElement;

    public WSSecSAMLToken(WSSecHeader securityHeader) {
        super(securityHeader);
    }

    public WSSecSAMLToken(Document doc) {
        super(doc);
    }

    public void prepare(SamlAssertionWrapper samlAssertion) {
        this.saml = samlAssertion;
    }

    public void prependToHeader() {
        try {
            Element element = this.getElement();
            if (element != null) {
                Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
                WSSecurityUtil.prependChildElement(securityHeaderElement, element);
            }
        }
        catch (WSSecurityException ex) {
            throw new RuntimeException(ex.toString(), ex);
        }
    }

    public Element getElement() throws WSSecurityException {
        if (this.samlElement != null) {
            return this.samlElement;
        }
        if (this.saml == null) {
            return null;
        }
        this.samlElement = this.saml.toDOM(this.getDocument());
        return this.samlElement;
    }

    public String getId() {
        if (this.saml == null) {
            return null;
        }
        return this.saml.getId();
    }

    public Document build(SamlAssertionWrapper samlAssertion) {
        LOG.debug("Begin add SAMLAssertion token...");
        this.prepare(samlAssertion);
        this.prependToHeader();
        return this.getDocument();
    }
}

