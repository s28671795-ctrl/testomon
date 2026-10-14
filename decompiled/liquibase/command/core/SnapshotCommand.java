/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.util.ArrayList;
import java.util.Locale;
import liquibase.CatalogAndSchema;
import liquibase.command.AbstractCommand;
import liquibase.command.CommandResult;
import liquibase.command.CommandResults;
import liquibase.command.CommandScope;
import liquibase.command.CommandValidationErrors;
import liquibase.command.core.InternalSnapshotCommandStep;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;
import liquibase.serializer.SnapshotSerializerFactory;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.SnapshotListener;
import liquibase.util.StringUtil;

public class SnapshotCommand
extends AbstractCommand<SnapshotCommandResult> {
    private Database database;
    private CatalogAndSchema[] schemas;
    private String serializerFormat;
    private SnapshotListener snapshotListener;

    @Override
    public String getName() {
        return "snapshot";
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public Database getDatabase() {
        return this.database;
    }

    public SnapshotCommand setSchemas(CatalogAndSchema ... catalogAndSchema) {
        this.schemas = catalogAndSchema;
        return this;
    }

    public SnapshotCommand setSchemas(String ... schemas) {
        if (schemas == null || schemas.length == 0 || schemas[0] == null) {
            this.schemas = null;
            return this;
        }
        schemas = StringUtil.join(schemas, ",").split("\\s*,\\s*");
        ArrayList<CatalogAndSchema> finalList = new ArrayList<CatalogAndSchema>();
        for (String schema : schemas) {
            finalList.add(new CatalogAndSchema(null, schema).customize(this.database));
        }
        this.schemas = finalList.toArray(new CatalogAndSchema[0]);
        return this;
    }

    public String getSerializerFormat() {
        return this.serializerFormat;
    }

    public SnapshotCommand setSerializerFormat(String serializerFormat) {
        this.serializerFormat = serializerFormat;
        return this;
    }

    public SnapshotListener getSnapshotListener() {
        return this.snapshotListener;
    }

    public void setSnapshotListener(SnapshotListener snapshotListener) {
        this.snapshotListener = snapshotListener;
    }

    @Override
    public SnapshotCommandResult run() throws Exception {
        CommandScope commandScope = new CommandScope("internalSnapshot");
        commandScope.addArgumentValue(InternalSnapshotCommandStep.DATABASE_ARG, this.getDatabase());
        commandScope.addArgumentValue(InternalSnapshotCommandStep.SCHEMAS_ARG, this.schemas);
        commandScope.addArgumentValue(InternalSnapshotCommandStep.SERIALIZER_FORMAT_ARG, this.getSerializerFormat());
        commandScope.addArgumentValue(InternalSnapshotCommandStep.SNAPSHOT_LISTENER_ARG, this.getSnapshotListener());
        CommandResults results = commandScope.execute();
        DatabaseSnapshot snapshot = (DatabaseSnapshot)results.getResult("snapshot");
        return new SnapshotCommandResult(snapshot);
    }

    @Override
    public CommandValidationErrors validate() {
        return new CommandValidationErrors(this);
    }

    public class SnapshotCommandResult
    extends CommandResult {
        public DatabaseSnapshot snapshot;

        public SnapshotCommandResult() {
        }

        public SnapshotCommandResult(DatabaseSnapshot snapshot) {
            this.snapshot = snapshot;
        }

        @Override
        public String print() throws LiquibaseException {
            String format = SnapshotCommand.this.getSerializerFormat();
            if (format == null) {
                format = "txt";
            }
            return SnapshotSerializerFactory.getInstance().getSerializer(format.toLowerCase(Locale.US)).serialize(this.snapshot, true);
        }

        public void merge(SnapshotCommandResult resultToMerge) {
            this.snapshot.merge(resultToMerge.snapshot);
        }
    }
}

