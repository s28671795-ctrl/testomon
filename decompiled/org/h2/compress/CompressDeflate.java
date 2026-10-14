/*
 * Decompiled with CFR 0.152.
 */
package org.h2.compress;

import java.util.StringTokenizer;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.h2.compress.Compressor;
import org.h2.mvstore.DataUtils;

public class CompressDeflate
implements Compressor {
    private int level = -1;
    private int strategy = 0;

    @Override
    public void setOptions(String string) {
        if (string == null) {
            return;
        }
        try {
            StringTokenizer stringTokenizer = new StringTokenizer(string);
            while (stringTokenizer.hasMoreElements()) {
                String string2 = stringTokenizer.nextToken();
                if ("level".equals(string2) || "l".equals(string2)) {
                    this.level = Integer.parseInt(stringTokenizer.nextToken());
                } else if ("strategy".equals(string2) || "s".equals(string2)) {
                    this.strategy = Integer.parseInt(stringTokenizer.nextToken());
                }
                Deflater deflater = new Deflater(this.level);
                deflater.setStrategy(this.strategy);
            }
        }
        catch (Exception exception) {
            throw DataUtils.newMVStoreException(90102, string, new Object[0]);
        }
    }

    @Override
    public int compress(byte[] byArray, int n, int n2, byte[] byArray2, int n3) {
        Deflater deflater = new Deflater(this.level);
        deflater.setStrategy(this.strategy);
        deflater.setInput(byArray, n, n2);
        deflater.finish();
        int n4 = deflater.deflate(byArray2, n3, byArray2.length - n3);
        if (n4 == 0) {
            this.strategy = 0;
            this.level = -1;
            return this.compress(byArray, n, n2, byArray2, n3);
        }
        deflater.end();
        return n3 + n4;
    }

    @Override
    public int getAlgorithm() {
        return 2;
    }

    @Override
    public void expand(byte[] byArray, int n, int n2, byte[] byArray2, int n3, int n4) {
        Inflater inflater = new Inflater();
        inflater.setInput(byArray, n, n2);
        inflater.finished();
        try {
            int n5 = inflater.inflate(byArray2, n3, n4);
            if (n5 != n4) {
                throw new DataFormatException(n5 + " " + n4);
            }
        }
        catch (DataFormatException dataFormatException) {
            throw DataUtils.newMVStoreException(90104, dataFormatException.getMessage(), dataFormatException);
        }
        inflater.end();
    }
}

