/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509.impl;

import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.security.auth.x500.X500Principal;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.SecurityException;
import org.opensaml.security.x509.InternalX500DNHandler;
import org.opensaml.security.x509.X500DNHandler;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.security.x509.X509Support;
import org.opensaml.security.x509.impl.X509CredentialNameEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicX509CredentialNameEvaluator
implements X509CredentialNameEvaluator {
    private final Logger log = LoggerFactory.getLogger(BasicX509CredentialNameEvaluator.class);
    private boolean checkSubjectAltNames;
    private boolean checkSubjectDNCommonName;
    private boolean checkSubjectDN;
    private Set<Integer> subjectAltNameTypes;
    private X500DNHandler x500DNHandler = new InternalX500DNHandler();

    public BasicX509CredentialNameEvaluator() {
        this.setCheckSubjectAltNames(true);
        this.setCheckSubjectDNCommonName(true);
        this.setCheckSubjectDN(true);
        this.setSubjectAltNameTypes(new HashSet<Integer>(Arrays.asList(X509Support.DNS_ALT_NAME, X509Support.URI_ALT_NAME)));
    }

    public boolean isNameCheckingActive() {
        return this.checkSubjectAltNames() || this.checkSubjectDNCommonName() || this.checkSubjectDN();
    }

    @Nonnull
    @NonnullElements
    @NotLive
    @Unmodifiable
    public Set<Integer> getSubjectAltNameTypes() {
        return ImmutableSet.copyOf(this.subjectAltNameTypes);
    }

    public void setSubjectAltNameTypes(@Nullable Set<Integer> nameTypes) {
        if (nameTypes == null) {
            this.subjectAltNameTypes = Collections.emptySet();
        } else {
            this.subjectAltNameTypes = new HashSet<Integer>();
            this.subjectAltNameTypes.addAll(Collections2.filter(nameTypes, Predicates.notNull()));
        }
    }

    public boolean checkSubjectAltNames() {
        return this.checkSubjectAltNames;
    }

    public void setCheckSubjectAltNames(boolean check2) {
        this.checkSubjectAltNames = check2;
    }

    public boolean checkSubjectDNCommonName() {
        return this.checkSubjectDNCommonName;
    }

    public void setCheckSubjectDNCommonName(boolean check2) {
        this.checkSubjectDNCommonName = check2;
    }

    public boolean checkSubjectDN() {
        return this.checkSubjectDN;
    }

    public void setCheckSubjectDN(boolean check2) {
        this.checkSubjectDN = check2;
    }

    @Nonnull
    public X500DNHandler getX500DNHandler() {
        return this.x500DNHandler;
    }

    public void setX500DNHandler(@Nonnull X500DNHandler handler) {
        this.x500DNHandler = Constraint.isNotNull(handler, "X500DNHandler cannot be null");
    }

    @Override
    public boolean evaluate(@Nonnull X509Credential credential, @Nullable Set<String> trustedNames) throws SecurityException {
        if (!this.isNameCheckingActive()) {
            this.log.debug("No trusted name options are active, skipping name evaluation");
            return true;
        }
        if (trustedNames == null || trustedNames.isEmpty()) {
            this.log.debug("Supplied trusted names are null or empty, failing name evaluation");
            return false;
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("Checking trusted names against credential: {}", (Object)X509Support.getIdentifiersToken(credential, this.x500DNHandler));
            this.log.debug("Trusted names being evaluated are: {}", (Object)trustedNames.toString());
        }
        return this.processNameChecks(credential, trustedNames);
    }

    protected boolean processNameChecks(@Nonnull X509Credential credential, @Nonnull Set<String> trustedNames) {
        X509Certificate entityCertificate = credential.getEntityCertificate();
        if (this.checkSubjectAltNames() && this.processSubjectAltNames(entityCertificate, trustedNames)) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Credential {} passed name check based on subject alt names", (Object)X509Support.getIdentifiersToken(credential, this.x500DNHandler));
            }
            return true;
        }
        if (this.checkSubjectDNCommonName() && this.processSubjectDNCommonName(entityCertificate, trustedNames)) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Credential {} passed name check based on subject common name", (Object)X509Support.getIdentifiersToken(credential, this.x500DNHandler));
            }
            return true;
        }
        if (this.checkSubjectDN() && this.processSubjectDN(entityCertificate, trustedNames)) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Credential {} passed name check based on subject DN", (Object)X509Support.getIdentifiersToken(credential, this.x500DNHandler));
            }
            return true;
        }
        this.log.error("Credential failed name check: {}", (Object)X509Support.getIdentifiersToken(credential, this.x500DNHandler));
        return false;
    }

    protected boolean processSubjectDNCommonName(@Nonnull X509Certificate certificate, @Nonnull Set<String> trustedNames) {
        this.log.debug("Processing subject DN common name");
        X500Principal subjectPrincipal = certificate.getSubjectX500Principal();
        List<String> commonNames = X509Support.getCommonNames(subjectPrincipal);
        if (commonNames == null || commonNames.isEmpty()) {
            return false;
        }
        String commonName = commonNames.get(0);
        this.log.debug("Extracted common name from certificate: {}", (Object)commonName);
        if (!Strings.isNullOrEmpty(commonName) && trustedNames.contains(commonName)) {
            this.log.debug("Matched subject DN common name to trusted names: {}", (Object)commonName);
            return true;
        }
        return false;
    }

    protected boolean processSubjectDN(@Nonnull X509Certificate certificate, @Nonnull Set<String> trustedNames) {
        this.log.debug("Processing subject DN");
        X500Principal subjectPrincipal = certificate.getSubjectX500Principal();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Extracted X500Principal from certificate: {}", (Object)this.x500DNHandler.getName(subjectPrincipal));
        }
        for (String trustedName : trustedNames) {
            X500Principal trustedNamePrincipal = null;
            try {
                trustedNamePrincipal = this.x500DNHandler.parse(trustedName);
                this.log.debug("Evaluating principal successfully parsed from trusted name: {}", (Object)trustedName);
                if (!subjectPrincipal.equals(trustedNamePrincipal)) continue;
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Matched subject DN to trusted names: {}", (Object)this.x500DNHandler.getName(subjectPrincipal));
                }
                return true;
            }
            catch (IllegalArgumentException e) {
                this.log.debug("Trusted name was not a DN or could not be parsed: {}", (Object)trustedName);
            }
        }
        return false;
    }

    protected boolean processSubjectAltNames(@Nonnull X509Certificate certificate, @Nonnull Set<String> trustedNames) {
        this.log.debug("Processing subject alt names");
        Integer[] nameTypes = new Integer[this.getSubjectAltNameTypes().size()];
        this.getSubjectAltNameTypes().toArray(nameTypes);
        List altNames = X509Support.getAltNames(certificate, nameTypes);
        if (altNames != null) {
            this.log.debug("Extracted subject alt names from certificate: {}", (Object)altNames);
            for (Object altName : altNames) {
                if (!trustedNames.contains(altName)) continue;
                this.log.debug("Matched subject alt name to trusted names: {}", (Object)altName.toString());
                return true;
            }
        }
        return false;
    }
}

