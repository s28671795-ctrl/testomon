/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Graph;
import akka.stream.Shape;
import akka.stream.impl.TraversalBuilder;
import akka.stream.scaladsl.GenericGraphWithChangedAttributes;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00054Q!\u0003\u0006\u0003\u0019AA\u0001b\f\u0001\u0003\u0006\u0004%\t\u0005\r\u0005\tc\u0001\u0011\t\u0011)A\u00059!A!\u0007\u0001BC\u0002\u0013\u00053\u0007\u0003\u0005;\u0001\t\u0005\t\u0015!\u00035\u0011\u0015Y\u0004\u0001\"\u0001=\u0011\u0015\t\u0005\u0001\"\u0011C\u0011\u0015q\u0005\u0001\"\u0011P\u0011\u0015)\u0006\u0001\"\u0001W\u000519UM\\3sS\u000e<%/\u00199i\u0015\tYA\"\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\tia\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u001f\u0005!\u0011m[6b+\r\tb$K\n\u0004\u0001IA\u0002CA\n\u0017\u001b\u0005!\"\"A\u000b\u0002\u000bM\u001c\u0017\r\\1\n\u0005]!\"AB!osJ+g\r\u0005\u0003\u001a5qAS\"\u0001\u0007\n\u0005ma!!B$sCBD\u0007CA\u000f\u001f\u0019\u0001!Qa\b\u0001C\u0002\u0005\u0012\u0011aU\u0002\u0001#\t\u0011S\u0005\u0005\u0002\u0014G%\u0011A\u0005\u0006\u0002\b\u001d>$\b.\u001b8h!\tIb%\u0003\u0002(\u0019\t)1\u000b[1qKB\u0011Q$\u000b\u0003\u0006U\u0001\u0011\ra\u000b\u0002\u0004\u001b\u0006$\u0018C\u0001\u0012-!\t\u0019R&\u0003\u0002/)\t\u0019\u0011I\\=\u0002\u000bMD\u0017\r]3\u0016\u0003q\taa\u001d5ba\u0016\u0004\u0013\u0001\u0005;sCZ,'o]1m\u0005VLG\u000eZ3s+\u0005!\u0004CA\u001b9\u001b\u00051$BA\u001c\r\u0003\u0011IW\u000e\u001d7\n\u0005e2$\u0001\u0005+sCZ,'o]1m\u0005VLG\u000eZ3s\u0003E!(/\u0019<feN\fGNQ;jY\u0012,'\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007uz\u0004\t\u0005\u0003?\u0001qAS\"\u0001\u0006\t\u000b=*\u0001\u0019\u0001\u000f\t\u000bI*\u0001\u0019\u0001\u001b\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012a\u0011\t\u0003\t.s!!R%\u0011\u0005\u0019#R\"A$\u000b\u0005!\u0003\u0013A\u0002\u001fs_>$h(\u0003\u0002K)\u00051\u0001K]3eK\u001aL!\u0001T'\u0003\rM#(/\u001b8h\u0015\tQE#\u0001\bxSRD\u0017\t\u001e;sS\n,H/Z:\u0015\u0005a\u0001\u0006\"B)\b\u0001\u0004\u0011\u0016\u0001B1uiJ\u0004\"!G*\n\u0005Qc!AC!uiJL'-\u001e;fg\u0006!R.\u00199NCR,'/[1mSj,GMV1mk\u0016,\"a\u0016.\u0015\u0005ac\u0006\u0003\u0002 \u00019e\u0003\"!\b.\u0005\u000bmC!\u0019A\u0016\u0003\t5\u000bGO\r\u0005\u0006;\"\u0001\rAX\u0001\u0002MB!1c\u0018\u0015Z\u0013\t\u0001GCA\u0005Gk:\u001cG/[8oc\u0001")
public final class GenericGraph<S extends Shape, Mat>
implements Graph<S, Mat> {
    private final S shape;
    private final TraversalBuilder traversalBuilder;

    @Override
    public Graph<S, Mat> named(String name) {
        return Graph.named$(this, name);
    }

    @Override
    public Graph<S, Mat> async() {
        return Graph.async$(this);
    }

    @Override
    public Graph<S, Mat> async(String dispatcher) {
        return Graph.async$(this, dispatcher);
    }

    @Override
    public Graph<S, Mat> async(String dispatcher, int inputBufferSize) {
        return Graph.async$(this, dispatcher, inputBufferSize);
    }

    @Override
    public Graph<S, Mat> addAttributes(Attributes attr) {
        return Graph.addAttributes$(this, attr);
    }

    @Override
    public Attributes getAttributes() {
        return Graph.getAttributes$(this);
    }

    @Override
    public S shape() {
        return this.shape;
    }

    @Override
    public TraversalBuilder traversalBuilder() {
        return this.traversalBuilder;
    }

    public String toString() {
        return new StringBuilder(14).append("GenericGraph(").append(this.shape()).append(")").toString();
    }

    @Override
    public Graph<S, Mat> withAttributes(Attributes attr) {
        return new GenericGraphWithChangedAttributes(this.shape(), this.traversalBuilder(), attr);
    }

    public <Mat2> GenericGraph<S, Mat2> mapMaterializedValue(Function1<Mat, Mat2> f) {
        return new GenericGraph<S, Mat>(this.shape(), this.traversalBuilder().transformMat(f));
    }

    public GenericGraph(S shape, TraversalBuilder traversalBuilder) {
        this.shape = shape;
        this.traversalBuilder = traversalBuilder;
        Graph.$init$(this);
    }
}

