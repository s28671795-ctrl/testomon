/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.FSM;
import java.io.Serializable;
import scala.Function1;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.ArrayOps$;
import scala.collection.IterableOps;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005\ra\u0001\u0003\b\u0010!\u0003\r\t\u0001\u0006>\t\u000b9\u0002A\u0011A\u0018\t\u000bM\u0002A\u0011\u0001\u001b\t\u0011a\u0002!\u0019!C!#eBq!\u0010\u0001C\u0002\u0013%a\bC\u0004G\u0001\t\u0007I\u0011B$\t\u000f%\u0003\u0001\u0019!C\u0005i!9!\n\u0001a\u0001\n\u0013Y\u0005b\u0002(\u0001\u0001\u0004%I!\u000f\u0005\b\u001f\u0002\u0001\r\u0011\"\u0003Q\u0011\u0015\u0011\u0006\u0001\"\u00030\u0011\u001d\u0019\u0006\u0001%C\u0001#QCQ!\u0017\u0001\u0005\u0012iC1B\u001c\u0001\u0011\u0002\u0007\u0005\t\u0011\"\u0003ps\nQAj\\4hS:<giU'\u000b\u0005A\t\u0012!B1di>\u0014(\"\u0001\n\u0002\t\u0005\\7.Y\u0002\u0001+\r)\"\u0005L\n\u0004\u0001Ya\u0002CA\f\u001b\u001b\u0005A\"\"A\r\u0002\u000bM\u001c\u0017\r\\1\n\u0005mA\"AB!osJ+g\r\u0005\u0003\u001e=\u0001ZS\"A\b\n\u0005}y!a\u0001$T\u001bB\u0011\u0011E\t\u0007\u0001\t\u0015\u0019\u0003A1\u0001%\u0005\u0005\u0019\u0016CA\u0013)!\t9b%\u0003\u0002(1\t9aj\u001c;iS:<\u0007CA\f*\u0013\tQ\u0003DA\u0002B]f\u0004\"!\t\u0017\u0005\u000b5\u0002!\u0019\u0001\u0013\u0003\u0003\u0011\u000ba\u0001J5oSR$C#\u0001\u0019\u0011\u0005]\t\u0014B\u0001\u001a\u0019\u0005\u0011)f.\u001b;\u0002\u00111|w\rR3qi\",\u0012!\u000e\t\u0003/YJ!a\u000e\r\u0003\u0007%sG/\u0001\u0006eK\n,x-\u0012<f]R,\u0012A\u000f\t\u0003/mJ!\u0001\u0010\r\u0003\u000f\t{w\u000e\\3b]\u00061QM^3oiN,\u0012a\u0010\t\u0004/\u0001\u0013\u0015BA!\u0019\u0005\u0015\t%O]1z!\t\u0019E)D\u0001\u0001\u0013\t)eDA\u0003Fm\u0016tG/\u0001\u0004ti\u0006$Xm]\u000b\u0002\u0011B\u0019q\u0003\u0011\f\u0002\u0007A|7/A\u0004q_N|F%Z9\u0015\u0005Ab\u0005bB'\b\u0003\u0003\u0005\r!N\u0001\u0004q\u0012\n\u0014\u0001\u00024vY2\f\u0001BZ;mY~#S-\u001d\u000b\u0003aECq!T\u0005\u0002\u0002\u0003\u0007!(A\u0004bIZ\fgnY3\u0002\u0019A\u0014xnY3tg\u00163XM\u001c;\u0015\u0007A*v\u000bC\u0003W\u0017\u0001\u0007!)A\u0003fm\u0016tG\u000fC\u0003Y\u0017\u0001\u0007a#\u0001\u0004t_V\u00148-Z\u0001\u0007O\u0016$Hj\\4\u0016\u0003m\u00032\u0001\u00183h\u001d\ti&M\u0004\u0002_C6\tqL\u0003\u0002a'\u00051AH]8pizJ\u0011!G\u0005\u0003Gb\tq\u0001]1dW\u0006<W-\u0003\u0002fM\nQ\u0011J\u001c3fq\u0016$7+Z9\u000b\u0005\rD\u0002\u0003\u00025lA-r!!H5\n\u0005)|\u0011a\u0001$T\u001b&\u0011A.\u001c\u0002\t\u0019><WI\u001c;ss*\u0011!nD\u0001\u0013gV\u0004XM\u001d\u0013qe>\u001cWm]:Fm\u0016tG\u000fF\u00021aFDQAV\u0007A\u0002\tCQ\u0001W\u0007A\u0002YA#!]:\u0011\u0005Q<X\"A;\u000b\u0005Y\f\u0012\u0001B;uS2L!\u0001_;\u0003\rUtWo]3e\u0013\t\u0019fDE\u0002|{z4A\u0001 \u0001\u0001u\naAH]3gS:,W.\u001a8u}A!Q\u0004\u0001\u0011,!\tir0C\u0002\u0002\u0002=\u0011Q!Q2u_J\u0004")
public interface LoggingFSM<S, D>
extends FSM<S, D> {
    public void akka$actor$LoggingFSM$_setter_$debugEvent_$eq(boolean var1);

    public void akka$actor$LoggingFSM$_setter_$akka$actor$LoggingFSM$$events_$eq(FSM.Event<D>[] var1);

    public void akka$actor$LoggingFSM$_setter_$akka$actor$LoggingFSM$$states_$eq(Object[] var1);

    public /* synthetic */ void akka$actor$LoggingFSM$$super$processEvent(FSM.Event var1, Object var2);

    public static /* synthetic */ int logDepth$(LoggingFSM $this) {
        return $this.logDepth();
    }

    default public int logDepth() {
        return 0;
    }

    @Override
    public boolean debugEvent();

    public FSM.Event<D>[] akka$actor$LoggingFSM$$events();

    public Object[] akka$actor$LoggingFSM$$states();

    public int akka$actor$LoggingFSM$$pos();

    public void akka$actor$LoggingFSM$$pos_$eq(int var1);

    public boolean akka$actor$LoggingFSM$$full();

    public void akka$actor$LoggingFSM$$full_$eq(boolean var1);

    private void advance() {
        int n = this.akka$actor$LoggingFSM$$pos() + 1;
        if (n == this.logDepth()) {
            this.akka$actor$LoggingFSM$$full_$eq(true);
            this.akka$actor$LoggingFSM$$pos_$eq(0);
            return;
        }
        this.akka$actor$LoggingFSM$$pos_$eq(n);
    }

    public static /* synthetic */ void processEvent$(LoggingFSM $this, FSM.Event event, Object source2) {
        $this.processEvent(event, source2);
    }

    @Override
    default public void processEvent(FSM.Event<D> event, Object source2) {
        if (this.debugEvent()) {
            String string;
            Object object = source2;
            if (object instanceof String) {
                String string2;
                string = string2 = (String)object;
            } else if (object instanceof FSM.Timer) {
                FSM.Timer timer = (FSM.Timer)object;
                String name = timer.name();
                string = new StringBuilder(6).append("timer ").append(name).toString();
            } else if (object instanceof ActorRef) {
                ActorRef actorRef2 = (ActorRef)object;
                string = actorRef2.toString();
            } else {
                string = "unknown";
            }
            String srcstr = string;
            this.log().debug("processing {} from {} in state {}", event, srcstr, this.stateName());
        }
        if (this.logDepth() > 0) {
            this.akka$actor$LoggingFSM$$states()[this.akka$actor$LoggingFSM$$pos()] = this.stateName();
            this.akka$actor$LoggingFSM$$events()[this.akka$actor$LoggingFSM$$pos()] = event;
            this.advance();
        }
        Object oldState = this.stateName();
        this.akka$actor$LoggingFSM$$super$processEvent(event, source2);
        Object newState = this.stateName();
        if (this.debugEvent() && !BoxesRunTime.equals(oldState, newState)) {
            this.log().debug(new StringBuilder(15).append("transition ").append(oldState).append(" -> ").append(newState).toString());
            return;
        }
    }

    public static /* synthetic */ IndexedSeq getLog$(LoggingFSM $this) {
        return $this.getLog();
    }

    default public IndexedSeq<FSM.LogEntry<S, D>> getLog() {
        FSM.LogEntry[] log = (FSM.LogEntry[])ArrayOps$.MODULE$.map$extension(Predef$.MODULE$.refArrayOps((Object[])ArrayOps$.MODULE$.filter$extension(Predef$.MODULE$.refArrayOps(ArrayOps$.MODULE$.zip$extension(Predef$.MODULE$.refArrayOps(this.akka$actor$LoggingFSM$$events()), Predef$.MODULE$.wrapRefArray(this.akka$actor$LoggingFSM$$states()))), (Function1<Tuple2, Object> & Serializable)x$5 -> BoxesRunTime.boxToBoolean(LoggingFSM.$anonfun$getLog$1(x$5)))), (Function1<Tuple2, FSM.LogEntry> & Serializable)x -> new FSM.LogEntry(x._2(), ((FSM.Event)x._1()).stateData(), ((FSM.Event)x._1()).event()), ClassTag$.MODULE$.apply(FSM.LogEntry.class));
        if (this.akka$actor$LoggingFSM$$full()) {
            return (IndexedSeq)((IterableOps)package$.MODULE$.IndexedSeq().apply((Seq)Nil$.MODULE$).$plus$plus(Predef$.MODULE$.wrapRefArray((Object[])ArrayOps$.MODULE$.drop$extension(Predef$.MODULE$.refArrayOps(log), this.akka$actor$LoggingFSM$$pos())))).$plus$plus(Predef$.MODULE$.wrapRefArray((Object[])ArrayOps$.MODULE$.take$extension(Predef$.MODULE$.refArrayOps(log), this.akka$actor$LoggingFSM$$pos())));
        }
        return (IndexedSeq)package$.MODULE$.IndexedSeq().apply((Seq)Nil$.MODULE$).$plus$plus(Predef$.MODULE$.wrapRefArray((Object[])log));
    }

    public static /* synthetic */ boolean $anonfun$getLog$1(Tuple2 x$5) {
        return x$5._1() != null;
    }

    public static void $init$(LoggingFSM $this) {
        $this.akka$actor$LoggingFSM$_setter_$debugEvent_$eq($this.context().system().settings().FsmDebugEvent());
        $this.akka$actor$LoggingFSM$_setter_$akka$actor$LoggingFSM$$events_$eq(new FSM.Event[$this.logDepth()]);
        $this.akka$actor$LoggingFSM$_setter_$akka$actor$LoggingFSM$$states_$eq(new Object[$this.logDepth()]);
        $this.akka$actor$LoggingFSM$$pos_$eq(0);
        $this.akka$actor$LoggingFSM$$full_$eq(false);
    }
}

