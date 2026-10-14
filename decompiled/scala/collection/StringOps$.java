/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.regex.PatternSyntaxException;
import scala.$less$colon$less$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.collection.AbstractIterator;
import scala.collection.IndexedSeqOps;
import scala.collection.IntStepper;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.LazyZip2;
import scala.collection.Seq;
import scala.collection.SeqOps;
import scala.collection.StringOps;
import scala.collection.StringParsers$;
import scala.collection.StringView;
import scala.collection.View;
import scala.collection.convert.impl.CharStringStepper;
import scala.collection.convert.impl.CodePointStringStepper;
import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.HashMap;
import scala.collection.immutable.HashMap$;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.IndexedSeq$;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Range;
import scala.collection.immutable.WrappedString;
import scala.collection.immutable.WrappedString$;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Map$;
import scala.collection.mutable.StringBuilder;
import scala.math.Ordering;
import scala.math.ScalaNumber;
import scala.package$;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.reflect.ManifestFactory;
import scala.runtime.BoxesRunTime;
import scala.util.Either;
import scala.util.Left;
import scala.util.Right;
import scala.util.matching.Regex;

public final class StringOps$ {
    public static final StringOps$ MODULE$ = new StringOps$();
    private static final Function1<Object, Object> fallback = (Function1<Object, Function1> & Serializable)x$1 -> MODULE$.fallback();

    private final int LF() {
        return 10;
    }

    private final int FF() {
        return 12;
    }

    private final int CR() {
        return 13;
    }

    private final int SU() {
        return 26;
    }

    private Function1<Object, Object> fallback() {
        return fallback;
    }

    public final StringView view$extension(String $this) {
        return new StringView($this);
    }

    public final int size$extension(String $this) {
        return $this.length();
    }

    public final int knownSize$extension(String $this) {
        return $this.length();
    }

    public final char apply$extension(String $this, int i) {
        return $this.charAt(i);
    }

    public final int sizeCompare$extension(String $this, int otherSize) {
        return Integer.compare($this.length(), otherSize);
    }

    public final int lengthCompare$extension(String $this, int len) {
        return Integer.compare($this.length(), len);
    }

    public final int sizeIs$extension(String $this) {
        return $this.length();
    }

    public final int lengthIs$extension(String $this) {
        return $this.length();
    }

    public final <B> IndexedSeq<B> map$extension(String $this, Function1<Object, B> f) {
        int len = $this.length();
        Object[] dst = new Object[len];
        for (int i = 0; i < len; ++i) {
            dst[i] = f.apply(Character.valueOf($this.charAt(i)));
        }
        return new ArraySeq.ofRef<Object>(dst);
    }

    public final String map$extension(String $this, Function1<Object, Object> f) {
        int len = $this.length();
        char[] dst = new char[len];
        for (int i = 0; i < len; ++i) {
            dst[i] = BoxesRunTime.unboxToChar(f.apply(Character.valueOf($this.charAt(i))));
        }
        return new String(dst);
    }

    public final <B> IndexedSeq<B> flatMap$extension(String $this, Function1<Object, IterableOnce<B>> f) {
        int len = $this.length();
        Builder b = IndexedSeq$.MODULE$.newBuilder();
        for (int i = 0; i < len; ++i) {
            b.addAll(f.apply(Character.valueOf($this.charAt(i))));
        }
        return (IndexedSeq)b.result();
    }

    public final String flatMap$extension(String $this, Function1<Object, String> f) {
        int len = $this.length();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < len; ++i) {
            sb.append(f.apply(Character.valueOf($this.charAt(i))));
        }
        return sb.toString();
    }

    public final String collect$extension(String $this, PartialFunction<Object, Object> pf) {
        Function1<Object, Object> fallback = this.fallback();
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < $this.length(); ++i) {
            Object v = pf.applyOrElse(Character.valueOf($this.charAt(i)), fallback);
            if (v == fallback) continue;
            b.addOne(BoxesRunTime.unboxToChar(v));
        }
        return b.result();
    }

    public final <B> IndexedSeq<B> collect$extension(String $this, PartialFunction<Object, B> pf) {
        Function1<Object, Object> fallback = this.fallback();
        Builder b = IndexedSeq$.MODULE$.newBuilder();
        for (int i = 0; i < $this.length(); ++i) {
            Object v = pf.applyOrElse(Character.valueOf($this.charAt(i)), fallback);
            if (v == fallback) continue;
            b.addOne(v);
        }
        return (IndexedSeq)b.result();
    }

    public final <B> IndexedSeq<B> concat$extension(String $this, IterableOnce<B> suffix) {
        Builder b = IndexedSeq$.MODULE$.newBuilder();
        int k = suffix.knownSize();
        b.sizeHint($this.length() + (k >= 0 ? k : 16));
        b.addAll(new WrappedString($this));
        b.addAll(suffix);
        return (IndexedSeq)b.result();
    }

    public final String concat$extension(String $this, IterableOnce<Object> suffix) {
        int k = suffix.knownSize();
        java.lang.StringBuilder sb = new java.lang.StringBuilder($this.length() + (k >= 0 ? k : 16));
        sb.append($this);
        suffix.iterator().foreach((Function1<Object, java.lang.StringBuilder> & Serializable)ch -> sb.append(BoxesRunTime.unboxToChar(ch)));
        return sb.toString();
    }

    public final String concat$extension(String $this, String suffix) {
        return new java.lang.StringBuilder(0).append($this).append(suffix).toString();
    }

    public final <B> IndexedSeq<B> $plus$plus$extension(String $this, Iterable<B> suffix) {
        return this.concat$extension($this, (IterableOnce<B>)suffix);
    }

    public final String $plus$plus$extension(String $this, IterableOnce<Object> suffix) {
        return this.concat$extension($this, suffix);
    }

    public final String $plus$plus$extension(String $this, String xs) {
        return new java.lang.StringBuilder(0).append($this).append(xs).toString();
    }

    public final <B> IndexedSeq<B> padTo$extension(String $this, int len, B elem) {
        int sLen = $this.length();
        if (sLen >= len) {
            return new WrappedString($this);
        }
        Builder b = IndexedSeq$.MODULE$.newBuilder();
        b.sizeHint(len);
        b.addAll(new WrappedString($this));
        for (int i = sLen; i < len; ++i) {
            b.addOne(elem);
        }
        return (IndexedSeq)b.result();
    }

    public final String padTo$extension(String $this, int len, char elem) {
        int sLen = $this.length();
        if (sLen >= len) {
            return $this;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(len);
        sb.append($this);
        for (int i = sLen; i < len; ++i) {
            sb.append(elem);
        }
        return sb.toString();
    }

    public final <B> IndexedSeq<B> prepended$extension(String $this, B elem) {
        Builder b = IndexedSeq$.MODULE$.newBuilder();
        b.sizeHint($this.length() + 1);
        b.addOne(elem);
        b.addAll(new WrappedString($this));
        return (IndexedSeq)b.result();
    }

    public final <B> IndexedSeq<B> $plus$colon$extension(String $this, B elem) {
        return this.prepended$extension($this, elem);
    }

    public final String prepended$extension(String $this, char c) {
        return new java.lang.StringBuilder($this.length() + 1).append(c).append($this).toString();
    }

    public final String $plus$colon$extension(String $this, char c) {
        return this.prepended$extension($this, c);
    }

    public final <B> IndexedSeq<B> prependedAll$extension(String $this, IterableOnce<B> prefix) {
        Builder b = IndexedSeq$.MODULE$.newBuilder();
        int k = prefix.knownSize();
        b.sizeHint($this.length() + (k >= 0 ? k : 16));
        b.addAll(prefix);
        b.addAll(new WrappedString($this));
        return (IndexedSeq)b.result();
    }

    public final <B> IndexedSeq<B> $plus$plus$colon$extension(String $this, IterableOnce<B> prefix) {
        return this.prependedAll$extension($this, prefix);
    }

    public final String prependedAll$extension(String $this, String prefix) {
        return new java.lang.StringBuilder(0).append(prefix).append($this).toString();
    }

    public final String $plus$plus$colon$extension(String $this, String prefix) {
        return this.prependedAll$extension($this, prefix);
    }

    public final <B> IndexedSeq<B> appended$extension(String $this, B elem) {
        Builder b = IndexedSeq$.MODULE$.newBuilder();
        b.sizeHint($this.length() + 1);
        b.addAll(new WrappedString($this));
        b.addOne(elem);
        return (IndexedSeq)b.result();
    }

    public final <B> IndexedSeq<B> $colon$plus$extension(String $this, B elem) {
        return this.appended$extension($this, elem);
    }

    public final String appended$extension(String $this, char c) {
        return new java.lang.StringBuilder($this.length() + 1).append($this).append(c).toString();
    }

    public final String $colon$plus$extension(String $this, char c) {
        return this.appended$extension($this, c);
    }

    public final <B> IndexedSeq<B> appendedAll$extension(String $this, IterableOnce<B> suffix) {
        return this.concat$extension($this, suffix);
    }

    public final <B> IndexedSeq<B> $colon$plus$plus$extension(String $this, IterableOnce<B> suffix) {
        return this.concat$extension($this, suffix);
    }

    public final String appendedAll$extension(String $this, String suffix) {
        return new java.lang.StringBuilder(0).append($this).append(suffix).toString();
    }

    public final String $colon$plus$plus$extension(String $this, String suffix) {
        return new java.lang.StringBuilder(0).append($this).append(suffix).toString();
    }

    public final <B> IndexedSeq<B> patch$extension(String $this, int from, IterableOnce<B> other, int replaced) {
        int chunk1;
        int len = $this.length();
        Builder b = IndexedSeq$.MODULE$.newBuilder();
        int k = other.knownSize();
        if (k >= 0) {
            b.sizeHint(len + k - replaced);
        }
        int n = from > 0 ? Math.min(from, len) : (chunk1 = 0);
        if (chunk1 > 0) {
            int slc$1_off = 0;
            b.addAll(new WrappedString($this.substring(slc$1_off, slc$1_off + chunk1)));
        }
        if (b == null) {
            throw null;
        }
        b.addAll(other);
        int remaining = len - chunk1 - replaced;
        if (remaining > 0) {
            int slc$1_off = len - remaining;
            b.addAll(new WrappedString($this.substring(slc$1_off, slc$1_off + remaining)));
        }
        return (IndexedSeq)b.result();
    }

    public final String patch$extension(String $this, int from, IterableOnce<Object> other, int replaced) {
        Iterator<Object> iterator2 = other.iterator();
        if (iterator2 == null) {
            throw null;
        }
        Iterator<Object> mkString_this = iterator2;
        String mkString_mkString_sep = "";
        Object var6_6 = null;
        Object var5_5 = null;
        return this.patch$extension($this, from, mkString_this.mkString("", mkString_mkString_sep, ""), replaced);
    }

    public final String patch$extension(String $this, int from, String other, int replaced) {
        int chunk1;
        int len = $this.length();
        java.lang.StringBuilder sb = new java.lang.StringBuilder(len + other.length() - replaced);
        int n = from > 0 ? Math.min(from, len) : (chunk1 = 0);
        if (chunk1 > 0) {
            sb.append($this, 0, chunk1);
        }
        sb.append(other);
        int remaining = len - chunk1 - replaced;
        if (remaining > 0) {
            sb.append($this, len - remaining, len);
        }
        return sb.toString();
    }

    public final String updated$extension(String $this, int index, char elem) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder($this.length()).append($this);
        sb.setCharAt(index, elem);
        return sb.toString();
    }

    public final boolean contains$extension(String $this, char elem) {
        return $this.indexOf(elem) >= 0;
    }

    public final String mkString$extension(String $this, String start2, String sep, String end) {
        StringBuilder stringBuilder = this.addString$extension($this, new StringBuilder(), start2, sep, end);
        if (stringBuilder == null) {
            throw null;
        }
        return stringBuilder.result();
    }

    public final String mkString$extension(String $this, String sep) {
        if (sep.isEmpty() || $this.length() < 2) {
            return $this;
        }
        return this.mkString$extension($this, "", sep, "");
    }

    public final String mkString$extension(String $this) {
        return $this;
    }

    public final StringBuilder addString$extension(String $this, StringBuilder b) {
        return b.append($this);
    }

    public final StringBuilder addString$extension(String $this, StringBuilder b, String sep) {
        return this.addString$extension($this, b, "", sep, "");
    }

    public final StringBuilder addString$extension(String $this, StringBuilder b, String start2, String sep, String end) {
        int len;
        java.lang.StringBuilder jsb = b.underlying();
        if (start2.length() != 0) {
            jsb.append(start2);
        }
        if ((len = $this.length()) != 0) {
            if (sep.isEmpty()) {
                jsb.append($this);
            } else {
                jsb.ensureCapacity(jsb.length() + len + end.length() + (len - 1) * sep.length());
                jsb.append($this.charAt(0));
                for (int i = 1; i < len; ++i) {
                    jsb.append(sep);
                    jsb.append($this.charAt(i));
                }
            }
        }
        if (end.length() != 0) {
            jsb.append(end);
        }
        return b;
    }

    public final String slice$extension(String $this, int from, int until) {
        int max$extension_that = 0;
        int start2 = Math.max(from, max$extension_that);
        int min$extension_that = $this.length();
        int end = Math.min(until, min$extension_that);
        if (start2 >= end) {
            return "";
        }
        return $this.substring(start2, end);
    }

    public final String $times$extension(String $this, int n) {
        if (n <= 0) {
            return "";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder($this.length() * n);
        for (int i = 0; i < n; ++i) {
            sb.append($this);
        }
        return sb.toString();
    }

    public final boolean isLineBreak$extension(String $this, char c) {
        return c == '\r' || c == '\n';
    }

    public final boolean isLineBreak2$extension(String $this, char c0, char c) {
        return c0 == '\r' && c == '\n';
    }

    public final String stripLineEnd$extension(String $this) {
        int apply$extension_i;
        if ($this.isEmpty()) {
            return $this;
        }
        int i = $this.length() - 1;
        char last = $this.charAt(i);
        if (!(last == '\r' || last == '\n')) {
            return $this;
        }
        if (i > 0 && $this.charAt(apply$extension_i = i - 1) == '\r' && last == '\n') {
            --i;
        }
        return $this.substring(0, i);
    }

    public final Iterator<String> linesWithSeparators$extension(String $this) {
        boolean linesSeparated$extension_stripped = false;
        return new AbstractIterator<String>($this, linesSeparated$extension_stripped){
            public final int scala$collection$StringOps$$anon$$len;
            public int scala$collection$StringOps$$anon$$index;
            private final String $this$2;
            private final boolean stripped$1;

            public boolean hasNext() {
                return !(this.scala$collection$StringOps$$anon$$index >= this.scala$collection$StringOps$$anon$$len);
            }

            public String next() {
                if (this.scala$collection$StringOps$$anon$$index >= this.scala$collection$StringOps$$anon$$len) {
                    return (String)((Object)Iterator$.scala$collection$Iterator$$_empty.next());
                }
                return this.advance();
            }

            private boolean done() {
                return this.scala$collection$StringOps$$anon$$index >= this.scala$collection$StringOps$$anon$$len;
            }

            /*
             * WARNING - void declaration
             */
            private String advance() {
                int start2 = this.scala$collection$StringOps$$anon$$index;
                while (!(this.scala$collection$StringOps$$anon$$index >= this.scala$collection$StringOps$$anon$$len)) {
                    char isLineBreak$extension_c = this.$this$2.charAt(this.scala$collection$StringOps$$anon$$index);
                    if (isLineBreak$extension_c == '\r' || isLineBreak$extension_c == '\n') break;
                    ++this.scala$collection$StringOps$$anon$$index;
                }
                int end = this.scala$collection$StringOps$$anon$$index;
                if (!(this.scala$collection$StringOps$$anon$$index >= this.scala$collection$StringOps$$anon$$len)) {
                    void apply$extension_i;
                    int n = this.scala$collection$StringOps$$anon$$index++;
                    String apply$extension_$this = this.$this$2;
                    Object var5_5 = null;
                    char c = apply$extension_$this.charAt((int)apply$extension_i);
                    if (!(this.scala$collection$StringOps$$anon$$index >= this.scala$collection$StringOps$$anon$$len)) {
                        void apply$extension_i2;
                        int n2 = this.scala$collection$StringOps$$anon$$index;
                        String apply$extension_$this2 = this.$this$2;
                        Object var7_8 = null;
                        char isLineBreak2$extension_c = apply$extension_$this2.charAt((int)apply$extension_i2);
                        if (c == '\r' && isLineBreak2$extension_c == '\n') {
                            ++this.scala$collection$StringOps$$anon$$index;
                        }
                    }
                    if (!this.stripped$1) {
                        end = this.scala$collection$StringOps$$anon$$index;
                    }
                }
                return this.$this$2.substring(start2, end);
            }
            {
                this.$this$2 = $this$2;
                this.stripped$1 = stripped$1;
                this.scala$collection$StringOps$$anon$$len = $this$2.length();
                this.scala$collection$StringOps$$anon$$index = 0;
            }
        };
    }

    public final Iterator<String> linesIterator$extension(String $this) {
        boolean linesSeparated$extension_stripped = true;
        return new /* invalid duplicate definition of identical inner class */;
    }

    public final Iterator<String> linesSeparated$extension(String $this, boolean stripped) {
        return new /* invalid duplicate definition of identical inner class */;
    }

    public final Iterator<String> lines$extension(String $this) {
        boolean linesIterator$extension_linesSeparated$extension_stripped = true;
        return new /* invalid duplicate definition of identical inner class */;
    }

    public final String capitalize$extension(String $this) {
        block3: {
            block2: {
                if ($this == null || $this.length() == 0) break block2;
                if (Character.isLowerCase($this.charAt(0))) break block3;
            }
            return $this;
        }
        return this.updated$extension($this, 0, Character.toUpperCase($this.charAt(0)));
    }

    public final String stripPrefix$extension(String $this, String prefix) {
        if ($this.startsWith(prefix)) {
            return $this.substring(prefix.length());
        }
        return $this;
    }

    public final String stripSuffix$extension(String $this, String suffix) {
        if ($this.endsWith(suffix)) {
            return $this.substring(0, $this.length() - suffix.length());
        }
        return $this;
    }

    public final String replaceAllLiterally$extension(String $this, String literal, String replacement) {
        return $this.replace(literal, replacement);
    }

    public final String stripMargin$extension(String $this, char marginChar) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder($this.length());
        boolean linesWithSeparators$extension_linesSeparated$extension_stripped = false;
        new /* invalid duplicate definition of identical inner class */.foreach((Function1<String, java.lang.StringBuilder> & Serializable)line -> {
            int index;
            int len = line.length();
            for (index = 0; index < len && line.charAt(index) <= ' '; ++index) {
            }
            String stripped = index < len && line.charAt(index) == marginChar ? line.substring(index + 1) : line;
            return sb.append(stripped);
        });
        return sb.toString();
    }

    public final String stripMargin$extension(String $this) {
        return this.stripMargin$extension($this, '|');
    }

    public final String escape$extension(String $this, char ch) {
        if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9') {
            return Character.toString(ch);
        }
        return new java.lang.StringBuilder(1).append("\\").append(ch).toString();
    }

    public final String[] split$extension(String $this, char separator) {
        return $this.split(this.escape$extension($this, separator));
    }

    public final String[] split$extension(String $this, char[] separators) throws PatternSyntaxException {
        java.lang.StringBuilder stringBuilder = new java.lang.StringBuilder(1);
        String foldLeft$extension_z = "[";
        if (separators == null) {
            throw new NullPointerException();
        }
        int foldLeft$extension_f$mCc$sp$1_length = separators.length;
        String foldLeft$extension_f$mCc$sp$1_v = foldLeft$extension_z;
        for (int foldLeft$extension_f$mCc$sp$1_i = 0; foldLeft$extension_f$mCc$sp$1_i < foldLeft$extension_f$mCc$sp$1_length; ++foldLeft$extension_f$mCc$sp$1_i) {
            char c = separators[foldLeft$extension_f$mCc$sp$1_i];
            foldLeft$extension_f$mCc$sp$1_v = StringOps$.$anonfun$split$1($this, foldLeft$extension_f$mCc$sp$1_v, c);
        }
        Object var6_5 = null;
        Object var4_3 = null;
        String re = stringBuilder.append(foldLeft$extension_f$mCc$sp$1_v).append("]").toString();
        return $this.split(re);
    }

    public final Regex r$extension(String $this) {
        return new Regex($this, (scala.collection.immutable.Seq<String>)Nil$.MODULE$);
    }

    public final Regex r$extension(String $this, scala.collection.immutable.Seq<String> groupNames) {
        return new Regex($this, groupNames);
    }

    public final boolean toBoolean$extension(String $this) {
        return this.toBooleanImpl$extension($this, $this);
    }

    public final Option<Object> toBooleanOption$extension(String $this) {
        if ($this.equalsIgnoreCase("true")) {
            return new Some<Object>(true);
        }
        if ($this.equalsIgnoreCase("false")) {
            return new Some<Object>(false);
        }
        return None$.MODULE$;
    }

    public final byte toByte$extension(String $this) {
        return Byte.parseByte($this);
    }

    public final Option<Object> toByteOption$extension(String $this) {
        return StringParsers$.MODULE$.parseByte($this);
    }

    public final short toShort$extension(String $this) {
        return Short.parseShort($this);
    }

    public final Option<Object> toShortOption$extension(String $this) {
        return StringParsers$.MODULE$.parseShort($this);
    }

    public final int toInt$extension(String $this) {
        return Integer.parseInt($this);
    }

    public final Option<Object> toIntOption$extension(String $this) {
        return StringParsers$.MODULE$.parseInt($this);
    }

    public final long toLong$extension(String $this) {
        return Long.parseLong($this);
    }

    public final Option<Object> toLongOption$extension(String $this) {
        return StringParsers$.MODULE$.parseLong($this);
    }

    public final float toFloat$extension(String $this) {
        return Float.parseFloat($this);
    }

    public final Option<Object> toFloatOption$extension(String $this) {
        if (StringParsers$.MODULE$.checkFloatFormat($this)) {
            return new Some<Object>(Float.valueOf(Float.parseFloat($this)));
        }
        return None$.MODULE$;
    }

    public final double toDouble$extension(String $this) {
        return Double.parseDouble($this);
    }

    public final Option<Object> toDoubleOption$extension(String $this) {
        if (StringParsers$.MODULE$.checkFloatFormat($this)) {
            return new Some<Object>(Double.parseDouble($this));
        }
        return None$.MODULE$;
    }

    public final boolean toBooleanImpl$extension(String $this, String s2) {
        if (s2 == null) {
            throw new IllegalArgumentException("For input string: \"null\"");
        }
        if (s2.equalsIgnoreCase("true")) {
            return true;
        }
        if (s2.equalsIgnoreCase("false")) {
            return false;
        }
        throw new IllegalArgumentException(new java.lang.StringBuilder(20).append("For input string: \"").append(s2).append("\"").toString());
    }

    public final <B> Object toArray$extension(String $this, ClassTag<B> tag) {
        ClassTag<B> classTag = tag;
        ManifestFactory.CharManifest charManifest = ClassTag$.MODULE$.Char();
        if (classTag != null && ((Object)classTag).equals(charManifest)) {
            return $this.toCharArray();
        }
        return IterableOnceOps.toArray$(new WrappedString($this), tag);
    }

    public final Object unwrapArg$extension(String $this, Object arg) {
        if (arg instanceof ScalaNumber) {
            return ((ScalaNumber)arg).underlying();
        }
        return arg;
    }

    public final String format$extension(String $this, scala.collection.immutable.Seq<Object> args2) {
        return String.format($this, (Object[])((IterableOnceOps)args2.map((Function1<Object, Object> & Serializable)arg -> MODULE$.unwrapArg$extension($this, arg))).toArray(ClassTag$.MODULE$.AnyRef()));
    }

    public final String formatLocal$extension(String $this, Locale l, scala.collection.immutable.Seq<Object> args2) {
        return String.format(l, $this, (Object[])((IterableOnceOps)args2.map((Function1<Object, Object> & Serializable)arg -> MODULE$.unwrapArg$extension($this, arg))).toArray(ClassTag$.MODULE$.AnyRef()));
    }

    public final int compare$extension(String $this, String that) {
        return $this.compareTo(that);
    }

    public final boolean $less$extension(String $this, String that) {
        return $this.compareTo(that) < 0;
    }

    public final boolean $greater$extension(String $this, String that) {
        return $this.compareTo(that) > 0;
    }

    public final boolean $less$eq$extension(String $this, String that) {
        return $this.compareTo(that) <= 0;
    }

    public final boolean $greater$eq$extension(String $this, String that) {
        return $this.compareTo(that) >= 0;
    }

    public final int count$extension(String $this, Function1<Object, Object> p) {
        int res = 0;
        int len = $this.length();
        for (int i = 0; i < len; ++i) {
            if (!BoxesRunTime.unboxToBoolean(p.apply(Character.valueOf($this.charAt(i))))) continue;
            ++res;
        }
        return res;
    }

    public final <U> void foreach$extension(String $this, Function1<Object, U> f) {
        int len = $this.length();
        for (int i = 0; i < len; ++i) {
            f.apply(Character.valueOf($this.charAt(i)));
        }
    }

    public final boolean forall$extension(String $this, Function1<Object, Object> p) {
        int len = $this.length();
        for (int i = 0; i < len; ++i) {
            if (BoxesRunTime.unboxToBoolean(p.apply(Character.valueOf($this.charAt(i))))) continue;
            return false;
        }
        return true;
    }

    public final <B> B foldLeft$extension(String $this, B z, Function2<B, Object, B> op) {
        B v = z;
        int len = $this.length();
        for (int i = 0; i < len; ++i) {
            v = op.apply(v, Character.valueOf($this.charAt(i)));
        }
        return v;
    }

    public final <B> B foldRight$extension(String $this, B z, Function2<Object, B, B> op) {
        B v = z;
        for (int i = $this.length() - 1; i >= 0; --i) {
            v = op.apply(Character.valueOf($this.charAt(i)), v);
        }
        return v;
    }

    public final <A1> A1 fold$extension(String $this, A1 z, Function2<A1, A1, A1> op) {
        A1 foldLeft$extension_v = z;
        int foldLeft$extension_len = $this.length();
        for (int foldLeft$extension_i = 0; foldLeft$extension_i < foldLeft$extension_len; ++foldLeft$extension_i) {
            foldLeft$extension_v = op.apply(foldLeft$extension_v, Character.valueOf($this.charAt(foldLeft$extension_i)));
        }
        return foldLeft$extension_v;
    }

    public final char head$extension(String $this) {
        if ($this.isEmpty()) {
            throw new NoSuchElementException("head of empty String");
        }
        return $this.charAt(0);
    }

    public final Option<Object> headOption$extension(String $this) {
        if ($this.isEmpty()) {
            return None$.MODULE$;
        }
        return new Some<Object>(Character.valueOf($this.charAt(0)));
    }

    public final char last$extension(String $this) {
        if ($this.isEmpty()) {
            throw new NoSuchElementException("last of empty String");
        }
        return $this.charAt($this.length() - 1);
    }

    public final Option<Object> lastOption$extension(String $this) {
        if ($this.isEmpty()) {
            return None$.MODULE$;
        }
        return new Some<Object>(Character.valueOf($this.charAt($this.length() - 1)));
    }

    /*
     * WARNING - void declaration
     */
    public final Range indices$extension(String $this) {
        void apply_end;
        void apply_start;
        int n = $this.length();
        boolean bl = false;
        if (package$.MODULE$.Range() == null) {
            throw null;
        }
        return new Range.Exclusive((int)apply_start, (int)apply_end, 1);
    }

    public final Iterator<Object> iterator$extension(String $this) {
        return new StringOps.StringIterator($this);
    }

    public final IntStepper stepper$extension(String $this) {
        return new CharStringStepper($this, 0, $this.length());
    }

    public final IntStepper charStepper$extension(String $this) {
        return new CharStringStepper($this, 0, $this.length());
    }

    public final IntStepper codePointStepper$extension(String $this) {
        return new CodePointStringStepper($this, 0, $this.length());
    }

    public final boolean nonEmpty$extension(String $this) {
        return !$this.isEmpty();
    }

    public final String reverse$extension(String $this) {
        return new java.lang.StringBuilder($this).reverse().toString();
    }

    public final Iterator<Object> reverseIterator$extension(String $this) {
        return new StringOps.ReverseIterator($this);
    }

    public final StringOps.WithFilter withFilter$extension(String $this, Function1<Object, Object> p) {
        return new StringOps.WithFilter(p, $this);
    }

    public final String tail$extension(String $this) {
        return this.slice$extension($this, 1, $this.length());
    }

    public final String init$extension(String $this) {
        return this.slice$extension($this, 0, $this.length() - 1);
    }

    public final String take$extension(String $this, int n) {
        int min_y = $this.length();
        return this.slice$extension($this, 0, Math.min(n, min_y));
    }

    public final String drop$extension(String $this, int n) {
        int min_y = $this.length();
        return this.slice$extension($this, Math.min(n, min_y), $this.length());
    }

    public final String takeRight$extension(String $this, int n) {
        int max_y = 0;
        return this.drop$extension($this, $this.length() - Math.max(n, max_y));
    }

    public final String dropRight$extension(String $this, int n) {
        int max_y = 0;
        return this.take$extension($this, $this.length() - Math.max(n, max_y));
    }

    /*
     * WARNING - void declaration
     */
    public final Iterator<String> tails$extension(String $this) {
        void iterateUntilEmpty$extension_$plus$plus_xs;
        Function1<String, String> & Serializable iterateUntilEmpty$extension_f = (Function1<String, String> & Serializable)x$4 -> MODULE$.tail$extension((String)x$4);
        Iterator iterator2 = new AbstractIterator<T>($this, iterateUntilEmpty$extension_f){
            private boolean first;
            private T acc;
            private final Function1 f$6;

            public boolean hasNext() {
                return true;
            }

            public T next() {
                if (this.first) {
                    this.first = false;
                } else {
                    this.acc = this.f$6.apply(this.acc);
                }
                return this.acc;
            }
            {
                this.f$6 = f$6;
                this.first = true;
                this.acc = start$3;
            }
        }.takeWhile((Function1<String, Object> & Serializable)x -> BoxesRunTime.boxToBoolean(StringOps$.$anonfun$iterateUntilEmpty$1(x)));
        Function0<Iterator> & Serializable intersect = (Function0<Iterator> & Serializable)() -> Iterator$.MODULE$.single("");
        if (iterator2 == null) {
            throw null;
        }
        return iterator2.concat(iterateUntilEmpty$extension_$plus$plus_xs);
    }

    /*
     * WARNING - void declaration
     */
    public final Iterator<String> inits$extension(String $this) {
        void iterateUntilEmpty$extension_$plus$plus_xs;
        Function1<String, String> & Serializable iterateUntilEmpty$extension_f = (Function1<String, String> & Serializable)x$5 -> MODULE$.init$extension((String)x$5);
        Iterator iterator2 = new /* invalid duplicate definition of identical inner class */.takeWhile((Function1<String, Object> & Serializable)x -> BoxesRunTime.boxToBoolean(StringOps$.$anonfun$iterateUntilEmpty$1(x)));
        Function0<Iterator> & Serializable intersect = (Function0<Iterator> & Serializable)() -> Iterator$.MODULE$.single("");
        if (iterator2 == null) {
            throw null;
        }
        return iterator2.concat(iterateUntilEmpty$extension_$plus$plus_xs);
    }

    /*
     * WARNING - void declaration
     */
    public final Iterator<String> iterateUntilEmpty$extension(String $this, Function1<String, String> f) {
        void $plus$plus_xs;
        Iterator iterator2 = new /* invalid duplicate definition of identical inner class */.takeWhile((Function1<String, Object> & Serializable)x -> BoxesRunTime.boxToBoolean(StringOps$.$anonfun$iterateUntilEmpty$1(x)));
        Function0<Iterator> & Serializable intersect = (Function0<Iterator> & Serializable)() -> Iterator$.MODULE$.single("");
        if (iterator2 == null) {
            throw null;
        }
        return iterator2.concat($plus$plus_xs);
    }

    public final String filter$extension(String $this, Function1<Object, Object> pred) {
        int len = $this.length();
        java.lang.StringBuilder sb = new java.lang.StringBuilder(len);
        for (int i = 0; i < len; ++i) {
            char x = $this.charAt(i);
            if (!BoxesRunTime.unboxToBoolean(pred.apply(Character.valueOf(x)))) continue;
            sb.append(x);
        }
        if (len == sb.length()) {
            return $this;
        }
        return sb.toString();
    }

    public final String filterNot$extension(String $this, Function1<Object, Object> pred) {
        int filter$extension_len = $this.length();
        java.lang.StringBuilder filter$extension_sb = new java.lang.StringBuilder(filter$extension_len);
        for (int filter$extension_i = 0; filter$extension_i < filter$extension_len; ++filter$extension_i) {
            char filter$extension_x = $this.charAt(filter$extension_i);
            if (!(!BoxesRunTime.unboxToBoolean(pred.apply(Character.valueOf(filter$extension_x))))) continue;
            filter$extension_sb.append(filter$extension_x);
        }
        if (filter$extension_len == filter$extension_sb.length()) {
            return $this;
        }
        return filter$extension_sb.toString();
    }

    public final int copyToArray$extension(String $this, char[] xs) {
        return this.copyToArray$extension($this, xs, 0, Integer.MAX_VALUE);
    }

    public final int copyToArray$extension(String $this, char[] xs, int start2) {
        return this.copyToArray$extension($this, xs, start2, Integer.MAX_VALUE);
    }

    /*
     * WARNING - void declaration
     */
    public final int copyToArray$extension(String $this, char[] xs, int start2, int len) {
        void elemsToCopyToArray_destLen;
        int n = xs.length;
        int elemsToCopyToArray_srcLen = $this.length();
        int copied = Math.max(Math.min(Math.min(len, elemsToCopyToArray_srcLen), (int)(elemsToCopyToArray_destLen - start2)), 0);
        if (copied > 0) {
            $this.getChars(0, copied, xs, start2);
        }
        return copied;
    }

    public final int indexWhere$extension(String $this, Function1<Object, Object> p, int from) {
        int len = $this.length();
        for (int i = from; i < len; ++i) {
            if (!BoxesRunTime.unboxToBoolean(p.apply(Character.valueOf($this.charAt(i))))) continue;
            return i;
        }
        return -1;
    }

    public final int indexWhere$default$2$extension(String $this) {
        return 0;
    }

    public final int lastIndexWhere$extension(String $this, Function1<Object, Object> p, int end) {
        int len = $this.length();
        int min_y = len - 1;
        for (int i = Math.min(end, min_y); i >= 0; --i) {
            if (!BoxesRunTime.unboxToBoolean(p.apply(Character.valueOf($this.charAt(i))))) continue;
            return i;
        }
        return -1;
    }

    public final int lastIndexWhere$default$2$extension(String $this) {
        return Integer.MAX_VALUE;
    }

    public final boolean exists$extension(String $this, Function1<Object, Object> p) {
        int n;
        block1: {
            int indexWhere$extension_from = 0;
            int indexWhere$extension_len = $this.length();
            for (int indexWhere$extension_i = indexWhere$extension_from; indexWhere$extension_i < indexWhere$extension_len; ++indexWhere$extension_i) {
                if (!BoxesRunTime.unboxToBoolean(p.apply(Character.valueOf($this.charAt(indexWhere$extension_i))))) continue;
                n = indexWhere$extension_i;
                break block1;
            }
            n = -1;
        }
        return n != -1;
    }

    public final Option<Object> find$extension(String $this, Function1<Object, Object> p) {
        int n;
        block4: {
            int indexWhere$extension_from = 0;
            int indexWhere$extension_len = $this.length();
            for (int indexWhere$extension_i = indexWhere$extension_from; indexWhere$extension_i < indexWhere$extension_len; ++indexWhere$extension_i) {
                if (!BoxesRunTime.unboxToBoolean(p.apply(Character.valueOf($this.charAt(indexWhere$extension_i))))) continue;
                n = indexWhere$extension_i;
                break block4;
            }
            n = -1;
        }
        int n2 = n;
        switch (n2) {
            case -1: {
                return None$.MODULE$;
            }
        }
        return new Some<Object>(Character.valueOf($this.charAt(n2)));
    }

    /*
     * WARNING - void declaration
     */
    public final String dropWhile$extension(String $this, Function1<Object, Object> p) {
        int n;
        block4: {
            int indexWhere$extension_from = 0;
            int indexWhere$extension_len = $this.length();
            for (int indexWhere$extension_i = indexWhere$extension_from; indexWhere$extension_i < indexWhere$extension_len; ++indexWhere$extension_i) {
                void var7_6;
                char indexWhere$extension_boxToCharacter_c = $this.charAt(indexWhere$extension_i);
                if (!(!BoxesRunTime.unboxToBoolean(p.apply(Character.valueOf((char)var7_6))))) continue;
                n = indexWhere$extension_i;
                break block4;
            }
            n = -1;
        }
        int n2 = n;
        switch (n2) {
            case -1: {
                return "";
            }
        }
        return $this.substring(n2);
    }

    /*
     * WARNING - void declaration
     */
    public final String takeWhile$extension(String $this, Function1<Object, Object> p) {
        int n;
        block4: {
            int indexWhere$extension_from = 0;
            int indexWhere$extension_len = $this.length();
            for (int indexWhere$extension_i = indexWhere$extension_from; indexWhere$extension_i < indexWhere$extension_len; ++indexWhere$extension_i) {
                void var7_6;
                char indexWhere$extension_boxToCharacter_c = $this.charAt(indexWhere$extension_i);
                if (!(!BoxesRunTime.unboxToBoolean(p.apply(Character.valueOf((char)var7_6))))) continue;
                n = indexWhere$extension_i;
                break block4;
            }
            n = -1;
        }
        int n2 = n;
        switch (n2) {
            case -1: {
                return $this;
            }
        }
        return $this.substring(0, n2);
    }

    public final Tuple2<String, String> splitAt$extension(String $this, int n) {
        return new Tuple2<String, String>(this.take$extension($this, n), this.drop$extension($this, n));
    }

    /*
     * WARNING - void declaration
     */
    public final Tuple2<String, String> span$extension(String $this, Function1<Object, Object> p) {
        int n;
        block4: {
            int indexWhere$extension_from = 0;
            int indexWhere$extension_len = $this.length();
            for (int indexWhere$extension_i = indexWhere$extension_from; indexWhere$extension_i < indexWhere$extension_len; ++indexWhere$extension_i) {
                void var7_6;
                char indexWhere$extension_boxToCharacter_c = $this.charAt(indexWhere$extension_i);
                if (!(!BoxesRunTime.unboxToBoolean(p.apply(Character.valueOf((char)var7_6))))) continue;
                n = indexWhere$extension_i;
                break block4;
            }
            n = -1;
        }
        int n2 = n;
        switch (n2) {
            case -1: {
                return new Tuple2<String, String>($this, "");
            }
        }
        return new Tuple2<String, String>($this.substring(0, n2), $this.substring(n2));
    }

    public final Iterator<String> grouped$extension(String $this, int size) {
        return new StringOps.GroupedIterator($this, size);
    }

    public final Tuple2<String, String> partition$extension(String $this, Function1<Object, Object> p) {
        java.lang.StringBuilder res1 = new java.lang.StringBuilder();
        java.lang.StringBuilder res2 = new java.lang.StringBuilder();
        int len = $this.length();
        for (int i = 0; i < len; ++i) {
            char x = $this.charAt(i);
            (BoxesRunTime.unboxToBoolean(p.apply(Character.valueOf(x))) ? res1 : res2).append(x);
        }
        return new Tuple2<String, String>(res1.toString(), res2.toString());
    }

    public final Tuple2<String, String> partitionMap$extension(String $this, Function1<Object, Either<Object, Object>> f) {
        java.lang.StringBuilder res1 = new java.lang.StringBuilder();
        java.lang.StringBuilder res2 = new java.lang.StringBuilder();
        int len = $this.length();
        for (int i = 0; i < len; ++i) {
            Either<Object, Object> either = f.apply(Character.valueOf($this.charAt(i)));
            if (either instanceof Left) {
                char c = BoxesRunTime.unboxToChar(((Left)either).value());
                res1.append(c);
                continue;
            }
            if (either instanceof Right) {
                char c = BoxesRunTime.unboxToChar(((Right)either).value());
                res2.append(c);
                continue;
            }
            throw new MatchError(either);
        }
        return new Tuple2<String, String>(res1.toString(), res2.toString());
    }

    public final <B> LazyZip2<Object, B, String> lazyZip$extension(String $this, Iterable<B> that) {
        return new LazyZip2<Object, B, String>($this, new WrappedString($this), that);
    }

    public final <B> String diff$extension(String $this, Seq<B> that) {
        return ((WrappedString)SeqOps.diff$(new WrappedString($this), that)).scala$collection$immutable$WrappedString$$self();
    }

    public final <B> String intersect$extension(String $this, Seq<B> that) {
        return ((WrappedString)SeqOps.intersect$(new WrappedString($this), that)).scala$collection$immutable$WrappedString$$self();
    }

    public final String distinct$extension(String $this) {
        return ((WrappedString)SeqOps.distinct$(new WrappedString($this))).scala$collection$immutable$WrappedString$$self();
    }

    public final <B> String distinctBy$extension(String $this, Function1<Object, B> f) {
        WrappedString distinctBy_this = new WrappedString($this);
        View.DistinctBy<Object, B> fromSpecific_coll = new View.DistinctBy<Object, B>(distinctBy_this, f);
        Object var4_4 = null;
        Object var3_3 = null;
        return ((WrappedString)WrappedString$.MODULE$.fromSpecific((IterableOnce)fromSpecific_coll)).scala$collection$immutable$WrappedString$$self();
    }

    public final <B> String sorted$extension(String $this, Ordering<B> ord) {
        return ((WrappedString)SeqOps.sorted$(new WrappedString($this), ord)).scala$collection$immutable$WrappedString$$self();
    }

    public final String sortWith$extension(String $this, Function2<Object, Object, Object> lt) {
        if (package$.MODULE$.Ordering() == null) {
            throw null;
        }
        return ((WrappedString)new WrappedString($this).sorted(new Ordering<T>(lt){
            private final Function2 cmp$2;

            public Some<Object> tryCompare(T x, T y) {
                return Ordering.tryCompare$(this, x, y);
            }

            public boolean equiv(T x, T y) {
                return Ordering.equiv$(this, x, y);
            }

            public <U extends T> U max(U x, U y) {
                return (U)Ordering.max$(this, x, y);
            }

            public <U extends T> U min(U x, U y) {
                return (U)Ordering.min$(this, x, y);
            }

            public Ordering<T> reverse() {
                return Ordering.reverse$(this);
            }

            public boolean isReverseOf(Ordering<?> other) {
                return Ordering.isReverseOf$(this, other);
            }

            public <U> Ordering<U> on(Function1<U, T> f) {
                return Ordering.on$(this, f);
            }

            public Ordering<T> orElse(Ordering<T> other) {
                return Ordering.orElse$(this, other);
            }

            public <S> Ordering<T> orElseBy(Function1<T, S> f, Ordering<S> ord) {
                return Ordering.orElseBy$(this, f, ord);
            }

            public Ordering.OrderingOps mkOrderingOps(T lhs) {
                return Ordering.mkOrderingOps$(this, lhs);
            }

            public int compare(T x, T y) {
                if (BoxesRunTime.unboxToBoolean(this.cmp$2.apply(x, y))) {
                    return -1;
                }
                if (BoxesRunTime.unboxToBoolean(this.cmp$2.apply(y, x))) {
                    return 1;
                }
                return 0;
            }

            public boolean lt(T x, T y) {
                return BoxesRunTime.unboxToBoolean(this.cmp$2.apply(x, y));
            }

            public boolean gt(T x, T y) {
                return BoxesRunTime.unboxToBoolean(this.cmp$2.apply(y, x));
            }

            public boolean gteq(T x, T y) {
                return !BoxesRunTime.unboxToBoolean(this.cmp$2.apply(x, y));
            }

            public boolean lteq(T x, T y) {
                return !BoxesRunTime.unboxToBoolean(this.cmp$2.apply(y, x));
            }
            {
                this.cmp$2 = cmp$2;
            }
        })).scala$collection$immutable$WrappedString$$self();
    }

    public final <B> String sortBy$extension(String $this, Function1<Object, B> f, Ordering<B> ord) {
        return ((WrappedString)new WrappedString($this).sorted(ord.on(f))).scala$collection$immutable$WrappedString$$self();
    }

    public final <K> Map<K, String> groupBy$extension(String $this, Function1<Object, K> f) {
        Object var13_13;
        Object var12_12;
        Tuple2 tuple2;
        WrappedString groupBy_this = new WrappedString($this);
        scala.collection.mutable.Map groupBy_m = (scala.collection.mutable.Map)Map$.MODULE$.empty();
        Iterator groupBy_it = IndexedSeqOps.iterator$(groupBy_this);
        while (groupBy_it.hasNext()) {
            Object groupBy_elem = groupBy_it.next();
            K groupBy_key = f.apply(groupBy_elem);
            Builder groupBy_bldr = (Builder)groupBy_m.getOrElseUpdate(groupBy_key, () -> IterableOps.$anonfun$groupBy$1(groupBy_this));
            if (groupBy_bldr == null) {
                throw null;
            }
            groupBy_bldr.addOne(groupBy_elem);
        }
        Object groupBy_result = HashMap$.MODULE$.empty();
        Iterator groupBy_mapIt = groupBy_m.iterator();
        while (groupBy_mapIt.hasNext()) {
            tuple2 = (Tuple2)groupBy_mapIt.next();
            if (tuple2 == null) {
                throw new MatchError((Object)null);
            }
            Object groupBy_k = tuple2._1();
            Builder groupBy_v = (Builder)tuple2._2();
            groupBy_result = ((HashMap)groupBy_result).updated((Object)var12_12, var13_13.result());
        }
        Object var4_4 = null;
        Object var5_5 = null;
        Object var6_6 = null;
        Object var7_7 = null;
        Object var8_8 = null;
        Object var9_9 = null;
        Object var10_10 = null;
        tuple2 = null;
        var12_12 = null;
        var13_13 = null;
        Object var3_3 = null;
        return groupBy_result.view().mapValues((Function1<WrappedString, String> & Serializable)x$6 -> x$6.scala$collection$immutable$WrappedString$$self()).toMap($less$colon$less$.MODULE$.refl());
    }

    public final Iterator<String> sliding$extension(String $this, int size, int step) {
        return IterableOps.sliding$(new WrappedString($this), size, step).map((Function1<WrappedString, String> & Serializable)x$7 -> x$7.scala$collection$immutable$WrappedString$$self());
    }

    public final int sliding$default$2$extension(String $this) {
        return 1;
    }

    public final Iterator<String> combinations$extension(String $this, int n) {
        return SeqOps.combinations$(new WrappedString($this), n).map((Function1<WrappedString, String> & Serializable)x$8 -> x$8.scala$collection$immutable$WrappedString$$self());
    }

    public final Iterator<String> permutations$extension(String $this) {
        return SeqOps.permutations$(new WrappedString($this)).map((Function1<WrappedString, String> & Serializable)x$9 -> x$9.scala$collection$immutable$WrappedString$$self());
    }

    public final int hashCode$extension(String $this) {
        return $this.hashCode();
    }

    public final boolean equals$extension(String $this, Object x$1) {
        if (x$1 instanceof StringOps) {
            String string = x$1 == null ? null : ((StringOps)x$1).scala$collection$StringOps$$s();
            String string2 = $this;
            if (!(string2 != null ? !string2.equals(string) : string != null)) {
                return true;
            }
        }
        return false;
    }

    private static final WrappedString slc$1(int off, int length, String $this$1) {
        return new WrappedString($this$1.substring(off, off + length));
    }

    public static final /* synthetic */ String $anonfun$split$1(String $this$3, String x$2, char x$3) {
        return new java.lang.StringBuilder(0).append(x$2).append(MODULE$.escape$extension($this$3, x$3)).toString();
    }

    public static final /* synthetic */ boolean $anonfun$iterateUntilEmpty$1(String x) {
        return !x.isEmpty();
    }

    public static final /* synthetic */ boolean $anonfun$filterNot$1(Function1 pred$1, char c) {
        return !BoxesRunTime.unboxToBoolean(pred$1.apply(Character.valueOf(c)));
    }

    public static final /* synthetic */ boolean $anonfun$dropWhile$1(Function1 p$1, char c) {
        return !BoxesRunTime.unboxToBoolean(p$1.apply(Character.valueOf(c)));
    }

    public static final /* synthetic */ boolean $anonfun$takeWhile$1(Function1 p$2, char c) {
        return !BoxesRunTime.unboxToBoolean(p$2.apply(Character.valueOf(c)));
    }

    public static final /* synthetic */ boolean $anonfun$span$1(Function1 p$3, char c) {
        return !BoxesRunTime.unboxToBoolean(p$3.apply(Character.valueOf(c)));
    }

    private StringOps$() {
    }

    public static final /* synthetic */ String $anonfun$split$1$adapted(String $this$3, String x$2, Object x$3) {
        return StringOps$.$anonfun$split$1($this$3, x$2, BoxesRunTime.unboxToChar(x$3));
    }

    public static final /* synthetic */ Object $anonfun$filterNot$1$adapted(Function1 pred$1, Object c) {
        return BoxesRunTime.boxToBoolean(StringOps$.$anonfun$filterNot$1(pred$1, BoxesRunTime.unboxToChar(c)));
    }

    public static final /* synthetic */ Object $anonfun$dropWhile$1$adapted(Function1 p$1, Object c) {
        return BoxesRunTime.boxToBoolean(StringOps$.$anonfun$dropWhile$1(p$1, BoxesRunTime.unboxToChar(c)));
    }

    public static final /* synthetic */ Object $anonfun$takeWhile$1$adapted(Function1 p$2, Object c) {
        return BoxesRunTime.boxToBoolean(StringOps$.$anonfun$takeWhile$1(p$2, BoxesRunTime.unboxToChar(c)));
    }

    public static final /* synthetic */ Object $anonfun$span$1$adapted(Function1 p$3, Object c) {
        return BoxesRunTime.boxToBoolean(StringOps$.$anonfun$span$1(p$3, BoxesRunTime.unboxToChar(c)));
    }
}

