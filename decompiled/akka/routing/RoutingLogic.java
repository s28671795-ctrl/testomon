/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.NoSerializationVerificationNeeded;
import akka.routing.Routee;
import scala.collection.immutable.IndexedSeq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)2qAA\u0002\u0011\u0002G\u0005\u0001\u0002C\u0003\u0016\u0001\u0019\u0005aC\u0001\u0007S_V$\u0018N\\4M_\u001eL7M\u0003\u0002\u0005\u000b\u00059!o\\;uS:<'\"\u0001\u0004\u0002\t\u0005\\7.Y\u0002\u0001'\r\u0001\u0011b\u0004\t\u0003\u00155i\u0011a\u0003\u0006\u0002\u0019\u0005)1oY1mC&\u0011ab\u0003\u0002\u0007\u0003:L(+\u001a4\u0011\u0005A\u0019R\"A\t\u000b\u0005I)\u0011!B1di>\u0014\u0018B\u0001\u000b\u0012\u0005\u0005runU3sS\u0006d\u0017N_1uS>tg+\u001a:jM&\u001c\u0017\r^5p]:+W\rZ3e\u0003\u0019\u0019X\r\\3diR\u0019qc\u0007\u0011\u0011\u0005aIR\"A\u0002\n\u0005i\u0019!A\u0002*pkR,W\rC\u0003\u001d\u0003\u0001\u0007Q$A\u0004nKN\u001c\u0018mZ3\u0011\u0005)q\u0012BA\u0010\f\u0005\r\te.\u001f\u0005\u0006C\u0005\u0001\rAI\u0001\be>,H/Z3t!\r\u0019\u0003fF\u0007\u0002I)\u0011QEJ\u0001\nS6lW\u000f^1cY\u0016T!aJ\u0006\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002*I\tQ\u0011J\u001c3fq\u0016$7+Z9")
public interface RoutingLogic
extends NoSerializationVerificationNeeded {
    public Routee select(Object var1, IndexedSeq<Routee> var2);
}

