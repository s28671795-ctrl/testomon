/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.io.PrintStream;
import liquibase.command.CommandResult;
import liquibase.command.CommandScope;
import liquibase.command.core.DiffChangelogCommandStep;
import liquibase.command.core.DiffCommand;
import liquibase.command.core.DiffCommandStep;
import liquibase.command.core.InternalSnapshotCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionArgumentsCommandStep;
import liquibase.command.core.helpers.DiffOutputControlCommandStep;
import liquibase.command.core.helpers.PreCompareCommandStep;
import liquibase.command.core.helpers.ReferenceDbUrlConnectionCommandStep;
import liquibase.diff.output.DiffOutputControl;

public class DiffToChangeLogCommand
extends DiffCommand {
    private String changeLogFile;
    private PrintStream outputStream;
    private DiffOutputControl diffOutputControl;

    @Override
    public String getName() {
        return "diffChangeLog";
    }

    public String getChangeLogFile() {
        return this.changeLogFile;
    }

    public DiffToChangeLogCommand setChangeLogFile(String changeLogFile) {
        this.changeLogFile = changeLogFile;
        return this;
    }

    @Override
    public PrintStream getOutputStream() {
        return this.outputStream;
    }

    @Override
    public DiffToChangeLogCommand setOutputStream(PrintStream outputStream2) {
        this.outputStream = outputStream2;
        return this;
    }

    public DiffOutputControl getDiffOutputControl() {
        return this.diffOutputControl;
    }

    public DiffToChangeLogCommand setDiffOutputControl(DiffOutputControl diffOutputControl) {
        this.diffOutputControl = diffOutputControl;
        return this;
    }

    @Override
    public CommandResult run() throws Exception {
        InternalSnapshotCommandStep.logUnsupportedDatabase(this.getReferenceDatabase(), this.getClass());
        CommandScope commandScope = new CommandScope("diffChangelog");
        commandScope.addArgumentValue(ReferenceDbUrlConnectionCommandStep.REFERENCE_DATABASE_ARG, this.getReferenceDatabase());
        commandScope.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, this.getTargetDatabase());
        commandScope.addArgumentValue(PreCompareCommandStep.SNAPSHOT_TYPES_ARG, this.getSnapshotTypes());
        commandScope.addArgumentValue(DiffCommandStep.SNAPSHOT_LISTENER_ARG, this.getSnapshotListener());
        commandScope.addArgumentValue(DiffCommandStep.REFERENCE_SNAPSHOT_CONTROL_ARG, this.getReferenceSnapshotControl());
        commandScope.addArgumentValue(DiffCommandStep.TARGET_SNAPSHOT_CONTROL_ARG, this.getTargetSnapshotControl());
        commandScope.addArgumentValue(PreCompareCommandStep.OBJECT_CHANGE_FILTER_ARG, this.getObjectChangeFilter());
        commandScope.addArgumentValue(PreCompareCommandStep.COMPARE_CONTROL_ARG, this.getCompareControl());
        commandScope.addArgumentValue(DiffChangelogCommandStep.CHANGELOG_FILE_ARG, this.getChangeLogFile());
        commandScope.addArgumentValue(DiffOutputControlCommandStep.INCLUDE_SCHEMA_ARG, this.getDiffOutputControl().getIncludeSchema());
        commandScope.addArgumentValue(DiffOutputControlCommandStep.INCLUDE_CATALOG_ARG, this.getDiffOutputControl().getIncludeCatalog());
        commandScope.addArgumentValue(DiffOutputControlCommandStep.INCLUDE_TABLESPACE_ARG, this.getDiffOutputControl().getIncludeTablespace());
        commandScope.setOutput(this.getOutputStream());
        commandScope.execute();
        return new CommandResult("OK");
    }
}

