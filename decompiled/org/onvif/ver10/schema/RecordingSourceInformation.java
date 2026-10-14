/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RecordingSourceInformation", propOrder={"sourceId", "name", "location", "description", "address", "any"})
public class RecordingSourceInformation {
    @XmlElement(name="SourceId", required=true)
    @XmlSchemaType(name="anyURI")
    protected String sourceId;
    @XmlElement(name="Name", required=true)
    protected String name;
    @XmlElement(name="Location", required=true)
    protected String location;
    @XmlElement(name="Description", required=true)
    protected String description;
    @XmlElement(name="Address", required=true)
    @XmlSchemaType(name="anyURI")
    protected String address;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(String value) {
        this.sourceId = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String value) {
        this.location = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String value) {
        this.address = value;
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

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

