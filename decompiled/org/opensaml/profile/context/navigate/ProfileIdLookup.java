/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.ContextDataLookupFunction
 */
package org.opensaml.profile.context.navigate;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.navigate.ContextDataLookupFunction;
import org.opensaml.profile.context.ProfileRequestContext;

public class ProfileIdLookup
implements ContextDataLookupFunction<ProfileRequestContext, String> {
    @Nullable
    public String apply(@Nullable ProfileRequestContext input) {
        if (input != null) {
            return input.getProfileId();
        }
        return null;
    }
}

