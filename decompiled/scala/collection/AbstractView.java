/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.AbstractIterable;
import scala.collection.IndexedSeq;
import scala.collection.IterableFactory;
import scala.collection.View;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00192QAA\u0002\u0002\u0002!AQ!\b\u0001\u0005\u0002y\u0011A\"\u00112tiJ\f7\r\u001e,jK^T!\u0001B\u0003\u0002\u0015\r|G\u000e\\3di&|gNC\u0001\u0007\u0003\u0015\u00198-\u00197b\u0007\u0001)\"!\u0003\t\u0014\u0007\u0001Q!\u0004E\u0002\f\u00199i\u0011aA\u0005\u0003\u001b\r\u0011\u0001#\u00112tiJ\f7\r^%uKJ\f'\r\\3\u0011\u0005=\u0001B\u0002\u0001\u0003\u0007#\u0001!)\u0019\u0001\n\u0003\u0003\u0005\u000b\"aE\f\u0011\u0005Q)R\"A\u0003\n\u0005Y)!a\u0002(pi\"Lgn\u001a\t\u0003)aI!!G\u0003\u0003\u0007\u0005s\u0017\u0010E\u0002\f79I!\u0001H\u0002\u0003\tYKWm^\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003}\u00012a\u0003\u0001\u000fQ\u0011\u0001\u0011\u0005J\u0013\u0011\u0005Q\u0011\u0013BA\u0012\u0006\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0004\u0001")
public abstract class AbstractView<A>
extends AbstractIterable<A>
implements View<A> {
    private static final long serialVersionUID = 3L;

    @Override
    public View<A> view() {
        return View.view$(this);
    }

    @Override
    public IterableFactory<View> iterableFactory() {
        return View.iterableFactory$(this);
    }

    @Override
    public View<A> empty() {
        return View.empty$(this);
    }

    @Override
    public String toString() {
        return View.toString$(this);
    }

    @Override
    public String stringPrefix() {
        return View.stringPrefix$(this);
    }

    @Override
    public IndexedSeq<A> force() {
        return View.force$(this);
    }
}

