/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MotionExpression", propOrder={"expression", "any"})
public class MotionExpression {
    @XmlElement(name="Expression", required=true)
    protected String expression;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="Type")
    protected String type;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String value) {
        this.expression = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

