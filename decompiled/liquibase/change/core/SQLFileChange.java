/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import liquibase.ChecksumVersion;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.change.AbstractSQLChange;
import liquibase.change.CheckSum;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.changelog.ChangeLogParameters;
import liquibase.database.Database;
import liquibase.exception.SetupException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.parser.ChangeLogParserConfiguration;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;
import liquibase.util.FileUtil;
import liquibase.util.ObjectUtil;
import liquibase.util.StreamUtil;
import liquibase.util.StringUtil;

@DatabaseChange(name="sqlFile", description="Allows you to specify any SQL statement and have it stored external in a file.", priority=1)
public class SQLFileChange
extends AbstractSQLChange {
    private String path;
    private Boolean relativeToChangelogFile;
    private Boolean doExpandExpressionsInGenerateChecksum = false;

    @Override
    public boolean generateStatementsVolatile(Database database) {
        return false;
    }

    @Override
    public boolean generateRollbackStatementsVolatile(Database database) {
        return false;
    }

    @DatabaseChangeProperty(description="The file path of the SQL file to load", exampleValue="my/path/file.sql", requiredForDatabase={"all"})
    public String getPath() {
        return this.path;
    }

    public void setPath(String fileName) {
        this.path = fileName;
    }

    @DatabaseChangeProperty(exampleValue="utf8", description="Encoding used in the file you specify in 'path'")
    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @DatabaseChangeProperty(description="Specifies whether the file path is relative to the changelog file rather than looked up in the search path. Default: false.")
    public Boolean isRelativeToChangelogFile() {
        return this.relativeToChangelogFile;
    }

    public void setRelativeToChangelogFile(Boolean relativeToChangelogFile) {
        this.relativeToChangelogFile = relativeToChangelogFile;
    }

    public void setDoExpandExpressionsInGenerateChecksum(Boolean doExpandExpressionsInGenerateChecksum) {
        this.doExpandExpressionsInGenerateChecksum = doExpandExpressionsInGenerateChecksum;
    }

    @Override
    public void finishInitialization() throws SetupException {
        if (this.path == null) {
            throw new SetupException("<sqlfile> - No path specified");
        }
    }

    @Override
    public InputStream openSqlStream() throws IOException {
        if (this.path == null) {
            return null;
        }
        return this.getResource().openInputStream();
    }

    private Resource getResource() throws IOException {
        ResourceAccessor resourceAccessor = Scope.getCurrentScope().getResourceAccessor();
        if (ObjectUtil.defaultIfNull(this.isRelativeToChangelogFile(), false).booleanValue()) {
            return resourceAccessor.get(this.getChangeSet().getChangeLog().getPhysicalFilePath()).resolveSibling(this.path);
        }
        return resourceAccessor.getExisting(this.path);
    }

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validationErrors = new ValidationErrors();
        if (StringUtil.trimToNull(this.getPath()) == null) {
            validationErrors.addError("'path' is required");
        } else {
            try {
                Resource resource = this.getResource();
                if (!resource.exists()) {
                    this.alertOnNonExistentSqlFile(validationErrors);
                }
            }
            catch (IOException e) {
                Scope.getCurrentScope().getLog(this.getClass()).warning("Failed to obtain sqlFile resource at path '" + this.path + "'while attempting to validate the existence of the sqlFile.", e);
                this.alertOnNonExistentSqlFile(validationErrors);
            }
        }
        return validationErrors;
    }

    private void alertOnNonExistentSqlFile(ValidationErrors validationErrors) {
        if (ChangeLogParserConfiguration.ON_MISSING_SQL_FILE.getCurrentValue().equals((Object)ChangeLogParserConfiguration.MissingIncludeConfiguration.WARN)) {
            validationErrors.addWarning(FileUtil.getFileNotFoundMessage(this.path));
        } else {
            validationErrors.addError(FileUtil.getFileNotFoundMessage(this.path));
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "SQL in file " + this.path + " executed";
    }

    @Override
    @DatabaseChangeProperty(isChangeProperty=false)
    public String getSql() {
        return this.getSql(true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getSql(boolean doExpandExpressions) {
        String sql = super.getSql();
        if (sql != null) return sql;
        try (InputStream sqlStream = this.openSqlStream();){
            ChangeLogParameters parameters;
            if (sqlStream == null) {
                String string2 = null;
                return string2;
            }
            String content = StreamUtil.readStreamAsString(sqlStream, this.getEncoding());
            if (doExpandExpressions && this.getChangeSet() != null && (parameters = this.getChangeSet().getChangeLogParameters()) != null) {
                content = parameters.expandExpressions(content, this.getChangeSet().getChangeLog());
            }
            String string = content;
            return string;
        }
        catch (IOException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    @Override
    public void setSql(String sql) {
        if (this.getChangeSet() != null && this.getChangeSet().getChangeLogParameters() != null) {
            sql = this.getChangeSet().getChangeLogParameters().expandExpressions(sql, this.getChangeSet().getChangeLog());
        }
        super.setSql(sql);
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public String describe() {
        return "SQLFileChange{path='" + this.path + '\'' + ", relativeToChangelogFile=" + this.relativeToChangelogFile + '}';
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public CheckSum generateCheckSum() {
        ChecksumVersion version = Scope.getCurrentScope().getChecksumVersion();
        if (version.lowerOrEqualThan(ChecksumVersion.V8)) {
            return super.generateCheckSum();
        }
        InputStream stream = null;
        try {
            String sqlContent = this.getSql(this.doExpandExpressionsInGenerateChecksum);
            Charset encoding = GlobalConfiguration.FILE_ENCODING.getCurrentValue();
            stream = new ByteArrayInputStream(sqlContent.getBytes(encoding));
            CheckSum checkSum = CheckSum.compute(new AbstractSQLChange.NormalizingStream(stream), false);
            return checkSum;
        }
        finally {
            if (stream != null) {
                try {
                    stream.close();
                }
                catch (IOException iOException) {}
            }
        }
    }
}

