/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import liquibase.change.ConstraintsConfig;
import liquibase.exception.DateParseException;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.AbstractLiquibaseSerializable;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.serializer.ReflectionSerializer;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.NotNullConstraint;
import liquibase.statement.SequenceCurrentValueFunction;
import liquibase.statement.SequenceNextValueFunction;
import liquibase.structure.core.Column;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.ForeignKeyConstraintType;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Table;
import liquibase.structure.core.UniqueConstraint;
import liquibase.util.BooleanUtil;
import liquibase.util.ISODateFormat;
import liquibase.util.NowAndTodayUtil;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;

public class ColumnConfig
extends AbstractLiquibaseSerializable {
    private String name;
    private Boolean computed;
    private String type;
    private String value;
    private Number valueNumeric;
    private Date valueDate;
    private Boolean valueBoolean;
    private String valueBlobFile;
    private String valueClobFile;
    private String encoding;
    private DatabaseFunction valueComputed;
    private SequenceNextValueFunction valueSequenceNext;
    private SequenceCurrentValueFunction valueSequenceCurrent;
    private String defaultValue;
    private Number defaultValueNumeric;
    private Date defaultValueDate;
    private Boolean defaultValueBoolean;
    private DatabaseFunction defaultValueComputed;
    private SequenceNextValueFunction defaultValueSequenceNext;
    private String defaultValueConstraintName;
    private ConstraintsConfig constraints;
    private Boolean autoIncrement;
    private String generationType;
    private Boolean defaultOnNull;
    private BigInteger startWith;
    private BigInteger incrementBy;
    private String remarks;
    private Boolean descending;

    public ColumnConfig(Column columnSnapshot) {
        this.setName(columnSnapshot.getName());
        this.setComputed(BooleanUtil.isTrue(columnSnapshot.getComputed()) ? Boolean.TRUE : null);
        this.setDescending(BooleanUtil.isTrue(columnSnapshot.getDescending()) ? Boolean.TRUE : null);
        if (columnSnapshot.getType() != null) {
            this.setType(columnSnapshot.getType().toString());
        }
        if (columnSnapshot.getDefaultValue() != null) {
            Object defaultValue = columnSnapshot.getDefaultValue();
            if (defaultValue instanceof Boolean) {
                this.setDefaultValueBoolean((Boolean)defaultValue);
            } else if (defaultValue instanceof Number) {
                this.setDefaultValueNumeric(defaultValue.toString());
            } else if (defaultValue instanceof SequenceNextValueFunction) {
                this.setDefaultValueSequenceNext((SequenceNextValueFunction)defaultValue);
            } else if (defaultValue instanceof DatabaseFunction) {
                this.setDefaultValueComputed((DatabaseFunction)defaultValue);
            } else if (defaultValue instanceof Date) {
                this.setDefaultValueDate((Date)defaultValue);
            } else {
                this.setDefaultValue(defaultValue.toString());
            }
        }
        this.setDefaultValueConstraintName(columnSnapshot.getDefaultValueConstraintName());
        boolean nonDefaultConstraints = false;
        ConstraintsConfig constraints = new ConstraintsConfig();
        if (columnSnapshot.isNullable() != null && !columnSnapshot.isNullable().booleanValue()) {
            constraints.setNullable(columnSnapshot.isNullable());
            constraints.setValidateNullable(columnSnapshot.getValidateNullable());
            nonDefaultConstraints = true;
        }
        if (columnSnapshot.getRelation() != null && columnSnapshot.getRelation() instanceof Table) {
            List<ForeignKey> fks;
            List<UniqueConstraint> uniqueConstraints;
            Table table = (Table)columnSnapshot.getRelation();
            List<NotNullConstraint> notNullConstraints = table.getNotNullConstraints();
            if (notNullConstraints != null) {
                for (NotNullConstraint constraint : notNullConstraints) {
                    if (!constraint.getColumnName().equals(this.getName())) continue;
                    constraints.setNullable(false);
                    constraints.setNotNullConstraintName(constraint.getConstraintName());
                    nonDefaultConstraints = true;
                }
            }
            if (columnSnapshot.isAutoIncrement()) {
                this.setAutoIncrement(true);
                this.setStartWith(columnSnapshot.getAutoIncrementInformation().getStartWith());
                this.setIncrementBy(columnSnapshot.getAutoIncrementInformation().getIncrementBy());
            } else {
                this.setAutoIncrement(false);
            }
            PrimaryKey primaryKey = table.getPrimaryKey();
            if (primaryKey != null && primaryKey.getColumnNamesAsList().contains(columnSnapshot.getName())) {
                constraints.setPrimaryKey(true);
                constraints.setPrimaryKeyName(primaryKey.getName());
                constraints.setPrimaryKeyTablespace(primaryKey.getTablespace());
                nonDefaultConstraints = true;
            }
            if ((uniqueConstraints = table.getUniqueConstraints()) != null) {
                for (UniqueConstraint constraint : uniqueConstraints) {
                    if (!constraint.getColumnNames().contains(this.getName())) continue;
                    constraints.setUnique(true);
                    constraints.setUniqueConstraintName(constraint.getName());
                    nonDefaultConstraints = true;
                }
            }
            if ((fks = table.getOutgoingForeignKeys()) != null) {
                for (ForeignKey fk : fks) {
                    if (fk.getForeignKeyColumns() == null || fk.getForeignKeyColumns().size() != 1 || !fk.getForeignKeyColumns().get(0).getName().equals(this.getName())) continue;
                    constraints.setForeignKeyName(fk.getName());
                    constraints.setReferences(fk.getPrimaryKeyTable().getName() + "(" + fk.getPrimaryKeyColumns().get(0).getName() + ")");
                    constraints.setDeleteCascade(fk.getDeleteRule() != null && fk.getDeleteRule() == ForeignKeyConstraintType.importedKeyCascade);
                    nonDefaultConstraints = true;
                }
            }
            if (nonDefaultConstraints) {
                this.setConstraints(constraints);
            }
        }
        this.setRemarks(columnSnapshot.getRemarks());
    }

    public ColumnConfig() {
    }

    public static ColumnConfig fromName(String name) {
        name = name.trim();
        Boolean descending = null;
        if (name.matches("(?i).*\\s+DESC")) {
            name = name.replaceFirst("(?i)\\s+DESC$", "");
            descending = true;
        } else if (name.matches("(?i).*\\s+ASC")) {
            name = name.replaceFirst("(?i)\\s+ASC$", "");
            descending = false;
        }
        return new ColumnConfig().setName(name).setDescending(descending);
    }

    public static ColumnConfig[] arrayFromNames(String names) {
        if (names == null) {
            return null;
        }
        List<String> nameArray = StringUtil.splitAndTrim(names, ",");
        ColumnConfig[] returnArray = new ColumnConfig[nameArray.size()];
        for (int i = 0; i < nameArray.size(); ++i) {
            returnArray[i] = ColumnConfig.fromName(nameArray.get(i));
        }
        return returnArray;
    }

    public String getName() {
        return this.name;
    }

    public ColumnConfig setName(String name) {
        this.name = name;
        return this;
    }

    public ColumnConfig setName(String name, boolean computed) {
        this.setComputed(computed);
        return this.setName(name);
    }

    public Boolean getComputed() {
        return this.computed;
    }

    public ColumnConfig setComputed(Boolean computed) {
        this.computed = computed;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public ColumnConfig setType(String type) {
        this.type = type;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public ColumnConfig setValue(String value) {
        this.value = value;
        return this;
    }

    public Number getValueNumeric() {
        return this.valueNumeric;
    }

    public ColumnConfig setValueNumeric(Number valueNumeric) {
        this.valueNumeric = valueNumeric;
        return this;
    }

    public ColumnConfig setValueNumeric(String valueNumeric) {
        if (valueNumeric == null || "null".equalsIgnoreCase(valueNumeric)) {
            this.valueNumeric = null;
        } else {
            String saved = valueNumeric;
            if (valueNumeric.startsWith("(")) {
                valueNumeric = valueNumeric.replaceFirst("^\\(", "");
                valueNumeric = valueNumeric.replaceFirst("\\)$", "");
            }
            try {
                this.valueNumeric = ValueNumeric.of(Locale.US, valueNumeric);
            }
            catch (ParseException e) {
                this.valueComputed = new DatabaseFunction(saved);
            }
        }
        return this;
    }

    public Boolean getValueBoolean() {
        return this.valueBoolean;
    }

    public ColumnConfig setValueBoolean(String valueBoolean) {
        if ((valueBoolean = StringUtil.trimToNull(valueBoolean)) == null || "null".equalsIgnoreCase(valueBoolean)) {
            this.valueBoolean = null;
        } else if ("true".equalsIgnoreCase(valueBoolean) || "1".equals(valueBoolean)) {
            this.valueBoolean = true;
        } else if ("false".equalsIgnoreCase(valueBoolean) || "0".equals(valueBoolean)) {
            this.valueBoolean = false;
        } else {
            this.valueComputed = new DatabaseFunction(valueBoolean);
        }
        return this;
    }

    public ColumnConfig setValueBoolean(Boolean valueBoolean) {
        this.valueBoolean = valueBoolean;
        return this;
    }

    public DatabaseFunction getValueComputed() {
        return this.valueComputed;
    }

    public ColumnConfig setValueComputed(DatabaseFunction valueComputed) {
        this.valueComputed = valueComputed;
        return this;
    }

    public SequenceNextValueFunction getValueSequenceNext() {
        return this.valueSequenceNext;
    }

    public ColumnConfig setValueSequenceNext(SequenceNextValueFunction valueSequenceNext) {
        this.valueSequenceNext = valueSequenceNext;
        return this;
    }

    public SequenceCurrentValueFunction getValueSequenceCurrent() {
        return this.valueSequenceCurrent;
    }

    public ColumnConfig setValueSequenceCurrent(SequenceCurrentValueFunction valueSequenceCurrent) {
        this.valueSequenceCurrent = valueSequenceCurrent;
        return this;
    }

    public Date getValueDate() {
        return this.valueDate;
    }

    public ColumnConfig setValueDate(String valueDate) throws DateParseException {
        if (valueDate == null || "null".equalsIgnoreCase(valueDate)) {
            this.valueDate = null;
        } else if (NowAndTodayUtil.isNowOrTodayFormat(valueDate)) {
            this.valueDate = NowAndTodayUtil.doNowOrToday(valueDate, this.getType());
        } else {
            try {
                this.valueDate = new ISODateFormat().parse(valueDate);
            }
            catch (ParseException e) {
                this.valueComputed = new DatabaseFunction(valueDate);
            }
        }
        return this;
    }

    public ColumnConfig setValueDate(Date valueDate) {
        this.valueDate = valueDate;
        return this;
    }

    public String getValueBlobFile() {
        return this.valueBlobFile;
    }

    public ColumnConfig setValueBlobFile(String valueBlobFile) {
        this.valueBlobFile = valueBlobFile;
        return this;
    }

    public String getValueClobFile() {
        return this.valueClobFile;
    }

    public ColumnConfig setValueClobFile(String valueClobFile) {
        this.valueClobFile = valueClobFile;
        return this;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public ColumnConfig setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public Object getValueObject() {
        if (this.getValue() != null) {
            return this.getValue();
        }
        if (this.getValueBoolean() != null) {
            return this.getValueBoolean();
        }
        if (this.getValueNumeric() != null) {
            return this.getValueNumeric();
        }
        if (this.getValueDate() != null) {
            return this.getValueDate();
        }
        if (this.getValueComputed() != null) {
            return this.getValueComputed();
        }
        if (this.getValueClobFile() != null) {
            return this.getValueClobFile();
        }
        if (this.getValueBlobFile() != null) {
            return this.getValueBlobFile();
        }
        if (this.getValueSequenceNext() != null) {
            return this.getValueSequenceNext();
        }
        if (this.getValueSequenceCurrent() != null) {
            return this.getValueSequenceCurrent();
        }
        return null;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public ColumnConfig setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public Number getDefaultValueNumeric() {
        return this.defaultValueNumeric;
    }

    public ColumnConfig setDefaultValueNumeric(String defaultValueNumeric) {
        if (defaultValueNumeric == null || "null".equalsIgnoreCase(defaultValueNumeric)) {
            this.defaultValueNumeric = null;
        } else if ("GENERATED_BY_DEFAULT".equals(defaultValueNumeric)) {
            this.setAutoIncrement(true);
        } else {
            if (defaultValueNumeric.startsWith("(")) {
                defaultValueNumeric = defaultValueNumeric.replaceFirst("^\\(", "");
                defaultValueNumeric = defaultValueNumeric.replaceFirst("\\)$", "");
            }
            try {
                this.defaultValueNumeric = ValueNumeric.of(Locale.US, defaultValueNumeric);
            }
            catch (ParseException e) {
                this.defaultValueComputed = new DatabaseFunction(defaultValueNumeric);
            }
        }
        return this;
    }

    public ColumnConfig setDefaultValueNumeric(Number defaultValueNumeric) {
        this.defaultValueNumeric = defaultValueNumeric;
        return this;
    }

    public Date getDefaultValueDate() {
        return this.defaultValueDate;
    }

    public ColumnConfig setDefaultValueDate(Date defaultValueDate) {
        this.defaultValueDate = defaultValueDate;
        return this;
    }

    public ColumnConfig setDefaultValueDate(String defaultValueDate) {
        if ((defaultValueDate = StringUtil.trimToNull(defaultValueDate)) == null || "null".equalsIgnoreCase(defaultValueDate)) {
            this.defaultValueDate = null;
        } else {
            try {
                this.defaultValueDate = new ISODateFormat().parse(defaultValueDate);
            }
            catch (ParseException e) {
                this.defaultValueComputed = new DatabaseFunction(defaultValueDate);
            }
        }
        return this;
    }

    public Boolean getDefaultValueBoolean() {
        return this.defaultValueBoolean;
    }

    public ColumnConfig setDefaultValueBoolean(String defaultValueBoolean) {
        if ((defaultValueBoolean = StringUtil.trimToNull(defaultValueBoolean)) == null || "null".equalsIgnoreCase(defaultValueBoolean)) {
            this.defaultValueBoolean = null;
        } else if ("true".equalsIgnoreCase(defaultValueBoolean) || "1".equals(defaultValueBoolean)) {
            this.defaultValueBoolean = true;
        } else if ("false".equalsIgnoreCase(defaultValueBoolean) || "0".equals(defaultValueBoolean)) {
            this.defaultValueBoolean = false;
        } else {
            this.defaultValueComputed = new DatabaseFunction(defaultValueBoolean);
        }
        return this;
    }

    public ColumnConfig setDefaultValueBoolean(Boolean defaultValueBoolean) {
        this.defaultValueBoolean = defaultValueBoolean;
        return this;
    }

    public DatabaseFunction getDefaultValueComputed() {
        return this.defaultValueComputed;
    }

    public ColumnConfig setDefaultValueComputed(DatabaseFunction defaultValueComputed) {
        this.defaultValueComputed = defaultValueComputed;
        return this;
    }

    public Object getDefaultValueObject() {
        if (this.getDefaultValue() != null) {
            return this.getDefaultValue();
        }
        if (this.getDefaultValueBoolean() != null) {
            return this.getDefaultValueBoolean();
        }
        if (this.getDefaultValueNumeric() != null) {
            return this.getDefaultValueNumeric();
        }
        if (this.getDefaultValueDate() != null) {
            return this.getDefaultValueDate();
        }
        if (this.getDefaultValueComputed() != null) {
            return this.getDefaultValueComputed();
        }
        if (this.getDefaultValueSequenceNext() != null) {
            return this.getDefaultValueSequenceNext();
        }
        return null;
    }

    public ConstraintsConfig getConstraints() {
        return this.constraints;
    }

    public ColumnConfig setConstraints(ConstraintsConfig constraints) {
        this.constraints = constraints;
        return this;
    }

    public Boolean isAutoIncrement() {
        return this.autoIncrement;
    }

    public ColumnConfig setAutoIncrement(Boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
        return this;
    }

    public BigInteger getStartWith() {
        return this.startWith;
    }

    public ColumnConfig setStartWith(BigInteger startWith) {
        this.startWith = startWith;
        return this;
    }

    public BigInteger getIncrementBy() {
        return this.incrementBy;
    }

    public ColumnConfig setIncrementBy(BigInteger incrementBy) {
        this.incrementBy = incrementBy;
        return this;
    }

    public boolean hasDefaultValue() {
        return this.getDefaultValue() != null || this.getDefaultValueBoolean() != null || this.getDefaultValueDate() != null || this.getDefaultValueNumeric() != null || this.getDefaultValueComputed() != null || this.getDefaultValueSequenceNext() != null;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public ColumnConfig setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public Boolean getDescending() {
        return this.descending;
    }

    public ColumnConfig setDescending(Boolean descending) {
        this.descending = descending;
        return this;
    }

    public Boolean getDefaultOnNull() {
        return this.defaultOnNull;
    }

    public ColumnConfig setDefaultOnNull(Boolean defaultOnNull) {
        this.defaultOnNull = defaultOnNull;
        return this;
    }

    public String getGenerationType() {
        return this.generationType;
    }

    public ColumnConfig setGenerationType(String generationType) {
        this.generationType = generationType;
        return this;
    }

    @Override
    public String getSerializedObjectName() {
        return "column";
    }

    public SequenceNextValueFunction getDefaultValueSequenceNext() {
        return this.defaultValueSequenceNext;
    }

    public ColumnConfig setDefaultValueSequenceNext(SequenceNextValueFunction defaultValueSequenceNext) {
        this.defaultValueSequenceNext = defaultValueSequenceNext;
        return this;
    }

    public String getDefaultValueConstraintName() {
        return this.defaultValueConstraintName;
    }

    public void setDefaultValueConstraintName(String defaultValueConstraintName) {
        this.defaultValueConstraintName = defaultValueConstraintName;
    }

    @Override
    public LiquibaseSerializable.SerializationType getSerializableFieldType(String field2) {
        return LiquibaseSerializable.SerializationType.NAMED_FIELD;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        String defaultValueSequenceNextString;
        String valueSequenceCurrentString;
        String valueSequenceNextString;
        for (ParsedNode child : parsedNode.getChildren()) {
            if (ObjectUtil.hasProperty(this, child.getName())) continue;
            throw new ParsedNodeException("Unexpected node: " + child.getName());
        }
        this.name = parsedNode.getChildValue((String)null, "name", String.class);
        this.computed = parsedNode.getChildValue((String)null, "computed", Boolean.class);
        this.type = parsedNode.getChildValue((String)null, "type", String.class);
        this.encoding = parsedNode.getChildValue((String)null, "encoding", String.class);
        this.autoIncrement = parsedNode.getChildValue((String)null, "autoIncrement", Boolean.class);
        this.startWith = parsedNode.getChildValue((String)null, "startWith", BigInteger.class);
        this.incrementBy = parsedNode.getChildValue((String)null, "incrementBy", BigInteger.class);
        this.remarks = parsedNode.getChildValue((String)null, "remarks", String.class);
        this.descending = parsedNode.getChildValue((String)null, "descending", Boolean.class);
        this.value = parsedNode.getChildValue((String)null, "value", String.class);
        if (this.value == null) {
            this.value = StringUtil.trimToNull((String)parsedNode.getValue());
        }
        this.setValueNumeric(parsedNode.getChildValue((String)null, "valueNumeric", String.class));
        try {
            this.valueDate = parsedNode.getChildValue((String)null, "valueDate", Date.class);
        }
        catch (ParsedNodeException e) {
            this.valueComputed = new DatabaseFunction(parsedNode.getChildValue((String)null, "valueDate", String.class));
        }
        this.valueBoolean = parsedNode.getChildValue((String)null, "valueBoolean", Boolean.class);
        this.valueBlobFile = parsedNode.getChildValue((String)null, "valueBlobFile", String.class);
        this.valueClobFile = parsedNode.getChildValue((String)null, "valueClobFile", String.class);
        String valueComputedString = parsedNode.getChildValue((String)null, "valueComputed", String.class);
        if (valueComputedString != null) {
            this.valueComputed = new DatabaseFunction(valueComputedString);
        }
        if ((valueSequenceNextString = parsedNode.getChildValue((String)null, "valueSequenceNext", String.class)) != null) {
            this.valueSequenceNext = new SequenceNextValueFunction(valueSequenceNextString);
        }
        if ((valueSequenceCurrentString = parsedNode.getChildValue((String)null, "valueSequenceCurrent", String.class)) != null) {
            this.valueSequenceCurrent = new SequenceCurrentValueFunction(valueSequenceCurrentString);
        }
        this.defaultValueConstraintName = parsedNode.getChildValue((String)null, "defaultValueConstraintName", String.class);
        this.defaultValue = parsedNode.getChildValue((String)null, "defaultValue", String.class);
        this.setDefaultValueNumeric(parsedNode.getChildValue((String)null, "defaultValueNumeric", String.class));
        try {
            this.defaultValueDate = parsedNode.getChildValue((String)null, "defaultValueDate", Date.class);
        }
        catch (ParsedNodeException e) {
            this.defaultValueComputed = new DatabaseFunction(parsedNode.getChildValue((String)null, "defaultValueDate", String.class));
        }
        this.defaultValueBoolean = parsedNode.getChildValue((String)null, "defaultValueBoolean", Boolean.class);
        String defaultValueComputedString = parsedNode.getChildValue((String)null, "defaultValueComputed", String.class);
        if (defaultValueComputedString != null) {
            this.defaultValueComputed = new DatabaseFunction(defaultValueComputedString);
        }
        if ((defaultValueSequenceNextString = parsedNode.getChildValue((String)null, "defaultValueSequenceNext", String.class)) != null) {
            this.defaultValueSequenceNext = new SequenceNextValueFunction(defaultValueSequenceNextString);
        }
        this.defaultOnNull = parsedNode.getChildValue((String)null, "defaultOnNull", Boolean.class);
        this.generationType = parsedNode.getChildValue((String)null, "generationType", String.class);
        this.loadConstraints(parsedNode.getChild(null, "constraints"));
    }

    protected void loadConstraints(ParsedNode constraintsNode) throws ParsedNodeException {
        if (constraintsNode == null) {
            return;
        }
        ConstraintsConfig constraints = new ConstraintsConfig();
        constraints.setNullable(constraintsNode.getChildValue((String)null, "nullable", Boolean.class));
        constraints.setNotNullConstraintName(constraintsNode.getChildValue((String)null, "notNullConstraintName", String.class));
        constraints.setPrimaryKey(constraintsNode.getChildValue((String)null, "primaryKey", Boolean.class));
        constraints.setPrimaryKeyName(constraintsNode.getChildValue((String)null, "primaryKeyName", String.class));
        constraints.setPrimaryKeyTablespace(constraintsNode.getChildValue((String)null, "primaryKeyTablespace", String.class));
        constraints.setReferences(constraintsNode.getChildValue((String)null, "references", String.class));
        constraints.setReferencedTableCatalogName(constraintsNode.getChildValue((String)null, "referencedTableCatalogName", String.class));
        constraints.setReferencedTableSchemaName(constraintsNode.getChildValue((String)null, "referencedTableSchemaName", String.class));
        constraints.setReferencedTableName(constraintsNode.getChildValue((String)null, "referencedTableName", String.class));
        constraints.setReferencedColumnNames(constraintsNode.getChildValue((String)null, "referencedColumnNames", String.class));
        constraints.setUnique(constraintsNode.getChildValue((String)null, "unique", Boolean.class));
        constraints.setUniqueConstraintName(constraintsNode.getChildValue((String)null, "uniqueConstraintName", String.class));
        constraints.setCheckConstraint(constraintsNode.getChildValue((String)null, "checkConstraint", String.class));
        constraints.setDeleteCascade(constraintsNode.getChildValue((String)null, "deleteCascade", Boolean.class));
        constraints.setForeignKeyName(constraintsNode.getChildValue((String)null, "foreignKeyName", String.class));
        constraints.setInitiallyDeferred(constraintsNode.getChildValue((String)null, "initiallyDeferred", Boolean.class));
        constraints.setDeferrable(constraintsNode.getChildValue((String)null, "deferrable", Boolean.class));
        constraints.setValidateNullable(constraintsNode.getChildValue((String)null, "validateNullable", Boolean.class));
        constraints.setValidateUnique(constraintsNode.getChildValue((String)null, "validateUnique", Boolean.class));
        constraints.setValidatePrimaryKey(constraintsNode.getChildValue((String)null, "validatePrimaryKey", Boolean.class));
        constraints.setValidateForeignKey(constraintsNode.getChildValue((String)null, "validateForeignKey", Boolean.class));
        this.setConstraints(constraints);
    }

    @Override
    public Object getSerializableFieldValue(String field2) {
        Object o = ReflectionSerializer.getInstance().getValue(this, field2);
        if (field2.equals("valueDate") || field2.equals("defaultValueDate")) {
            return new ISODateFormat().format((Date)o);
        }
        return o;
    }

    public static class ValueNumeric
    extends Number {
        private static final long serialVersionUID = 1381154777956917462L;
        private final Number delegate;
        private final String value;

        public ValueNumeric(String value, Number numeric) {
            this.delegate = numeric;
            this.value = value;
        }

        private static ValueNumeric of(Locale locale, String value) throws ParseException {
            Number parsedNumber = NumberFormat.getInstance(locale).parse(value);
            return new ValueNumeric(value, parsedNumber);
        }

        @Override
        public double doubleValue() {
            return this.delegate.doubleValue();
        }

        @Override
        public float floatValue() {
            return this.delegate.floatValue();
        }

        @Override
        public int intValue() {
            return this.delegate.intValue();
        }

        @Override
        public long longValue() {
            return this.delegate.longValue();
        }

        public String toString() {
            return this.value;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Number)) {
                return false;
            }
            return obj.toString().equals(this.toString());
        }

        public int hashCode() {
            return this.toString().hashCode();
        }

        public Number getDelegate() {
            return this.delegate;
        }
    }
}

