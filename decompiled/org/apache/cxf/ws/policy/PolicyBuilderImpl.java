/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.extension.BusExtension;
import org.apache.cxf.ws.policy.AssertionBuilderRegistry;
import org.apache.cxf.ws.policy.PolicyBuilder;
import org.apache.cxf.ws.policy.PolicyEngine;

@NoJSR250Annotations
public class PolicyBuilderImpl
extends org.apache.neethi.PolicyBuilder
implements PolicyBuilder,
BusExtension {
    private Bus bus;

    public PolicyBuilderImpl() {
    }

    public PolicyBuilderImpl(Bus theBus) {
        super(null);
        this.setBus(theBus);
    }

    @Override
    public Class<?> getRegistrationType() {
        return PolicyBuilder.class;
    }

    public void setAssertionBuilderRegistry(AssertionBuilderRegistry reg) {
        this.factory = reg;
    }

    public final void setBus(Bus theBus) {
        this.bus = theBus;
        if (this.bus != null) {
            PolicyEngine e;
            theBus.setExtension(this, PolicyBuilder.class);
            AssertionBuilderRegistry reg = theBus.getExtension(AssertionBuilderRegistry.class);
            if (reg != null) {
                this.factory = reg;
            }
            if ((e = this.bus.getExtension(PolicyEngine.class)) != null) {
                this.setPolicyRegistry(e.getRegistry());
            }
        }
    }

    public Bus getBus() {
        return this.bus;
    }
}

