/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005w!\u0002\u00192\u0011\u00031d!\u0002\u001d2\u0011\u0003I\u0004\"\u0002!\u0002\t\u0003\tea\u0002\u001d\u0002!\u0003\r\nC\u0011\u0004\u0007\u0003\u000f\n!)!\u0013\t\u0015\u0005-CA!f\u0001\n\u0003\ti\u0005\u0003\u0006\u0002P\u0011\u0011\t\u0012)A\u0005\u0003\u0003Aa\u0001\u0011\u0003\u0005\u0002\u0005E\u0003\u0002\u00031\u0005\u0003\u0003%\t!a\u0016\t\u0011\r$\u0011\u0013!C\u0001\u00037Bqa\u001c\u0003\u0002\u0002\u0013\u0005\u0003\u000fC\u0004z\t\u0005\u0005I\u0011\u0001>\t\u0011y$\u0011\u0011!C\u0001\u0003?B\u0011\"a\u0003\u0005\u0003\u0003%\t%!\u0004\t\u0013\u0005mA!!A\u0005\u0002\u0005\r\u0004\"CA\u0014\t\u0005\u0005I\u0011IA4\u0011%\ti\u0003BA\u0001\n\u0003\ny\u0003C\u0005\u00022\u0011\t\t\u0011\"\u0011\u00024!I\u0011Q\u0007\u0003\u0002\u0002\u0013\u0005\u00131N\u0004\n\u0003c\n\u0011\u0011!E\u0001\u0003g2\u0011\"a\u0012\u0002\u0003\u0003E\t!!\u001e\t\r\u0001#B\u0011AAG\u0011%\t\t\u0004FA\u0001\n\u000b\n\u0019\u0004C\u0005\u0002\u0010R\t\t\u0011\"!\u0002\u0012\"I\u0011Q\u0013\u000b\u0002\u0002\u0013\u0005\u0015q\u0013\u0005\n\u0003G#\u0012\u0011!C\u0005\u0003K3A\u0001U\u0001C#\"AqK\u0007BK\u0002\u0013\u0005\u0001\f\u0003\u0005]5\tE\t\u0015!\u0003Z\u0011\u0015\u0001%\u0004\"\u0001^\u0011\u001d\u0001'$!A\u0005\u0002\u0005Dqa\u0019\u000e\u0012\u0002\u0013\u0005A\rC\u0004p5\u0005\u0005I\u0011\t9\t\u000feT\u0012\u0011!C\u0001u\"9aPGA\u0001\n\u0003y\b\"CA\u00065\u0005\u0005I\u0011IA\u0007\u0011%\tYBGA\u0001\n\u0003\ti\u0002C\u0005\u0002(i\t\t\u0011\"\u0011\u0002*!I\u0011Q\u0006\u000e\u0002\u0002\u0013\u0005\u0013q\u0006\u0005\n\u0003cQ\u0012\u0011!C!\u0003gA\u0011\"!\u000e\u001b\u0003\u0003%\t%a\u000e\b\u0013\u00055\u0016!!A\t\u0002\u0005=f\u0001\u0003)\u0002\u0003\u0003E\t!!-\t\r\u0001SC\u0011AA[\u0011%\t\tDKA\u0001\n\u000b\n\u0019\u0004C\u0005\u0002\u0010*\n\t\u0011\"!\u00028\"I\u0011Q\u0013\u0016\u0002\u0002\u0013\u0005\u00151\u0018\u0005\n\u0003GS\u0013\u0011!C\u0005\u0003K\u000baa\u0015;biV\u001c(B\u0001\u001a4\u0003\u0015\t7\r^8s\u0015\u0005!\u0014\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u00028\u00035\t\u0011G\u0001\u0004Ti\u0006$Xo]\n\u0003\u0003i\u0002\"a\u000f \u000e\u0003qR\u0011!P\u0001\u0006g\u000e\fG.Y\u0005\u0003\u007fq\u0012a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u00017'\r\u0019!h\u0011\t\u0003\t2s!!\u0012&\u000f\u0005\u0019KU\"A$\u000b\u0005!+\u0014A\u0002\u001fs_>$h(C\u0001>\u0013\tYE(A\u0004qC\u000e\\\u0017mZ3\n\u00055s%\u0001D*fe&\fG.\u001b>bE2,'BA&=S\r\u0019!\u0004\u0002\u0002\b\r\u0006LG.\u001e:f'\u0015Q\"H\u0015+D!\t\u00196!D\u0001\u0002!\tYT+\u0003\u0002Wy\t9\u0001K]8ek\u000e$\u0018!B2bkN,W#A-\u0011\u0005\u0011S\u0016BA.O\u0005%!\u0006N]8xC\ndW-\u0001\u0004dCV\u001cX\r\t\u000b\u0003=~\u0003\"a\u0015\u000e\t\u000b]k\u0002\u0019A-\u0002\t\r|\u0007/\u001f\u000b\u0003=\nDqa\u0016\u0010\u0011\u0002\u0003\u0007\u0011,\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003\u0015T#!\u00174,\u0003\u001d\u0004\"\u0001[7\u000e\u0003%T!A[6\u0002\u0013Ut7\r[3dW\u0016$'B\u00017=\u0003)\tgN\\8uCRLwN\\\u0005\u0003]&\u0014\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\t\u0011\u000f\u0005\u0002so6\t1O\u0003\u0002uk\u0006!A.\u00198h\u0015\u00051\u0018\u0001\u00026bm\u0006L!\u0001_:\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005Y\bCA\u001e}\u0013\tiHHA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u0002\u0002\u0005\u001d\u0001cA\u001e\u0002\u0004%\u0019\u0011Q\u0001\u001f\u0003\u0007\u0005s\u0017\u0010\u0003\u0005\u0002\n\t\n\t\u00111\u0001|\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011q\u0002\t\u0007\u0003#\t9\"!\u0001\u000e\u0005\u0005M!bAA\u000by\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005e\u00111\u0003\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002 \u0005\u0015\u0002cA\u001e\u0002\"%\u0019\u00111\u0005\u001f\u0003\u000f\t{w\u000e\\3b]\"I\u0011\u0011\u0002\u0013\u0002\u0002\u0003\u0007\u0011\u0011A\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\rF\u0002r\u0003WA\u0001\"!\u0003&\u0003\u0003\u0005\ra_\u0001\tQ\u0006\u001c\bnQ8eKR\t10\u0001\u0005u_N#(/\u001b8h)\u0005\t\u0018AB3rk\u0006d7\u000f\u0006\u0003\u0002 \u0005e\u0002\"CA\u0005Q\u0005\u0005\t\u0019AA\u0001Q\u001dQ\u0012QHA\"\u0003\u000b\u00022aOA \u0013\r\t\t\u0005\u0010\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!\u0001\u0002\b'V\u001c7-Z:t'\u0015!!H\u0015+D\u0003\u0019\u0019H/\u0019;vgV\u0011\u0011\u0011A\u0001\bgR\fG/^:!)\u0011\t\u0019&!\u0016\u0011\u0005M#\u0001bBA&\u000f\u0001\u0007\u0011\u0011\u0001\u000b\u0005\u0003'\nI\u0006C\u0005\u0002L!\u0001\n\u00111\u0001\u0002\u0002U\u0011\u0011Q\f\u0016\u0004\u0003\u00031G\u0003BA\u0001\u0003CB\u0001\"!\u0003\r\u0003\u0003\u0005\ra\u001f\u000b\u0005\u0003?\t)\u0007C\u0005\u0002\n9\t\t\u00111\u0001\u0002\u0002Q\u0019\u0011/!\u001b\t\u0011\u0005%q\"!AA\u0002m$B!a\b\u0002n!I\u0011\u0011\u0002\n\u0002\u0002\u0003\u0007\u0011\u0011\u0001\u0015\b\t\u0005u\u00121IA#\u0003\u001d\u0019VoY2fgN\u0004\"a\u0015\u000b\u0014\u000bQ\t9(a!\u0011\u0011\u0005e\u0014qPA\u0001\u0003'j!!a\u001f\u000b\u0007\u0005uD(A\u0004sk:$\u0018.\\3\n\t\u0005\u0005\u00151\u0010\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\f\u0004\u0003BAC\u0003\u0017k!!a\"\u000b\u0007\u0005%U/\u0001\u0002j_&\u0019Q*a\"\u0015\u0005\u0005M\u0014!B1qa2LH\u0003BA*\u0003'Cq!a\u0013\u0018\u0001\u0004\t\t!A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\u0005e\u0015q\u0014\t\u0006w\u0005m\u0015\u0011A\u0005\u0004\u0003;c$AB(qi&|g\u000eC\u0005\u0002\"b\t\t\u00111\u0001\u0002T\u0005\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005\u001d\u0006c\u0001:\u0002*&\u0019\u00111V:\u0003\r=\u0013'.Z2u\u0003\u001d1\u0015-\u001b7ve\u0016\u0004\"a\u0015\u0016\u0014\u000b)\n\u0019,a!\u0011\r\u0005e\u0014qP-_)\t\ty\u000bF\u0002_\u0003sCQaV\u0017A\u0002e#B!!0\u0002@B!1(a'Z\u0011!\t\tKLA\u0001\u0002\u0004q\u0006")
public final class Status {

    public static final class Failure
    implements Status,
    Product {
        private static final long serialVersionUID = 1L;
        private final Throwable cause;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Throwable cause() {
            return this.cause;
        }

        public Failure copy(Throwable cause) {
            return new Failure(cause);
        }

        public Throwable copy$default$1() {
            return this.cause();
        }

        @Override
        public String productPrefix() {
            return "Failure";
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
                    return this.cause();
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
            return x$1 instanceof Failure;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "cause";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof Failure)) return false;
            boolean bl = true;
            if (!bl) return false;
            Failure failure = (Failure)x$1;
            Throwable throwable = this.cause();
            Throwable throwable2 = failure.cause();
            if (throwable != null) {
                if (!throwable.equals(throwable2)) return false;
                return true;
            }
            if (throwable2 == null) return true;
            return false;
        }

        public Failure(Throwable cause) {
            this.cause = cause;
            Product.$init$(this);
        }
    }

    public static interface Status
    extends Serializable {
    }

    public static final class Success
    implements Status,
    Product {
        private static final long serialVersionUID = 1L;
        private final Object status;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Object status() {
            return this.status;
        }

        public Success copy(Object status) {
            return new Success(status);
        }

        public Object copy$default$1() {
            return this.status();
        }

        @Override
        public String productPrefix() {
            return "Success";
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
                    return this.status();
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
            return x$1 instanceof Success;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "status";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof Success)) return false;
            boolean bl = true;
            if (!bl) return false;
            Success success = (Success)x$1;
            if (!BoxesRunTime.equals(this.status(), success.status())) return false;
            return true;
        }

        public Success(Object status) {
            this.status = status;
            Product.$init$(this);
        }
    }
}

