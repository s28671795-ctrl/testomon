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
import org.apache.wss4j.policy.builders.SignedElementsBuilder;
import org.apache.wss4j.policy.model.EncryptedElements;
import org.apache.wss4j.policy.model.XPath;
import org.w3c.dom.Element;

public class EncryptedElementsBuilder
extends SignedElementsBuilder {
    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) throws IllegalArgumentException {
        SPConstants.SPVersion spVersion = SPConstants.SPVersion.getSPVersion(element.getNamespaceURI());
        String xPathVersion = this.getXPathVersion(element);
        List<XPath> xPaths = this.getXPathExpressions(element, spVersion);
        EncryptedElements encryptedElements = new EncryptedElements(spVersion, xPathVersion, xPaths);
        encryptedElements.setOptional(SPUtils.isOptional(element));
        encryptedElements.setIgnorable(SPUtils.isIgnorable(element));
        return encryptedElements;
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{SP13Constants.ENCRYPTED_ELEMENTS, SP11Constants.ENCRYPTED_ELEMENTS};
    }
}

