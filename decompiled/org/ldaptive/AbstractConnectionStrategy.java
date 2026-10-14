/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.time.Instant;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.ldaptive.ConnectionStrategy;
import org.ldaptive.LdapURL;
import org.ldaptive.LdapURLActivatorService;
import org.ldaptive.LdapURLRetryMetadata;
import org.ldaptive.LdapURLSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConnectionStrategy
implements ConnectionStrategy {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected LdapURLSet ldapURLSet;
    private boolean initialized;
    private Predicate<LdapURL> activateCondition;
    private Predicate<LdapURL> retryCondition = url2 -> Instant.now().isAfter(url2.getRetryMetadata().getFailureTime().plus(LdapURLActivatorService.getPeriod()));

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    @Override
    public synchronized void initialize(String urls2, Predicate<LdapURL> condition) {
        if (this.isInitialized()) {
            throw new IllegalStateException("Strategy has already been initialized");
        }
        this.ldapURLSet = new LdapURLSet(this, urls2);
        this.activateCondition = condition;
        this.initialized = true;
    }

    @Override
    public void populate(String urls2, LdapURLSet urlSet) {
        if (urls2 == null || urls2.isEmpty()) {
            throw new IllegalArgumentException("urls cannot be empty or null");
        }
        if (urls2.contains(" ")) {
            urlSet.populate(Stream.of(urls2.split(" ")).map(s2 -> {
                LdapURL url2 = new LdapURL((String)s2);
                url2.setRetryMetadata(new LdapURLRetryMetadata(this));
                return url2;
            }).collect(Collectors.toList()));
        } else {
            LdapURL url2 = new LdapURL(urls2);
            url2.setRetryMetadata(new LdapURLRetryMetadata(this));
            urlSet.populate(Collections.singletonList(url2));
        }
    }

    @Override
    public Predicate<LdapURL> getActivateCondition() {
        return this.activateCondition;
    }

    @Override
    public Predicate<LdapURL> getRetryCondition() {
        return this.retryCondition;
    }

    public void setRetryCondition(Predicate<LdapURL> condition) {
        this.retryCondition = condition;
    }

    @Override
    public void success(LdapURL url2) {
        url2.activate();
        url2.getRetryMetadata().recordSuccess(Instant.now());
    }

    @Override
    public void failure(LdapURL url2) {
        url2.deactivate();
        url2.getRetryMetadata().recordFailure(Instant.now());
        LdapURLActivatorService.getInstance().registerUrl(url2);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "ldapURLSet=" + this.ldapURLSet + ", " + "activateCondition=" + this.activateCondition + ", " + "retryCondition=" + this.retryCondition + ", " + "initialized=" + this.initialized + "]";
    }

    protected static class DefaultLdapURLIterator
    implements Iterator<LdapURL> {
        private final List<LdapURL> ldapUrls;
        private int i;

        public DefaultLdapURLIterator(List<LdapURL> urls2) {
            this.ldapUrls = urls2;
        }

        @Override
        public boolean hasNext() {
            return this.i < this.ldapUrls.size();
        }

        @Override
        public LdapURL next() {
            return this.ldapUrls.get(this.i++);
        }
    }
}

