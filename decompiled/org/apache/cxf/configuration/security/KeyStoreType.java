/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="KeyStoreType", namespace="http://cxf.apache.org/configuration/security")
public class KeyStoreType {
    @XmlAttribute(name="type")
    protected String type;
    @XmlAttribute(name="password")
    protected String password;
    @XmlAttribute(name="provider")
    protected String provider;
    @XmlAttribute(name="url")
    protected String url;
    @XmlAttribute(name="file")
    protected String file;
    @XmlAttribute(name="resource")
    protected String resource;

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public boolean isSetType() {
        return this.type != null;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public boolean isSetPassword() {
        return this.password != null;
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

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String value) {
        this.url = value;
    }

    public boolean isSetUrl() {
        return this.url != null;
    }

    public String getFile() {
        return this.file;
    }

    public void setFile(String value) {
        this.file = value;
    }

    public boolean isSetFile() {
        return this.file != null;
    }

    public String getResource() {
        return this.resource;
    }

    public void setResource(String value) {
        this.resource = value;
    }

    public boolean isSetResource() {
        return this.resource != null;
    }
}

