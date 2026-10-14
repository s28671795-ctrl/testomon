/*
 * Decompiled with CFR 0.152.
 */
package liquibase.serializer.core.formattedsql;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.ContextExpression;
import liquibase.GlobalConfiguration;
import liquibase.Labels;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.changelog.ChangeLogChild;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.serializer.ChangeLogSerializer;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;

public class FormattedSqlChangeLogSerializer
implements ChangeLogSerializer {
    private static final String SQL_FILE_NAME_REGEX = ".*\\.(\\w+)\\.sql";
    private static final Pattern SQL_FILE_NAME_PATTERN = Pattern.compile(".*\\.(\\w+)\\.sql");

    @Override
    public String[] getValidFileExtensions() {
        return new String[]{"sql"};
    }

    @Override
    public String serialize(LiquibaseSerializable object, boolean pretty) {
        if (object instanceof ChangeSet) {
            ChangeSet changeSet = (ChangeSet)object;
            Database database = (Database)((Object)Scope.getCurrentScope().get("snapshotDatabase", Database.class));
            if (database == null) {
                database = this.getTargetDatabase(changeSet);
            }
            StringBuilder builder = new StringBuilder();
            this.createChangeSetInfo(changeSet, builder);
            for (Change change : changeSet.getChanges()) {
                Sql[] sqls = SqlGeneratorFactory.getInstance().generateSql(change.generateStatements(database), database);
                if (sqls == null) continue;
                for (Sql sql : sqls) {
                    builder.append(sql.toSql().endsWith(sql.getEndDelimiter()) ? sql.toSql() : sql.toSql() + sql.getEndDelimiter()).append("\n");
                }
            }
            return builder.toString();
        }
        throw new UnexpectedLiquibaseException("Cannot serialize object type: " + object.getClass().getName());
    }

    public void createChangeSetInfo(ChangeSet changeSet, StringBuilder builder) {
        ContextExpression contexts;
        String author = changeSet.getAuthor().replaceAll("\\s+", "_");
        author = author.replace("_(generated)", "");
        builder.append("-- changeset ").append(author).append(":").append(changeSet.getId());
        Labels labels = changeSet.getLabels();
        if (labels != null && !labels.isEmpty()) {
            String outputLabels = labels.toString();
            builder.append(" labels: \"");
            builder.append(outputLabels);
            builder.append("\"");
        }
        if ((contexts = changeSet.getContextFilter()) != null && !contexts.isEmpty()) {
            String outputContexts = contexts.toString();
            builder.append(" contextFilter: \"");
            builder.append(outputContexts);
            builder.append("\"");
        }
        builder.append("\n");
    }

    protected Database getTargetDatabase(ChangeSet changeSet) {
        String filePath = changeSet.getFilePath();
        if (filePath == null) {
            throw new UnexpectedLiquibaseException("You must specify the changelog file name as filename.DB_TYPE.sql. Example: changelog.mysql.sql");
        }
        Matcher matcher = SQL_FILE_NAME_PATTERN.matcher(filePath);
        if (!matcher.matches()) {
            throw new UnexpectedLiquibaseException("Serializing changelog as sql requires a file name in the format *.databaseType.sql. Example: changelog.h2.sql. Passed: " + filePath);
        }
        String shortName = matcher.replaceFirst("$1");
        Database database = DatabaseFactory.getInstance().getDatabase(shortName);
        if (database == null) {
            List<Database> databases = DatabaseFactory.getInstance().getImplementedDatabases();
            StringBuilder availableDbs = new StringBuilder();
            availableDbs.append("Available database short names for serialization:\n");
            for (Database db : databases) {
                availableDbs.append("  ").append(db.getShortName()).append("\n");
            }
            throw new UnexpectedLiquibaseException("Serializing changelog as sql requires a file name in the format *.databaseType.sql. Example: changelog.h2.sql. Unknown database type: " + shortName + "\n" + availableDbs);
        }
        return database;
    }

    @Override
    public <T extends ChangeLogChild> void write(List<T> children, OutputStream out) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("-- liquibase formatted sql\n\n");
        for (ChangeLogChild child : children) {
            builder.append(this.serialize(child, true));
            builder.append("\n");
        }
        out.write(builder.toString().getBytes(GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()));
    }

    @Override
    public void append(ChangeSet changeSet, File changeLogFile) throws IOException {
    }

    @Override
    public int getPriority() {
        return 1;
    }
}

