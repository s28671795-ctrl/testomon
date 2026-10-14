/*
 * Decompiled with CFR 0.152.
 */
package liquibase.structure.core;

import liquibase.serializer.AbstractLiquibaseSerializable;

public class DataType
extends AbstractLiquibaseSerializable {
    private String typeName;
    private Integer dataTypeId;
    private Integer columnSize;
    private ColumnSizeUnit columnSizeUnit;
    private Integer decimalDigits;
    private Integer radix;
    private Integer characterOctetLength;

    public DataType() {
    }

    public DataType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getDataTypeId() {
        return this.dataTypeId;
    }

    public void setDataTypeId(Integer dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public Integer getColumnSize() {
        return this.columnSize;
    }

    public void setColumnSize(Integer columnSize) {
        this.columnSize = columnSize;
    }

    public ColumnSizeUnit getColumnSizeUnit() {
        return this.columnSizeUnit;
    }

    public void setColumnSizeUnit(ColumnSizeUnit columnSizeUnit) {
        this.columnSizeUnit = columnSizeUnit;
    }

    public Integer getDecimalDigits() {
        return this.decimalDigits;
    }

    public void setDecimalDigits(Integer decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String toString() {
        String subtypeData = null;
        String value = this.typeName;
        if (value == null) {
            return value;
        }
        if (value.contains("FOR BIT DATA")) {
            value = this.typeName.replaceAll("\\(.*", "");
            subtypeData = " FOR BIT DATA";
        } else if (value.contains("FOR SBCS DATA")) {
            value = this.typeName.replaceAll("\\(.*", "");
            subtypeData = " FOR SBCS DATA";
        } else if (value.contains("FOR MIXED DATA")) {
            value = this.typeName.replaceAll("\\(.*", "");
            subtypeData = " FOR MIXED DATA";
        }
        boolean unsigned = false;
        if (value.toLowerCase().endsWith(" unsigned")) {
            value = value.substring(0, value.length() - " unsigned".length());
            unsigned = true;
        }
        if (this.columnSize == null) {
            if (this.decimalDigits != null) {
                value = value + "(*, " + this.decimalDigits + ")";
            }
        } else if (subtypeData != null) {
            value = value + "(";
            value = value + this.columnSize;
            if (this.columnSizeUnit != null && (this.typeName.equalsIgnoreCase("VARCHAR") || this.typeName.equalsIgnoreCase("VARCHAR2") || this.typeName.equalsIgnoreCase("CHAR"))) {
                value = value + " " + (Object)((Object)this.columnSizeUnit);
            }
            value = value + ")";
            value = value + subtypeData;
        } else {
            value = value + "(";
            value = value + this.columnSize;
            if (this.decimalDigits != null) {
                value = value + ", " + this.decimalDigits;
            }
            if (this.columnSizeUnit != null && ("VARCHAR".equalsIgnoreCase(this.typeName) || "VARCHAR2".equalsIgnoreCase(this.typeName) || "CHAR".equalsIgnoreCase(this.typeName))) {
                value = value + " " + (Object)((Object)this.columnSizeUnit);
            }
            value = value + ")";
        }
        if (unsigned) {
            value = value + " UNSIGNED";
        }
        return value;
    }

    public Integer getRadix() {
        return this.radix;
    }

    public void setRadix(Integer radix) {
        this.radix = radix;
    }

    public Integer getCharacterOctetLength() {
        return this.characterOctetLength;
    }

    public void setCharacterOctetLength(Integer characterOctetLength) {
        this.characterOctetLength = characterOctetLength;
    }

    @Override
    public String getSerializedObjectName() {
        return "dataType";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/snapshot";
    }

    public static enum ColumnSizeUnit {
        BYTE,
        CHAR;

    }
}

