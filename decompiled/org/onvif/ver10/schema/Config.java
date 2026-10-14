/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ItemList;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Config", propOrder={"parameters"})
public class Config {
    @XmlElement(name="Parameters", required=true)
    protected ItemList parameters;
    @XmlAttribute(name="Name", required=true)
    protected String name;
    @XmlAttribute(name="Type", required=true)
    protected QName type;

    public ItemList getParameters() {
        return this.parameters;
    }

    public void setParameters(ItemList value) {
        this.parameters = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public QName getType() {
        return this.type;
    }

    public void setType(QName value) {
        this.type = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

