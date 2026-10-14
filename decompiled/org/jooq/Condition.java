/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Select;
import org.jooq.Support;

public interface Condition
extends Field<Boolean> {
    @Support
    @NotNull
    public Condition and(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    public Condition and(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    public Condition and(String var1);

    @Support
    @PlainSQL
    @NotNull
    public Condition and(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    public Condition and(String var1, QueryPart ... var2);

    @Support
    @NotNull
    public Condition andNot(Condition var1);

    @Support
    @NotNull
    public Condition andNot(Field<Boolean> var1);

    @Support
    @NotNull
    public Condition andExists(Select<?> var1);

    @Support
    @NotNull
    public Condition andNotExists(Select<?> var1);

    @Support
    @NotNull
    public Condition or(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    public Condition or(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    public Condition or(String var1);

    @Support
    @PlainSQL
    @NotNull
    public Condition or(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    public Condition or(String var1, QueryPart ... var2);

    @Support
    @NotNull
    public Condition orNot(Condition var1);

    @Support
    @NotNull
    public Condition orNot(Field<Boolean> var1);

    @Support
    @NotNull
    public Condition orExists(Select<?> var1);

    @Support
    @NotNull
    public Condition orNotExists(Select<?> var1);

    @Support
    @NotNull
    public Condition xor(Field<Boolean> var1);

    @Support
    @PlainSQL
    @NotNull
    public Condition xor(SQL var1);

    @Support
    @PlainSQL
    @NotNull
    public Condition xor(String var1);

    @Support
    @PlainSQL
    @NotNull
    public Condition xor(String var1, Object ... var2);

    @Support
    @PlainSQL
    @NotNull
    public Condition xor(String var1, QueryPart ... var2);

    @Support
    @NotNull
    public Condition xorNot(Condition var1);

    @Support
    @NotNull
    public Condition xorNot(Field<Boolean> var1);

    @Support
    @NotNull
    public Condition xorExists(Select<?> var1);

    @Support
    @NotNull
    public Condition xorNotExists(Select<?> var1);

    @Support
    @NotNull
    public Condition and(Condition var1);

    @Support
    @NotNull
    public Condition not();

    @Support
    @NotNull
    public Condition or(Condition var1);

    @Support
    @NotNull
    public Condition xor(Condition var1);
}

