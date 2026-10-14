/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accessrules.wsdl;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.accessrules.wsdl.AccessPolicyExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AccessPolicy", propOrder={"scheduleToken", "entity", "entityType", "extension"})
public class AccessPolicy {
    @XmlElement(name="ScheduleToken", required=true)
    protected String scheduleToken;
    @XmlElement(name="Entity", required=true)
    protected String entity;
    @XmlElement(name="EntityType")
    protected QName entityType;
    @XmlElement(name="Extension")
    protected AccessPolicyExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getScheduleToken() {
        return this.scheduleToken;
    }

    public void setScheduleToken(String value) {
        this.scheduleToken = value;
    }

    public String getEntity() {
        return this.entity;
    }

    public void setEntity(String value) {
        this.entity = value;
    }

    public QName getEntityType() {
        return this.entityType;
    }

    public void setEntityType(QName value) {
        this.entityType = value;
    }

    public AccessPolicyExtension getExtension() {
        return this.extension;
    }

    public void setExtension(AccessPolicyExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

