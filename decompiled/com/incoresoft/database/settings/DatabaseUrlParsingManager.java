/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database.settings;

import com.incoresoft.database.settings.DatabaseUrlComponents;
import com.incoresoft.database.settings.exceptions.DatabaseTypeNotSupportedException;
import com.incoresoft.database.settings.exceptions.DatabaseUrlParsingException;
import com.incoresoft.middleware.database.DBType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseUrlParsingManager {
    private static final int CONNECTION_TIMEOUT_SECONDS = 5;
    private static final Pattern MYSQL_PATTERN = Pattern.compile("^jdbc:mysql://([^/]+)/([^?]+)");
    private static final Pattern POSTGRES_PATTERN = Pattern.compile("^jdbc:postgresql://([^/]+)/([^?]+)");
    private static final Pattern SQL_SERVER_PATTERN = Pattern.compile("^jdbc:sqlserver://([^;]+);databaseName=([^;]+)");
    private static final Pattern H2_PATTERN = Pattern.compile("^jdbc:h2:\\./([^;]+)");

    public DatabaseUrlComponents parseUrl(String url2) {
        try {
            DBType type = DBType.valueOf(url2.split(":")[1]);
            return switch (type) {
                case DBType.h2 -> this.parseH2Url(url2);
                case DBType.mysql -> this.parseMySqlUrl(url2);
                case DBType.postgresql -> this.parsePostgresUrl(url2);
                case DBType.sqlserver -> this.parseSqlServerUrl(url2);
                default -> throw new DatabaseTypeNotSupportedException(type);
            };
        }
        catch (Exception e) {
            throw new DatabaseUrlParsingException(e);
        }
    }

    public String parseUrlComponents(DatabaseUrlComponents components) {
        try {
            return switch (components.getType()) {
                case DBType.h2 -> String.format("jdbc:h2:./%s", components.getDatabaseName());
                case DBType.mysql -> String.format("jdbc:mysql://%s/%s?zeroDateTimeBehavior=convertToNull&characterEncoding=utf8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true&connectTimeout=%d", components.getHost(), components.getDatabaseName(), 5000);
                case DBType.postgresql -> String.format("jdbc:postgresql://%s/%s?connectTimeout=%d", components.getHost(), components.getDatabaseName(), 5);
                case DBType.sqlserver -> String.format("jdbc:sqlserver://%s;databaseName=%s;encrypt=false;trustServerCertificate=true;loginTimeout=%d;", components.getHost(), components.getDatabaseName(), 5);
                default -> throw new DatabaseTypeNotSupportedException(components.getType());
            };
        }
        catch (Exception e) {
            throw new DatabaseUrlParsingException(e);
        }
    }

    private DatabaseUrlComponents parseH2Url(String url2) {
        Matcher matcher = H2_PATTERN.matcher(url2);
        if (matcher.find()) {
            String databaseName = matcher.group(1);
            return new DatabaseUrlComponents(DBType.h2, null, databaseName);
        }
        throw new RuntimeException("The JDBC URL does not match the expected format.");
    }

    private DatabaseUrlComponents parseMySqlUrl(String url2) {
        Matcher matcher = MYSQL_PATTERN.matcher(url2);
        if (matcher.find()) {
            String host2 = matcher.group(1);
            String databaseName = matcher.group(2);
            return new DatabaseUrlComponents(DBType.mysql, host2, databaseName);
        }
        throw new RuntimeException("The JDBC URL does not match the expected format.");
    }

    private DatabaseUrlComponents parseSqlServerUrl(String url2) {
        Matcher matcher = SQL_SERVER_PATTERN.matcher(url2);
        if (matcher.find()) {
            String host2 = matcher.group(1);
            String databaseName = matcher.group(2);
            return new DatabaseUrlComponents(DBType.sqlserver, host2, databaseName);
        }
        throw new RuntimeException("The JDBC URL does not match the expected format.");
    }

    private DatabaseUrlComponents parsePostgresUrl(String url2) {
        Matcher matcher = POSTGRES_PATTERN.matcher(url2);
        if (matcher.find()) {
            String host2 = matcher.group(1);
            String databaseName = matcher.group(2);
            return new DatabaseUrlComponents(DBType.postgresql, host2, databaseName);
        }
        throw new RuntimeException("The JDBC URL does not match the expected format.");
    }
}

