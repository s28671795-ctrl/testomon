/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import liquibase.CatalogAndSchema;
import liquibase.Scope;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.core.InternalSnapshotCommandStep;
import liquibase.command.core.helpers.PreCompareCommandStep;
import liquibase.command.providers.ReferenceDatabase;
import liquibase.database.Database;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.diff.DiffGeneratorFactory;
import liquibase.diff.DiffResult;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.output.ObjectChangeFilter;
import liquibase.diff.output.report.DiffToReport;
import liquibase.exception.DatabaseException;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.SnapshotControl;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.snapshot.SnapshotListener;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.DatabaseObjectFactory;
import liquibase.structure.core.Schema;
import liquibase.util.StringUtil;

public class DiffCommandStep
extends AbstractCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"diff"};
    public static final CommandArgumentDefinition<SnapshotListener> SNAPSHOT_LISTENER_ARG;
    public static final CommandArgumentDefinition<SnapshotControl> REFERENCE_SNAPSHOT_CONTROL_ARG;
    public static final CommandArgumentDefinition<SnapshotControl> TARGET_SNAPSHOT_CONTROL_ARG;
    public static final CommandArgumentDefinition<String> FORMAT_ARG;
    public static final CommandResultDefinition<DiffResult> DIFF_RESULT;

    @Override
    public List<Class<?>> requiredDependencies() {
        return Arrays.asList(CompareControl.class, ReferenceDatabase.class);
    }

    @Override
    public List<Class<?>> providedDependencies() {
        return Collections.singletonList(DiffResult.class);
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Outputs a description of differences.  If you have a Liquibase Pro key, you can output the differences as JSON using the --format=JSON option");
    }

    public static Class<? extends DatabaseObject>[] parseSnapshotTypes(String ... snapshotTypes) {
        if (snapshotTypes == null || snapshotTypes.length == 0 || snapshotTypes[0] == null) {
            return new Class[0];
        }
        Set<Class<? extends DatabaseObject>> types = DatabaseObjectFactory.getInstance().parseTypes(StringUtil.join(snapshotTypes, ","));
        Class[] returnTypes = new Class[types.size()];
        int i = 0;
        for (Class<? extends DatabaseObject> type : types) {
            returnTypes[i++] = type;
        }
        return returnTypes;
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        try {
            CommandScope commandScope = resultsBuilder.getCommandScope();
            InternalSnapshotCommandStep.logUnsupportedDatabase((Database)commandScope.getDependency(Database.class), this.getClass());
            DiffResult diffResult = this.createDiffResult(resultsBuilder);
            resultsBuilder.addResult(DIFF_RESULT.getName(), diffResult);
            String printResult = commandScope.getArgumentValue(FORMAT_ARG);
            Scope.getCurrentScope().addMdcValue("format", printResult);
            if (printResult == null || printResult.equalsIgnoreCase("TXT")) {
                Scope.getCurrentScope().addMdcValue("format", "TXT");
                Scope.getCurrentScope().getUI().sendMessage("");
                Scope.getCurrentScope().getUI().sendMessage(coreBundle.getString("diff.results"));
                PrintStream printStream = new PrintStream(resultsBuilder.getOutputStream());
                new DiffToReport(diffResult, printStream).print();
                printStream.flush();
            }
            Scope.getCurrentScope().addMdcValue("diffOutcome", "success");
            Scope.getCurrentScope().getLog(this.getClass()).info("Diff command completed");
        }
        catch (Exception e) {
            Scope.getCurrentScope().addMdcValue("diffOutcome", "fail");
            throw e;
        }
    }

    public DiffResult createDiffResult(CommandResultsBuilder resultsBuilder) throws DatabaseException, InvalidExampleException {
        DatabaseSnapshot referenceSnapshot = this.createReferenceSnapshot(resultsBuilder);
        DatabaseSnapshot targetSnapshot = this.getTargetSnapshot(resultsBuilder);
        CompareControl compareControl = (CompareControl)resultsBuilder.getResult(PreCompareCommandStep.COMPARE_CONTROL_RESULT.getName());
        return DiffGeneratorFactory.getInstance().compare(referenceSnapshot, targetSnapshot, compareControl);
    }

    protected DatabaseSnapshot getTargetSnapshot(CommandResultsBuilder resultsBuilder) throws DatabaseException, InvalidExampleException {
        CatalogAndSchema[] schemas;
        CommandScope commandScope = resultsBuilder.getCommandScope();
        if (commandScope.getDependency(Database.class) == null) {
            return null;
        }
        Database targetDatabase = (Database)commandScope.getDependency(Database.class);
        CompareControl compareControl = (CompareControl)resultsBuilder.getResult(PreCompareCommandStep.COMPARE_CONTROL_RESULT.getName());
        if (compareControl.getSchemaComparisons() == null) {
            schemas = new CatalogAndSchema[]{targetDatabase.getDefaultSchema()};
        } else {
            schemas = new CatalogAndSchema[compareControl.getSchemaComparisons().length];
            int i = 0;
            for (CompareControl.SchemaComparison comparison : compareControl.getSchemaComparisons()) {
                CatalogAndSchema schema = targetDatabase.supports(Schema.class) ? new CatalogAndSchema(targetDatabase.getDefaultCatalogName(), comparison.getComparisonSchema().getSchemaName()) : new CatalogAndSchema(comparison.getComparisonSchema().getSchemaName(), comparison.getComparisonSchema().getSchemaName());
                schemas[i++] = schema;
            }
        }
        SnapshotControl snapshotControl = commandScope.getArgumentValue(TARGET_SNAPSHOT_CONTROL_ARG);
        if (snapshotControl == null) {
            ObjectChangeFilter objectChangeFilter = (ObjectChangeFilter)resultsBuilder.getResult(PreCompareCommandStep.OBJECT_CHANGE_FILTER_RESULT.getName());
            snapshotControl = new SnapshotControl(targetDatabase, objectChangeFilter, (Class[])resultsBuilder.getResult(PreCompareCommandStep.SNAPSHOT_TYPES_RESULT.getName()));
        }
        return this.generateDatabaseShapshot(commandScope, targetDatabase, compareControl, schemas, snapshotControl);
    }

    protected DatabaseSnapshot createReferenceSnapshot(CommandResultsBuilder resultsBuilder) throws DatabaseException, InvalidExampleException {
        CatalogAndSchema[] schemas;
        CommandScope commandScope = resultsBuilder.getCommandScope();
        Database targetDatabase = (Database)commandScope.getDependency(Database.class);
        Database referenceDatabase = (Database)commandScope.getDependency(ReferenceDatabase.class);
        CompareControl compareControl = (CompareControl)resultsBuilder.getResult(PreCompareCommandStep.COMPARE_CONTROL_RESULT.getName());
        Class[] snapshotTypes = (Class[])resultsBuilder.getResult(PreCompareCommandStep.SNAPSHOT_TYPES_RESULT.getName());
        ObjectChangeFilter objectChangeFilter = (ObjectChangeFilter)resultsBuilder.getResult(PreCompareCommandStep.OBJECT_CHANGE_FILTER_RESULT.getName());
        if (compareControl.getSchemaComparisons() == null) {
            schemas = new CatalogAndSchema[]{targetDatabase != null ? targetDatabase.getDefaultSchema() : referenceDatabase.getDefaultSchema()};
        } else {
            schemas = new CatalogAndSchema[compareControl.getSchemaComparisons().length];
            int i = 0;
            for (CompareControl.SchemaComparison comparison : compareControl.getSchemaComparisons()) {
                CatalogAndSchema schema = referenceDatabase.supports(Schema.class) ? new CatalogAndSchema(referenceDatabase.getDefaultCatalogName(), comparison.getReferenceSchema().getSchemaName()) : new CatalogAndSchema(comparison.getReferenceSchema().getSchemaName(), comparison.getReferenceSchema().getSchemaName());
                schemas[i++] = schema;
            }
        }
        SnapshotControl snapshotControl = commandScope.getArgumentValue(REFERENCE_SNAPSHOT_CONTROL_ARG);
        if (snapshotControl == null) {
            snapshotControl = new SnapshotControl(referenceDatabase, objectChangeFilter, snapshotTypes);
        }
        return this.generateDatabaseShapshot(commandScope, referenceDatabase, compareControl, schemas, snapshotControl);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private DatabaseSnapshot generateDatabaseShapshot(CommandScope commandScope, Database database, CompareControl compareControl, CatalogAndSchema[] schemas, SnapshotControl snapshotControl) throws DatabaseException, InvalidExampleException {
        SnapshotListener snapshotListener = commandScope.getArgumentValue(SNAPSHOT_LISTENER_ARG);
        if (snapshotListener != null) {
            snapshotControl.setSnapshotListener(snapshotListener);
        }
        ObjectQuotingStrategy originalStrategy = database.getObjectQuotingStrategy();
        try {
            database.setObjectQuotingStrategy(ObjectQuotingStrategy.QUOTE_ALL_OBJECTS);
            DatabaseSnapshot snapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(schemas, database, snapshotControl);
            snapshot.setSchemaComparisons(compareControl.getSchemaComparisons());
            DatabaseSnapshot databaseSnapshot = snapshot;
            return databaseSnapshot;
        }
        finally {
            database.setObjectQuotingStrategy(originalStrategy);
        }
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        SNAPSHOT_LISTENER_ARG = builder.argument("snapshotListener", SnapshotListener.class).hidden().build();
        REFERENCE_SNAPSHOT_CONTROL_ARG = builder.argument("referenceSnapshotControl", SnapshotControl.class).hidden().build();
        TARGET_SNAPSHOT_CONTROL_ARG = builder.argument("targetSnapshotControl", SnapshotControl.class).hidden().build();
        FORMAT_ARG = builder.argument("format", String.class).description("Output format. Default: TXT").hidden().build();
        DIFF_RESULT = builder.result("diffResult", DiffResult.class).description("Databases diff result").build();
    }
}

