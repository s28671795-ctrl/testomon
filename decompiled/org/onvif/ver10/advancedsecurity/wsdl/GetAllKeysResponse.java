/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.advancedsecurity.wsdl.KeyAttribute;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"keyAttribute"})
@XmlRootElement(name="GetAllKeysResponse")
public class GetAllKeysResponse {
    @XmlElement(name="KeyAttribute")
    protected List<KeyAttribute> keyAttribute;

    public List<KeyAttribute> getKeyAttribute() {
        if (this.keyAttribute == null) {
            this.keyAttribute = new ArrayList<KeyAttribute>();
        }
        return this.keyAttribute;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

