/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.keys.keyresolver.implementations;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.crypto.SecretKey;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.content.x509.XMLX509Certificate;
import org.apache.xml.security.keys.keyresolver.KeyResolverException;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class X509CertificateResolver
extends KeyResolverSpi {
    private static final Logger LOG = LoggerFactory.getLogger(X509CertificateResolver.class);

    @Override
    public PublicKey engineLookupAndResolvePublicKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
        X509Certificate cert = this.engineLookupResolveX509Certificate(element, baseURI, storage);
        if (cert != null) {
            return cert.getPublicKey();
        }
        return null;
    }

    @Override
    public X509Certificate engineLookupResolveX509Certificate(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
        try {
            Element[] els = XMLUtils.selectDsNodes(element.getFirstChild(), "X509Certificate");
            if (els == null || els.length == 0) {
                Element el = XMLUtils.selectDsNode(element.getFirstChild(), "X509Data", 0);
                if (el != null) {
                    return this.engineLookupResolveX509Certificate(el, baseURI, storage);
                }
                return null;
            }
            for (int i = 0; i < els.length; ++i) {
                XMLX509Certificate xmlCert = new XMLX509Certificate(els[i], baseURI);
                X509Certificate cert = xmlCert.getX509Certificate();
                if (cert == null) continue;
                return cert;
            }
            return null;
        }
        catch (XMLSecurityException ex) {
            LOG.debug("Security Exception", ex);
            throw new KeyResolverException(ex);
        }
    }

    @Override
    public SecretKey engineLookupAndResolveSecretKey(Element element, String baseURI, StorageResolver storage) {
        return null;
    }
}

