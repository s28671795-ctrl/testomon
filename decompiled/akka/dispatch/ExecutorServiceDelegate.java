/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00055da\u0002\t\u0012!\u0003\r\tA\u0006\u0005\u0006O\u0001!\t\u0001\u000b\u0005\u0006_\u00011\t\u0001\r\u0005\u0006c\u0001!\tA\r\u0005\u0006q\u0001!\t\u0001\u000b\u0005\u0006s\u0001!\tA\u000f\u0005\u0006\u007f\u0001!\t\u0001\u0011\u0005\u0006\t\u0002!\t\u0001\u0011\u0005\u0006\u000b\u0002!\tA\u0012\u0005\u0006#\u0002!\tA\u0015\u0005\u0006#\u0002!\ta\u001a\u0005\u0006#\u0002!\t\u0001\u001d\u0005\u0006s\u0002!\tA\u001f\u0005\u0007s\u0002!\t!a\u0006\t\u000f\u0005]\u0002\u0001\"\u0001\u0002:!9\u0011q\u0007\u0001\u0005\u0002\u0005E#aF#yK\u000e,Ho\u001c:TKJ4\u0018nY3EK2,w-\u0019;f\u0015\t\u00112#\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005!\u0012\u0001B1lW\u0006\u001c\u0001aE\u0002\u0001/}\u0001\"\u0001G\u000f\u000e\u0003eQ!AG\u000e\u0002\t1\fgn\u001a\u0006\u00029\u0005!!.\u0019<b\u0013\tq\u0012D\u0001\u0004PE*,7\r\u001e\t\u0003A\u0015j\u0011!\t\u0006\u0003E\r\n!bY8oGV\u0014(/\u001a8u\u0015\t!3$\u0001\u0003vi&d\u0017B\u0001\u0014\"\u0005=)\u00050Z2vi>\u00148+\u001a:wS\u000e,\u0017A\u0002\u0013j]&$H\u0005F\u0001*!\tQS&D\u0001,\u0015\u0005a\u0013!B:dC2\f\u0017B\u0001\u0018,\u0005\u0011)f.\u001b;\u0002\u0011\u0015DXmY;u_J,\u0012aH\u0001\bKb,7-\u001e;f)\tI3\u0007C\u00035\u0007\u0001\u0007Q'A\u0004d_6l\u0017M\u001c3\u0011\u0005a1\u0014BA\u001c\u001a\u0005!\u0011VO\u001c8bE2,\u0017\u0001C:ikR$wn\u001e8\u0002\u0017MDW\u000f\u001e3po:tun\u001e\u000b\u0002wA\u0019A(P\u001b\u000e\u0003\rJ!AP\u0012\u0003\t1K7\u000f^\u0001\u000bSN\u001c\u0006.\u001e;e_^tG#A!\u0011\u0005)\u0012\u0015BA\",\u0005\u001d\u0011un\u001c7fC:\fA\"[:UKJl\u0017N\\1uK\u0012\f\u0001#Y<bSR$VM]7j]\u0006$\u0018n\u001c8\u0015\u0007\u0005;E\nC\u0003I\u0011\u0001\u0007\u0011*A\u0001m!\tQ#*\u0003\u0002LW\t!Aj\u001c8h\u0011\u0015i\u0005\u00021\u0001O\u0003!!\u0018.\\3V]&$\bC\u0001\u0011P\u0013\t\u0001\u0016E\u0001\u0005US6,WK\\5u\u0003\u0019\u0019XOY7jiV\u00111+\u0017\u000b\u0003)\n\u00042\u0001I+X\u0013\t1\u0016E\u0001\u0004GkR,(/\u001a\t\u00031fc\u0001\u0001B\u0003[\u0013\t\u00071LA\u0001U#\tav\f\u0005\u0002+;&\u0011al\u000b\u0002\b\u001d>$\b.\u001b8h!\tQ\u0003-\u0003\u0002bW\t\u0019\u0011I\\=\t\u000b\rL\u0001\u0019\u00013\u0002\u0011\r\fG\u000e\\1cY\u0016\u00042\u0001I3X\u0013\t1\u0017E\u0001\u0005DC2d\u0017M\u00197f+\tA7\u000eF\u0002jY:\u00042\u0001I+k!\tA6\u000eB\u0003[\u0015\t\u00071\fC\u0003n\u0015\u0001\u0007Q'\u0001\u0005sk:t\u0017M\u00197f\u0011\u0015y'\u00021\u0001k\u0003\u0005!HCA9ya\t\u0011H\u000fE\u0002!+N\u0004\"\u0001\u0017;\u0005\u0013U\\\u0011\u0011!A\u0001\u0006\u00031(AA 1#\tav\u000f\u0005\u0002\u0019;!)Qn\u0003a\u0001k\u0005I\u0011N\u001c<pW\u0016\fE\u000e\\\u000b\u0003w~$2\u0001`A\u0001!\raT( \t\u0004AUs\bC\u0001-\u0000\t\u0015QFB1\u0001\\\u0011\u001d\t\u0019\u0001\u0004a\u0001\u0003\u000b\t\u0011bY1mY\u0006\u0014G.Z:1\t\u0005\u001d\u0011q\u0002\t\u0006y\u0005%\u0011QB\u0005\u0004\u0003\u0017\u0019#AC\"pY2,7\r^5p]B\u0019\u0001,a\u0004\u0005\u0019\u0005E\u0011\u0011AA\u0001\u0002\u0003\u0015\t!a\u0005\u0003\u0007}#\u0013'E\u0002]\u0003+\u00012\u0001I3\u007f+\u0011\tI\"!\t\u0015\u0011\u0005m\u00111EA\u001a\u0003k\u0001B\u0001P\u001f\u0002\u001eA!\u0001%VA\u0010!\rA\u0016\u0011\u0005\u0003\u000656\u0011\ra\u0017\u0005\b\u0003\u0007i\u0001\u0019AA\u0013a\u0011\t9#a\u000b\u0011\u000bq\nI!!\u000b\u0011\u0007a\u000bY\u0003\u0002\u0007\u0002.\u0005\r\u0012\u0011!A\u0001\u0006\u0003\tyCA\u0002`II\n2\u0001XA\u0019!\u0011\u0001S-a\b\t\u000b!k\u0001\u0019A%\t\u000b5k\u0001\u0019\u0001(\u0002\u0013%tgo\\6f\u0003:LX\u0003BA\u001e\u0003\u007f!B!!\u0010\u0002BA\u0019\u0001,a\u0010\u0005\u000bis!\u0019A.\t\u000f\u0005\ra\u00021\u0001\u0002DA\"\u0011QIA%!\u0015a\u0014\u0011BA$!\rA\u0016\u0011\n\u0003\r\u0003\u0017\n\t%!A\u0001\u0002\u000b\u0005\u0011Q\n\u0002\u0004?\u0012\u001a\u0014c\u0001/\u0002PA!\u0001%ZA\u001f+\u0011\t\u0019&a\u0016\u0015\u0011\u0005U\u0013\u0011LA5\u0003W\u00022\u0001WA,\t\u0015QvB1\u0001\\\u0011\u001d\t\u0019a\u0004a\u0001\u00037\u0002D!!\u0018\u0002bA)A(!\u0003\u0002`A\u0019\u0001,!\u0019\u0005\u0019\u0005\r\u0014\u0011LA\u0001\u0002\u0003\u0015\t!!\u001a\u0003\u0007}#C'E\u0002]\u0003O\u0002B\u0001I3\u0002V!)\u0001j\u0004a\u0001\u0013\")Qj\u0004a\u0001\u001d\u0002")
public interface ExecutorServiceDelegate
extends ExecutorService {
    public ExecutorService executor();

    public static /* synthetic */ void execute$(ExecutorServiceDelegate $this, Runnable command) {
        $this.execute(command);
    }

    @Override
    default public void execute(Runnable command) {
        this.executor().execute(command);
    }

    public static /* synthetic */ void shutdown$(ExecutorServiceDelegate $this) {
        $this.shutdown();
    }

    @Override
    default public void shutdown() {
        this.executor().shutdown();
    }

    public static /* synthetic */ List shutdownNow$(ExecutorServiceDelegate $this) {
        return $this.shutdownNow();
    }

    @Override
    default public List<Runnable> shutdownNow() {
        return this.executor().shutdownNow();
    }

    public static /* synthetic */ boolean isShutdown$(ExecutorServiceDelegate $this) {
        return $this.isShutdown();
    }

    @Override
    default public boolean isShutdown() {
        return this.executor().isShutdown();
    }

    public static /* synthetic */ boolean isTerminated$(ExecutorServiceDelegate $this) {
        return $this.isTerminated();
    }

    @Override
    default public boolean isTerminated() {
        return this.executor().isTerminated();
    }

    public static /* synthetic */ boolean awaitTermination$(ExecutorServiceDelegate $this, long l, TimeUnit timeUnit) {
        return $this.awaitTermination(l, timeUnit);
    }

    @Override
    default public boolean awaitTermination(long l, TimeUnit timeUnit) {
        return this.executor().awaitTermination(l, timeUnit);
    }

    public static /* synthetic */ Future submit$(ExecutorServiceDelegate $this, Callable callable) {
        return $this.submit(callable);
    }

    @Override
    default public <T> Future<T> submit(Callable<T> callable) {
        return this.executor().submit(callable);
    }

    public static /* synthetic */ Future submit$(ExecutorServiceDelegate $this, Runnable runnable2, Object t2) {
        return $this.submit(runnable2, t2);
    }

    @Override
    default public <T> Future<T> submit(Runnable runnable2, T t2) {
        return this.executor().submit(runnable2, t2);
    }

    public static /* synthetic */ Future submit$(ExecutorServiceDelegate $this, Runnable runnable2) {
        return $this.submit(runnable2);
    }

    @Override
    default public Future<?> submit(Runnable runnable2) {
        return this.executor().submit(runnable2);
    }

    public static /* synthetic */ List invokeAll$(ExecutorServiceDelegate $this, Collection callables) {
        return $this.invokeAll(callables);
    }

    @Override
    default public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> callables) {
        return this.executor().invokeAll(callables);
    }

    public static /* synthetic */ List invokeAll$(ExecutorServiceDelegate $this, Collection callables, long l, TimeUnit timeUnit) {
        return $this.invokeAll(callables, l, timeUnit);
    }

    @Override
    default public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> callables, long l, TimeUnit timeUnit) {
        return this.executor().invokeAll(callables, l, timeUnit);
    }

    public static /* synthetic */ Object invokeAny$(ExecutorServiceDelegate $this, Collection callables) {
        return $this.invokeAny(callables);
    }

    @Override
    default public <T> T invokeAny(Collection<? extends Callable<T>> callables) {
        return this.executor().invokeAny(callables);
    }

    public static /* synthetic */ Object invokeAny$(ExecutorServiceDelegate $this, Collection callables, long l, TimeUnit timeUnit) {
        return $this.invokeAny(callables, l, timeUnit);
    }

    @Override
    default public <T> T invokeAny(Collection<? extends Callable<T>> callables, long l, TimeUnit timeUnit) {
        return this.executor().invokeAny(callables, l, timeUnit);
    }

    public static void $init$(ExecutorServiceDelegate $this) {
    }
}

