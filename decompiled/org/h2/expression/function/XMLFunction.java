/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.FunctionN;
import org.h2.message.DbException;
import org.h2.util.StringUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarchar;

public final class XMLFunction
extends FunctionN {
    public static final int XMLATTR = 0;
    public static final int XMLCDATA = 1;
    public static final int XMLCOMMENT = 2;
    public static final int XMLNODE = 3;
    public static final int XMLSTARTDOC = 4;
    public static final int XMLTEXT = 5;
    private static final String[] NAMES = new String[]{"XMLATTR", "XMLCDATA", "XMLCOMMENT", "XMLNODE", "XMLSTARTDOC", "XMLTEXT"};
    private final int function;

    public XMLFunction(int n) {
        super(new Expression[4]);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        switch (this.function) {
            case 3: {
                return this.xmlNode(sessionLocal);
            }
            case 4: {
                return ValueVarchar.get(StringUtils.xmlStartDoc(), sessionLocal);
            }
        }
        return super.getValue(sessionLocal);
    }

    private Value xmlNode(SessionLocal sessionLocal) {
        boolean bl;
        String string;
        Value value = this.args[0].getValue(sessionLocal);
        if (value == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        int n = this.args.length;
        String string2 = n >= 2 ? this.args[1].getValue(sessionLocal).getString() : null;
        String string3 = string = n >= 3 ? this.args[2].getValue(sessionLocal).getString() : null;
        if (n >= 4) {
            Value value2 = this.args[3].getValue(sessionLocal);
            if (value2 == ValueNull.INSTANCE) {
                return ValueNull.INSTANCE;
            }
            bl = value2.getBoolean();
        } else {
            bl = true;
        }
        return ValueVarchar.get(StringUtils.xmlNode(value.getString(), string2, string, bl), sessionLocal);
    }

    @Override
    protected Value getValue(SessionLocal sessionLocal, Value value, Value value2, Value value3) {
        switch (this.function) {
            case 0: {
                value = ValueVarchar.get(StringUtils.xmlAttr(value.getString(), value2.getString()), sessionLocal);
                break;
            }
            case 1: {
                value = ValueVarchar.get(StringUtils.xmlCData(value.getString()), sessionLocal);
                break;
            }
            case 2: {
                value = ValueVarchar.get(StringUtils.xmlComment(value.getString()), sessionLocal);
                break;
            }
            case 5: {
                value = ValueVarchar.get(StringUtils.xmlText(value.getString(), value2 != null && value2.getBoolean()), sessionLocal);
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return value;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        int n;
        int n2;
        boolean bl = this.optimizeArguments(sessionLocal, true);
        switch (this.function) {
            case 0: {
                n2 = 2;
                n = 2;
                break;
            }
            case 3: {
                n2 = 1;
                n = 4;
                break;
            }
            case 1: 
            case 2: {
                n2 = 1;
                n = 1;
                break;
            }
            case 4: {
                n2 = 0;
                n = 0;
                break;
            }
            case 5: {
                n2 = 1;
                n = 2;
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        int n3 = this.args.length;
        if (n3 < n2 || n3 > n) {
            throw DbException.get(7001, this.getName(), n2 + ".." + n);
        }
        this.type = TypeInfo.TYPE_VARCHAR;
        if (bl) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

