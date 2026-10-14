/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.attachment.reference;

import java.util.List;
import javax.wsdl.extensions.UnknownExtensibilityElement;
import org.apache.cxf.service.model.DescriptionInfo;
import org.apache.cxf.ws.policy.PolicyBuilder;
import org.apache.cxf.ws.policy.attachment.reference.ReferenceResolver;
import org.apache.neethi.Constants;
import org.apache.neethi.Policy;

public class LocalServiceModelReferenceResolver
implements ReferenceResolver {
    private DescriptionInfo descriptionInfo;
    private PolicyBuilder builder;

    public LocalServiceModelReferenceResolver(DescriptionInfo d, PolicyBuilder b) {
        this.descriptionInfo = d;
        this.builder = b;
    }

    @Override
    public Policy resolveReference(String uri) {
        List<UnknownExtensibilityElement> extensions = this.descriptionInfo.getExtensors(UnknownExtensibilityElement.class);
        if (extensions != null) {
            for (UnknownExtensibilityElement e : extensions) {
                if (!Constants.isPolicyElement(e.getElementType()) || !uri.equals(e.getElement().getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id"))) continue;
                return this.builder.getPolicy(e.getElement());
            }
        }
        return null;
    }
}

