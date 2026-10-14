/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.doorcontrol.wsdl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.doorcontrol.wsdl.DoorCapabilities;
import org.onvif.ver10.doorcontrol.wsdl.DoorInfoBase;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DoorInfo", propOrder={"capabilities", "any"})
public class DoorInfo
extends DoorInfoBase {
    @XmlElement(name="Capabilities", required=true)
    protected DoorCapabilities capabilities;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public DoorCapabilities getCapabilities() {
        return this.capabilities;
    }

    public void setCapabilities(DoorCapabilities value) {
        this.capabilities = value;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

