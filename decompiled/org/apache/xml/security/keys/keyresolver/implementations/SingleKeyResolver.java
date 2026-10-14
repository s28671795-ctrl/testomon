/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.keys.keyresolver.implementations;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.crypto.SecretKey;
import org.apache.xml.security.keys.keyresolver.KeyResolverException;
import org.apache.xml.security.keys.keyresolver.KeyResolverSpi;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class SingleKeyResolver
extends KeyResolverSpi {
    private static final Logger LOG = LoggerFactory.getLogger(SingleKeyResolver.class);
    private String keyName;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private SecretKey secretKey;

    public SingleKeyResolver(String keyName, PublicKey publicKey) {
        this.keyName = keyName;
        this.publicKey = publicKey;
    }

    public SingleKeyResolver(String keyName, PrivateKey privateKey) {
        this.keyName = keyName;
        this.privateKey = privateKey;
    }

    public SingleKeyResolver(String keyName, SecretKey secretKey) {
        this.keyName = keyName;
        this.secretKey = secretKey;
    }

    @Override
    public boolean engineCanResolve(Element element, String baseURI, StorageResolver storage) {
        return XMLUtils.elementIsInSignatureSpace(element, "KeyName");
    }

    @Override
    public PublicKey engineLookupAndResolvePublicKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
        String name;
        LOG.debug("Can I resolve {}?", (Object)element.getTagName());
        if (this.publicKey != null && XMLUtils.elementIsInSignatureSpace(element, "KeyName") && this.keyName.equals(name = element.getFirstChild().getNodeValue())) {
            return this.publicKey;
        }
        LOG.debug("I can't");
        return null;
    }

    @Override
    public X509Certificate engineLookupResolveX509Certificate(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
        return null;
    }

    @Override
    public SecretKey engineResolveSecretKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
        String name;
        LOG.debug("Can I resolve {}?", (Object)element.getTagName());
        if (this.secretKey != null && XMLUtils.elementIsInSignatureSpace(element, "KeyName") && this.keyName.equals(name = element.getFirstChild().getNodeValue())) {
            return this.secretKey;
        }
        LOG.debug("I can't");
        return null;
    }

    @Override
    public PrivateKey engineLookupAndResolvePrivateKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
        String name;
        LOG.debug("Can I resolve {}?", (Object)element.getTagName());
        if (this.privateKey != null && XMLUtils.elementIsInSignatureSpace(element, "KeyName") && this.keyName.equals(name = element.getFirstChild().getNodeValue())) {
            return this.privateKey;
        }
        LOG.debug("I can't");
        return null;
    }
}

