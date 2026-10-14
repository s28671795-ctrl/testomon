/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.SupervisorStrategy;
import akka.routing.FromConfig;
import akka.routing.Pool$;
import akka.routing.Resizer;
import scala.None$;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.collection.Iterator;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

public final class FromConfig$
extends FromConfig
implements Product {
    public static final FromConfig$ MODULE$ = new FromConfig$();

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
    public FromConfig$ getInstance() {
        return this;
    }

    @Override
    public final FromConfig apply(Option<Resizer> resizer, SupervisorStrategy supervisorStrategy, String routerDispatcher) {
        return new FromConfig(resizer, supervisorStrategy, routerDispatcher);
    }

    @Override
    public final Option<Resizer> apply$default$1() {
        return None$.MODULE$;
    }

    @Override
    public final SupervisorStrategy apply$default$2() {
        return Pool$.MODULE$.defaultSupervisorStrategy();
    }

    @Override
    public final String apply$default$3() {
        return "akka.actor.default-dispatcher";
    }

    @Override
    public final Option<String> unapply(FromConfig fc) {
        return new Some<String>(fc.routerDispatcher());
    }

    @Override
    public String productPrefix() {
        return "FromConfig";
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
        return x$1 instanceof FromConfig$;
    }

    public int hashCode() {
        return -606307412;
    }

    public String toString() {
        return "FromConfig";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(FromConfig$.class);
    }

    private FromConfig$() {
    }
}

