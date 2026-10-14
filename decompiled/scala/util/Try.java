/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import java.io.Serializable;
import scala.$less$colon$less;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.util.Either;
import scala.util.Failure;
import scala.util.Success;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\t-b!B\u0011#\u0003C9\u0003\"B\u001c\u0001\t\u0003A\u0004\"\u0002$\u0001\r\u00039\u0005\"B&\u0001\r\u00039\u0005\"\u0002'\u0001\r\u0003i\u0005\"\u0002-\u0001\r\u0003I\u0006\"\u00021\u0001\r\u0003\t\u0007\"\u00022\u0001\r\u0003\u0019\u0007\"B8\u0001\r\u0003\u0001\b\"B<\u0001\r\u0003A\bBB@\u0001\r\u0003\t\t\u0001C\u0004\u0002\u0016\u00011\t!a\u0006\t\u000f\u0005}\u0001\u0001\"\u0002\u0002\"\u00191\u0011q\u0005\u0001\u0003\u0003SA!\"a\u0007\u000e\u0005\u0003\u0005\u000b\u0011BA\u000f\u0011\u00199T\u0002\"\u0001\u0002,!1q/\u0004C\u0001\u0003_Aaa\\\u0007\u0005\u0002\u0005u\u0002B\u00022\u000e\t\u0003\tY\u0005C\u0004\u0002 5!\t!a\u0016\t\u000f\u0005\u001d\u0004A\"\u0001\u0002j!9\u0011Q\u0010\u0001\u0007\u0002\u0005}\u0004bBAG\u0001\u0019\u0005\u0011q\u0012\u0005\b\u0003/\u0003a\u0011AAM\u0011\u001d\ti\u000b\u0001D\u0001\u0003_Cq!a-\u0001\r\u0003\t)\fC\u0004\u0002J\u00021\t!a3\t\u000f\u0005M\u0007A\"\u0001\u0002V\u001e9\u00111\u001f\u0012\t\u0002\u0005UhAB\u0011#\u0011\u0003\t9\u0010\u0003\u00048;\u0011\u0005!q\u0001\u0005\b\u0005\u0013iB\u0011\u0001B\u0006\u0011%\u0011Y\"HA\u0001\n\u0013\u0011iBA\u0002UefT!a\t\u0013\u0002\tU$\u0018\u000e\u001c\u0006\u0002K\u0005)1oY1mC\u000e\u0001QC\u0001\u0015>'\u0011\u0001\u0011&\f\u0019\u0011\u0005)ZS\"\u0001\u0013\n\u00051\"#AB!osJ+g\r\u0005\u0002+]%\u0011q\u0006\n\u0002\b!J|G-^2u!\t\tDG\u0004\u0002+e%\u00111\u0007J\u0001\ba\u0006\u001c7.Y4f\u0013\t)dG\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u00024I\u00051A(\u001b8jiz\"\u0012!\u000f\t\u0004u\u0001YT\"\u0001\u0012\u0011\u0005qjD\u0002\u0001\u0003\u0007}\u0001!)\u0019A \u0003\u0003Q\u000b\"\u0001Q\"\u0011\u0005)\n\u0015B\u0001\"%\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u000b#\n\u0005\u0015##aA!os\u0006I\u0011n\u001d$bS2,(/Z\u000b\u0002\u0011B\u0011!&S\u0005\u0003\u0015\u0012\u0012qAQ8pY\u0016\fg.A\u0005jgN+8mY3tg\u0006Iq-\u001a;Pe\u0016c7/Z\u000b\u0003\u001dB#\"aT*\u0011\u0005q\u0002F!B)\u0005\u0005\u0004\u0011&!A+\u0012\u0005m\u001a\u0005B\u0002+\u0005\t\u0003\u0007Q+A\u0004eK\u001a\fW\u000f\u001c;\u0011\u0007)2v*\u0003\u0002XI\tAAHY=oC6,g(\u0001\u0004pe\u0016c7/Z\u000b\u00035v#\"a\u00170\u0011\u0007i\u0002A\f\u0005\u0002=;\u0012)\u0011+\u0002b\u0001%\"1A+\u0002CA\u0002}\u00032A\u000b,\\\u0003\r9W\r^\u000b\u0002w\u00059am\u001c:fC\u000eDWC\u00013o)\t)\u0007\u000e\u0005\u0002+M&\u0011q\r\n\u0002\u0005+:LG\u000fC\u0003j\u000f\u0001\u0007!.A\u0001g!\u0011Q3nO7\n\u00051$#!\u0003$v]\u000e$\u0018n\u001c82!\tad\u000eB\u0003R\u000f\t\u0007q(A\u0004gY\u0006$X*\u00199\u0016\u0005E$HC\u0001:v!\rQ\u0004a\u001d\t\u0003yQ$Q!\u0015\u0005C\u0002}BQ!\u001b\u0005A\u0002Y\u0004BAK6<e\u0006\u0019Q.\u00199\u0016\u0005edHC\u0001>~!\rQ\u0004a\u001f\t\u0003yq$Q!U\u0005C\u0002}BQ![\u0005A\u0002y\u0004BAK6<w\u000691m\u001c7mK\u000e$X\u0003BA\u0002\u0003\u0013!B!!\u0002\u0002\fA!!\bAA\u0004!\ra\u0014\u0011\u0002\u0003\u0006#*\u0011\ra\u0010\u0005\b\u0003\u001bQ\u0001\u0019AA\b\u0003\t\u0001h\r\u0005\u0004+\u0003#Y\u0014qA\u0005\u0004\u0003'!#a\u0004)beRL\u0017\r\u001c$v]\u000e$\u0018n\u001c8\u0002\r\u0019LG\u000e^3s)\rI\u0014\u0011\u0004\u0005\b\u00037Y\u0001\u0019AA\u000f\u0003\u0005\u0001\b\u0003\u0002\u0016lw!\u000b!b^5uQ\u001aKG\u000e^3s)\u0011\t\u0019#!\u0018\u0011\u0007\u0005\u0015R\"D\u0001\u0001\u0005)9\u0016\u000e\u001e5GS2$XM]\n\u0003\u001b%\"B!a\t\u0002.!9\u00111D\bA\u0002\u0005uQ\u0003BA\u0019\u0003o!B!a\r\u0002:A!!\bAA\u001b!\ra\u0014q\u0007\u0003\u0006#B\u0011\ra\u0010\u0005\u0007SB\u0001\r!a\u000f\u0011\u000b)Z7(!\u000e\u0016\t\u0005}\u0012Q\t\u000b\u0005\u0003\u0003\n9\u0005\u0005\u0003;\u0001\u0005\r\u0003c\u0001\u001f\u0002F\u0011)\u0011+\u0005b\u0001\u007f!1\u0011.\u0005a\u0001\u0003\u0013\u0002RAK6<\u0003\u0003*B!!\u0014\u0002VQ\u0019Q-a\u0014\t\r%\u0014\u0002\u0019AA)!\u0015Q3nOA*!\ra\u0014Q\u000b\u0003\u0006#J\u0011\ra\u0010\u000b\u0005\u0003G\tI\u0006C\u0004\u0002\\M\u0001\r!!\b\u0002\u0003EDq!a\u0007\r\u0001\u0004\ti\u0002K\u0002\r\u0003C\u00022AKA2\u0013\r\t)\u0007\n\u0002\u0007S:d\u0017N\\3\u0002\u0017I,7m\u001c<fe^KG\u000f[\u000b\u0005\u0003W\n\t\b\u0006\u0003\u0002n\u0005M\u0004\u0003\u0002\u001e\u0001\u0003_\u00022\u0001PA9\t\u0015\tFC1\u0001S\u0011\u001d\ti\u0001\u0006a\u0001\u0003k\u0002rAKA\t\u0003o\ni\u0007E\u00022\u0003sJ1!a\u001f7\u0005%!\u0006N]8xC\ndW-A\u0004sK\u000e|g/\u001a:\u0016\t\u0005\u0005\u0015q\u0011\u000b\u0005\u0003\u0007\u000bI\t\u0005\u0003;\u0001\u0005\u0015\u0005c\u0001\u001f\u0002\b\u0012)\u0011+\u0006b\u0001%\"9\u0011QB\u000bA\u0002\u0005-\u0005c\u0002\u0016\u0002\u0012\u0005]\u0014QQ\u0001\ti>|\u0005\u000f^5p]V\u0011\u0011\u0011\u0013\t\u0005U\u0005M5(C\u0002\u0002\u0016\u0012\u0012aa\u00149uS>t\u0017a\u00024mCR$XM\\\u000b\u0005\u00037\u000b\t\u000b\u0006\u0003\u0002\u001e\u0006\r\u0006\u0003\u0002\u001e\u0001\u0003?\u00032\u0001PAQ\t\u0015\tvC1\u0001@\u0011\u001d\t)k\u0006a\u0002\u0003O\u000b!!\u001a<\u0011\r)\nIkOAO\u0013\r\tY\u000b\n\u0002\u0011I1,7o\u001d\u0013d_2|g\u000e\n7fgN\faAZ1jY\u0016$WCAAY!\u0011Q\u0004!a\u001e\u0002\u0013Q\u0014\u0018M\\:g_JlW\u0003BA\\\u0003{#b!!/\u0002@\u0006\u0015\u0007\u0003\u0002\u001e\u0001\u0003w\u00032\u0001PA_\t\u0015\t\u0016D1\u0001@\u0011\u001d\t\t-\u0007a\u0001\u0003\u0007\f\u0011a\u001d\t\u0006U-\\\u0014\u0011\u0018\u0005\u0007Sf\u0001\r!a2\u0011\r)Z\u0017qOA]\u0003!!x.R5uQ\u0016\u0014XCAAg!\u0019Q\u0014qZA<w%\u0019\u0011\u0011\u001b\u0012\u0003\r\u0015KG\u000f[3s\u0003\u00111w\u000e\u001c3\u0016\t\u0005]\u00171\u001c\u000b\u0007\u00033\fi.a9\u0011\u0007q\nY\u000eB\u0003R7\t\u0007q\bC\u0004\u0002`n\u0001\r!!9\u0002\u0005\u0019\f\u0007C\u0002\u0016l\u0003o\nI\u000eC\u0004\u0002fn\u0001\r!a:\u0002\u0005\u0019\u0014\u0007#\u0002\u0016lw\u0005e\u0017&\u0002\u0001\u0002l\u0006=\u0018bAAwE\t9a)Y5mkJ,\u0017bAAyE\t91+^2dKN\u001c\u0018a\u0001+ssB\u0011!(H\n\u0005;%\nI\u0010\u0005\u0003\u0002|\n\u0015QBAA\u007f\u0015\u0011\tyP!\u0001\u0002\u0005%|'B\u0001B\u0002\u0003\u0011Q\u0017M^1\n\u0007U\ni\u0010\u0006\u0002\u0002v\u0006)\u0011\r\u001d9msV!!Q\u0002B\n)\u0011\u0011yA!\u0006\u0011\ti\u0002!\u0011\u0003\t\u0004y\tMA!\u0002  \u0005\u0004y\u0004\u0002\u0003B\f?\u0011\u0005\rA!\u0007\u0002\u0003I\u0004BA\u000b,\u0003\u0012\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011!q\u0004\t\u0005\u0005C\u00119#\u0004\u0002\u0003$)!!Q\u0005B\u0001\u0003\u0011a\u0017M\\4\n\t\t%\"1\u0005\u0002\u0007\u001f\nTWm\u0019;")
public abstract class Try<T>
implements Product,
Serializable {
    public static <T> Try<T> apply(Function0<T> r) {
        try {
            return new Success<T>(r.apply());
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            if (throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty()) {
                Throwable apply_e = option.get();
                return new Failure(apply_e);
            }
            throw throwable;
        }
    }

    @Override
    public Iterator<Object> productIterator() {
        return Product.productIterator$(this);
    }

    @Override
    public String productPrefix() {
        return Product.productPrefix$(this);
    }

    @Override
    public String productElementName(int n) {
        return Product.productElementName$(this, n);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public abstract boolean isFailure();

    public abstract boolean isSuccess();

    public abstract <U> U getOrElse(Function0<U> var1);

    public abstract <U> Try<U> orElse(Function0<Try<U>> var1);

    public abstract T get();

    public abstract <U> void foreach(Function1<T, U> var1);

    public abstract <U> Try<U> flatMap(Function1<T, Try<U>> var1);

    public abstract <U> Try<U> map(Function1<T, U> var1);

    public abstract <U> Try<U> collect(PartialFunction<T, U> var1);

    public abstract Try<T> filter(Function1<T, Object> var1);

    public final WithFilter withFilter(Function1<T, Object> p) {
        return new WithFilter(this, p);
    }

    public abstract <U> Try<U> recoverWith(PartialFunction<Throwable, Try<U>> var1);

    public abstract <U> Try<U> recover(PartialFunction<Throwable, U> var1);

    public abstract Option<T> toOption();

    public abstract <U> Try<U> flatten($less$colon$less<T, Try<U>> var1);

    public abstract Try<Throwable> failed();

    public abstract <U> Try<U> transform(Function1<T, Try<U>> var1, Function1<Throwable, Try<U>> var2);

    public abstract Either<Throwable, T> toEither();

    public abstract <U> U fold(Function1<Throwable, U> var1, Function1<T, U> var2);

    public final class WithFilter {
        private final Function1<T, Object> p;
        private final /* synthetic */ Try $outer;

        public <U> Try<U> map(Function1<T, U> f) {
            return this.$outer.filter(this.p).map(f);
        }

        public <U> Try<U> flatMap(Function1<T, Try<U>> f) {
            return this.$outer.filter(this.p).flatMap(f);
        }

        public <U> void foreach(Function1<T, U> f) {
            this.$outer.filter(this.p).foreach(f);
        }

        public WithFilter withFilter(Function1<T, Object> q) {
            return new WithFilter(this.$outer, (Function1<Object, Object> & Serializable)x -> BoxesRunTime.boxToBoolean(WithFilter.$anonfun$withFilter$1(this, q, x)));
        }

        public static final /* synthetic */ boolean $anonfun$withFilter$1(WithFilter $this, Function1 q$1, Object x) {
            return BoxesRunTime.unboxToBoolean($this.p.apply(x)) && BoxesRunTime.unboxToBoolean(q$1.apply(x));
        }

        public WithFilter(Try $outer, Function1<T, Object> p) {
            this.p = p;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }
}

