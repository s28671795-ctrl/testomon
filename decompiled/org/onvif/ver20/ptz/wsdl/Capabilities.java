/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.ptz.wsdl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Capabilities", propOrder={"any"})
public class Capabilities {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="EFlip")
    protected Boolean eFlip;
    @XmlAttribute(name="Reverse")
    protected Boolean reverse;
    @XmlAttribute(name="GetCompatibleConfigurations")
    protected Boolean getCompatibleConfigurations;
    @XmlAttribute(name="MoveStatus")
    protected Boolean moveStatus;
    @XmlAttribute(name="StatusPosition")
    protected Boolean statusPosition;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Boolean getEFlip() {
        return this.eFlip;
    }

    public void setEFlip(Boolean value) {
        this.eFlip = value;
    }

    public Boolean getReverse() {
        return this.reverse;
    }

    public void setReverse(Boolean value) {
        this.reverse = value;
    }

    public Boolean getGetCompatibleConfigurations() {
        return this.getCompatibleConfigurations;
    }

    public void setGetCompatibleConfigurations(Boolean value) {
        this.getCompatibleConfigurations = value;
    }

    public Boolean getMoveStatus() {
        return this.moveStatus;
    }

    public void setMoveStatus(Boolean value) {
        this.moveStatus = value;
    }

    public Boolean getStatusPosition() {
        return this.statusPosition;
    }

    public void setStatusPosition(Boolean value) {
        this.statusPosition = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

