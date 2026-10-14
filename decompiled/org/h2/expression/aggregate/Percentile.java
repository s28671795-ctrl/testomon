/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.aggregate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import org.h2.api.IntervalQualifier;
import org.h2.command.query.QueryOrderBy;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.index.Cursor;
import org.h2.index.Index;
import org.h2.mode.DefaultNullOrdering;
import org.h2.result.SearchRow;
import org.h2.table.Column;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.util.DateTimeUtils;
import org.h2.util.IntervalUtils;
import org.h2.value.CompareMode;
import org.h2.value.Value;
import org.h2.value.ValueDate;
import org.h2.value.ValueInterval;
import org.h2.value.ValueNull;
import org.h2.value.ValueNumeric;
import org.h2.value.ValueTime;
import org.h2.value.ValueTimeTimeZone;
import org.h2.value.ValueTimestamp;
import org.h2.value.ValueTimestampTimeZone;

final class Percentile {
    static final BigDecimal HALF = BigDecimal.valueOf(0.5);

    private static boolean isNullsLast(DefaultNullOrdering defaultNullOrdering, Index index) {
        return defaultNullOrdering.compareNull(true, index.getIndexColumns()[0].sortType) > 0;
    }

    static Index getColumnIndex(Database database, Expression expression) {
        DefaultNullOrdering defaultNullOrdering = database.getDefaultNullOrdering();
        if (expression instanceof ExpressionColumn) {
            ExpressionColumn expressionColumn = (ExpressionColumn)expression;
            Column column = expressionColumn.getColumn();
            TableFilter tableFilter = expressionColumn.getTableFilter();
            if (tableFilter != null) {
                Table table = tableFilter.getTable();
                ArrayList<Index> arrayList = table.getIndexes();
                Index index = null;
                if (arrayList != null) {
                    boolean bl = column.isNullable();
                    int n = arrayList.size();
                    for (int i = 1; i < n; ++i) {
                        Index index2 = arrayList.get(i);
                        if (!index2.canFindNext() || !index2.isFirstColumn(column) || index != null && index.getColumns().length <= index2.getColumns().length && (!bl || !Percentile.isNullsLast(defaultNullOrdering, index) || Percentile.isNullsLast(defaultNullOrdering, index2))) continue;
                        index = index2;
                    }
                }
                return index;
            }
        }
        return null;
    }

    static Value getValue(SessionLocal sessionLocal, Value[] valueArray, int n, ArrayList<QueryOrderBy> arrayList, BigDecimal bigDecimal, boolean bl) {
        int n2;
        CompareMode compareMode = sessionLocal.getDatabase().getCompareMode();
        Arrays.sort(valueArray, compareMode);
        int n3 = valueArray.length;
        boolean bl2 = arrayList != null && (arrayList.get((int)0).sortType & 1) != 0;
        BigDecimal bigDecimal2 = BigDecimal.valueOf(n3 - 1).multiply(bigDecimal);
        int n4 = bigDecimal2.intValue();
        BigDecimal bigDecimal3 = bigDecimal2.subtract(BigDecimal.valueOf(n4));
        if (bigDecimal3.signum() == 0) {
            bl = false;
            n2 = n4;
        } else {
            n2 = n4 + 1;
            if (!bl) {
                if (bigDecimal3.compareTo(HALF) > 0) {
                    n4 = n2;
                } else {
                    n2 = n4;
                }
            }
        }
        if (bl2) {
            n4 = n3 - 1 - n4;
            n2 = n3 - 1 - n2;
        }
        Value value = valueArray[n4];
        if (!bl) {
            return value;
        }
        return Percentile.interpolate(value, valueArray[n2], bigDecimal3, n, sessionLocal, compareMode);
    }

    static Value getFromIndex(SessionLocal sessionLocal, Expression expression, int n, ArrayList<QueryOrderBy> arrayList, BigDecimal bigDecimal, boolean bl) {
        long l;
        long l2;
        Object object;
        Object object2;
        boolean bl2;
        Database database = sessionLocal.getDatabase();
        Index index = Percentile.getColumnIndex(database, expression);
        long l3 = index.getRowCount(sessionLocal);
        if (l3 == 0L) {
            return ValueNull.INSTANCE;
        }
        Cursor cursor = index.find(sessionLocal, null, null);
        cursor.next();
        int n2 = index.getColumns()[0].getColumnId();
        ExpressionColumn expressionColumn = (ExpressionColumn)expression;
        if (expressionColumn.getColumn().isNullable()) {
            bl2 = false;
            while (l3 > 0L) {
                object2 = cursor.getSearchRow();
                if (object2 == null) {
                    return ValueNull.INSTANCE;
                }
                if (((SearchRow)object2).getValue(n2) != ValueNull.INSTANCE) break;
                --l3;
                cursor.next();
                bl2 = true;
            }
            if (l3 == 0L) {
                return ValueNull.INSTANCE;
            }
            if (!bl2 && Percentile.isNullsLast(database.getDefaultNullOrdering(), index)) {
                TableFilter tableFilter = expressionColumn.getTableFilter();
                SearchRow searchRow = tableFilter.getTable().getTemplateSimpleRow(true);
                searchRow.setValue(n2, ValueNull.INSTANCE);
                object = index.find(sessionLocal, searchRow, searchRow);
                while (object.next()) {
                    --l3;
                }
                if (l3 <= 0L) {
                    return ValueNull.INSTANCE;
                }
            }
        }
        bl2 = (arrayList != null ? arrayList.get((int)0).sortType & 1 : 0) != (index.getIndexColumns()[0].sortType & 1);
        object2 = BigDecimal.valueOf(l3 - 1L).multiply(bigDecimal);
        object = ((BigDecimal)object2).subtract(BigDecimal.valueOf(l2 = ((BigDecimal)object2).longValue()));
        if (((BigDecimal)object).signum() == 0) {
            bl = false;
            l = l2;
        } else {
            l = l2 + 1L;
            if (!bl) {
                if (((BigDecimal)object).compareTo(HALF) > 0) {
                    l2 = l;
                } else {
                    l = l2;
                }
            }
        }
        long l4 = bl2 ? l3 - 1L - l : l2;
        int n3 = 0;
        while ((long)n3 < l4) {
            cursor.next();
            ++n3;
        }
        SearchRow searchRow = cursor.getSearchRow();
        if (searchRow == null) {
            return ValueNull.INSTANCE;
        }
        Value value = searchRow.getValue(n2);
        if (value == ValueNull.INSTANCE) {
            return value;
        }
        if (bl) {
            cursor.next();
            searchRow = cursor.getSearchRow();
            if (searchRow == null) {
                return value;
            }
            Value value2 = searchRow.getValue(n2);
            if (value2 == ValueNull.INSTANCE) {
                return value;
            }
            if (bl2) {
                Value value3 = value;
                value = value2;
                value2 = value3;
            }
            return Percentile.interpolate(value, value2, (BigDecimal)object, n, sessionLocal, database.getCompareMode());
        }
        return value;
    }

    private static Value interpolate(Value value, Value value2, BigDecimal bigDecimal, int n, SessionLocal sessionLocal, CompareMode compareMode) {
        if (value.compareTo(value2, sessionLocal, compareMode) == 0) {
            return value;
        }
        switch (n) {
            case 9: 
            case 10: 
            case 11: {
                return ValueNumeric.get(Percentile.interpolateDecimal(BigDecimal.valueOf(value.getInt()), BigDecimal.valueOf(value2.getInt()), bigDecimal));
            }
            case 12: {
                return ValueNumeric.get(Percentile.interpolateDecimal(BigDecimal.valueOf(value.getLong()), BigDecimal.valueOf(value2.getLong()), bigDecimal));
            }
            case 13: 
            case 16: {
                return ValueNumeric.get(Percentile.interpolateDecimal(value.getBigDecimal(), value2.getBigDecimal(), bigDecimal));
            }
            case 14: 
            case 15: {
                return ValueNumeric.get(Percentile.interpolateDecimal(BigDecimal.valueOf(value.getDouble()), BigDecimal.valueOf(value2.getDouble()), bigDecimal));
            }
            case 18: {
                ValueTime valueTime = (ValueTime)value;
                ValueTime valueTime2 = (ValueTime)value2;
                BigDecimal bigDecimal2 = BigDecimal.valueOf(valueTime.getNanos());
                BigDecimal bigDecimal3 = BigDecimal.valueOf(valueTime2.getNanos());
                return ValueTime.fromNanos(Percentile.interpolateDecimal(bigDecimal2, bigDecimal3, bigDecimal).longValue());
            }
            case 19: {
                long l;
                ValueTimeTimeZone valueTimeTimeZone = (ValueTimeTimeZone)value;
                ValueTimeTimeZone valueTimeTimeZone2 = (ValueTimeTimeZone)value2;
                BigDecimal bigDecimal4 = BigDecimal.valueOf(valueTimeTimeZone.getNanos());
                BigDecimal bigDecimal5 = BigDecimal.valueOf(valueTimeTimeZone2.getNanos());
                BigDecimal bigDecimal6 = BigDecimal.valueOf(valueTimeTimeZone.getTimeZoneOffsetSeconds()).multiply(BigDecimal.ONE.subtract(bigDecimal)).add(BigDecimal.valueOf(valueTimeTimeZone2.getTimeZoneOffsetSeconds()).multiply(bigDecimal));
                int n2 = bigDecimal6.intValue();
                BigDecimal bigDecimal7 = BigDecimal.valueOf(n2);
                BigDecimal bigDecimal8 = Percentile.interpolateDecimal(bigDecimal4, bigDecimal5, bigDecimal);
                if (bigDecimal6.compareTo(bigDecimal7) != 0) {
                    bigDecimal8 = bigDecimal8.add(bigDecimal6.subtract(bigDecimal7).multiply(BigDecimal.valueOf(1000000000L)));
                }
                if ((l = bigDecimal8.longValue()) < 0L) {
                    l += 1000000000L;
                    ++n2;
                } else if (l >= 86400000000000L) {
                    l -= 1000000000L;
                    --n2;
                }
                return ValueTimeTimeZone.fromNanos(l, n2);
            }
            case 17: {
                ValueDate valueDate = (ValueDate)value;
                ValueDate valueDate2 = (ValueDate)value2;
                BigDecimal bigDecimal9 = BigDecimal.valueOf(DateTimeUtils.absoluteDayFromDateValue(valueDate.getDateValue()));
                BigDecimal bigDecimal10 = BigDecimal.valueOf(DateTimeUtils.absoluteDayFromDateValue(valueDate2.getDateValue()));
                return ValueDate.fromDateValue(DateTimeUtils.dateValueFromAbsoluteDay(Percentile.interpolateDecimal(bigDecimal9, bigDecimal10, bigDecimal).longValue()));
            }
            case 20: {
                ValueTimestamp valueTimestamp = (ValueTimestamp)value;
                ValueTimestamp valueTimestamp2 = (ValueTimestamp)value2;
                BigDecimal bigDecimal11 = Percentile.timestampToDecimal(valueTimestamp.getDateValue(), valueTimestamp.getTimeNanos());
                BigDecimal bigDecimal12 = Percentile.timestampToDecimal(valueTimestamp2.getDateValue(), valueTimestamp2.getTimeNanos());
                BigInteger[] bigIntegerArray = Percentile.interpolateDecimal(bigDecimal11, bigDecimal12, bigDecimal).toBigInteger().divideAndRemainder(IntervalUtils.NANOS_PER_DAY_BI);
                long l = bigIntegerArray[0].longValue();
                long l2 = bigIntegerArray[1].longValue();
                if (l2 < 0L) {
                    l2 += 86400000000000L;
                    --l;
                }
                return ValueTimestamp.fromDateValueAndNanos(DateTimeUtils.dateValueFromAbsoluteDay(l), l2);
            }
            case 21: {
                ValueTimestampTimeZone valueTimestampTimeZone = (ValueTimestampTimeZone)value;
                ValueTimestampTimeZone valueTimestampTimeZone2 = (ValueTimestampTimeZone)value2;
                BigDecimal bigDecimal13 = Percentile.timestampToDecimal(valueTimestampTimeZone.getDateValue(), valueTimestampTimeZone.getTimeNanos());
                BigDecimal bigDecimal14 = Percentile.timestampToDecimal(valueTimestampTimeZone2.getDateValue(), valueTimestampTimeZone2.getTimeNanos());
                BigDecimal bigDecimal15 = BigDecimal.valueOf(valueTimestampTimeZone.getTimeZoneOffsetSeconds()).multiply(BigDecimal.ONE.subtract(bigDecimal)).add(BigDecimal.valueOf(valueTimestampTimeZone2.getTimeZoneOffsetSeconds()).multiply(bigDecimal));
                int n3 = bigDecimal15.intValue();
                BigDecimal bigDecimal16 = BigDecimal.valueOf(n3);
                BigDecimal bigDecimal17 = Percentile.interpolateDecimal(bigDecimal13, bigDecimal14, bigDecimal);
                if (bigDecimal15.compareTo(bigDecimal16) != 0) {
                    bigDecimal17 = bigDecimal17.add(bigDecimal15.subtract(bigDecimal16).multiply(BigDecimal.valueOf(1000000000L)));
                }
                BigInteger[] bigIntegerArray = bigDecimal17.toBigInteger().divideAndRemainder(IntervalUtils.NANOS_PER_DAY_BI);
                long l = bigIntegerArray[0].longValue();
                long l3 = bigIntegerArray[1].longValue();
                if (l3 < 0L) {
                    l3 += 86400000000000L;
                    --l;
                }
                return ValueTimestampTimeZone.fromDateValueAndNanos(DateTimeUtils.dateValueFromAbsoluteDay(l), l3, n3);
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
                return IntervalUtils.intervalFromAbsolute(IntervalQualifier.valueOf(n - 22), Percentile.interpolateDecimal(new BigDecimal(IntervalUtils.intervalToAbsolute((ValueInterval)value)), new BigDecimal(IntervalUtils.intervalToAbsolute((ValueInterval)value2)), bigDecimal).toBigInteger());
            }
        }
        return bigDecimal.compareTo(HALF) > 0 ? value2 : value;
    }

    private static BigDecimal timestampToDecimal(long l, long l2) {
        return new BigDecimal(BigInteger.valueOf(DateTimeUtils.absoluteDayFromDateValue(l)).multiply(IntervalUtils.NANOS_PER_DAY_BI).add(BigInteger.valueOf(l2)));
    }

    private static BigDecimal interpolateDecimal(BigDecimal bigDecimal, BigDecimal bigDecimal2, BigDecimal bigDecimal3) {
        return bigDecimal.multiply(BigDecimal.ONE.subtract(bigDecimal3)).add(bigDecimal2.multiply(bigDecimal3));
    }

    private Percentile() {
    }
}

