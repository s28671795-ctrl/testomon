/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot;

import liquibase.database.Database;
import liquibase.snapshot.ResultSetCache;

public class ResultSetCacheSnowflake
extends ResultSetCache {

    public static abstract class SingleResultSetExtractor
    extends ResultSetCache.SingleResultSetExtractor {
        public SingleResultSetExtractor(Database database) {
            super(database);
        }
    }

    public static class RowData
    extends ResultSetCache.RowData {
        public RowData(String catalog, String schema, Database database, String ... parameters) {
            super(catalog, schema, database, parameters);
        }
    }
}

