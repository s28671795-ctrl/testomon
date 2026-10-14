/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.profile.logic;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import org.opensaml.profile.context.ProfileRequestContext;

public class BrowserProfilePredicate
implements Predicate<ProfileRequestContext> {
    @Override
    public boolean apply(@Nullable ProfileRequestContext input) {
        return input != null && input.isBrowserProfile();
    }
}

