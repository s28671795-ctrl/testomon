/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.search.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.FindEventResultList;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"resultList"})
@XmlRootElement(name="GetEventSearchResultsResponse")
public class GetEventSearchResultsResponse {
    @XmlElement(name="ResultList", required=true)
    protected FindEventResultList resultList;

    public FindEventResultList getResultList() {
        return this.resultList;
    }

    public void setResultList(FindEventResultList value) {
        this.resultList = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

