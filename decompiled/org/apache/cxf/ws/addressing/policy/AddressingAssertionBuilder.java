/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.policy;

import java.util.Map;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.ws.addressing.policy.MetadataConstants;
import org.apache.cxf.ws.policy.PolicyConstants;
import org.apache.cxf.ws.policy.builder.primitive.PrimitiveAssertion;
import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.neethi.Policy;
import org.apache.neethi.builders.AssertionBuilder;
import org.apache.neethi.builders.PolicyContainingPrimitiveAssertion;
import org.apache.neethi.builders.xml.XMLPrimitiveAssertionBuilder;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class AddressingAssertionBuilder
implements AssertionBuilder<Element> {
    private static final Logger LOG = LogUtils.getL7dLogger(AddressingAssertionBuilder.class);
    private static final QName[] KNOWN_ELEMENTS = new QName[]{MetadataConstants.ADDRESSING_ASSERTION_QNAME, MetadataConstants.ANON_RESPONSES_ASSERTION_QNAME, MetadataConstants.NON_ANON_RESPONSES_ASSERTION_QNAME, MetadataConstants.ADDRESSING_ASSERTION_QNAME_0705, MetadataConstants.ANON_RESPONSES_ASSERTION_QNAME_0705, MetadataConstants.NON_ANON_RESPONSES_ASSERTION_QNAME_0705};

    @Override
    public Assertion build(Element elem, AssertionBuilderFactory factory2) {
        String localName = elem.getLocalName();
        QName qn = new QName(elem.getNamespaceURI(), localName);
        boolean optional = false;
        Attr attribute = PolicyConstants.findOptionalAttribute(elem);
        if (attribute != null) {
            optional = Boolean.valueOf(attribute.getValue());
        }
        if (MetadataConstants.ADDRESSING_ASSERTION_QNAME.equals(qn) || MetadataConstants.ADDRESSING_ASSERTION_QNAME_0705.equals(qn)) {
            Assertion nap = new XMLPrimitiveAssertionBuilder(){

                @Override
                public Assertion newPrimitiveAssertion(Element element, Map<QName, String> mp) {
                    return new PrimitiveAssertion(MetadataConstants.ADDRESSING_ASSERTION_QNAME, 1.isOptional(element), 1.isIgnorable(element), mp);
                }

                @Override
                public Assertion newPolicyContainingAssertion(Element element, Map<QName, String> mp, Policy policy) {
                    return new PolicyContainingPrimitiveAssertion(MetadataConstants.ADDRESSING_ASSERTION_QNAME, 1.isOptional(element), 1.isIgnorable(element), mp, policy);
                }
            }.build(elem, factory2);
            if (!(nap instanceof PolicyContainingPrimitiveAssertion) && !(nap instanceof org.apache.neethi.builders.PrimitiveAssertion)) {
                LOG.warning("Unable to recognize the addressing policy");
            }
            return nap;
        }
        if (MetadataConstants.ANON_RESPONSES_ASSERTION_QNAME.equals(qn) || MetadataConstants.ANON_RESPONSES_ASSERTION_QNAME_0705.equals(qn)) {
            return new PrimitiveAssertion(MetadataConstants.ANON_RESPONSES_ASSERTION_QNAME, optional);
        }
        if (MetadataConstants.NON_ANON_RESPONSES_ASSERTION_QNAME.getLocalPart().equals(localName) || MetadataConstants.NON_ANON_RESPONSES_ASSERTION_QNAME_0705.getLocalPart().equals(localName)) {
            return new PrimitiveAssertion(MetadataConstants.NON_ANON_RESPONSES_ASSERTION_QNAME, optional);
        }
        return null;
    }

    @Override
    public QName[] getKnownElements() {
        return KNOWN_ELEMENTS;
    }
}

