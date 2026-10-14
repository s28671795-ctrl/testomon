/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ClassDescriptorExtension2;
import org.onvif.ver10.schema.OtherType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ClassDescriptorExtension", propOrder={"any", "otherTypes", "extension"})
public class ClassDescriptorExtension {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="OtherTypes", required=true)
    protected List<OtherType> otherTypes;
    @XmlElement(name="Extension")
    protected ClassDescriptorExtension2 extension;

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public List<OtherType> getOtherTypes() {
        if (this.otherTypes == null) {
            this.otherTypes = new ArrayList<OtherType>();
        }
        return this.otherTypes;
    }

    public ClassDescriptorExtension2 getExtension() {
        return this.extension;
    }

    public void setExtension(ClassDescriptorExtension2 value) {
        this.extension = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

