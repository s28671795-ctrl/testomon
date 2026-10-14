/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.builders;

import java.util.List;
import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.wss4j.policy.SP13Constants;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.SPUtils;
import org.apache.wss4j.policy.builders.EncryptedElementsBuilder;
import org.apache.wss4j.policy.model.ContentEncryptedElements;
import org.apache.wss4j.policy.model.XPath;
import org.w3c.dom.Element;

public class ContentEncryptedElementsBuilder
extends EncryptedElementsBuilder {
    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) throws IllegalArgumentException {
        SPConstants.SPVersion spVersion = SPConstants.SPVersion.getSPVersion(element.getNamespaceURI());
        String xPathVersion = this.getXPathVersion(element);
        List<XPath> xPaths = this.getXPathExpressions(element, spVersion);
        ContentEncryptedElements contentEncryptedElements = new ContentEncryptedElements(spVersion, xPathVersion, xPaths);
        contentEncryptedElements.setOptional(SPUtils.isOptional(element));
        contentEncryptedElements.setIgnorable(SPUtils.isIgnorable(element));
        return contentEncryptedElements;
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{SP13Constants.CONTENT_ENCRYPTED_ELEMENTS};
    }
}

