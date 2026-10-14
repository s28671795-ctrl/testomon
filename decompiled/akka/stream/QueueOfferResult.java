/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.annotation.DoNotInherit;
import akka.stream.QueueCompletionResult;
import akka.stream.QueueOfferResult$;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Eh!B\"E\u0003CI\u0005\"\u0002)\u0001\t\u0003\t\u0006\"\u0002+\u0001\r\u0003)vABAx\t\"\u0005QLB\u0003D\t\"\u00051\fC\u0003Q\t\u0011\u0005AlB\u0003_\t!\u0005uLB\u0003b\t!\u0005%\rC\u0003Q\u000f\u0011\u0005!\u000fC\u0003U\u000f\u0011\u0005S\u000bC\u0004t\u000f\u0005\u0005I\u0011\t;\t\u000fu<\u0011\u0011!C\u0001}\"I\u0011QA\u0004\u0002\u0002\u0013\u0005\u0011q\u0001\u0005\n\u0003'9\u0011\u0011!C!\u0003+A\u0011\"a\t\b\u0003\u0003%\t!!\n\t\u0013\u0005%r!!A\u0005B\u0005-\u0002\"CA\u0017\u000f\u0005\u0005I\u0011IA\u0018\u0011%\t\tdBA\u0001\n\u0013\t\u0019\u0004C\u0004\u0002<\u0011!\t!!\u0010\b\u000f\u0005}B\u0001#!\u0002B\u00191!\f\u0002EA\u0003/Da\u0001\u0015\u000b\u0005\u0002\u0005e\u0007\"\u0002+\u0015\t\u0003*\u0006bB:\u0015\u0003\u0003%\t\u0005\u001e\u0005\b{R\t\t\u0011\"\u0001\u007f\u0011%\t)\u0001FA\u0001\n\u0003\tY\u000eC\u0005\u0002\u0014Q\t\t\u0011\"\u0011\u0002\u0016!I\u00111\u0005\u000b\u0002\u0002\u0013\u0005\u0011q\u001c\u0005\n\u0003S!\u0012\u0011!C!\u0003WA\u0011\"!\f\u0015\u0003\u0003%\t%a\f\t\u0013\u0005EB#!A\u0005\n\u0005M\u0002bBA\"\t\u0011\u0005\u0011Q\b\u0004\u0007\u0003\u000b\"!)a\u0012\t\u0015\u0005=\u0003E!f\u0001\n\u0003\t\t\u0006\u0003\u0006\u0002Z\u0001\u0012\t\u0012)A\u0005\u0003'Ba\u0001\u0015\u0011\u0005\u0002\u0005m\u0003\"\u0002+!\t\u0003*\u0006\"CA1A\u0005\u0005I\u0011AA2\u0011%\t9\u0007II\u0001\n\u0003\tI\u0007C\u0004tA\u0005\u0005I\u0011\t;\t\u000fu\u0004\u0013\u0011!C\u0001}\"I\u0011Q\u0001\u0011\u0002\u0002\u0013\u0005\u0011q\u0010\u0005\n\u0003'\u0001\u0013\u0011!C!\u0003+A\u0011\"a\t!\u0003\u0003%\t!a!\t\u0013\u0005\u001d\u0005%!A\u0005B\u0005%\u0005\"CA\u0015A\u0005\u0005I\u0011IA\u0016\u0011%\ti\u0003IA\u0001\n\u0003\ny\u0003C\u0005\u0002\u000e\u0002\n\t\u0011\"\u0011\u0002\u0010\u001eI\u00111\u0013\u0003\u0002\u0002#\u0005\u0011Q\u0013\u0004\n\u0003\u000b\"\u0011\u0011!E\u0001\u0003/Ca\u0001U\u0019\u0005\u0002\u0005=\u0006\"CA\u0017c\u0005\u0005IQIA\u0018\u0011%\t\t,MA\u0001\n\u0003\u000b\u0019\fC\u0005\u00028F\n\t\u0011\"!\u0002:\"I\u0011\u0011G\u0019\u0002\u0002\u0013%\u00111G\u0004\b\u0003\u000b$\u0001\u0012QAd\r\u001d\tI\r\u0002EA\u0003\u0017Da\u0001\u0015\u001d\u0005\u0002\u00055\u0007\"\u0002+9\t\u0003*\u0006bB:9\u0003\u0003%\t\u0005\u001e\u0005\b{b\n\t\u0011\"\u0001\u007f\u0011%\t)\u0001OA\u0001\n\u0003\ty\rC\u0005\u0002\u0014a\n\t\u0011\"\u0011\u0002\u0016!I\u00111\u0005\u001d\u0002\u0002\u0013\u0005\u00111\u001b\u0005\n\u0003SA\u0014\u0011!C!\u0003WA\u0011\"!\f9\u0003\u0003%\t%a\f\t\u0013\u0005E\u0002(!A\u0005\n\u0005M\"\u0001E)vKV,wJ\u001a4feJ+7/\u001e7u\u0015\t)e)\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u000f\u0006!\u0011m[6b\u0007\u0001\u0019\"\u0001\u0001&\u0011\u0005-sU\"\u0001'\u000b\u00035\u000bQa]2bY\u0006L!a\u0014'\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t!\u000b\u0005\u0002T\u00015\tA)\u0001\u0006jg\u0016s\u0017/^3vK\u0012,\u0012A\u0016\t\u0003\u0017^K!\u0001\u0017'\u0003\u000f\t{w\u000e\\3b]&*\u0001\u0001F\u0004\u0002L\t9AI]8qa\u0016$7C\u0001\u0003K)\u0005i\u0006CA*\u0005\u0003!)e.];fk\u0016$\u0007C\u00011\b\u001b\u0005!!\u0001C#ocV,W/\u001a3\u0014\t\u001d\u00116M\u001a\t\u0003\u0017\u0012L!!\u001a'\u0003\u000fA\u0013x\u000eZ;diB\u0011qm\u001c\b\u0003Q6t!!\u001b7\u000e\u0003)T!a\u001b%\u0002\rq\u0012xn\u001c;?\u0013\u0005i\u0015B\u00018M\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001]9\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u00059dE#A0\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005)\bC\u0001<|\u001b\u00059(B\u0001=z\u0003\u0011a\u0017M\\4\u000b\u0003i\fAA[1wC&\u0011Ap\u001e\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003}\u00042aSA\u0001\u0013\r\t\u0019\u0001\u0014\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003\u0013\ty\u0001E\u0002L\u0003\u0017I1!!\u0004M\u0005\r\te.\u001f\u0005\t\u0003#a\u0011\u0011!a\u0001\u007f\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!a\u0006\u0011\r\u0005e\u0011qDA\u0005\u001b\t\tYBC\u0002\u0002\u001e1\u000b!bY8mY\u0016\u001cG/[8o\u0013\u0011\t\t#a\u0007\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0004-\u0006\u001d\u0002\"CA\t\u001d\u0005\u0005\t\u0019AA\u0005\u0003!A\u0017m\u001d5D_\u0012,G#A@\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012!^\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003k\u00012A^A\u001c\u0013\r\tId\u001e\u0002\u0007\u001f\nTWm\u0019;\u0002\u0011\u0015t\u0017/^3vK\u0012,\u0012AU\u0001\b\tJ|\u0007\u000f]3e!\t\u0001G#A\u0004ee>\u0004\b/\u001a3\u0003\u000f\u0019\u000b\u0017\u000e\\;sKN)\u0001%!\u0013dMB\u00191+a\u0013\n\u0007\u00055CIA\u000bRk\u0016,XmQ8na2,G/[8o%\u0016\u001cX\u000f\u001c;\u0002\u000b\r\fWo]3\u0016\u0005\u0005M\u0003cA4\u0002V%\u0019\u0011qK9\u0003\u0013QC'o\\<bE2,\u0017AB2bkN,\u0007\u0005\u0006\u0003\u0002^\u0005}\u0003C\u00011!\u0011\u001d\tye\ta\u0001\u0003'\nAaY8qsR!\u0011QLA3\u0011%\ty%\nI\u0001\u0002\u0004\t\u0019&\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\u0005-$\u0006BA*\u0003[Z#!a\u001c\u0011\t\u0005E\u00141P\u0007\u0003\u0003gRA!!\u001e\u0002x\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003sb\u0015AC1o]>$\u0018\r^5p]&!\u0011QPA:\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u000b\u0005\u0003\u0013\t\t\t\u0003\u0005\u0002\u0012%\n\t\u00111\u0001\u0000)\r1\u0016Q\u0011\u0005\n\u0003#Y\u0013\u0011!a\u0001\u0003\u0013\t!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR\u0019Q/a#\t\u0011\u0005EA&!AA\u0002}\fa!Z9vC2\u001cHc\u0001,\u0002\u0012\"I\u0011\u0011C\u0018\u0002\u0002\u0003\u0007\u0011\u0011B\u0001\b\r\u0006LG.\u001e:f!\t\u0001\u0017gE\u00032\u00033\u000b)\u000b\u0005\u0005\u0002\u001c\u0006\u0005\u00161KA/\u001b\t\tiJC\u0002\u0002 2\u000bqA];oi&lW-\u0003\u0003\u0002$\u0006u%!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ocA!\u0011qUAW\u001b\t\tIKC\u0002\u0002,f\f!![8\n\u0007A\fI\u000b\u0006\u0002\u0002\u0016\u0006)\u0011\r\u001d9msR!\u0011QLA[\u0011\u001d\ty\u0005\u000ea\u0001\u0003'\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002<\u0006\u0005\u0007#B&\u0002>\u0006M\u0013bAA`\u0019\n1q\n\u001d;j_:D\u0011\"a16\u0003\u0003\u0005\r!!\u0018\u0002\u0007a$\u0003'A\u0006Rk\u0016,Xm\u00117pg\u0016$\u0007C\u000119\u0005-\tV/Z;f\u00072|7/\u001a3\u0014\u000ba\nIe\u00194\u0015\u0005\u0005\u001dG\u0003BA\u0005\u0003#D\u0001\"!\u0005>\u0003\u0003\u0005\ra \u000b\u0004-\u0006U\u0007\"CA\t\u007f\u0005\u0005\t\u0019AA\u0005'\u0011!\"k\u00194\u0015\u0005\u0005\u0005C\u0003BA\u0005\u0003;D\u0001\"!\u0005\u001a\u0003\u0003\u0005\ra \u000b\u0004-\u0006\u0005\b\"CA\t7\u0005\u0005\t\u0019AA\u0005Q\r\u0001\u0011Q\u001d\t\u0005\u0003O\fY/\u0004\u0002\u0002j*\u0019\u0011\u0011\u0010$\n\t\u00055\u0018\u0011\u001e\u0002\r\t>tu\u000e^%oQ\u0016\u0014\u0018\u000e^\u0001\u0011#V,W/Z(gM\u0016\u0014(+Z:vYR\u0004")
@DoNotInherit
public abstract class QueueOfferResult {
    public static QueueOfferResult dropped() {
        return QueueOfferResult$.MODULE$.dropped();
    }

    public static QueueOfferResult enqueued() {
        return QueueOfferResult$.MODULE$.enqueued();
    }

    public abstract boolean isEnqueued();

    public static final class Failure
    extends QueueCompletionResult
    implements Product,
    Serializable {
        private final Throwable cause;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Throwable cause() {
            return this.cause;
        }

        @Override
        public boolean isEnqueued() {
            return false;
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
}

