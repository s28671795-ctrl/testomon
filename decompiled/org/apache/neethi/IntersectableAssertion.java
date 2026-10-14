/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi;

import org.apache.neethi.Assertion;

public interface IntersectableAssertion
extends Assertion {
    public boolean isCompatible(Assertion var1, boolean var2);

    public Assertion intersect(Assertion var1, boolean var2);
}

