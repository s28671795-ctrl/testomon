/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.h2.jdbc.JdbcConnection;
import org.h2.tools.RunScript;
import org.h2.util.Tool;

public class CreateCluster
extends Tool {
    public static void main(String ... stringArray) throws SQLException {
        new CreateCluster().runTool(stringArray);
    }

    @Override
    public void runTool(String ... stringArray) throws SQLException {
        String string = null;
        String string2 = null;
        String string3 = "";
        String string4 = "";
        String string5 = null;
        for (int i = 0; stringArray != null && i < stringArray.length; ++i) {
            String string6 = stringArray[i];
            if (string6.equals("-urlSource")) {
                string = stringArray[++i];
                continue;
            }
            if (string6.equals("-urlTarget")) {
                string2 = stringArray[++i];
                continue;
            }
            if (string6.equals("-user")) {
                string3 = stringArray[++i];
                continue;
            }
            if (string6.equals("-password")) {
                string4 = stringArray[++i];
                continue;
            }
            if (string6.equals("-serverList")) {
                string5 = stringArray[++i];
                continue;
            }
            if (string6.equals("-help") || string6.equals("-?")) {
                this.showUsage();
                return;
            }
            this.showUsageAndThrowUnsupportedOption(string6);
        }
        if (string == null || string2 == null || string5 == null) {
            this.showUsage();
            throw new SQLException("Source URL, target URL, or server list not set");
        }
        CreateCluster.process(string, string2, string3, string4, string5);
    }

    public void execute(String string, String string2, String string3, String string4, String string5) throws SQLException {
        CreateCluster.process(string, string2, string3, string4, string5);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void process(String string, String string2, String string3, String string4, String string5) throws SQLException {
        try (JdbcConnection jdbcConnection = new JdbcConnection(string + ";CLUSTER=''", null, string3, string4, false);
             Statement statement = jdbcConnection.createStatement();){
            statement.execute("SET EXCLUSIVE 2");
            try {
                CreateCluster.performTransfer(statement, string2, string3, string4, string5);
            }
            finally {
                statement.execute("SET EXCLUSIVE FALSE");
            }
        }
    }

    private static void performTransfer(Statement statement, String string, String string2, String string3, String string4) throws SQLException {
        Object object;
        Object object2;
        try (AutoCloseable autoCloseable = new JdbcConnection(string + ";CLUSTER=''", null, string2, string3, false);){
            object2 = ((JdbcConnection)autoCloseable).createStatement();
            object = null;
            try {
                object2.execute("DROP ALL OBJECTS DELETE FILES");
            }
            catch (Throwable throwable) {
                object = throwable;
                throw throwable;
            }
            finally {
                if (object2 != null) {
                    if (object != null) {
                        try {
                            object2.close();
                        }
                        catch (Throwable throwable) {
                            ((Throwable)object).addSuppressed(throwable);
                        }
                    } else {
                        object2.close();
                    }
                }
            }
        }
        try {
            autoCloseable = new PipedReader();
            var6_7 = null;
            try {
                object2 = CreateCluster.startWriter((PipedReader)autoCloseable, statement);
                object = new JdbcConnection(string, null, string2, string3, false);
                Throwable throwable = null;
                try (Statement statement2 = ((JdbcConnection)object).createStatement();){
                    RunScript.execute((Connection)object, (Reader)autoCloseable);
                    try {
                        object2.get();
                    }
                    catch (ExecutionException executionException) {
                        throw new SQLException(executionException.getCause());
                    }
                    catch (InterruptedException interruptedException) {
                        throw new SQLException(interruptedException);
                    }
                    statement.executeUpdate("SET CLUSTER '" + string4 + "'");
                    statement2.executeUpdate("SET CLUSTER '" + string4 + "'");
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (object != null) {
                        if (throwable != null) {
                            try {
                                ((JdbcConnection)object).close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                        } else {
                            ((JdbcConnection)object).close();
                        }
                    }
                }
            }
            catch (Throwable throwable) {
                var6_7 = throwable;
                throw throwable;
            }
            finally {
                if (autoCloseable != null) {
                    if (var6_7 != null) {
                        try {
                            ((PipedReader)autoCloseable).close();
                        }
                        catch (Throwable throwable) {
                            var6_7.addSuppressed(throwable);
                        }
                    } else {
                        ((PipedReader)autoCloseable).close();
                    }
                }
            }
        }
        catch (IOException iOException) {
            throw new SQLException(iOException);
        }
    }

    private static Future<?> startWriter(PipedReader pipedReader, Statement statement) throws IOException {
        ExecutorService executorService2 = Executors.newFixedThreadPool(1);
        PipedWriter pipedWriter = new PipedWriter(pipedReader);
        Future<?> future = executorService2.submit(() -> {
            try (PipedWriter pipedWriter2 = pipedWriter;
                 ResultSet resultSet = statement.executeQuery("SCRIPT");){
                while (resultSet.next()) {
                    pipedWriter2.write(resultSet.getString(1) + "\n");
                }
            }
            catch (IOException | SQLException exception) {
                throw new IllegalStateException("Producing script from the source DB is failing.", exception);
            }
        });
        executorService2.shutdown();
        return future;
    }
}

