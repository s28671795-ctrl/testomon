/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import org.omg.CORBA.LocalObject;
import org.omg.CORBA.Policy;
import org.omg.PortableServer.LifespanPolicy;
import org.omg.PortableServer.LifespanPolicyValue;

final class LifespanPolicyImpl
extends LocalObject
implements LifespanPolicy {
    private static final long serialVersionUID = -7620425659409055717L;
    private LifespanPolicyValue value;

    public LifespanPolicyImpl(LifespanPolicyValue value) {
        this.value = value;
    }

    @Override
    public LifespanPolicyValue value() {
        return this.value;
    }

    @Override
    public int policy_type() {
        return 17;
    }

    @Override
    public Policy copy() {
        return new LifespanPolicyImpl(this.value);
    }

    @Override
    public void destroy() {
        this.value = null;
    }

    public String toString() {
        return "LifespanPolicy[" + (this.value.value() == 0 ? "TRANSIENT" : "PERSISTENT]");
    }
}

