/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.device.wsdl.StorageConfigurationData;
import org.onvif.ver10.schema.DeviceEntity;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="StorageConfiguration", propOrder={"data"})
public class StorageConfiguration
extends DeviceEntity {
    @XmlElement(name="Data", required=true)
    protected StorageConfigurationData data;

    public StorageConfigurationData getData() {
        return this.data;
    }

    public void setData(StorageConfigurationData value) {
        this.data = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

