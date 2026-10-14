/*
 * Decompiled with CFR 0.152.
 */
package scala.io;

import scala.io.Codec;
import scala.io.Codec$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q1\u0001b\u0001\u0003\u0011\u0002\u0007\u0005\u0011\"\u0007\u0005\u0006\u001d\u0001!\ta\u0004\u0005\t'\u0001A)\u0019!C\u0002)\tIBj\\<Qe&|'/\u001b;z\u0007>$WmY%na2L7-\u001b;t\u0015\t)a!\u0001\u0002j_*\tq!A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0005\u0001Q\u0001CA\u0006\r\u001b\u00051\u0011BA\u0007\u0007\u0005\u0019\te.\u001f*fM\u00061A%\u001b8ji\u0012\"\u0012\u0001\u0005\t\u0003\u0017EI!A\u0005\u0004\u0003\tUs\u0017\u000e^\u0001\u0014M\u0006dGNY1dWNK8\u000f^3n\u0007>$WmY\u000b\u0002+A\u0011acF\u0007\u0002\t%\u0011\u0001\u0004\u0002\u0002\u0006\u0007>$Wm\u0019\b\u0003-iI!a\u0007\u0003\u0002\u000b\r{G-Z2")
public interface LowPriorityCodecImplicits {
    public static /* synthetic */ Codec fallbackSystemCodec$(LowPriorityCodecImplicits $this) {
        return $this.fallbackSystemCodec();
    }

    default public Codec fallbackSystemCodec() {
        return ((Codec$)this).defaultCharsetCodec();
    }

    public static void $init$(LowPriorityCodecImplicits $this) {
    }
}

