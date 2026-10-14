/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.scaladsl.MergeHub;
import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.Source$;
import java.io.Serializable;
import scala.Function1;
import scala.Tuple2;

public final class MergeHub$ {
    public static final MergeHub$ MODULE$ = new MergeHub$();
    private static final int akka$stream$scaladsl$MergeHub$$Cancel = -1;

    public <T> boolean $lessinit$greater$default$2() {
        return false;
    }

    public int akka$stream$scaladsl$MergeHub$$Cancel() {
        return akka$stream$scaladsl$MergeHub$$Cancel;
    }

    public <T> Source<T, Sink<T, NotUsed>> source(int perProducerBufferSize) {
        return Source$.MODULE$.fromGraph(new MergeHub(perProducerBufferSize, false)).mapMaterializedValue((Function1<Tuple2, Sink> & Serializable)x$1 -> (Sink)x$1._1());
    }

    public <T> Source<T, Tuple2<Sink<T, NotUsed>, MergeHub.DrainingControl>> sourceWithDraining(int perProducerBufferSize) {
        return Source$.MODULE$.fromGraph(new MergeHub(perProducerBufferSize, true));
    }

    public <T> Source<T, Sink<T, NotUsed>> source() {
        return this.source(16);
    }

    public <T> Source<T, Tuple2<Sink<T, NotUsed>, MergeHub.DrainingControl>> sourceWithDraining() {
        return this.sourceWithDraining(16);
    }

    private MergeHub$() {
    }
}

