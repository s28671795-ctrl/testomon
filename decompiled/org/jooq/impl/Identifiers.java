/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.EnumMap;
import org.jooq.SQLDialect;

final class Identifiers {
    static final EnumMap<SQLDialect, char[][][]> QUOTES = new EnumMap(SQLDialect.class);
    static final int QUOTE_START_DELIMITER = 0;
    static final int QUOTE_END_DELIMITER = 1;
    static final int QUOTE_END_DELIMITER_ESCAPED = 2;

    Identifiers() {
    }

    static {
        block4: for (SQLDialect family : SQLDialect.families()) {
            switch (family) {
                case AURORA_MYSQL: 
                case BIGQUERY: 
                case MEMSQL: 
                case MARIADB: 
                case MYSQL: {
                    QUOTES.put(family, new char[][][]{new char[][]{{'`'}, {'\"'}}, new char[][]{{'`'}, {'\"'}}, new char[][]{{'`', '`'}, {'\"', '\"'}}});
                    continue block4;
                }
                case ACCESS: 
                case ASE: 
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: 
                case SYBASE: {
                    QUOTES.put(family, new char[][][]{new char[][]{{'['}}, new char[][]{{']'}}, new char[][]{{']', ']'}}});
                    continue block4;
                }
                default: {
                    QUOTES.put(family, new char[][][]{new char[][]{{'\"'}}, new char[][]{{'\"'}}, new char[][]{{'\"', '\"'}}});
                }
            }
        }
    }
}

