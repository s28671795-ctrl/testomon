/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Graph;
import akka.stream.Shape;
import akka.stream.impl.TraversalBuilder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005]3Q!\u0003\u0006\u0003\u0019AA\u0001b\f\u0001\u0003\u0006\u0004%\t\u0005\r\u0005\tc\u0001\u0011\t\u0011)A\u00059!A!\u0007\u0001B\u0001B\u0003%1\u0007\u0003\u0005:\u0001\t\u0005\t\u0015!\u0003;\u0011\u0015i\u0004\u0001\"\u0001?\u0011\u0019!\u0005\u0001\"\u0001\r\u000b\")a\t\u0001C!\u000f\")1\u000b\u0001C!)\n\ts)\u001a8fe&\u001cwI]1qQ^KG\u000f[\"iC:<W\rZ!uiJL'-\u001e;fg*\u00111\u0002D\u0001\tg\u000e\fG.\u00193tY*\u0011QBD\u0001\u0007gR\u0014X-Y7\u000b\u0003=\tA!Y6lCV\u0019\u0011CH\u0015\u0014\u0007\u0001\u0011\u0002\u0004\u0005\u0002\u0014-5\tACC\u0001\u0016\u0003\u0015\u00198-\u00197b\u0013\t9BC\u0001\u0004B]f\u0014VM\u001a\t\u00053ia\u0002&D\u0001\r\u0013\tYBBA\u0003He\u0006\u0004\b\u000e\u0005\u0002\u001e=1\u0001A!B\u0010\u0001\u0005\u0004\t#!A*\u0004\u0001E\u0011!%\n\t\u0003'\rJ!\u0001\n\u000b\u0003\u000f9{G\u000f[5oOB\u0011\u0011DJ\u0005\u0003O1\u0011Qa\u00155ba\u0016\u0004\"!H\u0015\u0005\u000b)\u0002!\u0019A\u0016\u0003\u00075\u000bG/\u0005\u0002#YA\u00111#L\u0005\u0003]Q\u00111!\u00118z\u0003\u0015\u0019\b.\u00199f+\u0005a\u0012AB:iCB,\u0007%\u0001\rpe&<\u0017N\\1m)J\fg/\u001a:tC2\u0014U/\u001b7eKJ\u0004\"\u0001N\u001c\u000e\u0003UR!A\u000e\u0007\u0002\t%l\u0007\u000f\\\u0005\u0003qU\u0012\u0001\u0003\u0016:bm\u0016\u00148/\u00197Ck&dG-\u001a:\u0002\u001b9,w/\u0011;ue&\u0014W\u000f^3t!\tI2(\u0003\u0002=\u0019\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\rqJg.\u001b;?)\u0011y\u0014IQ\"\u0011\t\u0001\u0003A\u0004K\u0007\u0002\u0015!)q&\u0002a\u00019!)!'\u0002a\u0001g!)\u0011(\u0002a\u0001u\u0005\u0001BO]1wKJ\u001c\u0018\r\u001c\"vS2$WM]\u000b\u0002g\u0005AAo\\*ue&tw\rF\u0001I!\tI\u0005K\u0004\u0002K\u001dB\u00111\nF\u0007\u0002\u0019*\u0011Q\nI\u0001\u0007yI|w\u000e\u001e \n\u0005=#\u0012A\u0002)sK\u0012,g-\u0003\u0002R%\n11\u000b\u001e:j]\u001eT!a\u0014\u000b\u0002\u001d]LG\u000f[!uiJL'-\u001e;fgR\u0011\u0001$\u0016\u0005\u0006-\"\u0001\rAO\u0001\u0005CR$(\u000f")
public final class GenericGraphWithChangedAttributes<S extends Shape, Mat>
implements Graph<S, Mat> {
    private final S shape;
    private final TraversalBuilder originalTraversalBuilder;
    private final Attributes newAttributes;

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
        return this.originalTraversalBuilder.setAttributes(this.newAttributes);
    }

    public String toString() {
        return new StringBuilder(35).append("GenericGraphWithChangedAttributes(").append(this.shape()).append(")").toString();
    }

    @Override
    public Graph<S, Mat> withAttributes(Attributes attr) {
        return new GenericGraphWithChangedAttributes<S, Mat>(this.shape(), this.originalTraversalBuilder, attr);
    }

    public GenericGraphWithChangedAttributes(S shape, TraversalBuilder originalTraversalBuilder, Attributes newAttributes) {
        this.shape = shape;
        this.originalTraversalBuilder = originalTraversalBuilder;
        this.newAttributes = newAttributes;
        Graph.$init$(this);
    }
}

