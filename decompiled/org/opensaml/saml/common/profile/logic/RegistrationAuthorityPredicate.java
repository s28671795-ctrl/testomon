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

public class RegistrationAuthorityPredicate
extends AbstractRegistrationInfoPredicate {
    @Nonnull
    @NonnullElements
    private final Set<String> authorities;

    public RegistrationAuthorityPredicate(@Nonnull @NonnullElements Collection<String> names) {
        Constraint.isNotNull(names, "Authority name collection cannot be null");
        this.authorities = new HashSet<String>(names.size());
        for (String name : names) {
            String trimmed = StringSupport.trimOrNull(name);
            if (trimmed == null) continue;
            this.authorities.add(trimmed);
        }
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public Set<String> getAuthorities() {
        return ImmutableSet.copyOf(this.authorities);
    }

    @Override
    protected boolean doApply(@Nonnull RegistrationInfo info) {
        return this.authorities.contains(info.getRegistrationAuthority());
    }
}

