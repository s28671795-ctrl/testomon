/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function0;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.AbstractIterable;
import scala.collection.AbstractIterator;
import scala.collection.Iterable;
import scala.collection.Iterable$;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.View;
import scala.package$;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;

public final class Option$
implements Serializable {
    public static final Option$ MODULE$ = new Option$();

    /*
     * WARNING - void declaration
     */
    public <A> Iterable<A> option2Iterable(Option<A> xo) {
        void single_a;
        if (xo.isEmpty()) {
            return (Iterable)package$.MODULE$.Iterable().empty();
        }
        A a = xo.get();
        if (package$.MODULE$.Iterable() == null) {
            throw null;
        }
        return new AbstractIterable<A>(single_a){
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

    public <A> Option<A> apply(A x) {
        if (x == null) {
            return None$.MODULE$;
        }
        return new Some<A>(x);
    }

    public <A> Option<A> empty() {
        return None$.MODULE$;
    }

    public <A> Option<A> when(boolean cond, Function0<A> a) {
        if (cond) {
            return new Some<A>(a.apply());
        }
        return None$.MODULE$;
    }

    public <A> Option<A> unless(boolean cond, Function0<A> a) {
        if (!cond) {
            return new Some<A>(a.apply());
        }
        return None$.MODULE$;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Option$.class);
    }

    private Option$() {
    }
}

