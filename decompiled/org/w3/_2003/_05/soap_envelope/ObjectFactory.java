/*
 * Decompiled with CFR 0.152.
 */
package org.w3._2003._05.soap_envelope;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.w3._2003._05.soap_envelope.Body;
import org.w3._2003._05.soap_envelope.Detail;
import org.w3._2003._05.soap_envelope.Envelope;
import org.w3._2003._05.soap_envelope.Fault;
import org.w3._2003._05.soap_envelope.Faultcode;
import org.w3._2003._05.soap_envelope.Faultreason;
import org.w3._2003._05.soap_envelope.Header;
import org.w3._2003._05.soap_envelope.NotUnderstoodType;
import org.w3._2003._05.soap_envelope.Reasontext;
import org.w3._2003._05.soap_envelope.Subcode;
import org.w3._2003._05.soap_envelope.SupportedEnvType;
import org.w3._2003._05.soap_envelope.UpgradeType;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Envelope_QNAME = new QName("http://www.w3.org/2003/05/soap-envelope", "Envelope");
    private static final QName _Header_QNAME = new QName("http://www.w3.org/2003/05/soap-envelope", "Header");
    private static final QName _Body_QNAME = new QName("http://www.w3.org/2003/05/soap-envelope", "Body");
    private static final QName _Fault_QNAME = new QName("http://www.w3.org/2003/05/soap-envelope", "Fault");
    private static final QName _NotUnderstood_QNAME = new QName("http://www.w3.org/2003/05/soap-envelope", "NotUnderstood");
    private static final QName _Upgrade_QNAME = new QName("http://www.w3.org/2003/05/soap-envelope", "Upgrade");

    public Envelope createEnvelope() {
        return new Envelope();
    }

    public Header createHeader() {
        return new Header();
    }

    public Body createBody() {
        return new Body();
    }

    public Fault createFault() {
        return new Fault();
    }

    public NotUnderstoodType createNotUnderstoodType() {
        return new NotUnderstoodType();
    }

    public UpgradeType createUpgradeType() {
        return new UpgradeType();
    }

    public Faultreason createFaultreason() {
        return new Faultreason();
    }

    public Reasontext createReasontext() {
        return new Reasontext();
    }

    public Faultcode createFaultcode() {
        return new Faultcode();
    }

    public Subcode createSubcode() {
        return new Subcode();
    }

    public Detail createDetail() {
        return new Detail();
    }

    public SupportedEnvType createSupportedEnvType() {
        return new SupportedEnvType();
    }

    @XmlElementDecl(namespace="http://www.w3.org/2003/05/soap-envelope", name="Envelope")
    public JAXBElement<Envelope> createEnvelope(Envelope value) {
        return new JAXBElement<Envelope>(_Envelope_QNAME, Envelope.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.w3.org/2003/05/soap-envelope", name="Header")
    public JAXBElement<Header> createHeader(Header value) {
        return new JAXBElement<Header>(_Header_QNAME, Header.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.w3.org/2003/05/soap-envelope", name="Body")
    public JAXBElement<Body> createBody(Body value) {
        return new JAXBElement<Body>(_Body_QNAME, Body.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.w3.org/2003/05/soap-envelope", name="Fault")
    public JAXBElement<Fault> createFault(Fault value) {
        return new JAXBElement<Fault>(_Fault_QNAME, Fault.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.w3.org/2003/05/soap-envelope", name="NotUnderstood")
    public JAXBElement<NotUnderstoodType> createNotUnderstood(NotUnderstoodType value) {
        return new JAXBElement<NotUnderstoodType>(_NotUnderstood_QNAME, NotUnderstoodType.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.w3.org/2003/05/soap-envelope", name="Upgrade")
    public JAXBElement<UpgradeType> createUpgrade(UpgradeType value) {
        return new JAXBElement<UpgradeType>(_Upgrade_QNAME, UpgradeType.class, null, value);
    }
}

