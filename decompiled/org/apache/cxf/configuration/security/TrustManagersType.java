/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.configuration.security.CertStoreType;
import org.apache.cxf.configuration.security.KeyStoreType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TrustManagersType", namespace="http://cxf.apache.org/configuration/security", propOrder={"keyStore", "certStore"})
public class TrustManagersType {
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected KeyStoreType keyStore;
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected CertStoreType certStore;
    @XmlAttribute(name="provider")
    protected String provider;
    @XmlAttribute(name="factoryAlgorithm")
    protected String factoryAlgorithm;
    @XmlAttribute(name="ref")
    protected String ref;

    public KeyStoreType getKeyStore() {
        return this.keyStore;
    }

    public void setKeyStore(KeyStoreType value) {
        this.keyStore = value;
    }

    public boolean isSetKeyStore() {
        return this.keyStore != null;
    }

    public CertStoreType getCertStore() {
        return this.certStore;
    }

    public void setCertStore(CertStoreType value) {
        this.certStore = value;
    }

    public boolean isSetCertStore() {
        return this.certStore != null;
    }

    public String getProvider() {
        return this.provider;
    }

    public void setProvider(String value) {
        this.provider = value;
    }

    public boolean isSetProvider() {
        return this.provider != null;
    }

    public String getFactoryAlgorithm() {
        return this.factoryAlgorithm;
    }

    public void setFactoryAlgorithm(String value) {
        this.factoryAlgorithm = value;
    }

    public boolean isSetFactoryAlgorithm() {
        return this.factoryAlgorithm != null;
    }

    public String getRef() {
        return this.ref;
    }

    public void setRef(String value) {
        this.ref = value;
    }

    public boolean isSetRef() {
        return this.ref != null;
    }
}

