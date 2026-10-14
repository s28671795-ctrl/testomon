/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import liquibase.CatalogAndSchema;
import liquibase.Scope;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResults;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.database.Database;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.database.core.DB2Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.exception.LiquibaseException;
import liquibase.license.LicenseServiceUtils;
import liquibase.serializer.SnapshotSerializerFactory;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.SnapshotControl;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.snapshot.SnapshotListener;
import liquibase.util.StringUtil;

public class InternalSnapshotCommandStep
extends AbstractCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"internalSnapshot"};
    public static final CommandArgumentDefinition<Database> DATABASE_ARG;
    public static final CommandArgumentDefinition<CatalogAndSchema[]> SCHEMAS_ARG;
    public static final CommandArgumentDefinition<String> SERIALIZER_FORMAT_ARG;
    public static final CommandArgumentDefinition<SnapshotListener> SNAPSHOT_LISTENER_ARG;
    public static final CommandArgumentDefinition<SnapshotControl> SNAPSHOT_CONTROL_ARG;
    private Map<String, Object> snapshotMetadata;

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        super.adjustCommandDefinition(commandDefinition);
        commandDefinition.setInternal(true);
    }

    public static CatalogAndSchema[] parseSchemas(Database database, String ... schemas) {
        if (schemas == null || schemas.length == 0 || schemas[0] == null) {
            return null;
        }
        schemas = StringUtil.join(schemas, ",").split("\\s*,\\s*");
        ArrayList<CatalogAndSchema> finalList = new ArrayList<CatalogAndSchema>();
        for (String schema : schemas) {
            finalList.add(new CatalogAndSchema(null, schema).customize(database));
        }
        return finalList.toArray(new CatalogAndSchema[0]);
    }

    public Map<String, Object> getSnapshotMetadata() {
        return this.snapshotMetadata;
    }

    public void setSnapshotMetadata(Map<String, Object> snapshotMetadata) {
        this.snapshotMetadata = snapshotMetadata;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        DatabaseSnapshot snapshot;
        CommandScope commandScope = resultsBuilder.getCommandScope();
        Database database = commandScope.getArgumentValue(DATABASE_ARG);
        SnapshotListener snapshotListener = commandScope.getArgumentValue(SNAPSHOT_LISTENER_ARG);
        CatalogAndSchema[] schemas = commandScope.getArgumentValue(SCHEMAS_ARG);
        InternalSnapshotCommandStep.logUnsupportedDatabase(database, this.getClass());
        SnapshotControl snapshotControl = commandScope.getArgumentValue(SNAPSHOT_CONTROL_ARG);
        if (snapshotControl == null) {
            snapshotControl = new SnapshotControl(database);
        }
        snapshotControl.setSnapshotListener(snapshotListener);
        if (schemas == null) {
            schemas = new CatalogAndSchema[]{database.getDefaultSchema()};
        }
        ObjectQuotingStrategy originalQuotingStrategy = database.getObjectQuotingStrategy();
        database.setObjectQuotingStrategy(ObjectQuotingStrategy.QUOTE_ALL_OBJECTS);
        try {
            snapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(schemas, database, snapshotControl);
        }
        finally {
            database.setObjectQuotingStrategy(originalQuotingStrategy);
        }
        snapshot.setMetadata(this.getSnapshotMetadata());
        resultsBuilder.addResult("snapshot", snapshot);
    }

    public static String printSnapshot(CommandScope commandScope, CommandResults snapshotResults) throws LiquibaseException {
        String format = commandScope.getArgumentValue(SERIALIZER_FORMAT_ARG);
        if (format == null) {
            format = "txt";
        }
        return SnapshotSerializerFactory.getInstance().getSerializer(format.toLowerCase(Locale.US)).serialize((DatabaseSnapshot)snapshotResults.getResult("snapshot"), true);
    }

    public static void logUnsupportedDatabase(Database database, Class callingClass) {
        if (!(!LicenseServiceUtils.isProLicenseValid() || database == null || database instanceof MSSQLDatabase || database instanceof OracleDatabase || database instanceof MySQLDatabase || database instanceof DB2Database || database instanceof PostgresDatabase || database.getShortName().equalsIgnoreCase("snowflake"))) {
            Scope.getCurrentScope().getUI().sendMessage("INFO This command might not yet capture Liquibase Pro additional object types on " + database.getShortName());
        }
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        DATABASE_ARG = builder.argument("database", Database.class).required().build();
        SCHEMAS_ARG = builder.argument("schemas", CatalogAndSchema[].class).build();
        SERIALIZER_FORMAT_ARG = builder.argument("serializerFormat", String.class).build();
        SNAPSHOT_LISTENER_ARG = builder.argument("snapshotListener", SnapshotListener.class).build();
        SNAPSHOT_CONTROL_ARG = builder.argument("snapshotControl", SnapshotControl.class).hidden().build();
    }
}

