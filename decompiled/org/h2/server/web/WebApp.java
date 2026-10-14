/*
 * Decompiled with CFR 0.152.
 */
package org.h2.server.web;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import org.h2.bnf.Bnf;
import org.h2.bnf.context.DbColumn;
import org.h2.bnf.context.DbContents;
import org.h2.bnf.context.DbSchema;
import org.h2.bnf.context.DbTableOrView;
import org.h2.command.ParserBase;
import org.h2.engine.SysProperties;
import org.h2.jdbc.JdbcException;
import org.h2.message.DbException;
import org.h2.security.SHA256;
import org.h2.server.web.ConnectionInfo;
import org.h2.server.web.PageParser;
import org.h2.server.web.WebServer;
import org.h2.server.web.WebSession;
import org.h2.tools.Backup;
import org.h2.tools.ChangeFileEncryption;
import org.h2.tools.ConvertTraceFile;
import org.h2.tools.CreateCluster;
import org.h2.tools.DeleteDbFiles;
import org.h2.tools.Recover;
import org.h2.tools.Restore;
import org.h2.tools.RunScript;
import org.h2.tools.Script;
import org.h2.tools.SimpleResultSet;
import org.h2.util.JdbcUtils;
import org.h2.util.NetUtils;
import org.h2.util.NetworkConnectionInfo;
import org.h2.util.Profiler;
import org.h2.util.ScriptReader;
import org.h2.util.SortedProperties;
import org.h2.util.StringUtils;
import org.h2.util.Tool;
import org.h2.util.Utils;
import org.h2.util.Utils10;
import org.h2.value.DataType;

public class WebApp {
    private static final Comparator<DbTableOrView> SYSTEM_SCHEMA_COMPARATOR = Comparator.comparing(DbTableOrView::getName, String.CASE_INSENSITIVE_ORDER);
    protected final WebServer server;
    protected WebSession session;
    protected Properties attributes;
    protected String mimeType;
    protected boolean cache;
    protected boolean stop;
    protected String headerLanguage;
    private Profiler profiler;

    WebApp(WebServer webServer) {
        this.server = webServer;
    }

    void setSession(WebSession webSession, Properties properties) {
        this.session = webSession;
        this.attributes = properties;
    }

    String processRequest(String string, NetworkConnectionInfo networkConnectionInfo) {
        int n = string.lastIndexOf(46);
        String string2 = n >= 0 ? string.substring(n + 1) : "";
        if ("ico".equals(string2)) {
            this.mimeType = "image/x-icon";
            this.cache = true;
        } else if ("gif".equals(string2)) {
            this.mimeType = "image/gif";
            this.cache = true;
        } else if ("css".equals(string2)) {
            this.cache = true;
            this.mimeType = "text/css";
        } else if ("html".equals(string2) || "do".equals(string2) || "jsp".equals(string2)) {
            this.cache = false;
            this.mimeType = "text/html";
            if (this.session == null) {
                this.session = this.server.createNewSession(NetUtils.ipToShortForm(null, networkConnectionInfo.getClientAddr(), false).toString());
                if (!"notAllowed.jsp".equals(string)) {
                    string = "index.do";
                }
            }
        } else if ("js".equals(string2)) {
            this.cache = true;
            this.mimeType = "text/javascript";
        } else {
            this.cache = true;
            this.mimeType = "application/octet-stream";
        }
        this.trace("mimeType=" + this.mimeType);
        this.trace(string);
        if (string.endsWith(".do")) {
            string = this.process(string, networkConnectionInfo);
        } else if (string.endsWith(".jsp")) {
            switch (string) {
                case "admin.jsp": 
                case "tools.jsp": {
                    if (this.checkAdmin(string)) break;
                    string = this.process("adminLogin.do", networkConnectionInfo);
                }
            }
        }
        return string;
    }

    private static String getComboBox(String[] stringArray, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string2 : stringArray) {
            stringBuilder.append("<option value=\"").append(PageParser.escapeHtmlData(string2)).append('\"');
            if (string2.equals(string)) {
                stringBuilder.append(" selected");
            }
            stringBuilder.append('>').append(PageParser.escapeHtml(string2)).append("</option>");
        }
        return stringBuilder.toString();
    }

    private static String getComboBox(String[][] stringArray, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String[] stringArray2 : stringArray) {
            stringBuilder.append("<option value=\"").append(PageParser.escapeHtmlData(stringArray2[0])).append('\"');
            if (stringArray2[0].equals(string)) {
                stringBuilder.append(" selected");
            }
            stringBuilder.append('>').append(PageParser.escapeHtml(stringArray2[1])).append("</option>");
        }
        return stringBuilder.toString();
    }

    private String process(String string, NetworkConnectionInfo networkConnectionInfo) {
        this.trace("process " + string);
        block38: while (string.endsWith(".do")) {
            switch (string) {
                case "login.do": {
                    string = this.login(networkConnectionInfo);
                    continue block38;
                }
                case "index.do": {
                    string = this.index();
                    continue block38;
                }
                case "logout.do": {
                    string = this.logout();
                    continue block38;
                }
                case "settingRemove.do": {
                    string = this.settingRemove();
                    continue block38;
                }
                case "settingSave.do": {
                    string = this.settingSave();
                    continue block38;
                }
                case "test.do": {
                    string = this.test(networkConnectionInfo);
                    continue block38;
                }
                case "query.do": {
                    string = this.query();
                    continue block38;
                }
                case "tables.do": {
                    string = this.tables();
                    continue block38;
                }
                case "editResult.do": {
                    string = this.editResult();
                    continue block38;
                }
                case "getHistory.do": {
                    string = this.getHistory();
                    continue block38;
                }
                case "admin.do": {
                    string = this.checkAdmin(string) ? this.admin() : "adminLogin.do";
                    continue block38;
                }
                case "adminSave.do": {
                    string = this.checkAdmin(string) ? this.adminSave() : "adminLogin.do";
                    continue block38;
                }
                case "adminStartTranslate.do": {
                    string = this.checkAdmin(string) ? this.adminStartTranslate() : "adminLogin.do";
                    continue block38;
                }
                case "adminShutdown.do": {
                    string = this.checkAdmin(string) ? this.adminShutdown() : "adminLogin.do";
                    continue block38;
                }
                case "autoCompleteList.do": {
                    string = this.autoCompleteList();
                    continue block38;
                }
                case "tools.do": {
                    string = this.checkAdmin(string) ? this.tools() : "adminLogin.do";
                    continue block38;
                }
                case "adminLogin.do": {
                    string = this.adminLogin();
                    continue block38;
                }
            }
            string = "error.jsp";
        }
        this.trace("return " + string);
        return string;
    }

    private boolean checkAdmin(String string) {
        Boolean bl = (Boolean)this.session.get("admin");
        if (bl != null && bl.booleanValue()) {
            return true;
        }
        String string2 = this.server.getKey();
        if (string2 != null && string2.equals(this.session.get("key"))) {
            return true;
        }
        this.session.put("adminBack", string);
        return false;
    }

    private String adminLogin() {
        String string = this.attributes.getProperty("password");
        if (string == null || string.isEmpty() || !this.server.checkAdminPassword(string)) {
            return "adminLogin.jsp";
        }
        String string2 = (String)this.session.remove("adminBack");
        this.session.put("admin", true);
        return string2 != null ? string2 : "admin.do";
    }

    private String autoCompleteList() {
        String string = (String)this.attributes.get("query");
        boolean bl = false;
        String string2 = string.trim();
        if (!string2.isEmpty() && Character.isLowerCase(string2.charAt(0))) {
            bl = true;
        }
        try {
            String string3;
            String string4 = string;
            if (string4.endsWith(";")) {
                string4 = string4 + " ";
            }
            ScriptReader scriptReader = new ScriptReader(new StringReader(string4));
            scriptReader.setSkipRemarks(true);
            String string5 = "";
            while ((string3 = scriptReader.readStatement()) != null) {
                string5 = string3;
            }
            string3 = "";
            if (scriptReader.isInsideRemark()) {
                string3 = scriptReader.isBlockRemark() ? "1#(End Remark)# */\n" + string3 : "1#(Newline)#\n" + string3;
            } else {
                char c;
                Bnf bnf;
                string4 = string5;
                while (string4.length() > 0 && string4.charAt(0) <= ' ') {
                    string4 = string4.substring(1);
                }
                String string6 = string4.trim();
                if (!string6.isEmpty() && Character.isLowerCase(string6.charAt(0))) {
                    bl = true;
                }
                if ((bnf = this.session.getBnf()) == null) {
                    return "autoCompleteList.jsp";
                }
                HashMap<String, String> hashMap = bnf.getNextTokenList(string4);
                String string7 = "";
                if (string4.length() > 0 && !Character.isWhitespace(c = string4.charAt(string4.length() - 1)) && c != '.' && c >= ' ' && c != '\'' && c != '\"') {
                    string7 = " ";
                }
                ArrayList<String> arrayList = new ArrayList<String>(hashMap.size());
                for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                    String string8 = entry.getKey();
                    String string9 = entry.getValue();
                    String string10 = String.valueOf(string8.charAt(0));
                    if (Integer.parseInt(string10) > 2) continue;
                    if (Character.isLetter((string8 = string8.substring(2)).charAt(0)) && bl) {
                        string8 = StringUtils.toLowerEnglish(string8);
                        string9 = StringUtils.toLowerEnglish(string9);
                    }
                    if (string8.equals(string9) && !".".equals(string9)) {
                        string9 = string7 + string9;
                    }
                    string8 = StringUtils.urlEncode(string8);
                    string8 = string8.replace('+', ' ');
                    string9 = StringUtils.urlEncode(string9);
                    string9 = string9.replace('+', ' ');
                    arrayList.add(string10 + "#" + string8 + "#" + string9);
                }
                Collections.sort(arrayList);
                if (string.endsWith("\n") || string2.endsWith(";")) {
                    arrayList.add(0, "1#(Newline)#\n");
                }
                string3 = String.join((CharSequence)"|", arrayList);
            }
            this.session.put("autoCompleteList", string3);
        }
        catch (Throwable throwable) {
            this.server.traceError(throwable);
        }
        return "autoCompleteList.jsp";
    }

    private String admin() {
        this.session.put("port", Integer.toString(this.server.getPort()));
        this.session.put("allowOthers", Boolean.toString(this.server.getAllowOthers()));
        this.session.put("webExternalNames", this.server.getExternalNames());
        this.session.put("ssl", String.valueOf(this.server.getSSL()));
        this.session.put("sessions", this.server.getSessions());
        return "admin.jsp";
    }

    private String adminSave() {
        try {
            SortedProperties sortedProperties = new SortedProperties();
            int n = Integer.decode((String)this.attributes.get("port"));
            sortedProperties.setProperty("webPort", Integer.toString(n));
            this.server.setPort(n);
            boolean bl = Utils.parseBoolean((String)this.attributes.get("allowOthers"), false, false);
            sortedProperties.setProperty("webAllowOthers", String.valueOf(bl));
            this.server.setAllowOthers(bl);
            String string = (String)this.attributes.get("webExternalNames");
            sortedProperties.setProperty("webExternalNames", string);
            this.server.setExternalNames(string);
            boolean bl2 = Utils.parseBoolean((String)this.attributes.get("ssl"), false, false);
            sortedProperties.setProperty("webSSL", String.valueOf(bl2));
            this.server.setSSL(bl2);
            byte[] byArray = this.server.getAdminPassword();
            if (byArray != null) {
                sortedProperties.setProperty("webAdminPassword", StringUtils.convertBytesToHex(byArray));
            }
            this.server.saveProperties(sortedProperties);
        }
        catch (Exception exception) {
            this.trace(exception.toString());
        }
        return this.admin();
    }

    private String tools() {
        try {
            String string = (String)this.attributes.get("tool");
            this.session.put("tool", string);
            String string2 = (String)this.attributes.get("args");
            String[] stringArray = StringUtils.arraySplit(string2, ',', false);
            Tool tool = null;
            if ("Backup".equals(string)) {
                tool = new Backup();
            } else if ("Restore".equals(string)) {
                tool = new Restore();
            } else if ("Recover".equals(string)) {
                tool = new Recover();
            } else if ("DeleteDbFiles".equals(string)) {
                tool = new DeleteDbFiles();
            } else if ("ChangeFileEncryption".equals(string)) {
                tool = new ChangeFileEncryption();
            } else if ("Script".equals(string)) {
                tool = new Script();
            } else if ("RunScript".equals(string)) {
                tool = new RunScript();
            } else if ("ConvertTraceFile".equals(string)) {
                tool = new ConvertTraceFile();
            } else if ("CreateCluster".equals(string)) {
                tool = new CreateCluster();
            } else {
                throw DbException.getInternalError(string);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream((OutputStream)byteArrayOutputStream, false, "UTF-8");
            tool.setOut(printStream);
            try {
                tool.runTool(stringArray);
                printStream.flush();
                String string3 = Utils10.byteArrayOutputStreamToString(byteArrayOutputStream, StandardCharsets.UTF_8);
                String string4 = PageParser.escapeHtml(string3);
                this.session.put("toolResult", string4);
            }
            catch (Exception exception) {
                this.session.put("toolResult", this.getStackTrace(0, exception, true));
            }
        }
        catch (Exception exception) {
            this.server.traceError(exception);
        }
        return "tools.jsp";
    }

    private String adminStartTranslate() {
        Map map;
        Map map2 = map = (Map)Map.class.cast(this.session.map.get("text"));
        String string = this.server.startTranslate(map2);
        this.session.put("translationFile", string);
        return "helpTranslate.jsp";
    }

    protected String adminShutdown() {
        this.server.shutdown();
        return "admin.jsp";
    }

    private String index() {
        String[][] stringArray = WebServer.LANGUAGES;
        String string = (String)this.attributes.get("language");
        Locale locale = this.session.locale;
        if (string != null) {
            if (locale == null || !StringUtils.toLowerEnglish(locale.getLanguage()).equals(string)) {
                locale = new Locale(string, "");
                this.server.readTranslations(this.session, locale.getLanguage());
                this.session.put("language", string);
                this.session.locale = locale;
            }
        } else {
            string = (String)this.session.get("language");
        }
        if (string == null) {
            string = this.headerLanguage;
        }
        this.session.put("languageCombo", WebApp.getComboBox(stringArray, string));
        String[] stringArray2 = this.server.getSettingNames();
        String string2 = this.attributes.getProperty("setting");
        if (string2 == null && stringArray2.length > 0) {
            string2 = stringArray2[0];
        }
        String string3 = WebApp.getComboBox(stringArray2, string2);
        this.session.put("settingsList", string3);
        ConnectionInfo connectionInfo = this.server.getSetting(string2);
        if (connectionInfo == null) {
            connectionInfo = new ConnectionInfo();
        }
        this.session.put("setting", PageParser.escapeHtmlData(string2));
        this.session.put("name", PageParser.escapeHtmlData(string2));
        this.session.put("driver", PageParser.escapeHtmlData(connectionInfo.driver));
        this.session.put("url", PageParser.escapeHtmlData(connectionInfo.url));
        this.session.put("user", PageParser.escapeHtmlData(connectionInfo.user));
        return "index.jsp";
    }

    private String getHistory() {
        int n = Integer.parseInt(this.attributes.getProperty("id"));
        String string = this.session.getCommand(n);
        this.session.put("query", PageParser.escapeHtmlData(string));
        return "query.jsp";
    }

    private static int addColumns(boolean bl, DbTableOrView dbTableOrView, StringBuilder stringBuilder, int n, boolean bl2, StringBuilder stringBuilder2) {
        DbColumn[] dbColumnArray = dbTableOrView.getColumns();
        for (int i = 0; dbColumnArray != null && i < dbColumnArray.length; ++i) {
            DbColumn dbColumn = dbColumnArray[i];
            if (stringBuilder2.length() > 0) {
                stringBuilder2.append(' ');
            }
            stringBuilder2.append(dbColumn.getName());
            String string = WebApp.escapeIdentifier(dbColumn.getName());
            String string2 = bl ? ", 1, 1" : ", 2, 2";
            stringBuilder.append("setNode(").append(n).append(string2).append(", 'column', '").append(PageParser.escapeJavaScript(dbColumn.getName())).append("', 'javascript:ins(\\'").append(string).append("\\')');\n");
            ++n;
            if (!bl || !bl2) continue;
            stringBuilder.append("setNode(").append(n).append(", 2, 2, 'type', '").append(PageParser.escapeJavaScript(dbColumn.getDataType())).append("', null);\n");
            ++n;
        }
        return n;
    }

    private static String escapeIdentifier(String string) {
        return StringUtils.urlEncode(PageParser.escapeJavaScript(string)).replace('+', ' ');
    }

    private static int addIndexes(boolean bl, DatabaseMetaData databaseMetaData, String string, String string2, StringBuilder stringBuilder, int n) throws SQLException {
        Object object;
        String string3;
        ResultSet resultSet;
        try {
            resultSet = databaseMetaData.getIndexInfo(null, string2, string, false, true);
        }
        catch (SQLException sQLException) {
            return n;
        }
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        while (resultSet.next()) {
            string3 = resultSet.getString("INDEX_NAME");
            object = (IndexInfo)hashMap.get(string3);
            if (object == null) {
                int n2 = resultSet.getInt("TYPE");
                Object object2 = n2 == 1 ? "" : (n2 == 2 ? " (${text.tree.hashed})" : (n2 == 3 ? "" : null));
                if (string3 == null || object2 == null) continue;
                object = new IndexInfo();
                ((IndexInfo)object).name = string3;
                ((IndexInfo)object).type = object2 = (resultSet.getBoolean("NON_UNIQUE") ? "${text.tree.nonUnique}" : "${text.tree.unique}") + (String)object2;
                ((IndexInfo)object).columns = resultSet.getString("COLUMN_NAME");
                hashMap.put(string3, object);
                continue;
            }
            ((IndexInfo)object).columns = ((IndexInfo)object).columns + ", " + resultSet.getString("COLUMN_NAME");
        }
        resultSet.close();
        if (hashMap.size() > 0) {
            string3 = bl ? ", 1, 1" : ", 2, 1";
            object = bl ? ", 2, 1" : ", 3, 1";
            String string4 = bl ? ", 3, 2" : ", 4, 2";
            stringBuilder.append("setNode(").append(n).append(string3).append(", 'index_az', '${text.tree.indexes}', null);\n");
            ++n;
            for (IndexInfo indexInfo : hashMap.values()) {
                stringBuilder.append("setNode(").append(n).append((String)object).append(", 'index', '").append(PageParser.escapeJavaScript(indexInfo.name)).append("', null);\n");
                stringBuilder.append("setNode(").append(++n).append(string4).append(", 'type', '").append(indexInfo.type).append("', null);\n");
                stringBuilder.append("setNode(").append(++n).append(string4).append(", 'type', '").append(PageParser.escapeJavaScript(indexInfo.columns)).append("', null);\n");
                ++n;
            }
        }
        return n;
    }

    private int addTablesAndViews(DbSchema dbSchema, boolean bl, StringBuilder stringBuilder, int n) throws SQLException {
        if (dbSchema == null) {
            return n;
        }
        Connection connection = this.session.getConnection();
        DatabaseMetaData databaseMetaData = this.session.getMetaData();
        int n2 = bl ? 0 : 1;
        boolean bl2 = bl || !dbSchema.isSystem;
        String string = ", " + n2 + ", " + (bl2 ? "1" : "2") + ", ";
        String string2 = ", " + (n2 + 1) + ", 2, ";
        DbTableOrView[] dbTableOrViewArray = dbSchema.getTables();
        if (dbTableOrViewArray == null) {
            return n;
        }
        DbContents dbContents = dbSchema.getContents();
        boolean bl3 = dbContents.isOracle();
        boolean bl4 = dbTableOrViewArray.length < SysProperties.CONSOLE_MAX_TABLES_LIST_INDEXES;
        try (PreparedStatement preparedStatement = bl2 ? WebApp.prepareViewDefinitionQuery(connection, dbContents) : null;){
            if (preparedStatement != null) {
                preparedStatement.setString(1, dbSchema.name);
            }
            AtomicReference<PreparedStatement> atomicReference = new AtomicReference<PreparedStatement>(preparedStatement);
            if (dbSchema.isSystem) {
                Arrays.sort(dbTableOrViewArray, SYSTEM_SCHEMA_COMPARATOR);
                for (DbTableOrView dbTableOrView : dbTableOrViewArray) {
                    n = WebApp.addTableOrView(dbSchema, bl, stringBuilder, n, databaseMetaData, false, string, bl3, bl4, dbTableOrView, dbTableOrView.isView(), atomicReference, string2);
                }
            } else {
                for (DbTableOrView dbTableOrView : dbTableOrViewArray) {
                    if (dbTableOrView.isView()) continue;
                    n = WebApp.addTableOrView(dbSchema, bl, stringBuilder, n, databaseMetaData, bl2, string, bl3, bl4, dbTableOrView, false, null, string2);
                }
                for (DbTableOrView dbTableOrView : dbTableOrViewArray) {
                    if (!dbTableOrView.isView()) continue;
                    n = WebApp.addTableOrView(dbSchema, bl, stringBuilder, n, databaseMetaData, bl2, string, bl3, bl4, dbTableOrView, true, atomicReference, string2);
                }
            }
        }
        return n;
    }

    private static PreparedStatement prepareViewDefinitionQuery(Connection connection, DbContents dbContents) {
        if (dbContents.mayHaveStandardViews()) {
            try {
                return connection.prepareStatement("SELECT VIEW_DEFINITION FROM INFORMATION_SCHEMA.VIEWS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?");
            }
            catch (SQLException sQLException) {
                dbContents.setMayHaveStandardViews(false);
            }
        }
        return null;
    }

    private static int addTableOrView(DbSchema dbSchema, boolean bl, StringBuilder stringBuilder, int n, DatabaseMetaData databaseMetaData, boolean bl2, String string, boolean bl3, boolean bl4, DbTableOrView dbTableOrView, boolean bl5, AtomicReference<PreparedStatement> atomicReference, String string2) throws SQLException {
        int n2 = n;
        String string3 = dbTableOrView.getQuotedName();
        if (!bl) {
            string3 = dbSchema.quotedName + '.' + string3;
        }
        string3 = WebApp.escapeIdentifier(string3);
        stringBuilder.append("setNode(").append(n).append(string).append(" '").append(bl5 ? "view" : "table").append("', '").append(PageParser.escapeJavaScript(dbTableOrView.getName())).append("', 'javascript:ins(\\'").append(string3).append("\\',true)');\n");
        ++n;
        if (bl2) {
            StringBuilder stringBuilder2 = new StringBuilder();
            n = WebApp.addColumns(bl, dbTableOrView, stringBuilder, n, bl4, stringBuilder2);
            if (bl5) {
                PreparedStatement preparedStatement = atomicReference.get();
                if (preparedStatement != null) {
                    preparedStatement.setString(2, dbTableOrView.getName());
                    try (ResultSet resultSet = preparedStatement.executeQuery();){
                        String string4;
                        if (resultSet.next() && (string4 = resultSet.getString(1)) != null) {
                            stringBuilder.append("setNode(").append(n).append(string2).append(" 'type', '").append(PageParser.escapeJavaScript(string4)).append("', null);\n");
                            ++n;
                        }
                    }
                    catch (SQLException sQLException) {
                        atomicReference.set(null);
                    }
                }
            } else if (!bl3 && bl4) {
                n = WebApp.addIndexes(bl, databaseMetaData, dbTableOrView.getName(), dbSchema.name, stringBuilder, n);
            }
            stringBuilder.append("addTable('").append(PageParser.escapeJavaScript(dbTableOrView.getName())).append("', '").append(PageParser.escapeJavaScript(stringBuilder2.toString())).append("', ").append(n2).append(");\n");
        }
        return n;
    }

    private String tables() {
        DbContents dbContents = this.session.getContents();
        boolean bl = false;
        try {
            Object object;
            String string = (String)this.session.get("url");
            Connection connection = this.session.getConnection();
            dbContents.readContents(string, connection);
            this.session.loadBnf();
            bl = dbContents.isH2();
            StringBuilder stringBuilder = new StringBuilder().append("setNode(0, 0, 0, 'database', '").append(PageParser.escapeJavaScript(string)).append("', null);\n");
            int n = 1;
            DbSchema dbSchema = dbContents.getDefaultSchema();
            n = this.addTablesAndViews(dbSchema, true, stringBuilder, n);
            DbSchema[] dbSchemaArray = dbContents.getSchemas();
            for (DbSchema dbSchema2 : dbSchemaArray) {
                if (dbSchema2 == dbSchema || dbSchema2 == null) continue;
                stringBuilder.append("setNode(").append(n).append(", 0, 1, 'folder', '").append(PageParser.escapeJavaScript(dbSchema2.name)).append("', null);\n");
                ++n;
                n = this.addTablesAndViews(dbSchema2, false, stringBuilder, n);
            }
            if (bl) {
                object = connection.createStatement();
                Throwable throwable = null;
                try {
                    String string2;
                    String string3;
                    ResultSet resultSet;
                    try {
                        resultSet = object.executeQuery("SELECT SEQUENCE_NAME, BASE_VALUE, INCREMENT FROM INFORMATION_SCHEMA.SEQUENCES ORDER BY SEQUENCE_NAME");
                    }
                    catch (SQLException sQLException) {
                        resultSet = object.executeQuery("SELECT SEQUENCE_NAME, CURRENT_VALUE, INCREMENT FROM INFORMATION_SCHEMA.SEQUENCES ORDER BY SEQUENCE_NAME");
                    }
                    int n2 = 0;
                    while (resultSet.next()) {
                        if (n2 == 0) {
                            stringBuilder.append("setNode(").append(n).append(", 0, 1, 'sequences', '${text.tree.sequences}', null);\n");
                            ++n;
                        }
                        string3 = resultSet.getString(1);
                        string2 = resultSet.getString(2);
                        String string4 = resultSet.getString(3);
                        stringBuilder.append("setNode(").append(n).append(", 1, 1, 'sequence', '").append(PageParser.escapeJavaScript(string3)).append("', null);\n");
                        stringBuilder.append("setNode(").append(++n).append(", 2, 2, 'type', '${text.tree.current}: ").append(PageParser.escapeJavaScript(string2)).append("', null);\n");
                        ++n;
                        if (!"1".equals(string4)) {
                            stringBuilder.append("setNode(").append(n).append(", 2, 2, 'type', '${text.tree.increment}: ").append(PageParser.escapeJavaScript(string4)).append("', null);\n");
                            ++n;
                        }
                        ++n2;
                    }
                    resultSet.close();
                    try {
                        resultSet = object.executeQuery("SELECT USER_NAME, IS_ADMIN FROM INFORMATION_SCHEMA.USERS ORDER BY USER_NAME");
                    }
                    catch (SQLException sQLException) {
                        resultSet = object.executeQuery("SELECT NAME, ADMIN FROM INFORMATION_SCHEMA.USERS ORDER BY NAME");
                    }
                    int n3 = 0;
                    while (resultSet.next()) {
                        if (n3 == 0) {
                            stringBuilder.append("setNode(").append(n).append(", 0, 1, 'users', '${text.tree.users}', null);\n");
                            ++n;
                        }
                        string3 = resultSet.getString(1);
                        string2 = resultSet.getString(2);
                        stringBuilder.append("setNode(").append(n).append(", 1, 1, 'user', '").append(PageParser.escapeJavaScript(string3)).append("', null);\n");
                        ++n;
                        if (string2.equalsIgnoreCase("TRUE")) {
                            stringBuilder.append("setNode(").append(n).append(", 2, 2, 'type', '${text.tree.admin}', null);\n");
                            ++n;
                        }
                        ++n3;
                    }
                    resultSet.close();
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (object != null) {
                        if (throwable != null) {
                            try {
                                object.close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                        } else {
                            object.close();
                        }
                    }
                }
            }
            object = this.session.getMetaData();
            String string5 = object.getDatabaseProductName() + " " + object.getDatabaseProductVersion();
            stringBuilder.append("setNode(").append(n).append(", 0, 0, 'info', '").append(PageParser.escapeJavaScript(string5)).append("', null);\n").append("refreshQueryTables();");
            this.session.put("tree", stringBuilder.toString());
        }
        catch (Exception exception) {
            this.session.put("tree", "");
            this.session.put("error", this.getStackTrace(0, exception, bl));
        }
        return "tables.jsp";
    }

    private String getStackTrace(int n, Throwable throwable, boolean bl) {
        try {
            StringWriter stringWriter = new StringWriter();
            throwable.printStackTrace(new PrintWriter(stringWriter));
            String string = stringWriter.toString();
            string = PageParser.escapeHtml(string);
            if (bl) {
                string = WebApp.linkToSource(string);
            }
            string = StringUtils.replaceAll(string, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
            String string2 = PageParser.escapeHtml(throwable.getMessage());
            String string3 = "<a class=\"error\" href=\"#\" onclick=\"var x=document.getElementById('st" + n + "').style;x.display=x.display==''?'none':'';\">" + string2 + "</a>";
            if (throwable instanceof SQLException) {
                SQLException sQLException = (SQLException)throwable;
                string3 = string3 + " " + sQLException.getSQLState() + "/" + sQLException.getErrorCode();
                if (bl) {
                    int n2 = sQLException.getErrorCode();
                    string3 = string3 + " <a href=\"https://h2database.com/javadoc/org/h2/api/ErrorCode.html#c" + n2 + "\">(${text.a.help})</a>";
                }
            }
            string3 = string3 + "<span style=\"display: none;\" id=\"st" + n + "\"><br />" + string + "</span>";
            string3 = WebApp.formatAsError(string3);
            return string3;
        }
        catch (OutOfMemoryError outOfMemoryError) {
            this.server.traceError(throwable);
            return throwable.toString();
        }
    }

    private static String linkToSource(String string) {
        try {
            StringBuilder stringBuilder = new StringBuilder(string.length());
            int n = string.indexOf("<br />");
            stringBuilder.append(string, 0, n);
            while (true) {
                int n2;
                if ((n2 = string.indexOf("org.h2.", n)) < 0) {
                    stringBuilder.append(string.substring(n));
                    break;
                }
                stringBuilder.append(string, n, n2);
                int n3 = string.indexOf(41, n2);
                if (n3 < 0) {
                    stringBuilder.append(string.substring(n));
                    break;
                }
                String string2 = string.substring(n2, n3);
                int n4 = string2.lastIndexOf(40);
                int n5 = string2.lastIndexOf(46, n4 - 1);
                int n6 = string2.lastIndexOf(46, n5 - 1);
                String string3 = string2.substring(0, n6);
                int n7 = string2.lastIndexOf(58);
                String string4 = string2.substring(n4 + 1, n7);
                String string5 = string2.substring(n7 + 1, string2.length());
                String string6 = string3.replace('.', '/') + "/" + string4;
                stringBuilder.append("<a href=\"https://h2database.com/html/source.html?file=");
                stringBuilder.append(string6);
                stringBuilder.append("&line=");
                stringBuilder.append(string5);
                stringBuilder.append("&build=");
                stringBuilder.append(224);
                stringBuilder.append("\">");
                stringBuilder.append(string2);
                stringBuilder.append("</a>");
                n = n3;
            }
            return stringBuilder.toString();
        }
        catch (Throwable throwable) {
            return string;
        }
    }

    private static String formatAsError(String string) {
        return "<div class=\"error\">" + string + "</div>";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String test(NetworkConnectionInfo networkConnectionInfo) {
        String string = this.attributes.getProperty("driver", "");
        String string2 = this.attributes.getProperty("url", "");
        String string3 = this.attributes.getProperty("user", "");
        String string4 = this.attributes.getProperty("password", "");
        this.session.put("driver", string);
        this.session.put("url", string2);
        this.session.put("user", string3);
        boolean bl = string2.startsWith("jdbc:h2:");
        try {
            Connection connection;
            long l = System.currentTimeMillis();
            String string5 = "";
            String string6 = "";
            Profiler profiler = new Profiler();
            profiler.startCollecting();
            try {
                connection = this.server.getConnection(string, string2, string3, string4, null, networkConnectionInfo);
            }
            finally {
                profiler.stopCollecting();
                string5 = profiler.getTop(3);
            }
            profiler = new Profiler();
            profiler.startCollecting();
            try {
                JdbcUtils.closeSilently(connection);
            }
            finally {
                profiler.stopCollecting();
                string6 = profiler.getTop(3);
            }
            long l2 = System.currentTimeMillis() - l;
            String string7 = l2 > 1000L ? "<a class=\"error\" href=\"#\" onclick=\"var x=document.getElementById('prof').style;x.display=x.display==''?'none':'';\">${text.login.testSuccessful}</a><span style=\"display: none;\" id=\"prof\"><br />" + PageParser.escapeHtml(string5) + "<br />" + PageParser.escapeHtml(string6) + "</span>" : "<div class=\"success\">${text.login.testSuccessful}</div>";
            this.session.put("error", string7);
            return "login.jsp";
        }
        catch (Exception exception) {
            this.session.put("error", this.getLoginError(exception, bl));
            return "login.jsp";
        }
    }

    private String getLoginError(Exception exception, boolean bl) {
        if (exception instanceof JdbcException && ((JdbcException)((Object)exception)).getErrorCode() == 90086) {
            return "${text.login.driverNotFound}<br />" + this.getStackTrace(0, exception, bl);
        }
        return this.getStackTrace(0, exception, bl);
    }

    private String login(NetworkConnectionInfo networkConnectionInfo) {
        String string = this.attributes.getProperty("driver", "");
        String string2 = this.attributes.getProperty("url", "");
        String string3 = this.attributes.getProperty("user", "");
        String string4 = this.attributes.getProperty("password", "");
        this.session.put("autoCommit", "checked");
        this.session.put("autoComplete", "1");
        this.session.put("maxrows", "1000");
        boolean bl = string2.startsWith("jdbc:h2:");
        try {
            Connection connection = this.server.getConnection(string, string2, string3, string4, (String)this.session.get("key"), networkConnectionInfo);
            this.session.setConnection(connection);
            this.session.put("url", string2);
            this.session.put("user", string3);
            this.session.remove("error");
            this.settingSave();
            return "frame.jsp";
        }
        catch (Exception exception) {
            this.session.put("error", this.getLoginError(exception, bl));
            return "login.jsp";
        }
    }

    private String logout() {
        try {
            Connection connection = this.session.getConnection();
            this.session.setConnection(null);
            this.session.remove("conn");
            this.session.remove("result");
            this.session.remove("tables");
            this.session.remove("user");
            this.session.remove("tool");
            if (connection != null) {
                if (this.session.getShutdownServerOnDisconnect()) {
                    this.server.shutdown();
                } else {
                    connection.close();
                }
            }
        }
        catch (Exception exception) {
            this.trace(exception.toString());
        }
        this.session.remove("admin");
        return "index.do";
    }

    private String query() {
        String string = this.attributes.getProperty("sql").trim();
        try {
            Object object;
            ScriptReader scriptReader = new ScriptReader(new StringReader(string));
            final ArrayList<String> arrayList = new ArrayList<String>();
            while ((object = scriptReader.readStatement()) != null) {
                arrayList.add((String)object);
            }
            object = this.session.getConnection();
            if (SysProperties.CONSOLE_STREAM && this.server.getAllowChunked()) {
                String string2 = new String(this.server.getFile("result.jsp"), StandardCharsets.UTF_8);
                int n = string2.indexOf("${result}");
                arrayList.add(0, string2.substring(0, n));
                arrayList.add(string2.substring(n + "${result}".length()));
                this.session.put("chunks", new Iterator<String>((Connection)object){
                    private int i;
                    final /* synthetic */ Connection val$conn;
                    {
                        this.val$conn = connection;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.i < arrayList.size();
                    }

                    @Override
                    public String next() {
                        String string = (String)arrayList.get(this.i++);
                        if (this.i == 1 || this.i == arrayList.size()) {
                            return string;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        WebApp.this.query(this.val$conn, string, this.i - 1, arrayList.size() - 2, stringBuilder);
                        return stringBuilder.toString();
                    }
                });
                return "result.jsp";
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < arrayList.size(); ++i) {
                String string3 = (String)arrayList.get(i);
                this.query((Connection)object, string3, i, arrayList.size(), stringBuilder);
            }
            String string4 = stringBuilder.toString();
            this.session.put("result", string4);
        }
        catch (Throwable throwable) {
            this.session.put("result", this.getStackTrace(0, throwable, this.session.getContents().isH2()));
        }
        return "result.jsp";
    }

    void query(Connection connection, String string, int n, int n2, StringBuilder stringBuilder) {
        if (!string.startsWith("@") || !string.endsWith(".")) {
            stringBuilder.append(PageParser.escapeHtml(string + ";")).append("<br />");
        }
        boolean bl = string.startsWith("@edit");
        stringBuilder.append(this.getResult(connection, n + 1, string, n2 == 1, bl)).append("<br />");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String editResult() {
        String string;
        String string2;
        block11: {
            ResultSet resultSet = this.session.result;
            int n = Integer.parseInt(this.attributes.getProperty("row"));
            int n2 = Integer.parseInt(this.attributes.getProperty("op"));
            string2 = "";
            string = "";
            try {
                if (n2 == 1) {
                    boolean bl;
                    boolean bl2 = bl = n < 0;
                    if (bl) {
                        resultSet.moveToInsertRow();
                    } else {
                        resultSet.absolute(n);
                    }
                    for (int i = 0; i < resultSet.getMetaData().getColumnCount(); ++i) {
                        String string3 = this.attributes.getProperty("r" + n + "c" + (i + 1));
                        this.unescapeData(string3, resultSet, i + 1);
                    }
                    if (bl) {
                        resultSet.insertRow();
                        break block11;
                    } else {
                        resultSet.updateRow();
                    }
                    break block11;
                }
                if (n2 == 2) {
                    resultSet.absolute(n);
                    resultSet.deleteRow();
                } else if (n2 != 3) {
                    // empty if block
                }
            }
            catch (Throwable throwable) {
                string2 = "<br />" + this.getStackTrace(0, throwable, this.session.getContents().isH2());
                string = WebApp.formatAsError(throwable.getMessage());
            }
        }
        String string4 = "@edit " + (String)this.session.get("resultSetSQL");
        Connection connection = this.session.getConnection();
        string2 = string + this.getResult(connection, -1, string4, true, true) + string2;
        this.session.put("result", string2);
        return "result.jsp";
    }

    private int getMaxrows() {
        String string = (String)this.session.get("maxrows");
        return string == null ? 0 : Integer.parseInt(string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String getResult(Connection connection, int n, String string, boolean bl, boolean bl2) {
        String string2;
        try {
            ResultSet resultSet;
            int n2;
            Object object;
            string = string.trim();
            StringBuilder stringBuilder = new StringBuilder();
            string2 = StringUtils.toUpperEnglish(string);
            if (string2.contains("CREATE") || string2.contains("DROP") || string2.contains("ALTER") || string2.contains("RUNSCRIPT")) {
                object = this.attributes.getProperty("jsessionid");
                stringBuilder.append("<script type=\"text/javascript\">parent['h2menu'].location='tables.do?jsessionid=").append((String)object).append("';</script>");
            }
            DbContents dbContents = this.session.getContents();
            object = bl2 || bl && dbContents.isH2() ? connection.createStatement(1004, 1008) : connection.createStatement();
            long l = System.currentTimeMillis();
            boolean bl3 = false;
            Object object2 = null;
            boolean bl4 = false;
            boolean bl5 = false;
            if (JdbcUtils.isBuiltIn(string, "@autocommit_true")) {
                connection.setAutoCommit(true);
                String string3 = "${text.result.autoCommitOn}";
                return string3;
            }
            if (JdbcUtils.isBuiltIn(string, "@autocommit_false")) {
                connection.setAutoCommit(false);
                String string4 = "${text.result.autoCommitOff}";
                return string4;
            }
            if (JdbcUtils.isBuiltIn(string, "@cancel")) {
                object = this.session.executingStatement;
                if (object != null) {
                    object.cancel();
                    stringBuilder.append("${text.result.statementWasCanceled}");
                } else {
                    stringBuilder.append("${text.result.noRunningStatement}");
                }
                String string5 = stringBuilder.toString();
                return string5;
            }
            if (JdbcUtils.isBuiltIn(string, "@edit")) {
                bl4 = true;
                string = StringUtils.trimSubstring(string, "@edit".length());
                this.session.put("resultSetSQL", string);
            }
            if (JdbcUtils.isBuiltIn(string, "@list")) {
                bl5 = true;
                string = StringUtils.trimSubstring(string, "@list".length());
            }
            if (JdbcUtils.isBuiltIn(string, "@meta")) {
                bl3 = true;
                string = StringUtils.trimSubstring(string, "@meta".length());
            }
            if (JdbcUtils.isBuiltIn(string, "@generated")) {
                int n3;
                object2 = true;
                n2 = string.length();
                for (n3 = "@generated".length(); n3 < n2; ++n3) {
                    char c = string.charAt(n3);
                    if (c == '(') {
                        ParserBase parserBase = new ParserBase();
                        object2 = parserBase.parseColumnList(string, n3);
                        n3 = parserBase.getLastParseIndex();
                        break;
                    }
                    if (!Character.isWhitespace(c)) break;
                }
                string = StringUtils.trimSubstring(string, n3);
            } else {
                if (JdbcUtils.isBuiltIn(string, "@history")) {
                    stringBuilder.append(this.getCommandHistoryString());
                    String string6 = stringBuilder.toString();
                    return string6;
                }
                if (JdbcUtils.isBuiltIn(string, "@loop")) {
                    string = StringUtils.trimSubstring(string, "@loop".length());
                    int n4 = string.indexOf(32);
                    int n5 = Integer.decode(string.substring(0, n4));
                    string = StringUtils.trimSubstring(string, n4);
                    String string7 = this.executeLoop(connection, n5, string);
                    return string7;
                }
                if (JdbcUtils.isBuiltIn(string, "@maxrows")) {
                    int n6 = (int)Double.parseDouble(StringUtils.trimSubstring(string, "@maxrows".length()));
                    this.session.put("maxrows", Integer.toString(n6));
                    String string8 = "${text.result.maxrowsSet}";
                    return string8;
                }
                if (JdbcUtils.isBuiltIn(string, "@parameter_meta")) {
                    string = StringUtils.trimSubstring(string, "@parameter_meta".length());
                    PreparedStatement preparedStatement = connection.prepareStatement(string);
                    stringBuilder.append(WebApp.getParameterResultSet(preparedStatement.getParameterMetaData()));
                    String string9 = stringBuilder.toString();
                    return string9;
                }
                if (JdbcUtils.isBuiltIn(string, "@password_hash")) {
                    string = StringUtils.trimSubstring(string, "@password_hash".length());
                    String[] stringArray = JdbcUtils.split(string);
                    String string10 = StringUtils.convertBytesToHex(SHA256.getKeyPasswordHash(stringArray[0], stringArray[1].toCharArray()));
                    return string10;
                }
                if (JdbcUtils.isBuiltIn(string, "@prof_start")) {
                    if (this.profiler != null) {
                        this.profiler.stopCollecting();
                    }
                    this.profiler = new Profiler();
                    this.profiler.startCollecting();
                    String string11 = "Ok";
                    return string11;
                }
                if (JdbcUtils.isBuiltIn(string, "@sleep")) {
                    String string12 = StringUtils.trimSubstring(string, "@sleep".length());
                    int n7 = 1;
                    if (string12.length() > 0) {
                        n7 = Integer.parseInt(string12);
                    }
                    Thread.sleep(n7 * 1000);
                    String string13 = "Ok";
                    return string13;
                }
                if (JdbcUtils.isBuiltIn(string, "@transaction_isolation")) {
                    String string14 = StringUtils.trimSubstring(string, "@transaction_isolation".length());
                    if (string14.length() > 0) {
                        n2 = Integer.parseInt(string14);
                        connection.setTransactionIsolation(n2);
                    }
                    stringBuilder.append("Transaction Isolation: ").append(connection.getTransactionIsolation()).append("<br />");
                    stringBuilder.append(1).append(": read_uncommitted<br />");
                    stringBuilder.append(2).append(": read_committed<br />");
                    stringBuilder.append(4).append(": repeatable_read<br />");
                    stringBuilder.append(6).append(": snapshot<br />");
                    stringBuilder.append(8).append(": serializable");
                }
            }
            if (string.startsWith("@")) {
                resultSet = JdbcUtils.getMetaResultSet(connection, string);
                if (resultSet == null && JdbcUtils.isBuiltIn(string, "@prof_stop") && this.profiler != null) {
                    this.profiler.stopCollecting();
                    SimpleResultSet simpleResultSet = new SimpleResultSet();
                    simpleResultSet.addColumn("Top Stack Trace(s)", 12, 0, 0);
                    simpleResultSet.addRow(this.profiler.getTop(3));
                    resultSet = simpleResultSet;
                    this.profiler = null;
                }
                if (resultSet == null) {
                    stringBuilder.append("?: ").append(string);
                    String string15 = stringBuilder.toString();
                    return string15;
                }
            } else {
                int n8 = this.getMaxrows();
                object.setMaxRows(n8);
                this.session.executingStatement = object;
                n2 = object2 == null ? (int)(object.execute(string) ? 1 : 0) : (object2 instanceof Boolean ? (int)(object.execute(string, (Boolean)object2 != false ? 1 : 2) ? 1 : 0) : (object2 instanceof String[] ? (int)(object.execute(string, (String[])object2) ? 1 : 0) : (int)(object.execute(string, (int[])object2) ? 1 : 0)));
                this.session.addCommand(string);
                if (object2 != null) {
                    resultSet = null;
                    resultSet = object.getGeneratedKeys();
                } else {
                    if (n2 == 0) {
                        long l2;
                        try {
                            l2 = object.getLargeUpdateCount();
                        }
                        catch (UnsupportedOperationException unsupportedOperationException) {
                            l2 = object.getUpdateCount();
                        }
                        stringBuilder.append("${text.result.updateCount}: ").append(l2);
                        l = System.currentTimeMillis() - l;
                        stringBuilder.append("<br />(").append(l).append(" ms)");
                        object.close();
                        String string16 = stringBuilder.toString();
                        return string16;
                    }
                    resultSet = object.getResultSet();
                }
            }
            l = System.currentTimeMillis() - l;
            stringBuilder.append(this.getResultSet(string, resultSet, bl3, bl5, bl4, l, bl));
            if (!bl4) {
                object.close();
            }
            String string17 = stringBuilder.toString();
            return string17;
        }
        catch (Throwable throwable) {
            string2 = this.getStackTrace(n, throwable, this.session.getContents().isH2());
            return string2;
        }
        finally {
            this.session.executingStatement = null;
        }
    }

    private String executeLoop(Connection connection, int n, String string) throws SQLException {
        Object object;
        int n2;
        Object object2;
        boolean bl;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int n3 = 0;
        while (!this.stop && (n3 = string.indexOf(63, n3)) >= 0) {
            if (JdbcUtils.isBuiltIn(string.substring(n3), "?/*rnd*/")) {
                arrayList.add(1);
                string = string.substring(0, n3) + "?" + string.substring(n3 + "/*rnd*/".length() + 1);
            } else {
                arrayList.add(0);
            }
            ++n3;
        }
        Random random = new Random(1L);
        long l = System.currentTimeMillis();
        if (JdbcUtils.isBuiltIn(string, "@statement")) {
            string = StringUtils.trimSubstring(string, "@statement".length());
            bl = false;
            object2 = connection.createStatement();
            for (n2 = 0; !this.stop && n2 < n; ++n2) {
                String string2 = string;
                for (Integer n4 : arrayList) {
                    n3 = string2.indexOf(63);
                    if (n4 == 1) {
                        string2 = string2.substring(0, n3) + random.nextInt(n) + string2.substring(n3 + 1);
                        continue;
                    }
                    string2 = string2.substring(0, n3) + n2 + string2.substring(n3 + 1);
                }
                if (!object2.execute(string2)) continue;
                object = object2.getResultSet();
                while (!this.stop && object.next()) {
                }
                object.close();
            }
        } else {
            bl = true;
            object2 = connection.prepareStatement(string);
            for (n2 = 0; !this.stop && n2 < n; ++n2) {
                for (int i = 0; i < arrayList.size(); ++i) {
                    object = (Integer)arrayList.get(i);
                    if ((Integer)object == 1) {
                        object2.setInt(i + 1, random.nextInt(n));
                        continue;
                    }
                    object2.setInt(i + 1, n2);
                }
                if (this.session.getContents().isSQLite()) {
                    object2.executeUpdate();
                    continue;
                }
                if (!object2.execute()) continue;
                ResultSet resultSet = object2.getResultSet();
                while (!this.stop && resultSet.next()) {
                }
                resultSet.close();
            }
        }
        l = System.currentTimeMillis() - l;
        object2 = new StringBuilder().append(l).append(" ms: ").append(n).append(" * ").append(bl ? "(Prepared) " : "(Statement) ").append('(');
        int n5 = arrayList.size();
        for (n2 = 0; n2 < n5; ++n2) {
            if (n2 > 0) {
                ((StringBuilder)object2).append(", ");
            }
            ((StringBuilder)object2).append((Integer)arrayList.get(n2) == 0 ? "i" : "rnd");
        }
        return ((StringBuilder)object2).append(") ").append(string).toString();
    }

    private String getCommandHistoryString() {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> arrayList = this.session.getCommandHistory();
        stringBuilder.append("<table cellspacing=0 cellpadding=0><tr><th></th><th>Command</th></tr>");
        for (int i = arrayList.size() - 1; i >= 0; --i) {
            String string = arrayList.get(i);
            stringBuilder.append("<tr><td><a href=\"getHistory.do?id=").append(i).append("&jsessionid=${sessionId}\" target=\"h2query\" >").append("<img width=16 height=16 src=\"ico_write.gif\" onmouseover = \"this.className ='icon_hover'\" ").append("onmouseout = \"this.className ='icon'\" class=\"icon\" alt=\"${text.resultEdit.edit}\" ").append("title=\"${text.resultEdit.edit}\" border=\"1\"/></a>").append("</td><td>").append(PageParser.escapeHtml(string)).append("</td></tr>");
        }
        stringBuilder.append("</table>");
        return stringBuilder.toString();
    }

    private static String getParameterResultSet(ParameterMetaData parameterMetaData) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        if (parameterMetaData == null) {
            return "No parameter meta data";
        }
        stringBuilder.append("<table cellspacing=0 cellpadding=0>").append("<tr><th>className</th><th>mode</th><th>type</th>").append("<th>typeName</th><th>precision</th><th>scale</th></tr>");
        for (int i = 0; i < parameterMetaData.getParameterCount(); ++i) {
            stringBuilder.append("</tr><td>").append(parameterMetaData.getParameterClassName(i + 1)).append("</td><td>").append(parameterMetaData.getParameterMode(i + 1)).append("</td><td>").append(parameterMetaData.getParameterType(i + 1)).append("</td><td>").append(parameterMetaData.getParameterTypeName(i + 1)).append("</td><td>").append(parameterMetaData.getPrecision(i + 1)).append("</td><td>").append(parameterMetaData.getScale(i + 1)).append("</td></tr>");
        }
        stringBuilder.append("</table>");
        return stringBuilder.toString();
    }

    private String getResultSet(String string, ResultSet wrapper, boolean bl, boolean bl2, boolean bl3, long l, boolean bl4) throws SQLException {
        int n;
        int n2;
        ResultSetMetaData resultSetMetaData;
        int n3 = this.getMaxrows();
        l = System.currentTimeMillis() - l;
        StringBuilder stringBuilder = new StringBuilder();
        if (bl3) {
            stringBuilder.append("<form id=\"editing\" name=\"editing\" method=\"post\" action=\"editResult.do?jsessionid=${sessionId}\" id=\"mainForm\" target=\"h2result\"><input type=\"hidden\" name=\"op\" value=\"1\" /><input type=\"hidden\" name=\"row\" value=\"\" /><table class=\"resultSet\" cellspacing=\"0\" cellpadding=\"0\" id=\"editTable\">");
        } else {
            stringBuilder.append("<table class=\"resultSet\" cellspacing=\"0\" cellpadding=\"0\">");
        }
        if (bl) {
            resultSetMetaData = new SimpleResultSet();
            ((SimpleResultSet)resultSetMetaData).addColumn("#", 4, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("label", 12, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("catalog", 12, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("schema", 12, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("table", 12, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("column", 12, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("type", 4, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("typeName", 12, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("class", 12, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("precision", 4, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("scale", 4, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("displaySize", 4, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("autoIncrement", 16, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("caseSensitive", 16, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("currency", 16, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("nullable", 4, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("readOnly", 16, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("searchable", 16, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("signed", 16, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("writable", 16, 0, 0);
            ((SimpleResultSet)resultSetMetaData).addColumn("definitelyWritable", 16, 0, 0);
            ResultSetMetaData resultSetMetaData2 = wrapper.getMetaData();
            for (n2 = 1; n2 <= resultSetMetaData2.getColumnCount(); ++n2) {
                ((SimpleResultSet)resultSetMetaData).addRow(n2, resultSetMetaData2.getColumnLabel(n2), resultSetMetaData2.getCatalogName(n2), resultSetMetaData2.getSchemaName(n2), resultSetMetaData2.getTableName(n2), resultSetMetaData2.getColumnName(n2), resultSetMetaData2.getColumnType(n2), resultSetMetaData2.getColumnTypeName(n2), resultSetMetaData2.getColumnClassName(n2), resultSetMetaData2.getPrecision(n2), resultSetMetaData2.getScale(n2), resultSetMetaData2.getColumnDisplaySize(n2), resultSetMetaData2.isAutoIncrement(n2), resultSetMetaData2.isCaseSensitive(n2), resultSetMetaData2.isCurrency(n2), resultSetMetaData2.isNullable(n2), resultSetMetaData2.isReadOnly(n2), resultSetMetaData2.isSearchable(n2), resultSetMetaData2.isSigned(n2), resultSetMetaData2.isWritable(n2), resultSetMetaData2.isDefinitelyWritable(n2));
            }
            wrapper = resultSetMetaData;
        }
        resultSetMetaData = wrapper.getMetaData();
        int n4 = resultSetMetaData.getColumnCount();
        n2 = 0;
        if (bl2) {
            stringBuilder.append("<tr><th>Column</th><th>Data</th></tr><tr>");
            while (wrapper.next() && (n3 <= 0 || n2 < n3)) {
                stringBuilder.append("<tr><td>Row #</td><td>").append(++n2).append("</tr>");
                for (n = 0; n < n4; ++n) {
                    stringBuilder.append("<tr><td>").append(PageParser.escapeHtml(resultSetMetaData.getColumnLabel(n + 1))).append("</td><td>").append(WebApp.escapeData(wrapper, n + 1)).append("</td></tr>");
                }
            }
        } else {
            stringBuilder.append("<tr>");
            if (bl3) {
                stringBuilder.append("<th>${text.resultEdit.action}</th>");
            }
            for (n = 0; n < n4; ++n) {
                stringBuilder.append("<th>").append(PageParser.escapeHtml(resultSetMetaData.getColumnLabel(n + 1))).append("</th>");
            }
            stringBuilder.append("</tr>");
            while (wrapper.next() && (n3 <= 0 || n2 < n3)) {
                ++n2;
                stringBuilder.append("<tr>");
                if (bl3) {
                    stringBuilder.append("<td>").append("<img onclick=\"javascript:editRow(").append(wrapper.getRow()).append(",'${sessionId}', '${text.resultEdit.save}', '${text.resultEdit.cancel}'").append(")\" width=16 height=16 src=\"ico_write.gif\" onmouseover = \"this.className ='icon_hover'\" onmouseout = \"this.className ='icon'\" class=\"icon\" alt=\"${text.resultEdit.edit}\" title=\"${text.resultEdit.edit}\" border=\"1\"/>").append("<img onclick=\"javascript:deleteRow(").append(wrapper.getRow()).append(",'${sessionId}', '${text.resultEdit.delete}', '${text.resultEdit.cancel}'").append(")\" width=16 height=16 src=\"ico_remove.gif\" onmouseover = \"this.className ='icon_hover'\" onmouseout = \"this.className ='icon'\" class=\"icon\" alt=\"${text.resultEdit.delete}\" title=\"${text.resultEdit.delete}\" border=\"1\" /></a>").append("</td>");
                }
                for (n = 0; n < n4; ++n) {
                    stringBuilder.append("<td>").append(WebApp.escapeData(wrapper, n + 1)).append("</td>");
                }
                stringBuilder.append("</tr>");
            }
        }
        n = 0;
        try {
            if (!this.session.getContents().isDB2()) {
                n = wrapper.getConcurrency() == 1008 && wrapper.getType() != 1003 ? 1 : 0;
            }
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
        if (bl3) {
            ResultSet resultSet = this.session.result;
            if (resultSet != null) {
                resultSet.close();
            }
            this.session.result = wrapper;
        } else {
            wrapper.close();
        }
        if (bl3) {
            stringBuilder.append("<tr><td>").append("<img onclick=\"javascript:editRow(-1, '${sessionId}', '${text.resultEdit.save}', '${text.resultEdit.cancel}'").append(")\" width=16 height=16 src=\"ico_add.gif\" onmouseover = \"this.className ='icon_hover'\" onmouseout = \"this.className ='icon'\" class=\"icon\" alt=\"${text.resultEdit.add}\" title=\"${text.resultEdit.add}\" border=\"1\"/>").append("</td>");
            for (int i = 0; i < n4; ++i) {
                stringBuilder.append("<td></td>");
            }
            stringBuilder.append("</tr>");
        }
        stringBuilder.append("</table>");
        if (bl3) {
            stringBuilder.append("</form>");
        }
        if (n2 == 0) {
            stringBuilder.append("(${text.result.noRows}");
        } else if (n2 == 1) {
            stringBuilder.append("(${text.result.1row}");
        } else {
            stringBuilder.append('(').append(n2).append(" ${text.result.rows}");
        }
        stringBuilder.append(", ");
        l = System.currentTimeMillis() - l;
        stringBuilder.append(l).append(" ms)");
        if (!bl3 && n != 0 && bl4) {
            stringBuilder.append("<br /><br /><form name=\"editResult\" method=\"post\" action=\"query.do?jsessionid=${sessionId}\" target=\"h2result\"><input type=\"submit\" class=\"button\" value=\"${text.resultEdit.editResult}\" /><input type=\"hidden\" name=\"sql\" value=\"@edit ").append(PageParser.escapeHtmlData(string)).append("\" /></form>");
        }
        return stringBuilder.toString();
    }

    private String settingSave() {
        ConnectionInfo connectionInfo = new ConnectionInfo();
        connectionInfo.name = this.attributes.getProperty("name", "");
        connectionInfo.driver = this.attributes.getProperty("driver", "");
        connectionInfo.url = this.attributes.getProperty("url", "");
        connectionInfo.user = this.attributes.getProperty("user", "");
        this.server.updateSetting(connectionInfo);
        this.attributes.put("setting", connectionInfo.name);
        this.server.saveProperties(null);
        return "index.do";
    }

    private static String escapeData(ResultSet resultSet, int n) throws SQLException {
        if (DataType.isBinaryColumn(resultSet.getMetaData(), n)) {
            byte[] byArray = resultSet.getBytes(n);
            if (byArray == null) {
                return "<i>null</i>";
            }
            if (byArray.length > 50000) {
                return "<div style='display: none'>=+</div>" + StringUtils.convertBytesToHex(byArray, 3) + "... (" + byArray.length + " ${text.result.bytes})";
            }
            return StringUtils.convertBytesToHex(byArray);
        }
        String string = resultSet.getString(n);
        if (string == null) {
            return "<i>null</i>";
        }
        if (string.length() > 100000) {
            return "<div style='display: none'>=+</div>" + PageParser.escapeHtml(string.substring(0, 100)) + "... (" + string.length() + " ${text.result.characters})";
        }
        if (string.equals("null") || string.startsWith("= ") || string.startsWith("=+")) {
            return "<div style='display: none'>= </div>" + PageParser.escapeHtml(string);
        }
        if (string.equals("")) {
            return "";
        }
        return PageParser.escapeHtml(string);
    }

    private void unescapeData(String string, ResultSet resultSet, int n) throws SQLException {
        ResultSetMetaData resultSetMetaData;
        if (string.equals("null")) {
            resultSet.updateNull(n);
            return;
        }
        if (string.startsWith("=+")) {
            return;
        }
        if (string.equals("=*")) {
            int n2 = resultSet.getMetaData().getColumnType(n);
            switch (n2) {
                case 92: {
                    resultSet.updateString(n, "12:00:00");
                    break;
                }
                case 91: 
                case 93: {
                    resultSet.updateString(n, "2001-01-01");
                    break;
                }
                default: {
                    resultSet.updateString(n, "1");
                }
            }
            return;
        }
        if (string.startsWith("= ")) {
            string = string.substring(2);
        }
        if (DataType.isBinaryColumn(resultSetMetaData = resultSet.getMetaData(), n)) {
            resultSet.updateBytes(n, StringUtils.convertHexToBytes(string));
            return;
        }
        int n3 = resultSetMetaData.getColumnType(n);
        if (this.session.getContents().isH2()) {
            resultSet.updateString(n, string);
            return;
        }
        switch (n3) {
            case -5: {
                resultSet.updateLong(n, (long)Long.decode(string));
                break;
            }
            case 3: {
                resultSet.updateBigDecimal(n, new BigDecimal(string));
                break;
            }
            case 6: 
            case 8: {
                resultSet.updateDouble(n, Double.parseDouble(string));
                break;
            }
            case 7: {
                resultSet.updateFloat(n, Float.parseFloat(string));
                break;
            }
            case 4: {
                resultSet.updateInt(n, (int)Integer.decode(string));
                break;
            }
            case -6: {
                resultSet.updateShort(n, (short)Short.decode(string));
                break;
            }
            default: {
                resultSet.updateString(n, string);
            }
        }
    }

    private String settingRemove() {
        String string = this.attributes.getProperty("name", "");
        this.server.removeSetting(string);
        ArrayList<ConnectionInfo> arrayList = this.server.getSettings();
        if (!arrayList.isEmpty()) {
            this.attributes.put("setting", arrayList.get(0));
        }
        this.server.saveProperties(null);
        return "index.do";
    }

    String getMimeType() {
        return this.mimeType;
    }

    boolean getCache() {
        return this.cache;
    }

    WebSession getSession() {
        return this.session;
    }

    private void trace(String string) {
        this.server.trace(string);
    }

    static class IndexInfo {
        String name;
        String type;
        String columns;

        IndexInfo() {
        }
    }
}

