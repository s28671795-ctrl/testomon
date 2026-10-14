/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import java.io.Serializable;
import scala.$less$colon$less;
import scala.Function0;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Product;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Either;
import scala.util.Failure$;
import scala.util.Left;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.NonFatal$;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\t}d\u0001\u0002\u0015*\u0005:B\u0001b\u0014\u0001\u0003\u0016\u0004%\t\u0001\u0015\u0005\t+\u0002\u0011\t\u0012)A\u0005#\")a\u000b\u0001C\u0001/\")!\f\u0001C!7\")q\f\u0001C!7\")\u0001\r\u0001C!C\")!\r\u0001C!G\")a\u000e\u0001C!_\")a\u000f\u0001C!o\"9\u00111\u0001\u0001\u0005B\u0005\u0015\u0001bBA\r\u0001\u0011\u0005\u00131\u0004\u0005\b\u0003[\u0001A\u0011IA\u0018\u0011\u001d\t\u0019\u0005\u0001C!\u0003\u000bBq!a\u0015\u0001\t\u0003\n)\u0006C\u0004\u0002j\u0001!\t%a\u001b\t\u000f\u0005M\u0004\u0001\"\u0011\u0002v!9\u00111\u0011\u0001\u0005B\u0005\u0015\u0005bBAJ\u0001\u0011\u0005\u0013Q\u0013\u0005\b\u00033\u0003A\u0011IAN\u0011\u001d\t\u0019\u000b\u0001C!\u0003KCq!!,\u0001\t\u0003\ny\u000bC\u0005\u0002D\u0002\t\t\u0011\"\u0001\u0002F\"I\u0011\u0011\u001b\u0001\u0012\u0002\u0013\u0005\u00111\u001b\u0005\n\u0003[\u0004\u0011\u0011!C!\u0003_D\u0011B!\u0001\u0001\u0003\u0003%\tAa\u0001\t\u0013\t-\u0001!!A\u0005\u0002\t5\u0001\"\u0003B\n\u0001\u0005\u0005I\u0011\tB\u000b\u0011%\u0011\u0019\u0003AA\u0001\n\u0003\u0011)\u0003C\u0005\u0003*\u0001\t\t\u0011\"\u0011\u0003,!I!q\u0006\u0001\u0002\u0002\u0013\u0005#\u0011\u0007\u0005\n\u0005g\u0001\u0011\u0011!C!\u0005kA\u0011Ba\u000e\u0001\u0003\u0003%\tE!\u000f\b\u0013\tu\u0012&!A\t\u0002\t}b\u0001\u0003\u0015*\u0003\u0003E\tA!\u0011\t\rY\u0013C\u0011\u0001B*\u0011%\u0011\u0019DIA\u0001\n\u000b\u0012)\u0004C\u0005\u0003V\t\n\t\u0011\"!\u0003X!I!1\r\u0012\u0002\u0002\u0013\u0005%Q\r\u0005\n\u0005k\u0012\u0013\u0011!C\u0005\u0005o\u0012qAR1jYV\u0014XM\u0003\u0002+W\u0005!Q\u000f^5m\u0015\u0005a\u0013!B:dC2\f7\u0001A\u000b\u0003_Y\u001aB\u0001\u0001\u0019A\u0007B\u0019\u0011G\r\u001b\u000e\u0003%J!aM\u0015\u0003\u0007Q\u0013\u0018\u0010\u0005\u00026m1\u0001AAB\u001c\u0001\t\u000b\u0007\u0001HA\u0001U#\tIT\b\u0005\u0002;w5\t1&\u0003\u0002=W\t9aj\u001c;iS:<\u0007C\u0001\u001e?\u0013\ty4FA\u0002B]f\u0004\"AO!\n\u0005\t[#a\u0002)s_\u0012,8\r\u001e\t\u0003\t2s!!\u0012&\u000f\u0005\u0019KU\"A$\u000b\u0005!k\u0013A\u0002\u001fs_>$h(C\u0001-\u0013\tY5&A\u0004qC\u000e\\\u0017mZ3\n\u00055s%\u0001D*fe&\fG.\u001b>bE2,'BA&,\u0003%)\u0007pY3qi&|g.F\u0001R!\t\u00116K\u0004\u0002;\u0015&\u0011AK\u0014\u0002\n)\"\u0014xn^1cY\u0016\f!\"\u001a=dKB$\u0018n\u001c8!\u0003\u0019a\u0014N\\5u}Q\u0011\u0001,\u0017\t\u0004c\u0001!\u0004\"B(\u0004\u0001\u0004\t\u0016!C5t\r\u0006LG.\u001e:f+\u0005a\u0006C\u0001\u001e^\u0013\tq6FA\u0004C_>dW-\u00198\u0002\u0013%\u001c8+^2dKN\u001c\u0018aA4fiV\tA'A\u0005hKR|%/\u00127tKV\u0011AM\u001a\u000b\u0003K&\u0004\"!\u000e4\u0005\u000b\u001d<!\u0019\u00015\u0003\u0003U\u000b\"\u0001N\u001f\t\r)<A\u00111\u0001l\u0003\u001d!WMZ1vYR\u00042A\u000f7f\u0013\ti7F\u0001\u0005=Eft\u0017-\\3?\u0003\u0019y'/\u00127tKV\u0011\u0001o\u001d\u000b\u0003cR\u00042!\r\u001as!\t)4\u000fB\u0003h\u0011\t\u0007\u0001\u000e\u0003\u0004k\u0011\u0011\u0005\r!\u001e\t\u0004u1\f\u0018a\u00024mCRl\u0015\r]\u000b\u0003qn$\"!\u001f?\u0011\u0007E\u0012$\u0010\u0005\u00026w\u0012)q-\u0003b\u0001q!)Q0\u0003a\u0001}\u0006\ta\r\u0005\u0003;\u007fRJ\u0018bAA\u0001W\tIa)\u001e8di&|g.M\u0001\bM2\fG\u000f^3o+\u0011\t9!!\u0004\u0015\t\u0005%\u0011q\u0002\t\u0005cI\nY\u0001E\u00026\u0003\u001b!Qa\u001a\u0006C\u0002aBq!!\u0005\u000b\u0001\b\t\u0019\"\u0001\u0002fmB1!(!\u00065\u0003\u0013I1!a\u0006,\u0005A!C.Z:tI\r|Gn\u001c8%Y\u0016\u001c8/A\u0004g_J,\u0017m\u00195\u0016\t\u0005u\u00111\u0006\u000b\u0005\u0003?\t)\u0003E\u0002;\u0003CI1!a\t,\u0005\u0011)f.\u001b;\t\ru\\\u0001\u0019AA\u0014!\u0015Qt\u0010NA\u0015!\r)\u00141\u0006\u0003\u0006O.\u0011\r\u0001O\u0001\niJ\fgn\u001d4pe6,B!!\r\u00028Q1\u00111GA\u001d\u0003\u007f\u0001B!\r\u001a\u00026A\u0019Q'a\u000e\u0005\u000b\u001dd!\u0019\u0001\u001d\t\u000f\u0005mB\u00021\u0001\u0002>\u0005\t1\u000fE\u0003;\u007fR\n\u0019\u0004\u0003\u0004~\u0019\u0001\u0007\u0011\u0011\t\t\u0006u}\f\u00161G\u0001\u0004[\u0006\u0004X\u0003BA$\u0003\u001b\"B!!\u0013\u0002PA!\u0011GMA&!\r)\u0014Q\n\u0003\u0006O6\u0011\r\u0001\u000f\u0005\u0007{6\u0001\r!!\u0015\u0011\u000bizH'a\u0013\u0002\u000f\r|G\u000e\\3diV!\u0011qKA/)\u0011\tI&a\u0018\u0011\tE\u0012\u00141\f\t\u0004k\u0005uC!B4\u000f\u0005\u0004A\u0004bBA1\u001d\u0001\u0007\u00111M\u0001\u0003a\u001a\u0004bAOA3i\u0005m\u0013bAA4W\ty\u0001+\u0019:uS\u0006dg)\u001e8di&|g.\u0001\u0004gS2$XM\u001d\u000b\u0004a\u00055\u0004bBA8\u001f\u0001\u0007\u0011\u0011O\u0001\u0002aB!!h \u001b]\u0003\u001d\u0011XmY8wKJ,B!a\u001e\u0002~Q!\u0011\u0011PA@!\u0011\t$'a\u001f\u0011\u0007U\ni\bB\u0003h!\t\u0007\u0001\u000eC\u0004\u0002bA\u0001\r!!!\u0011\ri\n)'UA>\u0003-\u0011XmY8wKJ<\u0016\u000e\u001e5\u0016\t\u0005\u001d\u0015Q\u0012\u000b\u0005\u0003\u0013\u000by\t\u0005\u00032e\u0005-\u0005cA\u001b\u0002\u000e\u0012)q-\u0005b\u0001Q\"9\u0011\u0011M\tA\u0002\u0005E\u0005C\u0002\u001e\u0002fE\u000bI)\u0001\u0004gC&dW\rZ\u000b\u0003\u0003/\u00032!\r\u001aR\u0003!!xn\u00149uS>tWCAAO!\u0011Q\u0014q\u0014\u001b\n\u0007\u0005\u00056F\u0001\u0004PaRLwN\\\u0001\ti>,\u0015\u000e\u001e5feV\u0011\u0011q\u0015\t\u0006c\u0005%\u0016\u000bN\u0005\u0004\u0003WK#AB#ji\",'/\u0001\u0003g_2$W\u0003BAY\u0003k#b!a-\u00028\u0006u\u0006cA\u001b\u00026\u0012)q-\u0006b\u0001q!9\u0011\u0011X\u000bA\u0002\u0005m\u0016A\u00014b!\u0015Qt0UAZ\u0011\u001d\ty,\u0006a\u0001\u0003\u0003\f!A\u001a2\u0011\u000bizH'a-\u0002\t\r|\u0007/_\u000b\u0005\u0003\u000f\fi\r\u0006\u0003\u0002J\u0006=\u0007\u0003B\u0019\u0001\u0003\u0017\u00042!NAg\t\u00159dC1\u00019\u0011\u001dye\u0003%AA\u0002E\u000babY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0003\u0002V\u0006-XCAAlU\r\t\u0016\u0011\\\u0016\u0003\u00037\u0004B!!8\u0002h6\u0011\u0011q\u001c\u0006\u0005\u0003C\f\u0019/A\u0005v]\u000eDWmY6fI*\u0019\u0011Q]\u0016\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002j\u0006}'!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0012)qg\u0006b\u0001q\u0005i\u0001O]8ek\u000e$\bK]3gSb,\"!!=\u0011\t\u0005M\u0018Q`\u0007\u0003\u0003kTA!a>\u0002z\u0006!A.\u00198h\u0015\t\tY0\u0001\u0003kCZ\f\u0017\u0002BA\u0000\u0003k\u0014aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLXC\u0001B\u0003!\rQ$qA\u0005\u0004\u0005\u0013Y#aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HcA\u001f\u0003\u0010!I!\u0011\u0003\u000e\u0002\u0002\u0003\u0007!QA\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\t]\u0001#\u0002B\r\u0005?iTB\u0001B\u000e\u0015\r\u0011ibK\u0001\u000bG>dG.Z2uS>t\u0017\u0002\u0002B\u0011\u00057\u0011\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR\u0019ALa\n\t\u0011\tEA$!AA\u0002u\n!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011\u0011\u001fB\u0017\u0011%\u0011\t\"HA\u0001\u0002\u0004\u0011)!\u0001\u0005iCND7i\u001c3f)\t\u0011)!\u0001\u0005u_N#(/\u001b8h)\t\t\t0\u0001\u0004fcV\fGn\u001d\u000b\u00049\nm\u0002\u0002\u0003B\tA\u0005\u0005\t\u0019A\u001f\u0002\u000f\u0019\u000b\u0017\u000e\\;sKB\u0011\u0011GI\n\u0006E\t\r#\u0011\n\t\u0004u\t\u0015\u0013b\u0001B$W\t1\u0011I\\=SK\u001a\u0004BAa\u0013\u0003R5\u0011!Q\n\u0006\u0005\u0005\u001f\nI0\u0001\u0002j_&\u0019QJ!\u0014\u0015\u0005\t}\u0012!B1qa2LX\u0003\u0002B-\u0005?\"BAa\u0017\u0003bA!\u0011\u0007\u0001B/!\r)$q\f\u0003\u0006o\u0015\u0012\r\u0001\u000f\u0005\u0006\u001f\u0016\u0002\r!U\u0001\bk:\f\u0007\u000f\u001d7z+\u0011\u00119Ga\u001d\u0015\t\t%$1\u000e\t\u0005u\u0005}\u0015\u000bC\u0005\u0003n\u0019\n\t\u00111\u0001\u0003p\u0005\u0019\u0001\u0010\n\u0019\u0011\tE\u0002!\u0011\u000f\t\u0004k\tMD!B\u001c'\u0005\u0004A\u0014\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001B=!\u0011\t\u0019Pa\u001f\n\t\tu\u0014Q\u001f\u0002\u0007\u001f\nTWm\u0019;")
public final class Failure<T>
extends Try<T> {
    private final Throwable exception;

    public static <T> Option<Throwable> unapply(Failure<T> x$0) {
        return Failure$.MODULE$.unapply(x$0);
    }

    public static <T> Failure<T> apply(Throwable exception) {
        return new Failure<T>(exception);
    }

    public Throwable exception() {
        return this.exception;
    }

    @Override
    public boolean isFailure() {
        return true;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public T get() {
        throw this.exception();
    }

    @Override
    public <U> U getOrElse(Function0<U> function0) {
        return function0.apply();
    }

    @Override
    public <U> Try<U> orElse(Function0<Try<U>> function0) {
        try {
            return function0.apply();
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            if (throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty()) {
                Throwable e = option.get();
                return new Failure<T>(e);
            }
            throw throwable;
        }
    }

    @Override
    public <U> Try<U> flatMap(Function1<T, Try<U>> f) {
        return this;
    }

    @Override
    public <U> Try<U> flatten($less$colon$less<T, Try<U>> ev) {
        return this;
    }

    @Override
    public <U> void foreach(Function1<T, U> f) {
    }

    @Override
    public <U> Try<U> transform(Function1<T, Try<U>> s2, Function1<Throwable, Try<U>> f) {
        try {
            return f.apply(this.exception());
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            if (throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty()) {
                Throwable e = option.get();
                return new Failure<T>(e);
            }
            throw throwable;
        }
    }

    @Override
    public <U> Try<U> map(Function1<T, U> f) {
        return this;
    }

    @Override
    public <U> Try<U> collect(PartialFunction<T, U> pf) {
        return this;
    }

    @Override
    public Try<T> filter(Function1<T, Object> p) {
        return this;
    }

    @Override
    public <U> Try<U> recover(PartialFunction<Throwable, U> pf) {
        Success success;
        Object marker = Statics.pfMarker;
        try {
            Object v = pf.applyOrElse(this.exception(), (Function1<Throwable, Object> & Serializable)x -> marker);
            if (marker == v) {
                return this;
            }
            success = new Success(v);
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            if (throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty()) {
                Throwable e = option.get();
                return new Failure<T>(e);
            }
            throw throwable;
        }
        return success;
    }

    @Override
    public <U> Try<U> recoverWith(PartialFunction<Throwable, Try<U>> pf) {
        Try try_;
        Object marker = Statics.pfMarker;
        try {
            Object v = pf.applyOrElse(this.exception(), (Function1<Throwable, Object> & Serializable)x -> marker);
            if (marker == v) {
                return this;
            }
            try_ = (Try)v;
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            if (throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty()) {
                Throwable e = option.get();
                return new Failure<T>(e);
            }
            throw throwable;
        }
        return try_;
    }

    @Override
    public Try<Throwable> failed() {
        return new Success<Throwable>(this.exception());
    }

    @Override
    public Option<T> toOption() {
        return None$.MODULE$;
    }

    @Override
    public Either<Throwable, T> toEither() {
        return new Left(this.exception());
    }

    @Override
    public <U> U fold(Function1<Throwable, U> fa, Function1<T, U> fb) {
        return fa.apply(this.exception());
    }

    public <T> Failure<T> copy(Throwable exception) {
        return new Failure<T>(exception);
    }

    public <T> Throwable copy$default$1() {
        return this.exception();
    }

    @Override
    public String productPrefix() {
        return "Failure";
    }

    @Override
    public int productArity() {
        return 1;
    }

    @Override
    public Object productElement(int x$1) {
        switch (x$1) {
            case 0: {
                return this.exception();
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
        return x$1 instanceof Failure;
    }

    @Override
    public String productElementName(int x$1) {
        switch (x$1) {
            case 0: {
                return "exception";
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
                if (!(x$1 instanceof Failure)) break block3;
                Failure failure = (Failure)x$1;
                Throwable throwable = this.exception();
                Throwable throwable2 = failure.exception();
                if (throwable != null ? !throwable.equals(throwable2) : throwable2 != null) break block3;
            }
            return true;
        }
        return false;
    }

    public Failure(Throwable exception) {
        this.exception = exception;
    }
}

