/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accesscontrol.wsdl;

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
import org.onvif.ver10.accesscontrol.wsdl.AccessPointCapabilities;
import org.onvif.ver10.accesscontrol.wsdl.AccessPointInfoBase;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AccessPointInfo", propOrder={"capabilities", "any"})
public class AccessPointInfo
extends AccessPointInfoBase {
    @XmlElement(name="Capabilities", required=true)
    protected AccessPointCapabilities capabilities;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public AccessPointCapabilities getCapabilities() {
        return this.capabilities;
    }

    public void setCapabilities(AccessPointCapabilities value) {
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

