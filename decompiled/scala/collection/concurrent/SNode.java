/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.concurrent;

import scala.Tuple2;
import scala.collection.StringOps$;
import scala.collection.concurrent.BasicNode;
import scala.collection.concurrent.KVNode;
import scala.collection.concurrent.TNode;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005a3Q!\u0004\b\u0003!QA\u0001\"\f\u0001\u0003\u0006\u0004%)A\f\u0005\t_\u0001\u0011\t\u0011)A\u0007;!A\u0001\u0007\u0001BC\u0002\u0013\u0015\u0011\u0007\u0003\u00053\u0001\t\u0005\t\u0015!\u0004+\u0011!\u0019\u0004A!b\u0001\n\u000b!\u0004\u0002\u0003\u001d\u0001\u0005\u0003\u0005\u000bQB\u001b\t\u000be\u0002A\u0011\u0001\u001e\t\u000b}\u0002A\u0011\u0001!\t\u000b\u0005\u0003A\u0011\u0001\"\t\u000b\u0019\u0003A\u0011\u0001!\t\u000b\u001d\u0003A\u0011\u0001%\t\u000b1\u0003A\u0011A'\u0003\u000bMsu\u000eZ3\u000b\u0005=\u0001\u0012AC2p]\u000e,(O]3oi*\u0011\u0011CE\u0001\u000bG>dG.Z2uS>t'\"A\n\u0002\u000bM\u001c\u0017\r\\1\u0016\u0007Uy2fE\u0002\u0001-i\u0001\"a\u0006\r\u000e\u00039I!!\u0007\b\u0003\u0013\t\u000b7/[2O_\u0012,\u0007\u0003B\f\u001c;)J!\u0001\b\b\u0003\r-3fj\u001c3f!\tqr\u0004\u0004\u0001\u0005\u000b\u0001\u0002!\u0019\u0001\u0012\u0003\u0003-\u001b\u0001!\u0005\u0002$OA\u0011A%J\u0007\u0002%%\u0011aE\u0005\u0002\b\u001d>$\b.\u001b8h!\t!\u0003&\u0003\u0002*%\t\u0019\u0011I\\=\u0011\u0005yYC!\u0002\u0017\u0001\u0005\u0004\u0011#!\u0001,\u0002\u0003-,\u0012!H\u0001\u0003W\u0002\n\u0011A^\u000b\u0002U\u0005\u0011a\u000fI\u0001\u0003Q\u000e,\u0012!\u000e\t\u0003IYJ!a\u000e\n\u0003\u0007%sG/A\u0002iG\u0002\na\u0001P5oSRtD\u0003B\u001e={y\u0002Ba\u0006\u0001\u001eU!)Qf\u0002a\u0001;!)\u0001g\u0002a\u0001U!)1g\u0002a\u0001k\u0005!1m\u001c9z+\u0005Y\u0014AC2paf$v.\u001c2fIV\t1\t\u0005\u0003\u0018\tvQ\u0013BA#\u000f\u0005\u0015!fj\u001c3f\u00031\u0019w\u000e]=V]R|WNY3e\u0003\u0019Yg\u000fU1jeV\t\u0011\n\u0005\u0003%\u0015vQ\u0013BA&\u0013\u0005\u0019!V\u000f\u001d7fe\u000511\u000f\u001e:j]\u001e$\"A\u0014,\u0011\u0005=#V\"\u0001)\u000b\u0005E\u0013\u0016\u0001\u00027b]\u001eT\u0011aU\u0001\u0005U\u00064\u0018-\u0003\u0002V!\n11\u000b\u001e:j]\u001eDQa\u0016\u0007A\u0002U\n1\u0001\\3w\u0001")
public final class SNode<K, V>
extends BasicNode
implements KVNode<K, V> {
    private final K k;
    private final V v;
    private final int hc;

    public final K k() {
        return this.k;
    }

    public final V v() {
        return this.v;
    }

    public final int hc() {
        return this.hc;
    }

    public SNode<K, V> copy() {
        return new SNode<K, V>(this.k(), this.v(), this.hc());
    }

    public TNode<K, V> copyTombed() {
        return new TNode<K, V>(this.k(), this.v(), this.hc());
    }

    public SNode<K, V> copyUntombed() {
        return new SNode<K, V>(this.k(), this.v(), this.hc());
    }

    @Override
    public Tuple2<K, V> kvPair() {
        return new Tuple2<K, V>(this.k(), this.v());
    }

    @Override
    public String string(int lev) {
        return new StringBuilder(0).append(StringOps$.MODULE$.$times$extension("  ", lev)).append(StringOps$.MODULE$.format$extension("SNode(%s, %s, %x)", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.k(), this.v(), this.hc()}))).toString();
    }

    public SNode(K k, V v, int hc) {
        this.k = k;
        this.v = v;
        this.hc = hc;
    }
}

