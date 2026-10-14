/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Policy;

public interface PolicyOperations {
    public int policy_type();

    public Policy copy();

    public void destroy();
}

