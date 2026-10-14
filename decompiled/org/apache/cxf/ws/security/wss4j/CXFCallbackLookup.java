/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import org.apache.cxf.helpers.DOMUtils;
import org.apache.wss4j.dom.callback.DOMCallbackLookup;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CXFCallbackLookup
extends DOMCallbackLookup {
    private Document doc;
    private Element soapBody;

    public CXFCallbackLookup(Document doc, Element soapBody) {
        super(doc);
        this.doc = doc;
        this.soapBody = soapBody;
    }

    @Override
    public Element getSOAPBody() {
        this.soapBody = (Element)DOMUtils.getDomElement(this.soapBody);
        if (this.soapBody != null) {
            return this.soapBody;
        }
        return WSSecurityUtil.findBodyElement(this.doc);
    }
}

