/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.configuration.security.KeyStoreType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="KeyManagersType", namespace="http://cxf.apache.org/configuration/security", propOrder={"keyStore"})
public class KeyManagersType {
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected KeyStoreType keyStore;
    @XmlAttribute(name="keyPassword")
    protected String keyPassword;
    @XmlAttribute(name="keyPasswordCallbackHandler")
    protected String keyPasswordCallbackHandler;
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

    public String getKeyPassword() {
        return this.keyPassword;
    }

    public void setKeyPassword(String value) {
        this.keyPassword = value;
    }

    public boolean isSetKeyPassword() {
        return this.keyPassword != null;
    }

    public String getKeyPasswordCallbackHandler() {
        return this.keyPasswordCallbackHandler;
    }

    public void setKeyPasswordCallbackHandler(String value) {
        this.keyPasswordCallbackHandler = value;
    }

    public boolean isSetKeyPasswordCallbackHandler() {
        return this.keyPasswordCallbackHandler != null;
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

