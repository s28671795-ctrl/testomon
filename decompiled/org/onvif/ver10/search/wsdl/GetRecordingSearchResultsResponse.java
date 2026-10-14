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
import org.onvif.ver10.schema.FindRecordingResultList;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"resultList"})
@XmlRootElement(name="GetRecordingSearchResultsResponse")
public class GetRecordingSearchResultsResponse {
    @XmlElement(name="ResultList", required=true)
    protected FindRecordingResultList resultList;

    public FindRecordingResultList getResultList() {
        return this.resultList;
    }

    public void setResultList(FindRecordingResultList value) {
        this.resultList = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

