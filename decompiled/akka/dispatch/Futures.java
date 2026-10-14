/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.Futures$;
import akka.japi.Function;
import akka.japi.Function2;
import akka.japi.Option;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005]v!\u0002\b\u0010\u0011\u0003!b!\u0002\f\u0010\u0011\u00039\u0002\"\u0002\u0010\u0002\t\u0003y\u0002\"\u0002\u0011\u0002\t\u0003\t\u0003\"\u0002#\u0002\t\u0003)\u0005\"\u0002'\u0002\t\u0003i\u0005\"\u00021\u0002\t\u0003\t\u0007\"\u00025\u0002\t\u0003I\u0007\"\u0002:\u0002\t\u0003\u0019\bbBA\u0012\u0003\u0011\u0005\u0011Q\u0005\u0005\b\u0003k\tA\u0011AA\u001c\u0011\u001d\ti&\u0001C\u0001\u0003?Bq!a\u001f\u0002\t\u0003\ti\bC\u0004\u0002\u0016\u0006!\t!a&\u0002\u000f\u0019+H/\u001e:fg*\u0011\u0001#E\u0001\tI&\u001c\b/\u0019;dQ*\t!#\u0001\u0003bW.\f7\u0001\u0001\t\u0003+\u0005i\u0011a\u0004\u0002\b\rV$XO]3t'\t\t\u0001\u0004\u0005\u0002\u001a95\t!DC\u0001\u001c\u0003\u0015\u00198-\u00197b\u0013\ti\"D\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003Q\taAZ;ukJ,WC\u0001\u0012,)\r\u0019Cg\u0010\t\u0004I\u001dJS\"A\u0013\u000b\u0005\u0019R\u0012AC2p]\u000e,(O]3oi&\u0011\u0001&\n\u0002\u0007\rV$XO]3\u0011\u0005)ZC\u0002\u0001\u0003\u0006Y\r\u0011\r!\f\u0002\u0002)F\u0011a&\r\t\u00033=J!\u0001\r\u000e\u0003\u000f9{G\u000f[5oOB\u0011\u0011DM\u0005\u0003gi\u00111!\u00118z\u0011\u0015)4\u00011\u00017\u0003\u0011\u0011w\u000eZ=\u0011\u0007]j\u0014&D\u00019\u0015\t1\u0013H\u0003\u0002;w\u0005!Q\u000f^5m\u0015\u0005a\u0014\u0001\u00026bm\u0006L!A\u0010\u001d\u0003\u0011\r\u000bG\u000e\\1cY\u0016DQ\u0001Q\u0002A\u0002\u0005\u000b\u0001\"\u001a=fGV$xN\u001d\t\u0003I\tK!aQ\u0013\u0003!\u0015CXmY;uS>t7i\u001c8uKb$\u0018a\u00029s_6L7/Z\u000b\u0003\r.#\u0012a\u0012\t\u0004I!S\u0015BA%&\u0005\u001d\u0001&o\\7jg\u0016\u0004\"AK&\u0005\u000b1\"!\u0019A\u0017\u0002\r\u0019\f\u0017\u000e\\3e+\tq\u0015\u000b\u0006\u0002P%B\u0019Ae\n)\u0011\u0005)\nF!\u0002\u0017\u0006\u0005\u0004i\u0003\"B*\u0006\u0001\u0004!\u0016!C3yG\u0016\u0004H/[8o!\t)VL\u0004\u0002W7:\u0011qKW\u0007\u00021*\u0011\u0011lE\u0001\u0007yI|w\u000e\u001e \n\u0003mI!\u0001\u0018\u000e\u0002\u000fA\f7m[1hK&\u0011al\u0018\u0002\n)\"\u0014xn^1cY\u0016T!\u0001\u0018\u000e\u0002\u0015M,8mY3tg\u001a,H.\u0006\u0002cKR\u00111M\u001a\t\u0004I\u001d\"\u0007C\u0001\u0016f\t\u0015acA1\u0001.\u0011\u00159g\u00011\u0001e\u0003\u0019\u0011Xm];mi\u0006)b-Y5mK\u0012\u001cu.\u001c9mKRLwN\\*uC\u001e,WC\u00016p)\tY\u0007\u000fE\u00028Y:L!!\u001c\u001d\u0003\u001f\r{W\u000e\u001d7fi&|gn\u0015;bO\u0016\u0004\"AK8\u0005\u000b1:!\u0019A\u0017\t\u000bE<\u0001\u0019\u0001+\u0002\u0005\u0015D\u0018\u0001\u00024j]\u0012,\"\u0001^?\u0015\rU|\u0018\u0011CA\u0011!\r!sE\u001e\t\u0004ojdX\"\u0001=\u000b\u0005e\f\u0012\u0001\u00026ba&L!a\u001f=\u0003\r=\u0003H/[8o!\tQS\u0010B\u0003-\u0011\t\u0007a0\u0005\u0002/1!9\u0011\u0011\u0001\u0005A\u0002\u0005\r\u0011a\u00024viV\u0014Xm\u001d\t\u0007\u0003\u000b\tY!a\u0004\u000e\u0005\u0005\u001d!bAA\u0005w\u0005!A.\u00198h\u0013\u0011\ti!a\u0002\u0003\u0011%#XM]1cY\u0016\u00042\u0001J\u0014}\u0011\u001d\t\u0019\u0002\u0003a\u0001\u0003+\t\u0011\u0002\u001d:fI&\u001c\u0017\r^3\u0011\r]\f9\u0002`A\u000e\u0013\r\tI\u0002\u001f\u0002\t\rVt7\r^5p]B!\u0011QAA\u000f\u0013\u0011\ty\"a\u0002\u0003\u000f\t{w\u000e\\3b]\")\u0001\t\u0003a\u0001\u0003\u0006\u0001b-\u001b:ti\u000e{W\u000e\u001d7fi\u0016$wJZ\u000b\u0005\u0003O\ti\u0003\u0006\u0004\u0002*\u0005=\u00121\u0007\t\u0005I\u001d\nY\u0003E\u0002+\u0003[!Q\u0001L\u0005C\u0002yDq!!\u0001\n\u0001\u0004\t\t\u0004\u0005\u0004\u0002\u0006\u0005-\u0011\u0011\u0006\u0005\u0006\u0001&\u0001\r!Q\u0001\u0005M>dG-\u0006\u0004\u0002:\u0005=\u0013q\b\u000b\u000b\u0003w\t\u0019%a\u0012\u0002R\u0005m\u0003\u0003\u0002\u0013(\u0003{\u00012AKA \t\u0019\t\tE\u0003b\u0001}\n\t!\u000bC\u0004\u0002F)\u0001\r!!\u0010\u0002\ti,'o\u001c\u0005\b\u0003\u0003Q\u0001\u0019AA%!\u0019\t)!a\u0003\u0002LA!AeJA'!\rQ\u0013q\n\u0003\u0006Y)\u0011\rA \u0005\b\u0003'R\u0001\u0019AA+\u0003\r1WO\u001c\t\no\u0006]\u0013QHA'\u0003{I1!!\u0017y\u0005%1UO\\2uS>t'\u0007C\u0003A\u0015\u0001\u0007\u0011)\u0001\u0004sK\u0012,8-Z\u000b\u0007\u0003C\ni'a\u001a\u0015\u0011\u0005\r\u0014qNA;\u0003s\u0002B\u0001J\u0014\u0002fA\u0019!&a\u001a\u0005\u000f\u0005\u00053B1\u0001\u0002jE\u0019\u00111N\u0019\u0011\u0007)\ni\u0007B\u0003-\u0017\t\u0007a\u0010C\u0004\u0002\u0002-\u0001\r!!\u001d\u0011\r\u0005\u0015\u00111BA:!\u0011!s%a\u001b\t\u000f\u0005M3\u00021\u0001\u0002xAIq/a\u0016\u0002f\u0005-\u0014Q\r\u0005\u0006\u0001.\u0001\r!Q\u0001\tg\u0016\fX/\u001a8dKV!\u0011qPAD)\u0019\t\t)a#\u0002\u0014B!AeJAB!\u0019\t)!a\u0003\u0002\u0006B\u0019!&a\"\u0005\r\u0005%EB1\u0001.\u0005\u0005\t\u0005bBAG\u0019\u0001\u0007\u0011qR\u0001\u0003S:\u0004b!!\u0002\u0002\f\u0005E\u0005\u0003\u0002\u0013(\u0003\u000bCQ\u0001\u0011\u0007A\u0002\u0005\u000b\u0001\u0002\u001e:bm\u0016\u00148/Z\u000b\u0007\u00033\u000bY+!)\u0015\u0011\u0005m\u0015QUAW\u0003k\u0003B\u0001J\u0014\u0002\u001eB1\u0011QAA\u0006\u0003?\u00032AKAQ\t\u0019\t\u0019+\u0004b\u0001[\t\t!\tC\u0004\u0002\u000e6\u0001\r!a*\u0011\r\u0005\u0015\u00111BAU!\rQ\u00131\u0016\u0003\u0007\u0003\u0013k!\u0019A\u0017\t\u000f\u0005=V\u00021\u0001\u00022\u0006\u0011aM\u001c\t\bo\u0006]\u0011\u0011VAZ!\u0011!s%a(\t\u000b\u0001k\u0001\u0019A!")
public final class Futures {
    public static <A, B> Future<Iterable<B>> traverse(Iterable<A> in, Function<A, Future<B>> fn, ExecutionContext executor2) {
        return Futures$.MODULE$.traverse(in, fn, executor2);
    }

    public static <A> Future<Iterable<A>> sequence(Iterable<Future<A>> in, ExecutionContext executor2) {
        return Futures$.MODULE$.sequence(in, executor2);
    }

    public static <T, R> Future<R> reduce(Iterable<Future<T>> futures, Function2<R, T, R> fun, ExecutionContext executor2) {
        return Futures$.MODULE$.reduce(futures, fun, executor2);
    }

    public static <T, R> Future<R> fold(R zero, Iterable<Future<T>> futures, Function2<R, T, R> fun, ExecutionContext executor2) {
        return Futures$.MODULE$.fold(zero, futures, fun, executor2);
    }

    public static <T> Future<T> firstCompletedOf(Iterable<Future<T>> futures, ExecutionContext executor2) {
        return Futures$.MODULE$.firstCompletedOf(futures, executor2);
    }

    public static <T> Future<Option<T>> find(Iterable<Future<T>> futures, Function<T, Boolean> predicate, ExecutionContext executor2) {
        return Futures$.MODULE$.find(futures, predicate, executor2);
    }

    public static <T> CompletionStage<T> failedCompletionStage(Throwable ex) {
        return Futures$.MODULE$.failedCompletionStage(ex);
    }

    public static <T> Future<T> successful(T result) {
        return Futures$.MODULE$.successful(result);
    }

    public static <T> Future<T> failed(Throwable exception) {
        return Futures$.MODULE$.failed(exception);
    }

    public static <T> Promise<T> promise() {
        return Futures$.MODULE$.promise();
    }

    public static <T> Future<T> future(Callable<T> body2, ExecutionContext executor2) {
        return Futures$.MODULE$.future(body2, executor2);
    }
}

