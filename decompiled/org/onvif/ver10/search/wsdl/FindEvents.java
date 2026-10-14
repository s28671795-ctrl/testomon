/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.search.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.EventFilter;
import org.onvif.ver10.schema.SearchScope;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"startPoint", "endPoint", "scope", "searchFilter", "includeStartState", "maxMatches", "keepAliveTime"})
@XmlRootElement(name="FindEvents")
public class FindEvents {
    @XmlElement(name="StartPoint", required=true)
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar startPoint;
    @XmlElement(name="EndPoint")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar endPoint;
    @XmlElement(name="Scope", required=true)
    protected SearchScope scope;
    @XmlElement(name="SearchFilter", required=true)
    protected EventFilter searchFilter;
    @XmlElement(name="IncludeStartState")
    protected boolean includeStartState;
    @XmlElement(name="MaxMatches")
    protected Integer maxMatches;
    @XmlElement(name="KeepAliveTime", required=true)
    protected Duration keepAliveTime;

    public XMLGregorianCalendar getStartPoint() {
        return this.startPoint;
    }

    public void setStartPoint(XMLGregorianCalendar value) {
        this.startPoint = value;
    }

    public XMLGregorianCalendar getEndPoint() {
        return this.endPoint;
    }

    public void setEndPoint(XMLGregorianCalendar value) {
        this.endPoint = value;
    }

    public SearchScope getScope() {
        return this.scope;
    }

    public void setScope(SearchScope value) {
        this.scope = value;
    }

    public EventFilter getSearchFilter() {
        return this.searchFilter;
    }

    public void setSearchFilter(EventFilter value) {
        this.searchFilter = value;
    }

    public boolean getIncludeStartState() {
        return this.includeStartState;
    }

    public void setIncludeStartState(boolean value) {
        this.includeStartState = value;
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

