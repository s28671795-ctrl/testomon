/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.dispatch.Mailbox;
import akka.dispatch.sysmsg.LatestFirstSystemMessageList$;
import akka.dispatch.sysmsg.NoMessage$;
import akka.dispatch.sysmsg.SystemMessage;
import scala.Predef$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005!3\u0011\"\u0002\u0004\u0011\u0002\u0007\u0005\u0001B\u0003!\t\u000bE\u0001A\u0011A\n\t\u000b]\u0001AQ\u0001\r\t\u000bA\u0002AQA\u0019\t\u000bm\u0002A\u0011\u0001\u001f\u00033\u0011+g-Y;miNK8\u000f^3n\u001b\u0016\u001c8/Y4f#V,W/\u001a\u0006\u0003\u000f!\t\u0001\u0002Z5ta\u0006$8\r\u001b\u0006\u0002\u0013\u0005!\u0011m[6b'\t\u00011\u0002\u0005\u0002\r\u001f5\tQBC\u0001\u000f\u0003\u0015\u00198-\u00197b\u0013\t\u0001RB\u0001\u0004B]f\u0014VMZ\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0004\u0001Q\tA\u0003\u0005\u0002\r+%\u0011a#\u0004\u0002\u0005+:LG/A\u0007tsN$X-\\#ocV,W/\u001a\u000b\u0004)e\t\u0003\"\u0002\u000e\u0003\u0001\u0004Y\u0012\u0001\u0003:fG\u0016Lg/\u001a:\u0011\u0005qyR\"A\u000f\u000b\u0005yA\u0011!B1di>\u0014\u0018B\u0001\u0011\u001e\u0005!\t5\r^8s%\u00164\u0007\"\u0002\u0012\u0003\u0001\u0004\u0019\u0013aB7fgN\fw-\u001a\t\u0003I\u001dj\u0011!\n\u0006\u0003M\u0019\taa]=t[N<\u0017B\u0001\u0015&\u00055\u0019\u0016p\u001d;f[6+7o]1hK\"\u0012!A\u000b\t\u0003W9j\u0011\u0001\f\u0006\u0003[5\t!\"\u00198o_R\fG/[8o\u0013\tyCFA\u0004uC&d'/Z2\u0002\u0017ML8\u000f^3n\tJ\f\u0017N\u001c\u000b\u0003eU\u0002\"\u0001J\u001a\n\u0005Q*#AH#be2LWm\u001d;GSJ\u001cHoU=ti\u0016lW*Z:tC\u001e,G*[:u\u0011\u001514\u00011\u00018\u0003-qWm^\"p]R,g\u000e^:\u0011\u0005\u0011B\u0014BA\u001d&\u0005qa\u0015\r^3ti\u001aK'o\u001d;TsN$X-\\'fgN\fw-\u001a'jgRD#a\u0001\u0016\u0002#!\f7oU=ti\u0016lW*Z:tC\u001e,7/F\u0001>!\taa(\u0003\u0002@\u001b\t9!i\\8mK\u0006t'cA!D\u000b\u001a!!\t\u0001\u0001A\u00051a$/\u001a4j]\u0016lWM\u001c;?!\t!\u0005!D\u0001\u0007!\t!e)\u0003\u0002H\r\t9Q*Y5mE>D\b")
public interface DefaultSystemMessageQueue {
    public static /* synthetic */ void systemEnqueue$(DefaultSystemMessageQueue $this, ActorRef receiver, SystemMessage message) {
        $this.systemEnqueue(receiver, message);
    }

    default public void systemEnqueue(ActorRef receiver, SystemMessage message) {
        block4: {
            BoxedUnit boxedUnit;
            while (true) {
                SystemMessage currentList;
                Predef$.MODULE$.assert(message.unlinked());
                SystemMessage systemMessage = currentList = ((Mailbox)this_).systemQueueGet();
                NoMessage$ noMessage$ = NoMessage$.MODULE$;
                if (!(systemMessage != null ? !systemMessage.equals(noMessage$) : noMessage$ != null)) {
                    if (((Mailbox)this_).actor() != null) {
                        ((Mailbox)this_).actor().dispatcher().mailboxes().deadLetterMailbox().systemEnqueue(receiver, message);
                        boxedUnit = BoxedUnit.UNIT;
                    } else {
                        boxedUnit = BoxedUnit.UNIT;
                    }
                    break block4;
                }
                SystemMessage systemMessage2 = message;
                if (((Mailbox)this_).systemQueuePut(currentList, LatestFirstSystemMessageList$.MODULE$.$colon$colon$extension(currentList, systemMessage2))) break;
                message.unlink();
                Mailbox this_ = this_;
            }
            boxedUnit = BoxedUnit.UNIT;
        }
    }

    public static /* synthetic */ SystemMessage systemDrain$(DefaultSystemMessageQueue $this, SystemMessage newContents) {
        return $this.systemDrain(newContents);
    }

    default public SystemMessage systemDrain(SystemMessage newContents) {
        while (true) {
            SystemMessage currentList;
            SystemMessage systemMessage = currentList = ((Mailbox)this_).systemQueueGet();
            NoMessage$ noMessage$ = NoMessage$.MODULE$;
            if (!(systemMessage != null ? !systemMessage.equals(noMessage$) : noMessage$ != null)) {
                return null;
            }
            if (((Mailbox)this_).systemQueuePut(currentList, newContents)) {
                return LatestFirstSystemMessageList$.MODULE$.reverse$extension(currentList);
            }
            Mailbox this_ = this_;
        }
    }

    public static /* synthetic */ boolean hasSystemMessages$(DefaultSystemMessageQueue $this) {
        return $this.hasSystemMessages();
    }

    default public boolean hasSystemMessages() {
        SystemMessage systemMessage = ((Mailbox)((Object)this)).systemQueueGet();
        return !(systemMessage == null ? true : NoMessage$.MODULE$.equals(systemMessage));
    }

    public static void $init$(DefaultSystemMessageQueue $this) {
    }
}

