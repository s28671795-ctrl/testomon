/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import java.util.ArrayList;
import org.h2.command.Prepared;
import org.h2.engine.Procedure;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.Parameter;
import org.h2.result.ResultInterface;
import org.h2.util.Utils;

public class ExecuteProcedure
extends Prepared {
    private final ArrayList<Expression> expressions = Utils.newSmallArrayList();
    private Procedure procedure;

    public ExecuteProcedure(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public void setExpression(int n, Expression expression) {
        this.expressions.add(n, expression);
    }

    private void setParameters() {
        Prepared prepared = this.procedure.getPrepared();
        ArrayList<Parameter> arrayList = prepared.getParameters();
        for (int i = 0; arrayList != null && i < arrayList.size() && i < this.expressions.size(); ++i) {
            Expression expression = this.expressions.get(i);
            Parameter parameter = arrayList.get(i);
            parameter.setValue(expression.getValue(this.session));
        }
    }

    @Override
    public boolean isQuery() {
        Prepared prepared = this.procedure.getPrepared();
        return prepared.isQuery();
    }

    @Override
    public long update() {
        this.setParameters();
        Prepared prepared = this.procedure.getPrepared();
        return prepared.update();
    }

    @Override
    public ResultInterface query(long l) {
        this.setParameters();
        Prepared prepared = this.procedure.getPrepared();
        return prepared.query(l);
    }

    @Override
    public boolean isTransactional() {
        return true;
    }

    @Override
    public ResultInterface queryMeta() {
        Prepared prepared = this.procedure.getPrepared();
        return prepared.queryMeta();
    }

    @Override
    public int getType() {
        return 59;
    }
}

