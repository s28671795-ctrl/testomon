/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.immutable.LongMap;
import scala.collection.immutable.LongMapIterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q2Q\u0001B\u0003\u0001\u000b-A\u0001\"\u000b\u0001\u0003\u0002\u0003\u0006IA\u000b\u0005\u0006[\u0001!\tA\f\u0005\u0006c\u0001!\tA\r\u0002\u0013\u0019>tw-T1q\u0017\u0016L\u0018\n^3sCR|'O\u0003\u0002\u0007\u000f\u0005I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003\u0011%\t!bY8mY\u0016\u001cG/[8o\u0015\u0005Q\u0011!B:dC2\fWC\u0001\u0007\u0014'\t\u0001Q\u0002\u0005\u0003\u000f\u001fEqR\"A\u0003\n\u0005A)!a\u0004'p]\u001el\u0015\r]%uKJ\fGo\u001c:\u0011\u0005I\u0019B\u0002\u0001\u0003\u0006)\u0001\u0011\rA\u0006\u0002\u0002-\u000e\u0001\u0011CA\f\u001c!\tA\u0012$D\u0001\n\u0013\tQ\u0012BA\u0004O_RD\u0017N\\4\u0011\u0005aa\u0012BA\u000f\n\u0005\r\te.\u001f\t\u0003?\tr!A\u0004\u0011\n\u0005\u0005*\u0011\u0001\u0004'p]\u001el\u0015\r]+uS2\u001c\u0018BA\u0012%\u0005\u0011auN\\4\n\u0005\r*#B\u0001\u0014(\u00035\u0011\u0015\u000e^(qKJ\fG/[8og*\u0011\u0001fB\u0001\bO\u0016tWM]5d\u0003\tIG\u000fE\u0002\u000fWEI!\u0001L\u0003\u0003\u000f1{gnZ'ba\u00061A(\u001b8jiz\"\"a\f\u0019\u0011\u00079\u0001\u0011\u0003C\u0003*\u0005\u0001\u0007!&A\u0004wC2,Xm\u00144\u0015\u0005y\u0019\u0004\"\u0002\u001b\u0004\u0001\u0004)\u0014a\u0001;jaB\u0019a'O\t\u000f\u000599\u0014B\u0001\u001d\u0006\u0003\u001dauN\\4NCBL!AO\u001e\u0003\u0007QK\u0007O\u0003\u00029\u000b\u0001")
public class LongMapKeyIterator<V>
extends LongMapIterator<V, Object> {
    @Override
    public long valueOf(LongMap.Tip<V> tip) {
        return tip.key();
    }

    public LongMapKeyIterator(LongMap<V> it) {
        super(it);
    }
}

