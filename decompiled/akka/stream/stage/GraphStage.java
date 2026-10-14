/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.stage;

import akka.NotUsed;
import akka.NotUsed$;
import akka.stream.Attributes;
import akka.stream.Shape;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005%3Q\u0001B\u0003\u0002\u00021AQ!\n\u0001\u0005\u0002\u0019BQ\u0001\u000b\u0001\u0005F%BQ!\u000e\u0001\u0007\u0002Y\u0012!b\u0012:ba\"\u001cF/Y4f\u0015\t1q!A\u0003ti\u0006<WM\u0003\u0002\t\u0013\u000511\u000f\u001e:fC6T\u0011AC\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\u00055!2C\u0001\u0001\u000f!\u0011y\u0001CE\u0011\u000e\u0003\u0015I!!E\u0003\u0003?\u001d\u0013\u0018\r\u001d5Ti\u0006<WmV5uQ6\u000bG/\u001a:jC2L'0\u001a3WC2,X\r\u0005\u0002\u0014)1\u0001A!B\u000b\u0001\u0005\u00041\"!A*\u0012\u0005]i\u0002C\u0001\r\u001c\u001b\u0005I\"\"\u0001\u000e\u0002\u000bM\u001c\u0017\r\\1\n\u0005qI\"a\u0002(pi\"Lgn\u001a\t\u0003=}i\u0011aB\u0005\u0003A\u001d\u0011Qa\u00155ba\u0016\u0004\"AI\u0012\u000e\u0003%I!\u0001J\u0005\u0003\u000f9{G/V:fI\u00061A(\u001b8jiz\"\u0012a\n\t\u0004\u001f\u0001\u0011\u0012aH2sK\u0006$X\rT8hS\u000e\fe\u000eZ'bi\u0016\u0014\u0018.\u00197ju\u0016$g+\u00197vKR\u0011!\u0006\r\t\u00051-j\u0013%\u0003\u0002-3\t1A+\u001e9mKJ\u0002\"a\u0004\u0018\n\u0005=*!aD$sCBD7\u000b^1hK2{w-[2\t\u000bE\u0012\u0001\u0019\u0001\u001a\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0011\u0005y\u0019\u0014B\u0001\u001b\b\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0002.o!)\u0011g\u0001a\u0001e!\u001a1!\u000f%\u0011\u0007aQD(\u0003\u0002<3\t1A\u000f\u001b:poN\u0004\"!P#\u000f\u0005y\u001aeBA C\u001b\u0005\u0001%BA!\f\u0003\u0019a$o\\8u}%\t!$\u0003\u0002E3\u00059\u0001/Y2lC\u001e,\u0017B\u0001$H\u0005%)\u0005pY3qi&|gN\u0003\u0002E3\r\nA\b")
public abstract class GraphStage<S extends Shape>
extends GraphStageWithMaterializedValue<S, NotUsed> {
    @Override
    public final Tuple2<GraphStageLogic, NotUsed> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        return new Tuple2<GraphStageLogic, NotUsed>(this.createLogic(inheritedAttributes), NotUsed$.MODULE$);
    }

    public abstract GraphStageLogic createLogic(Attributes var1) throws Exception;
}

