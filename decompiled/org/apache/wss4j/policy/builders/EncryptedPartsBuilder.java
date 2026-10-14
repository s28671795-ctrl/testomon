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
import org.apache.wss4j.policy.builders.SignedPartsBuilder;
import org.apache.wss4j.policy.model.Attachments;
import org.apache.wss4j.policy.model.EncryptedParts;
import org.apache.wss4j.policy.model.Header;
import org.w3c.dom.Element;

public class EncryptedPartsBuilder
extends SignedPartsBuilder {
    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) throws IllegalArgumentException {
        SPConstants.SPVersion spVersion = SPConstants.SPVersion.getSPVersion(element.getNamespaceURI());
        boolean body2 = SPUtils.hasChildElementWithName(element, spVersion.getSPConstants().getBody());
        List<Header> headers = this.getHeaders(element, true, spVersion);
        Attachments attachments = this.getAttachments(element, spVersion);
        EncryptedParts encryptedParts = new EncryptedParts(spVersion, body2 |= !SPUtils.hasChildElements(element), attachments, headers);
        encryptedParts.setOptional(SPUtils.isOptional(element));
        encryptedParts.setIgnorable(SPUtils.isIgnorable(element));
        return encryptedParts;
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{SP13Constants.ENCRYPTED_PARTS, SP11Constants.ENCRYPTED_PARTS};
    }
}

