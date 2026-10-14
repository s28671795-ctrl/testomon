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
@XmlType(name="IncludeProtocols", namespace="http://cxf.apache.org/configuration/security", propOrder={"includeProtocol"})
public class IncludeProtocols {
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected List<String> includeProtocol;

    public List<String> getIncludeProtocol() {
        if (this.includeProtocol == null) {
            this.includeProtocol = new ArrayList<String>();
        }
        return this.includeProtocol;
    }

    public boolean isSetIncludeProtocol() {
        return this.includeProtocol != null && !this.includeProtocol.isEmpty();
    }

    public void unsetIncludeProtocol() {
        this.includeProtocol = null;
    }
}

