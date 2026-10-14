/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.jooq.Batch;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.InsertQuery;
import org.jooq.Loader;
import org.jooq.LoaderCSVOptionsStep;
import org.jooq.LoaderCSVStep;
import org.jooq.LoaderContext;
import org.jooq.LoaderError;
import org.jooq.LoaderFieldMapper;
import org.jooq.LoaderJSONOptionsStep;
import org.jooq.LoaderJSONStep;
import org.jooq.LoaderOptionsStep;
import org.jooq.LoaderRowListener;
import org.jooq.LoaderRowsStep;
import org.jooq.LoaderXMLStep;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Source;
import org.jooq.Table;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.LoaderConfigurationException;
import org.jooq.impl.DefaultConnectionProvider;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.jooq.impl.JSONReader;
import org.jooq.impl.LoaderErrorImpl;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;
import org.jooq.tools.csv.CSVReader;
import org.jooq.tools.jdbc.DefaultPreparedStatement;
import org.jooq.tools.jdbc.JDBCUtils;
import org.xml.sax.InputSource;

final class LoaderImpl<R extends Record>
implements LoaderOptionsStep<R>,
LoaderRowsStep<R>,
LoaderXMLStep<R>,
LoaderCSVStep<R>,
LoaderCSVOptionsStep<R>,
LoaderJSONStep<R>,
LoaderJSONOptionsStep<R>,
Loader<R> {
    private static final JooqLogger log = JooqLogger.getLogger(LoaderImpl.class);
    private static final Set<SQLDialect> NO_SUPPORT_ROWCOUNT_ON_DUPLICATE = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL);
    private static final int ON_DUPLICATE_KEY_ERROR = 0;
    private static final int ON_DUPLICATE_KEY_IGNORE = 1;
    private static final int ON_DUPLICATE_KEY_UPDATE = 2;
    private static final int ON_ERROR_ABORT = 0;
    private static final int ON_ERROR_IGNORE = 1;
    private static final int COMMIT_NONE = 0;
    private static final int COMMIT_AFTER = 1;
    private static final int COMMIT_ALL = 2;
    private static final int BATCH_NONE = 0;
    private static final int BATCH_AFTER = 1;
    private static final int BATCH_ALL = 2;
    private static final int BULK_NONE = 0;
    private static final int BULK_AFTER = 1;
    private static final int BULK_ALL = 2;
    private static final int CONTENT_CSV = 0;
    private static final int CONTENT_XML = 1;
    private static final int CONTENT_JSON = 2;
    private static final int CONTENT_ARRAYS = 3;
    private final Configuration configuration;
    private final Table<R> table;
    private int onDuplicate = 0;
    private int onError = 0;
    private int commit = 0;
    private int commitAfter = 1;
    private int batch = 0;
    private int batchAfter = 1;
    private int bulk = 0;
    private int bulkAfter = 1;
    private int content = 0;
    private Source input;
    private Iterator<? extends Object[]> arrays;
    private int ignoreRows = 1;
    private char quote = (char)34;
    private char separator = (char)44;
    private String nullString = null;
    private Field<?>[] source;
    private Field<?>[] fields;
    private LoaderFieldMapper fieldMapper;
    private boolean fieldsCorresponding;
    private BitSet primaryKey;
    private LoaderRowListener onRowStart;
    private LoaderRowListener onRowEnd;
    private final LoaderContext rowCtx = new DefaultLoaderContext();
    private int ignored;
    private int processed;
    private int stored;
    private int executed;
    private int unexecuted;
    private int uncommitted;
    private final List<LoaderError> errors;

    LoaderImpl(Configuration configuration, Table<R> table) {
        this.configuration = configuration;
        this.table = table;
        this.errors = new ArrayList<LoaderError>();
    }

    @Override
    public final LoaderImpl<R> onDuplicateKeyError() {
        this.onDuplicate = 0;
        return this;
    }

    @Override
    public final LoaderImpl<R> onDuplicateKeyIgnore() {
        if (this.table.getPrimaryKey() == null) {
            throw new IllegalStateException("ON DUPLICATE KEY IGNORE only works on tables with explicit primary keys. Table is not updatable : " + String.valueOf(this.table));
        }
        this.onDuplicate = 1;
        return this;
    }

    @Override
    public final LoaderImpl<R> onDuplicateKeyUpdate() {
        if (this.table.getPrimaryKey() == null) {
            throw new IllegalStateException("ON DUPLICATE KEY UPDATE only works on tables with explicit primary keys. Table is not updatable : " + String.valueOf(this.table));
        }
        this.onDuplicate = 2;
        return this;
    }

    @Override
    public final LoaderImpl<R> onErrorIgnore() {
        this.onError = 1;
        return this;
    }

    @Override
    public final LoaderImpl<R> onErrorAbort() {
        this.onError = 0;
        return this;
    }

    @Override
    public final LoaderImpl<R> commitEach() {
        this.commit = 1;
        return this;
    }

    @Override
    public final LoaderImpl<R> commitAfter(int number) {
        this.commit = 1;
        this.commitAfter = number;
        return this;
    }

    @Override
    public final LoaderImpl<R> commitAll() {
        this.commit = 2;
        return this;
    }

    @Override
    public final LoaderImpl<R> commitNone() {
        this.commit = 0;
        return this;
    }

    @Override
    public final LoaderImpl<R> batchAll() {
        this.batch = 2;
        return this;
    }

    @Override
    public final LoaderImpl<R> batchNone() {
        this.batch = 0;
        return this;
    }

    @Override
    public final LoaderImpl<R> batchAfter(int number) {
        this.batch = 1;
        this.batchAfter = number;
        return this;
    }

    @Override
    public final LoaderImpl<R> bulkAll() {
        this.bulk = 2;
        return this;
    }

    @Override
    public final LoaderImpl<R> bulkNone() {
        this.bulk = 0;
        return this;
    }

    @Override
    public final LoaderImpl<R> bulkAfter(int number) {
        this.bulk = 1;
        this.bulkAfter = number;
        return this;
    }

    @Override
    public final LoaderRowsStep<R> loadArrays(Object[] ... a) {
        return this.loadArrays(Arrays.asList(a));
    }

    @Override
    public final LoaderRowsStep<R> loadArrays(Iterable<? extends Object[]> a) {
        return this.loadArrays(a.iterator());
    }

    @Override
    public final LoaderRowsStep<R> loadArrays(Iterator<? extends Object[]> a) {
        this.content = 3;
        this.arrays = a;
        return this;
    }

    @Override
    public final LoaderRowsStep<R> loadRecords(Record ... records) {
        return this.loadRecords(Arrays.asList(records));
    }

    @Override
    public final LoaderRowsStep<R> loadRecords(Iterable<? extends Record> records) {
        return this.loadRecords(records.iterator());
    }

    @Override
    public final LoaderRowsStep<R> loadRecords(Iterator<? extends Record> records) {
        return this.loadArrays(Tools.iterator(records, value -> {
            if (value == null) {
                return null;
            }
            if (this.source == null) {
                this.source = value.fields();
            }
            return value.intoArray();
        }));
    }

    @Override
    public final LoaderRowsStep<R> loadArrays(Stream<? extends Object[]> a) {
        return this.loadArrays(a.iterator());
    }

    @Override
    public final LoaderRowsStep<R> loadRecords(Stream<? extends Record> records) {
        return this.loadRecords(records.iterator());
    }

    @Override
    public final LoaderImpl<R> loadCSV(File file) {
        return this.loadCSV(Source.of(file));
    }

    @Override
    public final LoaderImpl<R> loadCSV(File file, String charsetName) {
        return this.loadCSV(Source.of(file, charsetName));
    }

    @Override
    public final LoaderImpl<R> loadCSV(File file, Charset cs) {
        return this.loadCSV(Source.of(file, cs));
    }

    @Override
    public final LoaderImpl<R> loadCSV(File file, CharsetDecoder dec) {
        return this.loadCSV(Source.of(file, dec));
    }

    @Override
    public final LoaderImpl<R> loadCSV(String csv) {
        return this.loadCSV(Source.of(csv));
    }

    @Override
    public final LoaderImpl<R> loadCSV(InputStream stream) {
        return this.loadCSV(Source.of(stream));
    }

    @Override
    public final LoaderImpl<R> loadCSV(InputStream stream, String charsetName) {
        return this.loadCSV(Source.of(stream, charsetName));
    }

    @Override
    public final LoaderImpl<R> loadCSV(InputStream stream, Charset cs) {
        return this.loadCSV(Source.of(stream, cs));
    }

    @Override
    public final LoaderImpl<R> loadCSV(InputStream stream, CharsetDecoder dec) {
        return this.loadCSV(Source.of(stream, dec));
    }

    @Override
    public final LoaderImpl<R> loadCSV(Reader reader) {
        return this.loadCSV(Source.of(reader));
    }

    @Override
    public final LoaderImpl<R> loadCSV(Source s2) {
        this.content = 0;
        this.input = s2;
        return this;
    }

    @Override
    public final LoaderImpl<R> loadXML(File file) {
        return this.loadXML(Source.of(file));
    }

    @Override
    public final LoaderImpl<R> loadXML(File file, String charsetName) {
        return this.loadXML(Source.of(file, charsetName));
    }

    @Override
    public final LoaderImpl<R> loadXML(File file, Charset cs) {
        return this.loadXML(Source.of(file, cs));
    }

    @Override
    public final LoaderImpl<R> loadXML(File file, CharsetDecoder dec) {
        return this.loadXML(Source.of(file, dec));
    }

    @Override
    public final LoaderImpl<R> loadXML(String xml) {
        return this.loadXML(Source.of(xml));
    }

    @Override
    public final LoaderImpl<R> loadXML(InputStream stream) {
        return this.loadXML(Source.of(stream));
    }

    @Override
    public final LoaderImpl<R> loadXML(InputStream stream, String charsetName) {
        return this.loadXML(Source.of(stream, charsetName));
    }

    @Override
    public final LoaderImpl<R> loadXML(InputStream stream, Charset cs) {
        return this.loadXML(Source.of(stream, cs));
    }

    @Override
    public final LoaderImpl<R> loadXML(InputStream stream, CharsetDecoder dec) {
        return this.loadXML(Source.of(stream, dec));
    }

    @Override
    public final LoaderImpl<R> loadXML(Reader reader) {
        return this.loadXML(Source.of(reader));
    }

    @Override
    public final LoaderImpl<R> loadXML(InputSource s2) {
        this.content = 1;
        throw new UnsupportedOperationException("This is not yet implemented");
    }

    @Override
    public final LoaderImpl<R> loadXML(Source s2) {
        this.content = 1;
        this.input = s2;
        throw new UnsupportedOperationException("This is not yet implemented");
    }

    @Override
    public final LoaderImpl<R> loadJSON(File file) {
        return this.loadJSON(Source.of(file));
    }

    @Override
    public final LoaderImpl<R> loadJSON(File file, String charsetName) {
        return this.loadJSON(Source.of(file, charsetName));
    }

    @Override
    public final LoaderImpl<R> loadJSON(File file, Charset cs) {
        return this.loadJSON(Source.of(file, cs));
    }

    @Override
    public final LoaderImpl<R> loadJSON(File file, CharsetDecoder dec) {
        return this.loadJSON(Source.of(file, dec));
    }

    @Override
    public final LoaderImpl<R> loadJSON(String json) {
        return this.loadJSON(Source.of(json));
    }

    @Override
    public final LoaderImpl<R> loadJSON(InputStream stream) {
        return this.loadJSON(Source.of(stream));
    }

    @Override
    public final LoaderImpl<R> loadJSON(InputStream stream, String charsetName) {
        return this.loadJSON(Source.of(stream, charsetName));
    }

    @Override
    public final LoaderImpl<R> loadJSON(InputStream stream, Charset cs) {
        return this.loadJSON(Source.of(stream, cs));
    }

    @Override
    public final LoaderImpl<R> loadJSON(InputStream stream, CharsetDecoder dec) {
        return this.loadJSON(Source.of(stream, dec));
    }

    @Override
    public final LoaderImpl<R> loadJSON(Reader reader) {
        return this.loadJSON(Source.of(reader));
    }

    @Override
    public final LoaderImpl<R> loadJSON(Source s2) {
        this.content = 2;
        this.input = s2;
        return this;
    }

    @Override
    public final LoaderImpl<R> fields(Field<?> ... f) {
        this.fields = f;
        this.primaryKey = new BitSet(f.length);
        if (this.table.getPrimaryKey() != null) {
            for (int i = 0; i < this.fields.length; ++i) {
                if (this.fields[i] == null || !this.table.getPrimaryKey().getFields().contains(this.fields[i])) continue;
                this.primaryKey.set(i);
            }
        }
        return this;
    }

    @Override
    public final LoaderImpl<R> fields(Collection<? extends Field<?>> f) {
        return this.fields((Field[])f.toArray(Tools.EMPTY_FIELD));
    }

    @Override
    public final LoaderImpl<R> fields(LoaderFieldMapper mapper2) {
        this.fieldMapper = mapper2;
        return this;
    }

    @Override
    @Deprecated
    public LoaderImpl<R> fieldsFromSource() {
        return this.fieldsCorresponding();
    }

    @Override
    public LoaderImpl<R> fieldsCorresponding() {
        this.fieldsCorresponding = true;
        return this;
    }

    private final void fields0(Object[] row) {
        Field[] f = new Field[row.length];
        if (this.source == null) {
            if (this.fieldsCorresponding) {
                throw new LoaderConfigurationException("Using fieldsCorresponding() requires field names to be available in source.");
            }
            this.source = Tools.fields(row.length);
        }
        if (this.fieldMapper != null) {
            for (int i = 0; i < row.length; ++i) {
                final int index = i;
                f[i] = this.fieldMapper.map(new LoaderFieldMapper.LoaderFieldContext(){

                    @Override
                    public int index() {
                        return index;
                    }

                    @Override
                    public Field<?> field() {
                        return LoaderImpl.this.source[index];
                    }
                });
            }
        } else if (this.fieldsCorresponding) {
            for (int i = 0; i < row.length; ++i) {
                f[i] = this.table.field(this.source[i]);
                if (f[i] != null) continue;
                log.info("No column in target table " + String.valueOf(this.table) + " found for input field " + String.valueOf(this.source[i]));
            }
        }
        this.fields(f);
    }

    @Override
    public final LoaderImpl<R> ignoreRows(int number) {
        this.ignoreRows = number;
        return this;
    }

    @Override
    public final LoaderImpl<R> quote(char q) {
        this.quote = q;
        return this;
    }

    @Override
    public final LoaderImpl<R> separator(char s2) {
        this.separator = s2;
        return this;
    }

    @Override
    public final LoaderImpl<R> nullString(String n) {
        this.nullString = n;
        return this;
    }

    @Override
    public final LoaderImpl<R> onRow(LoaderRowListener l) {
        return this.onRowEnd(l);
    }

    @Override
    public final LoaderImpl<R> onRowStart(LoaderRowListener l) {
        this.onRowStart = l;
        return this;
    }

    @Override
    public final LoaderImpl<R> onRowEnd(LoaderRowListener l) {
        this.onRowEnd = l;
        return this;
    }

    @Override
    public final LoaderImpl<R> execute() throws IOException {
        this.checkFlags();
        if (this.content == 0) {
            this.executeCSV();
        } else {
            if (this.content == 1) {
                throw new UnsupportedOperationException();
            }
            if (this.content == 2) {
                this.executeJSON();
            } else if (this.content == 3) {
                this.executeRows();
            } else {
                throw new IllegalStateException();
            }
        }
        return this;
    }

    private final void checkFlags() {
        if (this.bulk != 0 && this.onDuplicate != 0) {
            throw new LoaderConfigurationException("Cannot apply bulk loading with onDuplicateKey flags. Turn off either flag.");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void executeJSON() {
        Reader reader = null;
        try {
            reader = this.input.reader();
            Result r = new JSONReader(this.configuration.dsl(), null, null, false).read(reader);
            this.source = r.fields();
            List allRecords = Arrays.asList(r.intoArrays());
            this.executeSQL(allRecords.iterator());
        }
        finally {
            JDBCUtils.safeClose(reader);
        }
    }

    private final void executeCSV() {
        CSVReader reader = null;
        try {
            if (this.ignoreRows == 1) {
                reader = new CSVReader(this.input.reader(), this.separator, this.quote, 0);
                this.source = Tools.fieldsByName(reader.next());
            } else {
                reader = new CSVReader(this.input.reader(), this.separator, this.quote, this.ignoreRows);
            }
            this.executeSQL(reader);
        }
        catch (Throwable throwable) {
            JDBCUtils.safeClose(reader);
            throw throwable;
        }
        JDBCUtils.safeClose(reader);
    }

    private final void executeRows() {
        this.executeSQL(this.arrays);
    }

    private final void executeSQL(Iterator<? extends Object[]> iterator2) {
        this.configuration.dsl().connection(connection -> {
            Configuration c = this.configuration.derive(new DefaultConnectionProvider(connection));
            if (Boolean.FALSE.equals(c.settings().isCachePreparedStatementInLoader())) {
                this.executeSQL(iterator2, c.dsl());
            } else {
                try (CachedPSListener cache = new CachedPSListener();){
                    this.executeSQL(iterator2, c.derive(Tools.combine(new DefaultExecuteListenerProvider(cache), c.executeListenerProviders())).dsl());
                }
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private final void executeSQL(Iterator<? extends Object[]> iterator2, DSLContext ctx) {
        block45: {
            Object[] row = null;
            Batch bind = null;
            InsertQuery<R> insert = null;
            boolean newRecord = false;
            while (iterator2.hasNext() && (row = iterator2.next()) != null) {
                try {
                    block44: {
                        block43: {
                            int i;
                            if (row.getClass() != Object[].class) {
                                row = Arrays.copyOf(row, row.length, Object[].class);
                            }
                            if (this.fields == null) {
                                this.fields0(row);
                            }
                            for (i = 0; i < row.length; ++i) {
                                if (StringUtils.equals((Object)this.nullString, row[i])) {
                                    row[i] = null;
                                    continue;
                                }
                                if (i >= this.fields.length || this.fields[i] == null || this.fields[i].getType() != byte[].class || !(row[i] instanceof String)) continue;
                                row[i] = Base64.getDecoder().decode((String)row[i]);
                            }
                            if (row.length < this.fields.length) {
                                row = Arrays.copyOf(row, this.fields.length);
                            }
                            this.rowCtx.row(row);
                            if (this.onRowStart != null) {
                                this.onRowStart.row(this.rowCtx);
                                row = this.rowCtx.row();
                            }
                            ++this.processed;
                            ++this.unexecuted;
                            ++this.uncommitted;
                            if (insert == null) {
                                insert = ctx.insertQuery(this.table);
                            }
                            if (newRecord) {
                                newRecord = false;
                                insert.newRecord();
                            }
                            for (i = 0; i < row.length; ++i) {
                                if (i >= this.fields.length || this.fields[i] == null) continue;
                                this.addValue0(insert, this.fields[i], row[i]);
                            }
                            if (this.onDuplicate == 2) {
                                insert.onDuplicateKeyUpdate(true);
                                for (i = 0; i < row.length; ++i) {
                                    if (i >= this.fields.length || this.fields[i] == null || this.primaryKey.get(i)) continue;
                                    this.addValueForUpdate0(insert, this.fields[i], row[i]);
                                }
                            } else if (this.onDuplicate == 1) {
                                insert.onDuplicateKeyIgnore(true);
                            } else if (this.onDuplicate == 0) {
                                // empty if block
                            }
                            if (this.bulk == 0 || this.bulk != 2 && this.processed % this.bulkAfter == 0) break block43;
                            newRecord = true;
                            if (this.onRowEnd == null) continue;
                            this.onRowEnd.row(this.rowCtx);
                            continue;
                        }
                        if (this.batch != 0) {
                            if (bind == null) {
                                bind = ctx.batch((Query)insert);
                            }
                            bind.bind(insert.getBindValues().toArray());
                            insert = null;
                            if (this.batch == 2 || this.processed % (this.bulkAfter * this.batchAfter) != 0) {
                                if (this.onRowEnd == null) continue;
                                this.onRowEnd.row(this.rowCtx);
                                continue;
                            }
                        }
                        try {
                            int[] rowcounts = new int[]{0};
                            int totalRowCounts = 0;
                            if (bind != null) {
                                rowcounts = bind.execute();
                            } else if (insert != null) {
                                rowcounts = new int[]{insert.execute()};
                            }
                            if (this.onDuplicate == 2 && NO_SUPPORT_ROWCOUNT_ON_DUPLICATE.contains((Object)ctx.dialect())) {
                                totalRowCounts = this.unexecuted;
                            } else {
                                int rowCount;
                                int[] nArray = rowcounts;
                                int n = nArray.length;
                                for (int i = 0; i < n; totalRowCounts += rowCount, ++i) {
                                    rowCount = nArray[i];
                                }
                            }
                            this.stored += totalRowCounts;
                            this.ignored += this.unexecuted - totalRowCounts;
                            ++this.executed;
                            this.unexecuted = 0;
                            bind = null;
                            insert = null;
                            if (this.commit != 1 || this.processed % (this.bulkAfter * this.batchAfter) != 0 || this.processed / (this.bulkAfter * this.batchAfter) % this.commitAfter != 0) break block44;
                            this.commit();
                        }
                        catch (DataAccessException e) {
                            this.errors.add(new LoaderErrorImpl(e, row, this.processed - 1, insert));
                            this.ignored += this.unexecuted;
                            this.unexecuted = 0;
                            if (this.onError != 0) break block44;
                            if (this.onRowEnd != null) {
                                this.onRowEnd.row(this.rowCtx);
                            }
                            break block45;
                        }
                    }
                    if (this.onRowEnd == null) continue;
                    this.onRowEnd.row(this.rowCtx);
                }
                catch (Throwable throwable) {
                    if (this.onRowEnd == null) throw throwable;
                    this.onRowEnd.row(this.rowCtx);
                    throw throwable;
                }
            }
            if (this.unexecuted != 0) {
                try {
                    if (bind != null) {
                        bind.execute();
                    }
                    if (insert != null) {
                        insert.execute();
                    }
                    this.stored += this.unexecuted;
                    ++this.executed;
                    this.unexecuted = 0;
                }
                catch (DataAccessException e) {
                    this.errors.add(new LoaderErrorImpl(e, row, this.processed - 1, insert));
                    this.ignored += this.unexecuted;
                    this.unexecuted = 0;
                }
            }
            if (this.commit == 1 && this.uncommitted != 0) {
                this.commit();
            }
            if (this.onError == 0) {
                // empty if block
            }
        }
        try {
            if (this.commit != 2) return;
            if (!this.errors.isEmpty()) {
                this.stored = 0;
                this.rollback();
                return;
            }
            this.commit();
            return;
        }
        catch (DataAccessException e) {
            this.errors.add(new LoaderErrorImpl(e, null, this.processed - 1, null));
        }
    }

    private final void commit() {
        this.configuration.dsl().connection(Connection::commit);
        this.uncommitted = 0;
    }

    private final void rollback() {
        this.configuration.dsl().connection(Connection::rollback);
    }

    private final <T> void addValue0(InsertQuery<R> insert, Field<T> field2, Object row) {
        insert.addValue(field2, field2.getDataType().convert(row));
    }

    private final <T> void addValueForUpdate0(InsertQuery<R> insert, Field<T> field2, Object row) {
        insert.addValueForUpdate(field2, field2.getDataType().convert(row));
    }

    @Override
    public final List<LoaderError> errors() {
        return this.errors;
    }

    @Override
    public final int processed() {
        return this.processed;
    }

    @Override
    public final int executed() {
        return this.executed;
    }

    @Override
    public final int ignored() {
        return this.ignored;
    }

    @Override
    public final int stored() {
        return this.stored;
    }

    @Override
    public final LoaderContext result() {
        return this.rowCtx;
    }

    private class DefaultLoaderContext
    implements LoaderContext {
        Object[] row;

        private DefaultLoaderContext() {
        }

        @Override
        public final LoaderContext row(Object[] r) {
            this.row = r;
            return this;
        }

        @Override
        public final Object[] row() {
            return this.row;
        }

        @Override
        public final List<LoaderError> errors() {
            return LoaderImpl.this.errors;
        }

        @Override
        public final int processed() {
            return LoaderImpl.this.processed;
        }

        @Override
        public final int executed() {
            return LoaderImpl.this.executed;
        }

        @Override
        public final int ignored() {
            return LoaderImpl.this.ignored;
        }

        @Override
        public final int stored() {
            return LoaderImpl.this.stored;
        }
    }

    private static final class CachedPSListener
    implements ExecuteListener,
    AutoCloseable {
        final Map<String, CachedPS> map = new HashMap<String, CachedPS>();

        private CachedPSListener() {
        }

        @Override
        public void prepareStart(ExecuteContext ctx) {
            CachedPS ps = this.map.get(ctx.sql());
            if (ps != null) {
                ctx.statement(ps);
            }
        }

        @Override
        public void prepareEnd(ExecuteContext ctx) {
            if (!(ctx.statement() instanceof CachedPS)) {
                CachedPS ps = new CachedPS(ctx.statement());
                this.map.put(ctx.sql(), ps);
                ctx.statement(ps);
            }
        }

        @Override
        public void close() throws SQLException {
            for (CachedPS ps : this.map.values()) {
                JDBCUtils.safeClose(ps.getDelegate());
            }
        }
    }

    private static class CachedPS
    extends DefaultPreparedStatement {
        CachedPS(PreparedStatement delegate) {
            super(delegate);
        }

        @Override
        public void close() throws SQLException {
        }
    }
}

