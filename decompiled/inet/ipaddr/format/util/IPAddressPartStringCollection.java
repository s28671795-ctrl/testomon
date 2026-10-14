/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.IPAddressSection;
import inet.ipaddr.format.string.IPAddressStringDivision;
import inet.ipaddr.format.string.IPAddressStringDivisionSeries;
import inet.ipaddr.format.util.IPAddressPartConfiguredString;
import inet.ipaddr.format.util.IPAddressPartStringCollectionBase;
import inet.ipaddr.format.util.IPAddressPartStringSubCollection;
import inet.ipaddr.format.util.IPAddressStringWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class IPAddressPartStringCollection
extends IPAddressPartStringCollectionBase<IPAddressStringDivisionSeries, IPAddressStringWriter<?>, IPAddressPartConfiguredString<?, ?>> {
    private final List<IPAddressPartStringSubCollection<?, ?, ? extends IPAddressPartConfiguredString<?, ?>>> collections = new ArrayList();

    protected IPAddressPartStringCollection() {
    }

    protected void add(IPAddressPartStringSubCollection<?, ?, ? extends IPAddressPartConfiguredString<?, ?>> collection) {
        this.collections.add(collection);
    }

    protected void addAll(IPAddressPartStringCollection collections) {
        this.collections.addAll(collections.collections);
    }

    public int getPartCount() {
        return this.collections.size();
    }

    public IPAddressStringDivisionSeries getPart(int index) {
        return this.getSubCollection((int)index).part;
    }

    public IPAddressStringDivisionSeries[] getParts(IPAddressStringDivisionSeries[] array) {
        int size = this.getPartCount();
        IPAddressStringDivisionSeries[] result = array.length < size ? (IPAddressStringDivisionSeries[])Array.newInstance(array.getClass().getComponentType(), size) : array;
        int i = 0;
        for (IPAddressPartStringSubCollection<?, ?, ? extends IPAddressPartConfiguredString<?, ?>> iPAddressPartStringSubCollection : this.collections) {
            result[i++] = iPAddressPartStringSubCollection.part;
        }
        return result;
    }

    public IPAddressPartStringSubCollection<?, ?, ?> getSubCollection(IPAddressStringDivisionSeries part) {
        for (IPAddressPartStringSubCollection<?, ?, ? extends IPAddressPartConfiguredString<?, ?>> iPAddressPartStringSubCollection : this.collections) {
            if (!iPAddressPartStringSubCollection.part.equals(part)) continue;
            return iPAddressPartStringSubCollection;
        }
        return null;
    }

    public IPAddressPartStringSubCollection<?, ?, ?> getSubCollection(int index) {
        return this.collections.get(index);
    }

    @Override
    public int size() {
        int size = 0;
        for (IPAddressPartStringSubCollection<?, ?, IPAddressPartConfiguredString<?, ?>> collection : this.collections) {
            size += collection.size();
        }
        return size;
    }

    @Override
    public Iterator<IPAddressPartConfiguredString<?, ?>> iterator() {
        return new Iterator<IPAddressPartConfiguredString<?, ?>>(){
            private int i;
            private Iterator<? extends IPAddressPartConfiguredString<?, ?>> currentIterator;

            @Override
            public boolean hasNext() {
                while (true) {
                    if (this.currentIterator == null) {
                        if (this.i < IPAddressPartStringCollection.this.collections.size()) {
                            this.currentIterator = ((IPAddressPartStringSubCollection)IPAddressPartStringCollection.this.collections.get(this.i++)).iterator();
                        } else {
                            return false;
                        }
                    }
                    if (this.currentIterator.hasNext()) {
                        return true;
                    }
                    this.currentIterator = null;
                }
            }

            @Override
            public IPAddressPartConfiguredString<?, ?> next() {
                if (this.hasNext()) {
                    return this.currentIterator.next();
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                if (this.currentIterator == null) {
                    throw new IllegalStateException();
                }
                this.currentIterator.remove();
            }
        };
    }

    protected static abstract class AddressPartStringBuilder<T extends IPAddressStringDivisionSeries, P extends IPAddressStringWriter<T>, S extends IPAddressPartConfiguredString<T, P>, C extends IPAddressPartStringSubCollection<T, P, S>, O extends IPAddressSection.IPStringBuilderOptions> {
        protected static int MAX_BASE = 16;
        private int[][] leadingZeros;
        protected final T addressSection;
        protected final O options;
        protected final C collection;
        private boolean done;

        protected AddressPartStringBuilder(T addressSection, O options, C collection) {
            this.addressSection = addressSection;
            this.options = options;
            this.collection = collection;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public C getVariations() {
            if (!this.done) {
                AddressPartStringBuilder addressPartStringBuilder = this;
                synchronized (addressPartStringBuilder) {
                    if (!this.done) {
                        this.done = true;
                        this.addAllVariations();
                    }
                }
            }
            return this.collection;
        }

        protected abstract void addAllVariations();

        protected void addStringParam(P stringParams) {
            ((IPAddressPartStringSubCollection)this.collection).add(stringParams);
        }

        protected boolean isExpandable(int radix) {
            return AddressPartStringBuilder.isExpandable(radix, this.addressSection);
        }

        protected boolean isExpandableOutsideRange(int radix, int segmentIndex, int count) {
            return AddressPartStringBuilder.isExpandableOutsideRange(radix, this.addressSection, segmentIndex, count);
        }

        private static boolean isExpandable(int radix, IPAddressStringDivisionSeries part) {
            return AddressPartStringBuilder.isExpandableOutsideRange(radix, part, -1, 0);
        }

        private static boolean isExpandableOutsideRange(int radix, IPAddressStringDivisionSeries part, int segmentIndex, int count) {
            int nextSegmentIndex = segmentIndex + count;
            for (int i = 0; i < part.getDivisionCount(); ++i) {
                int maxDigitCount;
                IPAddressStringDivision div;
                int digitCount;
                if (i >= segmentIndex && i < nextSegmentIndex || (digitCount = (div = part.getDivision(i)).getDigitCount(radix)) >= (maxDigitCount = div.getMaxDigitCount(radix))) continue;
                return true;
            }
            return false;
        }

        protected int[] getExpandableSegments(int radix) {
            int[] result;
            if (this.leadingZeros == null) {
                this.leadingZeros = new int[MAX_BASE + 1][];
                result = AddressPartStringBuilder.getExpandableSegments(radix, this.addressSection);
                this.leadingZeros[radix] = result;
            } else {
                result = this.leadingZeros[radix];
                if (result == null) {
                    result = AddressPartStringBuilder.getExpandableSegments(radix, this.addressSection);
                    this.leadingZeros[radix] = result;
                }
            }
            return result;
        }

        protected static int[] getExpandableSegments(int radix, IPAddressStringDivisionSeries part) {
            int count = part.getDivisionCount();
            int[] expandables = new int[count];
            for (int i = 0; i < count; ++i) {
                int maxDigitCount;
                IPAddressStringDivision div = part.getDivision(i);
                int digitCount = div.getDigitCount(radix);
                expandables[i] = digitCount < (maxDigitCount = div.getMaxDigitCount(radix)) ? maxDigitCount - digitCount : 0;
            }
            return expandables;
        }
    }
}

