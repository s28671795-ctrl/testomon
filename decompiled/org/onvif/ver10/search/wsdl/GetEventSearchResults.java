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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"searchToken", "minResults", "maxResults", "waitTime"})
@XmlRootElement(name="GetEventSearchResults")
public class GetEventSearchResults {
    @XmlElement(name="SearchToken", required=true)
    protected String searchToken;
    @XmlElement(name="MinResults")
    protected Integer minResults;
    @XmlElement(name="MaxResults")
    protected Integer maxResults;
    @XmlElement(name="WaitTime")
    protected Duration waitTime;

    public String getSearchToken() {
        return this.searchToken;
    }

    public void setSearchToken(String value) {
        this.searchToken = value;
    }

    public Integer getMinResults() {
        return this.minResults;
    }

    public void setMinResults(Integer value) {
        this.minResults = value;
    }

    public Integer getMaxResults() {
        return this.maxResults;
    }

    public void setMaxResults(Integer value) {
        this.maxResults = value;
    }

    public Duration getWaitTime() {
        return this.waitTime;
    }

    public void setWaitTime(Duration value) {
        this.waitTime = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

