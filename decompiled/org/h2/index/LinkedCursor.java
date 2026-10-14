/*
 * Decompiled with CFR 0.152.
 */
package org.h2.index;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.h2.engine.SessionLocal;
import org.h2.index.Cursor;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.table.TableLink;
import org.h2.value.ValueToObjectConverter2;

public class LinkedCursor
implements Cursor {
    private final TableLink tableLink;
    private final PreparedStatement prep;
    private final String sql;
    private final SessionLocal session;
    private final ResultSet rs;
    private Row current;

    LinkedCursor(TableLink tableLink, ResultSet resultSet, SessionLocal sessionLocal, String string, PreparedStatement preparedStatement) {
        this.session = sessionLocal;
        this.tableLink = tableLink;
        this.rs = resultSet;
        this.sql = string;
        this.prep = preparedStatement;
    }

    @Override
    public Row get() {
        return this.current;
    }

    @Override
    public SearchRow getSearchRow() {
        return this.current;
    }

    @Override
    public boolean next() {
        int n;
        try {
            n = this.rs.next();
            if (n == 0) {
                this.rs.close();
                this.tableLink.reusePreparedStatement(this.prep, this.sql);
                this.current = null;
                return false;
            }
        }
        catch (SQLException sQLException) {
            throw DbException.convert(sQLException);
        }
        this.current = this.tableLink.getTemplateRow();
        for (n = 0; n < this.current.getColumnCount(); ++n) {
            this.current.setValue(n, ValueToObjectConverter2.readValue(this.session, this.rs, n + 1, this.tableLink.getColumn(n).getType().getValueType()));
        }
        return true;
    }

    @Override
    public boolean previous() {
        throw DbException.getInternalError(this.toString());
    }
}

