/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import org.omg.CORBA.LocalObject;
import org.omg.CORBA.Policy;
import org.omg.PortableServer.ThreadPolicy;
import org.omg.PortableServer.ThreadPolicyValue;

final class ThreadPolicyImpl
extends LocalObject
implements ThreadPolicy {
    private static final long serialVersionUID = -1130695147546073341L;
    private ThreadPolicyValue value;

    public ThreadPolicyImpl(ThreadPolicyValue value) {
        this.value = value;
    }

    @Override
    public ThreadPolicyValue value() {
        return this.value;
    }

    @Override
    public int policy_type() {
        return 16;
    }

    @Override
    public Policy copy() {
        return new ThreadPolicyImpl(this.value);
    }

    @Override
    public void destroy() {
        this.value = null;
    }

    public String toString() {
        return "ThreadPolicy[" + (this.value.value() == 1 ? "SINGLE_THREAD_MODEL" : "ORB_CTRL_MODEL]");
    }
}

