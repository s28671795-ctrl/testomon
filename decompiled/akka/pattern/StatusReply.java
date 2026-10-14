/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.Done;
import akka.actor.InvalidMessageException$;
import akka.pattern.StatusReply$;
import scala.MatchError;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005\t\u0005g\u0001B\u001d;\u0005}B\u0001b\u0012\u0001\u0003\u0006\u0004%I\u0001\u0013\u0005\t5\u0002\u0011\t\u0011)A\u0005\u0013\")1\f\u0001C\u00059\")\u0001\r\u0001C\u0001C\")!\r\u0001C\u0001G\")\u0001\u000f\u0001C\u0001c\")Q\u000f\u0001C\u0001c\")a\u000f\u0001C!o\")!\u0010\u0001C!w\"1q\u0010\u0001C!\u0003\u00039q!a\u0005;\u0011\u0003\t)B\u0002\u0004:u!\u0005\u0011q\u0003\u0005\u000772!\t!!\u0007\t\u0013\u0005mAB1A\u0005\u0002\u0005u\u0001\u0002CA\u0015\u0019\u0001\u0006I!a\b\t\u000f\u0005-B\u0002\"\u0001\u0002.!9\u0011q\u0006\u0007\u0005\u0002\u0005E\u0002bBA \u0019\u0011\u0005\u0011\u0011\t\u0005\b\u0003\u007faA\u0011AA(\u0011\u001d\ti\u0006\u0004C\u0001\u0003?Bq!!\u001c\r\t\u0003\tyG\u0002\u0004\u0002~1\u0011\u0015q\u0010\u0005\u000b\u0003\u001b2\"Q1A\u0005\n\u0005}\u0005BCAQ-\tE\t\u0015!\u0003\u0002\u0004!11L\u0006C\u0001\u0003GCaa \f\u0005B\u0005\u0005\u0001\"CAV-\u0005\u0005I\u0011AAW\u0011%\t\tLFI\u0001\n\u0003\t\u0019\fC\u0005\u0002JZY\t\u0011\"\u0001\u0002 \"I\u00111\u001a\f\u0002\u0002\u0013\u0005\u0013Q\u001a\u0005\n\u0003;4\u0012\u0011!C\u0001\u0003?D\u0011\"!9\u0017\u0003\u0003%\t!a9\t\u0013\u0005%h#!A\u0005B\u0005-\b\"CA}-\u0005\u0005I\u0011AA~\u0011%\tyPFA\u0001\n\u0003\u0012\t\u0001C\u0004{-\u0005\u0005I\u0011I>\t\u0011Y4\u0012\u0011!C!\u0005\u000b9\u0011B!\u0003\r\u0003\u0003E\tAa\u0003\u0007\u0013\u0005uD\"!A\t\u0002\t5\u0001BB.(\t\u0003\u0011)\u0003\u0003\u0005\u0000O\u0005\u0005IQ\tB\u0014\u0011%\u0011IcJA\u0001\n\u0003\u0013Y\u0003C\u0005\u00030\u001d\n\t\u0011\"!\u00032!I!QH\u0014\u0002\u0002\u0013%!qH\u0004\b\u0005\u000fb\u0001\u0012\u0001B%\r\u001d\u0011Y\u0005\u0004E\u0001\u0005\u001bBaa\u0017\u0018\u0005\u0002\t=\u0003b\u0002B\u0015]\u0011\u0005!\u0011\u000b\u0005\b\u0005_qC\u0011\u0001B/\u000f\u001d\u0011)\u0007\u0004E\u0001\u0005O2qA!\u001b\r\u0011\u0003\u0011Y\u0007\u0003\u0004\\g\u0011\u0005!Q\u000e\u0005\b\u0005S\u0019D\u0011\u0001B8\u0011\u001d\u0011Ic\rC\u0001\u0005wBqAa\f4\t\u0003\u00119\t\u0003\u0005\u0003\u00182!\t\u0001\u0010BM\u0005-\u0019F/\u0019;vgJ+\u0007\u000f\\=\u000b\u0005mb\u0014a\u00029biR,'O\u001c\u0006\u0002{\u0005!\u0011m[6b\u0007\u0001)\"\u0001Q)\u0014\u0005\u0001\t\u0005C\u0001\"F\u001b\u0005\u0019%\"\u0001#\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0019\u001b%AB!osJ+g-\u0001\u0004ti\u0006$Xo]\u000b\u0002\u0013B\u0019!*T(\u000e\u0003-S!\u0001T\"\u0002\tU$\u0018\u000e\\\u0005\u0003\u001d.\u00131\u0001\u0016:z!\t\u0001\u0016\u000b\u0004\u0001\u0005\rI\u0003AQ1\u0001T\u0005\u0005!\u0016C\u0001+X!\t\u0011U+\u0003\u0002W\u0007\n9aj\u001c;iS:<\u0007C\u0001\"Y\u0013\tI6IA\u0002B]f\fqa\u001d;biV\u001c\b%\u0001\u0004=S:LGO\u0010\u000b\u0003;~\u00032A\u0018\u0001P\u001b\u0005Q\u0004\"B$\u0004\u0001\u0004I\u0015\u0001C4fiZ\u000bG.^3\u0016\u0003=\u000b\u0001bZ3u\u000bJ\u0014xN]\u000b\u0002IB\u0011Q-\u001c\b\u0003M.t!a\u001a6\u000e\u0003!T!!\u001b \u0002\rq\u0012xn\u001c;?\u0013\u0005!\u0015B\u00017D\u0003\u001d\u0001\u0018mY6bO\u0016L!A\\8\u0003\u0013QC'o\\<bE2,'B\u00017D\u0003\u001dI7/\u0012:s_J,\u0012A\u001d\t\u0003\u0005NL!\u0001^\"\u0003\u000f\t{w\u000e\\3b]\u0006I\u0011n]*vG\u000e,7o]\u0001\u0007KF,\u0018\r\\:\u0015\u0005ID\b\"B=\t\u0001\u00049\u0016!B8uQ\u0016\u0014\u0018\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003q\u0004\"AQ?\n\u0005y\u001c%aA%oi\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\u0004A!\u0011QAA\u0007\u001d\u0011\t9!!\u0003\u0011\u0005\u001d\u001c\u0015bAA\u0006\u0007\u00061\u0001K]3eK\u001aLA!a\u0004\u0002\u0012\t11\u000b\u001e:j]\u001eT1!a\u0003D\u0003-\u0019F/\u0019;vgJ+\u0007\u000f\\=\u0011\u0005yc1C\u0001\u0007B)\t\t)\"A\u0002BG.,\"!a\b\u0011\ty\u0003\u0011\u0011\u0005\t\u0005\u0003G\t)#D\u0001=\u0013\r\t9\u0003\u0010\u0002\u0005\t>tW-\u0001\u0003BG.\u0004\u0013aA1dWR\u0011\u0011qD\u0001\bgV\u001c7-Z:t+\u0011\t\u0019$!\u000f\u0015\t\u0005U\u00121\b\t\u0005=\u0002\t9\u0004E\u0002Q\u0003s!QAU\tC\u0002MCq!!\u0010\u0012\u0001\u0004\t9$A\u0003wC2,X-A\u0003feJ|'/\u0006\u0003\u0002D\u0005%C\u0003BA#\u0003\u0017\u0002BA\u0018\u0001\u0002HA\u0019\u0001+!\u0013\u0005\u000bI\u0013\"\u0019A*\t\u000f\u00055#\u00031\u0001\u0002\u0004\u0005aQM\u001d:pe6+7o]1hKV!\u0011\u0011KA,)\u0011\t\u0019&!\u0017\u0011\ty\u0003\u0011Q\u000b\t\u0004!\u0006]C!\u0002*\u0014\u0005\u0004\u0019\u0006BBA.'\u0001\u0007A-A\u0005fq\u000e,\u0007\u000f^5p]\u0006!bM]8n)JL8*Z3q\u000bb\u001cW\r\u001d;j_:,B!!\u0019\u0002hQ!\u00111MA5!\u0011q\u0006!!\u001a\u0011\u0007A\u000b9\u0007B\u0003S)\t\u00071\u000b\u0003\u0004H)\u0001\u0007\u00111\u000e\t\u0005\u00156\u000b)'A\u0004ge>lGK]=\u0016\t\u0005E\u0014q\u000f\u000b\u0005\u0003g\nI\b\u0005\u0003_\u0001\u0005U\u0004c\u0001)\u0002x\u0011)!+\u0006b\u0001'\"1q)\u0006a\u0001\u0003w\u0002BAS'\u0002v\taQI\u001d:pe6+7o]1hKNIa#!!\u0002\b\u0006M\u0015\u0011\u0014\t\u0004K\u0006\r\u0015bAAC_\n\u0001\"+\u001e8uS6,W\t_2faRLwN\u001c\t\u0005\u0003\u0013\u000by)\u0004\u0002\u0002\f*\u0019\u0011QR&\u0002\u000f\r|g\u000e\u001e:pY&!\u0011\u0011SAF\u00051qun\u0015;bG.$&/Y2f!\r\u0011\u0015QS\u0005\u0004\u0003/\u001b%a\u0002)s_\u0012,8\r\u001e\t\u0004K\u0006m\u0015bAAO_\na1+\u001a:jC2L'0\u00192mKV\u0011\u00111A\u0001\u000eKJ\u0014xN]'fgN\fw-\u001a\u0011\u0015\t\u0005\u0015\u0016\u0011\u0016\t\u0004\u0003O3R\"\u0001\u0007\t\u000f\u00055\u0013\u00041\u0001\u0002\u0004\u0005!1m\u001c9z)\u0011\t)+a,\t\u0013\u000553\u0004%AA\u0002\u0005\r\u0011AD2paf$C-\u001a4bk2$H%M\u000b\u0003\u0003kSC!a\u0001\u00028.\u0012\u0011\u0011\u0018\t\u0005\u0003w\u000b)-\u0004\u0002\u0002>*!\u0011qXAa\u0003%)hn\u00195fG.,GMC\u0002\u0002D\u000e\u000b!\"\u00198o_R\fG/[8o\u0013\u0011\t9-!0\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u000bfeJ|'/T3tg\u0006<W\rJ1dG\u0016\u001c8\u000f\n\u0019\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\ty\r\u0005\u0003\u0002R\u0006mWBAAj\u0015\u0011\t).a6\u0002\t1\fgn\u001a\u0006\u0003\u00033\fAA[1wC&!\u0011qBAj\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005a\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004/\u0006\u0015\b\u0002CAtA\u0005\u0005\t\u0019\u0001?\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\ti\u000fE\u0003\u0002p\u0006Ux+\u0004\u0002\u0002r*\u0019\u00111_\"\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002x\u0006E(\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$2A]A\u007f\u0011!\t9OIA\u0001\u0002\u00049\u0016A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!a4\u0003\u0004!A\u0011q]\u0012\u0002\u0002\u0003\u0007A\u0010F\u0002s\u0005\u000fA\u0001\"a:&\u0003\u0003\u0005\raV\u0001\r\u000bJ\u0014xN]'fgN\fw-\u001a\t\u0004\u0003O;3#B\u0014\u0003\u0010\tm\u0001\u0003\u0003B\t\u0005/\t\u0019!!*\u000e\u0005\tM!b\u0001B\u000b\u0007\u00069!/\u001e8uS6,\u0017\u0002\u0002B\r\u0005'\u0011\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82!\u0011\u0011iBa\t\u000e\u0005\t}!\u0002\u0002B\u0011\u0003/\f!![8\n\t\u0005u%q\u0004\u000b\u0003\u0005\u0017!\"!a4\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\t\u0005\u0015&Q\u0006\u0005\b\u0003\u001bR\u0003\u0019AA\u0002\u0003\u001d)h.\u00199qYf$BAa\r\u0003:A)!I!\u000e\u0002\u0004%\u0019!qG\"\u0003\r=\u0003H/[8o\u0011%\u0011YdKA\u0001\u0002\u0004\t)+A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"A!\u0011\u0011\t\u0005E'1I\u0005\u0005\u0005\u000b\n\u0019N\u0001\u0004PE*,7\r^\u0001\b'V\u001c7-Z:t!\r\t9K\f\u0002\b'V\u001c7-Z:t'\tq\u0013\t\u0006\u0002\u0003JU!!1\u000bB-)\u0011\u0011)Fa\u0017\u0011\ty\u0003!q\u000b\t\u0004!\neC!\u0002*1\u0005\u0004\u0019\u0006bBA\u001fa\u0001\u0007!q\u000b\u000b\u0005\u0005?\u0012\t\u0007\u0005\u0003C\u0005k9\u0006BB$2\u0001\u0004\u0011\u0019\u0007E\u0002_\u0001]\u000bQ!\u0012:s_J\u00042!a*4\u0005\u0015)%O]8s'\t\u0019\u0014\t\u0006\u0002\u0003hU!!\u0011\u000fB<)\u0011\u0011\u0019H!\u001f\u0011\ty\u0003!Q\u000f\t\u0004!\n]D!\u0002*6\u0005\u0004\u0019\u0006bBA'k\u0001\u0007\u00111A\u000b\u0005\u0005{\u0012\u0019\t\u0006\u0003\u0003\u0000\t\u0015\u0005\u0003\u00020\u0001\u0005\u0003\u00032\u0001\u0015BB\t\u0015\u0011fG1\u0001T\u0011\u0019\tYF\u000ea\u0001IR!!\u0011\u0012BF!\u0011\u0011%Q\u00073\t\r\u001d;\u0004\u0019\u0001BGa\u0011\u0011yIa%\u0011\ty\u0003!\u0011\u0013\t\u0004!\nMEa\u0003BK\u0005\u0017\u000b\t\u0011!A\u0003\u0002M\u00131a\u0018\u00132\u0003M1G.\u0019;uK:\u001cF/\u0019;vg\u001a+H/\u001e:f+\u0011\u0011YJa+\u0015\t\tu%Q\u0016\t\u0007\u0005?\u0013)K!+\u000e\u0005\t\u0005&b\u0001BR\u0007\u0006Q1m\u001c8dkJ\u0014XM\u001c;\n\t\t\u001d&\u0011\u0015\u0002\u0007\rV$XO]3\u0011\u0007A\u0013Y\u000bB\u0003Sq\t\u00071\u000bC\u0004\u00030b\u0002\rA!-\u0002\u0003\u0019\u0004bAa(\u0003&\nM\u0006\u0003\u00020\u0001\u0005SC3\u0001\u000fB\\!\u0011\u0011IL!0\u000e\u0005\tm&bAAby%!!q\u0018B^\u0005-Ie\u000e^3s]\u0006d\u0017\t]5")
public final class StatusReply<T> {
    private final Try<T> status;

    public static <T> StatusReply<T> fromTry(Try<T> status) {
        return StatusReply$.MODULE$.fromTry(status);
    }

    public static <T> StatusReply<T> fromTryKeepException(Try<T> status) {
        return StatusReply$.MODULE$.fromTryKeepException(status);
    }

    public static <T> StatusReply<T> error(Throwable exception) {
        return StatusReply$.MODULE$.error(exception);
    }

    public static <T> StatusReply<T> error(String errorMessage) {
        return StatusReply$.MODULE$.error(errorMessage);
    }

    public static <T> StatusReply<T> success(T value) {
        return StatusReply$.MODULE$.success(value);
    }

    public static StatusReply<Done> ack() {
        return StatusReply$.MODULE$.ack();
    }

    public static StatusReply<Done> Ack() {
        return StatusReply$.MODULE$.Ack();
    }

    private Try<T> status() {
        return this.status;
    }

    public T getValue() {
        return this.status().get();
    }

    public Throwable getError() {
        Try<T> try_ = this.status();
        if (try_ instanceof Failure) {
            Failure failure = (Failure)try_;
            Throwable ex = failure.exception();
            return ex;
        }
        throw new IllegalArgumentException("Expected reply to be a failure, but was a success");
    }

    public boolean isError() {
        return this.status().isFailure();
    }

    public boolean isSuccess() {
        return this.status().isSuccess();
    }

    public boolean equals(Object other) {
        Object object = other;
        if (object instanceof StatusReply) {
            StatusReply statusReply = (StatusReply)object;
            Try<T> try_ = this.status();
            Try<T> try_2 = statusReply.status();
            return !(try_ != null ? !try_.equals(try_2) : try_2 != null);
        }
        return false;
    }

    public int hashCode() {
        return this.status().hashCode();
    }

    public String toString() {
        Try<T> try_ = this.status();
        if (try_ instanceof Success) {
            Success success = (Success)try_;
            Object t2 = success.value();
            return new StringBuilder(9).append("Success(").append(t2).append(")").toString();
        }
        if (try_ instanceof Failure) {
            Failure failure = (Failure)try_;
            Throwable ex = failure.exception();
            return new StringBuilder(7).append("Error(").append(ex.getMessage()).append(")").toString();
        }
        throw new MatchError(try_);
    }

    public StatusReply(Try<T> status) {
        this.status = status;
        if (status == null) {
            throw InvalidMessageException$.MODULE$.apply("[null] is not an allowed status");
        }
    }

    public static final class ErrorMessage
    extends RuntimeException
    implements NoStackTrace,
    Product {
        private final String errorMessage;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
            return super.fillInStackTrace();
        }

        @Override
        public Throwable fillInStackTrace() {
            return NoStackTrace.fillInStackTrace$(this);
        }

        public String errorMessage$access$0() {
            return this.errorMessage;
        }

        private String errorMessage() {
            return this.errorMessage;
        }

        @Override
        public String toString() {
            return this.errorMessage();
        }

        public ErrorMessage copy(String errorMessage) {
            return new ErrorMessage(errorMessage);
        }

        public String copy$default$1() {
            return this.errorMessage();
        }

        @Override
        public String productPrefix() {
            return "ErrorMessage";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.errorMessage$access$0();
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
            return x$1 instanceof ErrorMessage;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "errorMessage";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof ErrorMessage)) return false;
            boolean bl = true;
            if (!bl) return false;
            ErrorMessage errorMessage = (ErrorMessage)x$1;
            String string = this.errorMessage$access$0();
            String string2 = errorMessage.errorMessage$access$0();
            if (string != null) {
                if (!string.equals(string2)) return false;
                return true;
            }
            if (string2 == null) return true;
            return false;
        }

        public ErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            super(errorMessage);
            NoStackTrace.$init$(this);
            Product.$init$(this);
        }
    }
}

