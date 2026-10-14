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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ConfigDescriptionExtension;
import org.onvif.ver10.schema.ItemListDescription;
import org.onvif.ver10.schema.MessageDescription;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ConfigDescription", propOrder={"parameters", "messages", "extension"})
public class ConfigDescription {
    @XmlElement(name="Parameters", required=true)
    protected ItemListDescription parameters;
    @XmlElement(name="Messages")
    protected List<Messages> messages;
    @XmlElement(name="Extension")
    protected ConfigDescriptionExtension extension;
    @XmlAttribute(name="Name", required=true)
    protected QName name;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public ItemListDescription getParameters() {
        return this.parameters;
    }

    public void setParameters(ItemListDescription value) {
        this.parameters = value;
    }

    public List<Messages> getMessages() {
        if (this.messages == null) {
            this.messages = new ArrayList<Messages>();
        }
        return this.messages;
    }

    public ConfigDescriptionExtension getExtension() {
        return this.extension;
    }

    public void setExtension(ConfigDescriptionExtension value) {
        this.extension = value;
    }

    public QName getName() {
        return this.name;
    }

    public void setName(QName value) {
        this.name = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"parentTopic"})
    public static class Messages
    extends MessageDescription {
        @XmlElement(name="ParentTopic", required=true)
        protected String parentTopic;

        public String getParentTopic() {
            return this.parentTopic;
        }

        public void setParentTopic(String value) {
            this.parentTopic = value;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
        }
    }
}

