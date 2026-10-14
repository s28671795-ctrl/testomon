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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.SourceIdentificationExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SourceIdentification", propOrder={"name", "token", "extension"})
public class SourceIdentification {
    @XmlElement(name="Name", required=true)
    protected String name;
    @XmlElement(name="Token", required=true)
    protected List<String> token;
    @XmlElement(name="Extension")
    protected SourceIdentificationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public List<String> getToken() {
        if (this.token == null) {
            this.token = new ArrayList<String>();
        }
        return this.token;
    }

    public SourceIdentificationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(SourceIdentificationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

