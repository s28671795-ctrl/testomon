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
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.MoveStatus;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FocusStatus", propOrder={"position", "moveStatus", "error", "any"})
public class FocusStatus {
    @XmlElement(name="Position")
    protected float position;
    @XmlElement(name="MoveStatus", required=true)
    @XmlSchemaType(name="string")
    protected MoveStatus moveStatus;
    @XmlElement(name="Error", required=true)
    protected String error;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
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

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

