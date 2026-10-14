/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.IterableOnce;
import scala.collection.SetOps;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005]2qa\u0001\u0003\u0011\u0002\u0007\u0005\u0011\u0002C\u0003,\u0001\u0011\u0005A\u0006C\u00031\u0001\u0011\u0005\u0013GA\u000bTiJL7\r^(qi&l\u0017N_3e'\u0016$x\n]:\u000b\u0005\u00151\u0011AC2pY2,7\r^5p]*\tq!A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\t))r$J\n\u0005\u0001-y\u0001\u0006\u0005\u0002\r\u001b5\ta!\u0003\u0002\u000f\r\t1\u0011I\\=SK\u001a\u0004R\u0001E\t\u0014=\u0011j\u0011\u0001B\u0005\u0003%\u0011\u0011aaU3u\u001fB\u001c\bC\u0001\u000b\u0016\u0019\u0001!QA\u0006\u0001C\u0002]\u0011\u0011!Q\t\u00031m\u0001\"\u0001D\r\n\u0005i1!a\u0002(pi\"Lgn\u001a\t\u0003\u0019qI!!\b\u0004\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u0015?\u00111\u0001\u0005\u0001CC\u0002\u0005\u0012!aQ\"\u0016\u0005]\u0011C!B\u0012 \u0005\u00049\"\u0001B0%IE\u0002\"\u0001F\u0013\u0005\r\u0019\u0002AQ1\u0001(\u0005\u0005\u0019\u0015C\u0001\r\u0010!\u0015\u0001\u0012f\u0005\u0010%\u0013\tQCA\u0001\u000eTiJL7\r^(qi&l\u0017N_3e\u0013R,'/\u00192mK>\u00038/\u0001\u0004%S:LG\u000f\n\u000b\u0002[A\u0011ABL\u0005\u0003_\u0019\u0011A!\u00168ji\u000611m\u001c8dCR$\"\u0001\n\u001a\t\u000bM\u0012\u0001\u0019\u0001\u001b\u0002\tQD\u0017\r\u001e\t\u0004!U\u001a\u0012B\u0001\u001c\u0005\u00051IE/\u001a:bE2,wJ\\2f\u0001")
public interface StrictOptimizedSetOps<A, CC, C extends SetOps<A, CC, C>>
extends SetOps<A, CC, C>,
StrictOptimizedIterableOps<A, CC, C> {
    public static /* synthetic */ SetOps concat$(StrictOptimizedSetOps $this, IterableOnce that) {
        return $this.concat((IterableOnce<A>)that);
    }

    @Override
    default public C concat(IterableOnce<A> that) {
        Builder strictOptimizedConcat_b = this.newSpecificBuilder();
        if (strictOptimizedConcat_b == null) {
            throw null;
        }
        strictOptimizedConcat_b.addAll(this);
        strictOptimizedConcat_b.addAll(that);
        return (C)((SetOps)strictOptimizedConcat_b.result());
    }

    public static void $init$(StrictOptimizedSetOps $this) {
    }
}

