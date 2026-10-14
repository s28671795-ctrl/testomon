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
import javax.xml.datatype.Duration;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MediaUri", propOrder={"uri", "invalidAfterConnect", "invalidAfterReboot", "timeout", "any"})
public class MediaUri {
    @XmlElement(name="Uri", required=true)
    @XmlSchemaType(name="anyURI")
    protected String uri;
    @XmlElement(name="InvalidAfterConnect")
    protected boolean invalidAfterConnect;
    @XmlElement(name="InvalidAfterReboot")
    protected boolean invalidAfterReboot;
    @XmlElement(name="Timeout", required=true)
    protected Duration timeout;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getUri() {
        return this.uri;
    }

    public void setUri(String value) {
        this.uri = value;
    }

    public boolean getInvalidAfterConnect() {
        return this.invalidAfterConnect;
    }

    public void setInvalidAfterConnect(boolean value) {
        this.invalidAfterConnect = value;
    }

    public boolean getInvalidAfterReboot() {
        return this.invalidAfterReboot;
    }

    public void setInvalidAfterReboot(boolean value) {
        this.invalidAfterReboot = value;
    }

    public Duration getTimeout() {
        return this.timeout;
    }

    public void setTimeout(Duration value) {
        this.timeout = value;
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

