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
import org.onvif.ver10.schema.Scope;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"scopes"})
@XmlRootElement(name="GetScopesResponse")
public class GetScopesResponse {
    @XmlElement(name="Scopes", required=true)
    protected List<Scope> scopes;

    public List<Scope> getScopes() {
        if (this.scopes == null) {
            this.scopes = new ArrayList<Scope>();
        }
        return this.scopes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

