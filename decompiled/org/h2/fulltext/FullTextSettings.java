/*
 * Decompiled with CFR 0.152.
 */
package org.h2.fulltext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.h2.fulltext.FullText;
import org.h2.fulltext.IndexInfo;
import org.h2.util.SoftValuesHashMap;

final class FullTextSettings {
    private static final HashMap<String, FullTextSettings> SETTINGS = new HashMap();
    private boolean initialized;
    private final HashSet<String> ignoreList = new HashSet();
    private final HashMap<String, Integer> words = new HashMap();
    private final ConcurrentHashMap<Integer, IndexInfo> indexes = new ConcurrentHashMap();
    private final WeakHashMap<Connection, SoftValuesHashMap<String, PreparedStatement>> cache = new WeakHashMap();
    private String whitespaceChars = " \t\n\r\f+\"*%&/()=?'!,.;:-_#@|^~`{}[]<>\\";

    private FullTextSettings() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clearIgnored() {
        HashSet<String> hashSet = this.ignoreList;
        synchronized (hashSet) {
            this.ignoreList.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addIgnored(Iterable<String> iterable) {
        HashSet<String> hashSet = this.ignoreList;
        synchronized (hashSet) {
            for (String string : iterable) {
                string = FullTextSettings.normalizeWord(string);
                this.ignoreList.add(string);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clearWordList() {
        HashMap<String, Integer> hashMap = this.words;
        synchronized (hashMap) {
            this.words.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Integer getWordId(String string) {
        HashMap<String, Integer> hashMap = this.words;
        synchronized (hashMap) {
            return this.words.get(string);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addWord(String string, Integer n) {
        HashMap<String, Integer> hashMap = this.words;
        synchronized (hashMap) {
            this.words.putIfAbsent(string, n);
        }
    }

    IndexInfo getIndexInfo(int n) {
        return this.indexes.get(n);
    }

    void addIndexInfo(IndexInfo indexInfo) {
        this.indexes.put(indexInfo.id, indexInfo);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    String convertWord(String string) {
        string = FullTextSettings.normalizeWord(string);
        HashSet<String> hashSet = this.ignoreList;
        synchronized (hashSet) {
            if (this.ignoreList.contains(string)) {
                return null;
            }
        }
        return string;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static FullTextSettings getInstance(Connection connection) throws SQLException {
        FullTextSettings fullTextSettings;
        String string = FullTextSettings.getIndexPath(connection);
        HashMap<String, FullTextSettings> hashMap = SETTINGS;
        synchronized (hashMap) {
            fullTextSettings = SETTINGS.get(string);
            if (fullTextSettings == null) {
                fullTextSettings = new FullTextSettings();
                SETTINGS.put(string, fullTextSettings);
            }
        }
        return fullTextSettings;
    }

    private static String getIndexPath(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("CALL COALESCE(DATABASE_PATH(), 'MEM:' || DATABASE())");
        resultSet.next();
        String string = resultSet.getString(1);
        if ("MEM:UNNAMED".equals(string)) {
            throw FullText.throwException("Fulltext search for private (unnamed) in-memory databases is not supported.");
        }
        resultSet.close();
        return string;
    }

    synchronized PreparedStatement prepare(Connection connection, String string) throws SQLException {
        PreparedStatement preparedStatement;
        SoftValuesHashMap<String, PreparedStatement> softValuesHashMap = this.cache.get(connection);
        if (softValuesHashMap == null) {
            softValuesHashMap = new SoftValuesHashMap();
            this.cache.put(connection, softValuesHashMap);
        }
        if ((preparedStatement = softValuesHashMap.get(string)) != null && preparedStatement.getConnection().isClosed()) {
            preparedStatement = null;
        }
        if (preparedStatement == null) {
            preparedStatement = connection.prepareStatement(string);
            softValuesHashMap.put(string, preparedStatement);
        }
        return preparedStatement;
    }

    void removeAllIndexes() {
        this.indexes.clear();
    }

    void removeIndexInfo(IndexInfo indexInfo) {
        this.indexes.remove(indexInfo.id);
    }

    void setInitialized(boolean bl) {
        this.initialized = bl;
    }

    boolean isInitialized() {
        return this.initialized;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void closeAll() {
        HashMap<String, FullTextSettings> hashMap = SETTINGS;
        synchronized (hashMap) {
            SETTINGS.clear();
        }
    }

    void setWhitespaceChars(String string) {
        this.whitespaceChars = string;
    }

    String getWhitespaceChars() {
        return this.whitespaceChars;
    }

    private static String normalizeWord(String string) {
        return string.toUpperCase();
    }
}

