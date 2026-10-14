/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.FactoryDefaultType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"factoryDefault"})
@XmlRootElement(name="SetSystemFactoryDefault")
public class SetSystemFactoryDefault {
    @XmlElement(name="FactoryDefault", required=true)
    @XmlSchemaType(name="string")
    protected FactoryDefaultType factoryDefault;

    public FactoryDefaultType getFactoryDefault() {
        return this.factoryDefault;
    }

    public void setFactoryDefault(FactoryDefaultType value) {
        this.factoryDefault = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

