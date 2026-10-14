/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.attachment.reference;

import org.apache.cxf.resource.ExtendedURIResolver;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.ws.policy.PolicyBuilder;
import org.apache.cxf.ws.policy.PolicyConstants;
import org.apache.cxf.ws.policy.PolicyException;
import org.apache.cxf.ws.policy.attachment.reference.ReferenceResolver;
import org.apache.neethi.Policy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class RemoteReferenceResolver
implements ReferenceResolver {
    private String baseURI;
    private PolicyBuilder builder;

    public RemoteReferenceResolver(String uri, PolicyBuilder b) {
        this.baseURI = uri;
        this.builder = b;
    }

    @Override
    public Policy resolveReference(String uri) {
        ExtendedURIResolver resolver2 = new ExtendedURIResolver();
        int pos = uri.indexOf(35);
        String documentURI = pos == -1 ? uri : uri.substring(0, pos);
        InputSource is = resolver2.resolve(documentURI, this.baseURI);
        if (null == is) {
            return null;
        }
        Document doc = null;
        try {
            doc = StaxUtils.read(is.getByteStream());
        }
        catch (Exception ex) {
            throw new PolicyException(ex);
        }
        finally {
            resolver2.close();
        }
        if (pos == -1) {
            return this.builder.getPolicy(doc.getDocumentElement());
        }
        String id = uri.substring(pos + 1);
        for (Element elem : PolicyConstants.findAllPolicyElementsOfLocalName(doc, "Policy")) {
            if (!id.equals(elem.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id"))) continue;
            return this.builder.getPolicy(elem);
        }
        return null;
    }
}

