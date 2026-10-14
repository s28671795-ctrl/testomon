/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import liquibase.ContextExpression;
import liquibase.changelog.ChangeLogChild;
import liquibase.serializer.AbstractLiquibaseSerializable;

public class ChangeLogInclude
extends AbstractLiquibaseSerializable
implements ChangeLogChild {
    private String file;
    private Boolean relativeToChangelogFile;
    private Boolean errorIfMissing;
    private ContextExpression context;

    @Override
    public Set<String> getSerializableFields() {
        return new LinkedHashSet<String>(Arrays.asList("file", "relativeToChangelogFile", "errorIfMissing", "context"));
    }

    @Override
    public String getSerializedObjectName() {
        return "include";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    public String getFile() {
        return this.file;
    }

    public Boolean getRelativeToChangelogFile() {
        return this.relativeToChangelogFile;
    }

    public Boolean getErrorIfMissing() {
        return this.errorIfMissing;
    }

    public ContextExpression getContext() {
        return this.context;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setRelativeToChangelogFile(Boolean relativeToChangelogFile) {
        this.relativeToChangelogFile = relativeToChangelogFile;
    }

    public void setErrorIfMissing(Boolean errorIfMissing) {
        this.errorIfMissing = errorIfMissing;
    }

    public void setContext(ContextExpression context) {
        this.context = context;
    }
}

