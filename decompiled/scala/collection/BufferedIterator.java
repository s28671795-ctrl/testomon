/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005=2q!\u0002\u0004\u0011\u0002\u0007\u00051\u0002C\u0003!\u0001\u0011\u0005\u0011\u0005C\u0003&\u0001\u0019\u0005a\u0005C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005SF\u0001\tCk\u001a4WM]3e\u0013R,'/\u0019;pe*\u0011q\u0001C\u0001\u000bG>dG.Z2uS>t'\"A\u0005\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011AbF\n\u0004\u00015\t\u0002C\u0001\b\u0010\u001b\u0005A\u0011B\u0001\t\t\u0005\u0019\te.\u001f*fMB\u0019!cE\u000b\u000e\u0003\u0019I!\u0001\u0006\u0004\u0003\u0011%#XM]1u_J\u0004\"AF\f\r\u0001\u00111\u0001\u0004\u0001CC\u0002e\u0011\u0011!Q\t\u00035u\u0001\"AD\u000e\n\u0005qA!a\u0002(pi\"Lgn\u001a\t\u0003\u001dyI!a\b\u0005\u0003\u0007\u0005s\u00170\u0001\u0004%S:LG\u000f\n\u000b\u0002EA\u0011abI\u0005\u0003I!\u0011A!\u00168ji\u0006!\u0001.Z1e+\u0005)\u0012A\u00035fC\u0012|\u0005\u000f^5p]V\t\u0011\u0006E\u0002\u000fUUI!a\u000b\u0005\u0003\r=\u0003H/[8o\u0003!\u0011WO\u001a4fe\u0016$W#\u0001\u0018\u000e\u0003\u0001\u0001")
public interface BufferedIterator<A>
extends Iterator<A> {
    public A head();

    public static /* synthetic */ Option headOption$(BufferedIterator $this) {
        return $this.headOption();
    }

    default public Option<A> headOption() {
        if (this.hasNext()) {
            return new Some<A>(this.head());
        }
        return None$.MODULE$;
    }

    public static /* synthetic */ BufferedIterator buffered$(BufferedIterator $this) {
        return $this.buffered();
    }

    @Override
    default public BufferedIterator<A> buffered() {
        return this;
    }

    public static void $init$(BufferedIterator $this) {
    }
}

