/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.CheckReturnValue;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.SelectForXMLCommonDirectivesStep;
import org.jooq.Support;

@Pro
public interface SelectForXMLPathDirectivesStep<R extends Record>
extends SelectForXMLCommonDirectivesStep<R> {
    @Support(value={SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForXMLCommonDirectivesStep<R> elements();

    @Support(value={SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForXMLCommonDirectivesStep<R> elementsXsinil();

    @Support(value={SQLDialect.SQLSERVER})
    @NotNull
    @CheckReturnValue
    @Pro
    public SelectForXMLCommonDirectivesStep<R> elementsAbsent();
}

