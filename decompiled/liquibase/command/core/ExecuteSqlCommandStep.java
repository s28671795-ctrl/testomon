/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.changeset.ChangeSetService;
import liquibase.changeset.ChangeSetServiceFactory;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.lockservice.LockService;
import liquibase.resource.PathHandlerFactory;
import liquibase.resource.Resource;
import liquibase.statement.core.RawSqlStatement;
import liquibase.util.FileUtil;
import liquibase.util.StreamUtil;
import liquibase.util.StringUtil;

public class ExecuteSqlCommandStep
extends AbstractCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"executeSql"};
    public static final CommandArgumentDefinition<String> SQL_ARG;
    public static final CommandArgumentDefinition<String> SQLFILE_ARG;
    public static final CommandArgumentDefinition<String> DELIMITER_ARG;

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Execute a SQL string or file");
    }

    @Override
    public List<Class<?>> requiredDependencies() {
        return Arrays.asList(Database.class, LockService.class);
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        String[] sqlStrings;
        CommandScope commandScope = resultsBuilder.getCommandScope();
        Database database = (Database)commandScope.getDependency(Database.class);
        String sql = commandScope.getArgumentValue(SQL_ARG);
        String sqlFile = commandScope.getArgumentValue(SQLFILE_ARG);
        Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
        String sqlText = this.getSqlScript(sql, sqlFile);
        StringBuilder out = new StringBuilder();
        for (String sqlString : sqlStrings = StringUtil.processMultiLineSQL(sqlText, true, true, ExecuteSqlCommandStep.determineEndDelimiter(commandScope), null)) {
            if (sqlString.toLowerCase().matches("\\s*select .*")) {
                out.append(this.handleSelect(sqlString, executor2));
            } else {
                executor2.execute(new RawSqlStatement(sqlString));
                out.append("Successfully Executed: ").append(sqlString).append("\n");
            }
            out.append("\n");
        }
        database.commit();
        this.handleOutput(resultsBuilder, out.toString());
        resultsBuilder.addResult("output", out.toString());
    }

    protected static String determineEndDelimiter(CommandScope commandScope) {
        String delimiter = commandScope.getArgumentValue(DELIMITER_ARG);
        if (delimiter == null) {
            ChangeSetService service2 = ChangeSetServiceFactory.getInstance().createChangeSetService();
            delimiter = service2.getEndDelimiter(null);
        }
        return delimiter;
    }

    protected void handleOutput(CommandResultsBuilder resultsBuilder, String output) throws IOException {
        String charsetName = GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue();
        OutputStreamWriter outputWriter = new OutputStreamWriter(resultsBuilder.getOutputStream(), charsetName);
        outputWriter.write(output);
        ((Writer)outputWriter).flush();
    }

    protected String getSqlScript(String sql, String sqlFile) throws IOException, LiquibaseException {
        if (sqlFile == null) {
            return sql;
        }
        PathHandlerFactory pathHandlerFactory = Scope.getCurrentScope().getSingleton(PathHandlerFactory.class);
        Resource resource = pathHandlerFactory.getResource(sqlFile);
        if (!resource.exists()) {
            throw new LiquibaseException(FileUtil.getFileNotFoundMessage(sqlFile));
        }
        return StreamUtil.readStreamAsString(resource.openInputStream());
    }

    private String handleSelect(String sqlString, Executor executor2) throws DatabaseException {
        StringBuilder out = new StringBuilder();
        List<Map<String, ?>> rows = executor2.queryForList(new RawSqlStatement(sqlString));
        out.append("Output of ").append(sqlString).append(":\n");
        if (rows.isEmpty()) {
            out.append("-- Empty Resultset --\n");
        } else {
            LinkedHashSet<String> keys2 = new LinkedHashSet<String>();
            for (Map<String, ?> row : rows) {
                keys2.addAll(row.keySet());
            }
            out.append(StringUtil.join(keys2, " | ")).append(" |\n");
            for (Map<String, ?> row : rows) {
                for (String key : keys2) {
                    out.append(row.get(key)).append(" | ");
                }
                out.append("\n");
            }
        }
        return out.toString();
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        SQL_ARG = builder.argument("sql", String.class).description("SQL string to execute").build();
        SQLFILE_ARG = builder.argument("sqlFile", String.class).description("SQL script to execute").build();
        DELIMITER_ARG = builder.argument("delimiter", String.class).description("Delimiter to use when executing SQL script").build();
    }
}

