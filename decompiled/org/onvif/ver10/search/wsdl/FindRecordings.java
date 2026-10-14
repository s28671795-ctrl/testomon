/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.search.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.SearchScope;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"scope", "maxMatches", "keepAliveTime"})
@XmlRootElement(name="FindRecordings")
public class FindRecordings {
    @XmlElement(name="Scope", required=true)
    protected SearchScope scope;
    @XmlElement(name="MaxMatches")
    protected Integer maxMatches;
    @XmlElement(name="KeepAliveTime", required=true)
    protected Duration keepAliveTime;

    public SearchScope getScope() {
        return this.scope;
    }

    public void setScope(SearchScope value) {
        this.scope = value;
    }

    public Integer getMaxMatches() {
        return this.maxMatches;
    }

    public void setMaxMatches(Integer value) {
        this.maxMatches = value;
    }

    public Duration getKeepAliveTime() {
        return this.keepAliveTime;
    }

    public void setKeepAliveTime(Duration value) {
        this.keepAliveTime = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

