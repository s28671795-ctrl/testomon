/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorLogging;
import akka.event.LoggingAdapter;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M1QAA\u0002\u0002\u0002!AQ\u0001\u0005\u0001\u0005\u0002E\u0011A#\u00112tiJ\f7\r\u001e'pO\u001eLgnZ!di>\u0014(B\u0001\u0003\u0006\u0003\u0015\t7\r^8s\u0015\u00051\u0011\u0001B1lW\u0006\u001c\u0001aE\u0002\u0001\u00135\u0001\"AC\u0006\u000e\u0003\rI!\u0001D\u0002\u0003\u001b\u0005\u00137\u000f\u001e:bGR\f5\r^8s!\tQa\"\u0003\u0002\u0010\u0007\ta\u0011i\u0019;pe2{wmZ5oO\u00061A(\u001b8jiz\"\u0012A\u0005\t\u0003\u0015\u0001\u0001")
public abstract class AbstractLoggingActor
extends AbstractActor
implements ActorLogging {
    private LoggingAdapter akka$actor$ActorLogging$$_log;

    @Override
    public LoggingAdapter log() {
        return ActorLogging.log$(this);
    }

    @Override
    public LoggingAdapter akka$actor$ActorLogging$$_log() {
        return this.akka$actor$ActorLogging$$_log;
    }

    @Override
    public void akka$actor$ActorLogging$$_log_$eq(LoggingAdapter x$1) {
        this.akka$actor$ActorLogging$$_log = x$1;
    }

    public AbstractLoggingActor() {
        ActorLogging.$init$(this);
    }
}

