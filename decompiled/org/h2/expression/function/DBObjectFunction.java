/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.function.FunctionN;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarchar;

public final class DBObjectFunction
extends FunctionN {
    public static final int DB_OBJECT_ID = 0;
    public static final int DB_OBJECT_SQL = 1;
    private static final String[] NAMES = new String[]{"DB_OBJECT_ID", "DB_OBJECT_SQL"};
    private final int function;

    public DBObjectFunction(Expression expression, Expression expression2, Expression expression3, int n) {
        Expression[] expressionArray;
        if (expression3 == null) {
            Expression[] expressionArray2 = new Expression[2];
            expressionArray2[0] = expression;
            expressionArray = expressionArray2;
            expressionArray2[1] = expression2;
        } else {
            Expression[] expressionArray3 = new Expression[3];
            expressionArray3[0] = expression;
            expressionArray3[1] = expression2;
            expressionArray = expressionArray3;
            expressionArray3[2] = expression3;
        }
        super(expressionArray);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2, Value value3) {
        DbObject dbObject;
        Object object;
        sessionLocal.getUser().checkAdmin();
        String string = value.getString();
        if (value3 != null) {
            object = sessionLocal.getDatabase().findSchema(value2.getString());
            if (object == null) {
                return ValueNull.INSTANCE;
            }
            String string2 = value3.getString();
            switch (string) {
                case "CONSTANT": {
                    dbObject = ((Schema)object).findConstant(string2);
                    break;
                }
                case "CONSTRAINT": {
                    dbObject = ((Schema)object).findConstraint(sessionLocal, string2);
                    break;
                }
                case "DOMAIN": {
                    dbObject = ((Schema)object).findDomain(string2);
                    break;
                }
                case "INDEX": {
                    dbObject = ((Schema)object).findIndex(sessionLocal, string2);
                    break;
                }
                case "ROUTINE": {
                    dbObject = ((Schema)object).findFunctionOrAggregate(string2);
                    break;
                }
                case "SEQUENCE": {
                    dbObject = ((Schema)object).findSequence(string2);
                    break;
                }
                case "SYNONYM": {
                    dbObject = ((Schema)object).getSynonym(string2);
                    break;
                }
                case "TABLE": {
                    dbObject = ((Schema)object).findTableOrView(sessionLocal, string2);
                    break;
                }
                case "TRIGGER": {
                    dbObject = ((Schema)object).findTrigger(string2);
                    break;
                }
                default: {
                    return ValueNull.INSTANCE;
                }
            }
        } else {
            object = value2.getString();
            Database database = sessionLocal.getDatabase();
            switch (string) {
                case "ROLE": {
                    dbObject = database.findRole((String)object);
                    break;
                }
                case "SETTING": {
                    dbObject = database.findSetting((String)object);
                    break;
                }
                case "SCHEMA": {
                    dbObject = database.findSchema((String)object);
                    break;
                }
                case "USER": {
                    dbObject = database.findUser((String)object);
                    break;
                }
                default: {
                    return ValueNull.INSTANCE;
                }
            }
        }
        if (dbObject == null) {
            return ValueNull.INSTANCE;
        }
        switch (this.function) {
            case 0: {
                return ValueInteger.get(dbObject.getId());
            }
            case 1: {
                object = dbObject.getCreateSQLForMeta();
                return object != null ? ValueVarchar.get((String)object, sessionLocal) : ValueNull.INSTANCE;
            }
        }
        throw DbException.getInternalError("function=" + this.function);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.optimizeArguments(sessionLocal, false);
        this.type = this.function == 0 ? TypeInfo.TYPE_INTEGER : TypeInfo.TYPE_VARCHAR;
        return this;
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        switch (expressionVisitor.getType()) {
            case 2: {
                return false;
            }
        }
        return super.isEverything(expressionVisitor);
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

