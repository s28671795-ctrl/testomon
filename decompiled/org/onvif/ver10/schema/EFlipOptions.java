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
import org.onvif.ver10.schema.EFlipMode;
import org.onvif.ver10.schema.EFlipOptionsExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="EFlipOptions", propOrder={"mode", "extension"})
public class EFlipOptions {
    @XmlElement(name="Mode")
    @XmlSchemaType(name="string")
    protected List<EFlipMode> mode;
    @XmlElement(name="Extension")
    protected EFlipOptionsExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<EFlipMode> getMode() {
        if (this.mode == null) {
            this.mode = new ArrayList<EFlipMode>();
        }
        return this.mode;
    }

    public EFlipOptionsExtension getExtension() {
        return this.extension;
    }

    public void setExtension(EFlipOptionsExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

