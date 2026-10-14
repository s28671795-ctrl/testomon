/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Graph;
import akka.stream.Outlet;
import akka.stream.SourceShape;
import akka.stream.impl.MaybeSource$;
import akka.stream.stage.GraphStageLogic;
import scala.Option;
import scala.Tuple2;
import scala.concurrent.Promise;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m;aAC\u0006\t\u0002=\tbAB\n\f\u0011\u0003yA\u0003C\u0003/\u0003\u0011\u0005\u0001\u0007C\u00042\u0003\t\u0007I\u0011\u0001\u001a\t\rY\n\u0001\u0015!\u00034\u0011\u001d9\u0014A1A\u0005BaBa!O\u0001!\u0002\u0013Y\u0002\"\u0002\u001e\u0002\t#Z\u0004\"B \u0002\t\u0003\u0002\u0005\"B%\u0002\t\u0003R\u0015aC'bs\n,7k\\;sG\u0016T!\u0001D\u0007\u0002\t%l\u0007\u000f\u001c\u0006\u0003\u001d=\taa\u001d;sK\u0006l'\"\u0001\t\u0002\t\u0005\\7.\u0019\t\u0003%\u0005i\u0011a\u0003\u0002\f\u001b\u0006L(-Z*pkJ\u001cWm\u0005\u0002\u0002+A!a#G\u000e&\u001b\u00059\"B\u0001\r\u000e\u0003\u0015\u0019H/Y4f\u0013\tQrCA\u0010He\u0006\u0004\bn\u0015;bO\u0016<\u0016\u000e\u001e5NCR,'/[1mSj,GMV1mk\u0016\u00042\u0001H\u000f \u001b\u0005i\u0011B\u0001\u0010\u000e\u0005-\u0019v.\u001e:dKNC\u0017\r]3\u0011\u0005\u0001\u001aS\"A\u0011\u000b\u0003\t\nQa]2bY\u0006L!\u0001J\u0011\u0003\r\u0005s\u0017PU3g!\r1\u0013fK\u0007\u0002O)\u0011\u0001&I\u0001\u000bG>t7-\u001e:sK:$\u0018B\u0001\u0016(\u0005\u001d\u0001&o\\7jg\u0016\u00042\u0001\t\u0017 \u0013\ti\u0013E\u0001\u0004PaRLwN\\\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\t\u0011#A\u0002pkR,\u0012a\r\t\u00049Qz\u0012BA\u001b\u000e\u0005\u0019yU\u000f\u001e7fi\u0006!q.\u001e;!\u0003\u0015\u0019\b.\u00199f+\u0005Y\u0012AB:iCB,\u0007%A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\u0012\u0001\u0010\t\u00039uJ!AP\u0007\u0003\u0015\u0005#HO]5ckR,7/A\u0010de\u0016\fG/\u001a'pO&\u001c\u0017I\u001c3NCR,'/[1mSj,GMV1mk\u0016$\"!Q$\u0011\t\u0001\u0012E)J\u0005\u0003\u0007\u0006\u0012a\u0001V;qY\u0016\u0014\u0004C\u0001\fF\u0013\t1uCA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u0015A\u0005\u00021\u0001=\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t\u0003!!xn\u0015;sS:<G#A&\u0011\u00051\u000bV\"A'\u000b\u00059{\u0015\u0001\u00027b]\u001eT\u0011\u0001U\u0001\u0005U\u00064\u0018-\u0003\u0002S\u001b\n11\u000b\u001e:j]\u001eD#!\u0001+\u0011\u0005UCV\"\u0001,\u000b\u0005]{\u0011AC1o]>$\u0018\r^5p]&\u0011\u0011L\u0016\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e\u000b\u0002\u0001)\u0002")
@InternalApi
public final class MaybeSource {
    public static String toString() {
        return MaybeSource$.MODULE$.toString();
    }

    public static Tuple2<GraphStageLogic, Promise<Option<Object>>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        return MaybeSource$.MODULE$.createLogicAndMaterializedValue(inheritedAttributes);
    }

    public static SourceShape<Object> shape() {
        return MaybeSource$.MODULE$.shape();
    }

    public static Outlet<Object> out() {
        return MaybeSource$.MODULE$.out();
    }

    public static Graph<SourceShape<Object>, Promise<Option<Object>>> withAttributes(Attributes attr) {
        return MaybeSource$.MODULE$.withAttributes(attr);
    }

    public static Attributes getAttributes() {
        return MaybeSource$.MODULE$.getAttributes();
    }

    public static Graph<SourceShape<Object>, Promise<Option<Object>>> addAttributes(Attributes attr) {
        return MaybeSource$.MODULE$.addAttributes(attr);
    }

    public static Graph<SourceShape<Object>, Promise<Option<Object>>> async(String dispatcher, int inputBufferSize) {
        return MaybeSource$.MODULE$.async(dispatcher, inputBufferSize);
    }

    public static Graph<SourceShape<Object>, Promise<Option<Object>>> async(String dispatcher) {
        return MaybeSource$.MODULE$.async(dispatcher);
    }

    public static Graph<SourceShape<Object>, Promise<Option<Object>>> async() {
        return MaybeSource$.MODULE$.async();
    }

    public static Graph<SourceShape<Object>, Promise<Option<Object>>> named(String name) {
        return MaybeSource$.MODULE$.named(name);
    }
}

