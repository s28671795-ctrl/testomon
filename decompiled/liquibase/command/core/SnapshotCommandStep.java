/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import liquibase.CatalogAndSchema;
import liquibase.GlobalConfiguration;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.core.InternalSnapshotCommandStep;
import liquibase.database.Database;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.serializer.SnapshotSerializerFactory;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.SnapshotControl;
import liquibase.snapshot.SnapshotGeneratorFactory;

public class SnapshotCommandStep
extends AbstractCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"snapshot"};
    public static final CommandArgumentDefinition<String> SCHEMAS_ARG;
    public static final CommandArgumentDefinition<String> SNAPSHOT_FORMAT_ARG;
    public static final CommandArgumentDefinition<SnapshotControl> SNAPSHOT_CONTROL_ARG;
    private Map<String, Object> snapshotMetadata;

    @Override
    public List<Class<?>> requiredDependencies() {
        return Collections.singletonList(Database.class);
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Capture the current state of the database");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();
        Database database = (Database)commandScope.getDependency(Database.class);
        CatalogAndSchema[] schemas = InternalSnapshotCommandStep.parseSchemas(database, commandScope.getArgumentValue(SCHEMAS_ARG));
        InternalSnapshotCommandStep.logUnsupportedDatabase(database, this.getClass());
        SnapshotControl snapshotControl = this.createSnapshotControl(commandScope, database);
        if (schemas == null) {
            schemas = new CatalogAndSchema[]{database.getDefaultSchema()};
        }
        ObjectQuotingStrategy originalQuotingStrategy = database.getObjectQuotingStrategy();
        database.setObjectQuotingStrategy(ObjectQuotingStrategy.QUOTE_ALL_OBJECTS);
        try {
            DatabaseSnapshot snapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(schemas, database, snapshotControl);
            snapshot.setMetadata(this.getSnapshotMetadata());
            resultsBuilder.addResult("snapshot", snapshot);
            resultsBuilder.addResult("statusCode", (Object)0);
            OutputStream outputStream2 = resultsBuilder.getOutputStream();
            if (outputStream2 != null) {
                String result = this.printSnapshot(commandScope, snapshot);
                Writer outputWriter = this.getOutputWriter(outputStream2);
                outputWriter.write(result);
                outputWriter.flush();
            }
        }
        finally {
            database.setObjectQuotingStrategy(originalQuotingStrategy);
        }
    }

    protected SnapshotControl createSnapshotControl(CommandScope commandScope, Database database) {
        SnapshotControl snapshotControl = commandScope.getArgumentValue(SNAPSHOT_CONTROL_ARG) == null ? new SnapshotControl(database) : commandScope.getArgumentValue(SNAPSHOT_CONTROL_ARG);
        return snapshotControl;
    }

    private Writer getOutputWriter(OutputStream outputStream2) throws IOException {
        String charsetName = GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue();
        return new OutputStreamWriter(outputStream2, charsetName);
    }

    private String printSnapshot(CommandScope commandScope, DatabaseSnapshot snapshot) {
        String format = commandScope.getArgumentValue(SNAPSHOT_FORMAT_ARG);
        if (format == null) {
            format = "txt";
        }
        return SnapshotSerializerFactory.getInstance().getSerializer(format.toLowerCase(Locale.US)).serialize(snapshot, true);
    }

    public Map<String, Object> getSnapshotMetadata() {
        return this.snapshotMetadata;
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        SCHEMAS_ARG = builder.argument("schemas", String.class).description("The schemas to snapshot").build();
        SNAPSHOT_FORMAT_ARG = builder.argument("snapshotFormat", String.class).description("Output format to use (JSON, YAML, or TXT)").build();
        SNAPSHOT_CONTROL_ARG = builder.argument("snapshotControl", SnapshotControl.class).hidden().build();
    }
}

