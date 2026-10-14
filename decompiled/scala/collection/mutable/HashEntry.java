/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E2\u0001\"\u0002\u0004\u0011\u0002\u0007\u0005\u0001\u0002\u0004\u0005\u0006%\u0001!\t\u0001\u0006\u0005\b1\u0001\u0011\rQ\"\u0001\u001a\u0011%)\u0003\u00011AA\u0002\u0013\u0005a\u0005C\u0005.\u0001\u0001\u0007\t\u0019!C\u0001]\tI\u0001*Y:i\u000b:$(/\u001f\u0006\u0003\u000f!\tq!\\;uC\ndWM\u0003\u0002\n\u0015\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003-\tQa]2bY\u0006,2!\u0004\u000f)'\t\u0001a\u0002\u0005\u0002\u0010!5\t!\"\u0003\u0002\u0012\u0015\t1\u0011I\\=SK\u001a\fa\u0001J5oSR$3\u0001\u0001\u000b\u0002+A\u0011qBF\u0005\u0003/)\u0011A!\u00168ji\u0006\u00191.Z=\u0016\u0003i\u0001\"a\u0007\u000f\r\u0001\u0011)Q\u0004\u0001b\u0001=\t\t\u0011)\u0005\u0002 EA\u0011q\u0002I\u0005\u0003C)\u0011qAT8uQ&tw\r\u0005\u0002\u0010G%\u0011AE\u0003\u0002\u0004\u0003:L\u0018\u0001\u00028fqR,\u0012a\n\t\u00037!\"Q!\u000b\u0001C\u0002)\u0012\u0011!R\t\u0003?-\u0002B\u0001\f\u0001\u001bO5\ta!\u0001\u0005oKb$x\fJ3r)\t)r\u0006C\u00041\t\u0005\u0005\t\u0019A\u0014\u0002\u0007a$\u0013\u0007")
public interface HashEntry<A, E extends HashEntry<A, E>> {
    public A key();

    public E next();

    public void next_$eq(E var1);

    public static void $init$(HashEntry $this) {
    }
}

