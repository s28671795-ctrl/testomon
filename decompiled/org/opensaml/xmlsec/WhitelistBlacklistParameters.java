/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class WhitelistBlacklistParameters {
    @Nonnull
    @NonnullElements
    private Collection<String> whiteListedAlgorithmURIs = Collections.emptySet();
    @Nonnull
    @NonnullElements
    private Collection<String> blackListedAlgorithmURIs = Collections.emptySet();

    @Nonnull
    @NonnullElements
    @NotLive
    @Unmodifiable
    public Collection<String> getWhitelistedAlgorithms() {
        return ImmutableSet.copyOf(this.whiteListedAlgorithmURIs);
    }

    public void setWhitelistedAlgorithms(@Nullable Collection<String> uris) {
        if (uris == null) {
            this.whiteListedAlgorithmURIs = Collections.emptySet();
            return;
        }
        this.whiteListedAlgorithmURIs = new HashSet<String>(StringSupport.normalizeStringCollection(uris));
    }

    @Nonnull
    @NonnullElements
    @NotLive
    @Unmodifiable
    public Collection<String> getBlacklistedAlgorithms() {
        return ImmutableSet.copyOf(this.blackListedAlgorithmURIs);
    }

    public void setBlacklistedAlgorithms(@Nonnull @NonnullElements Collection<String> uris) {
        if (uris == null) {
            this.blackListedAlgorithmURIs = Collections.emptySet();
            return;
        }
        this.blackListedAlgorithmURIs = new HashSet<String>(StringSupport.normalizeStringCollection(uris));
    }
}

