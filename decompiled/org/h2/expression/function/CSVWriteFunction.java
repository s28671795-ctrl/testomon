/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.sql.SQLException;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.function.FunctionN;
import org.h2.jdbc.JdbcConnection;
import org.h2.message.DbException;
import org.h2.tools.Csv;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueInteger;

public final class CSVWriteFunction
extends FunctionN {
    public CSVWriteFunction() {
        super(new Expression[4]);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        sessionLocal.getUser().checkAdmin();
        JdbcConnection jdbcConnection = sessionLocal.createConnection(false);
        Csv csv = new Csv();
        String string = this.getValue(sessionLocal, 2);
        String string2 = null;
        if (string != null && string.indexOf(61) >= 0) {
            string2 = csv.setOptions(string);
        } else {
            string2 = string;
            String string3 = this.getValue(sessionLocal, 3);
            String string4 = this.getValue(sessionLocal, 4);
            String string5 = this.getValue(sessionLocal, 5);
            String string6 = this.getValue(sessionLocal, 6);
            String string7 = this.getValue(sessionLocal, 7);
            CSVWriteFunction.setCsvDelimiterEscape(csv, string3, string4, string5);
            csv.setNullString(string6);
            if (string7 != null) {
                csv.setLineSeparator(string7);
            }
        }
        try {
            return ValueInteger.get(csv.write(jdbcConnection, this.args[0].getValue(sessionLocal).getString(), this.args[1].getValue(sessionLocal).getString(), string2));
        }
        catch (SQLException sQLException) {
            throw DbException.convert(sQLException);
        }
    }

    private String getValue(SessionLocal sessionLocal, int n) {
        return n < this.args.length ? this.args[n].getValue(sessionLocal).getString() : null;
    }

    public static void setCsvDelimiterEscape(Csv csv, String string, String string2, String string3) {
        char c;
        if (string != null) {
            csv.setFieldSeparatorWrite(string);
            if (!string.isEmpty()) {
                c = string.charAt(0);
                csv.setFieldSeparatorRead(c);
            }
        }
        if (string2 != null) {
            c = string2.isEmpty() ? (char)'\u0000' : string2.charAt(0);
            csv.setFieldDelimiter(c);
        }
        if (string3 != null) {
            c = string3.isEmpty() ? (char)'\u0000' : string3.charAt(0);
            csv.setEscapeCharacter(c);
        }
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.optimizeArguments(sessionLocal, false);
        int n = this.args.length;
        if (n < 2 || n > 8) {
            throw DbException.get(7001, this.getName(), "2..8");
        }
        this.type = TypeInfo.TYPE_INTEGER;
        return this;
    }

    @Override
    public String getName() {
        return "CSVWRITE";
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        if (!super.isEverything(expressionVisitor)) {
            return false;
        }
        switch (expressionVisitor.getType()) {
            case 2: 
            case 5: 
            case 8: {
                return false;
            }
        }
        return true;
    }
}

