/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.keyinfo.impl;

import java.security.Key;
import java.security.KeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.impl.AbstractCriteriaFilteringCredentialResolver;
import org.opensaml.security.crypto.KeySupport;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCriterion;
import org.opensaml.xmlsec.keyinfo.KeyInfoSupport;
import org.opensaml.xmlsec.keyinfo.impl.KeyInfoProvider;
import org.opensaml.xmlsec.keyinfo.impl.KeyInfoResolutionContext;
import org.opensaml.xmlsec.signature.DEREncodedKeyValue;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.KeyName;
import org.opensaml.xmlsec.signature.KeyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicProviderKeyInfoCredentialResolver
extends AbstractCriteriaFilteringCredentialResolver
implements KeyInfoCredentialResolver {
    private final Logger log = LoggerFactory.getLogger(BasicProviderKeyInfoCredentialResolver.class);
    private final List<KeyInfoProvider> providers = new ArrayList<KeyInfoProvider>();

    public BasicProviderKeyInfoCredentialResolver(@Nonnull List<KeyInfoProvider> keyInfoProviders) {
        this.providers.addAll(keyInfoProviders);
    }

    @Nonnull
    protected List<KeyInfoProvider> getProviders() {
        return this.providers;
    }

    @Override
    @Nonnull
    protected Iterable<Credential> resolveFromSource(@Nullable CriteriaSet criteriaSet) throws ResolverException {
        KeyInfoCriterion kiCriteria = null;
        if (criteriaSet != null) {
            kiCriteria = criteriaSet.get(KeyInfoCriterion.class);
        }
        if (kiCriteria == null) {
            this.log.error("No KeyInfo criteria supplied, resolver could not process");
            throw new ResolverException("Credential criteria set did not contain an instance of KeyInfoCredentialCriteria");
        }
        KeyInfo keyInfo = kiCriteria.getKeyInfo();
        ArrayList<Credential> credentials = new ArrayList<Credential>();
        KeyInfoResolutionContext kiContext = new KeyInfoResolutionContext(credentials);
        if (keyInfo != null) {
            this.processKeyInfo(keyInfo, kiContext, criteriaSet, credentials);
        } else {
            this.log.info("KeyInfo was null, any credentials will be resolved by post-processing hooks only");
        }
        this.postProcess(kiContext, criteriaSet, credentials);
        if (credentials.isEmpty()) {
            this.log.debug("No credentials were found, calling empty credentials post-processing hook");
            this.postProcessEmptyCredentials(kiContext, criteriaSet, credentials);
        }
        this.log.debug("A total of {} credentials were resolved", (Object)credentials.size());
        return credentials;
    }

    private void processKeyInfo(@Nonnull KeyInfo keyInfo, @Nonnull KeyInfoResolutionContext kiContext, @Nullable CriteriaSet criteriaSet, @Nonnull List<Credential> credentials) throws ResolverException {
        Credential keyValueCredential;
        this.initResolutionContext(kiContext, keyInfo, criteriaSet);
        Key keyValueKey = kiContext.getKey();
        HashSet<String> keyNames = new HashSet<String>();
        keyNames.addAll(kiContext.getKeyNames());
        this.processKeyInfoChildren(kiContext, criteriaSet, credentials);
        if (credentials.isEmpty() && keyValueKey != null && (keyValueCredential = this.buildBasicCredential(keyValueKey, keyNames)) != null) {
            this.log.debug("No credentials were extracted by registered non-KeyValue handling providers, adding KeyValue credential to returned credential set");
            credentials.add(keyValueCredential);
        }
    }

    protected void postProcess(@Nonnull KeyInfoResolutionContext kiContext, @Nullable CriteriaSet criteriaSet, @Nonnull List<Credential> credentials) throws ResolverException {
    }

    protected void postProcessEmptyCredentials(@Nonnull KeyInfoResolutionContext kiContext, @Nullable CriteriaSet criteriaSet, @Nonnull List<Credential> credentials) throws ResolverException {
    }

    protected void processKeyInfoChildren(@Nonnull KeyInfoResolutionContext kiContext, @Nullable CriteriaSet criteriaSet, @Nonnull List<Credential> credentials) throws ResolverException {
        for (XMLObject keyInfoChild : kiContext.getKeyInfo().getXMLObjects()) {
            if (keyInfoChild instanceof KeyValue || keyInfoChild instanceof DEREncodedKeyValue) continue;
            this.log.debug("Processing KeyInfo child with QName: {}", (Object)keyInfoChild.getElementQName());
            Collection<Credential> childCreds = this.processKeyInfoChild(kiContext, criteriaSet, keyInfoChild);
            if (childCreds != null && !childCreds.isEmpty()) {
                credentials.addAll(childCreds);
                continue;
            }
            if (keyInfoChild instanceof KeyName) {
                this.log.debug("KeyName {} did not independently produce a credential based on any registered providers", (Object)((KeyName)keyInfoChild).getValue());
                continue;
            }
            this.log.warn("No credentials could be extracted from KeyInfo child with QName {} by any registered provider", (Object)keyInfoChild.getElementQName());
        }
    }

    @Nullable
    protected Collection<Credential> processKeyInfoChild(@Nonnull KeyInfoResolutionContext kiContext, @Nullable CriteriaSet criteriaSet, @Nonnull XMLObject keyInfoChild) throws ResolverException {
        for (KeyInfoProvider provider : this.getProviders()) {
            Collection<Credential> creds;
            if (!provider.handles(keyInfoChild)) {
                this.log.debug("Provider {} doesn't handle objects of type {}, skipping", (Object)provider.getClass().getName(), (Object)keyInfoChild.getElementQName());
                continue;
            }
            this.log.debug("Processing KeyInfo child {} with provider {}", (Object)keyInfoChild.getElementQName(), (Object)provider.getClass().getName());
            try {
                creds = provider.process(this, keyInfoChild, criteriaSet, kiContext);
            }
            catch (SecurityException e) {
                throw new ResolverException("Error processing KeyInfo child element", e);
            }
            if (creds == null || creds.isEmpty()) continue;
            this.log.debug("Credentials successfully extracted from child {} by provider {}", (Object)keyInfoChild.getElementQName(), (Object)provider.getClass().getName());
            return creds;
        }
        return null;
    }

    protected void initResolutionContext(@Nonnull KeyInfoResolutionContext kiContext, @Nonnull KeyInfo keyInfo, @Nullable CriteriaSet criteriaSet) throws ResolverException {
        kiContext.setKeyInfo(keyInfo);
        kiContext.getKeyNames().addAll(KeyInfoSupport.getKeyNames(keyInfo));
        this.log.debug("Found {} key names: {}", (Object)kiContext.getKeyNames().size(), (Object)kiContext.getKeyNames());
        this.resolveKeyValue(kiContext, criteriaSet, keyInfo.getKeyValues());
        this.resolveKeyValue(kiContext, criteriaSet, keyInfo.getDEREncodedKeyValues());
    }

    protected void resolveKeyValue(@Nonnull KeyInfoResolutionContext kiContext, @Nullable CriteriaSet criteriaSet, @Nonnull List<? extends XMLObject> keyValues) throws ResolverException {
        for (XMLObject xMLObject : keyValues) {
            Collection<Credential> creds;
            if (!(xMLObject instanceof KeyValue) && !(xMLObject instanceof DEREncodedKeyValue) || (creds = this.processKeyInfoChild(kiContext, criteriaSet, xMLObject)) == null) continue;
            for (Credential cred : creds) {
                Key key = this.extractKeyValue(cred);
                if (key == null) continue;
                kiContext.setKey(key);
                this.log.debug("Found a credential based on a KeyValue/DEREncodedKeyValue having key type: {}", (Object)key.getAlgorithm());
                return;
            }
        }
    }

    @Nullable
    protected Credential buildBasicCredential(@Nullable Key key, @Nonnull Set<String> keyNames) throws ResolverException {
        BasicCredential basicCred;
        block11: {
            if (key == null) {
                this.log.debug("Key supplied was null, could not build credential");
                return null;
            }
            basicCred = null;
            if (key instanceof PublicKey) {
                basicCred = new BasicCredential((PublicKey)key);
            } else if (key instanceof SecretKey) {
                basicCred = new BasicCredential((SecretKey)key);
            } else if (key instanceof PrivateKey) {
                PrivateKey privateKey = (PrivateKey)key;
                try {
                    PublicKey publicKey = KeySupport.derivePublicKey(privateKey);
                    if (publicKey != null) {
                        basicCred = new BasicCredential(publicKey, privateKey);
                        break block11;
                    }
                    this.log.error("Failed to derive public key from private key");
                }
                catch (KeyException e) {
                    this.log.error("Could not derive public key from private key", e);
                }
            } else {
                this.log.error("Key was of an unsupported type '{}'", (Object)key.getClass().getName());
            }
        }
        if (basicCred != null) {
            basicCred.getKeyNames().addAll(keyNames);
        }
        return basicCred;
    }

    @Nullable
    protected Key extractKeyValue(@Nullable Credential cred) {
        if (cred != null) {
            if (cred.getPublicKey() != null) {
                return cred.getPublicKey();
            }
            if (cred.getSecretKey() != null) {
                return cred.getSecretKey();
            }
            if (cred.getPrivateKey() != null) {
                return cred.getPrivateKey();
            }
        }
        return null;
    }
}

