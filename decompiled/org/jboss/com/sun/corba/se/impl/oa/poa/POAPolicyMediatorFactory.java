/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import org.jboss.com.sun.corba.se.impl.oa.poa.POAImpl;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAPolicyMediator;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAPolicyMediatorImpl_NR_UDS;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAPolicyMediatorImpl_NR_USM;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAPolicyMediatorImpl_R_AOM;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAPolicyMediatorImpl_R_UDS;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAPolicyMediatorImpl_R_USM;
import org.jboss.com.sun.corba.se.impl.oa.poa.Policies;

abstract class POAPolicyMediatorFactory {
    POAPolicyMediatorFactory() {
    }

    static POAPolicyMediator create(Policies policies, POAImpl poa) {
        if (policies.retainServants()) {
            if (policies.useActiveMapOnly()) {
                return new POAPolicyMediatorImpl_R_AOM(policies, poa);
            }
            if (policies.useDefaultServant()) {
                return new POAPolicyMediatorImpl_R_UDS(policies, poa);
            }
            if (policies.useServantManager()) {
                return new POAPolicyMediatorImpl_R_USM(policies, poa);
            }
            throw poa.invocationWrapper().pmfCreateRetain();
        }
        if (policies.useDefaultServant()) {
            return new POAPolicyMediatorImpl_NR_UDS(policies, poa);
        }
        if (policies.useServantManager()) {
            return new POAPolicyMediatorImpl_NR_USM(policies, poa);
        }
        throw poa.invocationWrapper().pmfCreateNonRetain();
    }
}

