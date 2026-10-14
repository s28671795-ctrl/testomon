/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util.geometry;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import org.h2.util.StringUtils;
import org.h2.util.geometry.EWKBUtils;
import org.h2.util.geometry.GeometryUtils;

public final class EWKTUtils {
    static final String[] TYPES = new String[]{"POINT", "LINESTRING", "POLYGON", "MULTIPOINT", "MULTILINESTRING", "MULTIPOLYGON", "GEOMETRYCOLLECTION"};
    private static final String[] DIMENSION_SYSTEMS = new String[]{"XY", "Z", "M", "ZM"};

    public static String ewkb2ewkt(byte[] byArray) {
        return EWKTUtils.ewkb2ewkt(byArray, EWKBUtils.getDimensionSystem(byArray));
    }

    public static String ewkb2ewkt(byte[] byArray, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        EWKBUtils.parseEWKB(byArray, new EWKTTarget(stringBuilder, n));
        return stringBuilder.toString();
    }

    public static byte[] ewkt2ewkb(String string) {
        return EWKTUtils.ewkt2ewkb(string, EWKTUtils.getDimensionSystem(string));
    }

    public static byte[] ewkt2ewkb(String string, int n) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        EWKBUtils.EWKBTarget eWKBTarget = new EWKBUtils.EWKBTarget(byteArrayOutputStream, n);
        EWKTUtils.parseEWKT(string, eWKBTarget);
        return byteArrayOutputStream.toByteArray();
    }

    public static void parseEWKT(String string, GeometryUtils.Target target) {
        EWKTUtils.parseEWKT(new EWKTSource(string), target, 0, 0);
    }

    public static int parseGeometryType(String string) {
        EWKTSource eWKTSource = new EWKTSource(string);
        int n = eWKTSource.readType();
        int n2 = 0;
        if (eWKTSource.hasData()) {
            n2 = eWKTSource.readDimensionSystem();
            if (eWKTSource.hasData()) {
                throw new IllegalArgumentException();
            }
        }
        return n2 * 1000 + n;
    }

    public static int parseDimensionSystem(String string) {
        EWKTSource eWKTSource = new EWKTSource(string);
        int n = eWKTSource.readDimensionSystem();
        if (eWKTSource.hasData() || n == 0) {
            throw new IllegalArgumentException();
        }
        return n;
    }

    public static StringBuilder formatGeometryTypeAndDimensionSystem(StringBuilder stringBuilder, int n) {
        int n2 = n % 1000;
        int n3 = n / 1000;
        if (n2 < 1 || n2 > 7 || n3 < 0 || n3 > 3) {
            throw new IllegalArgumentException();
        }
        stringBuilder.append(TYPES[n2 - 1]);
        if (n3 != 0) {
            stringBuilder.append(' ').append(DIMENSION_SYSTEMS[n3]);
        }
        return stringBuilder;
    }

    private static void parseEWKT(EWKTSource eWKTSource, GeometryUtils.Target target, int n, int n2) {
        int n3;
        if (n == 0) {
            target.init(eWKTSource.readSRID());
        }
        switch (n) {
            default: {
                n3 = eWKTSource.readType();
                n2 = eWKTSource.readDimensionSystem();
                break;
            }
            case 4: {
                n3 = 1;
                break;
            }
            case 5: {
                n3 = 2;
                break;
            }
            case 6: {
                n3 = 3;
            }
        }
        target.dimensionSystem(n2);
        switch (n3) {
            case 1: {
                if (n != 0 && n != 4 && n != 7) {
                    throw new IllegalArgumentException();
                }
                boolean bl = eWKTSource.readEmpty();
                target.startPoint();
                if (bl) {
                    target.addCoordinate(Double.NaN, Double.NaN, Double.NaN, Double.NaN, 0, 1);
                    break;
                }
                EWKTUtils.addCoordinate(eWKTSource, target, n2, 0, 1);
                eWKTSource.read(')');
                break;
            }
            case 2: {
                if (n != 0 && n != 5 && n != 7) {
                    throw new IllegalArgumentException();
                }
                boolean bl = eWKTSource.readEmpty();
                if (bl) {
                    target.startLineString(0);
                    break;
                }
                ArrayList<double[]> arrayList = new ArrayList<double[]>();
                do {
                    arrayList.add(EWKTUtils.readCoordinate(eWKTSource, n2));
                } while (eWKTSource.hasMoreCoordinates());
                int n4 = arrayList.size();
                if (n4 < 0 || n4 == 1) {
                    throw new IllegalArgumentException();
                }
                target.startLineString(n4);
                for (int i = 0; i < n4; ++i) {
                    double[] dArray = (double[])arrayList.get(i);
                    target.addCoordinate(dArray[0], dArray[1], dArray[2], dArray[3], i, n4);
                }
                break;
            }
            case 3: {
                if (n != 0 && n != 6 && n != 7) {
                    throw new IllegalArgumentException();
                }
                boolean bl = eWKTSource.readEmpty();
                if (bl) {
                    target.startPolygon(0, 0);
                    break;
                }
                ArrayList<double[]> arrayList = EWKTUtils.readRing(eWKTSource, n2);
                ArrayList<ArrayList<double[]>> arrayList2 = new ArrayList<ArrayList<double[]>>();
                while (eWKTSource.hasMoreCoordinates()) {
                    arrayList2.add(EWKTUtils.readRing(eWKTSource, n2));
                }
                int n5 = arrayList2.size();
                int n6 = arrayList.size();
                if (n6 >= 1 && n6 <= 3) {
                    throw new IllegalArgumentException();
                }
                if (n6 == 0 && n5 > 0) {
                    throw new IllegalArgumentException();
                }
                target.startPolygon(n5, n6);
                if (n6 <= 0) break;
                EWKTUtils.addRing(arrayList, target);
                for (int i = 0; i < n5; ++i) {
                    ArrayList arrayList3 = (ArrayList)arrayList2.get(i);
                    n6 = arrayList3.size();
                    if (n6 >= 1 && n6 <= 3) {
                        throw new IllegalArgumentException();
                    }
                    target.startPolygonInner(n6);
                    EWKTUtils.addRing(arrayList3, target);
                }
                target.endNonEmptyPolygon();
                break;
            }
            case 4: 
            case 5: 
            case 6: {
                EWKTUtils.parseCollection(eWKTSource, target, n3, n, n2);
                break;
            }
            case 7: {
                EWKTUtils.parseCollection(eWKTSource, target, 7, n, 0);
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
        target.endObject(n3);
        if (n == 0 && eWKTSource.hasData()) {
            throw new IllegalArgumentException();
        }
    }

    private static void parseCollection(EWKTSource eWKTSource, GeometryUtils.Target target, int n, int n2, int n3) {
        if (n2 != 0 && n2 != 7) {
            throw new IllegalArgumentException();
        }
        if (eWKTSource.readEmpty()) {
            target.startCollection(n, 0);
        } else if (n == 4 && eWKTSource.hasCoordinate()) {
            EWKTUtils.parseMultiPointAlternative(eWKTSource, target, n3);
        } else {
            int n4 = eWKTSource.getItemCount();
            target.startCollection(n, n4);
            for (int i = 0; i < n4; ++i) {
                if (i > 0) {
                    eWKTSource.read(',');
                }
                GeometryUtils.Target target2 = target.startCollectionItem(i, n4);
                EWKTUtils.parseEWKT(eWKTSource, target2, n, n3);
                target.endCollectionItem(target2, n, i, n4);
            }
            eWKTSource.read(')');
        }
    }

    private static void parseMultiPointAlternative(EWKTSource eWKTSource, GeometryUtils.Target target, int n) {
        ArrayList<double[]> arrayList = new ArrayList<double[]>();
        do {
            arrayList.add(EWKTUtils.readCoordinate(eWKTSource, n));
        } while (eWKTSource.hasMoreCoordinates());
        int n2 = arrayList.size();
        target.startCollection(4, n2);
        for (int i = 0; i < arrayList.size(); ++i) {
            GeometryUtils.Target target2 = target.startCollectionItem(i, n2);
            target.startPoint();
            double[] dArray = (double[])arrayList.get(i);
            target.addCoordinate(dArray[0], dArray[1], dArray[2], dArray[3], 0, 1);
            target.endCollectionItem(target2, 4, i, n2);
        }
    }

    private static ArrayList<double[]> readRing(EWKTSource eWKTSource, int n) {
        if (eWKTSource.readEmpty()) {
            return new ArrayList<double[]>(0);
        }
        ArrayList<double[]> arrayList = new ArrayList<double[]>();
        double[] dArray = EWKTUtils.readCoordinate(eWKTSource, n);
        double d = dArray[0];
        double d2 = dArray[1];
        arrayList.add(dArray);
        while (eWKTSource.hasMoreCoordinates()) {
            arrayList.add(EWKTUtils.readCoordinate(eWKTSource, n));
        }
        int n2 = arrayList.size();
        if (n2 < 4) {
            throw new IllegalArgumentException();
        }
        dArray = (double[])arrayList.get(n2 - 1);
        double d3 = dArray[0];
        double d4 = dArray[1];
        if (d != d3 || d2 != d4) {
            throw new IllegalArgumentException();
        }
        return arrayList;
    }

    private static void addRing(ArrayList<double[]> arrayList, GeometryUtils.Target target) {
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            double[] dArray = arrayList.get(i);
            target.addCoordinate(dArray[0], dArray[1], dArray[2], dArray[3], i, n);
        }
    }

    private static void addCoordinate(EWKTSource eWKTSource, GeometryUtils.Target target, int n, int n2, int n3) {
        double d = eWKTSource.readCoordinate();
        double d2 = eWKTSource.readCoordinate();
        double d3 = (n & 1) != 0 ? eWKTSource.readCoordinate() : Double.NaN;
        double d4 = (n & 2) != 0 ? eWKTSource.readCoordinate() : Double.NaN;
        target.addCoordinate(d, d2, d3, d4, n2, n3);
    }

    private static double[] readCoordinate(EWKTSource eWKTSource, int n) {
        double d = eWKTSource.readCoordinate();
        double d2 = eWKTSource.readCoordinate();
        double d3 = (n & 1) != 0 ? eWKTSource.readCoordinate() : Double.NaN;
        double d4 = (n & 2) != 0 ? eWKTSource.readCoordinate() : Double.NaN;
        return new double[]{d, d2, d3, d4};
    }

    public static int getDimensionSystem(String string) {
        EWKTSource eWKTSource = new EWKTSource(string);
        eWKTSource.readSRID();
        eWKTSource.readType();
        return eWKTSource.readDimensionSystem();
    }

    private EWKTUtils() {
    }

    private static final class EWKTSource {
        private final String ewkt;
        private int offset;

        EWKTSource(String string) {
            this.ewkt = string;
        }

        int readSRID() {
            int n;
            this.skipWS();
            if (this.ewkt.regionMatches(true, this.offset, "SRID=", 0, 5)) {
                this.offset += 5;
                int n2 = this.ewkt.indexOf(59, 5);
                if (n2 < 0) {
                    throw new IllegalArgumentException();
                }
                int n3 = n2;
                while (this.ewkt.charAt(n3 - 1) <= ' ') {
                    --n3;
                }
                n = Integer.parseInt(StringUtils.trimSubstring(this.ewkt, this.offset, n3));
                this.offset = n2 + 1;
            } else {
                n = 0;
            }
            return n;
        }

        void read(char c) {
            this.skipWS();
            int n = this.ewkt.length();
            if (this.offset >= n) {
                throw new IllegalArgumentException();
            }
            if (this.ewkt.charAt(this.offset) != c) {
                throw new IllegalArgumentException();
            }
            ++this.offset;
        }

        int readType() {
            this.skipWS();
            int n = this.ewkt.length();
            if (this.offset >= n) {
                throw new IllegalArgumentException();
            }
            int n2 = 0;
            char c = this.ewkt.charAt(this.offset);
            switch (c) {
                case 'P': 
                case 'p': {
                    n2 = this.match("POINT", 1);
                    if (n2 != 0) break;
                    n2 = this.match("POLYGON", 3);
                    break;
                }
                case 'L': 
                case 'l': {
                    n2 = this.match("LINESTRING", 2);
                    break;
                }
                case 'M': 
                case 'm': {
                    if (this.match("MULTI", 1) == 0 || (n2 = this.match("POINT", 4)) != 0 || (n2 = this.match("POLYGON", 6)) != 0) break;
                    n2 = this.match("LINESTRING", 5);
                    break;
                }
                case 'G': 
                case 'g': {
                    n2 = this.match("GEOMETRYCOLLECTION", 7);
                }
            }
            if (n2 == 0) {
                throw new IllegalArgumentException();
            }
            return n2;
        }

        int readDimensionSystem() {
            int n;
            int n2 = this.offset;
            this.skipWS();
            int n3 = this.ewkt.length();
            if (this.offset >= n3) {
                throw new IllegalArgumentException();
            }
            char c = this.ewkt.charAt(this.offset);
            switch (c) {
                case 'M': 
                case 'm': {
                    n = 2;
                    ++this.offset;
                    break;
                }
                case 'Z': 
                case 'z': {
                    ++this.offset;
                    if (this.offset >= n3) {
                        n = 1;
                        break;
                    }
                    c = this.ewkt.charAt(this.offset);
                    if (c == 'M' || c == 'm') {
                        ++this.offset;
                        n = 3;
                        break;
                    }
                    n = 1;
                    break;
                }
                default: {
                    n = 0;
                    if (n2 == this.offset) break;
                    return n;
                }
            }
            this.checkStringEnd(n3);
            return n;
        }

        boolean readEmpty() {
            this.skipWS();
            int n = this.ewkt.length();
            if (this.offset >= n) {
                throw new IllegalArgumentException();
            }
            if (this.ewkt.charAt(this.offset) == '(') {
                ++this.offset;
                return false;
            }
            if (this.match("EMPTY", 1) != 0) {
                this.checkStringEnd(n);
                return true;
            }
            throw new IllegalArgumentException();
        }

        private int match(String string, int n) {
            int n2 = string.length();
            if (this.offset <= this.ewkt.length() - n2 && this.ewkt.regionMatches(true, this.offset, string, 0, n2)) {
                this.offset += n2;
            } else {
                n = 0;
            }
            return n;
        }

        private void checkStringEnd(int n) {
            char c;
            if (this.offset < n && (c = this.ewkt.charAt(this.offset)) > ' ' && c != '(' && c != ')' && c != ',') {
                throw new IllegalArgumentException();
            }
        }

        public boolean hasCoordinate() {
            this.skipWS();
            if (this.offset >= this.ewkt.length()) {
                return false;
            }
            return EWKTSource.isNumberStart(this.ewkt.charAt(this.offset));
        }

        public double readCoordinate() {
            this.skipWS();
            int n = this.ewkt.length();
            if (this.offset >= n) {
                throw new IllegalArgumentException();
            }
            char c = this.ewkt.charAt(this.offset);
            if (!EWKTSource.isNumberStart(c)) {
                throw new IllegalArgumentException();
            }
            int n2 = this.offset++;
            while (this.offset < n && EWKTSource.isNumberPart(c = this.ewkt.charAt(this.offset))) {
                ++this.offset;
            }
            if (this.offset < n && c > ' ' && c != ')' && c != ',') {
                throw new IllegalArgumentException();
            }
            Double d = Double.parseDouble(this.ewkt.substring(n2, this.offset));
            return d == 0.0 ? 0.0 : d;
        }

        private static boolean isNumberStart(char c) {
            if (c >= '0' && c <= '9') {
                return true;
            }
            switch (c) {
                case '+': 
                case '-': 
                case '.': {
                    return true;
                }
            }
            return false;
        }

        private static boolean isNumberPart(char c) {
            if (c >= '0' && c <= '9') {
                return true;
            }
            switch (c) {
                case '+': 
                case '-': 
                case '.': 
                case 'E': 
                case 'e': {
                    return true;
                }
            }
            return false;
        }

        public boolean hasMoreCoordinates() {
            this.skipWS();
            if (this.offset >= this.ewkt.length()) {
                throw new IllegalArgumentException();
            }
            switch (this.ewkt.charAt(this.offset)) {
                case ',': {
                    ++this.offset;
                    return true;
                }
                case ')': {
                    ++this.offset;
                    return false;
                }
            }
            throw new IllegalArgumentException();
        }

        boolean hasData() {
            this.skipWS();
            return this.offset < this.ewkt.length();
        }

        int getItemCount() {
            int n = 1;
            int n2 = this.offset;
            int n3 = 0;
            int n4 = this.ewkt.length();
            while (n2 < n4) {
                switch (this.ewkt.charAt(n2++)) {
                    case ',': {
                        if (n3 != 0) break;
                        ++n;
                        break;
                    }
                    case '(': {
                        ++n3;
                        break;
                    }
                    case ')': {
                        if (--n3 >= 0) break;
                        return n;
                    }
                }
            }
            throw new IllegalArgumentException();
        }

        private void skipWS() {
            int n = this.ewkt.length();
            while (this.offset < n && this.ewkt.charAt(this.offset) <= ' ') {
                ++this.offset;
            }
        }

        public String toString() {
            return new StringBuilder(this.ewkt.length() + 3).append(this.ewkt, 0, this.offset).append("<*>").append(this.ewkt, this.offset, this.ewkt.length()).toString();
        }
    }

    public static final class EWKTTarget
    extends GeometryUtils.Target {
        private final StringBuilder output;
        private final int dimensionSystem;
        private int type;
        private boolean inMulti;

        public EWKTTarget(StringBuilder stringBuilder, int n) {
            this.output = stringBuilder;
            this.dimensionSystem = n;
        }

        @Override
        protected void init(int n) {
            if (n != 0) {
                this.output.append("SRID=").append(n).append(';');
            }
        }

        @Override
        protected void startPoint() {
            this.writeHeader(1);
        }

        @Override
        protected void startLineString(int n) {
            this.writeHeader(2);
            if (n == 0) {
                this.output.append("EMPTY");
            }
        }

        @Override
        protected void startPolygon(int n, int n2) {
            this.writeHeader(3);
            if (n2 == 0) {
                this.output.append("EMPTY");
            } else {
                this.output.append('(');
            }
        }

        @Override
        protected void startPolygonInner(int n) {
            this.output.append(n > 0 ? ", " : ", EMPTY");
        }

        @Override
        protected void endNonEmptyPolygon() {
            this.output.append(')');
        }

        @Override
        protected void startCollection(int n, int n2) {
            this.writeHeader(n);
            if (n2 == 0) {
                this.output.append("EMPTY");
            }
            if (n != 7) {
                this.inMulti = true;
            }
        }

        private void writeHeader(int n) {
            this.type = n;
            if (this.inMulti) {
                return;
            }
            this.output.append(TYPES[n - 1]);
            switch (this.dimensionSystem) {
                case 1: {
                    this.output.append(" Z");
                    break;
                }
                case 2: {
                    this.output.append(" M");
                    break;
                }
                case 3: {
                    this.output.append(" ZM");
                }
            }
            this.output.append(' ');
        }

        @Override
        protected GeometryUtils.Target startCollectionItem(int n, int n2) {
            if (n == 0) {
                this.output.append('(');
            } else {
                this.output.append(", ");
            }
            return this;
        }

        @Override
        protected void endCollectionItem(GeometryUtils.Target target, int n, int n2, int n3) {
            if (n2 + 1 == n3) {
                this.output.append(')');
            }
        }

        @Override
        protected void endObject(int n) {
            switch (n) {
                case 4: 
                case 5: 
                case 6: {
                    this.inMulti = false;
                }
            }
        }

        @Override
        protected void addCoordinate(double d, double d2, double d3, double d4, int n, int n2) {
            if (this.type == 1 && Double.isNaN(d) && Double.isNaN(d2) && Double.isNaN(d3) && Double.isNaN(d4)) {
                this.output.append("EMPTY");
                return;
            }
            if (n == 0) {
                this.output.append('(');
            } else {
                this.output.append(", ");
            }
            this.writeDouble(d);
            this.output.append(' ');
            this.writeDouble(d2);
            if ((this.dimensionSystem & 1) != 0) {
                this.output.append(' ');
                this.writeDouble(d3);
            }
            if ((this.dimensionSystem & 2) != 0) {
                this.output.append(' ');
                this.writeDouble(d4);
            }
            if (n + 1 == n2) {
                this.output.append(')');
            }
        }

        private void writeDouble(double d) {
            String string = Double.toString(GeometryUtils.checkFinite(d));
            if (string.endsWith(".0")) {
                this.output.append(string, 0, string.length() - 2);
            } else {
                int n = string.indexOf(".0E");
                if (n < 0) {
                    this.output.append(string);
                } else {
                    this.output.append(string, 0, n).append(string, n + 2, string.length());
                }
            }
        }
    }
}

