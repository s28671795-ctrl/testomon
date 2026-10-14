/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.NoRouter;
import akka.routing.Router;
import akka.routing.RouterActor;
import akka.routing.RouterConfig;
import scala.Product;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

public final class NoRouter$
extends NoRouter
implements Product {
    public static final NoRouter$ MODULE$ = new NoRouter$();

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
    public Router createRouter(ActorSystem system) {
        throw new UnsupportedOperationException("NoRouter has no Router");
    }

    @Override
    public RouterActor createRouterActor() {
        throw new UnsupportedOperationException("NoRouter must not create RouterActor");
    }

    @Override
    public String routerDispatcher() {
        throw new UnsupportedOperationException("NoRouter has no dispatcher");
    }

    @Override
    public RouterConfig withFallback(RouterConfig other) {
        return other;
    }

    @Override
    public NoRouter$ getInstance() {
        return this;
    }

    @Override
    public Props props(Props routeeProps) {
        return routeeProps.withRouter(this);
    }

    @Override
    public String productPrefix() {
        return "NoRouter";
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
        return x$1 instanceof NoRouter$;
    }

    public int hashCode() {
        return 682614922;
    }

    public String toString() {
        return "NoRouter";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(NoRouter$.class);
    }

    private NoRouter$() {
    }
}

