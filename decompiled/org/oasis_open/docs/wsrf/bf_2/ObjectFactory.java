/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.bf_2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.oasis_open.docs.wsrf.bf_2.BaseFaultType;

@XmlRegistry
public class ObjectFactory {
    private static final QName _BaseFault_QNAME = new QName("http://docs.oasis-open.org/wsrf/bf-2", "BaseFault");

    public BaseFaultType createBaseFaultType() {
        return new BaseFaultType();
    }

    public BaseFaultType.ErrorCode createBaseFaultTypeErrorCode() {
        return new BaseFaultType.ErrorCode();
    }

    public BaseFaultType.Description createBaseFaultTypeDescription() {
        return new BaseFaultType.Description();
    }

    public BaseFaultType.FaultCause createBaseFaultTypeFaultCause() {
        return new BaseFaultType.FaultCause();
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsrf/bf-2", name="BaseFault")
    public JAXBElement<BaseFaultType> createBaseFault(BaseFaultType value) {
        return new JAXBElement<BaseFaultType>(_BaseFault_QNAME, BaseFaultType.class, null, value);
    }
}

