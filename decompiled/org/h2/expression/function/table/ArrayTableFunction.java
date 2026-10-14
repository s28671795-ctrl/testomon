/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function.table;

import java.util.ArrayList;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.function.table.TableFunction;
import org.h2.message.DbException;
import org.h2.result.LocalResult;
import org.h2.result.ResultInterface;
import org.h2.table.Column;
import org.h2.util.json.JSONArray;
import org.h2.value.Value;
import org.h2.value.ValueCollectionBase;
import org.h2.value.ValueInteger;
import org.h2.value.ValueJson;
import org.h2.value.ValueNull;

public final class ArrayTableFunction
extends TableFunction {
    public static final int UNNEST = 0;
    public static final int TABLE = 1;
    public static final int TABLE_DISTINCT = 2;
    private Column[] columns;
    private static final String[] NAMES = new String[]{"UNNEST", "TABLE", "TABLE_DISTINCT"};
    private final int function;

    public ArrayTableFunction(int n) {
        super(new Expression[1]);
        this.function = n;
    }

    @Override
    public ResultInterface getValue(SessionLocal sessionLocal) {
        return this.getTable(sessionLocal, false);
    }

    @Override
    public void optimize(SessionLocal sessionLocal) {
        super.optimize(sessionLocal);
        if (this.args.length < 1) {
            throw DbException.get(7001, this.getName(), ">0");
        }
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        if (this.function == 0) {
            super.getSQL(stringBuilder, n);
            if (this.args.length < this.columns.length) {
                stringBuilder.append(" WITH ORDINALITY");
            }
        } else {
            stringBuilder.append(this.getName()).append('(');
            for (int i = 0; i < this.args.length; ++i) {
                if (i > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(this.columns[i].getCreateSQL()).append('=');
                this.args[i].getUnenclosedSQL(stringBuilder, n);
            }
            stringBuilder.append(')');
        }
        return stringBuilder;
    }

    @Override
    public ResultInterface getValueTemplate(SessionLocal sessionLocal) {
        return this.getTable(sessionLocal, true);
    }

    public void setColumns(ArrayList<Column> arrayList) {
        this.columns = arrayList.toArray(new Column[0]);
    }

    private ResultInterface getTable(SessionLocal sessionLocal, boolean bl) {
        int n = this.columns.length;
        Expression[] expressionArray = new Expression[n];
        Database database = sessionLocal.getDatabase();
        for (int i = 0; i < n; ++i) {
            Column column = this.columns[i];
            ExpressionColumn expressionColumn = new ExpressionColumn(database, column);
            expressionArray[i] = expressionColumn;
        }
        LocalResult localResult = new LocalResult(sessionLocal, expressionArray, n, n);
        if (!bl && this.function == 2) {
            localResult.setDistinct();
        }
        if (!bl) {
            Value[] valueArray;
            Value[] valueArray2;
            int n2;
            int n3 = n;
            boolean bl2 = this.function == 0;
            boolean bl3 = false;
            if (bl2 && (n3 = this.args.length) < n) {
                bl3 = true;
            }
            Value[][] valueArrayArray = new Value[n3][];
            int n4 = 0;
            for (n2 = 0; n2 < n3; ++n2) {
                Value[] valueArray3;
                valueArray2 = this.args[n2].getValue(sessionLocal);
                if (valueArray2 == ValueNull.INSTANCE) {
                    valueArrayArray[n2] = Value.EMPTY_VALUES;
                    continue;
                }
                switch (valueArray2.getValueType()) {
                    case 38: {
                        valueArray = valueArray2.convertToAnyJson().getDecomposition();
                        if (valueArray instanceof JSONArray) {
                            valueArray3 = ((JSONArray)valueArray).getArray(Value.class, ValueJson::fromJson);
                            break;
                        }
                        valueArray3 = Value.EMPTY_VALUES;
                        break;
                    }
                    case 40: 
                    case 41: {
                        valueArray3 = ((ValueCollectionBase)valueArray2).getList();
                        break;
                    }
                    default: {
                        valueArray3 = new Value[]{valueArray2};
                    }
                }
                valueArrayArray[n2] = valueArray3;
                n4 = Math.max(n4, valueArray3.length);
            }
            for (n2 = 0; n2 < n4; ++n2) {
                valueArray2 = new Value[n];
                for (int i = 0; i < n3; ++i) {
                    Value value;
                    valueArray = valueArrayArray[i];
                    if (valueArray.length <= n2) {
                        value = ValueNull.INSTANCE;
                    } else {
                        Column column = this.columns[i];
                        value = valueArray[n2];
                        if (!bl2) {
                            value = value.convertForAssignTo(column.getType(), sessionLocal, column);
                        }
                    }
                    valueArray2[i] = value;
                }
                if (bl3) {
                    valueArray2[n3] = ValueInteger.get(n2 + 1);
                }
                localResult.addRow(valueArray2);
            }
        }
        localResult.done();
        return localResult;
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }

    @Override
    public boolean isDeterministic() {
        return true;
    }

    public int getFunctionType() {
        return this.function;
    }
}

