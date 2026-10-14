/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.extension;

import org.omg.CORBA.LocalObject;
import org.omg.CORBA.Policy;

public class ZeroPortPolicy
extends LocalObject
implements Policy {
    private static ZeroPortPolicy policy = new ZeroPortPolicy(true);
    private boolean flag = true;

    private ZeroPortPolicy(boolean type) {
        this.flag = type;
    }

    public String toString() {
        return "ZeroPortPolicy[" + this.flag + "]";
    }

    public boolean forceZeroPort() {
        return this.flag;
    }

    public static synchronized ZeroPortPolicy getPolicy() {
        return policy;
    }

    @Override
    public int policy_type() {
        return 1398079489;
    }

    @Override
    public Policy copy() {
        return this;
    }

    @Override
    public void destroy() {
    }
}

