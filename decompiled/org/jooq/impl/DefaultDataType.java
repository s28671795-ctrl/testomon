/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLType;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.jetbrains.annotations.ApiStatus;
import org.jooq.ArrayRecord;
import org.jooq.Binding;
import org.jooq.CharacterSet;
import org.jooq.Collation;
import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.Generator;
import org.jooq.Name;
import org.jooq.Nullability;
import org.jooq.QualifiedRecord;
import org.jooq.SQLDialect;
import org.jooq.exception.MappingException;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.impl.AbstractDataType;
import org.jooq.impl.AbstractDataTypeX;
import org.jooq.impl.BuiltInDataType;
import org.jooq.impl.CommentImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultBinding;
import org.jooq.impl.DefaultGenerator;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.reflect.Reflect;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.jooq.types.UShort;

@ApiStatus.Internal
public class DefaultDataType<T>
extends AbstractDataTypeX<T> {
    private static final Set<SQLDialect> ENCODED_TIMESTAMP_PRECISION = SQLDialect.supportedBy(SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB);
    private static final Set<SQLDialect> NO_SUPPORT_TIMESTAMP_PRECISION = SQLDialect.supportedBy(SQLDialect.FIREBIRD, SQLDialect.MYSQL, SQLDialect.SQLITE);
    private static final Set<SQLDialect> SUPPORT_POSTGRES_ARRAY_NOTATION = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> SUPPORT_HSQLDB_ARRAY_NOTATION = SQLDialect.supportedBy(SQLDialect.H2, SQLDialect.HSQLDB);
    private static final Pattern NORMALISE_PATTERN = Pattern.compile("\"|\\.|\\s|\\(\\w+(\\s*,\\s*\\w+)*\\)|(NOT\\s*NULL)?");
    private static final Pattern TYPE_NAME_PATTERN = Pattern.compile("\\([^)]*\\)");
    private static final Map<String, DefaultDataType<?>>[] TYPES_BY_NAME;
    private static final Map<Class<?>, DefaultDataType<?>>[] TYPES_BY_TYPE;
    private static final Map<DataType<?>, DefaultDataType<?>>[] TYPES_BY_SQL_DATATYPE;
    private static final Map<Class<?>, DefaultDataType<?>> SQL_DATATYPES_BY_TYPE;
    static final int LONG_PRECISION;
    static final int INTEGER_PRECISION;
    static final int SHORT_PRECISION;
    static final int BYTE_PRECISION;
    private final SQLDialect dialect;
    private final DataType<T> sqlDataType;
    private final Class<T> uType;
    private final Class<?> tType;
    private final Binding<?, T> binding;
    private final String castTypeName;
    private final String castTypePrefix;
    private final String castTypeSuffix;
    private final String typeName;
    private final Nullability nullability;
    private final boolean readonly;
    private final Generator<?, ?, T> generatedAlwaysAs;
    private final QOM.GenerationOption generationOption;
    private final QOM.GenerationLocation generationLocation;
    private final Collation collation;
    private final CharacterSet characterSet;
    private final boolean identity;
    private final Field<T> defaultValue;
    private final Integer precision;
    private final Integer scale;
    private final Integer length;

    public DefaultDataType(SQLDialect dialect, DataType<T> sqlDataType, String typeName) {
        this(dialect, sqlDataType, typeName, null);
    }

    public DefaultDataType(SQLDialect dialect, DataType<T> sqlDataType, String typeName, String castTypeName) {
        this(dialect, sqlDataType, sqlDataType.getType(), sqlDataType.getQualifiedName(), typeName, castTypeName, sqlDataType.precisionDefined() ? Integer.valueOf(sqlDataType.precision()) : null, sqlDataType.scaleDefined() ? Integer.valueOf(sqlDataType.scale()) : null, sqlDataType.lengthDefined() ? Integer.valueOf(sqlDataType.length()) : null, sqlDataType.nullability(), sqlDataType.defaultValue());
    }

    public DefaultDataType(SQLDialect dialect, Class<T> type, String typeName) {
        this(dialect, null, type, DSL.systemName(typeName), typeName, null, null, null, null, Nullability.DEFAULT, null);
    }

    public DefaultDataType(SQLDialect dialect, Class<T> type, String typeName, String castTypeName) {
        this(dialect, null, type, DSL.systemName(typeName), typeName, castTypeName, null, null, null, Nullability.DEFAULT, null);
    }

    DefaultDataType(SQLDialect dialect, Class<T> type, Name qualifiedTypeName) {
        this(dialect, null, type, qualifiedTypeName, null, null, null, null, null, Nullability.DEFAULT, null);
    }

    DefaultDataType(SQLDialect dialect, Class<T> type, Binding<?, T> binding, Name qualifiedTypeName, String typeName, String castTypeName, Integer precision, Integer scale, Integer length, Nullability nullability, Field<T> defaultValue) {
        this(dialect, null, type, binding, qualifiedTypeName, typeName, castTypeName, precision, scale, length, nullability, defaultValue);
    }

    DefaultDataType(SQLDialect dialect, DataType<T> sqlDataType, Class<T> type, Name qualifiedTypeName, String typeName, String castTypeName, Integer precision, Integer scale, Integer length, Nullability nullability, Field<T> defaultValue) {
        this(dialect, sqlDataType, type, null, qualifiedTypeName, typeName, castTypeName, precision, scale, length, nullability, defaultValue);
    }

    DefaultDataType(SQLDialect dialect, DataType<T> sqlDataType, Class<T> type, Binding<?, T> binding, Name qualifiedTypeName, String typeName, String castTypeName, Integer precision, Integer scale, Integer length, Nullability nullability, Field<T> defaultValue) {
        this(dialect, sqlDataType, type, binding, qualifiedTypeName, typeName, castTypeName, precision, scale, length, nullability, false, null, QOM.GenerationOption.DEFAULT, QOM.GenerationLocation.SERVER, null, null, false, defaultValue);
    }

    DefaultDataType(SQLDialect dialect, DataType<T> sqlDataType, Class<T> type, Binding<?, T> binding, Name qualifiedTypeName, String typeName, String castTypeName, Integer precision, Integer scale, Integer length, Nullability nullability, boolean readonly, Generator<?, ?, T> generatedAlwaysAs, QOM.GenerationOption generationOption, QOM.GenerationLocation generationLocation, Collation collation, CharacterSet characterSet, boolean identity, Field<T> defaultValue) {
        super(qualifiedTypeName, CommentImpl.NO_COMMENT);
        int ordinal;
        if (typeName == null) {
            typeName = qualifiedTypeName.toString();
        }
        this.dialect = dialect;
        this.sqlDataType = dialect == null && sqlDataType == null ? this : sqlDataType;
        this.uType = type;
        this.typeName = TYPE_NAME_PATTERN.matcher(typeName).replaceAll("").trim();
        this.castTypeName = castTypeName == null ? this.typeName : castTypeName;
        String[] split = TYPE_NAME_PATTERN.split(castTypeName == null ? typeName : castTypeName);
        this.castTypePrefix = split.length > 0 ? split[0] : "";
        this.castTypeSuffix = split.length > 1 ? split[1] : "";
        this.nullability = nullability == null ? Nullability.DEFAULT : nullability;
        this.readonly = readonly;
        this.generatedAlwaysAs = generatedAlwaysAs;
        this.generationOption = generationOption == null ? QOM.GenerationOption.DEFAULT : generationOption;
        this.generationLocation = generationLocation == null ? QOM.GenerationLocation.SERVER : generationLocation;
        this.collation = collation;
        this.characterSet = characterSet;
        this.identity = identity;
        this.defaultValue = defaultValue;
        this.precision = DefaultDataType.integerPrecision(type, precision);
        this.scale = scale;
        this.length = length;
        int n = ordinal = dialect == null ? SQLDialect.DEFAULT.ordinal() : dialect.family().ordinal();
        if (!TYPES_BY_NAME[ordinal].containsKey(typeName.toUpperCase())) {
            TYPES_BY_NAME[ordinal].putIfAbsent(DefaultDataType.normalise(typeName), this);
        }
        TYPES_BY_TYPE[ordinal].putIfAbsent(type, this);
        if (sqlDataType != null) {
            TYPES_BY_SQL_DATATYPE[ordinal].putIfAbsent(sqlDataType, this);
        }
        if (dialect == null) {
            SQL_DATATYPES_BY_TYPE.putIfAbsent(type, this);
        }
        this.binding = binding != null ? binding : DefaultBinding.binding(this);
        this.tType = this.binding.converter().fromType();
        if (this instanceof BuiltInDataType && !this.tType.isArray()) {
            this.getArrayDataType();
        }
    }

    @Override
    DefaultDataType<T> construct(Integer newPrecision, Integer newScale, Integer newLength, Nullability newNullability, boolean newReadonly, Generator<?, ?, T> newGeneratedAlwaysAs, QOM.GenerationOption newGenerationOption, QOM.GenerationLocation newGenerationLocation, Collation newCollation, CharacterSet newCharacterSet, boolean newIdentity, Field<T> newDefaultValue) {
        return new DefaultDataType<T>(this, newPrecision, newScale, newLength, newNullability, newReadonly, newGeneratedAlwaysAs, newGenerationOption, newGenerationLocation, newCollation, newCharacterSet, newIdentity, newDefaultValue);
    }

    DefaultDataType(AbstractDataType<T> t2, Integer precision, Integer scale, Integer length, Nullability nullability, boolean readonly, Generator<?, ?, T> generatedAlwaysAs, QOM.GenerationOption generationOption, QOM.GenerationLocation generationLocation, Collation collation, CharacterSet characterSet, boolean identity, Field<T> defaultValue) {
        super(t2.getQualifiedName(), CommentImpl.NO_COMMENT);
        this.dialect = t2.getDialect();
        this.sqlDataType = t2.getSQLDataType();
        this.uType = t2.uType0();
        this.tType = t2.tType0();
        this.typeName = t2.typeName0();
        this.castTypeName = t2.castTypeName0();
        this.castTypePrefix = t2.castTypePrefix0();
        this.castTypeSuffix = t2.castTypeSuffix0();
        this.nullability = nullability;
        this.readonly = readonly;
        this.generatedAlwaysAs = generatedAlwaysAs;
        this.generationOption = generationOption;
        this.generationLocation = generationLocation;
        this.collation = collation;
        this.characterSet = characterSet;
        this.identity = identity;
        this.defaultValue = defaultValue;
        this.precision = DefaultDataType.integerPrecision(this.uType, precision);
        this.scale = scale;
        this.length = length;
        this.binding = t2.getBinding() instanceof DefaultBinding.InternalBinding ? DefaultBinding.binding(this, t2.getBinding().converter()) : t2.getBinding();
    }

    private static final Integer integerPrecision(Class<?> type, Integer precision) {
        if (precision == null) {
            if (type == Long.class || type == ULong.class) {
                precision = LONG_PRECISION;
            } else if (type == Integer.class || type == UInteger.class) {
                precision = INTEGER_PRECISION;
            } else if (type == Short.class || type == UShort.class) {
                precision = SHORT_PRECISION;
            } else if (type == Byte.class || type == UByte.class) {
                precision = BYTE_PRECISION;
            }
        }
        return precision;
    }

    @Override
    public final Nullability nullability() {
        return this.nullability;
    }

    @Override
    public final boolean readonly() {
        return this.readonly;
    }

    @Override
    public final Generator<?, ?, T> generatedAlwaysAsGenerator() {
        return this.generatedAlwaysAs == null ? null : (this.generatedAlwaysAs instanceof DefaultGenerator ? this.generatedAlwaysAs : new DefaultGenerator(this.generatedAlwaysAs));
    }

    @Override
    public final QOM.GenerationOption generationOption() {
        return this.generationOption;
    }

    @Override
    public final QOM.GenerationLocation generationLocation() {
        return this.generationLocation;
    }

    @Override
    public final Collation collation() {
        return this.collation;
    }

    @Override
    public final CharacterSet characterSet() {
        return this.characterSet;
    }

    @Override
    public final boolean identity() {
        return this.identity;
    }

    @Override
    public final Field<T> default_() {
        return this.defaultValue;
    }

    @Override
    final Integer precision0() {
        return this.precision;
    }

    @Override
    final Integer scale0() {
        return this.scale;
    }

    @Override
    final Integer length0() {
        return this.length;
    }

    @Override
    public final DataType<T> getSQLDataType() {
        return this.sqlDataType;
    }

    @Override
    public final DataType<T> getDataType(Configuration configuration) {
        if (this.getDialect() == null) {
            DefaultDataType<?> dataType = TYPES_BY_SQL_DATATYPE[configuration.family().ordinal()].get(this.length0(null).precision0(null, null));
            if (dataType != null) {
                return dataType.construct(this.precision, this.scale, this.length, this.nullability, this.readonly, (Generator)this.generatedAlwaysAs, this.generationOption, this.generationLocation, this.collation, this.characterSet, this.identity, (Field)this.defaultValue);
            }
        } else {
            if (this.getDialect().family() == configuration.family()) {
                return this;
            }
            if (this.getSQLDataType() == null) {
                return this;
            }
            return this.getSQLDataType().getDataType(configuration);
        }
        return this;
    }

    @Override
    public final Class<T> getType() {
        return this.uType;
    }

    @Override
    public final Binding<?, T> getBinding() {
        return this.binding;
    }

    @Override
    final String typeName0() {
        return this.typeName;
    }

    @Override
    final String castTypePrefix0() {
        return this.castTypePrefix;
    }

    @Override
    final String castTypeSuffix0() {
        return this.castTypeSuffix;
    }

    @Override
    final String castTypeName0() {
        return this.castTypeName;
    }

    @Override
    final Class<?> tType0() {
        return this.tType;
    }

    @Override
    final Class<T> uType0() {
        return this.uType;
    }

    @Override
    public final SQLDialect getDialect() {
        return this.dialect;
    }

    public static final DataType<Object> getDefaultDataType(String typeName) {
        return new DefaultDataType<Object>(SQLDialect.DEFAULT, Object.class, typeName, typeName);
    }

    public static final DataType<Object> getDefaultDataType(SQLDialect dialect, String typeName) {
        return new DefaultDataType<Object>(dialect, Object.class, typeName, typeName);
    }

    public static final DataType<?> getDataType(SQLDialect dialect, String typeName) {
        if (dialect == null) {
            dialect = SQLDialect.DEFAULT;
        }
        SQLDialect family = dialect.family();
        int ordinal = family.ordinal();
        String upper = typeName.toUpperCase();
        String normalised = typeName;
        DataType<Object[]> result = (DataType<Object[]>)TYPES_BY_NAME[ordinal].get(upper);
        if (result == null && (result = (DataType)TYPES_BY_NAME[ordinal].get(normalised = DefaultDataType.normalise(typeName))) == null) {
            result = TYPES_BY_NAME[SQLDialect.DEFAULT.ordinal()].get(normalised);
            if (result == null && "INT".equals(normalised)) {
                result = TYPES_BY_NAME[SQLDialect.DEFAULT.ordinal()].get("INTEGER");
            } else if (result == null && SUPPORT_POSTGRES_ARRAY_NOTATION.contains((Object)dialect) && normalised.charAt(0) == '_') {
                result = DefaultDataType.getDataType(dialect, normalised.substring(1)).getArrayDataType();
            } else if (result == null && SUPPORT_HSQLDB_ARRAY_NOTATION.contains((Object)dialect) && upper.endsWith(" ARRAY")) {
                result = DefaultDataType.getDataType(dialect, typeName.substring(0, typeName.length() - 6)).getArrayDataType();
            } else if (result == null && SUPPORT_HSQLDB_ARRAY_NOTATION.contains((Object)dialect) && upper.equals("ARRAY")) {
                result = SQLDataType.OTHER.getArrayDataType();
            } else if (result == null && family == SQLDialect.COCKROACHDB && normalised.endsWith("[]")) {
                result = DefaultDataType.getDataType(dialect, typeName.substring(0, typeName.length() - 2)).getArrayDataType();
            }
            if (result == null) {
                throw new SQLDialectNotSupportedException("Type " + typeName + " is not supported in dialect " + String.valueOf((Object)dialect), false);
            }
        }
        return result;
    }

    public static final DataType<?> getDataType(SQLDialect dialect, SQLType sqlType) {
        Integer i = sqlType.getVendorTypeNumber();
        return i == null ? SQLDataType.OTHER : DefaultDataType.getDataType(dialect, i);
    }

    public static final DataType<?> getDataType(SQLDialect dialect, int sqlType) {
        switch (sqlType) {
            case -5: {
                return SQLDataType.BIGINT;
            }
            case -2: {
                return SQLDataType.BINARY;
            }
            case -7: {
                return SQLDataType.BIT;
            }
            case 2004: {
                return SQLDataType.BLOB;
            }
            case 16: {
                return SQLDataType.BOOLEAN;
            }
            case 1: {
                return SQLDataType.CHAR;
            }
            case 2005: {
                return SQLDataType.CLOB;
            }
            case 91: {
                return SQLDataType.DATE;
            }
            case 3: {
                return SQLDataType.DECIMAL;
            }
            case 8: {
                return SQLDataType.DOUBLE;
            }
            case 6: {
                return SQLDataType.FLOAT;
            }
            case 4: {
                return SQLDataType.INTEGER;
            }
            case -16: {
                return SQLDataType.LONGNVARCHAR;
            }
            case -4: {
                return SQLDataType.LONGVARBINARY;
            }
            case -1: {
                return SQLDataType.LONGVARCHAR;
            }
            case -15: {
                return SQLDataType.NCHAR;
            }
            case 2011: {
                return SQLDataType.NCLOB;
            }
            case 2: {
                return SQLDataType.NUMERIC;
            }
            case -9: {
                return SQLDataType.NVARCHAR;
            }
            case 7: {
                return SQLDataType.REAL;
            }
            case 2012: {
                return SQLDataType.RESULT;
            }
            case 5: {
                return SQLDataType.SMALLINT;
            }
            case 2009: {
                return SQLDataType.XML;
            }
            case 2002: {
                return SQLDataType.RECORD;
            }
            case 92: {
                return SQLDataType.TIME;
            }
            case 2013: {
                return SQLDataType.TIMEWITHTIMEZONE;
            }
            case 93: {
                return SQLDataType.TIMESTAMP;
            }
            case 2014: {
                return SQLDataType.TIMESTAMPWITHTIMEZONE;
            }
            case -6: {
                return SQLDataType.TINYINT;
            }
            case -3: {
                return SQLDataType.VARBINARY;
            }
            case 12: {
                return SQLDataType.VARCHAR;
            }
        }
        return SQLDataType.OTHER;
    }

    public static final <T> DataType<T> getDataType(SQLDialect dialect, Class<T> type) {
        return DefaultDataType.getDataType(dialect, type, null);
    }

    public static final <T> DataType<T> getDataType(SQLDialect dialect, Class<T> type, DataType<T> fallbackDataType) {
        if (byte[].class != (type = Reflect.wrapper(type)) && type.isArray()) {
            return DefaultDataType.getDataType(dialect, type.getComponentType()).getArrayDataType();
        }
        DataType result = null;
        if (dialect != null) {
            result = TYPES_BY_TYPE[dialect.family().ordinal()].get(type);
        }
        if (result == null) {
            try {
                if (QualifiedRecord.class.isAssignableFrom(type)) {
                    return Tools.getRecordQualifier(type).getDataType();
                }
                if (ArrayRecord.class.isAssignableFrom(type)) {
                    return ((ArrayRecord)type.getDeclaredConstructor(new Class[0]).newInstance(new Object[0])).getArrayType();
                }
                if (EnumType.class.isAssignableFrom(type)) {
                    return SQLDataType.VARCHAR.asEnumDataType(type);
                }
            }
            catch (Exception e) {
                throw new MappingException("Cannot create instance of " + String.valueOf(type), e);
            }
        }
        if (result == null) {
            if (SQL_DATATYPES_BY_TYPE.get(type) != null) {
                return SQL_DATATYPES_BY_TYPE.get(type);
            }
            if (fallbackDataType != null) {
                return fallbackDataType;
            }
            if (Date.class == type) {
                return SQLDataType.TIMESTAMP;
            }
            throw new SQLDialectNotSupportedException("Type " + String.valueOf(type) + " is not supported in dialect " + String.valueOf((Object)dialect));
        }
        return result;
    }

    public static final String normalise(String typeName) {
        return NORMALISE_PATTERN.matcher(typeName.toUpperCase()).replaceAll("");
    }

    public static final DataType<?> getDataType(SQLDialect dialect, String t2, int p, int s2) throws SQLDialectNotSupportedException {
        return DefaultDataType.getDataType(dialect, t2, p, s2, true);
    }

    public static final DataType<?> getDataType(SQLDialect dialect, String t2, int p, int s2, boolean forceIntegerTypesOnZeroScaleDecimals) throws SQLDialectNotSupportedException {
        DataType<Object> result = DefaultDataType.getDataType(dialect, t2);
        boolean array = result.isArray();
        if (array) {
            result = result.getArrayComponentDataType();
        }
        if (forceIntegerTypesOnZeroScaleDecimals && result.getType() == BigDecimal.class) {
            result = DefaultDataType.getDataType(dialect, DefaultDataType.getNumericClass(p, s2));
        }
        if (result.getSQLDataType() != null) {
            result = result.getSQLDataType();
        }
        if (result.hasPrecision() && result.hasScale()) {
            result = result.precision(p, s2);
        } else if (result.hasPrecision() && result.isDateTime()) {
            if (ENCODED_TIMESTAMP_PRECISION.contains((Object)dialect)) {
                result = result.precision(DefaultDataType.decodeTimestampPrecision(p));
            } else if (!NO_SUPPORT_TIMESTAMP_PRECISION.contains((Object)dialect)) {
                result = result.precision(s2);
            }
        } else if (result.hasPrecision()) {
            result = result.precision(p);
        } else if (result.hasLength()) {
            result = result.length(p);
        }
        if (array) {
            result = result.getArrayDataType();
        }
        return result;
    }

    private static final int decodeTimestampPrecision(int precision) {
        return Math.max(0, precision - 20);
    }

    public static final Class<?> getType(SQLDialect dialect, String t2, int p, int s2) throws SQLDialectNotSupportedException {
        return DefaultDataType.getDataType(dialect, t2, p, s2).getType();
    }

    private static final Class<?> getNumericClass(int precision, int scale) {
        if (scale == 0 && precision != 0) {
            if (precision < BYTE_PRECISION) {
                return Byte.class;
            }
            if (precision < SHORT_PRECISION) {
                return Short.class;
            }
            if (precision < INTEGER_PRECISION) {
                return Integer.class;
            }
            if (precision < LONG_PRECISION) {
                return Long.class;
            }
            return BigInteger.class;
        }
        return BigDecimal.class;
    }

    static final Collection<Class<?>> types() {
        return Collections.unmodifiableCollection(SQL_DATATYPES_BY_TYPE.keySet());
    }

    static final Collection<DefaultDataType<?>> dataTypes() {
        return Collections.unmodifiableCollection(SQL_DATATYPES_BY_TYPE.values());
    }

    static final DataType<?> set(DataType<?> d, Integer l, Integer p, Integer s2) {
        if (l != null) {
            d = d.length(l);
        }
        if (p != null) {
            d = s2 != null ? d.precision(p, s2) : d.precision(p);
        }
        return d;
    }

    static {
        LONG_PRECISION = String.valueOf(Long.MAX_VALUE).length();
        INTEGER_PRECISION = String.valueOf(Integer.MAX_VALUE).length();
        SHORT_PRECISION = String.valueOf(Short.MAX_VALUE).length();
        BYTE_PRECISION = String.valueOf(127).length();
        TYPES_BY_SQL_DATATYPE = new Map[SQLDialect.values().length];
        TYPES_BY_NAME = new Map[SQLDialect.values().length];
        TYPES_BY_TYPE = new Map[SQLDialect.values().length];
        for (SQLDialect dialect : SQLDialect.values()) {
            DefaultDataType.TYPES_BY_SQL_DATATYPE[dialect.ordinal()] = new LinkedHashMap();
            DefaultDataType.TYPES_BY_NAME[dialect.ordinal()] = new LinkedHashMap();
            DefaultDataType.TYPES_BY_TYPE[dialect.ordinal()] = new LinkedHashMap();
        }
        SQL_DATATYPES_BY_TYPE = new LinkedHashMap();
        try {
            Class.forName(SQLDataType.class.getName());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

