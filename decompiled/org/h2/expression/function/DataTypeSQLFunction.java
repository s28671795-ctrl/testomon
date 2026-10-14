/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.function.FunctionN;
import org.h2.message.DbException;
import org.h2.schema.Constant;
import org.h2.schema.Domain;
import org.h2.schema.FunctionAlias;
import org.h2.schema.Schema;
import org.h2.table.Column;
import org.h2.table.Table;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueToObjectConverter2;
import org.h2.value.ValueVarchar;

public final class DataTypeSQLFunction
extends FunctionN {
    public DataTypeSQLFunction(Expression expression, Expression expression2, Expression expression3, Expression expression4) {
        super(new Expression[]{expression, expression2, expression3, expression4});
    }

    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2, Value value3) {
        TypeInfo typeInfo;
        Schema schema = sessionLocal.getDatabase().findSchema(value.getString());
        if (schema == null) {
            return ValueNull.INSTANCE;
        }
        String string = value2.getString();
        String string2 = value3.getString();
        String string3 = this.args[3].getValue(sessionLocal).getString();
        if (string3 == null) {
            return ValueNull.INSTANCE;
        }
        switch (string2) {
            case "CONSTANT": {
                Constant constant = schema.findConstant(string);
                if (constant == null || !string3.equals("TYPE")) {
                    return ValueNull.INSTANCE;
                }
                typeInfo = constant.getValue().getType();
                break;
            }
            case "DOMAIN": {
                Domain domain = schema.findDomain(string);
                if (domain == null || !string3.equals("TYPE")) {
                    return ValueNull.INSTANCE;
                }
                typeInfo = domain.getDataType();
                break;
            }
            case "ROUTINE": {
                Class<?>[] classArray;
                FunctionAlias.JavaMethod[] javaMethodArray;
                int n;
                int n2 = string.lastIndexOf(95);
                if (n2 < 0) {
                    return ValueNull.INSTANCE;
                }
                FunctionAlias functionAlias = schema.findFunction(string.substring(0, n2));
                if (functionAlias == null) {
                    return ValueNull.INSTANCE;
                }
                try {
                    n = Integer.parseInt(string.substring(n2 + 1));
                }
                catch (NumberFormatException numberFormatException) {
                    return ValueNull.INSTANCE;
                }
                try {
                    javaMethodArray = functionAlias.getJavaMethods();
                }
                catch (DbException dbException) {
                    return ValueNull.INSTANCE;
                }
                if (n < 1 || n > javaMethodArray.length) {
                    return ValueNull.INSTANCE;
                }
                FunctionAlias.JavaMethod javaMethod = javaMethodArray[n - 1];
                if (string3.equals("RESULT")) {
                    typeInfo = javaMethod.getDataType();
                    break;
                }
                try {
                    n = Integer.parseInt(string3);
                }
                catch (NumberFormatException numberFormatException) {
                    return ValueNull.INSTANCE;
                }
                if (n < 1) {
                    return ValueNull.INSTANCE;
                }
                if (!javaMethod.hasConnectionParam()) {
                    --n;
                }
                if (n >= (classArray = javaMethod.getColumnClasses()).length) {
                    return ValueNull.INSTANCE;
                }
                typeInfo = ValueToObjectConverter2.classToType(classArray[n]);
                break;
            }
            case "TABLE": {
                int n;
                Table table = schema.findTableOrView(sessionLocal, string);
                if (table == null) {
                    return ValueNull.INSTANCE;
                }
                try {
                    n = Integer.parseInt(string3);
                }
                catch (NumberFormatException numberFormatException) {
                    return ValueNull.INSTANCE;
                }
                Column[] columnArray = table.getColumns();
                if (n < 1 || n > columnArray.length) {
                    return ValueNull.INSTANCE;
                }
                typeInfo = columnArray[n - 1].getType();
                break;
            }
            default: {
                return ValueNull.INSTANCE;
            }
        }
        return ValueVarchar.get(typeInfo.getSQL(0));
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.optimizeArguments(sessionLocal, false);
        this.type = TypeInfo.TYPE_VARCHAR;
        return this;
    }

    @Override
    public String getName() {
        return "DATA_TYPE_SQL";
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        switch (expressionVisitor.getType()) {
            case 2: {
                return false;
            }
        }
        return true;
    }
}

