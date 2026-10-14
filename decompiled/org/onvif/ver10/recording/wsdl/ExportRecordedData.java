/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.recording.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.SearchScope;
import org.onvif.ver10.schema.StorageReferencePath;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"searchScope", "fileFormat", "storageDestination"})
@XmlRootElement(name="ExportRecordedData")
public class ExportRecordedData {
    @XmlElement(name="SearchScope", required=true)
    protected SearchScope searchScope;
    @XmlElement(name="FileFormat", required=true)
    protected String fileFormat;
    @XmlElement(name="StorageDestination", required=true)
    protected StorageReferencePath storageDestination;

    public SearchScope getSearchScope() {
        return this.searchScope;
    }

    public void setSearchScope(SearchScope value) {
        this.searchScope = value;
    }

    public String getFileFormat() {
        return this.fileFormat;
    }

    public void setFileFormat(String value) {
        this.fileFormat = value;
    }

    public StorageReferencePath getStorageDestination() {
        return this.storageDestination;
    }

    public void setStorageDestination(StorageReferencePath value) {
        this.storageDestination = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

