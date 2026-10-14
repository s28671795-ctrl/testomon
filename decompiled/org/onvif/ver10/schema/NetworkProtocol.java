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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.NetworkProtocolExtension;
import org.onvif.ver10.schema.NetworkProtocolType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="NetworkProtocol", propOrder={"name", "enabled", "port", "extension"})
public class NetworkProtocol {
    @XmlElement(name="Name", required=true)
    @XmlSchemaType(name="string")
    protected NetworkProtocolType name;
    @XmlElement(name="Enabled")
    protected boolean enabled;
    @XmlElement(name="Port", type=Integer.class)
    protected List<Integer> port;
    @XmlElement(name="Extension")
    protected NetworkProtocolExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public NetworkProtocolType getName() {
        return this.name;
    }

    public void setName(NetworkProtocolType value) {
        this.name = value;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean value) {
        this.enabled = value;
    }

    public List<Integer> getPort() {
        if (this.port == null) {
            this.port = new ArrayList<Integer>();
        }
        return this.port;
    }

    public NetworkProtocolExtension getExtension() {
        return this.extension;
    }

    public void setExtension(NetworkProtocolExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

