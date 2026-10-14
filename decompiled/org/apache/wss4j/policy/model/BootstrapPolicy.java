/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;

public class BootstrapPolicy
extends AbstractSecurityAssertion {
    private final Policy nestedPolicy;

    public BootstrapPolicy(SPConstants.SPVersion version, Policy nestedPolicy) {
        super(version);
        this.nestedPolicy = nestedPolicy;
    }

    @Override
    public QName getName() {
        return super.getVersion().getSPConstants().getBootstrapPolicy();
    }

    public Policy getPolicy() {
        return this.nestedPolicy;
    }

    @Override
    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        super.serialize(writer, this.nestedPolicy);
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return this;
    }
}

