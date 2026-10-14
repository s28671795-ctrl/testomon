/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wsu10;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.apache.wss4j.binding.wsu10.AttributedDateTime;
import org.apache.wss4j.binding.wsu10.AttributedURI;
import org.apache.wss4j.binding.wsu10.TimestampType;

@XmlRegistry
public class ObjectFactory {
    private static final String WSU_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
    private static final QName _Expires_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Expires");
    private static final QName _Created_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Created");
    private static final QName _Timestamp_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Timestamp");

    public AttributedDateTime createAttributedDateTime() {
        return new AttributedDateTime();
    }

    public TimestampType createTimestampType() {
        return new TimestampType();
    }

    public AttributedURI createAttributedURI() {
        return new AttributedURI();
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", name="Expires")
    public JAXBElement<AttributedDateTime> createExpires(AttributedDateTime value) {
        return new JAXBElement<AttributedDateTime>(_Expires_QNAME, AttributedDateTime.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", name="Created")
    public JAXBElement<AttributedDateTime> createCreated(AttributedDateTime value) {
        return new JAXBElement<AttributedDateTime>(_Created_QNAME, AttributedDateTime.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", name="Timestamp")
    public JAXBElement<TimestampType> createTimestamp(TimestampType value) {
        return new JAXBElement<TimestampType>(_Timestamp_QNAME, TimestampType.class, null, value);
    }
}

