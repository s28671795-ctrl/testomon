/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.AutoReceivedMessage;
import akka.actor.Kill$;
import akka.actor.PossiblyHarmful;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000594QAD\b\u0002\u0002QAQA\t\u0001\u0005\u0002\r:Q!J\b\t\u0002\u001a2QAD\b\t\u0002\u001eBQAI\u0002\u0005\u0002]BQ\u0001O\u0002\u0005\u0002eBqAO\u0002\u0002\u0002\u0013\u00053\bC\u0004E\u0007\u0005\u0005I\u0011A#\t\u000f%\u001b\u0011\u0011!C\u0001\u0015\"9\u0001kAA\u0001\n\u0003\n\u0006b\u0002-\u0004\u0003\u0003%\t!\u0017\u0005\b=\u000e\t\t\u0011\"\u0011`\u0011\u001d\u00017!!A\u0005B\u0005DqAY\u0002\u0002\u0002\u0013%1M\u0001\u0003LS2d'B\u0001\t\u0012\u0003\u0015\t7\r^8s\u0015\u0005\u0011\u0012\u0001B1lW\u0006\u001c\u0001a\u0005\u0003\u0001+my\u0002C\u0001\f\u001a\u001b\u00059\"\"\u0001\r\u0002\u000bM\u001c\u0017\r\\1\n\u0005i9\"AB!osJ+g\r\u0005\u0002\u001d;5\tq\"\u0003\u0002\u001f\u001f\t\u0019\u0012)\u001e;p%\u0016\u001cW-\u001b<fI6+7o]1hKB\u0011A\u0004I\u0005\u0003C=\u0011q\u0002U8tg&\u0014G.\u001f%be64W\u000f\\\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003\u0011\u0002\"\u0001\b\u0001\u0002\t-KG\u000e\u001c\t\u00039\r\u0019Ba\u0001\u0013)WA\u0011a#K\u0005\u0003U]\u0011q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002-i9\u0011QF\r\b\u0003]Ej\u0011a\f\u0006\u0003aM\ta\u0001\u0010:p_Rt\u0014\"\u0001\r\n\u0005M:\u0012a\u00029bG.\fw-Z\u0005\u0003kY\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!aM\f\u0015\u0003\u0019\n1bZ3u\u0013:\u001cH/\u00198dKV\ta%A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002yA\u0011QHQ\u0007\u0002})\u0011q\bQ\u0001\u0005Y\u0006twMC\u0001B\u0003\u0011Q\u0017M^1\n\u0005\rs$AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001G!\t1r)\u0003\u0002I/\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u00111J\u0014\t\u0003-1K!!T\f\u0003\u0007\u0005s\u0017\u0010C\u0004P\u0011\u0005\u0005\t\u0019\u0001$\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005\u0011\u0006cA*W\u00176\tAK\u0003\u0002V/\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005]#&\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$\"AW/\u0011\u0005YY\u0016B\u0001/\u0018\u0005\u001d\u0011un\u001c7fC:Dqa\u0014\u0006\u0002\u0002\u0003\u00071*\u0001\u0005iCND7i\u001c3f)\u00051\u0015\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003q\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\u0012\u0001\u001a\t\u0003{\u0015L!A\u001a \u0003\r=\u0013'.Z2uQ\u0011\u0019\u0001n\u001b7\u0011\u0005YI\u0017B\u00016\u0018\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0002Q\u0011\u0011\u0001n\u001b7")
public abstract class Kill
implements AutoReceivedMessage,
PossiblyHarmful {
    public static boolean canEqual(Object x$1) {
        return Kill$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return Kill$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return Kill$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return Kill$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return Kill$.MODULE$.productPrefix();
    }

    public static Kill$ getInstance() {
        return Kill$.MODULE$.getInstance();
    }

    public static Iterator<String> productElementNames() {
        return Kill$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return Kill$.MODULE$.productElementName(n);
    }
}

