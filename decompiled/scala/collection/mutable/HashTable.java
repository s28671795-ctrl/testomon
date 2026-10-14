/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import scala.Console$;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.collection.AbstractIterator;
import scala.collection.IterableFactory;
import scala.collection.Iterator;
import scala.collection.immutable.List$;
import scala.collection.mutable.ArraySeq;
import scala.collection.mutable.HashEntry;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t-d\u0001\u0003 @!\u0003\r\t!Q#\t\u000b1\u0004A\u0011A7\t\u000fu\u0003\u0001\u0019!C\t+\"I\u0011q\u0004\u0001A\u0002\u0013E\u0011\u0011\u0005\u0005\u000b\u0003O\u0001\u0001\u0019!C\t\u0003\u0006%\u0002BCA#\u0001\u0001\u0007I\u0011C!\u0002H!I\u00111\n\u0001A\u0002\u0013E\u0011)\u0016\u0005\u000b\u0003\u001b\u0002\u0001\u0019!C\t\u0003\u0006=\u0003\"B0\u0001\t\u000b)\u0006\"CA*\u0001\u0001\u0007I\u0011C!V\u0011)\t)\u0006\u0001a\u0001\n#\t\u0015q\u000b\u0005\n\u00037\u0002\u0001\u0019!C\t\u0003;B\u0011\"!\u0019\u0001\u0001\u0004%\t\"a\u0019\t\u0011\u0005\u001d\u0004\u00011A\u0005\u0012UC\u0011\"!\u001b\u0001\u0001\u0004%\t\"a\u001b\t\r\u0005=\u0004\u0001\"\u0005V\u0011\u0019\t\t\b\u0001C\t+\"9\u00111\u000f\u0001\u0005\n\u0005U\u0004BBA=\u0001\u0011%Q\u000b\u0003\u0004\u0002|\u0001!I!\u0016\u0005\t\u0003{\u0002A\u0011A!\u0002\u0000!A\u0011q\u0014\u0001\u0005\u0002\u0005\u000b\t\u000bC\u0004\u00028\u0002!)!!/\t\u0011\u0005u\u0006\u0001\"\u0006B\u0003\u007fC\u0001\"a2\u0001\t+\t\u0015\u0011\u001a\u0005\t\u0003\u001f\u0004AQC!\u0002R\"9\u0011q\u001b\u0001\u0005\u0002\u0005e\u0007bBAt\u0001\u0019\u0005\u0011\u0011\u001e\u0005\b\u0003_\u0004AQAAy\u0011!\t)\u0010\u0001C\u0003\u0003\u0006]\bbBA\u007f\u0001\u0011\u0005\u0011q \u0005\b\u0005\u0013\u0001A\u0011\u0001B\u0006\u0011\u0019\u0011Y\u0002\u0001C\u0001[\"9!Q\u0004\u0001\u0005\n\t}\u0001b\u0002B\u0013\u0001\u0011U!q\u0005\u0005\b\u0005W\u0001AQ\u0003B\u0017\u0011\u001d\u0011\t\u0004\u0001C\u000b\u0005gAqA!\u000f\u0001\t\u000b\tU\u000bC\u0004\u0003<\u0001!)B!\u0010\t\u000f\t\u0005\u0003\u0001\"\u0005\u0003D!1!q\t\u0001\u0005\u00165DqA!\u0013\u0001\t\u0003\tU\u000e\u0003\u0004\u0003L\u0001!)\"\u001c\u0005\b\u0005\u001b\u0002AQ\u0003B(\u0011\u001d\u00119\u0006\u0001C\t\u0005\u001fBqA!\u0017\u0001\t#\u0011Y\u0006\u0003\u0005\u0003f\u0001!)\"\u0011B4\u000f\u0019qu\b#\u0001B\u001f\u001a1ah\u0010E\u0001\u0003BCQ!\u0015\u0019\u0005\u0002MCa\u0001\u0016\u0019\u0005\u0006\u0005+\u0006BB-1\t\u000b\tU\u000b\u0003\u0004[a\u0011\u0015\u0011i\u0017\u0005\u0007AB\")!Q1\t\r\u0015\u0004DQA!g\r\u001dI\u0007\u0007%A\u0002\u0002)DQ\u0001\\\u001c\u0005\u00025DQ!]\u001c\u0005\u0016UCQA]\u001c\u0005\u0016UCaa]\u001c\u0005\u0012\u0005#\bbBA\u0003o\u0011U\u0011q\u0001\u0005\t\u0003#\u0001D\u0011A!\u0002\u0014\tI\u0001*Y:i)\u0006\u0014G.\u001a\u0006\u0003\u0001\u0006\u000bq!\\;uC\ndWM\u0003\u0002C\u0007\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003\u0011\u000bQa]2bY\u0006,rARA\u000e\u0003G\fIdE\u0002\u0001\u000f.\u0003\"\u0001S%\u000e\u0003\rK!AS\"\u0003\r\u0005s\u0017PU3g!\u0011au'!\u0007\u000f\u00055{S\"A \u0002\u0013!\u000b7\u000f\u001b+bE2,\u0007CA'1'\t\u0001t)\u0001\u0004=S:LGOP\u0002\u0001)\u0005y\u0015!\u00053fM\u0006,H\u000e\u001e'pC\u00124\u0015m\u0019;peV\ta\u000b\u0005\u0002I/&\u0011\u0001l\u0011\u0002\u0004\u0013:$\u0018a\u00047pC\u00124\u0015m\u0019;pe\u0012+g.^7\u0002\u00199,w\u000f\u00165sKNDw\u000e\u001c3\u0015\u0007Ycf\fC\u0003^i\u0001\u0007a+A\u0006`Y>\fGMR1di>\u0014\b\"B05\u0001\u00041\u0016\u0001B:ju\u0016\f\u0001c]5{K\u001a{'\u000f\u00165sKNDw\u000e\u001c3\u0015\u0007Y\u00137\rC\u0003^k\u0001\u0007a\u000bC\u0003ek\u0001\u0007a+A\u0002uQJ\f\u0001bY1qC\u000eLG/\u001f\u000b\u0003-\u001eDQ\u0001\u001b\u001cA\u0002Y\u000bA\"\u001a=qK\u000e$X\rZ*ju\u0016\u0014\u0011\u0002S1tQV#\u0018\u000e\\:\u0016\u0005-L8CA\u001cH\u0003\u0019!\u0013N\\5uIQ\ta\u000e\u0005\u0002I_&\u0011\u0001o\u0011\u0002\u0005+:LG/\u0001\u000btSj,W*\u00199Ck\u000e\\W\r\u001e\"jiNK'0Z\u0001\u0012g&TX-T1q\u0005V\u001c7.\u001a;TSj,\u0017\u0001D3mK6D\u0015m\u001d5D_\u0012,GC\u0001,v\u0011\u001518\b1\u0001x\u0003\rYW-\u001f\t\u0003qfd\u0001\u0001B\u0003{o\t\u00071PA\u0004LKf$\u0016\u0010]3\u0012\u0005q|\bC\u0001%~\u0013\tq8IA\u0004O_RD\u0017N\\4\u0011\u0007!\u000b\t!C\u0002\u0002\u0004\r\u00131!\u00118z\u0003\u001dIW\u000e\u001d:pm\u0016$RAVA\u0005\u0003\u001bAa!a\u0003=\u0001\u00041\u0016!\u00025d_\u0012,\u0007BBA\by\u0001\u0007a+\u0001\u0003tK\u0016$\u0017A\u00068fqR\u0004vn]5uSZ,\u0007k\\<fe>3Gk^8\u0015\u0007Y\u000b)\u0002\u0003\u0004\u0002\u0018u\u0002\rAV\u0001\u0007i\u0006\u0014x-\u001a;\u0011\u0007a\fY\u0002\u0002\u0004\u0002\u001e\u0001\u0011\ra\u001f\u0002\u0002\u0003\u0006yq\f\\8bI\u001a\u000b7\r^8s?\u0012*\u0017\u000fF\u0002o\u0003GA\u0001\"!\n\u0004\u0003\u0003\u0005\rAV\u0001\u0004q\u0012\n\u0014!\u0002;bE2,WCAA\u0016!\u0015A\u0015QFA\u0019\u0013\r\tyc\u0011\u0002\u0006\u0003J\u0014\u0018-\u001f\t\b\u001b\u0006M\u0012\u0011DA\u001c\u0013\r\t)d\u0010\u0002\n\u0011\u0006\u001c\b.\u00128uef\u00042\u0001_A\u001d\t\u001d\tY\u0004\u0001b\u0001\u0003{\u0011Q!\u00128uef\fB!a\u0010\u00022A\u0019\u0001*!\u0011\n\u0007\u0005\r3I\u0001\u0003Ok2d\u0017!\u0003;bE2,w\fJ3r)\rq\u0017\u0011\n\u0005\n\u0003K)\u0011\u0011!a\u0001\u0003W\t\u0011\u0002^1cY\u0016\u001c\u0016N_3\u0002\u001bQ\f'\r\\3TSj,w\fJ3r)\rq\u0017\u0011\u000b\u0005\t\u0003K9\u0011\u0011!a\u0001-\u0006IA\u000f\u001b:fg\"|G\u000eZ\u0001\u000ei\"\u0014Xm\u001d5pY\u0012|F%Z9\u0015\u00079\fI\u0006\u0003\u0005\u0002&)\t\t\u00111\u0001W\u0003\u001d\u0019\u0018N_3nCB,\"!a\u0018\u0011\t!\u000biCV\u0001\fg&TX-\\1q?\u0012*\u0017\u000fF\u0002o\u0003KB\u0011\"!\n\r\u0003\u0003\u0005\r!a\u0018\u0002\u0013M,W\r\u001a<bYV,\u0017!D:fK\u00124\u0018\r\\;f?\u0012*\u0017\u000fF\u0002o\u0003[B\u0001\"!\n\u000f\u0003\u0003\u0005\rAV\u0001\u000ei\u0006\u0014G.Z*ju\u0016\u001cV-\u001a3\u0002\u0017%t\u0017\u000e^5bYNK'0Z\u0001\u0011S:LG/[1m)\"\u0014Xm\u001d5pY\u0012$2AVA<\u0011\u0015i\u0016\u00031\u0001W\u0003=Ig.\u001b;jC2\u001c\u0015\r]1dSRL\u0018A\u00057bgR\u0004v\u000e];mCR,G-\u00138eKb\fA!\u001b8jiR)a.!!\u0002\u0016\"9\u00111\u0011\u000bA\u0002\u0005\u0015\u0015AA5o!\u0011\t9)!%\u000e\u0005\u0005%%\u0002BAF\u0003\u001b\u000b!![8\u000b\u0005\u0005=\u0015\u0001\u00026bm\u0006LA!a%\u0002\n\n\trJ\u00196fGRLe\u000e];u'R\u0014X-Y7\t\u0011\u0005]E\u0003\"a\u0001\u00033\u000b\u0011B]3bI\u0016sGO]=\u0011\u000b!\u000bY*a\u000e\n\u0007\u0005u5I\u0001\u0005=Eft\u0017-\\3?\u0003-\u0019XM]5bY&TX\rV8\u0015\u000b9\f\u0019+!,\t\u000f\u0005\u0015V\u00031\u0001\u0002(\u0006\u0019q.\u001e;\u0011\t\u0005\u001d\u0015\u0011V\u0005\u0005\u0003W\u000bII\u0001\nPE*,7\r^(viB,Ho\u0015;sK\u0006l\u0007bBAX+\u0001\u0007\u0011\u0011W\u0001\u000boJLG/Z#oiJL\bC\u0002%\u00024\u0006]b.C\u0002\u00026\u000e\u0013\u0011BR;oGRLwN\\\u0019\u0002\u0013\u0019Lg\u000eZ#oiJLH\u0003BA\u001c\u0003wCaA\u001e\fA\u0002\u0005e\u0011A\u00034j]\u0012,e\u000e\u001e:zaQ1\u0011qGAa\u0003\u0007DaA^\fA\u0002\u0005e\u0001BBAc/\u0001\u0007a+A\u0001i\u0003!\tG\rZ#oiJLHc\u00018\u0002L\"9\u0011Q\u001a\rA\u0002\u0005]\u0012!A3\u0002\u0013\u0005$G-\u00128uef\u0004D#\u00028\u0002T\u0006U\u0007bBAg3\u0001\u0007\u0011q\u0007\u0005\u0007\u0003\u000bL\u0002\u0019\u0001,\u0002\u001d\u0019Lg\u000eZ(s\u0003\u0012$WI\u001c;ssR1\u0011qGAn\u0003;DaA\u001e\u000eA\u0002\u0005e\u0001bBAp5\u0001\u0007\u0011\u0011]\u0001\u0006m\u0006dW/\u001a\t\u0004q\u0006\rHABAs\u0001\t\u00071PA\u0001C\u00039\u0019'/Z1uK:+w/\u00128uef$b!a\u000e\u0002l\u00065\bB\u0002<\u001c\u0001\u0004\tI\u0002C\u0004\u0002`n\u0001\r!!9\u0002\u0017I,Wn\u001c<f\u000b:$(/\u001f\u000b\u0005\u0003o\t\u0019\u0010\u0003\u0004w9\u0001\u0007\u0011\u0011D\u0001\re\u0016lwN^3F]R\u0014\u0018\u0010\r\u000b\u0007\u0003o\tI0a?\t\rYl\u0002\u0019AA\r\u0011\u0019\t)-\ba\u0001-\u0006yQM\u001c;sS\u0016\u001c\u0018\n^3sCR|'/\u0006\u0002\u0003\u0002A1!1\u0001B\u0003\u0003oi\u0011!Q\u0005\u0004\u0005\u000f\t%\u0001C%uKJ\fGo\u001c:\u0002\u0019\u0019|'/Z1dQ\u0016sGO]=\u0016\t\t5!q\u0003\u000b\u0004]\n=\u0001b\u0002B\t?\u0001\u0007!1C\u0001\u0002MB9\u0001*a-\u00028\tU\u0001c\u0001=\u0003\u0018\u00111!\u0011D\u0010C\u0002m\u0014\u0011!V\u0001\u000bG2,\u0017M\u001d+bE2,\u0017A\u0002:fg&TX\rF\u0002o\u0005CAaAa\t\"\u0001\u00041\u0016a\u00028foNK'0Z\u0001\r]:\u001c\u0016N_3NCB\fE\r\u001a\u000b\u0004]\n%\u0002BBAcE\u0001\u0007a+A\bo]NK'0Z'baJ+Wn\u001c<f)\rq'q\u0006\u0005\u0007\u0003\u000b\u001c\u0003\u0019\u0001,\u0002\u001d9t7+\u001b>f\u001b\u0006\u0004(+Z:fiR\u0019aN!\u000e\t\r\t]B\u00051\u0001W\u0003-!\u0018M\u00197f\u0019\u0016tw\r\u001e5\u0002'Q|G/\u00197TSj,W*\u00199Ck\u000e\\W\r^:\u0002\u001f\r\fGnY*ju\u0016l\u0015\r]*ju\u0016$2A\u0016B \u0011\u0019\u00119D\na\u0001-\u0006Y1/\u001b>f\u001b\u0006\u0004\u0018J\\5u)\rq'Q\t\u0005\u0007\u0005o9\u0003\u0019\u0001,\u0002+ML'0Z'ba&s\u0017\u000e^!oIJ+'-^5mI\u0006a\u0001O]5oiNK'0Z'ba\u0006q1/\u001b>f\u001b\u0006\u0004H)[:bE2,\u0017\u0001E5t'&TX-T1q\t\u00164\u0017N\\3e+\t\u0011\t\u0006E\u0002I\u0005'J1A!\u0016D\u0005\u001d\u0011un\u001c7fC:\f\u0011#\u00197xCf\u001c\u0018J\\5u'&TX-T1q\u0003))G.Z7FcV\fGn\u001d\u000b\u0007\u0005#\u0012iF!\u0019\t\u000f\t}S\u00061\u0001\u0002\u001a\u0005!1.Z=2\u0011\u001d\u0011\u0019'\fa\u0001\u00033\tAa[3ze\u0005)\u0011N\u001c3fqR\u0019aK!\u001b\t\r\u0005-a\u00061\u0001W\u0001")
public interface HashTable<A, B, Entry extends HashEntry<A, Entry>>
extends HashUtils<A> {
    public int _loadFactor();

    public void _loadFactor_$eq(int var1);

    public HashEntry<A, Entry>[] table();

    public void table_$eq(HashEntry<A, Entry>[] var1);

    public int tableSize();

    public void tableSize_$eq(int var1);

    public static /* synthetic */ int size$(HashTable $this) {
        return $this.size();
    }

    default public int size() {
        return this.tableSize();
    }

    public int threshold();

    public void threshold_$eq(int var1);

    public int[] sizemap();

    public void sizemap_$eq(int[] var1);

    public int seedvalue();

    public void seedvalue_$eq(int var1);

    public static /* synthetic */ int tableSizeSeed$(HashTable $this) {
        return $this.tableSizeSeed();
    }

    default public int tableSizeSeed() {
        return Integer.bitCount(this.table().length - 1);
    }

    public static /* synthetic */ int initialSize$(HashTable $this) {
        return $this.initialSize();
    }

    default public int initialSize() {
        return 16;
    }

    private int initialThreshold(int _loadFactor) {
        return (int)((long)this.initialCapacity() * (long)_loadFactor / (long)1000);
    }

    private int initialCapacity() {
        int capacity_expectedSize = this.initialSize();
        return 1 << -Integer.numberOfLeadingZeros(capacity_expectedSize - 1);
    }

    public static /* synthetic */ int scala$collection$mutable$HashTable$$lastPopulatedIndex$(HashTable $this) {
        return $this.scala$collection$mutable$HashTable$$lastPopulatedIndex();
    }

    default public int scala$collection$mutable$HashTable$$lastPopulatedIndex() {
        int idx;
        for (idx = this.table().length - 1; this.table()[idx] == null && idx > 0; --idx) {
        }
        return idx;
    }

    public static /* synthetic */ void init$(HashTable $this, ObjectInputStream in, Function0 readEntry2) {
        $this.init(in, readEntry2);
    }

    /*
     * WARNING - void declaration
     */
    default public void init(ObjectInputStream in, Function0<Entry> readEntry2) {
        void newThreshold_size;
        this._loadFactor_$eq(in.readInt());
        Predef$.MODULE$.assert(this._loadFactor() > 0);
        int size = in.readInt();
        this.tableSize_$eq(0);
        Predef$.MODULE$.assert(size >= 0);
        this.seedvalue_$eq(in.readInt());
        boolean smDefined = in.readBoolean();
        int sizeForThreshold__loadFactor = this._loadFactor();
        int capacity_expectedSize = (int)((long)size * (long)1000 / (long)sizeForThreshold__loadFactor);
        this.table_$eq(new HashEntry[1 << -Integer.numberOfLeadingZeros(capacity_expectedSize - 1)]);
        int n = this.table().length;
        int newThreshold__loadFactor = this._loadFactor();
        this.threshold_$eq((int)((long)newThreshold_size * (long)newThreshold__loadFactor / (long)1000));
        if (smDefined) {
            this.sizeMapInit(this.table().length);
        } else {
            this.sizemap_$eq(null);
        }
        for (int index = 0; index < size; ++index) {
            this.addEntry((HashEntry)readEntry2.apply());
        }
    }

    public static /* synthetic */ void serializeTo$(HashTable $this, ObjectOutputStream out, Function1 writeEntry) {
        $this.serializeTo(out, writeEntry);
    }

    default public void serializeTo(ObjectOutputStream out, Function1<Entry, BoxedUnit> writeEntry) {
        out.writeInt(this._loadFactor());
        out.writeInt(this.tableSize());
        out.writeInt(this.seedvalue());
        out.writeBoolean(this.isSizeMapDefined());
        this.foreachEntry(writeEntry);
    }

    public static /* synthetic */ HashEntry findEntry$(HashTable $this, Object key) {
        return $this.findEntry(key);
    }

    default public Entry findEntry(A key) {
        return this.findEntry0(key, this.index(this.elemHashCode(key)));
    }

    public static /* synthetic */ HashEntry findEntry0$(HashTable $this, Object key, int h2) {
        return $this.findEntry0(key, h2);
    }

    default public Entry findEntry0(A key, int h2) {
        HashEntry<A, Object> e;
        for (e = this.table()[h2]; e != null && !this.elemEquals(e.key(), key); e = e.next()) {
        }
        return (Entry)e;
    }

    public static /* synthetic */ void addEntry$(HashTable $this, HashEntry e) {
        $this.addEntry(e);
    }

    default public void addEntry(Entry e) {
        this.addEntry0(e, this.index(this.elemHashCode(e.key())));
    }

    public static /* synthetic */ void addEntry0$(HashTable $this, HashEntry e, int h2) {
        $this.addEntry0(e, h2);
    }

    default public void addEntry0(Entry e, int h2) {
        e.next_$eq(this.table()[h2]);
        this.table()[h2] = e;
        this.tableSize_$eq(this.tableSize() + 1);
        this.nnSizeMapAdd(h2);
        if (this.tableSize() > this.threshold()) {
            this.resize(2 * this.table().length);
            return;
        }
    }

    public static /* synthetic */ HashEntry findOrAddEntry$(HashTable $this, Object key, Object value) {
        return $this.findOrAddEntry(key, value);
    }

    default public Entry findOrAddEntry(A key, B value) {
        int h2 = this.index(this.elemHashCode(key));
        Entry e = this.findEntry0(key, h2);
        if (e != null) {
            return e;
        }
        this.addEntry0(this.createNewEntry(key, value), h2);
        return null;
    }

    public Entry createNewEntry(A var1, B var2);

    public static /* synthetic */ HashEntry removeEntry$(HashTable $this, Object key) {
        return $this.removeEntry(key);
    }

    default public Entry removeEntry(A key) {
        return this.removeEntry0(key, this.index(this.elemHashCode(key)));
    }

    public static /* synthetic */ HashEntry removeEntry0$(HashTable $this, Object key, int h2) {
        return $this.removeEntry0(key, h2);
    }

    default public Entry removeEntry0(A key, int h2) {
        HashEntry<A, Object> e = this.table()[h2];
        if (e != null) {
            Object e1;
            if (this.elemEquals(e.key(), key)) {
                this.table()[h2] = e.next();
                this.tableSize_$eq(this.tableSize() - 1);
                this.nnSizeMapRemove(h2);
                e.next_$eq(null);
                return (Entry)e;
            }
            for (e1 = e.next(); e1 != null && !this.elemEquals(e1.key(), key); e1 = e1.next()) {
                e = e1;
            }
            if (e1 != null) {
                e.next_$eq(e1.next());
                this.tableSize_$eq(this.tableSize() - 1);
                this.nnSizeMapRemove(h2);
                e1.next_$eq(null);
                return (Entry)e1;
            }
        }
        return null;
    }

    public static /* synthetic */ Iterator entriesIterator$(HashTable $this) {
        return $this.entriesIterator();
    }

    default public Iterator<Entry> entriesIterator() {
        return new AbstractIterator<Entry>(this){
            private final HashEntry<A, Entry>[] iterTable;
            private int idx;
            private HashEntry<A, Entry> es;

            private HashEntry<A, Entry>[] iterTable() {
                return this.iterTable;
            }

            private int idx() {
                return this.idx;
            }

            private void idx_$eq(int x$1) {
                this.idx = x$1;
            }

            private HashEntry<A, Entry> es() {
                return this.es;
            }

            private void es_$eq(HashEntry<A, Entry> x$1) {
                this.es = x$1;
            }

            public boolean hasNext() {
                return this.es() != null;
            }

            public Entry next() {
                HashEntry<A, Entry> res = this.es();
                this.es_$eq((HashEntry<A, Entry>)this.es().next());
                while (this.es() == null && this.idx() > 0) {
                    this.idx_$eq(this.idx() - 1);
                    this.es_$eq(this.iterTable()[this.idx()]);
                }
                return (Entry)res;
            }
            {
                this.iterTable = $outer.table();
                this.idx = $outer.scala$collection$mutable$HashTable$$lastPopulatedIndex();
                this.es = this.iterTable()[this.idx()];
            }
        };
    }

    public static /* synthetic */ void foreachEntry$(HashTable $this, Function1 f) {
        $this.foreachEntry(f);
    }

    default public <U> void foreachEntry(Function1<Entry, U> f) {
        HashEntry<A, Entry>[] iterTable = this.table();
        int idx = this.scala$collection$mutable$HashTable$$lastPopulatedIndex();
        HashEntry<A, Object> es = iterTable[idx];
        while (es != null) {
            Entry next = es.next();
            f.apply(es);
            es = next;
            while (es == null && idx > 0) {
                es = iterTable[--idx];
            }
        }
    }

    public static /* synthetic */ void clearTable$(HashTable $this) {
        $this.clearTable();
    }

    default public void clearTable() {
        for (int i = this.table().length - 1; i >= 0; --i) {
            this.table()[i] = null;
        }
        this.tableSize_$eq(0);
        this.nnSizeMapReset(0);
    }

    private void resize(int newSize) {
        HashEntry<A, Entry>[] oldTable = this.table();
        this.table_$eq(new HashEntry[newSize]);
        this.nnSizeMapReset(this.table().length);
        for (int i = oldTable.length - 1; i >= 0; --i) {
            HashEntry<A, Object> e = oldTable[i];
            while (e != null) {
                int h2 = this.index(this.elemHashCode(e.key()));
                Entry e1 = e.next();
                e.next_$eq(this.table()[h2]);
                this.table()[h2] = e;
                e = e1;
                this.nnSizeMapAdd(h2);
            }
        }
        int newThreshold__loadFactor = this._loadFactor();
        this.threshold_$eq((int)((long)newSize * (long)newThreshold__loadFactor / (long)1000));
    }

    public static /* synthetic */ void nnSizeMapAdd$(HashTable $this, int h2) {
        $this.nnSizeMapAdd(h2);
    }

    default public void nnSizeMapAdd(int h2) {
        if (this.sizemap() != null) {
            int[] nArray = this.sizemap();
            int n = h2 >> 5;
            nArray[n] = nArray[n] + 1;
            return;
        }
    }

    public static /* synthetic */ void nnSizeMapRemove$(HashTable $this, int h2) {
        $this.nnSizeMapRemove(h2);
    }

    default public void nnSizeMapRemove(int h2) {
        if (this.sizemap() != null) {
            int[] nArray = this.sizemap();
            int n = h2 >> 5;
            nArray[n] = nArray[n] - 1;
            return;
        }
    }

    public static /* synthetic */ void nnSizeMapReset$(HashTable $this, int tableLength) {
        $this.nnSizeMapReset(tableLength);
    }

    default public void nnSizeMapReset(int tableLength) {
        if (this.sizemap() != null) {
            int nsize = (tableLength >> 5) + 1;
            if (this.sizemap().length != nsize) {
                this.sizemap_$eq(new int[nsize]);
                return;
            }
            Arrays.fill(this.sizemap(), 0);
            return;
        }
    }

    public static /* synthetic */ int totalSizeMapBuckets$(HashTable $this) {
        return $this.totalSizeMapBuckets();
    }

    default public int totalSizeMapBuckets() {
        if (1 << 5 < this.table().length) {
            return 1;
        }
        return this.table().length / (1 << 5);
    }

    public static /* synthetic */ int calcSizeMapSize$(HashTable $this, int tableLength) {
        return $this.calcSizeMapSize(tableLength);
    }

    default public int calcSizeMapSize(int tableLength) {
        return (tableLength >> 5) + 1;
    }

    public static /* synthetic */ void sizeMapInit$(HashTable $this, int tableLength) {
        $this.sizeMapInit(tableLength);
    }

    default public void sizeMapInit(int tableLength) {
        this.sizemap_$eq(new int[(tableLength >> 5) + 1]);
    }

    public static /* synthetic */ void sizeMapInitAndRebuild$(HashTable $this) {
        $this.sizeMapInitAndRebuild();
    }

    default public void sizeMapInitAndRebuild() {
        this.sizeMapInit(this.table().length);
        int tableidx = 0;
        HashEntry<A, Entry>[] tbl = this.table();
        int tableuntil = tbl.length < 1 << 5 ? tbl.length : 1 << 5;
        int totalbuckets = this.totalSizeMapBuckets();
        for (int bucketidx = 0; bucketidx < totalbuckets; ++bucketidx) {
            int currbucketsize = 0;
            while (tableidx < tableuntil) {
                for (HashEntry<A, Object> e = tbl[tableidx]; e != null; e = e.next()) {
                    ++currbucketsize;
                }
                ++tableidx;
            }
            this.sizemap()[bucketidx] = currbucketsize;
            tableuntil += 1 << 5;
        }
    }

    public static /* synthetic */ void printSizeMap$(HashTable $this) {
        $this.printSizeMap();
    }

    /*
     * WARNING - void declaration
     */
    default public void printSizeMap() {
        void to_factory;
        ArraySeq.ofInt ofInt2 = Predef$.MODULE$.wrapIntArray(this.sizemap());
        List$ toFactory_factory = List$.MODULE$;
        Object var3_1 = null;
        IterableFactory.ToFactory toFactory = new IterableFactory.ToFactory(toFactory_factory);
        if (ofInt2 == null) {
            throw null;
        }
        ArraySeq.ofInt to_this = ofInt2;
        Object var1_3 = null;
        toFactory = null;
        Object println_x = to_factory.fromSpecific(to_this);
        Console$.MODULE$.println(println_x);
    }

    public static /* synthetic */ void sizeMapDisable$(HashTable $this) {
        $this.sizeMapDisable();
    }

    default public void sizeMapDisable() {
        this.sizemap_$eq(null);
    }

    public static /* synthetic */ boolean isSizeMapDefined$(HashTable $this) {
        return $this.isSizeMapDefined();
    }

    default public boolean isSizeMapDefined() {
        return this.sizemap() != null;
    }

    public static /* synthetic */ boolean alwaysInitSizeMap$(HashTable $this) {
        return $this.alwaysInitSizeMap();
    }

    default public boolean alwaysInitSizeMap() {
        return false;
    }

    public static /* synthetic */ boolean elemEquals$(HashTable $this, Object key1, Object key2) {
        return $this.elemEquals(key1, key2);
    }

    default public boolean elemEquals(A key1, A key2) {
        return BoxesRunTime.equals(key1, key2);
    }

    public static /* synthetic */ int index$(HashTable $this, int hcode) {
        return $this.index(hcode);
    }

    default public int index(int hcode) {
        int ones = this.table().length - 1;
        int exponent = Integer.numberOfLeadingZeros(ones);
        return this.improve(hcode, this.seedvalue()) >>> exponent & ones;
    }

    public static void $init$(HashTable $this) {
        $this._loadFactor_$eq(750);
        $this.table_$eq(new HashEntry[$this.initialCapacity()]);
        $this.tableSize_$eq(0);
        $this.threshold_$eq($this.initialThreshold($this._loadFactor()));
        $this.sizemap_$eq(null);
        $this.seedvalue_$eq($this.tableSizeSeed());
    }

    public static interface HashUtils<KeyType> {
        public static /* synthetic */ int sizeMapBucketBitSize$(HashUtils $this) {
            return $this.sizeMapBucketBitSize();
        }

        default public int sizeMapBucketBitSize() {
            return 5;
        }

        public static /* synthetic */ int sizeMapBucketSize$(HashUtils $this) {
            return $this.sizeMapBucketSize();
        }

        default public int sizeMapBucketSize() {
            return 1 << 5;
        }

        public static /* synthetic */ int elemHashCode$(HashUtils $this, Object key) {
            return $this.elemHashCode(key);
        }

        default public int elemHashCode(KeyType key) {
            return Statics.anyHash(key);
        }

        public static /* synthetic */ int improve$(HashUtils $this, int hcode, int seed) {
            return $this.improve(hcode, seed);
        }

        default public int improve(int hcode, int seed) {
            return Integer.rotateRight(Integer.reverseBytes(hcode * -1640532531) * -1640532531, seed);
        }

        public static void $init$(HashUtils $this) {
        }
    }
}

