/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.h2.engine.Constants;
import org.h2.server.web.ConnectionInfo;
import org.h2.util.JdbcUtils;
import org.h2.util.ScriptReader;
import org.h2.util.SortedProperties;
import org.h2.util.StringUtils;
import org.h2.util.Tool;
import org.h2.util.Utils;

public class Shell
extends Tool
implements Runnable {
    private static final int MAX_ROW_BUFFER = 5000;
    private static final int HISTORY_COUNT = 20;
    private static final char BOX_VERTICAL = '|';
    private PrintStream err = System.err;
    private InputStream in = System.in;
    private BufferedReader reader;
    private Connection conn;
    private Statement stat;
    private boolean listMode;
    private int maxColumnSize = 100;
    private final ArrayList<String> history = new ArrayList();
    private boolean stopHide;
    private String serverPropertiesDir = "~";

    public static void main(String ... stringArray) throws SQLException {
        new Shell().runTool(stringArray);
    }

    public void setErr(PrintStream printStream) {
        this.err = printStream;
    }

    public void setIn(InputStream inputStream2) {
        this.in = inputStream2;
    }

    public void setInReader(BufferedReader bufferedReader) {
        this.reader = bufferedReader;
    }

    @Override
    public void runTool(String ... stringArray) throws SQLException {
        String string;
        String string2 = null;
        String string3 = null;
        String string4 = "";
        String string5 = "";
        String string6 = null;
        for (int i = 0; stringArray != null && i < stringArray.length; ++i) {
            string = stringArray[i];
            if (string.equals("-url")) {
                string3 = stringArray[++i];
                continue;
            }
            if (string.equals("-user")) {
                string4 = stringArray[++i];
                continue;
            }
            if (string.equals("-password")) {
                string5 = stringArray[++i];
                continue;
            }
            if (string.equals("-driver")) {
                string2 = stringArray[++i];
                continue;
            }
            if (string.equals("-sql")) {
                string6 = stringArray[++i];
                continue;
            }
            if (string.equals("-properties")) {
                this.serverPropertiesDir = stringArray[++i];
                continue;
            }
            if (string.equals("-help") || string.equals("-?")) {
                this.showUsage();
                return;
            }
            if (string.equals("-list")) {
                this.listMode = true;
                continue;
            }
            this.showUsageAndThrowUnsupportedOption(string);
        }
        if (string3 != null) {
            this.conn = JdbcUtils.getConnection(string2, string3, string4, string5);
            this.stat = this.conn.createStatement();
        }
        if (string6 == null) {
            this.promptLoop();
        } else {
            ScriptReader scriptReader = new ScriptReader(new StringReader(string6));
            while ((string = scriptReader.readStatement()) != null) {
                this.execute(string);
            }
            if (this.conn != null) {
                this.conn.close();
            }
        }
    }

    public void runTool(Connection connection, String ... stringArray) throws SQLException {
        this.conn = connection;
        this.stat = connection.createStatement();
        this.runTool(stringArray);
    }

    private void showHelp() {
        this.println("Commands are case insensitive; SQL statements end with ';'");
        this.println("help or ?      Display this help");
        this.println("list           Toggle result list / stack trace mode");
        this.println("maxwidth       Set maximum column width (default is 100)");
        this.println("autocommit     Enable or disable autocommit");
        this.println("history        Show the last 20 statements");
        this.println("quit or exit   Close the connection and exit");
        this.println("");
    }

    private void promptLoop() {
        this.println("");
        this.println("Welcome to H2 Shell " + Constants.FULL_VERSION);
        this.println("Exit with Ctrl+C");
        if (this.conn != null) {
            this.showHelp();
        }
        String string = null;
        if (this.reader == null) {
            this.reader = new BufferedReader(new InputStreamReader(this.in));
        }
        block8: while (true) {
            try {
                while (true) {
                    int n;
                    int n2;
                    String string2;
                    if (this.conn == null) {
                        this.connect();
                        this.showHelp();
                    }
                    if (string == null) {
                        this.print("sql> ");
                    } else {
                        this.print("...> ");
                    }
                    String string3 = this.readLine();
                    if (string3 == null) break block8;
                    String string4 = string3.trim();
                    if (string4.isEmpty()) continue;
                    boolean bl = string4.endsWith(";");
                    if (bl) {
                        string3 = string3.substring(0, string3.lastIndexOf(59));
                        string4 = string4.substring(0, string4.length() - 1);
                    }
                    if ("exit".equals(string2 = StringUtils.toLowerEnglish(string4)) || "quit".equals(string2)) break block8;
                    if ("help".equals(string2) || "?".equals(string2)) {
                        this.showHelp();
                        continue;
                    }
                    if ("list".equals(string2)) {
                        this.listMode = !this.listMode;
                        this.println("Result list mode is now " + (this.listMode ? "on" : "off"));
                        continue;
                    }
                    if ("history".equals(string2)) {
                        n2 = this.history.size();
                        for (n = 0; n < n2; ++n) {
                            String string5 = this.history.get(n);
                            string5 = string5.replace('\n', ' ').replace('\r', ' ');
                            this.println("#" + (1 + n) + ": " + string5);
                        }
                        if (!this.history.isEmpty()) {
                            this.println("To re-run a statement, type the number and press and enter");
                            continue;
                        }
                        this.println("No history");
                        continue;
                    }
                    if (string2.startsWith("autocommit")) {
                        if ("true".equals(string2 = StringUtils.trimSubstring(string2, "autocommit".length()))) {
                            this.conn.setAutoCommit(true);
                        } else if ("false".equals(string2)) {
                            this.conn.setAutoCommit(false);
                        } else {
                            this.println("Usage: autocommit [true|false]");
                        }
                        this.println("Autocommit is now " + this.conn.getAutoCommit());
                        continue;
                    }
                    if (string2.startsWith("maxwidth")) {
                        string2 = StringUtils.trimSubstring(string2, "maxwidth".length());
                        try {
                            this.maxColumnSize = Integer.parseInt(string2);
                        }
                        catch (NumberFormatException numberFormatException) {
                            this.println("Usage: maxwidth <integer value>");
                        }
                        this.println("Maximum column width is now " + this.maxColumnSize);
                        continue;
                    }
                    n = 1;
                    if (string == null) {
                        if (StringUtils.isNumber(string3)) {
                            n2 = Integer.parseInt(string3);
                            if (n2 == 0 || n2 > this.history.size()) {
                                this.println("Not found");
                            } else {
                                string = this.history.get(n2 - 1);
                                n = 0;
                                this.println(string);
                                bl = true;
                            }
                        } else {
                            string = string3;
                        }
                    } else {
                        string = string + "\n" + string3;
                    }
                    if (!bl) continue;
                    if (n != 0) {
                        this.history.add(0, string);
                        if (this.history.size() > 20) {
                            this.history.remove(20);
                        }
                    }
                    this.execute(string);
                    string = null;
                }
            }
            catch (SQLException sQLException) {
                this.println("SQL Exception: " + sQLException.getMessage());
                string = null;
                continue;
            }
            catch (IOException iOException) {
                this.println(iOException.getMessage());
            }
            catch (Exception exception) {
                this.println("Exception: " + exception.toString());
                exception.printStackTrace(this.err);
            }
            break;
        }
        if (this.conn != null) {
            try {
                this.conn.close();
                this.println("Connection closed");
            }
            catch (SQLException sQLException) {
                this.println("SQL Exception: " + sQLException.getMessage());
                sQLException.printStackTrace(this.err);
            }
        }
    }

    private void connect() throws IOException, SQLException {
        String string = "jdbc:h2:~/test";
        String string2 = "";
        String string3 = null;
        try {
            String string4;
            Properties properties = "null".equals(this.serverPropertiesDir) ? new Properties() : SortedProperties.loadProperties(this.serverPropertiesDir + "/" + ".h2.server.properties");
            String string5 = null;
            boolean bl = false;
            int n = 0;
            while ((string4 = properties.getProperty(Integer.toString(n))) != null) {
                bl = true;
                string5 = string4;
                ++n;
            }
            if (bl) {
                ConnectionInfo connectionInfo = new ConnectionInfo(string5);
                string = connectionInfo.url;
                string2 = connectionInfo.user;
                string3 = connectionInfo.driver;
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.println("[Enter]   " + string);
        this.print("URL       ");
        string = this.readLine(string).trim();
        if (string3 == null) {
            string3 = JdbcUtils.getDriver(string);
        }
        if (string3 != null) {
            this.println("[Enter]   " + string3);
        }
        this.print("Driver    ");
        string3 = this.readLine(string3).trim();
        this.println("[Enter]   " + string2);
        this.print("User      ");
        string2 = this.readLine(string2);
        this.conn = string.startsWith("jdbc:h2:") ? this.connectH2(string3, string, string2) : JdbcUtils.getConnection(string3, string, string2, this.readPassword());
        this.stat = this.conn.createStatement();
        this.println("Connected");
    }

    private Connection connectH2(String string, String string2, String string3) throws IOException, SQLException {
        while (true) {
            String string4 = this.readPassword();
            try {
                return JdbcUtils.getConnection(string, string2 + ";IFEXISTS=TRUE", string3, string4);
            }
            catch (SQLException sQLException) {
                if (sQLException.getErrorCode() == 90146) {
                    this.println("Type the same password again to confirm database creation.");
                    String string5 = this.readPassword();
                    if (string4.equals(string5)) {
                        return JdbcUtils.getConnection(string, string2, string3, string4);
                    }
                    this.println("Passwords don't match. Try again.");
                    continue;
                }
                throw sQLException;
            }
            break;
        }
    }

    protected void print(String string) {
        this.out.print(string);
        this.out.flush();
    }

    private void println(String string) {
        this.out.println(string);
        this.out.flush();
    }

    private String readPassword() throws IOException {
        try {
            Object object = Utils.callStaticMethod("java.lang.System.console", new Object[0]);
            this.print("Password  ");
            char[] cArray = (char[])Utils.callMethod(object, "readPassword", new Object[0]);
            return cArray == null ? null : new String(cArray);
        }
        catch (Exception exception) {
            Thread thread2 = new Thread(this, "Password hider");
            this.stopHide = false;
            thread2.start();
            this.print("Password  > ");
            String string = this.readLine();
            this.stopHide = true;
            try {
                thread2.join();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            this.print("\b\b");
            return string;
        }
    }

    @Override
    public void run() {
        while (!this.stopHide) {
            this.print("\b\b><");
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    private String readLine(String string) throws IOException {
        String string2 = this.readLine();
        return string2.isEmpty() ? string : string2;
    }

    private String readLine() throws IOException {
        String string = this.reader.readLine();
        if (string == null) {
            throw new IOException("Aborted");
        }
        return string;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void execute(String string) {
        block10: {
            if (StringUtils.isWhitespaceOrEmpty(string)) {
                return;
            }
            long l = System.nanoTime();
            try {
                ResultSet resultSet;
                block9: {
                    resultSet = null;
                    try {
                        long l2;
                        if (string.startsWith("@")) {
                            resultSet = JdbcUtils.getMetaResultSet(this.conn, string);
                            this.printResult(resultSet, this.listMode);
                            break block9;
                        }
                        if (this.stat.execute(string)) {
                            resultSet = this.stat.getResultSet();
                            int n = this.printResult(resultSet, this.listMode);
                            l = System.nanoTime() - l;
                            this.println("(" + n + (n == 1 ? " row, " : " rows, ") + TimeUnit.NANOSECONDS.toMillis(l) + " ms)");
                            break block9;
                        }
                        try {
                            l2 = this.stat.getLargeUpdateCount();
                        }
                        catch (UnsupportedOperationException unsupportedOperationException) {
                            l2 = this.stat.getUpdateCount();
                        }
                        l = System.nanoTime() - l;
                        this.println("(Update count: " + l2 + ", " + TimeUnit.NANOSECONDS.toMillis(l) + " ms)");
                    }
                    catch (Throwable throwable) {
                        JdbcUtils.closeSilently(resultSet);
                        throw throwable;
                    }
                }
                JdbcUtils.closeSilently(resultSet);
            }
            catch (SQLException sQLException) {
                this.println("Error: " + sQLException.toString());
                if (!this.listMode) break block10;
                sQLException.printStackTrace(this.err);
            }
        }
    }

    private int printResult(ResultSet resultSet, boolean bl) throws SQLException {
        if (bl) {
            return this.printResultAsList(resultSet);
        }
        return this.printResultAsTable(resultSet);
    }

    private int printResultAsTable(ResultSet resultSet) throws SQLException {
        int n;
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int n2 = resultSetMetaData.getColumnCount();
        boolean bl = false;
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        String[] stringArray = new String[n2];
        for (n = 0; n < n2; ++n) {
            String string = resultSetMetaData.getColumnLabel(n + 1);
            stringArray[n] = string == null ? "" : string;
        }
        arrayList.add(stringArray);
        n = 0;
        while (resultSet.next()) {
            bl |= this.loadRow(resultSet, n2, arrayList);
            if (++n <= 5000) continue;
            this.printRows(arrayList, n2);
            arrayList.clear();
        }
        this.printRows(arrayList, n2);
        arrayList.clear();
        if (bl) {
            this.println("(data is partially truncated)");
        }
        return n;
    }

    private boolean loadRow(ResultSet resultSet, int n, ArrayList<String[]> arrayList) throws SQLException {
        boolean bl = false;
        String[] stringArray = new String[n];
        for (int i = 0; i < n; ++i) {
            String string = resultSet.getString(i + 1);
            if (string == null) {
                string = "null";
            }
            if (n > 1 && string.length() > this.maxColumnSize) {
                string = string.substring(0, this.maxColumnSize);
                bl = true;
            }
            stringArray[i] = string;
        }
        arrayList.add(stringArray);
        return bl;
    }

    private int[] printRows(ArrayList<String[]> arrayList, int n) {
        int[] nArray = new int[n];
        for (int i = 0; i < n; ++i) {
            int n2 = 0;
            for (String[] stringArray : arrayList) {
                n2 = Math.max(n2, stringArray[i].length());
            }
            if (n > 1) {
                n2 = Math.min(this.maxColumnSize, n2);
            }
            nArray[i] = n2;
        }
        for (String[] stringArray : arrayList) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < n; ++i) {
                if (i > 0) {
                    stringBuilder.append(' ').append('|').append(' ');
                }
                String string = stringArray[i];
                stringBuilder.append(string);
                if (i >= n - 1) continue;
                for (int j = string.length(); j < nArray[i]; ++j) {
                    stringBuilder.append(' ');
                }
            }
            this.println(stringBuilder.toString());
        }
        return nArray;
    }

    private int printResultAsList(ResultSet resultSet) throws SQLException {
        String string;
        int n;
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int n2 = 0;
        int n3 = resultSetMetaData.getColumnCount();
        String[] stringArray = new String[n3];
        for (int i = 0; i < n3; ++i) {
            String string2;
            stringArray[i] = string2 = resultSetMetaData.getColumnLabel(i + 1);
            n2 = Math.max(n2, string2.length());
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n4 = 0;
        while (resultSet.next()) {
            stringBuilder.setLength(0);
            if (++n4 > 1) {
                this.println("");
            }
            for (n = 0; n < n3; ++n) {
                if (n > 0) {
                    stringBuilder.append('\n');
                }
                string = stringArray[n];
                stringBuilder.append(string);
                for (int i = string.length(); i < n2; ++i) {
                    stringBuilder.append(' ');
                }
                stringBuilder.append(": ").append(resultSet.getString(n + 1));
            }
            this.println(stringBuilder.toString());
        }
        if (n4 == 0) {
            for (n = 0; n < n3; ++n) {
                if (n > 0) {
                    stringBuilder.append('\n');
                }
                string = stringArray[n];
                stringBuilder.append(string);
            }
            this.println(stringBuilder.toString());
        }
        return n4;
    }
}

