/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Operation0;
import org.h2.expression.function.NamedExpression;
import org.h2.message.DbException;
import org.h2.util.ParserUtil;
import org.h2.util.StringUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarchar;

public final class CurrentGeneralValueSpecification
extends Operation0
implements NamedExpression {
    public static final int CURRENT_CATALOG = 0;
    public static final int CURRENT_PATH = 1;
    public static final int CURRENT_ROLE = 2;
    public static final int CURRENT_SCHEMA = 3;
    public static final int CURRENT_USER = 4;
    public static final int SESSION_USER = 5;
    public static final int SYSTEM_USER = 6;
    private static final String[] NAMES = new String[]{"CURRENT_CATALOG", "CURRENT_PATH", "CURRENT_ROLE", "CURRENT_SCHEMA", "CURRENT_USER", "SESSION_USER", "SYSTEM_USER"};
    private final int specification;

    public CurrentGeneralValueSpecification(int n) {
        this.specification = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        String string;
        switch (this.specification) {
            case 0: {
                string = sessionLocal.getDatabase().getShortName();
                break;
            }
            case 1: {
                String[] stringArray = sessionLocal.getSchemaSearchPath();
                if (stringArray != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < stringArray.length; ++i) {
                        if (i > 0) {
                            stringBuilder.append(',');
                        }
                        ParserUtil.quoteIdentifier(stringBuilder, stringArray[i], 0);
                    }
                    string = stringBuilder.toString();
                    break;
                }
                string = "";
                break;
            }
            case 2: {
                Database database = sessionLocal.getDatabase();
                string = database.getPublicRole().getName();
                if (!database.getSettings().databaseToLower) break;
                string = StringUtils.toLowerEnglish(string);
                break;
            }
            case 3: {
                string = sessionLocal.getCurrentSchemaName();
                break;
            }
            case 4: 
            case 5: 
            case 6: {
                string = sessionLocal.getUser().getName();
                if (!sessionLocal.getDatabase().getSettings().databaseToLower) break;
                string = StringUtils.toLowerEnglish(string);
                break;
            }
            default: {
                throw DbException.getInternalError("specification=" + this.specification);
            }
        }
        return string != null ? ValueVarchar.get(string, sessionLocal) : ValueNull.INSTANCE;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return stringBuilder.append(this.getName());
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

    @Override
    public TypeInfo getType() {
        return TypeInfo.TYPE_VARCHAR;
    }

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public String getName() {
        return NAMES[this.specification];
    }
}

