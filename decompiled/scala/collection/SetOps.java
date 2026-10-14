/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import java.util.NoSuchElementException;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.collection.AbstractIterator;
import scala.collection.ArrayOps$;
import scala.collection.IndexedSeq;
import scala.collection.IndexedSeq$;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.Set;
import scala.collection.Set$;
import scala.collection.View;
import scala.collection.immutable.Range;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0005Mha\u0002\u000f\u001e!\u0003\r\tA\t\u0005\u0006\u0011\u0002!\t!\u0013\u0005\u0006\u001b\u00021\tA\u0014\u0005\u0006#\u0002!)A\u0015\u0005\u00061\u0002!\t!\u0017\u0005\u0006?\u0002!\t\u0001\u0019\u0005\u0006?\u0002!\t!\u001b\u0004\u0005U\u0002!1\u000e\u0003\u0005p\u000f\t\u0005\t\u0015!\u0003q\u0011!)wA!A!\u0002\u00131\u0007\"B:\b\t\u0003!\bBB=\bA\u0003%!\u0010\u0003\u0004~\u000f\u0001\u0006K!\u0012\u0005\u0006}\u001e!\ta \u0005\b\u0003\u00039A\u0011AA\u0002\u0011\u001d\tI\u0006\u0001C\u0001\u00037Bq!a\u0018\u0001\t\u000b\t\t\u0007C\u0004\u0002h\u00011\t!!\u001b\t\u000f\u00055\u0004\u0001\"\u0002\u0002p!9\u0011Q\u000f\u0001\u0005\u0002\u0005]\u0004bBAK\u0001\u0011\u0005\u0011q\u0013\u0005\b\u0003+\u0003A\u0011AAQ\u0011\u001d\tY\f\u0001C\u0001\u0003{Cq!!1\u0001\t\u0003\t\u0019\rC\u0004\u0002B\u0002!\t!!4\t\u000f\u0005m\u0007\u0001\"\u0002\u0002^\"9\u00111\u001d\u0001\u0005\u0006\u0005\u0015\bbBAv\u0001\u0011\u0015\u0011Q\u001e\u0002\u0007'\u0016$x\n]:\u000b\u0005yy\u0012AC2pY2,7\r^5p]*\t\u0001%A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\t\rr\u0003HP\n\u0005\u0001\u0011B#\t\u0005\u0002&M5\tq$\u0003\u0002(?\t1\u0011I\\=SK\u001a\u0004R!\u000b\u0016-ouj\u0011!H\u0005\u0003Wu\u00111\"\u0013;fe\u0006\u0014G.Z(qgB\u0011QF\f\u0007\u0001\t\u0015y\u0003A1\u00011\u0005\u0005\t\u0015CA\u00195!\t)#'\u0003\u00024?\t9aj\u001c;iS:<\u0007CA\u00136\u0013\t1tDA\u0002B]f\u0004\"!\f\u001d\u0005\re\u0002AQ1\u0001;\u0005\t\u00195)\u0006\u00021w\u0011)A\b\u000fb\u0001a\t!q\f\n\u00132!\tic\b\u0002\u0004@\u0001\u0011\u0015\r\u0001\u0011\u0002\u0002\u0007F\u0011\u0011'\u0011\t\u0006S\u0001as'\u0010\t\u0005K\rcS)\u0003\u0002E?\tIa)\u001e8di&|g.\r\t\u0003K\u0019K!aR\u0010\u0003\u000f\t{w\u000e\\3b]\u00061A%\u001b8ji\u0012\"\u0012A\u0013\t\u0003K-K!\u0001T\u0010\u0003\tUs\u0017\u000e^\u0001\tG>tG/Y5ogR\u0011Qi\u0014\u0005\u0006!\n\u0001\r\u0001L\u0001\u0005K2,W.A\u0003baBd\u0017\u0010\u0006\u0002F'\")\u0001k\u0001a\u0001Y!\u00121!\u0016\t\u0003KYK!aV\u0010\u0003\r%tG.\u001b8f\u0003!\u0019XOY:fi>3GCA#[\u0011\u0015YF\u00011\u0001]\u0003\u0011!\b.\u0019;\u0011\u0007%jF&\u0003\u0002_;\t\u00191+\u001a;\u0002\u000fM,(m]3ugR\u0011\u0011\r\u001a\t\u0004S\tl\u0014BA2\u001e\u0005!IE/\u001a:bi>\u0014\b\"B3\u0006\u0001\u00041\u0017a\u00017f]B\u0011QeZ\u0005\u0003Q~\u00111!\u00138u)\u0005\t'AC*vEN,Go]%ueN\u0011q\u0001\u001c\t\u0004S5l\u0014B\u00018\u001e\u0005A\t%m\u001d;sC\u000e$\u0018\n^3sCR|'/\u0001\u0003fY6\u001c\bcA\u0015rY%\u0011!/\b\u0002\u000b\u0013:$W\r_3e'\u0016\f\u0018A\u0002\u001fj]&$h\bF\u0002vob\u0004\"A^\u0004\u000e\u0003\u0001AQa\u001c\u0006A\u0002ADQ!\u001a\u0006A\u0002\u0019\fA!\u001b3ygB\u0019Qe\u001f4\n\u0005q|\"!B!se\u0006L\u0018\u0001C0iCNtU\r\u001f;\u0002\u000f!\f7OT3yiV\tQ)\u0001\u0003oKb$H#A\u001f)\u000b9\t9!a\u0007\u0011\u000b\u0015\nI!!\u0004\n\u0007\u0005-qD\u0001\u0004uQJ|wo\u001d\t\u0005\u0003\u001f\t)BD\u0002&\u0003#I1!a\u0005 \u0003\u001d\u0001\u0018mY6bO\u0016LA!a\u0006\u0002\u001a\t1bj\\*vG\",E.Z7f]R,\u0005pY3qi&|gNC\u0002\u0002\u0014}\ttAHA\u000f\u0003g\t9\u0006\u0005\u0003\u0002 \u00055b\u0002BA\u0011\u0003S\u00012!a\t \u001b\t\t)CC\u0002\u0002(\u0005\na\u0001\u0010:p_Rt\u0014bAA\u0016?\u00051\u0001K]3eK\u001aLA!a\f\u00022\t11\u000b\u001e:j]\u001eT1!a\u000b c%\u0019\u0013QGA\u001f\u0003\u001b\ny$\u0006\u0003\u00028\u0005eRCAA\u000f\t\u001d\tY$\tb\u0001\u0003\u000b\u0012\u0011\u0001V\u0005\u0005\u0003\u007f\t\t%A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%\r\u0006\u0004\u0003\u0007z\u0012A\u0002;ie><8/E\u00022\u0003\u000f\u0002B!a\u0004\u0002J%!\u00111JA\r\u0005%!\u0006N]8xC\ndW-M\u0005$\u0003\u001f\n\t&a\u0015\u0002D9\u0019Q%!\u0015\n\u0007\u0005\rs$M\u0003#K}\t)FA\u0003tG\u0006d\u0017-M\u0002'\u0003\u001b\t\u0011\"\u001b8uKJ\u001cXm\u0019;\u0015\u0007u\ni\u0006C\u0003\\\u001f\u0001\u0007A,\u0001\u0003%C6\u0004HcA\u001f\u0002d!)1\f\u0005a\u00019\"\u0012\u0001#V\u0001\u0005I&4g\rF\u0002>\u0003WBQaW\tA\u0002q\u000b!\u0002J1na\u0012\"\u0018\u000e\u001c3f)\ri\u0014\u0011\u000f\u0005\u00067J\u0001\r\u0001\u0018\u0015\u0003%U\u000bA\u0002J7j]V\u001cH%\\5okN$2!PA=\u0011\u0019Y6\u00031\u0001\u0002|A!\u0011&! -\u0013\r\ty(\b\u0002\r\u0013R,'/\u00192mK>s7-\u001a\u0015\f'\u0005\r\u0015\u0011RAF\u0003\u001f\u000b\t\nE\u0002&\u0003\u000bK1!a\" \u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\t\ti)A\u0012D_:\u001c\u0018\u000eZ3sAI,\u0017/^5sS:<\u0007%\u00198!S6lW\u000f^1cY\u0016\u00043+\u001a;\u0002\u000bMLgnY3\"\u0005\u0005M\u0015A\u0002\u001a/cMr\u0003'\u0001\u0004%[&tWo\u001d\u000b\u0004{\u0005e\u0005\"\u0002)\u0015\u0001\u0004a\u0003f\u0003\u000b\u0002\u0004\u0006%\u0015QTAH\u0003#\u000b#!a(\u0002y\r{gn]5eKJ\u0004#/Z9vSJLgn\u001a\u0011b]\u0002JW.\\;uC\ndW\rI*fi\u0002z'\u000f\t4bY2\u0004#-Y2lAQ|\u0007eU3u]\u0011LgM\u001a\u000b\b{\u0005\r\u0016qUAV\u0011\u0019\t)+\u0006a\u0001Y\u0005)Q\r\\3nc!1\u0011\u0011V\u000bA\u00021\nQ!\u001a7f[JBq!!,\u0016\u0001\u0004\ty+A\u0003fY\u0016l7\u000f\u0005\u0003&\u0003cc\u0013bAAZ?\tQAH]3qK\u0006$X\r\u001a )\u0017U\t\u0019)!#\u00028\u0006=\u0015\u0011S\u0011\u0003\u0003s\u000bQ)V:fA\u0019j\u0003e^5uQ\u0002\ng\u000eI3ya2L7-\u001b;!G>dG.Z2uS>t\u0007%\u0019:hk6,g\u000e\u001e\u0011j]N$X-\u00193!_\u001a\u0004S\u0006I<ji\"\u0004c/\u0019:be\u001e\u001c\u0018AB2p]\u000e\fG\u000fF\u0002>\u0003\u007fCaa\u0017\fA\u0002\u0005m\u0014!\u0002\u0013qYV\u001cHcA\u001f\u0002F\")\u0001k\u0006a\u0001Y!Zq#a!\u0002\n\u0006%\u0017qRAIC\t\tY-A\u001fD_:\u001c\u0018\u000eZ3sAI,\u0017/^5sS:<\u0007%\u00198!S6lW\u000f^1cY\u0016\u00043+\u001a;!_J\u0004c-\u00197mA\t\f7m\u001b\u0011u_\u0002\u001aV\r\u001e\u0018v]&|g\u000eF\u0004>\u0003\u001f\f\t.a5\t\r\u0005\u0015\u0006\u00041\u0001-\u0011\u0019\tI\u000b\u0007a\u0001Y!9\u0011Q\u0016\rA\u0002\u0005=\u0006f\u0003\r\u0002\u0004\u0006%\u0015q[AH\u0003#\u000b#!!7\u0002\u000bV\u001bX\rI\u0016,A]LG\u000f\u001b\u0011b]\u0002*\u0007\u0010\u001d7jG&$\beY8mY\u0016\u001cG/[8oA\u0005\u0014x-^7f]R\u0004\u0013N\\:uK\u0006$\u0007e\u001c4!W\u0001:\u0018\u000e\u001e5!m\u0006\u0014\u0018M]4t\u0003)!\u0003\u000f\\;tIAdWo\u001d\u000b\u0004{\u0005}\u0007BB.\u001a\u0001\u0004\tY\b\u000b\u0002\u001a+\u0006)QO\\5p]R\u0019Q(a:\t\u000bmS\u0002\u0019\u0001/)\u0005i)\u0016\u0001\u0002\u0013cCJ$2!PAx\u0011\u0015Y6\u00041\u0001]Q\tYR\u000b")
public interface SetOps<A, CC, C extends SetOps<A, CC, C>>
extends IterableOps<A, CC, C>,
Function1<A, Object> {
    public boolean contains(A var1);

    public static /* synthetic */ boolean apply$(SetOps $this, Object elem) {
        return $this.apply((A)elem);
    }

    @Override
    default public boolean apply(A elem) {
        return this.contains(elem);
    }

    public static /* synthetic */ boolean subsetOf$(SetOps $this, Set that) {
        return $this.subsetOf(that);
    }

    default public boolean subsetOf(Set<A> that) {
        return this.forall(that);
    }

    public static /* synthetic */ Iterator subsets$(SetOps $this, int len) {
        return $this.subsets(len);
    }

    default public Iterator<C> subsets(int len) {
        if (len < 0 || len > this.size()) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        IndexedSeq$ toFactory_factory = IndexedSeq$.MODULE$;
        Object var2_2 = null;
        return new SubsetsItr(this, this.to(new IterableFactory.ToFactory(toFactory_factory)), len);
    }

    public static /* synthetic */ Iterator subsets$(SetOps $this) {
        return $this.subsets();
    }

    default public Iterator<C> subsets() {
        return new AbstractIterator<C>(this){
            private final IndexedSeq<A> elms;
            private int len;
            private Iterator<C> itr;
            private final /* synthetic */ SetOps $outer;

            public boolean hasNext() {
                IndexedSeq<A> indexedSeq = this.elms;
                if (indexedSeq == null) {
                    throw null;
                }
                return this.len <= indexedSeq.length() || this.itr.hasNext();
            }

            public C next() {
                if (!this.itr.hasNext()) {
                    IndexedSeq<A> indexedSeq = this.elms;
                    if (indexedSeq == null) {
                        throw null;
                    }
                    if (this.len > indexedSeq.length()) {
                        Iterator$.scala$collection$Iterator$$_empty.next();
                    } else {
                        this.itr = new SubsetsItr(this.$outer, this.elms, this.len);
                        ++this.len;
                    }
                }
                return (C)((SetOps)this.itr.next());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                IndexedSeq$ toFactory_factory = IndexedSeq$.MODULE$;
                Object var2_2 = null;
                this.elms = $outer.to(new IterableFactory.ToFactory<A, IndexedSeq>(toFactory_factory));
                this.len = 0;
                this.itr = Iterator$.scala$collection$Iterator$$_empty;
            }
        };
    }

    public static /* synthetic */ SetOps intersect$(SetOps $this, Set that) {
        return $this.intersect(that);
    }

    default public C intersect(Set<A> that) {
        return (C)((SetOps)this.filter(that));
    }

    public static /* synthetic */ SetOps $amp$(SetOps $this, Set that) {
        return $this.$amp(that);
    }

    default public C $amp(Set<A> that) {
        return this.intersect(that);
    }

    public C diff(Set<A> var1);

    public static /* synthetic */ SetOps $amp$tilde$(SetOps $this, Set that) {
        return $this.$amp$tilde(that);
    }

    default public C $amp$tilde(Set<A> that) {
        return this.diff(that);
    }

    public static /* synthetic */ SetOps $minus$minus$(SetOps $this, IterableOnce that) {
        return $this.$minus$minus(that);
    }

    default public C $minus$minus(IterableOnce<A> that) {
        scala.collection.immutable.Set$ toFactory_factory = scala.collection.immutable.Set$.MODULE$;
        Object var3_2 = null;
        scala.collection.immutable.Set toRemove = that.iterator().to(new IterableFactory.ToFactory(toFactory_factory));
        return (C)((SetOps)this.fromSpecific((IterableOnce)this.view().filterNot(toRemove)));
    }

    public static /* synthetic */ SetOps $minus$(SetOps $this, Object elem) {
        return $this.$minus(elem);
    }

    default public C $minus(A elem) {
        return this.diff((Set)Set$.MODULE$.apply(ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{elem})));
    }

    public static /* synthetic */ SetOps $minus$(SetOps $this, Object elem1, Object elem2, Seq elems) {
        return $this.$minus(elem1, elem2, elems);
    }

    default public C $minus(A elem1, A elem2, Seq<A> elems) {
        scala.collection.immutable.Set set = elems.toSet();
        if (set == null) {
            throw null;
        }
        Object c = set.incl(elem1);
        if (c == null) {
            throw null;
        }
        return this.diff((Set)c.incl(elem2));
    }

    public static /* synthetic */ SetOps concat$(SetOps $this, IterableOnce that) {
        return $this.concat((IterableOnce<A>)that);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    default public C concat(IterableOnce<A> that) {
        IterableOnce<Object> iterableOnce;
        if (this instanceof Set.Set1 ? true : (this instanceof Set.Set2 ? true : (this instanceof Set.Set3 ? true : this instanceof Set.Set4))) {
            scala.collection.immutable.SetOps result = (scala.collection.immutable.SetOps)this;
            Iterator<A> it = that.iterator();
            while (it.hasNext()) {
                void $plus_elem;
                A a = it.next();
                if (result == null) {
                    throw null;
                }
                a = null;
                result = result.incl($plus_elem);
            }
            return (C)result;
        }
        if (that instanceof Iterable) {
            Iterable iterable = (Iterable)that;
            iterableOnce = new View.Concat(this, iterable);
        } else {
            iterableOnce = this.iterator().concat((Function0<Iterator> & Serializable)() -> that.iterator());
        }
        return (C)((SetOps)this.fromSpecific(iterableOnce));
    }

    public static /* synthetic */ SetOps $plus$(SetOps $this, Object elem) {
        return $this.$plus(elem);
    }

    default public C $plus(A elem) {
        return (C)((SetOps)this.fromSpecific(new View.Appended<A>(this, elem)));
    }

    public static /* synthetic */ SetOps $plus$(SetOps $this, Object elem1, Object elem2, Seq elems) {
        return $this.$plus(elem1, elem2, elems);
    }

    default public C $plus(A elem1, A elem2, Seq<A> elems) {
        return (C)((SetOps)this.fromSpecific(new View.Concat(new View.Appended<A>(new View.Appended<A>(this, elem1), elem2), elems)));
    }

    public static /* synthetic */ SetOps $plus$plus$(SetOps $this, IterableOnce that) {
        return $this.$plus$plus((IterableOnce<A>)that);
    }

    @Override
    default public C $plus$plus(IterableOnce<A> that) {
        return this.concat(that);
    }

    public static /* synthetic */ SetOps union$(SetOps $this, Set that) {
        return $this.union(that);
    }

    default public C union(Set<A> that) {
        return this.concat(that);
    }

    public static /* synthetic */ SetOps $bar$(SetOps $this, Set that) {
        return $this.$bar(that);
    }

    default public C $bar(Set<A> that) {
        return this.concat(that);
    }

    public static void $init$(SetOps $this) {
    }

    private class SubsetsItr
    extends AbstractIterator<C> {
        private final IndexedSeq<A> elms;
        private final int len;
        private final int[] idxs;
        private boolean _hasNext;
        public final /* synthetic */ SetOps $outer;

        @Override
        public boolean hasNext() {
            return this._hasNext;
        }

        @Override
        public C next() throws NoSuchElementException {
            int i;
            if (!this.hasNext()) {
                Iterator$.scala$collection$Iterator$$_empty.next();
            }
            Builder buf = this.scala$collection$SetOps$SubsetsItr$$$outer().newSpecificBuilder();
            for (int n : (int[])ArrayOps$.MODULE$.slice$extension(this.idxs, 0, this.len)) {
                SubsetsItr.$anonfun$next$1(this, buf, n);
            }
            Object var5_2 = null;
            SetOps result = (SetOps)buf.result();
            for (i = this.len - 1; i >= 0 && this.idxs[i] == this.idxs[i + 1] - 1; --i) {
            }
            if (i < 0) {
                this._hasNext = false;
            } else {
                this.idxs[i] = this.idxs[i] + 1;
                int n = i + 1;
                int until$extension_end = this.len;
                Range.Exclusive foreach$mVc$sp_this = new Range.Exclusive(n, until$extension_end, 1);
                if (!foreach$mVc$sp_this.isEmpty()) {
                    int foreach$mVc$sp_i = foreach$mVc$sp_this.start();
                    while (true) {
                        this.idxs[foreach$mVc$sp_i] = this.idxs[foreach$mVc$sp_i - 1] + 1;
                        if (foreach$mVc$sp_i == foreach$mVc$sp_this.scala$collection$immutable$Range$$lastElement) break;
                        foreach$mVc$sp_i += foreach$mVc$sp_this.step();
                    }
                }
            }
            return result;
        }

        public /* synthetic */ SetOps scala$collection$SetOps$SubsetsItr$$$outer() {
            return this.$outer;
        }

        /*
         * WARNING - void declaration
         */
        public static final /* synthetic */ Builder $anonfun$next$1(SubsetsItr $this, Builder buf$1, int idx) {
            void $plus$eq_elem;
            Object r = $this.elms.apply(idx);
            if (buf$1 == null) {
                throw null;
            }
            return (Builder)buf$1.addOne($plus$eq_elem);
        }

        public SubsetsItr(SetOps $outer, IndexedSeq<A> elms, int len) {
            this.elms = elms;
            this.len = len;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            this.idxs = Array$.MODULE$.range(0, len + 1, 1);
            this._hasNext = true;
            if (elms == null) {
                throw null;
            }
            this.idxs[len] = elms.length();
        }

        public static final /* synthetic */ Builder $anonfun$next$1$adapted(SubsetsItr $this, Builder buf$1, Object idx) {
            return SubsetsItr.$anonfun$next$1($this, buf$1, BoxesRunTime.unboxToInt(idx));
        }
    }
}

