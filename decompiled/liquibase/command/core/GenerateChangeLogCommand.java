/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import liquibase.command.CommandResult;
import liquibase.command.CommandScope;
import liquibase.command.core.DiffChangelogCommandStep;
import liquibase.command.core.DiffCommandStep;
import liquibase.command.core.DiffToChangeLogCommand;
import liquibase.command.core.GenerateChangelogCommandStep;
import liquibase.command.core.InternalSnapshotCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionArgumentsCommandStep;
import liquibase.command.core.helpers.DiffOutputControlCommandStep;
import liquibase.command.core.helpers.PreCompareCommandStep;
import liquibase.command.core.helpers.ReferenceDbUrlConnectionCommandStep;

public class GenerateChangeLogCommand
extends DiffToChangeLogCommand {
    private String author;
    private String context;

    @Override
    public String getName() {
        return "generateChangeLog";
    }

    public String getAuthor() {
        return this.author;
    }

    public GenerateChangeLogCommand setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getContext() {
        return this.context;
    }

    public GenerateChangeLogCommand setContext(String context) {
        this.context = context;
        return this;
    }

    @Override
    public CommandResult run() throws Exception {
        InternalSnapshotCommandStep.logUnsupportedDatabase(this.getReferenceDatabase(), this.getClass());
        CommandScope commandScope = new CommandScope("generateChangeLogInternal");
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
        commandScope.addArgumentValue(GenerateChangelogCommandStep.AUTHOR_ARG, this.getAuthor());
        commandScope.addArgumentValue(GenerateChangelogCommandStep.CONTEXT_ARG, this.getContext());
        commandScope.setOutput(this.getOutputStream());
        commandScope.execute();
        return new CommandResult("OK");
    }
}

