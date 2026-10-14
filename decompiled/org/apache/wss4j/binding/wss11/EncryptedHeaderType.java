/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wss11;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.xml.security.binding.xmlenc.EncryptedDataType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="EncryptedHeaderType", propOrder={"encryptedData"})
public class EncryptedHeaderType {
    @XmlElement(name="EncryptedData", namespace="http://www.w3.org/2001/04/xmlenc#", required=true)
    private EncryptedDataType encryptedData;
    @XmlAttribute(name="Id", namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name="ID")
    private String id;
    @XmlAttribute(name="mustUnderstand", namespace="http://schemas.xmlsoap.org/soap/envelope/")
    private Boolean mustUnderstand11;
    @XmlAttribute(name="actor", namespace="http://schemas.xmlsoap.org/soap/envelope/")
    @XmlSchemaType(name="anyURI")
    private String actor;
    @XmlAttribute(name="mustUnderstand", namespace="http://www.w3.org/2003/05/soap-envelope")
    private Boolean mustUnderstand12;
    @XmlAttribute(name="role", namespace="http://www.w3.org/2003/05/soap-envelope")
    @XmlSchemaType(name="anyURI")
    private String role;
    @XmlAttribute(name="relay", namespace="http://www.w3.org/2003/05/soap-envelope")
    private Boolean relay;

    public EncryptedDataType getEncryptedData() {
        return this.encryptedData;
    }

    public void setEncryptedData(EncryptedDataType value) {
        this.encryptedData = value;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public Boolean isMustUnderstand11() {
        return this.mustUnderstand11;
    }

    public void setMustUnderstand11(Boolean value) {
        this.mustUnderstand11 = value;
    }

    public String getActor() {
        return this.actor;
    }

    public void setActor(String value) {
        this.actor = value;
    }

    public boolean isMustUnderstand12() {
        if (this.mustUnderstand12 == null) {
            return false;
        }
        return this.mustUnderstand12;
    }

    public void setMustUnderstand12(Boolean value) {
        this.mustUnderstand12 = value;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String value) {
        this.role = value;
    }

    public boolean isRelay() {
        if (this.relay == null) {
            return false;
        }
        return this.relay;
    }

    public void setRelay(Boolean value) {
        this.relay = value;
    }
}

