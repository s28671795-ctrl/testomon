/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import liquibase.exception.DatabaseException;
import liquibase.executor.jvm.PreparedStatementSetter;
import liquibase.executor.jvm.StatementCreatorUtils;

class ArgTypePreparedStatementSetter
implements PreparedStatementSetter {
    private final Object[] args;
    private final int[] argTypes;

    public ArgTypePreparedStatementSetter(Object[] args2, int[] argTypes) throws DatabaseException {
        if (args2 != null && argTypes == null || args2 == null && argTypes != null || args2 != null && args2.length != argTypes.length) {
            throw new DatabaseException("args and argTypes parameters must match");
        }
        this.args = args2;
        this.argTypes = argTypes;
    }

    @Override
    public void setValues(PreparedStatement ps) throws SQLException {
        int argIndx = 1;
        if (this.args != null) {
            for (int i = 0; i < this.args.length; ++i) {
                Object arg = this.args[i];
                if (arg instanceof Collection && this.argTypes[i] != 2003) {
                    Collection entries = (Collection)arg;
                    for (Object entry : entries) {
                        StatementCreatorUtils.setParameterValue(ps, argIndx++, this.argTypes[i], entry);
                    }
                    continue;
                }
                StatementCreatorUtils.setParameterValue(ps, argIndx++, this.argTypes[i], arg);
            }
        }
    }
}

