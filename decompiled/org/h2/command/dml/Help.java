/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import org.h2.command.Prepared;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.message.DbException;
import org.h2.result.LocalResult;
import org.h2.result.ResultInterface;
import org.h2.table.Column;
import org.h2.tools.Csv;
import org.h2.util.Utils;
import org.h2.value.TypeInfo;
import org.h2.value.ValueVarchar;

public class Help
extends Prepared {
    private final String[] conditions;
    private final Expression[] expressions;

    public Help(SessionLocal sessionLocal, String[] stringArray) {
        super(sessionLocal);
        this.conditions = stringArray;
        Database database = this.getDatabase();
        this.expressions = new Expression[]{new ExpressionColumn(database, new Column("SECTION", TypeInfo.TYPE_VARCHAR)), new ExpressionColumn(database, new Column("TOPIC", TypeInfo.TYPE_VARCHAR)), new ExpressionColumn(database, new Column("SYNTAX", TypeInfo.TYPE_VARCHAR)), new ExpressionColumn(database, new Column("TEXT", TypeInfo.TYPE_VARCHAR))};
    }

    @Override
    public ResultInterface queryMeta() {
        LocalResult localResult = new LocalResult(this.session, this.expressions, 4, 4);
        localResult.done();
        return localResult;
    }

    @Override
    public ResultInterface query(long l) {
        LocalResult localResult = new LocalResult(this.session, this.expressions, 4, 4);
        try {
            ResultSet resultSet = Help.getTable();
            block2: while (resultSet.next()) {
                String string = resultSet.getString(2).trim();
                for (String string2 : this.conditions) {
                    if (!string.contains(string2)) continue block2;
                }
                localResult.addRow(ValueVarchar.get(resultSet.getString(1).trim(), this.session), ValueVarchar.get(string, this.session), ValueVarchar.get(Help.stripAnnotationsFromSyntax(resultSet.getString(3)), this.session), ValueVarchar.get(Help.processHelpText(resultSet.getString(4)), this.session));
            }
        }
        catch (Exception exception) {
            throw DbException.convert(exception);
        }
        localResult.done();
        return localResult;
    }

    public static String stripAnnotationsFromSyntax(String string) {
        return string.replaceAll("@c@ ", "").replaceAll("@h2@ ", "").replaceAll("@c@", "").replaceAll("@h2@", "").trim();
    }

    public static String processHelpText(String string) {
        int n;
        int n2 = string.length();
        for (n = 0; n < n2; ++n) {
            char c = string.charAt(n);
            if (c == '.') {
                ++n;
                break;
            }
            if (c != '\"') continue;
            while (++n < n2 && string.charAt(n) != '\"') {
            }
        }
        string = string.substring(0, n);
        return string.trim();
    }

    public static ResultSet getTable() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader((InputStream)new ByteArrayInputStream(Utils.getResource("/org/h2/res/help.csv")), StandardCharsets.UTF_8);
        Csv csv = new Csv();
        csv.setLineCommentCharacter('#');
        return csv.read(inputStreamReader, null);
    }

    @Override
    public boolean isQuery() {
        return true;
    }

    @Override
    public boolean isTransactional() {
        return true;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public int getType() {
        return 57;
    }

    @Override
    public boolean isCacheable() {
        return true;
    }
}

