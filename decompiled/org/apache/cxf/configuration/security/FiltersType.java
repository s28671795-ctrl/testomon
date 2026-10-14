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
@XmlType(name="FiltersType", namespace="http://cxf.apache.org/configuration/security", propOrder={"include", "exclude"})
public class FiltersType {
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected List<String> include;
    @XmlElement(namespace="http://cxf.apache.org/configuration/security")
    protected List<String> exclude;

    public List<String> getInclude() {
        if (this.include == null) {
            this.include = new ArrayList<String>();
        }
        return this.include;
    }

    public boolean isSetInclude() {
        return this.include != null && !this.include.isEmpty();
    }

    public void unsetInclude() {
        this.include = null;
    }

    public List<String> getExclude() {
        if (this.exclude == null) {
            this.exclude = new ArrayList<String>();
        }
        return this.exclude;
    }

    public boolean isSetExclude() {
        return this.exclude != null && !this.exclude.isEmpty();
    }

    public void unsetExclude() {
        this.exclude = null;
    }
}

