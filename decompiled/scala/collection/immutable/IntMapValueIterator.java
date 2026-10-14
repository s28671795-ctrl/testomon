/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.immutable.IntMap;
import scala.collection.immutable.IntMapIterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E2Q\u0001B\u0003\u0001\u000b-A\u0001B\b\u0001\u0003\u0002\u0003\u0006Ia\b\u0005\u0006E\u0001!\ta\t\u0005\u0006M\u0001!\ta\n\u0002\u0014\u0013:$X*\u00199WC2,X-\u0013;fe\u0006$xN\u001d\u0006\u0003\r\u001d\t\u0011\"[7nkR\f'\r\\3\u000b\u0005!I\u0011AC2pY2,7\r^5p]*\t!\"A\u0003tG\u0006d\u0017-\u0006\u0002\r'M\u0011\u0001!\u0004\t\u0005\u001d=\t\u0012#D\u0001\u0006\u0013\t\u0001RA\u0001\bJ]Rl\u0015\r]%uKJ\fGo\u001c:\u0011\u0005I\u0019B\u0002\u0001\u0003\u0006)\u0001\u0011\rA\u0006\u0002\u0002-\u000e\u0001\u0011CA\f\u001c!\tA\u0012$D\u0001\n\u0013\tQ\u0012BA\u0004O_RD\u0017N\\4\u0011\u0005aa\u0012BA\u000f\n\u0005\r\te._\u0001\u0003SR\u00042A\u0004\u0011\u0012\u0013\t\tSA\u0001\u0004J]Rl\u0015\r]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\u0011*\u0003c\u0001\b\u0001#!)aD\u0001a\u0001?\u00059a/\u00197vK>3GCA\t)\u0011\u0015I3\u00011\u0001+\u0003\r!\u0018\u000e\u001d\t\u0004W9\nbB\u0001\b-\u0013\tiS!\u0001\u0004J]Rl\u0015\r]\u0005\u0003_A\u00121\u0001V5q\u0015\tiS\u0001")
public class IntMapValueIterator<V>
extends IntMapIterator<V, V> {
    @Override
    public V valueOf(IntMap.Tip<V> tip) {
        return tip.value();
    }

    public IntMapValueIterator(IntMap<V> it) {
        super(it);
    }
}

