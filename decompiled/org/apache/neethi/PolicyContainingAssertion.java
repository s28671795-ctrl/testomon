/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi;

import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;

public interface PolicyContainingAssertion
extends Assertion {
    public Policy getPolicy();
}

