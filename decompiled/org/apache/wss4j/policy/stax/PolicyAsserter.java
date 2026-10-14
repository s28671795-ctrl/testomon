/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax;

import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;

public interface PolicyAsserter {
    public void assertPolicy(Assertion var1);

    public void unassertPolicy(Assertion var1, String var2);

    public void assertPolicy(QName var1);

    public void unassertPolicy(QName var1, String var2);
}

