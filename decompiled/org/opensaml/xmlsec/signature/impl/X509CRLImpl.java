/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import net.shibboleth.utilities.java.support.collection.IndexingObjectStore;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.X509CRL;

public class X509CRLImpl
extends AbstractXMLObject
implements X509CRL {
    private static final IndexingObjectStore<String> B64_CRL_STORE = new IndexingObjectStore();
    private String b64CRLIndex;

    protected X509CRLImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getValue() {
        return B64_CRL_STORE.get(this.b64CRLIndex);
    }

    @Override
    public void setValue(String newValue) {
        String b64Cert;
        String currentCert = B64_CRL_STORE.get(this.b64CRLIndex);
        if (!Objects.equals(currentCert, b64Cert = this.prepareForAssignment(currentCert, newValue))) {
            B64_CRL_STORE.remove(this.b64CRLIndex);
            this.b64CRLIndex = B64_CRL_STORE.put(b64Cert);
        }
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return Collections.emptyList();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        B64_CRL_STORE.remove(this.b64CRLIndex);
    }
}

