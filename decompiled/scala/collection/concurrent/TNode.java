/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.concurrent;

import scala.Tuple2;
import scala.collection.StringOps$;
import scala.collection.concurrent.KVNode;
import scala.collection.concurrent.MainNode;
import scala.collection.concurrent.SNode;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\r4Qa\u0004\t\u0003%YA\u0001b\f\u0001\u0003\u0006\u0004%)\u0001\r\u0005\tc\u0001\u0011\t\u0011)A\u00079!A!\u0007\u0001BC\u0002\u0013\u00151\u0007\u0003\u00055\u0001\t\u0005\t\u0015!\u0004*\u0011!)\u0004A!b\u0001\n\u000b1\u0004\u0002\u0003\u001e\u0001\u0005\u0003\u0005\u000bQB\u001c\t\u000bm\u0002A\u0011\u0001\u001f\t\u000b\u0005\u0003A\u0011\u0001\"\t\u000b\r\u0003A\u0011\u0001\"\t\u000b\u0011\u0003A\u0011A#\t\u000b%\u0003A\u0011\u0001&\t\u000b9\u0003A\u0011A(\t\u000bU\u0003A\u0011\u0001,\t\u000b]\u0003A\u0011\u0001-\u0003\u000bQsu\u000eZ3\u000b\u0005E\u0011\u0012AC2p]\u000e,(O]3oi*\u00111\u0003F\u0001\u000bG>dG.Z2uS>t'\"A\u000b\u0002\u000bM\u001c\u0017\r\\1\u0016\u0007]q\"fE\u0002\u000111\u0002B!\u0007\u000e\u001dS5\t\u0001#\u0003\u0002\u001c!\tAQ*Y5o\u001d>$W\r\u0005\u0002\u001e=1\u0001A!B\u0010\u0001\u0005\u0004\t#!A&\u0004\u0001E\u0011!E\n\t\u0003G\u0011j\u0011\u0001F\u0005\u0003KQ\u0011qAT8uQ&tw\r\u0005\u0002$O%\u0011\u0001\u0006\u0006\u0002\u0004\u0003:L\bCA\u000f+\t\u0015Y\u0003A1\u0001\"\u0005\u00051\u0006\u0003B\r.9%J!A\f\t\u0003\r-3fj\u001c3f\u0003\u0005YW#\u0001\u000f\u0002\u0005-\u0004\u0013!\u0001<\u0016\u0003%\n!A\u001e\u0011\u0002\u0005!\u001cW#A\u001c\u0011\u0005\rB\u0014BA\u001d\u0015\u0005\rIe\u000e^\u0001\u0004Q\u000e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0003>}}\u0002\u0005\u0003B\r\u00019%BQaL\u0004A\u0002qAQAM\u0004A\u0002%BQ!N\u0004A\u0002]\nAaY8qsV\tQ(\u0001\u0006d_BLHk\\7cK\u0012\fAbY8qsVsGo\\7cK\u0012,\u0012A\u0012\t\u00053\u001dc\u0012&\u0003\u0002I!\t)1KT8eK\u000611N\u001e)bSJ,\u0012a\u0013\t\u0005G1c\u0012&\u0003\u0002N)\t1A+\u001e9mKJ\n!bY1dQ\u0016$7+\u001b>f)\t9\u0004\u000bC\u0003R\u0019\u0001\u0007!+\u0001\u0002diB\u00111eU\u0005\u0003)R\u0011a!\u00118z%\u00164\u0017!C6o_^t7+\u001b>f)\u00059\u0014AB:ue&tw\r\u0006\u0002ZCB\u0011!lX\u0007\u00027*\u0011A,X\u0001\u0005Y\u0006twMC\u0001_\u0003\u0011Q\u0017M^1\n\u0005\u0001\\&AB*ue&tw\rC\u0003c\u001d\u0001\u0007q'A\u0002mKZ\u0004")
public final class TNode<K, V>
extends MainNode<K, V>
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

    public TNode<K, V> copy() {
        return new TNode<K, V>(this.k(), this.v(), this.hc());
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
    public int cachedSize(Object ct) {
        return 1;
    }

    @Override
    public int knownSize() {
        return 1;
    }

    @Override
    public String string(int lev) {
        return new StringBuilder(0).append(StringOps$.MODULE$.$times$extension("  ", lev)).append(StringOps$.MODULE$.format$extension("TNode(%s, %s, %x, !)", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.k(), this.v(), this.hc()}))).toString();
    }

    public TNode(K k, V v, int hc) {
        this.k = k;
        this.v = v;
        this.hc = hc;
    }
}

