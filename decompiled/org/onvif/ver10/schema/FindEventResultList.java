/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.FindEventResult;
import org.onvif.ver10.schema.SearchState;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="FindEventResultList", propOrder={"searchState", "result"})
public class FindEventResultList {
    @XmlElement(name="SearchState", required=true)
    @XmlSchemaType(name="string")
    protected SearchState searchState;
    @XmlElement(name="Result")
    protected List<FindEventResult> result;

    public SearchState getSearchState() {
        return this.searchState;
    }

    public void setSearchState(SearchState value) {
        this.searchState = value;
    }

    public List<FindEventResult> getResult() {
        if (this.result == null) {
            this.result = new ArrayList<FindEventResult>();
        }
        return this.result;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

