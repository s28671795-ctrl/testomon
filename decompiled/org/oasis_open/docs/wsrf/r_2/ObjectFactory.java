/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.r_2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.oasis_open.docs.wsrf.r_2.ResourceUnavailableFaultType;
import org.oasis_open.docs.wsrf.r_2.ResourceUnknownFaultType;

@XmlRegistry
public class ObjectFactory {
    private static final QName _ResourceUnknownFault_QNAME = new QName("http://docs.oasis-open.org/wsrf/r-2", "ResourceUnknownFault");
    private static final QName _ResourceUnavailableFault_QNAME = new QName("http://docs.oasis-open.org/wsrf/r-2", "ResourceUnavailableFault");

    public ResourceUnknownFaultType createResourceUnknownFaultType() {
        return new ResourceUnknownFaultType();
    }

    public ResourceUnavailableFaultType createResourceUnavailableFaultType() {
        return new ResourceUnavailableFaultType();
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/r-2", name="ResourceUnknownFault")
    public JAXBElement<ResourceUnknownFaultType> createResourceUnknownFault(ResourceUnknownFaultType value) {
        return new JAXBElement<ResourceUnknownFaultType>(_ResourceUnknownFault_QNAME, ResourceUnknownFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/r-2", name="ResourceUnavailableFault")
    public JAXBElement<ResourceUnavailableFaultType> createResourceUnavailableFault(ResourceUnavailableFaultType value) {
        return new JAXBElement<ResourceUnavailableFaultType>(_ResourceUnavailableFault_QNAME, ResourceUnavailableFaultType.class, null, value);
    }
}

