/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.Labels;
import liquibase.Scope;
import liquibase.change.AbstractSQLChange;
import liquibase.change.Change;
import liquibase.change.core.EmptyChange;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changeset.ChangeSetService;
import liquibase.changeset.ChangeSetServiceFactory;
import liquibase.exception.ChangeLogParseException;
import liquibase.parser.ChangeLogParser;
import liquibase.resource.ResourceAccessor;
import liquibase.util.StreamUtil;
import liquibase.util.StringUtil;
import org.apache.commons.lang3.NotImplementedException;

public abstract class AbstractFormattedChangeLogParser
implements ChangeLogParser {
    private static final ResourceBundle coreBundle = ResourceBundle.getBundle("liquibase/i18n/liquibase-core");
    protected static final String EXCEPTION_MESSAGE = coreBundle.getString("formatted.changelog.exception.message");
    protected final String FIRST_LINE_REGEX = String.format("^\\s*%s\\s*liquibase\\s*formatted.*", this.getSingleLineCommentSequence());
    protected final Pattern FIRST_LINE_PATTERN = Pattern.compile(this.FIRST_LINE_REGEX, 2);
    protected final String PROPERTY_REGEX = String.format("\\s*%s[\\s]*property\\s+(.*:.*)\\s+(.*:.*).*", this.getSingleLineCommentSequence());
    protected final Pattern PROPERTY_PATTERN = Pattern.compile(this.PROPERTY_REGEX, 2);
    protected final String ALT_PROPERTY_ONE_CHARACTER_REGEX = String.format("\\s*?[%s]+\\s*property\\s.*", this.getSingleLineCommentOneCharacter());
    protected final Pattern ALT_PROPERTY_ONE_CHARACTER_PATTERN = Pattern.compile(this.ALT_PROPERTY_ONE_CHARACTER_REGEX, 2);
    protected final String CHANGE_SET_REGEX = String.format("\\s*%s[\\s]*changeset\\s+(\"[^\"]+\"|[^:]+):\\s*(\"[^\"]+\"|\\S+).*", this.getSingleLineCommentSequence());
    protected final Pattern CHANGE_SET_PATTERN = Pattern.compile(this.CHANGE_SET_REGEX, 2);
    protected final String ALT_CHANGE_SET_ONE_CHARACTER_REGEX = String.format("%s[\\s]*changeset\\s.*", this.getSingleLineCommentOneCharacter());
    protected final Pattern ALT_CHANGE_SET_ONE_CHARACTER_PATTERN = Pattern.compile(this.ALT_CHANGE_SET_ONE_CHARACTER_REGEX, 2);
    protected final String ALT_CHANGE_SET_NO_OTHER_INFO_REGEX = String.format("\\s*%s[\\s]*changeset[\\s]*.*$", this.getSingleLineCommentSequence());
    protected final Pattern ALT_CHANGE_SET_NO_OTHER_INFO_PATTERN = Pattern.compile(this.ALT_CHANGE_SET_NO_OTHER_INFO_REGEX, 2);
    protected final String ROLLBACK_REGEX = String.format("\\s*%s[\\s]*rollback (.*)", this.getSingleLineCommentSequence());
    protected final Pattern ROLLBACK_PATTERN = Pattern.compile(this.ROLLBACK_REGEX, 2);
    protected final String ALT_ROLLBACK_ONE_CHARACTER_REGEX = String.format("\\s*%s[\\s]*rollback\\s.*", this.getSingleLineCommentOneCharacter());
    protected final Pattern ALT_ROLLBACK_ONE_CHARACTER_PATTERN = Pattern.compile(this.ALT_ROLLBACK_ONE_CHARACTER_REGEX, 2);
    protected final String PRECONDITIONS_REGEX = String.format("\\s*%s[\\s]*preconditions(.*)", this.getSingleLineCommentSequence());
    protected final Pattern PRECONDITIONS_PATTERN = Pattern.compile(this.PRECONDITIONS_REGEX, 2);
    protected final String ALT_PRECONDITIONS_ONE_CHARACTER_REGEX = String.format("\\s*%s[\\s]*preconditions\\s.*", this.getSingleLineCommentOneCharacter());
    protected final Pattern ALT_PRECONDITIONS_ONE_CHARACTER_PATTERN = Pattern.compile(this.ALT_PRECONDITIONS_ONE_CHARACTER_REGEX, 2);
    protected final String PRECONDITION_REGEX = String.format("\\s*%s[\\s]*precondition\\-([a-zA-Z0-9-]+) (.*)", this.getSingleLineCommentSequence());
    protected final Pattern PRECONDITION_PATTERN = Pattern.compile(this.PRECONDITION_REGEX, 2);
    protected final String INVALID_EMPTY_PRECONDITION_REGEX = String.format("\\s*%s[\\s]*precondition\\-([a-zA-Z0-9-]+)", this.getSingleLineCommentSequence());
    protected final Pattern INVALID_EMPTY_PRECONDITION_PATTERN = Pattern.compile(this.INVALID_EMPTY_PRECONDITION_REGEX, 2);
    protected final String ALT_PRECONDITION_ONE_CHARACTER_REGEX = String.format("\\s*%s[\\s]*precondition(.*)", this.getSingleLineCommentOneCharacter());
    protected final Pattern ALT_PRECONDITION_ONE_CHARACTER_PATTERN = Pattern.compile(this.ALT_PRECONDITION_ONE_CHARACTER_REGEX, 2);
    protected static final String STRIP_COMMENTS_REGEX = ".*stripComments:(\\w+).*";
    protected final Pattern STRIP_COMMENTS_PATTERN = Pattern.compile(".*stripComments:(\\w+).*", 2);
    protected static final String SPLIT_STATEMENTS_REGEX = ".*splitStatements:(\\w+).*";
    protected final Pattern SPLIT_STATEMENTS_PATTERN = Pattern.compile(".*splitStatements:(\\w+).*", 2);
    protected static final String ROLLBACK_SPLIT_STATEMENTS_REGEX = ".*rollbackSplitStatements:(\\w+).*";
    protected final Pattern ROLLBACK_SPLIT_STATEMENTS_PATTERN = Pattern.compile(".*rollbackSplitStatements:(\\w+).*", 2);
    protected static final String END_DELIMITER_REGEX = ".*endDelimiter:(\\S*).*";
    protected final Pattern END_DELIMITER_PATTERN = Pattern.compile(".*endDelimiter:(\\S*).*", 2);
    protected static final String ROLLBACK_END_DELIMITER_REGEX = ".*rollbackEndDelimiter:(\\S*).*";
    protected final Pattern ROLLBACK_END_DELIMITER_PATTERN = Pattern.compile(".*rollbackEndDelimiter:(\\S*).*", 2);
    protected final String COMMENT_REGEX = String.format("%s[\\s]*comment:? (.*)", this.getSingleLineCommentSequence());
    protected final Pattern COMMENT_PATTERN = Pattern.compile(this.COMMENT_REGEX, 2);
    protected final String ALT_COMMENT_PLURAL_REGEX = String.format("%s[\\s]*comments:? (.*)", this.getSingleLineCommentSequence());
    protected final Pattern ALT_COMMENT_PLURAL_PATTERN = Pattern.compile(this.ALT_COMMENT_PLURAL_REGEX, 2);
    protected final String ALT_COMMENT_ONE_CHARACTER_REGEX = String.format("%s[\\s]*comment:? (.*)", this.getSingleLineCommentOneCharacter());
    protected final Pattern ALT_COMMENT_ONE_CHARACTER_PATTERN = Pattern.compile(this.ALT_COMMENT_ONE_CHARACTER_REGEX, 2);
    protected final String VALID_CHECK_SUM_REGEX = String.format("%s[\\s]*validCheckSum:? (.*)", this.getSingleLineCommentSequence());
    protected final Pattern VALID_CHECK_SUM_PATTERN = Pattern.compile(this.VALID_CHECK_SUM_REGEX, 2);
    protected final String ALT_VALID_CHECK_SUM_ONE_CHARACTER_REGEX = String.format("^%s[\\s]*validCheckSum(.*)$", this.getSingleLineCommentOneCharacter());
    protected final Pattern ALT_VALID_CHECK_SUM_ONE_CHARACTER_PATTERN = Pattern.compile(this.ALT_VALID_CHECK_SUM_ONE_CHARACTER_REGEX, 2);
    protected final String IGNORE_LINES_REGEX = String.format("%s[\\s]*ignoreLines:(\\w+)", this.getSingleLineCommentSequence());
    protected final Pattern IGNORE_LINES_PATTERN = Pattern.compile(this.IGNORE_LINES_REGEX, 2);
    protected final String ALT_IGNORE_LINES_ONE_CHARACTER_REGEX = String.format("%s[\\s]*?ignoreLines:(\\w+).*$", this.getSingleLineCommentOneCharacter());
    protected final Pattern ALT_IGNORE_LINES_ONE_CHARACTER_PATTERN = Pattern.compile(this.ALT_IGNORE_LINES_ONE_CHARACTER_REGEX, 2);
    protected final String ALT_IGNORE_REGEX = String.format("%s[\\s]*ignore:(\\w+)", this.getSingleLineCommentSequence());
    protected final Pattern ALT_IGNORE_PATTERN = Pattern.compile(this.ALT_IGNORE_REGEX, 2);
    protected static final String RUN_WITH_REGEX = ".*runWith:([\\w\\$\\{\\}]+).*";
    protected final Pattern RUN_WITH_PATTERN = Pattern.compile(".*runWith:([\\w\\$\\{\\}]+).*", 2);
    protected static final String RUN_WITH_SPOOL_FILE_REGEX = ".*runWithSpoolFile:(.*).*";
    protected final Pattern RUN_WITH_SPOOL_FILE_PATTERN = Pattern.compile(".*runWithSpoolFile:(.*).*", 2);
    protected static final String RUN_ON_CHANGE_REGEX = ".*runOnChange:(\\w+).*";
    protected final Pattern RUN_ON_CHANGE_PATTERN = Pattern.compile(".*runOnChange:(\\w+).*", 2);
    protected static final String RUN_ALWAYS_REGEX = ".*runAlways:(\\w+).*";
    protected final Pattern RUN_ALWAYS_PATTERN = Pattern.compile(".*runAlways:(\\w+).*", 2);
    protected static final String CONTEXT_REGEX = ".*context:(\".*?\"|\\S*).*";
    protected final Pattern CONTEXT_PATTERN = Pattern.compile(".*context:(\".*?\"|\\S*).*", 2);
    protected static final String CONTEXT_FILTER_REGEX = ".*contextFilter:(\".*?\"|\\S*).*";
    protected final Pattern CONTEXT_FILTER_PATTERN = Pattern.compile(".*contextFilter:(\".*?\"|\\S*).*", 2);
    protected static final String LOGICAL_FILE_PATH_REGEX = ".*logicalFilePath:(\\S*).*";
    protected final Pattern LOGICAL_FILE_PATH_PATTERN = Pattern.compile(".*logicalFilePath:(\\S*).*", 2);
    protected static final String LABELS_REGEX = ".*labels:(\".*?\"|\\S*).*";
    protected final Pattern LABELS_PATTERN = Pattern.compile(".*labels:(\".*?\"|\\S*).*", 2);
    protected static final String RUN_IN_TRANSACTION_REGEX = ".*runInTransaction:(\\w+).*";
    protected final Pattern RUN_IN_TRANSACTION_PATTERN = Pattern.compile(".*runInTransaction:(\\w+).*", 2);
    protected static final String DBMS_REGEX = ".*dbms:([^,][\\w!,]+).*";
    protected final Pattern DBMS_PATTERN = Pattern.compile(".*dbms:([^,][\\w!,]+).*", 2);
    protected static final String IGNORE_REGEX = ".*ignore:(\\w*).*";
    protected final Pattern IGNORE_PATTERN = Pattern.compile(".*ignore:(\\w*).*", 2);
    protected static final String FAIL_ON_ERROR_REGEX = ".*failOnError:(\\w+).*";
    protected final Pattern FAIL_ON_ERROR_PATTERN = Pattern.compile(".*failOnError:(\\w+).*", 2);
    protected static final String ON_FAIL_REGEX = ".*onFail:(\\w+).*";
    protected final Pattern ON_FAIL_PATTERN = Pattern.compile(".*onFail:(\\w+).*", 2);
    protected static final String ON_ERROR_REGEX = ".*onError:(\\w+).*";
    protected final Pattern ON_ERROR_PATTERN = Pattern.compile(".*onError:(\\w+).*", 2);
    protected static final String ROLLBACK_CHANGE_SET_ID_REGEX = ".*changeSetId:(\\S+).*";
    protected final Pattern ROLLBACK_CHANGE_SET_ID_PATTERN = Pattern.compile(".*changeSetId:(\\S+).*", 2);
    protected static final String ROLLBACK_CHANGE_SET_AUTHOR_REGEX = ".*changesetAuthor:(\\S+).*";
    protected final Pattern ROLLBACK_CHANGE_SET_AUTHOR_PATTERN = Pattern.compile(".*changesetAuthor:(\\S+).*", 2);
    protected static final String ROLLBACK_CHANGE_SET_PATH_REGEX = ".*changesetPath:(\\S+).*";
    protected final Pattern ROLLBACK_CHANGE_SET_PATH_PATTERN = Pattern.compile(".*changesetPath:(\\S+).*", 2);
    protected final String ROLLBACK_MULTI_LINE_START_REGEX = String.format("\\s*%s\\s*liquibase\\s*rollback\\s*$", this.getStartMultiLineCommentSequence());
    protected final Pattern ROLLBACK_MULTI_LINE_START_PATTERN = Pattern.compile(this.ROLLBACK_MULTI_LINE_START_REGEX, 2);
    protected final String ROLLBACK_MULTI_LINE_END_REGEX = String.format(".*\\s*%s\\s*$", this.getEndMultiLineCommentSequence());
    protected final Pattern ROLLBACK_MULTI_LINE_END_PATTERN = Pattern.compile(this.ROLLBACK_MULTI_LINE_END_REGEX, 2);
    protected static final String WORD_RESULT_REGEX = "^(?:expectedResult:)?(\\w+) (.*)";
    protected static final String SINGLE_QUOTE_RESULT_REGEX = "^(?:expectedResult:)?'([^']+)' (.*)";
    protected static final String DOUBLE_QUOTE_RESULT_REGEX = "^(?:expectedResult:)?\"([^\"]+)\" (.*)";
    protected final Pattern[] WORD_AND_QUOTING_PATTERNS = new Pattern[]{Pattern.compile("^(?:expectedResult:)?(\\w+) (.*)", 2), Pattern.compile("^(?:expectedResult:)?'([^']+)' (.*)", 2), Pattern.compile("^(?:expectedResult:)?\"([^\"]+)\" (.*)", 2)};
    protected static final String NAME_REGEX = ".*name:\\s*(\\S++).*";
    protected final Pattern NAME_PATTERN = Pattern.compile(".*name:\\s*(\\S++).*", 2);
    protected static final String VALUE_REGEX = ".*value:\\s*(\\S+).*";
    protected final Pattern VALUE_PATTERN = Pattern.compile(".*value:\\s*(\\S+).*", 2);
    protected static final String GLOBAL_REGEX = ".*global:(\\S+).*";
    protected final Pattern GLOBAL_PATTERN = Pattern.compile(".*global:(\\S+).*", 2);
    protected static final String VIEW_NAME_STATEMENT_REGEX = ".*view:(\\w+).*";
    protected final Pattern VIEW_NAME_STATEMENT_PATTERN = Pattern.compile(".*view:(\\w+).*", 2);
    protected static final String TABLE_NAME_STATEMENT_REGEX = ".*table:(\\w+).*";
    protected final Pattern TABLE_NAME_STATEMENT_PATTERN = Pattern.compile(".*table:(\\w+).*", 2);
    protected static final String SCHEMA_NAME_STATEMENT_REGEX = ".*schema:(\\w+).*";
    protected final Pattern SCHEMA_NAME_STATEMENT_PATTERN = Pattern.compile(".*schema:(\\w+).*", 2);

    protected abstract String getSingleLineCommentOneCharacter();

    protected abstract String getSingleLineCommentSequence();

    protected abstract String getStartMultiLineCommentSequence();

    protected abstract String getEndMultiLineCommentSequence();

    protected abstract boolean supportsExtension(String var1);

    protected abstract void handlePreconditionCase(ChangeLogParameters var1, ChangeSet var2, Matcher var3) throws ChangeLogParseException;

    protected abstract void handlePreconditionsCase(ChangeSet var1, int var2, Matcher var3) throws ChangeLogParseException;

    protected abstract AbstractSQLChange getChange();

    protected abstract String getDocumentationLink();

    protected abstract String getSequenceName();

    protected abstract void setChangeSequence(AbstractSQLChange var1, String var2);

    protected abstract boolean isNotEndDelimiter(AbstractSQLChange var1);

    protected abstract void setChangeSequence(ChangeLogParameters var1, StringBuilder var2, ChangeSet var3, AbstractSQLChange var4);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean supports(String changeLogFile, ResourceAccessor resourceAccessor) {
        BufferedReader reader = null;
        try {
            if (this.supportsExtension(changeLogFile)) {
                InputStream fileStream = this.openChangeLogFile(changeLogFile, resourceAccessor);
                if (fileStream == null) {
                    boolean bl = false;
                    return bl;
                }
                reader = new BufferedReader(StreamUtil.readStreamWithReader(fileStream, null));
                String firstLine = reader.readLine();
                while (firstLine != null && firstLine.trim().isEmpty() && reader.ready()) {
                    firstLine = reader.readLine();
                }
                if (StringUtil.isEmpty(firstLine)) {
                    Scope.getCurrentScope().getLog(this.getClass()).warning(String.format("Skipping empty file '%s'", changeLogFile));
                    boolean e = false;
                    return e;
                }
                boolean e = this.FIRST_LINE_PATTERN.matcher(firstLine).matches();
                return e;
            }
            boolean fileStream = false;
            return fileStream;
        }
        catch (IOException e) {
            Scope.getCurrentScope().getLog(this.getClass()).fine("Exception reading " + changeLogFile, e);
            boolean bl = false;
            return bl;
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    Scope.getCurrentScope().getLog(this.getClass()).fine("Exception closing " + changeLogFile, e);
                }
            }
        }
    }

    @Override
    public int getPriority() {
        return 6;
    }

    @Override
    public DatabaseChangeLog parse(String physicalChangeLogLocation, ChangeLogParameters changeLogParameters, ResourceAccessor resourceAccessor) throws ChangeLogParseException {
        DatabaseChangeLog changeLog = new DatabaseChangeLog();
        changeLog.setChangeLogParameters(changeLogParameters);
        changeLog.setPhysicalFilePath(physicalChangeLogLocation);
        try (BufferedReader reader = new BufferedReader(StreamUtil.readStreamWithReader(this.openChangeLogFile(physicalChangeLogLocation, resourceAccessor), null));){
            String line;
            StringBuilder currentSequence = new StringBuilder();
            StringBuilder currentRollbackSequence = new StringBuilder();
            ChangeSet changeSet = null;
            AbstractSQLChange change = null;
            Matcher rollbackSplitStatementsPatternMatcher = null;
            boolean rollbackSplitStatements = true;
            String rollbackEndDelimiter = null;
            int count = 0;
            block9: while ((line = reader.readLine()) != null) {
                String message;
                ++count;
                Matcher commentMatcher = this.COMMENT_PATTERN.matcher(line);
                Matcher propertyPatternMatcher = this.PROPERTY_PATTERN.matcher(line);
                Matcher altPropertyPatternMatcher = this.ALT_PROPERTY_ONE_CHARACTER_PATTERN.matcher(line);
                if (propertyPatternMatcher.matches()) {
                    this.handleProperty(changeLogParameters, changeLog, line);
                    continue;
                }
                if (altPropertyPatternMatcher.matches()) {
                    String message2 = String.format(EXCEPTION_MESSAGE, physicalChangeLogLocation, count, this.getSequenceName(), "--property name=<property name> value=<property value>", this.getDocumentationLink());
                    throw new ChangeLogParseException("\n" + message2);
                }
                Matcher changeLogPatterMatcher = this.FIRST_LINE_PATTERN.matcher(line);
                this.setLogicalFilePath(changeLog, line, changeLogPatterMatcher);
                Matcher ignoreLinesMatcher = this.IGNORE_LINES_PATTERN.matcher(line);
                Matcher altIgnoreMatcher = this.ALT_IGNORE_PATTERN.matcher(line);
                Matcher altIgnoreLinesOneDashMatcher = this.ALT_IGNORE_LINES_ONE_CHARACTER_PATTERN.matcher(line);
                if (ignoreLinesMatcher.matches()) {
                    if ("start".equals(ignoreLinesMatcher.group(1))) {
                        while ((line = reader.readLine()) != null) {
                            altIgnoreLinesOneDashMatcher = this.ALT_IGNORE_LINES_ONE_CHARACTER_PATTERN.matcher(line);
                            ++count;
                            ignoreLinesMatcher = this.IGNORE_LINES_PATTERN.matcher(line);
                            if (ignoreLinesMatcher.matches()) {
                                if (!"end".equals(ignoreLinesMatcher.group(1))) continue;
                                continue block9;
                            }
                            if (!altIgnoreLinesOneDashMatcher.matches()) continue;
                            String message3 = String.format(EXCEPTION_MESSAGE, physicalChangeLogLocation, count, this.getSequenceName(), "--ignoreLines:end", this.getDocumentationLink());
                            throw new ChangeLogParseException("\n" + message3);
                        }
                        continue;
                    }
                    try {
                        long ignoreCount = Long.parseLong(ignoreLinesMatcher.group(1));
                        while (ignoreCount > 0L && reader.readLine() != null) {
                            --ignoreCount;
                            ++count;
                        }
                        continue;
                    }
                    catch (NullPointerException | NumberFormatException nfe) {
                        throw new ChangeLogParseException("Unknown ignoreLines syntax");
                    }
                }
                if (altIgnoreLinesOneDashMatcher.matches() || altIgnoreMatcher.matches()) {
                    String message4 = String.format(EXCEPTION_MESSAGE, physicalChangeLogLocation, count, this.getSequenceName(), "--ignoreLines:<count|start>", this.getDocumentationLink());
                    throw new ChangeLogParseException("\n" + message4);
                }
                Matcher changeSetPatternMatcher = this.CHANGE_SET_PATTERN.matcher(line);
                if (changeSetPatternMatcher.matches()) {
                    String logicalFilePath;
                    String labels;
                    String runWithSpoolFile;
                    String finalCurrentSequence = changeLogParameters.expandExpressions(StringUtil.trimToNull(currentSequence.toString()), changeLog);
                    if (changeSet != null) {
                        if (finalCurrentSequence == null) {
                            throw new ChangeLogParseException(String.format("No %s for changeset %s", this.getSequenceName(), changeSet.toString(false)));
                        }
                        this.setChangeSequence(change, finalCurrentSequence);
                        this.handleRollbackSequence(physicalChangeLogLocation, changeLogParameters, changeLog, currentRollbackSequence, changeSet, rollbackSplitStatementsPatternMatcher, rollbackSplitStatements, rollbackEndDelimiter);
                    }
                    Matcher runWithMatcher = this.RUN_WITH_PATTERN.matcher(line);
                    Matcher runWithSpoolFileMatcher = this.RUN_WITH_SPOOL_FILE_PATTERN.matcher(line);
                    rollbackSplitStatementsPatternMatcher = this.ROLLBACK_SPLIT_STATEMENTS_PATTERN.matcher(line);
                    Matcher rollbackEndDelimiterPatternMatcher = this.ROLLBACK_END_DELIMITER_PATTERN.matcher(line);
                    Matcher logicalFilePathMatcher = this.LOGICAL_FILE_PATH_PATTERN.matcher(line);
                    Matcher runOnChangePatternMatcher = this.RUN_ON_CHANGE_PATTERN.matcher(line);
                    Matcher runAlwaysPatternMatcher = this.RUN_ALWAYS_PATTERN.matcher(line);
                    Matcher contextPatternMatcher = this.CONTEXT_PATTERN.matcher(line);
                    Matcher contextFilterPatternMatcher = this.CONTEXT_FILTER_PATTERN.matcher(line);
                    Matcher labelsPatternMatcher = this.LABELS_PATTERN.matcher(line);
                    Matcher runInTransactionPatternMatcher = this.RUN_IN_TRANSACTION_PATTERN.matcher(line);
                    Matcher ignorePatternMatcher = this.IGNORE_PATTERN.matcher(line);
                    Matcher failOnErrorPatternMatcher = this.FAIL_ON_ERROR_PATTERN.matcher(line);
                    rollbackSplitStatements = this.parseBoolean(rollbackSplitStatementsPatternMatcher, changeSet, true);
                    boolean runOnChange = this.parseBoolean(runOnChangePatternMatcher, changeSet, false);
                    boolean runAlways = this.parseBoolean(runAlwaysPatternMatcher, changeSet, false);
                    boolean runInTransaction = this.parseBoolean(runInTransactionPatternMatcher, changeSet, true);
                    boolean failOnError = this.parseBoolean(failOnErrorPatternMatcher, changeSet, true);
                    String runWith = this.parseString(runWithMatcher);
                    if (runWith != null) {
                        runWith = changeLogParameters.expandExpressions(runWith, changeLog);
                    }
                    if ((runWithSpoolFile = this.parseString(runWithSpoolFileMatcher)) != null) {
                        runWithSpoolFile = changeLogParameters.expandExpressions(runWithSpoolFile, changeLog);
                    }
                    rollbackEndDelimiter = this.parseString(rollbackEndDelimiterPatternMatcher);
                    String context = this.parseString(contextFilterPatternMatcher);
                    if (context == null || context.isEmpty()) {
                        context = this.parseString(contextPatternMatcher);
                    }
                    if (context != null) {
                        context = changeLogParameters.expandExpressions(StringUtil.stripEnclosingQuotes(context), changeLog);
                    }
                    if ((labels = this.parseString(labelsPatternMatcher)) != null) {
                        labels = changeLogParameters.expandExpressions(StringUtil.stripEnclosingQuotes(labels), changeLog);
                    }
                    if ((logicalFilePath = this.parseString(logicalFilePathMatcher)) == null || logicalFilePath.isEmpty()) {
                        logicalFilePath = changeLog.getLogicalFilePath();
                    }
                    if (logicalFilePath != null) {
                        logicalFilePath = changeLogParameters.expandExpressions(logicalFilePath, changeLog);
                    }
                    String dbms = this.handleDbms(changeLogParameters, line, changeLog);
                    String ignore = this.parseString(ignorePatternMatcher);
                    if (ignore != null) {
                        ignore = changeLogParameters.expandExpressions(ignore, changeLog);
                    }
                    String idGroup = changeSetPatternMatcher.group(2);
                    String authorGroup = changeSetPatternMatcher.group(1);
                    Pattern changeSetAuthorIdPattern = Pattern.compile(String.format("\\s*%s[\\s]*changeset\\s+", this.getSingleLineCommentSequence()) + Pattern.quote(authorGroup + ":" + idGroup) + ".*$", 2);
                    Matcher changeSetAuthorIdPatternMatcher = changeSetAuthorIdPattern.matcher(line);
                    if (!changeSetAuthorIdPatternMatcher.matches()) {
                        String message5 = String.format(EXCEPTION_MESSAGE, physicalChangeLogLocation, count, this.getSequenceName(), "--changeset <authorname>:<changesetId>", this.getDocumentationLink());
                        throw new ChangeLogParseException("\n" + message5);
                    }
                    String changeSetId = changeLogParameters.expandExpressions(StringUtil.stripEnclosingQuotes(idGroup), changeLog);
                    String changeSetAuthor = changeLogParameters.expandExpressions(StringUtil.stripEnclosingQuotes(authorGroup), changeLog);
                    changeSet = this.configureChangeSet(changeLog, runOnChange, runAlways, runInTransaction, failOnError, runWith, runWithSpoolFile, context, labels, logicalFilePath, dbms, ignore, changeSetId, changeSetAuthor);
                    changeLog.addChangeSet(changeSet);
                    change = this.getChange();
                    this.setChangeSequence(change, finalCurrentSequence);
                    this.handleSplitStatements(line, changeSet, change);
                    this.handleStripComments(line, changeSet, change);
                    this.handleEndDelimiter(line, change);
                    changeSet.addChange(change);
                    AbstractFormattedChangeLogParser.resetSequences(currentSequence, currentRollbackSequence);
                    continue;
                }
                Matcher altChangeSetOneDashPatternMatcher = this.ALT_CHANGE_SET_ONE_CHARACTER_PATTERN.matcher(line);
                Matcher altChangeSetNoOtherInfoPatternMatcher = this.ALT_CHANGE_SET_NO_OTHER_INFO_PATTERN.matcher(line);
                if (altChangeSetOneDashPatternMatcher.matches() || altChangeSetNoOtherInfoPatternMatcher.matches()) {
                    message = String.format(EXCEPTION_MESSAGE, physicalChangeLogLocation, count, this.getSequenceName(), "--changeset <authorname>:<changesetId>", this.getDocumentationLink());
                    throw new ChangeLogParseException("\n" + message);
                }
                if (changeSet != null) {
                    AtomicBoolean changeSetFinished = new AtomicBoolean(false);
                    this.configureChangeSet(physicalChangeLogLocation, changeLogParameters, reader, currentSequence, currentRollbackSequence, changeSet, count, line, commentMatcher, resourceAccessor, changeLog, change, rollbackSplitStatementsPatternMatcher, rollbackSplitStatements, rollbackEndDelimiter, changeSetFinished);
                    if (!changeSetFinished.get()) continue;
                    changeSet = null;
                    continue;
                }
                if (commentMatcher.matches()) {
                    message = String.format("Unexpected formatting at line %d. Formatted %s changelogs do not allow comment lines outside of changesets. Learn all the options at %s", count, this.getSequenceName(), this.getDocumentationLink());
                    throw new ChangeLogParseException("\n" + message);
                }
                this.handleAdditionalLines(changeLog, resourceAccessor, line);
            }
            if (currentSequence.length() > 0) {
                this.handleChangeSet(physicalChangeLogLocation, changeLogParameters, changeSet, currentSequence, change, changeLog, currentRollbackSequence, rollbackSplitStatementsPatternMatcher, rollbackSplitStatements, rollbackEndDelimiter);
            }
        }
        catch (IOException e) {
            throw new ChangeLogParseException(e);
        }
        return changeLog;
    }

    protected void handleChangeSet(String physicalChangeLogLocation, ChangeLogParameters changeLogParameters, ChangeSet changeSet, StringBuilder currentSequence, AbstractSQLChange change, DatabaseChangeLog changeLog, StringBuilder currentRollbackSequence, Matcher rollbackSplitStatementsPatternMatcher, boolean rollbackSplitStatements, String rollbackEndDelimiter) throws ChangeLogParseException {
        if (changeSet != null) {
            this.setChangeSequence(changeLogParameters, currentSequence, changeSet, change);
            if (this.isNotEndDelimiter(change)) {
                change.setEndDelimiter("\n/$");
            }
            this.handleRollbackSequence(physicalChangeLogLocation, changeLogParameters, changeLog, currentRollbackSequence, changeSet, rollbackSplitStatementsPatternMatcher, rollbackSplitStatements, rollbackEndDelimiter);
        }
    }

    protected void handleStripComments(String line, ChangeSet changeSet, AbstractSQLChange change) throws ChangeLogParseException {
        Matcher stripCommentsPatternMatcher = this.STRIP_COMMENTS_PATTERN.matcher(line);
        boolean stripComments = this.parseBoolean(stripCommentsPatternMatcher, changeSet, true);
        change.setStripComments(stripComments);
    }

    protected void handleEndDelimiter(String line, AbstractSQLChange change) {
        Matcher endDelimiterPatternMatcher = this.END_DELIMITER_PATTERN.matcher(line);
        String endDelimiter = this.parseString(endDelimiterPatternMatcher);
        change.setEndDelimiter(endDelimiter);
    }

    protected String handleDbms(ChangeLogParameters changeLogParameters, String line, DatabaseChangeLog changeLog) {
        Matcher dbmsPatternMatcher = this.DBMS_PATTERN.matcher(line);
        String dbms = this.parseString(dbmsPatternMatcher);
        if (dbms != null) {
            dbms = changeLogParameters.expandExpressions(dbms, changeLog);
        }
        return dbms;
    }

    protected void handleSplitStatements(String line, ChangeSet changeSet, AbstractSQLChange change) throws ChangeLogParseException {
        Matcher splitStatementsPatternMatcher = this.SPLIT_STATEMENTS_PATTERN.matcher(line);
        boolean splitStatements = this.parseBoolean(splitStatementsPatternMatcher, changeSet, true);
        if (splitStatementsPatternMatcher.matches()) {
            change.setSplitStatements(splitStatements);
        }
    }

    @Deprecated
    protected void configureChangeSet(String physicalChangeLogLocation, ChangeLogParameters changeLogParameters, BufferedReader reader, StringBuilder currentSequence, StringBuilder currentRollbackSequence, ChangeSet changeSet, int count, String line, Matcher commentMatcher) throws ChangeLogParseException, IOException {
        this.configureChangeSet(physicalChangeLogLocation, changeLogParameters, reader, currentSequence, currentRollbackSequence, changeSet, count, line, commentMatcher, null);
    }

    protected void configureChangeSet(String physicalChangeLogLocation, ChangeLogParameters changeLogParameters, BufferedReader reader, StringBuilder currentSequence, StringBuilder currentRollbackSequence, ChangeSet changeSet, int count, String line, Matcher commentMatcher, ResourceAccessor resourceAccessor) throws ChangeLogParseException, IOException {
        this.configureChangeSet(physicalChangeLogLocation, changeLogParameters, reader, currentSequence, currentRollbackSequence, changeSet, count, line, commentMatcher, resourceAccessor, null, null, null, false, null, new AtomicBoolean(false));
    }

    protected void configureChangeSet(String physicalChangeLogLocation, ChangeLogParameters changeLogParameters, BufferedReader reader, StringBuilder currentSequence, StringBuilder currentRollbackSequence, ChangeSet changeSet, int count, String line, Matcher commentMatcher, ResourceAccessor resourceAccessor, DatabaseChangeLog changeLog, AbstractSQLChange change, Matcher rollbackSplitStatementsMatcher, boolean rollbackSplitStatements, String rollbackEndDelimiter, AtomicBoolean changeSetFinished) throws ChangeLogParseException, IOException {
        Matcher altCommentOneDashMatcher = this.ALT_COMMENT_ONE_CHARACTER_PATTERN.matcher(line);
        Matcher altCommentPluralMatcher = this.ALT_COMMENT_PLURAL_PATTERN.matcher(line);
        Matcher rollbackMatcher = this.ROLLBACK_PATTERN.matcher(line);
        Matcher altRollbackMatcher = this.ALT_ROLLBACK_ONE_CHARACTER_PATTERN.matcher(line);
        Matcher preconditionsMatcher = this.PRECONDITIONS_PATTERN.matcher(line);
        Matcher altPreconditionsOneDashMatcher = this.ALT_PRECONDITIONS_ONE_CHARACTER_PATTERN.matcher(line);
        Matcher preconditionMatcher = this.PRECONDITION_PATTERN.matcher(line);
        Matcher altPreconditionOneDashMatcher = this.ALT_PRECONDITION_ONE_CHARACTER_PATTERN.matcher(line);
        Matcher validCheckSumMatcher = this.VALID_CHECK_SUM_PATTERN.matcher(line);
        Matcher altValidCheckSumOneDashMatcher = this.ALT_VALID_CHECK_SUM_ONE_CHARACTER_PATTERN.matcher(line);
        Matcher rollbackMultiLineStartMatcher = this.ROLLBACK_MULTI_LINE_START_PATTERN.matcher(line);
        Matcher invalidEmptyPreconditionMatcher = this.INVALID_EMPTY_PRECONDITION_PATTERN.matcher(line);
        if (commentMatcher.matches()) {
            if (commentMatcher.groupCount() == 0) {
                String message = String.format(EXCEPTION_MESSAGE, physicalChangeLogLocation, count, this.getSequenceName(), "--comment <comment>", this.getDocumentationLink());
                throw new ChangeLogParseException("\n" + message);
            }
            if (commentMatcher.groupCount() == 1) {
                changeSet.setComments(commentMatcher.group(1));
            }
        } else {
            if (altCommentOneDashMatcher.matches() || altCommentPluralMatcher.matches()) {
                String message = String.format(EXCEPTION_MESSAGE, physicalChangeLogLocation, count, this.getSequenceName(), "--comment <comment>", this.getDocumentationLink());
                throw new ChangeLogParseException("\n" + message);
            }
            if (validCheckSumMatcher.matches()) {
                if (validCheckSumMatcher.groupCount() == 0) {
                    String message = String.format(EXCEPTION_MESSAGE, physicalChangeLogLocation, count, this.getSequenceName(), String.format("--rollback <rollback %s>", this.getSequenceName()), this.getDocumentationLink());
                    throw new ChangeLogParseException("\n" + message);
                }
                if (validCheckSumMatcher.groupCount() == 1) {
                    changeSet.addValidCheckSum(validCheckSumMatcher.group(1));
                }
            } else {
                if (altValidCheckSumOneDashMatcher.matches()) {
                    String message = String.format(EXCEPTION_MESSAGE, physicalChangeLogLocation, count, this.getSequenceName(), "--validChecksum <checksum>", this.getDocumentationLink());
                    throw new ChangeLogParseException("\n" + message);
                }
                if (rollbackMatcher.matches()) {
                    if (rollbackMatcher.groupCount() == 0) {
                        String message = String.format(EXCEPTION_MESSAGE, physicalChangeLogLocation, count, this.getSequenceName(), String.format("--rollback <rollback %s>", this.getSequenceName()), this.getDocumentationLink());
                        throw new ChangeLogParseException("\n" + message);
                    }
                    currentRollbackSequence.append(rollbackMatcher.group(1)).append(System.lineSeparator());
                } else {
                    if (altRollbackMatcher.matches()) {
                        String message = String.format(EXCEPTION_MESSAGE, physicalChangeLogLocation, count, this.getSequenceName(), String.format("--rollback <rollback %s>", this.getSequenceName()), this.getDocumentationLink());
                        throw new ChangeLogParseException("\n" + message);
                    }
                    if (rollbackMultiLineStartMatcher.matches()) {
                        if (rollbackMultiLineStartMatcher.groupCount() == 0) {
                            currentRollbackSequence.append((CharSequence)this.extractMultiLineRollBack(reader));
                        }
                    } else if (preconditionsMatcher.matches()) {
                        this.handlePreconditionsCase(changeSet, count, preconditionsMatcher);
                    } else {
                        if (altPreconditionsOneDashMatcher.matches()) {
                            String message = String.format(EXCEPTION_MESSAGE, physicalChangeLogLocation, count, this.getSequenceName(), "--preconditions <onFail>|<onError>|<onUpdate>", this.getDocumentationLink());
                            throw new ChangeLogParseException("\n" + message);
                        }
                        if (preconditionMatcher.matches()) {
                            this.handlePreconditionCase(changeLogParameters, changeSet, preconditionMatcher);
                        } else {
                            if (altPreconditionOneDashMatcher.matches()) {
                                String message = String.format(EXCEPTION_MESSAGE, physicalChangeLogLocation, count, this.getSequenceName(), "--precondition-sql-check", this.getDocumentationLink());
                                throw new ChangeLogParseException("\n" + message);
                            }
                            if (invalidEmptyPreconditionMatcher.matches()) {
                                this.handleInvalidEmptyPreconditionCase(changeLogParameters, changeSet, invalidEmptyPreconditionMatcher);
                            } else {
                                currentSequence.append(line).append(System.lineSeparator());
                            }
                        }
                    }
                }
            }
        }
        changeSetFinished.set(false);
    }

    protected ChangeSet configureChangeSet(DatabaseChangeLog changeLog, boolean runOnChange, boolean runAlways, boolean runInTransaction, boolean failOnError, String runWith, String runWithSpoolFile, String context, String labels, String logicalFilePath, String dbms, String ignore, String changeSetId, String changeSetAuthor) {
        ChangeSetService service2 = ChangeSetServiceFactory.getInstance().createChangeSetService();
        ChangeSet changeSet = service2.createChangeSet(changeSetId, changeSetAuthor, runAlways, runOnChange, DatabaseChangeLog.normalizePath(logicalFilePath), context, dbms, runWith, runWithSpoolFile, runInTransaction, changeLog.getObjectQuotingStrategy(), changeLog);
        changeSet.setLabels(new Labels(labels));
        changeSet.setIgnore(Boolean.parseBoolean(ignore));
        changeSet.setFailOnError(failOnError);
        return changeSet;
    }

    protected void setLogicalFilePath(DatabaseChangeLog changeLog, String line, Matcher changeLogPatterMatcher) {
        if (changeLogPatterMatcher.matches()) {
            Matcher logicalFilePathMatcher = this.LOGICAL_FILE_PATH_PATTERN.matcher(line);
            changeLog.setLogicalFilePath(this.parseString(logicalFilePathMatcher));
        }
    }

    protected String parseString(Matcher matcher) {
        String endDelimiter = null;
        if (matcher.matches()) {
            endDelimiter = matcher.group(1);
        }
        return endDelimiter;
    }

    protected InputStream openChangeLogFile(String physicalChangeLogLocation, ResourceAccessor resourceAccessor) throws IOException {
        return resourceAccessor.getExisting(physicalChangeLogLocation).openInputStream();
    }

    protected void handleInvalidEmptyPreconditionCase(ChangeLogParameters changeLogParameters, ChangeSet changeSet, Matcher preconditionMatcher) throws ChangeLogParseException {
        throw new NotImplementedException("Invalid empty precondition found");
    }

    protected static void resetSequences(StringBuilder currentSequence, StringBuilder currentRollbackSequence) {
        currentSequence.setLength(0);
        currentRollbackSequence.setLength(0);
    }

    protected boolean handleAdditionalLines(DatabaseChangeLog changeLog, ResourceAccessor resourceAccessor, String line) throws ChangeLogParseException {
        return false;
    }

    private void handleRollbackSequence(String physicalChangeLogLocation, ChangeLogParameters changeLogParameters, DatabaseChangeLog changeLog, StringBuilder currentRollbackSequence, ChangeSet changeSet, Matcher rollbackSplitStatementsPatternMatcher, boolean rollbackSplitStatements, String rollbackEndDelimiter) throws ChangeLogParseException {
        String currentRollbackSequenceAsString = currentRollbackSequence.toString();
        if (StringUtil.trimToNull(currentRollbackSequenceAsString) != null) {
            if (currentRollbackSequenceAsString.trim().toLowerCase().matches("^not required.*") || currentRollbackSequence.toString().trim().toLowerCase().matches("^empty.*")) {
                changeSet.addRollbackChange(new EmptyChange());
            } else if (currentRollbackSequenceAsString.trim().toLowerCase().contains("changesetid")) {
                this.configureRollbackChangeSet(physicalChangeLogLocation, changeLog, changeSet, currentRollbackSequenceAsString);
            } else {
                this.configureRollbackChange(changeLogParameters, currentRollbackSequence, changeSet, rollbackSplitStatementsPatternMatcher, rollbackSplitStatements, rollbackEndDelimiter);
            }
        }
    }

    private void configureRollbackChange(ChangeLogParameters changeLogParameters, StringBuilder currentRollbackSequence, ChangeSet changeSet, Matcher rollbackSplitStatementsPatternMatcher, boolean rollbackSplitStatements, String rollbackEndDelimiter) {
        AbstractSQLChange rollbackChange = this.getChange();
        this.setChangeSequence(rollbackChange, changeLogParameters.expandExpressions(currentRollbackSequence.toString(), changeSet.getChangeLog()));
        if (rollbackSplitStatementsPatternMatcher.matches()) {
            rollbackChange.setSplitStatements(rollbackSplitStatements);
        }
        if (rollbackEndDelimiter != null) {
            rollbackChange.setEndDelimiter(rollbackEndDelimiter);
        }
        changeSet.addRollbackChange(rollbackChange);
    }

    private void configureRollbackChangeSet(String physicalChangeLogLocation, DatabaseChangeLog changeLog, ChangeSet changeSet, String currentRollBackSequenceAsString) throws ChangeLogParseException {
        String rollbackString = currentRollBackSequenceAsString.replace("\n", "").replace("\r", "");
        Matcher authorMatcher = this.ROLLBACK_CHANGE_SET_AUTHOR_PATTERN.matcher(rollbackString);
        Matcher idMatcher = this.ROLLBACK_CHANGE_SET_ID_PATTERN.matcher(rollbackString);
        Matcher pathMatcher = this.ROLLBACK_CHANGE_SET_PATH_PATTERN.matcher(rollbackString);
        String changeSetAuthor = StringUtil.trimToNull(this.parseString(authorMatcher));
        String changeSetId = StringUtil.trimToNull(this.parseString(idMatcher));
        String changeSetPath = StringUtil.trimToNull(this.parseString(pathMatcher));
        if (changeSetId == null) {
            throw new ChangeLogParseException("'changesetId' not set in rollback block '" + rollbackString + "'");
        }
        if (changeSetAuthor == null) {
            throw new ChangeLogParseException("'changesetAuthor' not set in rollback block '" + rollbackString + "'");
        }
        if (changeSetPath == null) {
            changeSetPath = physicalChangeLogLocation;
        }
        ChangeSet rollbackChangeSet = changeLog.getChangeSet(changeSetPath, changeSetAuthor, changeSetId);
        DatabaseChangeLog parent = changeLog;
        while (rollbackChangeSet == null && parent != null) {
            if ((parent = parent.getParentChangeLog()) == null) continue;
            rollbackChangeSet = parent.getChangeSet(changeSetPath, changeSetAuthor, changeSetId);
        }
        if (rollbackChangeSet == null) {
            throw new ChangeLogParseException("Change set " + new ChangeSet(changeSetId, changeSetAuthor, false, false, changeSetPath, null, null, null).toString(false) + " does not exist");
        }
        for (Change rollbackChange : rollbackChangeSet.getChanges()) {
            changeSet.addRollbackChange(rollbackChange);
        }
    }

    private void handleProperty(ChangeLogParameters changeLogParameters, DatabaseChangeLog changeLog, String line) {
        String dbms;
        String labels;
        String context;
        String value;
        Matcher namePatternMatcher = this.NAME_PATTERN.matcher(line);
        Matcher valuePatternMatcher = this.VALUE_PATTERN.matcher(line);
        Matcher contextPatternMatcher = this.CONTEXT_PATTERN.matcher(line);
        Matcher contextFilterPatternMatcher = this.CONTEXT_FILTER_PATTERN.matcher(line);
        Matcher labelsPatternMatcher = this.LABELS_PATTERN.matcher(line);
        Matcher dbmsPatternMatcher = this.DBMS_PATTERN.matcher(line);
        Matcher globalPatternMatcher = this.GLOBAL_PATTERN.matcher(line);
        String name = this.parseString(namePatternMatcher);
        if (name != null) {
            name = changeLogParameters.expandExpressions(name, changeLog);
        }
        if ((value = this.parseString(valuePatternMatcher)) != null) {
            value = changeLogParameters.expandExpressions(value, changeLog);
        }
        if ((context = this.parseString(contextFilterPatternMatcher)) == null || context.isEmpty()) {
            context = this.parseString(contextPatternMatcher);
        }
        if (context != null) {
            context = changeLogParameters.expandExpressions(StringUtil.stripEnclosingQuotes(context), changeLog);
        }
        if ((labels = this.parseString(labelsPatternMatcher)) != null) {
            labels = changeLogParameters.expandExpressions(StringUtil.stripEnclosingQuotes(labels), changeLog);
        }
        if ((dbms = this.parseString(dbmsPatternMatcher)) != null) {
            dbms = changeLogParameters.expandExpressions(dbms.trim(), changeLog);
        }
        boolean global = this.parseBoolean(globalPatternMatcher, true);
        changeLogParameters.set(name, (Object)value, context, labels, dbms, global, changeLog);
    }

    private StringBuilder extractMultiLineRollBack(BufferedReader reader) throws IOException, ChangeLogParseException {
        StringBuilder multiLineRollback = new StringBuilder();
        if (reader != null) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (this.ROLLBACK_MULTI_LINE_END_PATTERN.matcher(line).matches()) {
                    String[] lastLineSplit = line.split(String.format("%s\\s*$", this.getEndMultiLineCommentSequence()));
                    if (lastLineSplit.length > 0 && !StringUtil.isWhitespace(lastLineSplit[0])) {
                        multiLineRollback.append(lastLineSplit[0]);
                    }
                    return multiLineRollback;
                }
                multiLineRollback.append(line);
            }
            throw new ChangeLogParseException("Liquibase rollback comment is not closed.");
        }
        return multiLineRollback;
    }

    private boolean parseBoolean(Matcher matcher, boolean defaultValue) {
        boolean value = defaultValue;
        if (matcher.matches()) {
            value = Boolean.parseBoolean(matcher.group(1));
        }
        return value;
    }

    protected boolean parseBoolean(Matcher matcher, ChangeSet changeSet, boolean defaultValue) throws ChangeLogParseException {
        boolean stripComments = defaultValue;
        if (matcher.matches()) {
            try {
                stripComments = Boolean.parseBoolean(matcher.group(1));
            }
            catch (Exception e) {
                throw new ChangeLogParseException("Cannot parse " + changeSet + " " + matcher.toString().replaceAll("\\.*", "") + " as a boolean");
            }
        }
        return stripComments;
    }
}

