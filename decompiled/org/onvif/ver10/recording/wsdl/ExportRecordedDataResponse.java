/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.recording.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"operationToken", "fileNames", "extension"})
@XmlRootElement(name="ExportRecordedDataResponse")
public class ExportRecordedDataResponse {
    @XmlElement(name="OperationToken", required=true)
    protected String operationToken;
    @XmlElement(name="FileNames")
    protected List<String> fileNames;
    @XmlElement(name="Extension")
    protected Extension extension;

    public String getOperationToken() {
        return this.operationToken;
    }

    public void setOperationToken(String value) {
        this.operationToken = value;
    }

    public List<String> getFileNames() {
        if (this.fileNames == null) {
            this.fileNames = new ArrayList<String>();
        }
        return this.fileNames;
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

