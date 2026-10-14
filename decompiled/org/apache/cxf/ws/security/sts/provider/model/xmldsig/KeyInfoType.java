/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model.xmldsig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="KeyInfoType", propOrder={"content"})
public class KeyInfoType {
    @XmlElementRefs(value={@XmlElementRef(name="KeyName", namespace="http://www.w3.org/2000/09/xmldsig#", type=JAXBElement.class, required=false), @XmlElementRef(name="KeyValue", namespace="http://www.w3.org/2000/09/xmldsig#", type=JAXBElement.class, required=false), @XmlElementRef(name="RetrievalMethod", namespace="http://www.w3.org/2000/09/xmldsig#", type=JAXBElement.class, required=false), @XmlElementRef(name="X509Data", namespace="http://www.w3.org/2000/09/xmldsig#", type=JAXBElement.class, required=false), @XmlElementRef(name="PGPData", namespace="http://www.w3.org/2000/09/xmldsig#", type=JAXBElement.class, required=false), @XmlElementRef(name="SPKIData", namespace="http://www.w3.org/2000/09/xmldsig#", type=JAXBElement.class, required=false), @XmlElementRef(name="MgmtData", namespace="http://www.w3.org/2000/09/xmldsig#", type=JAXBElement.class, required=false)})
    @XmlMixed
    @XmlAnyElement(lax=true)
    protected List<Object> content;
    @XmlAttribute(name="Id")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name="ID")
    protected String id;

    public List<Object> getContent() {
        if (this.content == null) {
            this.content = new ArrayList<Object>();
        }
        return this.content;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }
}

