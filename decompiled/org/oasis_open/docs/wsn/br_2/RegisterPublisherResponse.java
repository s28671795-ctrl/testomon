/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.br_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"publisherRegistrationReference", "consumerReference"})
@XmlRootElement(name="RegisterPublisherResponse")
public class RegisterPublisherResponse {
    @XmlElement(name="PublisherRegistrationReference", required=true)
    protected W3CEndpointReference publisherRegistrationReference;
    @XmlElement(name="ConsumerReference")
    protected W3CEndpointReference consumerReference;

    public W3CEndpointReference getPublisherRegistrationReference() {
        return this.publisherRegistrationReference;
    }

    public void setPublisherRegistrationReference(W3CEndpointReference value) {
        this.publisherRegistrationReference = value;
    }

    public W3CEndpointReference getConsumerReference() {
        return this.consumerReference;
    }

    public void setConsumerReference(W3CEndpointReference value) {
        this.consumerReference = value;
    }
}

