/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.callback;

import java.util.Collections;
import java.util.List;
import javax.xml.crypto.dom.DOMCryptoContext;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.callback.CallbackLookup;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DOMCallbackLookup
implements CallbackLookup {
    private Document doc;

    public DOMCallbackLookup(Document doc) {
        this.doc = doc;
    }

    @Override
    public Element getElement(String id, String valueType, boolean checkMultipleElements) throws WSSecurityException {
        return this.getAndRegisterElement(id, valueType, checkMultipleElements, null);
    }

    @Override
    public Element getAndRegisterElement(String id, String valueType, boolean checkMultipleElements, DOMCryptoContext context) throws WSSecurityException {
        String cId;
        String idToMatch = XMLUtils.getIDFromReference(id);
        Element bodyElement = this.getSOAPBody();
        if (bodyElement != null && (cId = bodyElement.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id")).equals(idToMatch)) {
            if (context != null) {
                context.setIdAttributeNS(bodyElement, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
            }
            return bodyElement;
        }
        Element foundElement = XMLUtils.findElementById(this.doc.getDocumentElement(), idToMatch, checkMultipleElements);
        if (foundElement != null) {
            if (context != null) {
                if (foundElement.hasAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id") && idToMatch.equals(foundElement.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id"))) {
                    context.setIdAttributeNS(foundElement, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
                }
                if (foundElement.hasAttributeNS(null, "Id") && idToMatch.equals(foundElement.getAttributeNS(null, "Id"))) {
                    context.setIdAttributeNS(foundElement, null, "Id");
                }
            }
            return foundElement;
        }
        if (("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(valueType) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(valueType) || "".equals(valueType) || valueType == null) && (foundElement = XMLUtils.findSAMLAssertionElementById(this.doc.getDocumentElement(), idToMatch)) != null) {
            if (context != null) {
                if (foundElement.hasAttributeNS(null, "ID") && idToMatch.equals(foundElement.getAttributeNS(null, "ID"))) {
                    context.setIdAttributeNS(foundElement, null, "ID");
                }
                if (foundElement.hasAttributeNS(null, "AssertionID") && idToMatch.equals(foundElement.getAttributeNS(null, "AssertionID"))) {
                    context.setIdAttributeNS(foundElement, null, "AssertionID");
                }
            }
            return foundElement;
        }
        return null;
    }

    @Override
    public List<Element> getElements(String localname, String namespace) throws WSSecurityException {
        Element bodyElement = this.getSOAPBody();
        if ("Body".equals(localname) && bodyElement.getNamespaceURI().equals(namespace)) {
            return Collections.singletonList(bodyElement);
        }
        return XMLUtils.findElements(this.doc.getDocumentElement(), localname, namespace);
    }

    @Override
    public Element getSOAPBody() {
        return WSSecurityUtil.findBodyElement(this.doc);
    }
}

