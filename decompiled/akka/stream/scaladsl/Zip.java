/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.scaladsl.Zip$;
import akka.stream.scaladsl.ZipWith2;
import java.io.Serializable;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0015;Qa\u0002\u0005\t\u0002=1Q!\u0005\u0005\t\u0002IAQ!G\u0001\u0005\u0002iAQaG\u0001\u0005\u0002q1A!\u0005\u0005\u0003?!)\u0011\u0004\u0002C\u0001k!)q\u0007\u0002C!q\u0005\u0019!,\u001b9\u000b\u0005%Q\u0011\u0001C:dC2\fGm\u001d7\u000b\u0005-a\u0011AB:ue\u0016\fWNC\u0001\u000e\u0003\u0011\t7n[1\u0004\u0001A\u0011\u0001#A\u0007\u0002\u0011\t\u0019!,\u001b9\u0014\u0005\u0005\u0019\u0002C\u0001\u000b\u0018\u001b\u0005)\"\"\u0001\f\u0002\u000bM\u001c\u0017\r\\1\n\u0005a)\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002\u001f\u0005)\u0011\r\u001d9msV\u0019QD\u0011#\u0015\u0003y\u0001B\u0001\u0005\u0003B\u0007V\u0019\u0001E\n\u0019\u0014\u0005\u0011\t\u0003#\u0002\t#I=\u0012\u0014BA\u0012\t\u0005!Q\u0016\u000e],ji\"\u0014\u0004CA\u0013'\u0019\u0001!Qa\n\u0003C\u0002!\u0012\u0011!Q\t\u0003S1\u0002\"\u0001\u0006\u0016\n\u0005-*\"a\u0002(pi\"Lgn\u001a\t\u0003)5J!AL\u000b\u0003\u0007\u0005s\u0017\u0010\u0005\u0002&a\u0011)\u0011\u0007\u0002b\u0001Q\t\t!\t\u0005\u0003\u0015g\u0011z\u0013B\u0001\u001b\u0016\u0005\u0019!V\u000f\u001d7feQ\ta\u0007\u0005\u0003\u0011\t\u0011z\u0013\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003e\u0002\"AO \u000e\u0003mR!\u0001P\u001f\u0002\t1\fgn\u001a\u0006\u0002}\u0005!!.\u0019<b\u0013\t\u00015H\u0001\u0004TiJLgn\u001a\t\u0003K\t#QaJ\u0002C\u0002!\u0002\"!\n#\u0005\u000bE\u001a!\u0019\u0001\u0015")
public final class Zip<A, B>
extends ZipWith2<A, B, Tuple2<A, B>> {
    public static <A, B> Zip<A, B> apply() {
        return Zip$.MODULE$.apply();
    }

    @Override
    public String toString() {
        return "Zip";
    }

    public Zip() {
        super(new Serializable(){
            private static final long serialVersionUID = 0L;

            public final Tuple2<A, B> apply(A _1, B _2) {
                return new Tuple2<A, B>(_1, _2);
            }
        });
    }
}

