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
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="X509DataType", propOrder={"x509IssuerSerialOrX509SKIOrX509SubjectName"})
public class X509DataType {
    @XmlElementRefs(value={@XmlElementRef(name="X509IssuerSerial", namespace="http://www.w3.org/2000/09/xmldsig#", type=JAXBElement.class, required=false), @XmlElementRef(name="X509SKI", namespace="http://www.w3.org/2000/09/xmldsig#", type=JAXBElement.class, required=false), @XmlElementRef(name="X509SubjectName", namespace="http://www.w3.org/2000/09/xmldsig#", type=JAXBElement.class, required=false), @XmlElementRef(name="X509Certificate", namespace="http://www.w3.org/2000/09/xmldsig#", type=JAXBElement.class, required=false), @XmlElementRef(name="X509CRL", namespace="http://www.w3.org/2000/09/xmldsig#", type=JAXBElement.class, required=false)})
    @XmlAnyElement(lax=true)
    protected List<Object> x509IssuerSerialOrX509SKIOrX509SubjectName;

    public List<Object> getX509IssuerSerialOrX509SKIOrX509SubjectName() {
        if (this.x509IssuerSerialOrX509SKIOrX509SubjectName == null) {
            this.x509IssuerSerialOrX509SKIOrX509SubjectName = new ArrayList<Object>();
        }
        return this.x509IssuerSerialOrX509SKIOrX509SubjectName;
    }
}

