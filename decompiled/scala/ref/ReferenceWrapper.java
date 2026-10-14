/*
 * Decompiled with CFR 0.152.
 */
package scala.ref;

import java.util.NoSuchElementException;
import scala.Option;
import scala.Option$;
import scala.Proxy;
import scala.ref.Reference;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000593q!\u0003\u0006\u0011\u0002\u0007\u0005q\u0002C\u0003%\u0001\u0011\u0005Q\u0005C\u0004*\u0001\t\u0007i\u0011\u0001\u0016\t\u000ba\u0002A\u0011I\u001d\t\u000bu\u0002A\u0011\u0001 \t\u000b}\u0002A\u0011A\u0013\t\u000b\u0001\u0003A\u0011A!\t\u000b\u0015\u0003A\u0011\u0001$\t\u000b\u001d\u0003A\u0011\u0001%\u0003!I+g-\u001a:f]\u000e,wK]1qa\u0016\u0014(BA\u0006\r\u0003\r\u0011XM\u001a\u0006\u0002\u001b\u0005)1oY1mC\u000e\u0001QC\u0001\t\u001c'\u0011\u0001\u0011#F\u0011\u0011\u0005I\u0019R\"\u0001\u0007\n\u0005Qa!AB!osJ+g\rE\u0002\u0017/ei\u0011AC\u0005\u00031)\u0011\u0011BU3gKJ,gnY3\u0011\u0005iYB\u0002\u0001\u0003\u00079\u0001!)\u0019A\u000f\u0003\u0003Q\u000b\"AH\t\u0011\u0005Iy\u0012B\u0001\u0011\r\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0005\u0012\n\u0005\rb!!\u0002)s_bL\u0018A\u0002\u0013j]&$H\u0005F\u0001'!\t\u0011r%\u0003\u0002)\u0019\t!QK\\5u\u0003))h\u000eZ3sYfLgnZ\u000b\u0002WA\u0012A&\u000e\t\u0004[M\"T\"\u0001\u0018\u000b\u0005-y#B\u0001\u00192\u0003\u0011a\u0017M\\4\u000b\u0003I\nAA[1wC&\u0011\u0001D\f\t\u00035U\"\u0011B\u000e\u0002\u0002\u0002\u0003\u0005)\u0011A\u001c\u0003\u0007}#\u0013'\u0005\u0002\u001f3\u0005\u0019q-\u001a;\u0016\u0003i\u00022AE\u001e\u001a\u0013\taDB\u0001\u0004PaRLwN\\\u0001\u0006CB\u0004H.\u001f\u000b\u00023\u0005)1\r\\3be\u00069QM\\9vKV,G#\u0001\"\u0011\u0005I\u0019\u0015B\u0001#\r\u0005\u001d\u0011un\u001c7fC:\f!\"[:F]F,X-^3e+\u0005\u0011\u0015\u0001B:fY\u001a,\u0012!\u0013\u0019\u0003\u00152\u00032!L\u001aL!\tQB\nB\u0005N\u0011\u0005\u0005\t\u0011!B\u0001o\t\u0019q\f\n\u001a")
public interface ReferenceWrapper<T>
extends Reference<T>,
Proxy {
    public java.lang.ref.Reference<? extends T> underlying();

    public static /* synthetic */ Option get$(ReferenceWrapper $this) {
        return $this.get();
    }

    @Override
    default public Option<T> get() {
        return Option$.MODULE$.apply(this.underlying().get());
    }

    public static /* synthetic */ Object apply$(ReferenceWrapper $this) {
        return $this.apply();
    }

    @Override
    default public T apply() {
        T ret = this.underlying().get();
        if (ret == null) {
            throw new NoSuchElementException();
        }
        return ret;
    }

    public static /* synthetic */ void clear$(ReferenceWrapper $this) {
        $this.clear();
    }

    @Override
    default public void clear() {
        this.underlying().clear();
    }

    public static /* synthetic */ boolean enqueue$(ReferenceWrapper $this) {
        return $this.enqueue();
    }

    @Override
    default public boolean enqueue() {
        return this.underlying().enqueue();
    }

    public static /* synthetic */ boolean isEnqueued$(ReferenceWrapper $this) {
        return $this.isEnqueued();
    }

    @Override
    default public boolean isEnqueued() {
        return this.underlying().isEnqueued();
    }

    public static /* synthetic */ java.lang.ref.Reference self$(ReferenceWrapper $this) {
        return $this.self();
    }

    @Override
    default public java.lang.ref.Reference<? extends T> self() {
        return this.underlying();
    }

    public static void $init$(ReferenceWrapper $this) {
    }
}

