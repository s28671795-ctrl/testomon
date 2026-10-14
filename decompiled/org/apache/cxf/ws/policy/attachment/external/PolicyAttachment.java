/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.attachment.external;

import java.util.Collection;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.ws.policy.attachment.external.DomainExpression;
import org.apache.neethi.Policy;
import org.w3c.dom.Element;

public class PolicyAttachment {
    private Collection<DomainExpression> domainExpressions;
    private Policy policy;
    private Element element;

    public Collection<DomainExpression> getDomainExpressions() {
        return this.domainExpressions;
    }

    public void setDomainExpressions(Collection<DomainExpression> des) {
        this.domainExpressions = des;
    }

    public Policy getPolicy() {
        return this.policy;
    }

    public void setPolicy(Policy p) {
        this.policy = p;
    }

    public Element getElement() {
        return this.element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public boolean appliesTo(ServiceInfo si) {
        for (DomainExpression de : this.domainExpressions) {
            if (!de.appliesTo(si)) continue;
            return true;
        }
        return false;
    }

    public boolean appliesTo(EndpointInfo ei) {
        for (DomainExpression de : this.domainExpressions) {
            if (!de.appliesTo(ei)) continue;
            return true;
        }
        return false;
    }

    public boolean appliesTo(BindingOperationInfo boi) {
        for (DomainExpression de : this.domainExpressions) {
            if (!de.appliesTo(boi)) continue;
            return true;
        }
        return false;
    }

    public boolean appliesTo(BindingMessageInfo bmi) {
        for (DomainExpression de : this.domainExpressions) {
            if (!de.appliesTo(bmi)) continue;
            return true;
        }
        return false;
    }

    public boolean appliesTo(BindingFaultInfo bfi) {
        for (DomainExpression de : this.domainExpressions) {
            if (!de.appliesTo(bfi)) continue;
            return true;
        }
        return false;
    }
}

