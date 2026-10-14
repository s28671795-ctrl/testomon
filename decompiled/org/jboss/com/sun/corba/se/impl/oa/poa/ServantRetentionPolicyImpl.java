/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import org.omg.CORBA.LocalObject;
import org.omg.CORBA.Policy;
import org.omg.PortableServer.ServantRetentionPolicy;
import org.omg.PortableServer.ServantRetentionPolicyValue;

final class ServantRetentionPolicyImpl
extends LocalObject
implements ServantRetentionPolicy {
    private static final long serialVersionUID = 7744962424711138173L;
    private ServantRetentionPolicyValue value;

    public ServantRetentionPolicyImpl(ServantRetentionPolicyValue value) {
        this.value = value;
    }

    @Override
    public ServantRetentionPolicyValue value() {
        return this.value;
    }

    @Override
    public int policy_type() {
        return 21;
    }

    @Override
    public Policy copy() {
        return new ServantRetentionPolicyImpl(this.value);
    }

    @Override
    public void destroy() {
        this.value = null;
    }

    public String toString() {
        return "ServantRetentionPolicy[" + (this.value.value() == 0 ? "RETAIN" : "NON_RETAIN]");
    }
}

