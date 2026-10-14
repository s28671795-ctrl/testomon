/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.StringContext;
import scala.Tuple2;
import scala.Tuple2$mcCI$sp;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Seq;
import scala.collection.StringOps$;
import scala.collection.immutable.ArraySeq$;
import scala.collection.mutable.ArrayBuilder;
import scala.math.Numeric$IntIsIntegral$;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class StringContext$
implements Serializable {
    public static final StringContext$ MODULE$ = new StringContext$();

    /*
     * WARNING - void declaration
     */
    public Option<scala.collection.immutable.Seq<String>> glob(scala.collection.immutable.Seq<String> patternChunks, String input) {
        String[] stringArray;
        int[] nArray;
        int[] nArray2;
        int[] nArray3;
        int patternIndex = 0;
        int inputIndex = 0;
        int nextPatternIndex = 0;
        int nextInputIndex = 0;
        int numWildcards = patternChunks.length() - 1;
        if (numWildcards <= 0) {
            nArray3 = new int[]{};
        } else {
            int[] fill_array = new int[numWildcards];
            for (int fill_i = 0; fill_i < numWildcards; ++fill_i) {
                void var33_10;
                int boxToInteger_i = -1;
                fill_array[fill_i] = var33_10;
            }
            nArray3 = fill_array;
        }
        Object var19_8 = null;
        int[] matchStarts = nArray3;
        if (numWildcards <= 0) {
            nArray2 = new int[]{};
        } else {
            int[] fill_array = new int[numWildcards];
            for (int fill_i = 0; fill_i < numWildcards; ++fill_i) {
                void var34_14;
                int boxToInteger_i = -1;
                fill_array[fill_i] = var34_14;
            }
            nArray2 = fill_array;
        }
        Object var21_12 = null;
        int[] matchEnds = nArray2;
        int nameLength = input.length();
        int patternLength = BoxesRunTime.unboxToInt(patternChunks.iterator().map((Function1<String, Object> & Serializable)x$1 -> BoxesRunTime.boxToInteger(x$1.length())).sum(Numeric$IntIsIntegral$.MODULE$)) + numWildcards;
        ArrayBuilder.ofShort b = new ArrayBuilder.ofShort();
        b.sizeHint(patternLength);
        String foreach$extension_$this = (String)patternChunks.head();
        int foreach$extension_len = foreach$extension_$this.length();
        for (int foreach$extension_i = 0; foreach$extension_i < foreach$extension_len; ++foreach$extension_i) {
            void var26_22;
            char foreach$extension_boxToCharacter_c = foreach$extension_$this.charAt(foreach$extension_i);
            b.addOne((short)var26_22);
        }
        Object var23_19 = null;
        ((IterableOnceOps)patternChunks.tail()).foreach((Function1<String, Object> & Serializable)s2 -> {
            StringContext$.$anonfun$glob$5(b, s2);
            return BoxedUnit.UNIT;
        });
        short[] pattern = b.result();
        int fill_n = patternLength + 1;
        if (fill_n <= 0) {
            nArray = new int[]{};
        } else {
            int[] fill_array = new int[fill_n];
            for (int fill_i = 0; fill_i < fill_n; ++fill_i) {
                void var35_27;
                int boxToInteger_i = -1;
                fill_array[fill_i] = var35_27;
            }
            nArray = fill_array;
        }
        Object var28_25 = null;
        int[] arr = nArray;
        ((IterableOnceOps)((IterableOps)patternChunks.init()).zipWithIndex()).foldLeft(0, (Function2<Object, Tuple2, Object> & Serializable)(x0$1, x1$1) -> BoxesRunTime.boxToInteger(StringContext$.$anonfun$glob$8(arr, BoxesRunTime.unboxToInt(x0$1), x1$1)));
        while (patternIndex < patternLength || inputIndex < nameLength) {
            boolean bl;
            void var14_28;
            void var15_29 = var14_28[patternIndex];
            block0 : switch (var15_29) {
                case -1: {
                    break;
                }
                default: {
                    int n;
                    int n2 = matchStarts[var15_29];
                    switch (n2) {
                        case -1: {
                            n = inputIndex;
                            break;
                        }
                        default: {
                            n = Math.min(n2, inputIndex);
                        }
                    }
                    matchStarts[var15_29] = n;
                    int n3 = matchEnds[var15_29];
                    switch (n3) {
                        case -1: {
                            int n4 = inputIndex;
                            break block0;
                        }
                        default: {
                            int n4 = matchEnds[var15_29] = Math.max(n3, inputIndex);
                        }
                    }
                }
            }
            if (patternIndex < patternLength) {
                short c = pattern[patternIndex];
                switch (c) {
                    case -1: {
                        nextPatternIndex = patternIndex++;
                        nextInputIndex = inputIndex + 1;
                        bl = true;
                        break;
                    }
                    default: {
                        if (inputIndex < nameLength && input.charAt(inputIndex) == c) {
                            ++patternIndex;
                            ++inputIndex;
                            bl = true;
                            break;
                        }
                        bl = false;
                        break;
                    }
                }
            } else {
                bl = false;
            }
            if (bl) continue;
            if (0 < nextInputIndex && nextInputIndex <= nameLength) {
                patternIndex = nextPatternIndex;
                inputIndex = nextInputIndex;
                continue;
            }
            return None$.MODULE$;
        }
        int tabulate_n = patternChunks.length() - 1;
        if (tabulate_n <= 0) {
            stringArray = new String[]{};
        } else {
            String[] tabulate_array = new String[tabulate_n];
            for (int tabulate_i = 0; tabulate_i < tabulate_n; ++tabulate_i) {
                String array_update_value;
                tabulate_array[tabulate_i] = array_update_value = StringOps$.MODULE$.slice$extension(input, matchStarts[tabulate_i], matchEnds[tabulate_i]);
                Object var36_36 = null;
            }
            stringArray = tabulate_array;
        }
        Object var31_34 = null;
        return new Some<scala.collection.immutable.Seq<String>>(ArraySeq$.MODULE$.unsafeWrapArray(stringArray));
    }

    private Tuple2<Object, Object> readUEscape(String src, int startindex) {
        int len = src.length();
        return this.loop$1(startindex, startindex, len, src);
    }

    public String treatEscapes(String str) {
        return this.processEscapes(str);
    }

    public String processEscapes(String str) {
        int n = str.indexOf(92);
        switch (n) {
            case -1: {
                return str;
            }
        }
        return this.replace(str, n);
    }

    public String processUnicode(String str) {
        int n = str.indexOf("\\u");
        switch (n) {
            case -1: {
                return str;
            }
        }
        return this.replaceU(str, n);
    }

    private String replace(String str, int first) {
        int len = str.length();
        StringBuilder b = new StringBuilder();
        return this.loop$2(0, first, b, str, len);
    }

    private String replaceU(String str, int backslash) {
        int len = str.length();
        StringBuilder b = new StringBuilder();
        return this.loop$3(0, backslash, str, b, len);
    }

    public String standardInterpolator(Function1<String, String> process, Seq<Object> args2, scala.collection.immutable.Seq<String> parts) {
        this.checkLengths(args2, parts);
        Iterator pi = parts.iterator();
        Iterator ai = args2.iterator();
        StringBuilder bldr = new StringBuilder(process.apply((String)pi.next()));
        while (ai.hasNext()) {
            bldr.append(ai.next());
            bldr.append(process.apply((String)pi.next()));
        }
        return bldr.toString();
    }

    public void checkLengths(Seq<Object> args2, scala.collection.immutable.Seq<String> parts) {
        if (parts.length() != args2.length() + 1) {
            throw new IllegalArgumentException(new StringBuilder(64).append("wrong number of arguments (").append(args2.length()).append(") for interpolated string with ").append(parts.length()).append(" parts").toString());
        }
    }

    public StringContext apply(scala.collection.immutable.Seq<String> parts) {
        return new StringContext(parts);
    }

    public Option<scala.collection.immutable.Seq<String>> unapplySeq(StringContext x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<scala.collection.immutable.Seq<String>>(x$0.parts());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(StringContext$.class);
    }

    public static final /* synthetic */ int $anonfun$glob$1() {
        return -1;
    }

    public static final /* synthetic */ int $anonfun$glob$2() {
        return -1;
    }

    /*
     * WARNING - void declaration
     */
    public static final /* synthetic */ void $anonfun$glob$5(ArrayBuilder.ofShort b$1, String s2) {
        b$1.addOne((short)-1);
        int foreach$extension_len = s2.length();
        for (int foreach$extension_i = 0; foreach$extension_i < foreach$extension_len; ++foreach$extension_i) {
            void var4_4;
            char foreach$extension_boxToCharacter_c = s2.charAt(foreach$extension_i);
            b$1.addOne((short)var4_4);
        }
    }

    public static final /* synthetic */ int $anonfun$glob$7() {
        return -1;
    }

    public static final /* synthetic */ int $anonfun$glob$8(int[] arr$1, int x0$1, Tuple2 x1$1) {
        Tuple2<Integer, Tuple2> tuple2 = new Tuple2<Integer, Tuple2>(x0$1, x1$1);
        if (x1$1 != null) {
            String chunk = (String)x1$1._1();
            int i = x1$1._2$mcI$sp();
            int sum = x0$1 + chunk.length();
            arr$1[sum] = i;
            return sum + 1;
        }
        throw new MatchError(tuple2);
    }

    private final Tuple2 loopCP$1(int dindex, int codepoint, int uindex$1, int startindex$1, int len$1, String src$1) {
        while (true) {
            if (dindex >= 4) {
                int usRead = uindex$1 - startindex$1;
                return new Tuple2$mcCI$sp((char)codepoint, usRead + dindex);
            }
            if (dindex + uindex$1 >= len$1) {
                throw new StringContext.InvalidUnicodeEscapeException(src$1, startindex$1, uindex$1 + dindex);
            }
            int apply$extension_i = dindex + uindex$1;
            char ch = src$1.charAt(apply$extension_i);
            int e = Character.digit(ch, 36);
            if (e < 0 || e > 15) break;
            codepoint = (codepoint << 4) + e;
            ++dindex;
        }
        throw new StringContext.InvalidUnicodeEscapeException(src$1, startindex$1, uindex$1 + dindex);
    }

    private final Tuple2 loop$1(int uindex, int startindex$1, int len$1, String src$1) {
        while (true) {
            if (uindex >= len$1) {
                throw new StringContext.InvalidUnicodeEscapeException(src$1, startindex$1, uindex - 1);
            }
            if (src$1.charAt(uindex) != 'u') break;
            ++uindex;
        }
        return this.loopCP$1(0, 0, uindex, startindex$1, len$1, src$1);
    }

    /*
     * WARNING - void declaration
     */
    private final String loop$2(int i, int next, StringBuilder b$2, String str$1, int len$2) {
        while (next >= 0) {
            void var10_10;
            void var9_9;
            Tuple2 tuple2;
            char c;
            int idx;
            if (next > i) {
                b$2.append(str$1, i, next);
            }
            if ((idx = next + 1) >= len$2) {
                throw new StringContext.InvalidEscapeException(str$1, next);
            }
            switch (str$1.charAt(idx)) {
                case 'u': {
                    c = 'u';
                    break;
                }
                case 'b': {
                    c = '\b';
                    break;
                }
                case 't': {
                    c = '\t';
                    break;
                }
                case 'n': {
                    c = '\n';
                    break;
                }
                case 'f': {
                    c = '\f';
                    break;
                }
                case 'r': {
                    c = '\r';
                    break;
                }
                case '\"': {
                    c = '\"';
                    break;
                }
                case '\'': {
                    c = '\'';
                    break;
                }
                case '\\': {
                    c = '\\';
                    break;
                }
                default: {
                    throw new StringContext.InvalidEscapeException(str$1, next);
                }
            }
            char c2 = c;
            Tuple2 tuple22 = tuple2 = c2 == 'u' ? this.readUEscape(str$1, idx) : new Tuple2$mcCI$sp(c2, 1);
            if (tuple2 == null) {
                throw new MatchError((Object)null);
            }
            char ch = tuple2._1$mcC$sp();
            int advance = tuple2._2$mcI$sp();
            b$2.append((char)var9_9);
            next = str$1.indexOf(92, idx += var10_10);
            i = idx;
        }
        if (i < len$2) {
            b$2.append(str$1, i, len$2);
        }
        return b$2.toString();
    }

    private final boolean oddBackslashes$1(int ibackslash, String str$2, int next$1) {
        int apply$extension_i;
        while (ibackslash > 0 && str$2.charAt(apply$extension_i = ibackslash - 1) == '\\') {
            --ibackslash;
        }
        return (next$1 - ibackslash) % 2 == 0;
    }

    /*
     * WARNING - void declaration
     */
    private final String loop$3(int i, int next, String str$2, StringBuilder b$3, int len$3) {
        while (next >= 0) {
            if (this.oddBackslashes$1(next, str$2, next)) {
                void var8_8;
                void var9_9;
                int idx;
                Tuple2<Object, Object> tuple2;
                if (next > i) {
                    b$3.append(str$2, i, next);
                }
                if ((tuple2 = this.readUEscape(str$2, idx = next + 1)) == null) {
                    throw new MatchError((Object)null);
                }
                char ch = tuple2._1$mcC$sp();
                int advance = tuple2._2$mcI$sp();
                int nextCharIndex = idx + var9_9;
                b$3.append((char)var8_8);
                next = str$2.indexOf("\\u", nextCharIndex);
                i = nextCharIndex;
                continue;
            }
            next = str$2.indexOf("\\u", next + 1);
        }
        if (i < len$3) {
            b$3.append(str$2, i, len$3);
        }
        return b$3.toString();
    }

    private StringContext$() {
    }

    public static final /* synthetic */ ArrayBuilder.ofShort $anonfun$glob$4$adapted(ArrayBuilder.ofShort b$1, Object c) {
        return b$1.addOne((short)BoxesRunTime.unboxToChar(c));
    }

    public static final /* synthetic */ String $anonfun$glob$9$adapted(String input$1, int[] matchStarts$1, int[] matchEnds$1, Object n) {
        return StringOps$.MODULE$.slice$extension(input$1, matchStarts$1[BoxesRunTime.unboxToInt(n)], matchEnds$1[BoxesRunTime.unboxToInt(n)]);
    }

    public static final /* synthetic */ ArrayBuilder.ofShort $anonfun$glob$6$adapted(ArrayBuilder.ofShort b$1, Object c) {
        return b$1.addOne((short)BoxesRunTime.unboxToChar(c));
    }
}

