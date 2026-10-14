/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.geometry;

import java.io.ByteArrayOutputStream;
import org.h2.util.Bits;
import org.h2.util.StringUtils;
import org.h2.util.geometry.GeometryUtils;

public final class EWKBUtils {
    public static final int EWKB_Z = Integer.MIN_VALUE;
    public static final int EWKB_M = 0x40000000;
    public static final int EWKB_SRID = 0x20000000;

    public static byte[] ewkb2ewkb(byte[] byArray) {
        return EWKBUtils.ewkb2ewkb(byArray, EWKBUtils.getDimensionSystem(byArray));
    }

    public static byte[] ewkb2ewkb(byte[] byArray, int n) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        EWKBTarget eWKBTarget = new EWKBTarget(byteArrayOutputStream, n);
        EWKBUtils.parseEWKB(byArray, eWKBTarget);
        return byteArrayOutputStream.toByteArray();
    }

    public static void parseEWKB(byte[] byArray, GeometryUtils.Target target) {
        try {
            EWKBUtils.parseEWKB(new EWKBSource(byArray), target, 0);
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new IllegalArgumentException();
        }
    }

    public static int type2dimensionSystem(int n) {
        boolean bl = (n & Integer.MIN_VALUE) != 0;
        boolean bl2 = (n & 0x40000000) != 0;
        switch ((n &= 0xFFFF) / 1000) {
            case 1: {
                bl = true;
                break;
            }
            case 3: {
                bl = true;
            }
            case 2: {
                bl2 = true;
            }
        }
        return (bl ? 1 : 0) | (bl2 ? 2 : 0);
    }

    private static void parseEWKB(EWKBSource eWKBSource, GeometryUtils.Target target, int n) {
        int n2;
        switch (eWKBSource.readByte()) {
            case 0: {
                eWKBSource.bigEndian = true;
                break;
            }
            case 1: {
                eWKBSource.bigEndian = false;
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
        int n3 = eWKBSource.readInt();
        boolean bl = (n3 & Integer.MIN_VALUE) != 0;
        boolean bl2 = (n3 & 0x40000000) != 0;
        int n4 = n2 = (n3 & 0x20000000) != 0 ? eWKBSource.readInt() : 0;
        if (n == 0) {
            target.init(n2);
        }
        switch ((n3 &= 0xFFFF) / 1000) {
            case 1: {
                bl = true;
                break;
            }
            case 3: {
                bl = true;
            }
            case 2: {
                bl2 = true;
            }
        }
        target.dimensionSystem((bl ? 1 : 0) | (bl2 ? 2 : 0));
        switch (n3 %= 1000) {
            case 1: {
                if (n != 0 && n != 4 && n != 7) {
                    throw new IllegalArgumentException();
                }
                target.startPoint();
                EWKBUtils.addCoordinate(eWKBSource, target, bl, bl2, 0, 1);
                break;
            }
            case 2: {
                if (n != 0 && n != 5 && n != 7) {
                    throw new IllegalArgumentException();
                }
                int n5 = eWKBSource.readInt();
                if (n5 < 0 || n5 == 1) {
                    throw new IllegalArgumentException();
                }
                target.startLineString(n5);
                for (int i = 0; i < n5; ++i) {
                    EWKBUtils.addCoordinate(eWKBSource, target, bl, bl2, i, n5);
                }
                break;
            }
            case 3: {
                if (n != 0 && n != 6 && n != 7) {
                    throw new IllegalArgumentException();
                }
                int n6 = eWKBSource.readInt();
                if (n6 == 0) {
                    target.startPolygon(0, 0);
                    break;
                }
                if (n6 < 0) {
                    throw new IllegalArgumentException();
                }
                --n6;
                int n7 = eWKBSource.readInt();
                if (n7 < 0 || n7 >= 1 && n7 <= 3) {
                    throw new IllegalArgumentException();
                }
                if (n7 == 0 && n6 > 0) {
                    throw new IllegalArgumentException();
                }
                target.startPolygon(n6, n7);
                if (n7 <= 0) break;
                EWKBUtils.addRing(eWKBSource, target, bl, bl2, n7);
                for (int i = 0; i < n6; ++i) {
                    n7 = eWKBSource.readInt();
                    if (n7 < 0 || n7 >= 1 && n7 <= 3) {
                        throw new IllegalArgumentException();
                    }
                    target.startPolygonInner(n7);
                    EWKBUtils.addRing(eWKBSource, target, bl, bl2, n7);
                }
                target.endNonEmptyPolygon();
                break;
            }
            case 4: 
            case 5: 
            case 6: 
            case 7: {
                if (n != 0 && n != 7) {
                    throw new IllegalArgumentException();
                }
                int n8 = eWKBSource.readInt();
                if (n8 < 0) {
                    throw new IllegalArgumentException();
                }
                target.startCollection(n3, n8);
                for (int i = 0; i < n8; ++i) {
                    GeometryUtils.Target target2 = target.startCollectionItem(i, n8);
                    EWKBUtils.parseEWKB(eWKBSource, target2, n3);
                    target.endCollectionItem(target2, n3, i, n8);
                }
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
        target.endObject(n3);
    }

    private static void addRing(EWKBSource eWKBSource, GeometryUtils.Target target, boolean bl, boolean bl2, int n) {
        if (n >= 4) {
            double d = eWKBSource.readCoordinate();
            double d2 = eWKBSource.readCoordinate();
            target.addCoordinate(d, d2, bl ? eWKBSource.readCoordinate() : Double.NaN, bl2 ? eWKBSource.readCoordinate() : Double.NaN, 0, n);
            for (int i = 1; i < n - 1; ++i) {
                EWKBUtils.addCoordinate(eWKBSource, target, bl, bl2, i, n);
            }
            double d3 = eWKBSource.readCoordinate();
            double d4 = eWKBSource.readCoordinate();
            if (d != d3 || d2 != d4) {
                throw new IllegalArgumentException();
            }
            target.addCoordinate(d3, d4, bl ? eWKBSource.readCoordinate() : Double.NaN, bl2 ? eWKBSource.readCoordinate() : Double.NaN, n - 1, n);
        }
    }

    private static void addCoordinate(EWKBSource eWKBSource, GeometryUtils.Target target, boolean bl, boolean bl2, int n, int n2) {
        target.addCoordinate(eWKBSource.readCoordinate(), eWKBSource.readCoordinate(), bl ? eWKBSource.readCoordinate() : Double.NaN, bl2 ? eWKBSource.readCoordinate() : Double.NaN, n, n2);
    }

    public static int getDimensionSystem(byte[] byArray) {
        EWKBSource eWKBSource = new EWKBSource(byArray);
        switch (eWKBSource.readByte()) {
            case 0: {
                eWKBSource.bigEndian = true;
                break;
            }
            case 1: {
                eWKBSource.bigEndian = false;
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
        return EWKBUtils.type2dimensionSystem(eWKBSource.readInt());
    }

    public static byte[] envelope2wkb(double[] dArray) {
        byte[] byArray;
        if (dArray == null) {
            return null;
        }
        double d = dArray[0];
        double d2 = dArray[1];
        double d3 = dArray[2];
        double d4 = dArray[3];
        if (d == d2 && d3 == d4) {
            byArray = new byte[21];
            byArray[4] = 1;
            Bits.writeDouble(byArray, 5, d);
            Bits.writeDouble(byArray, 13, d3);
        } else if (d == d2 || d3 == d4) {
            byArray = new byte[41];
            byArray[4] = 2;
            byArray[8] = 2;
            Bits.writeDouble(byArray, 9, d);
            Bits.writeDouble(byArray, 17, d3);
            Bits.writeDouble(byArray, 25, d2);
            Bits.writeDouble(byArray, 33, d4);
        } else {
            byArray = new byte[93];
            byArray[4] = 3;
            byArray[8] = 1;
            byArray[12] = 5;
            Bits.writeDouble(byArray, 13, d);
            Bits.writeDouble(byArray, 21, d3);
            Bits.writeDouble(byArray, 29, d);
            Bits.writeDouble(byArray, 37, d4);
            Bits.writeDouble(byArray, 45, d2);
            Bits.writeDouble(byArray, 53, d4);
            Bits.writeDouble(byArray, 61, d2);
            Bits.writeDouble(byArray, 69, d3);
            Bits.writeDouble(byArray, 77, d);
            Bits.writeDouble(byArray, 85, d3);
        }
        return byArray;
    }

    private EWKBUtils() {
    }

    private static final class EWKBSource {
        private final byte[] ewkb;
        private int offset;
        boolean bigEndian;

        EWKBSource(byte[] byArray) {
            this.ewkb = byArray;
        }

        byte readByte() {
            return this.ewkb[this.offset++];
        }

        int readInt() {
            int n = this.bigEndian ? Bits.readInt(this.ewkb, this.offset) : Bits.readIntLE(this.ewkb, this.offset);
            this.offset += 4;
            return n;
        }

        double readCoordinate() {
            double d = this.bigEndian ? Bits.readDouble(this.ewkb, this.offset) : Bits.readDoubleLE(this.ewkb, this.offset);
            this.offset += 8;
            return GeometryUtils.toCanonicalDouble(d);
        }

        public String toString() {
            String string = StringUtils.convertBytesToHex(this.ewkb);
            int n = this.offset * 2;
            return new StringBuilder(string.length() + 3).append(string, 0, n).append("<*>").append(string, n, string.length()).toString();
        }
    }

    public static final class EWKBTarget
    extends GeometryUtils.Target {
        private final ByteArrayOutputStream output;
        private final int dimensionSystem;
        private final byte[] buf = new byte[8];
        private int type;
        private int srid;

        public EWKBTarget(ByteArrayOutputStream byteArrayOutputStream, int n) {
            this.output = byteArrayOutputStream;
            this.dimensionSystem = n;
        }

        @Override
        protected void init(int n) {
            this.srid = n;
        }

        @Override
        protected void startPoint() {
            this.writeHeader(1);
        }

        @Override
        protected void startLineString(int n) {
            this.writeHeader(2);
            this.writeInt(n);
        }

        @Override
        protected void startPolygon(int n, int n2) {
            this.writeHeader(3);
            if (n == 0 && n2 == 0) {
                this.writeInt(0);
            } else {
                this.writeInt(n + 1);
                this.writeInt(n2);
            }
        }

        @Override
        protected void startPolygonInner(int n) {
            this.writeInt(n);
        }

        @Override
        protected void startCollection(int n, int n2) {
            this.writeHeader(n);
            this.writeInt(n2);
        }

        private void writeHeader(int n) {
            this.type = n;
            switch (this.dimensionSystem) {
                case 1: {
                    n |= Integer.MIN_VALUE;
                    break;
                }
                case 3: {
                    n |= Integer.MIN_VALUE;
                }
                case 2: {
                    n |= 0x40000000;
                }
            }
            if (this.srid != 0) {
                n |= 0x20000000;
            }
            this.output.write(0);
            this.writeInt(n);
            if (this.srid != 0) {
                this.writeInt(this.srid);
                this.srid = 0;
            }
        }

        @Override
        protected GeometryUtils.Target startCollectionItem(int n, int n2) {
            return this;
        }

        @Override
        protected void addCoordinate(double d, double d2, double d3, double d4, int n, int n2) {
            boolean bl;
            boolean bl2 = bl = this.type != 1 || !Double.isNaN(d) || !Double.isNaN(d2) || !Double.isNaN(d3) || !Double.isNaN(d4);
            if (bl) {
                GeometryUtils.checkFinite(d);
                GeometryUtils.checkFinite(d2);
            }
            this.writeDouble(d);
            this.writeDouble(d2);
            if ((this.dimensionSystem & 1) != 0) {
                this.writeDouble(bl ? GeometryUtils.checkFinite(d3) : d3);
            } else if (bl && !Double.isNaN(d3)) {
                throw new IllegalArgumentException();
            }
            if ((this.dimensionSystem & 2) != 0) {
                this.writeDouble(bl ? GeometryUtils.checkFinite(d4) : d4);
            } else if (bl && !Double.isNaN(d4)) {
                throw new IllegalArgumentException();
            }
        }

        private void writeInt(int n) {
            Bits.writeInt(this.buf, 0, n);
            this.output.write(this.buf, 0, 4);
        }

        private void writeDouble(double d) {
            d = GeometryUtils.toCanonicalDouble(d);
            Bits.writeDouble(this.buf, 0, d);
            this.output.write(this.buf, 0, 8);
        }
    }
}

