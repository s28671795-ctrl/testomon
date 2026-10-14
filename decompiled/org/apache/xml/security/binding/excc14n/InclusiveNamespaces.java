/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.binding.excc14n;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="InclusiveNamespaces", namespace="http://www.w3.org/2001/10/xml-exc-c14n#")
public class InclusiveNamespaces {
    @XmlAttribute(name="PrefixList")
    protected List<String> prefixList;

    public List<String> getPrefixList() {
        if (this.prefixList == null) {
            this.prefixList = new ArrayList<String>();
        }
        return this.prefixList;
    }
}

