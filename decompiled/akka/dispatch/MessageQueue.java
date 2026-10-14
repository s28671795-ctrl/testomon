/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.dispatch.Envelope;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e2qAB\u0004\u0011\u0002G\u0005A\u0002C\u0003\u0014\u0001\u0019\u0005A\u0003C\u0003'\u0001\u0019\u0005q\u0005C\u0003)\u0001\u0019\u0005\u0011\u0006C\u0003.\u0001\u0019\u0005a\u0006C\u00033\u0001\u0019\u00051G\u0001\u0007NKN\u001c\u0018mZ3Rk\u0016,XM\u0003\u0002\t\u0013\u0005AA-[:qCR\u001c\u0007NC\u0001\u000b\u0003\u0011\t7n[1\u0004\u0001M\u0011\u0001!\u0004\t\u0003\u001dEi\u0011a\u0004\u0006\u0002!\u0005)1oY1mC&\u0011!c\u0004\u0002\u0007\u0003:L(+\u001a4\u0002\u000f\u0015t\u0017/^3vKR\u0019Q\u0003\u0007\u0011\u0011\u000591\u0012BA\f\u0010\u0005\u0011)f.\u001b;\t\u000be\t\u0001\u0019\u0001\u000e\u0002\u0011I,7-Z5wKJ\u0004\"a\u0007\u0010\u000e\u0003qQ!!H\u0005\u0002\u000b\u0005\u001cGo\u001c:\n\u0005}a\"\u0001C!di>\u0014(+\u001a4\t\u000b\u0005\n\u0001\u0019\u0001\u0012\u0002\r!\fg\u000e\u001a7f!\t\u0019C%D\u0001\b\u0013\t)sA\u0001\u0005F]Z,Gn\u001c9f\u0003\u001d!W-];fk\u0016$\u0012AI\u0001\u0011]Vl'-\u001a:PM6+7o]1hKN,\u0012A\u000b\t\u0003\u001d-J!\u0001L\b\u0003\u0007%sG/A\u0006iCNlUm]:bO\u0016\u001cX#A\u0018\u0011\u00059\u0001\u0014BA\u0019\u0010\u0005\u001d\u0011un\u001c7fC:\fqa\u00197fC:,\u0006\u000fF\u0002\u0016iYBQ!N\u0003A\u0002i\tQa\\<oKJDQaN\u0003A\u0002a\n1\u0002Z3bI2+G\u000f^3sgB\u00111\u0005\u0001")
public interface MessageQueue {
    public void enqueue(ActorRef var1, Envelope var2);

    public Envelope dequeue();

    public int numberOfMessages();

    public boolean hasMessages();

    public void cleanUp(ActorRef var1, MessageQueue var2);
}

