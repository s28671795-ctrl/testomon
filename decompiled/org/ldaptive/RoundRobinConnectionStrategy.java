/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import org.ldaptive.AbstractConnectionStrategy;
import org.ldaptive.LdapURL;

public class RoundRobinConnectionStrategy
extends AbstractConnectionStrategy {
    private final AtomicInteger counter = new AtomicInteger();
    private final Function<List<LdapURL>, Iterator<LdapURL>> iterFunction;

    public RoundRobinConnectionStrategy() {
        this(null);
    }

    public RoundRobinConnectionStrategy(Function<List<LdapURL>, Iterator<LdapURL>> function) {
        this.iterFunction = function;
    }

    @Override
    public synchronized Iterator<LdapURL> iterator() {
        if (!this.isInitialized()) {
            throw new IllegalStateException("Strategy is not initialized");
        }
        ArrayList<LdapURL> urls2 = new ArrayList<LdapURL>(this.ldapURLSet.getActiveUrls());
        if (urls2.size() > 1) {
            for (int i = 0; i < this.counter.get(); ++i) {
                urls2.add((LdapURL)urls2.remove(0));
            }
        }
        urls2.addAll(this.ldapURLSet.getInactiveUrls());
        this.counter.incrementAndGet();
        if (this.iterFunction != null) {
            return this.iterFunction.apply(this.ldapURLSet.getUrls());
        }
        return new AbstractConnectionStrategy.DefaultLdapURLIterator(urls2);
    }

    @Override
    public RoundRobinConnectionStrategy newInstance() {
        RoundRobinConnectionStrategy strategy = new RoundRobinConnectionStrategy(this.iterFunction);
        strategy.setRetryCondition(this.getRetryCondition());
        return strategy;
    }
}

