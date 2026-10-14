/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Some;
import scala.collection.AbstractIterable;
import scala.collection.AbstractIterator;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.View;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;

public final class Iterable$
extends IterableFactory.Delegate<Iterable> {
    public static final Iterable$ MODULE$ = new Iterable$();
    private static final long serialVersionUID = 3L;

    public <A> Iterable<A> single(A a) {
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

    private Object writeReplace() {
        return new ModuleSerializationProxy(Iterable$.class);
    }

    private Iterable$() {
        super(scala.collection.immutable.Iterable$.MODULE$);
    }
}

