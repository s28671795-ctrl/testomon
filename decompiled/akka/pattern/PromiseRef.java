/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.FutureRef;
import akka.pattern.PromiseRef$;
import akka.util.Timeout;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000514\u0001\u0002D\u0007\u0011\u0002\u0007\u0005!#\u0012\u0005\u00065\u0001!\ta\u0007\u0005\u0006?\u00011\t\u0001\t\u0005\u0006O\u00011\t\u0001\u000b\u0005\u0006u\u0001!)a\u000f\u0005\u0006\u007f\u00011\t\u0001Q\u0004\u0006\u00136A\tA\u0013\u0004\u0006\u00195A\ta\u0013\u0005\u0006\u0019\u001e!\t!\u0014\u0005\u0007\u001d\u001e!\taD(\t\u000ba;A\u0011A-\t\u000ba;A\u0011\u00015\u0003\u0015A\u0013x.\\5tKJ+gM\u0003\u0002\u000f\u001f\u00059\u0001/\u0019;uKJt'\"\u0001\t\u0002\t\u0005\\7.Y\u0002\u0001+\t\u0019\u0012g\u0005\u0002\u0001)A\u0011Q\u0003G\u0007\u0002-)\tq#A\u0003tG\u0006d\u0017-\u0003\u0002\u001a-\t1\u0011I\\=SK\u001a\fa\u0001J5oSR$C#\u0001\u000f\u0011\u0005Ui\u0012B\u0001\u0010\u0017\u0005\u0011)f.\u001b;\u0002\u0007I,g-F\u0001\"!\t\u0011S%D\u0001$\u0015\t!s\"A\u0003bGR|'/\u0003\u0002'G\tA\u0011i\u0019;peJ+g-A\u0004qe>l\u0017n]3\u0016\u0003%\u00022AK\u00170\u001b\u0005Y#B\u0001\u0017\u0017\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0003]-\u0012q\u0001\u0015:p[&\u001cX\r\u0005\u00021c1\u0001A!\u0002\u001a\u0001\u0005\u0004\u0019$!\u0001+\u0012\u0005Q:\u0004CA\u000b6\u0013\t1dCA\u0004O_RD\u0017N\\4\u0011\u0005UA\u0014BA\u001d\u0017\u0005\r\te._\u0001\u0007MV$XO]3\u0016\u0003q\u00022AK\u001f0\u0013\tq4F\u0001\u0004GkR,(/Z\u0001\fi>4U\u000f^;sKJ+g-F\u0001B!\r\u00115iL\u0007\u0002\u001b%\u0011A)\u0004\u0002\n\rV$XO]3SK\u001a\u00142A\u0012%B\r\u00119\u0005\u0001A#\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0011\u0007\t\u0003q&\u0001\u0006Qe>l\u0017n]3SK\u001a\u0004\"AQ\u0004\u0014\u0005\u001d!\u0012A\u0002\u001fj]&$h\bF\u0001K\u0003\u00119(/\u00199\u0016\u0005A\u001bFcA)U-B\u0019!\t\u0001*\u0011\u0005A\u001aF!\u0002\u001a\n\u0005\u0004\u0019\u0004\"B+\n\u0001\u0004\t\u0013\u0001C1di>\u0014(+\u001a4\t\u000b\u001dJ\u0001\u0019A,\u0011\u0007)j#+A\u0003baBd\u0017\u0010F\u0002[7\u0002\u00042A\u0011\u00018\u0011\u0015a&\u00021\u0001^\u0003\u0019\u0019\u0018p\u001d;f[B\u0011!EX\u0005\u0003?\u000e\u00121\"Q2u_J\u001c\u0016p\u001d;f[\")\u0011M\u0003a\u0001E\u00069A/[7f_V$\bCA2g\u001b\u0005!'BA3\u0010\u0003\u0011)H/\u001b7\n\u0005\u001d$'a\u0002+j[\u0016|W\u000f\u001e\u000b\u0003S.$\"A\u00176\t\u000bq[\u00019A/\t\u000b\u0005\\\u0001\u0019\u00012")
public interface PromiseRef<T> {
    public static PromiseRef<Object> apply(Timeout timeout2, ActorSystem system) {
        return PromiseRef$.MODULE$.apply(timeout2, system);
    }

    public static PromiseRef<Object> apply(ActorSystem system, Timeout timeout2) {
        return PromiseRef$.MODULE$.apply(system, timeout2);
    }

    public ActorRef ref();

    public Promise<T> promise();

    public static /* synthetic */ Future future$(PromiseRef $this) {
        return $this.future();
    }

    default public Future<T> future() {
        return this.promise().future();
    }

    public FutureRef<T> toFutureRef();

    public static void $init$(PromiseRef $this) {
    }
}

