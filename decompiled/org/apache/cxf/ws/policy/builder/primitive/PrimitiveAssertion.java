/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.builder.primitive;

import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.neethi.builders.xml.XMLPrimitiveAssertionBuilder;
import org.w3c.dom.Element;

public class PrimitiveAssertion
extends org.apache.neethi.builders.PrimitiveAssertion {
    public PrimitiveAssertion() {
    }

    public PrimitiveAssertion(QName n) {
        super(n, false);
    }

    public PrimitiveAssertion(QName n, boolean o) {
        super(n, o);
    }

    public PrimitiveAssertion(QName n, boolean o, boolean i) {
        super(n, o, i);
    }

    public PrimitiveAssertion(QName n, boolean o, boolean i, Map<QName, String> atts) {
        super(n, o, i, atts);
    }

    public PrimitiveAssertion(Element element) {
        super(new QName(element.getNamespaceURI(), element.getLocalName()), XMLPrimitiveAssertionBuilder.isOptional(element), XMLPrimitiveAssertionBuilder.isIgnorable(element));
    }

    @Override
    protected Assertion clone(boolean opt) {
        if (opt == this.optional) {
            return this;
        }
        return new PrimitiveAssertion(this.name, opt, this.ignorable);
    }
}

