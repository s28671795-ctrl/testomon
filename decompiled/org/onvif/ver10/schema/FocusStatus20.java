/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
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
import org.onvif.ver10.schema.FocusStatus20Extension;
import org.onvif.ver10.schema.MoveStatus;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FocusStatus20", propOrder={"position", "moveStatus", "error", "extension"})
public class FocusStatus20 {
    @XmlElement(name="Position")
    protected float position;
    @XmlElement(name="MoveStatus", required=true)
    @XmlSchemaType(name="string")
    protected MoveStatus moveStatus;
    @XmlElement(name="Error")
    protected String error;
    @XmlElement(name="Extension")
    protected FocusStatus20Extension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public float getPosition() {
        return this.position;
    }

    public void setPosition(float value) {
        this.position = value;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }

    public void setMoveStatus(MoveStatus value) {
        this.moveStatus = value;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String value) {
        this.error = value;
    }

    public FocusStatus20Extension getExtension() {
        return this.extension;
    }

    public void setExtension(FocusStatus20Extension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

