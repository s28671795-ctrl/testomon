/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec;

import java.util.Collection;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;

public interface WhitelistBlacklistConfiguration {
    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public Collection<String> getWhitelistedAlgorithms();

    public boolean isWhitelistMerge();

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public Collection<String> getBlacklistedAlgorithms();

    public boolean isBlacklistMerge();

    @Nonnull
    public Precedence getWhitelistBlacklistPrecedence();

    public static enum Precedence {
        WHITELIST,
        BLACKLIST;

    }
}

