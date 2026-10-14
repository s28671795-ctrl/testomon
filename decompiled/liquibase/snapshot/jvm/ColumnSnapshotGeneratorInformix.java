/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot.jvm;

import java.util.HashMap;
import java.util.Map;
import liquibase.database.Database;
import liquibase.database.core.InformixDatabase;
import liquibase.exception.DatabaseException;
import liquibase.snapshot.CachedRow;
import liquibase.snapshot.SnapshotGenerator;
import liquibase.snapshot.jvm.ColumnSnapshotGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.DataType;

public class ColumnSnapshotGeneratorInformix
extends ColumnSnapshotGenerator {
    private static final Map<Integer, String> qualifiers = new HashMap<Integer, String>();

    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (database instanceof InformixDatabase) {
            return 5;
        }
        return -1;
    }

    @Override
    public Class<? extends SnapshotGenerator>[] replaces() {
        return new Class[]{ColumnSnapshotGenerator.class};
    }

    @Override
    protected DataType readDataType(CachedRow columnMetadataResultSet, Column column, Database database) throws DatabaseException {
        String typeName = columnMetadataResultSet.getString("TYPE_NAME").toUpperCase();
        if ("DATETIME".equals(typeName) || "INTERVAL".equals(typeName)) {
            int colLength = columnMetadataResultSet.getInt("COLUMN_SIZE");
            int firstQualifierType = colLength % 256 / 16;
            int lastQualifierType = colLength % 256 % 16;
            String type = "DATETIME".equals(typeName) ? "DATETIME" : "INTERVAL";
            String firstQualifier = qualifiers.get(firstQualifierType);
            String lastQualifier = qualifiers.get(lastQualifierType);
            if (firstQualifier == null) {
                throw new DatabaseException(String.format("Encountered unknown firstQualifier code (%d) for column '%s', basic date type '%s', while trying to decipher information encoded in the column length (%d)", firstQualifierType, column.toString(), typeName, colLength));
            }
            if (lastQualifier == null) {
                throw new DatabaseException(String.format("Encountered unknown lastQualifier code (%d) for column '%s', basic date type '%s', while trying to decipher information encoded in the column length (%d)", firstQualifierType, column.toString(), typeName, colLength));
            }
            DataType dataTypeMetaData = new DataType(type + " " + firstQualifier + " TO " + lastQualifier);
            dataTypeMetaData.setColumnSizeUnit(DataType.ColumnSizeUnit.BYTE);
            return dataTypeMetaData;
        }
        return super.readDataType(columnMetadataResultSet, column, database);
    }

    static {
        qualifiers.put(0, "YEAR");
        qualifiers.put(2, "MONTH");
        qualifiers.put(4, "DAY");
        qualifiers.put(6, "HOUR");
        qualifiers.put(8, "MINUTE");
        qualifiers.put(10, "SECOND");
        qualifiers.put(11, "FRACTION(1)");
        qualifiers.put(12, "FRACTION(2)");
        qualifiers.put(13, "FRACTION(3)");
        qualifiers.put(14, "FRACTION(4)");
        qualifiers.put(15, "FRACTION(5)");
    }
}

