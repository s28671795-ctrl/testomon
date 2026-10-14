/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Predicate;

public abstract class AbstractTriStatePredicate<T>
implements Predicate<T> {
    private boolean nullInputSatisfies;
    private boolean unevaluableSatisfies;

    public boolean isNullInputSatisfies() {
        return this.nullInputSatisfies;
    }

    public void setNullInputSatisfies(boolean flag) {
        this.nullInputSatisfies = flag;
    }

    public boolean isUnevaluableSatisfies() {
        return this.unevaluableSatisfies;
    }

    public void setUnevaluableSatisfies(boolean flag) {
        this.unevaluableSatisfies = flag;
    }
}

