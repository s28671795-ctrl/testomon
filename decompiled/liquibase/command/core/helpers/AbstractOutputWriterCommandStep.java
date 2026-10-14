/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core.helpers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.List;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.command.CleanUpCommandStep;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.core.helpers.AbstractHelperCommandStep;
import liquibase.database.Database;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.executor.LoggingExecutor;

public abstract class AbstractOutputWriterCommandStep
extends AbstractHelperCommandStep
implements CleanUpCommandStep {
    private OutputStreamWriter outputStreamWriter;

    @Override
    public List<Class<?>> providedDependencies() {
        return Collections.singletonList(this.getProvidedWriterDependency());
    }

    public abstract Class<?> getProvidedWriterDependency();

    @Override
    public List<Class<?>> requiredDependencies() {
        return Collections.singletonList(this.getDatabaseDependency());
    }

    public abstract Class<?> getDatabaseDependency();

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();
        String charsetName = GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue();
        this.outputStreamWriter = new OutputStreamWriter(resultsBuilder.getOutputStream(), charsetName);
        Database database = (Database)commandScope.getDependency(Database.class);
        Executor databaseExecutor = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
        LoggingExecutor loggingExecutor = new LoggingExecutor(databaseExecutor, this.outputStreamWriter, database);
        Scope.getCurrentScope().getSingleton(ExecutorService.class).setExecutor("jdbc", database, loggingExecutor);
        Scope.getCurrentScope().getSingleton(ExecutorService.class).setExecutor("logging", database, loggingExecutor);
        commandScope.provideDependency(this.getProvidedWriterDependency(), this.outputStreamWriter);
    }

    @Override
    public String[][] defineCommandNames() {
        String name = "outputWriterCommandStep-" + this.getDatabaseDependency().getSimpleName();
        return new String[][]{{name}};
    }

    @Override
    public void cleanUp(CommandResultsBuilder resultsBuilder) {
        if (this.outputStreamWriter != null) {
            Scope.getCurrentScope().getSingleton(ExecutorService.class).reset();
            try {
                this.outputStreamWriter.close();
            }
            catch (IOException e) {
                Scope.getCurrentScope().getLog(this.getClass()).warning("Failed to close output stream writer.", e);
            }
        }
    }
}

