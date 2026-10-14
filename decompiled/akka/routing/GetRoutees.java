/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.GetRoutees$;
import akka.routing.RouterManagementMesssage;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000514QAD\b\u0002\u0002QAQa\b\u0001\u0005\u0002\u0001:Q\u0001K\b\t\u0002&2QAD\b\t\u0002*BQaH\u0002\u0005\u0002iBQaO\u0002\u0005\u0002qBq!P\u0002\u0002\u0002\u0013\u0005c\bC\u0004H\u0007\u0005\u0005I\u0011\u0001%\t\u000f1\u001b\u0011\u0011!C\u0001\u001b\"91kAA\u0001\n\u0003\"\u0006bB.\u0004\u0003\u0003%\t\u0001\u0018\u0005\bC\u000e\t\t\u0011\"\u0011c\u0011\u001d\u00197!!A\u0005B\u0011Dq!Z\u0002\u0002\u0002\u0013%aM\u0001\u0006HKR\u0014v.\u001e;fKNT!\u0001E\t\u0002\u000fI|W\u000f^5oO*\t!#\u0001\u0003bW.\f7\u0001A\n\u0004\u0001UY\u0002C\u0001\f\u001a\u001b\u00059\"\"\u0001\r\u0002\u000bM\u001c\u0017\r\\1\n\u0005i9\"AB!osJ+g\r\u0005\u0002\u001d;5\tq\"\u0003\u0002\u001f\u001f\tA\"k\\;uKJl\u0015M\\1hK6,g\u000e^'fgN\u001c\u0018mZ3\u0002\rqJg.\u001b;?)\u0005\t\u0003C\u0001\u000f\u0001Q\u0011\u00011EJ\u0014\u0011\u0005Y!\u0013BA\u0013\u0018\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0002\u0003)9U\r\u001e*pkR,Wm\u001d\t\u00039\r\u0019BaA\u0011,]A\u0011a\u0003L\u0005\u0003[]\u0011q\u0001\u0015:pIV\u001cG\u000f\u0005\u00020o9\u0011\u0001'\u000e\b\u0003cQj\u0011A\r\u0006\u0003gM\ta\u0001\u0010:p_Rt\u0014\"\u0001\r\n\u0005Y:\u0012a\u00029bG.\fw-Z\u0005\u0003qe\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!AN\f\u0015\u0003%\n1bZ3u\u0013:\u001cH/\u00198dKV\t\u0011&A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002\u007fA\u0011\u0001)R\u0007\u0002\u0003*\u0011!iQ\u0001\u0005Y\u0006twMC\u0001E\u0003\u0011Q\u0017M^1\n\u0005\u0019\u000b%AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001J!\t1\"*\u0003\u0002L/\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011a*\u0015\t\u0003-=K!\u0001U\f\u0003\u0007\u0005s\u0017\u0010C\u0004S\u0011\u0005\u0005\t\u0019A%\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005)\u0006c\u0001,Z\u001d6\tqK\u0003\u0002Y/\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005i;&\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$\"!\u00181\u0011\u0005Yq\u0016BA0\u0018\u0005\u001d\u0011un\u001c7fC:DqA\u0015\u0006\u0002\u0002\u0003\u0007a*\u0001\u0005iCND7i\u001c3f)\u0005I\u0015\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003}\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\u0012a\u001a\t\u0003\u0001\"L!![!\u0003\r=\u0013'.Z2uQ\u0011\u00191EJ\u0014)\t\t\u0019ce\n")
public abstract class GetRoutees
implements RouterManagementMesssage {
    private static final long serialVersionUID = 1L;

    public static boolean canEqual(Object x$1) {
        return GetRoutees$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return GetRoutees$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return GetRoutees$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return GetRoutees$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return GetRoutees$.MODULE$.productPrefix();
    }

    public static GetRoutees$ getInstance() {
        return GetRoutees$.MODULE$.getInstance();
    }

    public static Iterator<String> productElementNames() {
        return GetRoutees$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return GetRoutees$.MODULE$.productElementName(n);
    }
}

