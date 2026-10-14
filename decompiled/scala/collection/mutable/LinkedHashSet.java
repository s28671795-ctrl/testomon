/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.NoSuchElementException;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterator;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.View;
import scala.collection.generic.DefaultSerializable;
import scala.collection.mutable.AbstractSet;
import scala.collection.mutable.Builder;
import scala.collection.mutable.GrowableBuilder;
import scala.collection.mutable.HashEntry;
import scala.collection.mutable.HashTable;
import scala.collection.mutable.LinkedHashSet$;
import scala.math.Integral;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\teb\u0001B\u00181\u0001]BQa\u0017\u0001\u0005\u0002qCQ!\u0018\u0001\u0005By+AA\u0019\u0001\u0001G\"I\u00111\u000e\u0001A\u0002\u0013E\u0011Q\u000e\u0005\n\u0003g\u0002\u0001\u0019!C\t\u0003kB\u0001\"!\u001f\u0001A\u0003&\u0011q\u000e\u0005\n\u0003\u0007\u0003\u0001\u0019!C\t\u0003[B\u0011\"!\"\u0001\u0001\u0004%\t\"a\"\t\u0011\u0005-\u0005\u0001)Q\u0005\u0003_B\u0001\"a$\u0001A\u0003&\u0011\u0011\u0013\u0005\t\u00033\u0003A\u0011\u0001\u0019\u0002\u001c\"9\u0011Q\u0014\u0001\u0005\n\u0005}\u0005bBAT\u0001\u0011\u0005\u0013\u0011\u0016\u0005\b\u0003W\u0003A\u0011IAW\u0011\u001d\t)\f\u0001C!\u0003SCq!a.\u0001\t\u0003\ni\u000bC\u0004\u0002:\u0002!\t%a/\t\u000f\u0005\r\u0007\u0001\"\u0011\u0002<\"9\u0011Q\u0019\u0001\u0005B\u0005\u001d\u0007bBAh\u0001\u0011\u0005\u0011\u0011\u001b\u0005\b\u0003/\u0004A\u0011AAm\u0011\u001d\ti\u000e\u0001C\u0001\u0003?Dq!a9\u0001\t\u0003\n)\u000fC\u0004\u0002j\u0002!\t!a;\t\u000f\u0005M\b\u0001\"\u0011\u0002v\"9!\u0011\u0002\u0001\u0005B\t-\u0001b\u0002B\u0007\u0001\u0011%!q\u0002\u0005\b\u0005C\u0001A\u0011\u0002B\u0012\u0011!\u0011y\u0003\u0001Q\u0005R\tEr!B31\u0011\u00031g!B\u00181\u0011\u00039\u0007\"B. \t\u0003Y\u0007\"\u00027 \t\u0003j\u0007\"\u0002: \t\u0003\u0019\b\"\u0002@ \t\u0003yhA\u00022 \u0005A\ny\u0001\u0003\u0006\u0002\"\u0011\u0012)\u0019!C\u0001\u0003GA!\"!\n%\u0005\u0003\u0005\u000b\u0011BA\r\u0011\u0019YF\u0005\"\u0001\u0002(!I\u00111\u0006\u0013A\u0002\u0013\u0005\u0011Q\u0006\u0005\n\u0003_!\u0003\u0019!C\u0001\u0003cA\u0001\"!\u0010%A\u0003&\u0011Q\u0004\u0005\n\u0003\u007f!\u0003\u0019!C\u0001\u0003[A\u0011\"!\u0011%\u0001\u0004%\t!a\u0011\t\u0011\u0005\u001dC\u0005)Q\u0005\u0003;A\u0011\"!\u0013 \u0003\u0003%I!a\u0013\u0003\u001b1Kgn[3e\u0011\u0006\u001c\bnU3u\u0015\t\t$'A\u0004nkR\f'\r\\3\u000b\u0005M\"\u0014AC2pY2,7\r^5p]*\tQ'A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005az4C\u0002\u0001:\u0013:\u0013V\u000bE\u0002;wuj\u0011\u0001M\u0005\u0003yA\u00121\"\u00112tiJ\f7\r^*fiB\u0011ah\u0010\u0007\u0001\t\u0015\u0001\u0005A1\u0001B\u0005\u0005\t\u0015C\u0001\"G!\t\u0019E)D\u00015\u0013\t)EGA\u0004O_RD\u0017N\\4\u0011\u0005\r;\u0015B\u0001%5\u0005\r\te.\u001f\t\u0006u)kD*T\u0005\u0003\u0017B\u0012aaU3u\u001fB\u001c\bC\u0001\u001e\u0001!\rQ\u0004!\u0010\t\u0006\u001fBkD*T\u0007\u0002e%\u0011\u0011K\r\u0002\u001b'R\u0014\u0018n\u0019;PaRLW.\u001b>fI&#XM]1cY\u0016|\u0005o\u001d\t\u0005\u001fNkD*\u0003\u0002Ue\t9\u0012\n^3sC\ndWMR1di>\u0014\u0018\u0010R3gCVdGo\u001d\t\u0003-fk\u0011a\u0016\u0006\u00031J\nqaZ3oKJL7-\u0003\u0002[/\n\u0019B)\u001a4bk2$8+\u001a:jC2L'0\u00192mK\u00061A(\u001b8jiz\"\u0012!T\u0001\u0010SR,'/\u00192mK\u001a\u000b7\r^8ssV\tq\fE\u0002PA2K!!\u0019\u001a\u0003\u001f%#XM]1cY\u00164\u0015m\u0019;pef\u0014Q!\u00128uef\u00042\u0001\u001a\u0013>\u001d\tQd$A\u0007MS:\\W\r\u001a%bg\"\u001cV\r\u001e\t\u0003u}\u00192a\b5`!\t\u0019\u0015.\u0003\u0002ki\t1\u0011I\\=SK\u001a$\u0012AZ\u0001\u0006K6\u0004H/_\u000b\u0003]F,\u0012a\u001c\t\u0004u\u0001\u0001\bC\u0001 r\t\u0015\u0001\u0015E1\u0001B\u0003\u00111'o\\7\u0016\u0005Q<HCA;z!\rQ\u0004A\u001e\t\u0003}]$Q\u0001\u001f\u0012C\u0002\u0005\u0013\u0011!\u0012\u0005\u0006u\n\u0002\ra_\u0001\u0003SR\u00042a\u0014?w\u0013\ti(G\u0001\u0007Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cW-\u0001\u0006oK^\u0014U/\u001b7eKJ,B!!\u0001\u0002\fU\u0011\u00111\u0001\t\bu\u0005\u0015\u0011\u0011BA\u0007\u0013\r\t9\u0001\r\u0002\u0010\u000fJ|w/\u00192mK\n+\u0018\u000e\u001c3feB\u0019a(a\u0003\u0005\u000b\u0001\u001b#\u0019A!\u0011\ti\u0002\u0011\u0011B\u000b\u0005\u0003#\tYb\u0005\u0003%Q\u0006M\u0001c\u0002\u001e\u0002\u0016\u0005e\u0011QD\u0005\u0004\u0003/\u0001$!\u0003%bg\",e\u000e\u001e:z!\rq\u00141\u0004\u0003\u0006\u0001\u0012\u0012\r!\u0011\t\u0006\u0003?!\u0013\u0011D\u0007\u0002?\u0005\u00191.Z=\u0016\u0005\u0005e\u0011\u0001B6fs\u0002\"B!!\b\u0002*!9\u0011\u0011E\u0014A\u0002\u0005e\u0011aB3be2LWM]\u000b\u0003\u0003;\t1\"Z1sY&,'o\u0018\u0013fcR!\u00111GA\u001d!\r\u0019\u0015QG\u0005\u0004\u0003o!$\u0001B+oSRD\u0011\"a\u000f*\u0003\u0003\u0005\r!!\b\u0002\u0007a$\u0013'\u0001\u0005fCJd\u0017.\u001a:!\u0003\u0015a\u0017\r^3s\u0003%a\u0017\r^3s?\u0012*\u0017\u000f\u0006\u0003\u00024\u0005\u0015\u0003\"CA\u001eY\u0005\u0005\t\u0019AA\u000f\u0003\u0019a\u0017\r^3sA\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011Q\n\t\u0005\u0003\u001f\nI&\u0004\u0002\u0002R)!\u00111KA+\u0003\u0011a\u0017M\\4\u000b\u0005\u0005]\u0013\u0001\u00026bm\u0006LA!a\u0017\u0002R\t1qJ\u00196fGRDsaHA0\u0003K\n9\u0007E\u0002D\u0003CJ1!a\u00195\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0004Q\u001dq\u0012qLA3\u0003O\n!BZ5sgR,e\u000e\u001e:z+\t\ty\u0007E\u0002\u0002r\ri\u0011\u0001A\u0001\u000fM&\u00148\u000f^#oiJLx\fJ3r)\u0011\t\u0019$a\u001e\t\u0013\u0005mR!!AA\u0002\u0005=\u0014a\u00034jeN$XI\u001c;ss\u0002B3ABA?!\r\u0019\u0015qP\u0005\u0004\u0003\u0003#$!\u0003;sC:\u001c\u0018.\u001a8u\u0003%a\u0017m\u001d;F]R\u0014\u00180A\u0007mCN$XI\u001c;ss~#S-\u001d\u000b\u0005\u0003g\tI\tC\u0005\u0002<!\t\t\u00111\u0001\u0002p\u0005QA.Y:u\u000b:$(/\u001f\u0011)\u0007%\ti(A\u0003uC\ndW\rE\u0004;\u0003'k\u0004.a\u001c\n\u0007\u0005U\u0005GA\u0005ICNDG+\u00192mK\"\u001a!\"! \u0002\u0011\u001d,G\u000fV1cY\u0016,\"!!%\u0002\u00199,w\u000fS1tQR\u000b'\r\\3\u0016\u0005\u0005\u0005&#BARQ\u0006EeABAS\u0019\u0001\t\tK\u0001\u0007=e\u00164\u0017N\\3nK:$h(\u0001\u0003mCN$X#A\u001f\u0002\u00151\f7\u000f^(qi&|g.\u0006\u0002\u00020B!1)!->\u0013\r\t\u0019\f\u000e\u0002\u0007\u001fB$\u0018n\u001c8\u0002\t!,\u0017\rZ\u0001\u000bQ\u0016\fGm\u00149uS>t\u0017\u0001B:ju\u0016,\"!!0\u0011\u0007\r\u000by,C\u0002\u0002BR\u00121!\u00138u\u0003%Ygn\\<o'&TX-A\u0004jg\u0016k\u0007\u000f^=\u0016\u0005\u0005%\u0007cA\"\u0002L&\u0019\u0011Q\u001a\u001b\u0003\u000f\t{w\u000e\\3b]\u0006A1m\u001c8uC&t7\u000f\u0006\u0003\u0002J\u0006M\u0007BBAk)\u0001\u0007Q(\u0001\u0003fY\u0016l\u0017AB1eI>sW\r\u0006\u0003\u0002r\u0005m\u0007BBAk+\u0001\u0007Q(A\u0006tk\n$(/Y2u\u001f:,G\u0003BA9\u0003CDa!!6\u0017\u0001\u0004i\u0014A\u0002:f[>4X\r\u0006\u0003\u0002J\u0006\u001d\bBBAk/\u0001\u0007Q(\u0001\u0005ji\u0016\u0014\u0018\r^8s+\t\ti\u000f\u0005\u0003P\u0003_l\u0014bAAye\tA\u0011\n^3sCR|'/A\u0004g_J,\u0017m\u00195\u0016\t\u0005](Q\u0001\u000b\u0005\u0003g\tI\u0010C\u0004\u0002|f\u0001\r!!@\u0002\u0003\u0019\u0004baQA\u0000{\t\r\u0011b\u0001B\u0001i\tIa)\u001e8di&|g.\r\t\u0004}\t\u0015AA\u0002B\u00043\t\u0007\u0011IA\u0001V\u0003\u0015\u0019G.Z1s)\t\t\u0019$A\u0006xe&$Xm\u00142kK\u000e$H\u0003BA\u001a\u0005#AqAa\u0005\u001c\u0001\u0004\u0011)\"A\u0002pkR\u0004BAa\u0006\u0003\u001e5\u0011!\u0011\u0004\u0006\u0005\u00057\t)&\u0001\u0002j_&!!q\u0004B\r\u0005Iy%M[3di>+H\u000f];u'R\u0014X-Y7\u0002\u0015I,\u0017\rZ(cU\u0016\u001cG\u000f\u0006\u0003\u00024\t\u0015\u0002b\u0002B\u00149\u0001\u0007!\u0011F\u0001\u0003S:\u0004BAa\u0006\u0003,%!!Q\u0006B\r\u0005Ey%M[3di&s\u0007/\u001e;TiJ,\u0017-\\\u0001\rgR\u0014\u0018N\\4Qe\u00164\u0017\u000e_\u000b\u0003\u0005g\u0001B!a\u0014\u00036%!!qGA)\u0005\u0019\u0019FO]5oO\u0002")
public class LinkedHashSet<A>
extends AbstractSet<A>
implements StrictOptimizedIterableOps<A, LinkedHashSet, LinkedHashSet<A>>,
DefaultSerializable {
    private transient Entry<A> firstEntry = null;
    private transient Entry<A> lastEntry = null;
    private transient HashTable<A, Object, Entry<A>> table = new HashTable<A, Object, Entry<A>>(this){
        private int _loadFactor;
        private HashEntry<A, Entry<A>>[] table;
        private int tableSize;
        private int threshold;
        private int[] sizemap;
        private int seedvalue;
        private final /* synthetic */ LinkedHashSet $outer;

        public final int size() {
            return HashTable.size$(this);
        }

        public int tableSizeSeed() {
            return HashTable.tableSizeSeed$(this);
        }

        public int initialSize() {
            return HashTable.initialSize$(this);
        }

        public void init(ObjectInputStream in, Function0<Entry<A>> readEntry2) {
            HashTable.init$(this, in, readEntry2);
        }

        public void serializeTo(ObjectOutputStream out, Function1<Entry<A>, BoxedUnit> writeEntry) {
            HashTable.serializeTo$(this, out, writeEntry);
        }

        public final HashEntry findEntry(Object key) {
            return HashTable.findEntry$(this, key);
        }

        public final HashEntry findEntry0(Object key, int h2) {
            return HashTable.findEntry0$(this, key, h2);
        }

        public final void addEntry(HashEntry e) {
            HashTable.addEntry$(this, e);
        }

        public final void addEntry0(HashEntry e, int h2) {
            HashTable.addEntry0$(this, e, h2);
        }

        public HashEntry findOrAddEntry(Object key, Object value) {
            return HashTable.findOrAddEntry$(this, key, value);
        }

        public final HashEntry removeEntry(Object key) {
            return HashTable.removeEntry$(this, key);
        }

        public final HashEntry removeEntry0(Object key, int h2) {
            return HashTable.removeEntry0$(this, key, h2);
        }

        public Iterator<Entry<A>> entriesIterator() {
            return HashTable.entriesIterator$(this);
        }

        public void clearTable() {
            HashTable.clearTable$(this);
        }

        public final void nnSizeMapAdd(int h2) {
            HashTable.nnSizeMapAdd$(this, h2);
        }

        public final void nnSizeMapRemove(int h2) {
            HashTable.nnSizeMapRemove$(this, h2);
        }

        public final void nnSizeMapReset(int tableLength) {
            HashTable.nnSizeMapReset$(this, tableLength);
        }

        public final int totalSizeMapBuckets() {
            return HashTable.totalSizeMapBuckets$(this);
        }

        public final int calcSizeMapSize(int tableLength) {
            return HashTable.calcSizeMapSize$(this, tableLength);
        }

        public void sizeMapInit(int tableLength) {
            HashTable.sizeMapInit$(this, tableLength);
        }

        public final void sizeMapInitAndRebuild() {
            HashTable.sizeMapInitAndRebuild$(this);
        }

        public void printSizeMap() {
            HashTable.printSizeMap$(this);
        }

        public final void sizeMapDisable() {
            HashTable.sizeMapDisable$(this);
        }

        public final boolean isSizeMapDefined() {
            return HashTable.isSizeMapDefined$(this);
        }

        public boolean alwaysInitSizeMap() {
            return HashTable.alwaysInitSizeMap$(this);
        }

        public boolean elemEquals(A key1, A key2) {
            return HashTable.elemEquals$(this, key1, key2);
        }

        public final int index(int hcode) {
            return HashTable.index$(this, hcode);
        }

        public final int sizeMapBucketBitSize() {
            return HashTable.HashUtils.sizeMapBucketBitSize$(this);
        }

        public final int sizeMapBucketSize() {
            return HashTable.HashUtils.sizeMapBucketSize$(this);
        }

        public int elemHashCode(A key) {
            return HashTable.HashUtils.elemHashCode$(this, key);
        }

        public final int improve(int hcode, int seed) {
            return HashTable.HashUtils.improve$(this, hcode, seed);
        }

        public int _loadFactor() {
            return this._loadFactor;
        }

        public void _loadFactor_$eq(int x$1) {
            this._loadFactor = x$1;
        }

        public HashEntry<A, Entry<A>>[] table() {
            return this.table;
        }

        public void table_$eq(HashEntry<A, Entry<A>>[] x$1) {
            this.table = x$1;
        }

        public int tableSize() {
            return this.tableSize;
        }

        public void tableSize_$eq(int x$1) {
            this.tableSize = x$1;
        }

        public int threshold() {
            return this.threshold;
        }

        public void threshold_$eq(int x$1) {
            this.threshold = x$1;
        }

        public int[] sizemap() {
            return this.sizemap;
        }

        public void sizemap_$eq(int[] x$1) {
            this.sizemap = x$1;
        }

        public int seedvalue() {
            return this.seedvalue;
        }

        public void seedvalue_$eq(int x$1) {
            this.seedvalue = x$1;
        }

        public Entry<A> createNewEntry(A key, Object value) {
            Entry<A> e = new Entry<A>(key);
            if (this.$outer.firstEntry() == null) {
                this.$outer.firstEntry_$eq(e);
            } else {
                this.$outer.lastEntry().later_$eq(e);
                e.earlier_$eq(this.$outer.lastEntry());
            }
            this.$outer.lastEntry_$eq(e);
            return e;
        }

        public <U> void foreachEntry(Function1<Entry<A>, U> f) {
            for (Entry<A> cur = this.$outer.firstEntry(); cur != null; cur = cur.later()) {
                f.apply(cur);
            }
        }
        {
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            HashTable.$init$(this);
        }
    };

    public static <A> GrowableBuilder<A, LinkedHashSet<A>> newBuilder() {
        return LinkedHashSet$.MODULE$.newBuilder();
    }

    public static <E> LinkedHashSet<E> from(IterableOnce<E> it) {
        return LinkedHashSet$.MODULE$.from((IterableOnce)it);
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        LinkedHashSet$ tabulate_this = LinkedHashSet$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$7$adapted(tabulate_this, n2, n3, n4, n5, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        LinkedHashSet$ tabulate_this = LinkedHashSet$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$5$adapted(tabulate_this, n2, n3, n4, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f) {
        LinkedHashSet$ tabulate_this = LinkedHashSet$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$3$adapted(tabulate_this, n2, n3, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n1, int n2, Function2 f) {
        LinkedHashSet$ tabulate_this = LinkedHashSet$.MODULE$;
        Function1<Object, Object> & Serializable tabulate_f = arg_0 -> IterableFactory.$anonfun$tabulate$1$adapted(tabulate_this, n2, f, arg_0);
        View.Tabulate from_source = new View.Tabulate(n1, tabulate_f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object tabulate(int n, Function1 f) {
        LinkedHashSet$ tabulate_this = LinkedHashSet$.MODULE$;
        View.Tabulate from_source = new View.Tabulate(n, f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        LinkedHashSet$ fill_this = LinkedHashSet$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$4(fill_this, n2, n3, n4, n5, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        LinkedHashSet$ fill_this = LinkedHashSet$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$3(fill_this, n2, n3, n4, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem) {
        LinkedHashSet$ fill_this = LinkedHashSet$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$2(fill_this, n2, n3, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, Function0 elem) {
        LinkedHashSet$ fill_this = LinkedHashSet$.MODULE$;
        Function0<Object> & Serializable fill_elem = () -> IterableFactory.$anonfun$fill$1(fill_this, n2, elem);
        View.Fill from_source = new View.Fill(n1, fill_elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n, Function0 elem) {
        LinkedHashSet$ fill_this = LinkedHashSet$.MODULE$;
        View.Fill from_source = new View.Fill(n, elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(LinkedHashSet$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(LinkedHashSet$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        LinkedHashSet$ unfold_this = LinkedHashSet$.MODULE$;
        View.Unfold from_source = new View.Unfold(init2, f);
        return unfold_this.from((IterableOnce)from_source);
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        LinkedHashSet$ iterate_this = LinkedHashSet$.MODULE$;
        View.Iterate<Object> from_source = new View.Iterate<Object>(start2, len, f);
        return iterate_this.from((IterableOnce)from_source);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
    }

    @Override
    public Tuple2<LinkedHashSet<A>, LinkedHashSet<A>> partition(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<LinkedHashSet<A>, LinkedHashSet<A>> span(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<LinkedHashSet<A1>, LinkedHashSet<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<LinkedHashSet<A1>, LinkedHashSet<A2>, LinkedHashSet<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<A, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<A, IterableOnce<B>> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatMap$(this, b, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedConcat(IterableOnce<B> that, Builder<B, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedConcat$(this, that, b);
    }

    @Override
    public Object collect(PartialFunction pf) {
        return StrictOptimizedIterableOps.collect$(this, pf);
    }

    @Override
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<A, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<A, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<A, B>, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedZip$(this, that, b);
    }

    @Override
    public Object zipWithIndex() {
        return StrictOptimizedIterableOps.zipWithIndex$(this);
    }

    @Override
    public Object scanLeft(Object z, Function2 op) {
        return StrictOptimizedIterableOps.scanLeft$(this, z, op);
    }

    @Override
    public Object filter(Function1 pred) {
        return StrictOptimizedIterableOps.filter$(this, pred);
    }

    @Override
    public Object filterNot(Function1 pred) {
        return StrictOptimizedIterableOps.filterNot$(this, pred);
    }

    @Override
    public Object filterImpl(Function1 pred, boolean isFlipped) {
        return StrictOptimizedIterableOps.filterImpl$(this, pred, isFlipped);
    }

    @Override
    public <A1, A2> Tuple2<LinkedHashSet<A1>, LinkedHashSet<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
    }

    @Override
    public Object takeRight(int n) {
        return StrictOptimizedIterableOps.takeRight$(this, n);
    }

    @Override
    public Object dropRight(int n) {
        return StrictOptimizedIterableOps.dropRight$(this, n);
    }

    @Override
    public IterableFactory<LinkedHashSet> iterableFactory() {
        return LinkedHashSet$.MODULE$;
    }

    public Entry<A> firstEntry() {
        return this.firstEntry;
    }

    public void firstEntry_$eq(Entry<A> x$1) {
        this.firstEntry = x$1;
    }

    public Entry<A> lastEntry() {
        return this.lastEntry;
    }

    public void lastEntry_$eq(Entry<A> x$1) {
        this.lastEntry = x$1;
    }

    public HashTable<A, Object, Entry<A>> getTable() {
        return this.table;
    }

    private HashTable<A, Object, Entry<A>> newHashTable() {
        return new /* invalid duplicate definition of identical inner class */;
    }

    @Override
    public A last() {
        if (this.size() > 0) {
            return this.lastEntry().key();
        }
        throw new NoSuchElementException("Cannot call .last on empty LinkedHashSet");
    }

    @Override
    public Option<A> lastOption() {
        if (this.size() > 0) {
            return new Some<A>(this.lastEntry().key());
        }
        return None$.MODULE$;
    }

    @Override
    public A head() {
        if (this.size() > 0) {
            return this.firstEntry().key();
        }
        throw new NoSuchElementException("Cannot call .head on empty LinkedHashSet");
    }

    @Override
    public Option<A> headOption() {
        if (this.size() > 0) {
            return new Some<A>(this.firstEntry().key());
        }
        return None$.MODULE$;
    }

    @Override
    public int size() {
        return this.table.tableSize();
    }

    @Override
    public int knownSize() {
        return this.size();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(A elem) {
        return this.table.findEntry(elem) != null;
    }

    @Override
    public LinkedHashSet<A> addOne(A elem) {
        this.table.findOrAddEntry(elem, null);
        return this;
    }

    @Override
    public LinkedHashSet<A> subtractOne(A elem) {
        this.remove(elem);
        return this;
    }

    @Override
    public boolean remove(A elem) {
        Entry e = this.table.removeEntry(elem);
        if (e == null) {
            return false;
        }
        if (e.earlier() == null) {
            this.firstEntry_$eq(e.later());
        } else {
            e.earlier().later_$eq(e.later());
        }
        if (e.later() == null) {
            this.lastEntry_$eq(e.earlier());
        } else {
            e.later().earlier_$eq(e.earlier());
        }
        e.earlier_$eq(null);
        e.later_$eq(null);
        return true;
    }

    @Override
    public Iterator<A> iterator() {
        return new AbstractIterator<A>(this){
            private Entry<A> cur;

            public boolean hasNext() {
                return this.cur != null;
            }

            public A next() {
                if (this.hasNext()) {
                    A res = this.cur.key();
                    this.cur = this.cur.later();
                    return res;
                }
                return (A)Iterator$.scala$collection$Iterator$$_empty.next();
            }
            {
                this.cur = $outer.firstEntry();
            }
        };
    }

    @Override
    public <U> void foreach(Function1<A, U> f) {
        for (Entry<A> cur = this.firstEntry(); cur != null; cur = cur.later()) {
            f.apply(cur.key());
        }
    }

    @Override
    public void clear() {
        this.table.clearTable();
        this.firstEntry_$eq(null);
        this.lastEntry_$eq(null);
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        this.table.serializeTo(out, (Function1<Entry, Object> & Serializable)e -> {
            out.writeObject(e.key());
            return BoxedUnit.UNIT;
        });
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        this.table = new /* invalid duplicate definition of identical inner class */;
        this.table.init(in, (Function0<Entry> & Serializable)() -> $this.table.createNewEntry(in.readObject(), null));
    }

    @Override
    public String stringPrefix() {
        return "LinkedHashSet";
    }

    public static final class Entry<A>
    implements HashEntry<A, Entry<A>> {
        private final A key;
        private Entry<A> earlier;
        private Entry<A> later;
        private Entry<A> next;

        @Override
        public Entry<A> next() {
            return this.next;
        }

        @Override
        public void next_$eq(Entry<A> x$1) {
            this.next = x$1;
        }

        @Override
        public A key() {
            return this.key;
        }

        public Entry<A> earlier() {
            return this.earlier;
        }

        public void earlier_$eq(Entry<A> x$1) {
            this.earlier = x$1;
        }

        public Entry<A> later() {
            return this.later;
        }

        public void later_$eq(Entry<A> x$1) {
            this.later = x$1;
        }

        public Entry(A key) {
            this.key = key;
            this.earlier = null;
            this.later = null;
        }
    }
}

