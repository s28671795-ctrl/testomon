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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.device.wsdl.UserCredential;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="StorageConfigurationData", propOrder={"localPath", "storageUri", "user", "extension"})
public class StorageConfigurationData {
    @XmlElement(name="LocalPath")
    @XmlSchemaType(name="anyURI")
    protected String localPath;
    @XmlElement(name="StorageUri")
    @XmlSchemaType(name="anyURI")
    protected String storageUri;
    @XmlElement(name="User")
    protected UserCredential user;
    @XmlElement(name="Extension")
    protected Extension extension;
    @XmlAttribute(name="type", required=true)
    protected String type;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getLocalPath() {
        return this.localPath;
    }

    public void setLocalPath(String value) {
        this.localPath = value;
    }

    public String getStorageUri() {
        return this.storageUri;
    }

    public void setStorageUri(String value) {
        this.storageUri = value;
    }

    public UserCredential getUser() {
        return this.user;
    }

    public void setUser(UserCredential value) {
        this.user = value;
    }

    public Extension getExtension() {
        return this.extension;
    }

    public void setExtension(Extension value) {
        this.extension = value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"any"})
    public static class Extension {
        @XmlAnyElement(lax=true)
        protected List<Object> any;

        public List<Object> getAny() {
            if (this.any == null) {
                this.any = new ArrayList<Object>();
            }
            return this.any;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
        }
    }
}

