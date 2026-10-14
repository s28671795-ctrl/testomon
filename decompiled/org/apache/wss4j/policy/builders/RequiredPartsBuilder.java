/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.builders;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.neethi.builders.AssertionBuilder;
import org.apache.wss4j.policy.SP13Constants;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.SPUtils;
import org.apache.wss4j.policy.model.Header;
import org.apache.wss4j.policy.model.RequiredParts;
import org.w3c.dom.Element;

public class RequiredPartsBuilder
implements AssertionBuilder<Element> {
    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) throws IllegalArgumentException {
        SPConstants.SPVersion spVersion = SPConstants.SPVersion.getSPVersion(element.getNamespaceURI());
        List<Header> headers = this.getHeaders(element, false, spVersion);
        RequiredParts requiredParts = new RequiredParts(spVersion, headers);
        requiredParts.setOptional(SPUtils.isOptional(element));
        requiredParts.setIgnorable(SPUtils.isIgnorable(element));
        return requiredParts;
    }

    protected List<Header> getHeaders(Element element, boolean ignoreNameElement, SPConstants.SPVersion spVersion) {
        ArrayList<Header> headers = new ArrayList<Header>();
        Element child = SPUtils.getFirstChildElement(element);
        while (child != null) {
            if ("Header".equals(child.getLocalName()) && spVersion.getNamespace().equals(child.getNamespaceURI())) {
                String headerNamespace;
                String headerName = child.getAttributeNS(null, "Name");
                if ("".equals(headerName)) {
                    if (ignoreNameElement) {
                        headerName = null;
                    } else {
                        throw new IllegalArgumentException("sp:" + element.getLocalName() + "/sp:" + child.getLocalName() + " must have a Name attribute");
                    }
                }
                if ((headerNamespace = child.getAttributeNS(null, "Namespace")) == null || "".equals(headerNamespace)) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                headers.add(new Header(headerName, headerNamespace));
            }
            child = SPUtils.getNextSiblingElement(child);
        }
        return headers;
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{SP13Constants.REQUIRED_PARTS};
    }
}

