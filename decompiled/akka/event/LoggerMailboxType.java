/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.MailboxType;
import akka.dispatch.MessageQueue;
import akka.dispatch.ProducesMessageQueue;
import akka.event.LoggerMailbox;
import com.typesafe.config.Config;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y3Q!\u0002\u0004\u0001\u0011)A\u0001B\b\u0001\u0003\u0002\u0003\u0006I\u0001\t\u0005\tc\u0001\u0011\t\u0011)A\u0005e!)A\b\u0001C\u0001{!)1\t\u0001C!\t\n\tBj\\4hKJl\u0015-\u001b7c_b$\u0016\u0010]3\u000b\u0005\u001dA\u0011!B3wK:$(\"A\u0005\u0002\t\u0005\\7.Y\n\u0005\u0001-\tr\u0003\u0005\u0002\r\u001f5\tQBC\u0001\u000f\u0003\u0015\u00198-\u00197b\u0013\t\u0001RB\u0001\u0004B]f\u0014VM\u001a\t\u0003%Ui\u0011a\u0005\u0006\u0003)!\t\u0001\u0002Z5ta\u0006$8\r[\u0005\u0003-M\u00111\"T1jY\n|\u0007\u0010V=qKB\u0019!\u0003\u0007\u000e\n\u0005e\u0019\"\u0001\u0006)s_\u0012,8-Z:NKN\u001c\u0018mZ3Rk\u0016,X\r\u0005\u0002\u001c95\ta!\u0003\u0002\u001e\r\tiAj\\4hKJl\u0015-\u001b7c_b\f\u0001b]3ui&twm]\u0002\u0001!\t\tsE\u0004\u0002#K5\t1E\u0003\u0002%\u0011\u0005)\u0011m\u0019;pe&\u0011aeI\u0001\f\u0003\u000e$xN]*zgR,W.\u0003\u0002)S\tA1+\u001a;uS:<7O\u0003\u0002'G!\u0012\u0011a\u000b\t\u0003Y=j\u0011!\f\u0006\u0003]!\tA!\u001e;jY&\u0011\u0001'\f\u0002\u0007k:,8/\u001a3\u0002\r\r|gNZ5h!\t\u0019\u0014(D\u00015\u0015\t\tTG\u0003\u00027o\u0005AA/\u001f9fg\u00064WMC\u00019\u0003\r\u0019w.\\\u0005\u0003uQ\u0012aaQ8oM&<\u0007F\u0001\u0002,\u0003\u0019a\u0014N\\5u}Q\u0019ahP!\u0011\u0005m\u0001\u0001\"\u0002\u0010\u0004\u0001\u0004\u0001\u0003FA ,\u0011\u0015\t4\u00011\u00013Q\t\t5&\u0001\u0004de\u0016\fG/\u001a\u000b\u0004\u000b\"\u0003\u0006C\u0001\nG\u0013\t95C\u0001\u0007NKN\u001c\u0018mZ3Rk\u0016,X\rC\u0003J\t\u0001\u0007!*A\u0003po:,'\u000fE\u0002\r\u00176K!\u0001T\u0007\u0003\r=\u0003H/[8o!\t\u0011c*\u0003\u0002PG\tA\u0011i\u0019;peJ+g\rC\u0003R\t\u0001\u0007!+\u0001\u0004tsN$X-\u001c\t\u0004\u0019-\u001b\u0006C\u0001\u0012U\u0013\t)6EA\u0006BGR|'oU=ti\u0016l\u0007")
public class LoggerMailboxType
implements MailboxType,
ProducesMessageQueue<LoggerMailbox> {
    @Override
    public MessageQueue create(Option<ActorRef> owner, Option<ActorSystem> system) {
        Tuple2<Option<ActorRef>, Option<ActorSystem>> tuple2 = new Tuple2<Option<ActorRef>, Option<ActorSystem>>(owner, system);
        if (tuple2 != null) {
            Option<ActorRef> option = tuple2._1();
            Option<ActorSystem> option2 = tuple2._2();
            if (option instanceof Some) {
                Some some = (Some)option;
                ActorRef o = (ActorRef)some.value();
                if (option2 instanceof Some) {
                    Some some2 = (Some)option2;
                    ActorSystem s2 = (ActorSystem)some2.value();
                    return new LoggerMailbox(o, s2);
                }
            }
        }
        throw new IllegalArgumentException("no mailbox owner or system given");
    }

    public LoggerMailboxType(ActorSystem.Settings settings, Config config) {
    }
}

