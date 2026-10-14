/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model.xmldsig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.cxf.ws.security.sts.provider.model.xmldsig.CanonicalizationMethodType;
import org.apache.cxf.ws.security.sts.provider.model.xmldsig.ReferenceType;
import org.apache.cxf.ws.security.sts.provider.model.xmldsig.SignatureMethodType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SignedInfoType", propOrder={"canonicalizationMethod", "signatureMethod", "reference"})
public class SignedInfoType {
    @XmlElement(name="CanonicalizationMethod", required=true)
    protected CanonicalizationMethodType canonicalizationMethod = new CanonicalizationMethodType();
    @XmlElement(name="SignatureMethod", required=true)
    protected SignatureMethodType signatureMethod = new SignatureMethodType();
    @XmlElement(name="Reference", required=true)
    protected List<ReferenceType> reference;
    @XmlAttribute(name="Id")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name="ID")
    protected String id;

    public CanonicalizationMethodType getCanonicalizationMethod() {
        return this.canonicalizationMethod;
    }

    public void setCanonicalizationMethod(CanonicalizationMethodType value) {
        this.canonicalizationMethod = value;
    }

    public SignatureMethodType getSignatureMethod() {
        return this.signatureMethod;
    }

    public void setSignatureMethod(SignatureMethodType value) {
        this.signatureMethod = value;
    }

    public List<ReferenceType> getReference() {
        if (this.reference == null) {
            this.reference = new ArrayList<ReferenceType>();
        }
        return this.reference;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }
}

