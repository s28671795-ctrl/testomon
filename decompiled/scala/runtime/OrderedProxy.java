/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Proxy;
import scala.math.Ordered;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005]2q\u0001B\u0003\u0011\u0002\u0007\u0005!\u0002C\u0003'\u0001\u0011\u0005q\u0005C\u0003,\u0001\u0019EA\u0006C\u00031\u0001\u0011\u0005\u0011G\u0001\u0007Pe\u0012,'/\u001a3Qe>D\u0018P\u0003\u0002\u0007\u000f\u00059!/\u001e8uS6,'\"\u0001\u0005\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u00111\"G\n\u0005\u00011\u0001r\u0004\u0005\u0002\u000e\u001d5\tq!\u0003\u0002\u0010\u000f\t\u0019\u0011I\\=\u0011\u0007E!rC\u0004\u0002\u000e%%\u00111cB\u0001\ba\u0006\u001c7.Y4f\u0013\t)bCA\u0004Pe\u0012,'/\u001a3\u000b\u0005M9\u0001C\u0001\r\u001a\u0019\u0001!QA\u0007\u0001C\u0002m\u0011\u0011\u0001V\t\u000391\u0001\"!D\u000f\n\u0005y9!a\u0002(pi\"Lgn\u001a\t\u0004A\r:bBA\u0007\"\u0013\t\u0011s!A\u0003Qe>D\u00180\u0003\u0002%K\t)A+\u001f9fI*\u0011!eB\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003!\u0002\"!D\u0015\n\u0005):!\u0001B+oSR\f1a\u001c:e+\u0005i\u0003cA\t//%\u0011qF\u0006\u0002\t\u001fJ$WM]5oO\u000691m\\7qCJ,GC\u0001\u001a6!\ti1'\u0003\u00025\u000f\t\u0019\u0011J\u001c;\t\u000bY\u001a\u0001\u0019A\f\u0002\u0003e\u0004")
public interface OrderedProxy<T>
extends Ordered<T>,
Proxy.Typed<T> {
    public Ordering<T> ord();

    public static /* synthetic */ int compare$(OrderedProxy $this, Object y) {
        return $this.compare((T)y);
    }

    @Override
    default public int compare(T y) {
        return this.ord().compare(this.self(), y);
    }

    public static void $init$(OrderedProxy $this) {
    }
}

