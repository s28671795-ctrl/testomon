/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

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
import org.onvif.ver10.schema.StorageReferencePathExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="StorageReferencePath", propOrder={"storageToken", "relativePath", "extension"})
public class StorageReferencePath {
    @XmlElement(name="StorageToken", required=true)
    protected String storageToken;
    @XmlElement(name="RelativePath")
    protected String relativePath;
    @XmlElement(name="Extension")
    protected StorageReferencePathExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getStorageToken() {
        return this.storageToken;
    }

    public void setStorageToken(String value) {
        this.storageToken = value;
    }

    public String getRelativePath() {
        return this.relativePath;
    }

    public void setRelativePath(String value) {
        this.relativePath = value;
    }

    public StorageReferencePathExtension getExtension() {
        return this.extension;
    }

    public void setExtension(StorageReferencePathExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

