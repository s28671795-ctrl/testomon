/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Traverser;
import org.jooq.WindowDefinition;
import org.jooq.WindowSpecification;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractWindowFunction;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.Val;

abstract class AbstractLeadLag<T>
extends AbstractWindowFunction<T> {
    private final Field<T> field;
    private final Field<Integer> offset;
    private final Field<T> defaultValue;

    AbstractLeadLag(Name name, Field<T> field2, Field<Integer> offset, Field<T> defaultValue) {
        super(name, field2.getDataType().null_());
        this.field = field2;
        this.offset = offset;
        this.defaultValue = defaultValue;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.defaultValue == null) {
            this.accept0(ctx);
        } else {
            switch (ctx.family()) {
                case REDSHIFT: {
                    ctx.visit(Names.N_COALESCE).sql('(');
                    this.accept0(ctx);
                    ctx.sql(", ").visit(this.defaultValue).sql(')');
                    break;
                }
                default: {
                    this.accept0(ctx);
                }
            }
        }
    }

    private final void accept0(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql(this.getName().toLowerCase()).sql('(').visit(this.field).sql(')');
                this.acceptNullTreatment(ctx);
                break;
            }
            default: {
                ctx.visit(this.getUnqualifiedName()).sql('(').visit(this.field);
                if (this.offset != null) {
                    switch (ctx.family()) {
                        case EXASOL: {
                            ctx.paramType(ParamType.INLINED, c -> c.sql(", ").visit(this.offset));
                            break;
                        }
                        case JAVA: {
                            ctx.sql(", ").visit(Val.getJavaValue(this.offset));
                            break;
                        }
                        default: {
                            ctx.sql(", ").visit(this.offset);
                        }
                    }
                }
                if (this.defaultValue != null) {
                    switch (ctx.family()) {
                        case REDSHIFT: {
                            break;
                        }
                        case VERTICA: {
                            ctx.sql(", ").visit(DSL.inlined(this.defaultValue));
                            break;
                        }
                        default: {
                            ctx.sql(", ").visit(this.defaultValue);
                        }
                    }
                }
                this.acceptNullTreatmentDB2(ctx);
                this.acceptNullTreatmentVertica(ctx);
                ctx.sql(')');
                this.acceptNullTreatment(ctx);
            }
        }
        this.acceptOverClause(ctx);
    }

    public final Field<T> $field() {
        return this.field;
    }

    public final Field<Integer> $offset() {
        return this.offset;
    }

    public final Field<T> $defaultValue() {
        return this.defaultValue;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.field, this.offset, this.defaultValue, this.$windowSpecification() != null ? this.$windowSpecification() : this.$windowDefinition());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.field, this.offset, this.defaultValue, this.$windowSpecification(), this.$windowDefinition(), (f, o, def, s2, d) -> this.constructor((Field<T>)f, (Field<Integer>)o, (Field<T>)def).$windowSpecification((WindowSpecification)s2).$windowDefinition((WindowDefinition)d).$nullTreatment(this.nullTreatment), replacer);
    }

    abstract AbstractLeadLag<T> constructor(Field<T> var1, Field<Integer> var2, Field<T> var3);
}

