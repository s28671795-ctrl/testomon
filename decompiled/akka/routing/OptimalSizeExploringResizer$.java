/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.DefaultOptimalSizeExploringResizer;
import akka.routing.OptimalSizeExploringResizer;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import com.typesafe.config.Config;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

public final class OptimalSizeExploringResizer$
implements Product,
Serializable {
    public static final OptimalSizeExploringResizer$ MODULE$ = new OptimalSizeExploringResizer$();

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

    public OptimalSizeExploringResizer apply(Config resizerCfg) {
        int x$1 = resizerCfg.getInt("lower-bound");
        int x$2 = resizerCfg.getInt("upper-bound");
        double x$3 = resizerCfg.getDouble("chance-of-ramping-down-when-full");
        FiniteDuration x$4 = JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(resizerCfg.getDuration("action-interval")));
        FiniteDuration x$5 = JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(resizerCfg.getDuration("downsize-after-underutilized-for")));
        int x$6 = resizerCfg.getInt("optimization-range");
        double x$7 = resizerCfg.getDouble("explore-step-size");
        double x$8 = resizerCfg.getDouble("chance-of-exploration");
        double x$9 = resizerCfg.getDouble("weight-of-latest-metric");
        double x$10 = resizerCfg.getDouble("downsize-ratio");
        return new DefaultOptimalSizeExploringResizer(x$1, x$2, x$3, x$4, x$6, x$7, x$10, x$5, x$8, x$9);
    }

    @Override
    public String productPrefix() {
        return "OptimalSizeExploringResizer";
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
        return x$1 instanceof OptimalSizeExploringResizer$;
    }

    public int hashCode() {
        return -303294415;
    }

    public String toString() {
        return "OptimalSizeExploringResizer";
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(OptimalSizeExploringResizer$.class);
    }

    private OptimalSizeExploringResizer$() {
    }
}

