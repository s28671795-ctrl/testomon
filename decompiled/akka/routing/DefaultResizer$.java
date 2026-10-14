/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.DefaultResizer;
import com.typesafe.config.Config;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.Tuple7;
import scala.collection.Iterator;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

public final class DefaultResizer$
implements Product,
Serializable {
    public static final DefaultResizer$ MODULE$ = new DefaultResizer$();

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

    public int $lessinit$greater$default$1() {
        return 1;
    }

    public int $lessinit$greater$default$2() {
        return 10;
    }

    public int $lessinit$greater$default$3() {
        return 1;
    }

    public double $lessinit$greater$default$4() {
        return 0.2;
    }

    public double $lessinit$greater$default$5() {
        return 0.3;
    }

    public double $lessinit$greater$default$6() {
        return 0.1;
    }

    public int $lessinit$greater$default$7() {
        return 10;
    }

    public DefaultResizer apply(Config resizerConfig) {
        return new DefaultResizer(resizerConfig.getInt("lower-bound"), resizerConfig.getInt("upper-bound"), resizerConfig.getInt("pressure-threshold"), resizerConfig.getDouble("rampup-rate"), resizerConfig.getDouble("backoff-threshold"), resizerConfig.getDouble("backoff-rate"), resizerConfig.getInt("messages-per-resize"));
    }

    public int apply$default$1() {
        return 1;
    }

    public int apply$default$2() {
        return 10;
    }

    public int apply$default$3() {
        return 1;
    }

    public double apply$default$4() {
        return 0.2;
    }

    public double apply$default$5() {
        return 0.3;
    }

    public double apply$default$6() {
        return 0.1;
    }

    public int apply$default$7() {
        return 10;
    }

    public Option<DefaultResizer> fromConfig(Config resizerConfig) {
        if (resizerConfig.getBoolean("resizer.enabled")) {
            return new Some<DefaultResizer>(this.apply(resizerConfig.getConfig("resizer")));
        }
        return None$.MODULE$;
    }

    public DefaultResizer apply(int lowerBound, int upperBound, int pressureThreshold, double rampupRate, double backoffThreshold, double backoffRate, int messagesPerResize) {
        return new DefaultResizer(lowerBound, upperBound, pressureThreshold, rampupRate, backoffThreshold, backoffRate, messagesPerResize);
    }

    public Option<Tuple7<Object, Object, Object, Object, Object, Object, Object>> unapply(DefaultResizer x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple7<Object, Object, Object, Object, Object, Object, Object>>(new Tuple7<Integer, Integer, Integer, Double, Double, Double, Integer>(BoxesRunTime.boxToInteger(x$0.lowerBound()), BoxesRunTime.boxToInteger(x$0.upperBound()), BoxesRunTime.boxToInteger(x$0.pressureThreshold()), BoxesRunTime.boxToDouble(x$0.rampupRate()), BoxesRunTime.boxToDouble(x$0.backoffThreshold()), BoxesRunTime.boxToDouble(x$0.backoffRate()), BoxesRunTime.boxToInteger(x$0.messagesPerResize())));
    }

    @Override
    public String productPrefix() {
        return "DefaultResizer";
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
        return x$1 instanceof DefaultResizer$;
    }

    public int hashCode() {
        return 2099326173;
    }

    public String toString() {
        return "DefaultResizer";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DefaultResizer$.class);
    }

    private DefaultResizer$() {
    }
}

