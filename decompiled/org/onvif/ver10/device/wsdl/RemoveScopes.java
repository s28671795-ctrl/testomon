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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"scopeItem"})
@XmlRootElement(name="RemoveScopes")
public class RemoveScopes {
    @XmlElement(name="ScopeItem", required=true)
    @XmlSchemaType(name="anyURI")
    protected List<String> scopeItem;

    public List<String> getScopeItem() {
        if (this.scopeItem == null) {
            this.scopeItem = new ArrayList<String>();
        }
        return this.scopeItem;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

