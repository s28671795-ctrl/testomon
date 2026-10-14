/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;
import org.h2.message.DbException;
import org.h2.store.fs.FileUtils;
import org.h2.util.IOUtils;
import org.h2.util.JdbcUtils;
import org.h2.util.ScriptReader;
import org.h2.util.StringUtils;
import org.h2.util.Tool;

public class RunScript
extends Tool {
    private boolean showResults;
    private boolean checkResults;

    public static void main(String ... stringArray) throws SQLException {
        new RunScript().runTool(stringArray);
    }

    @Override
    public void runTool(String ... stringArray) throws SQLException {
        String string = null;
        String string2 = "";
        String string3 = "";
        String string4 = "backup.sql";
        String string5 = null;
        boolean bl = false;
        boolean bl2 = false;
        for (int i = 0; stringArray != null && i < stringArray.length; ++i) {
            CharSequence charSequence;
            String string6 = stringArray[i];
            if (string6.equals("-url")) {
                string = stringArray[++i];
                continue;
            }
            if (string6.equals("-user")) {
                string2 = stringArray[++i];
                continue;
            }
            if (string6.equals("-password")) {
                string3 = stringArray[++i];
                continue;
            }
            if (string6.equals("-continueOnError")) {
                bl = true;
                continue;
            }
            if (string6.equals("-checkResults")) {
                this.checkResults = true;
                continue;
            }
            if (string6.equals("-showResults")) {
                this.showResults = true;
                continue;
            }
            if (string6.equals("-script")) {
                string4 = stringArray[++i];
                continue;
            }
            if (string6.equals("-time")) {
                bl2 = true;
                continue;
            }
            if (string6.equals("-driver")) {
                charSequence = stringArray[++i];
                JdbcUtils.loadUserClass((String)charSequence);
                continue;
            }
            if (string6.equals("-options")) {
                charSequence = new StringBuilder();
                ++i;
                while (i < stringArray.length) {
                    ((StringBuilder)charSequence).append(' ').append(stringArray[i]);
                    ++i;
                }
                string5 = ((StringBuilder)charSequence).toString();
                continue;
            }
            if (string6.equals("-help") || string6.equals("-?")) {
                this.showUsage();
                return;
            }
            this.showUsageAndThrowUnsupportedOption(string6);
        }
        if (string == null) {
            this.showUsage();
            throw new SQLException("URL not set");
        }
        long l = System.nanoTime();
        if (string5 != null) {
            RunScript.processRunscript(string, string2, string3, string4, string5);
        } else {
            this.process(string, string2, string3, string4, null, bl);
        }
        if (bl2) {
            l = System.nanoTime() - l;
            this.out.println("Done in " + TimeUnit.NANOSECONDS.toMillis(l) + " ms");
        }
    }

    public static ResultSet execute(Connection connection, Reader reader) throws SQLException {
        String string;
        Statement statement = connection.createStatement();
        ResultSet resultSet = null;
        ScriptReader scriptReader = new ScriptReader(reader);
        while ((string = scriptReader.readStatement()) != null) {
            boolean bl;
            if (StringUtils.isWhitespaceOrEmpty(string) || !(bl = statement.execute(string))) continue;
            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }
            resultSet = statement.getResultSet();
        }
        return resultSet;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void process(Connection connection, String string, boolean bl, Charset charset) throws SQLException, IOException {
        BufferedReader bufferedReader = FileUtils.newBufferedReader(string, charset);
        try {
            this.process(connection, bl, FileUtils.getParent(string), bufferedReader, charset);
        }
        finally {
            IOUtils.closeSilently(bufferedReader);
        }
    }

    private void process(Connection connection, boolean bl, String string, Reader reader, Charset charset) throws SQLException, IOException {
        String string2;
        Statement statement = connection.createStatement();
        ScriptReader scriptReader = new ScriptReader(reader);
        while ((string2 = scriptReader.readStatement()) != null) {
            String string3 = string2.trim();
            if (string3.isEmpty()) continue;
            if (string3.startsWith("@") && StringUtils.toUpperEnglish(string3).startsWith("@INCLUDE")) {
                if (!FileUtils.isAbsolute(string2 = StringUtils.trimSubstring(string2, "@INCLUDE".length()))) {
                    string2 = string + File.separatorChar + string2;
                }
                this.process(connection, string2, bl, charset);
                continue;
            }
            try {
                if (this.showResults && !string3.startsWith("-->")) {
                    this.out.print(string2 + ";");
                }
                if (this.showResults || this.checkResults) {
                    String string4;
                    boolean bl2 = statement.execute(string2);
                    if (!bl2) continue;
                    ResultSet resultSet = statement.getResultSet();
                    int n = resultSet.getMetaData().getColumnCount();
                    StringBuilder stringBuilder = new StringBuilder();
                    while (resultSet.next()) {
                        stringBuilder.append("\n-->");
                        for (int i = 0; i < n; ++i) {
                            string4 = resultSet.getString(i + 1);
                            if (string4 != null) {
                                string4 = StringUtils.replaceAll(string4, "\r\n", "\n");
                                string4 = StringUtils.replaceAll(string4, "\n", "\n-->    ");
                                string4 = StringUtils.replaceAll(string4, "\r", "\r-->    ");
                            }
                            stringBuilder.append(' ').append(string4);
                        }
                    }
                    stringBuilder.append("\n;");
                    String string5 = stringBuilder.toString();
                    if (this.showResults) {
                        this.out.print(string5);
                    }
                    if (!this.checkResults) continue;
                    string4 = scriptReader.readStatement() + ";";
                    string4 = StringUtils.replaceAll(string4, "\r\n", "\n");
                    if ((string4 = StringUtils.replaceAll(string4, "\r", "\n")).equals(string5)) continue;
                    string4 = StringUtils.replaceAll(string4, " ", "+");
                    string5 = StringUtils.replaceAll(string5, " ", "+");
                    throw new SQLException("Unexpected output for:\n" + string2.trim() + "\nGot:\n" + string5 + "\nExpected:\n" + string4);
                }
                statement.execute(string2);
            }
            catch (Exception exception) {
                if (bl) {
                    exception.printStackTrace(this.out);
                    continue;
                }
                throw DbException.toSQLException(exception);
            }
        }
    }

    private static void processRunscript(String string, String string2, String string3, String string4, String string5) throws SQLException {
        try (Connection connection = JdbcUtils.getConnection(null, string, string2, string3);
             Statement statement = connection.createStatement();){
            String string6 = "RUNSCRIPT FROM '" + string4 + "' " + string5;
            statement.execute(string6);
        }
    }

    public static void execute(String string, String string2, String string3, String string4, Charset charset, boolean bl) throws SQLException {
        new RunScript().process(string, string2, string3, string4, charset, bl);
    }

    void process(String string, String string2, String string3, String string4, Charset charset, boolean bl) throws SQLException {
        if (charset == null) {
            charset = StandardCharsets.UTF_8;
        }
        try (Connection connection = JdbcUtils.getConnection(null, string, string2, string3);){
            this.process(connection, string4, bl, charset);
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, string4);
        }
    }
}

