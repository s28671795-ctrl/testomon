/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.deviceio.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.deviceio.wsdl.ParityBit;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ParityBitList", propOrder={"items"})
public class ParityBitList {
    @XmlElement(name="Items")
    @XmlSchemaType(name="string")
    protected List<ParityBit> items;

    public List<ParityBit> getItems() {
        if (this.items == null) {
            this.items = new ArrayList<ParityBit>();
        }
        return this.items;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

