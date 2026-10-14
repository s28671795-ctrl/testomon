/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.BoxesRunTime;

public final class StringParsers$ {
    public static final StringParsers$ MODULE$ = new StringParsers$();

    private final int intOverflowBoundary() {
        return -214748364;
    }

    private final int intOverflowDigit() {
        return 9;
    }

    private final long longOverflowBoundary() {
        return -922337203685477580L;
    }

    private final int longOverflowDigit() {
        return 9;
    }

    private final int decValue(char ch) {
        return Character.digit(ch, 10);
    }

    private final Option<Object> stepToOverflow(String from, int len, int agg, boolean isPositive, int min2) {
        return this.rec$1(1, agg, min2, len, isPositive, from);
    }

    private final boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public final Option<Object> parseBool(String from) {
        if (from.equalsIgnoreCase("true")) {
            return new Some<Object>(true);
        }
        if (from.equalsIgnoreCase("false")) {
            return new Some<Object>(false);
        }
        return None$.MODULE$;
    }

    /*
     * WARNING - void declaration
     */
    public final Option<Object> parseByte(String from) {
        int len = from.length();
        if (len == 0) {
            return None$.MODULE$;
        }
        char first = from.charAt(0);
        int v = Character.digit(first, 10);
        if (len == 1) {
            if (v > -1) {
                return new Some<Object>((byte)v);
            }
            return None$.MODULE$;
        }
        if (v > -1) {
            void stepToOverflow_isPositive;
            void stepToOverflow_min;
            int n = -128;
            boolean bl = true;
            int stepToOverflow_agg = -v;
            Option option = this.rec$1(1, stepToOverflow_agg, (int)stepToOverflow_min, len, (boolean)stepToOverflow_isPositive, from);
            if (option == null) {
                throw null;
            }
            Option map_this = option;
            if (map_this.isEmpty()) {
                return None$.MODULE$;
            }
            return new Some<Object>((byte)BoxesRunTime.unboxToInt(map_this.get()));
        }
        if (first == '+') {
            void stepToOverflow_isPositive;
            void stepToOverflow_min;
            int n = -128;
            boolean bl = true;
            int stepToOverflow_agg = 0;
            Option option = this.rec$1(1, stepToOverflow_agg, (int)stepToOverflow_min, len, (boolean)stepToOverflow_isPositive, from);
            if (option == null) {
                throw null;
            }
            Option map_this = option;
            if (map_this.isEmpty()) {
                return None$.MODULE$;
            }
            return new Some<Object>((byte)BoxesRunTime.unboxToInt(map_this.get()));
        }
        if (first == '-') {
            void stepToOverflow_isPositive;
            void stepToOverflow_min;
            int n = -128;
            boolean bl = false;
            int stepToOverflow_agg = 0;
            Option option = this.rec$1(1, stepToOverflow_agg, (int)stepToOverflow_min, len, (boolean)stepToOverflow_isPositive, from);
            if (option == null) {
                throw null;
            }
            Option map_this = option;
            if (map_this.isEmpty()) {
                return None$.MODULE$;
            }
            return new Some<Object>((byte)BoxesRunTime.unboxToInt(map_this.get()));
        }
        return None$.MODULE$;
    }

    /*
     * WARNING - void declaration
     */
    public final Option<Object> parseShort(String from) {
        int len = from.length();
        if (len == 0) {
            return None$.MODULE$;
        }
        char first = from.charAt(0);
        int v = Character.digit(first, 10);
        if (len == 1) {
            if (v > -1) {
                return new Some<Object>((short)v);
            }
            return None$.MODULE$;
        }
        if (v > -1) {
            void stepToOverflow_isPositive;
            void stepToOverflow_min;
            int n = Short.MIN_VALUE;
            boolean bl = true;
            int stepToOverflow_agg = -v;
            Option option = this.rec$1(1, stepToOverflow_agg, (int)stepToOverflow_min, len, (boolean)stepToOverflow_isPositive, from);
            if (option == null) {
                throw null;
            }
            Option map_this = option;
            if (map_this.isEmpty()) {
                return None$.MODULE$;
            }
            return new Some<Object>((short)BoxesRunTime.unboxToInt(map_this.get()));
        }
        if (first == '+') {
            void stepToOverflow_isPositive;
            void stepToOverflow_min;
            int n = Short.MIN_VALUE;
            boolean bl = true;
            int stepToOverflow_agg = 0;
            Option option = this.rec$1(1, stepToOverflow_agg, (int)stepToOverflow_min, len, (boolean)stepToOverflow_isPositive, from);
            if (option == null) {
                throw null;
            }
            Option map_this = option;
            if (map_this.isEmpty()) {
                return None$.MODULE$;
            }
            return new Some<Object>((short)BoxesRunTime.unboxToInt(map_this.get()));
        }
        if (first == '-') {
            void stepToOverflow_isPositive;
            void stepToOverflow_min;
            int n = Short.MIN_VALUE;
            boolean bl = false;
            int stepToOverflow_agg = 0;
            Option option = this.rec$1(1, stepToOverflow_agg, (int)stepToOverflow_min, len, (boolean)stepToOverflow_isPositive, from);
            if (option == null) {
                throw null;
            }
            Option map_this = option;
            if (map_this.isEmpty()) {
                return None$.MODULE$;
            }
            return new Some<Object>((short)BoxesRunTime.unboxToInt(map_this.get()));
        }
        return None$.MODULE$;
    }

    public final Option<Object> parseInt(String from) {
        int len = from.length();
        if (len == 0) {
            return None$.MODULE$;
        }
        char first = from.charAt(0);
        int v = Character.digit(first, 10);
        if (len == 1) {
            if (v > -1) {
                return new Some<Object>(v);
            }
            return None$.MODULE$;
        }
        if (v > -1) {
            return this.step$1(1, -v, true, len, from);
        }
        if (first == '+') {
            return this.step$1(1, 0, true, len, from);
        }
        if (first == '-') {
            return this.step$1(1, 0, false, len, from);
        }
        return None$.MODULE$;
    }

    public final Option<Object> parseLong(String from) {
        int len = from.length();
        if (len == 0) {
            return None$.MODULE$;
        }
        char first = from.charAt(0);
        long v = Character.digit(first, 10);
        if (len == 1) {
            if (v > -1L) {
                return new Some<Object>(v);
            }
            return None$.MODULE$;
        }
        if (v > -1L) {
            return this.step$2(1, -v, true, len, from);
        }
        if (first == '+') {
            return this.step$2(1, 0L, true, len, from);
        }
        if (first == '-') {
            return this.step$2(1, 0L, false, len, from);
        }
        return None$.MODULE$;
    }

    public final boolean checkFloatFormat(String format) {
        int unsigned;
        int n;
        int unspacedStart;
        block9: {
            int n2;
            block8: {
                int x$2 = 0;
                int indexWhere$extension_len = format.length();
                for (int indexWhere$extension_i = x$2; indexWhere$extension_i < indexWhere$extension_len; ++indexWhere$extension_i) {
                    if (!StringParsers$.$anonfun$checkFloatFormat$12(format.charAt(indexWhere$extension_i))) continue;
                    n2 = indexWhere$extension_i;
                    break block8;
                }
                n2 = -1;
            }
            unspacedStart = n2;
            int x$4 = Integer.MAX_VALUE;
            int lastIndexWhere$extension_len = format.length();
            int lastIndexWhere$extension_min_y = lastIndexWhere$extension_len - 1;
            for (int lastIndexWhere$extension_i = Math.min(x$4, lastIndexWhere$extension_min_y); lastIndexWhere$extension_i >= 0; --lastIndexWhere$extension_i) {
                if (!StringParsers$.$anonfun$checkFloatFormat$13(format.charAt(lastIndexWhere$extension_i))) continue;
                n = lastIndexWhere$extension_i;
                break block9;
            }
            n = -1;
        }
        int unspacedEnd = n + 1;
        if (unspacedStart == -1 || unspacedStart >= unspacedEnd || unspacedEnd <= 0) {
            return false;
        }
        char startchar = format.charAt(unspacedStart);
        int n3 = unsigned = startchar == '-' || startchar == '+' ? unspacedStart + 1 : unspacedStart;
        if (unsigned >= unspacedEnd) {
            return false;
        }
        if (format.charAt(unsigned) == 'N') {
            String string = format.substring(unsigned, unspacedEnd);
            String string2 = "NaN";
            return string != null && string.equals(string2);
        }
        if (format.charAt(unsigned) == 'I') {
            String string = format.substring(unsigned, unspacedEnd);
            String string3 = "Infinity";
            return string != null && string.equals(string3);
        }
        char endchar = format.charAt(unspacedEnd - 1);
        int desuffixed = endchar == 'f' || endchar == 'F' || endchar == 'd' || endchar == 'D' ? unspacedEnd - 1 : unspacedEnd;
        int len = desuffixed - unsigned;
        if (len <= 0) {
            return false;
        }
        if (len >= 2 && (format.charAt(unsigned + 1) == 'x' || format.charAt(unsigned + 1) == 'X')) {
            return format.charAt(unsigned) == '0' && this.isHexFloatLiteral$1(unsigned + 2, desuffixed, format);
        }
        return this.isDecFloatLiteral$1(unsigned, desuffixed, format);
    }

    public Option<Object> parseFloat(String from) {
        if (this.checkFloatFormat(from)) {
            return new Some<Object>(Float.valueOf(Float.parseFloat(from)));
        }
        return None$.MODULE$;
    }

    public Option<Object> parseDouble(String from) {
        if (this.checkFloatFormat(from)) {
            return new Some<Object>(Double.parseDouble(from));
        }
        return None$.MODULE$;
    }

    private final Option rec$1(int i, int agg, int min$1, int len$1, boolean isPositive$1, String from$1) {
        while (agg >= min$1) {
            if (i == len$1) {
                if (!isPositive$1) {
                    return new Some<Integer>(agg);
                }
                if (agg == min$1) {
                    return None$.MODULE$;
                }
                return new Some<Integer>(-agg);
            }
            int digit = Character.digit(from$1.charAt(i), 10);
            if (digit == -1) {
                return None$.MODULE$;
            }
            agg = agg * 10 - digit;
            ++i;
        }
        return None$.MODULE$;
    }

    public static final /* synthetic */ byte $anonfun$parseByte$1(int x$1) {
        return (byte)x$1;
    }

    public static final /* synthetic */ byte $anonfun$parseByte$2(int x$2) {
        return (byte)x$2;
    }

    public static final /* synthetic */ byte $anonfun$parseByte$3(int x$3) {
        return (byte)x$3;
    }

    public static final /* synthetic */ short $anonfun$parseShort$1(int x$4) {
        return (short)x$4;
    }

    public static final /* synthetic */ short $anonfun$parseShort$2(int x$5) {
        return (short)x$5;
    }

    public static final /* synthetic */ short $anonfun$parseShort$3(int x$6) {
        return (short)x$6;
    }

    private final Option step$1(int i, int agg, boolean isPositive, int len$2, String from$2) {
        while (true) {
            if (i == len$2) {
                if (!isPositive) {
                    return new Some<Integer>(agg);
                }
                if (agg == Integer.MIN_VALUE) {
                    return None$.MODULE$;
                }
                return new Some<Integer>(-agg);
            }
            if (agg < -214748364) {
                return None$.MODULE$;
            }
            int digit = Character.digit(from$2.charAt(i), 10);
            if (digit == -1 || agg == -214748364 && digit == 9) {
                return None$.MODULE$;
            }
            agg = agg * 10 - digit;
            ++i;
        }
    }

    private final Option step$2(int i, long agg, boolean isPositive, int len$3, String from$3) {
        while (true) {
            if (i == len$3) {
                if (isPositive && agg == Long.MIN_VALUE) {
                    return None$.MODULE$;
                }
                if (isPositive) {
                    return new Some<Long>(-agg);
                }
                return new Some<Long>(agg);
            }
            if (agg < -922337203685477580L) {
                return None$.MODULE$;
            }
            int digit = Character.digit(from$3.charAt(i), 10);
            if (digit == -1 || agg == -922337203685477580L && digit == 9) {
                return None$.MODULE$;
            }
            agg = agg * 10L - (long)digit;
            ++i;
        }
    }

    private final boolean rec$2(int i, int end$1, Function1 pred$1, String format$1) {
        block2: {
            while (i < end$1) {
                if (BoxesRunTime.unboxToBoolean(pred$1.apply(Character.valueOf(format$1.charAt(i))))) {
                    ++i;
                    continue;
                }
                break block2;
            }
            return true;
        }
        return false;
    }

    private final boolean forAllBetween$1(int start2, int end, Function1 pred, String format$1) {
        block2: {
            for (int rec$2_i = start2; rec$2_i < end; ++rec$2_i) {
                if (BoxesRunTime.unboxToBoolean(pred.apply(Character.valueOf(format$1.charAt(rec$2_i))))) {
                    continue;
                }
                break block2;
            }
            return true;
        }
        return false;
    }

    private final int rec$3(int i, int until$1, Function1 predicate$1, String format$1) {
        while (i < until$1 && BoxesRunTime.unboxToBoolean(predicate$1.apply(Character.valueOf(format$1.charAt(i))))) {
            ++i;
        }
        return i;
    }

    private final int skipIndexWhile$1(Function1 predicate, int from, int until, String format$1) {
        int rec$3_i;
        for (rec$3_i = from; rec$3_i < until && BoxesRunTime.unboxToBoolean(predicate.apply(Character.valueOf(format$1.charAt(rec$3_i)))); ++rec$3_i) {
        }
        return rec$3_i;
    }

    private static final boolean isHexDigit$1(char ch) {
        return ch >= '0' && ch <= '9' || ch >= 'a' && ch <= 'f' || ch >= 'A' && ch <= 'F';
    }

    /*
     * WARNING - void declaration
     */
    private final boolean prefixOK$1(int startIndex, int endIndex, String format$1) {
        block12: {
            block15: {
                boolean bl;
                block16: {
                    block11: {
                        void var6_6;
                        block13: {
                            boolean bl2;
                            block14: {
                                block10: {
                                    int len = endIndex - startIndex;
                                    if (len <= 0) break block12;
                                    if (format$1.charAt(startIndex) != '.') break block13;
                                    if (len <= 1) break block12;
                                    for (int forAllBetween$1_rec$2_i = startIndex + 1; forAllBetween$1_rec$2_i < endIndex; ++forAllBetween$1_rec$2_i) {
                                        if (StringParsers$.isHexDigit$1(format$1.charAt(forAllBetween$1_rec$2_i))) {
                                            continue;
                                        }
                                        break block10;
                                    }
                                    bl2 = true;
                                    break block14;
                                }
                                bl2 = false;
                            }
                            if (bl2) break block15;
                            break block12;
                        }
                        for (int skipIndexWhile$1_rec$3_i = startIndex; skipIndexWhile$1_rec$3_i < endIndex && StringParsers$.isHexDigit$1(format$1.charAt(skipIndexWhile$1_rec$3_i)); ++skipIndexWhile$1_rec$3_i) {
                        }
                        if (var6_6 >= endIndex) break block15;
                        if (format$1.charAt((int)var6_6) != '.') break block12;
                        for (void forAllBetween$1_rec$2_i = var6_6 + true; forAllBetween$1_rec$2_i < endIndex; ++forAllBetween$1_rec$2_i) {
                            if (StringParsers$.isHexDigit$1(format$1.charAt((int)forAllBetween$1_rec$2_i))) {
                                continue;
                            }
                            break block11;
                        }
                        bl = true;
                        break block16;
                    }
                    bl = false;
                }
                if (!bl) break block12;
            }
            return true;
        }
        return false;
    }

    private final boolean postfixOK$1(int startIndex, int endIndex, String format$1) {
        block10: {
            block12: {
                boolean bl;
                block13: {
                    block9: {
                        boolean bl2;
                        block11: {
                            block8: {
                                if (startIndex >= endIndex) break block10;
                                for (int forAllBetween$1_rec$2_i = startIndex; forAllBetween$1_rec$2_i < endIndex; ++forAllBetween$1_rec$2_i) {
                                    if (StringParsers$.MODULE$.isDigit(format$1.charAt(forAllBetween$1_rec$2_i))) {
                                        continue;
                                    }
                                    break block8;
                                }
                                bl2 = true;
                                break block11;
                            }
                            bl2 = false;
                        }
                        if (bl2) break block12;
                        char startchar = format$1.charAt(startIndex);
                        if (startchar != '+' && startchar != '-' || endIndex - startIndex <= 1) break block10;
                        for (int forAllBetween$1_rec$2_i = startIndex + 1; forAllBetween$1_rec$2_i < endIndex; ++forAllBetween$1_rec$2_i) {
                            if (StringParsers$.MODULE$.isDigit(format$1.charAt(forAllBetween$1_rec$2_i))) {
                                continue;
                            }
                            break block9;
                        }
                        bl = true;
                        break block13;
                    }
                    bl = false;
                }
                if (!bl) break block10;
            }
            return true;
        }
        return false;
    }

    public static final /* synthetic */ boolean $anonfun$checkFloatFormat$6(char ch) {
        return ch == 'p' || ch == 'P';
    }

    private final boolean isHexFloatLiteral$1(int startIndex, int endIndex, String format$1) {
        int pIndex;
        block1: {
            int n;
            int indexWhere$extension_len = format$1.length();
            for (int indexWhere$extension_i = startIndex; indexWhere$extension_i < indexWhere$extension_len; ++indexWhere$extension_i) {
                if (!StringParsers$.$anonfun$checkFloatFormat$6(format$1.charAt(indexWhere$extension_i))) continue;
                n = indexWhere$extension_i;
                break block1;
            }
            n = pIndex = -1;
        }
        return pIndex <= endIndex && this.prefixOK$1(startIndex, pIndex, format$1) && this.postfixOK$1(pIndex + 1, endIndex, format$1);
    }

    private static final boolean isExp$1(char c) {
        return c == 'e' || c == 'E';
    }

    private final boolean expOK$1(int startIndex, int endIndex, String format$1) {
        block6: {
            block8: {
                int skipIndexWhile$1_rec$3_i;
                block7: {
                    int skipIndexWhile$1_rec$3_i2;
                    if (startIndex >= endIndex) break block6;
                    char startChar = format$1.charAt(startIndex);
                    if (startChar != '+' && startChar != '-') break block7;
                    if (endIndex <= startIndex + 1) break block6;
                    for (skipIndexWhile$1_rec$3_i2 = startIndex + 1; skipIndexWhile$1_rec$3_i2 < endIndex && StringParsers$.MODULE$.isDigit(format$1.charAt(skipIndexWhile$1_rec$3_i2)); ++skipIndexWhile$1_rec$3_i2) {
                    }
                    if (skipIndexWhile$1_rec$3_i2 == endIndex) break block8;
                    break block6;
                }
                for (skipIndexWhile$1_rec$3_i = startIndex; skipIndexWhile$1_rec$3_i < endIndex && StringParsers$.MODULE$.isDigit(format$1.charAt(skipIndexWhile$1_rec$3_i)); ++skipIndexWhile$1_rec$3_i) {
                }
                if (skipIndexWhile$1_rec$3_i != endIndex) break block6;
            }
            return true;
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    private final boolean isDecFloatLiteral$1(int startIndex, int endIndex, String format$1) {
        block7: {
            block10: {
                block8: {
                    void var6_6;
                    block9: {
                        void var7_7;
                        char startChar = format$1.charAt(startIndex);
                        if (startChar == '.') {
                            void var5_5;
                            for (int skipIndexWhile$1_rec$3_i = startIndex + 1; skipIndexWhile$1_rec$3_i < endIndex && StringParsers$.MODULE$.isDigit(format$1.charAt(skipIndexWhile$1_rec$3_i)); ++skipIndexWhile$1_rec$3_i) {
                            }
                            return var5_5 > startIndex + 1 && (var5_5 >= endIndex || StringParsers$.isExp$1(format$1.charAt((int)var5_5)) && this.expOK$1((int)(var5_5 + true), endIndex, format$1));
                        }
                        if (!(startChar >= '0' && startChar <= '9')) break block7;
                        for (int skipIndexWhile$1_rec$3_i = startIndex; skipIndexWhile$1_rec$3_i < endIndex && StringParsers$.MODULE$.isDigit(format$1.charAt(skipIndexWhile$1_rec$3_i)); ++skipIndexWhile$1_rec$3_i) {
                        }
                        if (var6_6 == endIndex) break block8;
                        if (format$1.charAt((int)var6_6) != '.') break block9;
                        for (void skipIndexWhile$1_rec$3_i = var6_6 + true; skipIndexWhile$1_rec$3_i < endIndex && StringParsers$.MODULE$.isDigit(format$1.charAt((int)skipIndexWhile$1_rec$3_i)); ++skipIndexWhile$1_rec$3_i) {
                        }
                        if (var7_7 >= endIndex || StringParsers$.isExp$1(format$1.charAt((int)var7_7)) && this.expOK$1((int)(var7_7 + true), endIndex, format$1)) break block8;
                        break block10;
                    }
                    if (!StringParsers$.isExp$1(format$1.charAt((int)var6_6)) || !this.expOK$1((int)(var6_6 + true), endIndex, format$1)) break block10;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public static final /* synthetic */ boolean $anonfun$checkFloatFormat$12(char ch) {
        return ch > ' ';
    }

    public static final /* synthetic */ boolean $anonfun$checkFloatFormat$13(char ch) {
        return ch > ' ';
    }

    private StringParsers$() {
    }

    public static final /* synthetic */ Object $anonfun$parseByte$1$adapted(Object x$1) {
        return BoxesRunTime.boxToByte(StringParsers$.$anonfun$parseByte$1(BoxesRunTime.unboxToInt(x$1)));
    }

    public static final /* synthetic */ Object $anonfun$parseByte$2$adapted(Object x$2) {
        return BoxesRunTime.boxToByte(StringParsers$.$anonfun$parseByte$2(BoxesRunTime.unboxToInt(x$2)));
    }

    public static final /* synthetic */ Object $anonfun$parseByte$3$adapted(Object x$3) {
        return BoxesRunTime.boxToByte(StringParsers$.$anonfun$parseByte$3(BoxesRunTime.unboxToInt(x$3)));
    }

    public static final /* synthetic */ Object $anonfun$parseShort$1$adapted(Object x$4) {
        return BoxesRunTime.boxToShort(StringParsers$.$anonfun$parseShort$1(BoxesRunTime.unboxToInt(x$4)));
    }

    public static final /* synthetic */ Object $anonfun$parseShort$2$adapted(Object x$5) {
        return BoxesRunTime.boxToShort(StringParsers$.$anonfun$parseShort$2(BoxesRunTime.unboxToInt(x$5)));
    }

    public static final /* synthetic */ Object $anonfun$parseShort$3$adapted(Object x$6) {
        return BoxesRunTime.boxToShort(StringParsers$.$anonfun$parseShort$3(BoxesRunTime.unboxToInt(x$6)));
    }

    public static final /* synthetic */ Object $anonfun$checkFloatFormat$12$adapted(Object ch) {
        return BoxesRunTime.boxToBoolean(StringParsers$.$anonfun$checkFloatFormat$12(BoxesRunTime.unboxToChar(ch)));
    }

    public static final /* synthetic */ Object $anonfun$checkFloatFormat$13$adapted(Object ch) {
        return BoxesRunTime.boxToBoolean(StringParsers$.$anonfun$checkFloatFormat$13(BoxesRunTime.unboxToChar(ch)));
    }

    public static final /* synthetic */ Object $anonfun$checkFloatFormat$1$adapted(Object ch) {
        return BoxesRunTime.boxToBoolean(StringParsers$.isHexDigit$1(BoxesRunTime.unboxToChar(ch)));
    }

    public static final /* synthetic */ Object $anonfun$checkFloatFormat$2$adapted(Object ch) {
        return BoxesRunTime.boxToBoolean(StringParsers$.isHexDigit$1(BoxesRunTime.unboxToChar(ch)));
    }

    public static final /* synthetic */ Object $anonfun$checkFloatFormat$3$adapted(Object ch) {
        return BoxesRunTime.boxToBoolean(StringParsers$.isHexDigit$1(BoxesRunTime.unboxToChar(ch)));
    }

    public static final /* synthetic */ Object $anonfun$checkFloatFormat$4$adapted(Object c) {
        return BoxesRunTime.boxToBoolean(StringParsers$.MODULE$.isDigit(BoxesRunTime.unboxToChar(c)));
    }

    public static final /* synthetic */ Object $anonfun$checkFloatFormat$5$adapted(Object c) {
        return BoxesRunTime.boxToBoolean(StringParsers$.MODULE$.isDigit(BoxesRunTime.unboxToChar(c)));
    }

    public static final /* synthetic */ Object $anonfun$checkFloatFormat$6$adapted(Object ch) {
        return BoxesRunTime.boxToBoolean(StringParsers$.$anonfun$checkFloatFormat$6(BoxesRunTime.unboxToChar(ch)));
    }

    public static final /* synthetic */ Object $anonfun$checkFloatFormat$7$adapted(Object c) {
        return BoxesRunTime.boxToBoolean(StringParsers$.MODULE$.isDigit(BoxesRunTime.unboxToChar(c)));
    }

    public static final /* synthetic */ Object $anonfun$checkFloatFormat$8$adapted(Object c) {
        return BoxesRunTime.boxToBoolean(StringParsers$.MODULE$.isDigit(BoxesRunTime.unboxToChar(c)));
    }

    public static final /* synthetic */ Object $anonfun$checkFloatFormat$9$adapted(Object c) {
        return BoxesRunTime.boxToBoolean(StringParsers$.MODULE$.isDigit(BoxesRunTime.unboxToChar(c)));
    }

    public static final /* synthetic */ Object $anonfun$checkFloatFormat$10$adapted(Object c) {
        return BoxesRunTime.boxToBoolean(StringParsers$.MODULE$.isDigit(BoxesRunTime.unboxToChar(c)));
    }

    public static final /* synthetic */ Object $anonfun$checkFloatFormat$11$adapted(Object c) {
        return BoxesRunTime.boxToBoolean(StringParsers$.MODULE$.isDigit(BoxesRunTime.unboxToChar(c)));
    }
}

