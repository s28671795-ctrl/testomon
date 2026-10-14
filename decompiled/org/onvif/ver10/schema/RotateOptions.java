/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.IntList;
import org.onvif.ver10.schema.RotateMode;
import org.onvif.ver10.schema.RotateOptionsExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RotateOptions", propOrder={"mode", "degreeList", "extension"})
public class RotateOptions {
    @XmlElement(name="Mode", required=true)
    @XmlSchemaType(name="string")
    protected List<RotateMode> mode;
    @XmlElement(name="DegreeList")
    protected IntList degreeList;
    @XmlElement(name="Extension")
    protected RotateOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<RotateMode> getMode() {
        if (this.mode == null) {
            this.mode = new ArrayList<RotateMode>();
        }
        return this.mode;
    }

    public IntList getDegreeList() {
        return this.degreeList;
    }

    public void setDegreeList(IntList value) {
        this.degreeList = value;
    }

    public RotateOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(RotateOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

