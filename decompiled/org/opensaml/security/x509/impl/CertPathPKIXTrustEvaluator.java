/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509.impl;

import java.security.GeneralSecurityException;
import java.security.cert.CRL;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.SecurityException;
import org.opensaml.security.x509.InternalX500DNHandler;
import org.opensaml.security.x509.PKIXTrustEvaluator;
import org.opensaml.security.x509.PKIXValidationInformation;
import org.opensaml.security.x509.PKIXValidationOptions;
import org.opensaml.security.x509.X500DNHandler;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.security.x509.X509Support;
import org.opensaml.security.x509.impl.CertPathPKIXValidationOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CertPathPKIXTrustEvaluator
implements PKIXTrustEvaluator {
    private final Logger log = LoggerFactory.getLogger(CertPathPKIXTrustEvaluator.class);
    private X500DNHandler x500DNHandler;
    private PKIXValidationOptions options;

    public CertPathPKIXTrustEvaluator() {
        this.options = new PKIXValidationOptions();
        this.x500DNHandler = new InternalX500DNHandler();
    }

    public CertPathPKIXTrustEvaluator(@Nonnull PKIXValidationOptions newOptions) {
        this.options = Constraint.isNotNull(newOptions, "PKIXValidationOptions cannot be null");
        this.x500DNHandler = new InternalX500DNHandler();
    }

    @Override
    @Nonnull
    public PKIXValidationOptions getPKIXValidationOptions() {
        return this.options;
    }

    public void setPKIXValidationOptions(@Nonnull PKIXValidationOptions newOptions) {
        this.options = Constraint.isNotNull(newOptions, "PKIXValidationOptions cannot be null");
    }

    @Nonnull
    public X500DNHandler getX500DNHandler() {
        return this.x500DNHandler;
    }

    public void setX500DNHandler(@Nonnull X500DNHandler handler) {
        this.x500DNHandler = Constraint.isNotNull(handler, "X500DNHandler cannot be null");
    }

    @Override
    public boolean validate(@Nonnull PKIXValidationInformation validationInfo, @Nonnull X509Credential untrustedCredential) throws SecurityException {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Attempting PKIX path validation on untrusted credential: {}", (Object)X509Support.getIdentifiersToken(untrustedCredential, this.getX500DNHandler()));
        }
        try {
            PKIXBuilderParameters params = this.getPKIXBuilderParameters(validationInfo, untrustedCredential);
            this.log.trace("Building certificate validation path");
            CertPathBuilder builder = CertPathBuilder.getInstance("PKIX");
            PKIXCertPathBuilderResult buildResult = (PKIXCertPathBuilderResult)builder.build(params);
            if (this.log.isDebugEnabled()) {
                this.logCertPathDebug(buildResult, untrustedCredential.getEntityCertificate());
                this.log.debug("PKIX validation succeeded for untrusted credential: {}", (Object)X509Support.getIdentifiersToken(untrustedCredential, this.getX500DNHandler()));
            }
            return true;
        }
        catch (CertPathBuilderException e) {
            if (this.log.isTraceEnabled()) {
                this.log.trace("PKIX path construction failed for untrusted credential: " + X509Support.getIdentifiersToken(untrustedCredential, this.getX500DNHandler()), e);
            } else {
                this.log.error("PKIX path construction failed for untrusted credential: " + X509Support.getIdentifiersToken(untrustedCredential, this.getX500DNHandler()) + ": " + e.getMessage());
            }
            return false;
        }
        catch (GeneralSecurityException e) {
            this.log.error("PKIX validation failure", e);
            throw new SecurityException("PKIX validation failure", e);
        }
    }

    protected PKIXBuilderParameters getPKIXBuilderParameters(@Nonnull PKIXValidationInformation validationInfo, @Nonnull X509Credential untrustedCredential) throws GeneralSecurityException {
        Set<TrustAnchor> trustAnchors = this.getTrustAnchors(validationInfo);
        if (trustAnchors == null || trustAnchors.isEmpty()) {
            throw new GeneralSecurityException("Unable to validate X509 certificate, no trust anchors found in the PKIX validation information");
        }
        X509CertSelector selector = new X509CertSelector();
        selector.setCertificate(untrustedCredential.getEntityCertificate());
        this.log.trace("Adding trust anchors to PKIX validator parameters");
        PKIXBuilderParameters params = new PKIXBuilderParameters(trustAnchors, (CertSelector)selector);
        Integer effectiveVerifyDepth = this.getEffectiveVerificationDepth(validationInfo);
        this.log.trace("Setting max verification depth to: {} ", (Object)effectiveVerifyDepth);
        params.setMaxPathLength(effectiveVerifyDepth);
        CertStore certStore = this.buildCertStore(validationInfo, untrustedCredential);
        params.addCertStore(certStore);
        boolean isForceRevocationEnabled = false;
        boolean forcedRevocation = false;
        boolean policyMappingInhibited = false;
        boolean anyPolicyInhibited = false;
        Set<String> initialPolicies = null;
        if (this.options instanceof CertPathPKIXValidationOptions) {
            CertPathPKIXValidationOptions certpathOptions = (CertPathPKIXValidationOptions)this.options;
            isForceRevocationEnabled = certpathOptions.isForceRevocationEnabled();
            forcedRevocation = certpathOptions.isRevocationEnabled();
            policyMappingInhibited = certpathOptions.isPolicyMappingInhibited();
            anyPolicyInhibited = certpathOptions.isAnyPolicyInhibited();
            initialPolicies = certpathOptions.getInitialPolicies();
        }
        if (isForceRevocationEnabled) {
            this.log.trace("PKIXBuilderParameters#setRevocationEnabled is being forced to: {}", (Object)forcedRevocation);
            params.setRevocationEnabled(forcedRevocation);
        } else if (this.storeContainsCRLs(certStore)) {
            this.log.trace("At least one CRL was present in cert store, enabling revocation checking");
            params.setRevocationEnabled(true);
        } else {
            this.log.trace("No CRLs present in cert store, disabling revocation checking");
            params.setRevocationEnabled(false);
        }
        params.setPolicyMappingInhibited(policyMappingInhibited);
        params.setAnyPolicyInhibited(anyPolicyInhibited);
        if (initialPolicies != null && !initialPolicies.isEmpty()) {
            this.log.debug("PKIXBuilderParameters#setInitialPolicies is being set to: {}", (Object)initialPolicies.toString());
            params.setInitialPolicies(initialPolicies);
            params.setExplicitPolicyRequired(true);
        }
        this.log.trace("PKIXBuilderParameters successfully created: {}", (Object)params.toString());
        return params;
    }

    protected boolean storeContainsCRLs(@Nonnull CertStore certStore) {
        Collection<? extends CRL> crls = null;
        try {
            crls = certStore.getCRLs(null);
        }
        catch (CertStoreException e) {
            this.log.error("Error examining cert store for CRL's, treating as if no CRL's present", e);
            return false;
        }
        return crls != null && !crls.isEmpty();
    }

    @Nonnull
    protected Integer getEffectiveVerificationDepth(@Nonnull PKIXValidationInformation validationInfo) {
        Integer effectiveVerifyDepth = validationInfo.getVerificationDepth();
        if (effectiveVerifyDepth == null) {
            effectiveVerifyDepth = this.options.getDefaultVerificationDepth();
        }
        return effectiveVerifyDepth;
    }

    @Nullable
    protected Set<TrustAnchor> getTrustAnchors(@Nonnull PKIXValidationInformation validationInfo) {
        Collection<X509Certificate> validationCertificates = validationInfo.getCertificates();
        if (validationCertificates == null || validationCertificates.isEmpty()) {
            return null;
        }
        this.log.trace("Constructing trust anchors for PKIX validation");
        HashSet<TrustAnchor> trustAnchors = new HashSet<TrustAnchor>();
        for (X509Certificate cert : validationCertificates) {
            trustAnchors.add(this.buildTrustAnchor(cert));
        }
        if (this.log.isTraceEnabled()) {
            for (TrustAnchor anchor : trustAnchors) {
                this.log.trace("TrustAnchor: {}", (Object)anchor.toString());
            }
        }
        return trustAnchors;
    }

    @Nonnull
    protected TrustAnchor buildTrustAnchor(@Nonnull X509Certificate cert) {
        return new TrustAnchor(cert, null);
    }

    @Nonnull
    protected CertStore buildCertStore(@Nonnull PKIXValidationInformation validationInfo, @Nonnull X509Credential untrustedCredential) throws GeneralSecurityException {
        this.log.trace("Creating cert store to use during path validation");
        this.log.trace("Adding entity certificate chain to cert store");
        ArrayList<Object> storeMaterial = new ArrayList<Object>(untrustedCredential.getEntityCertificateChain());
        if (this.log.isTraceEnabled()) {
            for (X509Certificate cert : untrustedCredential.getEntityCertificateChain()) {
                this.log.trace(String.format("Added X509Certificate from entity cert chain to cert store with subject name '%s' issued by '%s' with serial number '%s'", this.getX500DNHandler().getName(cert.getSubjectX500Principal()), this.getX500DNHandler().getName(cert.getIssuerX500Principal()), cert.getSerialNumber().toString()));
            }
        }
        Date now = new Date();
        Collection<X509CRL> crls = validationInfo.getCRLs();
        if (crls != null && !crls.isEmpty()) {
            this.log.trace("Processing CRLs from PKIX info set");
            this.addCRLsToStoreMaterial(storeMaterial, crls, now);
        }
        if ((crls = untrustedCredential.getCRLs()) != null && !crls.isEmpty() && this.options.isProcessCredentialCRLs()) {
            this.log.trace("Processing CRLs from untrusted credential");
            this.addCRLsToStoreMaterial(storeMaterial, crls, now);
        }
        return CertStore.getInstance("Collection", new CollectionCertStoreParameters(storeMaterial));
    }

    protected void addCRLsToStoreMaterial(@Nonnull List<Object> storeMaterial, @Nonnull Collection<X509CRL> crls, @Nonnull Date now) {
        for (X509CRL crl : crls) {
            boolean isEmpty = crl.getRevokedCertificates() == null || crl.getRevokedCertificates().isEmpty();
            boolean isExpired = crl.getNextUpdate().before(now);
            if (!isEmpty || this.options.isProcessEmptyCRLs()) {
                if (!isExpired || this.options.isProcessExpiredCRLs()) {
                    storeMaterial.add(crl);
                    if (this.log.isTraceEnabled()) {
                        this.log.trace("Added X509CRL to cert store from issuer {} dated {}", (Object)this.getX500DNHandler().getName(crl.getIssuerX500Principal()), (Object)crl.getThisUpdate());
                        if (isEmpty) {
                            this.log.trace("X509CRL added to cert store from issuer {} dated {} was empty", (Object)this.getX500DNHandler().getName(crl.getIssuerX500Principal()), (Object)crl.getThisUpdate());
                        }
                    }
                    if (!isExpired) continue;
                    this.log.warn("Using X509CRL from issuer {} with a nextUpdate in the past: {}", (Object)this.getX500DNHandler().getName(crl.getIssuerX500Principal()), (Object)crl.getNextUpdate());
                    continue;
                }
                if (!this.log.isTraceEnabled()) continue;
                this.log.trace("Expired X509CRL not added to cert store, from issuer {} nextUpdate {}", (Object)this.getX500DNHandler().getName(crl.getIssuerX500Principal()), (Object)crl.getNextUpdate());
                continue;
            }
            if (!this.log.isTraceEnabled()) continue;
            this.log.trace("Empty X509CRL not added to cert store, from issuer {} dated {}", (Object)this.getX500DNHandler().getName(crl.getIssuerX500Principal()), (Object)crl.getThisUpdate());
        }
    }

    private void logCertPathDebug(@Nonnull PKIXCertPathBuilderResult buildResult, @Nonnull X509Certificate targetCert) {
        this.log.debug("Built valid PKIX cert path");
        this.log.debug("Target certificate: {}", (Object)this.getX500DNHandler().getName(targetCert.getSubjectX500Principal()));
        for (Certificate certificate : buildResult.getCertPath().getCertificates()) {
            this.log.debug("CertPath certificate: {}", (Object)this.getX500DNHandler().getName(((X509Certificate)certificate).getSubjectX500Principal()));
        }
        TrustAnchor ta = buildResult.getTrustAnchor();
        if (ta.getTrustedCert() != null) {
            this.log.debug("TrustAnchor: {}", (Object)this.getX500DNHandler().getName(ta.getTrustedCert().getSubjectX500Principal()));
        } else if (ta.getCA() != null) {
            this.log.debug("TrustAnchor: {}", (Object)this.getX500DNHandler().getName(ta.getCA()));
        } else {
            this.log.debug("TrustAnchor: {}", (Object)ta.getCAName());
        }
    }
}

