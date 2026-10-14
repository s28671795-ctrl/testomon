/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"oldCertPathValidationPolicyID", "newCertPathValidationPolicyID"})
@XmlRootElement(name="ReplaceCertPathValidationPolicyAssignment")
public class ReplaceCertPathValidationPolicyAssignment {
    @XmlElement(name="OldCertPathValidationPolicyID", required=true)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name="ID")
    protected String oldCertPathValidationPolicyID;
    @XmlElement(name="NewCertPathValidationPolicyID", required=true)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name="ID")
    protected String newCertPathValidationPolicyID;

    public String getOldCertPathValidationPolicyID() {
        return this.oldCertPathValidationPolicyID;
    }

    public void setOldCertPathValidationPolicyID(String value) {
        this.oldCertPathValidationPolicyID = value;
    }

    public String getNewCertPathValidationPolicyID() {
        return this.newCertPathValidationPolicyID;
    }

    public void setNewCertPathValidationPolicyID(String value) {
        this.newCertPathValidationPolicyID = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

