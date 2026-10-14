/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import java.util.ArrayList;
import org.h2.command.ddl.AlterDomain;
import org.h2.command.ddl.AlterDomainAddConstraint;
import org.h2.command.ddl.AlterTableAddConstraint;
import org.h2.constraint.ConstraintActionType;
import org.h2.constraint.ConstraintDomain;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.message.DbException;
import org.h2.schema.Domain;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.ColumnTemplate;
import org.h2.table.Table;

public class DropDomain
extends AlterDomain {
    private ConstraintActionType dropAction;

    public DropDomain(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
        this.dropAction = this.getDatabase().getSettings().dropRestrict ? ConstraintActionType.RESTRICT : ConstraintActionType.CASCADE;
    }

    public void setDropAction(ConstraintActionType constraintActionType) {
        this.dropAction = constraintActionType;
    }

    @Override
    long update(Schema schema, Domain domain) {
        DropDomain.forAllDependencies(this.session, domain, this::copyColumn, this::copyDomain, true);
        this.getDatabase().removeSchemaObject(this.session, domain);
        return 0L;
    }

    private boolean copyColumn(Domain domain, Column column) {
        Table table = column.getTable();
        if (this.dropAction == ConstraintActionType.RESTRICT) {
            throw DbException.get(90107, this.domainName, table.getCreateSQL());
        }
        String string = column.getName();
        ArrayList<ConstraintDomain> arrayList = domain.getConstraints();
        if (arrayList != null && !arrayList.isEmpty()) {
            for (ConstraintDomain constraintDomain : arrayList) {
                Expression expression = constraintDomain.getCheckConstraint(this.session, string);
                AlterTableAddConstraint alterTableAddConstraint = new AlterTableAddConstraint(this.session, table.getSchema(), 3, false);
                alterTableAddConstraint.setTableName(table.getName());
                alterTableAddConstraint.setCheckExpression(expression);
                alterTableAddConstraint.update();
            }
        }
        DropDomain.copyExpressions(this.session, domain, column);
        return true;
    }

    private boolean copyDomain(Domain domain, Domain domain2) {
        if (this.dropAction == ConstraintActionType.RESTRICT) {
            throw DbException.get(90107, this.domainName, domain2.getTraceSQL());
        }
        ArrayList<ConstraintDomain> arrayList = domain.getConstraints();
        if (arrayList != null && !arrayList.isEmpty()) {
            for (ConstraintDomain constraintDomain : arrayList) {
                Expression expression = constraintDomain.getCheckConstraint(this.session, null);
                AlterDomainAddConstraint alterDomainAddConstraint = new AlterDomainAddConstraint(this.session, domain2.getSchema(), false);
                alterDomainAddConstraint.setDomainName(domain2.getName());
                alterDomainAddConstraint.setCheckExpression(expression);
                alterDomainAddConstraint.update();
            }
        }
        DropDomain.copyExpressions(this.session, domain, domain2);
        return true;
    }

    private static boolean copyExpressions(SessionLocal sessionLocal, Domain domain, ColumnTemplate columnTemplate) {
        columnTemplate.setDomain(domain.getDomain());
        Expression expression = domain.getDefaultExpression();
        boolean bl = false;
        if (expression != null && columnTemplate.getDefaultExpression() == null) {
            columnTemplate.setDefaultExpression(sessionLocal, expression);
            bl = true;
        }
        if ((expression = domain.getOnUpdateExpression()) != null && columnTemplate.getOnUpdateExpression() == null) {
            columnTemplate.setOnUpdateExpression(sessionLocal, expression);
            bl = true;
        }
        return bl;
    }

    @Override
    public int getType() {
        return 47;
    }
}

