/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.neethi.Assertion;

public interface PolicyAssertion
extends Assertion {
    public boolean isAsserted(AssertionInfoMap var1);
}

