/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.concurrent;

import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.StringOps$;
import scala.collection.concurrent.BasicNode;
import scala.collection.concurrent.CNode;
import scala.collection.concurrent.CNode$;
import scala.collection.concurrent.FailedNode;
import scala.collection.concurrent.Gen;
import scala.collection.concurrent.INode$;
import scala.collection.concurrent.INodeBase;
import scala.collection.concurrent.LNode;
import scala.collection.concurrent.MainNode;
import scala.collection.concurrent.SNode;
import scala.collection.concurrent.TNode;
import scala.collection.concurrent.TrieMap;
import scala.collection.concurrent.TrieMap$RemovalPolicy$;
import scala.math.Equiv;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\tEa!\u0002\u0012$\u0005\u0015J\u0003\u0002C \u0001\u0005\u0003\u0005\u000b\u0011\u0002!\t\u0011\r\u0003!\u0011!Q\u0001\n\u0011C\u0001b\u0012\u0001\u0003\u0002\u0003\u0006I\u0001\u0013\u0005\u0006\u001f\u0002!\t\u0001\u0015\u0005\u0006\u001f\u0002!\t!\u0016\u0005\u00061\u0002!\t!\u0017\u0005\u0006?\u0002!\t\u0001\u0019\u0005\u0006Q\u0002!\t!\u001b\u0005\u0006_\u0002!\t\u0001\u001d\u0005\u0006e\u0002!Ia\u001d\u0005\u0006}\u0002!\ta \u0005\b\u0003\u000f\u0001A\u0011BA\u0005\u0011\u001d\t)\u0002\u0001C\u0005\u0003/Aq!!\b\u0001\t\u0003\ty\u0002C\u0004\u0002(\u0001!\t!!\u000b\t\u000f\u00055\u0003\u0001\"\u0001\u0002P!9\u0011Q\u000f\u0001\u0005\u0002\u0005]\u0004bBAD\u0001\u0011\u0005\u0011\u0011\u0012\u0005\b\u0003;\u0003A\u0011BAP\u0011\u001d\tI\u000b\u0001C\u0001\u0003WCq!a,\u0001\t\u0003\t\t\fC\u0004\u00026\u0002!\t!a.\t\u000f\u0005m\u0006\u0001\"\u0001\u0002>\u001eA\u0011q[\u0012\t\u0002\r\nINB\u0004#G!\u00051%a7\t\r=KB\u0011AAo\u0011%\ty.\u0007b\u0001\n\u000b\t\t\u000f\u0003\u0005\u0002tf\u0001\u000bQBAr\u0011%\t)0\u0007b\u0001\n\u000b\t\t\u000f\u0003\u0005\u0002xf\u0001\u000bQBAr\u0011%\tI0\u0007b\u0001\n\u000b\t\t\u000f\u0003\u0005\u0002|f\u0001\u000bQBAr\u0011\u001d\ti0\u0007C\u0001\u0003\u007f\u0014Q!\u0013(pI\u0016T!\u0001J\u0013\u0002\u0015\r|gnY;se\u0016tGO\u0003\u0002'O\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003!\nQa]2bY\u0006,2AK\u0019>'\t\u00011\u0006\u0005\u0003-[=bT\"A\u0012\n\u00059\u001a#!C%O_\u0012,')Y:f!\t\u0001\u0014\u0007\u0004\u0001\u0005\u000bI\u0002!\u0019\u0001\u001b\u0003\u0003-\u001b\u0001!\u0005\u00026sA\u0011agN\u0007\u0002O%\u0011\u0001h\n\u0002\b\u001d>$\b.\u001b8h!\t1$(\u0003\u0002<O\t\u0019\u0011I\\=\u0011\u0005AjD!\u0002 \u0001\u0005\u0004!$!\u0001,\u0002\u0005\tt\u0007\u0003\u0002\u0017B_qJ!AQ\u0012\u0003\u00115\u000b\u0017N\u001c(pI\u0016\f\u0011a\u001a\t\u0003Y\u0015K!AR\u0012\u0003\u0007\u001d+g.A\u0003fcVLg\u000fE\u0002J\u0019>r!A\u000e&\n\u0005-;\u0013a\u00029bG.\fw-Z\u0005\u0003\u001b:\u0013Q!R9vSZT!aS\u0014\u0002\rqJg.\u001b;?)\u0011\t&k\u0015+\u0011\t1\u0002q\u0006\u0010\u0005\u0006\u007f\u0011\u0001\r\u0001\u0011\u0005\u0006\u0007\u0012\u0001\r\u0001\u0012\u0005\u0006\u000f\u0012\u0001\r\u0001\u0013\u000b\u0004#Z;\u0006\"B\"\u0006\u0001\u0004!\u0005\"B$\u0006\u0001\u0004A\u0015!B,S\u0013R+EC\u0001.^!\t14,\u0003\u0002]O\t!QK\\5u\u0011\u0015qf\u00011\u0001A\u0003\u0011qg/\u00197\u0002\u0007\r\u000b5\u000bF\u0002bI\u001a\u0004\"A\u000e2\n\u0005\r<#a\u0002\"p_2,\u0017M\u001c\u0005\u0006K\u001e\u0001\r\u0001Q\u0001\u0004_2$\u0007\"B4\b\u0001\u0004\u0001\u0015!\u00018\u0002\u0011\u001d\u001c\u0017m\u001d*fC\u0012$\"\u0001\u00116\t\u000b-D\u0001\u0019\u00017\u0002\u0005\r$\b\u0003\u0002\u0017n_qJ!A\\\u0012\u0003\u000fQ\u0013\u0018.Z'ba\u0006IqiQ!T?J+\u0015\t\u0012\u000b\u0003\u0001FDQa[\u0005A\u00021\fQbR\"B'~\u001bu.\u001c9mKR,Gc\u0001!um\")QO\u0003a\u0001\u0001\u0006\tQ\u000eC\u0003l\u0015\u0001\u0007A\u000e\u000b\u0002\u000bqB\u0011\u0011\u0010`\u0007\u0002u*\u00111pJ\u0001\u000bC:tw\u000e^1uS>t\u0017BA?{\u0005\u001d!\u0018-\u001b7sK\u000e\fAaR\"B'R9\u0011-!\u0001\u0002\u0004\u0005\u0015\u0001\"B3\f\u0001\u0004\u0001\u0005\"B4\f\u0001\u0004\u0001\u0005\"B6\f\u0001\u0004a\u0017!B3rk\u0006dGcB1\u0002\f\u0005=\u00111\u0003\u0005\u0007\u0003\u001ba\u0001\u0019A\u0018\u0002\u0005-\f\u0004BBA\t\u0019\u0001\u0007q&\u0001\u0002le!)1\u000e\u0004a\u0001Y\u0006)\u0011N\\8eKR\u0019\u0011+!\u0007\t\r\u0005mQ\u00021\u0001A\u0003\t\u0019g.A\u0005d_BLHk\\$f]R)\u0011+!\t\u0002&!1\u00111\u0005\bA\u0002\u0011\u000bAA\\4f]\")1N\u0004a\u0001Y\u0006Q!/Z2`S:\u001cXM\u001d;\u0015\u001f\u0005\fY#a\f\u00024\u0005u\u0012\u0011IA#\u0003\u0013Ba!!\f\u0010\u0001\u0004y\u0013!A6\t\r\u0005Er\u00021\u0001=\u0003\u00051\bbBA\u001b\u001f\u0001\u0007\u0011qG\u0001\u0003Q\u000e\u00042ANA\u001d\u0013\r\tYd\n\u0002\u0004\u0013:$\bbBA \u001f\u0001\u0007\u0011qG\u0001\u0004Y\u00164\bBBA\"\u001f\u0001\u0007\u0011+\u0001\u0004qCJ,g\u000e\u001e\u0005\u0007\u0003\u000fz\u0001\u0019\u0001#\u0002\u0011M$\u0018M\u001d;hK:DQa[\bA\u00021D#a\u0004=\u0002\u0019I,7mX5og\u0016\u0014H/\u001b4\u0015)\u0005E\u0013qKA-\u00037\ni&a\u001a\u0002l\u00055\u0014qNA9!\u00111\u00141\u000b\u001f\n\u0007\u0005UsE\u0001\u0004PaRLwN\u001c\u0005\u0007\u0003[\u0001\u0002\u0019A\u0018\t\r\u0005E\u0002\u00031\u0001=\u0011\u001d\t)\u0004\u0005a\u0001\u0003oAq!a\u0018\u0011\u0001\u0004\t\t'\u0001\u0003d_:$\u0007c\u0001\u001c\u0002d%\u0019\u0011QM\u0014\u0003\r\u0005s\u0017PU3g\u0011\u0019\tI\u0007\u0005a\u0001C\u0006Qa-\u001e7m\u000bF,\u0018\r\\:\t\u000f\u0005}\u0002\u00031\u0001\u00028!1\u00111\t\tA\u0002ECa!a\u0012\u0011\u0001\u0004!\u0005\"B6\u0011\u0001\u0004a\u0007F\u0001\ty\u0003)\u0011XmY0m_>\\W\u000f\u001d\u000b\u000f\u0003C\nI(a\u001f\u0002~\u0005}\u0014\u0011QAB\u0011\u0019\ti#\u0005a\u0001_!9\u0011QG\tA\u0002\u0005]\u0002bBA #\u0001\u0007\u0011q\u0007\u0005\u0007\u0003\u0007\n\u0002\u0019A)\t\r\u0005\u001d\u0013\u00031\u0001E\u0011\u0015Y\u0017\u00031\u0001mQ\t\t\u00020\u0001\u0006sK\u000e|&/Z7pm\u0016$\"#!\u0015\u0002\f\u00065\u0015qRAJ\u0003+\u000b9*!'\u0002\u001c\"1\u0011Q\u0006\nA\u0002=Ba!!\r\u0013\u0001\u0004a\u0004bBAI%\u0001\u0007\u0011qG\u0001\u000ee\u0016lwN^1m!>d\u0017nY=\t\u000f\u0005U\"\u00031\u0001\u00028!9\u0011q\b\nA\u0002\u0005]\u0002BBA\"%\u0001\u0007\u0011\u000b\u0003\u0004\u0002HI\u0001\r\u0001\u0012\u0005\u0006WJ\u0001\r\u0001\\\u0001\u0006G2,\u0017M\u001c\u000b\b5\u0006\u0005\u0016QUAT\u0011\u0019\t\u0019k\u0005a\u0001#\u0006\u0011a\u000e\u001a\u0005\u0006WN\u0001\r\u0001\u001c\u0005\b\u0003\u007f\u0019\u0002\u0019AA\u001c\u0003-I7OT;mY&sw\u000eZ3\u0015\u0007\u0005\fi\u000bC\u0003l)\u0001\u0007A.\u0001\u0006dC\u000eDW\rZ*ju\u0016$B!a\u000e\u00024\")1.\u0006a\u0001Y\u0006I1N\\8x]NK'0\u001a\u000b\u0005\u0003o\tI\fC\u0003l-\u0001\u0007A.\u0001\u0004tiJLgn\u001a\u000b\u0005\u0003\u007f\u000b)\u000e\u0005\u0003\u0002B\u0006=g\u0002BAb\u0003\u0017\u00042!!2(\u001b\t\t9MC\u0002\u0002JN\na\u0001\u0010:p_Rt\u0014bAAgO\u00051\u0001K]3eK\u001aLA!!5\u0002T\n11\u000b\u001e:j]\u001eT1!!4(\u0011\u001d\tyd\u0006a\u0001\u0003o\tQ!\u0013(pI\u0016\u0004\"\u0001L\r\u0014\u0007e\t\t\u0007\u0006\u0002\u0002Z\u0006Y1*R-`!J+5+\u0012(U+\t\t\u0019\u000f\u0005\u0003\u0002f\u0006=XBAAt\u0015\u0011\tI/a;\u0002\t1\fgn\u001a\u0006\u0003\u0003[\fAA[1wC&!\u0011\u0011_At\u0005\u0019y%M[3di\u0006a1*R-`!J+5+\u0012(UA\u0005Q1*R-`\u0003\n\u001bVI\u0014+\u0002\u0017-+\u0015lX!C'\u0016sE\u000bI\u0001\u0016\u0017\u0016Kv\f\u0015*F'\u0016sEkX(S?\u0006\u00135+\u0012(U\u0003YYU)W0Q%\u0016\u001bVI\u0014+`\u001fJ{\u0016IQ*F\u001dR\u0003\u0013a\u00038foJ{w\u000e\u001e(pI\u0016,bA!\u0001\u0003\b\t-A\u0003\u0002B\u0002\u0005\u001b\u0001b\u0001\f\u0001\u0003\u0006\t%\u0001c\u0001\u0019\u0003\b\u0011)!'\tb\u0001iA\u0019\u0001Ga\u0003\u0005\u000by\n#\u0019\u0001\u001b\t\r\u001d\u000b\u0003\u0019\u0001B\b!\u0011IEJ!\u0002")
public final class INode<K, V>
extends INodeBase<K, V> {
    private final Equiv<K> equiv;

    public static <K, V> INode<K, V> newRootNode(Equiv<K> equiv) {
        return INode$.MODULE$.newRootNode(equiv);
    }

    public static Object KEY_PRESENT_OR_ABSENT() {
        return INode$.MODULE$.KEY_PRESENT_OR_ABSENT();
    }

    public static Object KEY_ABSENT() {
        return INode$.MODULE$.KEY_ABSENT();
    }

    public static Object KEY_PRESENT() {
        return INode$.MODULE$.KEY_PRESENT();
    }

    public void WRITE(MainNode<K, V> nval) {
        INodeBase.updater.set(this, nval);
    }

    public boolean CAS(MainNode<K, V> old, MainNode<K, V> n) {
        return INodeBase.updater.compareAndSet(this, old, n);
    }

    public MainNode<K, V> gcasRead(TrieMap<K, V> ct) {
        return this.GCAS_READ(ct);
    }

    public MainNode<K, V> GCAS_READ(TrieMap<K, V> ct) {
        MainNode m4 = this.mainnode;
        if (m4.prev == null) {
            return m4;
        }
        return this.GCAS_Complete(m4, ct);
    }

    /*
     * WARNING - void declaration
     */
    private MainNode<K, V> GCAS_Complete(MainNode<K, V> m4, TrieMap<K, V> ct) {
        while (m4 != null) {
            void readRoot_abort;
            MainNode prev = m4.prev;
            boolean bl = true;
            if (ct == null) {
                throw null;
            }
            INode<K, V> ctr = ct.RDCSS_READ_ROOT((boolean)readRoot_abort);
            if (prev == null) {
                return m4;
            }
            if (prev instanceof FailedNode) {
                FailedNode failedNode = (FailedNode)prev;
                MainNode CAS_n = failedNode.prev;
                Object var7_7 = null;
                if (INodeBase.updater.compareAndSet(this, m4, CAS_n)) {
                    return failedNode.prev;
                }
                m4 = this.mainnode;
                continue;
            }
            if (ctr.gen == this.gen && ct.nonReadOnly()) {
                if (!m4.CAS_PREV(prev, null)) continue;
                return m4;
            }
            m4.CAS_PREV(prev, new FailedNode(prev));
            m4 = this.mainnode;
        }
        return null;
    }

    public boolean GCAS(MainNode<K, V> old, MainNode<K, V> n, TrieMap<K, V> ct) {
        n.WRITE_PREV(old);
        if (INodeBase.updater.compareAndSet(this, old, n)) {
            this.GCAS_Complete(n, ct);
            return n.prev == null;
        }
        return false;
    }

    private boolean equal(K k1, K k2, TrieMap<K, V> ct) {
        return ct.equality().equiv(k1, k2);
    }

    private INode<K, V> inode(MainNode<K, V> cn) {
        INode<K, V> nin = new INode<K, V>(this.gen, this.equiv);
        INodeBase.updater.set(nin, cn);
        return nin;
    }

    public INode<K, V> copyToGen(Gen ngen, TrieMap<K, V> ct) {
        INode<K, V> nin = new INode<K, V>(ngen, this.equiv);
        MainNode<K, V> main = this.GCAS_READ(ct);
        INodeBase.updater.set(nin, main);
        return nin;
    }

    public boolean rec_insert(K k, V v, int hc, int lev, INode<K, V> parent, Gen startgen, TrieMap<K, V> ct) {
        MainNode<K, V> m4;
        while ((m4 = this_.GCAS_READ(ct)) instanceof CNode) {
            CNode<K, V> cNode = (CNode<K, V>)m4;
            int idx = hc >>> lev & 0x1F;
            int flag = 1 << idx;
            int bmp = cNode.bitmap();
            int mask = flag - 1;
            int pos = Integer.bitCount(bmp & mask);
            if ((bmp & flag) != 0) {
                BasicNode basicNode = cNode.array()[pos];
                if (basicNode instanceof INode) {
                    INode iNode = (INode)basicNode;
                    if (startgen == iNode.gen) {
                        parent = this_;
                        lev += 5;
                        INode this_ = iNode;
                        continue;
                    }
                    if (this_.GCAS(cNode, cNode.renewed(startgen, ct), ct)) continue;
                    return false;
                }
                if (basicNode instanceof SNode) {
                    SNode sNode = (SNode)basicNode;
                    if (sNode.hc() == hc && this_.equal(sNode.k(), k, ct)) {
                        return this_.GCAS(cNode, cNode.updatedAt(pos, new SNode(sNode.k(), v, hc), this_.gen), ct);
                    }
                    CNode<K, V> nn = (cNode.gen() == this_.gen ? cNode : cNode.renewed(this_.gen, ct)).updatedAt(pos, this_.inode(CNode$.MODULE$.dual(sNode, sNode.hc(), new SNode<K, V>(k, v, hc), hc, lev + 5, this_.gen, this_.equiv)), this_.gen);
                    return this_.GCAS(cNode, nn, ct);
                }
                throw new MatchError(basicNode);
            }
            CNode<K, V> ncnode = (cNode.gen() == this_.gen ? cNode : cNode.renewed(this_.gen, ct)).insertedAt(pos, flag, k, v, hc, this_.gen);
            return this_.GCAS(cNode, ncnode, ct);
        }
        if (m4 instanceof TNode) {
            this_.clean(parent, ct, lev - 5);
            return false;
        }
        if (m4 instanceof LNode) {
            LNode lNode = (LNode)m4;
            LNode<K, V> nn = lNode.inserted(k, v);
            return this_.GCAS(lNode, nn, ct);
        }
        throw new MatchError(m4);
    }

    public Option<V> rec_insertif(K k, V v, int hc, Object cond, boolean fullEquals, int lev, INode<K, V> parent, Gen startgen, TrieMap<K, V> ct) {
        MainNode<K, V> m4;
        while ((m4 = this_.GCAS_READ(ct)) instanceof CNode) {
            CNode<K, V> cNode = (CNode<K, V>)m4;
            int idx = hc >>> lev & 0x1F;
            int flag = 1 << idx;
            int bmp = cNode.bitmap();
            int mask = flag - 1;
            int pos = Integer.bitCount(bmp & mask);
            if ((bmp & flag) != 0) {
                BasicNode basicNode = cNode.array()[pos];
                if (basicNode instanceof INode) {
                    INode iNode = (INode)basicNode;
                    if (startgen == iNode.gen) {
                        parent = this_;
                        lev += 5;
                        INode this_ = iNode;
                        continue;
                    }
                    if (this_.GCAS(cNode, cNode.renewed(startgen, ct), ct)) continue;
                    return null;
                }
                if (basicNode instanceof SNode) {
                    SNode sNode = (SNode)basicNode;
                    if (BoxesRunTime.equals(INode$.MODULE$.KEY_PRESENT_OR_ABSENT(), cond)) {
                        if (sNode.hc() == hc && this_.equal(sNode.k(), k, ct)) {
                            if (this_.GCAS(cNode, cNode.updatedAt(pos, new SNode(sNode.k(), v, hc), this_.gen), ct)) {
                                return new Some(sNode.v());
                            }
                            return null;
                        }
                        CNode<K, V> nn = (cNode.gen() == this_.gen ? cNode : cNode.renewed(this_.gen, ct)).updatedAt(pos, this_.inode(CNode$.MODULE$.dual(sNode, sNode.hc(), new SNode<K, V>(k, v, hc), hc, lev + 5, this_.gen, this_.equiv)), this_.gen);
                        if (this_.GCAS(cNode, nn, ct)) {
                            return None$.MODULE$;
                        }
                        return null;
                    }
                    if (BoxesRunTime.equals(INode$.MODULE$.KEY_ABSENT(), cond)) {
                        if (sNode.hc() == hc && this_.equal(sNode.k(), k, ct)) {
                            return new Some(sNode.v());
                        }
                        CNode<K, V> nn = (cNode.gen() == this_.gen ? cNode : cNode.renewed(this_.gen, ct)).updatedAt(pos, this_.inode(CNode$.MODULE$.dual(sNode, sNode.hc(), new SNode<K, V>(k, v, hc), hc, lev + 5, this_.gen, this_.equiv)), this_.gen);
                        if (this_.GCAS(cNode, nn, ct)) {
                            return None$.MODULE$;
                        }
                        return null;
                    }
                    if (BoxesRunTime.equals(INode$.MODULE$.KEY_PRESENT(), cond)) {
                        if (sNode.hc() == hc && this_.equal(sNode.k(), k, ct)) {
                            if (this_.GCAS(cNode, cNode.updatedAt(pos, new SNode<K, V>(k, v, hc), this_.gen), ct)) {
                                return new Some(sNode.v());
                            }
                            return null;
                        }
                        return None$.MODULE$;
                    }
                    if (sNode.hc() == hc && this_.equal(sNode.k(), k, ct) && !(!fullEquals ? sNode.v() != cond : !BoxesRunTime.equals(sNode.v(), cond))) {
                        if (this_.GCAS(cNode, cNode.updatedAt(pos, new SNode<K, V>(k, v, hc), this_.gen), ct)) {
                            return new Some(sNode.v());
                        }
                        return null;
                    }
                    return None$.MODULE$;
                }
                throw new MatchError(basicNode);
            }
            if (BoxesRunTime.equals(INode$.MODULE$.KEY_PRESENT_OR_ABSENT(), cond) ? true : BoxesRunTime.equals(INode$.MODULE$.KEY_ABSENT(), cond)) {
                CNode<K, V> ncnode = (cNode.gen() == this_.gen ? cNode : cNode.renewed(this_.gen, ct)).insertedAt(pos, flag, k, v, hc, this_.gen);
                if (this_.GCAS(cNode, ncnode, ct)) {
                    return None$.MODULE$;
                }
                return null;
            }
            if (BoxesRunTime.equals(INode$.MODULE$.KEY_PRESENT(), cond)) {
                return None$.MODULE$;
            }
            return None$.MODULE$;
        }
        if (m4 instanceof TNode) {
            this_.clean(parent, ct, lev - 5);
            return null;
        }
        if (m4 instanceof LNode) {
            LNode lNode = (LNode)m4;
            if (BoxesRunTime.equals(INode$.MODULE$.KEY_PRESENT_OR_ABSENT(), cond)) {
                Option optv = lNode.get(k);
                if (this_.insertln$1(lNode, k, v, ct)) {
                    return optv;
                }
                return null;
            }
            if (BoxesRunTime.equals(INode$.MODULE$.KEY_ABSENT(), cond)) {
                Option option = lNode.get(k);
                if (None$.MODULE$.equals(option)) {
                    if (this_.insertln$1(lNode, k, v, ct)) {
                        return None$.MODULE$;
                    }
                    return null;
                }
                return option;
            }
            if (BoxesRunTime.equals(INode$.MODULE$.KEY_PRESENT(), cond)) {
                Option option = lNode.get(k);
                if (option instanceof Some) {
                    Object v0 = ((Some)option).value();
                    if (this_.insertln$1(lNode, k, v, ct)) {
                        return new Some(v0);
                    }
                    return null;
                }
                if (None$.MODULE$.equals(option)) {
                    return None$.MODULE$;
                }
                throw new MatchError(option);
            }
            Option option = lNode.get(k);
            if (option instanceof Some) {
                Object v0 = ((Some)option).value();
                if (!(!fullEquals ? v0 != cond : !BoxesRunTime.equals(v0, cond))) {
                    if (this_.insertln$1(lNode, k, v, ct)) {
                        return new Some<Object>(cond);
                    }
                    return null;
                }
            }
            return None$.MODULE$;
        }
        throw new MatchError(m4);
    }

    public Object rec_lookup(K k, int hc, int lev, INode<K, V> parent, Gen startgen, TrieMap<K, V> ct) {
        MainNode<K, V> m4;
        while ((m4 = this_.GCAS_READ(ct)) instanceof CNode) {
            CNode cNode = (CNode)m4;
            int idx = hc >>> lev & 0x1F;
            int flag = 1 << idx;
            int bmp = cNode.bitmap();
            if ((bmp & flag) == 0) {
                return INodeBase.NO_SUCH_ELEMENT_SENTINEL;
            }
            int pos = bmp == -1 ? idx : Integer.bitCount(bmp & flag - 1);
            BasicNode sub = cNode.array()[pos];
            if (sub instanceof INode) {
                INode iNode = (INode)sub;
                if (ct.isReadOnly() || startgen == iNode.gen) {
                    parent = this_;
                    lev += 5;
                    INode this_ = iNode;
                    continue;
                }
                if (this_.GCAS(cNode, cNode.renewed(startgen, ct), ct)) continue;
                return INodeBase.RESTART;
            }
            if (sub instanceof SNode) {
                SNode sNode = (SNode)sub;
                if (sNode.hc() == hc && this_.equal(sNode.k(), k, ct)) {
                    return sNode.v();
                }
                return INodeBase.NO_SUCH_ELEMENT_SENTINEL;
            }
            throw new MatchError(sub);
        }
        if (m4 instanceof TNode) {
            TNode tNode = (TNode)m4;
            return this_.cleanReadOnly$1(tNode, ct, parent, lev, hc, k);
        }
        if (m4 instanceof LNode) {
            Option option = ((LNode)m4).get(k);
            if (option == null) {
                throw null;
            }
            Option getOrElse_this = option;
            if (getOrElse_this.isEmpty()) {
                return INodeBase.NO_SUCH_ELEMENT_SENTINEL;
            }
            return getOrElse_this.get();
        }
        throw new MatchError(m4);
    }

    public Option<V> rec_remove(K k, V v, int removalPolicy, int hc, int lev, INode<K, V> parent, Gen startgen, TrieMap<K, V> ct) {
        MainNode<K, V> m4 = this.GCAS_READ(ct);
        if (m4 instanceof CNode) {
            MainNode<K, V> n;
            None$ res;
            Option option;
            int flag;
            CNode cNode = (CNode)m4;
            int idx = hc >>> lev & 0x1F;
            int bmp = cNode.bitmap();
            if ((bmp & (flag = 1 << idx)) == 0) {
                return None$.MODULE$;
            }
            int pos = Integer.bitCount(bmp & flag - 1);
            BasicNode sub = cNode.array()[pos];
            if (sub instanceof INode) {
                INode iNode = (INode)sub;
                option = startgen == iNode.gen ? iNode.rec_remove(k, v, removalPolicy, hc, lev + 5, this, startgen, ct) : (this.GCAS(cNode, cNode.renewed(startgen, ct), ct) ? this.rec_remove(k, v, removalPolicy, hc, lev, parent, startgen, ct) : null);
            } else if (sub instanceof SNode) {
                MainNode ncn;
                SNode sNode = (SNode)sub;
                option = sNode.hc() == hc && this.equal(sNode.k(), k, ct) && TrieMap$RemovalPolicy$.MODULE$.shouldRemove(removalPolicy, sNode.v(), v) ? (this.GCAS(cNode, ncn = cNode.removedAt(pos, flag, this.gen).toContracted(lev), ct) ? new Some(sNode.v()) : null) : None$.MODULE$;
            } else {
                throw new MatchError(sub);
            }
            None$ none$ = res = option;
            None$ none$2 = None$.MODULE$;
            if (none$ != null && none$.equals(none$2) || res == null) {
                return res;
            }
            if (parent != null && (n = this.GCAS_READ(ct)) instanceof TNode) {
                this.cleanParent$1(n, parent, ct, hc, lev, startgen);
            }
            return res;
        }
        if (m4 instanceof TNode) {
            this.clean(parent, ct, lev - 5);
            return null;
        }
        if (m4 instanceof LNode) {
            Some some;
            Object v0;
            LNode lNode = (LNode)m4;
            if (removalPolicy == 0) {
                Option optv = lNode.get(k);
                MainNode<K, V> nn = lNode.removed(k, ct);
                if (this.GCAS(lNode, nn, ct)) {
                    return optv;
                }
                return null;
            }
            Option option = lNode.get(k);
            if (option instanceof Some && TrieMap$RemovalPolicy$.MODULE$.shouldRemove(removalPolicy, v, v0 = (some = (Some)option).value())) {
                MainNode<K, V> nn = lNode.removed(k, ct);
                if (this.GCAS(lNode, nn, ct)) {
                    return some;
                }
                return null;
            }
            return None$.MODULE$;
        }
        throw new MatchError(m4);
    }

    private void clean(INode<K, V> nd, TrieMap<K, V> ct, int lev) {
        MainNode<K, V> m4 = nd.GCAS_READ(ct);
        if (m4 instanceof CNode) {
            CNode cNode = (CNode)m4;
            nd.GCAS(cNode, cNode.toCompressed(ct, lev, this.gen), ct);
            return;
        }
    }

    public boolean isNullInode(TrieMap<K, V> ct) {
        return this.GCAS_READ(ct) == null;
    }

    public int cachedSize(TrieMap<K, V> ct) {
        return this.GCAS_READ(ct).cachedSize(ct);
    }

    public int knownSize(TrieMap<K, V> ct) {
        return this.GCAS_READ(ct).knownSize();
    }

    @Override
    public String string(int lev) {
        String string;
        Object[] objectArray = new Object[2];
        objectArray[0] = StringOps$.MODULE$.$times$extension("  ", lev);
        MainNode mainNode = this.mainnode;
        if (mainNode == null) {
            string = "<null>";
        } else if (mainNode instanceof TNode) {
            TNode tNode = (TNode)mainNode;
            string = StringOps$.MODULE$.format$extension("TNode(%s, %s, %d, !)", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{tNode.k(), tNode.v(), tNode.hc()}));
        } else {
            string = mainNode instanceof CNode ? ((CNode)mainNode).string(lev) : (mainNode instanceof LNode ? ((LNode)mainNode).string(lev) : StringOps$.MODULE$.format$extension("<elem: %s>", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{mainNode})));
        }
        objectArray[1] = string;
        return StringOps$.MODULE$.format$extension("%sINode -> %s", ScalaRunTime$.MODULE$.genericWrapArray(objectArray));
    }

    private final boolean insertln$1(LNode x4$1, Object k$1, Object v$1, TrieMap ct$1) {
        LNode<Object, Object> nn = x4$1.inserted(k$1, v$1);
        return this.GCAS(x4$1, nn, ct$1);
    }

    private final Object cleanReadOnly$1(TNode tn, TrieMap ct$2, INode parent$1, int lev$1, int hc$1, Object k$2) {
        if (ct$2.nonReadOnly()) {
            this.clean(parent$1, ct$2, lev$1 - 5);
            return INodeBase.RESTART;
        }
        if (tn.hc() == hc$1 && BoxesRunTime.equals(tn.k(), k$2)) {
            return tn.v();
        }
        return INodeBase.NO_SUCH_ELEMENT_SENTINEL;
    }

    public static final /* synthetic */ Object $anonfun$rec_lookup$1() {
        return INodeBase.NO_SUCH_ELEMENT_SENTINEL;
    }

    private final void cleanParent$1(Object nonlive, INode parent$2, TrieMap ct$3, int hc$2, int lev$2, Gen startgen$1) {
        MainNode<K, V> pm;
        while ((pm = parent$2.GCAS_READ(ct$3)) instanceof CNode) {
            int flag;
            CNode cNode = (CNode)pm;
            int idx = hc$2 >>> lev$2 - 5 & 0x1F;
            int bmp = cNode.bitmap();
            if ((bmp & (flag = 1 << idx)) == 0) break;
            int pos = Integer.bitCount(bmp & flag - 1);
            if (cNode.array()[pos] != this) break;
            if (nonlive instanceof TNode) {
                TNode tNode = (TNode)nonlive;
                MainNode ncn = cNode.updatedAt(pos, tNode.copyUntombed(), this.gen).toContracted(lev$2 - 5);
                if (parent$2.GCAS(cNode, ncn, ct$3)) break;
                if (ct$3 == null) {
                    throw null;
                }
                boolean readRoot_abort = false;
                Gen gen = ct$3.RDCSS_READ_ROOT((boolean)readRoot_abort).gen;
                if (gen == null) {
                    if (startgen$1 == null) continue;
                    return;
                }
                if (!gen.equals(startgen$1)) break;
                continue;
            }
            throw new MatchError(nonlive);
        }
    }

    public INode(MainNode<K, V> bn, Gen g2, Equiv<K> equiv) {
        this.equiv = equiv;
        super(g2);
        INodeBase.updater.set(this, bn);
    }

    public INode(Gen g2, Equiv<K> equiv) {
        this(null, g2, equiv);
    }
}

