/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ClientAuthentication", namespace="http://cxf.apache.org/configuration/security")
public class ClientAuthentication {
    @XmlAttribute(name="want")
    protected Boolean want;
    @XmlAttribute(name="required")
    protected Boolean required;

    public Boolean isWant() {
        return this.want;
    }

    public void setWant(Boolean value) {
        this.want = value;
    }

    public boolean isSetWant() {
        return this.want != null;
    }

    public Boolean isRequired() {
        return this.required;
    }

    public void setRequired(Boolean value) {
        this.required = value;
    }

    public boolean isSetRequired() {
        return this.required != null;
    }
}

