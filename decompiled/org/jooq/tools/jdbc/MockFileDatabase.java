/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.Source;
import org.jooq.exception.MockFileDatabaseException;
import org.jooq.impl.DSL;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockFileDatabaseConfiguration;
import org.jooq.tools.jdbc.MockResult;

public class MockFileDatabase
implements MockDataProvider {
    private static final JooqLogger log = JooqLogger.getLogger(MockFileDatabase.class);
    private final MockFileDatabaseConfiguration configuration;
    private final Map<String, List<MockResult>> matchExactly;
    private final Map<Pattern, List<MockResult>> matchPattern;
    private final DSLContext create;
    @Deprecated
    private String nullLiteral;
    private static final Pattern END_OF_STATEMENT = Pattern.compile("^(.*?);[ \t]*$");

    public MockFileDatabase(File file) throws IOException {
        this(new MockFileDatabaseConfiguration().source(file));
    }

    public MockFileDatabase(File file, String encoding) throws IOException {
        this(new MockFileDatabaseConfiguration().source(file, encoding));
    }

    public MockFileDatabase(InputStream stream) throws IOException {
        this(new MockFileDatabaseConfiguration().source(stream));
    }

    public MockFileDatabase(InputStream stream, String encoding) throws IOException {
        this(new MockFileDatabaseConfiguration().source(stream, encoding));
    }

    public MockFileDatabase(Reader reader) throws IOException {
        this(new MockFileDatabaseConfiguration().source(reader));
    }

    public MockFileDatabase(String string) throws IOException {
        this(new MockFileDatabaseConfiguration().source(string));
    }

    public MockFileDatabase(Source source2) throws IOException {
        this(new MockFileDatabaseConfiguration().source(source2));
    }

    @Deprecated
    public MockFileDatabase nullLiteral(String literal) {
        this.nullLiteral = literal;
        return this;
    }

    public MockFileDatabase(MockFileDatabaseConfiguration configuration) throws IOException {
        this.configuration = configuration;
        this.matchExactly = new LinkedHashMap<String, List<MockResult>>();
        this.matchPattern = new LinkedHashMap<Pattern, List<MockResult>>();
        this.create = DSL.using(SQLDialect.DEFAULT);
        this.load();
    }

    private void load() throws FileNotFoundException, IOException {
        new Object(){
            private StringBuilder currentSQL = new StringBuilder();
            private StringBuilder currentResult = new StringBuilder();
            private String previousSQL = null;

            private void load() throws FileNotFoundException, IOException {
                try {
                    while (true) {
                        String line;
                        if ((line = this.readLine()) == null) {
                            if (this.currentResult.length() > 0) {
                                this.loadOneResult("");
                                this.currentResult = new StringBuilder();
                            }
                            break;
                        }
                        if (line.startsWith("#")) continue;
                        if (line.startsWith(">")) {
                            this.currentResult.append(line.substring(2));
                            this.currentResult.append("\n");
                            continue;
                        }
                        if (line.startsWith("@")) {
                            this.loadOneResult(line);
                            this.currentResult = new StringBuilder();
                            continue;
                        }
                        Matcher matcher = END_OF_STATEMENT.matcher(line);
                        if (matcher.matches()) {
                            if (this.currentSQL.length() > 0) {
                                this.currentSQL.append('\n');
                            }
                            this.currentSQL.append(matcher.group(1));
                            if (this.previousSQL != null && !MockFileDatabase.this.matchExactly.containsKey(this.previousSQL)) {
                                MockFileDatabase.this.matchExactly.put(this.previousSQL, null);
                            }
                            this.previousSQL = this.currentSQL.toString();
                            this.currentSQL = new StringBuilder();
                            if (!log.isDebugEnabled()) continue;
                            log.debug((Object)"Loaded SQL", this.previousSQL);
                            continue;
                        }
                        if (this.currentResult.length() > 0) {
                            this.loadOneResult("");
                            this.currentResult = new StringBuilder();
                        }
                        if (this.currentSQL.length() > 0) {
                            this.currentSQL.append('\n');
                        }
                        this.currentSQL.append(line);
                    }
                }
                finally {
                    if (MockFileDatabase.this.configuration.in != null) {
                        MockFileDatabase.this.configuration.in.close();
                    }
                }
            }

            private void loadOneResult(String line) {
                List<MockResult> results = MockFileDatabase.this.matchExactly.get(this.previousSQL);
                if (results == null) {
                    results = new ArrayList<MockResult>();
                    if (MockFileDatabase.this.configuration.patterns) {
                        try {
                            Pattern p = Pattern.compile(this.previousSQL);
                            MockFileDatabase.this.matchPattern.put(p, results);
                        }
                        catch (PatternSyntaxException e) {
                            throw new MockFileDatabaseException("Not a pattern: " + this.previousSQL, e);
                        }
                    } else {
                        MockFileDatabase.this.matchExactly.put(this.previousSQL, results);
                    }
                }
                MockResult mock = this.parse(line);
                results.add(mock);
                if (mock.data != null && log.isDebugEnabled()) {
                    String comment = "Loaded Result";
                    for (String l : mock.data.format(5).split("\n")) {
                        log.debug((Object)comment, l);
                        comment = "";
                    }
                }
            }

            private MockResult parse(String rowString) {
                MockResult result;
                int rows = 0;
                SQLException exception = null;
                if (rowString.startsWith("@ rows:")) {
                    rows = Integer.parseInt(rowString.substring(7).trim());
                }
                if (rowString.startsWith("@ exception:")) {
                    exception = new SQLException(rowString.substring(12).trim());
                }
                String resultText = this.currentResult.toString();
                String trimmed = resultText.trim();
                MockResult mockResult = exception != null ? new MockResult(exception) : (resultText.isEmpty() ? new MockResult(rows) : (trimmed.startsWith("<") ? new MockResult(rows, MockFileDatabase.this.create.fetchFromXML(resultText)) : (trimmed.startsWith("{") || trimmed.startsWith("[") ? new MockResult(rows, MockFileDatabase.this.create.fetchFromJSON(resultText)) : (result = new MockResult(rows, MockFileDatabase.this.configuration.nullLiteral == null && MockFileDatabase.this.nullLiteral == null ? MockFileDatabase.this.create.fetchFromTXT(resultText) : (MockFileDatabase.this.configuration.nullLiteral != null ? MockFileDatabase.this.create.fetchFromTXT(resultText, MockFileDatabase.this.configuration.nullLiteral) : MockFileDatabase.this.create.fetchFromTXT(resultText, MockFileDatabase.this.nullLiteral)))))));
                if (result.data != null && rows != result.data.size()) {
                    throw new MockFileDatabaseException("Rows mismatch. Declared: " + rows + ". Actual: " + result.data.size() + ".");
                }
                return result;
            }

            private String readLine() throws IOException {
                String line;
                do {
                    if ((line = MockFileDatabase.this.configuration.in.readLine()) != null) continue;
                    return line;
                } while (line.length() <= 0 || line.trim().length() <= 0);
                return line;
            }
        }.load();
    }

    @Deprecated
    public Map<String, List<MockResult>> queries() {
        return this.matchExactly;
    }

    @Override
    public MockResult[] execute(MockExecuteContext ctx) throws SQLException {
        if (ctx.batch()) {
            throw new SQLFeatureNotSupportedException("Not yet supported");
        }
        String sql = ctx.sql();
        String inlined2 = null;
        List<MockResult> list = this.matchExactly.get(sql);
        if (list == null) {
            inlined2 = this.create.query(sql, ctx.bindings()).toString();
            list = this.matchExactly.get(inlined2);
        }
        if (list == null) {
            for (Map.Entry<Pattern, List<MockResult>> entry : this.matchPattern.entrySet()) {
                if (!entry.getKey().matcher(sql).matches() && !entry.getKey().matcher(inlined2).matches()) continue;
                list = entry.getValue();
                break;
            }
        }
        if (list == null) {
            throw new SQLException("SQL statement could not be matched by any statement in the MockFileDatabase: " + sql + "\nPossible reasons include: \n  Your regular expressions are case sensitive.\n  Your regular expressions use constant literals (e.g. 'Hello'), but the above SQL string uses bind variable placeholders (e.g. ?).\n  Your regular expressions did not quote special characters (e.g. \\?).\n  Your regular expressions' whitespace doesn't match the input SQL's whitespace.");
        }
        return list.toArray(new MockResult[list.size()]);
    }
}

