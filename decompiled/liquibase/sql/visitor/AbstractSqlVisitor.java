/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sql.visitor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import liquibase.ChecksumVersion;
import liquibase.ContextExpression;
import liquibase.Labels;
import liquibase.Scope;
import liquibase.change.CheckSum;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.serializer.ReflectionSerializer;
import liquibase.serializer.core.string.StringChangeLogSerializer;
import liquibase.sql.visitor.SqlVisitor;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;

public abstract class AbstractSqlVisitor
implements SqlVisitor {
    private Set<String> applicableDbms;
    private boolean applyToRollback;
    private ContextExpression contextFilter;
    private Labels labels;

    @Override
    public Set<String> getApplicableDbms() {
        return this.applicableDbms;
    }

    @Override
    public void setApplicableDbms(Set<String> applicableDbms) {
        this.applicableDbms = applicableDbms;
    }

    @Override
    public boolean isApplyToRollback() {
        return this.applyToRollback;
    }

    @Override
    public void setApplyToRollback(boolean applyToRollback) {
        this.applyToRollback = applyToRollback;
    }

    @Deprecated
    public ContextExpression getContexts() {
        return this.contextFilter;
    }

    @Deprecated
    public void setContexts(ContextExpression contexts) {
        this.contextFilter = contexts;
    }

    @Override
    public ContextExpression getContextFilter() {
        return this.contextFilter;
    }

    @Override
    public void setContextFilter(ContextExpression contextFilter) {
        this.contextFilter = contextFilter;
    }

    @Override
    public Labels getLabels() {
        return this.labels;
    }

    @Override
    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    @Override
    public CheckSum generateCheckSum() {
        final ChecksumVersion version = Scope.getCurrentScope().getChecksumVersion();
        return CheckSum.compute(new StringChangeLogSerializer(new StringChangeLogSerializer.FieldFilter(){

            @Override
            public boolean include(Object obj, String field2, Object value) {
                if (Arrays.stream(AbstractSqlVisitor.this.getExcludedFieldFilters(version)).anyMatch(filter2 -> filter2.equals(field2))) {
                    return false;
                }
                return super.include(obj, field2, value);
            }
        }).serialize(this, false));
    }

    public String[] getExcludedFieldFilters(ChecksumVersion version) {
        if (version.lowerOrEqualThan(ChecksumVersion.V8)) {
            return new String[0];
        }
        return new String[]{"applicableDbms", "contextFilter", "labels"};
    }

    @Override
    public Set<String> getSerializableFields() {
        return ReflectionSerializer.getInstance().getFields(this);
    }

    @Override
    public Object getSerializableFieldValue(String field2) {
        return ReflectionSerializer.getInstance().getValue(this, field2);
    }

    @Override
    public String getSerializedObjectName() {
        return this.getName();
    }

    @Override
    public LiquibaseSerializable.SerializationType getSerializableFieldType(String field2) {
        return LiquibaseSerializable.SerializationType.NAMED_FIELD;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog-ext";
    }

    @Override
    public String getSerializableFieldNamespace(String field2) {
        return this.getSerializedObjectNamespace();
    }

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        for (ParsedNode childNode : parsedNode.getChildren()) {
            try {
                Object value;
                if ("dbms".equals(childNode.getName())) {
                    this.setApplicableDbms(new HashSet<String>(StringUtil.splitAndTrim((String)childNode.getValue(), ",")));
                    continue;
                }
                if ("applyToRollback".equals(childNode.getName())) {
                    value = childNode.getValue(Boolean.class);
                    if (value == null) continue;
                    this.setApplyToRollback((Boolean)value);
                    continue;
                }
                if ("contextFilter".equals(childNode.getName()) || "context".equals(childNode.getName()) || "contexts".equals(childNode.getName())) {
                    this.setContextFilter(new ContextExpression((String)childNode.getValue()));
                    continue;
                }
                if (!ObjectUtil.hasWriteProperty(this, childNode.getName())) continue;
                value = childNode.getValue();
                if (value != null) {
                    value = value.toString();
                }
                ObjectUtil.setProperty((Object)this, childNode.getName(), (String)value);
            }
            catch (Exception e) {
                throw new ParsedNodeException("Error setting property", e);
            }
        }
    }

    @Override
    public ParsedNode serialize() {
        throw new RuntimeException("TODO");
    }
}

