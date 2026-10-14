/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.AbstractFSM;
import akka.actor.FSM;
import akka.actor.LoggingFSM;
import scala.collection.immutable.IndexedSeq;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u00152QAA\u0002\u0002\u0002!AQA\t\u0001\u0005\u0002\r\u0012!#\u00112tiJ\f7\r\u001e'pO\u001eLgn\u001a$T\u001b*\u0011A!B\u0001\u0006C\u000e$xN\u001d\u0006\u0002\r\u0005!\u0011m[6b\u0007\u0001)2!\u0003\t\u001e'\r\u0001!b\b\t\u0005\u00171qA$D\u0001\u0004\u0013\ti1AA\u0006BEN$(/Y2u\rNk\u0005CA\b\u0011\u0019\u0001!Q!\u0005\u0001C\u0002I\u0011\u0011aU\t\u0003'e\u0001\"\u0001F\f\u000e\u0003UQ\u0011AF\u0001\u0006g\u000e\fG.Y\u0005\u00031U\u0011qAT8uQ&tw\r\u0005\u0002\u00155%\u00111$\u0006\u0002\u0004\u0003:L\bCA\b\u001e\t\u0015q\u0002A1\u0001\u0013\u0005\u0005!\u0005\u0003B\u0006!\u001dqI!!I\u0002\u0003\u00151{wmZ5oO\u001a\u001bV*\u0001\u0004=S:LGO\u0010\u000b\u0002IA!1\u0002\u0001\b\u001d\u0001")
public abstract class AbstractLoggingFSM<S, D>
extends AbstractFSM<S, D>
implements LoggingFSM<S, D> {
    private boolean debugEvent;
    private FSM.Event<D>[] akka$actor$LoggingFSM$$events;
    private Object[] akka$actor$LoggingFSM$$states;
    private int akka$actor$LoggingFSM$$pos;
    private boolean akka$actor$LoggingFSM$$full;

    @Override
    public /* synthetic */ void akka$actor$LoggingFSM$$super$processEvent(FSM.Event event, Object source2) {
        FSM.processEvent$(this, event, source2);
    }

    @Override
    public int logDepth() {
        return LoggingFSM.logDepth$(this);
    }

    @Override
    public void processEvent(FSM.Event<D> event, Object source2) {
        LoggingFSM.processEvent$(this, event, source2);
    }

    @Override
    public IndexedSeq<FSM.LogEntry<S, D>> getLog() {
        return LoggingFSM.getLog$(this);
    }

    @Override
    public boolean debugEvent() {
        return this.debugEvent;
    }

    @Override
    public FSM.Event<D>[] akka$actor$LoggingFSM$$events() {
        return this.akka$actor$LoggingFSM$$events;
    }

    @Override
    public Object[] akka$actor$LoggingFSM$$states() {
        return this.akka$actor$LoggingFSM$$states;
    }

    @Override
    public int akka$actor$LoggingFSM$$pos() {
        return this.akka$actor$LoggingFSM$$pos;
    }

    @Override
    public void akka$actor$LoggingFSM$$pos_$eq(int x$1) {
        this.akka$actor$LoggingFSM$$pos = x$1;
    }

    @Override
    public boolean akka$actor$LoggingFSM$$full() {
        return this.akka$actor$LoggingFSM$$full;
    }

    @Override
    public void akka$actor$LoggingFSM$$full_$eq(boolean x$1) {
        this.akka$actor$LoggingFSM$$full = x$1;
    }

    @Override
    public void akka$actor$LoggingFSM$_setter_$debugEvent_$eq(boolean x$1) {
        this.debugEvent = x$1;
    }

    @Override
    public final void akka$actor$LoggingFSM$_setter_$akka$actor$LoggingFSM$$events_$eq(FSM.Event<D>[] x$1) {
        this.akka$actor$LoggingFSM$$events = x$1;
    }

    @Override
    public final void akka$actor$LoggingFSM$_setter_$akka$actor$LoggingFSM$$states_$eq(Object[] x$1) {
        this.akka$actor$LoggingFSM$$states = x$1;
    }

    public AbstractLoggingFSM() {
        LoggingFSM.$init$(this);
        Statics.releaseFence();
    }
}

