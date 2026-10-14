/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.ldaptive.AbstractConnectionStrategy;
import org.ldaptive.LdapURL;

public class RandomConnectionStrategy
extends AbstractConnectionStrategy {
    @Override
    public Iterator<LdapURL> iterator() {
        if (!this.isInitialized()) {
            throw new IllegalStateException("Strategy is not initialized");
        }
        return new Iterator<LdapURL>(){
            private final List<LdapURL> active;
            private final List<LdapURL> inactive;
            private int i;
            {
                this.active = RandomConnectionStrategy.this.ldapURLSet.getActiveUrls().stream().collect(Collectors.collectingAndThen(Collectors.toCollection(ArrayList::new), list -> {
                    Collections.shuffle(list);
                    return list;
                }));
                this.inactive = RandomConnectionStrategy.this.ldapURLSet.getInactiveUrls().stream().collect(Collectors.collectingAndThen(Collectors.toCollection(ArrayList::new), list -> {
                    Collections.shuffle(list);
                    return list;
                }));
            }

            @Override
            public boolean hasNext() {
                return this.i < this.active.size() + this.inactive.size();
            }

            @Override
            public LdapURL next() {
                LdapURL url2 = this.i < this.active.size() ? this.active.get(this.i) : this.inactive.get(this.i - this.active.size());
                ++this.i;
                return url2;
            }
        };
    }

    @Override
    public RandomConnectionStrategy newInstance() {
        RandomConnectionStrategy strategy = new RandomConnectionStrategy();
        strategy.setRetryCondition(this.getRetryCondition());
        return strategy;
    }
}

