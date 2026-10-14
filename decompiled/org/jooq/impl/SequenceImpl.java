/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Catalog;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Internal;
import org.jooq.Keyword;
import org.jooq.Name;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.SelectConditionStep;
import org.jooq.Sequence;
import org.jooq.TableLike;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AbstractTypedNamed;
import org.jooq.impl.CommentImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultMetaProvider;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedImpl;

@Internal
public class SequenceImpl<T extends Number>
extends AbstractTypedNamed<T>
implements Sequence<T>,
QOM.UNotYetImplemented {
    private static final Clause[] CLAUSES = new Clause[]{Clause.SEQUENCE, Clause.SEQUENCE_REFERENCE};
    private final boolean nameIsPlainSQL;
    private final Schema schema;
    private final Field<T> startWith;
    private final Field<T> incrementBy;
    private final Field<T> minvalue;
    private final Field<T> maxvalue;
    private final boolean cycle;
    private final Field<T> cache;
    private final SequenceFunction<T> currval;
    private final SequenceFunction<T> nextval;

    @Deprecated
    public SequenceImpl(String name, Schema schema, DataType<T> type) {
        this(name, schema, type, false);
    }

    SequenceImpl(String name, Schema schema, DataType<T> type, boolean nameIsPlainSQL) {
        this(DSL.name(name), schema, type, nameIsPlainSQL);
    }

    SequenceImpl(Name name, Schema schema, DataType<T> type, boolean nameIsPlainSQL) {
        this(name, schema, type, nameIsPlainSQL, null, null, null, null, false, null);
    }

    SequenceImpl(Name name, Schema schema, DataType<T> type, boolean nameIsPlainSQL, Field<T> startWith, Field<T> incrementBy, Field<T> minvalue, Field<T> maxvalue, boolean cycle, Field<T> cache) {
        super(SequenceImpl.qualify(schema, name), CommentImpl.NO_COMMENT, type);
        this.schema = schema;
        this.nameIsPlainSQL = nameIsPlainSQL;
        this.startWith = startWith;
        this.incrementBy = incrementBy;
        this.minvalue = minvalue;
        this.maxvalue = maxvalue;
        this.cycle = cycle;
        this.cache = cache;
        this.currval = new SequenceFunction(SequenceMethod.CURRVAL, this);
        this.nextval = new SequenceFunction(SequenceMethod.NEXTVAL, this);
    }

    @Override
    public final Catalog getCatalog() {
        return this.getSchema() == null ? null : this.getSchema().getCatalog();
    }

    @Override
    public final Schema getSchema() {
        return this.schema;
    }

    @Override
    public final Field<T> getStartWith() {
        return this.startWith;
    }

    @Override
    public final Field<T> getIncrementBy() {
        return this.incrementBy;
    }

    @Override
    public final Field<T> getMinvalue() {
        return this.minvalue;
    }

    @Override
    public final Field<T> getMaxvalue() {
        return this.maxvalue;
    }

    @Override
    public final boolean getCycle() {
        return this.cycle;
    }

    @Override
    public final Field<T> getCache() {
        return this.cache;
    }

    @Override
    public final Field<T> currval() {
        return this.currval;
    }

    @Override
    public final Field<T> nextval() {
        return this.nextval;
    }

    @Override
    public final Select<Record1<T>> nextvals(int size) {
        return DSL.select(this.nextval().as(Names.N_NEXTVAL)).from((TableLike<?>)((Object)DSL.generateSeries(1, size).as(Names.N_GENERATE_SERIES)));
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            if (DefaultMetaProvider.meta(ctx.configuration()).getSequences(this.getQualifiedName()).isEmpty()) {
                ctx.sql("sequence(").visit(this.getQualifiedName()).sql(")");
            } else {
                ctx.sql(this.getName());
            }
            return;
        }
        QualifiedImpl.acceptMappedSchemaPrefix(ctx, this.getSchema());
        if (this.nameIsPlainSQL) {
            ctx.sql(this.getName());
        } else {
            ctx.visit(this.getUnqualifiedName());
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final Schema $schema() {
        return this.schema;
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof SequenceImpl) {
            SequenceImpl s2 = (SequenceImpl)that;
            return this.getQualifiedName().equals(s2.getQualifiedName());
        }
        return super.equals(that);
    }

    private static class SequenceFunction<T extends Number>
    extends AbstractField<T>
    implements QOM.UNotYetImplemented {
        private final SequenceMethod method;
        private final SequenceImpl<T> sequence;

        SequenceFunction(SequenceMethod method2, SequenceImpl<T> sequence2) {
            super(method2.name, sequence2.getDataType());
            this.method = method2;
            this.sequence = sequence2;
        }

        @Override
        public final void accept(Context<?> ctx) {
            Configuration configuration = ctx.configuration();
            SQLDialect family = configuration.family();
            switch (family) {
                case JAVA: {
                    ctx.visit(this.sequence).sql('.').visit(this.method.keyword).sql("()");
                    break;
                }
                case DB2: 
                case INGRES: 
                case ORACLE: 
                case SNOWFLAKE: 
                case SYBASE: {
                    ctx.visit(this.sequence).sql('.').visit(this.method.keyword);
                    break;
                }
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    ctx.visit(this.method.keyword).sql('(');
                    ctx.sql('\'').stringLiteral(true).visit(this.sequence).stringLiteral(false).sql('\'');
                    ctx.sql(')');
                    break;
                }
                case CUBRID: {
                    ctx.visit(this.sequence).sql('.');
                    if (this.method == SequenceMethod.NEXTVAL) {
                        ctx.visit(DSL.keyword("next_value"));
                        break;
                    }
                    ctx.visit(DSL.keyword("current_value"));
                    break;
                }
                case SQLSERVER: 
                case DERBY: 
                case FIREBIRD: 
                case H2: 
                case HSQLDB: 
                case MARIADB: {
                    if (this.method == SequenceMethod.NEXTVAL) {
                        ctx.visit(Keywords.K_NEXT_VALUE_FOR).sql(' ').visit(this.sequence);
                        break;
                    }
                    if (SQLDialect.supportedUntil(SQLDialect.H2_1_4_200).contains((Object)ctx.dialect())) {
                        ctx.visit(this.sequence).sql('.').visit(this.method.keyword);
                        break;
                    }
                    if (family == SQLDialect.HSQLDB || family == SQLDialect.H2) {
                        ctx.visit(Keywords.K_CURRENT_VALUE_FOR).sql(' ').visit(this.sequence);
                        break;
                    }
                    if (family == SQLDialect.MARIADB) {
                        ctx.visit(Keywords.K_PREVIOUS_VALUE_FOR).sql(' ').visit(this.sequence);
                        break;
                    }
                    if (family == SQLDialect.FIREBIRD) {
                        ctx.visit(Names.N_GEN_ID).sql('(').visit(this.sequence).sql(", 0)");
                        break;
                    }
                    if (family == SQLDialect.SQLSERVER) {
                        Object[] objectArray;
                        SelectConditionStep selectConditionStep = DSL.select(DSL.field("current_value")).from("sys.sequences sq").join("sys.schemas sc").on("sq.schema_id = sc.schema_id").where("sq.name = ?", this.sequence.getName());
                        String string = this.sequence.schema == null ? "1 = 1" : "sc.name = ?";
                        if (this.sequence.schema == null) {
                            objectArray = new Object[]{};
                        } else {
                            Object[] objectArray2 = new Object[1];
                            objectArray = objectArray2;
                            objectArray2[0] = this.sequence.schema.getName();
                        }
                        ctx.visit(selectConditionStep.and(string, objectArray).asField().cast(this.sequence.getDataType()));
                        break;
                    }
                    ctx.visit(this.sequence).sql('.').visit(this.method.keyword);
                    break;
                }
                default: {
                    ctx.visit(this.sequence).sql('.').visit(this.method.keyword);
                }
            }
        }

        @Override
        public boolean equals(Object that) {
            if (that instanceof SequenceFunction) {
                SequenceFunction s2 = (SequenceFunction)that;
                return this.method == s2.method && this.sequence.equals(s2.sequence);
            }
            return super.equals(that);
        }
    }

    private static enum SequenceMethod {
        CURRVAL(Keywords.K_CURRVAL, Names.N_CURRVAL),
        NEXTVAL(Keywords.K_NEXTVAL, Names.N_NEXTVAL);

        final Keyword keyword;
        final Name name;

        private SequenceMethod(Keyword keyword, Name name) {
            this.keyword = keyword;
            this.name = name;
        }
    }
}

