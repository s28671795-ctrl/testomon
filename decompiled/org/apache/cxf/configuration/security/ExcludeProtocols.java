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
@XmlType(name="ExcludeProtocols", namespace="http://cxf.apache.org/configuration/security", propOrder={"excludeProtocol"})
public class ExcludeProtocols {
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected List<String> excludeProtocol;

    public List<String> getExcludeProtocol() {
        if (this.excludeProtocol == null) {
            this.excludeProtocol = new ArrayList<String>();
        }
        return this.excludeProtocol;
    }

    public boolean isSetExcludeProtocol() {
        return this.excludeProtocol != null && !this.excludeProtocol.isEmpty();
    }

    public void unsetExcludeProtocol() {
        this.excludeProtocol = null;
    }
}

