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
import org.apache.cxf.jaxws.handler.types.DisplayNameType;
import org.apache.cxf.jaxws.handler.types.FullyQualifiedClassType;
import org.apache.cxf.jaxws.handler.types.IconType;
import org.apache.cxf.jaxws.handler.types.ParamValueType;
import org.apache.cxf.jaxws.handler.types.XsdQNameType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="port-component_handlerType", propOrder={"description", "displayName", "icon", "handlerName", "handlerClass", "initParam", "soapHeader", "soapRole"})
public class PortComponentHandlerType {
    protected List<DescriptionType> description;
    @XmlElement(name="display-name")
    protected List<DisplayNameType> displayName;
    protected List<IconType> icon;
    @XmlElement(name="handler-name", required=true)
    protected CString handlerName;
    @XmlElement(name="handler-class", required=true)
    protected FullyQualifiedClassType handlerClass;
    @XmlElement(name="init-param")
    protected List<ParamValueType> initParam;
    @XmlElement(name="soap-header")
    protected List<XsdQNameType> soapHeader;
    @XmlElement(name="soap-role")
    protected List<CString> soapRole;
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

    public List<DisplayNameType> getDisplayName() {
        if (this.displayName == null) {
            this.displayName = new ArrayList<DisplayNameType>();
        }
        return this.displayName;
    }

    public List<IconType> getIcon() {
        if (this.icon == null) {
            this.icon = new ArrayList<IconType>();
        }
        return this.icon;
    }

    public CString getHandlerName() {
        if (this.handlerName == null) {
            this.handlerName = new CString();
            this.handlerName.setValue("");
        }
        return this.handlerName;
    }

    public void setHandlerName(CString value) {
        this.handlerName = value;
    }

    public FullyQualifiedClassType getHandlerClass() {
        return this.handlerClass;
    }

    public void setHandlerClass(FullyQualifiedClassType value) {
        this.handlerClass = value;
    }

    public List<ParamValueType> getInitParam() {
        if (this.initParam == null) {
            this.initParam = new ArrayList<ParamValueType>();
        }
        return this.initParam;
    }

    public List<XsdQNameType> getSoapHeader() {
        if (this.soapHeader == null) {
            this.soapHeader = new ArrayList<XsdQNameType>();
        }
        return this.soapHeader;
    }

    public List<CString> getSoapRole() {
        if (this.soapRole == null) {
            this.soapRole = new ArrayList<CString>();
        }
        return this.soapRole;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }
}

