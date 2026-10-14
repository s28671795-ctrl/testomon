/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Type;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;

public enum R2dbcType implements Type
{
    CHAR(String.class),
    VARCHAR(String.class),
    NCHAR(String.class),
    NVARCHAR(String.class),
    CLOB(String.class),
    NCLOB(String.class),
    BOOLEAN(Boolean.class),
    BINARY(ByteBuffer.class),
    VARBINARY(ByteBuffer.class),
    BLOB(ByteBuffer.class),
    INTEGER(Integer.class),
    TINYINT(Byte.class),
    SMALLINT(Short.class),
    BIGINT(Long.class),
    NUMERIC(BigDecimal.class),
    DECIMAL(BigDecimal.class),
    FLOAT(Double.class),
    REAL(Float.class),
    DOUBLE(Double.class),
    DATE(LocalDate.class),
    TIME(LocalTime.class),
    TIME_WITH_TIME_ZONE(OffsetTime.class),
    TIMESTAMP(LocalDateTime.class),
    TIMESTAMP_WITH_TIME_ZONE(OffsetDateTime.class),
    COLLECTION(Object[].class);

    private final Class<?> javaType;

    private R2dbcType(Class<?> javaType) {
        this.javaType = javaType;
    }

    @Override
    public Class<?> getJavaType() {
        return this.javaType;
    }

    @Override
    public String getName() {
        return this.name();
    }
}

