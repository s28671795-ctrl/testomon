/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import liquibase.CatalogAndSchema;
import liquibase.database.Database;
import liquibase.database.core.InformixDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.executor.jvm.ColumnMapRowMapper;
import liquibase.executor.jvm.RowMapperResultSetExtractor;
import liquibase.snapshot.CachedRow;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Schema;
import liquibase.util.JdbcUtil;
import liquibase.util.StringUtil;

public class ResultSetCache {
    private final Map<String, Integer> timesSingleQueried = new HashMap<String, Integer>();
    private final Map<String, Boolean> didBulkQuery = new HashMap<String, Boolean>();
    private boolean bulkTracking = true;
    private final Map<String, Map<String, List<CachedRow>>> cacheBySchema = new ConcurrentHashMap<String, Map<String, List<CachedRow>>>();
    private final Map<String, Object> info = new ConcurrentHashMap<String, Object>();

    public List<CachedRow> get(ResultSetExtractor resultSetExtractor) throws DatabaseException {
        try {
            ArrayList returnList;
            List<CachedRow> results;
            String wantedKey = resultSetExtractor.wantedKeyParameters().createParamsKey(resultSetExtractor.database);
            String schemaKey = resultSetExtractor.wantedKeyParameters().createSchemaKey(resultSetExtractor.database);
            Map<Object, Object> cache = this.cacheBySchema.computeIfAbsent(schemaKey, k -> new HashMap());
            if (cache.containsKey(wantedKey)) {
                return (List)cache.get(wantedKey);
            }
            if (this.didBulkQuery.containsKey(schemaKey) && this.didBulkQuery.get(schemaKey).booleanValue()) {
                return new ArrayList<CachedRow>();
            }
            boolean bulkQueried = false;
            if (resultSetExtractor.shouldBulkSelect(schemaKey, this)) {
                if (resultSetExtractor.bulkContainsSchema(schemaKey)) {
                    for (Map<String, List<CachedRow>> cachedValue : this.cacheBySchema.values()) {
                        cachedValue.clear();
                    }
                } else {
                    cache.clear();
                }
                results = resultSetExtractor.bulkFetch();
                this.didBulkQuery.put(schemaKey, this.bulkTracking);
                bulkQueried = true;
            } else {
                cache = new HashMap();
                Object previousCount = this.timesSingleQueried.get(schemaKey);
                if (previousCount == null) {
                    previousCount = 0;
                }
                this.timesSingleQueried.put(schemaKey, (Integer)previousCount + 1);
                results = resultSetExtractor.fastFetch();
            }
            for (CachedRow row : results) {
                for (String rowKey : resultSetExtractor.rowKeyParameters(row).getKeyPermutations()) {
                    if (bulkQueried && resultSetExtractor.bulkContainsSchema(schemaKey)) {
                        String rowSchema = CatalogAndSchema.CatalogAndSchemaCase.ORIGINAL_CASE.equals((Object)resultSetExtractor.database.getSchemaAndCatalogCase()) ? resultSetExtractor.getSchemaKey(row) : resultSetExtractor.getSchemaKey(row).toLowerCase();
                        cache = this.cacheBySchema.computeIfAbsent(rowSchema, k -> new HashMap());
                    }
                    if (!cache.containsKey(rowKey)) {
                        cache.put(rowKey, new ArrayList());
                    }
                    ((List)cache.get(rowKey)).add(row);
                }
            }
            if (bulkQueried) {
                cache = this.cacheBySchema.get(schemaKey);
            }
            if ((returnList = (ArrayList)cache.get(wantedKey)) == null) {
                returnList = new ArrayList();
            }
            return returnList;
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public <T> T getInfo(String key, Class<T> type) {
        return (T)this.info.get(key);
    }

    public void putInfo(String key, Object value) {
        this.info.put(key, value);
    }

    private int getTimesSingleQueried(String schemaKey) {
        Integer integer = this.timesSingleQueried.get(schemaKey);
        if (integer == null) {
            return 0;
        }
        return integer;
    }

    public void setBulkTracking(boolean bulkTracking) {
        this.bulkTracking = bulkTracking;
    }

    public static abstract class ResultSetExtractor {
        private final Database database;

        public ResultSetExtractor(Database database) {
            this.database = database;
        }

        public abstract boolean bulkContainsSchema(String var1);

        public String getSchemaKey(CachedRow row) {
            throw new UnexpectedLiquibaseException("Not Implemented");
        }

        protected boolean shouldBulkSelect(String schemaKey, ResultSetCache resultSetCache) {
            return resultSetCache.getTimesSingleQueried(schemaKey) >= 3;
        }

        protected List<CachedRow> executeAndExtract(String sql, Database database) throws DatabaseException, SQLException {
            return this.executeAndExtract(database, false, sql, new Object[0]);
        }

        protected List<CachedRow> executeAndExtract(Database database, String sql, Object ... parameters) throws DatabaseException, SQLException {
            return this.executeAndExtract(database, false, sql, parameters);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        protected List<CachedRow> executeAndExtract(Database database, boolean informixTrimHint, String sql, Object ... parameters) throws DatabaseException, SQLException {
            List<CachedRow> list;
            if (sql == null) {
                return new ArrayList<CachedRow>();
            }
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                JdbcConnection connection = (JdbcConnection)database.getConnection();
                statement = connection.prepareStatement(sql);
                for (int i = 0; i < parameters.length; ++i) {
                    statement.setObject(i + 1, parameters[i]);
                }
                resultSet = statement.executeQuery();
                resultSet.setFetchSize(database.getFetchSize());
                list = this.extract(resultSet, informixTrimHint);
            }
            catch (Throwable throwable) {
                JdbcUtil.close(resultSet, statement);
                throw throwable;
            }
            JdbcUtil.close(resultSet, statement);
            return list;
        }

        public boolean equals(Object expectedValue, Object foundValue) {
            return this.equals(expectedValue, foundValue, true);
        }

        public boolean equals(Object expectedValue, Object foundValue, boolean equalIfEitherNull) {
            if (expectedValue == null && foundValue == null) {
                return true;
            }
            if (expectedValue == null || foundValue == null) {
                return equalIfEitherNull;
            }
            return expectedValue.equals(foundValue);
        }

        public abstract RowData rowKeyParameters(CachedRow var1);

        public abstract RowData wantedKeyParameters();

        public abstract List<CachedRow> fastFetch() throws SQLException, DatabaseException;

        public abstract List<CachedRow> bulkFetch() throws SQLException, DatabaseException;

        protected List<CachedRow> extract(ResultSet resultSet) throws SQLException {
            return this.extract(resultSet, false);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        protected List<CachedRow> extract(ResultSet resultSet, final boolean informixIndexTrimHint) throws SQLException {
            resultSet.setFetchSize(this.database.getFetchSize());
            ArrayList<CachedRow> returnList = new ArrayList<CachedRow>();
            try {
                List result = (List)new RowMapperResultSetExtractor(new ColumnMapRowMapper(this.database.isCaseSensitive()){

                    @Override
                    protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
                        Object value = super.getColumnValue(rs, index);
                        if (value instanceof String) {
                            if (!informixIndexTrimHint) {
                                value = ((String)value).trim();
                            } else {
                                boolean startsWithSpace = database instanceof InformixDatabase && ((String)value).matches("^ .*$");
                                value = ((String)value).trim();
                                if (startsWithSpace) {
                                    value = " " + value;
                                }
                            }
                        }
                        return value;
                    }
                }).extractData(resultSet);
                for (Map row : result) {
                    returnList.add(new CachedRow(row));
                }
            }
            finally {
                JdbcUtil.closeResultSet(resultSet);
            }
            return returnList;
        }
    }

    public static class RowData {
        private final Database database;
        private final String[] parameters;
        private final String catalog;
        private final String schema;
        private String[] keyPermutations;

        protected RowData(String catalog, String schema, Database database, String ... parameters) {
            this.database = database;
            this.catalog = catalog;
            this.schema = schema;
            this.parameters = parameters;
        }

        public String[] getKeyPermutations() {
            if (this.keyPermutations == null) {
                this.keyPermutations = this.permutations(this.parameters);
            }
            return this.keyPermutations;
        }

        protected String[] permutations(String[] params) {
            return this.permute(params, 0);
        }

        private String[] permute(String[] params, int fromIndex) {
            String[] nullVersion = Arrays.copyOf(params, params.length);
            nullVersion[fromIndex] = null;
            if (params.length == fromIndex + 1) {
                return new String[]{this.createKey(this.database, params), this.createKey(this.database, nullVersion)};
            }
            ArrayList permutations = new ArrayList();
            Collections.addAll(permutations, this.permute(params, fromIndex + 1));
            Collections.addAll(permutations, this.permute(nullVersion, fromIndex + 1));
            return permutations.toArray(new String[0]);
        }

        public String createSchemaKey(Database database) {
            if (!database.supports(Catalog.class) && !database.supports(Schema.class)) {
                return "all";
            }
            if (database.supports(Catalog.class) && database.supports(Schema.class)) {
                if (CatalogAndSchema.CatalogAndSchemaCase.ORIGINAL_CASE.equals((Object)database.getSchemaAndCatalogCase())) {
                    return this.catalog + "." + this.schema;
                }
                return (this.catalog + "." + this.schema).toLowerCase();
            }
            if (this.catalog == null && this.schema != null) {
                if (CatalogAndSchema.CatalogAndSchemaCase.ORIGINAL_CASE.equals((Object)database.getSchemaAndCatalogCase())) {
                    return this.schema;
                }
                return this.schema.toLowerCase();
            }
            if (this.catalog == null) {
                return "all";
            }
            if (CatalogAndSchema.CatalogAndSchemaCase.ORIGINAL_CASE.equals((Object)database.getSchemaAndCatalogCase())) {
                return this.catalog;
            }
            return this.catalog.toLowerCase();
        }

        public String createKey(Database database, String ... params) {
            String key = StringUtil.join(params, ":");
            if (CatalogAndSchema.CatalogAndSchemaCase.ORIGINAL_CASE.equals((Object)database.getSchemaAndCatalogCase())) {
                return key;
            }
            if (!database.isCaseSensitive()) {
                return key.toLowerCase();
            }
            return key;
        }

        public String createParamsKey(Database database) {
            return this.createKey(database, this.parameters);
        }
    }

    public static abstract class UnionResultSetExtractor
    extends ResultSetExtractor {
        protected UnionResultSetExtractor(Database database) {
            super(database);
        }
    }

    public static abstract class SingleResultSetExtractor
    extends ResultSetExtractor {
        public SingleResultSetExtractor(Database database) {
            super(database);
        }

        public abstract List<CachedRow> fastFetchQuery() throws SQLException, DatabaseException;

        public abstract List<CachedRow> bulkFetchQuery() throws SQLException, DatabaseException;

        @Override
        public List<CachedRow> fastFetch() throws SQLException, DatabaseException {
            return this.fastFetchQuery();
        }

        @Override
        public List<CachedRow> bulkFetch() throws SQLException, DatabaseException {
            return this.bulkFetchQuery();
        }
    }
}

