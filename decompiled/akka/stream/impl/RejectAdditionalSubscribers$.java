/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.ReactiveStreamsCompliance;
import akka.stream.impl.ReactiveStreamsCompliance$;
import java.io.Serializable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import scala.Product;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@InternalApi
public final class RejectAdditionalSubscribers$
implements Publisher<Nothing$>,
Product,
Serializable {
    public static final RejectAdditionalSubscribers$ MODULE$ = new RejectAdditionalSubscribers$();

    static {
        Product.$init$(MODULE$);
    }

    @Override
    public String productElementName(int n) {
        return Product.productElementName$(this, n);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public void subscribe(Subscriber<?> subscriber) {
        try {
            ReactiveStreamsCompliance$.MODULE$.rejectAdditionalSubscriber(subscriber, "Publisher");
        }
        catch (Throwable throwable) {
            Throwable throwable2 = throwable;
            if (throwable2 instanceof ReactiveStreamsCompliance.SpecViolation) {
            }
            throw throwable;
        }
    }

    public <T> Publisher<T> apply() {
        return this;
    }

    public String toString() {
        return "already-subscribed-publisher";
    }

    @Override
    public String productPrefix() {
        return "RejectAdditionalSubscribers";
    }

    @Override
    public int productArity() {
        return 0;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof RejectAdditionalSubscribers$;
    }

    public int hashCode() {
        return -755617819;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(RejectAdditionalSubscribers$.class);
    }

    private RejectAdditionalSubscribers$() {
    }
}

