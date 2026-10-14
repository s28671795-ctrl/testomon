/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ScopeDefinition;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Scope", propOrder={"scopeDef", "scopeItem"})
public class Scope {
    @XmlElement(name="ScopeDef", required=true)
    @XmlSchemaType(name="string")
    protected ScopeDefinition scopeDef;
    @XmlElement(name="ScopeItem", required=true)
    @XmlSchemaType(name="anyURI")
    protected String scopeItem;

    public ScopeDefinition getScopeDef() {
        return this.scopeDef;
    }

    public void setScopeDef(ScopeDefinition value) {
        this.scopeDef = value;
    }

    public String getScopeItem() {
        return this.scopeItem;
    }

    public void setScopeItem(String value) {
        this.scopeItem = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

