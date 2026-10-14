/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.keyinfo.impl;

import com.google.common.base.Strings;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CRLException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509CRL;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.InternalX500DNHandler;
import org.opensaml.security.x509.X500DNHandler;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.security.x509.X509Support;
import org.opensaml.xmlsec.keyinfo.KeyInfoGenerator;
import org.opensaml.xmlsec.keyinfo.KeyInfoSupport;
import org.opensaml.xmlsec.keyinfo.impl.BasicKeyInfoGeneratorFactory;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.X509Certificate;
import org.opensaml.xmlsec.signature.X509Data;
import org.opensaml.xmlsec.signature.X509SKI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class X509KeyInfoGeneratorFactory
extends BasicKeyInfoGeneratorFactory {
    private final X509Options options = (X509Options)super.getOptions();

    @Override
    @Nonnull
    public Class<? extends Credential> getCredentialType() {
        return X509Credential.class;
    }

    @Override
    public boolean handles(@Nonnull Credential credential) {
        return credential instanceof X509Credential;
    }

    @Override
    @Nonnull
    public KeyInfoGenerator newInstance() {
        X509Options newOptions = this.options.clone();
        return new X509KeyInfoGenerator(newOptions);
    }

    public boolean emitCRLs() {
        return this.options.emitCRLs;
    }

    public void setEmitCRLs(boolean newValue) {
        this.options.emitCRLs = newValue;
    }

    public boolean emitEntityCertificate() {
        return this.options.emitEntityCertificate;
    }

    public void setEmitEntityCertificate(boolean newValue) {
        this.options.emitEntityCertificate = newValue;
    }

    public boolean emitEntityCertificateChain() {
        return this.options.emitEntityCertificateChain;
    }

    public void setEmitEntityCertificateChain(boolean newValue) {
        this.options.emitEntityCertificateChain = newValue;
    }

    public boolean emitSubjectAltNamesAsKeyNames() {
        return this.options.emitSubjectAltNamesAsKeyNames;
    }

    public void setEmitSubjectAltNamesAsKeyNames(boolean newValue) {
        this.options.emitSubjectAltNamesAsKeyNames = newValue;
    }

    public boolean emitSubjectCNAsKeyName() {
        return this.options.emitSubjectCNAsKeyName;
    }

    public void setEmitSubjectCNAsKeyName(boolean newValue) {
        this.options.emitSubjectCNAsKeyName = newValue;
    }

    public boolean emitSubjectDNAsKeyName() {
        return this.options.emitSubjectDNAsKeyName;
    }

    public void setEmitSubjectDNAsKeyName(boolean newValue) {
        this.options.emitSubjectDNAsKeyName = newValue;
    }

    public boolean emitX509IssuerSerial() {
        return this.options.emitX509IssuerSerial;
    }

    public void setEmitX509IssuerSerial(boolean newValue) {
        this.options.emitX509IssuerSerial = newValue;
    }

    public boolean emitX509SKI() {
        return this.options.emitX509SKI;
    }

    public void setEmitX509SKI(boolean newValue) {
        this.options.emitX509SKI = newValue;
    }

    public boolean emitX509Digest() {
        return this.options.emitX509Digest;
    }

    public void setEmitX509Digest(boolean newValue) {
        this.options.emitX509Digest = newValue;
    }

    @Nonnull
    public String getX509DigestAlgorithmURI() {
        return this.options.x509DigestAlgorithmURI;
    }

    public void setX509DigestAlgorithmURI(@Nonnull String alg) {
        this.options.x509DigestAlgorithmURI = Constraint.isNotNull(alg, "Algorithm cannot be null");
    }

    public boolean emitX509SubjectName() {
        return this.options.emitX509SubjectName;
    }

    public void setEmitX509SubjectName(boolean newValue) {
        this.options.emitX509SubjectName = newValue;
    }

    @Nonnull
    public Set<Integer> getSubjectAltNames() {
        return this.options.subjectAltNames;
    }

    @Nonnull
    public X500DNHandler getX500DNHandler() {
        return this.options.x500DNHandler;
    }

    public void setX500DNHandler(@Nonnull X500DNHandler handler) {
        this.options.x500DNHandler = Constraint.isNotNull(handler, "X500DNHandler cannot be null");
    }

    @Nullable
    public String getX500SubjectDNFormat() {
        return this.options.x500SubjectDNFormat;
    }

    public void setX500SubjectDNFormat(@Nullable String format) {
        this.options.x500SubjectDNFormat = format;
    }

    @Nullable
    public String getX500IssuerDNFormat() {
        return this.options.x500IssuerDNFormat;
    }

    public void setX500IssuerDNFormat(@Nullable String format) {
        this.options.x500IssuerDNFormat = format;
    }

    @Override
    @Nonnull
    protected X509Options getOptions() {
        return this.options;
    }

    @Override
    @Nonnull
    protected X509Options newOptions() {
        return new X509Options();
    }

    protected class X509Options
    extends BasicKeyInfoGeneratorFactory.BasicOptions {
        private boolean emitEntityCertificate;
        private boolean emitEntityCertificateChain;
        private boolean emitCRLs;
        private boolean emitX509SubjectName;
        private boolean emitX509IssuerSerial;
        private boolean emitX509SKI;
        private boolean emitX509Digest;
        private String x509DigestAlgorithmURI = "http://www.w3.org/2001/04/xmlenc#sha256";
        private boolean emitSubjectDNAsKeyName;
        private boolean emitSubjectCNAsKeyName;
        private boolean emitSubjectAltNamesAsKeyNames;
        private Set<Integer> subjectAltNames = new LazySet<Integer>();
        private X500DNHandler x500DNHandler = new InternalX500DNHandler();
        private String x500SubjectDNFormat = "RFC2253";
        private String x500IssuerDNFormat = "RFC2253";

        protected X509Options() {
        }

        @Override
        protected X509Options clone() {
            X509Options clonedOptions = (X509Options)super.clone();
            clonedOptions.subjectAltNames = new LazySet<Integer>();
            clonedOptions.subjectAltNames.addAll(this.subjectAltNames);
            clonedOptions.x500DNHandler = this.x500DNHandler.clone();
            return clonedOptions;
        }
    }

    public class X509KeyInfoGenerator
    extends BasicKeyInfoGeneratorFactory.BasicKeyInfoGenerator {
        private final Logger log;
        private X509Options options;
        private final XMLObjectBuilder<KeyInfo> keyInfoBuilder;
        private final XMLObjectBuilder<X509Data> x509DataBuilder;

        protected X509KeyInfoGenerator(X509Options newOptions) {
            super(newOptions);
            this.log = LoggerFactory.getLogger(X509KeyInfoGenerator.class);
            this.options = newOptions;
            this.keyInfoBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(KeyInfo.DEFAULT_ELEMENT_NAME);
            this.x509DataBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(X509Data.DEFAULT_ELEMENT_NAME);
        }

        @Override
        @Nullable
        public KeyInfo generate(@Nullable Credential credential) throws SecurityException {
            List<XMLObject> keyInfoChildren;
            if (credential == null) {
                this.log.warn("X509KeyInfoGenerator was passed a null credential");
                return null;
            }
            if (!(credential instanceof X509Credential)) {
                this.log.warn("X509KeyInfoGenerator was passed a credential that was not an instance of X509Credential: {}", (Object)credential.getClass().getName());
                return null;
            }
            X509Credential x509Credential = (X509Credential)credential;
            KeyInfo keyInfo = super.generate(credential);
            if (keyInfo == null) {
                keyInfo = this.keyInfoBuilder.buildObject(KeyInfo.DEFAULT_ELEMENT_NAME);
            }
            X509Data x509Data = this.x509DataBuilder.buildObject(X509Data.DEFAULT_ELEMENT_NAME);
            this.processEntityCertificate(keyInfo, x509Data, x509Credential);
            this.processEntityCertificateChain(keyInfo, x509Data, x509Credential);
            this.processCRLs(keyInfo, x509Data, x509Credential);
            List<XMLObject> x509DataChildren = x509Data.getOrderedChildren();
            if (x509DataChildren != null && x509DataChildren.size() > 0) {
                keyInfo.getX509Datas().add(x509Data);
            }
            if ((keyInfoChildren = keyInfo.getOrderedChildren()) != null && keyInfoChildren.size() > 0) {
                return keyInfo;
            }
            return null;
        }

        protected void processEntityCertificate(@Nonnull KeyInfo keyInfo, @Nonnull X509Data x509Data, @Nonnull X509Credential credential) throws SecurityException {
            if (credential.getEntityCertificate() == null) {
                return;
            }
            java.security.cert.X509Certificate javaCert = credential.getEntityCertificate();
            this.processCertX509DataOptions(x509Data, javaCert);
            this.processCertKeyNameOptions(keyInfo, javaCert);
            if (this.options.emitEntityCertificate && !this.options.emitEntityCertificateChain) {
                try {
                    X509Certificate xmlCert = KeyInfoSupport.buildX509Certificate(javaCert);
                    x509Data.getX509Certificates().add(xmlCert);
                }
                catch (CertificateEncodingException e) {
                    throw new SecurityException("Error generating X509Certificate element from credential's end-entity certificate", e);
                }
            }
        }

        protected void processCertX509DataOptions(@Nonnull X509Data x509Data, @Nonnull java.security.cert.X509Certificate cert) throws SecurityException {
            this.processCertX509SubjectName(x509Data, cert);
            this.processCertX509IssuerSerial(x509Data, cert);
            this.processCertX509SKI(x509Data, cert);
            this.processCertX509Digest(x509Data, cert);
        }

        protected void processCertKeyNameOptions(@Nonnull KeyInfo keyInfo, @Nonnull java.security.cert.X509Certificate cert) {
            this.processSubjectDNKeyName(keyInfo, cert);
            this.processSubjectCNKeyName(keyInfo, cert);
            this.processSubjectAltNameKeyNames(keyInfo, cert);
        }

        protected void processCertX509SubjectName(@Nonnull X509Data x509Data, @Nonnull java.security.cert.X509Certificate cert) {
            String subjectNameValue;
            if (this.options.emitX509SubjectName && !Strings.isNullOrEmpty(subjectNameValue = this.getSubjectName(cert))) {
                x509Data.getX509SubjectNames().add(KeyInfoSupport.buildX509SubjectName(subjectNameValue));
            }
        }

        protected void processCertX509IssuerSerial(@Nonnull X509Data x509Data, @Nonnull java.security.cert.X509Certificate cert) {
            String issuerNameValue;
            if (this.options.emitX509IssuerSerial && !Strings.isNullOrEmpty(issuerNameValue = this.getIssuerName(cert))) {
                x509Data.getX509IssuerSerials().add(KeyInfoSupport.buildX509IssuerSerial(issuerNameValue, cert.getSerialNumber()));
            }
        }

        protected void processCertX509SKI(@Nonnull X509Data x509Data, @Nonnull java.security.cert.X509Certificate cert) {
            X509SKI xmlSKI;
            if (this.options.emitX509SKI && (xmlSKI = KeyInfoSupport.buildX509SKI(cert)) != null) {
                x509Data.getX509SKIs().add(xmlSKI);
            }
        }

        protected void processCertX509Digest(@Nonnull X509Data x509Data, @Nonnull java.security.cert.X509Certificate cert) throws SecurityException {
            if (this.options.emitX509Digest) {
                try {
                    x509Data.getX509Digests().add(KeyInfoSupport.buildX509Digest(cert, this.options.x509DigestAlgorithmURI));
                }
                catch (CertificateEncodingException e) {
                    throw new SecurityException("Can't digest certificate, certificate encoding error", e);
                }
                catch (NoSuchAlgorithmException e) {
                    throw new SecurityException("Can't digest certificate, unsupported digest algorithm", e);
                }
            }
        }

        @Nullable
        protected String getSubjectName(@Nullable java.security.cert.X509Certificate cert) {
            if (cert == null) {
                return null;
            }
            if (!Strings.isNullOrEmpty(this.options.x500SubjectDNFormat)) {
                return this.options.x500DNHandler.getName(cert.getSubjectX500Principal(), this.options.x500SubjectDNFormat);
            }
            return this.options.x500DNHandler.getName(cert.getSubjectX500Principal());
        }

        protected String getIssuerName(@Nullable java.security.cert.X509Certificate cert) {
            if (cert == null) {
                return null;
            }
            if (!Strings.isNullOrEmpty(this.options.x500IssuerDNFormat)) {
                return this.options.x500DNHandler.getName(cert.getIssuerX500Principal(), this.options.x500IssuerDNFormat);
            }
            return this.options.x500DNHandler.getName(cert.getIssuerX500Principal());
        }

        protected void processSubjectDNKeyName(@Nonnull KeyInfo keyInfo, @Nullable java.security.cert.X509Certificate cert) {
            String subjectNameValue;
            if (this.options.emitSubjectDNAsKeyName && !Strings.isNullOrEmpty(subjectNameValue = this.getSubjectName(cert))) {
                KeyInfoSupport.addKeyName(keyInfo, subjectNameValue);
            }
        }

        protected void processSubjectCNKeyName(@Nonnull KeyInfo keyInfo, @Nullable java.security.cert.X509Certificate cert) {
            List<String> cnames;
            if (this.options.emitSubjectCNAsKeyName && (cnames = X509Support.getCommonNames(cert.getSubjectX500Principal())) != null) {
                for (String name : cnames) {
                    if (Strings.isNullOrEmpty(name)) continue;
                    KeyInfoSupport.addKeyName(keyInfo, name);
                }
            }
        }

        protected void processSubjectAltNameKeyNames(@Nonnull KeyInfo keyInfo, @Nullable java.security.cert.X509Certificate cert) {
            if (this.options.emitSubjectAltNamesAsKeyNames && this.options.subjectAltNames.size() > 0) {
                Integer[] nameTypes = new Integer[this.options.subjectAltNames.size()];
                this.options.subjectAltNames.toArray(nameTypes);
                List altnames = X509Support.getAltNames(cert, nameTypes);
                if (altnames != null) {
                    for (Object altNameValue : altnames) {
                        if (altNameValue instanceof String) {
                            KeyInfoSupport.addKeyName(keyInfo, (String)altNameValue);
                            continue;
                        }
                        if (altNameValue instanceof byte[]) {
                            this.log.warn("Certificate contained an alt name value as a DER-encoded byte[] (not supported)");
                            continue;
                        }
                        this.log.warn("Certificate contained an alt name value with an unexpected type: {}", (Object)altNameValue.getClass().getName());
                    }
                }
            }
        }

        protected void processEntityCertificateChain(@Nonnull KeyInfo keyInfo, @Nonnull X509Data x509Data, @Nonnull X509Credential credential) throws SecurityException {
            if (this.options.emitEntityCertificateChain) {
                for (java.security.cert.X509Certificate javaCert : credential.getEntityCertificateChain()) {
                    try {
                        X509Certificate xmlCert = KeyInfoSupport.buildX509Certificate(javaCert);
                        x509Data.getX509Certificates().add(xmlCert);
                    }
                    catch (CertificateEncodingException e) {
                        throw new SecurityException("Error generating X509Certificate element from a certificate in credential's certificate chain", e);
                    }
                }
            }
        }

        protected void processCRLs(@Nonnull KeyInfo keyInfo, @Nonnull X509Data x509Data, @Nonnull X509Credential credential) throws SecurityException {
            if (this.options.emitCRLs && credential.getCRLs() != null) {
                for (X509CRL javaCRL : credential.getCRLs()) {
                    try {
                        org.opensaml.xmlsec.signature.X509CRL xmlCRL = KeyInfoSupport.buildX509CRL(javaCRL);
                        x509Data.getX509CRLs().add(xmlCRL);
                    }
                    catch (CRLException e) {
                        throw new SecurityException("Error generating X509CRL element from a CRL in credential's CRL list", e);
                    }
                }
            }
        }
    }
}

