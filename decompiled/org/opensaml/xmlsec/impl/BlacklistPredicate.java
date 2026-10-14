/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.impl;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.Collection;
import java.util.HashSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.logic.Constraint;

public class BlacklistPredicate
implements Predicate<String> {
    @Nonnull
    @NonnullElements
    private Collection<String> blacklist;

    public BlacklistPredicate(@Nonnull Collection<String> algorithms) {
        Constraint.isNotNull(algorithms, "Blacklist may not be null");
        this.blacklist = new HashSet<String>();
        this.blacklist.addAll(Collections2.filter(algorithms, Predicates.notNull()));
    }

    @Override
    public boolean apply(@Nullable String input) {
        if (input == null) {
            throw new IllegalArgumentException("Algorithm URI to evaluate may not be null");
        }
        return !this.blacklist.contains(input);
    }
}

