/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.builders;

import java.util.List;
import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP13Constants;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.SPUtils;
import org.apache.wss4j.policy.builders.RequiredPartsBuilder;
import org.apache.wss4j.policy.model.Attachments;
import org.apache.wss4j.policy.model.Header;
import org.apache.wss4j.policy.model.SignedParts;
import org.w3c.dom.Element;

public class SignedPartsBuilder
extends RequiredPartsBuilder {
    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) throws IllegalArgumentException {
        SPConstants.SPVersion spVersion = SPConstants.SPVersion.getSPVersion(element.getNamespaceURI());
        boolean body2 = SPUtils.hasChildElementWithName(element, spVersion.getSPConstants().getBody());
        List<Header> headers = this.getHeaders(element, true, spVersion);
        Attachments attachments = this.getAttachments(element, spVersion);
        boolean signAllHeaders = !SPUtils.hasChildElements(element);
        SignedParts signedParts = new SignedParts(spVersion, body2 |= signAllHeaders, attachments, headers, signAllHeaders);
        signedParts.setOptional(SPUtils.isOptional(element));
        signedParts.setIgnorable(SPUtils.isIgnorable(element));
        return signedParts;
    }

    protected Attachments getAttachments(Element element, SPConstants.SPVersion spVersion) {
        Element child = SPUtils.getFirstChildElement(element);
        while (child != null) {
            if (spVersion.getSPConstants().getAttachments().getLocalPart().equals(child.getLocalName()) && spVersion.getSPConstants().getAttachments().getNamespaceURI().equals(child.getNamespaceURI())) {
                boolean contentSignatureTransform = SPUtils.hasChildElementWithName(child, spVersion.getSPConstants().getContentSignatureTransform());
                QName completeSigTransform = spVersion.getSPConstants().getAttachmentCompleteSignatureTransform();
                boolean attachmentCompleteSignatureTransform = SPUtils.hasChildElementWithName(child, completeSigTransform);
                return new Attachments(spVersion, contentSignatureTransform, attachmentCompleteSignatureTransform);
            }
            child = SPUtils.getNextSiblingElement(child);
        }
        return null;
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{SP13Constants.SIGNED_PARTS, SP11Constants.SIGNED_PARTS};
    }
}

