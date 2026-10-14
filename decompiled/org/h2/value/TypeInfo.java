/*
 * Decompiled with CFR 0.152.
 */
package org.h2.value;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.h2.api.IntervalQualifier;
import org.h2.message.DbException;
import org.h2.value.ExtTypeInfo;
import org.h2.value.ExtTypeInfoEnum;
import org.h2.value.ExtTypeInfoGeometry;
import org.h2.value.ExtTypeInfoNumeric;
import org.h2.value.ExtTypeInfoRow;
import org.h2.value.Typed;
import org.h2.value.Value;
import org.h2.value.ValueInterval;

public class TypeInfo
extends ExtTypeInfo
implements Typed {
    public static final TypeInfo TYPE_UNKNOWN;
    public static final TypeInfo TYPE_NULL;
    public static final TypeInfo TYPE_CHAR;
    public static final TypeInfo TYPE_VARCHAR;
    public static final TypeInfo TYPE_VARCHAR_IGNORECASE;
    public static final TypeInfo TYPE_CLOB;
    public static final TypeInfo TYPE_BINARY;
    public static final TypeInfo TYPE_VARBINARY;
    public static final TypeInfo TYPE_BLOB;
    public static final TypeInfo TYPE_BOOLEAN;
    public static final TypeInfo TYPE_TINYINT;
    public static final TypeInfo TYPE_SMALLINT;
    public static final TypeInfo TYPE_INTEGER;
    public static final TypeInfo TYPE_BIGINT;
    public static final TypeInfo TYPE_NUMERIC_SCALE_0;
    public static final TypeInfo TYPE_NUMERIC_BIGINT;
    public static final TypeInfo TYPE_NUMERIC_FLOATING_POINT;
    public static final TypeInfo TYPE_REAL;
    public static final TypeInfo TYPE_DOUBLE;
    public static final TypeInfo TYPE_DECFLOAT;
    public static final TypeInfo TYPE_DECFLOAT_BIGINT;
    public static final TypeInfo TYPE_DATE;
    public static final TypeInfo TYPE_TIME;
    public static final TypeInfo TYPE_TIME_TZ;
    public static final TypeInfo TYPE_TIMESTAMP;
    public static final TypeInfo TYPE_TIMESTAMP_TZ;
    public static final TypeInfo TYPE_INTERVAL_DAY;
    public static final TypeInfo TYPE_INTERVAL_YEAR_TO_MONTH;
    public static final TypeInfo TYPE_INTERVAL_DAY_TO_SECOND;
    public static final TypeInfo TYPE_INTERVAL_HOUR_TO_SECOND;
    public static final TypeInfo TYPE_JAVA_OBJECT;
    public static final TypeInfo TYPE_ENUM_UNDEFINED;
    public static final TypeInfo TYPE_GEOMETRY;
    public static final TypeInfo TYPE_JSON;
    public static final TypeInfo TYPE_UUID;
    public static final TypeInfo TYPE_ARRAY_UNKNOWN;
    public static final TypeInfo TYPE_ROW_EMPTY;
    private static final TypeInfo[] TYPE_INFOS_BY_VALUE_TYPE;
    private final int valueType;
    private final long precision;
    private final int scale;
    private final ExtTypeInfo extTypeInfo;

    public static TypeInfo getTypeInfo(int n) {
        TypeInfo typeInfo;
        if (n == -1) {
            throw DbException.get(50004, "?");
        }
        if (n >= 0 && n < 42 && (typeInfo = TYPE_INFOS_BY_VALUE_TYPE[n]) != null) {
            return typeInfo;
        }
        return TYPE_NULL;
    }

    public static TypeInfo getTypeInfo(int n, long l, int n2, ExtTypeInfo extTypeInfo) {
        switch (n) {
            case 0: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 17: 
            case 39: {
                return TYPE_INFOS_BY_VALUE_TYPE[n];
            }
            case -1: {
                return TYPE_UNKNOWN;
            }
            case 1: {
                if (l < 1L) {
                    return TYPE_CHAR;
                }
                if (l > 1000000000L) {
                    l = 1000000000L;
                }
                return new TypeInfo(1, l);
            }
            case 2: {
                if (l < 1L || l >= 1000000000L) {
                    if (l != 0L) {
                        return TYPE_VARCHAR;
                    }
                    l = 1L;
                }
                return new TypeInfo(2, l);
            }
            case 3: {
                if (l < 1L) {
                    return TYPE_CLOB;
                }
                return new TypeInfo(3, l);
            }
            case 4: {
                if (l < 1L || l >= 1000000000L) {
                    if (l != 0L) {
                        return TYPE_VARCHAR_IGNORECASE;
                    }
                    l = 1L;
                }
                return new TypeInfo(4, l);
            }
            case 5: {
                if (l < 1L) {
                    return TYPE_BINARY;
                }
                if (l > 1000000000L) {
                    l = 1000000000L;
                }
                return new TypeInfo(5, l);
            }
            case 6: {
                if (l < 1L || l >= 1000000000L) {
                    if (l != 0L) {
                        return TYPE_VARBINARY;
                    }
                    l = 1L;
                }
                return new TypeInfo(6, l);
            }
            case 7: {
                if (l < 1L) {
                    return TYPE_BLOB;
                }
                return new TypeInfo(7, l);
            }
            case 13: {
                if (l < 1L) {
                    l = -1L;
                } else if (l > 100000L) {
                    l = 100000L;
                }
                if (n2 < 0) {
                    n2 = -1;
                } else if (n2 > 100000) {
                    n2 = 100000;
                }
                return new TypeInfo(13, l, n2, extTypeInfo instanceof ExtTypeInfoNumeric ? extTypeInfo : null);
            }
            case 14: {
                if (l >= 1L && l <= 24L) {
                    return new TypeInfo(14, l, -1, extTypeInfo);
                }
                return TYPE_REAL;
            }
            case 15: {
                if (l == 0L || l >= 25L && l <= 53L) {
                    return new TypeInfo(15, l, -1, extTypeInfo);
                }
                return TYPE_DOUBLE;
            }
            case 16: {
                if (l < 1L) {
                    l = -1L;
                } else if (l >= 100000L) {
                    return TYPE_DECFLOAT;
                }
                return new TypeInfo(16, l, -1, null);
            }
            case 18: {
                if (n2 < 0) {
                    n2 = -1;
                } else if (n2 >= 9) {
                    return TYPE_TIME;
                }
                return new TypeInfo(18, n2);
            }
            case 19: {
                if (n2 < 0) {
                    n2 = -1;
                } else if (n2 >= 9) {
                    return TYPE_TIME_TZ;
                }
                return new TypeInfo(19, n2);
            }
            case 20: {
                if (n2 < 0) {
                    n2 = -1;
                } else if (n2 >= 9) {
                    return TYPE_TIMESTAMP;
                }
                return new TypeInfo(20, n2);
            }
            case 21: {
                if (n2 < 0) {
                    n2 = -1;
                } else if (n2 >= 9) {
                    return TYPE_TIMESTAMP_TZ;
                }
                return new TypeInfo(21, n2);
            }
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 28: 
            case 29: 
            case 30: 
            case 32: {
                if (l < 1L) {
                    l = -1L;
                } else if (l > 18L) {
                    l = 18L;
                }
                return new TypeInfo(n, l);
            }
            case 27: 
            case 31: 
            case 33: 
            case 34: {
                if (l < 1L) {
                    l = -1L;
                } else if (l > 18L) {
                    l = 18L;
                }
                if (n2 < 0) {
                    n2 = -1;
                } else if (n2 > 9) {
                    n2 = 9;
                }
                return new TypeInfo(n, l, n2, null);
            }
            case 35: {
                if (l < 1L) {
                    return TYPE_JAVA_OBJECT;
                }
                if (l > 1000000000L) {
                    l = 1000000000L;
                }
                return new TypeInfo(35, l);
            }
            case 36: {
                if (extTypeInfo instanceof ExtTypeInfoEnum) {
                    return ((ExtTypeInfoEnum)extTypeInfo).getType();
                }
                return TYPE_ENUM_UNDEFINED;
            }
            case 37: {
                if (extTypeInfo instanceof ExtTypeInfoGeometry) {
                    return new TypeInfo(37, -1L, -1, extTypeInfo);
                }
                return TYPE_GEOMETRY;
            }
            case 38: {
                if (l < 1L) {
                    return TYPE_JSON;
                }
                if (l > 1000000000L) {
                    l = 1000000000L;
                }
                return new TypeInfo(38, l);
            }
            case 40: {
                if (!(extTypeInfo instanceof TypeInfo)) {
                    throw new IllegalArgumentException();
                }
                if (l < 0L || l >= 65536L) {
                    l = -1L;
                }
                return new TypeInfo(40, l, -1, extTypeInfo);
            }
            case 41: {
                if (!(extTypeInfo instanceof ExtTypeInfoRow)) {
                    throw new IllegalArgumentException();
                }
                return new TypeInfo(41, -1L, -1, extTypeInfo);
            }
        }
        return TYPE_NULL;
    }

    public static TypeInfo getHigherType(Typed[] typedArray) {
        TypeInfo typeInfo;
        int n = typedArray.length;
        if (n == 0) {
            typeInfo = TYPE_NULL;
        } else {
            typeInfo = typedArray[0].getType();
            boolean bl = false;
            boolean bl2 = false;
            switch (typeInfo.getValueType()) {
                case -1: {
                    bl = true;
                    break;
                }
                case 0: {
                    bl2 = true;
                }
            }
            block8: for (int i = 1; i < n; ++i) {
                TypeInfo typeInfo2 = typedArray[i].getType();
                switch (typeInfo2.getValueType()) {
                    case -1: {
                        bl = true;
                        continue block8;
                    }
                    case 0: {
                        bl2 = true;
                        continue block8;
                    }
                    default: {
                        typeInfo = TypeInfo.getHigherType(typeInfo, typeInfo2);
                    }
                }
            }
            if (typeInfo.getValueType() <= 0 && bl) {
                throw DbException.get(50004, bl2 ? "NULL, ?" : "?");
            }
        }
        return typeInfo;
    }

    public static TypeInfo getHigherType(TypeInfo typeInfo, TypeInfo typeInfo2) {
        long l;
        int n;
        int n2;
        int n3 = typeInfo.getValueType();
        if (n3 == (n2 = typeInfo2.getValueType())) {
            if (n3 == -1) {
                throw DbException.get(50004, "?, ?");
            }
            n = n3;
        } else {
            if (n3 < n2) {
                int n4 = n3;
                n3 = n2;
                n2 = n4;
                TypeInfo typeInfo3 = typeInfo;
                typeInfo = typeInfo2;
                typeInfo2 = typeInfo3;
            }
            if (n3 == -1) {
                if (n2 == 0) {
                    throw DbException.get(50004, "?, NULL");
                }
                return typeInfo2;
            }
            if (n2 == -1) {
                if (n3 == 0) {
                    throw DbException.get(50004, "NULL, ?");
                }
                return typeInfo;
            }
            if (n2 == 0) {
                return typeInfo;
            }
            n = Value.getHigherOrderKnown(n3, n2);
        }
        switch (n) {
            case 13: {
                int n5;
                typeInfo = typeInfo.toNumericType();
                typeInfo2 = typeInfo2.toNumericType();
                long l2 = typeInfo.getPrecision();
                long l3 = typeInfo2.getPrecision();
                int n6 = typeInfo.getScale();
                int n7 = typeInfo2.getScale();
                if (n6 < n7) {
                    l2 += (long)(n7 - n6);
                    n5 = n7;
                } else {
                    l3 += (long)(n6 - n7);
                    n5 = n6;
                }
                return TypeInfo.getTypeInfo(13, Math.max(l2, l3), n5, null);
            }
            case 14: 
            case 15: {
                l = -1L;
                break;
            }
            case 37: {
                return TypeInfo.getHigherGeometry(typeInfo, typeInfo2);
            }
            case 40: {
                return TypeInfo.getHigherArray(typeInfo, typeInfo2, TypeInfo.dimensions(typeInfo), TypeInfo.dimensions(typeInfo2));
            }
            case 41: {
                return TypeInfo.getHigherRow(typeInfo, typeInfo2);
            }
            default: {
                l = Math.max(typeInfo.getPrecision(), typeInfo2.getPrecision());
            }
        }
        ExtTypeInfo extTypeInfo = typeInfo.extTypeInfo;
        return TypeInfo.getTypeInfo(n, l, Math.max(typeInfo.getScale(), typeInfo2.getScale()), n == n3 && extTypeInfo != null ? extTypeInfo : (n == n2 ? typeInfo2.extTypeInfo : null));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static TypeInfo getHigherGeometry(TypeInfo typeInfo, TypeInfo typeInfo2) {
        ExtTypeInfo extTypeInfo = typeInfo.getExtTypeInfo();
        ExtTypeInfo extTypeInfo2 = typeInfo2.getExtTypeInfo();
        if (extTypeInfo instanceof ExtTypeInfoGeometry) {
            if (!(extTypeInfo2 instanceof ExtTypeInfoGeometry)) return typeInfo2.getValueType() == 37 ? TYPE_GEOMETRY : typeInfo;
            ExtTypeInfoGeometry extTypeInfoGeometry = (ExtTypeInfoGeometry)extTypeInfo;
            ExtTypeInfoGeometry extTypeInfoGeometry2 = (ExtTypeInfoGeometry)extTypeInfo2;
            int n = extTypeInfoGeometry.getType();
            Integer n2 = extTypeInfoGeometry.getSrid();
            int n3 = extTypeInfoGeometry2.getType();
            Integer n4 = extTypeInfoGeometry2.getSrid();
            if (Objects.equals(n2, n4)) {
                if (n == n3) {
                    return typeInfo;
                }
                if (n2 == null) {
                    return TYPE_GEOMETRY;
                }
                n = 0;
                return new TypeInfo(37, -1L, -1, new ExtTypeInfoGeometry(n, n2));
            } else {
                if (n2 != null && n4 != null) throw DbException.get(90110, typeInfo.getTraceSQL(), typeInfo2.getTraceSQL());
                if (n == 0 || n != n3) {
                    return TYPE_GEOMETRY;
                }
                n2 = null;
            }
            return new TypeInfo(37, -1L, -1, new ExtTypeInfoGeometry(n, n2));
        } else {
            if (!(extTypeInfo2 instanceof ExtTypeInfoGeometry)) return TYPE_GEOMETRY;
            return typeInfo.getValueType() == 37 ? TYPE_GEOMETRY : typeInfo2;
        }
    }

    private static int dimensions(TypeInfo typeInfo) {
        int n = 0;
        while (typeInfo.getValueType() == 40) {
            typeInfo = (TypeInfo)typeInfo.extTypeInfo;
            ++n;
        }
        return n;
    }

    private static TypeInfo getHigherArray(TypeInfo typeInfo, TypeInfo typeInfo2, int n, int n2) {
        long l;
        if (n > n2) {
            --n;
            l = Math.max(typeInfo.getPrecision(), 1L);
            typeInfo = (TypeInfo)typeInfo.extTypeInfo;
        } else if (n < n2) {
            --n2;
            l = Math.max(1L, typeInfo2.getPrecision());
            typeInfo2 = (TypeInfo)typeInfo2.extTypeInfo;
        } else if (n > 0) {
            --n;
            --n2;
            l = Math.max(typeInfo.getPrecision(), typeInfo2.getPrecision());
            typeInfo = (TypeInfo)typeInfo.extTypeInfo;
            typeInfo2 = (TypeInfo)typeInfo2.extTypeInfo;
        } else {
            return TypeInfo.getHigherType(typeInfo, typeInfo2);
        }
        return TypeInfo.getTypeInfo(40, l, 0, TypeInfo.getHigherArray(typeInfo, typeInfo2, n, n2));
    }

    private static TypeInfo getHigherRow(TypeInfo typeInfo, TypeInfo typeInfo2) {
        ExtTypeInfoRow extTypeInfoRow;
        ExtTypeInfoRow extTypeInfoRow2;
        if (typeInfo.getValueType() != 41) {
            typeInfo = TypeInfo.typeToRow(typeInfo);
        }
        if (typeInfo2.getValueType() != 41) {
            typeInfo2 = TypeInfo.typeToRow(typeInfo2);
        }
        if ((extTypeInfoRow2 = (ExtTypeInfoRow)typeInfo.getExtTypeInfo()).equals(extTypeInfoRow = (ExtTypeInfoRow)typeInfo2.getExtTypeInfo())) {
            return typeInfo;
        }
        Set<Map.Entry<String, TypeInfo>> set = extTypeInfoRow2.getFields();
        Set<Map.Entry<String, TypeInfo>> set2 = extTypeInfoRow.getFields();
        int n = set.size();
        if (set2.size() != n) {
            throw DbException.get(21002);
        }
        LinkedHashMap<String, TypeInfo> linkedHashMap = new LinkedHashMap<String, TypeInfo>((int)Math.ceil((double)n / 0.75));
        Iterator<Map.Entry<String, TypeInfo>> iterator2 = set.iterator();
        Iterator<Map.Entry<String, TypeInfo>> iterator3 = set2.iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, TypeInfo> entry = iterator2.next();
            linkedHashMap.put(entry.getKey(), TypeInfo.getHigherType(entry.getValue(), iterator3.next().getValue()));
        }
        return TypeInfo.getTypeInfo(41, 0L, 0, new ExtTypeInfoRow(linkedHashMap));
    }

    private static TypeInfo typeToRow(TypeInfo typeInfo) {
        LinkedHashMap<String, TypeInfo> linkedHashMap = new LinkedHashMap<String, TypeInfo>(2);
        linkedHashMap.put("C1", typeInfo);
        return TypeInfo.getTypeInfo(41, 0L, 0, new ExtTypeInfoRow(linkedHashMap));
    }

    public static boolean areSameTypes(TypeInfo typeInfo, TypeInfo typeInfo2) {
        int n;
        while ((n = typeInfo.getValueType()) == typeInfo2.getValueType()) {
            ExtTypeInfo extTypeInfo = typeInfo.getExtTypeInfo();
            ExtTypeInfo extTypeInfo2 = typeInfo2.getExtTypeInfo();
            if (n != 40) {
                return Objects.equals(extTypeInfo, extTypeInfo2);
            }
            typeInfo = (TypeInfo)extTypeInfo;
            typeInfo2 = (TypeInfo)extTypeInfo2;
        }
        return false;
    }

    public static void checkComparable(TypeInfo typeInfo, TypeInfo typeInfo2) {
        if (!TypeInfo.areComparable(typeInfo, typeInfo2)) {
            throw DbException.get(90110, typeInfo.getTraceSQL(), typeInfo2.getTraceSQL());
        }
    }

    private static boolean areComparable(TypeInfo typeInfo, TypeInfo typeInfo2) {
        Object object;
        int n;
        int n2;
        int n3 = (typeInfo = typeInfo.unwrapRow()).getValueType();
        if (n3 > (n2 = (typeInfo2 = typeInfo2.unwrapRow()).getValueType())) {
            n = n3;
            n3 = n2;
            n2 = n;
            object = typeInfo;
            typeInfo = typeInfo2;
            typeInfo2 = object;
        }
        if (n3 <= 0) {
            return true;
        }
        if (n3 == n2) {
            switch (n3) {
                case 40: {
                    return TypeInfo.areComparable((TypeInfo)typeInfo.getExtTypeInfo(), (TypeInfo)typeInfo2.getExtTypeInfo());
                }
                case 41: {
                    Set<Map.Entry<String, TypeInfo>> set = ((ExtTypeInfoRow)typeInfo.getExtTypeInfo()).getFields();
                    object = ((ExtTypeInfoRow)typeInfo2.getExtTypeInfo()).getFields();
                    int n4 = set.size();
                    if (object.size() != n4) {
                        return false;
                    }
                    Iterator<Map.Entry<String, TypeInfo>> iterator2 = set.iterator();
                    Iterator iterator3 = object.iterator();
                    while (iterator2.hasNext()) {
                        if (TypeInfo.areComparable(iterator2.next().getValue(), (TypeInfo)((Map.Entry)iterator3.next()).getValue())) continue;
                        return false;
                    }
                    break;
                }
            }
            return true;
        }
        n = Value.GROUPS[n3];
        byte by = Value.GROUPS[n2];
        if (n == by) {
            switch (n) {
                default: {
                    return true;
                }
                case 5: {
                    return n3 != 17 || n2 != 18 && n2 != 19;
                }
                case 8: 
                case 9: 
            }
            return false;
        }
        switch (n) {
            case 1: {
                switch (by) {
                    case 4: 
                    case 5: 
                    case 6: 
                    case 7: {
                        return true;
                    }
                    case 8: {
                        switch (n2) {
                            case 36: 
                            case 37: 
                            case 38: 
                            case 39: {
                                return true;
                            }
                        }
                        return false;
                    }
                }
                return false;
            }
            case 2: {
                switch (n2) {
                    case 35: 
                    case 37: 
                    case 38: 
                    case 39: {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public static boolean haveSameOrdering(TypeInfo typeInfo, TypeInfo typeInfo2) {
        Object object;
        int n;
        int n2;
        int n3 = (typeInfo = typeInfo.unwrapRow()).getValueType();
        if (n3 > (n2 = (typeInfo2 = typeInfo2.unwrapRow()).getValueType())) {
            n = n3;
            n3 = n2;
            n2 = n;
            object = typeInfo;
            typeInfo = typeInfo2;
            typeInfo2 = object;
        }
        if (n3 <= 0) {
            return true;
        }
        if (n3 == n2) {
            switch (n3) {
                case 40: {
                    return TypeInfo.haveSameOrdering((TypeInfo)typeInfo.getExtTypeInfo(), (TypeInfo)typeInfo2.getExtTypeInfo());
                }
                case 41: {
                    Set<Map.Entry<String, TypeInfo>> set = ((ExtTypeInfoRow)typeInfo.getExtTypeInfo()).getFields();
                    object = ((ExtTypeInfoRow)typeInfo2.getExtTypeInfo()).getFields();
                    int n4 = set.size();
                    if (object.size() != n4) {
                        return false;
                    }
                    Iterator<Map.Entry<String, TypeInfo>> iterator2 = set.iterator();
                    Iterator iterator3 = object.iterator();
                    while (iterator2.hasNext()) {
                        if (TypeInfo.haveSameOrdering(iterator2.next().getValue(), (TypeInfo)((Map.Entry)iterator3.next()).getValue())) continue;
                        return false;
                    }
                    break;
                }
            }
            return true;
        }
        n = Value.GROUPS[n3];
        byte by = Value.GROUPS[n2];
        if (n == by) {
            switch (n) {
                default: {
                    return true;
                }
                case 1: {
                    return n3 == 4 == (n2 == 4);
                }
                case 5: {
                    switch (n3) {
                        case 17: {
                            return n2 == 20 || n2 == 21;
                        }
                        case 18: 
                        case 19: {
                            return n2 == 18 || n2 == 19;
                        }
                    }
                    return true;
                }
                case 8: 
                case 9: 
            }
            return false;
        }
        if (n == 2) {
            switch (n2) {
                case 35: 
                case 37: 
                case 38: 
                case 39: {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private TypeInfo(int n) {
        this.valueType = n;
        this.precision = -1L;
        this.scale = -1;
        this.extTypeInfo = null;
    }

    private TypeInfo(int n, long l) {
        this.valueType = n;
        this.precision = l;
        this.scale = -1;
        this.extTypeInfo = null;
    }

    private TypeInfo(int n, int n2) {
        this.valueType = n;
        this.precision = -1L;
        this.scale = n2;
        this.extTypeInfo = null;
    }

    public TypeInfo(int n, long l, int n2, ExtTypeInfo extTypeInfo) {
        this.valueType = n;
        this.precision = l;
        this.scale = n2;
        this.extTypeInfo = extTypeInfo;
    }

    @Override
    public TypeInfo getType() {
        return this;
    }

    public int getValueType() {
        return this.valueType;
    }

    public long getPrecision() {
        switch (this.valueType) {
            case -1: {
                return -1L;
            }
            case 0: {
                return 1L;
            }
            case 1: 
            case 5: {
                return this.precision >= 0L ? this.precision : 1L;
            }
            case 2: 
            case 4: 
            case 6: 
            case 35: 
            case 36: 
            case 37: 
            case 38: {
                return this.precision >= 0L ? this.precision : 1000000000L;
            }
            case 3: 
            case 7: {
                return this.precision >= 0L ? this.precision : Long.MAX_VALUE;
            }
            case 8: {
                return 1L;
            }
            case 9: {
                return 8L;
            }
            case 10: {
                return 16L;
            }
            case 11: {
                return 32L;
            }
            case 12: {
                return 64L;
            }
            case 13: {
                return this.precision >= 0L ? this.precision : 100000L;
            }
            case 14: {
                return 24L;
            }
            case 15: {
                return 53L;
            }
            case 16: {
                return this.precision >= 0L ? this.precision : 100000L;
            }
            case 17: {
                return 10L;
            }
            case 18: {
                int n = this.scale >= 0 ? this.scale : 0;
                return n == 0 ? 8L : (long)(9 + n);
            }
            case 19: {
                int n = this.scale >= 0 ? this.scale : 0;
                return n == 0 ? 14L : (long)(15 + n);
            }
            case 20: {
                int n = this.scale >= 0 ? this.scale : 6;
                return n == 0 ? 19L : (long)(20 + n);
            }
            case 21: {
                int n = this.scale >= 0 ? this.scale : 6;
                return n == 0 ? 25L : (long)(26 + n);
            }
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: {
                return this.precision >= 0L ? this.precision : 2L;
            }
            case 41: {
                return Integer.MAX_VALUE;
            }
            case 39: {
                return 16L;
            }
            case 40: {
                return this.precision >= 0L ? this.precision : 65536L;
            }
        }
        return this.precision;
    }

    public long getDeclaredPrecision() {
        return this.precision;
    }

    public int getScale() {
        switch (this.valueType) {
            case -1: {
                return -1;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 28: 
            case 29: 
            case 30: 
            case 32: 
            case 35: 
            case 36: 
            case 37: 
            case 38: 
            case 39: 
            case 40: 
            case 41: {
                return 0;
            }
            case 13: {
                return this.scale >= 0 ? this.scale : 0;
            }
            case 18: 
            case 19: {
                return this.scale >= 0 ? this.scale : 0;
            }
            case 20: 
            case 21: {
                return this.scale >= 0 ? this.scale : 6;
            }
            case 27: 
            case 31: 
            case 33: 
            case 34: {
                return this.scale >= 0 ? this.scale : 6;
            }
        }
        return this.scale;
    }

    public int getDeclaredScale() {
        return this.scale;
    }

    public int getDisplaySize() {
        switch (this.valueType) {
            default: {
                return -1;
            }
            case 0: {
                return 4;
            }
            case 1: {
                return this.precision >= 0L ? (int)this.precision : 1;
            }
            case 2: 
            case 4: 
            case 38: {
                return this.precision >= 0L ? (int)this.precision : 1000000000;
            }
            case 3: {
                return this.precision >= 0L && this.precision <= Integer.MAX_VALUE ? (int)this.precision : Integer.MAX_VALUE;
            }
            case 5: {
                return this.precision >= 0L ? (int)this.precision * 2 : 2;
            }
            case 6: 
            case 35: {
                return this.precision >= 0L ? (int)this.precision * 2 : 2000000000;
            }
            case 7: {
                return this.precision >= 0L && this.precision <= 0x3FFFFFFFL ? (int)this.precision * 2 : Integer.MAX_VALUE;
            }
            case 8: {
                return 5;
            }
            case 9: {
                return 4;
            }
            case 10: {
                return 6;
            }
            case 11: {
                return 11;
            }
            case 12: {
                return 20;
            }
            case 13: {
                return this.precision >= 0L ? (int)this.precision + 2 : 100002;
            }
            case 14: {
                return 15;
            }
            case 15: {
                return 24;
            }
            case 16: {
                return this.precision >= 0L ? (int)this.precision + 12 : 100012;
            }
            case 17: {
                return 10;
            }
            case 18: {
                int n = this.scale >= 0 ? this.scale : 0;
                return n == 0 ? 8 : 9 + n;
            }
            case 19: {
                int n = this.scale >= 0 ? this.scale : 0;
                return n == 0 ? 14 : 15 + n;
            }
            case 20: {
                int n = this.scale >= 0 ? this.scale : 6;
                return n == 0 ? 19 : 20 + n;
            }
            case 21: {
                int n = this.scale >= 0 ? this.scale : 6;
                return n == 0 ? 25 : 26 + n;
            }
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: {
                return ValueInterval.getDisplaySize(this.valueType, this.precision >= 0L ? (int)this.precision : 2, this.scale >= 0 ? this.scale : 6);
            }
            case 37: 
            case 40: 
            case 41: {
                return Integer.MAX_VALUE;
            }
            case 36: {
                return this.extTypeInfo != null ? (int)this.precision : 1000000000;
            }
            case 39: 
        }
        return 36;
    }

    public ExtTypeInfo getExtTypeInfo() {
        return this.extTypeInfo;
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        switch (this.valueType) {
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 35: 
            case 38: {
                stringBuilder.append(Value.getTypeName(this.valueType));
                if (this.precision < 0L) break;
                stringBuilder.append('(').append(this.precision).append(')');
                break;
            }
            case 13: {
                boolean bl;
                if (this.extTypeInfo != null) {
                    this.extTypeInfo.getSQL(stringBuilder, n);
                } else {
                    stringBuilder.append("NUMERIC");
                }
                boolean bl2 = this.precision >= 0L;
                boolean bl3 = bl = this.scale >= 0;
                if (!bl2 && !bl) break;
                stringBuilder.append('(').append(bl2 ? this.precision : 100000L);
                if (bl) {
                    stringBuilder.append(", ").append(this.scale);
                }
                stringBuilder.append(')');
                break;
            }
            case 14: 
            case 15: {
                if (this.precision < 0L) {
                    stringBuilder.append(Value.getTypeName(this.valueType));
                    break;
                }
                stringBuilder.append("FLOAT");
                if (this.precision <= 0L) break;
                stringBuilder.append('(').append(this.precision).append(')');
                break;
            }
            case 16: {
                stringBuilder.append("DECFLOAT");
                if (this.precision < 0L) break;
                stringBuilder.append('(').append(this.precision).append(')');
                break;
            }
            case 18: 
            case 19: {
                stringBuilder.append("TIME");
                if (this.scale >= 0) {
                    stringBuilder.append('(').append(this.scale).append(')');
                }
                if (this.valueType != 19) break;
                stringBuilder.append(" WITH TIME ZONE");
                break;
            }
            case 20: 
            case 21: {
                stringBuilder.append("TIMESTAMP");
                if (this.scale >= 0) {
                    stringBuilder.append('(').append(this.scale).append(')');
                }
                if (this.valueType != 21) break;
                stringBuilder.append(" WITH TIME ZONE");
                break;
            }
            case 22: 
            case 23: 
            case 24: 
            case 25: 
            case 26: 
            case 27: 
            case 28: 
            case 29: 
            case 30: 
            case 31: 
            case 32: 
            case 33: 
            case 34: {
                IntervalQualifier.valueOf(this.valueType - 22).getTypeName(stringBuilder, (int)this.precision, this.scale, false);
                break;
            }
            case 36: {
                this.extTypeInfo.getSQL(stringBuilder.append("ENUM"), n);
                break;
            }
            case 37: {
                stringBuilder.append("GEOMETRY");
                if (this.extTypeInfo == null) break;
                this.extTypeInfo.getSQL(stringBuilder, n);
                break;
            }
            case 40: {
                if (this.extTypeInfo != null) {
                    this.extTypeInfo.getSQL(stringBuilder, n).append(' ');
                }
                stringBuilder.append("ARRAY");
                if (this.precision < 0L) break;
                stringBuilder.append('[').append(this.precision).append(']');
                break;
            }
            case 41: {
                stringBuilder.append("ROW");
                if (this.extTypeInfo == null) break;
                this.extTypeInfo.getSQL(stringBuilder, n);
                break;
            }
            default: {
                stringBuilder.append(Value.getTypeName(this.valueType));
            }
        }
        return stringBuilder;
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + this.valueType;
        n = 31 * n + (int)(this.precision ^ this.precision >>> 32);
        n = 31 * n + this.scale;
        n = 31 * n + (this.extTypeInfo == null ? 0 : this.extTypeInfo.hashCode());
        return n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || object.getClass() != TypeInfo.class) {
            return false;
        }
        TypeInfo typeInfo = (TypeInfo)object;
        return this.valueType == typeInfo.valueType && this.precision == typeInfo.precision && this.scale == typeInfo.scale && Objects.equals(this.extTypeInfo, typeInfo.extTypeInfo);
    }

    public TypeInfo toNumericType() {
        switch (this.valueType) {
            case 8: 
            case 9: 
            case 10: 
            case 11: {
                return TypeInfo.getTypeInfo(13, this.getDecimalPrecision(), 0, null);
            }
            case 12: {
                return TYPE_NUMERIC_BIGINT;
            }
            case 13: {
                return this;
            }
            case 14: {
                return TypeInfo.getTypeInfo(13, 85L, 46, null);
            }
            case 15: {
                return TypeInfo.getTypeInfo(13, 634L, 325, null);
            }
        }
        return TYPE_NUMERIC_FLOATING_POINT;
    }

    public TypeInfo toDecfloatType() {
        switch (this.valueType) {
            case 8: 
            case 9: 
            case 10: 
            case 11: {
                return TypeInfo.getTypeInfo(16, this.getDecimalPrecision(), 0, null);
            }
            case 12: {
                return TYPE_DECFLOAT_BIGINT;
            }
            case 13: {
                return TypeInfo.getTypeInfo(16, this.getPrecision(), 0, null);
            }
            case 14: {
                return TypeInfo.getTypeInfo(16, 7L, 0, null);
            }
            case 15: {
                return TypeInfo.getTypeInfo(16, 17L, 0, null);
            }
            case 16: {
                return this;
            }
        }
        return TYPE_DECFLOAT;
    }

    public TypeInfo unwrapRow() {
        Set<Map.Entry<String, TypeInfo>> set;
        if (this.valueType == 41 && (set = ((ExtTypeInfoRow)this.extTypeInfo).getFields()).size() == 1) {
            return set.iterator().next().getValue().unwrapRow();
        }
        return this;
    }

    public long getDecimalPrecision() {
        switch (this.valueType) {
            case 9: {
                return 3L;
            }
            case 10: {
                return 5L;
            }
            case 11: {
                return 10L;
            }
            case 12: {
                return 19L;
            }
            case 14: {
                return 7L;
            }
            case 15: {
                return 17L;
            }
        }
        return this.precision;
    }

    public String getDeclaredTypeName() {
        switch (this.valueType) {
            case 13: {
                return this.extTypeInfo != null ? "DECIMAL" : "NUMERIC";
            }
            case 14: 
            case 15: {
                if (this.extTypeInfo == null) break;
                return "FLOAT";
            }
            case 36: 
            case 37: 
            case 41: {
                return this.getSQL(0);
            }
            case 40: {
                TypeInfo typeInfo = (TypeInfo)this.extTypeInfo;
                return typeInfo.getSQL(new StringBuilder(), 0).append(" ARRAY").toString();
            }
        }
        return Value.getTypeName(this.valueType);
    }

    static {
        TypeInfo[] typeInfoArray = new TypeInfo[42];
        TYPE_UNKNOWN = new TypeInfo(-1);
        typeInfoArray[0] = TYPE_NULL = new TypeInfo(0);
        typeInfoArray[1] = TYPE_CHAR = new TypeInfo(1, -1L);
        typeInfoArray[2] = TYPE_VARCHAR = new TypeInfo(2);
        typeInfoArray[3] = TYPE_CLOB = new TypeInfo(3);
        typeInfoArray[4] = TYPE_VARCHAR_IGNORECASE = new TypeInfo(4);
        typeInfoArray[5] = TYPE_BINARY = new TypeInfo(5, -1L);
        typeInfoArray[6] = TYPE_VARBINARY = new TypeInfo(6);
        typeInfoArray[7] = TYPE_BLOB = new TypeInfo(7);
        typeInfoArray[8] = TYPE_BOOLEAN = new TypeInfo(8);
        typeInfoArray[9] = TYPE_TINYINT = new TypeInfo(9);
        typeInfoArray[10] = TYPE_SMALLINT = new TypeInfo(10);
        typeInfoArray[11] = TYPE_INTEGER = new TypeInfo(11);
        typeInfoArray[12] = TYPE_BIGINT = new TypeInfo(12);
        TYPE_NUMERIC_SCALE_0 = new TypeInfo(13, 100000L, 0, null);
        TYPE_NUMERIC_BIGINT = new TypeInfo(13, 19L, 0, null);
        typeInfoArray[13] = TYPE_NUMERIC_FLOATING_POINT = new TypeInfo(13, 100000L, 50000, null);
        typeInfoArray[14] = TYPE_REAL = new TypeInfo(14);
        typeInfoArray[15] = TYPE_DOUBLE = new TypeInfo(15);
        typeInfoArray[16] = TYPE_DECFLOAT = new TypeInfo(16);
        TYPE_DECFLOAT_BIGINT = new TypeInfo(16, 19L);
        typeInfoArray[17] = TYPE_DATE = new TypeInfo(17);
        typeInfoArray[18] = TYPE_TIME = new TypeInfo(18, 9);
        typeInfoArray[19] = TYPE_TIME_TZ = new TypeInfo(19, 9);
        typeInfoArray[20] = TYPE_TIMESTAMP = new TypeInfo(20, 9);
        typeInfoArray[21] = TYPE_TIMESTAMP_TZ = new TypeInfo(21, 9);
        for (int i = 22; i <= 34; ++i) {
            typeInfoArray[i] = new TypeInfo(i, 18L, IntervalQualifier.valueOf(i - 22).hasSeconds() ? 9 : -1, null);
        }
        TYPE_INTERVAL_DAY = typeInfoArray[24];
        TYPE_INTERVAL_YEAR_TO_MONTH = typeInfoArray[28];
        TYPE_INTERVAL_DAY_TO_SECOND = typeInfoArray[31];
        TYPE_INTERVAL_HOUR_TO_SECOND = typeInfoArray[33];
        typeInfoArray[35] = TYPE_JAVA_OBJECT = new TypeInfo(35);
        typeInfoArray[36] = TYPE_ENUM_UNDEFINED = new TypeInfo(36);
        typeInfoArray[37] = TYPE_GEOMETRY = new TypeInfo(37);
        typeInfoArray[38] = TYPE_JSON = new TypeInfo(38);
        typeInfoArray[39] = TYPE_UUID = new TypeInfo(39);
        typeInfoArray[40] = TYPE_ARRAY_UNKNOWN = new TypeInfo(40);
        typeInfoArray[41] = TYPE_ROW_EMPTY = new TypeInfo(41, -1L, -1, new ExtTypeInfoRow(new LinkedHashMap<String, TypeInfo>()));
        TYPE_INFOS_BY_VALUE_TYPE = typeInfoArray;
    }
}

