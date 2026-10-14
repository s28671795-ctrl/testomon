/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.Seq;
import scala.collection.SeqOps;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.mutable.Builder;
import scala.collection.mutable.HashSet;
import scala.collection.mutable.Map;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005}4qAC\u0006\u0011\u0002\u0007\u0005\u0001\u0003C\u0003/\u0001\u0011\u0005q\u0006C\u00034\u0001\u0011\u0005C\u0007C\u0003?\u0001\u0011\u0005s\bC\u0003H\u0001\u0011\u0005\u0003\nC\u0003O\u0001\u0011\u0005s\nC\u0003Z\u0001\u0011\u0005#\fC\u0003c\u0001\u0011\u00053\rC\u0003o\u0001\u0011\u0005s\u000eC\u0003y\u0001\u0011\u0005\u0013PA\u000bTiJL7\r^(qi&l\u0017N_3e'\u0016\fx\n]:\u000b\u00051i\u0011AC2pY2,7\r^5p]*\ta\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\tEa2%K\n\u0005\u0001I12\u0006\u0005\u0002\u0014)5\tQ\"\u0003\u0002\u0016\u001b\t\u0019\u0011I\\=\u0011\u000b]A\"D\t\u0015\u000e\u0003-I!!G\u0006\u0003\rM+\u0017o\u00149t!\tYB\u0004\u0004\u0001\u0005\ru\u0001AQ1\u0001\u001f\u0005\u0005\t\u0015CA\u0010\u0013!\t\u0019\u0002%\u0003\u0002\"\u001b\t9aj\u001c;iS:<\u0007CA\u000e$\t\u0019!\u0003\u0001\"b\u0001K\t\u00111iQ\u000b\u0003=\u0019\"QaJ\u0012C\u0002y\u0011Aa\u0018\u0013%cA\u00111$\u000b\u0003\u0007U\u0001!)\u0019\u0001\u0010\u0003\u0003\r\u0003Ra\u0006\u0017\u001bE!J!!L\u0006\u00035M#(/[2u\u001fB$\u0018.\\5{K\u0012LE/\u001a:bE2,w\n]:\u0002\r\u0011Jg.\u001b;%)\u0005\u0001\u0004CA\n2\u0013\t\u0011TB\u0001\u0003V]&$\u0018A\u00033jgRLgn\u0019;CsV\u0011Q\u0007\u0010\u000b\u0003QYBQa\u000e\u0002A\u0002a\n\u0011A\u001a\t\u0005'eR2(\u0003\u0002;\u001b\tIa)\u001e8di&|g.\r\t\u00037q\"Q!\u0010\u0002C\u0002y\u0011\u0011AQ\u0001\naJ,\u0007/\u001a8eK\u0012,\"\u0001Q\"\u0015\u0005\u0005+\u0005cA\u000e$\u0005B\u00111d\u0011\u0003\u0006{\r\u0011\r\u0001R\t\u00035IAQAR\u0002A\u0002\t\u000bA!\u001a7f[\u0006A\u0011\r\u001d9f]\u0012,G-\u0006\u0002J\u0019R\u0011!*\u0014\t\u00047\rZ\u0005CA\u000eM\t\u0015iDA1\u0001E\u0011\u00151E\u00011\u0001L\u0003-\t\u0007\u000f]3oI\u0016$\u0017\t\u001c7\u0016\u0005A\u001bFCA)U!\rY2E\u0015\t\u00037M#Q!P\u0003C\u0002\u0011CQ!V\u0003A\u0002Y\u000baa];gM&D\bcA\fX%&\u0011\u0001l\u0003\u0002\r\u0013R,'/\u00192mK>s7-Z\u0001\raJ,\u0007/\u001a8eK\u0012\fE\u000e\\\u000b\u00037z#\"\u0001X0\u0011\u0007m\u0019S\f\u0005\u0002\u001c=\u0012)QH\u0002b\u0001\t\")\u0001M\u0002a\u0001C\u00061\u0001O]3gSb\u00042aF,^\u0003\u0015\u0001\u0018\r\u001a+p+\t!w\rF\u0002fQ6\u00042aG\u0012g!\tYr\rB\u0003>\u000f\t\u0007A\tC\u0003j\u000f\u0001\u0007!.A\u0002mK:\u0004\"aE6\n\u00051l!aA%oi\")ai\u0002a\u0001M\u0006!A-\u001b4g+\t\u0001x\u000f\u0006\u0002)c\")!\u000f\u0003a\u0001g\u0006!A\u000f[1u!\r9BO^\u0005\u0003k.\u00111aU3r!\tYr\u000fB\u0003>\u0011\t\u0007A)A\u0005j]R,'o]3diV\u0011!P \u000b\u0003QmDQA]\u0005A\u0002q\u00042a\u0006;~!\tYb\u0010B\u0003>\u0013\t\u0007A\t")
public interface StrictOptimizedSeqOps<A, CC, C>
extends SeqOps<A, CC, C>,
StrictOptimizedIterableOps<A, CC, C> {
    public static /* synthetic */ Object distinctBy$(StrictOptimizedSeqOps $this, Function1 f) {
        return $this.distinctBy(f);
    }

    @Override
    default public <B> C distinctBy(Function1<A, B> f) {
        Builder builder = this.newSpecificBuilder();
        HashSet<B> seen = new HashSet<B>();
        Iterator it = this.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (!seen.add(f.apply(next))) continue;
            if (builder == null) {
                throw null;
            }
            builder.addOne(next);
        }
        return builder.result();
    }

    public static /* synthetic */ Object prepended$(StrictOptimizedSeqOps $this, Object elem) {
        return $this.prepended(elem);
    }

    @Override
    default public <B> CC prepended(B elem) {
        Builder b = this.iterableFactory().newBuilder();
        if (this.knownSize() >= 0) {
            b.sizeHint(this.length() + 1);
        }
        if (b == null) {
            throw null;
        }
        b.addOne(elem);
        b.addAll(this);
        return b.result();
    }

    public static /* synthetic */ Object appended$(StrictOptimizedSeqOps $this, Object elem) {
        return $this.appended(elem);
    }

    @Override
    default public <B> CC appended(B elem) {
        Builder b = this.iterableFactory().newBuilder();
        if (this.knownSize() >= 0) {
            b.sizeHint(this.length() + 1);
        }
        if (b == null) {
            throw null;
        }
        b.addAll(this);
        b.addOne(elem);
        return b.result();
    }

    public static /* synthetic */ Object appendedAll$(StrictOptimizedSeqOps $this, IterableOnce suffix) {
        return $this.appendedAll(suffix);
    }

    @Override
    default public <B> CC appendedAll(IterableOnce<B> suffix) {
        Builder strictOptimizedConcat_b = this.iterableFactory().newBuilder();
        if (strictOptimizedConcat_b == null) {
            throw null;
        }
        strictOptimizedConcat_b.addAll(this);
        strictOptimizedConcat_b.addAll(suffix);
        return strictOptimizedConcat_b.result();
    }

    public static /* synthetic */ Object prependedAll$(StrictOptimizedSeqOps $this, IterableOnce prefix) {
        return $this.prependedAll(prefix);
    }

    @Override
    default public <B> CC prependedAll(IterableOnce<B> prefix) {
        Builder b = this.iterableFactory().newBuilder();
        if (b == null) {
            throw null;
        }
        b.addAll(prefix);
        b.addAll(this);
        return b.result();
    }

    public static /* synthetic */ Object padTo$(StrictOptimizedSeqOps $this, int len, Object elem) {
        return $this.padTo(len, elem);
    }

    @Override
    default public <B> CC padTo(int len, B elem) {
        Builder b = this.iterableFactory().newBuilder();
        int L = this.length();
        b.sizeHint(Math.max(L, len));
        b.addAll(this);
        for (int diff = len - L; diff > 0; --diff) {
            b.addOne(elem);
        }
        return b.result();
    }

    public static /* synthetic */ Object diff$(StrictOptimizedSeqOps $this, Seq that) {
        return $this.diff(that);
    }

    @Override
    default public <B> C diff(Seq<B> that) {
        if (this.isEmpty() || that.isEmpty()) {
            return this.coll();
        }
        Map occ = this.occCounts(that);
        Builder b = this.newSpecificBuilder();
        this.foreach((Function1<Object, Option> & Serializable)x -> occ.updateWith(x, (Function1<Option, Option> & Serializable)x0$1 -> {
            boolean bl = false;
            Some some = null;
            if (None$.MODULE$.equals(x0$1)) {
                b.addOne(x);
                return None$.MODULE$;
            }
            if (x0$1 instanceof Some) {
                bl = true;
                some = (Some)x0$1;
                int n = BoxesRunTime.unboxToInt(some.value());
                if (1 == n) {
                    return None$.MODULE$;
                }
            }
            if (bl) {
                int n = BoxesRunTime.unboxToInt(some.value());
                return new Some<Integer>(n - 1);
            }
            throw new MatchError(x0$1);
        }));
        return b.result();
    }

    public static /* synthetic */ Object intersect$(StrictOptimizedSeqOps $this, Seq that) {
        return $this.intersect(that);
    }

    @Override
    default public <B> C intersect(Seq<B> that) {
        if (this.isEmpty() || that.isEmpty()) {
            return this.empty();
        }
        Map occ = this.occCounts(that);
        Builder b = this.newSpecificBuilder();
        this.foreach((Function1<Object, Option> & Serializable)x -> occ.updateWith(x, (Function1<Option, Option> & Serializable)x0$1 -> {
            if (None$.MODULE$.equals(x0$1)) {
                return None$.MODULE$;
            }
            if (x0$1 instanceof Some) {
                int n = BoxesRunTime.unboxToInt(((Some)x0$1).value());
                b.addOne(x);
                if (n == 1) {
                    return None$.MODULE$;
                }
                return new Some<Integer>(n - 1);
            }
            throw new MatchError(x0$1);
        }));
        return b.result();
    }

    public static void $init$(StrictOptimizedSeqOps $this) {
    }
}

