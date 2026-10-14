/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import org.omg.CORBA.LocalObject;
import org.omg.CORBA.Policy;
import org.omg.PortableServer.ImplicitActivationPolicy;
import org.omg.PortableServer.ImplicitActivationPolicyValue;

final class ImplicitActivationPolicyImpl
extends LocalObject
implements ImplicitActivationPolicy {
    private static final long serialVersionUID = 3189145753312485561L;
    private ImplicitActivationPolicyValue value;

    public ImplicitActivationPolicyImpl(ImplicitActivationPolicyValue value) {
        this.value = value;
    }

    @Override
    public ImplicitActivationPolicyValue value() {
        return this.value;
    }

    @Override
    public int policy_type() {
        return 20;
    }

    @Override
    public Policy copy() {
        return new ImplicitActivationPolicyImpl(this.value);
    }

    @Override
    public void destroy() {
        this.value = null;
    }

    public String toString() {
        return "ImplicitActivationPolicy[" + (this.value.value() == 0 ? "IMPLICIT_ACTIVATION" : "NO_IMPLICIT_ACTIVATION]");
    }
}

