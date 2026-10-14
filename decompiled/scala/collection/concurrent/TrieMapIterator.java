/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.concurrent;

import scala.Array$;
import scala.MatchError;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.AbstractIterator;
import scala.collection.ArrayOps$;
import scala.collection.IterableFactory;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.Seq;
import scala.collection.concurrent.BasicNode;
import scala.collection.concurrent.CNode;
import scala.collection.concurrent.INode;
import scala.collection.concurrent.KVNode;
import scala.collection.concurrent.LNode;
import scala.collection.concurrent.MainNode;
import scala.collection.concurrent.SNode;
import scala.collection.concurrent.TNode;
import scala.collection.concurrent.TrieMap;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.List$;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005\u0005%e!B\u0012%\u0001\u0019R\u0003\u0002C\"\u0001\u0005\u0003\u0007I\u0011\u0001#\t\u0011!\u0003!\u00111A\u0005\u0002%C\u0001b\u0014\u0001\u0003\u0002\u0003\u0006K!\u0012\u0005\t!\u0002\u0011\t\u0019!C\u0005#\"Aa\u000b\u0001BA\u0002\u0013%q\u000b\u0003\u0005Z\u0001\t\u0005\t\u0015)\u0003S\u0011!Q\u0006A!A!\u0002\u0013Y\u0006\"\u00020\u0001\t\u0003y\u0006b\u00023\u0001\u0005\u0004%I!\u001a\u0005\u0007[\u0002\u0001\u000b\u0011\u00024\t\u000f9\u0004!\u0019!C\u0005_\"1\u0011\u000f\u0001Q\u0001\nADqA\u001d\u0001A\u0002\u0013%A\tC\u0004t\u0001\u0001\u0007I\u0011\u0002;\t\rY\u0004\u0001\u0015)\u0003F\u0011\u001d9\b\u00011A\u0005\naDq\u0001 \u0001A\u0002\u0013%Q\u0010\u0003\u0004\u0000\u0001\u0001\u0006K!\u001f\u0005\n\u0003\u0003\u0001\u0001\u0019!C\u0005\u0003\u0007A\u0011\"a\u0003\u0001\u0001\u0004%I!!\u0004\t\u0011\u0005E\u0001\u0001)Q\u0005\u0003\u000bAq!a\u0005\u0001\t\u0003\t)\u0002C\u0004\u0002\u0018\u0001!\t!!\u0007\t\u000f\u0005m\u0001\u0001\"\u0003\u0002\u001e!9\u0011\u0011\u0006\u0001\u0005\n\u0005-\u0002bBA\u0017\u0001\u0011%\u00111\u0006\u0005\b\u0003_\u0001AQAA\u0016\u0011\u001d\ty\u0004\u0001C\t\u0003\u0003Bq!a\u0014\u0001\t#\t\t\u0006C\u0004\u0002X\u0001!\t\"!\u0017\b\u0015\u0005\u0005D%!A\t\u0002\u0019\n\u0019GB\u0005$I\u0005\u0005\t\u0012\u0001\u0014\u0002f!1a\f\tC\u0001\u0003[B\u0011\"a\u001c!#\u0003%\t!!\u001d\u0003\u001fQ\u0013\u0018.Z'ba&#XM]1u_JT!!\n\u0014\u0002\u0015\r|gnY;se\u0016tGO\u0003\u0002(Q\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003%\nQa]2bY\u0006,2a\u000b\u001cB'\t\u0001A\u0006E\u0002.]Aj\u0011AJ\u0005\u0003_\u0019\u0012\u0001#\u00112tiJ\f7\r^%uKJ\fGo\u001c:\u0011\tE\u0012D\u0007Q\u0007\u0002Q%\u00111\u0007\u000b\u0002\u0007)V\u0004H.\u001a\u001a\u0011\u0005U2D\u0002\u0001\u0003\u0006o\u0001\u0011\r!\u000f\u0002\u0002\u0017\u000e\u0001\u0011C\u0001\u001e>!\t\t4(\u0003\u0002=Q\t9aj\u001c;iS:<\u0007CA\u0019?\u0013\ty\u0004FA\u0002B]f\u0004\"!N!\u0005\u000b\t\u0003!\u0019A\u001d\u0003\u0003Y\u000bQ\u0001\\3wK2,\u0012!\u0012\t\u0003c\u0019K!a\u0012\u0015\u0003\u0007%sG/A\u0005mKZ,Gn\u0018\u0013fcR\u0011!*\u0014\t\u0003c-K!\u0001\u0014\u0015\u0003\tUs\u0017\u000e\u001e\u0005\b\u001d\n\t\t\u00111\u0001F\u0003\rAH%M\u0001\u0007Y\u00164X\r\u001c\u0011\u0002\u0005\r$X#\u0001*\u0011\tM#F\u0007Q\u0007\u0002I%\u0011Q\u000b\n\u0002\b)JLW-T1q\u0003\u0019\u0019Go\u0018\u0013fcR\u0011!\n\u0017\u0005\b\u001d\u0016\t\t\u00111\u0001S\u0003\r\u0019G\u000fI\u0001\t[V\u001cH/\u00138jiB\u0011\u0011\u0007X\u0005\u0003;\"\u0012qAQ8pY\u0016\fg.\u0001\u0004=S:LGO\u0010\u000b\u0005A\u0006\u00147\r\u0005\u0003T\u0001Q\u0002\u0005\"B\"\t\u0001\u0004)\u0005\"\u0002)\t\u0001\u0004\u0011\u0006b\u0002.\t!\u0003\u0005\raW\u0001\u0006gR\f7m[\u000b\u0002MB\u0019\u0011gZ5\n\u0005!D#!B!se\u0006L\bcA\u0019hUB\u00111k[\u0005\u0003Y\u0012\u0012\u0011BQ1tS\u000etu\u000eZ3\u0002\rM$\u0018mY6!\u0003!\u0019H/Y2la>\u001cX#\u00019\u0011\u0007E:W)A\u0005ti\u0006\u001c7\u000e]8tA\u0005)A-\u001a9uQ\u0006IA-\u001a9uQ~#S-\u001d\u000b\u0003\u0015VDqA\u0014\b\u0002\u0002\u0003\u0007Q)\u0001\u0004eKB$\b\u000eI\u0001\bgV\u0014\u0017\u000e^3s+\u0005I\bcA\u0017{a%\u00111P\n\u0002\t\u0013R,'/\u0019;pe\u0006Y1/\u001e2ji\u0016\u0014x\fJ3r)\tQe\u0010C\u0004O#\u0005\u0005\t\u0019A=\u0002\u0011M,(-\u001b;fe\u0002\nqaY;se\u0016tG/\u0006\u0002\u0002\u0006A)1+a\u00025\u0001&\u0019\u0011\u0011\u0002\u0013\u0003\r-3fj\u001c3f\u0003-\u0019WO\u001d:f]R|F%Z9\u0015\u0007)\u000by\u0001\u0003\u0005O)\u0005\u0005\t\u0019AA\u0003\u0003!\u0019WO\u001d:f]R\u0004\u0013a\u00025bg:+\u0007\u0010^\u000b\u00027\u0006!a.\u001a=u)\u0005\u0001\u0014A\u0002:fC\u0012Lg\u000eF\u0002K\u0003?Aq!!\t\u0019\u0001\u0004\t\u0019#\u0001\u0002j]B)1+!\n5\u0001&\u0019\u0011q\u0005\u0013\u0003\u000b%su\u000eZ3\u0002\u0019\rDWmY6Tk\nLG/\u001a:\u0015\u0003)\u000b!\"\u001b8ji&\fG.\u001b>f\u0003\u001d\tGM^1oG\u0016D3aGA\u001a!\u0011\t)$a\u000f\u000e\u0005\u0005]\"bAA\u001dQ\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005u\u0012q\u0007\u0002\bi\u0006LGN]3d\u0003-qWm^%uKJ\fGo\u001c:\u0015\u000f\u0001\f\u0019%a\u0012\u0002L!1\u0011Q\t\u000fA\u0002\u0015\u000bAa\u00187fm\"1\u0011\u0011\n\u000fA\u0002I\u000b1aX2u\u0011\u0019\ti\u0005\ba\u00017\u0006Iq,\\;ti&s\u0017\u000e^\u0001\u0006IV\u0004Hk\u001c\u000b\u0004\u0015\u0006M\u0003BBA+;\u0001\u0007\u0001-\u0001\u0002ji\u0006I1/\u001e2eSZLG-\u001a\u000b\u0003\u00037\u0002B!LA/s&\u0019\u0011q\f\u0014\u0003\u0007M+\u0017/A\bUe&,W*\u00199Ji\u0016\u0014\u0018\r^8s!\t\u0019\u0006eE\u0002!\u0003O\u00022!MA5\u0013\r\tY\u0007\u000b\u0002\u0007\u0003:L(+\u001a4\u0015\u0005\u0005\r\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$3'\u0006\u0004\u0002t\u0005\u0015\u0015qQ\u000b\u0003\u0003kR3aWA<W\t\tI\b\u0005\u0003\u0002|\u0005\u0005UBAA?\u0015\u0011\ty(a\u000e\u0002\u0013Ut7\r[3dW\u0016$\u0017\u0002BAB\u0003{\u0012\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\t\u00159$E1\u0001:\t\u0015\u0011%E1\u0001:\u0001")
public class TrieMapIterator<K, V>
extends AbstractIterator<Tuple2<K, V>> {
    private int level;
    private TrieMap<K, V> ct;
    private final BasicNode[][] stack;
    private final int[] stackpos;
    private int depth;
    private Iterator<Tuple2<K, V>> subiter;
    private KVNode<K, V> current;

    public static <K, V> boolean $lessinit$greater$default$3() {
        return true;
    }

    public int level() {
        return this.level;
    }

    public void level_$eq(int x$1) {
        this.level = x$1;
    }

    private TrieMap<K, V> ct() {
        return this.ct;
    }

    private void ct_$eq(TrieMap<K, V> x$1) {
        this.ct = x$1;
    }

    private BasicNode[][] stack() {
        return this.stack;
    }

    private int[] stackpos() {
        return this.stackpos;
    }

    private int depth() {
        return this.depth;
    }

    private void depth_$eq(int x$1) {
        this.depth = x$1;
    }

    private Iterator<Tuple2<K, V>> subiter() {
        return this.subiter;
    }

    private void subiter_$eq(Iterator<Tuple2<K, V>> x$1) {
        this.subiter = x$1;
    }

    private KVNode<K, V> current() {
        return this.current;
    }

    private void current_$eq(KVNode<K, V> x$1) {
        this.current = x$1;
    }

    @Override
    public boolean hasNext() {
        return this.current() != null || this.subiter() != null;
    }

    @Override
    public Tuple2<K, V> next() {
        if (this.hasNext()) {
            Tuple2<K, V> r;
            if (this.subiter() != null) {
                r = this.subiter().next();
                this.checkSubiter();
            } else {
                r = this.current().kvPair();
                this.advance();
            }
            return r;
        }
        return (Tuple2)((Object)Iterator$.scala$collection$Iterator$$_empty.next());
    }

    /*
     * WARNING - void declaration
     */
    private void readin(INode<K, V> in) {
        void gcasRead_ct;
        TrieMap<K, V> trieMap = this.ct();
        if (in == null) {
            throw null;
        }
        trieMap = null;
        MainNode<K, V> mainNode = in.GCAS_READ((TrieMap<K, V>)gcasRead_ct);
        if (mainNode instanceof CNode) {
            CNode cNode = (CNode)mainNode;
            this.depth_$eq(this.depth() + 1);
            this.stack()[this.depth()] = cNode.array();
            this.stackpos()[this.depth()] = -1;
            this.advance();
            return;
        }
        if (mainNode instanceof TNode) {
            TNode tNode = (TNode)mainNode;
            this.current_$eq(tNode);
            return;
        }
        if (mainNode instanceof LNode) {
            LNode lNode = (LNode)mainNode;
            this.subiter_$eq(lNode.entries().iterator());
            this.checkSubiter();
            return;
        }
        if (mainNode == null) {
            this.current_$eq(null);
            return;
        }
        throw new MatchError(mainNode);
    }

    private void checkSubiter() {
        if (!this.subiter().hasNext()) {
            this.subiter_$eq(null);
            this.advance();
            return;
        }
    }

    private void initialize() {
        Predef$.MODULE$.assert(this.ct().isReadOnly());
        TrieMap<K, V> qual$1 = this.ct();
        if (qual$1 == null) {
            throw null;
        }
        boolean x$1 = false;
        INode<K, V> r = qual$1.RDCSS_READ_ROOT(x$1);
        this.readin(r);
    }

    public final void advance() {
        while (this.depth() >= 0) {
            int npos = this.stackpos()[this.depth()] + 1;
            if (npos < this.stack()[this.depth()].length) {
                this.stackpos()[this.depth()] = npos;
                BasicNode basicNode = this.stack()[this.depth()][npos];
                if (basicNode instanceof SNode) {
                    SNode sNode = (SNode)basicNode;
                    this.current_$eq(sNode);
                    return;
                }
                if (basicNode instanceof INode) {
                    INode iNode = (INode)basicNode;
                    this.readin(iNode);
                    return;
                }
                throw new MatchError(basicNode);
            }
            this.depth_$eq(this.depth() - 1);
        }
        this.current_$eq(null);
    }

    public TrieMapIterator<K, V> newIterator(int _lev, TrieMap<K, V> _ct, boolean _mustInit) {
        return new TrieMapIterator<K, V>(_lev, _ct, _mustInit);
    }

    public void dupTo(TrieMapIterator<K, V> it) {
        it.level_$eq(this.level());
        super.ct_$eq(this.ct());
        super.depth_$eq(this.depth());
        super.current_$eq(this.current());
        Array$.MODULE$.copy(this.stack(), 0, super.stack(), 0, 7);
        Array$.MODULE$.copy(this.stackpos(), 0, super.stackpos(), 0, 7);
        if (this.subiter() == null) {
            super.subiter_$eq(null);
            return;
        }
        List$ toFactory_factory = List$.MODULE$;
        Object var3_2 = null;
        List lst = this.subiter().to(new IterableFactory.ToFactory(toFactory_factory));
        this.subiter_$eq(lst.iterator());
        super.subiter_$eq(lst.iterator());
    }

    /*
     * WARNING - void declaration
     */
    public Seq<Iterator<Tuple2<K, V>>> subdivide() {
        if (this.subiter() != null) {
            TrieMapIterator<K, V> it = this.newIterator(this.level() + 1, this.ct(), false);
            super.depth_$eq(-1);
            super.subiter_$eq(this.subiter());
            super.current_$eq(null);
            this.subiter_$eq(null);
            this.advance();
            this.level_$eq(this.level() + 1);
            return new $colon$colon<Nothing$>((Nothing$)((Object)it), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)this), Nil$.MODULE$));
        }
        if (this.depth() == -1) {
            this.level_$eq(this.level() + 1);
            return new $colon$colon<Nothing$>((Nothing$)((Object)this), Nil$.MODULE$);
        }
        for (int d = 0; d <= this.depth(); ++d) {
            void var6_6;
            void var5_5;
            int rem = this.stack()[d].length - 1 - this.stackpos()[d];
            if (rem <= 0) continue;
            Tuple2<Object, Object> tuple2 = ArrayOps$.MODULE$.splitAt$extension((Object[])ArrayOps$.MODULE$.drop$extension(this.stack()[d], this.stackpos()[d] + 1), rem / 2);
            if (tuple2 == null) {
                throw new MatchError((Object)null);
            }
            BasicNode[] arr1 = (BasicNode[])tuple2._1();
            BasicNode[] arr2 = (BasicNode[])tuple2._2();
            this.stack()[d] = var5_5;
            this.stackpos()[d] = -1;
            TrieMapIterator<K, V> it = this.newIterator(this.level() + 1, this.ct(), false);
            super.stack()[0] = var6_6;
            super.stackpos()[0] = -1;
            super.depth_$eq(0);
            it.advance();
            this.level_$eq(this.level() + 1);
            return new $colon$colon<Nothing$>((Nothing$)((Object)this), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)it), Nil$.MODULE$));
        }
        this.level_$eq(this.level() + 1);
        return new $colon$colon<Nothing$>((Nothing$)((Object)this), Nil$.MODULE$);
    }

    public TrieMapIterator(int level, TrieMap<K, V> ct, boolean mustInit) {
        this.level = level;
        this.ct = ct;
        this.stack = new BasicNode[7][];
        this.stackpos = new int[7];
        this.depth = -1;
        this.subiter = null;
        this.current = null;
        if (mustInit) {
            this.initialize();
        }
    }
}

