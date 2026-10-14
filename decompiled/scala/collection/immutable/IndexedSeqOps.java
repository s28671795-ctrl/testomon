/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.immutable.SeqOps;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00013q\u0001B\u0003\u0011\u0002\u0007\u0005A\u0002C\u0003.\u0001\u0011\u0005a\u0006C\u00033\u0001\u0011\u00053\u0007C\u0006<\u0001A\u0005\u0019\u0011!A\u0005\nqz$!D%oI\u0016DX\rZ*fc>\u00038O\u0003\u0002\u0007\u000f\u0005I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003\u0011%\t!bY8mY\u0016\u001cG/[8o\u0015\u0005Q\u0011!B:dC2\f7\u0001A\u000b\u0005\u001ba\u0011\u0003f\u0005\u0003\u0001\u001dIQ\u0003CA\b\u0011\u001b\u0005I\u0011BA\t\n\u0005\u0019\te.\u001f*fMB)1\u0003\u0006\f\"O5\tQ!\u0003\u0002\u0016\u000b\t11+Z9PaN\u0004\"a\u0006\r\r\u0001\u00111\u0011\u0004\u0001CC\u0002i\u0011\u0011!Q\t\u00037y\u0001\"a\u0004\u000f\n\u0005uI!a\u0002(pi\"Lgn\u001a\t\u0003\u001f}I!\u0001I\u0005\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u0018E\u001111\u0005\u0001CC\u0002\u0011\u0012!aQ\"\u0016\u0005i)C!\u0002\u0014#\u0005\u0004Q\"\u0001B0%II\u0002\"a\u0006\u0015\u0005\r%\u0002AQ1\u0001\u001b\u0005\u0005\u0019\u0005#B\u0016--\u0005:S\"A\u0004\n\u0005\u00119\u0011A\u0002\u0013j]&$H\u0005F\u00010!\ty\u0001'\u0003\u00022\u0013\t!QK\\5u\u0003\u0015\u0019H.[2f)\r9C'\u000f\u0005\u0006k\t\u0001\rAN\u0001\u0005MJ|W\u000e\u0005\u0002\u0010o%\u0011\u0001(\u0003\u0002\u0004\u0013:$\b\"\u0002\u001e\u0003\u0001\u00041\u0014!B;oi&d\u0017aC:va\u0016\u0014He\u001d7jG\u0016$2aJ\u001f?\u0011\u0015)4\u00011\u00017\u0011\u0015Q4\u00011\u00017\u0013\t\u0011D\u0006")
public interface IndexedSeqOps<A, CC, C>
extends SeqOps<A, CC, C>,
scala.collection.IndexedSeqOps<A, CC, C> {
    public /* synthetic */ Object scala$collection$immutable$IndexedSeqOps$$super$slice(int var1, int var2);

    public static /* synthetic */ Object slice$(IndexedSeqOps $this, int from, int until) {
        return $this.slice(from, until);
    }

    @Override
    default public C slice(int from, int until) {
        if (from <= 0 && until >= this.length()) {
            return this.coll();
        }
        return (C)this.scala$collection$immutable$IndexedSeqOps$$super$slice(from, until);
    }

    public static void $init$(IndexedSeqOps $this) {
    }
}

