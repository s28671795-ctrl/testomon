/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.Pop$;
import akka.stream.impl.Traversal;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0019<aa\u0003\u0007\t\u0002B\u0011bA\u0002\u000b\r\u0011\u0003\u0003R\u0003C\u00030\u0003\u0011\u0005\u0001\u0007C\u00042\u0003\u0005\u0005I\u0011\t\u001a\t\u000fm\n\u0011\u0011!C\u0001y!9\u0001)AA\u0001\n\u0003\t\u0005bB$\u0002\u0003\u0003%\t\u0005\u0013\u0005\b\u001f\u0006\t\t\u0011\"\u0001Q\u0011\u001d)\u0016!!A\u0005BYCqaV\u0001\u0002\u0002\u0013\u0005\u0003\fC\u0004Z\u0003\u0005\u0005I\u0011\u0002.\u0002\u0007A{\u0007O\u0003\u0002\u000e\u001d\u0005!\u0011.\u001c9m\u0015\ty\u0001#\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002#\u0005!\u0011m[6b!\t\u0019\u0012!D\u0001\r\u0005\r\u0001v\u000e]\n\u0006\u0003YarD\t\t\u0003/ii\u0011\u0001\u0007\u0006\u00023\u0005)1oY1mC&\u00111\u0004\u0007\u0002\u0007\u0003:L(+\u001a4\u0011\u0005Mi\u0012B\u0001\u0010\r\u0005Mi\u0015\r^3sS\u0006d\u0017N_3e-\u0006dW/Z(q!\t9\u0002%\u0003\u0002\"1\t9\u0001K]8ek\u000e$\bCA\u0012-\u001d\t!#F\u0004\u0002&S5\taE\u0003\u0002(Q\u00051AH]8piz\u001a\u0001!C\u0001\u001a\u0013\tY\u0003$A\u0004qC\u000e\\\u0017mZ3\n\u00055r#\u0001D*fe&\fG.\u001b>bE2,'BA\u0016\u0019\u0003\u0019a\u0014N\\5u}Q\t!#A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002gA\u0011A'O\u0007\u0002k)\u0011agN\u0001\u0005Y\u0006twMC\u00019\u0003\u0011Q\u0017M^1\n\u0005i*$AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001>!\t9b(\u0003\u0002@1\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011!)\u0012\t\u0003/\rK!\u0001\u0012\r\u0003\u0007\u0005s\u0017\u0010C\u0004G\u000b\u0005\u0005\t\u0019A\u001f\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005I\u0005c\u0001&N\u00056\t1J\u0003\u0002M1\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u00059[%\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$\"!\u0015+\u0011\u0005]\u0011\u0016BA*\u0019\u0005\u001d\u0011un\u001c7fC:DqAR\u0004\u0002\u0002\u0003\u0007!)\u0001\u0005iCND7i\u001c3f)\u0005i\u0014\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003M\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\u0012a\u0017\t\u0003iqK!!X\u001b\u0003\r=\u0013'.Z2uQ\t\tq\f\u0005\u0002aG6\t\u0011M\u0003\u0002c!\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005\u0011\f'aC%oi\u0016\u0014h.\u00197Ba&D#\u0001A0")
@InternalApi
public final class Pop {
    public static String toString() {
        return Pop$.MODULE$.toString();
    }

    public static int hashCode() {
        return Pop$.MODULE$.hashCode();
    }

    public static boolean canEqual(Object x$1) {
        return Pop$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return Pop$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return Pop$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return Pop$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return Pop$.MODULE$.productPrefix();
    }

    public static Iterator<String> productElementNames() {
        return Pop$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return Pop$.MODULE$.productElementName(n);
    }

    public static Traversal rewireFirstTo(int relativeOffset) {
        return Pop$.MODULE$.rewireFirstTo(relativeOffset);
    }

    public static Traversal concat(Traversal that) {
        return Pop$.MODULE$.concat(that);
    }
}

