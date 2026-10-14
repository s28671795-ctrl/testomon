/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.function.CSVWriteFunction;
import org.h2.expression.function.table.TableFunction;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.schema.FunctionAlias;
import org.h2.tools.Csv;
import org.h2.util.StringUtils;

public final class CSVReadFunction
extends TableFunction {
    public CSVReadFunction() {
        super(new Expression[4]);
    }

    @Override
    public ResultInterface getValue(SessionLocal sessionLocal) {
        String[] stringArray;
        sessionLocal.getUser().checkAdmin();
        String string = this.getValue(sessionLocal, 0);
        String string2 = this.getValue(sessionLocal, 1);
        Csv csv = new Csv();
        String string3 = this.getValue(sessionLocal, 2);
        String string4 = null;
        if (string3 != null && string3.indexOf(61) >= 0) {
            string4 = csv.setOptions(string3);
        } else {
            string4 = string3;
            String string5 = this.getValue(sessionLocal, 3);
            stringArray = this.getValue(sessionLocal, 4);
            String string6 = this.getValue(sessionLocal, 5);
            String string7 = this.getValue(sessionLocal, 6);
            CSVWriteFunction.setCsvDelimiterEscape(csv, string5, (String)stringArray, string6);
            csv.setNullString(string7);
        }
        char c = csv.getFieldSeparatorRead();
        stringArray = StringUtils.arraySplit(string2, c, true);
        try {
            return FunctionAlias.JavaMethod.resultSetToResult(sessionLocal, csv.read(string, stringArray, string4), Integer.MAX_VALUE);
        }
        catch (SQLException sQLException) {
            throw DbException.convert(sQLException);
        }
    }

    private String getValue(SessionLocal sessionLocal, int n) {
        return CSVReadFunction.getValue(sessionLocal, this.args, n);
    }

    @Override
    public void optimize(SessionLocal sessionLocal) {
        super.optimize(sessionLocal);
        int n = this.args.length;
        if (n < 1 || n > 7) {
            throw DbException.get(7001, this.getName(), "1..7");
        }
    }

    @Override
    public ResultInterface getValueTemplate(SessionLocal sessionLocal) {
        Object object;
        String[] stringArray;
        sessionLocal.getUser().checkAdmin();
        String string = CSVReadFunction.getValue(sessionLocal, this.args, 0);
        if (string == null) {
            throw DbException.get(90012, "fileName");
        }
        String string2 = CSVReadFunction.getValue(sessionLocal, this.args, 1);
        Csv csv = new Csv();
        String string3 = CSVReadFunction.getValue(sessionLocal, this.args, 2);
        String string4 = null;
        if (string3 != null && string3.indexOf(61) >= 0) {
            string4 = csv.setOptions(string3);
        } else {
            string4 = string3;
            String string5 = CSVReadFunction.getValue(sessionLocal, this.args, 3);
            stringArray = CSVReadFunction.getValue(sessionLocal, this.args, 4);
            object = CSVReadFunction.getValue(sessionLocal, this.args, 5);
            CSVWriteFunction.setCsvDelimiterEscape(csv, string5, (String)stringArray, (String)object);
        }
        char c = csv.getFieldSeparatorRead();
        stringArray = StringUtils.arraySplit(string2, c, true);
        try (ResultSet resultSet = csv.read(string, stringArray, string4);){
            object = FunctionAlias.JavaMethod.resultSetToResult(sessionLocal, resultSet, 0);
        }
        catch (SQLException sQLException) {
            throw DbException.convert(sQLException);
        }
        finally {
            csv.close();
        }
        return object;
    }

    private static String getValue(SessionLocal sessionLocal, Expression[] expressionArray, int n) {
        return n < expressionArray.length ? expressionArray[n].getValue(sessionLocal).getString() : null;
    }

    @Override
    public String getName() {
        return "CSVREAD";
    }

    @Override
    public boolean isDeterministic() {
        return false;
    }
}

