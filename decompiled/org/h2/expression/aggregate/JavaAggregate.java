/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.aggregate;

import java.sql.SQLException;
import org.h2.api.Aggregate;
import org.h2.command.query.Select;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.aggregate.AbstractAggregate;
import org.h2.expression.aggregate.AggregateDataCollecting;
import org.h2.jdbc.JdbcConnection;
import org.h2.message.DbException;
import org.h2.schema.UserAggregate;
import org.h2.util.ParserUtil;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;
import org.h2.value.ValueRow;
import org.h2.value.ValueToObjectConverter;

public class JavaAggregate
extends AbstractAggregate {
    private final UserAggregate userAggregate;
    private int[] argTypes;
    private int dataType;
    private JdbcConnection userConnection;

    public JavaAggregate(UserAggregate userAggregate, Expression[] expressionArray, Select select, boolean bl) {
        super(select, expressionArray, bl);
        this.userAggregate = userAggregate;
    }

    @Override
    public int getCost() {
        int n = 5;
        for (Expression expression : this.args) {
            n += expression.getCost();
        }
        if (this.filterCondition != null) {
            n += this.filterCondition.getCost();
        }
        return n;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        ParserUtil.quoteIdentifier(stringBuilder, this.userAggregate.getName(), n).append('(');
        JavaAggregate.writeExpressions(stringBuilder, this.args, n).append(')');
        return this.appendTailConditions(stringBuilder, n, false);
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        if (!super.isEverything(expressionVisitor)) {
            return false;
        }
        switch (expressionVisitor.getType()) {
            case 1: 
            case 2: {
                return false;
            }
            case 7: {
                expressionVisitor.addDependency(this.userAggregate);
                break;
            }
        }
        for (Expression expression : this.args) {
            if (expression == null || expression.isEverything(expressionVisitor)) continue;
            return false;
        }
        return this.filterCondition == null || this.filterCondition.isEverything(expressionVisitor);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        super.optimize(sessionLocal);
        this.userConnection = sessionLocal.createConnection(false);
        int n = this.args.length;
        this.argTypes = new int[n];
        for (int i = 0; i < n; ++i) {
            int n2;
            this.argTypes[i] = n2 = this.args[i].getType().getValueType();
        }
        try {
            Aggregate aggregate = this.getInstance();
            this.dataType = aggregate.getInternalType(this.argTypes);
            this.type = TypeInfo.getTypeInfo(this.dataType);
        }
        catch (SQLException sQLException) {
            throw DbException.convert(sQLException);
        }
        return this;
    }

    private Aggregate getInstance() {
        Aggregate aggregate = this.userAggregate.getInstance();
        try {
            aggregate.init(this.userConnection);
        }
        catch (SQLException sQLException) {
            throw DbException.convert(sQLException);
        }
        return aggregate;
    }

    @Override
    public Value getAggregatedValue(SessionLocal sessionLocal, Object object) {
        try {
            Object object2;
            Aggregate aggregate;
            if (this.distinct) {
                aggregate = this.getInstance();
                object2 = (AggregateDataCollecting)object;
                if (object2 != null) {
                    for (Value value : ((AggregateDataCollecting)object2).values) {
                        if (this.args.length == 1) {
                            aggregate.add(ValueToObjectConverter.valueToDefaultObject(value, this.userConnection, false));
                            continue;
                        }
                        Value[] valueArray = ((ValueRow)value).getList();
                        Object[] objectArray = new Object[this.args.length];
                        int n = this.args.length;
                        for (int i = 0; i < n; ++i) {
                            objectArray[i] = ValueToObjectConverter.valueToDefaultObject(valueArray[i], this.userConnection, false);
                        }
                        aggregate.add(objectArray);
                    }
                }
            } else {
                aggregate = (Aggregate)object;
                if (aggregate == null) {
                    aggregate = this.getInstance();
                }
            }
            if ((object2 = aggregate.getResult()) == null) {
                return ValueNull.INSTANCE;
            }
            return ValueToObjectConverter.objectToValue(sessionLocal, object2, this.dataType);
        }
        catch (SQLException sQLException) {
            throw DbException.convert(sQLException);
        }
    }

    @Override
    protected void updateAggregate(SessionLocal sessionLocal, Object object) {
        this.updateData(sessionLocal, object, null);
    }

    private void updateData(SessionLocal sessionLocal, Object object, Value[] valueArray) {
        try {
            if (this.distinct) {
                AggregateDataCollecting aggregateDataCollecting = (AggregateDataCollecting)object;
                Value[] valueArray2 = new Value[this.args.length];
                Value value = null;
                int n = this.args.length;
                for (int i = 0; i < n; ++i) {
                    valueArray2[i] = value = valueArray == null ? this.args[i].getValue(sessionLocal) : valueArray[i];
                }
                aggregateDataCollecting.add(sessionLocal, this.args.length == 1 ? value : ValueRow.get(valueArray2));
            } else {
                Aggregate aggregate = (Aggregate)object;
                Object[] objectArray = new Object[this.args.length];
                Object object2 = null;
                int n = this.args.length;
                for (int i = 0; i < n; ++i) {
                    Value value = valueArray == null ? this.args[i].getValue(sessionLocal) : valueArray[i];
                    objectArray[i] = object2 = ValueToObjectConverter.valueToDefaultObject(value, this.userConnection, false);
                }
                aggregate.add(this.args.length == 1 ? object2 : objectArray);
            }
        }
        catch (SQLException sQLException) {
            throw DbException.convert(sQLException);
        }
    }

    @Override
    protected void updateGroupAggregates(SessionLocal sessionLocal, int n) {
        super.updateGroupAggregates(sessionLocal, n);
        for (Expression expression : this.args) {
            expression.updateAggregate(sessionLocal, n);
        }
    }

    @Override
    protected int getNumExpressions() {
        int n = this.args.length;
        if (this.filterCondition != null) {
            ++n;
        }
        return n;
    }

    @Override
    protected void rememberExpressions(SessionLocal sessionLocal, Value[] valueArray) {
        int n = this.args.length;
        for (int i = 0; i < n; ++i) {
            valueArray[i] = this.args[i].getValue(sessionLocal);
        }
        if (this.filterCondition != null) {
            valueArray[n] = ValueBoolean.get(this.filterCondition.getBooleanValue(sessionLocal));
        }
    }

    @Override
    protected void updateFromExpressions(SessionLocal sessionLocal, Object object, Value[] valueArray) {
        if (this.filterCondition == null || valueArray[this.getNumExpressions() - 1].isTrue()) {
            this.updateData(sessionLocal, object, valueArray);
        }
    }

    @Override
    protected Object createAggregateData() {
        return this.distinct ? new AggregateDataCollecting(true, false, AggregateDataCollecting.NullCollectionMode.IGNORED) : this.getInstance();
    }
}

