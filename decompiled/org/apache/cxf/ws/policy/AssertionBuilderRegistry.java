/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import org.apache.neethi.AssertionBuilderFactory;

public interface AssertionBuilderRegistry
extends AssertionBuilderFactory {
    public boolean isIgnoreUnknownAssertions();

    public void setIgnoreUnknownAssertions(boolean var1);
}

