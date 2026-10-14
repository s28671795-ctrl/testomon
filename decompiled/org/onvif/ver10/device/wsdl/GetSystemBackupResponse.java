/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.BackupFile;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"backupFiles"})
@XmlRootElement(name="GetSystemBackupResponse")
public class GetSystemBackupResponse {
    @XmlElement(name="BackupFiles", required=true)
    protected List<BackupFile> backupFiles;

    public List<BackupFile> getBackupFiles() {
        if (this.backupFiles == null) {
            this.backupFiles = new ArrayList<BackupFile>();
        }
        return this.backupFiles;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

