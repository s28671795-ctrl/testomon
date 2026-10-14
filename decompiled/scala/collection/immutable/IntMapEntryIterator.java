/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Tuple2;
import scala.collection.immutable.IntMap;
import scala.collection.immutable.IntMapIterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}2Q\u0001B\u0003\u0001\u000b-A\u0001\u0002\f\u0001\u0003\u0002\u0003\u0006I!\f\u0005\u0006a\u0001!\t!\r\u0005\u0006i\u0001!\t!\u000e\u0002\u0014\u0013:$X*\u00199F]R\u0014\u00180\u0013;fe\u0006$xN\u001d\u0006\u0003\r\u001d\t\u0011\"[7nkR\f'\r\\3\u000b\u0005!I\u0011AC2pY2,7\r^5p]*\t!\"A\u0003tG\u0006d\u0017-\u0006\u0002\r'M\u0011\u0001!\u0004\t\u0005\u001d=\tb$D\u0001\u0006\u0013\t\u0001RA\u0001\bJ]Rl\u0015\r]%uKJ\fGo\u001c:\u0011\u0005I\u0019B\u0002\u0001\u0003\u0006)\u0001\u0011\rA\u0006\u0002\u0002-\u000e\u0001\u0011CA\f\u001c!\tA\u0012$D\u0001\n\u0013\tQ\u0012BA\u0004O_RD\u0017N\\4\u0011\u0005aa\u0012BA\u000f\n\u0005\r\te.\u001f\t\u00051}\t\u0013#\u0003\u0002!\u0013\t1A+\u001e9mKJ\u0002\"AI\u0013\u000f\u00059\u0019\u0013B\u0001\u0013\u0006\u0003-Ie\u000e^'baV#\u0018\u000e\\:\n\u0005\u0019:#aA%oi&\u0011a\u0005\u000b\u0006\u0003S)\nQBQ5u\u001fB,'/\u0019;j_:\u001c(BA\u0016\b\u0003\u001d9WM\\3sS\u000e\f!!\u001b;\u0011\u00079q\u0013#\u0003\u00020\u000b\t1\u0011J\u001c;NCB\fa\u0001P5oSRtDC\u0001\u001a4!\rq\u0001!\u0005\u0005\u0006Y\t\u0001\r!L\u0001\bm\u0006dW/Z(g)\tqb\u0007C\u00038\u0007\u0001\u0007\u0001(A\u0002uSB\u00042!\u000f\u001f\u0012\u001d\tq!(\u0003\u0002<\u000b\u00051\u0011J\u001c;NCBL!!\u0010 \u0003\u0007QK\u0007O\u0003\u0002<\u000b\u0001")
public class IntMapEntryIterator<V>
extends IntMapIterator<V, Tuple2<Object, V>> {
    @Override
    public Tuple2<Object, V> valueOf(IntMap.Tip<V> tip) {
        return new Tuple2<Integer, V>(tip.key(), tip.value());
    }

    public IntMapEntryIterator(IntMap<V> it) {
        super(it);
    }
}

