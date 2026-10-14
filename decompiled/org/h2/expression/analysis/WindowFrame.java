/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.h2.engine.SessionLocal;
import org.h2.expression.BinaryOperation;
import org.h2.expression.ValueExpression;
import org.h2.expression.analysis.Window;
import org.h2.expression.analysis.WindowFrameBound;
import org.h2.expression.analysis.WindowFrameBoundType;
import org.h2.expression.analysis.WindowFrameExclusion;
import org.h2.expression.analysis.WindowFrameUnits;
import org.h2.message.DbException;
import org.h2.result.SortOrder;
import org.h2.table.ColumnResolver;
import org.h2.value.Value;
import org.h2.value.ValueNull;

public final class WindowFrame {
    private final WindowFrameUnits units;
    private final WindowFrameBound starting;
    private final WindowFrameBound following;
    private final WindowFrameExclusion exclusion;

    public static Iterator<Value[]> iterator(Window window, SessionLocal sessionLocal, ArrayList<Value[]> arrayList, SortOrder sortOrder, int n, boolean bl) {
        WindowFrame windowFrame = window.getWindowFrame();
        if (windowFrame != null) {
            return windowFrame.iterator(sessionLocal, arrayList, sortOrder, n, bl);
        }
        int n2 = arrayList.size() - 1;
        return WindowFrame.plainIterator(arrayList, 0, window.getOrderBy() == null ? n2 : WindowFrame.toGroupEnd(arrayList, sortOrder, n, n2), bl);
    }

    public static int getEndIndex(Window window, SessionLocal sessionLocal, ArrayList<Value[]> arrayList, SortOrder sortOrder, int n) {
        WindowFrame windowFrame = window.getWindowFrame();
        if (windowFrame != null) {
            return windowFrame.getEndIndex(sessionLocal, arrayList, sortOrder, n);
        }
        int n2 = arrayList.size() - 1;
        return window.getOrderBy() == null ? n2 : WindowFrame.toGroupEnd(arrayList, sortOrder, n, n2);
    }

    private static Iterator<Value[]> plainIterator(ArrayList<Value[]> arrayList, int n, int n2, boolean bl) {
        if (n2 < n) {
            return Collections.emptyIterator();
        }
        return bl ? new PlainReverseItr(arrayList, n, n2) : new PlainItr(arrayList, n, n2);
    }

    private static Iterator<Value[]> biIterator(ArrayList<Value[]> arrayList, int n, int n2, int n3, int n4, boolean bl) {
        return bl ? new BiReverseItr(arrayList, n, n2, n3, n4) : new BiItr(arrayList, n, n2, n3, n4);
    }

    private static Iterator<Value[]> triIterator(ArrayList<Value[]> arrayList, int n, int n2, int n3, int n4, int n5, int n6, boolean bl) {
        return bl ? new TriReverseItr(arrayList, n, n2, n3, n4, n5, n6) : new TriItr(arrayList, n, n2, n3, n4, n5, n6);
    }

    private static int toGroupStart(ArrayList<Value[]> arrayList, SortOrder sortOrder, int n, int n2) {
        Value[] valueArray = arrayList.get(n);
        while (n > n2 && sortOrder.compare(valueArray, arrayList.get(n - 1)) == 0) {
            --n;
        }
        return n;
    }

    private static int toGroupEnd(ArrayList<Value[]> arrayList, SortOrder sortOrder, int n, int n2) {
        Value[] valueArray = arrayList.get(n);
        while (n < n2 && sortOrder.compare(valueArray, arrayList.get(n + 1)) == 0) {
            ++n;
        }
        return n;
    }

    private static int getIntOffset(WindowFrameBound windowFrameBound, Value[] valueArray, SessionLocal sessionLocal) {
        int n;
        Value value;
        Value value2 = value = windowFrameBound.isVariable() ? valueArray[windowFrameBound.getExpressionIndex()] : windowFrameBound.getValue().getValue(sessionLocal);
        if (value == ValueNull.INSTANCE || (n = value.getInt()) < 0) {
            throw DbException.get(22013, value.getTraceSQL());
        }
        return n;
    }

    private static Value[] getCompareRow(SessionLocal sessionLocal, ArrayList<Value[]> arrayList, SortOrder sortOrder, int n, WindowFrameBound windowFrameBound, boolean bl) {
        Object object;
        Value value;
        int n2 = sortOrder.getQueryColumnIndexes()[0];
        Value[] valueArray = arrayList.get(n);
        Value value2 = valueArray[n2];
        int n3 = value2.getValueType();
        Value value3 = WindowFrame.getValueOffset(windowFrameBound, arrayList.get(n), sessionLocal);
        switch (n3) {
            case 0: {
                value = ValueNull.INSTANCE;
                break;
            }
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: 
            case 15: 
            case 16: 
            case 17: 
            case 18: 
            case 19: 
            case 20: 
            case 21: 
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
                object = bl ^ (sortOrder.getSortTypes()[0] & 1) != 0 ? BinaryOperation.OpType.PLUS : BinaryOperation.OpType.MINUS;
                try {
                    value = new BinaryOperation((BinaryOperation.OpType)((Object)object), ValueExpression.get(value2), ValueExpression.get(value3)).optimize(sessionLocal).getValue(sessionLocal).convertTo(n3);
                    break;
                }
                catch (DbException dbException) {
                    switch (dbException.getErrorCode()) {
                        case 22003: 
                        case 22004: {
                            return null;
                        }
                    }
                    throw dbException;
                }
            }
            default: {
                throw DbException.getInvalidValueException("unsupported type of sort key for RANGE units", value2.getTraceSQL());
            }
        }
        object = (Value[])valueArray.clone();
        object[n2] = value;
        return object;
    }

    private static Value getValueOffset(WindowFrameBound windowFrameBound, Value[] valueArray, SessionLocal sessionLocal) {
        Value value;
        Value value2 = value = windowFrameBound.isVariable() ? valueArray[windowFrameBound.getExpressionIndex()] : windowFrameBound.getValue().getValue(sessionLocal);
        if (value == ValueNull.INSTANCE || value.getSignum() < 0) {
            throw DbException.get(22013, value.getTraceSQL());
        }
        return value;
    }

    public WindowFrame(WindowFrameUnits windowFrameUnits, WindowFrameBound windowFrameBound, WindowFrameBound windowFrameBound2, WindowFrameExclusion windowFrameExclusion) {
        this.units = windowFrameUnits;
        this.starting = windowFrameBound;
        if (windowFrameBound2 != null && windowFrameBound2.getType() == WindowFrameBoundType.CURRENT_ROW) {
            windowFrameBound2 = null;
        }
        this.following = windowFrameBound2;
        this.exclusion = windowFrameExclusion;
    }

    public WindowFrameUnits getUnits() {
        return this.units;
    }

    public WindowFrameBound getStarting() {
        return this.starting;
    }

    public WindowFrameBound getFollowing() {
        return this.following;
    }

    public WindowFrameExclusion getExclusion() {
        return this.exclusion;
    }

    public boolean isValid() {
        WindowFrameBoundType windowFrameBoundType = this.starting.getType();
        WindowFrameBoundType windowFrameBoundType2 = this.following != null ? this.following.getType() : WindowFrameBoundType.CURRENT_ROW;
        return windowFrameBoundType != WindowFrameBoundType.UNBOUNDED_FOLLOWING && windowFrameBoundType2 != WindowFrameBoundType.UNBOUNDED_PRECEDING && windowFrameBoundType.compareTo(windowFrameBoundType2) <= 0;
    }

    public boolean isVariableBounds() {
        if (this.starting.isVariable()) {
            return true;
        }
        return this.following != null && this.following.isVariable();
    }

    void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.starting.mapColumns(columnResolver, n, n2);
        if (this.following != null) {
            this.following.mapColumns(columnResolver, n, n2);
        }
    }

    void optimize(SessionLocal sessionLocal) {
        this.starting.optimize(sessionLocal);
        if (this.following != null) {
            this.following.optimize(sessionLocal);
        }
    }

    void updateAggregate(SessionLocal sessionLocal, int n) {
        this.starting.updateAggregate(sessionLocal, n);
        if (this.following != null) {
            this.following.updateAggregate(sessionLocal, n);
        }
    }

    public Iterator<Value[]> iterator(SessionLocal sessionLocal, ArrayList<Value[]> arrayList, SortOrder sortOrder, int n, boolean bl) {
        int n2;
        int n3 = this.getIndex(sessionLocal, arrayList, sortOrder, n, this.starting, false);
        int n4 = this.following != null ? this.getIndex(sessionLocal, arrayList, sortOrder, n, this.following, true) : (n2 = this.units == WindowFrameUnits.ROWS ? n : WindowFrame.toGroupEnd(arrayList, sortOrder, n, arrayList.size() - 1));
        if (n2 < n3) {
            return Collections.emptyIterator();
        }
        int n5 = arrayList.size();
        if (n3 >= n5 || n2 < 0) {
            return Collections.emptyIterator();
        }
        if (n3 < 0) {
            n3 = 0;
        }
        if (n2 >= n5) {
            n2 = n5 - 1;
        }
        return this.exclusion != WindowFrameExclusion.EXCLUDE_NO_OTHERS ? this.complexIterator(arrayList, sortOrder, n, n3, n2, bl) : WindowFrame.plainIterator(arrayList, n3, n2, bl);
    }

    public int getStartIndex(SessionLocal sessionLocal, ArrayList<Value[]> arrayList, SortOrder sortOrder, int n) {
        if (this.exclusion != WindowFrameExclusion.EXCLUDE_NO_OTHERS) {
            throw new UnsupportedOperationException();
        }
        int n2 = this.getIndex(sessionLocal, arrayList, sortOrder, n, this.starting, false);
        if (n2 < 0) {
            n2 = 0;
        }
        return n2;
    }

    private int getEndIndex(SessionLocal sessionLocal, ArrayList<Value[]> arrayList, SortOrder sortOrder, int n) {
        int n2;
        if (this.exclusion != WindowFrameExclusion.EXCLUDE_NO_OTHERS) {
            throw new UnsupportedOperationException();
        }
        int n3 = this.following != null ? this.getIndex(sessionLocal, arrayList, sortOrder, n, this.following, true) : (this.units == WindowFrameUnits.ROWS ? n : WindowFrame.toGroupEnd(arrayList, sortOrder, n, arrayList.size() - 1));
        if (n3 >= (n2 = arrayList.size())) {
            n3 = n2 - 1;
        }
        return n3;
    }

    private int getIndex(SessionLocal sessionLocal, ArrayList<Value[]> arrayList, SortOrder sortOrder, int n, WindowFrameBound windowFrameBound, boolean bl) {
        int n2;
        int n3 = arrayList.size();
        int n4 = n3 - 1;
        block0 : switch (windowFrameBound.getType()) {
            case UNBOUNDED_PRECEDING: {
                n2 = -1;
                break;
            }
            case PRECEDING: {
                switch (this.units) {
                    case ROWS: {
                        int n5 = WindowFrame.getIntOffset(windowFrameBound, arrayList.get(n), sessionLocal);
                        n2 = n5 > n ? -1 : n - n5;
                        break block0;
                    }
                    case GROUPS: {
                        int n6;
                        if (!bl) {
                            n2 = WindowFrame.toGroupStart(arrayList, sortOrder, n, 0);
                            for (n6 = WindowFrame.getIntOffset(windowFrameBound, arrayList.get(n), sessionLocal); n6 > 0 && n2 > 0; --n6) {
                                n2 = WindowFrame.toGroupStart(arrayList, sortOrder, n2 - 1, 0);
                            }
                            if (n6 <= 0) break block0;
                            n2 = -1;
                            break block0;
                        }
                        if (n6 == 0) {
                            n2 = WindowFrame.toGroupEnd(arrayList, sortOrder, n, n4);
                            break block0;
                        }
                        n2 = n;
                        while (n6 > 0 && n2 >= 0) {
                            --n6;
                            n2 = WindowFrame.toGroupStart(arrayList, sortOrder, n2, 0) - 1;
                        }
                        break block0;
                    }
                    case RANGE: {
                        n2 = n;
                        Value[] valueArray = WindowFrame.getCompareRow(sessionLocal, arrayList, sortOrder, n2, windowFrameBound, false);
                        if (valueArray != null) {
                            n2 = Collections.binarySearch(arrayList, valueArray, sortOrder);
                            if (n2 >= 0) {
                                if (!bl) {
                                    while (n2 > 0 && sortOrder.compare(valueArray, arrayList.get(n2 - 1)) == 0) {
                                        --n2;
                                    }
                                } else {
                                    while (n2 < n4 && sortOrder.compare(valueArray, arrayList.get(n2 + 1)) == 0) {
                                        ++n2;
                                    }
                                }
                                break block0;
                            }
                            n2 ^= 0xFFFFFFFF;
                            if (!bl) {
                                if (n2 != 0) break block0;
                                n2 = -1;
                                break block0;
                            }
                            --n2;
                            break block0;
                        }
                        n2 = -1;
                        break block0;
                    }
                    default: {
                        throw DbException.getUnsupportedException("units=" + (Object)((Object)this.units));
                    }
                }
            }
            case CURRENT_ROW: {
                switch (this.units) {
                    case ROWS: {
                        n2 = n;
                        break block0;
                    }
                    case GROUPS: 
                    case RANGE: {
                        n2 = bl ? WindowFrame.toGroupEnd(arrayList, sortOrder, n, n4) : WindowFrame.toGroupStart(arrayList, sortOrder, n, 0);
                        break block0;
                    }
                }
                throw DbException.getUnsupportedException("units=" + (Object)((Object)this.units));
            }
            case FOLLOWING: {
                switch (this.units) {
                    case ROWS: {
                        int n7 = WindowFrame.getIntOffset(windowFrameBound, arrayList.get(n), sessionLocal);
                        int n8 = n4 - n;
                        n2 = n7 > n8 ? n3 : n + n7;
                        break block0;
                    }
                    case GROUPS: {
                        int n9;
                        if (bl) {
                            n2 = WindowFrame.toGroupEnd(arrayList, sortOrder, n, n4);
                            for (n9 = WindowFrame.getIntOffset(windowFrameBound, arrayList.get(n), sessionLocal); n9 > 0 && n2 < n4; --n9) {
                                n2 = WindowFrame.toGroupEnd(arrayList, sortOrder, n2 + 1, n4);
                            }
                            if (n9 <= 0) break block0;
                            n2 = n3;
                            break block0;
                        }
                        if (n9 == 0) {
                            n2 = WindowFrame.toGroupStart(arrayList, sortOrder, n, 0);
                            break block0;
                        }
                        n2 = n;
                        while (n9 > 0 && n2 <= n4) {
                            --n9;
                            n2 = WindowFrame.toGroupEnd(arrayList, sortOrder, n2, n4) + 1;
                        }
                        break block0;
                    }
                    case RANGE: {
                        n2 = n;
                        Value[] valueArray = WindowFrame.getCompareRow(sessionLocal, arrayList, sortOrder, n2, windowFrameBound, true);
                        if (valueArray != null) {
                            n2 = Collections.binarySearch(arrayList, valueArray, sortOrder);
                            if (n2 >= 0) {
                                if (bl) {
                                    while (n2 < n4 && sortOrder.compare(valueArray, arrayList.get(n2 + 1)) == 0) {
                                        ++n2;
                                    }
                                } else {
                                    while (n2 > 0 && sortOrder.compare(valueArray, arrayList.get(n2 - 1)) == 0) {
                                        --n2;
                                    }
                                }
                                break block0;
                            }
                            if (!bl || (n2 ^= 0xFFFFFFFF) == n3) break block0;
                            --n2;
                            break block0;
                        }
                        n2 = n3;
                        break block0;
                    }
                    default: {
                        throw DbException.getUnsupportedException("units=" + (Object)((Object)this.units));
                    }
                }
            }
            case UNBOUNDED_FOLLOWING: {
                n2 = n3;
                break;
            }
            default: {
                throw DbException.getUnsupportedException("window frame bound type=" + (Object)((Object)windowFrameBound.getType()));
            }
        }
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Iterator<Value[]> complexIterator(ArrayList<Value[]> arrayList, SortOrder sortOrder, int n, int n2, int n3, boolean bl) {
        if (this.exclusion == WindowFrameExclusion.EXCLUDE_CURRENT_ROW) {
            if (n < n2 || n > n3) return WindowFrame.plainIterator(arrayList, n2, n3, bl);
            if (n == n2) {
                ++n2;
                return WindowFrame.plainIterator(arrayList, n2, n3, bl);
            } else {
                if (n != n3) return WindowFrame.biIterator(arrayList, n2, n - 1, n + 1, n3, bl);
                --n3;
            }
            return WindowFrame.plainIterator(arrayList, n2, n3, bl);
        } else {
            boolean bl2;
            int n4 = WindowFrame.toGroupStart(arrayList, sortOrder, n, n2);
            int n5 = WindowFrame.toGroupEnd(arrayList, sortOrder, n, n3);
            boolean bl3 = bl2 = this.exclusion == WindowFrameExclusion.EXCLUDE_TIES;
            if (bl2) {
                if (n == n4) {
                    ++n4;
                    bl2 = false;
                } else if (n == n5) {
                    --n5;
                    bl2 = false;
                }
            }
            if (n4 > n5 || n5 < n2 || n4 > n3) return WindowFrame.plainIterator(arrayList, n2, n3, bl);
            if (bl2) {
                if (n2 == n4) {
                    if (n3 != n5) return WindowFrame.biIterator(arrayList, n, n, n5 + 1, n3, bl);
                    return Collections.singleton(arrayList.get(n)).iterator();
                }
                if (n3 != n5) return WindowFrame.triIterator(arrayList, n2, n4 - 1, n, n, n5 + 1, n3, bl);
                return WindowFrame.biIterator(arrayList, n2, n4 - 1, n, n, bl);
            }
            if (n2 >= n4) {
                n2 = n5 + 1;
                return WindowFrame.plainIterator(arrayList, n2, n3, bl);
            } else {
                if (n3 > n5) return WindowFrame.biIterator(arrayList, n2, n4 - 1, n5 + 1, n3, bl);
                n3 = n4 - 1;
            }
        }
        return WindowFrame.plainIterator(arrayList, n2, n3, bl);
    }

    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append(this.units.getSQL());
        if (this.following == null) {
            stringBuilder.append(' ');
            this.starting.getSQL(stringBuilder, false, n);
        } else {
            stringBuilder.append(" BETWEEN ");
            this.starting.getSQL(stringBuilder, false, n).append(" AND ");
            this.following.getSQL(stringBuilder, true, n);
        }
        if (this.exclusion != WindowFrameExclusion.EXCLUDE_NO_OTHERS) {
            stringBuilder.append(' ').append(this.exclusion.getSQL());
        }
        return stringBuilder;
    }

    private static final class TriReverseItr
    extends BiReverseItr {
        private final int end2;
        private final int start2;

        TriReverseItr(ArrayList<Value[]> arrayList, int n, int n2, int n3, int n4, int n5, int n6) {
            super(arrayList, n, n2, n3, n6);
            this.end2 = n4;
            this.start2 = n5;
        }

        @Override
        public Value[] next() {
            if (this.cursor < this.startIndex) {
                throw new NoSuchElementException();
            }
            Value[] valueArray = (Value[])this.orderedRows.get(this.cursor);
            this.cursor = this.cursor != this.start1 ? (this.cursor != this.start2 ? this.cursor - 1 : this.end2) : this.end1;
            return valueArray;
        }
    }

    private static final class TriItr
    extends BiItr {
        private final int end2;
        private final int start2;

        TriItr(ArrayList<Value[]> arrayList, int n, int n2, int n3, int n4, int n5, int n6) {
            super(arrayList, n, n2, n3, n6);
            this.end2 = n4;
            this.start2 = n5;
        }

        @Override
        public Value[] next() {
            if (this.cursor > this.endIndex) {
                throw new NoSuchElementException();
            }
            Value[] valueArray = (Value[])this.orderedRows.get(this.cursor);
            this.cursor = this.cursor != this.end1 ? (this.cursor != this.end2 ? this.cursor + 1 : this.start2) : this.start1;
            return valueArray;
        }
    }

    private static class BiReverseItr
    extends PlainReverseItr {
        final int end1;
        final int start1;

        BiReverseItr(ArrayList<Value[]> arrayList, int n, int n2, int n3, int n4) {
            super(arrayList, n, n4);
            this.end1 = n2;
            this.start1 = n3;
        }

        @Override
        public Value[] next() {
            if (this.cursor < this.startIndex) {
                throw new NoSuchElementException();
            }
            Value[] valueArray = (Value[])this.orderedRows.get(this.cursor);
            this.cursor = this.cursor != this.start1 ? this.cursor - 1 : this.end1;
            return valueArray;
        }
    }

    private static class BiItr
    extends PlainItr {
        final int end1;
        final int start1;

        BiItr(ArrayList<Value[]> arrayList, int n, int n2, int n3, int n4) {
            super(arrayList, n, n4);
            this.end1 = n2;
            this.start1 = n3;
        }

        @Override
        public Value[] next() {
            if (this.cursor > this.endIndex) {
                throw new NoSuchElementException();
            }
            Value[] valueArray = (Value[])this.orderedRows.get(this.cursor);
            this.cursor = this.cursor != this.end1 ? this.cursor + 1 : this.start1;
            return valueArray;
        }
    }

    private static class PlainReverseItr
    extends Itr {
        final int startIndex;

        PlainReverseItr(ArrayList<Value[]> arrayList, int n, int n2) {
            super(arrayList);
            this.startIndex = n;
            this.cursor = n2;
        }

        @Override
        public boolean hasNext() {
            return this.cursor >= this.startIndex;
        }

        @Override
        public Value[] next() {
            if (this.cursor < this.startIndex) {
                throw new NoSuchElementException();
            }
            return (Value[])this.orderedRows.get(this.cursor--);
        }
    }

    private static class PlainItr
    extends Itr {
        final int endIndex;

        PlainItr(ArrayList<Value[]> arrayList, int n, int n2) {
            super(arrayList);
            this.endIndex = n2;
            this.cursor = n;
        }

        @Override
        public boolean hasNext() {
            return this.cursor <= this.endIndex;
        }

        @Override
        public Value[] next() {
            if (this.cursor > this.endIndex) {
                throw new NoSuchElementException();
            }
            return (Value[])this.orderedRows.get(this.cursor++);
        }
    }

    private static abstract class Itr
    implements Iterator<Value[]> {
        final ArrayList<Value[]> orderedRows;
        int cursor;

        Itr(ArrayList<Value[]> arrayList) {
            this.orderedRows = arrayList;
        }
    }
}

