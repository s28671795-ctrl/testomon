/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.security;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CipherSuites", namespace="http://cxf.apache.org/configuration/security", propOrder={"cipherSuite"})
public class CipherSuites {
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected List<String> cipherSuite;

    public List<String> getCipherSuite() {
        if (this.cipherSuite == null) {
            this.cipherSuite = new ArrayList<String>();
        }
        return this.cipherSuite;
    }

    public boolean isSetCipherSuite() {
        return this.cipherSuite != null && !this.cipherSuite.isEmpty();
    }

    public void unsetCipherSuite() {
        this.cipherSuite = null;
    }
}

