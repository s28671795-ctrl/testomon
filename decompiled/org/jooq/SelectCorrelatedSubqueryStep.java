/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.BetweenAndStep;
import org.jooq.BetweenAndStepR;
import org.jooq.CheckReturnValue;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.QuantifiedSelect;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.SelectFinalStep;
import org.jooq.Support;

public interface SelectCorrelatedSubqueryStep<R extends Record>
extends SelectFinalStep<R> {
    @Support
    @NotNull
    @CheckReturnValue
    public Condition compare(Comparator var1, R var2);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition compare(Comparator var1, Select<? extends R> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition compare(Comparator var1, QuantifiedSelect<? extends R> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition eq(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition eq(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition eq(QuantifiedSelect<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition equal(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition equal(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition equal(QuantifiedSelect<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition ne(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition ne(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition ne(QuantifiedSelect<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition notEqual(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition notEqual(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition notEqual(QuantifiedSelect<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition lt(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition lt(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition lt(QuantifiedSelect<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition lessThan(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition lessThan(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition lessThan(QuantifiedSelect<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition le(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition le(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition le(QuantifiedSelect<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition lessOrEqual(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition lessOrEqual(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition lessOrEqual(QuantifiedSelect<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition gt(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition gt(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition gt(QuantifiedSelect<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition greaterThan(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition greaterThan(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition greaterThan(QuantifiedSelect<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition ge(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition ge(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition ge(QuantifiedSelect<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition greaterOrEqual(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition greaterOrEqual(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition greaterOrEqual(QuantifiedSelect<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition in(R ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition in(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition notIn(R ... var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition notIn(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition isDistinctFrom(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition isDistinctFrom(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition isDistinctFrom(QuantifiedSelect<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition isNotDistinctFrom(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition isNotDistinctFrom(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition isNotDistinctFrom(QuantifiedSelect<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public BetweenAndStep<R> between(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition between(R var1, R var2);

    @Support
    @NotNull
    @CheckReturnValue
    public BetweenAndStep<R> between(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition between(Select<? extends R> var1, Select<? extends R> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public BetweenAndStepR<R> betweenSymmetric(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition betweenSymmetric(R var1, R var2);

    @Support
    @NotNull
    @CheckReturnValue
    public BetweenAndStepR<R> betweenSymmetric(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition betweenSymmetric(Select<? extends R> var1, Select<? extends R> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public BetweenAndStepR<R> notBetween(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition notBetween(R var1, R var2);

    @Support
    @NotNull
    @CheckReturnValue
    public BetweenAndStepR<R> notBetween(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition notBetween(Select<? extends R> var1, Select<? extends R> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public BetweenAndStepR<R> notBetweenSymmetric(R var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition notBetweenSymmetric(R var1, R var2);

    @Support
    @NotNull
    @CheckReturnValue
    public BetweenAndStepR<R> notBetweenSymmetric(Select<? extends R> var1);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition notBetweenSymmetric(Select<? extends R> var1, Select<? extends R> var2);

    @Support
    @NotNull
    @CheckReturnValue
    public Condition isNull();

    @Support
    @NotNull
    @CheckReturnValue
    public Condition isNotNull();
}

