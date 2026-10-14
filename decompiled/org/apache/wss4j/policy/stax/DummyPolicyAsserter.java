/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax;

import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.wss4j.policy.stax.PolicyAsserter;

public class DummyPolicyAsserter
implements PolicyAsserter {
    @Override
    public void assertPolicy(Assertion assertion) {
    }

    @Override
    public void unassertPolicy(Assertion assertion, String reason) {
    }

    @Override
    public void assertPolicy(QName qName) {
    }

    @Override
    public void unassertPolicy(QName qName, String reason) {
    }
}

