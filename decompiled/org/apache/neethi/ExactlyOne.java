/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.AbstractPolicyOperator;
import org.apache.neethi.Constants;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyOperator;

public class ExactlyOne
extends AbstractPolicyOperator {
    public ExactlyOne() {
    }

    public ExactlyOne(PolicyOperator parent) {
        super(parent);
    }

    @Override
    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        String namespace = Constants.findPolicyNamespace(writer);
        String prefix = writer.getPrefix(namespace);
        if (prefix == null) {
            writer.writeStartElement("wsp", "ExactlyOne", namespace);
            writer.writeNamespace("wsp", namespace);
            writer.setPrefix("wsp", namespace);
        } else {
            writer.writeStartElement(namespace, "ExactlyOne");
        }
        for (PolicyComponent policyComponent : this.getPolicyComponents()) {
            policyComponent.serialize(writer);
        }
        writer.writeEndElement();
    }

    @Override
    public final short getType() {
        return 2;
    }
}

