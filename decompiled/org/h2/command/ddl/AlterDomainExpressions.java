/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.AlterDomain;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.message.DbException;
import org.h2.schema.Domain;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.ColumnTemplate;

public class AlterDomainExpressions
extends AlterDomain {
    private final int type;
    private Expression expression;

    public AlterDomainExpressions(SessionLocal sessionLocal, Schema schema, int n) {
        super(sessionLocal, schema);
        this.type = n;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    long update(Schema schema, Domain domain) {
        switch (this.type) {
            case 94: {
                domain.setDefaultExpression(this.session, this.expression);
                break;
            }
            case 95: {
                domain.setOnUpdateExpression(this.session, this.expression);
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.type);
            }
        }
        if (this.expression != null) {
            AlterDomainExpressions.forAllDependencies(this.session, domain, this::copyColumn, this::copyDomain, true);
        }
        this.getDatabase().updateMeta(this.session, domain);
        return 0L;
    }

    private boolean copyColumn(Domain domain, Column column) {
        return this.copyExpressions(this.session, domain, column);
    }

    private boolean copyDomain(Domain domain, Domain domain2) {
        return this.copyExpressions(this.session, domain, domain2);
    }

    private boolean copyExpressions(SessionLocal sessionLocal, Domain domain, ColumnTemplate columnTemplate) {
        switch (this.type) {
            case 94: {
                Expression expression = domain.getDefaultExpression();
                if (expression == null || columnTemplate.getDefaultExpression() != null) break;
                columnTemplate.setDefaultExpression(sessionLocal, expression);
                return true;
            }
            case 95: {
                Expression expression = domain.getOnUpdateExpression();
                if (expression == null || columnTemplate.getOnUpdateExpression() != null) break;
                columnTemplate.setOnUpdateExpression(sessionLocal, expression);
                return true;
            }
        }
        return false;
    }

    @Override
    public int getType() {
        return this.type;
    }
}

