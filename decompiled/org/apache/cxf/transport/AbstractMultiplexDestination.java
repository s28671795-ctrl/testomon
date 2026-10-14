/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.AbstractDestination;
import org.apache.cxf.transport.MultiplexDestination;
import org.apache.cxf.ws.addressing.AddressingProperties;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.EndpointReferenceUtils;
import org.apache.cxf.ws.addressing.ObjectFactory;
import org.apache.cxf.ws.addressing.ReferenceParametersType;

public abstract class AbstractMultiplexDestination
extends AbstractDestination
implements MultiplexDestination {
    private static final QName MULTIPLEX_ID_QNAME = new QName("http://multiplex.transport.cxf.apache.org", "id");

    public AbstractMultiplexDestination(Bus b, EndpointReferenceType ref, EndpointInfo ei) {
        super(b, ref, ei);
    }

    @Override
    public EndpointReferenceType getAddressWithId(String id) {
        EndpointReferenceType epr = EndpointReferenceUtils.duplicate(EndpointReferenceUtils.mint(this.reference, this.bus));
        ReferenceParametersType newParams = new ObjectFactory().createReferenceParametersType();
        ReferenceParametersType existingParams = epr.getReferenceParameters();
        if (null != existingParams) {
            newParams.getAny().addAll(existingParams.getAny());
        }
        newParams.getAny().add(new JAXBElement<String>(MULTIPLEX_ID_QNAME, String.class, id));
        epr.setReferenceParameters(newParams);
        return epr;
    }

    @Override
    public String getId(Map<String, Object> contextMap) {
        EndpointReferenceType toEpr;
        String markedParam = null;
        AddressingProperties maps = (AddressingProperties)contextMap.get("javax.xml.ws.addressing.context.inbound");
        if (null != maps && null != (toEpr = maps.getToEndpointReference())) {
            markedParam = this.extractStringElementFromAny(MULTIPLEX_ID_QNAME, toEpr);
        }
        return markedParam;
    }

    private String extractStringElementFromAny(QName elementQName, EndpointReferenceType epr) {
        String elementStringValue = null;
        if (null != epr.getReferenceParameters()) {
            for (Object o : epr.getReferenceParameters().getAny()) {
                JAXBElement el;
                if (!(o instanceof JAXBElement) || !(el = (JAXBElement)o).getName().equals(elementQName)) continue;
                elementStringValue = (String)el.getValue();
            }
        }
        return elementStringValue;
    }
}

