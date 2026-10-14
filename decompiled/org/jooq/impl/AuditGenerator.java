/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.EnumSet;
import org.jooq.AuditType;
import org.jooq.Field;
import org.jooq.Generator;
import org.jooq.GeneratorContext;
import org.jooq.GeneratorStatementType;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.Table;

@Pro
public final class AuditGenerator<R extends Record, X extends Table<R>, T>
implements Generator<R, X, T> {
    private final AuditType auditType;
    private final EnumSet<GeneratorStatementType> statementTypes;

    public AuditGenerator(AuditType auditType, GeneratorStatementType ... statementTypes) {
        this.auditType = auditType;
        this.statementTypes = EnumSet.noneOf(GeneratorStatementType.class);
        for (GeneratorStatementType s2 : statementTypes) {
            this.statementTypes.add(s2);
        }
    }

    @Override
    public final Field<T> apply(GeneratorContext<R, X, T> ctx) {
        switch (this.auditType) {
            case TIMESTAMP: {
                return ctx.configuration().auditProvider().provideTimestamp(ctx);
            }
            case USER: {
                return ctx.configuration().auditProvider().provideUser(ctx);
            }
        }
        throw new IllegalStateException("Unsupported audit type: " + String.valueOf((Object)this.auditType));
    }

    @Override
    public boolean supports(GeneratorStatementType s2) {
        return this.statementTypes.contains((Object)s2);
    }
}

