/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.PTZVector;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PTZPreset", propOrder={"name", "ptzPosition"})
public class PTZPreset {
    @XmlElement(name="Name")
    protected String name;
    @XmlElement(name="PTZPosition")
    protected PTZVector ptzPosition;
    @XmlAttribute(name="token")
    protected String token;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public PTZVector getPTZPosition() {
        return this.ptzPosition;
    }

    public void setPTZPosition(PTZVector value) {
        this.ptzPosition = value;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String value) {
        this.token = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

