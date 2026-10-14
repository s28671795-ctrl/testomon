/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.keys.keyresolver.implementations;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.crypto.SecretKey;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.content.DEREncodedKeyValue;
import org.apache.xml.security.keys.keyresolver.KeyResolverException;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class DEREncodedKeyValueResolver
extends KeyResolverSpi {
    private static final Logger LOG = LoggerFactory.getLogger(DEREncodedKeyValueResolver.class);

    @Override
    public boolean engineCanResolve(Element element, String baseURI, StorageResolver storage) {
        return XMLUtils.elementIsInSignature11Space(element, "DEREncodedKeyValue");
    }

    @Override
    public PublicKey engineLookupAndResolvePublicKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
        LOG.debug("Can I resolve {}", (Object)element.getTagName());
        if (!this.engineCanResolve(element, baseURI, storage)) {
            return null;
        }
        try {
            DEREncodedKeyValue derKeyValue = new DEREncodedKeyValue(element, baseURI);
            return derKeyValue.getPublicKey();
        }
        catch (XMLSecurityException e) {
            LOG.debug("XMLSecurityException", e);
            return null;
        }
    }

    @Override
    public X509Certificate engineLookupResolveX509Certificate(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
        return null;
    }

    @Override
    public SecretKey engineLookupAndResolveSecretKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
        return null;
    }

    @Override
    public PrivateKey engineLookupAndResolvePrivateKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
        return null;
    }
}

