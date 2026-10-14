/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.util.Collections;
import java.util.List;
import liquibase.ChecksumVersion;
import liquibase.Scope;
import liquibase.change.CheckSum;
import liquibase.changelog.ChangeLogHistoryService;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.command.CommonArgumentNames;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.resource.ResourceAccessor;
import liquibase.util.StringUtil;

public class CalculateChecksumCommandStep
extends AbstractCommandStep {
    protected static final String[] COMMAND_NAME = new String[]{"calculateChecksum"};
    public static final CommandArgumentDefinition<String> CHANGELOG_FILE_ARG;
    public static final CommandArgumentDefinition<String> CHANGESET_PATH_ARG;
    public static final CommandArgumentDefinition<String> CHANGESET_ID_ARG;
    public static final CommandArgumentDefinition<String> CHANGESET_AUTHOR_ARG;
    public static final CommandResultDefinition<CheckSum> CHECKSUM_RESULT;
    public static final CommandArgumentDefinition<String> CHANGESET_IDENTIFIER_ARG;
    private static final int CHANGESET_IDENTIFIER_PARTS_LENGTH = 3;
    private static final int CHANGESET_IDENTIFIER_AUTHOR_PART = 2;
    private static final int CHANGESET_IDENTIFIER_ID_PART = 1;
    private static final int CHANGESET_IDENTIFIER_PATH_PART = 0;

    @Override
    public List<Class<?>> requiredDependencies() {
        return Collections.singletonList(Database.class);
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        String changeSetAuthor;
        String changeSetId;
        String changeSetPath;
        CommandScope commandScope = resultsBuilder.getCommandScope();
        String changeSetIdentifier = commandScope.getArgumentValue(CHANGESET_IDENTIFIER_ARG);
        String changeLogFile = commandScope.getArgumentValue(CHANGELOG_FILE_ARG).replace('\\', '/');
        boolean isChangeSetIdentifierPassed = changeSetIdentifier != null;
        this.validateIdentifierParameters(commandScope, changeSetIdentifier);
        if (isChangeSetIdentifierPassed) {
            List<String> parts = this.validateAndExtractParts(changeSetIdentifier, changeLogFile);
            changeSetPath = parts.get(0);
            changeSetId = parts.get(1);
            changeSetAuthor = parts.get(2);
        } else {
            changeSetPath = commandScope.getArgumentValue(CHANGESET_PATH_ARG);
            changeSetId = commandScope.getArgumentValue(CHANGESET_ID_ARG);
            changeSetAuthor = commandScope.getArgumentValue(CHANGESET_AUTHOR_ARG);
        }
        Database database = (Database)commandScope.getDependency(Database.class);
        Scope.getCurrentScope().getLog(this.getClass()).info(String.format("Calculating checksum for changeSet identified by changeSet id: %s, author: %s, path: %s", changeSetId, changeSetAuthor, changeSetPath));
        ResourceAccessor resourceAccessor = Scope.getCurrentScope().getResourceAccessor();
        DatabaseChangeLog changeLog = ChangeLogParserFactory.getInstance().getParser(changeLogFile, resourceAccessor).parse(changeLogFile, new ChangeLogParameters(database), resourceAccessor);
        ChangeSet changeSet = changeLog.getChangeSet(changeSetPath, changeSetAuthor, changeSetId);
        if (changeSet == null) {
            throw new LiquibaseException(new IllegalArgumentException(String.format("No such changeSet identified by changeSet id: %s, author: %s, path: %s", changeSetId, changeSetAuthor, changeSetPath)));
        }
        ChangeLogHistoryService changeLogService = ChangeLogHistoryServiceFactory.getInstance().getChangeLogService(database);
        RanChangeSet ranChangeSet = changeLogService.getRanChangeSet(changeSet);
        CalculateChecksumCommandStep.sendMessages(resultsBuilder, changeSet.generateCheckSum(ranChangeSet != null && ranChangeSet.getLastCheckSum() != null ? ChecksumVersion.enumFromChecksumVersion(ranChangeSet.getLastCheckSum().getVersion()) : ChecksumVersion.latest()));
    }

    private void validateIdentifierParameters(CommandScope commandScope, String changeSetIdentifier) throws LiquibaseException {
        boolean isRequiredCompositeIdentifierMissing;
        boolean isAmbiguousNumberOfIdentifierProvided;
        boolean bl = isAmbiguousNumberOfIdentifierProvided = (commandScope.getArgumentValue(CHANGESET_ID_ARG) != null || commandScope.getArgumentValue(CHANGESET_AUTHOR_ARG) != null || commandScope.getArgumentValue(CHANGESET_PATH_ARG) != null) && changeSetIdentifier != null;
        if (isAmbiguousNumberOfIdentifierProvided) {
            String errorMessage = "Error encountered while parsing the command line. '--changeset-identifier' cannot be provided alongside other changeset arguments: '--changeset-id', '--changeset-path', '--changeset-author'.";
            throw new LiquibaseException(new IllegalArgumentException(errorMessage));
        }
        boolean bl2 = isRequiredCompositeIdentifierMissing = (commandScope.getArgumentValue(CHANGESET_ID_ARG) == null || commandScope.getArgumentValue(CHANGESET_AUTHOR_ARG) == null || commandScope.getArgumentValue(CHANGESET_PATH_ARG) == null) && changeSetIdentifier == null;
        if (isRequiredCompositeIdentifierMissing) {
            String errorMessage = "Error encountered while parsing the command line. If --changeset-identifier is not provided than --changeset-id, --changeset-author and --changeset-path must be specified. Missing argument: ";
            if (StringUtil.isEmpty(commandScope.getArgumentValue(CHANGESET_ID_ARG))) {
                errorMessage = errorMessage + " '--changeset-id',";
            }
            if (StringUtil.isEmpty(commandScope.getArgumentValue(CHANGESET_AUTHOR_ARG))) {
                errorMessage = errorMessage + " '--changeset-author',";
            }
            if (StringUtil.isEmpty(commandScope.getArgumentValue(CHANGESET_PATH_ARG))) {
                errorMessage = errorMessage + " '--changeset-path',";
            }
            errorMessage = errorMessage.substring(0, errorMessage.length() - 1) + ".";
            throw new LiquibaseException(new IllegalArgumentException(errorMessage));
        }
    }

    private List<String> validateAndExtractParts(String changeSetIdentifier, String changeLogFile) throws LiquibaseException {
        if (StringUtil.isEmpty(changeSetIdentifier)) {
            throw new LiquibaseException(new IllegalArgumentException(CHANGESET_IDENTIFIER_ARG.getName()));
        }
        if (StringUtil.isEmpty(changeLogFile)) {
            throw new LiquibaseException(new IllegalArgumentException(CHANGELOG_FILE_ARG.getName()));
        }
        List<String> parts = StringUtil.splitAndTrim(changeSetIdentifier, "::");
        if (parts == null || parts.size() < 3) {
            throw new LiquibaseException(new IllegalArgumentException("Invalid changeSet identifier: " + changeSetIdentifier));
        }
        return parts;
    }

    private static void sendMessages(CommandResultsBuilder resultsBuilder, CheckSum checkSum) {
        resultsBuilder.addResult(CHECKSUM_RESULT, checkSum);
        Scope.getCurrentScope().getUI().sendMessage(checkSum.toString());
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Calculates and prints a checksum for the changeset");
        commandDefinition.setLongDescription("Calculates and prints a checksum for the changeset with the given id in the format filepath::id::author");
        commandDefinition.setHelpFooter("\nCalculate checksum provides two ways to identify a changeSet.\n\n1. Composite changeSet identifier\n\nThe composite changeSet identifier must be passed in the following pattern myPath::myId::myAuthor.\n\nliquibase calculateCheckSum --changesetIdentifier myFile::myId::myAuthor\n\n2. Individual changeSet parameters\n\nThe second option requires all three parameters to be defined.\nThis variant offers some more flexibility in naming conventions for path, id and author.\n\nliquibase calculateCheckSum --changesetId myId --changesetAuthor myAuthor --changesetPath myPath\n");
    }

    static {
        CommandBuilder builder = new CommandBuilder(new String[][]{COMMAND_NAME});
        CHANGELOG_FILE_ARG = builder.argument(CommonArgumentNames.CHANGELOG_FILE, String.class).required().description("The root changelog file").build();
        CHANGESET_IDENTIFIER_ARG = builder.argument("changesetIdentifier", String.class).description("ChangeSet identifier of form filepath::id::author").build();
        CHANGESET_PATH_ARG = builder.argument("changesetPath", String.class).description("Changelog path in which the changeSet is included").build();
        CHANGESET_ID_ARG = builder.argument("changesetId", String.class).description("ChangeSet ID attribute").build();
        CHANGESET_AUTHOR_ARG = builder.argument("changesetAuthor", String.class).description("ChangeSet Author attribute").build();
        CHECKSUM_RESULT = builder.result("checksumResult", CheckSum.class).description("Calculated checksum").build();
    }
}

