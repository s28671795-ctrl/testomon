/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.UUID;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function0;
import org.jooq.Pro;
import org.jooq.TableLike;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class Uuid
extends AbstractField<UUID>
implements QOM.Uuid {
    Uuid() {
        super(Names.N_UUID, Tools.allNotNull(SQLDataType.UUID));
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: {
                return true;
            }
            case ASE: {
                return false;
            }
            case BIGQUERY: {
                return true;
            }
            case COCKROACHDB: 
            case POSTGRES: {
                return true;
            }
            case DB2: {
                return false;
            }
            case FIREBIRD: {
                return false;
            }
            case HANA: {
                return false;
            }
            case H2: {
                return true;
            }
            case HSQLDB: 
            case IGNITE: 
            case MARIADB: 
            case MYSQL: {
                return true;
            }
            case ORACLE: {
                return false;
            }
            case SNOWFLAKE: {
                return true;
            }
            case SQLITE: {
                return false;
            }
            case SQLSERVER: {
                return true;
            }
            case VERTICA: {
                return true;
            }
        }
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case ACCESS: {
                ctx.visit(DSL.function(Names.N_GENGUID, this.getDataType(), new Field[0]));
                break;
            }
            case ASE: {
                ctx.visit(DSL.function(Names.N_NEWID, this.getDataType(), DSL.inline(-1)));
                break;
            }
            case BIGQUERY: {
                ctx.visit(DSL.function(Names.N_GENERATE_UUID, this.getDataType(), new Field[0]));
                break;
            }
            case COCKROACHDB: 
            case POSTGRES: {
                ctx.visit(DSL.function(Names.N_GEN_RANDOM_UUID, this.getDataType(), new Field[0]));
                break;
            }
            case DB2: {
                ctx.visit(DSL.regexpReplaceAll(DSL.function(Names.N_HEX, SQLDataType.VARCHAR, DSL.rand()).concat(DSL.function(Names.N_HEX, SQLDataType.VARCHAR, DSL.function(Names.N_GENERATE_UNIQUE, SQLDataType.NUMERIC, new Field[0]))), DSL.inline("(.{8})(.{4})(.{4})(.{4})(.{12}).*"), DSL.inline("$1-$2-$3-$4-$5")).cast(SQLDataType.CHAR(36)));
                break;
            }
            case FIREBIRD: {
                ctx.visit(DSL.function(Names.N_UUID_TO_CHAR, this.getDataType(), DSL.function(Names.N_GEN_UUID, this.getDataType(), new Field[0])));
                break;
            }
            case HANA: {
                ctx.visit(DSL.regexpReplaceAll(DSL.field(Names.N_SYSUUID).cast(SQLDataType.CHAR(36)), DSL.inline("(.{8})(.{4})(.{4})(.{4})(.{12}).*"), DSL.inline("\\1-\\2-\\3-\\4-\\5")).cast(SQLDataType.CHAR(36)));
                break;
            }
            case H2: {
                ctx.visit(DSL.function(Names.N_RANDOM_UUID, this.getDataType(), new Field[0]));
                break;
            }
            case HSQLDB: 
            case IGNITE: 
            case MARIADB: 
            case MYSQL: {
                ctx.visit(DSL.function(Names.N_UUID, this.getDataType(), new Field[0]));
                break;
            }
            case ORACLE: {
                ctx.visit(DSL.regexpReplaceAll(DSL.function(Names.N_RAWTOHEX, SQLDataType.VARCHAR, DSL.function(Names.N_SYS_GUID, this.getDataType(), new Field[0])), DSL.inline("(.{8})(.{4})(.{4})(.{4})(.{12}).*"), DSL.inline("\\1-\\2-\\3-\\4-\\5")).cast(SQLDataType.CHAR(36)));
                break;
            }
            case SNOWFLAKE: {
                ctx.visit(DSL.function(Names.N_UUID_STRING, this.getDataType(), new Field[0]));
                break;
            }
            case SQLITE: {
                Field<String> u = DSL.field(DSL.name("u"), SQLDataType.VARCHAR);
                ctx.visit(DSL.field(DSL.select(DSL.substring(u, DSL.inline(1), DSL.inline(8)).concat(DSL.inline('-')).concat(DSL.substring(u, DSL.inline(9), DSL.inline(4)).concat(DSL.inline('-'))).concat(DSL.substring(u, DSL.inline(13), DSL.inline(4)).concat(DSL.inline('-'))).concat(DSL.substring(u, DSL.inline(17), DSL.inline(4)).concat(DSL.inline('-'))).concat(DSL.substring(u, DSL.inline(21)))).from((TableLike<?>)DSL.select(DSL.lower(DSL.function(Names.N_HEX, SQLDataType.VARCHAR, DSL.function(Names.N_RANDOMBLOB, SQLDataType.BINARY, DSL.inline(16)))).as((Field)u)).asTable(DSL.unquotedName("t")))));
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.function(Names.N_NEWID, this.getDataType(), new Field[0]));
                break;
            }
            case VERTICA: {
                ctx.visit(DSL.function(Names.N_UUID_GENERATE, this.getDataType(), new Field[0]));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_UUID, this.getDataType(), new Field[0]));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("uuid()");
    }

    @Override
    public final Function0<? extends QOM.Uuid> $constructor() {
        return () -> new Uuid();
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Uuid) {
            QOM.Uuid o = (QOM.Uuid)that;
            return true;
        }
        return super.equals(that);
    }
}

