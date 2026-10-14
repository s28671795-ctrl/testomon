/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.binding.xmldsig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="KeyValueType", namespace="http://www.w3.org/2000/09/xmldsig#", propOrder={"content"})
public class KeyValueType {
    @XmlElementRefs(value={@XmlElementRef(name="RSAKeyValue", namespace="http://www.w3.org/2000/09/xmldsig#", type=JAXBElement.class), @XmlElementRef(name="DSAKeyValue", namespace="http://www.w3.org/2000/09/xmldsig#", type=JAXBElement.class)})
    @XmlMixed
    @XmlAnyElement(lax=true)
    protected List<Object> content;

    public List<Object> getContent() {
        if (this.content == null) {
            this.content = new ArrayList<Object>();
        }
        return this.content;
    }
}

