/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.keyinfo.impl.provider;

import com.google.common.base.Strings;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.security.auth.x500.X500Principal;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.security.x509.InternalX500DNHandler;
import org.opensaml.security.x509.X500DNHandler;
import org.opensaml.security.x509.X509Support;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoSupport;
import org.opensaml.xmlsec.keyinfo.impl.KeyInfoCredentialContext;
import org.opensaml.xmlsec.keyinfo.impl.KeyInfoResolutionContext;
import org.opensaml.xmlsec.keyinfo.impl.provider.AbstractKeyInfoProvider;
import org.opensaml.xmlsec.signature.X509Data;
import org.opensaml.xmlsec.signature.X509Digest;
import org.opensaml.xmlsec.signature.X509IssuerSerial;
import org.opensaml.xmlsec.signature.X509SKI;
import org.opensaml.xmlsec.signature.X509SubjectName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InlineX509DataProvider
extends AbstractKeyInfoProvider {
    private final Logger log = LoggerFactory.getLogger(InlineX509DataProvider.class);
    private X500DNHandler x500DNHandler = new InternalX500DNHandler();

    @Nonnull
    public X500DNHandler getX500DNHandler() {
        return this.x500DNHandler;
    }

    public void setX500DNHandler(@Nonnull X500DNHandler handler) {
        this.x500DNHandler = Constraint.isNotNull(handler, "X500DNHandler cannot be null");
    }

    @Override
    public boolean handles(@Nonnull XMLObject keyInfoChild) {
        return keyInfoChild instanceof X509Data;
    }

    @Override
    @Nullable
    public Collection<Credential> process(@Nonnull KeyInfoCredentialResolver resolver2, @Nonnull XMLObject keyInfoChild, @Nullable CriteriaSet criteriaSet, @Nonnull KeyInfoResolutionContext kiContext) throws SecurityException {
        X509Certificate entityCert;
        if (!this.handles(keyInfoChild)) {
            return null;
        }
        X509Data x509Data = (X509Data)keyInfoChild;
        this.log.debug("Attempting to extract credential from an X509Data");
        List<X509Certificate> certs = this.extractCertificates(x509Data);
        if (certs.isEmpty()) {
            this.log.info("The X509Data contained no X509Certificate elements, skipping credential extraction");
            return null;
        }
        List<X509CRL> crls = this.extractCRLs(x509Data);
        PublicKey resolvedPublicKey = null;
        if (kiContext.getKey() != null && kiContext.getKey() instanceof PublicKey) {
            resolvedPublicKey = (PublicKey)kiContext.getKey();
        }
        if ((entityCert = this.findEntityCert(certs, x509Data, resolvedPublicKey)) == null) {
            this.log.warn("The end-entity cert could not be identified, skipping credential extraction");
            return null;
        }
        BasicX509Credential cred = new BasicX509Credential(entityCert);
        cred.setCRLs(crls);
        cred.setEntityCertificateChain(certs);
        cred.getKeyNames().addAll(kiContext.getKeyNames());
        KeyInfoCredentialContext credContext = this.buildCredentialContext(kiContext);
        if (credContext != null) {
            cred.getCredentialContextSet().add(credContext);
        }
        LazySet<Credential> credentialSet = new LazySet<Credential>();
        credentialSet.add(cred);
        return credentialSet;
    }

    @Nonnull
    private List<X509CRL> extractCRLs(@Nonnull X509Data x509Data) throws SecurityException {
        List<X509CRL> crls = null;
        try {
            crls = KeyInfoSupport.getCRLs(x509Data);
        }
        catch (CRLException e) {
            this.log.error("Error extracting CRLs from X509Data", e);
            throw new SecurityException("Error extracting CRLs from X509Data", e);
        }
        this.log.debug("Found {} X509CRLs", (Object)crls.size());
        return crls;
    }

    @Nonnull
    private List<X509Certificate> extractCertificates(@Nonnull X509Data x509Data) throws SecurityException {
        List<X509Certificate> certs = null;
        try {
            certs = KeyInfoSupport.getCertificates(x509Data);
        }
        catch (CertificateException e) {
            this.log.error("Error extracting certificates from X509Data", e);
            throw new SecurityException("Error extracting certificates from X509Data", e);
        }
        this.log.debug("Found {} X509Certificates", (Object)certs.size());
        return certs;
    }

    @Nullable
    protected X509Certificate findEntityCert(@Nullable List<X509Certificate> certs, @Nonnull X509Data x509Data, @Nullable PublicKey resolvedKey) {
        if (certs == null || certs.isEmpty()) {
            return null;
        }
        if (certs.size() == 1) {
            this.log.debug("Single certificate was present, treating as end-entity certificate");
            return certs.get(0);
        }
        X509Certificate cert = null;
        cert = this.findCertFromKey(certs, resolvedKey);
        if (cert != null) {
            this.log.debug("End-entity certificate resolved by matching previously resolved public key");
            return cert;
        }
        cert = this.findCertFromSubjectNames(certs, x509Data.getX509SubjectNames());
        if (cert != null) {
            this.log.debug("End-entity certificate resolved by matching X509SubjectName");
            return cert;
        }
        cert = this.findCertFromIssuerSerials(certs, x509Data.getX509IssuerSerials());
        if (cert != null) {
            this.log.debug("End-entity certificate resolved by matching X509IssuerSerial");
            return cert;
        }
        cert = this.findCertFromSubjectKeyIdentifier(certs, x509Data.getX509SKIs());
        if (cert != null) {
            this.log.debug("End-entity certificate resolved by matching X509SKI");
            return cert;
        }
        cert = this.findCertFromDigest(certs, x509Data.getX509Digests());
        if (cert != null) {
            this.log.debug("End-entity certificate resolved by matching X509Digest");
            return cert;
        }
        this.log.debug("Treating the first certificate in the X509Data as the end-entity certificate");
        return certs.get(0);
    }

    @Nullable
    protected X509Certificate findCertFromKey(@Nonnull List<X509Certificate> certs, @Nullable PublicKey key) {
        if (key != null) {
            for (X509Certificate cert : certs) {
                if (!cert.getPublicKey().equals(key)) continue;
                return cert;
            }
        }
        return null;
    }

    @Nullable
    protected X509Certificate findCertFromSubjectNames(@Nonnull List<X509Certificate> certs, @Nonnull List<X509SubjectName> names) {
        for (X509SubjectName subjectName : names) {
            if (Strings.isNullOrEmpty(subjectName.getValue())) continue;
            X500Principal subjectX500Principal = null;
            try {
                subjectX500Principal = this.x500DNHandler.parse(subjectName.getValue());
            }
            catch (IllegalArgumentException e) {
                this.log.warn("X500 subject name '{}' could not be parsed by configured X500DNHandler '{}'", (Object)subjectName.getValue(), (Object)this.x500DNHandler.getClass().getName());
                return null;
            }
            for (X509Certificate cert : certs) {
                if (!cert.getSubjectX500Principal().equals(subjectX500Principal)) continue;
                return cert;
            }
        }
        return null;
    }

    @Nullable
    protected X509Certificate findCertFromIssuerSerials(@Nonnull List<X509Certificate> certs, @Nonnull List<X509IssuerSerial> serials) {
        for (X509IssuerSerial issuerSerial : serials) {
            if (issuerSerial.getX509IssuerName() == null || issuerSerial.getX509SerialNumber() == null) continue;
            String issuerNameValue = issuerSerial.getX509IssuerName().getValue();
            BigInteger serialNumber = issuerSerial.getX509SerialNumber().getValue();
            if (Strings.isNullOrEmpty(issuerNameValue)) continue;
            X500Principal issuerX500Principal = null;
            try {
                issuerX500Principal = this.x500DNHandler.parse(issuerNameValue);
            }
            catch (IllegalArgumentException e) {
                this.log.warn("X500 issuer name '{}' could not be parsed by configured X500DNHandler '{}'", (Object)issuerNameValue, (Object)this.x500DNHandler.getClass().getName());
                return null;
            }
            for (X509Certificate cert : certs) {
                if (!cert.getIssuerX500Principal().equals(issuerX500Principal) || !cert.getSerialNumber().equals(serialNumber)) continue;
                return cert;
            }
        }
        return null;
    }

    @Nullable
    protected X509Certificate findCertFromSubjectKeyIdentifier(@Nonnull List<X509Certificate> certs, @Nonnull List<X509SKI> skis) {
        for (X509SKI ski : skis) {
            if (Strings.isNullOrEmpty(ski.getValue())) continue;
            byte[] xmlValue = Base64Support.decode(ski.getValue());
            for (X509Certificate cert : certs) {
                byte[] certValue = X509Support.getSubjectKeyIdentifier(cert);
                if (certValue == null || !Arrays.equals(xmlValue, certValue)) continue;
                return cert;
            }
        }
        return null;
    }

    @Nullable
    protected X509Certificate findCertFromDigest(@Nonnull List<X509Certificate> certs, @Nonnull List<X509Digest> digests) {
        for (X509Digest digest : digests) {
            if (Strings.isNullOrEmpty(digest.getValue()) || Strings.isNullOrEmpty(digest.getAlgorithm())) continue;
            String alg = AlgorithmSupport.getAlgorithmID(digest.getAlgorithm());
            if (alg == null) {
                this.log.warn("Algorithm {} not supported", (Object)digest.getAlgorithm());
                continue;
            }
            byte[] xmlValue = Base64Support.decode(digest.getValue());
            for (X509Certificate cert : certs) {
                try {
                    byte[] certValue = X509Support.getX509Digest(cert, alg);
                    if (certValue == null || !Arrays.equals(xmlValue, certValue)) continue;
                    return cert;
                }
                catch (SecurityException securityException) {
                }
            }
        }
        return null;
    }
}

