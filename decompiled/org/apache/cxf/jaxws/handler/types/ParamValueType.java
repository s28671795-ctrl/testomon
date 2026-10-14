/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.cxf.jaxws.handler.types.CString;
import org.apache.cxf.jaxws.handler.types.DescriptionType;
import org.apache.cxf.jaxws.handler.types.XsdStringType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="param-valueType", propOrder={"description", "paramName", "paramValue"})
public class ParamValueType {
    protected List<DescriptionType> description;
    @XmlElement(name="param-name", required=true)
    protected CString paramName;
    @XmlElement(name="param-value", required=true)
    protected XsdStringType paramValue;
    @XmlAttribute
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlID
    protected String id;

    public List<DescriptionType> getDescription() {
        if (this.description == null) {
            this.description = new ArrayList<DescriptionType>();
        }
        return this.description;
    }

    public CString getParamName() {
        return this.paramName;
    }

    public void setParamName(CString value) {
        this.paramName = value;
    }

    public XsdStringType getParamValue() {
        return this.paramValue;
    }

    public void setParamValue(XsdStringType value) {
        this.paramValue = value;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }
}

