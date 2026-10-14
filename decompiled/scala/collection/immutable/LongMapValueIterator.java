/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.immutable.LongMap;
import scala.collection.immutable.LongMapIterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E2Q\u0001B\u0003\u0001\u000b-A\u0001B\b\u0001\u0003\u0002\u0003\u0006Ia\b\u0005\u0006E\u0001!\ta\t\u0005\u0006M\u0001!\ta\n\u0002\u0015\u0019>tw-T1q-\u0006dW/Z%uKJ\fGo\u001c:\u000b\u0005\u00199\u0011!C5n[V$\u0018M\u00197f\u0015\tA\u0011\"\u0001\u0006d_2dWm\u0019;j_:T\u0011AC\u0001\u0006g\u000e\fG.Y\u000b\u0003\u0019M\u0019\"\u0001A\u0007\u0011\t9y\u0011#E\u0007\u0002\u000b%\u0011\u0001#\u0002\u0002\u0010\u0019>tw-T1q\u0013R,'/\u0019;peB\u0011!c\u0005\u0007\u0001\t\u0015!\u0002A1\u0001\u0017\u0005\u000516\u0001A\t\u0003/m\u0001\"\u0001G\r\u000e\u0003%I!AG\u0005\u0003\u000f9{G\u000f[5oOB\u0011\u0001\u0004H\u0005\u0003;%\u00111!\u00118z\u0003\tIG\u000fE\u0002\u000fAEI!!I\u0003\u0003\u000f1{gnZ'ba\u00061A(\u001b8jiz\"\"\u0001J\u0013\u0011\u00079\u0001\u0011\u0003C\u0003\u001f\u0005\u0001\u0007q$A\u0004wC2,Xm\u00144\u0015\u0005EA\u0003\"B\u0015\u0004\u0001\u0004Q\u0013a\u0001;jaB\u00191FL\t\u000f\u00059a\u0013BA\u0017\u0006\u0003\u001dauN\\4NCBL!a\f\u0019\u0003\u0007QK\u0007O\u0003\u0002.\u000b\u0001")
public class LongMapValueIterator<V>
extends LongMapIterator<V, V> {
    @Override
    public V valueOf(LongMap.Tip<V> tip) {
        return tip.value();
    }

    public LongMapValueIterator(LongMap<V> it) {
        super(it);
    }
}

