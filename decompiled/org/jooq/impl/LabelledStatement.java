/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Label;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.Statement;
import org.jooq.Traverser;
import org.jooq.impl.AbstractStatement;
import org.jooq.impl.QOM;

@Pro
final class LabelledStatement
extends AbstractStatement
implements QOM.LabelledStatement {
    private final Label label;
    private final Statement statement;

    LabelledStatement(Label label, Statement statement) {
        this.label = label;
        this.statement = statement;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case H2: 
            case DB2: 
            case FIREBIRD: 
            case HSQLDB: 
            case MARIADB: 
            case MYSQL: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(this.label).sql(':').formatSeparator().visit(this.statement);
                break;
            }
            default: {
                ctx.sql("<<").visit(this.label).sql(">>").formatSeparator().visit(this.statement);
            }
        }
    }

    @Override
    public final Label $label() {
        return this.label;
    }

    @Override
    public final Statement $statement() {
        return this.statement;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.label, this.statement);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.label, this.statement, LabelledStatement::new, replacer);
    }
}

