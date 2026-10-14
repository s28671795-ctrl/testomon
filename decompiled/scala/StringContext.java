/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.StringContext$;
import scala.StringContext$s$;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.collection.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\t\u0015c\u0001B\u00193\u0001VB\u0001\"\u0013\u0001\u0003\u0016\u0004%\tA\u0013\u0005\t-\u0002\u0011\t\u0012)A\u0005\u0017\")q\u000b\u0001C\u00011\")1\f\u0001C\u00019\"1Q\u000f\u0001B\u0005\u0002Y<Q!\u001e\u0001\t\u0002e4Qa\u001f\u0001\t\u0002qDQaV\u0004\u0005\u0002uDQA`\u0004\u0005\u0002}D\u0001\"a\u0004\u0001\u0005\u0013\u0005\u0011\u0011\u0003\u0005\b\u0003+\u0001A\u0011AA\f\u0011!\ti\u0003\u0001B\u0005\u0002\u0005=\u0002\"CA!\u0001\u0005\u0005I\u0011IA\"\u0011%\t\u0019\u0006AA\u0001\n\u0003\t)\u0006C\u0005\u0002^\u0001\t\t\u0011\"\u0001\u0002`!I\u0011Q\r\u0001\u0002\u0002\u0013\u0005\u0013q\r\u0005\n\u0003_\u0002\u0011\u0011!C\u0001\u0003cB\u0011\"a\u001f\u0001\u0003\u0003%\t%! \t\u0013\u0005\u0005\u0005!!A\u0005B\u0005\r\u0005\"CAC\u0001\u0005\u0005I\u0011IAD\u0011%\tI\tAA\u0001\n\u0003\nYiB\u0004\u0002\u0010JB\t!!%\u0007\rE\u0012\u0004\u0012AAJ\u0011\u00199v\u0003\"\u0001\u0002 \"9\u0011\u0011U\f\u0005\u0002\u0005\rfABAW/\u0001\ty\u000bC\u0005\u00028j\u0011\t\u0011)A\u0005\u001d\"Q\u0011\u0011\u0018\u000e\u0003\u0006\u0004%\t!!\u0016\t\u0015\u0005m&D!A!\u0002\u0013\t9\u0006\u0003\u0004X5\u0011\u0005\u0011Q\u0018\u0004\b\u0003\u000f<\u0002BMAe\u0011%\t9l\bB\u0001B\u0003%a\n\u0003\u0006\u0002L~\u0011)\u0019!C\u0001\u0003+B!\"!4 \u0005\u0003\u0005\u000b\u0011BA,\u0011)\tIl\bBC\u0002\u0013\u0005\u0011Q\u000b\u0005\u000b\u0003w{\"\u0011!Q\u0001\n\u0005]\u0003BB, \t\u0003\ty\r\u0003\u0005\u0002Z^\u0001K\u0011BAn\u0011\u001d\t\tp\u0006C\u0001\u0003gDq!!@\u0018\t\u0003\ty\u0010\u0003\u0005\u0003\u0004]!\tB\rB\u0003\u0011!\u0011Ia\u0006Q\u0005\n\t-\u0001\u0002\u0003B\n/\u0001&IA!\u0006\t\u000f\u0005Uq\u0003\"\u0001\u0003\u001e!11l\u0006C\u0001\u0005KA\u0011Ba\u000b\u0018\u0003\u0003%\tI!\f\t\u0011y<\u0012\u0011!CA\u0005cA\u0011Ba\u000f\u0018\u0003\u0003%IA!\u0010\u0003\u001bM#(/\u001b8h\u0007>tG/\u001a=u\u0015\u0005\u0019\u0014!B:dC2\f7\u0001A\n\u0005\u0001YRT\b\u0005\u00028q5\t!'\u0003\u0002:e\t1\u0011I\\=SK\u001a\u0004\"aN\u001e\n\u0005q\u0012$a\u0002)s_\u0012,8\r\u001e\t\u0003}\u0019s!a\u0010#\u000f\u0005\u0001\u001bU\"A!\u000b\u0005\t#\u0014A\u0002\u001fs_>$h(C\u00014\u0013\t)%'A\u0004qC\u000e\\\u0017mZ3\n\u0005\u001dC%\u0001D*fe&\fG.\u001b>bE2,'BA#3\u0003\u0015\u0001\u0018M\u001d;t+\u0005Y\u0005cA\u001cM\u001d&\u0011QJ\r\u0002\u000byI,\u0007/Z1uK\u0012t\u0004CA(T\u001d\t\u0001\u0016\u000b\u0005\u0002Ae%\u0011!KM\u0001\u0007!J,G-\u001a4\n\u0005Q+&AB*ue&twM\u0003\u0002Se\u00051\u0001/\u0019:ug\u0002\na\u0001P5oSRtDCA-[!\t9\u0004\u0001C\u0003J\u0007\u0001\u00071*\u0001\u0007dQ\u0016\u001c7\u000eT3oORD7\u000f\u0006\u0002^AB\u0011qGX\u0005\u0003?J\u0012A!\u00168ji\")\u0011\r\u0002a\u0001E\u0006!\u0011M]4t!\r\u0019g\r[\u0007\u0002I*\u0011QMM\u0001\u000bG>dG.Z2uS>t\u0017BA4e\u0005\r\u0019V-\u001d\t\u0003o%L!A\u001b\u001a\u0003\u0007\u0005s\u0017\u0010\u000b\u0004\u0005Y>\u0004(o\u001d\t\u0003o5L!A\u001c\u001a\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0003E\fq'^:fAM\fW.Z\u0017oC6,G\rI7fi\"|G\rI8oAM#(/\u001b8h\u0007>tG/\u001a=uA\r|W\u000e]1oS>t\u0007e\u001c2kK\u000e$\u0018!B:j]\u000e,\u0017%\u0001;\u0002\rIr\u0013g\r\u00181\u0003\u0005\u0019HC\u0001(x\u0011\u0015\tW\u00011\u0001y!\r9D\n\u001b\t\u0003u\u001ei\u0011\u0001\u0001\u0002\u0002gN\u0011qA\u000e\u000b\u0002s\u0006QQO\\1qa2L8+Z9\u0015\t\u0005\u0005\u0011Q\u0002\t\u0006o\u0005\r\u0011qA\u0005\u0004\u0003\u000b\u0011$AB(qi&|g\u000eE\u0003\u0002\n\u0005-aJ\u0004\u00028\t&\u0011q\r\u0013\u0005\u0006k&\u0001\rAT\u0001\u0004e\u0006<Hc\u0001(\u0002\u0014!)\u0011M\u0003a\u0001q\u0006!2\u000f^1oI\u0006\u0014H-\u00138uKJ\u0004x\u000e\\1u_J$RATA\r\u0003GAq!a\u0007\f\u0001\u0004\ti\"A\u0004qe>\u001cWm]:\u0011\u000b]\nyB\u0014(\n\u0007\u0005\u0005\"GA\u0005Gk:\u001cG/[8oc!1\u0011m\u0003a\u0001\u0003K\u0001R!!\u0003\u0002\f!Dsa\u00037p\u0003S\u00118/\t\u0002\u0002,\u00059Vk]3!i\",\u0007e\u001d;bi&\u001c\u0007%\\3uQ>$\u0007e\u0015;sS:<7i\u001c8uKb$hf\u001d;b]\u0012\f'\u000fZ%oi\u0016\u0014\bo\u001c7bi>\u0014\b%\u001b8ti\u0016\fG\rI8gAQDW\rI5ogR\fgnY3![\u0016$\bn\u001c3\u0002\u0003\u0019,B!!\r\u0002<Q\u0019a*a\r\t\r\u0005d\u0001\u0019AA\u001b!\u00119D*a\u000e\u0011\t\u0005e\u00121\b\u0007\u0001\t\u001d\ti\u0004\u0004b\u0001\u0003\u007f\u0011\u0011!Q\t\u0003Q\"\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAA#!\u0011\t9%!\u0015\u000e\u0005\u0005%#\u0002BA&\u0003\u001b\nA\u0001\\1oO*\u0011\u0011qJ\u0001\u0005U\u00064\u0018-C\u0002U\u0003\u0013\nA\u0002\u001d:pIV\u001cG/\u0011:jif,\"!a\u0016\u0011\u0007]\nI&C\u0002\u0002\\I\u00121!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$2\u0001[A1\u0011%\t\u0019gDA\u0001\u0002\u0004\t9&A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003S\u0002BaYA6Q&\u0019\u0011Q\u000e3\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003g\nI\bE\u00028\u0003kJ1!a\u001e3\u0005\u001d\u0011un\u001c7fC:D\u0001\"a\u0019\u0012\u0003\u0003\u0005\r\u0001[\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002F\u0005}\u0004\"CA2%\u0005\u0005\t\u0019AA,\u0003!A\u0017m\u001d5D_\u0012,GCAA,\u0003!!xn\u0015;sS:<GCAA#\u0003\u0019)\u0017/^1mgR!\u00111OAG\u0011!\t\u0019'FA\u0001\u0002\u0004A\u0017!D*ue&twmQ8oi\u0016DH\u000f\u0005\u00028/M!qCNAK!\u0011\t9*!(\u000e\u0005\u0005e%\u0002BAN\u0003\u001b\n!![8\n\u0007\u001d\u000bI\n\u0006\u0002\u0002\u0012\u0006!q\r\\8c)\u0019\t\t!!*\u0002*\"9\u0011qU\rA\u0002\u0005\u001d\u0011!\u00049biR,'O\\\"ik:\\7\u000f\u0003\u0004\u0002,f\u0001\rAT\u0001\u0006S:\u0004X\u000f\u001e\u0002\u0017\u0013:4\u0018\r\\5e\u000bN\u001c\u0017\r]3Fq\u000e,\u0007\u000f^5p]N\u0019!$!-\u0011\t\u0005%\u00111W\u0005\u0004\u0003kC%\u0001G%mY\u0016<\u0017\r\\!sOVlWM\u001c;Fq\u000e,\u0007\u000f^5p]\u0006\u00191\u000f\u001e:\u0002\u000b%tG-\u001a=\u0002\r%tG-\u001a=!)\u0019\ty,a1\u0002FB\u0019\u0011\u0011\u0019\u000e\u000e\u0003]Aa!a.\u001f\u0001\u0004q\u0005bBA]=\u0001\u0007\u0011q\u000b\u0002\u001e\u0013:4\u0018\r\\5e+:L7m\u001c3f\u000bN\u001c\u0017\r]3Fq\u000e,\u0007\u000f^5p]N\u0019q$!-\u0002\u0017\u0015\u001c8-\u00199f'R\f'\u000f^\u0001\rKN\u001c\u0017\r]3Ti\u0006\u0014H\u000f\t\u000b\t\u0003#\f\u0019.!6\u0002XB\u0019\u0011\u0011Y\u0010\t\r\u0005]V\u00051\u0001O\u0011\u001d\tY-\na\u0001\u0003/Bq!!/&\u0001\u0004\t9&A\u0006sK\u0006$W+R:dCB,GCBAo\u0003S\fi\u000fE\u00048\u0003?\f\u0019/a\u0016\n\u0007\u0005\u0005(G\u0001\u0004UkBdWM\r\t\u0004o\u0005\u0015\u0018bAAte\t!1\t[1s\u0011\u0019\tYO\na\u0001\u001d\u0006\u00191O]2\t\u000f\u0005=h\u00051\u0001\u0002X\u0005Q1\u000f^1si&tG-\u001a=\u0002\u0019Q\u0014X-\u0019;Fg\u000e\f\u0007/Z:\u0015\u00079\u000b)\u0010\u0003\u0004\u00028\u001e\u0002\rA\u0014\u0015\bO1|\u0017\u0011 :tC\t\tY0\u0001\nvg\u0016\u0004\u0003O]8dKN\u001cXi]2ba\u0016\u001c\u0018A\u00049s_\u000e,7o]#tG\u0006\u0004Xm\u001d\u000b\u0004\u001d\n\u0005\u0001BBA\\Q\u0001\u0007a*\u0001\bqe>\u001cWm]:V]&\u001cw\u000eZ3\u0015\u00079\u00139\u0001\u0003\u0004\u00028&\u0002\rAT\u0001\be\u0016\u0004H.Y2f)\u0015q%Q\u0002B\b\u0011\u0019\t9L\u000ba\u0001\u001d\"9!\u0011\u0003\u0016A\u0002\u0005]\u0013!\u00024jeN$\u0018\u0001\u0003:fa2\f7-Z+\u0015\u000b9\u00139B!\u0007\t\r\u0005]6\u00061\u0001O\u0011\u001d\u0011Yb\u000ba\u0001\u0003/\n\u0011BY1dWNd\u0017m\u001d5\u0015\u000f9\u0013yB!\t\u0003$!9\u00111\u0004\u0017A\u0002\u0005u\u0001\"B1-\u0001\u0004\u0011\u0007BB%-\u0001\u0004\t9\u0001F\u0003^\u0005O\u0011I\u0003C\u0003b[\u0001\u0007!\r\u0003\u0004J[\u0001\u0007\u0011qA\u0001\u0006CB\u0004H.\u001f\u000b\u00043\n=\u0002\"B%/\u0001\u0004YE\u0003\u0002B\u001a\u0005o\u0001RaNA\u0002\u0005k\u0001BAPA\u0006\u001d\"A!\u0011H\u0018\u0002\u0002\u0003\u0007\u0011,A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"Aa\u0010\u0011\t\u0005\u001d#\u0011I\u0005\u0005\u0005\u0007\nIE\u0001\u0004PE*,7\r\u001e")
public class StringContext
implements Product,
Serializable {
    private volatile StringContext$s$ s$module;
    private final scala.collection.immutable.Seq<String> parts;

    public static Option<scala.collection.immutable.Seq<String>> unapplySeq(StringContext x$0) {
        return StringContext$.MODULE$.unapplySeq(x$0);
    }

    public static StringContext apply(scala.collection.immutable.Seq<String> parts) {
        return new StringContext(parts);
    }

    public static String processEscapes(String str) {
        return StringContext$.MODULE$.processEscapes(str);
    }

    public static String treatEscapes(String str) {
        return StringContext$.MODULE$.processEscapes(str);
    }

    public static Option<scala.collection.immutable.Seq<String>> glob(scala.collection.immutable.Seq<String> patternChunks, String input) {
        return StringContext$.MODULE$.glob(patternChunks, input);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public StringContext$s$ s() {
        if (this.s$module == null) {
            this.s$lzycompute$1();
        }
        return this.s$module;
    }

    public scala.collection.immutable.Seq<String> parts() {
        return this.parts;
    }

    public void checkLengths(Seq<Object> args2) {
        StringContext$.MODULE$.checkLengths(args2, this.parts());
    }

    public String standardInterpolator(Function1<String, String> process, scala.collection.immutable.Seq<Object> args2) {
        scala.collection.immutable.Seq<String> standardInterpolator_parts = this.parts();
        StringContext$.MODULE$.checkLengths(args2, standardInterpolator_parts);
        Iterator standardInterpolator_pi = standardInterpolator_parts.iterator();
        Iterator standardInterpolator_ai = args2.iterator();
        StringBuilder standardInterpolator_bldr = new StringBuilder(process.apply((String)standardInterpolator_pi.next()));
        while (standardInterpolator_ai.hasNext()) {
            standardInterpolator_bldr.append(standardInterpolator_ai.next());
            standardInterpolator_bldr.append(process.apply((String)standardInterpolator_pi.next()));
        }
        return standardInterpolator_bldr.toString();
    }

    @Override
    public String productPrefix() {
        return "StringContext";
    }

    @Override
    public int productArity() {
        return 1;
    }

    @Override
    public Object productElement(int x$1) {
        switch (x$1) {
            case 0: {
                return this.parts();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return new AbstractIterator<T>(this){
            private int c;
            private final int cmax;
            private final Product x$2;

            public boolean hasNext() {
                return this.c < this.cmax;
            }

            public T next() {
                Object result = this.x$2.productElement(this.c);
                ++this.c;
                return (T)result;
            }
            {
                this.x$2 = x$2;
                this.c = 0;
                this.cmax = x$2.productArity();
            }
        };
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof StringContext;
    }

    @Override
    public String productElementName(int x$1) {
        switch (x$1) {
            case 0: {
                return "parts";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return MurmurHash3$.MODULE$.productHash(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    @Override
    public boolean equals(Object x$1) {
        block3: {
            block2: {
                if (this == x$1) break block2;
                if (!(x$1 instanceof StringContext)) break block3;
                StringContext stringContext = (StringContext)x$1;
                scala.collection.immutable.Seq<String> seq = this.parts();
                scala.collection.immutable.Seq<String> seq2 = stringContext.parts();
                if ((seq != null ? !seq.equals(seq2) : seq2 != null) || !stringContext.canEqual(this)) break block3;
            }
            return true;
        }
        return false;
    }

    private final void s$lzycompute$1() {
        synchronized (this) {
            if (this.s$module == null) {
                this.s$module = new StringContext$s$(this);
            }
            return;
        }
    }

    public StringContext(scala.collection.immutable.Seq<String> parts) {
        this.parts = parts;
    }

    public static class InvalidEscapeException
    extends IllegalArgumentException {
        private final int index;

        public int index() {
            return this.index;
        }

        public InvalidEscapeException(String str, int index) {
            String string;
            this.index = index;
            StringBuilder stringBuilder = new StringBuilder(51).append("invalid escape ");
            Predef$.MODULE$.require(index >= 0 && index < str.length());
            String ok = "[\\b, \\t, \\n, \\f, \\r, \\\\, \\\", \\', \\uxxxx]";
            if (index == str.length() - 1) {
                string = "at terminal";
            } else {
                int apply$extension_i = index + 1;
                string = new StringBuilder(18).append("'\\").append(str.charAt(apply$extension_i)).append("' not one of ").append(ok).append(" at").toString();
            }
            super(stringBuilder.append((Object)string).append(" index ").append(index).append(" in \"").append(str).append("\". Use \\\\ for literal \\.").toString());
        }
    }

    public static class InvalidUnicodeEscapeException
    extends IllegalArgumentException {
        private final int escapeStart;
        private final int index;

        public int escapeStart() {
            return this.escapeStart;
        }

        public int index() {
            return this.index;
        }

        public InvalidUnicodeEscapeException(String str, int escapeStart, int index) {
            this.escapeStart = escapeStart;
            this.index = index;
            super(new StringBuilder(36).append("invalid unicode escape at index ").append(index).append(" of ").append(str).toString());
        }
    }
}

