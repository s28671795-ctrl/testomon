/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.builder.primitive;

import java.util.Collection;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.policy.builder.primitive.PrimitiveAssertion;
import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.neethi.builders.AssertionBuilder;
import org.w3c.dom.Element;

public class PrimitiveAssertionBuilder
implements AssertionBuilder<Element> {
    private QName[] knownElements = new QName[0];

    public PrimitiveAssertionBuilder() {
    }

    public PrimitiveAssertionBuilder(Collection<QName> els) {
        this.knownElements = els.toArray(new QName[0]);
    }

    public PrimitiveAssertionBuilder(QName[] els) {
        this.knownElements = els;
    }

    @Override
    public Assertion build(Element element, AssertionBuilderFactory fact) {
        return new PrimitiveAssertion(element);
    }

    @Override
    public QName[] getKnownElements() {
        return this.knownElements;
    }

    public void setKnownElements(Collection<QName> k) {
        this.knownElements = k.toArray(new QName[0]);
    }

    public void setKnownElements(QName[] k) {
        this.knownElements = k;
    }
}

