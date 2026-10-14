/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.collection.immutable.IntMap;
import scala.collection.immutable.IntMapIterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q2Q\u0001B\u0003\u0001\u000b-A\u0001\"\u000b\u0001\u0003\u0002\u0003\u0006IA\u000b\u0005\u0006[\u0001!\tA\f\u0005\u0006c\u0001!\tA\r\u0002\u0012\u0013:$X*\u00199LKfLE/\u001a:bi>\u0014(B\u0001\u0004\b\u0003%IW.\\;uC\ndWM\u0003\u0002\t\u0013\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003)\tQa]2bY\u0006,\"\u0001D\n\u0014\u0005\u0001i\u0001\u0003\u0002\b\u0010#yi\u0011!B\u0005\u0003!\u0015\u0011a\"\u00138u\u001b\u0006\u0004\u0018\n^3sCR|'\u000f\u0005\u0002\u0013'1\u0001A!\u0002\u000b\u0001\u0005\u00041\"!\u0001,\u0004\u0001E\u0011qc\u0007\t\u00031ei\u0011!C\u0005\u00035%\u0011qAT8uQ&tw\r\u0005\u0002\u00199%\u0011Q$\u0003\u0002\u0004\u0003:L\bCA\u0010#\u001d\tq\u0001%\u0003\u0002\"\u000b\u0005Y\u0011J\u001c;NCB,F/\u001b7t\u0013\t\u0019CEA\u0002J]RL!aI\u0013\u000b\u0005\u0019:\u0013!\u0004\"ji>\u0003XM]1uS>t7O\u0003\u0002)\u000f\u00059q-\u001a8fe&\u001c\u0017AA5u!\rq1&E\u0005\u0003Y\u0015\u0011a!\u00138u\u001b\u0006\u0004\u0018A\u0002\u001fj]&$h\b\u0006\u00020aA\u0019a\u0002A\t\t\u000b%\u0012\u0001\u0019\u0001\u0016\u0002\u000fY\fG.^3PMR\u0011ad\r\u0005\u0006i\r\u0001\r!N\u0001\u0004i&\u0004\bc\u0001\u001c:#9\u0011abN\u0005\u0003q\u0015\ta!\u00138u\u001b\u0006\u0004\u0018B\u0001\u001e<\u0005\r!\u0016\u000e\u001d\u0006\u0003q\u0015\u0001")
public class IntMapKeyIterator<V>
extends IntMapIterator<V, Object> {
    @Override
    public int valueOf(IntMap.Tip<V> tip) {
        return tip.key();
    }

    public IntMapKeyIterator(IntMap<V> it) {
        super(it);
    }
}

