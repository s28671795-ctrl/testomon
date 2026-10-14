/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.concurrent;

import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001daaB\u0007\u000f!\u0003\r\t!\u0006\u0005\u0006]\u0001!\ta\f\u0005\u0006g\u00011\t\u0001\u000e\u0005\u0006y\u00011\t!\u0010\u0005\u0006\u0007\u00021\t\u0001\u0012\u0005\u0006\u0007\u00021\tA\u0013\u0005\u0006\u001b\u0002!\tE\u0014\u0005\u0007-\u0002!\t\u0001E,\t\ri\u0003A\u0011\u0001\t\\\u0011\u0015\t\u0007\u0001\"\u0011c\u0011\u0015Q\u0007\u0001\"\u0003l\u0011\u00191\b\u0001\"\u0001\u0011o\"1a\u0010\u0001C\u0001!}\u00141!T1q\u0015\ty\u0001#\u0001\u0006d_:\u001cWO\u001d:f]RT!!\u0005\n\u0002\u0015\r|G\u000e\\3di&|gNC\u0001\u0014\u0003\u0015\u00198-\u00197b\u0007\u0001)2A\u0006\u0012-'\r\u0001qc\u0007\t\u00031ei\u0011AE\u0005\u00035I\u0011a!\u00118z%\u00164\u0007\u0003\u0002\u000f A-j\u0011!\b\u0006\u0003=A\tq!\\;uC\ndW-\u0003\u0002\u000e;A\u0011\u0011E\t\u0007\u0001\t\u0015\u0019\u0003A1\u0001%\u0005\u0005Y\u0015CA\u0013)!\tAb%\u0003\u0002(%\t9aj\u001c;iS:<\u0007C\u0001\r*\u0013\tQ#CA\u0002B]f\u0004\"!\t\u0017\u0005\u000b5\u0002!\u0019\u0001\u0013\u0003\u0003Y\u000ba\u0001J5oSR$C#\u0001\u0019\u0011\u0005a\t\u0014B\u0001\u001a\u0013\u0005\u0011)f.\u001b;\u0002\u0017A,H/\u00134BEN,g\u000e\u001e\u000b\u0004kaR\u0004c\u0001\r7W%\u0011qG\u0005\u0002\u0007\u001fB$\u0018n\u001c8\t\u000be\u0012\u0001\u0019\u0001\u0011\u0002\u0003-DQa\u000f\u0002A\u0002-\n\u0011A^\u0001\u0007e\u0016lwN^3\u0015\u0007y\n%\t\u0005\u0002\u0019\u007f%\u0011\u0001I\u0005\u0002\b\u0005>|G.Z1o\u0011\u0015I4\u00011\u0001!\u0011\u0015Y4\u00011\u0001,\u0003\u001d\u0011X\r\u001d7bG\u0016$BAP#G\u0011\")\u0011\b\u0002a\u0001A!)q\t\u0002a\u0001W\u0005Aq\u000e\u001c3wC2,X\rC\u0003J\t\u0001\u00071&\u0001\u0005oK^4\u0018\r\\;f)\r)4\n\u0014\u0005\u0006s\u0015\u0001\r\u0001\t\u0005\u0006w\u0015\u0001\raK\u0001\u0010O\u0016$xJ]#mg\u0016,\u0006\u000fZ1uKR\u00191fT)\t\u000bA3\u0001\u0019\u0001\u0011\u0002\u0007-,\u0017\u0010\u0003\u0004S\r\u0011\u0005\raU\u0001\u0003_B\u00042\u0001\u0007+,\u0013\t)&C\u0001\u0005=Eft\u0017-\\3?\u0003-\u0011X-\\8wKJ+g-R9\u0015\u0007yB\u0016\fC\u0003:\u000f\u0001\u0007\u0001\u0005C\u0003<\u000f\u0001\u00071&\u0001\u0007sKBd\u0017mY3SK\u001a,\u0015\u000f\u0006\u0003?9v{\u0006\"B\u001d\t\u0001\u0004\u0001\u0003\"\u00020\t\u0001\u0004Y\u0013\u0001C8mIZ\u000bG.^3\t\u000b\u0001D\u0001\u0019A\u0016\u0002\u00119,wOV1mk\u0016\f!\"\u001e9eCR,w+\u001b;i)\t\u0019\u0017\u000e\u0006\u00026I\")Q-\u0003a\u0001M\u0006\t\"/Z7baBLgn\u001a$v]\u000e$\u0018n\u001c8\u0011\ta9W'N\u0005\u0003QJ\u0011\u0011BR;oGRLwN\\\u0019\t\u000bAK\u0001\u0019\u0001\u0011\u0002\u001bU\u0004H-\u0019;f/&$\b.Q;y)\tag\u000e\u0006\u00026[\")QM\u0003a\u0001M\")\u0001K\u0003a\u0001A!\u0012!\u0002\u001d\t\u0003cRl\u0011A\u001d\u0006\u0003gJ\t!\"\u00198o_R\fG/[8o\u0013\t)(OA\u0004uC&d'/Z2\u0002#\u0019LG\u000e^3s\u0013:\u0004F.Y2f\u00136\u0004H\u000e\u0006\u0002ys6\t\u0001\u0001C\u0003{\u0017\u0001\u000710A\u0001q!\u0015AB\u0010I\u0016?\u0013\ti(CA\u0005Gk:\u001cG/[8oe\u0005!R.\u00199WC2,Xm]%o!2\f7-Z%na2$2\u0001_A\u0001\u0011\u001d\t\u0019\u0001\u0004a\u0001\u0003\u000b\t\u0011A\u001a\t\u00061q\u00043f\u000b")
public interface Map<K, V>
extends scala.collection.mutable.Map<K, V> {
    public Option<V> putIfAbsent(K var1, V var2);

    public boolean remove(K var1, V var2);

    public boolean replace(K var1, V var2, V var3);

    public Option<V> replace(K var1, V var2);

    public static /* synthetic */ Object getOrElseUpdate$(Map $this, Object key, Function0 op) {
        return $this.getOrElseUpdate(key, op);
    }

    @Override
    default public V getOrElseUpdate(K key, Function0<V> op) {
        Option option = this.get(key);
        if (option instanceof Some) {
            return (V)((Some)option).value();
        }
        if (None$.MODULE$.equals(option)) {
            V v = op.apply();
            Option<V> option2 = this.putIfAbsent(key, v);
            if (option2 instanceof Some) {
                return (V)((Some)option2).value();
            }
            if (None$.MODULE$.equals(option2)) {
                return v;
            }
            throw new MatchError(option2);
        }
        throw new MatchError(option);
    }

    public static /* synthetic */ boolean removeRefEq$(Map $this, Object k, Object v) {
        return $this.removeRefEq(k, v);
    }

    default public boolean removeRefEq(K k, V v) {
        return this.remove(k, v);
    }

    public static /* synthetic */ boolean replaceRefEq$(Map $this, Object k, Object oldValue, Object newValue) {
        return $this.replaceRefEq(k, oldValue, newValue);
    }

    default public boolean replaceRefEq(K k, V oldValue, V newValue) {
        return this.replace(k, oldValue, newValue);
    }

    public static /* synthetic */ Option updateWith$(Map $this, Object key, Function1 remappingFunction) {
        return $this.updateWith(key, remappingFunction);
    }

    @Override
    default public Option<V> updateWith(K key, Function1<Option<V>, Option<V>> remappingFunction) {
        block3: {
            Option<V> updateWithAux_nextValue;
            while (true) {
                Option updateWithAux_previousValue = this.get(key);
                updateWithAux_nextValue = remappingFunction.apply(updateWithAux_previousValue);
                if (updateWithAux_previousValue instanceof Some) {
                    Object updateWithAux_prev = ((Some)updateWithAux_previousValue).value();
                    if (updateWithAux_nextValue instanceof Some) {
                        Object updateWithAux_next = ((Some)updateWithAux_nextValue).value();
                        if (!this.replaceRefEq(key, updateWithAux_prev, updateWithAux_next)) continue;
                        return updateWithAux_nextValue;
                    }
                    if (!this.removeRefEq(key, updateWithAux_prev)) continue;
                    return None$.MODULE$;
                }
                if (!(updateWithAux_nextValue instanceof Some)) break block3;
                Object updateWithAux_next = ((Some)updateWithAux_nextValue).value();
                if (this.putIfAbsent(key, updateWithAux_next).isEmpty()) break;
            }
            return updateWithAux_nextValue;
        }
        return None$.MODULE$;
    }

    private Option<V> updateWithAux(K key, Function1<Option<V>, Option<V>> remappingFunction) {
        while (true) {
            Option previousValue = this.get(key);
            Option<V> nextValue = remappingFunction.apply(previousValue);
            if (previousValue instanceof Some) {
                Object prev = ((Some)previousValue).value();
                if (nextValue instanceof Some) {
                    Object next = ((Some)nextValue).value();
                    if (!this.replaceRefEq(key, prev, next)) continue;
                    return nextValue;
                }
                if (!this.removeRefEq(key, prev)) continue;
                return None$.MODULE$;
            }
            if (nextValue instanceof Some) {
                Object next = ((Some)nextValue).value();
                if (!this.putIfAbsent(key, next).isEmpty()) continue;
                return nextValue;
            }
            return None$.MODULE$;
        }
    }

    public static /* synthetic */ Map filterInPlaceImpl$(Map $this, Function2 p) {
        return $this.filterInPlaceImpl(p);
    }

    /*
     * WARNING - void declaration
     */
    default public Map<K, V> filterInPlaceImpl(Function2<K, V, Object> p) {
        Iterator it = this.iterator();
        while (it.hasNext()) {
            void var5_5;
            void var4_4;
            Tuple2 tuple2 = (Tuple2)it.next();
            if (tuple2 == null) {
                throw new MatchError((Object)null);
            }
            Object k = tuple2._1();
            Object v = tuple2._2();
            if (BoxesRunTime.unboxToBoolean(p.apply(var4_4, var5_5))) continue;
            this.removeRefEq(var4_4, var5_5);
        }
        return this;
    }

    public static /* synthetic */ Map mapValuesInPlaceImpl$(Map $this, Function2 f) {
        return $this.mapValuesInPlaceImpl(f);
    }

    /*
     * WARNING - void declaration
     */
    default public Map<K, V> mapValuesInPlaceImpl(Function2<K, V, V> f) {
        Iterator it = this.iterator();
        while (it.hasNext()) {
            void var5_5;
            void var4_4;
            Tuple2 tuple2 = (Tuple2)it.next();
            if (tuple2 == null) {
                throw new MatchError((Object)null);
            }
            Object k = tuple2._1();
            Object v = tuple2._2();
            this.replaceRefEq(var4_4, var5_5, f.apply(var4_4, var5_5));
        }
        return this;
    }

    public static void $init$(Map $this) {
    }
}

