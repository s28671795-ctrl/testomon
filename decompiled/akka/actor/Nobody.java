/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.InternalActorRef;
import akka.actor.Nobody$;
import akka.actor.RootActorPath;
import akka.dispatch.sysmsg.SystemMessage;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005]<aAD\b\t\u0002F\u0019bAB\u000b\u0010\u0011\u0003\u000bb\u0003C\u00031\u0003\u0011\u0005\u0011\u0007C\u00043\u0003\t\u0007I\u0011I\u001a\t\r]\n\u0001\u0015!\u00035\u0011\u0015A\u0014\u0001\"\u0011:\u0011\u001di\u0014A1A\u0005\nyBaAQ\u0001!\u0002\u0013y\u0004\"B\"\u0002\t#\"\u0005bB+\u0002\u0003\u0003%\tE\u0016\u0005\b;\u0006\t\t\u0011\"\u0001_\u0011\u001d\u0011\u0017!!A\u0005\u0002\rDq![\u0001\u0002\u0002\u0013\u0005#\u000eC\u0004r\u0003\u0005\u0005I\u0011\u0001:\u0002\r9{'m\u001c3z\u0015\t\u0001\u0012#A\u0003bGR|'OC\u0001\u0013\u0003\u0011\t7n[1\u0011\u0005Q\tQ\"A\b\u0003\r9{'m\u001c3z'\u0015\tqCG\u000f$!\t!\u0002$\u0003\u0002\u001a\u001f\t\u0001\u0012J\u001c;fe:\fG.Q2u_J\u0014VM\u001a\t\u0003)mI!\u0001H\b\u0003\u001f5Kg.[7bY\u0006\u001bGo\u001c:SK\u001a\u0004\"AH\u0011\u000e\u0003}Q\u0011\u0001I\u0001\u0006g\u000e\fG.Y\u0005\u0003E}\u0011q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002%[9\u0011Qe\u000b\b\u0003M)j\u0011a\n\u0006\u0003Q%\na\u0001\u0010:p_Rt4\u0001A\u0005\u0002A%\u0011AfH\u0001\ba\u0006\u001c7.Y4f\u0013\tqsF\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002-?\u00051A(\u001b8jiz\"\u0012aE\u0001\u0005a\u0006$\b.F\u00015!\t!R'\u0003\u00027\u001f\ti!k\\8u\u0003\u000e$xN\u001d)bi\"\fQ\u0001]1uQ\u0002\n\u0001\u0002\u001d:pm&$WM]\u000b\u0002uA\u0011adO\u0005\u0003y}\u0011qAT8uQ&tw-\u0001\u0006tKJL\u0017\r\\5{K\u0012,\u0012a\u0010\t\u0003)\u0001K!!Q\b\u0003!M+'/[1mSj,GMT8c_\u0012L\u0018aC:fe&\fG.\u001b>fI\u0002\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\u0012!\u0012\t\u0003=\u0019K!aR\u0010\u0003\r\u0005s\u0017PU3gQ\rA\u0011\n\u0016\t\u0004=)c\u0015BA& \u0005\u0019!\bN]8xgB\u0011QJU\u0007\u0002\u001d*\u0011q\nU\u0001\u0003S>T\u0011!U\u0001\u0005U\u00064\u0018-\u0003\u0002T\u001d\n)rJ\u00196fGR\u001cFO]3b[\u0016C8-\u001a9uS>t7%\u0001'\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u00059\u0006C\u0001-\\\u001b\u0005I&B\u0001.Q\u0003\u0011a\u0017M\\4\n\u0005qK&AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001`!\tq\u0002-\u0003\u0002b?\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011Am\u001a\t\u0003=\u0015L!AZ\u0010\u0003\u0007\u0005s\u0017\u0010C\u0004i\u0017\u0005\u0005\t\u0019A0\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005Y\u0007c\u00017pI6\tQN\u0003\u0002o?\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005Al'\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$\"a\u001d<\u0011\u0005y!\u0018BA; \u0005\u001d\u0011un\u001c7fC:Dq\u0001[\u0007\u0002\u0002\u0003\u0007A\r")
public final class Nobody {
    public static boolean canEqual(Object x$1) {
        return Nobody$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return Nobody$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return Nobody$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return Nobody$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return Nobody$.MODULE$.productPrefix();
    }

    public static Nothing$ provider() {
        return Nobody$.MODULE$.provider();
    }

    public static RootActorPath path() {
        return Nobody$.MODULE$.path();
    }

    public static Iterator<String> productElementNames() {
        return Nobody$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return Nobody$.MODULE$.productElementName(n);
    }

    public static void restart(Throwable cause) {
        Nobody$.MODULE$.restart(cause);
    }

    public static void sendSystemMessage(SystemMessage message) {
        Nobody$.MODULE$.sendSystemMessage(message);
    }

    public static ActorRef $bang$default$2(Object message) {
        return Nobody$.MODULE$.$bang$default$2(message);
    }

    public static void $bang(Object message, ActorRef sender) {
        Nobody$.MODULE$.$bang(message, sender);
    }

    public static void stop() {
        Nobody$.MODULE$.stop();
    }

    public static void resume(Throwable causedByFailure) {
        Nobody$.MODULE$.resume(causedByFailure);
    }

    public static void suspend() {
        Nobody$.MODULE$.suspend();
    }

    public static void start() {
        Nobody$.MODULE$.start();
    }

    public static InternalActorRef getChild(Iterator<String> names) {
        return Nobody$.MODULE$.getChild(names);
    }

    public static InternalActorRef getParent() {
        return Nobody$.MODULE$.getParent();
    }

    public static boolean isLocal() {
        return Nobody$.MODULE$.isLocal();
    }

    public static String toString() {
        return Nobody$.MODULE$.toString();
    }

    public static boolean equals(Object that) {
        return Nobody$.MODULE$.equals(that);
    }

    public static int hashCode() {
        return Nobody$.MODULE$.hashCode();
    }

    public static void forward(Object message, ActorContext context) {
        Nobody$.MODULE$.forward(message, context);
    }

    public static void tell(Object msg, ActorRef sender) {
        Nobody$.MODULE$.tell(msg, sender);
    }

    public static int compareTo(ActorRef other) {
        return Nobody$.MODULE$.compareTo(other);
    }
}

