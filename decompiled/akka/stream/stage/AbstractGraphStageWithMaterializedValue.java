/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.stage;

import akka.japi.Pair;
import akka.stream.Attributes;
import akka.stream.Shape;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M3Q\u0001B\u0003\u0002\u00021AQ\u0001\u000b\u0001\u0005\u0002%BQa\u000b\u0001\u0005\u00061BQ!\u0013\u0001\u0007\u0002)\u0013q%\u00112tiJ\f7\r^$sCBD7\u000b^1hK^KG\u000f['bi\u0016\u0014\u0018.\u00197ju\u0016$g+\u00197vK*\u0011aaB\u0001\u0006gR\fw-\u001a\u0006\u0003\u0011%\taa\u001d;sK\u0006l'\"\u0001\u0006\u0002\t\u0005\\7.Y\u0002\u0001+\riACI\n\u0003\u00019\u0001Ba\u0004\t\u0013C5\tQ!\u0003\u0002\u0012\u000b\tyrI]1qQN#\u0018mZ3XSRDW*\u0019;fe&\fG.\u001b>fIZ\u000bG.^3\u0011\u0005M!B\u0002\u0001\u0003\u0007+\u0001!)\u0019\u0001\f\u0003\u0003M\u000b\"aF\u000f\u0011\u0005aYR\"A\r\u000b\u0003i\tQa]2bY\u0006L!\u0001H\r\u0003\u000f9{G\u000f[5oOB\u0011adH\u0007\u0002\u000f%\u0011\u0001e\u0002\u0002\u0006'\"\f\u0007/\u001a\t\u0003'\t\"Qa\t\u0001C\u0002\u0011\u0012\u0011!T\t\u0003/\u0015\u0002\"\u0001\u0007\u0014\n\u0005\u001dJ\"aA!os\u00061A(\u001b8jiz\"\u0012A\u000b\t\u0005\u001f\u0001\u0011\u0012%A\u0010de\u0016\fG/\u001a'pO&\u001c\u0017I\u001c3NCR,'/[1mSj,GMV1mk\u0016$\"!L\u001a\u0011\taq\u0003'I\u0005\u0003_e\u0011a\u0001V;qY\u0016\u0014\u0004CA\b2\u0013\t\u0011TAA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u0015!$\u00011\u00016\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t!\tqb'\u0003\u00028\u000f\tQ\u0011\t\u001e;sS\n,H/Z:)\u0007\tI\u0004\nE\u0002\u0019uqJ!aO\r\u0003\rQD'o\\<t!\tiTI\u0004\u0002?\u0007:\u0011qHQ\u0007\u0002\u0001*\u0011\u0011iC\u0001\u0007yI|w\u000e\u001e \n\u0003iI!\u0001R\r\u0002\u000fA\f7m[1hK&\u0011ai\u0012\u0002\n\u000bb\u001cW\r\u001d;j_:T!\u0001R\r$\u0003q\n1e\u0019:fCR,Gj\\4jG\u0006sG-T1uKJL\u0017\r\\5{K\u00124\u0016\r\\;f!\u0006L'\u000f\u0006\u0002L#B!Aj\u0014\u0019\"\u001b\u0005i%B\u0001(\n\u0003\u0011Q\u0017\r]5\n\u0005Ak%\u0001\u0002)bSJDQ\u0001N\u0002A\u0002UB3aA\u001dI\u0001")
public abstract class AbstractGraphStageWithMaterializedValue<S extends Shape, M>
extends GraphStageWithMaterializedValue<S, M> {
    @Override
    public final Tuple2<GraphStageLogic, M> createLogicAndMaterializedValue(Attributes inheritedAttributes) throws Exception {
        Pair<GraphStageLogic, M> pair = this.createLogicAndMaterializedValuePair(inheritedAttributes);
        return Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(pair.first()), pair.second());
    }

    public abstract Pair<GraphStageLogic, M> createLogicAndMaterializedValuePair(Attributes var1) throws Exception;
}

