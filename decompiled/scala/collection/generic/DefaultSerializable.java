/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.generic;

import java.io.Serializable;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.Map;
import scala.collection.SortedMap;
import scala.collection.SortedOps;
import scala.collection.SortedSet;
import scala.collection.generic.DefaultSerializationProxy;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a2\u0001b\u0001\u0003\u0011\u0002\u0007\u00051b\t\u0005\u00069\u0001!\t!\b\u0005\u0007C\u0001\u0001K\u0011\u0003\u0012\u0003'\u0011+g-Y;miN+'/[1mSj\f'\r\\3\u000b\u0005\u00151\u0011aB4f]\u0016\u0014\u0018n\u0019\u0006\u0003\u000f!\t!bY8mY\u0016\u001cG/[8o\u0015\u0005I\u0011!B:dC2\f7\u0001A\n\u0004\u00011\u0001\u0002CA\u0007\u000f\u001b\u0005A\u0011BA\b\t\u0005\u0019\te.\u001f*fMB\u0011\u0011#\u0007\b\u0003%]q!a\u0005\f\u000e\u0003QQ!!\u0006\u0006\u0002\rq\u0012xn\u001c;?\u0013\u0005I\u0011B\u0001\r\t\u0003\u001d\u0001\u0018mY6bO\u0016L!AG\u000e\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005aA\u0011A\u0002\u0013j]&$H\u0005F\u0001\u001f!\tiq$\u0003\u0002!\u0011\t!QK\\5u\u000319(/\u001b;f%\u0016\u0004H.Y2f)\u0005a!c\u0001\u0013'Q\u0019!Q\u0005\u0001\u0001$\u00051a$/\u001a4j]\u0016lWM\u001c;?!\t9\u0003!D\u0001\u0005a\tIs\u0006E\u0002+W5j\u0011AB\u0005\u0003Y\u0019\u0011\u0001\"\u0013;fe\u0006\u0014G.\u001a\t\u0003]=b\u0001\u0001B\u00051\u0001\u0005\u0005\t\u0011!B\u0001c\t\u0019q\fJ\u0019\u0012\u0005I*\u0004CA\u00074\u0013\t!\u0004BA\u0004O_RD\u0017N\\4\u0011\u000551\u0014BA\u001c\t\u0005\r\te.\u001f")
public interface DefaultSerializable
extends Serializable {
    public static /* synthetic */ Object writeReplace$(DefaultSerializable $this) {
        return $this.writeReplace();
    }

    default public Object writeReplace() {
        Factory f = this instanceof SortedMap ? ((SortedMap)((Object)this)).sortedMapFactory().sortedMapFactory(((SortedOps)((Object)this)).ordering()) : (this instanceof Map ? ((Map)((Object)this)).mapFactory().mapFactory() : (this instanceof SortedSet ? ((SortedSet)((Object)this)).sortedIterableFactory().evidenceIterableFactory(((SortedOps)((Object)this)).ordering()) : ((Iterable)((Object)this)).iterableFactory().iterableFactory()));
        return new DefaultSerializationProxy(f, (Iterable)((Object)this));
    }

    public static void $init$(DefaultSerializable $this) {
    }
}

