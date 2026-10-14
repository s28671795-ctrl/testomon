/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.UpdateSummaryEnum;
import liquibase.UpdateSummaryOutputEnum;
import liquibase.changelog.ChangeLogIterator;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.ChangeSetStatus;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.changelog.filter.DbmsChangeSetFilter;
import liquibase.changelog.filter.ShouldRunChangeSetFilter;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.changelog.visitor.DefaultChangeExecListener;
import liquibase.changelog.visitor.StatusVisitor;
import liquibase.exception.LiquibaseException;
import liquibase.logging.mdc.MdcObject;
import liquibase.logging.mdc.customobjects.UpdateSummary;
import liquibase.report.ShowSummaryGenerator;
import liquibase.report.ShowSummaryGeneratorFactory;
import liquibase.util.CollectionUtil;
import liquibase.util.StringUtil;
import liquibase.util.TableOutput;
import liquibase.util.UpdateSummaryDetails;

public class ShowSummaryUtil {
    @Deprecated
    public static void showUpdateSummary(DatabaseChangeLog changeLog, UpdateSummaryEnum showSummary, UpdateSummaryOutputEnum showSummaryOutput, StatusVisitor statusVisitor, OutputStream outputStream2) throws LiquibaseException, IOException {
        ShowSummaryUtil.showUpdateSummary(changeLog, showSummary, showSummaryOutput, statusVisitor, outputStream2, null);
    }

    public static void showUpdateSummary(DatabaseChangeLog changeLog, UpdateSummaryEnum showSummary, UpdateSummaryOutputEnum showSummaryOutput, StatusVisitor statusVisitor, OutputStream outputStream2, ChangeLogIterator runChangeLogIterator) throws LiquibaseException, IOException {
        ShowSummaryUtil.buildSummaryDetails(changeLog, showSummary, showSummaryOutput, statusVisitor, outputStream2, runChangeLogIterator, null);
    }

    @Deprecated
    public static UpdateSummaryDetails buildSummaryDetails(DatabaseChangeLog changeLog, UpdateSummaryEnum showSummary, UpdateSummaryOutputEnum showSummaryOutput, StatusVisitor statusVisitor, OutputStream outputStream2, ChangeLogIterator runChangeLogIterator) throws LiquibaseException, IOException {
        return ShowSummaryUtil.buildSummaryDetails(changeLog, showSummary, showSummaryOutput, statusVisitor, outputStream2, runChangeLogIterator, null);
    }

    public static UpdateSummaryDetails buildSummaryDetails(DatabaseChangeLog changeLog, UpdateSummaryEnum showSummary, UpdateSummaryOutputEnum showSummaryOutput, StatusVisitor statusVisitor, OutputStream outputStream2, ChangeLogIterator runChangeLogIterator, ChangeExecListener changeExecListener) throws LiquibaseException, IOException {
        if (showSummary == null || showSummary == UpdateSummaryEnum.OFF) {
            return null;
        }
        List<ChangeSetStatus> denied = statusVisitor.getChangeSetsToSkip();
        List<ChangeSet> skippedChangeSets = changeLog.getSkippedChangeSets();
        List<ChangeSetStatus> filterDenied = denied.stream().filter(status -> status.getFilterResults().stream().anyMatch(result -> result.getFilter() != ShouldRunChangeSetFilter.class)).collect(Collectors.toList());
        ShowSummaryGeneratorFactory showSummaryGeneratorFactory = Scope.getCurrentScope().getSingleton(ShowSummaryGeneratorFactory.class);
        ShowSummaryGenerator showSummaryGenerator = showSummaryGeneratorFactory.getShowSummaryGenerator();
        List<ChangeSetStatus> additionalChangeSetStatus = showSummaryGenerator.getAllAdditionalChangeSetStatus(runChangeLogIterator);
        UpdateSummaryDetails summaryDetails = ShowSummaryUtil.showSummary(changeLog, statusVisitor, skippedChangeSets, filterDenied, outputStream2, showSummaryOutput, runChangeLogIterator, changeExecListener);
        summaryDetails.getSummary().setValue(showSummary.toString());
        boolean shouldPrintDetailTable = showSummary != UpdateSummaryEnum.SUMMARY && (!skippedChangeSets.isEmpty() || !denied.isEmpty() || !additionalChangeSetStatus.isEmpty());
        FilteredChanges filteredChanges = ShowSummaryUtil.showDetailTable(skippedChangeSets, filterDenied, outputStream2, shouldPrintDetailTable, showSummaryOutput, additionalChangeSetStatus, runChangeLogIterator);
        summaryDetails.getSummary().setSkipped(filteredChanges.getMdcSkipCounts());
        summaryDetails.setSkipped(filteredChanges.getSkippedChangesetsMessage());
        try (MdcObject updateSummaryMdcObject = Scope.getCurrentScope().addMdcValue("updateSummary", summaryDetails.getSummary());){
            Scope.getCurrentScope().getLog(ShowSummaryUtil.class).info("Update summary generated");
        }
        return summaryDetails;
    }

    private static FilteredChanges showDetailTable(List<ChangeSet> skippedChangeSets, List<ChangeSetStatus> filterDenied, OutputStream outputStream2, boolean shouldPrintDetailTable, UpdateSummaryOutputEnum showSummaryOutput, List<ChangeSetStatus> additionalChangesets, ChangeLogIterator runChangeLogIterator) throws IOException, LiquibaseException {
        String totalSkippedMdcKey = "totalSkipped";
        if (filterDenied.isEmpty() && skippedChangeSets.isEmpty() && additionalChangesets.isEmpty()) {
            return new FilteredChanges(new TreeMap<String, Integer>(Collections.singletonMap(totalSkippedMdcKey, 0)), new LinkedHashMap<ChangeSet, String>());
        }
        ArrayList<String> columnHeaders = new ArrayList<String>();
        columnHeaders.add("Changeset Info");
        columnHeaders.add("Reason Skipped");
        ArrayList<List<String>> table = new ArrayList<List<String>>();
        table.add(columnHeaders);
        TreeMap<String, Integer> mdcSkipCounts = new TreeMap<String, Integer>();
        mdcSkipCounts.put(totalSkippedMdcKey, skippedChangeSets.size() + filterDenied.size());
        List<ChangeSetStatus> finalList = ShowSummaryUtil.createFinalStatusList(skippedChangeSets, filterDenied, mdcSkipCounts);
        ShowSummaryGeneratorFactory showSummaryGeneratorFactory = Scope.getCurrentScope().getSingleton(ShowSummaryGeneratorFactory.class);
        ShowSummaryGenerator showSummaryGenerator = showSummaryGeneratorFactory.getShowSummaryGenerator();
        finalList.addAll(showSummaryGenerator.getAllAdditionalChangeSetStatus(runChangeLogIterator));
        finalList.sort((o1, o2) -> {
            ChangeSet c1 = o1.getChangeSet();
            ChangeSet c2 = o2.getChangeSet();
            int order1 = ShowSummaryUtil.determineOrderInChangelog(c1);
            int order2 = ShowSummaryUtil.determineOrderInChangelog(c2);
            return Integer.compare(order1, order2);
        });
        LinkedHashMap<ChangeSet, String> filteredChangesets = new LinkedHashMap<ChangeSet, String>();
        ArrayList skippedMessages = new ArrayList();
        for (ChangeSetStatus changeSetStatus : finalList) {
            AtomicBoolean flag = new AtomicBoolean(true);
            StringBuilder builder = new StringBuilder();
            changeSetStatus.getFilterResults().forEach(filterResult -> {
                if (filterResult.getFilter() != null && !filterResult.getFilter().isAssignableFrom(ShouldNotCountAsSkipChangesetFilter.class)) {
                    String displayName = filterResult.getMdcName();
                    mdcSkipCounts.merge(displayName, 1, Integer::sum);
                }
                String skippedMessage = String.format("   '%s' : %s", changeSetStatus.getChangeSet().toString(), filterResult.getMessage());
                skippedMessages.add(skippedMessage);
                if (!flag.get()) {
                    builder.append(System.lineSeparator());
                }
                builder.append(filterResult.getMessage());
                flag.set(false);
            });
            ArrayList<String> outputRow = new ArrayList<String>();
            outputRow.add(changeSetStatus.getChangeSet().toString());
            outputRow.add(builder.toString());
            filteredChangesets.put(changeSetStatus.getChangeSet(), builder.toString());
            table.add(outputRow);
        }
        if (shouldPrintDetailTable) {
            switch (showSummaryOutput) {
                case CONSOLE: {
                    ShowSummaryUtil.printDetailTable(table, outputStream2);
                    break;
                }
                case LOG: {
                    skippedMessages.forEach(ShowSummaryUtil::writeToLog);
                    break;
                }
                default: {
                    ShowSummaryUtil.printDetailTable(table, outputStream2);
                    skippedMessages.forEach(ShowSummaryUtil::writeToLog);
                }
            }
        }
        return new FilteredChanges(mdcSkipCounts, filteredChangesets);
    }

    private static void printDetailTable(List<List<String>> table, OutputStream outputStream2) throws IOException, LiquibaseException {
        ArrayList<Integer> widths = new ArrayList<Integer>();
        widths.add(60);
        widths.add(40);
        Writer writer = ShowSummaryUtil.createOutputWriter(outputStream2);
        TableOutput.formatOutput(table, widths, true, writer);
    }

    private static List<ChangeSetStatus> createFinalStatusList(List<ChangeSet> skippedChangeSets, List<ChangeSetStatus> filterDenied, SortedMap<String, Integer> mdcSkipCounts) {
        ArrayList<ChangeSetStatus> finalList = new ArrayList<ChangeSetStatus>(filterDenied);
        skippedChangeSets.forEach(skippedChangeSet -> {
            String dbmsList = String.format("'%s'", StringUtil.join(skippedChangeSet.getDbmsSet(), ", "));
            String mismatchMessage = String.format("mismatched DBMS value of %s", dbmsList);
            ChangeSetStatus changeSetStatus = new ChangeSetStatus((ChangeSet)skippedChangeSet);
            ChangeSetFilterResult filterResult = new ChangeSetFilterResult(false, mismatchMessage, DbmsChangeSetFilter.class, "dbmsMismatch", "DBMS mismatch");
            changeSetStatus.setFilterResults(Collections.singleton(filterResult));
            finalList.add(changeSetStatus);
        });
        return finalList;
    }

    private static int determineOrderInChangelog(ChangeSet changeSetToMatch) {
        DatabaseChangeLog changeLog = changeSetToMatch.getChangeLog();
        int order = 0;
        for (ChangeSet changeSet : changeLog.getChangeSets()) {
            if (changeSet == changeSetToMatch) {
                return order;
            }
            ++order;
        }
        return -1;
    }

    private static UpdateSummaryDetails showSummary(DatabaseChangeLog changeLog, StatusVisitor statusVisitor, List<ChangeSet> skippedChangeSets, List<ChangeSetStatus> filterDenied, OutputStream outputStream2, UpdateSummaryOutputEnum showSummaryOutput, ChangeLogIterator runChangeLogIterator, ChangeExecListener changeExecListener) throws LiquibaseException {
        StringBuilder builder = new StringBuilder();
        builder.append(System.lineSeparator());
        int skipped = skippedChangeSets.size();
        int filtered2 = filterDenied.size();
        int totalAccepted = ShowSummaryUtil.calculateAccepted(statusVisitor, changeExecListener);
        int totalPreviouslyRun = ShowSummaryUtil.calculatePreviouslyRun(statusVisitor);
        int totalInChangelog = CollectionUtil.createIfNull(changeLog.getChangeSets()).size() + CollectionUtil.createIfNull(changeLog.getSkippedChangeSets()).size();
        UpdateSummary updateSummaryMdc = new UpdateSummary(null, totalAccepted, totalPreviouslyRun, null, totalInChangelog);
        String message = "UPDATE SUMMARY";
        builder.append(message);
        builder.append(System.lineSeparator());
        message = String.format("Run:                     %6d", totalAccepted);
        builder.append(message);
        builder.append(System.lineSeparator());
        message = String.format("Previously run:          %6d", totalPreviouslyRun);
        builder.append(message);
        builder.append(System.lineSeparator());
        message = String.format("Filtered out:            %6d", filtered2 + skipped);
        builder.append(message);
        builder.append(System.lineSeparator());
        ShowSummaryGeneratorFactory showSummaryGeneratorFactory = Scope.getCurrentScope().getSingleton(ShowSummaryGeneratorFactory.class);
        ShowSummaryGenerator showSummaryGenerator = showSummaryGeneratorFactory.getShowSummaryGenerator();
        showSummaryGenerator.appendAdditionalSummaryMessages(builder, runChangeLogIterator);
        message = "-------------------------------";
        builder.append(message);
        builder.append(System.lineSeparator());
        message = String.format("Total change sets:       %6d", totalInChangelog);
        builder.append(message);
        builder.append(System.lineSeparator());
        builder.append(System.lineSeparator());
        LinkedHashMap<String, Integer> filterSummaryMap = new LinkedHashMap<String, Integer>();
        List<ChangeSetStatus> finalList = ShowSummaryUtil.createFinalStatusList(skippedChangeSets, filterDenied, null);
        finalList.forEach(status -> status.getFilterResults().forEach(result -> {
            if (!result.isAccepted()) {
                String displayName = result.getDisplayName();
                filterSummaryMap.merge(displayName, 1, Integer::sum);
            }
        }));
        if (!filterSummaryMap.isEmpty()) {
            message = "FILTERED CHANGE SETS SUMMARY";
            builder.append(System.lineSeparator());
            builder.append(message);
            builder.append(System.lineSeparator());
            filterSummaryMap.forEach((filterDisplayName, count) -> {
                String filterSummaryDetailMessage = String.format("%-18s       %6d", filterDisplayName + ":", count);
                builder.append(filterSummaryDetailMessage);
                builder.append(System.lineSeparator());
            });
            builder.append(System.lineSeparator());
        }
        String outputMessage = builder.toString();
        ShowSummaryUtil.writeMessage(outputMessage, showSummaryOutput, outputStream2);
        UpdateSummaryDetails updateSummaryDetails = new UpdateSummaryDetails();
        updateSummaryDetails.setSummary(updateSummaryMdc);
        updateSummaryDetails.setOutput(outputMessage);
        return updateSummaryDetails;
    }

    private static int calculatePreviouslyRun(StatusVisitor statusVisitor) {
        return (int)statusVisitor.getStatuses().stream().filter(s2 -> s2.getFilterResults().stream().anyMatch(fr -> fr.getFilter().isAssignableFrom(ShouldRunChangeSetFilter.class) && !fr.isAccepted() && fr.getMessage().equals("Changeset already ran"))).count();
    }

    private static int calculateAccepted(StatusVisitor statusVisitor, ChangeExecListener changeExecListener) {
        int ran = statusVisitor.getChangeSetsToRun().size();
        if (changeExecListener instanceof DefaultChangeExecListener) {
            ran = ((DefaultChangeExecListener)changeExecListener).getDeployedChangeSets().size();
        }
        return ran;
    }

    private static Writer createOutputWriter(OutputStream outputStream2) throws IOException {
        String charsetName = GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue();
        return new OutputStreamWriter(outputStream2, charsetName);
    }

    private static void writeMessage(String message, UpdateSummaryOutputEnum showSummaryOutput, OutputStream outputStream2) throws LiquibaseException {
        switch (showSummaryOutput) {
            case CONSOLE: {
                ShowSummaryUtil.writeToOutput(outputStream2, message);
                break;
            }
            case LOG: {
                ShowSummaryUtil.writeToLog(message);
                break;
            }
            default: {
                ShowSummaryUtil.writeToOutput(outputStream2, message);
                ShowSummaryUtil.writeToLog(message);
            }
        }
    }

    private static void writeToOutput(OutputStream outputStream2, String message) throws LiquibaseException {
        try {
            Writer writer = ShowSummaryUtil.createOutputWriter(outputStream2);
            writer.append(message);
            writer.flush();
        }
        catch (IOException ioe) {
            throw new LiquibaseException(ioe);
        }
    }

    private static void writeToLog(String message) {
        Stream.of(message.split(System.lineSeparator())).filter(s2 -> !StringUtil.isWhitespace(s2)).forEach(Scope.getCurrentScope().getLog(ShowSummaryUtil.class)::info);
    }

    private static class FilteredChanges {
        private final SortedMap<String, Integer> mdcSkipCounts;
        private final Map<ChangeSet, String> skippedChangesetsMessage;

        public FilteredChanges(SortedMap<String, Integer> mdcSkipCounts, Map<ChangeSet, String> skippedChangesetsMessage) {
            this.mdcSkipCounts = mdcSkipCounts;
            this.skippedChangesetsMessage = skippedChangesetsMessage;
        }

        public SortedMap<String, Integer> getMdcSkipCounts() {
            return this.mdcSkipCounts;
        }

        public Map<ChangeSet, String> getSkippedChangesetsMessage() {
            return this.skippedChangesetsMessage;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof FilteredChanges)) {
                return false;
            }
            FilteredChanges other = (FilteredChanges)o;
            if (!other.canEqual(this)) {
                return false;
            }
            SortedMap<String, Integer> this$mdcSkipCounts = this.getMdcSkipCounts();
            SortedMap<String, Integer> other$mdcSkipCounts = other.getMdcSkipCounts();
            if (this$mdcSkipCounts == null ? other$mdcSkipCounts != null : !this$mdcSkipCounts.equals(other$mdcSkipCounts)) {
                return false;
            }
            Map<ChangeSet, String> this$skippedChangesetsMessage = this.getSkippedChangesetsMessage();
            Map<ChangeSet, String> other$skippedChangesetsMessage = other.getSkippedChangesetsMessage();
            return !(this$skippedChangesetsMessage == null ? other$skippedChangesetsMessage != null : !((Object)this$skippedChangesetsMessage).equals(other$skippedChangesetsMessage));
        }

        protected boolean canEqual(Object other) {
            return other instanceof FilteredChanges;
        }

        public int hashCode() {
            int PRIME = 59;
            int result = 1;
            SortedMap<String, Integer> $mdcSkipCounts = this.getMdcSkipCounts();
            result = result * 59 + ($mdcSkipCounts == null ? 43 : $mdcSkipCounts.hashCode());
            Map<ChangeSet, String> $skippedChangesetsMessage = this.getSkippedChangesetsMessage();
            result = result * 59 + ($skippedChangesetsMessage == null ? 43 : ((Object)$skippedChangesetsMessage).hashCode());
            return result;
        }

        public String toString() {
            return "ShowSummaryUtil.FilteredChanges(mdcSkipCounts=" + this.getMdcSkipCounts() + ", skippedChangesetsMessage=" + this.getSkippedChangesetsMessage() + ")";
        }
    }

    public static interface ShouldNotCountAsSkipChangesetFilter
    extends ChangeSetFilter {
    }
}

