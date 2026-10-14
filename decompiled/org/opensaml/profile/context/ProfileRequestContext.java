/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.InOutOperationContext
 */
package org.opensaml.profile.context;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.InOutOperationContext;

@ThreadSafe
public final class ProfileRequestContext<InboundMessageType, OutboundMessageType>
extends InOutOperationContext<InboundMessageType, OutboundMessageType> {
    public static final String BINDING_KEY = "opensamlProfileRequestContext";
    public static final String ANONYMOUS_PROFILE_ID = "anonymous";
    private String profileId = "anonymous";
    private String loggingId = "anonymous";
    private boolean browserProfile;

    @Nonnull
    @NotEmpty
    public String getProfileId() {
        return this.profileId;
    }

    public void setProfileId(@Nullable String id) {
        String trimmedId = StringSupport.trimOrNull(id);
        this.profileId = trimmedId == null ? ANONYMOUS_PROFILE_ID : trimmedId;
    }

    @Nonnull
    @NotEmpty
    public String getLoggingId() {
        return this.loggingId;
    }

    public void setLoggingId(@Nullable String id) {
        String trimmedId = StringSupport.trimOrNull(id);
        this.loggingId = trimmedId == null ? ANONYMOUS_PROFILE_ID : trimmedId;
    }

    public boolean isBrowserProfile() {
        return this.browserProfile;
    }

    public void setBrowserProfile(boolean browser) {
        this.browserProfile = browser;
    }
}

