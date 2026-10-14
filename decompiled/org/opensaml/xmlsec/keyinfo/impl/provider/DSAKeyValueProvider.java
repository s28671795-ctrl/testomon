/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.keyinfo.impl.provider;

import java.security.KeyException;
import java.security.PublicKey;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.criteria.KeyAlgorithmCriterion;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoSupport;
import org.opensaml.xmlsec.keyinfo.impl.KeyInfoCredentialContext;
import org.opensaml.xmlsec.keyinfo.impl.KeyInfoResolutionContext;
import org.opensaml.xmlsec.keyinfo.impl.provider.AbstractKeyInfoProvider;
import org.opensaml.xmlsec.signature.DSAKeyValue;
import org.opensaml.xmlsec.signature.KeyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DSAKeyValueProvider
extends AbstractKeyInfoProvider {
    private final Logger log = LoggerFactory.getLogger(DSAKeyValueProvider.class);

    @Override
    public boolean handles(@Nonnull XMLObject keyInfoChild) {
        return this.getDSAKeyValue(keyInfoChild) != null;
    }

    @Override
    @Nullable
    public Collection<Credential> process(@Nonnull KeyInfoCredentialResolver resolver2, @Nonnull XMLObject keyInfoChild, @Nullable CriteriaSet criteriaSet, @Nonnull KeyInfoResolutionContext kiContext) throws SecurityException {
        KeyAlgorithmCriterion algorithmCriteria;
        DSAKeyValue keyValue = this.getDSAKeyValue(keyInfoChild);
        if (keyValue == null) {
            return null;
        }
        if (criteriaSet != null && (algorithmCriteria = criteriaSet.get(KeyAlgorithmCriterion.class)) != null && algorithmCriteria.getKeyAlgorithm() != null && !"DSA".equals(algorithmCriteria.getKeyAlgorithm())) {
            this.log.debug("Criterion specified non-DSA key algorithm, skipping");
            return null;
        }
        this.log.debug("Attempting to extract credential from a DSAKeyValue");
        PublicKey pubKey = null;
        try {
            pubKey = KeyInfoSupport.getDSAKey(keyValue);
        }
        catch (KeyException e) {
            this.log.error("Error extracting DSA key value", e);
            throw new SecurityException("Error extracting DSA key value", e);
        }
        BasicCredential cred = new BasicCredential(pubKey);
        cred.getKeyNames().addAll(kiContext.getKeyNames());
        KeyInfoCredentialContext credContext = this.buildCredentialContext(kiContext);
        if (credContext != null) {
            cred.getCredentialContextSet().add(credContext);
        }
        this.log.debug("Credential successfully extracted from DSAKeyValue");
        LazySet<Credential> credentialSet = new LazySet<Credential>();
        credentialSet.add(cred);
        return credentialSet;
    }

    protected DSAKeyValue getDSAKeyValue(@Nonnull XMLObject xmlObject) {
        if (xmlObject instanceof DSAKeyValue) {
            return (DSAKeyValue)xmlObject;
        }
        if (xmlObject instanceof KeyValue) {
            return ((KeyValue)xmlObject).getDSAKeyValue();
        }
        return null;
    }
}

