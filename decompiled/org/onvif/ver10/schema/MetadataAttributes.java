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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MetadataAttributes", propOrder={"canContainPTZ", "canContainAnalytics", "canContainNotifications", "any"})
public class MetadataAttributes {
    @XmlElement(name="CanContainPTZ")
    protected boolean canContainPTZ;
    @XmlElement(name="CanContainAnalytics")
    protected boolean canContainAnalytics;
    @XmlElement(name="CanContainNotifications")
    protected boolean canContainNotifications;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="PtzSpaces")
    protected List<String> ptzSpaces;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public boolean getCanContainPTZ() {
        return this.canContainPTZ;
    }

    public void setCanContainPTZ(boolean value) {
        this.canContainPTZ = value;
    }

    public boolean getCanContainAnalytics() {
        return this.canContainAnalytics;
    }

    public void setCanContainAnalytics(boolean value) {
        this.canContainAnalytics = value;
    }

    public boolean getCanContainNotifications() {
        return this.canContainNotifications;
    }

    public void setCanContainNotifications(boolean value) {
        this.canContainNotifications = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public List<String> getPtzSpaces() {
        if (this.ptzSpaces == null) {
            this.ptzSpaces = new ArrayList<String>();
        }
        return this.ptzSpaces;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

