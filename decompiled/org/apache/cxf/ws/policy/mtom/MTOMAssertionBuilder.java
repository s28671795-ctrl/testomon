/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.mtom;

import javax.xml.namespace.QName;
import org.apache.cxf.ws.policy.PolicyConstants;
import org.apache.cxf.ws.policy.builder.primitive.PrimitiveAssertion;
import org.apache.cxf.ws.policy.mtom.MetadataConstants;
import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.neethi.builders.AssertionBuilder;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class MTOMAssertionBuilder
implements AssertionBuilder<Element> {
    private static final QName[] KNOWN_ELEMENTS = new QName[]{MetadataConstants.MTOM_ASSERTION_QNAME};

    @Override
    public Assertion build(Element elem, AssertionBuilderFactory f) {
        String localName = elem.getLocalName();
        QName qn = new QName(elem.getNamespaceURI(), localName);
        boolean optional = false;
        Attr attribute = PolicyConstants.findOptionalAttribute(elem);
        if (attribute != null) {
            optional = Boolean.valueOf(attribute.getValue());
        }
        if (MetadataConstants.MTOM_ASSERTION_QNAME.equals(qn)) {
            return new PrimitiveAssertion(MetadataConstants.MTOM_ASSERTION_QNAME, optional);
        }
        return null;
    }

    @Override
    public QName[] getKnownElements() {
        return KNOWN_ELEMENTS;
    }
}

