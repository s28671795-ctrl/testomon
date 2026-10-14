/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.TypedMultiMap$;
import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0005eb\u0001B\u000b\u0017\u0001mA\u0001b\t\u0001\u0003\u0006\u0004%I\u0001\n\u0005\t}\u0001\u0011\t\u0011)A\u0005K!)q\b\u0001C\u0005\u0001\")A\n\u0001C\u0001\u001b\")q\n\u0001C\u0001!\")\u0001\f\u0001C\u00013\")a\f\u0001C\u0001?\")\u0011\r\u0001C\u0001E\")A\r\u0001C\u0001K\")1\u000e\u0001C\u0001Y\")A\u000f\u0001C\u0001k\")\u0001\u0010\u0001C!s\")Q\u0010\u0001C!}\"9\u0011q\u0001\u0001\u0005B\u0005%qaBA\t-!\u0005\u00111\u0003\u0004\u0007+YA\t!!\u0006\t\r}\u0002B\u0011AA\f\u0011%\tI\u0002\u0005b\u0001\n\u0013\tY\u0002\u0003\u0005\u0002 A\u0001\u000b\u0011BA\u000f\u0011\u001d\t\t\u0003\u0005C\u0001\u0003G\u0011Q\u0002V=qK\u0012lU\u000f\u001c;j\u001b\u0006\u0004(BA\f\u0019\u0003\u0011)H/\u001b7\u000b\u0003e\tA!Y6lC\u000e\u0001Qc\u0001\u000f3\tN\u0011\u0001!\b\t\u0003=\u0005j\u0011a\b\u0006\u0002A\u0005)1oY1mC&\u0011!e\b\u0002\u0007\u0003:L(+\u001a4\u0002\u00075\f\u0007/F\u0001&!\u00111S\u0006\r\u001d\u000f\u0005\u001dZ\u0003C\u0001\u0015 \u001b\u0005I#B\u0001\u0016\u001b\u0003\u0019a$o\\8u}%\u0011AfH\u0001\u0007!J,G-\u001a4\n\u00059z#aA'ba*\u0011Af\b\t\u0003cIb\u0001\u0001B\u00034\u0001\t\u0007AGA\u0001U#\t)T\u0004\u0005\u0002\u001fm%\u0011qg\b\u0002\b\u001d>$\b.\u001b8h!\r1\u0013hO\u0005\u0003u=\u00121aU3u!\tqB(\u0003\u0002>?\t\u0019\u0011I\\=\u0002\t5\f\u0007\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\u0005[\u0005\u0003\u0002\"\u0001a\rk\u0011A\u0006\t\u0003c\u0011#Q!\u0012\u0001C\u0002\u0019\u0013\u0011aS\u000b\u0003\u000f\"\u000b\"!N\u001e\u0005\u000b%#%\u0019\u0001&\u0003\t}#C%M\t\u0003kABQaI\u0002A\u0002\u0015\naa[3z'\u0016$X#\u0001(\u0011\u0007\u0019J\u0004'\u0001\u0005j]N,'\u000f^3e)\t\tf\u000b\u0006\u0002B%\")1+\u0002a\u0001)\u0006)a/\u00197vKB\u0019\u0011\u0007R+\u000f\u0005E2\u0006\"B,\u0006\u0001\u0004\u0001\u0014aA6fs\u0006\u0019q-\u001a;\u0015\u0005ik\u0006c\u0001\u0014:7B\u0019\u0011\u0007\u0012/\u000f\u0005Ej\u0006\"B,\u0007\u0001\u0004\u0001\u0014\u0001\u0004<bYV,'+Z7pm\u0016$GCA!a\u0011\u0015\u0019v\u00011\u0001<\u0003)YW-\u001f*f[>4X\r\u001a\u000b\u0003\u0003\u000eDQa\u0016\u0005A\u0002A\nqA]3n_Z,G\r\u0006\u0002gUR\u0011\u0011i\u001a\u0005\u0006'&\u0001\r\u0001\u001b\t\u0004c\u0011KgBA\u0019k\u0011\u00159\u0016\u00021\u00011\u0003\u0019\u0019X\r^!mYR\u0011Qn\u001d\u000b\u0003\u0003:DQa\u001c\u0006A\u0002A\faA^1mk\u0016\u001c\bc\u0001\u0014:cB\u0019\u0011\u0007\u0012:\u000f\u0005E\u001a\b\"B,\u000b\u0001\u0004\u0001\u0014A\u0003\u0013qYV\u001cH\u0005\u001d7vgR\u0011\u0011I\u001e\u0005\u0006o.\u0001\r!Q\u0001\u0006_RDWM]\u0001\ti>\u001cFO]5oOR\t!\u0010\u0005\u0002'w&\u0011Ap\f\u0002\u0007'R\u0014\u0018N\\4\u0002\r\u0015\fX/\u00197t)\ry\u0018Q\u0001\t\u0004=\u0005\u0005\u0011bAA\u0002?\t9!i\\8mK\u0006t\u0007\"B<\u000e\u0001\u0004Y\u0014\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005-\u0001c\u0001\u0010\u0002\u000e%\u0019\u0011qB\u0010\u0003\u0007%sG/A\u0007UsB,G-T;mi&l\u0015\r\u001d\t\u0003\u0005B\u0019\"\u0001E\u000f\u0015\u0005\u0005M\u0011AB0f[B$\u00180\u0006\u0002\u0002\u001eA!!\tA\u001b6\u0003\u001dyV-\u001c9us\u0002\nQ!Z7qif,b!!\n\u0002,\u0005=RCAA\u0014!\u0019\u0011\u0005!!\u000b\u0002.A\u0019\u0011'a\u000b\u0005\u000bM\"\"\u0019\u0001\u001b\u0011\u0007E\ny\u0003\u0002\u0004F)\t\u0007\u0011\u0011G\u000b\u0004\u000f\u0006MB\u0001CA\u001b\u0003_\u0011\r!a\u000e\u0003\t}#CEM\t\u0004k\u0005%\u0002")
public class TypedMultiMap<T, K> {
    private final Map<T, Set<Object>> map;

    public static <T, K> TypedMultiMap<T, K> empty() {
        return TypedMultiMap$.MODULE$.empty();
    }

    private Map<T, Set<Object>> map() {
        return this.map;
    }

    public Set<T> keySet() {
        return this.map().keySet();
    }

    public TypedMultiMap<T, K> inserted(T key, K value) {
        Object object;
        K k = value;
        Option option = this.map().get(key);
        if (option instanceof Some) {
            Some some = (Some)option;
            Set s2 = (Set)some.value();
            object = s2;
        } else if (None$.MODULE$.equals(option)) {
            object = Predef$.MODULE$.Set().empty();
        } else {
            throw new MatchError(option);
        }
        Object set = object;
        return new TypedMultiMap<T, K>((Map)this.map().updated(key, set.$plus(k)));
    }

    public Set<K> get(T key) {
        Option option = this.map().get(key);
        if (option instanceof Some) {
            Some some = (Some)option;
            Set s2 = (Set)some.value();
            return s2;
        }
        if (None$.MODULE$.equals(option)) {
            return Predef$.MODULE$.Set().empty();
        }
        throw new MatchError(option);
    }

    public TypedMultiMap<T, K> valueRemoved(Object value) {
        Set s2 = (Set)Predef$.MODULE$.Set().apply((Seq)ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{value}));
        Map m4 = (Map)this.map().collect(new Serializable(null, s2, value){
            private static final long serialVersionUID = 0L;
            private final Set s$1;
            private final Object value$3;

            public final <A1 extends Tuple2<T, Set<Object>>, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 != null) {
                    Set<Object> set;
                    T k = A1._1();
                    Set<Object> set2 = set = A1._2();
                    Set set3 = this.s$1;
                    if (set2 == null ? set3 != null : !set2.equals(set3)) {
                        return (B1)new Tuple2<T, C>(k, set.$minus(this.value$3));
                    }
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Tuple2<T, Set<Object>> x1) {
                Tuple2<T, Set<Object>> tuple2 = x1;
                if (tuple2 != null) {
                    Set<Object> set;
                    Set<Object> set2 = set = tuple2._2();
                    Set set3 = this.s$1;
                    if (set2 == null ? set3 != null : !set2.equals(set3)) {
                        return true;
                    }
                }
                return false;
            }
            {
                this.s$1 = s$1;
                this.value$3 = value$3;
            }
        });
        return new TypedMultiMap<T, K>(m4);
    }

    public TypedMultiMap<T, K> keyRemoved(T key) {
        return new TypedMultiMap<T, K>((Map)this.map().$minus(key));
    }

    public TypedMultiMap<T, K> removed(T key, K value) {
        K k = value;
        Option option = this.map().get(key);
        if (None$.MODULE$.equals(option)) {
            return this;
        }
        if (option instanceof Some) {
            Some some = (Some)option;
            Set set = (Set)some.value();
            if (set.apply(k)) {
                Set newset = (Set)set.$minus(k);
                Map newmap = newset.isEmpty() ? (Map)this.map().$minus(key) : (Map)this.map().updated(key, newset);
                return new TypedMultiMap<T, K>(newmap);
            }
            return this;
        }
        throw new MatchError(option);
    }

    public TypedMultiMap<T, K> setAll(T key, Set<K> values2) {
        Set<K> set = values2;
        return new TypedMultiMap<T, K>((Map)this.map().updated(key, set));
    }

    public TypedMultiMap<T, K> $plus$plus(TypedMultiMap<T, K> other) {
        return new TypedMultiMap<T, K>((Map)this.map().$plus$plus(super.map()));
    }

    public String toString() {
        return new StringBuilder(15).append("TypedMultiMap(").append(this.map()).append(")").toString();
    }

    public boolean equals(Object other) {
        Object object = other;
        if (object instanceof TypedMultiMap) {
            TypedMultiMap typedMultiMap = (TypedMultiMap)object;
            Map<T, Set<Object>> map = this.map();
            Map<T, Set<Object>> map2 = typedMultiMap.map();
            return !(map != null ? !map.equals(map2) : map2 != null);
        }
        return false;
    }

    public int hashCode() {
        return this.map().hashCode();
    }

    public TypedMultiMap(Map<T, Set<Object>> map) {
        this.map = map;
    }
}

