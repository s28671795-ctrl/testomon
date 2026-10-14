/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.attachment.external;

import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.policy.attachment.external.DomainExpression;

public class EndpointReferenceDomainExpression
implements DomainExpression {
    private EndpointReferenceType epr;

    public EndpointReferenceType getEndpointReference() {
        return this.epr;
    }

    public void setEndpointReference(EndpointReferenceType e) {
        this.epr = e;
    }

    @Override
    public boolean appliesTo(BindingFaultInfo bfi) {
        return false;
    }

    @Override
    public boolean appliesTo(BindingMessageInfo bmi) {
        return false;
    }

    @Override
    public boolean appliesTo(BindingOperationInfo boi) {
        return false;
    }

    @Override
    public boolean appliesTo(EndpointInfo ei) {
        return this.epr.getAddress().getValue().equals(ei.getAddress());
    }

    @Override
    public boolean appliesTo(ServiceInfo si) {
        return false;
    }
}

