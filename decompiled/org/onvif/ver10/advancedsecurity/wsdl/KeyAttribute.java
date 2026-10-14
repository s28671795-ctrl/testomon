/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="KeyAttribute", propOrder={"keyID", "alias", "hasPrivateKey", "keyStatus", "any", "externallyGenerated", "securelyStored", "extension"})
public class KeyAttribute {
    @XmlElement(name="KeyID", required=true)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="NCName")
    protected String keyID;
    @XmlElement(name="Alias")
    protected String alias;
    protected Boolean hasPrivateKey;
    @XmlElement(name="KeyStatus", required=true)
    protected String keyStatus;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    protected Boolean externallyGenerated;
    protected Boolean securelyStored;
    @XmlElement(name="Extension")
    protected Extension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getKeyID() {
        return this.keyID;
    }

    public void setKeyID(String value) {
        this.keyID = value;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String value) {
        this.alias = value;
    }

    public Boolean getHasPrivateKey() {
        return this.hasPrivateKey;
    }

    public void setHasPrivateKey(Boolean value) {
        this.hasPrivateKey = value;
    }

    public String getKeyStatus() {
        return this.keyStatus;
    }

    public void setKeyStatus(String value) {
        this.keyStatus = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Boolean getExternallyGenerated() {
        return this.externallyGenerated;
    }

    public void setExternallyGenerated(Boolean value) {
        this.externallyGenerated = value;
    }

    public Boolean getSecurelyStored() {
        return this.securelyStored;
    }

    public void setSecurelyStored(Boolean value) {
        this.securelyStored = value;
    }

    public Extension getExtension() {
        return this.extension;
    }

    public void setExtension(Extension value) {
        this.extension = value;
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

