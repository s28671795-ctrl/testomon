/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import org.h2.util.StringUtils;

public class MultiDimension
implements Comparator<long[]> {
    private static final MultiDimension INSTANCE = new MultiDimension();

    protected MultiDimension() {
    }

    public static MultiDimension getInstance() {
        return INSTANCE;
    }

    public int normalize(int n, double d, double d2, double d3) {
        if (d < d2 || d > d3) {
            throw new IllegalArgumentException(d2 + "<" + d + "<" + d3);
        }
        double d4 = (d - d2) / (d3 - d2);
        return (int)(d4 * (double)this.getMaxValue(n));
    }

    public int getMaxValue(int n) {
        if (n < 2 || n > 32) {
            throw new IllegalArgumentException(Integer.toString(n));
        }
        int n2 = MultiDimension.getBitsPerValue(n);
        return (int)((1L << n2) - 1L);
    }

    private static int getBitsPerValue(int n) {
        return Math.min(31, 64 / n);
    }

    public long interleave(int ... nArray) {
        int n = nArray.length;
        long l = this.getMaxValue(n);
        int n2 = MultiDimension.getBitsPerValue(n);
        long l2 = 0L;
        for (int i = 0; i < n; ++i) {
            long l3 = nArray[i];
            if (l3 < 0L || l3 > l) {
                throw new IllegalArgumentException("0<" + l3 + "<" + l);
            }
            for (int j = 0; j < n2; ++j) {
                l2 |= (l3 & 1L << j) << i + (n - 1) * j;
            }
        }
        return l2;
    }

    public long interleave(int n, int n2) {
        if (n < 0) {
            throw new IllegalArgumentException("0<" + n);
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("0<" + n2);
        }
        long l = 0L;
        for (int i = 0; i < 32; ++i) {
            l |= ((long)n & 1L << i) << i;
            l |= ((long)n2 & 1L << i) << i + 1;
        }
        return l;
    }

    public int deinterleave(int n, long l, int n2) {
        int n3 = MultiDimension.getBitsPerValue(n);
        int n4 = 0;
        for (int i = 0; i < n3; ++i) {
            n4 = (int)((long)n4 | l >> n2 + (n - 1) * i & 1L << i);
        }
        return n4;
    }

    public String generatePreparedQuery(String string, String string2, String[] stringArray) {
        StringBuilder stringBuilder = new StringBuilder("SELECT D.* FROM ");
        StringUtils.quoteIdentifier(stringBuilder, string).append(" D, TABLE(_FROM_ BIGINT=?, _TO_ BIGINT=?) WHERE ");
        StringUtils.quoteIdentifier(stringBuilder, string2).append(" BETWEEN _FROM_ AND _TO_");
        for (String string3 : stringArray) {
            stringBuilder.append(" AND ");
            StringUtils.quoteIdentifier(stringBuilder, string3).append("+1 BETWEEN ?+1 AND ?+1");
        }
        return stringBuilder.toString();
    }

    public ResultSet getResult(PreparedStatement preparedStatement, int[] nArray, int[] nArray2) throws SQLException {
        int n;
        long[][] lArray = this.getMortonRanges(nArray, nArray2);
        int n2 = lArray.length;
        Long[] longArray = new Long[n2];
        Long[] longArray2 = new Long[n2];
        for (n = 0; n < n2; ++n) {
            longArray[n] = lArray[n][0];
            longArray2[n] = lArray[n][1];
        }
        preparedStatement.setObject(1, longArray);
        preparedStatement.setObject(2, longArray2);
        n2 = nArray.length;
        int n3 = 3;
        for (n = 0; n < n2; ++n) {
            preparedStatement.setInt(n3++, nArray[n]);
            preparedStatement.setInt(n3++, nArray2[n]);
        }
        return preparedStatement.executeQuery();
    }

    private long[][] getMortonRanges(int[] nArray, int[] nArray2) {
        int n;
        int n2 = nArray.length;
        if (nArray2.length != n2) {
            throw new IllegalArgumentException(n2 + "=" + nArray2.length);
        }
        for (n = 0; n < n2; ++n) {
            if (nArray[n] <= nArray2[n]) continue;
            int n3 = nArray[n];
            nArray[n] = nArray2[n];
            nArray2[n] = n3;
        }
        n = MultiDimension.getSize(nArray, nArray2, n2);
        ArrayList<long[]> arrayList = new ArrayList<long[]>();
        this.addMortonRanges(arrayList, nArray, nArray2, n2, 0);
        this.combineEntries(arrayList, n);
        return (long[][])arrayList.toArray((T[])new long[0][]);
    }

    private static int getSize(int[] nArray, int[] nArray2, int n) {
        int n2 = 1;
        for (int i = 0; i < n; ++i) {
            int n3 = nArray2[i] - nArray[i];
            n2 *= n3 + 1;
        }
        return n2;
    }

    private void combineEntries(ArrayList<long[]> arrayList, int n) {
        arrayList.sort(this);
        for (int i = 10; i < n; i += i / 2) {
            long[] lArray;
            Object object;
            int n2;
            for (n2 = 0; n2 < arrayList.size() - 1; ++n2) {
                object = arrayList.get(n2);
                if (object[1] + (long)i < (lArray = arrayList.get(n2 + 1))[0]) continue;
                object[1] = lArray[1];
                arrayList.remove(n2 + 1);
                --n2;
            }
            n2 = 0;
            object = arrayList.iterator();
            while (object.hasNext()) {
                lArray = (long[])object.next();
                n2 = (int)((long)n2 + (lArray[1] - lArray[0] + 1L));
            }
            if (n2 > 2 * n || arrayList.size() < 100) break;
        }
    }

    @Override
    public int compare(long[] lArray, long[] lArray2) {
        return lArray[0] > lArray2[0] ? 1 : -1;
    }

    private void addMortonRanges(ArrayList<long[]> arrayList, int[] nArray, int[] nArray2, int n, int n2) {
        if (n2 > 100) {
            throw new IllegalArgumentException(Integer.toString(n2));
        }
        int n3 = 0;
        int n4 = 0;
        long l = 1L;
        for (int i = 0; i < n; ++i) {
            int n5 = nArray2[i] - nArray[i];
            if (n5 < 0) {
                throw new IllegalArgumentException(Integer.toString(n5));
            }
            if ((l *= (long)(n5 + 1)) < 0L) {
                throw new IllegalArgumentException(Long.toString(l));
            }
            if (n5 <= n4) continue;
            n4 = n5;
            n3 = i;
        }
        long l2 = this.interleave(nArray);
        long l3 = this.interleave(nArray2);
        if (l3 < l2) {
            throw new IllegalArgumentException(l3 + "<" + l2);
        }
        long l4 = l3 - l2 + 1L;
        if (l4 == l) {
            long[] lArray = new long[]{l2, l3};
            arrayList.add(lArray);
        } else {
            int n6 = MultiDimension.findMiddle(nArray[n3], nArray2[n3]);
            int n7 = nArray2[n3];
            nArray2[n3] = n6;
            this.addMortonRanges(arrayList, nArray, nArray2, n, n2 + 1);
            nArray2[n3] = n7;
            n7 = nArray[n3];
            nArray[n3] = n6 + 1;
            this.addMortonRanges(arrayList, nArray, nArray2, n, n2 + 1);
            nArray[n3] = n7;
        }
    }

    private static int roundUp(int n, int n2) {
        return n + n2 - 1 & -n2;
    }

    private static int findMiddle(int n, int n2) {
        int n3;
        int n4 = n2 - n - 1;
        if (n4 == 0) {
            return n;
        }
        if (n4 == 1) {
            return n + 1;
        }
        int n5 = 0;
        while (1 << n5 < n4) {
            ++n5;
        }
        if ((n3 = MultiDimension.roundUp(n + 2, 1 << --n5) - 1) <= n || n3 >= n2) {
            throw new IllegalArgumentException(n + "<" + n3 + "<" + n2);
        }
        return n3;
    }
}

