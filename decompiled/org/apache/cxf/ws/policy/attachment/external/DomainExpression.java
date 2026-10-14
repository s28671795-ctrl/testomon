/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.attachment.external;

import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.ServiceInfo;

public interface DomainExpression {
    public boolean appliesTo(BindingFaultInfo var1);

    public boolean appliesTo(BindingMessageInfo var1);

    public boolean appliesTo(BindingOperationInfo var1);

    public boolean appliesTo(EndpointInfo var1);

    public boolean appliesTo(ServiceInfo var1);
}

