/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.CapabilityCategory;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"category"})
@XmlRootElement(name="GetCapabilities")
public class GetCapabilities {
    @XmlElement(name="Category")
    @XmlSchemaType(name="string")
    protected List<CapabilityCategory> category;

    public List<CapabilityCategory> getCategory() {
        if (this.category == null) {
            this.category = new ArrayList<CapabilityCategory>();
        }
        return this.category;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

