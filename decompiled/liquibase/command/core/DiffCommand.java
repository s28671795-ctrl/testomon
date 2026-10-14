/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.io.PrintStream;
import java.util.Set;
import liquibase.command.AbstractCommand;
import liquibase.command.CommandResult;
import liquibase.command.CommandScope;
import liquibase.command.CommandValidationErrors;
import liquibase.command.core.DiffCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionArgumentsCommandStep;
import liquibase.command.core.helpers.PreCompareCommandStep;
import liquibase.command.core.helpers.ReferenceDbUrlConnectionCommandStep;
import liquibase.database.Database;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.output.ObjectChangeFilter;
import liquibase.snapshot.SnapshotControl;
import liquibase.snapshot.SnapshotListener;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.DatabaseObjectFactory;
import liquibase.util.StringUtil;

public class DiffCommand
extends AbstractCommand<CommandResult> {
    private Database referenceDatabase;
    private Database targetDatabase;
    private Class<? extends DatabaseObject>[] snapshotTypes;
    private PrintStream outputStream;
    private SnapshotListener snapshotListener;
    private SnapshotControl referenceSnapshotControl;
    private SnapshotControl targetSnapshotControl;
    private ObjectChangeFilter objectChangeFilter;
    private CompareControl compareControl;

    @Override
    public String getName() {
        return "diff";
    }

    @Override
    public CommandValidationErrors validate() {
        return new CommandValidationErrors(this);
    }

    public Database getReferenceDatabase() {
        return this.referenceDatabase;
    }

    public DiffCommand setReferenceDatabase(Database referenceDatabase) {
        this.referenceDatabase = referenceDatabase;
        return this;
    }

    public Database getTargetDatabase() {
        return this.targetDatabase;
    }

    public DiffCommand setTargetDatabase(Database targetDatabase) {
        this.targetDatabase = targetDatabase;
        return this;
    }

    public Class<? extends DatabaseObject>[] getSnapshotTypes() {
        return this.snapshotTypes;
    }

    public DiffCommand setSnapshotTypes(String ... snapshotTypes) {
        if (snapshotTypes == null || snapshotTypes.length == 0 || snapshotTypes[0] == null) {
            this.snapshotTypes = null;
            return this;
        }
        Set<Class<? extends DatabaseObject>> types = DatabaseObjectFactory.getInstance().parseTypes(StringUtil.join(snapshotTypes, ","));
        this.snapshotTypes = new Class[types.size()];
        int i = 0;
        for (Class<? extends DatabaseObject> type : types) {
            this.snapshotTypes[i++] = type;
        }
        return this;
    }

    public DiffCommand setSnapshotTypes(Class<? extends DatabaseObject> ... snapshotTypes) {
        this.snapshotTypes = snapshotTypes;
        return this;
    }

    public PrintStream getOutputStream() {
        return this.outputStream;
    }

    public DiffCommand setOutputStream(PrintStream outputStream2) {
        this.outputStream = outputStream2;
        return this;
    }

    public SnapshotControl getReferenceSnapshotControl() {
        return this.referenceSnapshotControl;
    }

    public DiffCommand setReferenceSnapshotControl(SnapshotControl referenceSnapshotControl) {
        this.referenceSnapshotControl = referenceSnapshotControl;
        return this;
    }

    public SnapshotControl getTargetSnapshotControl() {
        return this.targetSnapshotControl;
    }

    public DiffCommand setTargetSnapshotControl(SnapshotControl targetSnapshotControl) {
        this.targetSnapshotControl = targetSnapshotControl;
        return this;
    }

    public SnapshotListener getSnapshotListener() {
        return this.snapshotListener;
    }

    public DiffCommand setSnapshotListener(SnapshotListener snapshotListener) {
        this.snapshotListener = snapshotListener;
        return this;
    }

    public CompareControl getCompareControl() {
        return this.compareControl;
    }

    public DiffCommand setCompareControl(CompareControl compareControl) {
        this.compareControl = compareControl;
        return this;
    }

    public ObjectChangeFilter getObjectChangeFilter() {
        return this.objectChangeFilter;
    }

    public DiffCommand setObjectChangeFilter(ObjectChangeFilter objectChangeFilter) {
        this.objectChangeFilter = objectChangeFilter;
        return this;
    }

    @Override
    public CommandResult run() throws Exception {
        CommandScope commandScope = new CommandScope("diff");
        commandScope.addArgumentValue(ReferenceDbUrlConnectionCommandStep.REFERENCE_DATABASE_ARG, this.referenceDatabase);
        commandScope.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.targetDatabase);
        commandScope.addArgumentValue(DiffCommandStep.SNAPSHOT_LISTENER_ARG, this.snapshotListener);
        commandScope.addArgumentValue(DiffCommandStep.REFERENCE_SNAPSHOT_CONTROL_ARG, this.referenceSnapshotControl);
        commandScope.addArgumentValue(DiffCommandStep.TARGET_SNAPSHOT_CONTROL_ARG, this.targetSnapshotControl);
        commandScope.addArgumentValue(PreCompareCommandStep.SNAPSHOT_TYPES_ARG, this.snapshotTypes);
        commandScope.addArgumentValue(PreCompareCommandStep.OBJECT_CHANGE_FILTER_ARG, this.objectChangeFilter);
        commandScope.addArgumentValue(PreCompareCommandStep.COMPARE_CONTROL_ARG, this.compareControl);
        commandScope.setOutput(this.outputStream);
        commandScope.execute();
        return new CommandResult("OK");
    }
}

