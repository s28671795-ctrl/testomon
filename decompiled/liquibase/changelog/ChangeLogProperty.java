/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import liquibase.changelog.ChangeLogChild;
import liquibase.serializer.AbstractLiquibaseSerializable;

public class ChangeLogProperty
extends AbstractLiquibaseSerializable
implements ChangeLogChild {
    private String file;
    private Boolean relativeToChangelogFile;
    private Boolean errorIfMissing;
    private String name;
    private String value;
    private String contextFilter;
    private String labels;
    private String dbms;
    private Boolean global;

    @Override
    public Set<String> getSerializableFields() {
        return new LinkedHashSet<String>(Arrays.asList("file", "relativeToChangelogFile", "errorIfMissing", "name", "value", "contextFilter", "labels", "dbms", "global"));
    }

    @Override
    public String getSerializedObjectName() {
        return "property";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setRelativeToChangelogFile(Boolean getRelativeToChangelogFile) {
        this.relativeToChangelogFile = getRelativeToChangelogFile;
    }

    public void setErrorIfMissing(Boolean errorIfMissing) {
        this.errorIfMissing = errorIfMissing;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Deprecated
    public String getContext() {
        return this.contextFilter;
    }

    @Deprecated
    public void setContext(String context) {
        this.contextFilter = context;
    }

    public ChangeLogProperty setContextFilter(String contextFilter) {
        this.contextFilter = contextFilter;
        return this;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public void setDbms(String dbms) {
        this.dbms = dbms;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
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

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public String getContextFilter() {
        return this.contextFilter;
    }

    public String getLabels() {
        return this.labels;
    }

    public String getDbms() {
        return this.dbms;
    }

    public Boolean getGlobal() {
        return this.global;
    }
}

