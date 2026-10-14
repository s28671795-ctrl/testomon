/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import org.ldaptive.AbstractConnectionStrategy;
import org.ldaptive.LdapURL;

public class ActivePassiveConnectionStrategy
extends AbstractConnectionStrategy {
    private final Function<List<LdapURL>, Iterator<LdapURL>> iterFunction;

    public ActivePassiveConnectionStrategy() {
        this(null);
    }

    public ActivePassiveConnectionStrategy(Function<List<LdapURL>, Iterator<LdapURL>> function) {
        this.iterFunction = function;
    }

    @Override
    public Iterator<LdapURL> iterator() {
        if (!this.isInitialized()) {
            throw new IllegalStateException("Strategy is not initialized");
        }
        if (this.iterFunction != null) {
            return this.iterFunction.apply(this.ldapURLSet.getUrls());
        }
        return new AbstractConnectionStrategy.DefaultLdapURLIterator(this.ldapURLSet.getUrls());
    }

    @Override
    public ActivePassiveConnectionStrategy newInstance() {
        ActivePassiveConnectionStrategy strategy = new ActivePassiveConnectionStrategy(this.iterFunction);
        strategy.setRetryCondition(this.getRetryCondition());
        return strategy;
    }
}

