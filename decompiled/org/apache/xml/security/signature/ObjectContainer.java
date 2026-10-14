/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.signature;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ObjectContainer
extends SignatureElementProxy {
    public ObjectContainer(Document doc) {
        super(doc);
    }

    public ObjectContainer(Element element, String baseURI) throws XMLSecurityException {
        super(element, baseURI);
    }

    public void setId(String Id2) {
        if (Id2 != null) {
            this.setLocalIdAttribute("Id", Id2);
        }
    }

    public String getId() {
        return this.getLocalAttribute("Id");
    }

    public void setMimeType(String MimeType2) {
        if (MimeType2 != null) {
            this.setLocalAttribute("MimeType", MimeType2);
        }
    }

    public String getMimeType() {
        return this.getLocalAttribute("MimeType");
    }

    public void setEncoding(String Encoding2) {
        if (Encoding2 != null) {
            this.setLocalAttribute("Encoding", Encoding2);
        }
    }

    public String getEncoding() {
        return this.getLocalAttribute("Encoding");
    }

    public Node appendChild(Node node) {
        this.appendSelf(node);
        return node;
    }

    @Override
    public String getBaseLocalName() {
        return "Object";
    }
}

