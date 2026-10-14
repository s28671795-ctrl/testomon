/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.InvalidSequenceNumberException$;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u00055d\u0001B\u000e\u001d\u0005\u0006B\u0001\u0002\u0010\u0001\u0003\u0016\u0004%\t!\u0010\u0005\t\u0003\u0002\u0011\t\u0012)A\u0005}!A!\t\u0001BK\u0002\u0013\u0005Q\b\u0003\u0005D\u0001\tE\t\u0015!\u0003?\u0011!!\u0005A!f\u0001\n\u0003)\u0005\u0002\u0003(\u0001\u0005#\u0005\u000b\u0011\u0002$\t\u000b=\u0003A\u0011\u0001)\t\u000fY\u0003\u0011\u0011!C\u0001/\"91\fAI\u0001\n\u0003a\u0006bB4\u0001#\u0003%\t\u0001\u0018\u0005\bQ\u0002\t\n\u0011\"\u0001j\u0011\u001dY\u0007!!A\u0005B1Dqa\u001c\u0001\u0002\u0002\u0013\u0005\u0001\u000fC\u0004u\u0001\u0005\u0005I\u0011A;\t\u000fm\u0004\u0011\u0011!C!y\"I\u0011q\u0001\u0001\u0002\u0002\u0013\u0005\u0011\u0011\u0002\u0005\n\u0003'\u0001\u0011\u0011!C!\u0003+A\u0011\"!\u0007\u0001\u0003\u0003%\t%a\u0007\t\u0013\u0005u\u0001!!A\u0005B\u0005}q!CA\u00129\u0005\u0005\t\u0012AA\u0013\r!YB$!A\t\u0002\u0005\u001d\u0002BB(\u0016\t\u0003\ty\u0004C\u0005\u0002BU\t\t\u0011\"\u0012\u0002D!I\u0011QI\u000b\u0002\u0002\u0013\u0005\u0015q\t\u0005\n\u0003\u001f*\u0012\u0011!CA\u0003#B\u0011\"a\u0019\u0016\u0003\u0003%I!!\u001a\u0003=%sg/\u00197jIN+\u0017/^3oG\u0016tU/\u001c2fe\u0016C8-\u001a9uS>t'BA\u000f\u001f\u0003\u0019\u0019HO]3b[*\tq$\u0001\u0003bW.\f7\u0001A\n\u0005\u0001\tR\u0003\u0007\u0005\u0002$Q5\tAE\u0003\u0002&M\u0005!A.\u00198h\u0015\u00059\u0013\u0001\u00026bm\u0006L!!\u000b\u0013\u0003+%cG.Z4bYN#\u0018\r^3Fq\u000e,\u0007\u000f^5p]B\u00111FL\u0007\u0002Y)\tQ&A\u0003tG\u0006d\u0017-\u0003\u00020Y\t9\u0001K]8ek\u000e$\bCA\u0019:\u001d\t\u0011tG\u0004\u00024m5\tAG\u0003\u00026A\u00051AH]8pizJ\u0011!L\u0005\u0003q1\nq\u0001]1dW\u0006<W-\u0003\u0002;w\ta1+\u001a:jC2L'0\u00192mK*\u0011\u0001\bL\u0001\u000eKb\u0004Xm\u0019;fIN+\u0017O\u0014:\u0016\u0003y\u0002\"aK \n\u0005\u0001c#\u0001\u0002'p]\u001e\fa\"\u001a=qK\u000e$X\rZ*fc:\u0013\b%\u0001\u0005h_R\u001cV-\u001d(s\u0003%9w\u000e^*fc:\u0013\b%A\u0002ng\u001e,\u0012A\u0012\t\u0003\u000f.s!\u0001S%\u0011\u0005Mb\u0013B\u0001&-\u0003\u0019\u0001&/\u001a3fM&\u0011A*\u0014\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005)c\u0013\u0001B7tO\u0002\na\u0001P5oSRtD\u0003B)T)V\u0003\"A\u0015\u0001\u000e\u0003qAQ\u0001P\u0004A\u0002yBQAQ\u0004A\u0002yBQ\u0001R\u0004A\u0002\u0019\u000bAaY8qsR!\u0011\u000bW-[\u0011\u001da\u0004\u0002%AA\u0002yBqA\u0011\u0005\u0011\u0002\u0003\u0007a\bC\u0004E\u0011A\u0005\t\u0019\u0001$\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\tQL\u000b\u0002?=.\nq\f\u0005\u0002aK6\t\u0011M\u0003\u0002cG\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003I2\n!\"\u00198o_R\fG/[8o\u0013\t1\u0017MA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0016\u0003)T#A\u00120\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005i\u0007CA\u0012o\u0013\taE%\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001r!\tY#/\u0003\u0002tY\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011a/\u001f\t\u0003W]L!\u0001\u001f\u0017\u0003\u0007\u0005s\u0017\u0010C\u0004{\u001d\u0005\u0005\t\u0019A9\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005i\b\u0003\u0002@\u0002\u0004Yl\u0011a \u0006\u0004\u0003\u0003a\u0013AC2pY2,7\r^5p]&\u0019\u0011QA@\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003\u0017\t\t\u0002E\u0002,\u0003\u001bI1!a\u0004-\u0005\u001d\u0011un\u001c7fC:DqA\u001f\t\u0002\u0002\u0003\u0007a/\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,GcA7\u0002\u0018!9!0EA\u0001\u0002\u0004\t\u0018\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003E\fa!Z9vC2\u001cH\u0003BA\u0006\u0003CAqA_\n\u0002\u0002\u0003\u0007a/\u0001\u0010J]Z\fG.\u001b3TKF,XM\\2f\u001dVl'-\u001a:Fq\u000e,\u0007\u000f^5p]B\u0011!+F\n\u0006+\u0005%\u0012Q\u0007\t\t\u0003W\t\tD\u0010 G#6\u0011\u0011Q\u0006\u0006\u0004\u0003_a\u0013a\u0002:v]RLW.Z\u0005\u0005\u0003g\tiCA\tBEN$(/Y2u\rVt7\r^5p]N\u0002B!a\u000e\u0002>5\u0011\u0011\u0011\b\u0006\u0004\u0003w1\u0013AA5p\u0013\rQ\u0014\u0011\b\u000b\u0003\u0003K\t\u0001\u0002^8TiJLgn\u001a\u000b\u0002[\u0006)\u0011\r\u001d9msR9\u0011+!\u0013\u0002L\u00055\u0003\"\u0002\u001f\u0019\u0001\u0004q\u0004\"\u0002\"\u0019\u0001\u0004q\u0004\"\u0002#\u0019\u0001\u00041\u0015aB;oCB\u0004H.\u001f\u000b\u0005\u0003'\ny\u0006E\u0003,\u0003+\nI&C\u0002\u0002X1\u0012aa\u00149uS>t\u0007CB\u0016\u0002\\yrd)C\u0002\u0002^1\u0012a\u0001V;qY\u0016\u001c\u0004\u0002CA13\u0005\u0005\t\u0019A)\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002hA\u00191%!\u001b\n\u0007\u0005-DE\u0001\u0004PE*,7\r\u001e")
public final class InvalidSequenceNumberException
extends IllegalStateException
implements Product {
    private final long expectedSeqNr;
    private final long gotSeqNr;
    private final String msg;

    public static Option<Tuple3<Object, Object, String>> unapply(InvalidSequenceNumberException x$0) {
        return InvalidSequenceNumberException$.MODULE$.unapply(x$0);
    }

    public static InvalidSequenceNumberException apply(long expectedSeqNr, long gotSeqNr, String msg) {
        return InvalidSequenceNumberException$.MODULE$.apply(expectedSeqNr, gotSeqNr, msg);
    }

    public static Function1<Tuple3<Object, Object, String>, InvalidSequenceNumberException> tupled() {
        return InvalidSequenceNumberException$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Object, Function1<String, InvalidSequenceNumberException>>> curried() {
        return InvalidSequenceNumberException$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public long expectedSeqNr() {
        return this.expectedSeqNr;
    }

    public long gotSeqNr() {
        return this.gotSeqNr;
    }

    public String msg() {
        return this.msg;
    }

    public InvalidSequenceNumberException copy(long expectedSeqNr, long gotSeqNr, String msg) {
        return new InvalidSequenceNumberException(expectedSeqNr, gotSeqNr, msg);
    }

    public long copy$default$1() {
        return this.expectedSeqNr();
    }

    public long copy$default$2() {
        return this.gotSeqNr();
    }

    public String copy$default$3() {
        return this.msg();
    }

    @Override
    public String productPrefix() {
        return "InvalidSequenceNumberException";
    }

    @Override
    public int productArity() {
        return 3;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return BoxesRunTime.boxToLong(this.expectedSeqNr());
            }
            case 1: {
                return BoxesRunTime.boxToLong(this.gotSeqNr());
            }
            case 2: {
                return this.msg();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof InvalidSequenceNumberException;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "expectedSeqNr";
            }
            case 1: {
                return "gotSeqNr";
            }
            case 2: {
                return "msg";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.longHash(this.expectedSeqNr()));
        n = Statics.mix(n, Statics.longHash(this.gotSeqNr()));
        n = Statics.mix(n, Statics.anyHash(this.msg()));
        return Statics.finalizeHash(n, 3);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof InvalidSequenceNumberException)) return false;
        boolean bl = true;
        if (!bl) return false;
        InvalidSequenceNumberException invalidSequenceNumberException = (InvalidSequenceNumberException)x$1;
        if (this.expectedSeqNr() != invalidSequenceNumberException.expectedSeqNr()) return false;
        if (this.gotSeqNr() != invalidSequenceNumberException.gotSeqNr()) return false;
        String string = this.msg();
        String string2 = invalidSequenceNumberException.msg();
        if (string != null) {
            if (!string.equals(string2)) return false;
            return true;
        }
        if (string2 == null) return true;
        return false;
    }

    public InvalidSequenceNumberException(long expectedSeqNr, long gotSeqNr, String msg) {
        this.expectedSeqNr = expectedSeqNr;
        this.gotSeqNr = gotSeqNr;
        this.msg = msg;
        super(new StringBuilder(130).append(msg).append(" (expected: ").append(expectedSeqNr).append(", got: ").append(gotSeqNr).append("). ").append("In most cases this means that message loss on this connection has occurred and the stream will fail eagerly.").toString());
        Product.$init$(this);
    }
}

