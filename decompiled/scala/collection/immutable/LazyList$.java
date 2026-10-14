/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.LinearSeq;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.immutable.LazyList;
import scala.collection.immutable.LazyList$State$Empty$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.math.Integral;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;
import scala.runtime.ObjectRef;
import scala.runtime.Statics;

public final class LazyList$
implements SeqFactory<LazyList> {
    public static final LazyList$ MODULE$ = new LazyList$();
    private static final long serialVersionUID = 3L;
    private static final LazyList<Nothing$> _empty;
    private static final Function1<Object, Object> scala$collection$immutable$LazyList$$anyToMarker;

    static {
        Function0<LazyList$State$Empty$> & Serializable scala$collection$immutable$LazyList$$newLL_state = (Function0<LazyList$State$Empty$> & Serializable)() -> LazyList$State$Empty$.MODULE$;
        Object var0 = null;
        _empty = new LazyList(scala$collection$immutable$LazyList$$newLL_state).force();
        scala$collection$immutable$LazyList$$anyToMarker = (Function1<Object, Object> & Serializable)x$10 -> Statics.pfMarker;
    }

    @Override
    public final SeqOps unapplySeq(SeqOps x) {
        return SeqFactory.unapplySeq$(this, x);
    }

    @Override
    public Object apply(Seq elems) {
        return IterableFactory.apply$(this, elems);
    }

    @Override
    public Object iterate(Object start2, int len, Function1 f) {
        return IterableFactory.iterate$(this, start2, len, f);
    }

    @Override
    public Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(this, start2, end, evidence$3);
    }

    @Override
    public Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(this, start2, end, step, evidence$4);
    }

    @Override
    public Object fill(int n1, int n2, Function0 elem) {
        return IterableFactory.fill$(this, n1, n2, elem);
    }

    @Override
    public Object fill(int n1, int n2, int n3, Function0 elem) {
        return IterableFactory.fill$(this, n1, n2, n3, elem);
    }

    @Override
    public Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        return IterableFactory.fill$(this, n1, n2, n3, n4, elem);
    }

    @Override
    public Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        return IterableFactory.fill$(this, n1, n2, n3, n4, n5, elem);
    }

    @Override
    public Object tabulate(int n1, int n2, Function2 f) {
        return IterableFactory.tabulate$(this, n1, n2, f);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, Function3 f) {
        return IterableFactory.tabulate$(this, n1, n2, n3, f);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        return IterableFactory.tabulate$(this, n1, n2, n3, n4, f);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        return IterableFactory.tabulate$(this, n1, n2, n3, n4, n5, f);
    }

    @Override
    public <A> Factory<A, LazyList<A>> iterableFactory() {
        return IterableFactory.iterableFactory$(this);
    }

    public <A> LazyList<A> scala$collection$immutable$LazyList$$newLL(Function0<LazyList.State<A>> state) {
        return new LazyList<A>(state);
    }

    public <A> LazyList.State<A> scala$collection$immutable$LazyList$$sCons(A hd, LazyList<A> tl) {
        return new LazyList.State.Cons<A>(hd, tl);
    }

    public Function1<Object, Object> scala$collection$immutable$LazyList$$anyToMarker() {
        return scala$collection$immutable$LazyList$$anyToMarker;
    }

    public <A> LazyList<A> scala$collection$immutable$LazyList$$filterImpl(LazyList<A> ll, Function1<A, Object> p, boolean isFlipped) {
        Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$newLL_state = () -> LazyList$.$anonfun$filterImpl$1(new ObjectRef<LazyList<A>>(ll), p, isFlipped);
        return new LazyList(scala$collection$immutable$LazyList$$newLL_state);
    }

    public <A, B> LazyList<B> scala$collection$immutable$LazyList$$collectImpl(LazyList<A> ll, PartialFunction<A, B> pf) {
        Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$newLL_state = () -> LazyList$.$anonfun$collectImpl$1(new ObjectRef<LazyList<A>>(ll), pf);
        return new LazyList(scala$collection$immutable$LazyList$$newLL_state);
    }

    public <A, B> LazyList<B> scala$collection$immutable$LazyList$$flatMapImpl(LazyList<A> ll, Function1<A, IterableOnce<B>> f) {
        Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$newLL_state = () -> LazyList$.$anonfun$flatMapImpl$1(new ObjectRef<LazyList<A>>(ll), f);
        return new LazyList(scala$collection$immutable$LazyList$$newLL_state);
    }

    public <A> LazyList<A> scala$collection$immutable$LazyList$$dropImpl(LazyList<A> ll, int n) {
        ObjectRef<LazyList<A>> restRef = new ObjectRef<LazyList<A>>(ll);
        IntRef iRef = new IntRef(n);
        Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$newLL_state = (Function0<LazyList.State> & Serializable)() -> {
            LinearSeq rest = (LazyList)restRef$4.elem;
            int i = iRef$1.elem;
            while (i > 0 && !rest.isEmpty()) {
                rest = rest.tail();
                restRef$4.elem = rest;
                iRef$1.elem = --i;
            }
            return rest.scala$collection$immutable$LazyList$$state();
        };
        return new LazyList(scala$collection$immutable$LazyList$$newLL_state);
    }

    public <A> LazyList<A> scala$collection$immutable$LazyList$$dropWhileImpl(LazyList<A> ll, Function1<A, Object> p) {
        Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$newLL_state = () -> LazyList$.$anonfun$dropWhileImpl$1(new ObjectRef<LazyList<A>>(ll), p);
        return new LazyList(scala$collection$immutable$LazyList$$newLL_state);
    }

    public <A> LazyList<A> scala$collection$immutable$LazyList$$takeRightImpl(LazyList<A> ll, int n) {
        ObjectRef<LazyList<A>> restRef = new ObjectRef<LazyList<A>>(ll);
        ObjectRef<LazyList<A>> scoutRef = new ObjectRef<LazyList<A>>(ll);
        IntRef remainingRef = new IntRef(n);
        Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$newLL_state = (Function0<LazyList.State> & Serializable)() -> {
            LinearSeq scout = (LazyList)scoutRef$1.elem;
            int remaining = remainingRef$1.elem;
            while (remaining > 0 && !((LazyList)scout).isEmpty()) {
                scout = ((LazyList)scout).tail();
                scoutRef$1.elem = scout;
                remainingRef$1.elem = --remaining;
            }
            LinearSeq rest = (LazyList)restRef$6.elem;
            while (!((LazyList)scout).isEmpty()) {
                scout = ((LazyList)scout).tail();
                scoutRef$1.elem = scout;
                rest = rest.tail();
                restRef$6.elem = rest;
            }
            return rest.scala$collection$immutable$LazyList$$state();
        };
        return new LazyList(scala$collection$immutable$LazyList$$newLL_state);
    }

    public <A> Function0<LazyList<A>> toDeferrer(Function0<LazyList<A>> l) {
        return l;
    }

    @Override
    public <A> LazyList<A> from(IterableOnce<A> coll) {
        if (coll instanceof LazyList) {
            return (LazyList)coll;
        }
        if (coll.knownSize() == 0) {
            return this.empty();
        }
        Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$newLL_state = (Function0<LazyList.State> & Serializable)() -> MODULE$.scala$collection$immutable$LazyList$$stateFromIterator(coll.iterator());
        return new LazyList(scala$collection$immutable$LazyList$$newLL_state);
    }

    @Override
    public <A> LazyList<A> empty() {
        return _empty;
    }

    /*
     * WARNING - void declaration
     */
    public <A> LazyList.State<A> scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix(Iterator<A> it, Function0<LazyList.State<A>> suffix) {
        if (it.hasNext()) {
            void scala$collection$immutable$LazyList$$sCons_tl;
            Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$newLL_state = (Function0<LazyList.State> & Serializable)() -> MODULE$.scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix(it, suffix);
            Object var3_3 = null;
            LazyList lazyList = new LazyList(scala$collection$immutable$LazyList$$newLL_state);
            A scala$collection$immutable$LazyList$$sCons_hd = it.next();
            return new LazyList.State.Cons<A>(scala$collection$immutable$LazyList$$sCons_hd, scala$collection$immutable$LazyList$$sCons_tl);
        }
        return suffix.apply();
    }

    /*
     * WARNING - void declaration
     */
    public <A> LazyList.State<A> scala$collection$immutable$LazyList$$stateFromIterator(Iterator<A> it) {
        if (it.hasNext()) {
            void scala$collection$immutable$LazyList$$sCons_tl;
            Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$newLL_state = (Function0<LazyList.State> & Serializable)() -> MODULE$.scala$collection$immutable$LazyList$$stateFromIterator(it);
            Object var2_2 = null;
            LazyList lazyList = new LazyList(scala$collection$immutable$LazyList$$newLL_state);
            A scala$collection$immutable$LazyList$$sCons_hd = it.next();
            return new LazyList.State.Cons<A>(scala$collection$immutable$LazyList$$sCons_hd, scala$collection$immutable$LazyList$$sCons_tl);
        }
        return LazyList$State$Empty$.MODULE$;
    }

    @Override
    public <A> LazyList<A> concat(Seq<Iterable<A>> xss) {
        if (xss.knownSize() == 0) {
            return this.empty();
        }
        Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$newLL_state = (Function0<LazyList.State> & Serializable)() -> MODULE$.concatIterator(xss.iterator());
        return new LazyList(scala$collection$immutable$LazyList$$newLL_state);
    }

    /*
     * WARNING - void declaration
     */
    private <A> LazyList.State<A> concatIterator(Iterator<Iterable<A>> it) {
        if (!it.hasNext()) {
            return LazyList$State$Empty$.MODULE$;
        }
        Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_suffix = (Function0<LazyList.State> & Serializable)() -> MODULE$.concatIterator(it);
        Iterator scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_it = ((IterableOnce)it.next()).iterator();
        if (scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_it.hasNext()) {
            void scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_scala$collection$immutable$LazyList$$sCons_tl;
            Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_scala$collection$immutable$LazyList$$newLL_state = (Function0<LazyList.State> & Serializable)() -> MODULE$.scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix(it, suffix);
            Object var4_4 = null;
            LazyList lazyList = new LazyList(scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_scala$collection$immutable$LazyList$$newLL_state);
            Object scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_scala$collection$immutable$LazyList$$sCons_hd = scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_it.next();
            return new LazyList.State.Cons(scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_scala$collection$immutable$LazyList$$sCons_hd, scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_scala$collection$immutable$LazyList$$sCons_tl);
        }
        return LazyList$.MODULE$.concatIterator(it);
    }

    public <A> LazyList<A> iterate(Function0<A> start2, Function1<A, A> f) {
        Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$newLL_state = (Function0<LazyList.State> & Serializable)() -> {
            Object head = start2.apply();
            Function0<Object> & Serializable iterate_start = (Function0<Object> & Serializable)() -> f.apply(head);
            Function0<LazyList.State> & Serializable iterate_scala$collection$immutable$LazyList$$newLL_state = () -> LazyList$.$anonfun$iterate$1(iterate_start, f);
            Object var5_4 = null;
            Object var4_3 = null;
            LazyList scala$collection$immutable$LazyList$$sCons_tl = new LazyList(iterate_scala$collection$immutable$LazyList$$newLL_state);
            return new LazyList.State.Cons(head, scala$collection$immutable$LazyList$$sCons_tl);
        };
        return new LazyList(scala$collection$immutable$LazyList$$newLL_state);
    }

    public LazyList<Object> from(int start2, int step) {
        Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$newLL_state = (Function0<LazyList.State> & Serializable)() -> {
            LazyList<Object> scala$collection$immutable$LazyList$$sCons_tl = MODULE$.from(start2 + step, step);
            Integer scala$collection$immutable$LazyList$$sCons_hd = start2;
            return new LazyList.State.Cons<Object>(scala$collection$immutable$LazyList$$sCons_hd, scala$collection$immutable$LazyList$$sCons_tl);
        };
        return new LazyList<Object>(scala$collection$immutable$LazyList$$newLL_state);
    }

    public LazyList<Object> from(int start2) {
        return this.from(start2, 1);
    }

    public <A> LazyList<A> continually(Function0<A> elem) {
        Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$newLL_state = (Function0<LazyList.State> & Serializable)() -> {
            Function0<LazyList.State> & Serializable continually_scala$collection$immutable$LazyList$$newLL_state = () -> LazyList$.$anonfun$continually$1(elem);
            Object var3_1 = null;
            LazyList scala$collection$immutable$LazyList$$sCons_tl = new LazyList(continually_scala$collection$immutable$LazyList$$newLL_state);
            Object scala$collection$immutable$LazyList$$sCons_hd = elem.apply();
            return new LazyList.State.Cons(scala$collection$immutable$LazyList$$sCons_hd, scala$collection$immutable$LazyList$$sCons_tl);
        };
        return new LazyList(scala$collection$immutable$LazyList$$newLL_state);
    }

    @Override
    public <A> LazyList<A> fill(int n, Function0<A> elem) {
        if (n > 0) {
            Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$newLL_state = (Function0<LazyList.State> & Serializable)() -> {
                Object var6_4;
                Object object;
                void fill_n;
                Object r = elem.apply();
                int n = n - 1;
                LazyList$ fill_this = MODULE$;
                if (fill_n > false) {
                    Function0<LazyList.State> & Serializable fill_scala$collection$immutable$LazyList$$newLL_state = () -> LazyList$.$anonfun$fill$1(elem, (int)fill_n);
                    object = new LazyList(fill_scala$collection$immutable$LazyList$$newLL_state);
                    var6_4 = null;
                } else {
                    object = fill_this.empty();
                }
                Object var4_3 = null;
                var6_4 = null;
                Object scala$collection$immutable$LazyList$$sCons_tl = object;
                Object scala$collection$immutable$LazyList$$sCons_hd = r;
                return new LazyList.State.Cons(scala$collection$immutable$LazyList$$sCons_hd, scala$collection$immutable$LazyList$$sCons_tl);
            };
            return new LazyList(scala$collection$immutable$LazyList$$newLL_state);
        }
        return this.empty();
    }

    @Override
    public <A> LazyList<A> tabulate(int n, Function1<Object, A> f) {
        int at$1_index = 0;
        if (at$1_index < n) {
            Function0<LazyList.State> & Serializable at$1_scala$collection$immutable$LazyList$$newLL_state = (Function0<LazyList.State> & Serializable)() -> {
                void scala$collection$immutable$LazyList$$sCons_tl;
                Object var7_5;
                Object object;
                Object r = f.apply(at$1_index);
                int at$1_index = at$1_index + 1;
                if (at$1_index < n) {
                    Function0<LazyList.State> & Serializable at$1_scala$collection$immutable$LazyList$$newLL_state = () -> LazyList$.$anonfun$tabulate$1(this, f, at$1_index, n);
                    object = new LazyList(at$1_scala$collection$immutable$LazyList$$newLL_state);
                    var7_5 = null;
                } else {
                    object = this.empty();
                }
                var7_5 = null;
                Object object2 = object;
                Object scala$collection$immutable$LazyList$$sCons_hd = r;
                return new LazyList.State.Cons(scala$collection$immutable$LazyList$$sCons_hd, scala$collection$immutable$LazyList$$sCons_tl);
            };
            return new LazyList(at$1_scala$collection$immutable$LazyList$$newLL_state);
        }
        return this.empty();
    }

    @Override
    public <A, S> LazyList<A> unfold(S init2, Function1<S, Option<Tuple2<A, S>>> f) {
        Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$newLL_state = (Function0<LazyList.State> & Serializable)() -> {
            Tuple2 tuple2;
            Option option = (Option)f.apply(init2);
            if (option instanceof Some && (tuple2 = (Tuple2)((Some)option).value()) != null) {
                Object elem = tuple2._1();
                Object state = tuple2._2();
                Function0<LazyList.State> & Serializable unfold_scala$collection$immutable$LazyList$$newLL_state = () -> LazyList$.$anonfun$unfold$1(f, state);
                Object var7_6 = null;
                LazyList scala$collection$immutable$LazyList$$sCons_tl = new LazyList(unfold_scala$collection$immutable$LazyList$$newLL_state);
                return new LazyList.State.Cons(elem, scala$collection$immutable$LazyList$$sCons_tl);
            }
            if (None$.MODULE$.equals(option)) {
                return LazyList$State$Empty$.MODULE$;
            }
            throw new MatchError(option);
        };
        return new LazyList(scala$collection$immutable$LazyList$$newLL_state);
    }

    @Override
    public <A> Builder<A, LazyList<A>> newBuilder() {
        return new LazyList.LazyBuilder();
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(LazyList$.class);
    }

    public static final /* synthetic */ LazyList.State $anonfun$filterImpl$1(ObjectRef restRef$1, Function1 p$2, boolean isFlipped$1) {
        Object elem = null;
        boolean found = false;
        LinearSeq rest = (LazyList)restRef$1.elem;
        while (!found && !rest.isEmpty()) {
            elem = rest.head();
            found = BoxesRunTime.unboxToBoolean(p$2.apply(elem)) != isFlipped$1;
            rest = rest.tail();
            restRef$1.elem = rest;
        }
        if (found) {
            ObjectRef<LazyList> scala$collection$immutable$LazyList$$filterImpl_restRef = new ObjectRef<LazyList>((LazyList)rest);
            Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$filterImpl_scala$collection$immutable$LazyList$$newLL_state = () -> LazyList$.$anonfun$filterImpl$1(scala$collection$immutable$LazyList$$filterImpl_restRef, p$2, isFlipped$1);
            Object var8_7 = null;
            Object var7_6 = null;
            LazyList scala$collection$immutable$LazyList$$sCons_tl = new LazyList(scala$collection$immutable$LazyList$$filterImpl_scala$collection$immutable$LazyList$$newLL_state);
            return new LazyList.State.Cons<Object>(elem, scala$collection$immutable$LazyList$$sCons_tl);
        }
        return LazyList$State$Empty$.MODULE$;
    }

    public static final /* synthetic */ LazyList.State $anonfun$collectImpl$1(ObjectRef restRef$2, PartialFunction pf$1) {
        Object marker = Statics.pfMarker;
        Function1<Object, Object> toMarker = MODULE$.scala$collection$immutable$LazyList$$anyToMarker();
        Object res = marker;
        LinearSeq rest = (LazyList)restRef$2.elem;
        while (res == marker && !rest.isEmpty()) {
            res = pf$1.applyOrElse(rest.head(), toMarker);
            rest = rest.tail();
            restRef$2.elem = rest;
        }
        if (res == marker) {
            return LazyList$State$Empty$.MODULE$;
        }
        LazyList scala$collection$immutable$LazyList$$sCons_tl = MODULE$.scala$collection$immutable$LazyList$$collectImpl((LazyList)rest, pf$1);
        return new LazyList.State.Cons<Object>(res, scala$collection$immutable$LazyList$$sCons_tl);
    }

    public static final /* synthetic */ LazyList.State $anonfun$flatMapImpl$3(ObjectRef rest$1, Function1 f$3) {
        LazyList scala$collection$immutable$LazyList$$flatMapImpl_ll = (LazyList)rest$1.elem;
        Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$flatMapImpl_scala$collection$immutable$LazyList$$newLL_state = () -> LazyList$.$anonfun$flatMapImpl$1(new ObjectRef<LazyList>(scala$collection$immutable$LazyList$$flatMapImpl_ll), f$3);
        Object var3_3 = null;
        Object var2_2 = null;
        return new LazyList(scala$collection$immutable$LazyList$$flatMapImpl_scala$collection$immutable$LazyList$$newLL_state).scala$collection$immutable$LazyList$$state();
    }

    public static final /* synthetic */ LazyList.State $anonfun$flatMapImpl$1(ObjectRef restRef$3, Function1 f$3) {
        ObjectRef<Object> it = new ObjectRef<Object>(null);
        boolean itHasNext = false;
        LazyList create_e = (LazyList)restRef$3.elem;
        Object var8_4 = null;
        ObjectRef<LazyList> rest = new ObjectRef<LazyList>(create_e);
        while (!itHasNext && !((LazyList)rest.elem).isEmpty()) {
            it.elem = ((IterableOnce)f$3.apply(((LazyList)rest.elem).head())).iterator();
            itHasNext = ((Iterator)it.elem).hasNext();
            if (itHasNext) continue;
            rest.elem = ((LazyList)rest.elem).tail();
            restRef$3.elem = (LazyList)rest.elem;
        }
        if (itHasNext) {
            Object head = ((Iterator)it.elem).next();
            rest.elem = ((LazyList)rest.elem).tail();
            restRef$3.elem = (LazyList)rest.elem;
            Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$newLL_state = (Function0<LazyList.State> & Serializable)() -> {
                Function0<LazyList.State> & Serializable intersect = () -> LazyList$.$anonfun$flatMapImpl$3(rest, f$3);
                Iterator scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_it = (Iterator)it$3.elem;
                if (scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_it.hasNext()) {
                    void scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_scala$collection$immutable$LazyList$$sCons_tl;
                    void scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_suffix;
                    Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_scala$collection$immutable$LazyList$$newLL_state = () -> LazyList$.$anonfun$stateFromIteratorConcatSuffix$1(scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_it, (Function0)scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_suffix);
                    Object var5_5 = null;
                    LazyList lazyList = new LazyList(scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_scala$collection$immutable$LazyList$$newLL_state);
                    Object scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_scala$collection$immutable$LazyList$$sCons_hd = scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_it.next();
                    return new LazyList.State.Cons(scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_scala$collection$immutable$LazyList$$sCons_hd, scala$collection$immutable$LazyList$$stateFromIteratorConcatSuffix_scala$collection$immutable$LazyList$$sCons_tl);
                }
                LazyList $anonfun$flatMapImpl$3_scala$collection$immutable$LazyList$$flatMapImpl_ll = (LazyList)rest$1.elem;
                Function0<LazyList.State> & Serializable $anonfun$flatMapImpl$3_scala$collection$immutable$LazyList$$flatMapImpl_scala$collection$immutable$LazyList$$newLL_state = () -> LazyList$.$anonfun$flatMapImpl$1(new ObjectRef<LazyList>($anonfun$flatMapImpl$3_scala$collection$immutable$LazyList$$flatMapImpl_ll), f$3);
                Object var9_9 = null;
                Object var8_8 = null;
                return new LazyList($anonfun$flatMapImpl$3_scala$collection$immutable$LazyList$$flatMapImpl_scala$collection$immutable$LazyList$$newLL_state).scala$collection$immutable$LazyList$$state();
            };
            Object var6_7 = null;
            LazyList scala$collection$immutable$LazyList$$sCons_tl = new LazyList(scala$collection$immutable$LazyList$$newLL_state);
            return new LazyList.State.Cons(head, scala$collection$immutable$LazyList$$sCons_tl);
        }
        return LazyList$State$Empty$.MODULE$;
    }

    public static final /* synthetic */ LazyList.State $anonfun$dropWhileImpl$1(ObjectRef restRef$5, Function1 p$3) {
        LinearSeq rest = (LazyList)restRef$5.elem;
        while (!rest.isEmpty() && BoxesRunTime.unboxToBoolean(p$3.apply(rest.head()))) {
            rest = rest.tail();
            restRef$5.elem = rest;
        }
        return rest.scala$collection$immutable$LazyList$$state();
    }

    private final LazyList at$1(int index, int n$4, Function1 f$5) {
        if (index < n$4) {
            Function0<LazyList.State> & Serializable scala$collection$immutable$LazyList$$newLL_state = (Function0<LazyList.State> & Serializable)() -> {
                void scala$collection$immutable$LazyList$$sCons_tl;
                Object var7_5;
                Object object;
                Object r = f.apply(at$1_index);
                int at$1_index = at$1_index + 1;
                if (at$1_index < n) {
                    Function0<LazyList.State> & Serializable at$1_scala$collection$immutable$LazyList$$newLL_state = () -> LazyList$.$anonfun$tabulate$1(this, f, at$1_index, n);
                    object = new LazyList(at$1_scala$collection$immutable$LazyList$$newLL_state);
                    var7_5 = null;
                } else {
                    object = this.empty();
                }
                var7_5 = null;
                Object object2 = object;
                Object scala$collection$immutable$LazyList$$sCons_hd = r;
                return new LazyList.State.Cons(scala$collection$immutable$LazyList$$sCons_hd, scala$collection$immutable$LazyList$$sCons_tl);
            };
            return new LazyList(scala$collection$immutable$LazyList$$newLL_state);
        }
        return this.empty();
    }

    private LazyList$() {
    }
}

