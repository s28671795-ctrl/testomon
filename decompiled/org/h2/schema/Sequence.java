/*
 * Decompiled with CFR 0.152.
 */
package org.h2.schema;

import org.h2.command.ddl.SequenceOptions;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;

public final class Sequence
extends SchemaObject {
    public static final int DEFAULT_CACHE_SIZE = 32;
    private long baseValue;
    private long margin;
    private TypeInfo dataType;
    private long increment;
    private long cacheSize;
    private long startValue;
    private long minValue;
    private long maxValue;
    private Cycle cycle;
    private boolean belongsToTable;
    private boolean writeWithMargin;

    public Sequence(SessionLocal sessionLocal, Schema schema, int n, String string, SequenceOptions sequenceOptions, boolean bl) {
        super(schema, n, string, 9);
        boolean bl2;
        long l;
        long l2;
        this.dataType = sequenceOptions.getDataType();
        if (this.dataType == null) {
            this.dataType = sessionLocal.getMode().decimalSequences ? TypeInfo.TYPE_NUMERIC_BIGINT : TypeInfo.TYPE_BIGINT;
            sequenceOptions.setDataType(this.dataType);
        }
        long[] lArray = sequenceOptions.getBounds();
        Long l3 = sequenceOptions.getIncrement(sessionLocal);
        long l4 = l3 != null ? l3 : 1L;
        Long l5 = sequenceOptions.getStartValue(sessionLocal);
        Long l6 = sequenceOptions.getMinValue(null, sessionLocal);
        Long l7 = sequenceOptions.getMaxValue(null, sessionLocal);
        long l8 = l6 != null ? l6 : Sequence.getDefaultMinValue(l5, l4, lArray);
        long l9 = l2 = l7 != null ? l7 : Sequence.getDefaultMaxValue(l5, l4, lArray);
        long l10 = l5 != null ? l5 : (l4 >= 0L ? l8 : l2);
        Long l11 = sequenceOptions.getRestartValue(sessionLocal, l10);
        long l12 = l11 != null ? l11 : l10;
        l3 = sequenceOptions.getCacheSize(sessionLocal);
        if (l3 != null) {
            l = l3;
            bl2 = false;
        } else {
            l = 32L;
            bl2 = true;
        }
        l = this.checkOptions(l12, l10, l8, l2, l4, l, bl2);
        Cycle cycle = sequenceOptions.getCycle();
        if (cycle == null) {
            cycle = Cycle.NO_CYCLE;
        } else if (cycle == Cycle.EXHAUSTED) {
            l12 = l10;
        }
        this.margin = this.baseValue = l12;
        this.increment = l4;
        this.cacheSize = l;
        this.startValue = l10;
        this.minValue = l8;
        this.maxValue = l2;
        this.cycle = cycle;
        this.belongsToTable = bl;
    }

    public synchronized void modify(Long l, Long l2, Long l3, Long l4, Long l5, Cycle cycle, Long l6) {
        boolean bl;
        long l7;
        long l8;
        long l9 = l != null ? l : this.baseValue;
        long l10 = l2 != null ? l2 : this.startValue;
        long l11 = l3 != null ? l3 : this.minValue;
        long l12 = l4 != null ? l4 : this.maxValue;
        long l13 = l8 = l5 != null ? l5 : this.increment;
        if (l6 != null) {
            l7 = l6;
            bl = false;
        } else {
            l7 = this.cacheSize;
            bl = true;
        }
        l7 = this.checkOptions(l9, l10, l11, l12, l8, l7, bl);
        if (cycle == null) {
            cycle = this.cycle;
            if (cycle == Cycle.EXHAUSTED && l != null) {
                cycle = Cycle.NO_CYCLE;
            }
        } else if (cycle == Cycle.EXHAUSTED) {
            l9 = l10;
        }
        this.margin = this.baseValue = l9;
        this.startValue = l10;
        this.minValue = l11;
        this.maxValue = l12;
        this.increment = l8;
        this.cacheSize = l7;
        this.cycle = cycle;
    }

    private long checkOptions(long l, long l2, long l3, long l4, long l5, long l6, boolean bl) {
        if (l3 <= l && l <= l4 && l3 <= l2 && l2 <= l4 && l3 < l4 && l5 != 0L) {
            long l7 = l4 - l3;
            if (Long.compareUnsigned(Math.abs(l5), l7) <= 0 && l6 >= 0L) {
                if (l6 <= 1L) {
                    return 1L;
                }
                long l8 = Sequence.getMaxCacheSize(l7, l5);
                if (l6 <= l8) {
                    return l6;
                }
                if (bl) {
                    return l8;
                }
            }
        }
        throw DbException.get(90009, this.getName(), Long.toString(l), Long.toString(l2), Long.toString(l3), Long.toString(l4), Long.toString(l5), Long.toString(l6));
    }

    private static long getMaxCacheSize(long l, long l2) {
        if (l2 > 0L) {
            if (l < 0L) {
                l = Long.MAX_VALUE;
            } else if ((l += l2) < 0L) {
                l = Long.MAX_VALUE;
            }
        } else if ((l = -l) > 0L) {
            l = Long.MIN_VALUE;
        } else if ((l += l2) >= 0L) {
            l = Long.MIN_VALUE;
        }
        return l / l2;
    }

    public static long getDefaultMinValue(Long l, long l2, long[] lArray) {
        long l3;
        long l4 = l3 = l2 >= 0L ? 1L : lArray[0];
        if (l != null && l2 >= 0L && l < l3) {
            l3 = l;
        }
        return l3;
    }

    public static long getDefaultMaxValue(Long l, long l2, long[] lArray) {
        long l3;
        long l4 = l3 = l2 >= 0L ? lArray[1] : -1L;
        if (l != null && l2 < 0L && l > l3) {
            l3 = l;
        }
        return l3;
    }

    public boolean getBelongsToTable() {
        return this.belongsToTable;
    }

    public TypeInfo getDataType() {
        return this.dataType;
    }

    public int getEffectivePrecision() {
        TypeInfo typeInfo = this.dataType;
        switch (typeInfo.getValueType()) {
            case 13: {
                int n = (int)typeInfo.getPrecision();
                int n2 = typeInfo.getScale();
                if (n - n2 > 19) {
                    return 19 + n2;
                }
                return n;
            }
            case 16: {
                return Math.min((int)typeInfo.getPrecision(), 19);
            }
        }
        return (int)typeInfo.getPrecision();
    }

    public long getIncrement() {
        return this.increment;
    }

    public long getStartValue() {
        return this.startValue;
    }

    public long getMinValue() {
        return this.minValue;
    }

    public long getMaxValue() {
        return this.maxValue;
    }

    public Cycle getCycle() {
        return this.cycle;
    }

    @Override
    public String getDropSQL() {
        if (this.getBelongsToTable()) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder("DROP SEQUENCE IF EXISTS ");
        return this.getSQL(stringBuilder, 0).toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getCreateSQL() {
        StringBuilder stringBuilder = this.getSQL(new StringBuilder("CREATE SEQUENCE "), 0);
        if (this.dataType.getValueType() != 12) {
            this.dataType.getSQL(stringBuilder.append(" AS "), 0);
        }
        stringBuilder.append(' ');
        Sequence sequence2 = this;
        synchronized (sequence2) {
            this.getSequenceOptionsSQL(stringBuilder, this.writeWithMargin ? this.margin : this.baseValue);
        }
        if (this.belongsToTable) {
            stringBuilder.append(" BELONGS_TO_TABLE");
        }
        return stringBuilder.toString();
    }

    public synchronized StringBuilder getSequenceOptionsSQL(StringBuilder stringBuilder) {
        return this.getSequenceOptionsSQL(stringBuilder, this.baseValue);
    }

    private StringBuilder getSequenceOptionsSQL(StringBuilder stringBuilder, long l) {
        stringBuilder.append("START WITH ").append(this.startValue);
        if (l != this.startValue && this.cycle != Cycle.EXHAUSTED) {
            stringBuilder.append(" RESTART WITH ").append(l);
        }
        if (this.increment != 1L) {
            stringBuilder.append(" INCREMENT BY ").append(this.increment);
        }
        long[] lArray = SequenceOptions.getBounds(this.dataType);
        if (this.minValue != Sequence.getDefaultMinValue(l, this.increment, lArray)) {
            stringBuilder.append(" MINVALUE ").append(this.minValue);
        }
        if (this.maxValue != Sequence.getDefaultMaxValue(l, this.increment, lArray)) {
            stringBuilder.append(" MAXVALUE ").append(this.maxValue);
        }
        if (this.cycle == Cycle.CYCLE) {
            stringBuilder.append(" CYCLE");
        } else if (this.cycle == Cycle.EXHAUSTED) {
            stringBuilder.append(" EXHAUSTED");
        }
        if (this.cacheSize != 32L) {
            if (this.cacheSize == 1L) {
                stringBuilder.append(" NO CACHE");
            } else if (this.cacheSize > 32L || this.cacheSize != Sequence.getMaxCacheSize(this.maxValue - this.minValue, this.increment)) {
                stringBuilder.append(" CACHE ").append(this.cacheSize);
            }
        }
        return stringBuilder;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Value getNext(SessionLocal sessionLocal) {
        boolean bl;
        long l;
        Sequence sequence2 = this;
        synchronized (sequence2) {
            if (this.cycle == Cycle.EXHAUSTED) {
                throw DbException.get(90006, this.getName());
            }
            l = this.baseValue;
            long l2 = l + this.increment;
            bl = this.increment > 0L ? this.increment(l, l2) : this.decrement(l, l2);
        }
        if (bl) {
            this.flush(sessionLocal);
        }
        return ValueBigint.get(l).castTo(this.dataType, sessionLocal);
    }

    private boolean increment(long l, long l2) {
        boolean bl = false;
        if (l2 > this.maxValue || ((l ^ 0xFFFFFFFFFFFFFFFFL) & l2) < 0L) {
            l2 = this.minValue;
            bl = true;
            if (this.cycle == Cycle.CYCLE) {
                this.margin = l2 + this.increment * (this.cacheSize - 1L);
            } else {
                this.margin = l2;
                this.cycle = Cycle.EXHAUSTED;
            }
        } else if (l2 > this.margin) {
            long l3 = l2 + this.increment * (this.cacheSize - 1L);
            if (l3 > this.maxValue || ((l2 ^ 0xFFFFFFFFFFFFFFFFL) & l3) < 0L) {
                l3 = l2;
            }
            this.margin = l3;
            bl = true;
        }
        this.baseValue = l2;
        return bl;
    }

    private boolean decrement(long l, long l2) {
        boolean bl = false;
        if (l2 < this.minValue || (l & (l2 ^ 0xFFFFFFFFFFFFFFFFL)) < 0L) {
            l2 = this.maxValue;
            bl = true;
            if (this.cycle == Cycle.CYCLE) {
                this.margin = l2 + this.increment * (this.cacheSize - 1L);
            } else {
                this.margin = l2;
                this.cycle = Cycle.EXHAUSTED;
            }
        } else if (l2 < this.margin) {
            long l3 = l2 + this.increment * (this.cacheSize - 1L);
            if (l3 < this.minValue || (l2 & (l3 ^ 0xFFFFFFFFFFFFFFFFL)) < 0L) {
                l3 = l2;
            }
            this.margin = l3;
            bl = true;
        }
        this.baseValue = l2;
        return bl;
    }

    public void flushWithoutMargin() {
        if (this.margin != this.baseValue) {
            this.margin = this.baseValue;
            this.flush(null);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void flush(SessionLocal sessionLocal) {
        if (this.isTemporary()) {
            return;
        }
        if (sessionLocal == null || !this.database.isSysTableLockedBy(sessionLocal)) {
            SessionLocal sessionLocal2 = this.database.getSystemSession();
            sessionLocal2.lock();
            try {
                this.flushInternal(sessionLocal2);
                sessionLocal2.commit(false);
            }
            finally {
                sessionLocal2.unlock();
            }
        }
        sessionLocal.lock();
        try {
            this.flushInternal(sessionLocal);
        }
        finally {
            sessionLocal.unlock();
        }
    }

    private void flushInternal(SessionLocal sessionLocal) {
        boolean bl = this.database.lockMeta(sessionLocal);
        try {
            this.writeWithMargin = true;
            this.database.updateMeta(sessionLocal, this);
        }
        finally {
            this.writeWithMargin = false;
            if (!bl) {
                this.database.unlockMeta(sessionLocal);
            }
        }
    }

    public void close() {
        this.flushWithoutMargin();
    }

    @Override
    public int getType() {
        return 3;
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        this.database.removeMeta(sessionLocal, this.getId());
        this.invalidate();
    }

    public synchronized long getBaseValue() {
        return this.baseValue;
    }

    public synchronized long getCurrentValue() {
        return this.baseValue - this.increment;
    }

    public void setBelongsToTable(boolean bl) {
        this.belongsToTable = bl;
    }

    public long getCacheSize() {
        return this.cacheSize;
    }

    public static enum Cycle {
        CYCLE,
        NO_CYCLE,
        EXHAUSTED;


        public boolean isCycle() {
            return this == CYCLE;
        }
    }
}

