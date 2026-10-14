/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.impl;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.xmlsec.WhitelistBlacklistConfiguration;

public class BasicWhitelistBlacklistConfiguration
implements WhitelistBlacklistConfiguration {
    public static final WhitelistBlacklistConfiguration.Precedence DEFAULT_PRECEDENCE = WhitelistBlacklistConfiguration.Precedence.WHITELIST;
    private Collection<String> whitelist = Collections.emptySet();
    private boolean whitelistMerge = false;
    private Collection<String> blacklist = Collections.emptySet();
    private boolean blacklistMerge = true;
    private WhitelistBlacklistConfiguration.Precedence precedence = DEFAULT_PRECEDENCE;

    @Override
    @Nonnull
    @NonnullElements
    @NotLive
    @Unmodifiable
    public Collection<String> getWhitelistedAlgorithms() {
        return ImmutableSet.copyOf(this.whitelist);
    }

    public void setWhitelistedAlgorithms(@Nullable Collection<String> uris) {
        if (uris == null) {
            this.whitelist = Collections.emptySet();
            return;
        }
        this.whitelist = new HashSet<String>(StringSupport.normalizeStringCollection(uris));
    }

    @Override
    public boolean isWhitelistMerge() {
        return this.whitelistMerge;
    }

    public void setWhitelistMerge(boolean flag) {
        this.whitelistMerge = flag;
    }

    @Override
    @Nonnull
    @NonnullElements
    @NotLive
    @Unmodifiable
    public Collection<String> getBlacklistedAlgorithms() {
        return ImmutableSet.copyOf(this.blacklist);
    }

    public void setBlacklistedAlgorithms(@Nullable Collection<String> uris) {
        if (uris == null) {
            this.blacklist = Collections.emptySet();
            return;
        }
        this.blacklist = new HashSet<String>(StringSupport.normalizeStringCollection(uris));
    }

    @Override
    public boolean isBlacklistMerge() {
        return this.blacklistMerge;
    }

    public void setBlacklistMerge(boolean flag) {
        this.blacklistMerge = flag;
    }

    @Override
    @Nonnull
    public WhitelistBlacklistConfiguration.Precedence getWhitelistBlacklistPrecedence() {
        return this.precedence;
    }

    public void setWhitelistBlacklistPrecedence(@Nonnull WhitelistBlacklistConfiguration.Precedence value) {
        this.precedence = Constraint.isNotNull(value, "Precedence may not be null");
    }
}

