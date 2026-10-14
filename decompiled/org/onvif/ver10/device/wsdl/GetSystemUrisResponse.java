/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.SystemLogUriList;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"systemLogUris", "supportInfoUri", "systemBackupUri", "extension"})
@XmlRootElement(name="GetSystemUrisResponse")
public class GetSystemUrisResponse {
    @XmlElement(name="SystemLogUris")
    protected SystemLogUriList systemLogUris;
    @XmlElement(name="SupportInfoUri")
    @XmlSchemaType(name="anyURI")
    protected String supportInfoUri;
    @XmlElement(name="SystemBackupUri")
    @XmlSchemaType(name="anyURI")
    protected String systemBackupUri;
    @XmlElement(name="Extension")
    protected Extension extension;

    public SystemLogUriList getSystemLogUris() {
        return this.systemLogUris;
    }

    public void setSystemLogUris(SystemLogUriList value) {
        this.systemLogUris = value;
    }

    public String getSupportInfoUri() {
        return this.supportInfoUri;
    }

    public void setSupportInfoUri(String value) {
        this.supportInfoUri = value;
    }

    public String getSystemBackupUri() {
        return this.systemBackupUri;
    }

    public void setSystemBackupUri(String value) {
        this.systemBackupUri = value;
    }

    public Extension getExtension() {
        return this.extension;
    }

    public void setExtension(Extension value) {
        this.extension = value;
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

