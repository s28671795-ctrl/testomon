/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.ByteString;
import akka.util.PrettyByteString$;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0001<aa\u0004\t\t\u0002I!bA\u0002\f\u0011\u0011\u0003\u0011r\u0003C\u0003\u001f\u0003\u0011\u0005\u0001\u0005C\u0004\"\u0003\t\u0007I\u0011\u0002\u0012\t\r\u0019\n\u0001\u0015!\u0003$\u0011\u001d9\u0013A1A\u0005\n!Ba\u0001N\u0001!\u0002\u0013Ic\u0001B\u001b\u0002\u0003YB\u0001bN\u0004\u0003\u0002\u0003\u0006I\u0001\u000f\u0005\u0006=\u001d!\ta\u000f\u0005\u0006\u007f\u001d!\t\u0001\u0011\u0005\b\u0007\u001e\t\n\u0011\"\u0001E\u0011\u001dy\u0015!!A\u0005\u0004ACQAU\u0001\u0005\u0002MCqaX\u0001\u0012\u0002\u0013\u0005A)\u0001\tQe\u0016$H/\u001f\"zi\u0016\u001cFO]5oO*\u0011\u0011CE\u0001\u0005kRLGNC\u0001\u0014\u0003\u0011\t7n[1\u0011\u0005U\tQ\"\u0001\t\u0003!A\u0013X\r\u001e;z\u0005f$Xm\u0015;sS:<7CA\u0001\u0019!\tIB$D\u0001\u001b\u0015\u0005Y\u0012!B:dC2\f\u0017BA\u000f\u001b\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001\u0015\u0003-Ig\u000eZ3oi\u0012+\u0007\u000f\u001e5\u0016\u0003\r\u0002\"!\u0007\u0013\n\u0005\u0015R\"aA%oi\u0006a\u0011N\u001c3f]R$U\r\u001d;iA\u00051\u0011N\u001c3f]R,\u0012!\u000b\t\u0003UEr!aK\u0018\u0011\u00051RR\"A\u0017\u000b\u00059z\u0012A\u0002\u001fs_>$h(\u0003\u000215\u00051\u0001K]3eK\u001aL!AM\u001a\u0003\rM#(/\u001b8h\u0015\t\u0001$$A\u0004j]\u0012,g\u000e\u001e\u0011\u0003\u0011\u0005\u001c\bK]3uif\u001c\"a\u0002\r\u0002\u0005\t\u001c\bCA\u000b:\u0013\tQ\u0004C\u0001\u0006CsR,7\u000b\u001e:j]\u001e$\"\u0001\u0010 \u0011\u0005u:Q\"A\u0001\t\u000b]J\u0001\u0019\u0001\u001d\u0002\u0017A\u0014X\r\u001e;z!JLg\u000e\u001e\u000b\u0003S\u0005CqA\u0011\u0006\u0011\u0002\u0003\u00071%\u0001\u0005nCb\u0014\u0015\u0010^3t\u0003U\u0001(/\u001a;usB\u0013\u0018N\u001c;%I\u00164\u0017-\u001e7uIE*\u0012!\u0012\u0016\u0003G\u0019[\u0013a\u0012\t\u0003\u00116k\u0011!\u0013\u0006\u0003\u0015.\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u00051S\u0012AC1o]>$\u0018\r^5p]&\u0011a*\u0013\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017\u0001C1t!J,G\u000f^=\u0015\u0005q\n\u0006\"B\u001c\r\u0001\u0004A\u0014a\u00034pe6\fGOQ=uKN$2\u0001V/_!\r)&,\u000b\b\u0003-bs!\u0001L,\n\u0003mI!!\u0017\u000e\u0002\u000fA\f7m[1hK&\u00111\f\u0018\u0002\t\u0013R,'/\u0019;pe*\u0011\u0011L\u0007\u0005\u0006o5\u0001\r\u0001\u000f\u0005\b\u00056\u0001\n\u00111\u0001$\u0003U1wN]7bi\nKH/Z:%I\u00164\u0017-\u001e7uII\u0002")
public final class PrettyByteString {
    public static int formatBytes$default$2() {
        return PrettyByteString$.MODULE$.formatBytes$default$2();
    }

    public static Iterator<String> formatBytes(ByteString bs, int maxBytes) {
        return PrettyByteString$.MODULE$.formatBytes(bs, maxBytes);
    }

    public static asPretty asPretty(ByteString bs) {
        return PrettyByteString$.MODULE$.asPretty(bs);
    }

    public static class asPretty {
        private final ByteString bs;

        public String prettyPrint(int maxBytes) {
            return PrettyByteString$.MODULE$.formatBytes(this.bs, maxBytes).mkString("\n");
        }

        public int prettyPrint$default$1() {
            return 80;
        }

        public asPretty(ByteString bs) {
            this.bs = bs;
        }
    }
}

