/*
 * Decompiled with CFR 0.152.
 */
package liquibase.structure.core;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import liquibase.GlobalConfiguration;
import liquibase.change.ColumnConfig;
import liquibase.change.ConstraintsConfig;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.AbstractLiquibaseSerializable;
import liquibase.structure.AbstractDatabaseObject;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.DataType;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.structure.core.View;
import liquibase.util.BooleanUtil;
import liquibase.util.StringUtil;

public class Column
extends AbstractDatabaseObject {
    private String name;
    private Boolean computed;
    private Boolean descending;
    private boolean forIndex;

    public Column() {
    }

    public Column(String columnName) {
        this.setName(columnName);
    }

    public Column(Class<? extends Relation> relationType, String catalogName, String schemaName, String tableName, String columnName) {
        if (Table.class.isAssignableFrom(relationType)) {
            this.setRelation(new Table(catalogName, schemaName, tableName));
        } else if (View.class.isAssignableFrom(relationType)) {
            this.setRelation(new View(catalogName, schemaName, tableName));
        }
        this.setName(columnName);
    }

    public Column(ColumnConfig columnConfig) {
        ConstraintsConfig constraints;
        this.setName(columnConfig.getName());
        this.setDescending(columnConfig.getDescending());
        this.setType(new DataType(columnConfig.getType()));
        if (columnConfig.getDefaultValueObject() != null) {
            this.setDefaultValue(columnConfig.getDefaultValueObject());
        }
        if (columnConfig.isAutoIncrement() != null && columnConfig.isAutoIncrement().booleanValue()) {
            this.setAutoIncrementInformation(new AutoIncrementInformation(columnConfig.getStartWith(), columnConfig.getIncrementBy()));
        }
        if ((constraints = columnConfig.getConstraints()) != null) {
            this.setNullable(constraints.isNullable());
            this.setValidateNullable(constraints.getValidateNullable());
        }
        this.setRemarks(columnConfig.getRemarks());
    }

    public boolean isForIndex() {
        return this.forIndex;
    }

    public void setForIndex(boolean forIndex) {
        this.forIndex = forIndex;
    }

    public Relation getRelation() {
        return (Relation)((Object)this.getAttribute("relation", Relation.class));
    }

    @Override
    public DatabaseObject[] getContainingObjects() {
        return new DatabaseObject[]{this.getRelation()};
    }

    public Column setRelation(Relation relation) {
        this.setAttribute("relation", relation);
        return this;
    }

    @Override
    public Schema getSchema() {
        Relation relation = this.getRelation();
        if (relation == null) {
            return null;
        }
        return relation.getSchema();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Column setName(String name) {
        this.name = name;
        this.setAttribute("name", name);
        return this;
    }

    public Column setName(String name, boolean computed) {
        this.setName(name);
        this.setComputed(computed);
        return this;
    }

    public Boolean getComputed() {
        return this.computed;
    }

    public Column setComputed(Boolean computed) {
        this.computed = computed;
        this.setAttribute("computed", computed);
        return this;
    }

    public Boolean isNullable() {
        return (Boolean)((Object)this.getAttribute("nullable", Boolean.class));
    }

    public Column setNullable(Boolean nullable) {
        this.setAttribute("nullable", nullable);
        return this;
    }

    public DataType getType() {
        return (DataType)((Object)this.getAttribute("type", DataType.class));
    }

    public Column setType(DataType type) {
        this.setAttribute("type", type);
        return this;
    }

    public Object getDefaultValue() {
        return this.getAttribute("defaultValue", Object.class);
    }

    public Column setDefaultValue(Object defaultValue) {
        this.setAttribute("defaultValue", defaultValue);
        return this;
    }

    public String getDefaultValueConstraintName() {
        return (String)((Object)this.getAttribute("defaultValueConstraintName", String.class));
    }

    public Column setDefaultValueConstraintName(String defaultValueConstraintName) {
        this.setAttribute("defaultValueConstraintName", defaultValueConstraintName);
        return this;
    }

    public boolean isAutoIncrement() {
        return this.getAutoIncrementInformation() != null;
    }

    public AutoIncrementInformation getAutoIncrementInformation() {
        return (AutoIncrementInformation)((Object)this.getAttribute("autoIncrementInformation", AutoIncrementInformation.class));
    }

    public void setAutoIncrementInformation(AutoIncrementInformation autoIncrementInformation) {
        this.setAttribute("autoIncrementInformation", autoIncrementInformation);
    }

    public Boolean getDescending() {
        return this.descending;
    }

    public Column setDescending(Boolean descending) {
        this.descending = descending;
        this.setAttribute("descending", descending);
        return this;
    }

    public boolean getValidate() {
        return this.getAttribute("validate", true);
    }

    public Column setValidateNullable(Boolean validateNullable) {
        this.setAttribute("validateNullable", validateNullable);
        return this;
    }

    public boolean getValidateNullable() {
        return this.getAttribute("validateNullable", true);
    }

    public String toString(boolean includeRelation) {
        if (includeRelation) {
            return this.toString();
        }
        return this.getName() + (this.getDescending() != null && this.getDescending() != false ? " DESC" : "");
    }

    @Override
    public String toString() {
        String columnOrder;
        String string = columnOrder = this.getDescending() != null && this.getDescending() != false ? " DESC" : "";
        if (this.getRelation() == null) {
            return this.getName() + columnOrder;
        }
        String tableOrViewName = this.getRelation().getName();
        if (this.getRelation().getSchema() != null && this.getRelation().getSchema().getName() != null) {
            tableOrViewName = this.getRelation().getSchema().getName() + "." + tableOrViewName;
        }
        return tableOrViewName + "." + this.getName() + columnOrder;
    }

    @Override
    public int compareTo(Object other) {
        Column o = (Column)other;
        try {
            int returnValue = 0;
            if (this.getRelation() != null && o.getRelation() == null) {
                return 1;
            }
            if (this.getRelation() == null && o.getRelation() != null) {
                return -1;
            }
            if (this.getRelation() != null && o.getRelation() != null && (returnValue = this.getRelation().compareTo(o.getRelation())) == 0 && this.getRelation().getSchema() != null && o.getRelation().getSchema() != null) {
                returnValue = this.getSchema().compareTo(o.getRelation().getSchema());
            }
            if (returnValue == 0) {
                returnValue = this.toString().toUpperCase().compareTo(o.toString().toUpperCase());
            }
            return returnValue;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean equals(Object o) {
        try {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            Column column = (Column)o;
            return this.compareTo(column) == 0;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int hashCode() {
        return this.toString().toUpperCase().hashCode();
    }

    public boolean isDataTypeDifferent(Column otherColumn) {
        if (!this.isCertainDataType() || !otherColumn.isCertainDataType()) {
            return false;
        }
        return !this.getType().equals(otherColumn.getType());
    }

    public boolean isNullabilityDifferent(Column otherColumn) {
        if (this.isNullable() == null && otherColumn.isNullable() == null) {
            return false;
        }
        if (this.isNullable() == null && otherColumn.isNullable() != null) {
            return true;
        }
        if (this.isNullable() != null && otherColumn.isNullable() == null) {
            return true;
        }
        return !this.isNullable().equals(otherColumn.isNullable());
    }

    public boolean isDifferent(Column otherColumn) {
        return this.isDataTypeDifferent(otherColumn) || this.isNullabilityDifferent(otherColumn);
    }

    public boolean isCertainDataType() {
        return (Boolean)((Object)this.getAttribute("certainDataType", Boolean.class));
    }

    public Column setCertainDataType(boolean certainDataType) {
        this.setAttribute("certainDataType", certainDataType);
        return this;
    }

    public String getRemarks() {
        return (String)((Object)this.getAttribute("remarks", String.class));
    }

    public Column setRemarks(String remarks) {
        this.setAttribute("remarks", remarks);
        return this;
    }

    public static Column fromName(String columnName) {
        columnName = columnName.trim();
        Boolean descending = null;
        if (columnName.matches("(?i).*\\s+DESC")) {
            columnName = columnName.replaceFirst("(?i)\\s+DESC$", "");
            descending = true;
        } else if (columnName.matches("(?i).*\\s+ASC")) {
            columnName = columnName.replaceFirst("(?i)\\s+ASC$", "");
            descending = false;
        }
        return new Column(columnName).setDescending(descending);
    }

    public Integer getOrder() {
        return (Integer)((Object)this.getAttribute("order", Integer.class));
    }

    public Column setOrder(Integer order) {
        this.setAttribute("order", order);
        return this;
    }

    public static Column[] arrayFromNames(String columnNames) {
        if (columnNames == null) {
            return null;
        }
        List<String> columnNameList = StringUtil.splitAndTrim(columnNames, ",");
        Column[] returnArray = new Column[columnNameList.size()];
        for (int i = 0; i < columnNameList.size(); ++i) {
            returnArray[i] = Column.fromName(columnNameList.get(i));
        }
        return returnArray;
    }

    public static List<Column> listFromNames(String columnNames) {
        if (columnNames == null) {
            return null;
        }
        return Arrays.asList(Column.arrayFromNames(columnNames));
    }

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        ParsedNode autoIncrementInformation;
        super.load(parsedNode, resourceAccessor);
        ParsedNode typeNode = parsedNode.getChild(null, "type");
        if (typeNode != null) {
            DataType type = new DataType();
            type.load(typeNode, resourceAccessor);
            this.setType(type);
        }
        if ((autoIncrementInformation = parsedNode.getChild(null, "autoIncrementInformation")) != null) {
            AutoIncrementInformation info = new AutoIncrementInformation();
            info.load(autoIncrementInformation, resourceAccessor);
            this.setAutoIncrementInformation(info);
        }
    }

    @Override
    public Set<String> getSerializableFields() {
        Set<String> fields = super.getSerializableFields();
        if ((BooleanUtil.isTrue(this.getDescending()) || BooleanUtil.isTrue(this.getComputed())) && !this.isRealObject()) {
            fields.remove("relation");
        }
        fields.remove("forIndex");
        return fields;
    }

    private boolean isRealObject() {
        if (Boolean.FALSE.equals(GlobalConfiguration.INCLUDE_RELATIONS_FOR_COMPUTED_COLUMNS.getCurrentValue())) {
            return false;
        }
        Class<Object> obj = this.getAttribute("relation", Object.class);
        if (obj instanceof DatabaseObject) {
            DatabaseObject databaseObject = (DatabaseObject)((Object)obj);
            return databaseObject.getSnapshotId() != null;
        }
        return false;
    }

    public static class AutoIncrementInformation
    extends AbstractLiquibaseSerializable {
        private BigInteger startWith;
        private BigInteger incrementBy;
        private Boolean defaultOnNull;
        private String generationType;

        public AutoIncrementInformation() {
            this(1, 1);
        }

        public AutoIncrementInformation(Number startWith, Number incrementBy) {
            this.startWith = startWith == null ? null : BigInteger.valueOf(startWith.longValue());
            this.incrementBy = incrementBy == null ? null : BigInteger.valueOf(incrementBy.longValue());
        }

        public BigInteger getStartWith() {
            return this.startWith;
        }

        public BigInteger getIncrementBy() {
            return this.incrementBy;
        }

        public void setDefaultOnNull(Boolean defaultOnNull) {
            this.defaultOnNull = defaultOnNull;
        }

        public Boolean getDefaultOnNull() {
            return this.defaultOnNull;
        }

        public void setGenerationType(String generationType) {
            this.generationType = generationType;
        }

        public String getGenerationType() {
            return this.generationType;
        }

        public String toString() {
            return String.format("GENERATED %s %sAUTO INCREMENT START WITH %d INCREMENT BY %d", this.generationType, Boolean.TRUE.equals(this.defaultOnNull) ? "ON NULL " : "", this.startWith, this.incrementBy);
        }

        @Override
        public String getSerializedObjectName() {
            return "autoIncrementInformation";
        }

        @Override
        public String getSerializedObjectNamespace() {
            return "http://www.liquibase.org/xml/ns/dbchangelog";
        }

        @Override
        public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
            this.startWith = (BigInteger)this.convertEscaped(parsedNode.getChildValue(null, "startWith"));
            this.incrementBy = (BigInteger)this.convertEscaped(parsedNode.getChildValue(null, "incrementBy"));
            this.defaultOnNull = parsedNode.getChildValue((String)null, "defaultOnNull", Boolean.class);
            this.generationType = parsedNode.getChildValue((String)null, "generationType", String.class);
        }
    }
}

