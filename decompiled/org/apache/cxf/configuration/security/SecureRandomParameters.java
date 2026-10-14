/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SecureRandomParameters", namespace="http://cxf.apache.org/configuration/security")
public class SecureRandomParameters {
    @XmlAttribute(name="algorithm")
    @XmlSchemaType(name="anySimpleType")
    protected String algorithm;
    @XmlAttribute(name="provider")
    @XmlSchemaType(name="anySimpleType")
    protected String provider;

    public String getAlgorithm() {
        return this.algorithm;
    }

    public void setAlgorithm(String value) {
        this.algorithm = value;
    }

    public boolean isSetAlgorithm() {
        return this.algorithm != null;
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
}

