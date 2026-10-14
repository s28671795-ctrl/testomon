/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Graph;
import akka.stream.Shape;
import akka.stream.impl.TraversalBuilder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}2a!\u0002\u0004\u0002\u0002\u0019Q\u0001\u0002C\u0015\u0001\u0005\u0003\u0005\u000b\u0011\u0002\n\t\u000b)\u0002A\u0011A\u0016\t\u000b9\u0002AQI\u0018\t\rA\u0002AQ\t\u00042\u000559%/\u00199i\t\u0016dWmZ1uK*\u0011q\u0001C\u0001\u0007gR\u0014X-Y7\u000b\u0003%\tA!Y6lCV\u00191\u0002G\u0012\u0014\u0007\u0001a!\u0003\u0005\u0002\u000e!5\taBC\u0001\u0010\u0003\u0015\u00198-\u00197b\u0013\t\tbB\u0001\u0004B]f\u0014VM\u001a\t\u0005'Q1\"%D\u0001\u0007\u0013\t)bAA\u0003He\u0006\u0004\b\u000e\u0005\u0002\u001811\u0001AAB\r\u0001\t\u000b\u00071DA\u0001T\u0007\u0001\t\"\u0001H\u0010\u0011\u00055i\u0012B\u0001\u0010\u000f\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"a\u0005\u0011\n\u0005\u00052!!B*iCB,\u0007CA\f$\t\u0019!\u0003\u0001\"b\u0001K\t\u0019Q*\u0019;\u0012\u0005q1\u0003CA\u0007(\u0013\tAcBA\u0002B]f\f\u0001\u0002Z3mK\u001e\fG/Z\u0001\u0007y%t\u0017\u000e\u001e \u0015\u00051j\u0003\u0003B\n\u0001-\tBQ!\u000b\u0002A\u0002I\tQa\u001d5ba\u0016,\u0012AF\u0001\u0011iJ\fg/\u001a:tC2\u0014U/\u001b7eKJ,\u0012A\r\t\u0003gYj\u0011\u0001\u000e\u0006\u0003k\u0019\tA![7qY&\u0011q\u0007\u000e\u0002\u0011)J\fg/\u001a:tC2\u0014U/\u001b7eKJD#\u0001A\u001d\u0011\u0005ijT\"A\u001e\u000b\u0005qB\u0011AC1o]>$\u0018\r^5p]&\u0011ah\u000f\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e")
@InternalApi
public abstract class GraphDelegate<S extends Shape, Mat>
implements Graph<S, Mat> {
    private final Graph<S, Mat> delegate;

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
    public final S shape() {
        return this.delegate.shape();
    }

    @Override
    public final TraversalBuilder traversalBuilder() {
        return this.delegate.traversalBuilder();
    }

    public GraphDelegate(Graph<S, Mat> delegate) {
        this.delegate = delegate;
        Graph.$init$(this);
    }
}

