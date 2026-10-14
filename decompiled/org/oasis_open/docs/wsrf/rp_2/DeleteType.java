/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rp_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DeleteType")
public class DeleteType {
    @XmlAttribute(name="ResourceProperty", required=true)
    protected QName resourceProperty;

    public QName getResourceProperty() {
        return this.resourceProperty;
    }

    public void setResourceProperty(QName value) {
        this.resourceProperty = value;
    }
}

