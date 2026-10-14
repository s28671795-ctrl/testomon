/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import scala.Function0;
import scala.collection.BitSetOps;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.Set;
import scala.collection.SortedSet;
import scala.collection.View;
import scala.collection.immutable.BitSet$;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005ecaB\u000e\u001d!\u0003\r\t!\t\u0005\u0006c\u0001!\tA\r\u0005\u0006m\u0001!\tf\u000e\u0005\u0006{\u0001!\tF\u0010\u0005\u0006\u000b\u0002!\tE\u0012\u0005\u0007\u000f\u0002\u0001K\u0011\u000b%\t\u000bE\u0003A\u0011\t*\b\u000bYc\u0002\u0012A,\u0007\u000bma\u0002\u0012\u0001-\t\u000bqCA\u0011A/\t\u0011yC!\u0019!C\u00039}Caa\u0019\u0005!\u0002\u001b\u0001\u0007\u0002\u00033\t\u0005\u0004%)\u0001H3\t\r%D\u0001\u0015!\u0004g\u0011\u0015)\u0005\u0002\"\u0001G\u0011\u0015Q\u0007\u0002\"\u0001?\u0011\u00151\u0004\u0002\"\u0001l\r\u0019q\u0007\"!\u0001\u001d_\"A\u0011(\u0005BC\u0002\u0013Ea\t\u0003\u0005x#\t\u0005\t\u0015!\u00031\u0011\u0015a\u0016\u0003\"\u0001}\u0011-\t\t!\u0005a\u0001\u0002\u0004%\t\"a\u0001\t\u0017\u0005E\u0011\u00031AA\u0002\u0013E\u00111\u0003\u0005\f\u00033\t\u0002\u0019!A!B\u0013\t)\u0001\u0003\u0005\u0002\u001eE\u0001K\u0011BA\u0010\u0011!\t\t$\u0005Q\u0005\n\u0005M\u0002\u0002CA #\u00016\t\"!\u0011\u0003\r\tKGoU3u\u0015\tib$\u0001\u0006d_2dWm\u0019;j_:T\u0011aH\u0001\u0006g\u000e\fG.Y\u0002\u0001'\u0011\u0001!EJ\u0017\u0011\u0005\r\"S\"\u0001\u0010\n\u0005\u0015r\"AB!osJ+g\rE\u0002(Q)j\u0011\u0001H\u0005\u0003Sq\u0011\u0011bU8si\u0016$7+\u001a;\u0011\u0005\rZ\u0013B\u0001\u0017\u001f\u0005\rIe\u000e\u001e\t\u0004O9\u0002\u0014BA\u0018\u001d\u0005%\u0011\u0015\u000e^*fi>\u00038\u000f\u0005\u0002(\u0001\u00051A%\u001b8ji\u0012\"\u0012a\r\t\u0003GQJ!!\u000e\u0010\u0003\tUs\u0017\u000e^\u0001\rMJ|Wn\u00159fG&4\u0017n\u0019\u000b\u0003aaBQ!\u000f\u0002A\u0002i\nAaY8mYB\u0019qe\u000f\u0016\n\u0005qb\"\u0001D%uKJ\f'\r\\3P]\u000e,\u0017A\u00058foN\u0003XmY5gS\u000e\u0014U/\u001b7eKJ,\u0012a\u0010\t\u0005\u0001\u000eS\u0003'D\u0001B\u0015\t\u0011E$A\u0004nkR\f'\r\\3\n\u0005\u0011\u000b%a\u0002\"vS2$WM]\u0001\u0006K6\u0004H/_\u000b\u0002a\u0005a1\u000f\u001e:j]\u001e\u0004&/\u001a4jqV\t\u0011\n\u0005\u0002K\u001f6\t1J\u0003\u0002M\u001b\u0006!A.\u00198h\u0015\u0005q\u0015\u0001\u00026bm\u0006L!\u0001U&\u0003\rM#(/\u001b8h\u0003!)hn]8si\u0016$W#A*\u0011\u0007\u001d\"&&\u0003\u0002V9\t\u00191+\u001a;\u0002\r\tKGoU3u!\t9\u0003bE\u0002\tEe\u0003Ba\n.+a%\u00111\f\b\u0002\u0018'B,7-\u001b4jG&#XM]1cY\u00164\u0015m\u0019;pef\fa\u0001P5oSRtD#A,\u0002\r=\u0014H-T:h+\u0005\u0001w\"A1\"\u0003\t\faPT8!S6\u0004H.[2ji\u0002z%\u000fZ3sS:<7\fJ>C{v\u0003cm\\;oI\u0002\"x\u000e\t2vS2$\u0007%\u0019\u0011T_J$X\rZ*fin#3PQ?^]\u0001Jv.\u001e\u0011nCf\u0004s/\u00198uAQ|\u0007%\u001e9dCN$\b\u0005^8!C\u0002\u001aV\r^.J]Rl\u0006EZ5sgR\u0004#-\u001f\u0011dC2d\u0017N\\4!AVt7o\u001c:uK\u0012\u0004g&A\u0004pe\u0012l5o\u001a\u0011\u0002\u0013iL\u0007o\u0014:e\u001bN<W#\u00014\u0010\u0003\u001d\f\u0013\u0001[\u0001\u0002\f9{\u0007%[7qY&\u001c\u0017\u000e\u001e\u0011Pe\u0012,'/\u001b8h7\u0012Z()`/!M>,h\u000e\u001a\u0011u_\u0002\u0012W/\u001b7eA\u0005\u00043k\u001c:uK\u0012\u001cV\r^.)\u0013:$H\u0006\t\u0013|\u0005vLSL\f\u0011Z_V\u0004S.Y=!o\u0006tG\u000f\t;pAU\u00048-Y:uAQ|\u0007%\u0019\u0011TKR\\\u0016J\u001c;^A\u0019L'o\u001d;!Ef\u00043-\u00197mS:<\u0007\u0005Y;og>\u0014H/\u001a3a]\u0005Q!0\u001b9Pe\u0012l5o\u001a\u0011\u0002\u00159,wOQ;jY\u0012,'\u000f\u0006\u00021Y\")Q\u000e\u0005a\u0001u\u0005\u0011\u0011\u000e\u001e\u0002\u0013'\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8Qe>D\u0018pE\u0002\u0012EA\u0004\"!\u001d;\u000f\u0005\r\u0012\u0018BA:\u001f\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u001e<\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005Mt\u0012!B2pY2\u0004\u0003FA\nz!\t\u0019#0\u0003\u0002|=\tIAO]1og&,g\u000e\u001e\u000b\u0003{~\u0004\"A`\t\u000e\u0003!AQ!\u000f\u000bA\u0002A\nQ!\u001a7f[N,\"!!\u0002\u0011\u000b\r\n9!a\u0003\n\u0007\u0005%aDA\u0003BeJ\f\u0017\u0010E\u0002$\u0003\u001bI1!a\u0004\u001f\u0005\u0011auN\\4\u0002\u0013\u0015dW-\\:`I\u0015\fHcA\u001a\u0002\u0016!I\u0011q\u0003\f\u0002\u0002\u0003\u0007\u0011QA\u0001\u0004q\u0012\n\u0014AB3mK6\u001c\b\u0005\u000b\u0002\u0018s\u0006YqO]5uK>\u0013'.Z2u)\r\u0019\u0014\u0011\u0005\u0005\b\u0003GA\u0002\u0019AA\u0013\u0003\ryW\u000f\u001e\t\u0005\u0003O\ti#\u0004\u0002\u0002*)\u0019\u00111F'\u0002\u0005%|\u0017\u0002BA\u0018\u0003S\u0011!c\u00142kK\u000e$x*\u001e;qkR\u001cFO]3b[\u0006Q!/Z1e\u001f\nTWm\u0019;\u0015\u0007M\n)\u0004C\u0004\u00028e\u0001\r!!\u000f\u0002\u0005%t\u0007\u0003BA\u0014\u0003wIA!!\u0010\u0002*\t\trJ\u00196fGRLe\u000e];u'R\u0014X-Y7\u0002\u0017I,\u0017\r\u001a*fg>dg/\u001a\u000b\u0003\u0003\u0007\u00022aIA#\u0013\r\t9E\b\u0002\u0004\u0003:L\bfB\t\u0002L\u0005E\u00131\u000b\t\u0004G\u00055\u0013bAA(=\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0007!:\u0001\"a\u0013\u0002R\u0005M\u0003fB\u0004\u0002L\u0005E\u00131\u000b")
public interface BitSet
extends SortedSet<Object>,
BitSetOps<BitSet> {
    public static Builder<Object, BitSet> newBuilder() {
        return BitSet$.MODULE$.newBuilder();
    }

    public static Factory<Object, BitSet> specificIterableFactory() {
        return scala.collection.BitSet$.MODULE$;
    }

    public static Object fill(int n, Function0 elem) {
        View.Fill fromSpecific_it = new View.Fill(n, elem);
        return BitSet$.MODULE$.fromSpecific((IterableOnce)fromSpecific_it);
    }

    public static /* synthetic */ BitSet fromSpecific$(BitSet $this, IterableOnce coll) {
        return $this.fromSpecific(coll);
    }

    @Override
    default public BitSet fromSpecific(IterableOnce<Object> coll) {
        return (BitSet)this.bitSetFactory().fromSpecific(coll);
    }

    public static /* synthetic */ Builder newSpecificBuilder$(BitSet $this) {
        return $this.newSpecificBuilder();
    }

    @Override
    default public Builder<Object, BitSet> newSpecificBuilder() {
        return this.bitSetFactory().newBuilder();
    }

    public static /* synthetic */ BitSet empty$(BitSet $this) {
        return $this.empty();
    }

    @Override
    default public BitSet empty() {
        return (BitSet)this.bitSetFactory().empty();
    }

    public static /* synthetic */ String stringPrefix$(BitSet $this) {
        return $this.stringPrefix();
    }

    @Override
    default public String stringPrefix() {
        return "BitSet";
    }

    public static /* synthetic */ Set unsorted$(BitSet $this) {
        return $this.unsorted();
    }

    @Override
    default public Set<Object> unsorted() {
        return this;
    }

    public static void $init$(BitSet $this) {
    }

    public static abstract class SerializationProxy
    implements Serializable {
        private static final long serialVersionUID = 3L;
        private final transient BitSet coll;
        private transient long[] elems;

        public BitSet coll() {
            return this.coll;
        }

        public long[] elems() {
            return this.elems;
        }

        public void elems_$eq(long[] x$1) {
            this.elems = x$1;
        }

        private void writeObject(ObjectOutputStream out) {
            out.defaultWriteObject();
            int nwords = this.coll().nwords();
            out.writeInt(nwords);
            for (int i = 0; i < nwords; ++i) {
                out.writeLong(this.coll().word(i));
            }
        }

        private void readObject(ObjectInputStream in) {
            in.defaultReadObject();
            int nwords = in.readInt();
            this.elems_$eq(new long[nwords]);
            for (int i = 0; i < nwords; ++i) {
                this.elems()[i] = in.readLong();
            }
        }

        public abstract Object readResolve();

        public SerializationProxy(BitSet coll) {
            this.coll = coll;
        }
    }
}

