/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.ImmutableIntMap$;
import java.io.Serializable;
import java.util.Arrays;
import scala.Function0;
import scala.Function1;
import scala.collection.Iterator;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015rA\u0002\r\u001a\u0011\u0003YRD\u0002\u0004 3!\u00051\u0004\t\u0005\u0006O\u0005!\t!\u000b\u0005\bU\u0005\u0011\r\u0011\"\u0002,\u0011\u001d\ty\"\u0001Q\u0001\u000e12QaH\r\u000375B\u0001BL\u0003\u0003\u0006\u0004%ia\f\u0005\tm\u0015\u0011\t\u0011)A\u0007a!Aq'\u0002BC\u0002\u0013\u0015\u0001\b\u0003\u0005:\u000b\t\u0005\t\u0015!\u00044\u0011\u00159S\u0001\"\u0003;\u0011\u00159S\u0001\"\u0004>\u0011\u0019\u0011U\u0001)C\u0007\u0007\")Q)\u0002C\u0003\r\")\u0001*\u0002C\u0003\u0013\")a*\u0002C\u0003\u001f\")Q+\u0002C\u0003-\"1\u0011,\u0002Q\u0005\u000eiCaAX\u0003!\n\u001by\u0006\"\u00023\u0006\t\u000b)\u0007\"B4\u0006\t\u000bA\u0007\"B;\u0006\t\u000b2\bBB@\u0006\t\u000b\n\t\u0001C\u0004\u0002\u0004\u0015!)%!\u0002\u0002\u001f%kW.\u001e;bE2,\u0017J\u001c;NCBT!AG\u000e\u0002\tU$\u0018\u000e\u001c\u0006\u00029\u0005!\u0011m[6b!\tq\u0012!D\u0001\u001a\u0005=IU.\\;uC\ndW-\u00138u\u001b\u0006\u00048CA\u0001\"!\t\u0011S%D\u0001$\u0015\u0005!\u0013!B:dC2\f\u0017B\u0001\u0014$\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001\u001e\u0003\u0015)W\u000e\u001d;z+\u0005a\u0003C\u0001\u0010\u0006'\t)\u0011%A\u0002lmN,\u0012\u0001\r\t\u0004EE\u001a\u0014B\u0001\u001a$\u0005\u0015\t%O]1z!\t\u0011C'\u0003\u00026G\t\u0019\u0011J\u001c;\u0002\t-48\u000fI\u0001\u0005g&TX-F\u00014\u0003\u0015\u0019\u0018N_3!)\ra3\b\u0010\u0005\u0006])\u0001\r\u0001\r\u0005\u0006o)\u0001\ra\r\u000b\u0004Yy\u0002\u0005\"B \f\u0001\u0004\u0019\u0014aA6fs\")\u0011i\u0003a\u0001g\u0005)a/\u00197vK\u0006Y\u0011N\u001c3fq\u001a{'oS3z)\t\u0019D\tC\u0003@\u0019\u0001\u00071'A\u0002hKR$\"aM$\t\u000b}j\u0001\u0019A\u001a\u0002\u0011\r|g\u000e^1j]N$\"AS'\u0011\u0005\tZ\u0015B\u0001'$\u0005\u001d\u0011un\u001c7fC:DQa\u0010\bA\u0002M\na\"\u001e9eCR,\u0017JZ!cg\u0016tG\u000fF\u0002-!FCQaP\bA\u0002MBa!Q\b\u0005\u0002\u0004\u0011\u0006c\u0001\u0012Tg%\u0011Ak\t\u0002\ty\tLh.Y7f}\u00059Q\u000f\u001d3bi\u0016$Gc\u0001\u0017X1\")q\b\u0005a\u0001g!)\u0011\t\u0005a\u0001g\u00051Q\u000f\u001d3bi\u0016$2\u0001L.]\u0011\u0015\t\u0015\u00031\u00014\u0011\u0015i\u0016\u00031\u00014\u0003)1\u0018\r\\;f\u0013:$W\r_\u0001\u0007S:\u001cXM\u001d;\u0015\t1\u0002\u0017M\u0019\u0005\u0006\u007fI\u0001\ra\r\u0005\u0006\u0003J\u0001\ra\r\u0005\u0006GJ\u0001\raM\u0001\u0006S:$W\r_\u0001\u0007e\u0016lwN^3\u0015\u000512\u0007\"B \u0014\u0001\u0004\u0019\u0014\u0001D6fsNLE/\u001a:bi>\u0014X#A5\u0011\u0007)\u00148G\u0004\u0002la:\u0011An\\\u0007\u0002[*\u0011a\u000eK\u0001\u0007yI|w\u000e\u001e \n\u0003\u0011J!!]\u0012\u0002\u000fA\f7m[1hK&\u00111\u000f\u001e\u0002\t\u0013R,'/\u0019;pe*\u0011\u0011oI\u0001\ti>\u001cFO]5oOR\tq\u000f\u0005\u0002yy:\u0011\u0011P\u001f\t\u0003Y\u000eJ!a_\u0012\u0002\rA\u0013X\rZ3g\u0013\tihP\u0001\u0004TiJLgn\u001a\u0006\u0003w\u000e\n\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002g\u00051Q-];bYN$2ASA\u0004\u0011\u001d\tIa\u0006a\u0001\u0003\u0017\t1a\u001c2k!\r\u0011\u0013QB\u0005\u0004\u0003\u001f\u0019#aA!os\"\u001aQ!a\u0005\u0011\t\u0005U\u00111D\u0007\u0003\u0003/Q1!!\u0007\u001c\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003;\t9BA\u0006J]R,'O\\1m\u0003BL\u0017AB3naRL\b\u0005K\u0002\u0002\u0003'A3\u0001AA\n\u0001")
@InternalApi
public final class ImmutableIntMap {
    private final int[] kvs;
    private final int size;

    public static ImmutableIntMap empty() {
        return ImmutableIntMap$.MODULE$.empty();
    }

    private final int[] kvs() {
        return this.kvs;
    }

    public final int size() {
        return this.size;
    }

    private final int indexForKey(int key) {
        return this.find$1(0, this.size() - 1, key);
    }

    public final int get(int key) {
        return this.find$2(0, this.size() - 1, key);
    }

    public final boolean contains(int key) {
        return this.indexForKey(key) >= 0;
    }

    public final ImmutableIntMap updateIfAbsent(int key, Function0<Object> value) {
        if (this.size() > 0) {
            int i = this.indexForKey(key);
            if (i >= 0) {
                return this;
            }
            return this.insert(key, value.apply$mcI$sp(), i);
        }
        return new ImmutableIntMap(key, value.apply$mcI$sp());
    }

    public final ImmutableIntMap updated(int key, int value) {
        if (this.size() > 0) {
            int i = this.indexForKey(key);
            if (i >= 0) {
                int valueIndex = i + 1;
                if (this.kvs()[valueIndex] != value) {
                    return this.update(value, valueIndex);
                }
                return this;
            }
            return this.insert(key, value, i);
        }
        return new ImmutableIntMap(key, value);
    }

    private final ImmutableIntMap update(int value, int valueIndex) {
        int[] newKvs = (int[])this.kvs().clone();
        newKvs[valueIndex] = value;
        return new ImmutableIntMap(newKvs, this.size());
    }

    private final ImmutableIntMap insert(int key, int value, int index) {
        int at = ~index;
        int[] newKvs = new int[this.kvs().length + 2];
        System.arraycopy(this.kvs(), 0, newKvs, 0, at);
        newKvs[at] = key;
        newKvs[at + 1] = value;
        System.arraycopy(this.kvs(), at, newKvs, at + 2, this.kvs().length - at);
        return new ImmutableIntMap(newKvs, this.size() + 1);
    }

    public final ImmutableIntMap remove(int key) {
        int i = this.indexForKey(key);
        if (i >= 0) {
            if (this.size() > 1) {
                int newSz = this.kvs().length - 2;
                int[] newKvs = new int[newSz];
                System.arraycopy(this.kvs(), 0, newKvs, 0, i);
                System.arraycopy(this.kvs(), i + 2, newKvs, i, newSz - i);
                return new ImmutableIntMap(newKvs, this.size() - 1);
            }
            return ImmutableIntMap$.MODULE$.empty();
        }
        return this;
    }

    public final Iterator<Object> keysIterator() {
        if (this.size() < 1) {
            return package$.MODULE$.Iterator().empty();
        }
        return package$.MODULE$.Iterator().range(0, this.kvs().length - 1, 2).map(i -> this.kvs()[i]);
    }

    public final String toString() {
        if (this.size() < 1) {
            return "ImmutableIntMap()";
        }
        return package$.MODULE$.Iterator().range(0, this.kvs().length - 1, 2).map((Function1<Object, String> & Serializable)i -> ImmutableIntMap.$anonfun$toString$1(this, BoxesRunTime.unboxToInt(i))).mkString("ImmutableIntMap(", ", ", ")");
    }

    public final int hashCode() {
        return Arrays.hashCode(this.kvs());
    }

    public final boolean equals(Object obj) {
        Object object = obj;
        if (object instanceof ImmutableIntMap) {
            ImmutableIntMap immutableIntMap = (ImmutableIntMap)object;
            return Arrays.equals(this.kvs(), immutableIntMap.kvs());
        }
        return false;
    }

    private final int find$1(int lo, int hi, int key$1) {
        while (lo <= hi) {
            int lohi = lo + hi;
            int idx = lohi & ~1;
            int k = this.kvs()[idx];
            if (k == key$1) {
                return idx;
            }
            if (k < key$1) {
                lo = (lohi >>> 1) + 1;
                continue;
            }
            hi = (lohi >>> 1) - 1;
        }
        return ~(lo << 1);
    }

    private final int find$2(int lo, int hi, int key$2) {
        while (lo <= hi) {
            int lohi = lo + hi;
            int k = this.kvs()[lohi & ~1];
            if (k == key$2) {
                return this.kvs()[lohi | 1];
            }
            if (k < key$2) {
                lo = (lohi >>> 1) + 1;
                continue;
            }
            hi = (lohi >>> 1) - 1;
        }
        return Integer.MIN_VALUE;
    }

    public static final /* synthetic */ String $anonfun$toString$1(ImmutableIntMap $this, int i) {
        return new StringBuilder(4).append($this.kvs()[i]).append(" -> ").append($this.kvs()[i + 1]).toString();
    }

    public ImmutableIntMap(int[] kvs, int size) {
        this.kvs = kvs;
        this.size = size;
    }

    private ImmutableIntMap(int key, int value) {
        this(new int[2], 1);
        this.kvs()[0] = key;
        this.kvs()[1] = value;
    }
}

