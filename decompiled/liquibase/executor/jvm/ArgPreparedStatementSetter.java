/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import liquibase.executor.jvm.PreparedStatementSetter;
import liquibase.executor.jvm.SqlParameterValue;
import liquibase.executor.jvm.StatementCreatorUtils;

class ArgPreparedStatementSetter
implements PreparedStatementSetter {
    private final Object[] args;

    public ArgPreparedStatementSetter(Object[] args2) {
        this.args = args2;
    }

    @Override
    public void setValues(PreparedStatement ps) throws SQLException {
        if (this.args != null) {
            for (int i = 0; i < this.args.length; ++i) {
                Object arg = this.args[i];
                if (arg instanceof SqlParameterValue) {
                    SqlParameterValue paramValue = (SqlParameterValue)arg;
                    StatementCreatorUtils.setParameterValue(ps, i + 1, paramValue, paramValue.getValue());
                    continue;
                }
                StatementCreatorUtils.setParameterValue(ps, i + 1, Integer.MIN_VALUE, arg);
            }
        }
    }
}

