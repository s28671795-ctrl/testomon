/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.keys.keyresolver.implementations;

import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import javax.crypto.SecretKey;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.content.x509.XMLX509SubjectName;
import org.apache.xml.security.keys.keyresolver.KeyResolverException;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class X509SubjectNameResolver
extends KeyResolverSpi {
    private static final Logger LOG = LoggerFactory.getLogger(X509SubjectNameResolver.class);

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
        LOG.debug("Can I resolve {}?", (Object)element.getTagName());
        Element[] x509childNodes = null;
        XMLX509SubjectName[] x509childObject = null;
        if (!XMLUtils.elementIsInSignatureSpace(element, "X509Data")) {
            LOG.debug("I can't");
            return null;
        }
        x509childNodes = XMLUtils.selectDsNodes(element.getFirstChild(), "X509SubjectName");
        if (x509childNodes == null || x509childNodes.length <= 0) {
            LOG.debug("I can't");
            return null;
        }
        try {
            if (storage == null) {
                Object[] exArgs = new Object[]{"X509SubjectName"};
                KeyResolverException ex = new KeyResolverException("KeyResolver.needStorageResolver", exArgs);
                LOG.debug("", ex);
                throw ex;
            }
            x509childObject = new XMLX509SubjectName[x509childNodes.length];
            for (int i = 0; i < x509childNodes.length; ++i) {
                x509childObject[i] = new XMLX509SubjectName(x509childNodes[i], baseURI);
            }
            Iterator<Certificate> storageIterator = storage.getIterator();
            while (storageIterator.hasNext()) {
                X509Certificate cert = (X509Certificate)storageIterator.next();
                XMLX509SubjectName certSN = new XMLX509SubjectName(element.getOwnerDocument(), cert);
                LOG.debug("Found Certificate SN: {}", (Object)certSN.getSubjectName());
                for (int i = 0; i < x509childObject.length; ++i) {
                    LOG.debug("Found Element SN:     {}", (Object)x509childObject[i].getSubjectName());
                    if (certSN.equals(x509childObject[i])) {
                        LOG.debug("match !!! ");
                        return cert;
                    }
                    LOG.debug("no match...");
                }
            }
            return null;
        }
        catch (XMLSecurityException ex) {
            LOG.debug("XMLSecurityException", ex);
            throw new KeyResolverException(ex);
        }
    }

    @Override
    public SecretKey engineLookupAndResolveSecretKey(Element element, String baseURI, StorageResolver storage) {
        return null;
    }
}

