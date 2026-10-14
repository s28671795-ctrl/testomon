/*
 * Decompiled with CFR 0.152.
 */
package scala.sys.process;

import scala.Function1;
import scala.collection.SeqOps;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.mutable.ArrayBuffer;
import scala.collection.mutable.Growable;
import scala.runtime.BooleanRef;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.Nothing$;
import scala.runtime.ObjectRef;
import scala.sys.process.Parser;

public final class Parser$ {
    public static final Parser$ MODULE$ = new Parser$();

    private final char DQ() {
        return '\"';
    }

    private final char SQ() {
        return '\'';
    }

    private final int EOF() {
        return -1;
    }

    /*
     * WARNING - void declaration
     */
    public List<String> tokenize(String line, Function1<String, BoxedUnit> errorFn) {
        List list = Nil$.MODULE$;
        int n = 0;
        ArrayBuffer<Integer> qpos = new ArrayBuffer<Integer>(16);
        while (true) {
            void loop$1_$colon$colon_elem;
            Object var9_11;
            String string;
            boolean bl;
            Object var7_9;
            Object var5_7;
            if (Character.isWhitespace(n >= line.length() ? -1 : (int)line.charAt(n))) {
                ++n;
                continue;
            }
            int n2 = n;
            if (n >= line.length()) {
                return ((List)list).reverse();
            }
            boolean bl2 = false;
            while (true) {
                int n3;
                int n4 = n3 = n >= line.length() ? -1 : (int)line.charAt(n);
                if (bl2) {
                    bl2 = false;
                    ++n;
                    continue;
                }
                if (92 == n3) {
                    bl2 = true;
                    ++n;
                    continue;
                }
                if (34 == n3 ? true : 39 == n3) {
                    Integer loop$1_skipToDelim$1_advance$1_quote$1_$plus$eq_elem = n;
                    qpos.addOne(loop$1_skipToDelim$1_advance$1_quote$1_$plus$eq_elem);
                    var5_7 = null;
                    ++n;
                    boolean bl3 = false;
                    while (true) {
                        boolean bl4;
                        int n5;
                        int n6 = n5 = n >= line.length() ? -1 : (int)line.charAt(n);
                        if (bl3) {
                            bl3 = false;
                            bl4 = false;
                        } else if (92 == n5) {
                            bl3 = true;
                            bl4 = false;
                        } else {
                            bl4 = n3 == n5 ? true : -1 == n5;
                        }
                        if (bl4) break;
                        ++n;
                    }
                    if (!(n >= line.length())) {
                        Integer loop$1_skipToDelim$1_advance$1_quote$1_$plus$eq_elem2 = n;
                        qpos.addOne(loop$1_skipToDelim$1_advance$1_quote$1_$plus$eq_elem2);
                        var7_9 = null;
                        ++n;
                        continue;
                    }
                    bl = false;
                    break;
                }
                if (-1 == n3) {
                    bl = true;
                    break;
                }
                if (Character.isWhitespace(n3)) {
                    bl = true;
                    break;
                }
                ++n;
            }
            var5_7 = null;
            var7_9 = null;
            if (!bl) {
                errorFn.apply(new StringBuilder(20).append("Unmatched quote [").append(qpos.last()).append("](").append(line.charAt(BoxesRunTime.unboxToInt(qpos.last()))).append(")").toString());
                return Nil$.MODULE$;
            }
            List list2 = list;
            if (qpos.isEmpty()) {
                string = line.substring(n2, n);
            } else if (BoxesRunTime.unboxToInt(qpos.apply(0)) == n2 && BoxesRunTime.unboxToInt(qpos.apply(1)) == n) {
                string = line.substring(n2 + 1, n - 1);
            } else {
                StringBuilder loop$1_text$1_copyText$1_buf = new StringBuilder();
                int loop$1_text$1_copyText$1_p = n2;
                int loop$1_text$1_copyText$1_i = 0;
                while (loop$1_text$1_copyText$1_p < n) {
                    if (loop$1_text$1_copyText$1_i >= SeqOps.size$(qpos)) {
                        loop$1_text$1_copyText$1_buf.append(line, loop$1_text$1_copyText$1_p, n);
                        loop$1_text$1_copyText$1_p = n;
                        continue;
                    }
                    if (loop$1_text$1_copyText$1_p == BoxesRunTime.unboxToInt(qpos.apply(loop$1_text$1_copyText$1_i))) {
                        loop$1_text$1_copyText$1_buf.append(line, BoxesRunTime.unboxToInt(qpos.apply(loop$1_text$1_copyText$1_i)) + 1, BoxesRunTime.unboxToInt(qpos.apply(loop$1_text$1_copyText$1_i + 1)));
                        loop$1_text$1_copyText$1_p = BoxesRunTime.unboxToInt(qpos.apply(loop$1_text$1_copyText$1_i + 1)) + 1;
                        loop$1_text$1_copyText$1_i += 2;
                        continue;
                    }
                    loop$1_text$1_copyText$1_buf.append(line, loop$1_text$1_copyText$1_p, BoxesRunTime.unboxToInt(qpos.apply(loop$1_text$1_copyText$1_i)));
                    loop$1_text$1_copyText$1_p = BoxesRunTime.unboxToInt(qpos.apply(loop$1_text$1_copyText$1_i));
                }
                string = loop$1_text$1_copyText$1_buf.toString();
                var9_11 = null;
            }
            String loop$1_text$1_res = string;
            qpos.clear();
            Object var8_10 = null;
            var9_11 = null;
            String string2 = loop$1_text$1_res;
            List loop$1_$colon$colon_this = list2;
            Object var12_14 = null;
            string2 = null;
            list = new $colon$colon<void>(loop$1_$colon$colon_elem, loop$1_$colon$colon_this);
        }
    }

    /*
     * WARNING - void declaration
     */
    public List<String> tokenize(String line) {
        List list = Nil$.MODULE$;
        int n = 0;
        ArrayBuffer<Integer> tokenize_qpos = new ArrayBuffer<Integer>(16);
        while (true) {
            void tokenize_loop$1_$colon$colon_elem;
            Object var8_10;
            String string;
            boolean bl;
            Object var6_8;
            Object var4_6;
            if (Character.isWhitespace(n >= line.length() ? -1 : (int)line.charAt(n))) {
                ++n;
                continue;
            }
            int n2 = n;
            if (n >= line.length()) {
                return ((List)list).reverse();
            }
            boolean bl2 = false;
            while (true) {
                int n3;
                int n4 = n3 = n >= line.length() ? -1 : (int)line.charAt(n);
                if (bl2) {
                    bl2 = false;
                    ++n;
                    continue;
                }
                if (92 == n3) {
                    bl2 = true;
                    ++n;
                    continue;
                }
                if (34 == n3 ? true : 39 == n3) {
                    Integer tokenize_loop$1_skipToDelim$1_advance$1_quote$1_$plus$eq_elem = n;
                    tokenize_qpos.addOne(tokenize_loop$1_skipToDelim$1_advance$1_quote$1_$plus$eq_elem);
                    var4_6 = null;
                    ++n;
                    boolean bl3 = false;
                    while (true) {
                        boolean bl4;
                        int n5;
                        int n6 = n5 = n >= line.length() ? -1 : (int)line.charAt(n);
                        if (bl3) {
                            bl3 = false;
                            bl4 = false;
                        } else if (92 == n5) {
                            bl3 = true;
                            bl4 = false;
                        } else {
                            bl4 = n3 == n5 ? true : -1 == n5;
                        }
                        if (bl4) break;
                        ++n;
                    }
                    if (!(n >= line.length())) {
                        Integer tokenize_loop$1_skipToDelim$1_advance$1_quote$1_$plus$eq_elem2 = n;
                        tokenize_qpos.addOne(tokenize_loop$1_skipToDelim$1_advance$1_quote$1_$plus$eq_elem2);
                        var6_8 = null;
                        ++n;
                        continue;
                    }
                    bl = false;
                    break;
                }
                if (-1 == n3) {
                    bl = true;
                    break;
                }
                if (Character.isWhitespace(n3)) {
                    bl = true;
                    break;
                }
                ++n;
            }
            var4_6 = null;
            var6_8 = null;
            if (!bl) {
                String string2 = new StringBuilder(20).append("Unmatched quote [").append(tokenize_qpos.last()).append("](").append(line.charAt(BoxesRunTime.unboxToInt(tokenize_qpos.last()))).append(")").toString();
                throw new Parser.ParseException(string2);
            }
            List list2 = list;
            if (tokenize_qpos.isEmpty()) {
                string = line.substring(n2, n);
            } else if (BoxesRunTime.unboxToInt(tokenize_qpos.apply(0)) == n2 && BoxesRunTime.unboxToInt(tokenize_qpos.apply(1)) == n) {
                string = line.substring(n2 + 1, n - 1);
            } else {
                StringBuilder tokenize_loop$1_text$1_copyText$1_buf = new StringBuilder();
                int tokenize_loop$1_text$1_copyText$1_p = n2;
                int tokenize_loop$1_text$1_copyText$1_i = 0;
                while (tokenize_loop$1_text$1_copyText$1_p < n) {
                    if (tokenize_loop$1_text$1_copyText$1_i >= SeqOps.size$(tokenize_qpos)) {
                        tokenize_loop$1_text$1_copyText$1_buf.append(line, tokenize_loop$1_text$1_copyText$1_p, n);
                        tokenize_loop$1_text$1_copyText$1_p = n;
                        continue;
                    }
                    if (tokenize_loop$1_text$1_copyText$1_p == BoxesRunTime.unboxToInt(tokenize_qpos.apply(tokenize_loop$1_text$1_copyText$1_i))) {
                        tokenize_loop$1_text$1_copyText$1_buf.append(line, BoxesRunTime.unboxToInt(tokenize_qpos.apply(tokenize_loop$1_text$1_copyText$1_i)) + 1, BoxesRunTime.unboxToInt(tokenize_qpos.apply(tokenize_loop$1_text$1_copyText$1_i + 1)));
                        tokenize_loop$1_text$1_copyText$1_p = BoxesRunTime.unboxToInt(tokenize_qpos.apply(tokenize_loop$1_text$1_copyText$1_i + 1)) + 1;
                        tokenize_loop$1_text$1_copyText$1_i += 2;
                        continue;
                    }
                    tokenize_loop$1_text$1_copyText$1_buf.append(line, tokenize_loop$1_text$1_copyText$1_p, BoxesRunTime.unboxToInt(tokenize_qpos.apply(tokenize_loop$1_text$1_copyText$1_i)));
                    tokenize_loop$1_text$1_copyText$1_p = BoxesRunTime.unboxToInt(tokenize_qpos.apply(tokenize_loop$1_text$1_copyText$1_i));
                }
                string = tokenize_loop$1_text$1_copyText$1_buf.toString();
                var8_10 = null;
            }
            String tokenize_loop$1_text$1_res = string;
            tokenize_qpos.clear();
            Object var7_9 = null;
            var8_10 = null;
            String string3 = tokenize_loop$1_text$1_res;
            List tokenize_loop$1_$colon$colon_this = list2;
            Object var11_13 = null;
            string3 = null;
            list = new $colon$colon<void>(tokenize_loop$1_$colon$colon_elem, tokenize_loop$1_$colon$colon_this);
        }
    }

    private static final int cur$1(String line$1, IntRef pos$1) {
        if (pos$1.elem >= line$1.length()) {
            return -1;
        }
        return line$1.charAt(pos$1.elem);
    }

    private static final void bump$1(IntRef pos$1) {
        ++pos$1.elem;
    }

    private static final boolean done$1(IntRef pos$1, String line$1) {
        return pos$1.elem >= line$1.length();
    }

    private static final boolean terminal$1(BooleanRef escaped$1, int q$1, String line$1, IntRef pos$1) {
        int n;
        int n2 = n = pos$1.elem >= line$1.length() ? -1 : (int)line$1.charAt(pos$1.elem);
        if (escaped$1.elem) {
            escaped$1.elem = false;
            return false;
        }
        if (92 == n) {
            escaped$1.elem = true;
            return false;
        }
        return q$1 == n ? true : -1 == n;
    }

    private static final boolean skipToQuote$1(int q, String line$1, IntRef pos$1) {
        boolean bl = false;
        while (true) {
            boolean bl2;
            int n;
            int n2 = n = pos$1.elem >= line$1.length() ? -1 : (int)line$1.charAt(pos$1.elem);
            if (bl) {
                bl = false;
                bl2 = false;
            } else if (92 == n) {
                bl = true;
                bl2 = false;
            } else {
                bl2 = q == n ? true : -1 == n;
            }
            if (bl2) break;
            ++pos$1.elem;
        }
        return !(pos$1.elem >= line$1.length());
    }

    private static final void quote$1(ArrayBuffer qpos$1, IntRef pos$1) {
        Integer $plus$eq_elem = pos$1.elem;
        qpos$1.addOne($plus$eq_elem);
        ++pos$1.elem;
    }

    private final boolean advance$1(BooleanRef escaped$2, String line$1, IntRef pos$1, ArrayBuffer qpos$1) {
        while (true) {
            int n;
            int n2 = n = pos$1.elem >= line$1.length() ? -1 : (int)line$1.charAt(pos$1.elem);
            if (escaped$2.elem) {
                escaped$2.elem = false;
                ++pos$1.elem;
                continue;
            }
            if (92 == n) {
                escaped$2.elem = true;
                ++pos$1.elem;
                continue;
            }
            if (34 == n ? true : 39 == n) {
                Integer quote$1_$plus$eq_elem = pos$1.elem;
                if (qpos$1 == null) {
                    throw null;
                }
                qpos$1.addOne(quote$1_$plus$eq_elem);
                Object var6_6 = null;
                ++pos$1.elem;
                boolean bl = false;
                while (true) {
                    boolean bl2;
                    int n3;
                    int n4 = n3 = pos$1.elem >= line$1.length() ? -1 : (int)line$1.charAt(pos$1.elem);
                    if (bl) {
                        bl = false;
                        bl2 = false;
                    } else if (92 == n3) {
                        bl = true;
                        bl2 = false;
                    } else {
                        bl2 = n == n3 ? true : -1 == n3;
                    }
                    if (bl2) break;
                    ++pos$1.elem;
                }
                if (!(pos$1.elem >= line$1.length())) {
                    Integer quote$1_$plus$eq_elem2 = pos$1.elem;
                    qpos$1.addOne(quote$1_$plus$eq_elem2);
                    Object var8_8 = null;
                    ++pos$1.elem;
                    continue;
                }
                return false;
            }
            if (-1 == n) {
                return true;
            }
            if (Character.isWhitespace(n)) {
                return true;
            }
            ++pos$1.elem;
        }
    }

    private final boolean skipToDelim$1(ArrayBuffer qpos$1, IntRef pos$1, String line$1) {
        boolean bl = false;
        while (true) {
            int n;
            int n2 = n = pos$1.elem >= line$1.length() ? -1 : (int)line$1.charAt(pos$1.elem);
            if (bl) {
                bl = false;
                ++pos$1.elem;
                continue;
            }
            if (92 == n) {
                bl = true;
                ++pos$1.elem;
                continue;
            }
            if (34 == n ? true : 39 == n) {
                Integer advance$1_quote$1_$plus$eq_elem = pos$1.elem;
                if (qpos$1 == null) {
                    throw null;
                }
                qpos$1.addOne(advance$1_quote$1_$plus$eq_elem);
                Object var5_6 = null;
                ++pos$1.elem;
                boolean bl2 = false;
                while (true) {
                    boolean bl3;
                    int n3;
                    int n4 = n3 = pos$1.elem >= line$1.length() ? -1 : (int)line$1.charAt(pos$1.elem);
                    if (bl2) {
                        bl2 = false;
                        bl3 = false;
                    } else if (92 == n3) {
                        bl2 = true;
                        bl3 = false;
                    } else {
                        bl3 = n == n3 ? true : -1 == n3;
                    }
                    if (bl3) break;
                    ++pos$1.elem;
                }
                if (!(pos$1.elem >= line$1.length())) {
                    Integer advance$1_quote$1_$plus$eq_elem2 = pos$1.elem;
                    qpos$1.addOne(advance$1_quote$1_$plus$eq_elem2);
                    Object var7_8 = null;
                    ++pos$1.elem;
                    continue;
                }
                return false;
            }
            if (-1 == n) {
                return true;
            }
            if (Character.isWhitespace(n)) {
                return true;
            }
            ++pos$1.elem;
        }
    }

    private static final void skipWhitespace$1(String line$1, IntRef pos$1) {
        while (Character.isWhitespace(pos$1.elem >= line$1.length() ? -1 : (int)line$1.charAt(pos$1.elem))) {
            ++pos$1.elem;
        }
    }

    private static final String copyText$1(IntRef start$1, IntRef pos$1, ArrayBuffer qpos$1, String line$1) {
        StringBuilder buf = new StringBuilder();
        int p = start$1.elem;
        int i = 0;
        while (p < pos$1.elem) {
            if (qpos$1 == null) {
                throw null;
            }
            if (i >= qpos$1.length()) {
                buf.append(line$1, p, pos$1.elem);
                p = pos$1.elem;
                continue;
            }
            if (p == BoxesRunTime.unboxToInt(qpos$1.apply(i))) {
                buf.append(line$1, BoxesRunTime.unboxToInt(qpos$1.apply(i)) + 1, BoxesRunTime.unboxToInt(qpos$1.apply(i + 1)));
                p = BoxesRunTime.unboxToInt(qpos$1.apply(i + 1)) + 1;
                i += 2;
                continue;
            }
            buf.append(line$1, p, BoxesRunTime.unboxToInt(qpos$1.apply(i)));
            p = BoxesRunTime.unboxToInt(qpos$1.apply(i));
        }
        return buf.toString();
    }

    private static final String text$1(ArrayBuffer qpos$1, String line$1, IntRef start$1, IntRef pos$1) {
        String string;
        if (qpos$1.isEmpty()) {
            string = line$1.substring(start$1.elem, pos$1.elem);
        } else if (BoxesRunTime.unboxToInt(qpos$1.apply(0)) == start$1.elem && BoxesRunTime.unboxToInt(qpos$1.apply(1)) == pos$1.elem) {
            string = line$1.substring(start$1.elem + 1, pos$1.elem - 1);
        } else {
            StringBuilder copyText$1_buf = new StringBuilder();
            int copyText$1_p = start$1.elem;
            int copyText$1_i = 0;
            while (copyText$1_p < pos$1.elem) {
                if (copyText$1_i >= qpos$1.length()) {
                    copyText$1_buf.append(line$1, copyText$1_p, pos$1.elem);
                    copyText$1_p = pos$1.elem;
                    continue;
                }
                if (copyText$1_p == BoxesRunTime.unboxToInt(qpos$1.apply(copyText$1_i))) {
                    copyText$1_buf.append(line$1, BoxesRunTime.unboxToInt(qpos$1.apply(copyText$1_i)) + 1, BoxesRunTime.unboxToInt(qpos$1.apply(copyText$1_i + 1)));
                    copyText$1_p = BoxesRunTime.unboxToInt(qpos$1.apply(copyText$1_i + 1)) + 1;
                    copyText$1_i += 2;
                    continue;
                }
                copyText$1_buf.append(line$1, copyText$1_p, BoxesRunTime.unboxToInt(qpos$1.apply(copyText$1_i)));
                copyText$1_p = BoxesRunTime.unboxToInt(qpos$1.apply(copyText$1_i));
            }
            string = copyText$1_buf.toString();
            Object var5_4 = null;
        }
        String res = string;
        qpos$1.clear();
        return res;
    }

    private static final void badquote$1(Function1 errorFn$1, ArrayBuffer qpos$1, String line$1) {
        errorFn$1.apply(new StringBuilder(20).append("Unmatched quote [").append(qpos$1.last()).append("](").append(line$1.charAt(BoxesRunTime.unboxToInt(qpos$1.last()))).append(")").toString());
    }

    /*
     * WARNING - void declaration
     */
    private final List loop$1(IntRef start$1, IntRef pos$1, ObjectRef accum$1, String line$1, ArrayBuffer qpos$1, Function1 errorFn$1) {
        while (true) {
            void $colon$colon_elem;
            Object var12_12;
            String string;
            boolean bl;
            Object var10_10;
            Object var8_8;
            if (Character.isWhitespace(pos$1.elem >= line$1.length() ? -1 : (int)line$1.charAt(pos$1.elem))) {
                ++pos$1.elem;
                continue;
            }
            start$1.elem = pos$1.elem;
            if (pos$1.elem >= line$1.length()) {
                return ((List)accum$1.elem).reverse();
            }
            boolean bl2 = false;
            while (true) {
                int n;
                int n2 = n = pos$1.elem >= line$1.length() ? -1 : (int)line$1.charAt(pos$1.elem);
                if (bl2) {
                    bl2 = false;
                    ++pos$1.elem;
                    continue;
                }
                if (92 == n) {
                    bl2 = true;
                    ++pos$1.elem;
                    continue;
                }
                if (34 == n ? true : 39 == n) {
                    Integer skipToDelim$1_advance$1_quote$1_$plus$eq_elem = pos$1.elem;
                    Growable.$plus$eq$(qpos$1, skipToDelim$1_advance$1_quote$1_$plus$eq_elem);
                    var8_8 = null;
                    ++pos$1.elem;
                    boolean bl3 = false;
                    while (true) {
                        boolean bl4;
                        int n3;
                        int n4 = n3 = pos$1.elem >= line$1.length() ? -1 : (int)line$1.charAt(pos$1.elem);
                        if (bl3) {
                            bl3 = false;
                            bl4 = false;
                        } else if (92 == n3) {
                            bl3 = true;
                            bl4 = false;
                        } else {
                            bl4 = n == n3 ? true : -1 == n3;
                        }
                        if (bl4) break;
                        ++pos$1.elem;
                    }
                    if (!(pos$1.elem >= line$1.length())) {
                        Integer skipToDelim$1_advance$1_quote$1_$plus$eq_elem2 = pos$1.elem;
                        Growable.$plus$eq$(qpos$1, skipToDelim$1_advance$1_quote$1_$plus$eq_elem2);
                        var10_10 = null;
                        ++pos$1.elem;
                        continue;
                    }
                    bl = false;
                    break;
                }
                if (-1 == n) {
                    bl = true;
                    break;
                }
                if (Character.isWhitespace(n)) {
                    bl = true;
                    break;
                }
                ++pos$1.elem;
            }
            var8_8 = null;
            var10_10 = null;
            if (!bl) {
                errorFn$1.apply(new StringBuilder(20).append("Unmatched quote [").append(qpos$1.last()).append("](").append(line$1.charAt(BoxesRunTime.unboxToInt(qpos$1.last()))).append(")").toString());
                return Nil$.MODULE$;
            }
            List list = (List)accum$1.elem;
            if (qpos$1.isEmpty()) {
                string = line$1.substring(start$1.elem, pos$1.elem);
            } else if (BoxesRunTime.unboxToInt(qpos$1.apply(0)) == start$1.elem && BoxesRunTime.unboxToInt(qpos$1.apply(1)) == pos$1.elem) {
                string = line$1.substring(start$1.elem + 1, pos$1.elem - 1);
            } else {
                StringBuilder text$1_copyText$1_buf = new StringBuilder();
                int text$1_copyText$1_p = start$1.elem;
                int text$1_copyText$1_i = 0;
                while (text$1_copyText$1_p < pos$1.elem) {
                    if (text$1_copyText$1_i >= qpos$1.length()) {
                        text$1_copyText$1_buf.append(line$1, text$1_copyText$1_p, pos$1.elem);
                        text$1_copyText$1_p = pos$1.elem;
                        continue;
                    }
                    if (text$1_copyText$1_p == BoxesRunTime.unboxToInt(qpos$1.apply(text$1_copyText$1_i))) {
                        text$1_copyText$1_buf.append(line$1, BoxesRunTime.unboxToInt(qpos$1.apply(text$1_copyText$1_i)) + 1, BoxesRunTime.unboxToInt(qpos$1.apply(text$1_copyText$1_i + 1)));
                        text$1_copyText$1_p = BoxesRunTime.unboxToInt(qpos$1.apply(text$1_copyText$1_i + 1)) + 1;
                        text$1_copyText$1_i += 2;
                        continue;
                    }
                    text$1_copyText$1_buf.append(line$1, text$1_copyText$1_p, BoxesRunTime.unboxToInt(qpos$1.apply(text$1_copyText$1_i)));
                    text$1_copyText$1_p = BoxesRunTime.unboxToInt(qpos$1.apply(text$1_copyText$1_i));
                }
                string = text$1_copyText$1_buf.toString();
                var12_12 = null;
            }
            String text$1_res = string;
            qpos$1.clear();
            Object var11_11 = null;
            var12_12 = null;
            String string2 = text$1_res;
            if (list == null) {
                throw null;
            }
            List $colon$colon_this = list;
            Object var15_15 = null;
            string2 = null;
            accum$1.elem = new $colon$colon<void>($colon$colon_elem, $colon$colon_this);
        }
    }

    public static final /* synthetic */ Nothing$ $anonfun$tokenize$1(String x) {
        throw new Parser.ParseException(x);
    }

    private Parser$() {
    }
}

