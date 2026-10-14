/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.util.Collection;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.neethi.Assertion;
import org.apache.wss4j.policy.stax.PolicyAsserter;

public class WSS4JPolicyAsserter
implements PolicyAsserter {
    private AssertionInfoMap aim;

    public WSS4JPolicyAsserter(AssertionInfoMap aim) {
        this.aim = aim;
    }

    @Override
    public void assertPolicy(Assertion assertion) {
        Collection<AssertionInfo> ais = this.aim.getAssertionInfo(assertion.getName());
        if (ais != null && !ais.isEmpty()) {
            for (AssertionInfo ai : ais) {
                if (ai.getAssertion() != assertion) continue;
                ai.setAsserted(true);
            }
        }
    }

    @Override
    public void assertPolicy(QName qName) {
        Collection<AssertionInfo> ais = this.aim.getAssertionInfo(qName);
        if (ais != null && !ais.isEmpty()) {
            for (AssertionInfo ai : ais) {
                ai.setAsserted(true);
            }
        }
    }

    @Override
    public void unassertPolicy(Assertion assertion, String reason) {
        Collection<AssertionInfo> ais = this.aim.getAssertionInfo(assertion.getName());
        if (ais != null && !ais.isEmpty()) {
            for (AssertionInfo ai : ais) {
                if (ai.getAssertion() != assertion) continue;
                ai.setNotAsserted(reason);
            }
        }
    }

    @Override
    public void unassertPolicy(QName qName, String reason) {
        Collection<AssertionInfo> ais = this.aim.getAssertionInfo(qName);
        if (ais != null && !ais.isEmpty()) {
            for (AssertionInfo ai : ais) {
                ai.setNotAsserted(reason);
            }
        }
    }
}

