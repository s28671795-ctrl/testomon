/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import org.omg.CORBA.LocalObject;
import org.omg.CORBA.Policy;
import org.omg.PortableServer.RequestProcessingPolicy;
import org.omg.PortableServer.RequestProcessingPolicyValue;

public class RequestProcessingPolicyImpl
extends LocalObject
implements RequestProcessingPolicy {
    private static final long serialVersionUID = 5900928116128639434L;
    private RequestProcessingPolicyValue value;

    public RequestProcessingPolicyImpl(RequestProcessingPolicyValue value) {
        this.value = value;
    }

    @Override
    public RequestProcessingPolicyValue value() {
        return this.value;
    }

    @Override
    public int policy_type() {
        return 22;
    }

    @Override
    public Policy copy() {
        return new RequestProcessingPolicyImpl(this.value);
    }

    @Override
    public void destroy() {
        this.value = null;
    }

    public String toString() {
        String type = null;
        switch (this.value.value()) {
            case 0: {
                type = "USE_ACTIVE_OBJECT_MAP_ONLY";
                break;
            }
            case 1: {
                type = "USE_DEFAULT_SERVANT";
                break;
            }
            case 2: {
                type = "USE_SERVANT_MANAGER";
            }
        }
        return "RequestProcessingPolicy[" + type + "]";
    }
}

