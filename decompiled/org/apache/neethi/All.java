/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi;

import java.util.List;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.AbstractPolicyOperator;
import org.apache.neethi.Assertion;
import org.apache.neethi.Constants;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyOperator;

public class All
extends AbstractPolicyOperator {
    public All() {
    }

    public All(PolicyOperator parent) {
        super(parent);
    }

    public void addAssertion(Assertion assertion) {
        this.addPolicyComponent(assertion);
    }

    public List<PolicyComponent> getAssertions() {
        return this.policyComponents;
    }

    @Override
    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        String namespace = Constants.findPolicyNamespace(writer);
        String prefix = writer.getPrefix(namespace);
        if (prefix == null) {
            writer.writeStartElement("wsp", "All", namespace);
            writer.writeNamespace("wsp", namespace);
            writer.setPrefix("wsp", namespace);
        } else {
            writer.writeStartElement(namespace, "All");
        }
        for (PolicyComponent policyComponent : this.getPolicyComponents()) {
            policyComponent.serialize(writer);
        }
        writer.writeEndElement();
    }

    @Override
    public short getType() {
        return 3;
    }
}

