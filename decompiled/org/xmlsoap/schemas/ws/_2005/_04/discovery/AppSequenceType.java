/*
 * Decompiled with CFR 0.152.
 */
package org.xmlsoap.schemas.ws._2005._04.discovery;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AppSequenceType")
public class AppSequenceType {
    @XmlAttribute(name="InstanceId", required=true)
    @XmlSchemaType(name="unsignedInt")
    protected long instanceId;
    @XmlAttribute(name="SequenceId")
    @XmlSchemaType(name="anyURI")
    protected String sequenceId;
    @XmlAttribute(name="MessageNumber", required=true)
    @XmlSchemaType(name="unsignedInt")
    protected long messageNumber;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public long getInstanceId() {
        return this.instanceId;
    }

    public void setInstanceId(long value) {
        this.instanceId = value;
    }

    public String getSequenceId() {
        return this.sequenceId;
    }

    public void setSequenceId(String value) {
        this.sequenceId = value;
    }

    public long getMessageNumber() {
        return this.messageNumber;
    }

    public void setMessageNumber(long value) {
        this.messageNumber = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

