/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.aggregate;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import org.h2.command.query.QueryOrderBy;
import org.h2.command.query.Select;
import org.h2.engine.CastDataProvider;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.ExpressionWithFlags;
import org.h2.expression.ValueExpression;
import org.h2.expression.aggregate.AbstractAggregate;
import org.h2.expression.aggregate.AggregateData;
import org.h2.expression.aggregate.AggregateDataAnyValue;
import org.h2.expression.aggregate.AggregateDataAvg;
import org.h2.expression.aggregate.AggregateDataBinarySet;
import org.h2.expression.aggregate.AggregateDataCollecting;
import org.h2.expression.aggregate.AggregateDataCorr;
import org.h2.expression.aggregate.AggregateDataCount;
import org.h2.expression.aggregate.AggregateDataCovar;
import org.h2.expression.aggregate.AggregateDataDefault;
import org.h2.expression.aggregate.AggregateDataDistinctWithCounts;
import org.h2.expression.aggregate.AggregateDataEnvelope;
import org.h2.expression.aggregate.AggregateDataStdVar;
import org.h2.expression.aggregate.AggregateType;
import org.h2.expression.aggregate.ListaggArguments;
import org.h2.expression.aggregate.LongDataCounter;
import org.h2.expression.aggregate.Percentile;
import org.h2.expression.analysis.Window;
import org.h2.expression.function.BitFunction;
import org.h2.expression.function.JsonConstructorFunction;
import org.h2.index.Cursor;
import org.h2.index.Index;
import org.h2.message.DbException;
import org.h2.mvstore.db.MVSpatialIndex;
import org.h2.result.SearchRow;
import org.h2.result.SortOrder;
import org.h2.table.Column;
import org.h2.table.ColumnResolver;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.util.StringUtils;
import org.h2.util.json.JsonConstructorUtils;
import org.h2.value.CompareMode;
import org.h2.value.DataType;
import org.h2.value.ExtTypeInfoRow;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueBigint;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueDouble;
import org.h2.value.ValueInteger;
import org.h2.value.ValueJson;
import org.h2.value.ValueNull;
import org.h2.value.ValueRow;
import org.h2.value.ValueVarchar;

public class Aggregate
extends AbstractAggregate
implements ExpressionWithFlags {
    private static final int ADDITIONAL_SUM_PRECISION = 10;
    private static final int ADDITIONAL_AVG_SCALE = 10;
    private static final HashMap<String, AggregateType> AGGREGATES = new HashMap(128);
    private final AggregateType aggregateType;
    private ArrayList<QueryOrderBy> orderByList;
    private SortOrder orderBySort;
    private Object extraArguments;
    private int flags;

    public Aggregate(AggregateType aggregateType, Expression[] expressionArray, Select select, boolean bl) {
        super(select, expressionArray, bl);
        if (bl && aggregateType == AggregateType.COUNT_ALL) {
            throw DbException.getInternalError();
        }
        this.aggregateType = aggregateType;
    }

    private static void addAggregate(String string, AggregateType aggregateType) {
        AGGREGATES.put(string, aggregateType);
    }

    public static AggregateType getAggregateType(String string) {
        return AGGREGATES.get(string);
    }

    public void setOrderByList(ArrayList<QueryOrderBy> arrayList) {
        this.orderByList = arrayList;
    }

    public AggregateType getAggregateType() {
        return this.aggregateType;
    }

    public void setExtraArguments(Object object) {
        this.extraArguments = object;
    }

    public Object getExtraArguments() {
        return this.extraArguments;
    }

    @Override
    public void setFlags(int n) {
        this.flags = n;
    }

    @Override
    public int getFlags() {
        return this.flags;
    }

    private void sortWithOrderBy(Value[] valueArray) {
        SortOrder sortOrder = this.orderBySort;
        Arrays.sort(valueArray, sortOrder != null ? (value, value2) -> sortOrder.compare(((ValueRow)value).getList(), ((ValueRow)value2).getList()) : this.select.getSession().getDatabase().getCompareMode());
    }

    @Override
    protected void updateAggregate(SessionLocal sessionLocal, Object object) {
        AggregateData aggregateData = (AggregateData)object;
        Value value = this.args.length == 0 ? null : this.args[0].getValue(sessionLocal);
        this.updateData(sessionLocal, aggregateData, value, null);
    }

    private void updateData(SessionLocal sessionLocal, AggregateData aggregateData, Value value, Value[] valueArray) {
        switch (this.aggregateType) {
            case COVAR_POP: 
            case COVAR_SAMP: 
            case CORR: 
            case REGR_SLOPE: 
            case REGR_INTERCEPT: 
            case REGR_R2: 
            case REGR_SXY: {
                Value value2;
                if (value == ValueNull.INSTANCE || (value2 = this.getSecondValue(sessionLocal, valueArray)) == ValueNull.INSTANCE) {
                    return;
                }
                ((AggregateDataBinarySet)aggregateData).add(sessionLocal, value, value2);
                return;
            }
            case REGR_COUNT: 
            case REGR_AVGY: 
            case REGR_SYY: {
                if (value != ValueNull.INSTANCE && this.getSecondValue(sessionLocal, valueArray) != ValueNull.INSTANCE) break;
                return;
            }
            case REGR_AVGX: 
            case REGR_SXX: {
                if (value != ValueNull.INSTANCE && (value = this.getSecondValue(sessionLocal, valueArray)) != ValueNull.INSTANCE) break;
                return;
            }
            case LISTAGG: {
                if (value == ValueNull.INSTANCE) {
                    return;
                }
                value = this.updateCollecting(sessionLocal, value.convertTo(TypeInfo.TYPE_VARCHAR), valueArray);
                break;
            }
            case ARRAY_AGG: {
                value = this.updateCollecting(sessionLocal, value, valueArray);
                break;
            }
            case RANK: 
            case DENSE_RANK: 
            case PERCENT_RANK: 
            case CUME_DIST: {
                int n;
                int n2 = this.args.length;
                Value[] valueArray2 = new Value[n2];
                for (n = 0; n < n2; ++n) {
                    valueArray2[n] = valueArray != null ? valueArray[n] : this.args[n].getValue(sessionLocal);
                }
                ((AggregateDataCollecting)aggregateData).setSharedArgument(ValueRow.get(valueArray2));
                valueArray2 = new Value[n2];
                for (n = 0; n < n2; ++n) {
                    valueArray2[n] = valueArray != null ? valueArray[n2 + n] : this.orderByList.get((int)n).expression.getValue(sessionLocal);
                }
                value = ValueRow.get(valueArray2);
                break;
            }
            case PERCENTILE_CONT: 
            case PERCENTILE_DISC: {
                ((AggregateDataCollecting)aggregateData).setSharedArgument(value);
                value = valueArray != null ? valueArray[1] : this.orderByList.get((int)0).expression.getValue(sessionLocal);
                break;
            }
            case MODE: {
                value = valueArray != null ? valueArray[0] : this.orderByList.get((int)0).expression.getValue(sessionLocal);
                break;
            }
            case JSON_ARRAYAGG: {
                value = this.updateCollecting(sessionLocal, value, valueArray);
                break;
            }
            case JSON_OBJECTAGG: {
                Value value3 = value;
                Value value4 = this.getSecondValue(sessionLocal, valueArray);
                if (value3 == ValueNull.INSTANCE) {
                    throw DbException.getInvalidValueException("JSON_OBJECTAGG key", "NULL");
                }
                value = ValueRow.get(new Value[]{value3, value4});
                break;
            }
        }
        aggregateData.add(sessionLocal, value);
    }

    private Value getSecondValue(SessionLocal sessionLocal, Value[] valueArray) {
        return valueArray != null ? valueArray[1] : this.args[1].getValue(sessionLocal);
    }

    @Override
    protected void updateGroupAggregates(SessionLocal sessionLocal, int n) {
        super.updateGroupAggregates(sessionLocal, n);
        for (Expression expression : this.args) {
            expression.updateAggregate(sessionLocal, n);
        }
        if (this.orderByList != null) {
            for (QueryOrderBy queryOrderBy : this.orderByList) {
                queryOrderBy.expression.updateAggregate(sessionLocal, n);
            }
        }
    }

    private Value updateCollecting(SessionLocal sessionLocal, Value value, Value[] valueArray) {
        if (this.orderByList != null) {
            int n = this.orderByList.size();
            Value[] valueArray2 = new Value[1 + n];
            valueArray2[0] = value;
            if (valueArray == null) {
                for (int i = 0; i < n; ++i) {
                    QueryOrderBy queryOrderBy = this.orderByList.get(i);
                    valueArray2[i + 1] = queryOrderBy.expression.getValue(sessionLocal);
                }
            } else {
                System.arraycopy(valueArray, 1, valueArray2, 1, n);
            }
            value = ValueRow.get(valueArray2);
        }
        return value;
    }

    @Override
    protected int getNumExpressions() {
        int n = this.args.length;
        if (this.orderByList != null) {
            n += this.orderByList.size();
        }
        if (this.filterCondition != null) {
            ++n;
        }
        return n;
    }

    @Override
    protected void rememberExpressions(SessionLocal sessionLocal, Value[] valueArray) {
        int n = 0;
        for (Expression expression : this.args) {
            valueArray[n++] = expression.getValue(sessionLocal);
        }
        if (this.orderByList != null) {
            for (QueryOrderBy queryOrderBy : this.orderByList) {
                valueArray[n++] = queryOrderBy.expression.getValue(sessionLocal);
            }
        }
        if (this.filterCondition != null) {
            valueArray[n] = ValueBoolean.get(this.filterCondition.getBooleanValue(sessionLocal));
        }
    }

    @Override
    protected void updateFromExpressions(SessionLocal sessionLocal, Object object, Value[] valueArray) {
        if (this.filterCondition == null || valueArray[this.getNumExpressions() - 1].isTrue()) {
            AggregateData aggregateData = (AggregateData)object;
            Value value = this.args.length == 0 ? null : valueArray[0];
            this.updateData(sessionLocal, aggregateData, value, valueArray);
        }
    }

    @Override
    protected Object createAggregateData() {
        switch (this.aggregateType) {
            case REGR_COUNT: 
            case COUNT_ALL: {
                return new AggregateDataCount(true);
            }
            case COUNT: {
                if (this.distinct) break;
                return new AggregateDataCount(false);
            }
            case RANK: 
            case DENSE_RANK: 
            case PERCENT_RANK: 
            case CUME_DIST: 
            case PERCENTILE_CONT: 
            case PERCENTILE_DISC: 
            case MEDIAN: {
                break;
            }
            case SUM: 
            case BIT_XOR_AGG: 
            case BIT_XNOR_AGG: {
                if (this.distinct) break;
            }
            case MIN: 
            case MAX: 
            case BIT_AND_AGG: 
            case BIT_OR_AGG: 
            case BIT_NAND_AGG: 
            case BIT_NOR_AGG: 
            case ANY: 
            case EVERY: {
                return new AggregateDataDefault(this.aggregateType, this.type);
            }
            case AVG: {
                if (this.distinct) break;
            }
            case REGR_AVGY: 
            case REGR_AVGX: {
                return new AggregateDataAvg(this.type);
            }
            case STDDEV_POP: 
            case STDDEV_SAMP: 
            case VAR_POP: 
            case VAR_SAMP: {
                if (this.distinct) break;
            }
            case REGR_SYY: 
            case REGR_SXX: {
                return new AggregateDataStdVar(this.aggregateType);
            }
            case HISTOGRAM: {
                return new AggregateDataDistinctWithCounts(false, 10000);
            }
            case COVAR_POP: 
            case COVAR_SAMP: 
            case REGR_SXY: {
                return new AggregateDataCovar(this.aggregateType);
            }
            case CORR: 
            case REGR_SLOPE: 
            case REGR_INTERCEPT: 
            case REGR_R2: {
                return new AggregateDataCorr(this.aggregateType);
            }
            case ANY_VALUE: {
                if (this.distinct) break;
                return new AggregateDataAnyValue();
            }
            case LISTAGG: 
            case ARRAY_AGG: {
                return new AggregateDataCollecting(this.distinct, this.orderByList != null, AggregateDataCollecting.NullCollectionMode.USED_OR_IMPOSSIBLE);
            }
            case MODE: {
                return new AggregateDataDistinctWithCounts(true, Integer.MAX_VALUE);
            }
            case ENVELOPE: {
                return new AggregateDataEnvelope();
            }
            case JSON_ARRAYAGG: {
                return new AggregateDataCollecting(this.distinct, this.orderByList != null, (this.flags & 1) != 0 ? AggregateDataCollecting.NullCollectionMode.EXCLUDED : AggregateDataCollecting.NullCollectionMode.USED_OR_IMPOSSIBLE);
            }
            case JSON_OBJECTAGG: {
                return new AggregateDataCollecting(this.distinct, false, AggregateDataCollecting.NullCollectionMode.USED_OR_IMPOSSIBLE);
            }
            default: {
                throw DbException.getInternalError("type=" + (Object)((Object)this.aggregateType));
            }
        }
        return new AggregateDataCollecting(this.distinct, false, AggregateDataCollecting.NullCollectionMode.IGNORED);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        return this.select.isQuickAggregateQuery() ? this.getValueQuick(sessionLocal) : super.getValue(sessionLocal);
    }

    private Value getValueQuick(SessionLocal sessionLocal) {
        switch (this.aggregateType) {
            case COUNT_ALL: 
            case COUNT: {
                Table table = this.select.getTopTableFilter().getTable();
                return ValueBigint.get(table.getRowCount(sessionLocal));
            }
            case MIN: 
            case MAX: {
                Cursor cursor;
                SearchRow searchRow;
                boolean bl = this.aggregateType == AggregateType.MIN;
                Index index = this.getMinMaxColumnIndex();
                int n = index.getIndexColumns()[0].sortType;
                if ((n & 1) != 0) {
                    bl = !bl;
                }
                Value value = (searchRow = (cursor = index.findFirstOrLast(sessionLocal, bl)).getSearchRow()) == null ? ValueNull.INSTANCE : searchRow.getValue(index.getColumns()[0].getColumnId());
                return value;
            }
            case PERCENTILE_CONT: 
            case PERCENTILE_DISC: {
                Value value = this.args[0].getValue(sessionLocal);
                if (value == ValueNull.INSTANCE) {
                    return ValueNull.INSTANCE;
                }
                BigDecimal bigDecimal = value.getBigDecimal();
                if (bigDecimal.signum() >= 0 && bigDecimal.compareTo(BigDecimal.ONE) <= 0) {
                    return Percentile.getFromIndex(sessionLocal, this.orderByList.get((int)0).expression, this.type.getValueType(), this.orderByList, bigDecimal, this.aggregateType == AggregateType.PERCENTILE_CONT);
                }
                throw DbException.getInvalidValueException(this.aggregateType == AggregateType.PERCENTILE_CONT ? "PERCENTILE_CONT argument" : "PERCENTILE_DISC argument", bigDecimal);
            }
            case MEDIAN: {
                return Percentile.getFromIndex(sessionLocal, this.args[0], this.type.getValueType(), this.orderByList, Percentile.HALF, true);
            }
            case ENVELOPE: {
                return ((MVSpatialIndex)AggregateDataEnvelope.getGeometryColumnIndex(this.args[0])).getBounds(sessionLocal);
            }
        }
        throw DbException.getInternalError("type=" + (Object)((Object)this.aggregateType));
    }

    @Override
    public Value getAggregatedValue(SessionLocal sessionLocal, Object object) {
        AggregateData aggregateData = (AggregateData)object;
        if (aggregateData == null) {
            aggregateData = (AggregateData)this.createAggregateData();
        }
        switch (this.aggregateType) {
            case COUNT: {
                if (!this.distinct) break;
                return ValueBigint.get(((AggregateDataCollecting)aggregateData).getCount());
            }
            case SUM: 
            case BIT_XOR_AGG: 
            case BIT_XNOR_AGG: {
                if (!this.distinct) break;
                AggregateDataCollecting aggregateDataCollecting = (AggregateDataCollecting)aggregateData;
                if (aggregateDataCollecting.getCount() == 0) {
                    return ValueNull.INSTANCE;
                }
                return Aggregate.collect(sessionLocal, aggregateDataCollecting, new AggregateDataDefault(this.aggregateType, this.type));
            }
            case AVG: {
                if (!this.distinct) break;
                AggregateDataCollecting aggregateDataCollecting = (AggregateDataCollecting)aggregateData;
                if (aggregateDataCollecting.getCount() == 0) {
                    return ValueNull.INSTANCE;
                }
                return Aggregate.collect(sessionLocal, aggregateDataCollecting, new AggregateDataAvg(this.type));
            }
            case STDDEV_POP: 
            case STDDEV_SAMP: 
            case VAR_POP: 
            case VAR_SAMP: {
                if (!this.distinct) break;
                AggregateDataCollecting aggregateDataCollecting = (AggregateDataCollecting)aggregateData;
                if (aggregateDataCollecting.getCount() == 0) {
                    return ValueNull.INSTANCE;
                }
                return Aggregate.collect(sessionLocal, aggregateDataCollecting, new AggregateDataStdVar(this.aggregateType));
            }
            case ANY_VALUE: {
                if (!this.distinct) break;
                Value[] valueArray = ((AggregateDataCollecting)aggregateData).getArray();
                if (valueArray == null) {
                    return ValueNull.INSTANCE;
                }
                return valueArray[sessionLocal.getRandom().nextInt(valueArray.length)];
            }
            case HISTOGRAM: {
                return this.getHistogram(sessionLocal, aggregateData);
            }
            case LISTAGG: {
                return this.getListagg(sessionLocal, aggregateData);
            }
            case ARRAY_AGG: {
                Value[] valueArray = ((AggregateDataCollecting)aggregateData).getArray();
                if (valueArray == null) {
                    return ValueNull.INSTANCE;
                }
                if (this.orderByList != null || this.distinct) {
                    this.sortWithOrderBy(valueArray);
                }
                if (this.orderByList != null) {
                    for (int i = 0; i < valueArray.length; ++i) {
                        valueArray[i] = ((ValueRow)valueArray[i]).getList()[0];
                    }
                }
                return ValueArray.get((TypeInfo)this.type.getExtTypeInfo(), valueArray, sessionLocal);
            }
            case RANK: 
            case DENSE_RANK: 
            case PERCENT_RANK: 
            case CUME_DIST: {
                return this.getHypotheticalSet(sessionLocal, aggregateData);
            }
            case PERCENTILE_CONT: 
            case PERCENTILE_DISC: {
                AggregateDataCollecting aggregateDataCollecting = (AggregateDataCollecting)aggregateData;
                Value[] valueArray = aggregateDataCollecting.getArray();
                if (valueArray == null) {
                    return ValueNull.INSTANCE;
                }
                Value value = aggregateDataCollecting.getSharedArgument();
                if (value == ValueNull.INSTANCE) {
                    return ValueNull.INSTANCE;
                }
                BigDecimal bigDecimal = value.getBigDecimal();
                if (bigDecimal.signum() >= 0 && bigDecimal.compareTo(BigDecimal.ONE) <= 0) {
                    return Percentile.getValue(sessionLocal, valueArray, this.type.getValueType(), this.orderByList, bigDecimal, this.aggregateType == AggregateType.PERCENTILE_CONT);
                }
                throw DbException.getInvalidValueException(this.aggregateType == AggregateType.PERCENTILE_CONT ? "PERCENTILE_CONT argument" : "PERCENTILE_DISC argument", bigDecimal);
            }
            case MEDIAN: {
                Value[] valueArray = ((AggregateDataCollecting)aggregateData).getArray();
                if (valueArray == null) {
                    return ValueNull.INSTANCE;
                }
                return Percentile.getValue(sessionLocal, valueArray, this.type.getValueType(), this.orderByList, Percentile.HALF, true);
            }
            case MODE: {
                return this.getMode(sessionLocal, aggregateData);
            }
            case JSON_ARRAYAGG: {
                Value[] valueArray = ((AggregateDataCollecting)aggregateData).getArray();
                if (valueArray == null) {
                    return ValueNull.INSTANCE;
                }
                if (this.orderByList != null) {
                    this.sortWithOrderBy(valueArray);
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(91);
                for (Value value : valueArray) {
                    if (this.orderByList != null) {
                        value = ((ValueRow)value).getList()[0];
                    }
                    JsonConstructorUtils.jsonArrayAppend(byteArrayOutputStream, value != ValueNull.INSTANCE ? value : ValueJson.NULL, this.flags);
                }
                byteArrayOutputStream.write(93);
                return ValueJson.getInternal(byteArrayOutputStream.toByteArray());
            }
            case JSON_OBJECTAGG: {
                Value[] valueArray = ((AggregateDataCollecting)aggregateData).getArray();
                if (valueArray == null) {
                    return ValueNull.INSTANCE;
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(123);
                for (Value value : valueArray) {
                    Value[] valueArray2 = ((ValueRow)value).getList();
                    String string = valueArray2[0].getString();
                    if (string == null) {
                        throw DbException.getInvalidValueException("JSON_OBJECTAGG key", "NULL");
                    }
                    Value value2 = valueArray2[1];
                    if (value2 == ValueNull.INSTANCE) {
                        if ((this.flags & 1) != 0) continue;
                        value2 = ValueJson.NULL;
                    }
                    JsonConstructorUtils.jsonObjectAppend(byteArrayOutputStream, string, value2);
                }
                return JsonConstructorUtils.jsonObjectFinish(byteArrayOutputStream, this.flags);
            }
        }
        return aggregateData.getValue(sessionLocal);
    }

    private static Value collect(SessionLocal sessionLocal, AggregateDataCollecting aggregateDataCollecting, AggregateData aggregateData) {
        for (Value value : aggregateDataCollecting) {
            aggregateData.add(sessionLocal, value);
        }
        return aggregateData.getValue(sessionLocal);
    }

    private Value getHypotheticalSet(SessionLocal sessionLocal, AggregateData aggregateData) {
        AggregateDataCollecting aggregateDataCollecting = (AggregateDataCollecting)aggregateData;
        Value value = aggregateDataCollecting.getSharedArgument();
        if (value == null) {
            switch (this.aggregateType) {
                case RANK: 
                case DENSE_RANK: {
                    return ValueInteger.get(1);
                }
                case PERCENT_RANK: {
                    return ValueDouble.ZERO;
                }
                case CUME_DIST: {
                    return ValueDouble.ONE;
                }
            }
            throw DbException.getUnsupportedException("aggregateType=" + (Object)((Object)this.aggregateType));
        }
        aggregateDataCollecting.add(sessionLocal, value);
        Value[] valueArray = aggregateDataCollecting.getArray();
        Comparator<Value> comparator = this.orderBySort.getRowValueComparator();
        Arrays.sort(valueArray, comparator);
        return this.aggregateType == AggregateType.CUME_DIST ? Aggregate.getCumeDist(valueArray, value, comparator) : this.getRank(valueArray, value, comparator);
    }

    private Value getRank(Value[] valueArray, Value value, Comparator<Value> comparator) {
        int n = valueArray.length;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3;
            Value value2 = valueArray[i];
            if (i == 0) {
                n2 = 1;
            } else if (comparator.compare(valueArray[i - 1], value2) != 0) {
                n2 = this.aggregateType == AggregateType.DENSE_RANK ? ++n2 : i + 1;
            }
            Value value3 = this.aggregateType == AggregateType.PERCENT_RANK ? ((n3 = n2 - 1) == 0 ? ValueDouble.ZERO : ValueDouble.get((double)n3 / (double)(n - 1))) : ValueBigint.get(n2);
            if (comparator.compare(value2, value) != 0) continue;
            return value3;
        }
        throw DbException.getInternalError();
    }

    private static Value getCumeDist(Value[] valueArray, Value value, Comparator<Value> comparator) {
        int n = valueArray.length;
        int n2 = 0;
        while (n2 < n) {
            int n3;
            Value value2 = valueArray[n2];
            for (n3 = n2 + 1; n3 < n && comparator.compare(value2, valueArray[n3]) == 0; ++n3) {
            }
            ValueDouble valueDouble = ValueDouble.get((double)n3 / (double)n);
            for (int i = n2; i < n3; ++i) {
                if (comparator.compare(valueArray[i], value) != 0) continue;
                return valueDouble;
            }
            n2 = n3;
        }
        throw DbException.getInternalError();
    }

    private Value getListagg(SessionLocal sessionLocal, AggregateData aggregateData) {
        AggregateDataCollecting aggregateDataCollecting = (AggregateDataCollecting)aggregateData;
        Value[] valueArray = aggregateDataCollecting.getArray();
        if (valueArray == null) {
            return ValueNull.INSTANCE;
        }
        if (valueArray.length == 1) {
            Value value = valueArray[0];
            if (this.orderByList != null) {
                value = ((ValueRow)value).getList()[0];
            }
            return value.convertTo(2, (CastDataProvider)sessionLocal);
        }
        if (this.orderByList != null || this.distinct) {
            this.sortWithOrderBy(valueArray);
        }
        ListaggArguments listaggArguments = (ListaggArguments)this.extraArguments;
        String string = listaggArguments.getEffectiveSeparator();
        return ValueVarchar.get((listaggArguments.getOnOverflowTruncate() ? this.getListaggTruncate(valueArray, string, listaggArguments.getEffectiveFilter(), listaggArguments.isWithoutCount()) : this.getListaggError(valueArray, string)).toString(), sessionLocal);
    }

    private StringBuilder getListaggError(Value[] valueArray, String string) {
        StringBuilder stringBuilder = new StringBuilder(this.getListaggItem(valueArray[0]));
        int n = valueArray.length;
        for (int i = 1; i < n; ++i) {
            String string2 = this.getListaggItem(valueArray[i]);
            long l = (long)stringBuilder.length() + (long)string.length() + (long)string2.length();
            if (l > 1000000000L) {
                int n2 = 81;
                StringUtils.appendToLength(stringBuilder, string, n2);
                StringUtils.appendToLength(stringBuilder, string2, n2);
                throw DbException.getValueTooLongException("CHARACTER VARYING", stringBuilder.substring(0, n2), -1L);
            }
            stringBuilder.append(string).append(string2);
        }
        return stringBuilder;
    }

    private StringBuilder getListaggTruncate(Value[] valueArray, String string, String string2, boolean bl) {
        String string3;
        int n = valueArray.length;
        String[] stringArray = new String[n];
        stringArray[0] = string3 = this.getListaggItem(valueArray[0]);
        int n2 = (int)Math.min(1000000000L, (long)string3.length() * (long)n);
        StringBuilder stringBuilder = new StringBuilder(n2);
        stringBuilder.append(string3);
        block0: for (int i = 1; i < n; ++i) {
            stringArray[i] = string3 = this.getListaggItem(valueArray[i]);
            int n3 = stringBuilder.length();
            long l = (long)n3 + (long)string.length() + (long)string3.length();
            if (l > 1000000000L) {
                if (l - (long)string3.length() >= 1000000000L) {
                    --i;
                } else {
                    stringBuilder.append(string);
                    n3 = (int)l;
                }
                while (i > 0) {
                    stringBuilder.setLength(n3 -= stringArray[i].length());
                    StringUtils.appendToLength(stringBuilder, string2, 1000000001);
                    if (!bl) {
                        stringBuilder.append('(').append(n - i).append(')');
                    }
                    if (stringBuilder.length() <= 1000000000) break block0;
                    n3 -= string.length();
                    --i;
                }
                stringBuilder.setLength(0);
                stringBuilder.append(string2).append('(').append(n).append(')');
                break;
            }
            stringBuilder.append(string).append(string3);
        }
        return stringBuilder;
    }

    private String getListaggItem(Value value) {
        if (this.orderByList != null) {
            value = ((ValueRow)value).getList()[0];
        }
        return value.getString();
    }

    private Value getHistogram(SessionLocal sessionLocal, AggregateData aggregateData) {
        TreeMap<Value, LongDataCounter> treeMap = ((AggregateDataDistinctWithCounts)aggregateData).getValues();
        TypeInfo typeInfo = (TypeInfo)this.type.getExtTypeInfo();
        if (treeMap == null) {
            return ValueArray.get(typeInfo, Value.EMPTY_VALUES, sessionLocal);
        }
        Value[] valueArray = new ValueRow[treeMap.size()];
        int n = 0;
        for (Map.Entry<Value, LongDataCounter> object2 : treeMap.entrySet()) {
            LongDataCounter longDataCounter = object2.getValue();
            valueArray[n] = ValueRow.get(typeInfo, new Value[]{object2.getKey(), ValueBigint.get(longDataCounter.count)});
            ++n;
        }
        Database database = sessionLocal.getDatabase();
        CompareMode compareMode = database.getCompareMode();
        Arrays.sort(valueArray, (valueRow, valueRow2) -> valueRow.getList()[0].compareTo(valueRow2.getList()[0], sessionLocal, compareMode));
        return ValueArray.get(typeInfo, valueArray, sessionLocal);
    }

    private Value getMode(SessionLocal sessionLocal, AggregateData aggregateData) {
        Value value = ValueNull.INSTANCE;
        TreeMap<Value, LongDataCounter> treeMap = ((AggregateDataDistinctWithCounts)aggregateData).getValues();
        if (treeMap == null) {
            return value;
        }
        long l = 0L;
        if (this.orderByList != null) {
            boolean bl = (this.orderByList.get((int)0).sortType & 1) != 0;
            for (Map.Entry<Value, LongDataCounter> entry : treeMap.entrySet()) {
                long l2 = entry.getValue().count;
                if (l2 > l) {
                    value = entry.getKey();
                    l = l2;
                    continue;
                }
                if (l2 != l) continue;
                Value value2 = entry.getKey();
                int n = sessionLocal.compareTypeSafe(value, value2);
                if (bl ? n >= 0 : n <= 0) continue;
                value = value2;
            }
        } else {
            for (Map.Entry<Value, LongDataCounter> entry : treeMap.entrySet()) {
                long l3 = entry.getValue().count;
                if (l3 <= l) continue;
                value = entry.getKey();
                l = l3;
            }
        }
        return value;
    }

    @Override
    public void mapColumnsAnalysis(ColumnResolver columnResolver, int n, int n2) {
        if (this.orderByList != null) {
            for (QueryOrderBy queryOrderBy : this.orderByList) {
                queryOrderBy.expression.mapColumns(columnResolver, n, n2);
            }
        }
        super.mapColumnsAnalysis(columnResolver, n, n2);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        super.optimize(sessionLocal);
        if (this.args.length == 1) {
            this.type = this.args[0].getType();
        }
        if (this.orderByList != null) {
            int n;
            switch (this.aggregateType) {
                case LISTAGG: 
                case ARRAY_AGG: 
                case JSON_ARRAYAGG: {
                    n = 1;
                    break;
                }
                default: {
                    n = 0;
                }
            }
            Iterator<QueryOrderBy> iterator2 = this.orderByList.iterator();
            while (iterator2.hasNext()) {
                QueryOrderBy queryOrderBy = iterator2.next();
                Expression expression = queryOrderBy.expression.optimize(sessionLocal);
                if (n != 0 && expression.isConstant()) {
                    iterator2.remove();
                    continue;
                }
                queryOrderBy.expression = expression;
            }
            if (this.orderByList.isEmpty()) {
                this.orderByList = null;
            } else {
                this.orderBySort = Aggregate.createOrder(sessionLocal, this.orderByList, n);
            }
        }
        switch (this.aggregateType) {
            case LISTAGG: {
                this.type = TypeInfo.TYPE_VARCHAR;
                break;
            }
            case COUNT: {
                if (this.args[0].isConstant()) {
                    if (this.args[0].getValue(sessionLocal) == ValueNull.INSTANCE) {
                        return ValueExpression.get(ValueBigint.get(0L));
                    }
                    if (!this.distinct) {
                        Aggregate aggregate = new Aggregate(AggregateType.COUNT_ALL, new Expression[0], this.select, false);
                        aggregate.setFilterCondition(this.filterCondition);
                        aggregate.setOverCondition(this.over);
                        return aggregate.optimize(sessionLocal);
                    }
                }
            }
            case REGR_COUNT: 
            case COUNT_ALL: {
                this.type = TypeInfo.TYPE_BIGINT;
                break;
            }
            case HISTOGRAM: {
                LinkedHashMap<String, TypeInfo> linkedHashMap = new LinkedHashMap<String, TypeInfo>(4);
                linkedHashMap.put("VALUE", this.type);
                linkedHashMap.put("COUNT", TypeInfo.TYPE_BIGINT);
                this.type = TypeInfo.getTypeInfo(40, -1L, 0, TypeInfo.getTypeInfo(41, -1L, -1, new ExtTypeInfoRow(linkedHashMap)));
                break;
            }
            case SUM: {
                this.type = Aggregate.getSumType(this.type);
                if (this.type != null) break;
                throw DbException.get(90015, this.getTraceSQL());
            }
            case AVG: {
                this.type = Aggregate.getAvgType(this.type);
                if (this.type != null) break;
                throw DbException.get(90015, this.getTraceSQL());
            }
            case MIN: 
            case MAX: 
            case ANY_VALUE: {
                break;
            }
            case COVAR_POP: 
            case COVAR_SAMP: 
            case CORR: 
            case REGR_SLOPE: 
            case REGR_INTERCEPT: 
            case REGR_R2: 
            case REGR_SXY: 
            case REGR_SYY: 
            case REGR_SXX: 
            case STDDEV_POP: 
            case STDDEV_SAMP: 
            case VAR_POP: 
            case VAR_SAMP: {
                this.type = TypeInfo.TYPE_DOUBLE;
                break;
            }
            case REGR_AVGX: {
                this.type = Aggregate.getAvgType(this.args[1].getType());
                if (this.type != null) break;
                throw DbException.get(90015, this.getTraceSQL());
            }
            case REGR_AVGY: {
                this.type = Aggregate.getAvgType(this.args[0].getType());
                if (this.type != null) break;
                throw DbException.get(90015, this.getTraceSQL());
            }
            case RANK: 
            case DENSE_RANK: {
                this.type = TypeInfo.TYPE_BIGINT;
                break;
            }
            case PERCENT_RANK: 
            case CUME_DIST: {
                this.type = TypeInfo.TYPE_DOUBLE;
                break;
            }
            case PERCENTILE_CONT: {
                this.type = this.orderByList.get((int)0).expression.getType();
            }
            case MEDIAN: {
                switch (this.type.getValueType()) {
                    case 9: 
                    case 10: 
                    case 11: 
                    case 12: 
                    case 13: 
                    case 14: 
                    case 15: 
                    case 16: {
                        this.type = TypeInfo.TYPE_NUMERIC_FLOATING_POINT;
                    }
                }
                break;
            }
            case PERCENTILE_DISC: 
            case MODE: {
                this.type = this.orderByList.get((int)0).expression.getType();
                break;
            }
            case ANY: 
            case EVERY: {
                this.type = TypeInfo.TYPE_BOOLEAN;
                break;
            }
            case BIT_XOR_AGG: 
            case BIT_XNOR_AGG: 
            case BIT_AND_AGG: 
            case BIT_OR_AGG: 
            case BIT_NAND_AGG: 
            case BIT_NOR_AGG: {
                BitFunction.checkArgType(this.args[0]);
                break;
            }
            case ARRAY_AGG: {
                this.type = TypeInfo.getTypeInfo(40, -1L, 0, this.args[0].getType());
                break;
            }
            case ENVELOPE: {
                this.type = TypeInfo.TYPE_GEOMETRY;
                break;
            }
            case JSON_ARRAYAGG: 
            case JSON_OBJECTAGG: {
                this.type = TypeInfo.TYPE_JSON;
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + (Object)((Object)this.aggregateType));
            }
        }
        return this;
    }

    private static TypeInfo getSumType(TypeInfo typeInfo) {
        int n = typeInfo.getValueType();
        switch (n) {
            case 8: 
            case 9: 
            case 10: 
            case 11: {
                return TypeInfo.TYPE_BIGINT;
            }
            case 12: {
                return TypeInfo.getTypeInfo(13, 29L, -1, null);
            }
            case 13: {
                return TypeInfo.getTypeInfo(13, typeInfo.getPrecision() + 10L, typeInfo.getDeclaredScale(), null);
            }
            case 14: {
                return TypeInfo.TYPE_DOUBLE;
            }
            case 15: {
                return TypeInfo.getTypeInfo(16, 27L, -1, null);
            }
            case 16: {
                return TypeInfo.getTypeInfo(16, typeInfo.getPrecision() + 10L, -1, null);
            }
        }
        if (DataType.isIntervalType(n)) {
            return TypeInfo.getTypeInfo(n, 18L, typeInfo.getDeclaredScale(), null);
        }
        return null;
    }

    private static TypeInfo getAvgType(TypeInfo typeInfo) {
        switch (typeInfo.getValueType()) {
            case 9: 
            case 10: 
            case 11: 
            case 14: {
                return TypeInfo.TYPE_DOUBLE;
            }
            case 12: {
                return TypeInfo.getTypeInfo(13, 29L, 10, null);
            }
            case 13: {
                int n = Math.min(100000 - typeInfo.getScale(), Math.min(100000 - (int)typeInfo.getPrecision(), 10));
                return TypeInfo.getTypeInfo(13, typeInfo.getPrecision() + (long)n, typeInfo.getScale() + n, null);
            }
            case 15: {
                return TypeInfo.getTypeInfo(16, 27L, -1, null);
            }
            case 16: {
                return TypeInfo.getTypeInfo(16, typeInfo.getPrecision() + 10L, -1, null);
            }
            case 22: 
            case 28: {
                return TypeInfo.getTypeInfo(28, typeInfo.getDeclaredPrecision(), 0, null);
            }
            case 23: {
                return TypeInfo.getTypeInfo(23, typeInfo.getDeclaredPrecision(), 0, null);
            }
            case 24: 
            case 29: 
            case 30: 
            case 31: {
                return TypeInfo.getTypeInfo(31, typeInfo.getDeclaredPrecision(), 9, null);
            }
            case 25: 
            case 32: 
            case 33: {
                return TypeInfo.getTypeInfo(33, typeInfo.getDeclaredPrecision(), 9, null);
            }
            case 26: 
            case 34: {
                return TypeInfo.getTypeInfo(34, typeInfo.getDeclaredPrecision(), 9, null);
            }
            case 27: {
                return TypeInfo.getTypeInfo(27, typeInfo.getDeclaredPrecision(), 9, null);
            }
        }
        return null;
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        if (this.orderByList != null) {
            for (QueryOrderBy queryOrderBy : this.orderByList) {
                queryOrderBy.expression.setEvaluatable(tableFilter, bl);
            }
        }
        super.setEvaluatable(tableFilter, bl);
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        switch (this.aggregateType) {
            case COUNT_ALL: {
                return this.appendTailConditions(stringBuilder.append("COUNT(*)"), n, false);
            }
            case LISTAGG: {
                return this.getSQLListagg(stringBuilder, n);
            }
            case ARRAY_AGG: {
                return this.getSQLArrayAggregate(stringBuilder, n);
            }
            case JSON_OBJECTAGG: {
                return this.getSQLJsonObjectAggregate(stringBuilder, n);
            }
            case JSON_ARRAYAGG: {
                return this.getSQLJsonArrayAggregate(stringBuilder, n);
            }
        }
        stringBuilder.append(this.aggregateType.name());
        if (this.distinct) {
            stringBuilder.append("(DISTINCT ");
        } else {
            stringBuilder.append('(');
        }
        Aggregate.writeExpressions(stringBuilder, this.args, n).append(')');
        if (this.orderByList != null) {
            stringBuilder.append(" WITHIN GROUP (");
            Window.appendOrderBy(stringBuilder, this.orderByList, n, false);
            stringBuilder.append(')');
        }
        return this.appendTailConditions(stringBuilder, n, false);
    }

    private StringBuilder getSQLArrayAggregate(StringBuilder stringBuilder, int n) {
        stringBuilder.append("ARRAY_AGG(");
        if (this.distinct) {
            stringBuilder.append("DISTINCT ");
        }
        this.args[0].getUnenclosedSQL(stringBuilder, n);
        Window.appendOrderBy(stringBuilder, this.orderByList, n, false);
        stringBuilder.append(')');
        return this.appendTailConditions(stringBuilder, n, false);
    }

    private StringBuilder getSQLListagg(StringBuilder stringBuilder, int n) {
        stringBuilder.append("LISTAGG(");
        if (this.distinct) {
            stringBuilder.append("DISTINCT ");
        }
        this.args[0].getUnenclosedSQL(stringBuilder, n);
        ListaggArguments listaggArguments = (ListaggArguments)this.extraArguments;
        Expression expression = listaggArguments.getSeparator();
        if (expression != null) {
            expression.getUnenclosedSQL(stringBuilder.append(", "), n);
        }
        if (listaggArguments.getOnOverflowTruncate()) {
            stringBuilder.append(" ON OVERFLOW TRUNCATE ");
            expression = listaggArguments.getFilter();
            if (expression != null) {
                expression.getUnenclosedSQL(stringBuilder, n).append(' ');
            }
            stringBuilder.append(listaggArguments.isWithoutCount() ? "WITHOUT" : "WITH").append(" COUNT");
        }
        stringBuilder.append(')');
        stringBuilder.append(" WITHIN GROUP (");
        Window.appendOrderBy(stringBuilder, this.orderByList, n, true);
        stringBuilder.append(')');
        return this.appendTailConditions(stringBuilder, n, false);
    }

    private StringBuilder getSQLJsonObjectAggregate(StringBuilder stringBuilder, int n) {
        stringBuilder.append("JSON_OBJECTAGG(");
        this.args[0].getUnenclosedSQL(stringBuilder, n).append(": ");
        this.args[1].getUnenclosedSQL(stringBuilder, n);
        JsonConstructorFunction.getJsonFunctionFlagsSQL(stringBuilder, this.flags, false).append(')');
        return this.appendTailConditions(stringBuilder, n, false);
    }

    private StringBuilder getSQLJsonArrayAggregate(StringBuilder stringBuilder, int n) {
        stringBuilder.append("JSON_ARRAYAGG(");
        if (this.distinct) {
            stringBuilder.append("DISTINCT ");
        }
        this.args[0].getUnenclosedSQL(stringBuilder, n);
        JsonConstructorFunction.getJsonFunctionFlagsSQL(stringBuilder, this.flags, true);
        Window.appendOrderBy(stringBuilder, this.orderByList, n, false);
        stringBuilder.append(')');
        return this.appendTailConditions(stringBuilder, n, false);
    }

    private Index getMinMaxColumnIndex() {
        Expression expression = this.args[0];
        if (expression instanceof ExpressionColumn) {
            ExpressionColumn expressionColumn = (ExpressionColumn)expression;
            Column column = expressionColumn.getColumn();
            TableFilter tableFilter = expressionColumn.getTableFilter();
            if (tableFilter != null) {
                Table table = tableFilter.getTable();
                return table.getIndexForColumn(column, true, false);
            }
        }
        return null;
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        if (!super.isEverything(expressionVisitor)) {
            return false;
        }
        if (this.filterCondition != null && !this.filterCondition.isEverything(expressionVisitor)) {
            return false;
        }
        switch (expressionVisitor.getType()) {
            case 1: {
                switch (this.aggregateType) {
                    case COUNT: {
                        if (this.distinct || this.args[0].getNullable() != 0) {
                            return false;
                        }
                    }
                    case COUNT_ALL: {
                        return expressionVisitor.getTable().canGetRowCount(this.select.getSession());
                    }
                    case MIN: 
                    case MAX: {
                        return this.getMinMaxColumnIndex() != null;
                    }
                    case PERCENTILE_CONT: 
                    case PERCENTILE_DISC: {
                        return this.args[0].isConstant() && Percentile.getColumnIndex(this.select.getSession().getDatabase(), this.orderByList.get((int)0).expression) != null;
                    }
                    case MEDIAN: {
                        if (this.distinct) {
                            return false;
                        }
                        return Percentile.getColumnIndex(this.select.getSession().getDatabase(), this.args[0]) != null;
                    }
                    case ENVELOPE: {
                        return AggregateDataEnvelope.getGeometryColumnIndex(this.args[0]) != null;
                    }
                }
                return false;
            }
            case 2: {
                if (this.aggregateType != AggregateType.ANY_VALUE) break;
                return false;
            }
        }
        for (Expression expression : this.args) {
            if (expression.isEverything(expressionVisitor)) continue;
            return false;
        }
        if (this.orderByList != null) {
            for (QueryOrderBy queryOrderBy : this.orderByList) {
                if (queryOrderBy.expression.isEverything(expressionVisitor)) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public int getCost() {
        int n = 1;
        for (Expression expression : this.args) {
            n += expression.getCost();
        }
        if (this.orderByList != null) {
            for (QueryOrderBy queryOrderBy : this.orderByList) {
                n += queryOrderBy.expression.getCost();
            }
        }
        if (this.filterCondition != null) {
            n += this.filterCondition.getCost();
        }
        return n;
    }

    public Select getSelect() {
        return this.select;
    }

    public boolean isDistinct() {
        return this.distinct;
    }

    static {
        Aggregate.addAggregate("COUNT", AggregateType.COUNT);
        Aggregate.addAggregate("SUM", AggregateType.SUM);
        Aggregate.addAggregate("MIN", AggregateType.MIN);
        Aggregate.addAggregate("MAX", AggregateType.MAX);
        Aggregate.addAggregate("AVG", AggregateType.AVG);
        Aggregate.addAggregate("LISTAGG", AggregateType.LISTAGG);
        Aggregate.addAggregate("GROUP_CONCAT", AggregateType.LISTAGG);
        Aggregate.addAggregate("STRING_AGG", AggregateType.LISTAGG);
        Aggregate.addAggregate("STDDEV_SAMP", AggregateType.STDDEV_SAMP);
        Aggregate.addAggregate("STDDEV", AggregateType.STDDEV_SAMP);
        Aggregate.addAggregate("STDDEV_POP", AggregateType.STDDEV_POP);
        Aggregate.addAggregate("STDDEVP", AggregateType.STDDEV_POP);
        Aggregate.addAggregate("VAR_POP", AggregateType.VAR_POP);
        Aggregate.addAggregate("VARP", AggregateType.VAR_POP);
        Aggregate.addAggregate("VAR_SAMP", AggregateType.VAR_SAMP);
        Aggregate.addAggregate("VAR", AggregateType.VAR_SAMP);
        Aggregate.addAggregate("VARIANCE", AggregateType.VAR_SAMP);
        Aggregate.addAggregate("ANY_VALUE", AggregateType.ANY_VALUE);
        Aggregate.addAggregate("ANY", AggregateType.ANY);
        Aggregate.addAggregate("SOME", AggregateType.ANY);
        Aggregate.addAggregate("BOOL_OR", AggregateType.ANY);
        Aggregate.addAggregate("EVERY", AggregateType.EVERY);
        Aggregate.addAggregate("BOOL_AND", AggregateType.EVERY);
        Aggregate.addAggregate("HISTOGRAM", AggregateType.HISTOGRAM);
        Aggregate.addAggregate("BIT_AND_AGG", AggregateType.BIT_AND_AGG);
        Aggregate.addAggregate("BIT_AND", AggregateType.BIT_AND_AGG);
        Aggregate.addAggregate("BIT_OR_AGG", AggregateType.BIT_OR_AGG);
        Aggregate.addAggregate("BIT_OR", AggregateType.BIT_OR_AGG);
        Aggregate.addAggregate("BIT_XOR_AGG", AggregateType.BIT_XOR_AGG);
        Aggregate.addAggregate("BIT_NAND_AGG", AggregateType.BIT_NAND_AGG);
        Aggregate.addAggregate("BIT_NOR_AGG", AggregateType.BIT_NOR_AGG);
        Aggregate.addAggregate("BIT_XNOR_AGG", AggregateType.BIT_XNOR_AGG);
        Aggregate.addAggregate("COVAR_POP", AggregateType.COVAR_POP);
        Aggregate.addAggregate("COVAR_SAMP", AggregateType.COVAR_SAMP);
        Aggregate.addAggregate("CORR", AggregateType.CORR);
        Aggregate.addAggregate("REGR_SLOPE", AggregateType.REGR_SLOPE);
        Aggregate.addAggregate("REGR_INTERCEPT", AggregateType.REGR_INTERCEPT);
        Aggregate.addAggregate("REGR_COUNT", AggregateType.REGR_COUNT);
        Aggregate.addAggregate("REGR_R2", AggregateType.REGR_R2);
        Aggregate.addAggregate("REGR_AVGX", AggregateType.REGR_AVGX);
        Aggregate.addAggregate("REGR_AVGY", AggregateType.REGR_AVGY);
        Aggregate.addAggregate("REGR_SXX", AggregateType.REGR_SXX);
        Aggregate.addAggregate("REGR_SYY", AggregateType.REGR_SYY);
        Aggregate.addAggregate("REGR_SXY", AggregateType.REGR_SXY);
        Aggregate.addAggregate("RANK", AggregateType.RANK);
        Aggregate.addAggregate("DENSE_RANK", AggregateType.DENSE_RANK);
        Aggregate.addAggregate("PERCENT_RANK", AggregateType.PERCENT_RANK);
        Aggregate.addAggregate("CUME_DIST", AggregateType.CUME_DIST);
        Aggregate.addAggregate("PERCENTILE_CONT", AggregateType.PERCENTILE_CONT);
        Aggregate.addAggregate("PERCENTILE_DISC", AggregateType.PERCENTILE_DISC);
        Aggregate.addAggregate("MEDIAN", AggregateType.MEDIAN);
        Aggregate.addAggregate("ARRAY_AGG", AggregateType.ARRAY_AGG);
        Aggregate.addAggregate("MODE", AggregateType.MODE);
        Aggregate.addAggregate("STATS_MODE", AggregateType.MODE);
        Aggregate.addAggregate("ENVELOPE", AggregateType.ENVELOPE);
        Aggregate.addAggregate("JSON_OBJECTAGG", AggregateType.JSON_OBJECTAGG);
        Aggregate.addAggregate("JSON_ARRAYAGG", AggregateType.JSON_ARRAYAGG);
    }
}

