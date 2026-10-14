/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.io.Resource
 */
package org.apache.cxf.ws.policy.attachment.external;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.ws.policy.PolicyConstants;
import org.apache.cxf.ws.policy.PolicyException;
import org.apache.cxf.ws.policy.attachment.AbstractPolicyProvider;
import org.apache.cxf.ws.policy.attachment.external.DomainExpression;
import org.apache.cxf.ws.policy.attachment.external.DomainExpressionBuilderRegistry;
import org.apache.cxf.ws.policy.attachment.external.PolicyAttachment;
import org.apache.cxf.ws.policy.attachment.reference.LocalDocumentReferenceResolver;
import org.apache.neethi.Constants;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyReference;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@NoJSR250Annotations
public class ExternalAttachmentProvider
extends AbstractPolicyProvider {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(ExternalAttachmentProvider.class);
    private Resource location;
    private Collection<PolicyAttachment> attachments;

    ExternalAttachmentProvider() {
    }

    ExternalAttachmentProvider(Bus b) {
        super(b);
    }

    public void setLocation(Resource u) {
        this.location = u;
    }

    public Resource getLocation() {
        return this.location;
    }

    @Override
    public Policy getEffectivePolicy(BindingFaultInfo bfi, Message m4) {
        this.readDocument();
        Policy p = null;
        for (PolicyAttachment pa : this.attachments) {
            if (!pa.appliesTo(bfi)) continue;
            if (p == null) {
                p = new Policy();
            }
            p = p.merge(pa.getPolicy());
        }
        return p;
    }

    @Override
    public Policy getEffectivePolicy(BindingMessageInfo bmi, Message m4) {
        this.readDocument();
        Policy p = null;
        for (PolicyAttachment pa : this.attachments) {
            if (!pa.appliesTo(bmi)) continue;
            if (p == null) {
                p = new Policy();
            }
            p = p.merge(pa.getPolicy());
        }
        return p;
    }

    @Override
    public Policy getEffectivePolicy(BindingOperationInfo boi, Message m4) {
        this.readDocument();
        Policy p = null;
        for (PolicyAttachment pa : this.attachments) {
            if (!pa.appliesTo(boi)) continue;
            if (p == null) {
                p = new Policy();
            }
            p = p.merge(pa.getPolicy());
        }
        return p;
    }

    @Override
    public Policy getEffectivePolicy(EndpointInfo ei, Message m4) {
        this.readDocument();
        Policy p = null;
        for (PolicyAttachment pa : this.attachments) {
            if (!pa.appliesTo(ei)) continue;
            if (p == null) {
                p = new Policy();
            }
            p = p.merge(pa.getPolicy());
        }
        return p;
    }

    @Override
    public Policy getEffectivePolicy(ServiceInfo si, Message m4) {
        this.readDocument();
        Policy p = null;
        for (PolicyAttachment pa : this.attachments) {
            if (!pa.appliesTo(si)) continue;
            if (p == null) {
                p = new Policy();
            }
            p = p.merge(pa.getPolicy());
        }
        return p;
    }

    void readDocument() {
        if (null != this.attachments) {
            return;
        }
        this.attachments = new ArrayList<PolicyAttachment>();
        Document doc = null;
        try {
            InputStream is = this.location.getInputStream();
            if (null == is) {
                throw new PolicyException(new org.apache.cxf.common.i18n.Message("COULD_NOT_OPEN_ATTACHMENT_DOC_EXC", BUNDLE, this.location));
            }
            doc = StaxUtils.read(is);
        }
        catch (Exception ex) {
            throw new PolicyException(ex);
        }
        for (Element ae : PolicyConstants.findAllPolicyElementsOfLocalName(doc, "PolicyAttachment")) {
            PolicyAttachment attachment = new PolicyAttachment();
            for (Node nd = ae.getFirstChild(); nd != null; nd = nd.getNextSibling()) {
                PolicyReference ref;
                if (1 != nd.getNodeType()) continue;
                QName qn = new QName(nd.getNamespaceURI(), nd.getLocalName());
                if (Constants.isAppliesToElem(qn)) {
                    Collection<DomainExpression> des = this.readDomainExpressions((Element)nd);
                    if (des.isEmpty()) continue;
                    attachment.setDomainExpressions(des);
                    continue;
                }
                if (Constants.isPolicyElement(qn)) {
                    Policy p = this.builder.getPolicy(nd);
                    if (null != attachment.getPolicy()) {
                        p = p.merge(attachment.getPolicy());
                    }
                    attachment.setPolicy(p);
                    attachment.setElement((Element)nd);
                    continue;
                }
                if (!Constants.isPolicyRef(qn) || null == (ref = this.builder.getPolicyReference(nd))) continue;
                Policy p = this.resolveReference(ref, doc);
                if (null != attachment.getPolicy()) {
                    p = p.merge(attachment.getPolicy());
                }
                attachment.setPolicy(p);
            }
            if (null == attachment.getPolicy() || null == attachment.getDomainExpressions()) continue;
            this.attachments.add(attachment);
        }
    }

    Policy resolveReference(PolicyReference ref, Document doc) {
        Policy p = null;
        p = this.isExternal(ref) ? this.resolveExternal(ref, doc.getBaseURI()) : this.resolveLocal(ref, doc);
        this.checkResolved(ref, p);
        return p;
    }

    Policy resolveLocal(PolicyReference ref, Document doc) {
        String relativeURI = ref.getURI().substring(1);
        String absoluteURI = doc.getBaseURI() + ref.getURI();
        Policy resolved = this.registry.lookup(absoluteURI);
        if (null != resolved) {
            return resolved;
        }
        LocalDocumentReferenceResolver resolver2 = new LocalDocumentReferenceResolver(doc, this.builder);
        resolved = resolver2.resolveReference(relativeURI);
        if (null != resolved) {
            ref.setURI(absoluteURI);
            this.registry.register(absoluteURI, resolved);
        }
        return resolved;
    }

    Collection<DomainExpression> readDomainExpressions(Element appliesToElem) {
        ArrayList<DomainExpression> des = new ArrayList<DomainExpression>();
        for (Node nd = appliesToElem.getFirstChild(); nd != null; nd = nd.getNextSibling()) {
            if (1 != nd.getNodeType()) continue;
            DomainExpressionBuilderRegistry debr = this.bus.getExtension(DomainExpressionBuilderRegistry.class);
            assert (null != debr);
            DomainExpression de = debr.build((Element)nd);
            des.add(de);
        }
        return des;
    }

    void setAttachments(Collection<PolicyAttachment> a) {
        this.attachments = a;
    }

    public Collection<PolicyAttachment> getAttachments() {
        return this.attachments;
    }
}

