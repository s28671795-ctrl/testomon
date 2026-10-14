/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.MessageQueue;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00192qAA\u0002\u0011\u0002G\u0005\u0001\u0002C\u0003\u0010\u0001\u0019\u0005\u0001CA\u0006NC&d'm\u001c=UsB,'B\u0001\u0003\u0006\u0003!!\u0017n\u001d9bi\u000eD'\"\u0001\u0004\u0002\t\u0005\\7.Y\u0002\u0001'\t\u0001\u0011\u0002\u0005\u0002\u000b\u001b5\t1BC\u0001\r\u0003\u0015\u00198-\u00197b\u0013\tq1B\u0001\u0004B]f\u0014VMZ\u0001\u0007GJ,\u0017\r^3\u0015\u0007E)\u0002\u0005\u0005\u0002\u0013'5\t1!\u0003\u0002\u0015\u0007\taQ*Z:tC\u001e,\u0017+^3vK\")a#\u0001a\u0001/\u0005)qn\u001e8feB\u0019!\u0002\u0007\u000e\n\u0005eY!AB(qi&|g\u000e\u0005\u0002\u001c=5\tAD\u0003\u0002\u001e\u000b\u0005)\u0011m\u0019;pe&\u0011q\u0004\b\u0002\t\u0003\u000e$xN\u001d*fM\")\u0011%\u0001a\u0001E\u000511/_:uK6\u00042A\u0003\r$!\tYB%\u0003\u0002&9\tY\u0011i\u0019;peNK8\u000f^3n\u0001")
public interface MailboxType {
    public MessageQueue create(Option<ActorRef> var1, Option<ActorSystem> var2);
}

