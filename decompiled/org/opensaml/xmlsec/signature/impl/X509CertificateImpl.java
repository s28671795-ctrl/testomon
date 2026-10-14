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
import org.opensaml.xmlsec.signature.X509Certificate;

public class X509CertificateImpl
extends AbstractXMLObject
implements X509Certificate {
    private static final IndexingObjectStore<String> B64_CERT_STORE = new IndexingObjectStore();
    private String b64CertIndex;

    protected X509CertificateImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getValue() {
        return B64_CERT_STORE.get(this.b64CertIndex);
    }

    @Override
    public void setValue(String newValue) {
        String b64Cert;
        String currentCert = B64_CERT_STORE.get(this.b64CertIndex);
        if (!Objects.equals(currentCert, b64Cert = this.prepareForAssignment(currentCert, newValue))) {
            B64_CERT_STORE.remove(this.b64CertIndex);
            this.b64CertIndex = B64_CERT_STORE.put(b64Cert);
        }
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return Collections.emptyList();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        B64_CERT_STORE.remove(this.b64CertIndex);
    }
}

