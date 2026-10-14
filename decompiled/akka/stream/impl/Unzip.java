/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.Props;
import akka.annotation.InternalApi;
import akka.japi.Pair;
import akka.stream.Attributes;
import akka.stream.impl.FanOut;
import akka.stream.impl.TransferPhase;
import akka.stream.impl.Unzip$;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e:aa\u0002\u0005\t\u00021qaA\u0002\t\t\u0011\u0003a\u0011\u0003C\u0003\u0019\u0003\u0011\u0005!\u0004C\u0003\u001c\u0003\u0011\u0005ADB\u0003\u0011\u0011\u0001a\u0011\u0007\u0003\u0005%\t\t\u0005\t\u0015!\u0003&\u0011\u0015AB\u0001\"\u00016\u0003\u0015)fN_5q\u0015\tI!\"\u0001\u0003j[Bd'BA\u0006\r\u0003\u0019\u0019HO]3b[*\tQ\"\u0001\u0003bW.\f\u0007CA\b\u0002\u001b\u0005A!!B+ou&\u00048CA\u0001\u0013!\t\u0019b#D\u0001\u0015\u0015\u0005)\u0012!B:dC2\f\u0017BA\f\u0015\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001\u000f\u0003\u0015\u0001(o\u001c9t)\ti2\u0005\u0005\u0002\u001fC5\tqD\u0003\u0002!\u0019\u0005)\u0011m\u0019;pe&\u0011!e\b\u0002\u0006!J|\u0007o\u001d\u0005\u0006I\r\u0001\r!J\u0001\u000bCR$(/\u001b2vi\u0016\u001c\bC\u0001\u0014(\u001b\u0005Q\u0011B\u0001\u0015\u000b\u0005)\tE\u000f\u001e:jEV$Xm\u001d\u0015\u0003\u0003)\u0002\"a\u000b\u0018\u000e\u00031R!!\f\u0007\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u00020Y\tY\u0011J\u001c;fe:\fG.\u00119jQ\t\u0001!f\u0005\u0002\u0005eA\u0011qbM\u0005\u0003i!\u0011aAR1o\u001fV$HC\u0001\u001c8!\tyA\u0001C\u0003%\r\u0001\u0007Q\u0005\u000b\u0002\u0005U\u0001")
@InternalApi
public class Unzip
extends FanOut {
    public static Props props(Attributes attributes) {
        return Unzip$.MODULE$.props(attributes);
    }

    public Unzip(Attributes attributes) {
        super(attributes, 2);
        this.outputBunch().markAllOutputs();
        this.initialPhase(1, new TransferPhase(this.primaryInputs().NeedsInput().$amp$amp(this.outputBunch().AllOfMarkedOutputs()), () -> {
            Object object = this.primaryInputs().dequeueInputElement();
            if (object instanceof Tuple2) {
                Tuple2 tuple2 = (Tuple2)object;
                Object a = tuple2._1();
                Object b = tuple2._2();
                this.outputBunch().enqueue(0, a);
                this.outputBunch().enqueue(1, b);
                return;
            }
            if (object instanceof Pair) {
                Pair pair = (Pair)object;
                this.outputBunch().enqueue(0, pair.first());
                this.outputBunch().enqueue(1, pair.second());
                return;
            }
            throw new IllegalArgumentException(new StringBuilder(77).append("Unable to unzip elements of type ").append(object.getClass().getName()).append(", ").append("can only handle Tuple2 and akka.japi.Pair!").toString());
        }));
    }
}

