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
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ServiceCapabilities", propOrder={"any"})
public class ServiceCapabilities {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="MaxLimit", required=true)
    protected long maxLimit;
    @XmlAttribute(name="MaxAccessProfiles", required=true)
    protected long maxAccessProfiles;
    @XmlAttribute(name="MaxAccessPoliciesPerAccessProfile", required=true)
    protected long maxAccessPoliciesPerAccessProfile;
    @XmlAttribute(name="MultipleSchedulesPerAccessPointSupported", required=true)
    protected boolean multipleSchedulesPerAccessPointSupported;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public long getMaxLimit() {
        return this.maxLimit;
    }

    public void setMaxLimit(long value) {
        this.maxLimit = value;
    }

    public long getMaxAccessProfiles() {
        return this.maxAccessProfiles;
    }

    public void setMaxAccessProfiles(long value) {
        this.maxAccessProfiles = value;
    }

    public long getMaxAccessPoliciesPerAccessProfile() {
        return this.maxAccessPoliciesPerAccessProfile;
    }

    public void setMaxAccessPoliciesPerAccessProfile(long value) {
        this.maxAccessPoliciesPerAccessProfile = value;
    }

    public boolean getMultipleSchedulesPerAccessPointSupported() {
        return this.multipleSchedulesPerAccessPointSupported;
    }

    public void setMultipleSchedulesPerAccessPointSupported(boolean value) {
        this.multipleSchedulesPerAccessPointSupported = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

