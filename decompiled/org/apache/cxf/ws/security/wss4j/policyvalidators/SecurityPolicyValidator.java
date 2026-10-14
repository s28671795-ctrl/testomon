/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import java.util.Collection;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;

public interface SecurityPolicyValidator {
    public boolean canValidatePolicy(AssertionInfo var1);

    public void validatePolicies(PolicyValidatorParameters var1, Collection<AssertionInfo> var2);
}

