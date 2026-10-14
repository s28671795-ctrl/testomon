/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import liquibase.Scope;
import liquibase.parser.LiquibaseSqlParser;
import liquibase.parser.SqlParserFactory;
import liquibase.util.StringClauses;

@Deprecated
public class SqlParser {
    @Deprecated
    public static StringClauses parse(String sqlBlock) {
        SqlParserFactory sqlParserFactory = Scope.getCurrentScope().getSingleton(SqlParserFactory.class);
        LiquibaseSqlParser sqlParser = sqlParserFactory.getSqlParser();
        return sqlParser.parse(sqlBlock);
    }

    @Deprecated
    public static StringClauses parse(String sqlBlock, boolean preserveWhitespace, boolean preserveComments) {
        SqlParserFactory sqlParserFactory = Scope.getCurrentScope().getSingleton(SqlParserFactory.class);
        LiquibaseSqlParser sqlParser = sqlParserFactory.getSqlParser();
        return sqlParser.parse(sqlBlock, preserveWhitespace, preserveComments);
    }
}

