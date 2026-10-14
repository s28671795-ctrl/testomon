/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.attachment.reference;

import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.ws.policy.PolicyBuilder;
import org.apache.cxf.ws.policy.attachment.reference.ReferenceResolver;
import org.apache.neethi.Policy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LocalDocumentReferenceResolver
implements ReferenceResolver {
    private Document document;
    private PolicyBuilder builder;

    public LocalDocumentReferenceResolver(Document di, PolicyBuilder b) {
        this.document = di;
        this.builder = b;
    }

    @Override
    public Policy resolveReference(String uri) {
        return this.resolveReference(uri, this.document.getDocumentElement());
    }

    public Policy resolveReference(String uri, Element el) {
        if (el == null) {
            return null;
        }
        if (uri.equals(el.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id"))) {
            return this.builder.getPolicy(el);
        }
        Element el2 = DOMUtils.getFirstElement(el);
        while (el2 != null) {
            Policy p = this.resolveReference(uri, el2);
            if (p != null) {
                return p;
            }
            el2 = DOMUtils.getNextElement(el2);
        }
        return null;
    }
}

