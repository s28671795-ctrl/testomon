/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.resolver;

import net.shibboleth.utilities.java.support.collection.ClassIndexedSet;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public class CriteriaSet
extends ClassIndexedSet<Criterion>
implements Criterion {
    public CriteriaSet() {
    }

    public CriteriaSet(Criterion ... criteria) {
        if (criteria == null || criteria.length == 0) {
            return;
        }
        for (Criterion criterion : criteria) {
            if (criterion == null) continue;
            this.add(criterion);
        }
    }
}

