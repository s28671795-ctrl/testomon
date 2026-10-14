/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.NotUsed;
import akka.NotUsed$;
import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.MaterializationContext;
import akka.stream.SourceShape;
import akka.stream.impl.SourceModule;
import org.reactivestreams.Publisher;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005%4Q!\u0003\u0006\u0003\u001dAA\u0001\"\u000b\u0001\u0003\u0002\u0003\u0006IA\u000b\u0005\te\u0001\u0011)\u0019!C\u0001g!A\u0001\b\u0001B\u0001B\u0003%A\u0007C\u0005:\u0001\t\u0005\t\u0015!\u0003;{!)a\b\u0001C\u0001\u007f!)A\t\u0001C)\u000b\")\u0011\u000b\u0001C!%\")a\f\u0001C!?\ny\u0001+\u001e2mSNDWM]*pkJ\u001cWM\u0003\u0002\f\u0019\u0005!\u0011.\u001c9m\u0015\tia\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u001f\u0005!\u0011m[6b+\t\t\u0002d\u0005\u0002\u0001%A!1\u0003\u0006\f&\u001b\u0005Q\u0011BA\u000b\u000b\u00051\u0019v.\u001e:dK6{G-\u001e7f!\t9\u0002\u0004\u0004\u0001\u0005\u000be\u0001!\u0019A\u000e\u0003\u0007=+Ho\u0001\u0001\u0012\u0005q\u0011\u0003CA\u000f!\u001b\u0005q\"\"A\u0010\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0005r\"a\u0002(pi\"Lgn\u001a\t\u0003;\rJ!\u0001\n\u0010\u0003\u0007\u0005s\u0017\u0010\u0005\u0002'O5\ta\"\u0003\u0002)\u001d\t9aj\u001c;Vg\u0016$\u0017!\u00019\u0011\u0007-\u0002d#D\u0001-\u0015\tic&A\bsK\u0006\u001cG/\u001b<fgR\u0014X-Y7t\u0015\u0005y\u0013aA8sO&\u0011\u0011\u0007\f\u0002\n!V\u0014G.[:iKJ\f!\"\u0019;ue&\u0014W\u000f^3t+\u0005!\u0004CA\u001b7\u001b\u0005a\u0011BA\u001c\r\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\fCR$(/\u001b2vi\u0016\u001c\b%A\u0003tQ\u0006\u0004X\rE\u00026wYI!\u0001\u0010\u0007\u0003\u0017M{WO]2f'\"\f\u0007/Z\u0005\u0003sQ\ta\u0001P5oSRtD\u0003\u0002!B\u0005\u000e\u00032a\u0005\u0001\u0017\u0011\u0015IS\u00011\u0001+\u0011\u0015\u0011T\u00011\u00015\u0011\u0015IT\u00011\u0001;\u0003\u0015a\u0017MY3m+\u00051\u0005CA$O\u001d\tAE\n\u0005\u0002J=5\t!J\u0003\u0002L5\u00051AH]8pizJ!!\u0014\u0010\u0002\rA\u0013X\rZ3g\u0013\ty\u0005K\u0001\u0004TiJLgn\u001a\u0006\u0003\u001bz\taa\u0019:fCR,GCA*Z!\u0011iBK\u000b,\n\u0005Us\"A\u0002+va2,'G\u0004\u0002'/&\u0011\u0001LD\u0001\b\u001d>$Xk]3e\u0011\u0015Qv\u00011\u0001\\\u0003\u001d\u0019wN\u001c;fqR\u0004\"!\u000e/\n\u0005uc!AF'bi\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8D_:$X\r\u001f;\u0002\u001d]LG\u000f[!uiJL'-\u001e;fgR\u0011!\u0003\u0019\u0005\u0006C\"\u0001\r\u0001N\u0001\u0005CR$(\u000f\u000b\u0002\u0001GB\u0011AmZ\u0007\u0002K*\u0011aMD\u0001\u000bC:tw\u000e^1uS>t\u0017B\u00015f\u0005-Ie\u000e^3s]\u0006d\u0017\t]5")
@InternalApi
public final class PublisherSource<Out>
extends SourceModule<Out, NotUsed> {
    private final Publisher<Out> p;
    private final Attributes attributes;

    @Override
    public Attributes attributes() {
        return this.attributes;
    }

    @Override
    public String label() {
        return new StringBuilder(17).append("PublisherSource(").append(this.p).append(")").toString();
    }

    @Override
    public Tuple2<Publisher<Out>, NotUsed$> create(MaterializationContext context) {
        return new Tuple2<Publisher<Out>, NotUsed$>(this.p, NotUsed$.MODULE$);
    }

    @Override
    public SourceModule<Out, NotUsed> withAttributes(Attributes attr) {
        return new PublisherSource<Out>(this.p, attr, this.amendShape(attr));
    }

    public PublisherSource(Publisher<Out> p, Attributes attributes, SourceShape<Out> shape) {
        this.p = p;
        this.attributes = attributes;
        super(shape);
    }
}

