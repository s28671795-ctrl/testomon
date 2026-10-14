/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.NotUsed;
import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Graph;
import akka.stream.Outlet;
import akka.stream.SourceShape;
import akka.stream.impl.EmptySource$;
import akka.stream.stage.GraphStageLogic;
import scala.Tuple2;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005=;aAC\u0006\t\u0002=\tbAB\n\f\u0011\u0003yA\u0003C\u0003&\u0003\u0011\u0005q\u0005C\u0004)\u0003\t\u0007I\u0011A\u0015\t\r5\n\u0001\u0015!\u0003+\u0011\u001dq\u0013A1A\u0005B=Ba\u0001M\u0001!\u0002\u0013Y\u0002\"B\u0019\u0002\t#\u0012\u0004\"\u0002\u001c\u0002\t\u0003:\u0004\"B\u001f\u0002\t\u0003r\u0014aC#naRL8k\\;sG\u0016T!\u0001D\u0007\u0002\t%l\u0007\u000f\u001c\u0006\u0003\u001d=\taa\u001d;sK\u0006l'\"\u0001\t\u0002\t\u0005\\7.\u0019\t\u0003%\u0005i\u0011a\u0003\u0002\f\u000b6\u0004H/_*pkJ\u001cWm\u0005\u0002\u0002+A\u0019a#G\u000e\u000e\u0003]Q!\u0001G\u0007\u0002\u000bM$\u0018mZ3\n\u0005i9\"AC$sCBD7\u000b^1hKB\u0019A$H\u0010\u000e\u00035I!AH\u0007\u0003\u0017M{WO]2f'\"\f\u0007/\u001a\t\u0003A\rj\u0011!\t\u0006\u0002E\u0005)1oY1mC&\u0011A%\t\u0002\b\u001d>$\b.\u001b8h\u0003\u0019a\u0014N\\5u}\r\u0001A#A\t\u0002\u0007=,H/F\u0001+!\ra2fH\u0005\u0003Y5\u0011aaT;uY\u0016$\u0018\u0001B8vi\u0002\nQa\u001d5ba\u0016,\u0012aG\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u00014!\taB'\u0003\u00026\u001b\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u0017\r\u0014X-\u0019;f\u0019><\u0017n\u0019\u000b\u0003qm\u0002\"AF\u001d\n\u0005i:\"aD$sCBD7\u000b^1hK2{w-[2\t\u000bqB\u0001\u0019A\u001a\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012a\u0010\t\u0003\u0001\u0016k\u0011!\u0011\u0006\u0003\u0005\u000e\u000bA\u0001\\1oO*\tA)\u0001\u0003kCZ\f\u0017B\u0001$B\u0005\u0019\u0019FO]5oO\"\u0012\u0011\u0001\u0013\t\u0003\u00132k\u0011A\u0013\u0006\u0003\u0017>\t!\"\u00198o_R\fG/[8o\u0013\ti%JA\u0006J]R,'O\\1m\u0003BL\u0007F\u0001\u0001I\u0001")
@InternalApi
public final class EmptySource {
    public static String toString() {
        return EmptySource$.MODULE$.toString();
    }

    public static GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return EmptySource$.MODULE$.createLogic(inheritedAttributes);
    }

    public static SourceShape<Nothing$> shape() {
        return EmptySource$.MODULE$.shape();
    }

    public static Outlet<Nothing$> out() {
        return EmptySource$.MODULE$.out();
    }

    public static Tuple2<GraphStageLogic, NotUsed> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        return EmptySource$.MODULE$.createLogicAndMaterializedValue(inheritedAttributes);
    }

    public static Graph<SourceShape<Nothing$>, NotUsed> withAttributes(Attributes attr) {
        return EmptySource$.MODULE$.withAttributes(attr);
    }

    public static Attributes getAttributes() {
        return EmptySource$.MODULE$.getAttributes();
    }

    public static Graph<SourceShape<Nothing$>, NotUsed> addAttributes(Attributes attr) {
        return EmptySource$.MODULE$.addAttributes(attr);
    }

    public static Graph<SourceShape<Nothing$>, NotUsed> async(String dispatcher, int inputBufferSize) {
        return EmptySource$.MODULE$.async(dispatcher, inputBufferSize);
    }

    public static Graph<SourceShape<Nothing$>, NotUsed> async(String dispatcher) {
        return EmptySource$.MODULE$.async(dispatcher);
    }

    public static Graph<SourceShape<Nothing$>, NotUsed> async() {
        return EmptySource$.MODULE$.async();
    }

    public static Graph<SourceShape<Nothing$>, NotUsed> named(String name) {
        return EmptySource$.MODULE$.named(name);
    }
}

