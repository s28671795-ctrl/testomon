/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.jdbc.JDBCUtils;

final class FetchServerOutputListener
implements ExecuteListener {
    private static final JooqLogger log = JooqLogger.getLogger(FetchServerOutputListener.class);

    FetchServerOutputListener() {
    }

    @Override
    public final void executeStart(ExecuteContext ctx) {
        switch (ctx.family()) {
            case ORACLE: {
                try (PreparedStatement s2 = ctx.connection().unwrap(Connection.class).prepareStatement("begin dbms_output.enable(?); end;");){
                    s2.setInt(1, SettingsTools.getFetchServerOutputSize(0, ctx.settings()));
                    s2.executeUpdate();
                    break;
                }
                catch (SQLException e) {
                    throw new DataAccessException("Could not enable DBMS_OUTPUT", e);
                }
            }
        }
    }

    @Override
    public final void executeEnd(ExecuteContext ctx) {
        switch (ctx.family()) {
            case ORACLE: {
                Array a = null;
                try (CallableStatement s2 = ctx.connection().unwrap(Connection.class).prepareCall("declare\n  num integer := ?;\nbegin\n  dbms_output.get_lines(?, num);\n  dbms_output.disable();\nend;\n");){
                    s2.setInt(1, SettingsTools.getFetchServerOutputSize(0, ctx.settings()));
                    s2.registerOutParameter(2, 2003, "DBMSOUTPUT_LINESARRAY");
                    s2.executeUpdate();
                    a = s2.getArray(2);
                    ArrayList<String> list = new ArrayList<String>();
                    for (Object o : (Object[])a.getArray()) {
                        if (o == null) break;
                        if (log.isDebugEnabled()) {
                            log.debug(o.toString());
                        }
                        list.add(o.toString());
                    }
                    ctx.serverOutput(list.toArray(Tools.EMPTY_STRING));
                }
                catch (SQLException e) {
                    try {
                        throw new DataAccessException("Could not disable DBMS_OUTPUT", e);
                    }
                    catch (Throwable throwable) {
                        JDBCUtils.safeFree(a);
                        throw throwable;
                    }
                }
                JDBCUtils.safeFree(a);
                break;
            }
        }
    }
}

