/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.profile.logic;

import com.google.common.base.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.profile.context.ProfileRequestContext;

public class ProfileIdPredicate
implements Predicate<ProfileRequestContext> {
    @Nonnull
    @NotEmpty
    private final String profileId;

    public ProfileIdPredicate(@Nonnull @ParameterName(name="id") @NotEmpty String id) {
        this.profileId = Constraint.isNotNull(StringSupport.trimOrNull(id), "Profile ID cannot be null or empty");
    }

    @Override
    public boolean apply(@Nullable ProfileRequestContext input) {
        return input != null && this.profileId.equals(input.getProfileId());
    }
}

