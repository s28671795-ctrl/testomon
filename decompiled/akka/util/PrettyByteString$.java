/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.ByteString;
import akka.util.PrettyByteString;
import java.io.Serializable;
import scala.Function1;
import scala.Predef$;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

public final class PrettyByteString$ {
    public static final PrettyByteString$ MODULE$ = new PrettyByteString$();
    private static final int indentDepth = 2;
    private static final String indent = StringOps$.MODULE$.$times$extension(Predef$.MODULE$.augmentString(" "), MODULE$.indentDepth() + 1);

    private int indentDepth() {
        return indentDepth;
    }

    private String indent() {
        return indent;
    }

    public PrettyByteString.asPretty asPretty(ByteString bs) {
        return new PrettyByteString.asPretty(bs);
    }

    public Iterator<String> formatBytes(ByteString bs, int maxBytes) {
        String prefix = new StringBuilder(18).append(this.indent()).append("ByteString(").append(bs.size()).append(" bytes)").toString();
        if (bs.size() <= maxBytes) {
            return package$.MODULE$.Iterator().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new String[]{new StringBuilder(1).append(prefix).append("\n").toString(), this.formatBytes$1(bs)}));
        }
        return package$.MODULE$.Iterator().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new String[]{new StringBuilder(16).append(prefix).append(" first + last ").append(maxBytes).append(":\n").toString(), this.formatBytes$1(bs.take(maxBytes)), new StringBuilder(46).append("\n").append(this.indent()).append("                    ... [").append(bs.size() - maxBytes).append(" bytes omitted] ...\n").toString(), this.formatBytes$1(bs.takeRight(maxBytes))}));
    }

    public int formatBytes$default$2() {
        return 80;
    }

    private static final String asHex$1(byte b) {
        return StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("%02X"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{BoxesRunTime.boxToByte(b)}));
    }

    private static final char asASCII$1(byte b) {
        if (b >= 32 && b < 127) {
            return (char)b;
        }
        return '.';
    }

    private final String formatLine$1(ByteString bs) {
        ByteString data = (ByteString)bs.toSeq();
        String hex = ((IterableOnceOps)data.map((Function1<Object, String> & Serializable)b -> PrettyByteString$.asHex$1(BoxesRunTime.unboxToByte(b)))).mkString(" ");
        String ascii = ((IterableOnceOps)data.map((Function1<Object, Object> & Serializable)b -> BoxesRunTime.boxToCharacter(PrettyByteString$.asASCII$1(BoxesRunTime.unboxToByte(b))))).mkString();
        return StringOps$.MODULE$.format$extension("%s  %-48s | %s", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.indent(), hex, ascii}));
    }

    private final String formatBytes$1(ByteString bs2) {
        return bs2.grouped(16).map((Function1<ByteString, String> & Serializable)bs -> this.formatLine$1((ByteString)bs)).mkString("\n");
    }

    private PrettyByteString$() {
    }
}

