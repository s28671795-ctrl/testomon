/*
 * Decompiled with CFR 0.152.
 */
package scala.sys;

import java.io.Serializable;
import java.util.NoSuchElementException;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.collection.IterableOnce;
import scala.collection.convert.AsScalaExtensions;
import scala.collection.immutable.ArraySeq$;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.Map;
import scala.jdk.CollectionConverters$;
import scala.runtime.BoxedUnit;
import scala.runtime.Nothing$;
import scala.sys.ShutdownHookThread;
import scala.sys.ShutdownHookThread$;
import scala.sys.SystemProperties;

public final class package$ {
    public static final package$ MODULE$ = new package$();

    public Nothing$ error(String message) {
        throw new RuntimeException(message);
    }

    public Nothing$ exit() {
        return this.exit(0);
    }

    public Nothing$ exit(int status) {
        System.exit(status);
        throw new Throwable();
    }

    public Runtime runtime() {
        return Runtime.getRuntime();
    }

    public SystemProperties props() {
        return new SystemProperties();
    }

    public Map<String, String> env() {
        java.util.Map<String, String> MapHasAsScala_m = System.getenv();
        CollectionConverters$ MapHasAsScala_this = CollectionConverters$.MODULE$;
        Object var1_2 = null;
        Object var2_1 = null;
        return Predef$.MODULE$.Map().from((IterableOnce)new AsScalaExtensions.MapHasAsScala<String, String>(MapHasAsScala_this, MapHasAsScala_m).asScala()).withDefault((Function1<String, String> & Serializable)v -> {
            String s2 = System.getenv(v);
            if (s2 == null) {
                throw new NoSuchElementException((String)v);
            }
            return s2;
        });
    }

    public ShutdownHookThread addShutdownHook(Function0<BoxedUnit> body2) {
        return ShutdownHookThread$.MODULE$.apply(body2);
    }

    public IndexedSeq<Thread> allThreads() {
        Thread[] tarray = new Thread[Thread.activeCount()];
        int got = Thread.enumerate(tarray);
        return ArraySeq$.MODULE$.unsafeWrapArray(tarray).take(got);
    }

    private package$() {
    }
}

