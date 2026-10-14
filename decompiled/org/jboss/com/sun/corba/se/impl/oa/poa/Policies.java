/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import org.jboss.com.sun.corba.se.spi.extension.CopyObjectPolicy;
import org.jboss.com.sun.corba.se.spi.extension.ServantCachingPolicy;
import org.jboss.com.sun.corba.se.spi.extension.ZeroPortPolicy;
import org.omg.CORBA.Policy;
import org.omg.PortableServer.IdAssignmentPolicy;
import org.omg.PortableServer.IdUniquenessPolicy;
import org.omg.PortableServer.ImplicitActivationPolicy;
import org.omg.PortableServer.LifespanPolicy;
import org.omg.PortableServer.POAPackage.InvalidPolicy;
import org.omg.PortableServer.RequestProcessingPolicy;
import org.omg.PortableServer.ServantRetentionPolicy;
import org.omg.PortableServer.ThreadPolicy;

public final class Policies {
    private static final int MIN_POA_POLICY_ID = 16;
    int defaultObjectCopierFactoryId;
    private HashMap<Integer, Policy> policyMap = new HashMap();
    public static final Policies defaultPolicies = new Policies();
    public static final Policies rootPOAPolicies = new Policies(0, 0, 0, 1, 0, 0, 0);
    private int[] poaPolicyValues;

    private int getPolicyValue(int id) {
        return this.poaPolicyValues[id - 16];
    }

    private void setPolicyValue(int id, int value) {
        this.poaPolicyValues[id - 16] = value;
    }

    private Policies(int threadModel, int lifespan, int idUniqueness, int idAssignment, int implicitActivation, int retention, int requestProcessing) {
        this.poaPolicyValues = new int[]{threadModel, lifespan, idUniqueness, idAssignment, implicitActivation, retention, requestProcessing};
    }

    private Policies() {
        this(0, 0, 0, 1, 1, 0, 0);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Policies[");
        boolean first = true;
        Iterator<Policy> iter = this.policyMap.values().iterator();
        while (iter.hasNext()) {
            if (first) {
                first = false;
            } else {
                buffer.append(",");
            }
            buffer.append(iter.next().toString());
        }
        buffer.append("]");
        return buffer.toString();
    }

    private int getPOAPolicyValue(Policy policy) {
        if (policy instanceof ThreadPolicy) {
            return ((ThreadPolicy)policy).value().value();
        }
        if (policy instanceof LifespanPolicy) {
            return ((LifespanPolicy)policy).value().value();
        }
        if (policy instanceof IdUniquenessPolicy) {
            return ((IdUniquenessPolicy)policy).value().value();
        }
        if (policy instanceof IdAssignmentPolicy) {
            return ((IdAssignmentPolicy)policy).value().value();
        }
        if (policy instanceof ServantRetentionPolicy) {
            return ((ServantRetentionPolicy)policy).value().value();
        }
        if (policy instanceof RequestProcessingPolicy) {
            return ((RequestProcessingPolicy)policy).value().value();
        }
        if (policy instanceof ImplicitActivationPolicy) {
            return ((ImplicitActivationPolicy)policy).value().value();
        }
        return -1;
    }

    private void checkForPolicyError(BitSet errorSet) throws InvalidPolicy {
        for (short ctr = 0; ctr < errorSet.length(); ctr = (short)(ctr + 1)) {
            if (!errorSet.get(ctr)) continue;
            throw new InvalidPolicy(ctr);
        }
    }

    private void addToErrorSet(Policy[] policies, int policyId, BitSet errorSet) {
        for (int ctr = 0; ctr < policies.length; ++ctr) {
            if (policies[ctr].policy_type() != policyId) continue;
            errorSet.set(ctr);
            return;
        }
    }

    Policies(Policy[] policies, int id) throws InvalidPolicy {
        this();
        this.defaultObjectCopierFactoryId = id;
        if (policies == null) {
            return;
        }
        BitSet errorSet = new BitSet(policies.length);
        for (int i = 0; i < policies.length; i = (int)((short)(i + 1))) {
            Policy policy = policies[i];
            int POAPolicyValue = this.getPOAPolicyValue(policy);
            Integer key = new Integer(policy.policy_type());
            Policy prev = this.policyMap.get(key);
            if (prev == null) {
                this.policyMap.put(key, policy);
            }
            if (POAPolicyValue < 0) continue;
            this.setPolicyValue(key, POAPolicyValue);
            if (prev == null || this.getPOAPolicyValue(prev) == POAPolicyValue) continue;
            errorSet.set(i);
        }
        if (!this.retainServants() && this.useActiveMapOnly()) {
            this.addToErrorSet(policies, 21, errorSet);
            this.addToErrorSet(policies, 22, errorSet);
        }
        if (this.isImplicitlyActivated()) {
            if (!this.retainServants()) {
                this.addToErrorSet(policies, 20, errorSet);
                this.addToErrorSet(policies, 21, errorSet);
            }
            if (!this.isSystemAssignedIds()) {
                this.addToErrorSet(policies, 20, errorSet);
                this.addToErrorSet(policies, 19, errorSet);
            }
        }
        this.checkForPolicyError(errorSet);
    }

    public Policy get_effective_policy(int type) {
        Integer key = new Integer(type);
        Policy result = this.policyMap.get(key);
        return result;
    }

    public final boolean isOrbControlledThreads() {
        return this.getPolicyValue(16) == 0;
    }

    public final boolean isSingleThreaded() {
        return this.getPolicyValue(16) == 1;
    }

    public final boolean isTransient() {
        return this.getPolicyValue(17) == 0;
    }

    public final boolean isPersistent() {
        return this.getPolicyValue(17) == 1;
    }

    public final boolean isUniqueIds() {
        return this.getPolicyValue(18) == 0;
    }

    public final boolean isMultipleIds() {
        return this.getPolicyValue(18) == 1;
    }

    public final boolean isUserAssignedIds() {
        return this.getPolicyValue(19) == 0;
    }

    public final boolean isSystemAssignedIds() {
        return this.getPolicyValue(19) == 1;
    }

    public final boolean retainServants() {
        return this.getPolicyValue(21) == 0;
    }

    public final boolean useActiveMapOnly() {
        return this.getPolicyValue(22) == 0;
    }

    public final boolean useDefaultServant() {
        return this.getPolicyValue(22) == 1;
    }

    public final boolean useServantManager() {
        return this.getPolicyValue(22) == 2;
    }

    public final boolean isImplicitlyActivated() {
        return this.getPolicyValue(20) == 0;
    }

    public final int servantCachingLevel() {
        Integer key = new Integer(0x53550000);
        ServantCachingPolicy policy = (ServantCachingPolicy)this.policyMap.get(key);
        if (policy == null) {
            return 0;
        }
        return policy.getType();
    }

    public final boolean forceZeroPort() {
        Integer key = new Integer(1398079489);
        ZeroPortPolicy policy = (ZeroPortPolicy)this.policyMap.get(key);
        if (policy == null) {
            return false;
        }
        return policy.forceZeroPort();
    }

    public final int getCopierId() {
        Integer key = new Integer(1398079490);
        CopyObjectPolicy policy = (CopyObjectPolicy)this.policyMap.get(key);
        if (policy != null) {
            return policy.getValue();
        }
        return this.defaultObjectCopierFactoryId;
    }
}

