/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

import java.io.Serializable;
import scala.Function1;
import scala.concurrent.ExecutionContext$parasitic$;
import scala.concurrent.Future;
import scala.concurrent.Promise$;
import scala.concurrent.impl.Promise;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\u0005-baB\n\u0015!\u0003\r\t!\u0007\u0005\u0006?\u0001!\t\u0001\t\u0005\u0006I\u00011\t!\n\u0005\u0006k\u00011\tA\u000e\u0005\u0006u\u0001!\ta\u000f\u0005\u0006\u000b\u00021\tA\u0012\u0005\u0006\u0011\u0002!\t!\u0013\u0005\u0006\u0019\u0002!)!\u0014\u0005\u00063\u0002!\tA\u0017\u0005\u0006;\u0002!\tA\u0018\u0005\u0006A\u0002!\t!\u0019\u0005\u0006a\u0002!\t!]\u0004\u0006gRA\t\u0001\u001e\u0004\u0006'QA\t!\u001e\u0005\u0006m6!\ta\u001e\u0005\u0006q6!)!\u001f\u0005\u0006}6!)a \u0005\b\u0003\u001biAQAA\b\u0011\u001d\tY\"\u0004C\u0003\u0003;\u0011q\u0001\u0015:p[&\u001cXM\u0003\u0002\u0016-\u0005Q1m\u001c8dkJ\u0014XM\u001c;\u000b\u0003]\tQa]2bY\u0006\u001c\u0001!\u0006\u0002\u001bYM\u0011\u0001a\u0007\t\u00039ui\u0011AF\u0005\u0003=Y\u0011a!\u00118z%\u00164\u0017A\u0002\u0013j]&$H\u0005F\u0001\"!\ta\"%\u0003\u0002$-\t!QK\\5u\u0003\u00191W\u000f^;sKV\ta\u0005E\u0002(Q)j\u0011\u0001F\u0005\u0003SQ\u0011aAR;ukJ,\u0007CA\u0016-\u0019\u0001!Q!\f\u0001C\u00029\u0012\u0011\u0001V\t\u0003_I\u0002\"\u0001\b\u0019\n\u0005E2\"a\u0002(pi\"Lgn\u001a\t\u00039MJ!\u0001\u000e\f\u0003\u0007\u0005s\u00170A\u0006jg\u000e{W\u000e\u001d7fi\u0016$W#A\u001c\u0011\u0005qA\u0014BA\u001d\u0017\u0005\u001d\u0011un\u001c7fC:\f\u0001bY8na2,G/\u001a\u000b\u0003yuj\u0011\u0001\u0001\u0005\u0006}\u0011\u0001\raP\u0001\u0007e\u0016\u001cX\u000f\u001c;\u0011\u0007\u0001\u001b%&D\u0001B\u0015\t\u0011e#\u0001\u0003vi&d\u0017B\u0001#B\u0005\r!&/_\u0001\fiJL8i\\7qY\u0016$X\r\u0006\u00028\u000f\")a(\u0002a\u0001\u007f\u0005a1m\\7qY\u0016$XmV5uQR\u0011AH\u0013\u0005\u0006\u0017\u001a\u0001\rAJ\u0001\u0006_RDWM]\u0001\u0010iJL8i\\7qY\u0016$XmV5uQR\u0011AH\u0014\u0005\u0006\u0017\u001e\u0001\rA\n\u0015\u0007\u000fA\u001bFKV,\u0011\u0005q\t\u0016B\u0001*\u0017\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\u0005)\u0016!U*j]\u000e,\u0007\u0005\u001e5jg\u0002jW\r\u001e5pI\u0002J7\u000fI:f[\u0006tG/[2bY2L\b%Z9vSZ\fG.\u001a8uAQ|\u0007\u0005Y2p[BdW\r^3XSRD\u0007\r\f\u0011vg\u0016\u0004C\u000f[1uA%t7\u000f^3bI:\nQa]5oG\u0016\f\u0013\u0001W\u0001\u0007e9\n4G\f\u0019\u0002\u000fM,8mY3tgR\u0011Ah\u0017\u0005\u00069\"\u0001\rAK\u0001\u0006m\u0006dW/Z\u0001\u000biJL8+^2dKN\u001cHCA\u001c`\u0011\u0015a\u0016\u00021\u0001+\u0003\u001d1\u0017-\u001b7ve\u0016$\"\u0001\u00102\t\u000b\rT\u0001\u0019\u00013\u0002\u000b\r\fWo]3\u0011\u0005\u0015lgB\u00014l\u001d\t9'.D\u0001i\u0015\tI\u0007$\u0001\u0004=e>|GOP\u0005\u0002/%\u0011ANF\u0001\ba\u0006\u001c7.Y4f\u0013\tqwNA\u0005UQJ|w/\u00192mK*\u0011ANF\u0001\u000biJLh)Y5mkJ,GCA\u001cs\u0011\u0015\u00197\u00021\u0001e\u0003\u001d\u0001&o\\7jg\u0016\u0004\"aJ\u0007\u0014\u00055Y\u0012A\u0002\u001fj]&$h\bF\u0001u\u0003\u0015\t\u0007\u000f\u001d7z+\tQX\u0010F\u0001|!\r9\u0003\u0001 \t\u0003Wu$Q!L\bC\u00029\naAZ1jY\u0016$W\u0003BA\u0001\u0003\u000f!B!a\u0001\u0002\nA!q\u0005AA\u0003!\rY\u0013q\u0001\u0003\u0006[A\u0011\rA\f\u0005\u0007\u0003\u0017\u0001\u0002\u0019\u00013\u0002\u0013\u0015D8-\u001a9uS>t\u0017AC:vG\u000e,7o\u001d4vYV!\u0011\u0011CA\f)\u0011\t\u0019\"!\u0007\u0011\t\u001d\u0002\u0011Q\u0003\t\u0004W\u0005]A!B\u0017\u0012\u0005\u0004q\u0003B\u0002 \u0012\u0001\u0004\t)\"A\u0004ge>lGK]=\u0016\t\u0005}\u0011Q\u0005\u000b\u0005\u0003C\t9\u0003\u0005\u0003(\u0001\u0005\r\u0002cA\u0016\u0002&\u0011)QF\u0005b\u0001]!1aH\u0005a\u0001\u0003S\u0001B\u0001Q\"\u0002$\u0001")
public interface Promise<T> {
    public static <T> Promise<T> fromTry(Try<T> result) {
        return new Promise.DefaultPromise<T>(result);
    }

    public static <T> Promise<T> successful(T result) {
        return Promise$.MODULE$.successful(result);
    }

    public static <T> Promise<T> failed(Throwable exception) {
        return Promise$.MODULE$.failed(exception);
    }

    public static <T> Promise<T> apply() {
        return new Promise.DefaultPromise();
    }

    public Future<T> future();

    public boolean isCompleted();

    public static /* synthetic */ Promise complete$(Promise $this, Try result) {
        return $this.complete(result);
    }

    default public Promise<T> complete(Try<T> result) {
        if (this.tryComplete(result)) {
            return this;
        }
        throw new IllegalStateException("Promise already completed.");
    }

    public boolean tryComplete(Try<T> var1);

    public static /* synthetic */ Promise completeWith$(Promise $this, Future other) {
        return $this.completeWith(other);
    }

    default public Promise<T> completeWith(Future<T> other) {
        if (other != this.future()) {
            other.onComplete((Function1<Try, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(this.tryComplete(x$1)), ExecutionContext$parasitic$.MODULE$);
        }
        return this;
    }

    public static /* synthetic */ Promise tryCompleteWith$(Promise $this, Future other) {
        return $this.tryCompleteWith(other);
    }

    default public Promise<T> tryCompleteWith(Future<T> other) {
        return this.completeWith(other);
    }

    public static /* synthetic */ Promise success$(Promise $this, Object value) {
        return $this.success(value);
    }

    default public Promise<T> success(T value) {
        return this.complete(new Success<T>(value));
    }

    public static /* synthetic */ boolean trySuccess$(Promise $this, Object value) {
        return $this.trySuccess(value);
    }

    default public boolean trySuccess(T value) {
        return this.tryComplete(new Success<T>(value));
    }

    public static /* synthetic */ Promise failure$(Promise $this, Throwable cause) {
        return $this.failure(cause);
    }

    default public Promise<T> failure(Throwable cause) {
        return this.complete(new Failure(cause));
    }

    public static /* synthetic */ boolean tryFailure$(Promise $this, Throwable cause) {
        return $this.tryFailure(cause);
    }

    default public boolean tryFailure(Throwable cause) {
        return this.tryComplete(new Failure(cause));
    }

    public static void $init$(Promise $this) {
    }
}

