/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accessrules.wsdl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.accessrules.wsdl.AccessPolicy;
import org.onvif.ver10.accessrules.wsdl.AccessProfileExtension;
import org.onvif.ver10.accessrules.wsdl.AccessProfileInfo;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AccessProfile", propOrder={"accessPolicy", "extension"})
public class AccessProfile
extends AccessProfileInfo {
    @XmlElement(name="AccessPolicy")
    protected List<AccessPolicy> accessPolicy;
    @XmlElement(name="Extension")
    protected AccessProfileExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<AccessPolicy> getAccessPolicy() {
        if (this.accessPolicy == null) {
            this.accessPolicy = new ArrayList<AccessPolicy>();
        }
        return this.accessPolicy;
    }

    public AccessProfileExtension getExtension() {
        return this.extension;
    }

    public void setExtension(AccessProfileExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

