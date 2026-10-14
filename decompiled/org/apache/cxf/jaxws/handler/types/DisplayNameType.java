/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.jaxws.handler.types.CString;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="display-nameType")
public class DisplayNameType
extends CString {
    @XmlAttribute(namespace="http://www.w3.org/XML/1998/namespace")
    protected String lang;

    public String getLang() {
        return this.lang;
    }

    public void setLang(String value) {
        this.lang = value;
    }
}

