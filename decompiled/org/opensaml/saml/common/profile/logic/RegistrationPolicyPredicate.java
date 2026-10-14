/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.profile.logic;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.saml.common.profile.logic.AbstractRegistrationInfoPredicate;
import org.opensaml.saml.ext.saml2mdrpi.RegistrationInfo;
import org.opensaml.saml.ext.saml2mdrpi.RegistrationPolicy;

public class RegistrationPolicyPredicate
extends AbstractRegistrationInfoPredicate {
    @Nonnull
    @NonnullElements
    private final Set<String> policySet;

    public RegistrationPolicyPredicate(@Nonnull @NonnullElements Collection<String> policies) {
        Constraint.isNotNull(policies, "Authority name collection cannot be null");
        this.policySet = new HashSet<String>(policies.size());
        for (String policy : policies) {
            String trimmed = StringSupport.trimOrNull(policy);
            if (trimmed == null) continue;
            policies.add(trimmed);
        }
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public Set<String> getPolicies() {
        return ImmutableSet.copyOf(this.policySet);
    }

    @Override
    protected boolean doApply(@Nonnull RegistrationInfo info) {
        for (RegistrationPolicy policy : info.getRegistrationPolicies()) {
            if (!this.policySet.contains(policy.getValue())) continue;
            return true;
        }
        return false;
    }
}

