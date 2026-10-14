/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.collection;

import com.google.common.base.MoreObjects;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;

public class Pair<T1, T2> {
    private T1 first;
    private T2 second;

    public Pair() {
    }

    public Pair(@Nullable T1 newFirst, @Nullable T2 newSecond) {
        this.first = newFirst;
        this.second = newSecond;
    }

    public Pair(@Nonnull Pair<? extends T1, ? extends T2> pair) {
        Constraint.isNotNull(pair, "Pair to be copied can not be null");
        this.first = pair.getFirst();
        this.second = pair.getSecond();
    }

    @Nullable
    public T1 getFirst() {
        return this.first;
    }

    public void setFirst(@Nullable T1 newFirst) {
        this.first = newFirst;
    }

    @Nullable
    public T2 getSecond() {
        return this.second;
    }

    public void setSecond(@Nullable T2 newSecond) {
        this.second = newSecond;
    }

    public boolean equals(@Nullable Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Pair) {
            Pair otherPair = (Pair)o;
            return Objects.equals(this.getFirst(), otherPair.getFirst()) && Objects.equals(this.getSecond(), otherPair.getSecond());
        }
        return false;
    }

    public int hashCode() {
        return com.google.common.base.Objects.hashCode(this.first, this.second);
    }

    @Nonnull
    public String toString() {
        return MoreObjects.toStringHelper(this).add("first", this.first).add("second", this.second).toString();
    }
}

