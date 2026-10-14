/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.Envelope;
import akka.dispatch.MessageQueue;
import akka.dispatch.QueueBasedMessageQueue;
import akka.dispatch.UnboundedMailbox;
import akka.event.LoggerMessageQueueSemantics;
import akka.event.Logging;
import akka.event.Logging$;
import akka.event.Logging$LogLevel$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}2Q!\u0002\u0004\u0001\u0011)A\u0001\"\u0007\u0001\u0003\u0002\u0003\u0006Ia\u0007\u0005\tQ\u0001\u0011\t\u0011)A\u0005S!)A\u0006\u0001C\u0001[!)!\u0007\u0001C!g\tiAj\\4hKJl\u0015-\u001b7c_bT!a\u0002\u0005\u0002\u000b\u00154XM\u001c;\u000b\u0003%\tA!Y6lCN\u0019\u0001aC\u000b\u0011\u00051\u0011bBA\u0007\u0011\u001b\u0005q!BA\b\t\u0003!!\u0017n\u001d9bi\u000eD\u0017BA\t\u000f\u0003A)fNY8v]\u0012,G-T1jY\n|\u00070\u0003\u0002\u0014)\taQ*Z:tC\u001e,\u0017+^3vK*\u0011\u0011C\u0004\t\u0003-]i\u0011AB\u0005\u00031\u0019\u00111\u0004T8hO\u0016\u0014X*Z:tC\u001e,\u0017+^3vKN+W.\u00198uS\u000e\u001c\u0018!B8x]\u0016\u00148\u0001\u0001\t\u00039}i\u0011!\b\u0006\u0003=!\tQ!Y2u_JL!\u0001I\u000f\u0003\u0011\u0005\u001bGo\u001c:SK\u001aD#!\u0001\u0012\u0011\u0005\r2S\"\u0001\u0013\u000b\u0005\u0015B\u0011\u0001B;uS2L!a\n\u0013\u0003\rUtWo]3e\u0003\u0019\u0019\u0018p\u001d;f[B\u0011ADK\u0005\u0003Wu\u00111\"Q2u_J\u001c\u0016p\u001d;f[\u00061A(\u001b8jiz\"2AL\u00182!\t1\u0002\u0001C\u0003\u001a\u0007\u0001\u00071\u0004\u000b\u00020E!)\u0001f\u0001a\u0001S\u000591\r\\3b]V\u0003Hc\u0001\u001b;wA\u0011Q\u0007O\u0007\u0002m)\tq'A\u0003tG\u0006d\u0017-\u0003\u0002:m\t!QK\\5u\u0011\u0015IB\u00011\u0001\u001c\u0011\u0015aD\u00011\u0001>\u0003-!W-\u00193MKR$XM]:\u0011\u00055q\u0014BA\n\u000f\u0001")
public class LoggerMailbox
extends UnboundedMailbox.MessageQueue
implements LoggerMessageQueueSemantics {
    private final ActorSystem system;

    @Override
    public void cleanUp(ActorRef owner, MessageQueue deadLetters) {
        if (this.hasMessages()) {
            int logLevel = this.system.eventStream().logLevel();
            Envelope envelope = this.dequeue();
            boolean loggingEnabled = Logging$.MODULE$.AllLogLevels().contains(new Logging.LogLevel(logLevel));
            while (envelope != null) {
                if (loggingEnabled) {
                    Logging.LogEvent logEvent;
                    Object object = envelope.message();
                    if (object instanceof Logging.LogEvent && Logging$LogLevel$.MODULE$.$less$eq$extension((logEvent = (Logging.LogEvent)object).level(), logLevel)) {
                        Logging$.MODULE$.StandardOutLogger().tell(envelope.message(), envelope.sender());
                    }
                }
                envelope = this.dequeue();
            }
        }
        QueueBasedMessageQueue.cleanUp$(this, owner, deadLetters);
    }

    public LoggerMailbox(ActorRef owner, ActorSystem system) {
        this.system = system;
    }
}

