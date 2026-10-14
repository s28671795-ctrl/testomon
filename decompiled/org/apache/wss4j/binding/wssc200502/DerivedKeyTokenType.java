/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wssc200502;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.wss4j.binding.wss10.SecurityTokenReferenceType;
import org.apache.wss4j.binding.wssc.AbstractDerivedKeyTokenType;
import org.apache.wss4j.binding.wssc200502.PropertiesType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DerivedKeyTokenType", propOrder={"securityTokenReference", "properties", "generation", "offset", "length", "label", "nonce"})
public class DerivedKeyTokenType
extends AbstractDerivedKeyTokenType {
    @XmlElement(name="SecurityTokenReference", namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")
    private SecurityTokenReferenceType securityTokenReference;
    @XmlElement(name="Properties")
    private PropertiesType properties;
    @XmlElement(name="Generation")
    @XmlSchemaType(name="unsignedLong")
    private BigInteger generation;
    @XmlElement(name="Offset")
    @XmlSchemaType(name="unsignedLong")
    private BigInteger offset;
    @XmlElement(name="Length")
    @XmlSchemaType(name="unsignedLong")
    private BigInteger length;
    @XmlElement(name="Label")
    private String label;
    @XmlElement(name="Nonce")
    private byte[] nonce;
    @XmlAttribute(name="Id", namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name="ID")
    private String id;
    @XmlAttribute(name="Algorithm")
    @XmlSchemaType(name="anyURI")
    private String algorithm;

    @Override
    public SecurityTokenReferenceType getSecurityTokenReference() {
        return this.securityTokenReference;
    }

    public void setSecurityTokenReference(SecurityTokenReferenceType value) {
        this.securityTokenReference = value;
    }

    @Override
    public PropertiesType getProperties() {
        return this.properties;
    }

    public void setProperties(PropertiesType value) {
        this.properties = value;
    }

    @Override
    public BigInteger getGeneration() {
        return this.generation;
    }

    public void setGeneration(BigInteger value) {
        this.generation = value;
    }

    @Override
    public BigInteger getOffset() {
        return this.offset;
    }

    public void setOffset(BigInteger value) {
        this.offset = value;
    }

    @Override
    public BigInteger getLength() {
        return this.length;
    }

    public void setLength(BigInteger value) {
        this.length = value;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public void setLabel(String value) {
        this.label = value;
    }

    @Override
    public byte[] getNonce() {
        return this.nonce;
    }

    public void setNonce(byte[] value) {
        this.nonce = value;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String value) {
        this.id = value;
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }

    public void setAlgorithm(String value) {
        this.algorithm = value;
    }
}

