/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.$less$colon$less;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.IndexedSeq;
import scala.collection.Iterable;
import scala.collection.Iterable$;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceExtensionMethods;
import scala.collection.Iterator;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Map$;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Seq$;
import scala.collection.immutable.Set;
import scala.collection.immutable.Set$;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Stream$;
import scala.collection.immutable.Vector;
import scala.collection.immutable.Vector$;
import scala.collection.mutable.ArrayBuffer$;
import scala.collection.mutable.Buffer;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.reflect.ClassTag;

public final class IterableOnceExtensionMethods$ {
    public static final IterableOnceExtensionMethods$ MODULE$ = new IterableOnceExtensionMethods$();

    public final <A> Iterator<A> withFilter$extension(IterableOnce<A> $this, Function1<A, Object> f) {
        return $this.iterator().withFilter(f);
    }

    public final <A> Option<A> reduceLeftOption$extension(IterableOnce<A> $this, Function2<A, A, A> f) {
        return $this.iterator().reduceLeftOption(f);
    }

    public final <A> A min$extension(IterableOnce<A> $this, Ordering<A> ord) {
        return $this.iterator().min(ord);
    }

    public final <A> boolean nonEmpty$extension(IterableOnce<A> $this) {
        return $this.iterator().nonEmpty();
    }

    public final <A> A max$extension(IterableOnce<A> $this, Ordering<A> ord) {
        return $this.iterator().max(ord);
    }

    public final <A> A reduceRight$extension(IterableOnce<A> $this, Function2<A, A, A> f) {
        return $this.iterator().reduceRight(f);
    }

    public final <B, A> A maxBy$extension(IterableOnce<A> $this, Function1<A, B> f, Ordering<B> cmp) {
        return $this.iterator().maxBy(f, cmp);
    }

    public final <A> A reduceLeft$extension(IterableOnce<A> $this, Function2<A, A, A> f) {
        return $this.iterator().reduceLeft(f);
    }

    public final <A> A sum$extension(IterableOnce<A> $this, Numeric<A> num) {
        return $this.iterator().sum(num);
    }

    public final <A> A product$extension(IterableOnce<A> $this, Numeric<A> num) {
        return $this.iterator().product(num);
    }

    public final <A> int count$extension(IterableOnce<A> $this, Function1<A, Object> f) {
        return $this.iterator().count(f);
    }

    public final <A> Option<A> reduceOption$extension(IterableOnce<A> $this, Function2<A, A, A> f) {
        return $this.iterator().reduceOption(f);
    }

    public final <B, A> A minBy$extension(IterableOnce<A> $this, Function1<A, B> f, Ordering<B> cmp) {
        return $this.iterator().minBy(f, cmp);
    }

    public final <A> int size$extension(IterableOnce<A> $this) {
        return $this.iterator().size();
    }

    public final <A> boolean forall$extension(IterableOnce<A> $this, Function1<A, Object> f) {
        return $this.iterator().forall(f);
    }

    public final <B, A> Option<B> collectFirst$extension(IterableOnce<A> $this, PartialFunction<A, B> f) {
        return $this.iterator().collectFirst(f);
    }

    public final <A> Iterator<A> filter$extension(IterableOnce<A> $this, Function1<A, Object> f) {
        return $this.iterator().filter(f);
    }

    public final <A> boolean exists$extension(IterableOnce<A> $this, Function1<A, Object> f) {
        return $this.iterator().exists(f);
    }

    public final <A> void copyToBuffer$extension(IterableOnce<A> $this, Buffer<A> dest) {
        Iterator<A> iterator2 = $this.iterator();
        if (iterator2 == null) {
            throw null;
        }
        Iterator<A> copyToBuffer_this = iterator2;
        if (dest == null) {
            throw null;
        }
        dest.addAll(copyToBuffer_this);
    }

    public final <A> A reduce$extension(IterableOnce<A> $this, Function2<A, A, A> f) {
        return $this.iterator().reduce(f);
    }

    public final <A> Option<A> reduceRightOption$extension(IterableOnce<A> $this, Function2<A, A, A> f) {
        return $this.iterator().reduceRightOption(f);
    }

    public final <A> IndexedSeq<A> toIndexedSeq$extension(IterableOnce<A> $this) {
        return $this.iterator().toIndexedSeq();
    }

    public final <U, A> void foreach$extension(IterableOnce<A> $this, Function1<A, U> f) {
        if ($this instanceof Iterable) {
            ((Iterable)$this).foreach(f);
            return;
        }
        $this.iterator().foreach(f);
    }

    public final <C1, A> C1 to$extension(IterableOnce<A> $this, Factory<A, C1> factory2) {
        return factory2.fromSpecific($this);
    }

    public final <B, A> Buffer<B> toBuffer$extension(IterableOnce<A> $this) {
        return ArrayBuffer$.MODULE$.from((IterableOnce)$this);
    }

    public final <B, A> Object toArray$extension(IterableOnce<A> $this, ClassTag<B> evidence$1) {
        if ($this instanceof Iterable) {
            return ((Iterable)$this).toArray(evidence$1);
        }
        return $this.iterator().toArray(evidence$1);
    }

    public final <A> List<A> toList$extension(IterableOnce<A> $this) {
        return Nil$.MODULE$.prependedAll($this);
    }

    public final <B, A> Set<B> toSet$extension(IterableOnce<A> $this) {
        return Set$.MODULE$.from((IterableOnce)$this);
    }

    public final <A> Iterable<A> toTraversable$extension(IterableOnce<A> $this) {
        return (Iterable)Iterable$.MODULE$.from($this);
    }

    public final <A> Iterable<A> toIterable$extension(IterableOnce<A> $this) {
        return (Iterable)Iterable$.MODULE$.from($this);
    }

    public final <A> Seq<A> toSeq$extension(IterableOnce<A> $this) {
        return Seq$.MODULE$.from((IterableOnce)$this);
    }

    public final <A> Stream<A> toStream$extension(IterableOnce<A> $this) {
        return Stream$.MODULE$.from((IterableOnce)$this);
    }

    public final <A> Vector<A> toVector$extension(IterableOnce<A> $this) {
        return Vector$.MODULE$.from((IterableOnce)$this);
    }

    public final <K, V, A> Map<K, V> toMap$extension(IterableOnce<A> $this, $less$colon$less<A, Tuple2<K, V>> ev) {
        return Map$.MODULE$.from($this);
    }

    public final <A> Iterator<A> toIterator$extension(IterableOnce<A> $this) {
        return $this.iterator();
    }

    public final <A> boolean isEmpty$extension(IterableOnce<A> $this) {
        if ($this instanceof Iterable) {
            return ((Iterable)$this).isEmpty();
        }
        return $this.iterator().isEmpty();
    }

    public final <A> String mkString$extension(IterableOnce<A> $this, String start2, String sep, String end) {
        if ($this instanceof Iterable) {
            return ((Iterable)$this).mkString(start2, sep, end);
        }
        return $this.iterator().mkString(start2, sep, end);
    }

    public final <A> String mkString$extension(IterableOnce<A> $this, String sep) {
        if ($this instanceof Iterable) {
            Iterable iterable = (Iterable)$this;
            if (iterable == null) {
                throw null;
            }
            return iterable.mkString("", sep, "");
        }
        Iterator<A> iterator2 = $this.iterator();
        if (iterator2 == null) {
            throw null;
        }
        return iterator2.mkString("", sep, "");
    }

    public final <A> String mkString$extension(IterableOnce<A> $this) {
        if ($this instanceof Iterable) {
            Iterable iterable = (Iterable)$this;
            if (iterable == null) {
                throw null;
            }
            String mkString_mkString_sep = "";
            return iterable.mkString("", mkString_mkString_sep, "");
        }
        Iterator<A> iterator2 = $this.iterator();
        if (iterator2 == null) {
            throw null;
        }
        Iterator<A> mkString_this = iterator2;
        String mkString_mkString_sep = "";
        return mkString_this.mkString("", mkString_mkString_sep, "");
    }

    public final <A> Option<A> find$extension(IterableOnce<A> $this, Function1<A, Object> p) {
        return $this.iterator().find(p);
    }

    public final <B, A> B foldLeft$extension(IterableOnce<A> $this, B z, Function2<B, A, B> op) {
        return $this.iterator().foldLeft(z, op);
    }

    public final <B, A> B foldRight$extension(IterableOnce<A> $this, B z, Function2<A, B, B> op) {
        return $this.iterator().foldRight(z, op);
    }

    public final <A1, A> A1 fold$extension(IterableOnce<A> $this, A1 z, Function2<A1, A1, A1> op) {
        return $this.iterator().fold(z, op);
    }

    public final <B, A> B $div$colon$extension(IterableOnce<A> $this, B z, Function2<B, A, B> op) {
        return $this.iterator().foldLeft(z, op);
    }

    public final <B, A> B $colon$bslash$extension(IterableOnce<A> $this, B z, Function2<A, B, B> op) {
        return $this.iterator().foldRight(z, op);
    }

    public final <B, A> IterableOnce<B> map$extension(IterableOnce<A> $this, Function1<A, B> f) {
        if ($this instanceof Iterable) {
            return (IterableOnce)((Iterable)$this).map(f);
        }
        return $this.iterator().map(f);
    }

    public final <B, A> IterableOnce<B> flatMap$extension(IterableOnce<A> $this, Function1<A, IterableOnce<B>> f) {
        if ($this instanceof Iterable) {
            return (IterableOnce)((Iterable)$this).flatMap(f);
        }
        return $this.iterator().flatMap(f);
    }

    public final <B, A> boolean sameElements$extension(IterableOnce<A> $this, IterableOnce<B> that) {
        return $this.iterator().sameElements(that);
    }

    public final <A> int hashCode$extension(IterableOnce<A> $this) {
        return $this.hashCode();
    }

    public final <A> boolean equals$extension(IterableOnce<A> $this, Object x$1) {
        if (x$1 instanceof IterableOnceExtensionMethods) {
            IterableOnce iterableOnce = x$1 == null ? null : ((IterableOnceExtensionMethods)x$1).scala$collection$IterableOnceExtensionMethods$$it();
            IterableOnce<A> iterableOnce2 = $this;
            if (!(iterableOnce2 != null ? !iterableOnce2.equals(iterableOnce) : iterableOnce != null)) {
                return true;
            }
        }
        return false;
    }

    private IterableOnceExtensionMethods$() {
    }
}

