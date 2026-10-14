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
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZMoveStatus;
import org.onvif.ver10.schema.PTZVector;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZStatus", propOrder={"position", "moveStatus", "error", "utcTime", "any"})
public class PTZStatus {
    @XmlElement(name="Position")
    protected PTZVector position;
    @XmlElement(name="MoveStatus")
    protected PTZMoveStatus moveStatus;
    @XmlElement(name="Error")
    protected String error;
    @XmlElement(name="UtcTime", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar utcTime;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public PTZVector getPosition() {
        return this.position;
    }

    public void setPosition(PTZVector value) {
        this.position = value;
    }

    public PTZMoveStatus getMoveStatus() {
        return this.moveStatus;
    }

    public void setMoveStatus(PTZMoveStatus value) {
        this.moveStatus = value;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String value) {
        this.error = value;
    }

    public XMLGregorianCalendar getUtcTime() {
        return this.utcTime;
    }

    public void setUtcTime(XMLGregorianCalendar value) {
        this.utcTime = value;
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

