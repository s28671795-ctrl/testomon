/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.advancedsecurity.wsdl.DNAttributeTypeAndValue;
import org.onvif.ver10.advancedsecurity.wsdl.MultiValuedRDN;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DistinguishedName", propOrder={"country", "organization", "organizationalUnit", "distinguishedNameQualifier", "stateOrProvinceName", "commonName", "serialNumber", "locality", "title", "surname", "givenName", "initials", "pseudonym", "generationQualifier", "genericAttribute", "multiValuedRDN", "anyAttribute"})
public class DistinguishedName {
    @XmlElement(name="Country")
    protected List<String> country;
    @XmlElement(name="Organization")
    protected List<String> organization;
    @XmlElement(name="OrganizationalUnit")
    protected List<String> organizationalUnit;
    @XmlElement(name="DistinguishedNameQualifier")
    protected List<String> distinguishedNameQualifier;
    @XmlElement(name="StateOrProvinceName")
    protected List<String> stateOrProvinceName;
    @XmlElement(name="CommonName")
    protected List<String> commonName;
    @XmlElement(name="SerialNumber")
    protected List<String> serialNumber;
    @XmlElement(name="Locality")
    protected List<String> locality;
    @XmlElement(name="Title")
    protected List<String> title;
    @XmlElement(name="Surname")
    protected List<String> surname;
    @XmlElement(name="GivenName")
    protected List<String> givenName;
    @XmlElement(name="Initials")
    protected List<String> initials;
    @XmlElement(name="Pseudonym")
    protected List<String> pseudonym;
    @XmlElement(name="GenerationQualifier")
    protected List<String> generationQualifier;
    @XmlElement(name="GenericAttribute")
    protected List<DNAttributeTypeAndValue> genericAttribute;
    @XmlElement(name="MultiValuedRDN")
    protected List<MultiValuedRDN> multiValuedRDN;
    protected AnyAttribute anyAttribute;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<String> getCountry() {
        if (this.country == null) {
            this.country = new ArrayList<String>();
        }
        return this.country;
    }

    public List<String> getOrganization() {
        if (this.organization == null) {
            this.organization = new ArrayList<String>();
        }
        return this.organization;
    }

    public List<String> getOrganizationalUnit() {
        if (this.organizationalUnit == null) {
            this.organizationalUnit = new ArrayList<String>();
        }
        return this.organizationalUnit;
    }

    public List<String> getDistinguishedNameQualifier() {
        if (this.distinguishedNameQualifier == null) {
            this.distinguishedNameQualifier = new ArrayList<String>();
        }
        return this.distinguishedNameQualifier;
    }

    public List<String> getStateOrProvinceName() {
        if (this.stateOrProvinceName == null) {
            this.stateOrProvinceName = new ArrayList<String>();
        }
        return this.stateOrProvinceName;
    }

    public List<String> getCommonName() {
        if (this.commonName == null) {
            this.commonName = new ArrayList<String>();
        }
        return this.commonName;
    }

    public List<String> getSerialNumber() {
        if (this.serialNumber == null) {
            this.serialNumber = new ArrayList<String>();
        }
        return this.serialNumber;
    }

    public List<String> getLocality() {
        if (this.locality == null) {
            this.locality = new ArrayList<String>();
        }
        return this.locality;
    }

    public List<String> getTitle() {
        if (this.title == null) {
            this.title = new ArrayList<String>();
        }
        return this.title;
    }

    public List<String> getSurname() {
        if (this.surname == null) {
            this.surname = new ArrayList<String>();
        }
        return this.surname;
    }

    public List<String> getGivenName() {
        if (this.givenName == null) {
            this.givenName = new ArrayList<String>();
        }
        return this.givenName;
    }

    public List<String> getInitials() {
        if (this.initials == null) {
            this.initials = new ArrayList<String>();
        }
        return this.initials;
    }

    public List<String> getPseudonym() {
        if (this.pseudonym == null) {
            this.pseudonym = new ArrayList<String>();
        }
        return this.pseudonym;
    }

    public List<String> getGenerationQualifier() {
        if (this.generationQualifier == null) {
            this.generationQualifier = new ArrayList<String>();
        }
        return this.generationQualifier;
    }

    public List<DNAttributeTypeAndValue> getGenericAttribute() {
        if (this.genericAttribute == null) {
            this.genericAttribute = new ArrayList<DNAttributeTypeAndValue>();
        }
        return this.genericAttribute;
    }

    public List<MultiValuedRDN> getMultiValuedRDN() {
        if (this.multiValuedRDN == null) {
            this.multiValuedRDN = new ArrayList<MultiValuedRDN>();
        }
        return this.multiValuedRDN;
    }

    public AnyAttribute getAnyAttribute() {
        return this.anyAttribute;
    }

    public void setAnyAttribute(AnyAttribute value) {
        this.anyAttribute = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"any"})
    public static class AnyAttribute {
        @XmlAnyElement(lax=true)
        protected List<Object> any;

        public List<Object> getAny() {
            if (this.any == null) {
                this.any = new ArrayList<Object>();
            }
            return this.any;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
        }
    }
}

