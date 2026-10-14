/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.Attributes;
import akka.stream.impl.ProcessorModule;
import akka.stream.impl.Stages$DefaultAttributes$;
import java.io.Serializable;
import org.reactivestreams.Processor;
import scala.Function0;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.ModuleSerializationProxy;

public final class ProcessorModule$
implements Serializable {
    public static final ProcessorModule$ MODULE$ = new ProcessorModule$();

    public <In, Out, Mat> Attributes $lessinit$greater$default$2() {
        return Stages$DefaultAttributes$.MODULE$.processor();
    }

    public final String toString() {
        return "ProcessorModule";
    }

    public <In, Out, Mat> ProcessorModule<In, Out, Mat> apply(Function0<Tuple2<Processor<In, Out>, Mat>> createProcessor, Attributes attributes) {
        return new ProcessorModule<In, Out, Mat>(createProcessor, attributes);
    }

    public <In, Out, Mat> Attributes apply$default$2() {
        return Stages$DefaultAttributes$.MODULE$.processor();
    }

    public <In, Out, Mat> Option<Tuple2<Function0<Tuple2<Processor<In, Out>, Mat>>, Attributes>> unapply(ProcessorModule<In, Out, Mat> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Function0<Tuple2<Processor<In, Out>, Mat>>, Attributes>>(new Tuple2<Function0<Tuple2<Processor<In, Out>, Mat>>, Attributes>(x$0.createProcessor(), x$0.attributes()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ProcessorModule$.class);
    }

    private ProcessorModule$() {
    }
}

