/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv.bean;

import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.ArrayUtils;

public class HeaderIndex {
    private String[] positionToHeader = ArrayUtils.EMPTY_STRING_ARRAY;
    private MultiValuedMap<String, Integer> headerToPosition = new ArrayListValuedHashMap<String, Integer>();

    public void clear() {
        this.positionToHeader = ArrayUtils.EMPTY_STRING_ARRAY;
        this.headerToPosition.clear();
    }

    public int findMaxIndex() {
        return this.positionToHeader.length - 1;
    }

    public void initializeHeaderIndex(String[] header) {
        this.positionToHeader = header != null ? ArrayUtils.clone(header) : ArrayUtils.EMPTY_STRING_ARRAY;
        this.headerToPosition.clear();
        for (int i = 0; i < this.positionToHeader.length; ++i) {
            this.headerToPosition.put(header[i], i);
        }
    }

    public boolean isEmpty() {
        return this.positionToHeader.length == 0;
    }

    public int[] getByName(String headerName) {
        Collection<Integer> positions = this.headerToPosition.get(headerName);
        if (positions != null) {
            return ArrayUtils.toPrimitive(positions.toArray(ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY));
        }
        return ArrayUtils.EMPTY_INT_ARRAY;
    }

    public String getByPosition(int i) {
        if (i < this.positionToHeader.length) {
            return this.positionToHeader[i];
        }
        return null;
    }

    public String[] getHeaderIndex() {
        return ArrayUtils.clone(this.positionToHeader);
    }

    public int getHeaderIndexLength() {
        return this.positionToHeader.length;
    }

    public void put(int k, String v) {
        if (k >= this.positionToHeader.length) {
            this.positionToHeader = Arrays.copyOf(this.positionToHeader, k + 1);
            this.positionToHeader[k] = v;
        }
        this.headerToPosition.put(v, k);
    }
}

