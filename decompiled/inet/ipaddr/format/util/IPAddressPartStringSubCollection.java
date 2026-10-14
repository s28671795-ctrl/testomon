/*
 * Decompiled with CFR 0.152.
 */
package inet.ipaddr.format.util;

import inet.ipaddr.format.string.IPAddressStringDivisionSeries;
import inet.ipaddr.format.util.IPAddressPartConfiguredString;
import inet.ipaddr.format.util.IPAddressPartStringCollectionBase;
import inet.ipaddr.format.util.IPAddressStringWriter;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class IPAddressPartStringSubCollection<T extends IPAddressStringDivisionSeries, P extends IPAddressStringWriter<T>, S extends IPAddressPartConfiguredString<T, P>>
extends IPAddressPartStringCollectionBase<T, P, S> {
    public final T part;
    protected ArrayList<P> params = new ArrayList();

    protected IPAddressPartStringSubCollection(T part) {
        this.part = part;
    }

    void add(P stringParams) {
        this.params.add(stringParams);
    }

    public P[] getParams(P[] array) {
        return (IPAddressStringWriter[])this.params.toArray(array);
    }

    public int getParamCount() {
        return this.params.size();
    }

    @Override
    public int size() {
        return this.params.size();
    }

    protected abstract class IPAddressConfigurableStringIterator
    implements Iterator<S> {
        protected Iterator<P> iterator;

        protected IPAddressConfigurableStringIterator() {
            this.iterator = IPAddressPartStringSubCollection.this.params.iterator();
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public void remove() {
            this.iterator.remove();
        }
    }
}

