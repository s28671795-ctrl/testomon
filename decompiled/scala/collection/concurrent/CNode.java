/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.concurrent;

import java.util.concurrent.ThreadLocalRandom;
import scala.Array$;
import scala.MatchError;
import scala.Predef$;
import scala.Some;
import scala.collection.AbstractIterable;
import scala.collection.AbstractIterator;
import scala.collection.Iterable;
import scala.collection.Iterable$;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.Seq;
import scala.collection.StringOps$;
import scala.collection.View;
import scala.collection.concurrent.BasicNode;
import scala.collection.concurrent.CNode$;
import scala.collection.concurrent.CNodeBase;
import scala.collection.concurrent.Gen;
import scala.collection.concurrent.INode;
import scala.collection.concurrent.MainNode;
import scala.collection.concurrent.SNode;
import scala.collection.concurrent.TNode;
import scala.collection.concurrent.TrieMap;
import scala.collection.mutable.ArrayBuilder;
import scala.collection.mutable.ArraySeq;
import scala.math.Equiv;
import scala.math.Ordering$String$;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001de!\u0002\r\u001a\u0005my\u0002\u0002C\u001b\u0001\u0005\u000b\u0007I\u0011\u0001\u001c\t\u0011i\u0002!\u0011!Q\u0001\n]B\u0001b\u000f\u0001\u0003\u0006\u0004%\t\u0001\u0010\u0005\t\u0007\u0002\u0011\t\u0011)A\u0005{!AA\t\u0001BC\u0002\u0013\u0005Q\t\u0003\u0005J\u0001\t\u0005\t\u0015!\u0003G\u0011\u0015Q\u0005\u0001\"\u0001L\u0011\u0015\u0001\u0006\u0001\"\u0001R\u0011\u00159\u0006\u0001\"\u0001Y\u0011\u0015I\u0006\u0001\"\u0003[\u0011\u0015y\u0006\u0001\"\u0001a\u0011\u00151\u0007\u0001\"\u0001h\u0011\u0015a\u0007\u0001\"\u0001n\u0011\u00159\b\u0001\"\u0001y\u0011\u0015a\b\u0001\"\u0003~\u0011\u001d\tY\u0001\u0001C\u0001\u0003\u001bAq!!\u0007\u0001\t\u0003\tY\u0002C\u0004\u0002$\u0001!\t!!\n\t\u000f\u0005}\u0002\u0001\"\u0011\u0002B\u001dA\u00111I\r\t\u0002e\t)EB\u0004\u00193!\u0005\u0011$a\u0012\t\r)+B\u0011AA%\u0011\u001d\tY%\u0006C\u0001\u0003\u001b\u0012Qa\u0011(pI\u0016T!AG\u000e\u0002\u0015\r|gnY;se\u0016tGO\u0003\u0002\u001d;\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003y\tQa]2bY\u0006,2\u0001I\u00144'\t\u0001\u0011\u0005\u0005\u0003#G\u0015\u0012T\"A\r\n\u0005\u0011J\"!C\"O_\u0012,')Y:f!\t1s\u0005\u0004\u0001\u0005\u000b!\u0002!\u0019\u0001\u0016\u0003\u0003-\u001b\u0001!\u0005\u0002,_A\u0011A&L\u0007\u0002;%\u0011a&\b\u0002\b\u001d>$\b.\u001b8h!\ta\u0003'\u0003\u00022;\t\u0019\u0011I\\=\u0011\u0005\u0019\u001aD!\u0002\u001b\u0001\u0005\u0004Q#!\u0001,\u0002\r\tLG/\\1q+\u00059\u0004C\u0001\u00179\u0013\tITDA\u0002J]R\fqAY5u[\u0006\u0004\b%A\u0003beJ\f\u00170F\u0001>!\rac\bQ\u0005\u0003\u007fu\u0011Q!\u0011:sCf\u0004\"AI!\n\u0005\tK\"!\u0003\"bg&\u001cgj\u001c3f\u0003\u0019\t'O]1zA\u0005\u0019q-\u001a8\u0016\u0003\u0019\u0003\"AI$\n\u0005!K\"aA$f]\u0006!q-\u001a8!\u0003\u0019a\u0014N\\5u}Q!A*\u0014(P!\u0011\u0011\u0003!\n\u001a\t\u000bU:\u0001\u0019A\u001c\t\u000bm:\u0001\u0019A\u001f\t\u000b\u0011;\u0001\u0019\u0001$\u0002\u0015\r\f7\r[3e'&TX\r\u0006\u00028%\")1\u000b\u0003a\u0001)\u0006\u00111\r\u001e\t\u0003YUK!AV\u000f\u0003\r\u0005s\u0017PU3g\u0003%Ygn\\<o'&TX\rF\u00018\u0003-\u0019w.\u001c9vi\u0016\u001c\u0016N_3\u0015\u0005]Z\u0006\"B*\u000b\u0001\u0004a\u0006\u0003\u0002\u0012^KIJ!AX\r\u0003\u000fQ\u0013\u0018.Z'ba\u0006IQ\u000f\u001d3bi\u0016$\u0017\t\u001e\u000b\u0005\u0019\u0006\u001cW\rC\u0003c\u0017\u0001\u0007q'A\u0002q_NDQ\u0001Z\u0006A\u0002\u0001\u000b!A\u001c8\t\u000b\u0011[\u0001\u0019\u0001$\u0002\u0013I,Wn\u001c<fI\u0006#H\u0003\u0002'iS.DQA\u0019\u0007A\u0002]BQA\u001b\u0007A\u0002]\nAA\u001a7bO\")A\t\u0004a\u0001\r\u0006Q\u0011N\\:feR,G-\u0011;\u0015\u000f1sw\u000e\u001d:um\")!-\u0004a\u0001o!)!.\u0004a\u0001o!)\u0011/\u0004a\u0001K\u0005\t1\u000eC\u0003t\u001b\u0001\u0007!'A\u0001w\u0011\u0015)X\u00021\u00018\u0003\tA7\rC\u0003E\u001b\u0001\u0007a)A\u0004sK:,w/\u001a3\u0015\u00071K8\u0010C\u0003{\u001d\u0001\u0007a)\u0001\u0003oO\u0016t\u0007\"B*\u000f\u0001\u0004a\u0016!\u0003:fgV\u0014(/Z2u)\u0011\u0001e0a\u0002\t\r}|\u0001\u0019AA\u0001\u0003\u0015Ign\u001c3f!\u0015\u0011\u00131A\u00133\u0013\r\t)!\u0007\u0002\u0006\u0013:{G-\u001a\u0005\u0007\u0003\u0013y\u0001\u0019\u0001+\u0002\u0013%tw\u000eZ3nC&t\u0017\u0001\u0004;p\u0007>tGO]1di\u0016$G\u0003BA\b\u0003+\u0001RAIA\tKIJ1!a\u0005\u001a\u0005!i\u0015-\u001b8O_\u0012,\u0007BBA\f!\u0001\u0007q'A\u0002mKZ\fA\u0002^8D_6\u0004(/Z:tK\u0012$\u0002\"a\u0004\u0002\u001e\u0005}\u0011\u0011\u0005\u0005\u0006'F\u0001\r\u0001\u0018\u0005\u0007\u0003/\t\u0002\u0019A\u001c\t\u000b\u0011\u000b\u0002\u0019\u0001$\u0002\rM$(/\u001b8h)\u0011\t9#!\u0010\u0011\t\u0005%\u0012q\u0007\b\u0005\u0003W\t\u0019\u0004E\u0002\u0002.ui!!a\f\u000b\u0007\u0005E\u0012&\u0001\u0004=e>|GOP\u0005\u0004\u0003ki\u0012A\u0002)sK\u0012,g-\u0003\u0003\u0002:\u0005m\"AB*ue&twMC\u0002\u00026uAa!a\u0006\u0013\u0001\u00049\u0014\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005\u001d\u0012!B\"O_\u0012,\u0007C\u0001\u0012\u0016'\t)B\u000b\u0006\u0002\u0002F\u0005!A-^1m+\u0019\ty%!\u0016\u0002ZQ\u0001\u0012\u0011KA.\u0003K\nI'!\u001c\u0002r\u0005M\u0014Q\u000f\t\bE\u0005E\u00111KA,!\r1\u0013Q\u000b\u0003\u0006Q]\u0011\rA\u000b\t\u0004M\u0005eC!\u0002\u001b\u0018\u0005\u0004Q\u0003bBA//\u0001\u0007\u0011qL\u0001\u0002qB9!%!\u0019\u0002T\u0005]\u0013bAA23\t)1KT8eK\"1\u0011qM\fA\u0002]\n1\u0001\u001f5d\u0011\u001d\tYg\u0006a\u0001\u0003?\n\u0011!\u001f\u0005\u0007\u0003_:\u0002\u0019A\u001c\u0002\u0007eD7\r\u0003\u0004\u0002\u0018]\u0001\ra\u000e\u0005\u0006\t^\u0001\rA\u0012\u0005\b\u0003o:\u0002\u0019AA=\u0003\u0015)\u0017/^5w!\u0019\tY(!!\u0002T9\u0019A&! \n\u0007\u0005}T$A\u0004qC\u000e\\\u0017mZ3\n\t\u0005\r\u0015Q\u0011\u0002\u0006\u000bF,\u0018N\u001e\u0006\u0004\u0003\u007fj\u0002")
public final class CNode<K, V>
extends CNodeBase<K, V> {
    private final int bitmap;
    private final BasicNode[] array;
    private final Gen gen;

    public static <K, V> MainNode<K, V> dual(SNode<K, V> x, int xhc, SNode<K, V> y, int yhc, int lev, Gen gen, Equiv<K> equiv) {
        return CNode$.MODULE$.dual(x, xhc, y, yhc, lev, gen, equiv);
    }

    public int bitmap() {
        return this.bitmap;
    }

    public BasicNode[] array() {
        return this.array;
    }

    public Gen gen() {
        return this.gen;
    }

    @Override
    public int cachedSize(Object ct) {
        int currsz = this.READ_SIZE();
        if (currsz != -1) {
            return currsz;
        }
        int sz = this.computeSize((TrieMap)ct);
        while (this.READ_SIZE() == -1) {
            this.CAS_SIZE(-1, sz);
        }
        return this.READ_SIZE();
    }

    @Override
    public int knownSize() {
        return this.READ_SIZE();
    }

    private int computeSize(TrieMap<K, V> ct) {
        int offset;
        int sz = 0;
        int n = offset = this.array().length > 0 ? ThreadLocalRandom.current().nextInt(0, this.array().length) : 0;
        for (int i = 0; i < this.array().length; ++i) {
            int pos = (i + offset) % this.array().length;
            BasicNode basicNode = this.array()[pos];
            if (basicNode instanceof SNode) {
                ++sz;
                continue;
            }
            if (basicNode instanceof INode) {
                INode iNode = (INode)basicNode;
                sz += iNode.cachedSize(ct);
                continue;
            }
            throw new MatchError(basicNode);
        }
        return sz;
    }

    public CNode<K, V> updatedAt(int pos, BasicNode nn, Gen gen) {
        int len = this.array().length;
        BasicNode[] narr = new BasicNode[len];
        Array$.MODULE$.copy(this.array(), 0, narr, 0, len);
        narr[pos] = nn;
        return new CNode<K, V>(this.bitmap(), narr, gen);
    }

    public CNode<K, V> removedAt(int pos, int flag, Gen gen) {
        BasicNode[] arr = this.array();
        int len = arr.length;
        BasicNode[] narr = new BasicNode[len - 1];
        Array$.MODULE$.copy(arr, 0, narr, 0, pos);
        Array$.MODULE$.copy(arr, pos + 1, narr, pos, len - pos - 1);
        return new CNode<K, V>(this.bitmap() ^ flag, narr, gen);
    }

    public CNode<K, V> insertedAt(int pos, int flag, K k, V v, int hc, Gen gen) {
        int len = this.array().length;
        int bmp = this.bitmap();
        BasicNode[] narr = new BasicNode[len + 1];
        Array$.MODULE$.copy(this.array(), 0, narr, 0, pos);
        narr[pos] = new SNode<K, V>(k, v, hc);
        Array$.MODULE$.copy(this.array(), pos, narr, pos + 1, len - pos);
        return new CNode<K, V>(bmp | flag, narr, gen);
    }

    public CNode<K, V> renewed(Gen ngen, TrieMap<K, V> ct) {
        BasicNode[] arr = this.array();
        int len = arr.length;
        BasicNode[] narr = new BasicNode[len];
        for (int i = 0; i < len; ++i) {
            BasicNode basicNode = arr[i];
            if (basicNode instanceof INode) {
                INode iNode = (INode)basicNode;
                narr[i] = iNode.copyToGen(ngen, ct);
                continue;
            }
            if (basicNode != null) {
                narr[i] = basicNode;
                continue;
            }
            throw new MatchError((Object)null);
        }
        return new CNode<K, V>(this.bitmap(), narr, ngen);
    }

    private BasicNode resurrect(INode<K, V> inode, Object inodemain) {
        if (inodemain instanceof TNode) {
            return ((TNode)inodemain).copyUntombed();
        }
        return inode;
    }

    public MainNode<K, V> toContracted(int lev) {
        if (this.array().length == 1 && lev > 0) {
            BasicNode basicNode = this.array()[0];
            if (basicNode instanceof SNode) {
                return ((SNode)basicNode).copyTombed();
            }
            return this;
        }
        return this;
    }

    public MainNode<K, V> toCompressed(TrieMap<K, V> ct, int lev, Gen gen) {
        int bmp = this.bitmap();
        BasicNode[] arr = this.array();
        BasicNode[] tmparray = new BasicNode[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            BasicNode sub = arr[i];
            if (sub instanceof INode) {
                INode iNode = (INode)sub;
                if (iNode == null) {
                    throw null;
                }
                MainNode<K, V> inodemain = iNode.GCAS_READ(ct);
                Predef$.MODULE$.assert(inodemain != null);
                tmparray[i] = this.resurrect(iNode, inodemain);
                continue;
            }
            if (sub instanceof SNode) {
                SNode sNode = (SNode)sub;
                tmparray[i] = sNode;
                continue;
            }
            throw new MatchError(sub);
        }
        return new CNode<K, V>(bmp, tmparray, gen).toContracted(lev);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public String string(int lev) {
        void mkString_end;
        void mkString_sep;
        Object[] objectArray = new Object[2];
        objectArray[0] = this.bitmap();
        BasicNode[] map$extension_$this = this.array();
        int map$extension_len = map$extension_$this.length;
        String[] map$extension_ys = new String[map$extension_len];
        if (map$extension_len > 0) {
            for (int map$extension_i = 0; map$extension_i < map$extension_len; ++map$extension_i) {
                String array_update_value;
                map$extension_ys[map$extension_i] = array_update_value = map$extension_$this[map$extension_i].string(lev + 1);
                Object var10_6 = null;
            }
        }
        Object var4_2 = null;
        Object var6_4 = null;
        ArraySeq.ofRef<String> ofRef2 = Predef$.MODULE$.wrapRefArray(map$extension_ys);
        String string = "\n";
        if (ofRef2 == null) {
            throw null;
        }
        ArraySeq.ofRef<String> mkString_this = ofRef2;
        String string2 = "";
        String mkString_start = "";
        Object var8_10 = null;
        string2 = null;
        Object var2_8 = null;
        string = null;
        objectArray[1] = IterableOnceOps.mkString$(mkString_this, mkString_start, (String)mkString_sep, (String)mkString_end);
        return StringOps$.MODULE$.format$extension("CNode %x\n%s", ScalaRunTime$.MODULE$.genericWrapArray(objectArray));
    }

    /*
     * WARNING - void declaration
     */
    public String toString() {
        void mkString_sep;
        Object[] objectArray = new Object[2];
        Seq seq = this.elems$1();
        if (seq == null) {
            throw null;
        }
        objectArray[0] = seq.length();
        IterableOnceOps iterableOnceOps = (IterableOnceOps)this.elems$1().sorted(Ordering$String$.MODULE$);
        String string = ", ";
        if (iterableOnceOps == null) {
            throw null;
        }
        string = null;
        objectArray[1] = iterableOnceOps.mkString("", (String)mkString_sep, "");
        return StringOps$.MODULE$.format$extension("CNode(sz: %d; %s)", ScalaRunTime$.MODULE$.genericWrapArray(objectArray));
    }

    public static final /* synthetic */ String $anonfun$string$1(int lev$3, BasicNode x$3) {
        return x$3.string(lev$3 + 1);
    }

    public static final /* synthetic */ Iterable $anonfun$toString$1(BasicNode x0$1) {
        if (x0$1 instanceof SNode) {
            SNode sNode = (SNode)x0$1;
            String single_a = sNode.kvPair()._2().toString();
            return new AbstractIterable<A>(single_a){
                private final Object a$1;

                public Iterator<A> iterator() {
                    Object single_a = this.a$1;
                    return new AbstractIterator<A>(single_a){
                        private boolean consumed;
                        private final Object a$1;

                        public boolean hasNext() {
                            return !this.consumed;
                        }

                        public A next() {
                            if (this.consumed) {
                                return (A)Iterator$.scala$collection$Iterator$$_empty.next();
                            }
                            this.consumed = true;
                            return (A)this.a$1;
                        }

                        public Iterator<A> sliceIterator(int from, int until) {
                            if (this.consumed || from > 0 || until == 0) {
                                return Iterator$.scala$collection$Iterator$$_empty;
                            }
                            return this;
                        }
                        {
                            this.a$1 = a$1;
                            this.consumed = false;
                        }
                    };
                }

                public int knownSize() {
                    return 1;
                }

                public A head() {
                    return (A)this.a$1;
                }

                public Some<A> headOption() {
                    return new Some<Object>(this.a$1);
                }

                public A last() {
                    return (A)this.a$1;
                }

                public Some<A> lastOption() {
                    return new Some<Object>(this.a$1);
                }

                public View.Single<A> view() {
                    return new View.Single<Object>(this.a$1);
                }

                public Iterable<A> take(int n) {
                    if (n > 0) {
                        return this;
                    }
                    return (Iterable)Iterable$.MODULE$.empty();
                }

                public Iterable<A> takeRight(int n) {
                    if (n > 0) {
                        return this;
                    }
                    return (Iterable)Iterable$.MODULE$.empty();
                }

                public Iterable<A> drop(int n) {
                    if (n > 0) {
                        return (Iterable)Iterable$.MODULE$.empty();
                    }
                    return this;
                }

                public Iterable<A> dropRight(int n) {
                    if (n > 0) {
                        return (Iterable)Iterable$.MODULE$.empty();
                    }
                    return this;
                }

                public Iterable<Nothing$> tail() {
                    return (Iterable)Iterable$.MODULE$.empty();
                }

                public Iterable<Nothing$> init() {
                    return (Iterable)Iterable$.MODULE$.empty();
                }
                {
                    this.a$1 = a$1;
                }
            };
        }
        if (x0$1 instanceof INode) {
            INode iNode = (INode)x0$1;
            String single_a = new StringBuilder(2).append(StringOps$.MODULE$.drop$extension(iNode.toString(), 14)).append("(").append(iNode.gen).append(")").toString();
            return new /* invalid duplicate definition of identical inner class */;
        }
        throw new MatchError(x0$1);
    }

    private final Seq elems$1() {
        Object var6_6;
        ArrayBuilder arrayBuilder;
        ClassTag flatMap$extension_evidence$8 = ClassTag$.MODULE$.apply(String.class);
        BasicNode[] flatMap$extension_$this = this.array();
        Class<?> clazz = flatMap$extension_evidence$8.runtimeClass();
        Class<Byte> clazz2 = Byte.TYPE;
        if (!(clazz2 != null ? !clazz2.equals(clazz) : clazz != null)) {
            arrayBuilder = new ArrayBuilder.ofByte();
        } else {
            Class<Short> clazz3 = Short.TYPE;
            if (!(clazz3 != null ? !clazz3.equals(clazz) : clazz != null)) {
                arrayBuilder = new ArrayBuilder.ofShort();
            } else {
                Class<Character> clazz4 = Character.TYPE;
                if (!(clazz4 != null ? !clazz4.equals(clazz) : clazz != null)) {
                    arrayBuilder = new ArrayBuilder.ofChar();
                } else {
                    Class<Integer> clazz5 = Integer.TYPE;
                    if (!(clazz5 != null ? !clazz5.equals(clazz) : clazz != null)) {
                        arrayBuilder = new ArrayBuilder.ofInt();
                    } else {
                        Class<Long> clazz6 = Long.TYPE;
                        if (!(clazz6 != null ? !clazz6.equals(clazz) : clazz != null)) {
                            arrayBuilder = new ArrayBuilder.ofLong();
                        } else {
                            Class<Float> clazz7 = Float.TYPE;
                            if (!(clazz7 != null ? !clazz7.equals(clazz) : clazz != null)) {
                                arrayBuilder = new ArrayBuilder.ofFloat();
                            } else {
                                Class<Double> clazz8 = Double.TYPE;
                                if (!(clazz8 != null ? !clazz8.equals(clazz) : clazz != null)) {
                                    arrayBuilder = new ArrayBuilder.ofDouble();
                                } else {
                                    Class<Boolean> clazz9 = Boolean.TYPE;
                                    if (!(clazz9 != null ? !clazz9.equals(clazz) : clazz != null)) {
                                        arrayBuilder = new ArrayBuilder.ofBoolean();
                                    } else {
                                        Class<Void> clazz10 = Void.TYPE;
                                        arrayBuilder = !(clazz10 != null ? !clazz10.equals(clazz) : clazz != null) ? new ArrayBuilder.ofUnit() : new ArrayBuilder.ofRef(flatMap$extension_evidence$8);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        clazz = null;
        ArrayBuilder.ofByte flatMap$extension_b = arrayBuilder;
        for (int flatMap$extension_i = 0; flatMap$extension_i < flatMap$extension_$this.length; ++flatMap$extension_i) {
            Iterable flatMap$extension_$plus$plus$eq_xs = CNode.$anonfun$toString$1(flatMap$extension_$this[flatMap$extension_i]);
            flatMap$extension_b.addAll(flatMap$extension_$plus$plus$eq_xs);
            var6_6 = null;
        }
        Object var1_2 = null;
        Object var2_1 = null;
        Object var3_4 = null;
        var6_6 = null;
        return Predef$.MODULE$.wrapRefArray((Object[])flatMap$extension_b.result());
    }

    public CNode(int bitmap, BasicNode[] array, Gen gen) {
        this.bitmap = bitmap;
        this.array = array;
        this.gen = gen;
    }
}

