/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.ActorAttributes$;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.Graph$;
import akka.stream.Shape;
import akka.stream.impl.TraversalBuilder;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005}caB\u000b\u0017!\u0003\r\ta\u0007\u0005\u0006G\u0001!\t\u0001J\u0003\u0005Q\u0001\u0001\u0011\u0006C\u0003?\u0001\u0019\u0005q\b\u0003\u0004A\u0001\u0019\u0005a#\u0011\u0005\u0006\u0011\u00021\t!\u0013\u0005\u0006/\u0002!\t\u0001\u0017\u0005\u0006M\u0002!\ta\u001a\u0005\u0006M\u0002!\t\u0001\u001b\u0005\u0006M\u0002!\ta\u001b\u0005\u0006e\u0002!\ta\u001d\u0005\u0006k\u0002!\tA^\u0004\u0006oZA\t\u0001\u001f\u0004\u0006+YA\t!\u001f\u0005\u0006u6!\ta\u001f\u0005\u0006y6!\t! \u0004\u0007\u0003Gi1!!\n\t\u0015\u0005%\u0002C!A!\u0002\u0013\tY\u0003\u0003\u0004{!\u0011\u0005\u0011Q\u0007\u0005\u0007yB!\t!!\u0010\t\u0013\u0005-S\"!A\u0005\b\u00055#!B$sCBD'BA\f\u0019\u0003\u0019\u0019HO]3b[*\t\u0011$\u0001\u0003bW.\f7\u0001A\u000b\u000491b5C\u0001\u0001\u001e!\tq\u0012%D\u0001 \u0015\u0005\u0001\u0013!B:dC2\f\u0017B\u0001\u0012 \u0005\u0019\te.\u001f*fM\u00061A%\u001b8ji\u0012\"\u0012!\n\t\u0003=\u0019J!aJ\u0010\u0003\tUs\u0017\u000e\u001e\u0002\u0006'\"\f\u0007/\u001a\u0016\u0003UU\u0002\"a\u000b\u0017\r\u0001\u00111Q\u0006\u0001CC\u00029\u0012\u0011aU\t\u0003_I\u0002\"A\b\u0019\n\u0005Ez\"a\u0002(pi\"Lgn\u001a\t\u0003gQj\u0011AF\u0005\u0003QYY\u0013A\u000e\t\u0003oqj\u0011\u0001\u000f\u0006\u0003si\n\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005mz\u0012AC1o]>$\u0018\r^5p]&\u0011Q\b\u000f\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017!B:iCB,W#\u0001\u0016\u0002!Q\u0014\u0018M^3sg\u0006d')^5mI\u0016\u0014X#\u0001\"\u0011\u0005\r3U\"\u0001#\u000b\u0005\u00153\u0012\u0001B5na2L!a\u0012#\u0003!Q\u0013\u0018M^3sg\u0006d')^5mI\u0016\u0014\u0018AD<ji\"\fE\u000f\u001e:jEV$Xm\u001d\u000b\u0003\u0015J\u0003Ba\r\u0001+\u0017B\u00111\u0006\u0014\u0003\u0007\u001b\u0002!)\u0019\u0001(\u0003\u00035\u000b\"aL(\u0011\u0005y\u0001\u0016BA) \u0005\r\te.\u001f\u0005\u0006'\u0016\u0001\r\u0001V\u0001\u0005CR$(\u000f\u0005\u00024+&\u0011aK\u0006\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018!\u00028b[\u0016$GC\u0001&Z\u0011\u0015Qf\u00011\u0001\\\u0003\u0011q\u0017-\\3\u0011\u0005q\u001bgBA/b!\tqv$D\u0001`\u0015\t\u0001'$\u0001\u0004=e>|GOP\u0005\u0003E~\ta\u0001\u0015:fI\u00164\u0017B\u00013f\u0005\u0019\u0019FO]5oO*\u0011!mH\u0001\u0006CNLhnY\u000b\u0002\u0015R\u0011!*\u001b\u0005\u0006U\"\u0001\raW\u0001\u000bI&\u001c\b/\u0019;dQ\u0016\u0014Hc\u0001&m[\")!.\u0003a\u00017\")a.\u0003a\u0001_\u0006y\u0011N\u001c9vi\n+hMZ3s'&TX\r\u0005\u0002\u001fa&\u0011\u0011o\b\u0002\u0004\u0013:$\u0018!D1eI\u0006#HO]5ckR,7\u000f\u0006\u0002Ki\")1K\u0003a\u0001)\u0006iq-\u001a;BiR\u0014\u0018NY;uKN,\u0012\u0001V\u0001\u0006\u000fJ\f\u0007\u000f\u001b\t\u0003g5\u0019\"!D\u000f\u0002\rqJg.\u001b;?)\u0005A\u0018\u0001F7ba6\u000bG/\u001a:jC2L'0\u001a3WC2,X-F\u0004\u007f\u0003\u000b\tI\"!\u0003\u0015\u0007}\fi\u0002\u0006\u0003\u0002\u0002\u00055\u0001CB\u001a\u0001\u0003\u0007\t9\u0001E\u0002,\u0003\u000b!Q!L\bC\u00029\u00022aKA\u0005\t\u0019\tYa\u0004b\u0001\u001d\n\u0011QJ\r\u0005\b\u0003\u001fy\u0001\u0019AA\t\u0003\u00051\u0007c\u0002\u0010\u0002\u0014\u0005]\u0011qA\u0005\u0004\u0003+y\"!\u0003$v]\u000e$\u0018n\u001c82!\rY\u0013\u0011\u0004\u0003\u0007\u00037y!\u0019\u0001(\u0003\u00055\u000b\u0004bBA\u0010\u001f\u0001\u0007\u0011\u0011E\u0001\u0002OB11\u0007AA\u0002\u0003/\u0011ab\u0012:ba\"l\u0015\r]'biZ\u000bG.\u0006\u0004\u0002(\u0005=\u00121G\n\u0003!u\tAa]3mMB11\u0007AA\u0017\u0003c\u00012aKA\u0018\t\u0015i\u0003C1\u0001/!\rY\u00131\u0007\u0003\u0006\u001bB\u0011\rA\u0014\u000b\u0005\u0003o\tY\u0004E\u0004\u0002:A\ti#!\r\u000e\u00035Aq!!\u000b\u0013\u0001\u0004\tY#\u0006\u0003\u0002@\u0005\u0015C\u0003BA!\u0003\u000f\u0002ba\r\u0001\u0002.\u0005\r\u0003cA\u0016\u0002F\u00111\u00111B\nC\u00029Cq!a\u0004\u0014\u0001\u0004\tI\u0005E\u0004\u001f\u0003'\t\t$a\u0011\u0002\u001d\u001d\u0013\u0018\r\u001d5NCBl\u0015\r\u001e,bYV1\u0011qJA+\u00033\"B!!\u0015\u0002\\A9\u0011\u0011\b\t\u0002T\u0005]\u0003cA\u0016\u0002V\u0011)Q\u0006\u0006b\u0001]A\u00191&!\u0017\u0005\u000b5#\"\u0019\u0001(\t\u000f\u0005%B\u00031\u0001\u0002^A11\u0007AA*\u0003/\u0002")
public interface Graph<S extends Shape, M> {
    public static <S extends Shape, M> GraphMapMatVal<S, M> GraphMapMatVal(Graph<S, M> self) {
        return Graph$.MODULE$.GraphMapMatVal(self);
    }

    public static <S extends Shape, M1, M2> Graph<S, M2> mapMaterializedValue(Graph<S, M1> g2, Function1<M1, M2> f) {
        return Graph$.MODULE$.mapMaterializedValue(g2, f);
    }

    public S shape();

    public TraversalBuilder traversalBuilder();

    public Graph<S, M> withAttributes(Attributes var1);

    public static /* synthetic */ Graph named$(Graph $this, String name) {
        return $this.named(name);
    }

    default public Graph<S, M> named(String name) {
        return this.addAttributes(Attributes$.MODULE$.name(name));
    }

    public static /* synthetic */ Graph async$(Graph $this) {
        return $this.async();
    }

    default public Graph<S, M> async() {
        return this.addAttributes(Attributes$.MODULE$.asyncBoundary());
    }

    public static /* synthetic */ Graph async$(Graph $this, String dispatcher) {
        return $this.async(dispatcher);
    }

    default public Graph<S, M> async(String dispatcher) {
        return this.addAttributes(Attributes$.MODULE$.asyncBoundary().and(ActorAttributes$.MODULE$.dispatcher(dispatcher)));
    }

    public static /* synthetic */ Graph async$(Graph $this, String dispatcher, int inputBufferSize) {
        return $this.async(dispatcher, inputBufferSize);
    }

    default public Graph<S, M> async(String dispatcher, int inputBufferSize) {
        return this.addAttributes(Attributes$.MODULE$.asyncBoundary().and(ActorAttributes$.MODULE$.dispatcher(dispatcher)).and(Attributes$.MODULE$.inputBuffer(inputBufferSize, inputBufferSize)));
    }

    public static /* synthetic */ Graph addAttributes$(Graph $this, Attributes attr) {
        return $this.addAttributes(attr);
    }

    default public Graph<S, M> addAttributes(Attributes attr) {
        return this.withAttributes(this.traversalBuilder().attributes().and(attr));
    }

    public static /* synthetic */ Attributes getAttributes$(Graph $this) {
        return $this.getAttributes();
    }

    default public Attributes getAttributes() {
        return this.traversalBuilder().attributes();
    }

    public static void $init$(Graph $this) {
    }

    public static final class GraphMapMatVal<S extends Shape, M> {
        private final Graph<S, M> self;

        public <M2> Graph<S, M2> mapMaterializedValue(Function1<M, M2> f) {
            return Graph$.MODULE$.mapMaterializedValue(this.self, f);
        }

        public GraphMapMatVal(Graph<S, M> self) {
            this.self = self;
        }
    }
}

