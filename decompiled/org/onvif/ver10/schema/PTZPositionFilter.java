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
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZVector;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZPositionFilter", propOrder={"minPosition", "maxPosition", "enterOrExit", "any"})
public class PTZPositionFilter {
    @XmlElement(name="MinPosition", required=true)
    protected PTZVector minPosition;
    @XmlElement(name="MaxPosition", required=true)
    protected PTZVector maxPosition;
    @XmlElement(name="EnterOrExit")
    protected boolean enterOrExit;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public PTZVector getMinPosition() {
        return this.minPosition;
    }

    public void setMinPosition(PTZVector value) {
        this.minPosition = value;
    }

    public PTZVector getMaxPosition() {
        return this.maxPosition;
    }

    public void setMaxPosition(PTZVector value) {
        this.maxPosition = value;
    }

    public boolean getEnterOrExit() {
        return this.enterOrExit;
    }

    public void setEnterOrExit(boolean value) {
        this.enterOrExit = value;
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

