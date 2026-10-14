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
public class HsqlConnection
extends JdbcConnection {
    public HsqlConnection() {
    }

    public HsqlConnection(Connection connection) {
        super(connection);
    }

    @Override
    public void commit() throws DatabaseException {
        super.commit();
        Statement st = null;
        try {
            st = this.createStatement();
            String sql = "CHECKPOINT";
            for (SqlListener listener : Scope.getCurrentScope().getListeners(SqlListener.class)) {
                listener.writeSqlWillRun("CHECKPOINT");
            }
            st.execute("CHECKPOINT");
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
        finally {
            JdbcUtil.closeStatement(st);
        }
    }

    @Override
    public void rollback() throws DatabaseException {
        super.rollback();
        Statement st = null;
        try {
            st = this.createStatement();
            String sql = "CHECKPOINT";
            for (SqlListener listener : Scope.getCurrentScope().getListeners(SqlListener.class)) {
                listener.writeSqlWillRun("CHECKPOINT");
            }
            st.execute("CHECKPOINT");
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
        finally {
            JdbcUtil.closeStatement(st);
        }
    }
}

