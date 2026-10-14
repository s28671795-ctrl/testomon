/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.lucene.analysis.Analyzer
 *  org.apache.lucene.analysis.standard.StandardAnalyzer
 *  org.apache.lucene.document.DateTools
 *  org.apache.lucene.document.DateTools$Resolution
 *  org.apache.lucene.document.Document
 *  org.apache.lucene.document.Field
 *  org.apache.lucene.document.FieldType
 *  org.apache.lucene.document.TextField
 *  org.apache.lucene.index.DirectoryReader
 *  org.apache.lucene.index.IndexFormatTooOldException
 *  org.apache.lucene.index.IndexReader
 *  org.apache.lucene.index.IndexWriter
 *  org.apache.lucene.index.IndexWriterConfig
 *  org.apache.lucene.index.IndexWriterConfig$OpenMode
 *  org.apache.lucene.index.IndexableField
 *  org.apache.lucene.index.IndexableFieldType
 *  org.apache.lucene.index.Term
 *  org.apache.lucene.queryparser.flexible.standard.StandardQueryParser
 *  org.apache.lucene.search.IndexSearcher
 *  org.apache.lucene.search.Query
 *  org.apache.lucene.search.ScoreDoc
 *  org.apache.lucene.search.TopDocs
 *  org.apache.lucene.store.ByteBuffersDirectory
 *  org.apache.lucene.store.Directory
 *  org.apache.lucene.store.FSDirectory
 */
package org.h2.fulltext;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexFormatTooOldException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.IndexableFieldType;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.h2.api.Trigger;
import org.h2.command.Parser;
import org.h2.engine.SessionLocal;
import org.h2.expression.ExpressionColumn;
import org.h2.fulltext.FullText;
import org.h2.jdbc.JdbcConnection;
import org.h2.store.fs.FileUtils;
import org.h2.tools.SimpleResultSet;
import org.h2.util.StringUtils;
import org.h2.util.Utils;

public class FullTextLucene
extends FullText {
    protected static final boolean STORE_DOCUMENT_TEXT_IN_INDEX = Utils.getProperty("h2.storeDocumentTextInIndex", false);
    private static final HashMap<String, IndexAccess> INDEX_ACCESS = new HashMap();
    private static final String TRIGGER_PREFIX = "FTL_";
    private static final String SCHEMA = "FTL";
    private static final String LUCENE_FIELD_DATA = "_DATA";
    private static final String LUCENE_FIELD_QUERY = "_QUERY";
    private static final String LUCENE_FIELD_MODIFIED = "_modified";
    private static final String LUCENE_FIELD_COLUMN_PREFIX = "_";
    private static final String IN_MEMORY_PREFIX = "mem:";

    public static void init(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();){
            statement.execute("CREATE SCHEMA IF NOT EXISTS FTL");
            statement.execute("CREATE TABLE IF NOT EXISTS FTL.INDEXES(SCHEMA VARCHAR, `TABLE` VARCHAR, COLUMNS VARCHAR, PRIMARY KEY(SCHEMA, `TABLE`))");
            String string = FullTextLucene.class.getName();
            statement.execute("CREATE ALIAS IF NOT EXISTS FTL_CREATE_INDEX FOR '" + string + ".createIndex'");
            statement.execute("CREATE ALIAS IF NOT EXISTS FTL_DROP_INDEX FOR '" + string + ".dropIndex'");
            statement.execute("CREATE ALIAS IF NOT EXISTS FTL_SEARCH FOR '" + string + ".search'");
            statement.execute("CREATE ALIAS IF NOT EXISTS FTL_SEARCH_DATA FOR '" + string + ".searchData'");
            statement.execute("CREATE ALIAS IF NOT EXISTS FTL_REINDEX FOR '" + string + ".reindex'");
            statement.execute("CREATE ALIAS IF NOT EXISTS FTL_DROP_ALL FOR '" + string + ".dropAll'");
        }
    }

    public static void createIndex(Connection connection, String string, String string2, String string3) throws SQLException {
        FullTextLucene.init(connection);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO FTL.INDEXES(SCHEMA, `TABLE`, COLUMNS) VALUES(?, ?, ?)");
        preparedStatement.setString(1, string);
        preparedStatement.setString(2, string2);
        preparedStatement.setString(3, string3);
        preparedStatement.execute();
        FullTextLucene.createTrigger(connection, string, string2);
        FullTextLucene.indexExistingRows(connection, string, string2);
    }

    public static void dropIndex(Connection connection, String string, String string2) throws SQLException {
        FullTextLucene.init(connection);
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM FTL.INDEXES WHERE SCHEMA=? AND `TABLE`=?");
        preparedStatement.setString(1, string);
        preparedStatement.setString(2, string2);
        int n = preparedStatement.executeUpdate();
        if (n != 0) {
            FullTextLucene.reindex(connection);
        }
    }

    public static void reindex(Connection connection) throws SQLException {
        FullTextLucene.init(connection);
        FullTextLucene.removeAllTriggers(connection, TRIGGER_PREFIX);
        FullTextLucene.removeIndexFiles(connection);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM FTL.INDEXES");
        while (resultSet.next()) {
            String string = resultSet.getString("SCHEMA");
            String string2 = resultSet.getString("TABLE");
            FullTextLucene.createTrigger(connection, string, string2);
            FullTextLucene.indexExistingRows(connection, string, string2);
        }
    }

    public static void dropAll(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DROP SCHEMA IF EXISTS FTL CASCADE");
        FullTextLucene.removeAllTriggers(connection, TRIGGER_PREFIX);
        FullTextLucene.removeIndexFiles(connection);
    }

    public static ResultSet search(Connection connection, String string, int n, int n2) throws SQLException {
        return FullTextLucene.search(connection, string, n, n2, false);
    }

    public static ResultSet searchData(Connection connection, String string, int n, int n2) throws SQLException {
        return FullTextLucene.search(connection, string, n, n2, true);
    }

    protected static SQLException convertException(Exception exception) {
        return new SQLException("Error while indexing document", "FULLTEXT", exception);
    }

    private static void createTrigger(Connection connection, String string, String string2) throws SQLException {
        FullTextLucene.createOrDropTrigger(connection, string, string2, true);
    }

    private static void createOrDropTrigger(Connection connection, String string, String string2, boolean bl) throws SQLException {
        Statement statement = connection.createStatement();
        String string3 = StringUtils.quoteIdentifier(string) + "." + StringUtils.quoteIdentifier(TRIGGER_PREFIX + string2);
        statement.execute("DROP TRIGGER IF EXISTS " + string3);
        if (bl) {
            StringBuilder stringBuilder = new StringBuilder("CREATE TRIGGER IF NOT EXISTS ");
            stringBuilder.append(string3).append(" AFTER INSERT, UPDATE, DELETE, ROLLBACK ON ");
            StringUtils.quoteIdentifier(stringBuilder, string).append('.');
            StringUtils.quoteIdentifier(stringBuilder, string2).append(" FOR EACH ROW CALL \"").append(FullTextTrigger.class.getName()).append('\"');
            statement.execute(stringBuilder.toString());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected static IndexAccess getIndexAccess(Connection connection) throws SQLException {
        String string = FullTextLucene.getIndexPath(connection);
        HashMap<String, IndexAccess> hashMap = INDEX_ACCESS;
        synchronized (hashMap) {
            IndexAccess indexAccess = INDEX_ACCESS.get(string);
            while (indexAccess == null) {
                try {
                    ByteBuffersDirectory byteBuffersDirectory = string.startsWith(IN_MEMORY_PREFIX) ? new ByteBuffersDirectory() : FSDirectory.open((Path)Paths.get(string, new String[0]));
                    StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
                    IndexWriterConfig indexWriterConfig = new IndexWriterConfig((Analyzer)standardAnalyzer);
                    indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
                    IndexWriter indexWriter = new IndexWriter((Directory)byteBuffersDirectory, indexWriterConfig);
                    indexAccess = new IndexAccess(indexWriter);
                }
                catch (IndexFormatTooOldException indexFormatTooOldException) {
                    FullTextLucene.reindex(connection);
                    continue;
                }
                catch (IOException iOException) {
                    throw FullTextLucene.convertException(iOException);
                }
                INDEX_ACCESS.put(string, indexAccess);
                break;
            }
            return indexAccess;
        }
    }

    protected static String getIndexPath(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("CALL DATABASE_PATH()");
        resultSet.next();
        String string = resultSet.getString(1);
        if (string == null) {
            return IN_MEMORY_PREFIX + connection.getCatalog();
        }
        int n = string.lastIndexOf(58);
        if (n > 1) {
            string = string.substring(n + 1);
        }
        resultSet.close();
        return string;
    }

    private static void indexExistingRows(Connection connection, String string, String string2) throws SQLException {
        FullTextTrigger fullTextTrigger = new FullTextTrigger();
        fullTextTrigger.init(connection, string, null, string2, false, 1);
        String string3 = "SELECT * FROM " + StringUtils.quoteIdentifier(string) + "." + StringUtils.quoteIdentifier(string2);
        ResultSet resultSet = connection.createStatement().executeQuery(string3);
        int n = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            Object[] objectArray = new Object[n];
            for (int i = 0; i < n; ++i) {
                objectArray[i] = resultSet.getObject(i + 1);
            }
            fullTextTrigger.insert(objectArray, false);
        }
        fullTextTrigger.commitIndex();
    }

    private static void removeIndexFiles(Connection connection) throws SQLException {
        String string = FullTextLucene.getIndexPath(connection);
        FullTextLucene.removeIndexAccess(string);
        if (!string.startsWith(IN_MEMORY_PREFIX)) {
            FileUtils.deleteRecursive(string, false);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected static void removeIndexAccess(String string) throws SQLException {
        HashMap<String, IndexAccess> hashMap = INDEX_ACCESS;
        synchronized (hashMap) {
            try {
                IndexAccess indexAccess = INDEX_ACCESS.remove(string);
                if (indexAccess != null) {
                    indexAccess.close();
                }
            }
            catch (Exception exception) {
                throw FullTextLucene.convertException(exception);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected static ResultSet search(Connection connection, String string, int n, int n2, boolean bl) throws SQLException {
        SimpleResultSet simpleResultSet = FullTextLucene.createResultSet(bl);
        if (connection.getMetaData().getURL().startsWith("jdbc:columnlist:")) {
            return simpleResultSet;
        }
        if (string == null || StringUtils.isWhitespaceOrEmpty(string)) {
            return simpleResultSet;
        }
        try {
            IndexAccess indexAccess = FullTextLucene.getIndexAccess(connection);
            IndexSearcher indexSearcher = indexAccess.getSearcher();
            try {
                Analyzer analyzer = indexAccess.writer.getAnalyzer();
                StandardQueryParser standardQueryParser = new StandardQueryParser(analyzer);
                Query query = standardQueryParser.parse(string, LUCENE_FIELD_DATA);
                int n3 = (n == 0 ? 100 : n) + n2;
                TopDocs topDocs = indexSearcher.search(query, n3);
                long l = topDocs.totalHits.value;
                if (n == 0) {
                    n = (int)l;
                }
                int n4 = topDocs.scoreDocs.length;
                for (int i = 0; i < n && (long)(i + n2) < l && i + n2 < n4; ++i) {
                    ScoreDoc scoreDoc = topDocs.scoreDocs[i + n2];
                    Document document = indexSearcher.doc(scoreDoc.doc);
                    float f = scoreDoc.score;
                    String string2 = document.get(LUCENE_FIELD_QUERY);
                    if (bl) {
                        int n5 = string2.indexOf(" WHERE ");
                        JdbcConnection jdbcConnection = (JdbcConnection)connection;
                        SessionLocal sessionLocal = (SessionLocal)jdbcConnection.getSession();
                        Parser parser = new Parser(sessionLocal);
                        String string3 = string2.substring(0, n5);
                        ExpressionColumn expressionColumn = (ExpressionColumn)parser.parseExpression(string3);
                        String string4 = expressionColumn.getOriginalTableAliasName();
                        String string5 = expressionColumn.getColumnName(sessionLocal, -1);
                        string2 = string2.substring(n5 + " WHERE ".length());
                        String[][] stringArray = FullTextLucene.parseKey(connection, string2);
                        simpleResultSet.addRow(string4, string5, stringArray[0], stringArray[1], Float.valueOf(f));
                        continue;
                    }
                    simpleResultSet.addRow(string2, Float.valueOf(f));
                }
            }
            finally {
                indexAccess.returnSearcher(indexSearcher);
            }
        }
        catch (Exception exception) {
            throw FullTextLucene.convertException(exception);
        }
        return simpleResultSet;
    }

    private static final class IndexAccess {
        final IndexWriter writer;
        private IndexSearcher searcher;

        IndexAccess(IndexWriter indexWriter) throws IOException {
            this.writer = indexWriter;
            this.initializeSearcher();
        }

        synchronized IndexSearcher getSearcher() throws IOException {
            if (!this.searcher.getIndexReader().tryIncRef()) {
                this.initializeSearcher();
            }
            return this.searcher;
        }

        private void initializeSearcher() throws IOException {
            DirectoryReader directoryReader = DirectoryReader.open((IndexWriter)this.writer);
            this.searcher = new IndexSearcher((IndexReader)directoryReader);
        }

        synchronized void returnSearcher(IndexSearcher indexSearcher) throws IOException {
            indexSearcher.getIndexReader().decRef();
        }

        public synchronized void commit() throws IOException {
            this.writer.commit();
            this.returnSearcher(this.searcher);
            this.searcher = new IndexSearcher((IndexReader)DirectoryReader.open((IndexWriter)this.writer));
        }

        public synchronized void close() throws IOException {
            this.searcher = null;
            this.writer.close();
        }
    }

    public static final class FullTextTrigger
    implements Trigger {
        private String schema;
        private String table;
        private int[] keys;
        private int[] indexColumns;
        private String[] columns;
        private int[] columnTypes;
        private String indexPath;
        private IndexAccess indexAccess;
        private final FieldType DOC_ID_FIELD_TYPE = new FieldType((IndexableFieldType)TextField.TYPE_STORED);

        public FullTextTrigger() {
            this.DOC_ID_FIELD_TYPE.setTokenized(false);
            this.DOC_ID_FIELD_TYPE.freeze();
        }

        @Override
        public void init(Connection connection, String string, String string2, String string3, boolean bl, int n) throws SQLException {
            String string4;
            this.schema = string;
            this.table = string3;
            this.indexPath = FullTextLucene.getIndexPath(connection);
            this.indexAccess = FullTextLucene.getIndexAccess(connection);
            ArrayList<String> arrayList = Utils.newSmallArrayList();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getColumns(null, StringUtils.escapeMetaDataPattern(string), StringUtils.escapeMetaDataPattern(string3), null);
            ArrayList<String> arrayList2 = Utils.newSmallArrayList();
            while (resultSet.next()) {
                arrayList2.add(resultSet.getString("COLUMN_NAME"));
            }
            this.columnTypes = new int[arrayList2.size()];
            this.columns = arrayList2.toArray(new String[0]);
            resultSet = databaseMetaData.getColumns(null, StringUtils.escapeMetaDataPattern(string), StringUtils.escapeMetaDataPattern(string3), null);
            int n2 = 0;
            while (resultSet.next()) {
                this.columnTypes[n2] = resultSet.getInt("DATA_TYPE");
                ++n2;
            }
            if (arrayList.isEmpty()) {
                resultSet = databaseMetaData.getPrimaryKeys(null, StringUtils.escapeMetaDataPattern(string), string3);
                while (resultSet.next()) {
                    arrayList.add(resultSet.getString("COLUMN_NAME"));
                }
            }
            if (arrayList.isEmpty()) {
                throw FullText.throwException("No primary key for table " + string3);
            }
            ArrayList<String> arrayList3 = Utils.newSmallArrayList();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COLUMNS FROM FTL.INDEXES WHERE SCHEMA=? AND `TABLE`=?");
            preparedStatement.setString(1, string);
            preparedStatement.setString(2, string3);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && (string4 = resultSet.getString(1)) != null) {
                Collections.addAll(arrayList3, StringUtils.arraySplit(string4, ',', true));
            }
            if (arrayList3.isEmpty()) {
                arrayList3.addAll(arrayList2);
            }
            this.keys = new int[arrayList.size()];
            FullText.setColumns(this.keys, arrayList, arrayList2);
            this.indexColumns = new int[arrayList3.size()];
            FullText.setColumns(this.indexColumns, arrayList3, arrayList2);
        }

        @Override
        public void fire(Connection connection, Object[] objectArray, Object[] objectArray2) throws SQLException {
            if (objectArray != null) {
                if (objectArray2 != null) {
                    if (FullText.hasChanged(objectArray, objectArray2, this.indexColumns)) {
                        this.delete(objectArray, false);
                        this.insert(objectArray2, true);
                    }
                } else {
                    this.delete(objectArray, true);
                }
            } else if (objectArray2 != null) {
                this.insert(objectArray2, true);
            }
        }

        @Override
        public void close() throws SQLException {
            FullTextLucene.removeIndexAccess(this.indexPath);
        }

        void commitIndex() throws SQLException {
            try {
                this.indexAccess.commit();
            }
            catch (IOException iOException) {
                throw FullTextLucene.convertException(iOException);
            }
        }

        void insert(Object[] objectArray, boolean bl) throws SQLException {
            String string = this.getQuery(objectArray);
            Document document = new Document();
            document.add((IndexableField)new Field(FullTextLucene.LUCENE_FIELD_QUERY, (CharSequence)string, (IndexableFieldType)this.DOC_ID_FIELD_TYPE));
            long l = System.currentTimeMillis();
            document.add((IndexableField)new Field(FullTextLucene.LUCENE_FIELD_MODIFIED, (CharSequence)DateTools.timeToString((long)l, (DateTools.Resolution)DateTools.Resolution.SECOND), (IndexableFieldType)TextField.TYPE_STORED));
            StringBuilder stringBuilder = new StringBuilder();
            int n = this.indexColumns.length;
            for (int i = 0; i < n; ++i) {
                int n2 = this.indexColumns[i];
                String string2 = this.columns[n2];
                String string3 = FullText.asString(objectArray[n2], this.columnTypes[n2]);
                if (string2.startsWith(FullTextLucene.LUCENE_FIELD_COLUMN_PREFIX)) {
                    string2 = FullTextLucene.LUCENE_FIELD_COLUMN_PREFIX + string2;
                }
                document.add((IndexableField)new Field(string2, (CharSequence)string3, (IndexableFieldType)TextField.TYPE_NOT_STORED));
                if (i > 0) {
                    stringBuilder.append(' ');
                }
                stringBuilder.append(string3);
            }
            FieldType fieldType = STORE_DOCUMENT_TEXT_IN_INDEX ? TextField.TYPE_STORED : TextField.TYPE_NOT_STORED;
            document.add((IndexableField)new Field(FullTextLucene.LUCENE_FIELD_DATA, (CharSequence)stringBuilder.toString(), (IndexableFieldType)fieldType));
            try {
                this.indexAccess.writer.addDocument((Iterable)document);
                if (bl) {
                    this.commitIndex();
                }
            }
            catch (IOException iOException) {
                throw FullTextLucene.convertException(iOException);
            }
        }

        private void delete(Object[] objectArray, boolean bl) throws SQLException {
            String string = this.getQuery(objectArray);
            try {
                Term term = new Term(FullTextLucene.LUCENE_FIELD_QUERY, string);
                this.indexAccess.writer.deleteDocuments(new Term[]{term});
                if (bl) {
                    this.commitIndex();
                }
            }
            catch (IOException iOException) {
                throw FullTextLucene.convertException(iOException);
            }
        }

        private String getQuery(Object[] objectArray) throws SQLException {
            StringBuilder stringBuilder = new StringBuilder();
            if (this.schema != null) {
                StringUtils.quoteIdentifier(stringBuilder, this.schema).append('.');
            }
            StringUtils.quoteIdentifier(stringBuilder, this.table).append(" WHERE ");
            int n = this.keys.length;
            for (int i = 0; i < n; ++i) {
                if (i > 0) {
                    stringBuilder.append(" AND ");
                }
                int n2 = this.keys[i];
                StringUtils.quoteIdentifier(stringBuilder, this.columns[n2]);
                Object object = objectArray[n2];
                if (object == null) {
                    stringBuilder.append(" IS NULL");
                    continue;
                }
                stringBuilder.append('=').append(FullText.quoteSQL(object, this.columnTypes[n2]));
            }
            return stringBuilder.toString();
        }
    }
}

