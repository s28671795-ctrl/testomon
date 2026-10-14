/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.Some;
import scala.collection.AbstractIterable;
import scala.collection.AbstractIterator;
import scala.collection.Iterable$;
import scala.collection.IterableFactory;
import scala.collection.IterableFactoryDefaults;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.LazyZip2;
import scala.collection.View;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.math.Integral;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005\u0005Mba\u0002\t\u0012!\u0003\r\tA\u0006\u0005\u0006g\u0001!\t\u0001\u000e\u0005\u0006q\u0001!)!\u000f\u0005\u0006\u000b\u0002!)\"\u000f\u0005\u0006\r\u0002!\ta\u0012\u0005\u0006\u0017\u0002!\t!\u000f\u0005\u0007#\u0002\u0001K\u0011\u0003*\t\ry\u0003AQA\nS\u0011\u0019y\u0006\u0001)C\t%\")a\r\u0001C!O\")\u0001\u000e\u0001C\u0001S\u001e)A/\u0005E\u0001k\u001a)\u0001#\u0005E\u0001m\")a\u0010\u0004C\u0001\u007f\"9\u0011\u0011\u0001\u0007\u0005\u0002\u0005\r\u0001\"CA\t\u0019\u0005\u0005I\u0011BA\n\u0005!IE/\u001a:bE2,'B\u0001\n\u0014\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002)\u0005)1oY1mC\u000e\u0001QCA\f#'\u0015\u0001\u0001\u0004H\u00161!\tI\"$D\u0001\u0014\u0013\tY2C\u0001\u0004B]f\u0014VM\u001a\t\u0004;y\u0001S\"A\t\n\u0005}\t\"\u0001D%uKJ\f'\r\\3P]\u000e,\u0007CA\u0011#\u0019\u0001!aa\t\u0001\u0005\u0006\u0004!#!A!\u0012\u0005\u0015B\u0003CA\r'\u0013\t93CA\u0004O_RD\u0017N\\4\u0011\u0005eI\u0013B\u0001\u0016\u0014\u0005\r\te.\u001f\t\u0006;1\u0002cfL\u0005\u0003[E\u00111\"\u0013;fe\u0006\u0014G.Z(qgB\u0011Q\u0004\u0001\t\u0004;\u0001\u0001\u0003\u0003B\u000f2A9J!AM\t\u0003/%#XM]1cY\u00164\u0015m\u0019;pef$UMZ1vYR\u001c\u0018A\u0002\u0013j]&$H\u0005F\u00016!\tIb'\u0003\u00028'\t!QK\\5u\u0003)!x.\u0013;fe\u0006\u0014G.Z\u000b\u0002u5\t\u0001\u0001\u000b\u0004\u0003y}\u0002%i\u0011\t\u00033uJ!AP\n\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0003\u0005\u000b\u0011\u0011\u0004;p\u0013R,'/\u00192mK\u0002J7\u000fI5oi\u0016\u0014h.\u00197!C:$\u0007e^5mY\u0002\u0012W\rI7bI\u0016\u0004\u0003O]8uK\u000e$X\rZ\u001e!SR\u001c\bE\\1nK\u0002J7\u000fI:j[&d\u0017M\u001d\u0011u_\u0002\u0002Go\u001c'jgR\u0004\u0007e\u001c:!AR|7+Z9aY\u0001\u0012W\u000f\u001e\u0011ji\u0002\"w.Z:oOQ\u00043m\u001c9zA9|g.L5n[V$\u0018M\u00197fA\r|G\u000e\\3di&|gn]\u0001\u0006g&t7-Z\u0011\u0002\t\u00061!GL\u00194]]\nAaY8mY\u0006y\u0011\u000e^3sC\ndWMR1di>\u0014\u00180F\u0001I!\ri\u0012JL\u0005\u0003\u0015F\u0011q\"\u0013;fe\u0006\u0014G.\u001a$bGR|'/_\u0001\u0004g\u0016\f\bFB\u0003=\u007f5\u0013u*I\u0001O\u0003=JE/\u001a:bE2,gf]3rA\u0005dw/Y=tAI,G/\u001e:og\u0002\"\b.\u001a\u0011ji\u0016\u0014\u0018M\u00197fA%$8/\u001a7gC\u0005\u0001\u0016A\u0002\u001a/cMr\u0003'A\u0005dY\u0006\u001c8OT1nKV\t1\u000b\u0005\u0002U7:\u0011Q+\u0017\t\u0003-Ni\u0011a\u0016\u0006\u00031V\ta\u0001\u0010:p_Rt\u0014B\u0001.\u0014\u0003\u0019\u0001&/\u001a3fM&\u0011A,\u0018\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005i\u001b\u0012aE2pY2,7\r^5p]\u000ec\u0017m]:OC6,\u0017\u0001D:ue&tw\r\u0015:fM&D\bF\u0002\u0005b\u007f\u0011\u0014u\n\u0005\u0002\u001aE&\u00111m\u0005\u0002\u0015I\u0016\u0004(/Z2bi\u0016$wJ^3se&$\u0017N\\4\"\u0003\u0015\f!d\u0014<feJLG-\u001a\u0011dY\u0006\u001c8OT1nK\u0002Jgn\u001d;fC\u0012\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002'\u00069A.\u0019>z5&\u0004XC\u00016p)\tY\u0017\u000fE\u0003\u001eY\u0002r'(\u0003\u0002n#\tAA*\u0019>z5&\u0004(\u0007\u0005\u0002\"_\u0012)\u0001O\u0003b\u0001I\t\t!\tC\u0003s\u0015\u0001\u00071/\u0001\u0003uQ\u0006$\bcA\u000f\u0001]\u0006A\u0011\n^3sC\ndW\r\u0005\u0002\u001e\u0019M\u0011Ab\u001e\t\u0004qntcBA\u000fz\u0013\tQ\u0018#A\bJi\u0016\u0014\u0018M\u00197f\r\u0006\u001cGo\u001c:z\u0013\taXP\u0001\u0005EK2,w-\u0019;f\u0015\tQ\u0018#\u0001\u0004=S:LGO\u0010\u000b\u0002k\u000611/\u001b8hY\u0016,B!!\u0002\u0002\fQ!\u0011qAA\u0007!\u0011i\u0002!!\u0003\u0011\u0007\u0005\nY\u0001B\u0003$\u001d\t\u0007A\u0005C\u0004\u0002\u00109\u0001\r!!\u0003\u0002\u0003\u0005\fAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!\u0006\u0011\t\u0005]\u0011\u0011E\u0007\u0003\u00033QA!a\u0007\u0002\u001e\u0005!A.\u00198h\u0015\t\ty\"\u0001\u0003kCZ\f\u0017\u0002BA\u0012\u00033\u0011aa\u00142kK\u000e$\bf\u0002\u0007\u0002(\u00055\u0012q\u0006\t\u00043\u0005%\u0012bAA\u0016'\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0007!:1\"a\n\u0002.\u0005=\u0002")
public interface Iterable<A>
extends IterableFactoryDefaults<A, Iterable> {
    public static <A> Iterable<A> single(A a) {
        return new AbstractIterable<A>(a){
            private final Object a$1;

            public Iterator<A> iterator() {
                Object single_a = this.a$1;
                return new AbstractIterator<A>(single_a){
                    private boolean consumed;
                    private final Object a$1;

                    public boolean hasNext() {
                        return !this.consumed;
                    }

                    public A next() {
                        if (this.consumed) {
                            return (A)Iterator$.scala$collection$Iterator$$_empty.next();
                        }
                        this.consumed = true;
                        return (A)this.a$1;
                    }

                    public Iterator<A> sliceIterator(int from, int until) {
                        if (this.consumed || from > 0 || until == 0) {
                            return Iterator$.scala$collection$Iterator$$_empty;
                        }
                        return this;
                    }
                    {
                        this.a$1 = a$1;
                        this.consumed = false;
                    }
                };
            }

            public int knownSize() {
                return 1;
            }

            public A head() {
                return (A)this.a$1;
            }

            public Some<A> headOption() {
                return new Some<Object>(this.a$1);
            }

            public A last() {
                return (A)this.a$1;
            }

            public Some<A> lastOption() {
                return new Some<Object>(this.a$1);
            }

            public View.Single<A> view() {
                return new View.Single<Object>(this.a$1);
            }

            public Iterable<A> take(int n) {
                if (n > 0) {
                    return this;
                }
                return (Iterable)Iterable$.MODULE$.empty();
            }

            public Iterable<A> takeRight(int n) {
                if (n > 0) {
                    return this;
                }
                return (Iterable)Iterable$.MODULE$.empty();
            }

            public Iterable<A> drop(int n) {
                if (n > 0) {
                    return (Iterable)Iterable$.MODULE$.empty();
                }
                return this;
            }

            public Iterable<A> dropRight(int n) {
                if (n > 0) {
                    return (Iterable)Iterable$.MODULE$.empty();
                }
                return this;
            }

            public Iterable<Nothing$> tail() {
                return (Iterable)Iterable$.MODULE$.empty();
            }

            public Iterable<Nothing$> init() {
                return (Iterable)Iterable$.MODULE$.empty();
            }
            {
                this.a$1 = a$1;
            }
        };
    }

    public static <A> Builder<A, Iterable<A>> newBuilder() {
        return Iterable$.MODULE$.newBuilder();
    }

    public static Object from(IterableOnce it) {
        return Iterable$.MODULE$.from(it);
    }

    public static Object apply(Seq elems) {
        return Iterable$.MODULE$.apply(elems);
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        Iterable$ tabulate_this = Iterable$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$7$adapted(tabulate_this, n2, n3, n4, n5, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        Iterable$ tabulate_this = Iterable$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$5$adapted(tabulate_this, n2, n3, n4, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f) {
        Iterable$ tabulate_this = Iterable$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$3$adapted(tabulate_this, n2, n3, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, Function2 f) {
        Iterable$ tabulate_this = Iterable$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$1$adapted(tabulate_this, n2, f, arg_0));
    }

    public static Object tabulate(int n, Function1 f) {
        return Iterable$.MODULE$.from(new View.Tabulate(n, f));
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        Iterable$ fill_this = Iterable$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$4(fill_this, n2, n3, n4, n5, elem));
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        Iterable$ fill_this = Iterable$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$3(fill_this, n2, n3, n4, elem));
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem) {
        Iterable$ fill_this = Iterable$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$2(fill_this, n2, n3, elem));
    }

    public static Object fill(int n1, int n2, Function0 elem) {
        Iterable$ fill_this = Iterable$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$1(fill_this, n2, elem));
    }

    public static Object fill(int n, Function0 elem) {
        return Iterable$.MODULE$.from(new View.Fill(n, elem));
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(Iterable$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(Iterable$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        return Iterable$.MODULE$.from(new View.Unfold(init2, f));
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        return Iterable$.MODULE$.from(new View.Iterate<Object>(start2, len, f));
    }

    public static /* synthetic */ Iterable toIterable$(Iterable $this) {
        return $this.toIterable();
    }

    @Override
    default public Iterable<A> toIterable() {
        return this;
    }

    public static /* synthetic */ Iterable coll$(Iterable $this) {
        return $this.coll();
    }

    @Override
    default public Iterable<A> coll() {
        return this;
    }

    public static /* synthetic */ IterableFactory iterableFactory$(Iterable $this) {
        return $this.iterableFactory();
    }

    @Override
    default public IterableFactory<Iterable> iterableFactory() {
        return Iterable$.MODULE$;
    }

    public static /* synthetic */ Iterable seq$(Iterable $this) {
        return $this.seq();
    }

    default public Iterable<A> seq() {
        return this;
    }

    public static /* synthetic */ String className$(Iterable $this) {
        return $this.className();
    }

    default public String className() {
        return this.stringPrefix();
    }

    public static /* synthetic */ String collectionClassName$(Iterable $this) {
        return $this.collectionClassName();
    }

    default public String collectionClassName() {
        return this.className();
    }

    public static /* synthetic */ String stringPrefix$(Iterable $this) {
        return $this.stringPrefix();
    }

    default public String stringPrefix() {
        return "Iterable";
    }

    public static /* synthetic */ String toString$(Iterable $this) {
        return $this.toString();
    }

    default public String toString() {
        return this.mkString(new StringBuilder(1).append(this.className()).append("(").toString(), ", ", ")");
    }

    public static /* synthetic */ LazyZip2 lazyZip$(Iterable $this, Iterable that) {
        return $this.lazyZip(that);
    }

    default public <B> LazyZip2<A, B, Iterable<A>> lazyZip(Iterable<B> that) {
        return new LazyZip2(this, this, that);
    }

    public static void $init$(Iterable $this) {
    }
}

