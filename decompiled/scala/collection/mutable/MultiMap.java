/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.mutable.HashSet;
import scala.collection.mutable.Map;
import scala.collection.mutable.Set;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A3qAB\u0004\u0011\u0002\u0007\u0005a\u0002C\u0003*\u0001\u0011\u0005!\u0006C\u0003/\u0001\u0011Eq\u0006C\u00031\u0001\u0011\u0005\u0011\u0007C\u00038\u0001\u0011\u0005\u0001\bC\u0003<\u0001\u0011\u0005AH\u0001\u0005Nk2$\u0018.T1q\u0015\tA\u0011\"A\u0004nkR\f'\r\\3\u000b\u0005)Y\u0011AC2pY2,7\r^5p]*\tA\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0007=QreE\u0002\u0001!Q\u0001\"!\u0005\n\u000e\u0003-I!aE\u0006\u0003\r\u0005s\u0017PU3g!\u0011)b\u0003G\u0012\u000e\u0003\u001dI!aF\u0004\u0003\u00075\u000b\u0007\u000f\u0005\u0002\u001a51\u0001A!B\u000e\u0001\u0005\u0004a\"!A&\u0012\u0005u\u0001\u0003CA\t\u001f\u0013\ty2BA\u0004O_RD\u0017N\\4\u0011\u0005E\t\u0013B\u0001\u0012\f\u0005\r\te.\u001f\t\u0004+\u00112\u0013BA\u0013\b\u0005\r\u0019V\r\u001e\t\u00033\u001d\"Q\u0001\u000b\u0001C\u0002q\u0011\u0011AV\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003-\u0002\"!\u0005\u0017\n\u00055Z!\u0001B+oSR\fq!\\1lKN+G/F\u0001$\u0003)\tG\r\u001a\"j]\u0012Lgn\u001a\u000b\u0004eM*T\"\u0001\u0001\t\u000bQ\u001a\u0001\u0019\u0001\r\u0002\u0007-,\u0017\u0010C\u00037\u0007\u0001\u0007a%A\u0003wC2,X-A\u0007sK6|g/\u001a\"j]\u0012Lgn\u001a\u000b\u0004eeR\u0004\"\u0002\u001b\u0005\u0001\u0004A\u0002\"\u0002\u001c\u0005\u0001\u00041\u0013aC3oiJLX\t_5tiN$2!\u0010!B!\t\tb(\u0003\u0002@\u0017\t9!i\\8mK\u0006t\u0007\"\u0002\u001b\u0006\u0001\u0004A\u0002\"\u0002\"\u0006\u0001\u0004\u0019\u0015!\u00019\u0011\tE!e%P\u0005\u0003\u000b.\u0011\u0011BR;oGRLwN\\\u0019)\r\u00019%jS'O!\t\t\u0002*\u0003\u0002J\u0017\tQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\nA*A(Vg\u0016\u0004\u0013\rI:dC2\fgfY8mY\u0016\u001cG/[8o]5,H/\u00192mK:jU\u000f\u001c;j\t&\u001cG\u000fI5oAQDW\rI:dC2\fWfY8mY\u0016\u001cG/[8o[\r|g\u000e\u001e:jE\u0002jw\u000eZ;mK\u0006)1/\u001b8dK\u0006\nq*\u0001\u00043]E\u001ad\u0006\r")
public interface MultiMap<K, V>
extends Map<K, Set<V>> {
    public static /* synthetic */ Set makeSet$(MultiMap $this) {
        return $this.makeSet();
    }

    default public Set<V> makeSet() {
        return new HashSet();
    }

    public static /* synthetic */ MultiMap addBinding$(MultiMap $this, Object key, Object value) {
        return $this.addBinding(key, value);
    }

    default public MultiMap<K, V> addBinding(K key, V value) {
        Option option = this.get(key);
        if (None$.MODULE$.equals(option)) {
            Set<V> set = this.makeSet();
            if (set == null) {
                throw null;
            }
            set.addOne(value);
            this.update(key, set);
        } else if (option instanceof Some) {
            Set set = (Set)((Some)option).value();
            if (set == null) {
                throw null;
            }
            set.addOne(value);
        } else {
            throw new MatchError(option);
        }
        return this;
    }

    public static /* synthetic */ MultiMap removeBinding$(MultiMap $this, Object key, Object value) {
        return $this.removeBinding(key, value);
    }

    default public MultiMap<K, V> removeBinding(K key, V value) {
        Option option = this.get(key);
        if (!None$.MODULE$.equals(option)) {
            if (option instanceof Some) {
                Set set = (Set)((Some)option).value();
                if (set == null) {
                    throw null;
                }
                set.subtractOne(value);
                if (set.isEmpty()) {
                    this.subtractOne(key);
                }
            } else {
                throw new MatchError(option);
            }
        }
        return this;
    }

    public static /* synthetic */ boolean entryExists$(MultiMap $this, Object key, Function1 p) {
        return $this.entryExists(key, p);
    }

    default public boolean entryExists(K key, Function1<V, Object> p) {
        Option option = this.get(key);
        if (None$.MODULE$.equals(option)) {
            return false;
        }
        if (option instanceof Some) {
            return ((Set)((Some)option).value()).exists(p);
        }
        throw new MatchError(option);
    }

    public static void $init$(MultiMap $this) {
    }
}

