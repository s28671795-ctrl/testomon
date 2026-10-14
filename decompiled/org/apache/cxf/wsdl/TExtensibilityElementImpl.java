/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl;

import javax.wsdl.extensions.ExtensibilityElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.namespace.QName;

public class TExtensibilityElementImpl
implements ExtensibilityElement {
    @XmlTransient
    QName elementType;
    private Boolean required;

    @Override
    @XmlTransient
    public QName getElementType() {
        return this.elementType;
    }

    @Override
    public void setElementType(QName type) {
        this.elementType = type;
    }

    @Override
    @XmlAttribute(name="required", namespace="http://schemas.xmlsoap.org/wsdl/")
    public Boolean getRequired() {
        return this.required;
    }

    @Override
    public void setRequired(Boolean value) {
        this.required = value;
    }
}

