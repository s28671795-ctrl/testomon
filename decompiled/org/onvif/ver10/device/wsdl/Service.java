/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

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
import org.onvif.ver10.schema.OnvifVersion;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Service", propOrder={"namespace", "xAddr", "capabilities", "version", "any"})
public class Service {
    @XmlElement(name="Namespace", required=true)
    @XmlSchemaType(name="anyURI")
    protected String namespace;
    @XmlElement(name="XAddr", required=true)
    @XmlSchemaType(name="anyURI")
    protected String xAddr;
    @XmlElement(name="Capabilities")
    protected Capabilities capabilities;
    @XmlElement(name="Version", required=true)
    protected OnvifVersion version;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getNamespace() {
        return this.namespace;
    }

    public void setNamespace(String value) {
        this.namespace = value;
    }

    public String getXAddr() {
        return this.xAddr;
    }

    public void setXAddr(String value) {
        this.xAddr = value;
    }

    public Capabilities getCapabilities() {
        return this.capabilities;
    }

    public void setCapabilities(Capabilities value) {
        this.capabilities = value;
    }

    public OnvifVersion getVersion() {
        return this.version;
    }

    public void setVersion(OnvifVersion value) {
        this.version = value;
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

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"any"})
    public static class Capabilities {
        @XmlAnyElement(lax=true)
        protected Object any;

        public Object getAny() {
            return this.any;
        }

        public void setAny(Object value) {
            this.any = value;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
        }
    }
}

