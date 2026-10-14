/*
 * Decompiled with CFR 0.152.
 */
package akka.japi;

import akka.japi.Option$;
import java.io.Serializable;
import java.util.Collections;
import java.util.Spliterator;
import java.util.function.Consumer;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tmd!\u0002\u001e<\u0003C\u0001\u0005\"B.\u0001\t\u0003a\u0006\"B0\u0001\r\u0003\u0001\u0007\"B1\u0001\r\u0003\u0011\u0007\"\u00026\u0001\r\u0003Y\u0007\"B8\u0001\t\u0003Y\u0007\"\u00029\u0001\r\u0003\t\b\"\u0002;\u0001\t\u0003)xa\u0002B=w!\u0005\u0011q\u0001\u0004\u0006umB\tA \u0005\u00077&!\t!!\u0002\t\u000f\u0005%\u0011\u0002\"\u0001\u0002\f!9\u0011\u0011D\u0005\u0005\u0002\u0005m\u0001bBA\u0013\u0013\u0011\u0005\u0011q\u0005\u0005\b\u0003gIA\u0011AA\u001b\r\u0019\t9%\u0003\"\u0002J!Q\u0011qC\b\u0003\u0016\u0004%\t!!\u001d\t\u0015\u0005MtB!E!\u0002\u0013\ty\u0005\u0003\u0004\\\u001f\u0011\u0005\u0011Q\u000f\u0005\u0007?>!\t!!\u001d\t\r\u0005|A\u0011AA?\u0011\u0015Qw\u0002\"\u0001l\u0011\u0019\u0001x\u0002\"\u0001\u0002\n\"I\u0011qR\b\u0002\u0002\u0013\u0005\u0011\u0011\u0013\u0005\n\u0003;{\u0011\u0013!C\u0001\u0003?C\u0011\"!/\u0010\u0003\u0003%\t%a/\t\u0013\u0005\rw\"!A\u0005\u0002\u0005\u0015\u0007\"CAg\u001f\u0005\u0005I\u0011AAh\u0011%\t)nDA\u0001\n\u0003\n9\u000eC\u0005\u0002d>\t\t\u0011\"\u0001\u0002f\"I\u0011\u0011^\b\u0002\u0002\u0013\u0005\u00131\u001e\u0005\n\u0003_|\u0011\u0011!C!\u0003cD\u0011\"a=\u0010\u0003\u0003%\t%!>\t\u0013\u0005]x\"!A\u0005B\u0005ex!CA\u007f\u0013\u0005\u0005\t\u0012AA\u0000\r%\t9%CA\u0001\u0012\u0003\u0011\t\u0001\u0003\u0004\\G\u0011\u0005!Q\u0002\u0005\n\u0003g\u001c\u0013\u0011!C#\u0003kD\u0011Ba\u0004$\u0003\u0003%\tI!\u0005\t\u0013\tu1%!A\u0005\u0002\n}\u0001\"\u0003B\u0018G\u0005\u0005I\u0011\u0002B\u0019\u000f\u001d\u0011\u0019$\u0003EE\u0005k1a!`\u0005\t\n\ne\u0003BB.+\t\u0003\u0011i\u0006\u0003\u0004`U\u0011\u0005!q\f\u0005\u0007C*\"\tA!\u0019\t\u000b)TC\u0011A6\t\rATC\u0011\u0001B6\u0011%\tILKA\u0001\n\u0003\nY\fC\u0005\u0002D*\n\t\u0011\"\u0001\u0002F\"I\u0011Q\u001a\u0016\u0002\u0002\u0013\u0005!\u0011\u000f\u0005\n\u0003+T\u0013\u0011!C!\u0003/D\u0011\"a9+\u0003\u0003%\tA!\u001e\t\u0013\u0005=(&!A\u0005B\u0005E\b\"CAzU\u0005\u0005I\u0011IA{\u0011%\u0011yCKA\u0001\n\u0013\u0011\t\u0004C\u0004\u00038%!\u0019A!\u000f\t\u000f\t%\u0013\u0002b\u0001\u0003L\t1q\n\u001d;j_:T!\u0001P\u001f\u0002\t)\f\u0007/\u001b\u0006\u0002}\u0005!\u0011m[6b\u0007\u0001)\"!Q(\u0014\u0007\u0001\u0011%\n\u0005\u0002D\u00116\tAI\u0003\u0002F\r\u0006!A.\u00198h\u0015\u00059\u0015\u0001\u00026bm\u0006L!!\u0013#\u0003\r=\u0013'.Z2u!\r\u00195*T\u0005\u0003\u0019\u0012\u0013\u0001\"\u0013;fe\u0006\u0014G.\u001a\t\u0003\u001d>c\u0001\u0001B\u0003Q\u0001\t\u0007\u0011KA\u0001B#\t\u0011\u0006\f\u0005\u0002T-6\tAKC\u0001V\u0003\u0015\u00198-\u00197b\u0013\t9FKA\u0004O_RD\u0017N\\4\u0011\u0005MK\u0016B\u0001.U\u0005\r\te._\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003u\u00032A\u0018\u0001N\u001b\u0005Y\u0014aA4fiV\tQ*A\u0005hKR|%/\u00127tKV\u00111-\u001a\u000b\u0003I\"\u0004\"AT3\u0005\u000b\u0019\u001c!\u0019A4\u0003\u0003\t\u000b\"!\u0014-\t\u000b%\u001c\u0001\u0019\u00013\u0002\u0019\u0011,g-Y;miZ\u000bG.^3\u0002\u000f%\u001cX)\u001c9usV\tA\u000e\u0005\u0002T[&\u0011a\u000e\u0016\u0002\b\u0005>|G.Z1o\u0003%I7\u000fR3gS:,G-A\u0004bgN\u001b\u0017\r\\1\u0016\u0003I\u00042aU:N\u0013\tQD+\u0001\u0005ji\u0016\u0014\u0018\r^8s)\u00051\bcA<{\u001b6\t\u0001P\u0003\u0002z\r\u0006!Q\u000f^5m\u0013\tY\bP\u0001\u0005Ji\u0016\u0014\u0018\r^8sS\r\u0001!f\u0004\u0002\u0005\u001d>tWm\u0005\u0002\n\u007fB\u00191+!\u0001\n\u0007\u0005\rAK\u0001\u0004B]f\u0014VM\u001a\u000b\u0003\u0003\u000f\u0001\"AX\u0005\u0002\tM|W.Z\u000b\u0005\u0003\u001b\t\u0019\u0002\u0006\u0003\u0002\u0010\u0005U\u0001\u0003\u00020\u0001\u0003#\u00012ATA\n\t\u0015\u00016B1\u0001R\u0011\u001d\t9b\u0003a\u0001\u0003#\t\u0011A^\u0001\u0005]>tW-\u0006\u0003\u0002\u001e\u0005\rRCAA\u0010!\u0011q\u0006!!\t\u0011\u00079\u000b\u0019\u0003B\u0003Q\u0019\t\u0007\u0011+\u0001\u0004paRLwN\\\u000b\u0005\u0003S\ty\u0003\u0006\u0003\u0002,\u0005E\u0002\u0003\u00020\u0001\u0003[\u00012ATA\u0018\t\u0015\u0001VB1\u0001R\u0011\u001d\t9\"\u0004a\u0001\u0003[\tqB\u001a:p[N\u001b\u0017\r\\1PaRLwN\\\u000b\u0005\u0003o\ti\u0004\u0006\u0003\u0002:\u0005\u0005\u0003\u0003\u00020\u0001\u0003w\u00012ATA\u001f\t\u0019\tyD\u0004b\u0001#\n\tA\u000bC\u0004\u0002D9\u0001\r!!\u0012\u0002\u0017M\u001c\u0017\r\\1PaRLwN\u001c\t\u0005'N\fYD\u0001\u0003T_6,W\u0003BA&\u0003#\u001araDA'\u0003'\nI\u0006\u0005\u0003_\u0001\u0005=\u0003c\u0001(\u0002R\u0011)\u0001k\u0004b\u0001#B\u00191+!\u0016\n\u0007\u0005]CKA\u0004Qe>$Wo\u0019;\u0011\t\u0005m\u00131\u000e\b\u0005\u0003;\n9G\u0004\u0003\u0002`\u0005\u0015TBAA1\u0015\r\t\u0019gP\u0001\u0007yI|w\u000e\u001e \n\u0003UK1!!\u001bU\u0003\u001d\u0001\u0018mY6bO\u0016LA!!\u001c\u0002p\ta1+\u001a:jC2L'0\u00192mK*\u0019\u0011\u0011\u000e+\u0016\u0005\u0005=\u0013A\u0001<!)\u0011\t9(a\u001f\u0011\u000b\u0005et\"a\u0014\u000e\u0003%Aq!a\u0006\u0013\u0001\u0004\ty%\u0006\u0003\u0002\u0000\u0005\rE\u0003BAA\u0003\u000f\u00032ATAB\t\u00191GC1\u0001\u0002\u0006F\u0019\u0011q\n-\t\r%$\u0002\u0019AAA+\t\tY\tE\u0003T\u0003\u001b\u000by%C\u0002\u0002HQ\u000bAaY8qsV!\u00111SAM)\u0011\t)*a'\u0011\u000b\u0005et\"a&\u0011\u00079\u000bI\nB\u0003Q/\t\u0007\u0011\u000bC\u0005\u0002\u0018]\u0001\n\u00111\u0001\u0002\u0018\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT\u0003BAQ\u0003o+\"!a)+\t\u0005=\u0013QU\u0016\u0003\u0003O\u0003B!!+\u000246\u0011\u00111\u0016\u0006\u0005\u0003[\u000by+A\u0005v]\u000eDWmY6fI*\u0019\u0011\u0011\u0017+\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u00026\u0006-&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0012)\u0001\u000b\u0007b\u0001#\u0006i\u0001O]8ek\u000e$\bK]3gSb,\"!!0\u0011\u0007\r\u000by,C\u0002\u0002B\u0012\u0013aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLXCAAd!\r\u0019\u0016\u0011Z\u0005\u0004\u0003\u0017$&aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$Hc\u0001-\u0002R\"I\u00111[\u000e\u0002\u0002\u0003\u0007\u0011qY\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0005e\u0007#BAn\u0003CDVBAAo\u0015\r\ty\u000eV\u0001\u000bG>dG.Z2uS>t\u0017bA>\u0002^\u0006A1-\u00198FcV\fG\u000eF\u0002m\u0003OD\u0001\"a5\u001e\u0003\u0003\u0005\r\u0001W\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002>\u00065\b\"CAj=\u0005\u0005\t\u0019AAd\u0003!A\u0017m\u001d5D_\u0012,GCAAd\u0003!!xn\u0015;sS:<GCAA_\u0003\u0019)\u0017/^1mgR\u0019A.a?\t\u0011\u0005M\u0017%!AA\u0002a\u000bAaU8nKB\u0019\u0011\u0011P\u0012\u0014\t\rz(1\u0001\t\u0005\u0005\u000b\u0011Y!\u0004\u0002\u0003\b)\u0019!\u0011\u0002$\u0002\u0005%|\u0017\u0002BA7\u0005\u000f!\"!a@\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\t\tM!\u0011\u0004\u000b\u0005\u0005+\u0011Y\u0002E\u0003\u0002z=\u00119\u0002E\u0002O\u00053!Q\u0001\u0015\u0014C\u0002ECq!a\u0006'\u0001\u0004\u00119\"A\u0004v]\u0006\u0004\b\u000f\\=\u0016\t\t\u0005\"q\u0005\u000b\u0005\u0005G\u0011I\u0003\u0005\u0003Tg\n\u0015\u0002c\u0001(\u0003(\u0011)\u0001k\nb\u0001#\"I!1F\u0014\u0002\u0002\u0003\u0007!QF\u0001\u0004q\u0012\u0002\u0004#BA=\u001f\t\u0015\u0012\u0001D<sSR,'+\u001a9mC\u000e,G#\u0001\"\u0002\t9{g.\u001a\t\u0004\u0003sR\u0013\u0001\u00056bm\u0006\u00144kY1mC>\u0003H/[8o+\u0011\u0011YD!\u0011\u0015\t\tu\"1\t\t\u0005'N\u0014y\u0004E\u0002O\u0005\u0003\"Q\u0001\u0015\u001dC\u0002ECqA!\u00129\u0001\u0004\u00119%A\u0001p!\u0011q\u0006Aa\u0010\u0002!M\u001c\u0017\r\\13\u0015\u00064\u0018m\u00149uS>tW\u0003\u0002B'\u0005'\"BAa\u0014\u0003VA!a\f\u0001B)!\rq%1\u000b\u0003\u0006!f\u0012\r!\u0015\u0005\b\u0005\u000bJ\u0004\u0019\u0001B,!\u0011\u00196O!\u0015\u0014\u000f)\u0012Y&a\u0015\u0002ZA\u0019a\f\u0001*\u0015\u0005\tUR#\u0001*\u0016\t\t\r$q\r\u000b\u0005\u0005K\u0012I\u0007E\u0002O\u0005O\"QAZ\u0017C\u0002ECa![\u0017A\u0002\t\u0015TC\u0001B7\u001d\u0011\tiFa\u001c\n\u0007\tMB\u000bF\u0002Y\u0005gB\u0011\"a53\u0003\u0003\u0005\r!a2\u0015\u00071\u00149\b\u0003\u0005\u0002TR\n\t\u00111\u0001Y\u0003\u0019y\u0005\u000f^5p]\u0002")
public abstract class Option<A>
implements Iterable<A> {
    public static <A> Option<A> scala2JavaOption(scala.Option<A> o) {
        return Option$.MODULE$.scala2JavaOption(o);
    }

    public static <A> scala.Option<A> java2ScalaOption(Option<A> o) {
        return Option$.MODULE$.java2ScalaOption(o);
    }

    public static <T> Option<T> fromScalaOption(scala.Option<T> scalaOption) {
        return Option$.MODULE$.fromScalaOption(scalaOption);
    }

    public static <A> Option<A> option(A v) {
        return Option$.MODULE$.option(v);
    }

    public static <A> Option<A> none() {
        return Option$.MODULE$.none();
    }

    public static <A> Option<A> some(A v) {
        return Option$.MODULE$.some(v);
    }

    @Override
    public void forEach(Consumer<? super A> x$1) {
        Iterable.super.forEach(x$1);
    }

    @Override
    public Spliterator<A> spliterator() {
        return Iterable.super.spliterator();
    }

    public abstract A get();

    public abstract <B> B getOrElse(B var1);

    public abstract boolean isEmpty();

    public boolean isDefined() {
        return !this.isEmpty();
    }

    public abstract scala.Option<A> asScala();

    @Override
    public java.util.Iterator<A> iterator() {
        if (this.isEmpty()) {
            return Collections.emptyList().iterator();
        }
        return Collections.singletonList(this.get()).iterator();
    }

    public static final class Some<A>
    extends Option<A>
    implements Product,
    Serializable {
        private final A v;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public A v() {
            return this.v;
        }

        @Override
        public A get() {
            return this.v();
        }

        @Override
        public <B> B getOrElse(B defaultValue) {
            return (B)this.v();
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public scala.Some<A> asScala() {
            return new scala.Some<A>(this.v());
        }

        public <A> Some<A> copy(A v) {
            return new Some<A>(v);
        }

        public <A> A copy$default$1() {
            return this.v();
        }

        @Override
        public String productPrefix() {
            return "Some";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.v();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof Some;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "v";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof Some)) return false;
            boolean bl = true;
            if (!bl) return false;
            Some some = (Some)x$1;
            if (!BoxesRunTime.equals(this.v(), some.v())) return false;
            return true;
        }

        public Some(A v) {
            this.v = v;
            Product.$init$(this);
        }
    }
}

