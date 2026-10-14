/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.impl;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.security.Key;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Resolver;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.xmlsec.WhitelistBlacklistConfiguration;
import org.opensaml.xmlsec.WhitelistBlacklistParameters;
import org.opensaml.xmlsec.impl.BlacklistPredicate;
import org.opensaml.xmlsec.impl.WhitelistPredicate;
import org.opensaml.xmlsec.keyinfo.KeyInfoGenerator;
import org.opensaml.xmlsec.keyinfo.KeyInfoSupport;
import org.opensaml.xmlsec.keyinfo.NamedKeyInfoGeneratorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSecurityParametersResolver<ProductType>
implements Resolver<ProductType, CriteriaSet> {
    private Logger log = LoggerFactory.getLogger(AbstractSecurityParametersResolver.class);

    @Nullable
    protected KeyInfoGenerator lookupKeyInfoGenerator(@Nonnull Credential credential, @Nullable NamedKeyInfoGeneratorManager manager, @Nullable String keyInfoProfileName) {
        Constraint.isNotNull(credential, "Credential may not be null");
        if (manager == null) {
            this.log.trace("NamedKeyInfoGeneratorManger was null, can not resolve");
            return null;
        }
        if (this.log.isTraceEnabled()) {
            Key key = CredentialSupport.extractSigningKey(credential);
            if (key == null) {
                key = CredentialSupport.extractEncryptionKey(credential);
            }
            this.log.trace("Attempting to resolve KeyInfoGenerator for credential with key algo '{}' of impl: {}", (Object)(key != null ? key.getAlgorithm() : "n/a"), (Object)credential.getClass().getName());
        }
        return KeyInfoSupport.getKeyInfoGenerator(credential, manager, keyInfoProfileName);
    }

    protected void resolveAndPopulateWhiteAndBlacklists(@Nonnull WhitelistBlacklistParameters params, @Nonnull CriteriaSet criteria, @Nonnull @NonnullElements @NotEmpty List<? extends WhitelistBlacklistConfiguration> configs) {
        Collection<String> whitelist = this.resolveEffectiveWhitelist(criteria, configs);
        this.log.trace("Resolved effective whitelist: {}", (Object)whitelist);
        Collection<String> blacklist = this.resolveEffectiveBlacklist(criteria, configs);
        this.log.trace("Resolved effective blacklist: {}", (Object)blacklist);
        if (whitelist.isEmpty() && blacklist.isEmpty()) {
            this.log.trace("Both empty, nothing to populate");
            return;
        }
        if (whitelist.isEmpty()) {
            this.log.trace("Whitelist empty, populating blacklist");
            params.setBlacklistedAlgorithms(blacklist);
            return;
        }
        if (blacklist.isEmpty()) {
            this.log.trace("Blacklist empty, populating whitelist");
            params.setWhitelistedAlgorithms(whitelist);
            return;
        }
        WhitelistBlacklistConfiguration.Precedence precedence = this.resolveWhitelistBlacklistPrecedence(criteria, configs);
        this.log.trace("Resolved effective precedence: {}", (Object)precedence);
        switch (precedence) {
            case WHITELIST: {
                this.log.trace("Based on precedence, populating whitelist");
                params.setWhitelistedAlgorithms(whitelist);
                break;
            }
            case BLACKLIST: {
                this.log.trace("Based on precedence, populating blacklist");
                params.setBlacklistedAlgorithms(blacklist);
                break;
            }
            default: {
                throw new IllegalArgumentException("WhitelistBlacklistPrecedence value is unknown: " + (Object)((Object)precedence));
            }
        }
    }

    @Nonnull
    protected Predicate<String> resolveWhitelistBlacklistPredicate(@Nonnull CriteriaSet criteria, @Nonnull @NonnullElements @NotEmpty List<? extends WhitelistBlacklistConfiguration> configs) {
        Collection<String> whitelist = this.resolveEffectiveWhitelist(criteria, configs);
        this.log.trace("Resolved effective whitelist: {}", (Object)whitelist);
        Collection<String> blacklist = this.resolveEffectiveBlacklist(criteria, configs);
        this.log.trace("Resolved effective blacklist: {}", (Object)blacklist);
        if (whitelist.isEmpty() && blacklist.isEmpty()) {
            this.log.trace("Both empty, returning alwaysTrue predicate");
            return Predicates.alwaysTrue();
        }
        if (whitelist.isEmpty()) {
            this.log.trace("Whitelist empty, returning BlacklistPredicate");
            return new BlacklistPredicate(blacklist);
        }
        if (blacklist.isEmpty()) {
            this.log.trace("Blacklist empty, returning WhitelistPredicate");
            return new WhitelistPredicate(whitelist);
        }
        WhitelistBlacklistConfiguration.Precedence precedence = this.resolveWhitelistBlacklistPrecedence(criteria, configs);
        this.log.trace("Resolved effective precedence: {}", (Object)precedence);
        switch (precedence) {
            case WHITELIST: {
                this.log.trace("Based on precedence, returning WhitelistPredicate");
                return new WhitelistPredicate(whitelist);
            }
            case BLACKLIST: {
                this.log.trace("Based on precedence, returning BlacklistPredicate");
                return new BlacklistPredicate(blacklist);
            }
        }
        throw new IllegalArgumentException("WhitelistBlacklistPrecedence value is unknown: " + (Object)((Object)precedence));
    }

    @Nonnull
    protected Collection<String> resolveEffectiveBlacklist(@Nonnull CriteriaSet criteria, @Nonnull @NonnullElements @NotEmpty List<? extends WhitelistBlacklistConfiguration> configs) {
        LazySet<String> accumulator = new LazySet<String>();
        for (WhitelistBlacklistConfiguration whitelistBlacklistConfiguration : configs) {
            accumulator.addAll(whitelistBlacklistConfiguration.getBlacklistedAlgorithms());
            if (whitelistBlacklistConfiguration.isBlacklistMerge()) continue;
            break;
        }
        return accumulator;
    }

    @Nonnull
    protected Collection<String> resolveEffectiveWhitelist(@Nonnull CriteriaSet criteria, @Nonnull @NonnullElements @NotEmpty List<? extends WhitelistBlacklistConfiguration> configs) {
        LazySet<String> accumulator = new LazySet<String>();
        for (WhitelistBlacklistConfiguration whitelistBlacklistConfiguration : configs) {
            accumulator.addAll(whitelistBlacklistConfiguration.getWhitelistedAlgorithms());
            if (whitelistBlacklistConfiguration.isWhitelistMerge()) continue;
            break;
        }
        return accumulator;
    }

    @Nonnull
    protected WhitelistBlacklistConfiguration.Precedence resolveWhitelistBlacklistPrecedence(@Nonnull CriteriaSet criteria, @Nonnull @NonnullElements @NotEmpty List<? extends WhitelistBlacklistConfiguration> configs) {
        return configs.get(0).getWhitelistBlacklistPrecedence();
    }
}

