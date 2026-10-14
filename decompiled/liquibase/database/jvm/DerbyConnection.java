/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.jvm;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import liquibase.Scope;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.listener.SqlListener;
import liquibase.servicelocator.LiquibaseService;
import liquibase.util.JdbcUtil;

@LiquibaseService(skip=true)
public class DerbyConnection
extends JdbcConnection {
    public DerbyConnection() {
    }

    public DerbyConnection(Connection connection) {
        super(connection);
    }

    @Override
    public void commit() throws DatabaseException {
        super.commit();
        this.checkPoint();
    }

    @Override
    public void rollback() throws DatabaseException {
        super.rollback();
        this.checkPoint();
    }

    private void checkPoint() throws DatabaseException {
        Statement st = null;
        try {
            st = this.createStatement();
            String sql = "CALL SYSCS_UTIL.SYSCS_CHECKPOINT_DATABASE()";
            for (SqlListener listener : Scope.getCurrentScope().getListeners(SqlListener.class)) {
                listener.writeSqlWillRun("CALL SYSCS_UTIL.SYSCS_CHECKPOINT_DATABASE()");
            }
            st.execute("CALL SYSCS_UTIL.SYSCS_CHECKPOINT_DATABASE()");
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
        finally {
            JdbcUtil.closeStatement(st);
        }
    }
}

