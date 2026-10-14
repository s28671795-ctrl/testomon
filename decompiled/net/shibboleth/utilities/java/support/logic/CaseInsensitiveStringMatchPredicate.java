/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Predicate;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;

public class CaseInsensitiveStringMatchPredicate
implements Predicate<CharSequence> {
    private final String target;

    public CaseInsensitiveStringMatchPredicate(@Nonnull String matchString) {
        this.target = Constraint.isNotNull(matchString, "Target string can not be null");
    }

    @Override
    public boolean apply(CharSequence input) {
        if (input == null) {
            return false;
        }
        return this.target.equalsIgnoreCase(input.toString());
    }
}

