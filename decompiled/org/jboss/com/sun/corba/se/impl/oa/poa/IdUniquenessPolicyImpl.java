/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import org.omg.CORBA.LocalObject;
import org.omg.CORBA.Policy;
import org.omg.PortableServer.IdUniquenessPolicy;
import org.omg.PortableServer.IdUniquenessPolicyValue;

final class IdUniquenessPolicyImpl
extends LocalObject
implements IdUniquenessPolicy {
    private static final long serialVersionUID = -2373453208253083393L;
    private IdUniquenessPolicyValue value;

    public IdUniquenessPolicyImpl(IdUniquenessPolicyValue value) {
        this.value = value;
    }

    @Override
    public IdUniquenessPolicyValue value() {
        return this.value;
    }

    @Override
    public int policy_type() {
        return 18;
    }

    @Override
    public Policy copy() {
        return new IdUniquenessPolicyImpl(this.value);
    }

    @Override
    public void destroy() {
        this.value = null;
    }

    public String toString() {
        return "IdUniquenessPolicy[" + (this.value.value() == 0 ? "UNIQUE_ID" : "MULTIPLE_ID]");
    }
}

