/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.bindings.xformat;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="XMLBindingMessageFormat")
public class XMLBindingMessageFormat {
    @XmlAttribute(name="rootNode")
    protected QName rootNode;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public QName getRootNode() {
        return this.rootNode;
    }

    public void setRootNode(QName value) {
        this.rootNode = value;
    }

    public boolean isSetRootNode() {
        return this.rootNode != null;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}

