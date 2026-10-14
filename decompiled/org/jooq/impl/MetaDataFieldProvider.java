/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Objects;
import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

final class MetaDataFieldProvider
implements Serializable {
    private static final JooqLogger log = JooqLogger.getLogger(MetaDataFieldProvider.class);
    private final FieldsImpl<Record> fields;

    MetaDataFieldProvider(Configuration configuration, ResultSetMetaData meta) {
        this.fields = MetaDataFieldProvider.init(configuration, meta);
    }

    private static FieldsImpl<Record> init(Configuration configuration, ResultSetMetaData meta) {
        Field[] fields;
        int columnCount = 0;
        try {
            columnCount = meta.getColumnCount();
            fields = new Field[columnCount];
        }
        catch (SQLException e) {
            log.info("Cannot fetch column count for cursor : " + e.getMessage());
            fields = new Field[]{DSL.field("dummy")};
        }
        try {
            for (int i = 1; i <= columnCount; ++i) {
                Name name;
                block16: {
                    String columnLabel = meta.getColumnLabel(i);
                    String columnName = meta.getColumnName(i);
                    if (Objects.equals(columnName, columnLabel)) {
                        try {
                            String columnSchema = meta.getSchemaName(i);
                            String columnTable = meta.getTableName(i);
                            if (!StringUtils.isEmpty(columnSchema)) {
                                name = DSL.name(columnSchema, columnTable, columnName);
                                break block16;
                            }
                            if (!StringUtils.isEmpty(columnTable)) {
                                name = DSL.name(columnTable, columnName);
                                break block16;
                            }
                            name = DSL.name(columnName);
                        }
                        catch (SQLException e) {
                            name = DSL.name(columnLabel);
                        }
                    } else {
                        name = DSL.name(columnLabel);
                    }
                }
                int precision = meta.getPrecision(i);
                int scale = meta.getScale(i);
                DataType<Object> dataType = SQLDataType.OTHER;
                String type = meta.getColumnTypeName(i);
                try {
                    dataType = DefaultDataType.getDataType(configuration.family(), type, precision, scale, !Boolean.FALSE.equals(configuration.settings().isForceIntegerTypesOnZeroScaleDecimals()));
                    if (dataType.hasPrecision()) {
                        dataType = dataType.precision(precision);
                    }
                    if (dataType.hasScale()) {
                        dataType = dataType.scale(scale);
                    }
                    if (dataType.hasLength()) {
                        dataType = dataType.length(precision);
                    }
                }
                catch (SQLDialectNotSupportedException e) {
                    log.debug((Object)"Not supported by dialect", e.getMessage());
                }
                fields[i - 1] = DSL.field(name, dataType);
            }
        }
        catch (SQLException e) {
            throw Tools.translate(null, e);
        }
        return new FieldsImpl<Record>(fields);
    }

    final Field<?>[] getFields() {
        return this.fields.fields();
    }

    public String toString() {
        return this.fields.toString();
    }
}

