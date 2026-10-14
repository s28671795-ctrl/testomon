/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.advancedsecurity.wsdl.AlgorithmIdentifier;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="KeystoreCapabilities", propOrder={"signatureAlgorithms", "anyElement"})
public class KeystoreCapabilities {
    @XmlElement(name="SignatureAlgorithms")
    protected List<AlgorithmIdentifier> signatureAlgorithms;
    protected AnyElement anyElement;
    @XmlAttribute(name="MaximumNumberOfKeys")
    @XmlSchemaType(name="positiveInteger")
    protected BigInteger maximumNumberOfKeys;
    @XmlAttribute(name="MaximumNumberOfCertificates")
    @XmlSchemaType(name="positiveInteger")
    protected BigInteger maximumNumberOfCertificates;
    @XmlAttribute(name="MaximumNumberOfCertificationPaths")
    @XmlSchemaType(name="positiveInteger")
    protected BigInteger maximumNumberOfCertificationPaths;
    @XmlAttribute(name="RSAKeyPairGeneration")
    protected Boolean rsaKeyPairGeneration;
    @XmlAttribute(name="RSAKeyLengths")
    protected List<BigInteger> rsaKeyLengths;
    @XmlAttribute(name="PKCS10ExternalCertificationWithRSA")
    protected Boolean pkcs10ExternalCertificationWithRSA;
    @XmlAttribute(name="SelfSignedCertificateCreationWithRSA")
    protected Boolean selfSignedCertificateCreationWithRSA;
    @XmlAttribute(name="X509Versions")
    protected List<Integer> x509Versions;
    @XmlAttribute(name="MaximumNumberOfPassphrases")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger maximumNumberOfPassphrases;
    @XmlAttribute(name="PKCS8RSAKeyPairUpload")
    protected Boolean pkcs8RSAKeyPairUpload;
    @XmlAttribute(name="PKCS12CertificateWithRSAPrivateKeyUpload")
    protected Boolean pkcs12CertificateWithRSAPrivateKeyUpload;
    @XmlAttribute(name="PasswordBasedEncryptionAlgorithms")
    protected List<String> passwordBasedEncryptionAlgorithms;
    @XmlAttribute(name="PasswordBasedMACAlgorithms")
    protected List<String> passwordBasedMACAlgorithms;
    @XmlAttribute(name="MaximumNumberOfCRLs")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger maximumNumberOfCRLs;
    @XmlAttribute(name="MaximumNumberOfCertificationPathValidationPolicies")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger maximumNumberOfCertificationPathValidationPolicies;
    @XmlAttribute(name="EnforceTLSWebClientAuthExtKeyUsage")
    protected Boolean enforceTLSWebClientAuthExtKeyUsage;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<AlgorithmIdentifier> getSignatureAlgorithms() {
        if (this.signatureAlgorithms == null) {
            this.signatureAlgorithms = new ArrayList<AlgorithmIdentifier>();
        }
        return this.signatureAlgorithms;
    }

    public AnyElement getAnyElement() {
        return this.anyElement;
    }

    public void setAnyElement(AnyElement value) {
        this.anyElement = value;
    }

    public BigInteger getMaximumNumberOfKeys() {
        return this.maximumNumberOfKeys;
    }

    public void setMaximumNumberOfKeys(BigInteger value) {
        this.maximumNumberOfKeys = value;
    }

    public BigInteger getMaximumNumberOfCertificates() {
        return this.maximumNumberOfCertificates;
    }

    public void setMaximumNumberOfCertificates(BigInteger value) {
        this.maximumNumberOfCertificates = value;
    }

    public BigInteger getMaximumNumberOfCertificationPaths() {
        return this.maximumNumberOfCertificationPaths;
    }

    public void setMaximumNumberOfCertificationPaths(BigInteger value) {
        this.maximumNumberOfCertificationPaths = value;
    }

    public Boolean getRSAKeyPairGeneration() {
        return this.rsaKeyPairGeneration;
    }

    public void setRSAKeyPairGeneration(Boolean value) {
        this.rsaKeyPairGeneration = value;
    }

    public List<BigInteger> getRSAKeyLengths() {
        if (this.rsaKeyLengths == null) {
            this.rsaKeyLengths = new ArrayList<BigInteger>();
        }
        return this.rsaKeyLengths;
    }

    public Boolean getPKCS10ExternalCertificationWithRSA() {
        return this.pkcs10ExternalCertificationWithRSA;
    }

    public void setPKCS10ExternalCertificationWithRSA(Boolean value) {
        this.pkcs10ExternalCertificationWithRSA = value;
    }

    public Boolean getSelfSignedCertificateCreationWithRSA() {
        return this.selfSignedCertificateCreationWithRSA;
    }

    public void setSelfSignedCertificateCreationWithRSA(Boolean value) {
        this.selfSignedCertificateCreationWithRSA = value;
    }

    public List<Integer> getX509Versions() {
        if (this.x509Versions == null) {
            this.x509Versions = new ArrayList<Integer>();
        }
        return this.x509Versions;
    }

    public BigInteger getMaximumNumberOfPassphrases() {
        return this.maximumNumberOfPassphrases;
    }

    public void setMaximumNumberOfPassphrases(BigInteger value) {
        this.maximumNumberOfPassphrases = value;
    }

    public Boolean getPKCS8RSAKeyPairUpload() {
        return this.pkcs8RSAKeyPairUpload;
    }

    public void setPKCS8RSAKeyPairUpload(Boolean value) {
        this.pkcs8RSAKeyPairUpload = value;
    }

    public Boolean getPKCS12CertificateWithRSAPrivateKeyUpload() {
        return this.pkcs12CertificateWithRSAPrivateKeyUpload;
    }

    public void setPKCS12CertificateWithRSAPrivateKeyUpload(Boolean value) {
        this.pkcs12CertificateWithRSAPrivateKeyUpload = value;
    }

    public List<String> getPasswordBasedEncryptionAlgorithms() {
        if (this.passwordBasedEncryptionAlgorithms == null) {
            this.passwordBasedEncryptionAlgorithms = new ArrayList<String>();
        }
        return this.passwordBasedEncryptionAlgorithms;
    }

    public List<String> getPasswordBasedMACAlgorithms() {
        if (this.passwordBasedMACAlgorithms == null) {
            this.passwordBasedMACAlgorithms = new ArrayList<String>();
        }
        return this.passwordBasedMACAlgorithms;
    }

    public BigInteger getMaximumNumberOfCRLs() {
        return this.maximumNumberOfCRLs;
    }

    public void setMaximumNumberOfCRLs(BigInteger value) {
        this.maximumNumberOfCRLs = value;
    }

    public BigInteger getMaximumNumberOfCertificationPathValidationPolicies() {
        return this.maximumNumberOfCertificationPathValidationPolicies;
    }

    public void setMaximumNumberOfCertificationPathValidationPolicies(BigInteger value) {
        this.maximumNumberOfCertificationPathValidationPolicies = value;
    }

    public Boolean getEnforceTLSWebClientAuthExtKeyUsage() {
        return this.enforceTLSWebClientAuthExtKeyUsage;
    }

    public void setEnforceTLSWebClientAuthExtKeyUsage(Boolean value) {
        this.enforceTLSWebClientAuthExtKeyUsage = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"any"})
    public static class AnyElement {
        @XmlAnyElement(lax=true)
        protected List<Object> any;

        public List<Object> getAny() {
            if (this.any == null) {
                this.any = new ArrayList<Object>();
            }
            return this.any;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
        }
    }
}

