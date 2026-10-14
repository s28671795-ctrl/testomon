/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential.impl;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.UnrecoverableEntryException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.credential.impl.AbstractCriteriaFilteringCredentialResolver;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.security.x509.X509Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyStoreCredentialResolver
extends AbstractCriteriaFilteringCredentialResolver {
    private final Logger log = LoggerFactory.getLogger(KeyStoreCredentialResolver.class);
    private final KeyStore keyStore;
    private final Map<String, String> keyPasswords;
    private final UsageType keystoreUsage;

    public KeyStoreCredentialResolver(@Nonnull KeyStore store, @Nonnull Map<String, String> passwords) {
        this(store, passwords, null);
    }

    public KeyStoreCredentialResolver(@Nonnull KeyStore store, @Nonnull Map<String, String> passwords, @Nullable UsageType usage) {
        this.keyStore = Constraint.isNotNull(store, "Provided key store cannot be null");
        this.keyPasswords = Constraint.isNotNull(passwords, "Password map cannot be null");
        try {
            store.size();
        }
        catch (KeyStoreException e) {
            throw new IllegalStateException("Keystore has not been initialized.");
        }
        this.keystoreUsage = usage != null ? usage : UsageType.UNSPECIFIED;
    }

    @Override
    @Nonnull
    protected Iterable<Credential> resolveFromSource(@Nullable CriteriaSet criteriaSet) throws ResolverException {
        this.checkCriteriaRequirements(criteriaSet);
        String entityID = criteriaSet.get(EntityIdCriterion.class).getEntityId();
        UsageCriterion usageCriteria = criteriaSet.get(UsageCriterion.class);
        UsageType usage = usageCriteria != null ? usageCriteria.getUsage() : UsageType.UNSPECIFIED;
        if (!this.matchUsage(this.keystoreUsage, usage)) {
            this.log.debug("Specified usage criteria {} does not match keystore usage {}", (Object)usage, (Object)this.keystoreUsage);
            this.log.debug("Can not resolve credentials from this keystore");
            return Collections.emptySet();
        }
        KeyStore.PasswordProtection keyPassword = null;
        if (this.keyPasswords.containsKey(entityID)) {
            keyPassword = new KeyStore.PasswordProtection(this.keyPasswords.get(entityID).toCharArray());
        }
        KeyStore.Entry keyStoreEntry = null;
        try {
            keyStoreEntry = this.keyStore.getEntry(entityID, keyPassword);
        }
        catch (UnrecoverableEntryException e) {
            this.log.error("Unable to retrieve keystore entry for entityID (keystore alias): {}", (Object)entityID);
            this.log.error("Check for invalid keystore entityID/alias entry password");
            throw new ResolverException("Could not retrieve entry from keystore", e);
        }
        catch (GeneralSecurityException e) {
            this.log.error("Unable to retrieve keystore entry for entityID (keystore alias): {}", (Object)entityID, (Object)e);
            throw new ResolverException("Could not retrieve entry from keystore", e);
        }
        if (keyStoreEntry == null) {
            this.log.debug("Keystore entry for entityID (keystore alias) {} does not exist", (Object)entityID);
            return Collections.emptySet();
        }
        Credential credential = this.buildCredential(keyStoreEntry, entityID, this.keystoreUsage);
        return Collections.singleton(credential);
    }

    protected void checkCriteriaRequirements(@Nullable CriteriaSet criteriaSet) {
        if (criteriaSet == null || criteriaSet.get(EntityIdCriterion.class) == null) {
            this.log.error("EntityIDCriterion was not specified in the criteria set, resolution cannot be attempted");
            throw new IllegalArgumentException("No EntityIDCriterion was available in criteria set");
        }
    }

    protected boolean matchUsage(@Nonnull UsageType keyStoreUsage, @Nonnull UsageType criteriaUsage) {
        if (keyStoreUsage == UsageType.UNSPECIFIED || criteriaUsage == UsageType.UNSPECIFIED) {
            return true;
        }
        return keyStoreUsage == criteriaUsage;
    }

    @Nonnull
    protected Credential buildCredential(@Nonnull KeyStore.Entry keyStoreEntry, @Nonnull String entityID, @Nonnull UsageType usage) throws ResolverException {
        this.log.debug("Building credential from keystore entry for entityID {}, usage type {}", (Object)entityID, (Object)usage);
        if (keyStoreEntry instanceof KeyStore.PrivateKeyEntry) {
            return this.processPrivateKeyEntry((KeyStore.PrivateKeyEntry)keyStoreEntry, entityID, this.keystoreUsage);
        }
        if (keyStoreEntry instanceof KeyStore.TrustedCertificateEntry) {
            return this.processTrustedCertificateEntry((KeyStore.TrustedCertificateEntry)keyStoreEntry, entityID, this.keystoreUsage);
        }
        if (keyStoreEntry instanceof KeyStore.SecretKeyEntry) {
            return this.processSecretKeyEntry((KeyStore.SecretKeyEntry)keyStoreEntry, entityID, this.keystoreUsage);
        }
        throw new ResolverException("KeyStore entry was of an unsupported type: " + keyStoreEntry.getClass().getName());
    }

    protected X509Credential processTrustedCertificateEntry(@Nonnull KeyStore.TrustedCertificateEntry trustedCertEntry, @Nonnull String entityID, @Nonnull UsageType usage) {
        this.log.debug("Processing TrustedCertificateEntry from keystore");
        X509Certificate cert = (X509Certificate)trustedCertEntry.getTrustedCertificate();
        BasicX509Credential credential = new BasicX509Credential(cert);
        credential.setEntityId(entityID);
        credential.setUsageType(usage);
        ArrayList<X509Certificate> certChain = new ArrayList<X509Certificate>();
        certChain.add(cert);
        credential.setEntityCertificateChain(certChain);
        return credential;
    }

    protected X509Credential processPrivateKeyEntry(@Nonnull KeyStore.PrivateKeyEntry privateKeyEntry, @Nonnull String entityID, @Nonnull UsageType usage) {
        this.log.debug("Processing PrivateKeyEntry from keystore");
        BasicX509Credential credential = new BasicX509Credential((X509Certificate)privateKeyEntry.getCertificate(), privateKeyEntry.getPrivateKey());
        credential.setEntityId(entityID);
        credential.setUsageType(usage);
        credential.setEntityCertificateChain(Arrays.asList((X509Certificate[])privateKeyEntry.getCertificateChain()));
        return credential;
    }

    protected Credential processSecretKeyEntry(@Nonnull KeyStore.SecretKeyEntry secretKeyEntry, @Nonnull String entityID, @Nonnull UsageType usage) {
        this.log.debug("Processing SecretKeyEntry from keystore");
        BasicCredential credential = new BasicCredential(secretKeyEntry.getSecretKey());
        credential.setEntityId(entityID);
        credential.setUsageType(usage);
        return credential;
    }
}

