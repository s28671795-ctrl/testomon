/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Function$;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.Tuple4;
import scala.Tuple5;
import scala.collection.Seq;
import scala.reflect.ScalaSignature;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ScalaSignature(bytes="\u0006\u0005\tEs!\u0002\n\u0014\u0011\u00031b!\u0002\r\u0014\u0011\u0003I\u0002\"B\u000f\u0002\t\u0003q\u0002\"B\u0010\u0002\t\u0003\u0001\u0003\"\u0002\u001d\u0002\t\u0003I\u0004\"B#\u0002\t\u00031\u0005\"\u0002,\u0002\t\u00039\u0006\"\u0002,\u0002\t\u00039\u0007\"\u0002,\u0002\t\u0003I\bB\u0002,\u0002\t\u0003\ti\u0002C\u0004\u0002N\u0005!\t!a\u0014\t\u000f\u00055\u0013\u0001\"\u0001\u0002l!9\u0011QJ\u0001\u0005\u0002\u0005-\u0005bBA'\u0003\u0011\u0005\u0011q\u0016\u0005\b\u0003/\fA\u0011AAm\u0011\u001d\t9.\u0001C\u0001\u0003cDq!a6\u0002\t\u0003\u0011i\u0001C\u0004\u0002X\u0006!\tA!\f\u0002\u0011\u0019+hn\u0019;j_:T\u0011\u0001F\u0001\u0006g\u000e\fG.Y\u0002\u0001!\t9\u0012!D\u0001\u0014\u0005!1UO\\2uS>t7CA\u0001\u001b!\t92$\u0003\u0002\u001d'\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#\u0001\f\u0002\u000b\rD\u0017-\u001b8\u0016\u0005\u0005:CC\u0001\u00121!\u001192%J\u0013\n\u0005\u0011\u001a\"!\u0003$v]\u000e$\u0018n\u001c82!\t1s\u0005\u0004\u0001\u0005\u000b!\u001a!\u0019A\u0015\u0003\u0003Q\u000b\"AK\u0017\u0011\u0005]Y\u0013B\u0001\u0017\u0014\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"a\u0006\u0018\n\u0005=\u001a\"aA!os\")\u0011g\u0001a\u0001e\u0005\u0011am\u001d\t\u0004gY\u0012S\"\u0001\u001b\u000b\u0005U\u001a\u0012AC2pY2,7\r^5p]&\u0011q\u0007\u000e\u0002\u0004'\u0016\f\u0018!B2p]N$Xc\u0001\u001e>\u0003R\u00111h\u0011\u000b\u0003yy\u0002\"AJ\u001f\u0005\u000b!\"!\u0019A\u0015\t\u000b}\"\u0001\u0019\u0001!\u0002\u0003e\u0004\"AJ!\u0005\u000b\t#!\u0019A\u0015\u0003\u0003UCQ\u0001\u0012\u0003A\u0002q\n\u0011\u0001_\u0001\u0007k:d\u0017N\u001a;\u0016\u0007\u001dce\n\u0006\u0002I!B!q#S&N\u0013\tQ5CA\bQCJ$\u0018.\u00197Gk:\u001cG/[8o!\t1C\nB\u0003)\u000b\t\u0007\u0011\u0006\u0005\u0002'\u001d\u0012)q*\u0002b\u0001S\t\t!\u000bC\u0003R\u000b\u0001\u0007!+A\u0001g!\u001192eS*\u0011\u0007]!V*\u0003\u0002V'\t1q\n\u001d;j_:\f\u0011\"\u001e8dkJ\u0014\u0018.\u001a3\u0016\tak\u0006m\u0019\u000b\u00033\u0012\u0004Ra\u0006.]?\nL!aW\n\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004C\u0001\u0014^\t\u0015qfA1\u0001*\u0005\t!\u0016\u0007\u0005\u0002'A\u0012)\u0011M\u0002b\u0001S\t\u0011AK\r\t\u0003M\r$Qa\u0014\u0004C\u0002%BQ!\u0015\u0004A\u0002\u0015\u0004BaF\u0012]MB!qcI0c+\u0015AWn\\9u)\tIW\u000f\u0005\u0004\u0018U2t\u0007o]\u0005\u0003WN\u0011\u0011BR;oGRLwN\\\u001a\u0011\u0005\u0019jG!\u00020\b\u0005\u0004I\u0003C\u0001\u0014p\t\u0015\twA1\u0001*!\t1\u0013\u000fB\u0003s\u000f\t\u0007\u0011F\u0001\u0002UgA\u0011a\u0005\u001e\u0003\u0006\u001f\u001e\u0011\r!\u000b\u0005\u0006#\u001e\u0001\rA\u001e\t\u0005/\rbw\u000f\u0005\u0003\u0018G9D\b\u0003B\f$aN,\"B_@\u0002\u0004\u0005\u001d\u00111BA\t)\rY\u00181\u0003\t\f/qt\u0018\u0011AA\u0003\u0003\u0013\ty!\u0003\u0002~'\tIa)\u001e8di&|g\u000e\u000e\t\u0003M}$QA\u0018\u0005C\u0002%\u00022AJA\u0002\t\u0015\t\u0007B1\u0001*!\r1\u0013q\u0001\u0003\u0006e\"\u0011\r!\u000b\t\u0004M\u0005-AABA\u0007\u0011\t\u0007\u0011F\u0001\u0002UiA\u0019a%!\u0005\u0005\u000b=C!\u0019A\u0015\t\rEC\u0001\u0019AA\u000b!\u001592E`A\f!\u001992%!\u0001\u0002\u001aA1qcIA\u0003\u00037\u0001baF\u0012\u0002\n\u0005=QCDA\u0010\u0003S\ti#!\r\u00026\u0005e\u0012q\b\u000b\u0005\u0003C\t\t\u0005E\b\u0018\u0003G\t9#a\u000b\u00020\u0005M\u0012qGA\u001f\u0013\r\t)c\u0005\u0002\n\rVt7\r^5p]V\u00022AJA\u0015\t\u0015q\u0016B1\u0001*!\r1\u0013Q\u0006\u0003\u0006C&\u0011\r!\u000b\t\u0004M\u0005EB!\u0002:\n\u0005\u0004I\u0003c\u0001\u0014\u00026\u00111\u0011QB\u0005C\u0002%\u00022AJA\u001d\t\u0019\tY$\u0003b\u0001S\t\u0011A+\u000e\t\u0004M\u0005}B!B(\n\u0005\u0004I\u0003BB)\n\u0001\u0004\t\u0019\u0005\u0005\u0004\u0018G\u0005\u001d\u0012Q\t\t\u0007/\r\nY#a\u0012\u0011\r]\u0019\u0013qFA%!\u001992%a\r\u0002LA1qcIA\u001c\u0003{\ta\u0001^;qY\u0016$W\u0003CA)\u0003;\n\t'!\u001a\u0015\t\u0005M\u0013q\r\t\u0007/\r\n)&a\u0019\u0011\u000f]\t9&a\u0017\u0002`%\u0019\u0011\u0011L\n\u0003\rQ+\b\u000f\\33!\r1\u0013Q\f\u0003\u0006=*\u0011\r!\u000b\t\u0004M\u0005\u0005D!B1\u000b\u0005\u0004I\u0003c\u0001\u0014\u0002f\u0011)qJ\u0003b\u0001S!1\u0011K\u0003a\u0001\u0003S\u0002\u0002b\u0006.\u0002\\\u0005}\u00131M\u000b\u000b\u0003[\nI(! \u0002\u0002\u0006\u0015E\u0003BA8\u0003\u000f\u0003baF\u0012\u0002r\u0005\r\u0005#C\f\u0002t\u0005]\u00141PA@\u0013\r\t)h\u0005\u0002\u0007)V\u0004H.Z\u001a\u0011\u0007\u0019\nI\bB\u0003_\u0017\t\u0007\u0011\u0006E\u0002'\u0003{\"Q!Y\u0006C\u0002%\u00022AJAA\t\u0015\u00118B1\u0001*!\r1\u0013Q\u0011\u0003\u0006\u001f.\u0011\r!\u000b\u0005\u0007#.\u0001\r!!#\u0011\u0015]Q\u0017qOA>\u0003\u007f\n\u0019)\u0006\u0007\u0002\u000e\u0006e\u0015QTAQ\u0003K\u000bI\u000b\u0006\u0003\u0002\u0010\u0006-\u0006CB\f$\u0003#\u000b9\u000bE\u0006\u0018\u0003'\u000b9*a'\u0002 \u0006\r\u0016bAAK'\t1A+\u001e9mKR\u00022AJAM\t\u0015qFB1\u0001*!\r1\u0013Q\u0014\u0003\u0006C2\u0011\r!\u000b\t\u0004M\u0005\u0005F!\u0002:\r\u0005\u0004I\u0003c\u0001\u0014\u0002&\u00121\u0011Q\u0002\u0007C\u0002%\u00022AJAU\t\u0015yEB1\u0001*\u0011\u0019\tF\u00021\u0001\u0002.Baq\u0003`AL\u00037\u000by*a)\u0002(Vq\u0011\u0011WA_\u0003\u0003\f)-!3\u0002N\u0006EG\u0003BAZ\u0003'\u0004baF\u0012\u00026\u0006=\u0007#D\f\u00028\u0006m\u0016qXAb\u0003\u000f\fY-C\u0002\u0002:N\u0011a\u0001V;qY\u0016,\u0004c\u0001\u0014\u0002>\u0012)a,\u0004b\u0001SA\u0019a%!1\u0005\u000b\u0005l!\u0019A\u0015\u0011\u0007\u0019\n)\rB\u0003s\u001b\t\u0007\u0011\u0006E\u0002'\u0003\u0013$a!!\u0004\u000e\u0005\u0004I\u0003c\u0001\u0014\u0002N\u00121\u00111H\u0007C\u0002%\u00022AJAi\t\u0015yUB1\u0001*\u0011\u0019\tV\u00021\u0001\u0002VByq#a\t\u0002<\u0006}\u00161YAd\u0003\u0017\fy-\u0001\u0005v]R,\b\u000f\\3e+!\tY.!9\u0002f\u0006%H\u0003BAo\u0003W\u0004\u0002b\u0006.\u0002`\u0006\r\u0018q\u001d\t\u0004M\u0005\u0005H!\u00020\u000f\u0005\u0004I\u0003c\u0001\u0014\u0002f\u0012)\u0011M\u0004b\u0001SA\u0019a%!;\u0005\u000b=s!\u0019A\u0015\t\rEs\u0001\u0019AAw!\u001992%a<\u0002hB9q#a\u0016\u0002`\u0006\rXCCAz\u0003s\fiP!\u0001\u0003\u0006Q!\u0011Q\u001fB\u0004!)9\".a>\u0002|\u0006}(1\u0001\t\u0004M\u0005eH!\u00020\u0010\u0005\u0004I\u0003c\u0001\u0014\u0002~\u0012)\u0011m\u0004b\u0001SA\u0019aE!\u0001\u0005\u000bI|!\u0019A\u0015\u0011\u0007\u0019\u0012)\u0001B\u0003P\u001f\t\u0007\u0011\u0006\u0003\u0004R\u001f\u0001\u0007!\u0011\u0002\t\u0007/\r\u0012YAa\u0001\u0011\u0013]\t\u0019(a>\u0002|\u0006}X\u0003\u0004B\b\u0005+\u0011IB!\b\u0003\"\t\u0015B\u0003\u0002B\t\u0005O\u0001Bb\u0006?\u0003\u0014\t]!1\u0004B\u0010\u0005G\u00012A\nB\u000b\t\u0015q\u0006C1\u0001*!\r1#\u0011\u0004\u0003\u0006CB\u0011\r!\u000b\t\u0004M\tuA!\u0002:\u0011\u0005\u0004I\u0003c\u0001\u0014\u0003\"\u00111\u0011Q\u0002\tC\u0002%\u00022A\nB\u0013\t\u0015y\u0005C1\u0001*\u0011\u0019\t\u0006\u00031\u0001\u0003*A1qc\tB\u0016\u0005G\u00012bFAJ\u0005'\u00119Ba\u0007\u0003 Uq!q\u0006B\u001b\u0005s\u0011iD!\u0011\u0003F\t%C\u0003\u0002B\u0019\u0005\u0017\u0002rbFA\u0012\u0005g\u00119Da\u000f\u0003@\t\r#q\t\t\u0004M\tUB!\u00020\u0012\u0005\u0004I\u0003c\u0001\u0014\u0003:\u0011)\u0011-\u0005b\u0001SA\u0019aE!\u0010\u0005\u000bI\f\"\u0019A\u0015\u0011\u0007\u0019\u0012\t\u0005\u0002\u0004\u0002\u000eE\u0011\r!\u000b\t\u0004M\t\u0015CABA\u001e#\t\u0007\u0011\u0006E\u0002'\u0005\u0013\"QaT\tC\u0002%Ba!U\tA\u0002\t5\u0003CB\f$\u0005\u001f\u00129\u0005E\u0007\u0018\u0003o\u0013\u0019Da\u000e\u0003<\t}\"1\t")
public final class Function {
    public static <T1, T2, T3, T4, T5, R> Function5<T1, T2, T3, T4, T5, R> untupled(Function1<Tuple5<T1, T2, T3, T4, T5>, R> f) {
        return Function$.MODULE$.untupled(f);
    }

    public static <T1, T2, T3, T4, R> Function4<T1, T2, T3, T4, R> untupled(Function1<Tuple4<T1, T2, T3, T4>, R> f) {
        return Function$.MODULE$.untupled(f);
    }

    public static <T1, T2, T3, R> Function3<T1, T2, T3, R> untupled(Function1<Tuple3<T1, T2, T3>, R> f) {
        return Function$.MODULE$.untupled(f);
    }

    public static <T1, T2, R> Function2<T1, T2, R> untupled(Function1<Tuple2<T1, T2>, R> f) {
        return Function$.MODULE$.untupled(f);
    }

    public static <T1, T2, T3, T4, T5, R> Function1<Tuple5<T1, T2, T3, T4, T5>, R> tupled(Function5<T1, T2, T3, T4, T5, R> f) {
        return Function$.MODULE$.tupled(f);
    }

    public static <T1, T2, T3, T4, R> Function1<Tuple4<T1, T2, T3, T4>, R> tupled(Function4<T1, T2, T3, T4, R> f) {
        return Function$.MODULE$.tupled(f);
    }

    public static <T1, T2, T3, R> Function1<Tuple3<T1, T2, T3>, R> tupled(Function3<T1, T2, T3, R> f) {
        return Function$.MODULE$.tupled(f);
    }

    public static <T1, T2, R> Function1<Tuple2<T1, T2>, R> tupled(Function2<T1, T2, R> f) {
        return Function$.MODULE$.tupled(f);
    }

    public static <T1, T2, T3, T4, T5, R> Function5<T1, T2, T3, T4, T5, R> uncurried(Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, R>>>>> f) {
        return Function$.MODULE$.uncurried(f);
    }

    public static <T1, T2, T3, T4, R> Function4<T1, T2, T3, T4, R> uncurried(Function1<T1, Function1<T2, Function1<T3, Function1<T4, R>>>> f) {
        return Function$.MODULE$.uncurried(f);
    }

    public static <T1, T2, T3, R> Function3<T1, T2, T3, R> uncurried(Function1<T1, Function1<T2, Function1<T3, R>>> f) {
        return Function$.MODULE$.uncurried(f);
    }

    public static <T1, T2, R> Function2<T1, T2, R> uncurried(Function1<T1, Function1<T2, R>> f) {
        return Function$.MODULE$.uncurried(f);
    }

    public static <T, R> PartialFunction<T, R> unlift(Function1<T, Option<R>> f) {
        return Function$.MODULE$.unlift(f);
    }

    public static <T, U> T const(T x, U y) {
        return Function$.MODULE$.const(x, y);
    }

    public static <T> Function1<T, T> chain(Seq<Function1<T, T>> fs) {
        return Function$.MODULE$.chain(fs);
    }
}

