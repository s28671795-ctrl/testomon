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

public class ChangeLogIncludeAll
extends AbstractLiquibaseSerializable
implements ChangeLogChild {
    private String path;
    private Boolean errorIfMissingOrEmpty;
    private Boolean relativeToChangelogFile;
    private String resourceFilter;
    private ContextExpression context;
    private int minDepth;
    private int maxDepth;
    private String endsWithFilter;

    @Override
    public Set<String> getSerializableFields() {
        return new LinkedHashSet<String>(Arrays.asList("path", "errorIfMissingOrEmpty", "relativeToChangelogFile", "resourceFilter", "context", "minDepth", "maxDepth", "endsWithFilter"));
    }

    @Override
    public String getSerializedObjectName() {
        return "includeAll";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    public String getPath() {
        return this.path;
    }

    public Boolean getErrorIfMissingOrEmpty() {
        return this.errorIfMissingOrEmpty;
    }

    public Boolean getRelativeToChangelogFile() {
        return this.relativeToChangelogFile;
    }

    public String getResourceFilter() {
        return this.resourceFilter;
    }

    public ContextExpression getContext() {
        return this.context;
    }

    public int getMinDepth() {
        return this.minDepth;
    }

    public int getMaxDepth() {
        return this.maxDepth;
    }

    public String getEndsWithFilter() {
        return this.endsWithFilter;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setErrorIfMissingOrEmpty(Boolean errorIfMissingOrEmpty) {
        this.errorIfMissingOrEmpty = errorIfMissingOrEmpty;
    }

    public void setRelativeToChangelogFile(Boolean relativeToChangelogFile) {
        this.relativeToChangelogFile = relativeToChangelogFile;
    }

    public void setResourceFilter(String resourceFilter) {
        this.resourceFilter = resourceFilter;
    }

    public void setContext(ContextExpression context) {
        this.context = context;
    }

    public void setMinDepth(int minDepth) {
        this.minDepth = minDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public void setEndsWithFilter(String endsWithFilter) {
        this.endsWithFilter = endsWithFilter;
    }
}

