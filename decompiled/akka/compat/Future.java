/*
 * Decompiled with CFR 0.152.
 */
package akka.compat;

import akka.annotation.InternalApi;
import akka.compat.Future$;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.collection.IterableOnce;
import scala.collection.immutable.Iterable;
import scala.concurrent.ExecutionContext;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005-tAB\u0005\u000b\u0011\u0003aaB\u0002\u0004\u0011\u0015!\u0005A\"\u0005\u0005\u00061\u0005!\tA\u0007\u0005\u00067\u0005!\t\u0001\b\u0005\u00067\u0005!\ta\u0014\u0005\u0006O\u0006!\t\u0001\u001b\u0005\u0006O\u0006!\t\u0001\u001f\u0005\b\u0003#\tA\u0011AA\n\u0011\u001d\t\t\"\u0001C\u0001\u0003\u007f\taAR;ukJ,'BA\u0006\r\u0003\u0019\u0019w.\u001c9bi*\tQ\"\u0001\u0003bW.\f\u0007CA\b\u0002\u001b\u0005Q!A\u0002$viV\u0014Xm\u0005\u0002\u0002%A\u00111CF\u0007\u0002))\tQ#A\u0003tG\u0006d\u0017-\u0003\u0002\u0018)\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002\u001d\u0005!am\u001c7e+\riB\b\u000b\u000b\u0003=\u0001#\"a\b \u0015\u0005\u00012DCA\u00112!\r\u0011SEJ\u0007\u0002G)\u0011A\u0005F\u0001\u000bG>t7-\u001e:sK:$\u0018B\u0001\t$!\t9\u0003\u0006\u0004\u0001\u0005\u000b%\u001a!\u0019\u0001\u0016\u0003\u0003I\u000b\"a\u000b\u0018\u0011\u0005Ma\u0013BA\u0017\u0015\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aE\u0018\n\u0005A\"\"aA!os\")!g\u0001a\u0002g\u0005AQ\r_3dkR|'\u000f\u0005\u0002#i%\u0011Qg\t\u0002\u0011\u000bb,7-\u001e;j_:\u001cuN\u001c;fqRDQaN\u0002A\u0002a\n!a\u001c9\u0011\u000bMIde\u000f\u0014\n\u0005i\"\"!\u0003$v]\u000e$\u0018n\u001c83!\t9C\bB\u0003>\u0007\t\u0007!FA\u0001U\u0011\u0015y4\u00011\u0001'\u0003\u0011QXM]8\t\u000b\u0005\u001b\u0001\u0019\u0001\"\u0002\u000f\u0019,H/\u001e:fgB\u00191i\u0013(\u000f\u0005\u0011KeBA#I\u001b\u00051%BA$\u001a\u0003\u0019a$o\\8u}%\tQ#\u0003\u0002K)\u00059\u0001/Y2lC\u001e,\u0017B\u0001'N\u00051IE/\u001a:bE2,wJ\\2f\u0015\tQE\u0003E\u0002#Km*2\u0001U.W)\t\tV\f\u0006\u0002S9R\u00111\u000b\u0017\u000b\u0003)^\u00032AI\u0013V!\t9c\u000bB\u0003*\t\t\u0007!\u0006C\u00033\t\u0001\u000f1\u0007C\u00038\t\u0001\u0007\u0011\fE\u0003\u0014sUSV\u000b\u0005\u0002(7\u0012)Q\b\u0002b\u0001U!)q\b\u0002a\u0001+\")\u0011\t\u0002a\u0001=B\u0019q\f\u001a4\u000e\u0003\u0001T!!\u00192\u0002\u0013%lW.\u001e;bE2,'BA2\u0015\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003K\u0002\u0014\u0001\"\u0013;fe\u0006\u0014G.\u001a\t\u0004E\u0015R\u0016A\u0002:fIV\u001cW-F\u0002jc:$\"A[;\u0015\u0005-\u001cHC\u00017s!\r\u0011S%\u001c\t\u0003O9$Q!K\u0003C\u0002=\f\"\u0001\u001d\u0018\u0011\u0005\u001d\nH!B\u001f\u0006\u0005\u0004Q\u0003\"\u0002\u001a\u0006\u0001\b\u0019\u0004\"B\u001c\u0006\u0001\u0004!\b#B\n:[Bl\u0007\"B!\u0006\u0001\u00041\bcA\"LoB\u0019!%\n9\u0016\te\f\u0019A \u000b\u0004u\u0006-AcA>\u0002\bQ\u0019A0!\u0002\u0011\u0007\t*S\u0010\u0005\u0002(}\u0012)\u0011F\u0002b\u0001\u007fF\u0019\u0011\u0011\u0001\u0018\u0011\u0007\u001d\n\u0019\u0001B\u0003>\r\t\u0007!\u0006C\u00033\r\u0001\u000f1\u0007\u0003\u00048\r\u0001\u0007\u0011\u0011\u0002\t\u0007'ej\u0018\u0011A?\t\r\u00053\u0001\u0019AA\u0007!\u0011yF-a\u0004\u0011\t\t*\u0013\u0011A\u0001\u0005M&tG-\u0006\u0003\u0002\u0016\u0005\u0015B\u0003BA\f\u0003s!B!!\u0007\u0002*Q!\u00111DA\u0014!\u0011\u0011S%!\b\u0011\u000bM\ty\"a\t\n\u0007\u0005\u0005BC\u0001\u0004PaRLwN\u001c\t\u0004O\u0005\u0015B!B\u001f\b\u0005\u0004Q\u0003\"\u0002\u001a\b\u0001\b\u0019\u0004bBA\u0016\u000f\u0001\u0007\u0011QF\u0001\u0002aB91#a\f\u0002$\u0005M\u0012bAA\u0019)\tIa)\u001e8di&|g.\r\t\u0004'\u0005U\u0012bAA\u001c)\t9!i\\8mK\u0006t\u0007BB!\b\u0001\u0004\tY\u0004\u0005\u0003D\u0017\u0006u\u0002\u0003\u0002\u0012&\u0003G)B!!\u0011\u0002NQ!\u00111IA+)\u0011\t)%!\u0015\u0015\t\u0005\u001d\u0013q\n\t\u0005E\u0015\nI\u0005E\u0003\u0014\u0003?\tY\u0005E\u0002(\u0003\u001b\"Q!\u0010\u0005C\u0002)BQA\r\u0005A\u0004MBq!a\u000b\t\u0001\u0004\t\u0019\u0006E\u0004\u0014\u0003_\tY%a\r\t\r\u0005C\u0001\u0019AA,!\u0011yF-!\u0017\u0011\t\t*\u00131\n\u0015\u0004\u0003\u0005u\u0003\u0003BA0\u0003Kj!!!\u0019\u000b\u0007\u0005\rD\"\u0001\u0006b]:|G/\u0019;j_:LA!a\u001a\u0002b\tY\u0011J\u001c;fe:\fG.\u00119jQ\r\u0001\u0011Q\f")
@InternalApi
public final class Future {
    public static <T> scala.concurrent.Future<Option<T>> find(Iterable<scala.concurrent.Future<T>> futures, Function1<T, Object> p, ExecutionContext executor2) {
        return Future$.MODULE$.find(futures, p, executor2);
    }

    public static <T> scala.concurrent.Future<Option<T>> find(IterableOnce<scala.concurrent.Future<T>> futures, Function1<T, Object> p, ExecutionContext executor2) {
        return Future$.MODULE$.find(futures, p, executor2);
    }

    public static <T, R> scala.concurrent.Future<R> reduce(Iterable<scala.concurrent.Future<T>> futures, Function2<R, T, R> op, ExecutionContext executor2) {
        return Future$.MODULE$.reduce(futures, op, executor2);
    }

    public static <T, R> scala.concurrent.Future<R> reduce(IterableOnce<scala.concurrent.Future<T>> futures, Function2<R, T, R> op, ExecutionContext executor2) {
        return Future$.MODULE$.reduce(futures, op, executor2);
    }

    public static <T, R> scala.concurrent.Future<R> fold(Iterable<scala.concurrent.Future<T>> futures, R zero, Function2<R, T, R> op, ExecutionContext executor2) {
        return Future$.MODULE$.fold(futures, zero, op, executor2);
    }

    public static <T, R> scala.concurrent.Future<R> fold(IterableOnce<scala.concurrent.Future<T>> futures, R zero, Function2<R, T, R> op, ExecutionContext executor2) {
        return Future$.MODULE$.fold(futures, zero, op, executor2);
    }
}

