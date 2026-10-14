/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import liquibase.ChecksumVersion;
import liquibase.ContextExpression;
import liquibase.GlobalConfiguration;
import liquibase.Labels;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.change.CheckSum;
import liquibase.change.core.TagDatabaseChange;
import liquibase.changelog.AbstractChangeLogHistoryService;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.RanChangeSet;
import liquibase.database.Database;
import liquibase.database.OfflineConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.executor.ExecutorService;
import liquibase.servicelocator.LiquibaseService;
import liquibase.statement.core.CreateDatabaseChangeLogTableStatement;
import liquibase.statement.core.MarkChangeSetRanStatement;
import liquibase.statement.core.RemoveChangeSetRanStatusStatement;
import liquibase.statement.core.UpdateChangeSetChecksumStatement;
import liquibase.util.ISODateFormat;
import liquibase.util.LiquibaseUtil;
import liquibase.util.csv.CSVReader;
import liquibase.util.csv.CSVWriter;

@LiquibaseService(skip=true)
public class OfflineChangeLogHistoryService
extends AbstractChangeLogHistoryService {
    private final File changeLogFile;
    private boolean executeDmlAgainstDatabase = true;
    private boolean executeDdlAgainstDatabase = true;
    private Integer lastChangeSetSequenceValue;

    public OfflineChangeLogHistoryService(Database database, File changeLogFile, boolean executeDmlAgainstDatabase, boolean executeDdlAgainstDatabase) {
        this.setDatabase(database);
        this.executeDmlAgainstDatabase = executeDmlAgainstDatabase;
        this.executeDdlAgainstDatabase = executeDdlAgainstDatabase;
        this.changeLogFile = changeLogFile = changeLogFile.getAbsoluteFile();
    }

    @Override
    public int getPriority() {
        return 500;
    }

    @Override
    public boolean supports(Database database) {
        return database.getConnection() != null && database.getConnection() instanceof OfflineConnection;
    }

    public boolean isExecuteDmlAgainstDatabase() {
        return this.executeDmlAgainstDatabase;
    }

    public void setExecuteDmlAgainstDatabase(boolean executeDmlAgainstDatabase) {
        this.executeDmlAgainstDatabase = executeDmlAgainstDatabase;
    }

    public boolean isExecuteDdlAgainstDatabase() {
        return this.executeDdlAgainstDatabase;
    }

    public void setExecuteDdlAgainstDatabase(boolean executeDdlAgainstDatabase) {
        this.executeDdlAgainstDatabase = executeDdlAgainstDatabase;
    }

    @Override
    public void reset() {
    }

    @Override
    public void init() throws DatabaseException {
        if (!this.changeLogFile.exists()) {
            this.changeLogFile.getParentFile().mkdirs();
            try {
                this.changeLogFile.createNewFile();
                this.writeHeader(this.changeLogFile);
                if (this.isExecuteDdlAgainstDatabase()) {
                    Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this.getDatabase()).execute(new CreateDatabaseChangeLogTableStatement());
                }
            }
            catch (Exception e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
    }

    protected void writeHeader(File file) throws IOException {
        try (OutputStream outputStream2 = Files.newOutputStream(file.toPath(), new OpenOption[0]);
             OutputStreamWriter writer = new OutputStreamWriter(outputStream2, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue());){
            CSVWriter csvWriter = new CSVWriter(writer);
            String[] columns = new String[Columns.values().length];
            int i = 0;
            for (Columns column : Columns.values()) {
                columns[i++] = column.toString();
            }
            csvWriter.writeNext(columns);
        }
    }

    @Override
    public void replaceChecksum(ChangeSet changeSet) throws DatabaseException {
        if (this.isExecuteDmlAgainstDatabase()) {
            Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this.getDatabase()).execute(new UpdateChangeSetChecksumStatement(changeSet));
        }
        this.replaceChangeSet(changeSet, line -> {
            line[Columns.MD5SUM.ordinal()] = changeSet.generateCheckSum(ChecksumVersion.latest()).toString();
            return line;
        });
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<RanChangeSet> getRanChangeSets() throws DatabaseException {
        try (InputStreamReader reader = new InputStreamReader(Files.newInputStream(this.changeLogFile.toPath(), new OpenOption[0]), GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue());){
            CSVReader csvReader = new CSVReader(reader);
            String[] line = csvReader.readNext();
            if (line == null) {
                this.writeHeader(this.changeLogFile);
                ArrayList<RanChangeSet> arrayList = new ArrayList<RanChangeSet>();
                return arrayList;
            }
            if (!"ID".equals(line[Columns.ID.ordinal()])) {
                throw new DatabaseException("Missing header in file " + this.changeLogFile.getAbsolutePath());
            }
            ArrayList<RanChangeSet> returnList = new ArrayList<RanChangeSet>();
            while ((line = csvReader.readNext()) != null) {
                ContextExpression contexts = new ContextExpression();
                if (line.length > Columns.CONTEXTS.ordinal()) {
                    contexts = new ContextExpression(line[Columns.CONTEXTS.ordinal()]);
                }
                Labels labels = new Labels();
                if (line.length > Columns.LABELS.ordinal()) {
                    labels = new Labels(line[Columns.LABELS.ordinal()]);
                }
                String deploymentId = null;
                if (line.length > Columns.DEPLOYMENT_ID.ordinal()) {
                    deploymentId = line[Columns.DEPLOYMENT_ID.ordinal()];
                }
                returnList.add(new RanChangeSet(line[Columns.FILENAME.ordinal()], line[Columns.ID.ordinal()], line[Columns.AUTHOR.ordinal()], CheckSum.parse(line[Columns.MD5SUM.ordinal()]), new ISODateFormat().parse(line[Columns.DATEEXECUTED.ordinal()]), line[Columns.TAG.ordinal()], ChangeSet.ExecType.valueOf(line[Columns.EXECTYPE.ordinal()]), line[Columns.DESCRIPTION.ordinal()], line[Columns.COMMENTS.ordinal()], contexts, labels, deploymentId));
            }
            ArrayList<RanChangeSet> arrayList = returnList;
            return arrayList;
        }
        catch (Exception e) {
            throw new DatabaseException(e);
        }
    }

    protected void replaceChangeSet(ChangeSet changeSet, ReplaceChangeSetLogic replaceLogic) throws DatabaseException {
        File oldFile = this.changeLogFile;
        File newFile = new File(oldFile.getParentFile(), oldFile.getName() + ".new");
        try (InputStreamReader reader = new InputStreamReader(Files.newInputStream(oldFile.toPath(), new OpenOption[0]), GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue());
             OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(newFile.toPath(), new OpenOption[0]), GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue());
             CSVReader csvReader = new CSVReader(reader);
             CSVWriter csvWriter = new CSVWriter(writer);){
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                if (changeSet == null || line[Columns.ID.ordinal()].equals(changeSet.getId()) && line[Columns.AUTHOR.ordinal()].equals(changeSet.getAuthor()) && line[Columns.FILENAME.ordinal()].equals(changeSet.getFilePath())) {
                    line = replaceLogic.execute(line);
                }
                if (line == null) continue;
                csvWriter.writeNext(line);
            }
        }
        catch (Exception e) {
            throw new DatabaseException(e);
        }
        oldFile.delete();
        newFile.renameTo(oldFile);
    }

    protected void appendChangeSet(ChangeSet changeSet, ChangeSet.ExecType execType) throws DatabaseException {
        File oldFile = this.changeLogFile;
        File newFile = new File(oldFile.getParentFile(), oldFile.getName() + ".new");
        try (InputStreamReader reader = new InputStreamReader(Files.newInputStream(oldFile.toPath(), new OpenOption[0]), GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue());
             OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(newFile.toPath(), new OpenOption[0]), GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue());
             CSVReader csvReader = new CSVReader(reader);
             CSVWriter csvWriter = new CSVWriter(writer);){
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                csvWriter.writeNext(line);
            }
            String tag = "";
            for (Change change : changeSet.getChanges()) {
                if (!(change instanceof TagDatabaseChange)) continue;
                TagDatabaseChange tagChange = (TagDatabaseChange)change;
                tag = tagChange.getTag();
            }
            String[] newLine = new String[Columns.values().length];
            newLine[Columns.ID.ordinal()] = changeSet.getId();
            newLine[Columns.AUTHOR.ordinal()] = changeSet.getAuthor();
            newLine[Columns.FILENAME.ordinal()] = changeSet.getFilePath();
            newLine[Columns.DATEEXECUTED.ordinal()] = new ISODateFormat().format(new Timestamp(new Date().getTime()));
            newLine[Columns.ORDEREXECUTED.ordinal()] = String.valueOf(this.getNextSequenceValue());
            newLine[Columns.EXECTYPE.ordinal()] = execType.value;
            newLine[Columns.MD5SUM.ordinal()] = changeSet.generateCheckSum(ChecksumVersion.latest()).toString();
            newLine[Columns.DESCRIPTION.ordinal()] = changeSet.getDescription();
            newLine[Columns.COMMENTS.ordinal()] = changeSet.getComments();
            newLine[Columns.TAG.ordinal()] = tag;
            newLine[Columns.LIQUIBASE.ordinal()] = LiquibaseUtil.getBuildVersion();
            newLine[Columns.CONTEXTS.ordinal()] = changeSet.getContextFilter() == null ? null : changeSet.getContextFilter().toString();
            newLine[Columns.LABELS.ordinal()] = changeSet.getLabels() == null ? null : changeSet.getLabels().toString();
            newLine[Columns.DEPLOYMENT_ID.ordinal()] = this.getDeploymentId();
            csvWriter.writeNext(newLine);
        }
        catch (Exception e) {
            throw new DatabaseException(e);
        }
        oldFile.delete();
        newFile.renameTo(oldFile);
    }

    @Override
    public void setExecType(ChangeSet changeSet, ChangeSet.ExecType execType) throws DatabaseException {
        if (this.isExecuteDmlAgainstDatabase()) {
            Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this.getDatabase()).execute(new MarkChangeSetRanStatement(changeSet, execType));
            this.getDatabase().commit();
        }
        if (execType.equals((Object)ChangeSet.ExecType.FAILED) || execType.equals((Object)ChangeSet.ExecType.SKIPPED)) {
            return;
        }
        if (execType.ranBefore) {
            this.replaceChangeSet(changeSet, line -> {
                line[Columns.DATEEXECUTED.ordinal()] = new ISODateFormat().format(new Timestamp(new Date().getTime()));
                line[Columns.MD5SUM.ordinal()] = changeSet.generateCheckSum(ChecksumVersion.enumFromChecksumVersion(changeSet.getStoredCheckSum().getVersion())).toString();
                line[Columns.EXECTYPE.ordinal()] = execType.value;
                return line;
            });
        } else {
            this.appendChangeSet(changeSet, execType);
        }
    }

    @Override
    public void removeFromHistory(ChangeSet changeSet) throws DatabaseException {
        if (this.isExecuteDmlAgainstDatabase()) {
            Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this.getDatabase()).execute(new RemoveChangeSetRanStatusStatement(changeSet));
            this.getDatabase().commit();
        }
        this.replaceChangeSet(changeSet, line -> null);
    }

    @Override
    public int getNextSequenceValue() throws LiquibaseException {
        if (this.lastChangeSetSequenceValue == null) {
            this.lastChangeSetSequenceValue = 0;
            try (InputStreamReader reader = new InputStreamReader(Files.newInputStream(this.changeLogFile.toPath(), new OpenOption[0]), GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue());){
                CSVReader csvReader = new CSVReader(reader);
                String[] line = csvReader.readNext();
                while ((line = csvReader.readNext()) != null) {
                    try {
                        this.lastChangeSetSequenceValue = Integer.valueOf(line[Columns.ORDEREXECUTED.ordinal()]);
                    }
                    catch (NumberFormatException numberFormatException) {}
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.lastChangeSetSequenceValue = this.lastChangeSetSequenceValue + 1;
        return this.lastChangeSetSequenceValue;
    }

    @Override
    public void tag(String tagString) throws DatabaseException {
        RanChangeSet last = null;
        List<RanChangeSet> ranChangeSets = this.getRanChangeSets();
        if (ranChangeSets.isEmpty()) {
            ChangeSet emptyChangeSet = new ChangeSet(String.valueOf(new Date().getTime()), "liquibase", false, false, "liquibase-internal", null, null, this.getDatabase().getObjectQuotingStrategy(), null);
            this.appendChangeSet(emptyChangeSet, ChangeSet.ExecType.EXECUTED);
            last = new RanChangeSet(emptyChangeSet);
        } else {
            last = ranChangeSets.get(ranChangeSets.size() - 1);
        }
        ChangeSet lastChangeSet = new ChangeSet(last.getId(), last.getAuthor(), false, false, last.getChangeLog(), null, null, true, null, null);
        this.replaceChangeSet(lastChangeSet, line -> {
            line[Columns.TAG.ordinal()] = tagString;
            return line;
        });
    }

    @Override
    public boolean tagExists(String tag) throws DatabaseException {
        List<RanChangeSet> ranChangeSets = this.getRanChangeSets();
        for (RanChangeSet changeset : ranChangeSets) {
            if (!tag.equals(changeset.getTag())) continue;
            return true;
        }
        return false;
    }

    @Override
    public void clearAllCheckSums() throws LiquibaseException {
        this.replaceChangeSet(null, line -> {
            line[Columns.MD5SUM.ordinal()] = null;
            return line;
        });
    }

    @Override
    public void destroy() throws DatabaseException {
        if (this.changeLogFile.exists() && !this.changeLogFile.delete()) {
            throw new DatabaseException("Could not delete changelog history file " + this.changeLogFile.getAbsolutePath());
        }
    }

    @Override
    public boolean isDatabaseChecksumsCompatible() {
        return true;
    }

    private static enum Columns {
        ID,
        AUTHOR,
        FILENAME,
        DATEEXECUTED,
        ORDEREXECUTED,
        EXECTYPE,
        MD5SUM,
        DESCRIPTION,
        COMMENTS,
        TAG,
        LIQUIBASE,
        CONTEXTS,
        LABELS,
        DEPLOYMENT_ID;

    }

    private static interface ReplaceChangeSetLogic {
        public String[] execute(String[] var1);
    }
}

