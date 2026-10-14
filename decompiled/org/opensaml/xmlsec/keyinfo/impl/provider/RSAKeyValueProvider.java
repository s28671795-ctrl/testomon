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
import org.opensaml.xmlsec.signature.KeyValue;
import org.opensaml.xmlsec.signature.RSAKeyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSAKeyValueProvider
extends AbstractKeyInfoProvider {
    private final Logger log = LoggerFactory.getLogger(RSAKeyValueProvider.class);

    @Override
    public boolean handles(@Nonnull XMLObject keyInfoChild) {
        return this.getRSAKeyValue(keyInfoChild) != null;
    }

    @Override
    @Nullable
    public Collection<Credential> process(@Nonnull KeyInfoCredentialResolver resolver2, @Nonnull XMLObject keyInfoChild, @Nullable CriteriaSet criteriaSet, @Nonnull KeyInfoResolutionContext kiContext) throws SecurityException {
        KeyAlgorithmCriterion algorithmCriteria;
        RSAKeyValue keyValue = this.getRSAKeyValue(keyInfoChild);
        if (keyValue == null) {
            return null;
        }
        if (criteriaSet != null && (algorithmCriteria = criteriaSet.get(KeyAlgorithmCriterion.class)) != null && algorithmCriteria.getKeyAlgorithm() != null && !"RSA".equals(algorithmCriteria.getKeyAlgorithm())) {
            this.log.debug("Criterion specified non-RSA key algorithm, skipping");
            return null;
        }
        this.log.debug("Attempting to extract credential from an RSAKeyValue");
        PublicKey pubKey = null;
        try {
            pubKey = KeyInfoSupport.getRSAKey(keyValue);
        }
        catch (KeyException e) {
            this.log.error("Error extracting RSA key value", e);
            throw new SecurityException("Error extracting RSA key value", e);
        }
        BasicCredential cred = new BasicCredential(pubKey);
        cred.getKeyNames().addAll(kiContext.getKeyNames());
        KeyInfoCredentialContext credContext = this.buildCredentialContext(kiContext);
        if (credContext != null) {
            cred.getCredentialContextSet().add(credContext);
        }
        this.log.debug("Credential successfully extracted from RSAKeyValue");
        LazySet<Credential> credentialSet = new LazySet<Credential>();
        credentialSet.add(cred);
        return credentialSet;
    }

    @Nullable
    protected RSAKeyValue getRSAKeyValue(@Nonnull XMLObject xmlObject) {
        if (xmlObject instanceof RSAKeyValue) {
            return (RSAKeyValue)xmlObject;
        }
        if (xmlObject instanceof KeyValue) {
            return ((KeyValue)xmlObject).getRSAKeyValue();
        }
        return null;
    }
}

