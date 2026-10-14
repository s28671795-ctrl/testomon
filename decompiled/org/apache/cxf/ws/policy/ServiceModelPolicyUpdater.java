/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.Collection;
import javax.wsdl.extensions.UnknownExtensibilityElement;
import javax.xml.namespace.QName;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.DescriptionInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.Extensible;
import org.apache.cxf.ws.policy.attachment.external.PolicyAttachment;
import org.apache.neethi.Policy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ServiceModelPolicyUpdater {
    private EndpointInfo ei;

    public ServiceModelPolicyUpdater(EndpointInfo ei) {
        this.ei = ei;
    }

    public void addPolicyAttachments(Collection<PolicyAttachment> attachments) {
        for (PolicyAttachment pa : attachments) {
            boolean policyUsed = false;
            for (BindingOperationInfo boi : this.ei.getBinding().getOperations()) {
                BindingMessageInfo inputMessage = boi.getInput();
                BindingMessageInfo outputMessage = boi.getOutput();
                if (pa.appliesTo(boi)) {
                    this.addPolicyRef(boi, pa.getPolicy());
                    this.addPolicyRef(this.ei.getInterface().getOperation(boi.getName()), pa.getPolicy());
                    policyUsed = true;
                    continue;
                }
                if (pa.appliesTo(inputMessage)) {
                    this.addPolicyRef(inputMessage, pa.getPolicy());
                    policyUsed = true;
                    continue;
                }
                if (!pa.appliesTo(outputMessage)) continue;
                this.addPolicyRef(outputMessage, pa.getPolicy());
                policyUsed = true;
            }
            if (!policyUsed) continue;
            this.addPolicy(pa);
        }
    }

    private void addPolicyRef(Extensible ext, Policy p) {
        Document doc = DOMUtils.getEmptyDocument();
        Element el = doc.createElementNS(p.getNamespace(), "PolicyReference");
        el.setPrefix("wsp");
        el.setAttribute("URI", "#" + p.getId());
        UnknownExtensibilityElement uee = new UnknownExtensibilityElement();
        uee.setElementType(new QName(p.getNamespace(), "PolicyReference"));
        uee.setElement(el);
        uee.setRequired(true);
        ext.addExtensor(uee);
    }

    private void addPolicy(PolicyAttachment pa) {
        Element policyEl = pa.getElement();
        UnknownExtensibilityElement uee = new UnknownExtensibilityElement();
        uee.setRequired(true);
        uee.setElementType(DOMUtils.getElementQName(policyEl));
        uee.setElement(policyEl);
        if (this.ei.getService().getDescription() == null) {
            DescriptionInfo description = new DescriptionInfo();
            description.setName(this.ei.getService().getName());
            if (!StringUtils.isEmpty(this.ei.getAddress())) {
                description.setBaseURI(this.ei.getAddress() + "?wsdl");
            }
            this.ei.getService().setDescription(description);
        }
        this.ei.getService().getDescription().addExtensor(uee);
    }
}

