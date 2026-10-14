/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import org.jooq.Clause;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DSLContext;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.impl.DSL;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;

abstract class AbstractQueryPart
implements QueryPartInternal {
    private static final JooqLogger log = JooqLogger.getLogger(AbstractQueryPart.class, "serialization", 100);

    AbstractQueryPart() {
    }

    Configuration configuration() {
        return Tools.CONFIG.get();
    }

    @Override
    @Deprecated
    public Clause[] clauses(Context<?> ctx) {
        return null;
    }

    @Override
    public boolean rendersContent(Context<?> ctx) {
        return true;
    }

    @Override
    public boolean declaresFields() {
        return false;
    }

    @Override
    public boolean declaresTables() {
        return false;
    }

    @Override
    public boolean declaresWindows() {
        return false;
    }

    @Override
    public boolean declaresCTE() {
        return false;
    }

    @Override
    @Pro
    public boolean declaresParameters() {
        return false;
    }

    @Override
    public boolean generatesCast() {
        return false;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof QueryPart) {
            DSLContext dSLContext;
            QueryPart q = (QueryPart)that;
            DSLContext dsl1 = Tools.configuration(this.configuration()).dsl();
            if (that instanceof AbstractQueryPart) {
                AbstractQueryPart a = (AbstractQueryPart)that;
                dSLContext = Tools.configuration(a.configuration()).dsl();
            } else {
                dSLContext = dsl1;
            }
            DSLContext dsl2 = dSLContext;
            String sql1 = dsl1.renderInlined(this);
            String sql2 = dsl2.renderInlined(q);
            return sql1.equals(sql2);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.create().renderInlined(this).hashCode();
    }

    @Override
    public String toString() {
        try {
            Configuration configuration = Tools.configuration(this.configuration());
            return this.create(configuration.deriveSettings(s2 -> s2.withRenderFormatted(true))).renderInlined(this);
        }
        catch (SQLDialectNotSupportedException e) {
            return "[ ... " + e.getMessage() + " ... ]";
        }
    }

    @Deprecated
    protected final DSLContext create() {
        return this.create(this.configuration());
    }

    @Deprecated
    protected final DSLContext create(Configuration configuration) {
        return DSL.using(configuration);
    }

    @Deprecated
    protected final DSLContext create(Context<?> ctx) {
        return DSL.using(ctx.configuration());
    }

    protected final DataAccessException translate(String sql, SQLException e) {
        return Tools.translate(sql, e);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        if (log.isWarnEnabled()) {
            log.warn((Object)"DEPRECATION", "A QueryPart of type " + String.valueOf(this.getClass()) + " has been deserialised. Serialization support is deprecated in jOOQ. Please contact https://github.com/jOOQ/jOOQ/issues/11506 and state your use-case to see if it can be implemented otherwise.");
        }
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        if (log.isWarnEnabled()) {
            log.warn((Object)"DEPRECATION", "A QueryPart of type " + String.valueOf(this.getClass()) + " has been serialised. Serialization support is deprecated in jOOQ. Please contact https://github.com/jOOQ/jOOQ/issues/11506 and state your use-case to see if it can be implemented otherwise.");
        }
    }
}

